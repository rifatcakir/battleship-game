package com.battleship.engine.engine.rules;

import com.battleship.engine.engine.parameters.AttackCell;
import com.battleship.engine.engine.parameters.Parameter;
import com.battleship.engine.exception.InvalidGameAction;
import com.battleship.engine.model.BoardCell;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.ShipInfo;
import com.battleship.engine.model.enums.CellStateDomain;
import com.battleship.engine.model.enums.CurrentPlayerDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class AttackCellRule implements Rule {
    private static BoardCell getBoardCell(PlayerBoardDomain playerBoardDomain, int targetXCoordinate, int targetYCoordinate) {
        BoardCell targetCell = playerBoardDomain.getBoardCell(targetXCoordinate, targetYCoordinate);
        if (targetCell == null) {
            playerBoardDomain.getBoardCells()[targetXCoordinate][targetYCoordinate] = new BoardCell(null, CellStateDomain.EMPTY, null);
            targetCell = playerBoardDomain.getBoardCells()[targetXCoordinate][targetYCoordinate];
        } else if (targetCell.getOwnerState() == null) {
            playerBoardDomain.getBoardCells()[targetXCoordinate][targetYCoordinate] = new BoardCell(targetCell.getShipInfo(), CellStateDomain.EMPTY, targetCell.getEnemyState());
            targetCell = playerBoardDomain.getBoardCells()[targetXCoordinate][targetYCoordinate];
        }
        return targetCell;
    }

    @Override
    public void applyRule(Parameter param) {
        if (!(param instanceof AttackCell)) return;
        var attackCellParameter = (AttackCell) param;
        int targetXCoordinate = attackCellParameter.getPosition().getX();
        int targetYCoordinate = attackCellParameter.getPosition().getY();


        cellAttackPreProcess(attackCellParameter.getGameStatus(),
                attackCellParameter.getCurrentPlayer(),
                attackCellParameter.getPlayerBoardDomain().getCurrentPlayerDomain());

        cellAttackProcess(attackCellParameter.getEnemyBoardDomain(), targetXCoordinate, targetYCoordinate);

        BoardCell targetCell = attackCellParameter.getEnemyBoardDomain().getBoardCell(targetXCoordinate, targetYCoordinate);
        fillOwnerBoardWithResult(
                attackCellParameter.getPlayerBoardDomain(),
                targetCell.getOwnerState(),
                targetXCoordinate, targetYCoordinate
        );
        verifyAnySunk(attackCellParameter.getEnemyBoardDomain(), targetCell.getShipInfo(), targetCell.getOwnerState());
    }

    private void verifyAnySunk(PlayerBoardDomain enemyBoardDomain, ShipInfo shipInfo, CellStateDomain ownerState) {
        if (ownerState != CellStateDomain.HIT) return;

        if (shipInfo != null) {
            BoardCell[][] boardCells = enemyBoardDomain.getBoardCells();
            boolean allHit = Arrays.stream(boardCells).flatMap(Arrays::stream)
                    .filter(Objects::nonNull)
                    .filter(it -> it.getShipInfo() != null)
                    .filter(it -> it.getShipInfo().getShipGroupId().equals(shipInfo.getShipGroupId()))
                    .map(BoardCell::getOwnerState)
                    .distinct().count() == 1;

            if (allHit) {
                Arrays.stream(boardCells)
                        .flatMap(Arrays::stream)
                        .filter(Objects::nonNull)
                        .filter(it -> it.getShipInfo() != null)
                        .filter(it -> it.getShipInfo().getShipGroupId().equals(shipInfo.getShipGroupId()))
                        .forEach(it -> it.setOwnerState(CellStateDomain.SUNK));
            }
        }
    }

    private void fillOwnerBoardWithResult(PlayerBoardDomain playerBoardDomain, CellStateDomain ownerState, int xCellPos, int yCellPos) {
        BoardCell attackersCell = playerBoardDomain.getBoardCells()[xCellPos][yCellPos];
        if (attackersCell == null)
            playerBoardDomain.getBoardCells()[xCellPos][yCellPos] = new BoardCell(null, null, ownerState);
        else if (attackersCell.getEnemyState() == null)
            playerBoardDomain.getBoardCells()[xCellPos][yCellPos] = new BoardCell(attackersCell.getShipInfo(), attackersCell.getOwnerState(), ownerState);
        else
            attackersCell.setEnemyState(ownerState);
    }

    private void cellAttackProcess(PlayerBoardDomain playerBoardDomain, int targetXCoordinate, int targetYCoordinate) {

        BoardCell targetCell = getBoardCell(playerBoardDomain, targetXCoordinate, targetYCoordinate);

        if (targetCell.getOwnerState() == CellStateDomain.SHIP)
            targetCell.setOwnerState(CellStateDomain.HIT);
        else if (targetCell.getOwnerState() == CellStateDomain.EMPTY)
            targetCell.setOwnerState(CellStateDomain.MISS);

    }

    private void cellAttackPreProcess(GameStatusDomain gameStatus, CurrentPlayerDomain playerHasRightToPlay, CurrentPlayerDomain currentPlayer) {
        if (!gameStatus.equals(GameStatusDomain.ONGOING) || !playerHasRightToPlay.equals(currentPlayer)) {
            throw new InvalidGameAction("You are not allowed to attack!");
        }
    }
}
