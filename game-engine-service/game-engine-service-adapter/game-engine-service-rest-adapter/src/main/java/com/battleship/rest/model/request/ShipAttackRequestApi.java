package com.battleship.rest.model.request;

import com.battleship.rest.model.CellPositionHumanLanguageApi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ShipAttackRequestApi implements Serializable {
    @Schema(description = "UUID of the game lobby")
    private UUID gameLobbyId;

    @Schema(description = "Cell position for the ship attack")
    private CellPositionHumanLanguageApi cellPosition;
}
