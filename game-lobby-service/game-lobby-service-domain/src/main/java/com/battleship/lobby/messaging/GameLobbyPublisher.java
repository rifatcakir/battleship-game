package com.battleship.lobby.messaging;


import com.battleship.lobby.model.GameLobbyModel;


public interface GameLobbyPublisher {
    void publishGameLobby(GameLobbyModel gameLobby);
}
