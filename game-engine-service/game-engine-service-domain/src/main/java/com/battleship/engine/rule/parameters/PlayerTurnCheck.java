package com.battleship.engine.rule.parameters;

import com.battleship.engine.model.BattleshipGameBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class PlayerTurnCheck extends Parameter {
    private BattleshipGameBoard battleshipGameBoard;
}
