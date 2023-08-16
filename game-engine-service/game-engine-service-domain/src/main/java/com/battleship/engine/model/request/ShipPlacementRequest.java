package com.battleship.engine.model.request;

import com.battleship.engine.engine.model.CellPosition;
import com.battleship.engine.model.ShipType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ShipPlacementRequest {
    @Schema(description = "UUID of the game lobby")
    private UUID gameLobbyId;

    @Schema(description = "Type of the ship")
    private ShipType shipType;

    @Schema(description = "List of cell positions for ship placement")
    private List<CellPosition> cellPositions;
}