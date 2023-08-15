package com.battleship.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Game Engine", description = "Game engine actions")
@RequestMapping(value = "/game-engine")
public interface GameLobbyController {

    @Operation(summary = "Create new game lobby", description = "Game lobby registration endpoint", tags = "GameLobby")
    @PreAuthorize("hasAuthority('ROLE_PLAYER')")
    @GetMapping("/create")
    ResponseEntity<?> createGameLobby(HttpServletRequest request);
}
