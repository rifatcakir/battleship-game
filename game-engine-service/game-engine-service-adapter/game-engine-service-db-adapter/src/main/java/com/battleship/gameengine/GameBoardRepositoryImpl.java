package com.battleship.gameengine;

import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.BoardCell;
import com.battleship.engine.model.GameCreateRequest;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.enums.CellStateDomain;
import com.battleship.engine.model.enums.CurrentTurnDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import com.battleship.engine.repository.GameBoardRepository;
import com.battleship.gameengine.entity.BattleshipGameBoardEntity;
import com.battleship.gameengine.entity.BoardCellEntity;
import com.battleship.gameengine.entity.PlayerBoardEntity;
import com.battleship.gameengine.entity.enums.CurrentTurn;
import com.battleship.gameengine.entity.enums.GameStatus;
import com.battleship.gameengine.repository.BattleshipGameBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class GameBoardRepositoryImpl implements GameBoardRepository {
    @Autowired
    private BattleshipGameBoardRepository battleshipGameBoardRepository;
    @Value("${battleship.game.rowSize}")
    private Integer gameRowSize;
    @Value("${battleship.game.columnSize}")
    private Integer gameColumnSize;

    @Override
    public BattleshipGameBoard createGameBoard(GameCreateRequest message) {
        return toDomain(battleshipGameBoardRepository.save(gameCreateRequestToGameBoardEntity(message)));
    }

    private BattleshipGameBoard toDomain(BattleshipGameBoardEntity entity) {
        BattleshipGameBoard gameBoardDomain = new BattleshipGameBoard();
        gameBoardDomain.setGameId(entity.getGameId());
        gameBoardDomain.setPlayer1Board(playerBoardEntityToDomain(entity.getPlayer1Board()));
        gameBoardDomain.setPlayer2Board(playerBoardEntityToDomain(entity.getPlayer2Board()));
        gameBoardDomain.setStartDate(entity.getStartDate());
        gameBoardDomain.setEndDate(entity.getEndDate());
        gameBoardDomain.setStatus(GameStatusDomain.valueOf(entity.getStatus().name()));
        gameBoardDomain.setCurrentTurnDomain(CurrentTurnDomain.valueOf(entity.getCurrentTurn().name()));
        return gameBoardDomain;
    }

    private PlayerBoardDomain playerBoardEntityToDomain(PlayerBoardEntity entity) {
        return new PlayerBoardDomain(entity.getPlayerName(), cellEntityToDomain(entity.getBoardCellEntities()));
    }

    private BoardCell[][] cellEntityToDomain(BoardCellEntity[][] boardCellEntities) {
        int rows = boardCellEntities.length;
        int cols = boardCellEntities[0].length;

        BoardCell[][] boardCellsDomain = new BoardCell[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                BoardCellEntity entity = boardCellEntities[i][j];
                BoardCell domain = mapEntityToDomain(entity);
                boardCellsDomain[i][j] = domain;
            }
        }

        return boardCellsDomain;
    }

    private BoardCell mapEntityToDomain(BoardCellEntity entity) {
        return (entity == null) ? null : new BoardCell(CellStateDomain.valueOf(entity.getState().name()));
    }


    private BattleshipGameBoardEntity gameCreateRequestToGameBoardEntity(GameCreateRequest message) {
        BattleshipGameBoardEntity gameBoard = new BattleshipGameBoardEntity();
        gameBoard.setGameId(message.getGameLobbyId());
        gameBoard.setPlayer1Board(new PlayerBoardEntity(message.getPlayer1Name(), new BoardCellEntity[gameRowSize][gameColumnSize]));
        gameBoard.setPlayer2Board(new PlayerBoardEntity(message.getPlayer2Name(), new BoardCellEntity[gameRowSize][gameColumnSize]));
        gameBoard.setCurrentTurn(CurrentTurn.PLAYER1);
        gameBoard.setStatus(GameStatus.ONGOING);
        gameBoard.setStartDate(OffsetDateTime.now());
        return gameBoard;
    }
}
