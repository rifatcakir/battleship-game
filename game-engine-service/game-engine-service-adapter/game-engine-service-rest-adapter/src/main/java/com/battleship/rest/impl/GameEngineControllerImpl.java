package com.battleship.rest.impl;

import com.battleship.engine.engine.model.CellPosition;
import com.battleship.engine.model.ShipType;
import com.battleship.engine.model.request.ShipActionRequest;
import com.battleship.engine.model.request.ShipPlacementRequest;
import com.battleship.engine.model.response.GameStatusResponse;
import com.battleship.engine.model.response.ShipActionResponse;
import com.battleship.engine.service.AttackActionService;
import com.battleship.engine.service.GameStatusService;
import com.battleship.engine.service.ShipPlacementService;
import com.battleship.rest.GameEngineController;
import com.battleship.rest.model.CellPositionApi;
import com.battleship.rest.model.request.ShipAttackRequestApi;
import com.battleship.rest.model.request.ShipPlacementRequestApi;
import com.battleship.rest.model.response.GameStatusApiResponse;
import com.battleship.rest.model.response.ShipActionAPIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GameEngineControllerImpl implements GameEngineController {

    private final ShipPlacementService shipPlacementService;
    private final AttackActionService attackActionService;
    private final GameStatusService gameStatusService;

    @Override
    public String getTest() {
        return "HELLO";
    }

    @Override
    public ResponseEntity<ShipActionAPIResponse> shipPlacement(HttpServletRequest request, ShipPlacementRequestApi shipPlacementRequestApi) {
        return ResponseEntity.ok(toAPIModel(shipPlacementService.placeShip(request.getUserPrincipal().getName(), toShipPlacementDomainModel(shipPlacementRequestApi))));
    }

    @Override
    public ResponseEntity<ShipActionAPIResponse> performAttack(HttpServletRequest request, ShipAttackRequestApi shipAttackRequestApi) {
        return ResponseEntity.ok(toAPIModel(attackActionService.executeAction(request.getUserPrincipal().getName(), toShipAttackDomainModel(shipAttackRequestApi))));
    }

    @Override
    public ResponseEntity<GameStatusApiResponse> getCurrentGameStatus(HttpServletRequest request, UUID gameLobbyId) {
        return ResponseEntity.ok(toAPIModel(gameStatusService.getGameStatus(request.getUserPrincipal().getName(), gameLobbyId)));
    }

    private GameStatusApiResponse toAPIModel(GameStatusResponse gameStatus) {
        return new GameStatusApiResponse(gameStatus.getGameId(), gameStatus.getPlayerBoard(), gameStatus.getCurrentPlayer(), gameStatus.getStatus());
    }

    private ShipActionAPIResponse toAPIModel(ShipActionResponse response) {
        return new ShipActionAPIResponse(response.getGameId(), response.getPlayerBoard(), response.getCurrentPlayer(), response.getStatus(), response.getActionStatus());
    }

    private ShipActionRequest toShipAttackDomainModel(ShipAttackRequestApi req) {
        return new ShipActionRequest(req.getGameLobbyId(), new CellPosition(req.getCellPosition().getX(), req.getCellPosition().getY()));
    }

    private ShipPlacementRequest toShipPlacementDomainModel(ShipPlacementRequestApi request) {
        return new ShipPlacementRequest(
                request.getGameLobbyId(),
                ShipType.valueOf(request.getShipType().name()),
                toCellPositionDomain(request.getCellPositions())
        );
    }

    private List<CellPosition> toCellPositionDomain(List<CellPositionApi> cellPositions) {
        return cellPositions.stream().map(it -> new CellPosition(it.getX(), it.getY())).collect(Collectors.toList());
    }
}
