package com.battleship.engine.model.request;

import com.battleship.engine.engine.model.CellPosition;
import com.battleship.engine.model.ShipType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ShipPlacementRequest {
    private UUID gameLobbyId;
    private ShipType shipType;
    private List<CellPosition> cellPositions;
}