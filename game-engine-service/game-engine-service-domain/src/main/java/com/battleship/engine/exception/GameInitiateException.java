package com.battleship.engine.exception;

/**
 * The GameInitiateException class represents an exception that is thrown when game initiation encounters an issue.
 * It extends the RuntimeException class to indicate runtime exceptions related to game initiation errors.
 */
public class GameInitiateException extends RuntimeException {

    /**
     * Construct a GameInitiateException with the specified error message.
     *
     * @param message The error message describing the issue during game initiation.
     */
    public GameInitiateException(String message) {
        super(message);
    }
}
