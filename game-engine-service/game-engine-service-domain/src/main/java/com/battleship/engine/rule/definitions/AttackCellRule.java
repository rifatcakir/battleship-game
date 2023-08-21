package com.battleship.engine.rule.definitions;

import com.battleship.engine.exception.InvalidGameAction;
import com.battleship.engine.model.BoardCell;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.ShipInfo;
import com.battleship.engine.model.enums.CellStateDomain;
import com.battleship.engine.model.enums.CurrentPlayerDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import com.battleship.engine.rule.parameters.AttackCell;
import com.battleship.engine.rule.parameters.Parameter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class AttackCellRule implements Rule {

    @Override
    public void applyRule(Parameter param) {
        if (!(param instanceof AttackCell)) return;
        AttackCell attackCellParameter = (AttackCell) param;
        int targetXCoordinate = attackCellParameter.getPosition().getX();
        int targetYCoordinate = attackCellParameter.getPosition().getY();

        validateAttack(attackCellParameter);

        PlayerBoardDomain enemyBoardDomain = attackCellParameter.getEnemyBoardDomain();

        BoardCell targetCell = getOrCreateBoardCell(enemyBoardDomain, targetXCoordinate, targetYCoordinate);
        updateTargetCellState(targetCell);

        updateAttackerBoard(attackCellParameter.getPlayerBoardDomain(), targetCell.getOwnerState(), targetXCoordinate, targetYCoordinate);

        verifyAnySunk(enemyBoardDomain, targetCell.getShipInfo(), targetCell.getOwnerState());
    }

    private void validateAttack(AttackCell attackCellParameter) {
        GameStatusDomain gameStatus = attackCellParameter.getGameStatus();
        CurrentPlayerDomain playerHasRightToPlay = attackCellParameter.getCurrentPlayer();
        CurrentPlayerDomain currentPlayer = attackCellParameter.getPlayerBoardDomain().getCurrentPlayerDomain();

        if (!gameStatus.equals(GameStatusDomain.ONGOING) || !playerHasRightToPlay.equals(currentPlayer)) {
            throw new InvalidGameAction("You are not allowed to attack!");
        }
    }

    private void updateTargetCellState(BoardCell targetCell) {
        if (targetCell.getOwnerState() == CellStateDomain.SHIP)
            targetCell.setOwnerState(CellStateDomain.HIT);
        else if (targetCell.getOwnerState() == CellStateDomain.EMPTY)
            targetCell.setOwnerState(CellStateDomain.MISS);
    }

    private void updateAttackerBoard(PlayerBoardDomain playerBoardDomain, CellStateDomain ownerState, int xCellPos, int yCellPos) {
        BoardCell attackersCell = playerBoardDomain.getBoardCells()[xCellPos][yCellPos];
        if (attackersCell == null)
            playerBoardDomain.getBoardCells()[xCellPos][yCellPos] = new BoardCell(null, null, ownerState);
        else if (attackersCell.getEnemyState() == null)
            playerBoardDomain.getBoardCells()[xCellPos][yCellPos] = new BoardCell(attackersCell.getShipInfo(), attackersCell.getOwnerState(), ownerState);
        else
            attackersCell.setEnemyState(ownerState);
    }

    private void verifyAnySunk(PlayerBoardDomain enemyBoardDomain, ShipInfo shipInfo, CellStateDomain ownerState) {
        if (ownerState == CellStateDomain.HIT && shipInfo != null) {
            boolean allShipsHit = Arrays.stream(enemyBoardDomain.getBoardCells())
                    .flatMap(Arrays::stream)
                    .filter(Objects::nonNull)
                    .filter(it -> it.getShipInfo() != null && it.getShipInfo().getShipGroupId().equals(shipInfo.getShipGroupId()))
                    .allMatch(cell -> cell.getOwnerState() == CellStateDomain.HIT);

            if (allShipsHit) {
                Arrays.stream(enemyBoardDomain.getBoardCells())
                        .flatMap(Arrays::stream)
                        .filter(Objects::nonNull)
                        .filter(it -> it.getShipInfo() != null && it.getShipInfo().getShipGroupId().equals(shipInfo.getShipGroupId()))
                        .forEach(cell -> cell.setOwnerState(CellStateDomain.SUNK));
            }
        }
    }

    private BoardCell getOrCreateBoardCell(PlayerBoardDomain playerBoardDomain, int targetXCoordinate, int targetYCoordinate) {
        BoardCell targetCell = playerBoardDomain.getBoardCell(targetXCoordinate, targetYCoordinate);

        if (targetCell == null || targetCell.getOwnerState() == null) {
            BoardCell newCell = new BoardCell(
                    targetCell != null ? targetCell.getShipInfo() : null,
                    CellStateDomain.EMPTY,
                    targetCell != null ? targetCell.getEnemyState() : null
            );
            playerBoardDomain.getBoardCells()[targetXCoordinate][targetYCoordinate] = newCell;
            return newCell;
        }

        return targetCell;
    }
}