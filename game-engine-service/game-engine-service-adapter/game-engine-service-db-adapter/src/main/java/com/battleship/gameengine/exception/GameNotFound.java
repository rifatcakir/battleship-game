package com.battleship.gameengine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GameNotFound extends RuntimeException {
    public GameNotFound() {
        super("Game not found!");
    }
}
