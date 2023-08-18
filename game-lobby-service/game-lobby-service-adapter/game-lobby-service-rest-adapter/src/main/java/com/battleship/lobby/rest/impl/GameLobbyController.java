package com.battleship.lobby.rest.impl;

import com.battleship.lobby.exception.ErrorMessage;
import com.battleship.lobby.rest.model.GameLobbyAPIModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Tag(name = "Game Lobby", description = "Game lobby actions")
@RequestMapping(value = "/game-lobby/v0")
public interface GameLobbyController {

    @Operation(
            summary = "Create new game lobby",
            description = "Game lobby registration endpoint",
            tags = "GameLobby"
    )
    @PreAuthorize("hasAuthority('ROLE_PLAYER')")
    @PostMapping("/create")
    ResponseEntity<GameLobbyAPIModel> createGameLobby(HttpServletRequest request);

    @Operation(
            summary = "Find available game lobby",
            description = "Find available Game lobby endpoint",
            tags = "GameLobby"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully joined the game lobby",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GameLobbyAPIModel.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Game lobby not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            )})
    @PreAuthorize("hasAuthority('ROLE_PLAYER')")
    @GetMapping("/find/available")
    ResponseEntity<List<GameLobbyAPIModel>> findAvailableGameLobby();

    @PreAuthorize("hasAuthority('ROLE_PLAYER')")
    @PostMapping("/join/{id}")
    @Operation(
            summary = "Join available game lobby",
            description = "Join available Game lobby endpoint",
            tags = "GameLobby"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully joined the game lobby",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GameLobbyAPIModel.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Game lobby not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Game lobby action failed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)
                    )
            )
    })
    ResponseEntity<GameLobbyAPIModel> joinAvailableGameLobby(HttpServletRequest request, @PathVariable(value = "id") UUID gameLobbyId);

}
