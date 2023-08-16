package com.battleship.engine.engine.parameters;

import com.battleship.engine.engine.model.CellPosition;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.enums.CurrentPlayerDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AttackCellParameter extends Parameter {
    private CurrentPlayerDomain currentPlayer;
    private GameStatusDomain gameStatus;
    private PlayerBoardDomain playerBoardDomain;
    private PlayerBoardDomain enemyBoardDomain;
    private CellPosition position;
}
