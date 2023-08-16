package com.battleship.engine.engine.parameters;

import com.battleship.engine.model.BattleshipGameBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GameStatusCheck extends Parameter {
    private BattleshipGameBoard battleshipGameBoard;
}
