package com.battleship.gameengine.entity;

import com.battleship.gameengine.entity.enums.CurrentPlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlayerBoardEntity {
    private String playerName;
    private CurrentPlayerEntity player;
    private BoardCellEntity[][] boardCell;
    private PlayerBoardStatusEntity playerBoardStatus;

}
