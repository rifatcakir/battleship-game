package com.battleship.engine.rule.parameters;

import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.ShipType;
import com.battleship.engine.rule.model.CellPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PlaceShip extends Parameter {
    private PlayerBoardDomain playerBoardDomain;
    private ShipType shipType;
    private List<CellPosition> positions;
}
