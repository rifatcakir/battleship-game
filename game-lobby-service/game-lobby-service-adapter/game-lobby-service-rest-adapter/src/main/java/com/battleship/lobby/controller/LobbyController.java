package com.battleship.lobby.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Game Lobby", description = "Game lobby actions")
@RestController
@RequestMapping(value = "/game-lobby")
public class LobbyController {

    @GetMapping("/create")
    @Operation(summary = "Create new game lobby", description = "Game lobby registration endpoint", tags = "GameLobby")
    @PreAuthorize("hasAuthority('ROLE_PLAYER')")
    ResponseEntity<?> createGameLobby() {
        return ResponseEntity.ok("hu");
    }

    ;

    @GetMapping("/find/available")
    @Operation(summary = "Find available game lobby", description = "Find available Game lobby endpoint", tags = "GameLobby")
    @PreAuthorize("hasAuthority('ROLE_PLAYER')")
    ResponseEntity<?> findAvailableGameLobby() {
        return ResponseEntity.ok("hu2");
    }

    ;

}
