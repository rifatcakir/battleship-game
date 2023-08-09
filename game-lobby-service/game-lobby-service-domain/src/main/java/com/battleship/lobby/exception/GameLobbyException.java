package com.battleship.lobby.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GameLobbyException extends RuntimeException {

    public GameLobbyException(String msg) {
        super(msg);
    }
}
