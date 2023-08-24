package com.battleship.engine.service.impl;

import com.battleship.engine.model.ActionStatus;
import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.BoardCell;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.enums.ActionResult;
import com.battleship.engine.model.request.ShipActionRequest;
import com.battleship.engine.model.response.ShipActionResponse;
import com.battleship.engine.repository.GameBoardRepository;
import com.battleship.engine.rule.definitions.RuleService;
import com.battleship.engine.rule.model.CellPosition;
import com.battleship.engine.rule.parameters.AttackCell;
import com.battleship.engine.rule.parameters.GameStatusCheck;
import com.battleship.engine.rule.parameters.PlayerTurnCheck;
import com.battleship.engine.service.AttackActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttackActionServiceImpl implements AttackActionService {

    private final RuleService ruleService;
    private final GameBoardRepository gameBoardRepository;

    @Override
    public ShipActionResponse executeAction(String playerName, ShipActionRequest shipAttackDomainModel) {
        BattleshipGameBoard battleshipGameBoard = gameBoardRepository.findById(shipAttackDomainModel.getGameLobbyId());
        PlayerBoardDomain playerBoard = battleshipGameBoard.getPlayerBoardByPlayerName(playerName);
        PlayerBoardDomain enemyPlayerBoard = battleshipGameBoard.getEnemyPlayerBoardByPlayerName(playerName);

        applyAttackRules(battleshipGameBoard, playerBoard, enemyPlayerBoard, shipAttackDomainModel.getCellPosition());
        applyGameStatusAndPlayerTurnRules(battleshipGameBoard);

        gameBoardRepository.save(battleshipGameBoard);

        BoardCell actionResultCell = enemyPlayerBoard.getBoardCell(shipAttackDomainModel.getCellPosition().getX(), shipAttackDomainModel.getCellPosition().getY());
        return new ShipActionResponse(
                battleshipGameBoard.getGameId(),
                playerBoard,
                battleshipGameBoard.getCurrentPlayer(),
                battleshipGameBoard.getStatus(),
                new ActionStatus(getShipInfo(actionResultCell), ActionResult.valueOf(actionResultCell.getOwnerState().name()))
        );
    }

    private void applyAttackRules(BattleshipGameBoard gameBoard, PlayerBoardDomain playerBoard, PlayerBoardDomain enemyPlayerBoard, CellPosition cellPosition) {
        ruleService.applyRule(new AttackCell(gameBoard.getCurrentPlayer(), gameBoard.getStatus(), playerBoard, enemyPlayerBoard, cellPosition));
    }

    private void applyGameStatusAndPlayerTurnRules(BattleshipGameBoard gameBoard) {
        ruleService.applyRule(new GameStatusCheck(gameBoard));
        ruleService.applyRule(new PlayerTurnCheck(gameBoard));
    }

    private String getShipInfo(BoardCell actionResultCell) {
        return Optional.ofNullable(actionResultCell.getShipInfo())
                .map(shipInfo -> shipInfo.getShipType().name())
                .orElse(null);
    }
}
