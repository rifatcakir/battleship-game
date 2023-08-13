package com.battleship.engine.model.enums;

public enum CurrentTurnDomain {
    PLAYER1, PLAYER2;

    public CurrentTurnDomain nextPlayer() {
        return (this == PLAYER1) ? PLAYER2 : PLAYER1;
    }
}
