package com.battleship.engine.service;

import com.battleship.engine.messaging.GameLobbyCreateListener;
import com.battleship.engine.model.GameCreateRequest;
import org.springframework.stereotype.Service;

@Service
public class GameLobbyCreateListenerImpl implements GameLobbyCreateListener {
    @Override
    public void listener(GameCreateRequest message) {

    }
}
