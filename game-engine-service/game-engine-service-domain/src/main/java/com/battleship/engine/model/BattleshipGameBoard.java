package com.battleship.engine.model;

import com.battleship.engine.exception.GameInitiateException;
import com.battleship.engine.model.enums.CurrentPlayerDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class BattleshipGameBoard {
    private UUID gameId;
    private List<PlayerBoardDomain> playerBoards;
    private CurrentPlayerDomain currentPlayer;
    private GameStatusDomain status;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;

    public PlayerBoardDomain getEnemyBoardByPlayerCurrent(CurrentPlayerDomain currentPlayerDomain) {
        return playerBoards.stream().filter(it -> it.getCurrentPlayerDomain().equals(currentPlayerDomain.nextPlayer())).findFirst()
                .orElseThrow(() -> getGameInitiateException(currentPlayerDomain.nextPlayer().name()));
    }

    public PlayerBoardDomain getPlayerBoardByPlayerName(String playerName) {
        return playerBoards.stream().filter(it -> it.getPlayerName().equals(playerName)).findFirst()
                .orElseThrow(() -> getGameInitiateException(playerName));
    }

    public PlayerBoardDomain getEnemyPlayerBoardByPlayerName(String playerName) {
        return playerBoards.stream().filter(it -> !it.getPlayerName().equals(playerName)).findFirst()
                .orElseThrow(() -> getGameInitiateException(playerName));
    }

    private GameInitiateException getGameInitiateException(String currentPlayerDomain) {
        return new GameInitiateException(String.format("Player board not exits for [%s]", currentPlayerDomain));
    }
}