package com.battleship.engine.exception;

public class InvalidGameAction extends RuntimeException {
    public InvalidGameAction(String message) {
        super(message);
    }
}