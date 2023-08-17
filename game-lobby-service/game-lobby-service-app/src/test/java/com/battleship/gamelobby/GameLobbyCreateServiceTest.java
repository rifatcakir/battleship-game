package com.battleship.gamelobby;


import com.battleship.lobby.model.GameLobbyModel;
import com.battleship.lobby.repository.GameLobbyMysqlRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class GameLobbyCreateServiceTest {

    private final String baseUrl = "/game-lobby/v0";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GameLobbyUtil gameLobbyUtil;
    @Autowired
    private GameLobbyMysqlRepository gameLobbyMysqlRepository;

    @BeforeEach
    void setup() {
        gameLobbyMysqlRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "John", authorities = {"ROLE_PLAYER", "ROLE_USER"})
    void creatingNewGameLobbyShouldReturns200WhenUserAuthenticated() throws Exception {

        MvcResult response = mockMvc.perform(post(baseUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated()).andReturn();

        GameLobbyModel gameLobbyModel = gameLobbyUtil.mapMvcResultToObject(response, GameLobbyModel.class);

        assertThat(gameLobbyModel.getPlayer1Name()).isEqualTo("John");
        assertThat(gameLobbyMysqlRepository.findById(gameLobbyModel.getGameLobbyId())).isPresent();
    }

    @Test
    @WithAnonymousUser
    void creatingNewGameLobbyShouldReturns401WhenUserNotAuthenticated() throws Exception {
        mockMvc.perform(post(baseUrl + "/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized());
    }


}
