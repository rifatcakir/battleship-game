package com.battleship.authservice.payload.response;

import lombok.Data;

@Data
public class JWTResponse {
    private String token;
    private String type = "Bearer";
}
