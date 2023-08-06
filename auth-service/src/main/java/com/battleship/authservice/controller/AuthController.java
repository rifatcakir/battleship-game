package com.battleship.authservice.controller;

import com.battleship.authservice.payload.request.LoginRequest;
import com.battleship.authservice.payload.request.SignUpRequest;
import com.battleship.authservice.service.AuthenticateService;
import com.battleship.authservice.service.RegisterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Authentication API spec for Swagger doc
 */
@Tag(name = "Authorization", description = "Login endpoint")
@RestController
@RequestMapping(value = "/authenticate")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterService registerService;
    private final AuthenticateService authenticateService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest,
                                          UriComponentsBuilder uriComponentsBuilder) {
        var location = uriComponentsBuilder.path("/login")
                .buildAndExpand().toUri();
        return ResponseEntity.created(location).body(registerService.registerUser(signUpRequest.getUsername(), signUpRequest.getPassword()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticateService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword()));
    }

}
