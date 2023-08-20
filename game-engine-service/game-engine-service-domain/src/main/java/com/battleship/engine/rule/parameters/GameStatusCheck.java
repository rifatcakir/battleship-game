package com.battleship.engine.rule.parameters;

import com.battleship.engine.model.BattleshipGameBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GameStatusCheck implements Parameter {
    private BattleshipGameBoard battleshipGameBoard;
}
