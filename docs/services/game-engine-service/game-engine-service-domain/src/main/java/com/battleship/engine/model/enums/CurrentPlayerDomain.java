package com.battleship.engine.model.enums;

public enum CurrentPlayerDomain {
    PLAYER1, PLAYER2;

    public CurrentPlayerDomain nextPlayer() {
        return (this == PLAYER1) ? PLAYER2 : PLAYER1;
    }
}
