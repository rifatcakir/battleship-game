package com.battleship.rest;

import com.battleship.rest.model.request.ShipPlacementRequestApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Game Engine", description = "Game engine actions")
@RequestMapping(value = "/game-engine")
public interface GameEngineController {

    @Operation(summary = "Place Ship", description = "Ship placement endpoint", tags = "GameEngine")
    @PreAuthorize("hasAuthority('ROLE_PLAYER')")
    @PostMapping("/ship/place")
    ResponseEntity<?> shipPlacement(HttpServletRequest request, @RequestBody ShipPlacementRequestApi shipPlacementRequestApi);
}
