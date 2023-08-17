package com.battleship.lobby.exception;

public class GameLobbyNotAvailable extends RuntimeException {

    public GameLobbyNotAvailable() {
        super("Game lobby not available!");
    }
}
