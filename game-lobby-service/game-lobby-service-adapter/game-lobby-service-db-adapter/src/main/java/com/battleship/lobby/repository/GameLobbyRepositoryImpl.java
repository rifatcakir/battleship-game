package com.battleship.lobby.repository;

import com.battleship.lobby.entity.GameLobby;
import com.battleship.lobby.model.GameLobbyModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameLobbyRepositoryImpl implements GameLobbyRepository {

    private final GameLobbyMysqlRepository gameLobbyMysqlRepository;

    @Override
    public GameLobbyModel saveGameLobby(GameLobbyModel gameLobbyModel) {
        GameLobby gameLobbyEntity = gameLobbyMysqlRepository.save(toEntity(gameLobbyModel));
        return toDomainModel(gameLobbyEntity);
    }

    @Override
    public List<GameLobbyModel> findAvailableGameLobby() {
        return gameLobbyMysqlRepository.findAvailableGame().stream().map(this::toDomainModel).collect(Collectors.toList());
    }

    @Override
    public Optional<GameLobbyModel> findGameLobbyById(UUID gameLobbyId) {
        Optional<GameLobby> gameLobby = gameLobbyMysqlRepository.findById(gameLobbyId);
        return gameLobby.map(this::toDomainModel);
    }

    private GameLobby toEntity(GameLobbyModel gameLobbyModel) {
        GameLobby gameLobbyEntity = new GameLobby();
        gameLobbyEntity.setGameLobbyId(gameLobbyModel.getGameLobbyId());
        gameLobbyEntity.setPlayer1Name(gameLobbyModel.getPlayer1Name());
        gameLobbyEntity.setPlayer2Name(gameLobbyModel.getPlayer2Name());
        return gameLobbyEntity;
    }

    private GameLobbyModel toDomainModel(GameLobby gameLobbyEntity) {
        return new GameLobbyModel(
                gameLobbyEntity.getGameLobbyId(),
                gameLobbyEntity.getPlayer1Name(),
                gameLobbyEntity.getPlayer2Name()
        );
    }
}
