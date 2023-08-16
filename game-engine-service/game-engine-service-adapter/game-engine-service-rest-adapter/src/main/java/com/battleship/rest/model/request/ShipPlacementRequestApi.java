package com.battleship.rest.model.request;

import com.battleship.rest.model.CellPositionApi;
import com.battleship.rest.model.ShipTypeApi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ShipPlacementRequestApi {
    @Schema(description = "UUID of the game lobby")
    private UUID gameLobbyId;

    @Schema(description = "Type of the ship")
    private ShipTypeApi shipType;

    @Schema(description = "List of cell positions for ship placement")
    private List<CellPositionApi> cellPositions;
}