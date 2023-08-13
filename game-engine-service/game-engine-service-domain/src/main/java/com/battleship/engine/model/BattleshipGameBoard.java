package com.battleship.engine.model;

import com.battleship.engine.model.enums.CurrentTurnDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class BattleshipGameBoard {
    private UUID gameId;
    private PlayerBoardDomain player1Board;
    private PlayerBoardDomain player2Board;
    private CurrentTurnDomain currentTurnDomain;
    private GameStatusDomain status;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
}