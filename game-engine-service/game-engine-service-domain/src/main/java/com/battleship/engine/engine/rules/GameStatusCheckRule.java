package com.battleship.engine.engine.rules;

import com.battleship.engine.engine.parameters.GameStatusCheckParameter;
import com.battleship.engine.engine.parameters.Parameter;
import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.BoardCell;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.enums.CellStateDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import com.battleship.engine.model.enums.PlayerBoardStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GameStatusCheckRule implements Rule {
    @Override
    public void applyRule(Parameter param) {
        if (!(param instanceof GameStatusCheckParameter)) return;
        var checkParameter = (GameStatusCheckParameter) param;

        BattleshipGameBoard battleshipGameBoard = checkParameter.getBattleshipGameBoard();
        var playerBoards = battleshipGameBoard.getPlayerBoards();


        boolean gameIsOver = checkGameOver(playerBoards.stream().filter(it -> it.getCurrentPlayerDomain() != battleshipGameBoard.getCurrentPlayer()).findFirst());

        if (gameIsOver) {
            battleshipGameBoard.setStatus(GameStatusDomain.byPlayerType(battleshipGameBoard.getCurrentPlayer()));
        }
    }

    private boolean checkGameOver(Optional<PlayerBoardDomain> enemyPlayerBoard) {
        return enemyPlayerBoard.isPresent() && allShipsAreHit(enemyPlayerBoard.get());
    }

    private boolean allShipsAreHit(PlayerBoardDomain enemyPlayerBoard) {
        if (enemyPlayerBoard.getPlayerBoardStatus() != PlayerBoardStatus.ON_GOING) return false;
        Set<Boolean> allShipsStatus = Arrays.stream(enemyPlayerBoard.getBoardCells())
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .map(it -> it.getState() == CellStateDomain.SUNK)
                .collect(Collectors.toSet());

        return allShipsStatus.size() == 1 && allShipsStatus.iterator().next();
    }


    private boolean shipPlacementCompleted(BoardCell[][] boardCells) {
        long uniqueShipCounts =
                Arrays.stream(boardCells)
                        .flatMap(Arrays::stream)
                        .filter(Objects::nonNull)
                        .map(it -> it.getShipInfo().getShipGroupId())
                        .distinct().count();

        return uniqueShipCounts == 1;
    }

}
