package com.battleship.engine.messaging;


import com.battleship.engine.model.GameCreateRequest;
import org.springframework.stereotype.Service;

/**
 * The GameBoardCreator interface defines a method to create a game board based on the provided request.
 * Implementing classes handle the creation of a game board for players to participate in a game.
 */
@Service
public interface GameBoardCreator {

    /**
     * Create a game board based on the provided game creation request.
     *
     * @param gameCreateRequest The request containing information for creating the game board.
     */
    void createGameBoard(GameCreateRequest gameCreateRequest);
}
