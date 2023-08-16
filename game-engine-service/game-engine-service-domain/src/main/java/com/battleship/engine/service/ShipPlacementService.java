package com.battleship.engine.service;

import com.battleship.engine.model.request.ShipActionResponse;
import com.battleship.engine.model.request.ShipPlacementRequest;

public interface ShipPlacementService {

    ShipActionResponse placeShip(String playerName, ShipPlacementRequest shipPlacementRequest);
}
