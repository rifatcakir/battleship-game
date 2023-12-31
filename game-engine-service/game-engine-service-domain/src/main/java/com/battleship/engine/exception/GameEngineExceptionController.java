package com.battleship.engine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * The GameEngineExceptionController class provides centralized exception handling for game engine related exceptions.
 * It maps specific exception types to appropriate error responses for a consistent error handling approach.
 */
@ControllerAdvice
public class GameEngineExceptionController {

    /**
     * Handle the exception thrown when game initiation fails.
     *
     * @param ex      The GameInitiateException that occurred.
     * @param request The web request associated with the exception.
     * @return A ResponseEntity containing an error message and appropriate HTTP status.
     */
    @ExceptionHandler(GameInitiateException.class)
    public ResponseEntity<ErrorMessage> gameNotFound(GameInitiateException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle the exception thrown when an invalid game action is performed.
     *
     * @param ex      The InvalidGameAction that occurred.
     * @param request The web request associated with the exception.
     * @return A ResponseEntity containing an error message and appropriate HTTP status.
     */
    @ExceptionHandler(InvalidGameAction.class)
    public ResponseEntity<ErrorMessage> invalidGameAction(InvalidGameAction ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle general exceptions that are not explicitly caught.
     *
     * @param ex The Exception that occurred.
     * @param request The web request associated with the exception.
     * @return A ResponseEntity containing an error message and appropriate HTTP status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
