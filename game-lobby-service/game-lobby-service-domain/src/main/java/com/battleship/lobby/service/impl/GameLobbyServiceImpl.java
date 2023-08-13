package com.battleship.lobby.service.impl;

import com.battleship.lobby.exception.GameLobbyNotFoundException;
import com.battleship.lobby.messaging.GameLobbyPublisher;
import com.battleship.lobby.model.GameLobbyModel;
import com.battleship.lobby.repository.GameLobbyRepository;
import com.battleship.lobby.service.GameLobbyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GameLobbyServiceImpl implements GameLobbyService {

    @Autowired
    private final GameLobbyRepository gameLobbyRepository;

    @Autowired
    private final GameLobbyPublisher gameLobbyPublisher;

    @Override
    public GameLobbyModel createGameLobby(String userName) {
        return gameLobbyRepository.saveGameLobby(new GameLobbyModel(null, userName, null));
    }

    @Override
    public List<GameLobbyModel> findAvailableGameLobby() {
        return gameLobbyRepository.findAvailableGameLobby();
    }

    @Override
    @Transactional
    public GameLobbyModel joinGameLobby(UUID gameLobbyId, String userName) {
        GameLobbyModel gameLobby = gameLobbyRepository.findGameLobbyById(gameLobbyId)
                .orElseThrow(GameLobbyNotFoundException::new);

        //    if (!isGameLobbyAvailable(gameLobby)) {
        //         throw new GameLobbyNotAvailable();
        //      }

        gameLobby.setPlayer2Name(userName);

        GameLobbyModel savedLobby = gameLobbyRepository.saveGameLobby(gameLobby);
        gameLobbyPublisher.publishGameLobby(savedLobby);
        return savedLobby;
    }

    private boolean isGameLobbyAvailable(GameLobbyModel gameLobby) {
        return gameLobby.getPlayer2Name() == null;
    }
}
