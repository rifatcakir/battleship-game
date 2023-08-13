package com.battleship.gamelobby;


import com.battleship.lobby.entity.GameLobby;
import com.battleship.lobby.exception.GameLobbyNotAvailable;
import com.battleship.lobby.exception.GameLobbyNotFoundException;
import com.battleship.lobby.model.GameLobbyModel;
import com.battleship.lobby.repository.GameLobbyMysqlRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class GameLobbyJoinServiceTest {

    private final String baseUrl = "/game-lobby";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GameLobbyUtil gameLobbyUtil;
    @Autowired
    private GameLobbyMysqlRepository gameLobbyMysqlRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        gameLobbyMysqlRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "John", roles = {"USER"}, authorities = {"ROLE_PLAYER", "ROLE_USER"})
    void joinGameLobbyShouldReturns200WithAvailableLobby() throws Exception {
        GameLobby createdMockGameLobby = gameLobbyMysqlRepository.save(new GameLobby(UUID.randomUUID(), "Jane", null));

        MvcResult response = mockMvc.perform(post(baseUrl + "/join/" + createdMockGameLobby.getGameLobbyId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        GameLobbyModel lobbyResponse = gameLobbyUtil.mapMvcResultToObject(response, GameLobbyModel.class);

        assertThat(lobbyResponse.getGameLobbyId()).isEqualTo(createdMockGameLobby.getGameLobbyId());
        assertThat(lobbyResponse.getPlayer1Name()).isEqualTo(createdMockGameLobby.getPlayer1Name());
        assertThat(lobbyResponse.getPlayer2Name()).isEqualTo("John");
    }

    @Test
    @WithMockUser(username = "John", roles = {"USER"}, authorities = {"ROLE_PLAYER", "ROLE_USER"})
    void joinNotExistsGameLobbyShouldThrowsGameLobbyNotFoundException() throws Exception {
        mockMvc.perform(post(baseUrl + "/join/" + 12345).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof GameLobbyNotFoundException));
    }

    @Test
    @WithMockUser(username = "John", roles = {"USER"}, authorities = {"ROLE_PLAYER", "ROLE_USER"})
    void joinFullGameLobbyShouldThrowsGameLobbyNotAvailableException() throws Exception {
        GameLobby createdMockGameLobby = gameLobbyMysqlRepository.save(new GameLobby(UUID.randomUUID(), "Jane", "Gerrard"));

        mockMvc.perform(post(baseUrl + "/join/" + createdMockGameLobby.getGameLobbyId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof GameLobbyNotAvailable));
    }

    @Test
    @WithAnonymousUser
    void searchingGameLobbyShouldReturns401WhenUserNotAuthenticated() throws Exception {
        mockMvc.perform(post(baseUrl + "/find/available")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized());
    }

}
