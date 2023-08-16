package com.battleship.engine.engine.parameters;

import com.battleship.engine.engine.model.CellPosition;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.ShipType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ShipPlacementParameter extends Parameter {
    private PlayerBoardDomain playerBoardDomain;
    private ShipType shipType;
    private List<CellPosition> positions;
}
