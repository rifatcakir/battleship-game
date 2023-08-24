package com.battleship.engine.model.enums;

public enum GameStatusDomain {
    SHIP_PLACEMENT, ONGOING, PLAYER1_WIN, PLAYER2_WIN;

    public static GameStatusDomain byPlayerType(CurrentPlayerDomain currentPlayerDomain) {
        return (currentPlayerDomain == CurrentPlayerDomain.PLAYER1) ? PLAYER1_WIN : PLAYER2_WIN;
    }
}
