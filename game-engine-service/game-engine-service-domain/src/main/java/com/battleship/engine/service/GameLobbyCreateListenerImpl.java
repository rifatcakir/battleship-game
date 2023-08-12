package com.battleship.engine.service;

import com.battleship.engine.messaging.GameLobbyCreateListener;
import com.battleship.engine.model.GameCreateRequest;
import com.battleship.engine.repository.GameBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameLobbyCreateListenerImpl implements GameLobbyCreateListener {
    @Autowired
    private GameBoardRepository gameBoardRepository;
    @Override
    public void listener(GameCreateRequest message) {
        System.out.println(message.getGameLobbyId() + message.getPlayer1Name());
        System.out.println("------------------------");
        gameBoardRepository.createGameBoard();
    }
}
