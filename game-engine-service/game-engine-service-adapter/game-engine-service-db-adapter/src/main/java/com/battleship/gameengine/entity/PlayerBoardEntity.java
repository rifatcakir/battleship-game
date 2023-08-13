package com.battleship.gameengine.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlayerBoardEntity {
    private String playerName;
    private BoardCellEntity[][] boardCellEntities;

}
