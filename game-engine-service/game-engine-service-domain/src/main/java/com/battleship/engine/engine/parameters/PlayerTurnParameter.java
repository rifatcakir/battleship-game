package com.battleship.engine.engine.parameters;

import com.battleship.engine.model.BattleshipGameBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class PlayerTurnParameter extends Parameter {
    private BattleshipGameBoard battleshipGameBoard;
}
