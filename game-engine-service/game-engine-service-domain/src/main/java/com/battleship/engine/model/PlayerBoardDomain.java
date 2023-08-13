package com.battleship.engine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlayerBoardDomain {
    private String playerName;
    private BoardCell[][] boardCells;

}
