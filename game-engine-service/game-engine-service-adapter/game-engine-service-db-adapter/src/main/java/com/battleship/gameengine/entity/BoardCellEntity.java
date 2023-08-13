package com.battleship.gameengine.entity;

import com.battleship.gameengine.entity.enums.CellState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BoardCellEntity {
    private CellState state;
}
