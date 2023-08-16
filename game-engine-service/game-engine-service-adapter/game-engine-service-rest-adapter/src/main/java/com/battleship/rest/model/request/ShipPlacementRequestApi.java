package com.battleship.rest.model.request;

import com.battleship.rest.model.CellPositionApi;
import com.battleship.rest.model.ShipTypeApi;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ShipPlacementRequestApi {
    private UUID gameLobbyId;
    private ShipTypeApi shipType;
    private List<CellPositionApi> cellPositions;
}
