package com.battleship.lobby.messaging;

import com.battleship.lobby.messaging.model.GameLobbyMessage;
import com.battleship.lobby.model.GameLobbyModel;
import org.springframework.stereotype.Service;

@Service
public class GameLobbyPublisherImpl implements GameLobbyPublisher {


    @Override
    public void publishGameLobby(GameLobbyModel gameLobby) {
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
