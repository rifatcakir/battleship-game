package com.battleship.gamelobby;


import com.battleship.lobby.entity.GameLobby;
import com.battleship.lobby.model.GameLobbyModel;
import com.battleship.lobby.repository.GameLobbyMysqlRepository;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class GameLobbySearchServiceTest {

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
    void searchingGameLobbyShouldReturns200WithAvailableLobby() throws Exception {
        List<GameLobby> givenLobbies = List.of(new GameLobby(1, "Jane", null),
                new GameLobby(2, "Johan", null));

        gameLobbyMysqlRepository.saveAll(givenLobbies);

        MvcResult response = mockMvc.perform(get(baseUrl + "/find/available")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        List<GameLobbyModel> foundLobbies = gameLobbyUtil.mapMvcResultToObjects(response, new TypeReference<>() {
        });

        List<String> foundPlayer1 = foundLobbies.stream().map(GameLobbyModel::getPlayer1Name).collect(Collectors.toList());
        List<String> givenPlayer1 = givenLobbies.stream().map(GameLobby::getPlayer1Name).collect(Collectors.toList());
        assertThat(foundPlayer1).containsExactlyInAnyOrderElementsOf(givenPlayer1);

        List<String> foundPlayer2 = foundLobbies.stream().map(GameLobbyModel::getPlayer2Name).collect(Collectors.toList());
        List<String> givenPlayer2 = givenLobbies.stream().map(GameLobby::getPlayer2Name).collect(Collectors.toList());
        assertThat(foundPlayer2).containsExactlyInAnyOrderElementsOf(givenPlayer2);

    }

    @Test
    @WithMockUser(username = "John", roles = {"USER"}, authorities = {"ROLE_PLAYER", "ROLE_USER"})
    void searchingGameLobbyShouldReturnsOnlyAvailableLobby() throws Exception {
        List<GameLobby> givenLobbies = List.of(new GameLobby(1, "Jane", null),
                new GameLobby(2, "Johan", "Gerrard"));

        gameLobbyMysqlRepository.saveAll(givenLobbies);

        MvcResult response = mockMvc.perform(get(baseUrl + "/find/available")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        List<GameLobbyModel> foundLobbies = gameLobbyUtil.mapMvcResultToObjects(response, new TypeReference<>() {
        });

        // full lobby should not be found
        assertThat(foundLobbies).hasSize(1);
        assertThat(foundLobbies.stream().map(GameLobbyModel::getPlayer1Name)).doesNotContain("Johan");
        assertThat(foundLobbies.stream().map(GameLobbyModel::getPlayer2Name)).doesNotContain("Gerrard");
    }


    @Test
    @WithAnonymousUser
    void searchingGameLobbyShouldReturns401WhenUserNotAuthenticated() throws Exception {
        mockMvc.perform(post(baseUrl + "/find/available")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized());
    }

}
