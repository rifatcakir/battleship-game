package com.battleship.engine.model;

import com.battleship.engine.model.enums.CurrentPlayerDomain;
import com.battleship.engine.model.enums.PlayerBoardStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class PlayerBoardDomain implements Serializable {
    private CurrentPlayerDomain currentPlayerDomain;
    private String playerName;
    private BoardCell[][] boardCells;
    private PlayerBoardStatus playerBoardStatus;


    public BoardCell getBoardCell(int x, int y) {
        return boardCells[x][y];
    }
}
