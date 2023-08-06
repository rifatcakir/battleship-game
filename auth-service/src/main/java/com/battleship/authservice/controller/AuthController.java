package com.battleship.authservice.controller;

import com.battleship.authservice.jwt.JwtUtils;
import com.battleship.authservice.model.User;
import com.battleship.authservice.payload.request.LoginRequest;
import com.battleship.authservice.payload.request.SignUpRequest;
import com.battleship.authservice.payload.response.JWTResponse;
import com.battleship.authservice.payload.response.MessageResponse;
import com.battleship.authservice.security.CustomUserDetails;
import com.battleship.authservice.service.AuthenticateService;
import com.battleship.authservice.service.RegisterService;
import com.battleship.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterService registerService;
    private final AuthenticateService authenticateService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(registerService.registerUser(signUpRequest.getUsername(),signUpRequest.getPassword()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticateService.authenticateUser(loginRequest.getUsername(),loginRequest.getPassword()));
    }

}
