package com.battleship.engine.exception;

/**
 * The InvalidGameAction class represents an exception that is thrown when an invalid game action is attempted.
 * It extends the RuntimeException class to indicate runtime exceptions related to game actions.
 */
public class InvalidGameAction extends RuntimeException {

    /**
     * Construct an InvalidGameAction with the specified error message.
     *
     * @param message The error message describing the invalid game action.
     */
    public InvalidGameAction(String message) {
        super(message);
    }
}
