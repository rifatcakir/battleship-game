package com.battleship.engine.model;

import com.battleship.engine.model.enums.CurrentPlayerDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
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

    public Optional<PlayerBoardDomain> getPlayerBoardByPlayerName(String playerName) {
        return playerBoards.stream().filter(it -> it.getPlayerName().equals(playerName)).findFirst();
    }

    public Optional<PlayerBoardDomain> getEnemyPlayerBoardByPlayerName(String playerName) {
        return playerBoards.stream().filter(it -> !it.getPlayerName().equals(playerName)).findFirst();
    }
}