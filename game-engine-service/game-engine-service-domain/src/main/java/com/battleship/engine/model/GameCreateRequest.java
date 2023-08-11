package com.battleship.engine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameCreateRequest {
    private Integer gameLobbyId;
    private String player1Name;
    private String player2Name;
}
