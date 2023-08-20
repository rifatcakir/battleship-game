package com.battleship.engine.rule.definitions;

import com.battleship.engine.model.enums.GameStatusDomain;
import com.battleship.engine.rule.parameters.Parameter;
import com.battleship.engine.rule.parameters.PlayerTurnCheck;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerTurnRule implements Rule {
    @Override
    public void applyRule(Parameter parameter) {
        if (!(parameter instanceof PlayerTurnCheck)) return;

        var battleshipGameBoard = ((PlayerTurnCheck) parameter).getBattleshipGameBoard();
        if (!playableGameStatus().contains(battleshipGameBoard.getStatus())) return;

        battleshipGameBoard.setCurrentPlayer(battleshipGameBoard.getCurrentPlayer().nextPlayer());
    }

    private List<GameStatusDomain> playableGameStatus() {
        return List.of(GameStatusDomain.SHIP_PLACEMENT, GameStatusDomain.ONGOING);
    }
}
