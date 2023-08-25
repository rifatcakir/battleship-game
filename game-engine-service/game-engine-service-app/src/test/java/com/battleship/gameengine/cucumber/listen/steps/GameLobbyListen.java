package com.battleship.gameengine.cucumber.listen.steps;

import com.battleship.gameengine.cucumber.TestDataContextHolder;
import com.battleship.rest.model.response.GameStatusApiResponse;
import io.cucumber.java.en.When;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GameLobbyListen extends TestDataContextHolder {


    @When("Player see turn for {word}")
    public void playerSeeTurnForPlayer(String playerType) throws Exception {
        listenGameLobby();
        GameStatusApiResponse response = mockMvcResultUtil.mapMvcResultToObject(latestResult, GameStatusApiResponse.class);
        assertThat(response.getCurrentPlayer().name()).isEqualTo(playerType);
    }
}
