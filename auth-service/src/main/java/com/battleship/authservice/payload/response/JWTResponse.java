package com.battleship.authservice.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class JWTResponse {
    private String token;
    private String type = "Bearer";
}
