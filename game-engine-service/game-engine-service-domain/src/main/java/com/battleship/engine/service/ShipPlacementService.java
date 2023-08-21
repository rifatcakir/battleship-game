package com.battleship.engine.service;

import com.battleship.engine.model.request.ShipPlacementRequest;
import com.battleship.engine.model.response.ShipActionResponse;

/**
 * The ShipPlacementService interface defines methods to handle ship placement actions in a game.
 * Implementing classes provide logic for placing ships on the game board and returning appropriate responses.
 */
public interface ShipPlacementService {

    /**
     * Place a ship on the game board according to the provided ship placement request.
     *
     * @param playerName           The name of the player performing the ship placement.
     * @param shipPlacementRequest The request containing information about the ship placement.
     * @return A ShipActionResponse indicating the result of the ship placement action.
     */
    ShipActionResponse placeShip(String playerName, ShipPlacementRequest shipPlacementRequest);
}