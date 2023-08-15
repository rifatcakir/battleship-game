package com.battleship.engine.repository;

import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.GameCreateRequest;

import java.util.UUID;

public interface GameBoardRepository {
    BattleshipGameBoard createGameBoard(GameCreateRequest message);

    BattleshipGameBoard save(BattleshipGameBoard board);

    Boolean isGameBoardExists(UUID gameBoardId);
}
