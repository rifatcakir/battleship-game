package com.battleship.engine.service.impl;

import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.response.GameStatusResponse;
import com.battleship.engine.repository.GameBoardRepository;
import com.battleship.engine.service.GameStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GameStatusServiceImpl implements GameStatusService {

    private final GameBoardRepository gameBoardRepository;

    @Override
    public GameStatusResponse getGameStatus(String playerName, UUID gameLobbyId) {
        BattleshipGameBoard battleshipGameBoard = gameBoardRepository.findById(gameLobbyId);
        PlayerBoardDomain currentPlayerBoard = battleshipGameBoard.getPlayerBoardByPlayerName(playerName);
        return new GameStatusResponse(gameLobbyId, currentPlayerBoard, battleshipGameBoard.getCurrentPlayer(), battleshipGameBoard.getStatus());
    }
}
