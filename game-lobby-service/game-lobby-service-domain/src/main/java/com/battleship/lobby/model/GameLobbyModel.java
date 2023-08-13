package com.battleship.lobby.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameLobbyModel {
    private UUID gameLobbyId;
    private String player1Name;
    private String player2Name;
}
