package com.battleship.engine.rule.parameters;

import com.battleship.engine.model.BattleshipGameBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class PlayerTurnCheck implements Parameter {
    private BattleshipGameBoard battleshipGameBoard;
}
