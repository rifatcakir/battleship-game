package com.battleship.engine.repository;

import com.battleship.engine.model.BattleshipGameBoard;

import java.util.UUID;

public interface GameBoardRepository {
    BattleshipGameBoard save(BattleshipGameBoard board);

    BattleshipGameBoard findById(UUID gameLobbyId);

    Boolean isGameBoardExists(UUID gameBoardId);
}
