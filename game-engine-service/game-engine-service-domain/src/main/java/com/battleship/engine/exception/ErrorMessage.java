package com.battleship.engine.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

/**
 * The ErrorMessage class represents an error response message containing information about an error occurrence.
 * It includes details such as the HTTP status code, timestamp, error message, and a description of the error.
 */
@Getter
@AllArgsConstructor
public class ErrorMessage {

    /**
     * The HTTP status code indicating the nature of the error.
     */
    private int statusCode;

    /**
     * The timestamp indicating when the error occurred.
     */
    private Date timestamp;

    /**
     * The error message providing a brief description of the error.
     */
    private String message;

    /**
     * A detailed description or additional information about the error.
     */
    private String description;
}
