package com.battleship.gameengine.exception;

public class GameNotFound extends RuntimeException {
    public GameNotFound() {
        super("Game not found!");
    }
}
