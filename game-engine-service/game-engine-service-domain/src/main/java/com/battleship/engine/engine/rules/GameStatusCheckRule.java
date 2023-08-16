package com.battleship.engine.engine.rules;

import com.battleship.engine.engine.parameters.GameStatusCheckParameter;
import com.battleship.engine.engine.parameters.Parameter;
import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.BoardCell;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import com.battleship.engine.model.enums.PlayerBoardStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class GameStatusCheckRule implements Rule {
    @Override
    public void applyRule(Parameter param) {
        if (!(param instanceof GameStatusCheckParameter)) return;
        var checkParameter = (GameStatusCheckParameter) param;

        BattleshipGameBoard battleshipGameBoard = checkParameter.getBattleshipGameBoard();

        if (validatePlayerShipPlacementsClosed(battleshipGameBoard.getPlayerBoards())) {
            battleshipGameBoard.setStatus(GameStatusDomain.ONGOING);
        }
    }

    private boolean validatePlayerShipPlacementsClosed(List<PlayerBoardDomain> playerBoardDomains) {
        var shipPlacementOn = playerBoardDomains.stream().noneMatch(playerBoardDomain ->
                playerBoardDomain.getPlayerBoardStatus() == PlayerBoardStatus.SHIP_PLACEMENT);

        if (!shipPlacementOn) return false;

        playerBoardDomains.stream().filter(board -> board.getPlayerBoardStatus() == PlayerBoardStatus.SHIP_PLACEMENT)
                .forEach(board -> {
                    if (shipPlacementCompleted(board.getBoardCells())) {
                        board.setPlayerBoardStatus(PlayerBoardStatus.ON_GOING);
                    }
                });

        return playerBoardDomains.stream().noneMatch(playerBoardDomain ->
                playerBoardDomain.getPlayerBoardStatus() == PlayerBoardStatus.SHIP_PLACEMENT);

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
