package com.battleship.authservice.controller;

import com.battleship.authservice.payload.request.LoginRequest;
import com.battleship.authservice.payload.request.SignUpRequest;
import com.battleship.authservice.payload.response.JWTResponse;
import com.battleship.authservice.payload.response.MessageResponse;
import com.battleship.authservice.service.AuthenticateService;
import com.battleship.authservice.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Authentication API spec for Swagger doc
 */
@Tag(name = "Authorization", description = "Login and SignUp endpoints")
@RestController
@RequestMapping(value = "/authenticate")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterService registerService;
    private final AuthenticateService authenticateService;

    @Operation(
            summary = "Create new user",
            description = "User registration endpoint",
            tags = "Registration"
    )
    @ApiResponse(
            responseCode = "201",
            description = "User successfully registered",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MessageResponse.class)
            )
    )
    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody SignUpRequest signUpRequest) {
        MessageResponse messageResponse = registerService.registerUser(signUpRequest.getUsername(), signUpRequest.getPassword());
        messageResponse.add(linkTo(methodOn(AuthController.class).authenticateUser(null)).withRel("Authenticate"));
        return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Login", description = "Generating JWT tokens", tags = {"Authorization"})
    @PostMapping("/login")
    @ApiResponse(responseCode = "200", description = "JWT token",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = JWTResponse.class))))
    public ResponseEntity<JWTResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticateService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword()));
    }

}
