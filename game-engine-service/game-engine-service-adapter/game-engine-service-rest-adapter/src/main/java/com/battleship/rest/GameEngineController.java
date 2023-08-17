package com.battleship.rest;

import com.battleship.rest.model.request.ShipAttackRequestApi;
import com.battleship.rest.model.request.ShipPlacementRequestApi;
import com.battleship.rest.model.response.GameStatusApiResponse;
import com.battleship.rest.model.response.ShipActionAPIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Tag(name = "Game Engine", description = "Game engine actions")
@RequestMapping(value = "/game-engine/v0/actions")
public interface GameEngineController {

    @Operation(summary = "Place Ship", description = "Ship placement endpoint", tags = "GameEngine")
    @ApiResponse(
            responseCode = "200",
            description = "Successful response",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ShipActionAPIResponse.class)
            )
    )
    @PreAuthorize("hasAuthority('ROLE_PLAYER')")
    @PostMapping("/ship-place")
    ResponseEntity<ShipActionAPIResponse> shipPlacement(HttpServletRequest request, @RequestBody ShipPlacementRequestApi shipPlacementRequestApi);


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
                    schema = @Schema(implementation = ShipActionAPIResponse.class)
            )
    )
    ResponseEntity<ShipActionAPIResponse> performAttack(HttpServletRequest request, @RequestBody ShipAttackRequestApi shipAttackRequestApi);


    @GetMapping("/listen/{id}")
    @Operation(
            summary = "Get Current Game Status",
            description = "Endpoint to get the current game status"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved game status",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GameStatusApiResponse.class)
            )
    )
    ResponseEntity<GameStatusApiResponse> getCurrentGameStatus(HttpServletRequest request, @PathVariable(value = "id") UUID gameLobbyId);
}
