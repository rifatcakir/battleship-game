package com.battleship.gameengine.cucumber.attack.steps;

import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.enums.CellStateDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import com.battleship.engine.model.enums.PlayerBoardStatus;
import com.battleship.engine.rule.model.CellPosition;
import com.battleship.gameengine.cucumber.TestDataContextHolder;
import com.battleship.rest.model.response.ShipActionAPIResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ShipAttackIntegrationTestStepDef extends TestDataContextHolder {



    @Then("Player received a {word} {word}")
    public void playerReceivedPositionAIsHIT(String shipType, String cellState) throws UnsupportedEncodingException, JsonProcessingException {
        ShipActionAPIResponse response = mockMvcResultUtil.mapMvcResultToObject(latestResult, ShipActionAPIResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getActionStatus().getShipType()).isEqualTo(shipType);
        assertThat(response.getActionStatus().getActionResult().name()).isEqualTo(cellState);
    }

    @Then("Player received only a {word}")
    public void playerReceivedMISS(String cellState) throws UnsupportedEncodingException, JsonProcessingException {
        ShipActionAPIResponse response = mockMvcResultUtil.mapMvcResultToObject(latestResult, ShipActionAPIResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getActionStatus().getActionResult().name()).isEqualTo(cellState);
    }

    @And("Player see action result on {word} as {word}")
    public void playerSeeActionResultOnHISBoard(String position, String status) throws UnsupportedEncodingException, JsonProcessingException {
        ShipActionAPIResponse response = mockMvcResultUtil.mapMvcResultToObject(latestResult, ShipActionAPIResponse.class);
        CellPosition cellPosition = cellHumanLangConverterUtil.convert(position);
        assertThat(response.getPlayerBoard().getBoardCell(cellPosition.getX(), cellPosition.getY()).getEnemyState().name()).isEqualTo(status);

    }

    @Given("John sunk all ships except {word} for {word}")
    public void johnSunkAllShipsExceptShipOnE(String shipType, String enemyPlayer) {
        BattleshipGameBoard gameBoard = gameBoardRepository.findById(gameLobbyId);
        PlayerBoardDomain enemyBoard = gameBoard.getPlayerBoardByPlayerName(enemyPlayer);

        Arrays.stream(enemyBoard.getBoardCells()).flatMap(Arrays::stream)
                .filter(Objects::nonNull).filter(it -> !Objects.equals(it.getShipInfo().getShipType().name(), shipType))
                .forEach(
                        it -> it.setOwnerState(CellStateDomain.SUNK)
                );

        gameBoard.setStatus(GameStatusDomain.ONGOING);
        gameBoard.getPlayerBoards().forEach(it -> it.setPlayerBoardStatus(PlayerBoardStatus.ONGOING));

        gameBoardRepository.save(gameBoard);
    }

    @And("{word} wins the game")
    public void johnWinsTheGame(String playerName) throws UnsupportedEncodingException, JsonProcessingException {
        ShipActionAPIResponse response = mockMvcResultUtil.mapMvcResultToObject(latestResult, ShipActionAPIResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatus().name()).contains(response.getCurrentPlayer().name());
        assertThat(gameBoardRepository.findById(gameLobbyId).getEndDate()).isNotNull();
        assertThat(response.getPlayerBoard().getCurrentPlayerDomain().name()).isEqualTo(playerName);
    }
}
