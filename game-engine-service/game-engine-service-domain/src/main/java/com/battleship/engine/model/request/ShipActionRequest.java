package com.battleship.engine.model.request;

import com.battleship.engine.rule.model.CellPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ShipActionRequest {
    private UUID gameLobbyId;
    private CellPosition cellPosition;
}
