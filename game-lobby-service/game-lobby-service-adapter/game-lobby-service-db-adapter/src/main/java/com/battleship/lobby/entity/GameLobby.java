package com.battleship.lobby.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "GameLobby")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameLobby implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer gameLobbyId;

    private String player1Name;
    private String player2Name;
}
