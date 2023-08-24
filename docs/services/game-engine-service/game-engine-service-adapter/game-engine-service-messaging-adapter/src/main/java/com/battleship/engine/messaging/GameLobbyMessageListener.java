package com.battleship.engine.messaging;

import com.battleship.engine.messaging.metric.MetricManager;
import com.battleship.engine.messaging.model.GameLobbyMessage;
import com.battleship.engine.model.GameCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameLobbyMessageListener {
    private final MetricManager metricManager;
    private final GameBoardCreator gameBoardCreator;

    @RabbitListener(queues = {"${battleship.rabbitmq.queue}"})
    public void listener(GameLobbyMessage message) {
        try {
            gameBoardCreator.createGameBoard(toDomain(message));
        } catch (Exception e) {
            metricManager.increment();
            throw e;
        }

    }

    private GameCreateRequest toDomain(GameLobbyMessage message) {
        GameCreateRequest request = new GameCreateRequest();
        request.setGameLobbyId(message.getGameLobbyId());
        request.setPlayer1Name(message.getPlayer1Name());
        request.setPlayer2Name(message.getPlayer2Name());
        return request;
    }

}
