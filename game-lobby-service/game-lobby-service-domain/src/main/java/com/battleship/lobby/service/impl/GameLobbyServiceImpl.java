package com.battleship.lobby.service.impl;

import com.battleship.lobby.exception.GameLobbyActionFailed;
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
import java.util.Objects;
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

        if (!isGameLobbyAvailable(gameLobby.getPlayer2Name())) {
            throw new GameLobbyActionFailed("Game lobby not available!");
        }
        if (bothUsersSame(gameLobby.getPlayer1Name(), userName)) {
            throw new GameLobbyActionFailed("You cannot join the lobby you have created!");
        }

        gameLobby.setPlayer2Name(userName);

        GameLobbyModel savedLobby = gameLobbyRepository.saveGameLobby(gameLobby);
        gameLobbyPublisher.publishGameLobby(savedLobby);
        return savedLobby;
    }

    private boolean bothUsersSame(String player1Name, String userName) {
        return Objects.equals(player1Name, userName);
    }

    private boolean isGameLobbyAvailable(String player2Name) {
        return player2Name == null;
    }
}
