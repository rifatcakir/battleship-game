package com.battleship.engine.model;

import com.battleship.engine.exception.GameNotFound;
import com.battleship.engine.model.enums.CurrentTurnDomain;
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
    private CurrentTurnDomain currentTurnDomain;
    private GameStatusDomain status;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;

    public PlayerBoardDomain getPlayerBoardByPlayerName(String playerName) {
        return playerBoards.stream().filter(it -> it.getPlayerName().equals(playerName)).findFirst().orElseThrow(GameNotFound::new); // todo fix with new error
    }
}