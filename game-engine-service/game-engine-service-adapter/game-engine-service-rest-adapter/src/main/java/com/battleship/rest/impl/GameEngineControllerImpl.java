package com.battleship.rest.impl;

import com.battleship.engine.engine.model.CellPosition;
import com.battleship.engine.model.ShipType;
import com.battleship.engine.model.request.ShipActionRequest;
import com.battleship.engine.model.request.ShipActionResponse;
import com.battleship.engine.model.request.ShipPlacementRequest;
import com.battleship.engine.service.AttackActionService;
import com.battleship.engine.service.ShipPlacementService;
import com.battleship.rest.GameEngineController;
import com.battleship.rest.model.CellPositionApi;
import com.battleship.rest.model.request.ShipAttackRequestApi;
import com.battleship.rest.model.request.ShipPlacementRequestApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GameEngineControllerImpl implements GameEngineController {

    private final ShipPlacementService shipPlacementService;
    private final AttackActionService attackActionService;

    @Override
    public ResponseEntity<ShipActionResponse> shipPlacement(HttpServletRequest request, ShipPlacementRequestApi shipPlacementRequestApi) {
        return ResponseEntity.ok(shipPlacementService.placeShip(request.getUserPrincipal().getName(), toShipPlacementDomainModel(shipPlacementRequestApi)));
    }

    @Override
    public ResponseEntity<ShipActionResponse> performAttack(HttpServletRequest request, ShipAttackRequestApi shipAttackRequestApi) {
        return ResponseEntity.ok(attackActionService.executeAction(request.getUserPrincipal().getName(), toShipAttackDomainModel(shipAttackRequestApi)));
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
