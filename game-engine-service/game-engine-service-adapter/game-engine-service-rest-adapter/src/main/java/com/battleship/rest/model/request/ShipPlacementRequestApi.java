package com.battleship.rest.model.request;

import com.battleship.rest.model.CellPositionHumanLanguageApi;
import com.battleship.rest.model.ShipTypeApi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShipPlacementRequestApi implements Serializable {
    @Schema(description = "UUID of the game lobby")
    private UUID gameLobbyId;

    @Schema(description = "Type of the ship")
    private ShipTypeApi shipType;

    @Schema(description = "List of cell positions for ship placement")
    private List<CellPositionHumanLanguageApi> cellPositions;
}
