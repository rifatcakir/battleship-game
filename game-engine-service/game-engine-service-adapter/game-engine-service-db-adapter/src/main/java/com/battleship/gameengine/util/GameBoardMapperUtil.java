package com.battleship.gameengine.util;

import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.BoardCell;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.enums.CurrentPlayerDomain;
import com.battleship.engine.model.enums.GameStatusDomain;
import com.battleship.engine.model.enums.PlayerBoardStatus;
import com.battleship.gameengine.entity.BattleshipGameBoardEntity;
import com.battleship.gameengine.entity.BoardCellEntity;
import com.battleship.gameengine.entity.PlayerBoardEntity;
import com.battleship.gameengine.entity.PlayerBoardStatusEntity;
import com.battleship.gameengine.entity.enums.CurrentPlayerEntity;
import com.battleship.gameengine.entity.enums.CurrentTurn;
import com.battleship.gameengine.entity.enums.GameStatus;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GameBoardMapperUtil {

    public BattleshipGameBoard toDomain(BattleshipGameBoardEntity entity) {
        BattleshipGameBoard gameBoardDomain = new BattleshipGameBoard();
        gameBoardDomain.setGameId(entity.getGameId());
        gameBoardDomain.setPlayerBoards(entity.getPlayerBoards().stream().map(this::playerBoardEntityToDomain).collect(Collectors.toList()));
        gameBoardDomain.setStartDate(entity.getStartDate());
        gameBoardDomain.setEndDate(entity.getEndDate());
        gameBoardDomain.setStatus(GameStatusDomain.valueOf(entity.getStatus().name()));
        gameBoardDomain.setCurrentPlayer(CurrentPlayerDomain.valueOf(entity.getCurrentTurn().name()));
        return gameBoardDomain;
    }

    public BattleshipGameBoardEntity domainToEntity(BattleshipGameBoard battleshipGameBoard) {
        BattleshipGameBoardEntity gameBoardDomain = new BattleshipGameBoardEntity();
        gameBoardDomain.setGameId(battleshipGameBoard.getGameId());
        gameBoardDomain.setPlayerBoards(battleshipGameBoard.getPlayerBoards().stream().map(this::playerBoardDomainToEntity).collect(Collectors.toList()));
        gameBoardDomain.setStartDate(battleshipGameBoard.getStartDate());
        gameBoardDomain.setEndDate(battleshipGameBoard.getEndDate());
        gameBoardDomain.setStatus(GameStatus.valueOf(battleshipGameBoard.getStatus().name()));
        gameBoardDomain.setCurrentTurn(CurrentTurn.valueOf(battleshipGameBoard.getCurrentPlayer().name()));
        return gameBoardDomain;
    }

    private PlayerBoardEntity playerBoardDomainToEntity(PlayerBoardDomain playerBoardDomain) {
        return new PlayerBoardEntity(playerBoardDomain.getPlayerName(),
                CurrentPlayerEntity.valueOf(playerBoardDomain.getCurrentPlayerDomain().name()),
                cellDomainToEntity(playerBoardDomain.getBoardCells()),
                PlayerBoardStatusEntity.valueOf(playerBoardDomain.getPlayerBoardStatus().name()));
    }

    private PlayerBoardDomain playerBoardEntityToDomain(PlayerBoardEntity entity) {
        return new PlayerBoardDomain(CurrentPlayerDomain.valueOf(entity.getPlayer().name()),
                entity.getPlayerName(),
                cellEntityToDomain(entity.getBoardCell()),
                PlayerBoardStatus.valueOf(entity.getPlayerBoardStatus().name()));
    }

    private BoardCellEntity[][] cellDomainToEntity(BoardCell[][] boardCells) {
        int rows = boardCells.length;
        int cols = boardCells[0].length;

        BoardCellEntity[][] boardCellsEntity = new BoardCellEntity[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                BoardCell entity = boardCells[i][j];
                BoardCellEntity domain = mapDomainToEntity(entity);
                boardCellsEntity[i][j] = domain;
            }
        }

        return boardCellsEntity;
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
        return BoardCellMapper.INSTANCE.entityBoardCellToDomain(entity);
    }

    private BoardCellEntity mapDomainToEntity(BoardCell cell) {
        return BoardCellMapper.INSTANCE.domainBoardCellToEntity(cell);
    }
}
