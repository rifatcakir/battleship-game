package com.battleship.engine.service.impl;

import com.battleship.engine.engine.model.CellPosition;
import com.battleship.engine.engine.parameters.AttackCell;
import com.battleship.engine.engine.parameters.GameStatusCheck;
import com.battleship.engine.engine.parameters.PlayerTurnCheck;
import com.battleship.engine.engine.rules.RuleService;
import com.battleship.engine.model.ActionStatus;
import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.BoardCell;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.enums.ActionResult;
import com.battleship.engine.model.request.ShipActionRequest;
import com.battleship.engine.model.request.ShipActionResponse;
import com.battleship.engine.repository.GameBoardRepository;
import com.battleship.engine.service.AttackActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        ruleService.applyRule(
                new AttackCell(
                        battleshipGameBoard.getCurrentPlayer(),
                        battleshipGameBoard.getStatus(),
                        playerBoard,
                        enemyPlayerBoard,
                        shipAttackDomainModel.getCellPosition()
                )
        );

        ruleService.applyRule(new GameStatusCheck(battleshipGameBoard));
        ruleService.applyRule(new PlayerTurnCheck(battleshipGameBoard));

        gameBoardRepository.save(battleshipGameBoard);

        BoardCell actionResultCell = getActionResult(enemyPlayerBoard, shipAttackDomainModel.getCellPosition());
        return new ShipActionResponse(battleshipGameBoard.getGameId(),
                playerBoard,
                battleshipGameBoard.getCurrentPlayer(),
                battleshipGameBoard.getStatus(),
                new ActionStatus(getShipInfo(actionResultCell), ActionResult.valueOf(actionResultCell.getOwnerState().name())));
    }

    private String getShipInfo(BoardCell actionResultCell) {
        var shipInfo = actionResultCell.getShipInfo();
        return (shipInfo != null) ? shipInfo.getShipType().name() : null;
    }

    private BoardCell getActionResult(PlayerBoardDomain enemyPlayerBoard, CellPosition cellPosition) {
        return enemyPlayerBoard.getBoardCell(cellPosition.getX(), cellPosition.getY());
    }
}
