package com.battleship.engine.service.impl;

import com.battleship.engine.engine.parameters.GameStatusCheckParameter;
import com.battleship.engine.engine.parameters.Parameter;
import com.battleship.engine.engine.parameters.ShipPlacementParameter;
import com.battleship.engine.engine.rules.RuleService;
import com.battleship.engine.model.ActionStatus;
import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.enums.ActionResult;
import com.battleship.engine.model.enums.PlayerBoardStatus;
import com.battleship.engine.model.request.ShipActionResponse;
import com.battleship.engine.model.request.ShipPlacementRequest;
import com.battleship.engine.repository.GameBoardRepository;
import com.battleship.engine.service.ShipPlacementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ShipPlacementServiceImpl implements ShipPlacementService {

    private final RuleService ruleService;
    private final GameBoardRepository gameBoardRepository;

    @Override
    public ShipActionResponse placeShip(String playerName, ShipPlacementRequest shipPlacementRequest) {
        BattleshipGameBoard battleshipGameBoard = gameBoardRepository.findById(shipPlacementRequest.getGameLobbyId());
        PlayerBoardDomain currentPlayerBoard = battleshipGameBoard.getPlayerBoardByPlayerName(playerName);

        if (currentPlayerBoard.getPlayerBoardStatus() != PlayerBoardStatus.SHIP_PLACEMENT) {
            throw new IllegalArgumentException(); //TODO FIX
        }

        ruleService.applyRule(
                createShipPlacementParam(
                        currentPlayerBoard,
                        shipPlacementRequest
                )
        );
        ruleService.applyRule(new GameStatusCheckParameter(battleshipGameBoard));

        gameBoardRepository.save(battleshipGameBoard);

        return new ShipActionResponse(battleshipGameBoard.getGameId(),
                currentPlayerBoard,
                battleshipGameBoard.getCurrentPlayer(),
                battleshipGameBoard.getStatus(),
                new ActionStatus(shipPlacementRequest.getShipType().name(), ActionResult.PLACED));
    }

    private Parameter createShipPlacementParam(PlayerBoardDomain playerBoard, ShipPlacementRequest shipPlacementRequest) {
        return new ShipPlacementParameter(playerBoard, shipPlacementRequest.getShipType(), shipPlacementRequest.getCellPositions());
    }
}