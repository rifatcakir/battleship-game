package com.battleship.gameengine.cucumber;

import com.battleship.engine.messaging.GameBoardCreator;
import com.battleship.engine.model.BattleshipGameBoard;
import com.battleship.engine.model.GameCreateRequest;
import com.battleship.engine.model.ShipType;
import com.battleship.engine.model.enums.GameStatusDomain;
import com.battleship.engine.model.enums.PlayerBoardStatus;
import com.battleship.engine.repository.GameBoardRepository;
import com.battleship.gameengine.cucumber.utils.MockMvcResultUtil;
import com.battleship.rest.CellHumanLangConverterUtil;
import com.battleship.rest.model.CellPositionHumanLanguageApi;
import com.battleship.rest.model.ShipTypeApi;
import com.battleship.rest.model.request.ShipAttackRequestApi;
import com.battleship.rest.model.request.ShipPlacementRequestApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class TestDataContextHolder {

    public static MockHttpServletResponse latestResponse = null;
    public static MvcResult latestResult = null;
    public static UUID gameLobbyId;
    public static String playerName;
    public static String baseUrl = "/game-engine/v0/actions";

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

    @Autowired
    protected MockMvcResultUtil mockMvcResultUtil;

    public void createGameLobby(String player1Name, String player2Name) {
        gameLobbyId = UUID.randomUUID();
        gameBoardCreator.createGameBoard(new GameCreateRequest(gameLobbyId, player1Name, player2Name));
    }

    public void setPlayerNameIntoContext(String userName) {
        playerName = userName;
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

    public void placeShip(ShipTypeApi shipType, String positions) throws Exception {
        latestResponse = mockMvc.perform(post(baseUrl + "/ship-place")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(user(playerName).password("test").roles("USER").authorities(List.of(new SimpleGrantedAuthority("ROLE_PLAYER"), new SimpleGrantedAuthority("ROLE_USER"))))
                        .content(objectMapper.writeValueAsString(new ShipPlacementRequestApi(gameLobbyId, shipType, toCellAPI(positions)))))
                .andReturn().getResponse();
    }

    public List<CellPositionHumanLanguageApi> toCellAPI(String positions) {
        return Arrays.stream(positions.replaceAll(" ", "").split(",")).map(CellPositionHumanLanguageApi::new).collect(Collectors.toList());
    }

    public void playerReceivedError(String message, int status) throws UnsupportedEncodingException {
        assertThat(latestResponse).isNotNull();

        assertThat(latestResponse.getStatus()).isEqualTo(status);
        assertThat(latestResponse.getContentAsString()).contains(message);
    }

    public void playerPlacedAllShips() throws Exception {
        placeShip(ShipTypeApi.DESTROYER, "A1, A2");
        placeShip(ShipTypeApi.SUBMARINE, "B1, B2, B3");
        placeShip(ShipTypeApi.CRUISER, "C1, C2, C3");
        placeShip(ShipTypeApi.BATTLESHIP, "D1, D2, D3, D4");
        placeShip(ShipTypeApi.AIRCRAFT_CARRIER, "E1, E2, E3, E4, E5");
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

    public void attackToAShip(UUID gameLobbyId, String cellPos) throws Exception {
        latestResult = mockMvc.perform(post(baseUrl + "/attack")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(user(playerName).password("test").roles("USER").authorities(List.of(new SimpleGrantedAuthority("ROLE_PLAYER"), new SimpleGrantedAuthority("ROLE_USER"))))
                        .content(objectMapper.writeValueAsString(new ShipAttackRequestApi(gameLobbyId, toCellAPI(cellPos).stream().findFirst().get()))))
                .andReturn();
        latestResponse = latestResult.getResponse();
    }
    private CellPositionHumanLanguageApi cellPos(String pos) {
        return new CellPositionHumanLanguageApi(pos);
    }

}
