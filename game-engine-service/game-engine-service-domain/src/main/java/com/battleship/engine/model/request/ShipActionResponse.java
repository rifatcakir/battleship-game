package com.battleship.engine.model.request;

import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.enums.CurrentPlayerDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
public class ShipActionResponse {
    private UUID gameId;
    private PlayerBoardDomain playerBoard;
    private CurrentPlayerDomain currentPlayer;
    private GameStatusDomain status;
}
