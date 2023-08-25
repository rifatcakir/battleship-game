package com.battleship.engine.rule.definitions;

import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.enums.CellStateDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import com.battleship.engine.rule.parameters.GameStatusCheck;
import com.battleship.engine.rule.parameters.Parameter;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.battleship.engine.model.enums.PlayerBoardStatus.ONGOING;

@Component
public class GameStatusCheckRule implements Rule {

    @Override
    public void applyRule(Parameter param) {
        if (!(param instanceof GameStatusCheck)) return;
        GameStatusCheck checkParameter = (GameStatusCheck) param;
        BattleshipGameBoard battleshipGameBoard = checkParameter.getBattleshipGameBoard();

        updateGameStatus(battleshipGameBoard);
    }

    private void updateGameStatus(BattleshipGameBoard battleshipGameBoard) {
        if (shouldUpdateOngoingStatus(battleshipGameBoard)) {
            battleshipGameBoard.setStatus(GameStatusDomain.ONGOING);
        }

        if (isGameOver(battleshipGameBoard)) {
            battleshipGameBoard.setStatus(GameStatusDomain.byPlayerType(battleshipGameBoard.getCurrentPlayer()));
            battleshipGameBoard.setEndDate(OffsetDateTime.now());
        }
    }

    private boolean shouldUpdateOngoingStatus(BattleshipGameBoard battleshipGameBoard) {
        return isShipPlacementCompleted(battleshipGameBoard.getPlayerBoards()) &&
                battleshipGameBoard.getEndDate() == null;
    }

    private boolean isShipPlacementCompleted(List<PlayerBoardDomain> playerBoards) {
        return playerBoards.stream()
                .map(PlayerBoardDomain::getPlayerBoardStatus)
                .allMatch(status -> status == ONGOING);
    }

    private boolean isGameOver(BattleshipGameBoard battleshipGameBoard) {
        PlayerBoardDomain enemyPlayerBoard = battleshipGameBoard.getEnemyBoardByPlayerCurrent(
                battleshipGameBoard.getCurrentPlayer());

        return enemyPlayerBoard != null && allShipsAreHit(enemyPlayerBoard);
    }

    private boolean allShipsAreHit(PlayerBoardDomain enemyPlayerBoard) {
        return enemyPlayerBoard.getPlayerBoardStatus() == ONGOING &&
                Arrays.stream(enemyPlayerBoard.getBoardCells())
                        .flatMap(Arrays::stream)
                        .filter(Objects::nonNull)
                        .allMatch(cell -> cell.getOwnerState() == CellStateDomain.SUNK);
    }
}
