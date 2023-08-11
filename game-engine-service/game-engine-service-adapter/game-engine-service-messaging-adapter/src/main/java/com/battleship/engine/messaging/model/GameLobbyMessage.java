package com.battleship.engine.messaging.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GameLobbyMessage implements Serializable {
    private Integer gameLobbyId;
    private String player1Name;
    private String player2Name;
}
