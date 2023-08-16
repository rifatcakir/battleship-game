package com.battleship.engine.service;

import com.battleship.engine.engine.parameters.GameStatusCheckParameter;
import com.battleship.engine.engine.parameters.Parameter;
import com.battleship.engine.engine.parameters.ShipPlacementParameter;
import com.battleship.engine.engine.rules.RuleService;
import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.enums.PlayerBoardStatus;
import com.battleship.engine.model.request.ShipPlacementRequest;
import com.battleship.engine.repository.GameBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipPlacementServiceImpl implements ShipPlacementService {

    private final RuleService ruleService;
    private final GameBoardRepository gameBoardRepository;

    @Override
    public String placeShip(String playerName, ShipPlacementRequest shipPlacementRequest) {
        BattleshipGameBoard battleshipGameBoard = gameBoardRepository.findById(shipPlacementRequest.getGameLobbyId());
        PlayerBoardDomain currentPlayerBoard = battleshipGameBoard.getPlayerBoardByPlayerName(playerName);

        if (currentPlayerBoard.getPlayerBoardStatus() != PlayerBoardStatus.SHIP_PLACEMENT) {
            throw new IllegalArgumentException("Ship placement was closed!");
        }

        ruleService.applyRule(
                createShipPlacementParam(
                        battleshipGameBoard.getPlayerBoardByPlayerName(playerName),
                        shipPlacementRequest
                )
        );

        ruleService.applyRule(new GameStatusCheckParameter(battleshipGameBoard));

        return "DONE";
    }

    private Parameter createShipPlacementParam(PlayerBoardDomain playerBoard, ShipPlacementRequest shipPlacementRequest) {
        return new ShipPlacementParameter(playerBoard, shipPlacementRequest.getShipType(), shipPlacementRequest.getCellPositions());
    }
}
