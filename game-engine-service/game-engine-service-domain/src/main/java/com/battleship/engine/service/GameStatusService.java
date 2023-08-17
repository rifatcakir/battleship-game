package com.battleship.engine.service;

import com.battleship.engine.model.response.GameStatusResponse;

import java.util.UUID;

public interface GameStatusService {
    GameStatusResponse getGameStatus(String playerName, UUID gameLobbyId);
}
