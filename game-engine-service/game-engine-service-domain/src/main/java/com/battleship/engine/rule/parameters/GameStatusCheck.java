package com.battleship.engine.rule.parameters;

import com.battleship.engine.model.BattleshipGameBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GameStatusCheck extends Parameter {
    private BattleshipGameBoard battleshipGameBoard;
}
