package com.battleship.engine.rule.parameters;

import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.enums.CurrentPlayerDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import com.battleship.engine.rule.model.CellPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AttackCell implements Parameter {
    private CurrentPlayerDomain currentPlayer;
    private GameStatusDomain gameStatus;
    private PlayerBoardDomain playerBoardDomain;
    private PlayerBoardDomain enemyBoardDomain;
    private CellPosition position;
}
