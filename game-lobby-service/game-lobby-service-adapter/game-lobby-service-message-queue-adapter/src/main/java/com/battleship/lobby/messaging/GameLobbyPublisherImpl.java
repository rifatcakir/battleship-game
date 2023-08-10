package com.battleship.lobby.messaging;

import com.battleship.lobby.messaging.model.GameLobbyMessage;
import com.battleship.lobby.model.GameLobbyModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GameLobbyPublisherImpl implements GameLobbyPublisher {
    @Value("${battleship.rabbitmq.exchange}")
    private String exchange;

    @Value("${battleship.rabbitmq.routingkey}")
    private String routingKey;
    @Autowired
    private RabbitTemplate template;

    @Override
    public void publishGameLobby(GameLobbyModel gameLobby) {
        template.convertAndSend(exchange,
                routingKey, gameLobby);
        System.out.println("_________________________" + gameLobby.getGameLobbyId());
    }

    private GameLobbyMessage toMessage(GameLobbyModel gameLobby) {
        GameLobbyMessage message = new GameLobbyMessage();
        message.setGameLobbyId(gameLobby.getGameLobbyId());
        message.setPlayer1Name(gameLobby.getPlayer1Name());
        message.setPlayer2Name(gameLobby.getPlayer2Name());
        return message;
    }
}
