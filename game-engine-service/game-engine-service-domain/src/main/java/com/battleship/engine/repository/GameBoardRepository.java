package com.battleship.engine.repository;

import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.GameCreateRequest;

public interface GameBoardRepository {
    BattleshipGameBoard createGameBoard(GameCreateRequest message);
}
