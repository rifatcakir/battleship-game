package com.battleship.rest.model.response;

import com.battleship.engine.model.ActionStatus;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.enums.CurrentPlayerDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShipActionAPIResponse implements Serializable {
    @Schema(description = "UUID of the game")
    private UUID gameId;

    @Schema(description = "Player board details")
    private PlayerBoardDomain playerBoard;

    @Schema(description = "Details of the current player")
    private CurrentPlayerDomain currentPlayer;

    @Schema(description = "Status of the game")
    private GameStatusDomain status;

    @Schema(description = "Result of the action")
    private ActionStatus actionStatus;
}
