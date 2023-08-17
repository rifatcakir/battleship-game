package com.battleship.authservice.exception;

public class UserExistsException extends RuntimeException {
    public UserExistsException(String msg) {
        super(msg);
    }
}
