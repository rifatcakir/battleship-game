package com.battleship.engine.service;

import com.battleship.engine.model.request.ShipPlacementRequest;
import com.battleship.engine.model.response.ShipActionResponse;

public interface ShipPlacementService {

    ShipActionResponse placeShip(String playerName, ShipPlacementRequest shipPlacementRequest);
}
