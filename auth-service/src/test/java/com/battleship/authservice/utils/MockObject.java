package com.battleship.authservice.utils;

import com.battleship.authservice.model.User;
import com.battleship.authservice.payload.request.LoginRequest;
import com.battleship.authservice.payload.request.SignUpRequest;


public class MockObject {
    public static User user() {
        User user = new User();
        user.setUsername("John");
        user.setPassword("Doe");
        return user;
    }

    public static SignUpRequest signUpRequest() {
        var request = new SignUpRequest();
        request.setUsername("John");
        request.setPassword("Doe");
        return request;
    }

    public static LoginRequest loginRequest() {
        var request = new LoginRequest();
        request.setUsername("John");
        request.setPassword("Doe");
        return request;
    }
}
