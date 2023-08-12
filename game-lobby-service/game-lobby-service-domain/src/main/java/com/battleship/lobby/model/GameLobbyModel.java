package com.battleship.lobby.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameLobbyModel {
    private Integer gameLobbyId;
    private String player1Id;
    private String player2Id;
}
