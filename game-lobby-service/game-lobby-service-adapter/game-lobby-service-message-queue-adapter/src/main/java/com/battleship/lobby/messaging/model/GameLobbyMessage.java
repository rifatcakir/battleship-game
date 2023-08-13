package com.battleship.lobby.messaging.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class GameLobbyMessage implements Serializable {
    private UUID gameLobbyId;
    private String player1Name;
    private String player2Name;
}
