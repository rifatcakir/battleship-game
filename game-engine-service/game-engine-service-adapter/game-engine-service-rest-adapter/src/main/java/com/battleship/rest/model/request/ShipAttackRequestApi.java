package com.battleship.rest.model.request;

import com.battleship.rest.model.CellPositionApi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ShipAttackRequestApi {
    @Schema(description = "UUID of the game lobby")
    private UUID gameLobbyId;

    @Schema(description = "Cell position for the ship attack")
    private CellPositionApi cellPosition;
}
