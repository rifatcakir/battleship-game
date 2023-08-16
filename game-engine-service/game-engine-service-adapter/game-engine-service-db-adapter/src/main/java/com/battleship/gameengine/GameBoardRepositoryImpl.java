package com.battleship.gameengine;

import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.repository.GameBoardRepository;
import com.battleship.gameengine.exception.GameNotFound;
import com.battleship.gameengine.repository.BattleshipGameBoardRepository;
import com.battleship.gameengine.util.GameBoardMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameBoardRepositoryImpl implements GameBoardRepository {
    private final BattleshipGameBoardRepository battleshipGameBoardRepository;
    private final GameBoardMapperUtil gameBoardMapperUtil;

    @Override
    public BattleshipGameBoard save(BattleshipGameBoard board) {
        return gameBoardMapperUtil.toDomain(battleshipGameBoardRepository.save(gameBoardMapperUtil.domainToEntity(board)));
    }

    @Override
    public BattleshipGameBoard findById(UUID gameLobbyId) {
        return gameBoardMapperUtil.toDomain(battleshipGameBoardRepository.findById(gameLobbyId).orElseThrow(GameNotFound::new));
    }

    @Override
    public Boolean isGameBoardExists(UUID gameBoardId) {
        return battleshipGameBoardRepository.existsById(gameBoardId);
    }

}
