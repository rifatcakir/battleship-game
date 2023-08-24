package com.battleship.engine.model;

import com.battleship.engine.model.enums.CellStateDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class BoardCell implements Serializable {
    private ShipInfo shipInfo;
    private CellStateDomain ownerState;
    private CellStateDomain enemyState;
}
