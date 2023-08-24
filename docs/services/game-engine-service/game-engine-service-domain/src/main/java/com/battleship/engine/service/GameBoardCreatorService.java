package com.battleship.engine.service;

import com.battleship.engine.exception.GameInitiateException;
import com.battleship.engine.messaging.GameBoardCreator;
import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.BoardCell;
import com.battleship.engine.model.GameCreateRequest;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.enums.CurrentPlayerDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import com.battleship.engine.model.enums.PlayerBoardStatus;
import com.battleship.engine.repository.GameBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameBoardCreatorService implements GameBoardCreator {

    private final GameBoardRepository gameBoardRepository;

    @Value("${battleship.game.rowSize}")
    private Integer gameRowSize;
    @Value("${battleship.game.columnSize}")
    private Integer gameColumnSize;

    @Override
    public void createGameBoard(GameCreateRequest message) {
        if (gameBoardRepository.isGameBoardExists(message.getGameLobbyId())) {
            throw new GameInitiateException(String.format("Game lobby already created for id: [ %s ]", message.getGameLobbyId()));
        }

        BattleshipGameBoard gameLobby = gameBoardRepository.save(gameCreateRequestToGameBoardModel(message));
        log.info(String.format("Game lobby created with id= [%s]", gameLobby.getGameId()));
    }

    private BattleshipGameBoard gameCreateRequestToGameBoardModel(GameCreateRequest message) {
        BattleshipGameBoard gameBoard = new BattleshipGameBoard();
        gameBoard.setGameId(message.getGameLobbyId());
        gameBoard.setPlayerBoards(List.of(
                new PlayerBoardDomain(CurrentPlayerDomain.PLAYER1, message.getPlayer1Name(), new BoardCell[gameRowSize][gameColumnSize], PlayerBoardStatus.SHIP_PLACEMENT),
                new PlayerBoardDomain(CurrentPlayerDomain.PLAYER2, message.getPlayer2Name(), new BoardCell[gameRowSize][gameColumnSize], PlayerBoardStatus.SHIP_PLACEMENT)));
        gameBoard.setCurrentPlayer(CurrentPlayerDomain.PLAYER1);
        gameBoard.setStatus(GameStatusDomain.SHIP_PLACEMENT);
        gameBoard.setStartDate(OffsetDateTime.now());
        return gameBoard;
    }

}
