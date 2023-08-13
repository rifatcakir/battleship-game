package com.battleship.engine.model;

import com.battleship.engine.model.enums.CellStateDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BoardCell {
    private CellStateDomain state;
}
