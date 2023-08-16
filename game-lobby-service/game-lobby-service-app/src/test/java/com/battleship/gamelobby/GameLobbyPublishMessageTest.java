package com.battleship.gamelobby;

import com.battleship.lobby.entity.GameLobby;
import com.battleship.lobby.messaging.model.GameLobbyMessage;
import com.battleship.lobby.repository.GameLobbyMysqlRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class GameLobbyPublishMessageTest {
    private final String baseUrl = "/game-lobby";
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private GameLobbyMysqlRepository gameLobbyMysqlRepository;
    @Value("${battleship.rabbitmq.queue}")
    private String queue;

    @BeforeEach
    void setUp() {
        rabbitAdmin.purgeQueue(queue, true);
    }

    @AfterEach
    void tearDown() {
        rabbitAdmin.purgeQueue(queue, true);
    }

    @Test
    @WithMockUser(username = "John", authorities = {"ROLE_PLAYER", "ROLE_USER"})
    void joinGameLobbyShouldSentMessageToQueue() throws Exception {
        GameLobby createdMockGameLobby = gameLobbyMysqlRepository.save(new GameLobby(UUID.randomUUID(), "Jane", null));

        mockMvc.perform(post(baseUrl + "/join/" + createdMockGameLobby.getGameLobbyId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        GameLobbyMessage message = getMessageSentInQueue();

        assertThat(message.getGameLobbyId()).isEqualTo(createdMockGameLobby.getGameLobbyId());
        assertThat(message.getPlayer1Name()).isEqualTo(createdMockGameLobby.getPlayer1Name());
        assertThat(message.getPlayer2Name()).isEqualTo("John");
    }

    private GameLobbyMessage getMessageSentInQueue() {
        Integer messageCount = Objects.requireNonNull(rabbitTemplate.execute(it -> it.queueDeclarePassive(queue))).getMessageCount();
        assertThat(messageCount).isEqualTo(1);
        return rabbitTemplate.receiveAndConvert(queue, new ParameterizedTypeReference<>() {
        });
    }
}
