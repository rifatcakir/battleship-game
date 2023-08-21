package com.battleship.engine.service;

import com.battleship.engine.model.response.GameStatusResponse;

import java.util.UUID;

/**
 * The GameStatusService interface defines methods to retrieve the current game status for a player in a game.
 * Implementing classes provide logic to fetch and present relevant information about the game's status.
 */
public interface GameStatusService {

    /**
     * Get the current game status for a specific player in the specified game lobby.
     *
     * @param playerName  The name of the player requesting the game status.
     * @param gameLobbyId The unique identifier of the game lobby.
     * @return A GameStatusResponse containing information about the current status of the game.
     */
    GameStatusResponse getGameStatus(String playerName, UUID gameLobbyId);
}