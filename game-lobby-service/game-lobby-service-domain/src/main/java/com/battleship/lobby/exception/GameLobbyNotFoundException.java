package com.battleship.lobby.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GameLobbyNotFoundException extends RuntimeException {

    public GameLobbyNotFoundException() {
        super("Game lobby not found!");
    }
}