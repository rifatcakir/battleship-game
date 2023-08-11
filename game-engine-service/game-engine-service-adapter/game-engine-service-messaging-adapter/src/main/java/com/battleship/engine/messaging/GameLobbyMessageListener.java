package com.battleship.engine.messaging;

import com.battleship.engine.messaging.model.GameLobbyMessage;
import com.battleship.engine.model.GameCreateRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameLobbyMessageListener {

    @Autowired
    private GameLobbyCreateListener gameLobbyCreateListener;

    @RabbitListener(queues = {"${battleship.rabbitmq.queue}"})
    public void listener(GameLobbyMessage message) {
        gameLobbyCreateListener.listener(toDomain(message));
    }

    private GameCreateRequest toDomain(GameLobbyMessage message) {
        GameCreateRequest request = new GameCreateRequest();
        request.setGameLobbyId(message.getGameLobbyId());
        request.setPlayer1Name(message.getPlayer1Name());
        request.setPlayer2Name(message.getPlayer2Name());
        return request;
    }

}
