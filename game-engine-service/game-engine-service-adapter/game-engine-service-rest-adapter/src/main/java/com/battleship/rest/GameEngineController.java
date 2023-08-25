package com.battleship.rest;

import com.battleship.engine.exception.ErrorMessage;
import com.battleship.rest.model.request.ShipAttackRequestApi;
import com.battleship.rest.model.request.ShipPlacementRequestApi;
import com.battleship.rest.model.response.GameStatusApiResponse;
import com.battleship.rest.model.response.ShipActionAPIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Tag(name = "GameEngine", description = "Game engine actions")
@RequestMapping(value = "/game-engine/v0/actions")
public interface GameEngineController {

    @Operation(summary = "Place Ship", description = "Ship placement endpoint", tags = "GameEngine")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful response",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShipActionAPIResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Game not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid game action",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            )})
    @PreAuthorize("hasAuthority('ROLE_PLAYER')")
    @PostMapping("/ship-place")
    ResponseEntity<ShipActionAPIResponse> shipPlacement(HttpServletRequest request, @RequestBody ShipPlacementRequestApi shipPlacementRequestApi);


    @PostMapping("/attack")
    @Operation(
            summary = "Perform Attack",
            description = "Attack placement endpoint"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful attack",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShipActionAPIResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid attack action",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Game not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            )
    })
    ResponseEntity<ShipActionAPIResponse> performAttack(HttpServletRequest request, @RequestBody ShipAttackRequestApi shipAttackRequestApi);


    @GetMapping("/listen/{id}")
    @Operation(
            summary = "Get Current Game Status",
            description = "Endpoint to get the current game status"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved game status",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GameStatusApiResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Game not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            )
    })
    ResponseEntity<GameStatusApiResponse> getCurrentGameStatus(HttpServletRequest request, @PathVariable(value = "id") UUID gameLobbyId);
}
