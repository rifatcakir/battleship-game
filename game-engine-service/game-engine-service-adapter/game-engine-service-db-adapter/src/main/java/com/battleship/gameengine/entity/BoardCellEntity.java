package com.battleship.gameengine.entity;

import com.battleship.gameengine.entity.enums.CellStateEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BoardCellEntity {
    private ShipInfoEntity shipInfo;
    private CellStateEntity state;
}
