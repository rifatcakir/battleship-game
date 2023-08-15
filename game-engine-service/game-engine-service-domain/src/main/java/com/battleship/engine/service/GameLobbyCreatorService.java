package com.battleship.engine.service;

import com.battleship.engine.messaging.GameLobbyCreator;
import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.GameCreateRequest;
import com.battleship.engine.repository.GameBoardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class GameLobbyCreatorService implements GameLobbyCreator {

    private final GameBoardRepository gameBoardRepository;

    @Override
    public void listener(GameCreateRequest message) {
        if (gameBoardRepository.isGameBoardExists(message.getGameLobbyId())) {
            throw new IllegalArgumentException(String.format("Game lobby already created for id: [ %s ]", message.getGameLobbyId()));
        }
        BattleshipGameBoard gameLobby = gameBoardRepository.createGameBoard(message);
        log.info(String.format("Game lobby created with id= [%s]", gameLobby.getGameId()));
    }

}
