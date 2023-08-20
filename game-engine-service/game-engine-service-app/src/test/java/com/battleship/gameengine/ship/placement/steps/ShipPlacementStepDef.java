package com.battleship.gameengine.ship.placement.steps;

import com.battleship.engine.model.ShipType;
import com.battleship.engine.model.enums.GameStatusDomain;
import com.battleship.engine.model.enums.PlayerBoardStatus;
import com.battleship.gameengine.ship.placement.ShipPlacementIntegrationTest;
import com.battleship.rest.model.CellPositionHumanLanguageApi;
import com.battleship.rest.model.ShipTypeApi;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.stream.Collectors;


public class ShipPlacementStepDef extends ShipPlacementIntegrationTest {

    @Given("a valid game lobby for user {word} and {word}")
    public void aValidGameLobbyForUserJohnAndJane(String player1Name, String player2Name) {
        createGameLobby(player1Name, player2Name);
    }

    @When("Player place a {word} to {string}")
    public void johnPlaceADESTROYERToX(String shipType, String positions) throws Exception {
        placeShip(ShipTypeApi.valueOf(shipType),
                Arrays.stream(positions.replaceAll(" ", "").split(",")).map(CellPositionHumanLanguageApi::new).collect(Collectors.toList()));
    }


    @Given("User {word} login")
    public void userJohnLogin(String playerName) {
        setPlayerNameIntoContext(playerName);
    }

    @Then("{word} has a {word} on {string}")
    public void johnHasADESTROYEROnHisBoard(String playerName, String shipType, String positions) {
        givenPlayerHasGivenShipOnHisBoard(playerName, ShipType.valueOf(shipType),
                Arrays.stream(positions.replaceAll(" ", "").split(",")).map(CellPositionHumanLanguageApi::new).collect(Collectors.toList()));
    }

    @Then("Player received {string} with error code {int}")
    public void johnReceivedError(String message, int statusCode) throws UnsupportedEncodingException {
        playerReceivedError(message, statusCode);
    }


    @When("Player place all ships")
    public void johnPlaceAllShips() throws Exception {
        playerPlacedAllShips();
    }

    @Then("{word} board status is {word}")
    public void playerBoardStatusIsONGOING(String playerName, String boardStatus) {
        verifyPlayersBoardStatusIs(playerName, PlayerBoardStatus.valueOf(boardStatus));
    }

    @And("GameBoard status is {word}")
    public void gameBoardStatusIsONGOING(String status) {
        verifyGameBoardStatusIs(GameStatusDomain.valueOf(status));
    }

}