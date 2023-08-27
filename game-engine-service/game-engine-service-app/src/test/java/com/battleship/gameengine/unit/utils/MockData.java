package com.battleship.gameengine.unit.utils;


import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.BoardCell;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.enums.CurrentPlayerDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import com.battleship.engine.model.enums.PlayerBoardStatus;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockData {

    public static BattleshipGameBoard mockBattleShipGameBoard() {
        BattleshipGameBoard battleshipGameBoard = new BattleshipGameBoard();
        battleshipGameBoard.setGameId(UUID.randomUUID());
        battleshipGameBoard.setStatus(GameStatusDomain.SHIP_PLACEMENT);
        battleshipGameBoard.setStartDate(OffsetDateTime.now());
        battleshipGameBoard.setCurrentPlayer(CurrentPlayerDomain.PLAYER1);

        List<PlayerBoardDomain> playerBoards = new ArrayList<>();
        playerBoards.add(new PlayerBoardDomain(CurrentPlayerDomain.PLAYER1, "John", new BoardCell[10][10], PlayerBoardStatus.SHIP_PLACEMENT));
        playerBoards.add(new PlayerBoardDomain(CurrentPlayerDomain.PLAYER2, "Jane", new BoardCell[10][10], PlayerBoardStatus.SHIP_PLACEMENT));
        battleshipGameBoard.setPlayerBoards(playerBoards);

        return battleshipGameBoard;
    }
}
