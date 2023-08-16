package com.battleship.engine.service;

import com.battleship.engine.model.request.ShipPlacementRequest;

public interface ShipPlacementService {

    String placeShip(String playerName, ShipPlacementRequest shipPlacementRequest);
}
