package com.battleship.gameengine.ship.placement;

import com.battleship.engine.messaging.GameBoardCreator;
import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.GameCreateRequest;
import com.battleship.engine.model.ShipType;
import com.battleship.engine.model.enums.GameStatusDomain;
import com.battleship.engine.model.enums.PlayerBoardStatus;
import com.battleship.engine.repository.GameBoardRepository;
import com.battleship.gameengine.GameEngineApplication;
import com.battleship.rest.CellHumanLangConverterUtil;
import com.battleship.rest.model.CellPositionHumanLanguageApi;
import com.battleship.rest.model.ShipTypeApi;
import com.battleship.rest.model.request.ShipPlacementRequestApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@CucumberContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(classes = GameEngineApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ShipPlacementIntegrationTest {
    static MockHttpServletResponse latestResponse = null;
    static UUID gameLobbyId;
    private final String baseUrl = "/game-engine/v0/actions";
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected GameBoardCreator gameBoardCreator;
    @Autowired
    protected GameBoardRepository gameBoardRepository;
    @Autowired
    protected CellHumanLangConverterUtil cellHumanLangConverterUtil;
    private String playerName;

    public void createGameLobby(String player1Name, String player2Name) {
        gameLobbyId = UUID.randomUUID();
        gameBoardCreator.createGameBoard(new GameCreateRequest(gameLobbyId, player1Name, player2Name));
    }

    public void setPlayerNameIntoContext(String playerName) {
        this.playerName = playerName;
    }

    public void givenPlayerHasGivenShipOnHisBoard(String playerName, ShipType shipType, List<CellPositionHumanLanguageApi> cellPositions) {
        BattleshipGameBoard gameBoard = gameBoardRepository.findById(gameLobbyId);
        assertThat(gameBoard).isNotNull();

        var playerBoard = gameBoard.getPlayerBoardByPlayerName(playerName);
        assertThat(playerBoard).isNotNull();

        cellPositions.stream().map(it -> cellHumanLangConverterUtil.convert(it.getPosition())).forEach(
                cell -> {
                    ShipType savedShipType = playerBoard.getBoardCell(cell.getX(), cell.getY()).getShipInfo().getShipType();
                    assertThat(savedShipType).isEqualTo(shipType);
                });
    }

    public void placeShip(ShipTypeApi shipType, List<CellPositionHumanLanguageApi> cellPositions) throws Exception {
        var object = mockMvc.perform(post(baseUrl + "/ship-place")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(user(playerName).password("test").roles("USER").authorities(List.of(new SimpleGrantedAuthority("ROLE_PLAYER"), new SimpleGrantedAuthority("ROLE_USER"))))
                        .content(objectMapper.writeValueAsString(new ShipPlacementRequestApi(gameLobbyId, shipType, cellPositions))))
                .andReturn();

        latestResponse = object.getResponse();
    }

    public void playerReceivedError(String message, int status) throws UnsupportedEncodingException {
        assertThat(latestResponse).isNotNull();

        assertThat(latestResponse.getStatus()).isEqualTo(status);
        assertThat(latestResponse.getContentAsString()).contains(message);
    }

    public void playerPlacedAllShips() throws Exception {
        placeShip(ShipTypeApi.DESTROYER, List.of(cellPos("A1"), cellPos("A2")));
        placeShip(ShipTypeApi.SUBMARINE, List.of(cellPos("B1"), cellPos("B2"), cellPos("B3")));
        placeShip(ShipTypeApi.CRUISER, List.of(cellPos("C1"), cellPos("C2"), cellPos("C3")));
        placeShip(ShipTypeApi.BATTLESHIP, List.of(cellPos("D1"), cellPos("D2"), cellPos("D3"), cellPos("D4")));
        placeShip(ShipTypeApi.AIRCRAFT_CARRIER, List.of(cellPos("E1"), cellPos("E2"), cellPos("E3"), cellPos("E4"), cellPos("E5")));
    }

    public void verifyGameBoardStatusIs(GameStatusDomain gameStatusDomain) {
        BattleshipGameBoard gameBoard = gameBoardRepository.findById(gameLobbyId);
        assertThat(gameBoard.getStatus()).isEqualTo(gameStatusDomain);
    }

    public void verifyPlayersBoardStatusIs(String playerName, PlayerBoardStatus playerBoardStatus) {
        BattleshipGameBoard gameBoard = gameBoardRepository.findById(gameLobbyId);
        PlayerBoardStatus boardStatus = gameBoard.getPlayerBoardByPlayerName(playerName).getPlayerBoardStatus();
        assertThat(boardStatus).isEqualTo(playerBoardStatus);
    }

    private CellPositionHumanLanguageApi cellPos(String pos) {
        return new CellPositionHumanLanguageApi(pos);
    }

}