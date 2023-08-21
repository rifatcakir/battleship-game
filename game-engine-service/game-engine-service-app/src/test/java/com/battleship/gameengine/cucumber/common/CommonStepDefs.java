package com.battleship.gameengine.cucumber.common;

import com.battleship.engine.model.enums.GameStatusDomain;
import com.battleship.engine.model.enums.PlayerBoardStatus;
import com.battleship.gameengine.cucumber.TestDataContextHolder;
import com.battleship.rest.model.ShipTypeApi;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class CommonStepDefs extends TestDataContextHolder {
    @Given("A valid game lobby for user {word} and {word}")
    public void aValidGameLobbyForUserJohnAndJane(String player1Name, String player2Name) {
        createGameLobby(player1Name, player2Name);
    }

    @Given("Turn is for {word}")
    public void turnForUser(String userName) {
        setPlayerNameIntoContext(userName);
    }

    @Then("Player received {string} with error code {int}")
    public void johnReceivedError(String message, int statusCode) throws UnsupportedEncodingException {
        playerReceivedError(message, statusCode);
    }

    @Given("User {word} login")
    public void userJohnLogin(String userName) {
        setPlayerNameIntoContext(userName);
    }

    @Then("{word} board status is {word}")
    public void playerBoardStatusIsONGOING(String playerName, String boardStatus) {
        verifyPlayersBoardStatusIs(playerName, PlayerBoardStatus.valueOf(boardStatus));
    }

    @And("GameBoard status is {word}")
    public void gameBoardStatusIsONGOING(String status) {
        verifyGameBoardStatusIs(GameStatusDomain.valueOf(status));
    }

    @When("Player attack to {word}")
    public void playerAttack(String position) throws Exception {
        attackToAShip(gameLobbyId, position);
    }

    @And("given ships are placed")
    public void givenShipsArePlacedForJohn(DataTable table) throws Exception {
        List<List<String>> rows = table.asLists(String.class);
        List<List<String>> rowsWithoutHeader = rows.subList(1, rows.size());

        for (List<String> columns : rowsWithoutHeader) {
            placeShip(ShipTypeApi.valueOf(columns.get(0)), columns.get(1));
        }
    }
}
