package com.battleship.gameengine.cucumber.placement.steps;

import com.battleship.engine.model.ShipType;
import com.battleship.gameengine.cucumber.TestDataContextHolder;
import com.battleship.rest.model.CellPositionHumanLanguageApi;
import com.battleship.rest.model.ShipTypeApi;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Arrays;
import java.util.stream.Collectors;


public class ShipPlacementStepDef extends TestDataContextHolder {

    @When("Player place a {word} to {string}")
    public void johnPlaceADESTROYERToX(String shipType, String positions) throws Exception {
        placeShip(ShipTypeApi.valueOf(shipType), positions);
    }

    @Then("{word} has a {word} on {string}")
    public void johnHasADESTROYEROnHisBoard(String playerName, String shipType, String positions) {
        givenPlayerHasGivenShipOnHisBoard(playerName, ShipType.valueOf(shipType),
                Arrays.stream(positions.replaceAll(" ", "").split(",")).map(CellPositionHumanLanguageApi::new).collect(Collectors.toList()));
    }

    @When("Player place all ships")
    public void johnPlaceAllShips() throws Exception {
        playerPlacedAllShips();
    }

}