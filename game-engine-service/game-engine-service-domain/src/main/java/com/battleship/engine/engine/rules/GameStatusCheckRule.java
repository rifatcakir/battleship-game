package com.battleship.engine.engine.rules;

import com.battleship.engine.engine.parameters.GameStatusCheck;
import com.battleship.engine.engine.parameters.Parameter;
import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.enums.CellStateDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.battleship.engine.model.enums.PlayerBoardStatus.ONGOING;

@Component
public class GameStatusCheckRule implements Rule {
    @Override
    public void applyRule(Parameter param) {
        if (!(param instanceof GameStatusCheck)) return;
        var checkParameter = (GameStatusCheck) param;

        BattleshipGameBoard battleshipGameBoard = checkParameter.getBattleshipGameBoard();
        var playerBoards = battleshipGameBoard.getPlayerBoards();

        if (isShipPlacementCompleted(battleshipGameBoard.getPlayerBoards())) {
            battleshipGameBoard.setStatus(GameStatusDomain.ONGOING);
        }

        boolean gameIsOver = checkGameOver(playerBoards.stream().filter(it -> it.getCurrentPlayerDomain() != battleshipGameBoard.getCurrentPlayer()).findFirst().get());

        if (gameIsOver) {
            battleshipGameBoard.setStatus(GameStatusDomain.byPlayerType(battleshipGameBoard.getCurrentPlayer()));
            battleshipGameBoard.setEndDate(OffsetDateTime.now());
        }
    }

    private boolean isShipPlacementCompleted(List<PlayerBoardDomain> playerBoards) {
        return playerBoards.stream().map(it -> it.getPlayerBoardStatus()).allMatch(it -> it == ONGOING);
    }

    private boolean checkGameOver(PlayerBoardDomain enemyPlayerBoard) {
        return enemyPlayerBoard != null && allShipsAreHit(enemyPlayerBoard);
    }

    private boolean allShipsAreHit(PlayerBoardDomain enemyPlayerBoard) {
        if (enemyPlayerBoard.getPlayerBoardStatus() != ONGOING) return false;
        Set<Boolean> allShipsStatus = Arrays.stream(enemyPlayerBoard.getBoardCells())
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .map(it -> it.getOwnerState() == CellStateDomain.SUNK)
                .collect(Collectors.toSet());

        return allShipsStatus.size() == 1 && allShipsStatus.iterator().next();
    }

}
