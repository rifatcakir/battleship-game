package com.battleship.rest;

import com.battleship.engine.model.request.ShipActionResponse;
import com.battleship.rest.model.request.ShipAttackRequestApi;
import com.battleship.rest.model.request.ShipPlacementRequestApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Game Engine", description = "Game engine actions")
@RequestMapping(value = "/game-engine/v0/actions")
public interface GameEngineController {

    @Operation(summary = "Place Ship", description = "Ship placement endpoint", tags = "GameEngine")
    @ApiResponse(
            responseCode = "200",
            description = "Successful response",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ShipActionResponse.class)
            )
    )
    @PreAuthorize("hasAuthority('ROLE_PLAYER')")
    @PostMapping("/ship-place")
    ResponseEntity<ShipActionResponse> shipPlacement(HttpServletRequest request, @RequestBody ShipPlacementRequestApi shipPlacementRequestApi);


    @PostMapping("/attack")
    @Operation(
            summary = "Perform Attack",
            description = "Attack placement endpoint"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful attack",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ShipActionResponse.class)
            )
    )
    ResponseEntity<ShipActionResponse> performAttack(HttpServletRequest request, @RequestBody ShipAttackRequestApi shipAttackRequestApi);

}
