package com.battleship.gameengine.unit;

import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.response.GameStatusResponse;
import com.battleship.engine.repository.GameBoardRepository;
import com.battleship.engine.service.impl.GameStatusServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.battleship.gameengine.unit.utils.Assertions.assertThat;
import static com.battleship.gameengine.unit.utils.MockData.mockBattleShipGameBoard;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GameStatusServiceTest {
    @Mock
    private GameBoardRepository gameBoardRepository;

    @InjectMocks
    private GameStatusServiceImpl gameStatusService;


    @Test
    public void testGameStatusRetrievedCorrectly() {
        BattleshipGameBoard mockGameBoard = mockBattleShipGameBoard();
        given(gameBoardRepository.findById(mockGameBoard.getGameId())).willReturn(mockGameBoard);

        String firstPlayerName = mockGameBoard.getPlayerBoards().stream().findFirst().get().getPlayerName();

        GameStatusResponse response = gameStatusService.getGameStatus(firstPlayerName, mockGameBoard.getGameId());

        verify(gameBoardRepository, times(1)).findById(mockGameBoard.getGameId());

        assertThat(response)
                .hasGivenGameId(mockGameBoard.getGameId())
                .hasGivenPlayer(mockGameBoard.getCurrentPlayer())
                .hasGivenGameStatus(mockGameBoard.getStatus())
                .hasGivenPlayerBoard(mockGameBoard.getPlayerBoardByPlayerName(firstPlayerName));
    }
}
