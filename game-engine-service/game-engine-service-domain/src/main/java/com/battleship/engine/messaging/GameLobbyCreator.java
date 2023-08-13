package com.battleship.engine.messaging;


import com.battleship.engine.model.GameCreateRequest;
import org.springframework.stereotype.Service;

@Service
public interface GameLobbyCreator {
    void listener(GameCreateRequest message);
}
