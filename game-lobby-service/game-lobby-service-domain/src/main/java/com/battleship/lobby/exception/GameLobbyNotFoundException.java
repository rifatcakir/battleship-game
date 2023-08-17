package com.battleship.lobby.exception;

public class GameLobbyNotFoundException extends RuntimeException {

    public GameLobbyNotFoundException() {
        super("Game lobby not found!");
    }
}