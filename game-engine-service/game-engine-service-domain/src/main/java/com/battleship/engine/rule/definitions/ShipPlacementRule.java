package com.battleship.engine.rule.definitions;

import com.battleship.engine.exception.InvalidGameAction;
import com.battleship.engine.model.BoardCell;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.ShipInfo;
import com.battleship.engine.model.ShipType;
import com.battleship.engine.model.enums.CellStateDomain;
import com.battleship.engine.model.enums.PlayerBoardStatus;
import com.battleship.engine.rule.model.CellPosition;
import com.battleship.engine.rule.parameters.Parameter;
import com.battleship.engine.rule.parameters.PlaceShip;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class ShipPlacementRule implements Rule {

    private final Integer MAX_SHIP_PLACE_LIMIT = 5;
    private final boolean SAME_SHIP_USAGE_ALLOWED = false;

    @Override
    public void applyRule(Parameter param) {
        if (!(param instanceof PlaceShip)) return;
        var shipPlacementParameter = (PlaceShip) param;

        shipPlacementPreProcess(shipPlacementParameter);
        shipPlacementProcess(shipPlacementParameter);
        shipPlacementPostProcess(shipPlacementParameter);
    }

    private void shipPlacementPostProcess(PlaceShip placeShip) {
        var playerBoard = placeShip.getPlayerBoardDomain();
        if (shipPlacementCompleted(playerBoard.getBoardCells())) {
            playerBoard.setPlayerBoardStatus(PlayerBoardStatus.ONGOING);
        }
    }

    private void shipPlacementProcess(PlaceShip param) {
        UUID shipGroupId = UUID.randomUUID();
        for (CellPosition position : param.getPositions()) {
            placeShipAtPosition(shipGroupId, position, param);
        }
    }

    private void shipPlacementPreProcess(PlaceShip placeShip) {
        if (placeShip.getPlayerBoardDomain().getPlayerBoardStatus() != PlayerBoardStatus.SHIP_PLACEMENT) {
            throw new InvalidGameAction("Ship placement not allowed!");
        }

        if (SAME_SHIP_USAGE_ALLOWED || !verifyShipNotPlacedBefore(placeShip.getShipType(), placeShip.getPlayerBoardDomain())) {
            throw new InvalidGameAction(String.format("Given ship is already placed [%s]", placeShip.getShipType().name()));
        }

        if (!verifyCellPositionsAreValid(placeShip)) {
            throw new InvalidGameAction("Given ship position is not valid");
        }
    }

    private boolean verifyShipNotPlacedBefore(ShipType shipType, PlayerBoardDomain playerBoardDomain) {
        return Arrays.stream(playerBoardDomain.getBoardCells())
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .noneMatch(it -> it.getShipInfo().getShipType() == shipType);
    }

    private boolean shipPlacementCompleted(BoardCell[][] boardCells) {
        long uniqueShipCounts =
                Arrays.stream(boardCells)
                        .flatMap(Arrays::stream)
                        .filter(Objects::nonNull)
                        .filter(it -> it.getShipInfo() != null)
                        .map(it -> it.getShipInfo().getShipGroupId())
                        .distinct().count();

        return uniqueShipCounts == MAX_SHIP_PLACE_LIMIT;
    }

    private boolean verifyCellPositionsAreValid(PlaceShip param) {
        return isCellSizeValid(param.getPositions().size(), param.getShipType().getSize())
                && isCellPositionsAvailable(param.getPlayerBoardDomain(), param.getPositions())
                && isCellPositionsValid(param.getPositions());
    }

    private boolean isCellPositionsValid(List<CellPosition> positions) {
        List<Integer> xPositions = positions.stream().map(CellPosition::getX).collect(Collectors.toList());
        List<Integer> yPositions = positions.stream().map(CellPosition::getY).collect(Collectors.toList());

        return isVertical(xPositions, yPositions) || isHorizontal(xPositions, yPositions);
    }

    private boolean isCellPositionsAvailable(PlayerBoardDomain playerBoardDomain, List<CellPosition> positions) {
        return positions.stream().noneMatch(it -> playerBoardDomain.getBoardCell(it.getX(), it.getY()) != null);
    }

    private boolean isCellSizeValid(int cellSize, int shipSize) {
        return cellSize == shipSize;
    }

    private void placeShipAtPosition(UUID shipGroupId, CellPosition position, PlaceShip parameters) {
        BoardCell[][] boardCell = parameters.getPlayerBoardDomain().getBoardCells();
        boardCell[position.getX()][position.getY()] = createNewCell(shipGroupId, parameters.getShipType());
    }

    private BoardCell createNewCell(UUID shipGroupId, ShipType shipType) {
        return new BoardCell(new ShipInfo(shipGroupId, shipType), CellStateDomain.SHIP, null);
    }

    private boolean isHorizontal(List<Integer> xPositions, List<Integer> yPositions) {
        return new HashSet<>(yPositions).size() == 1
                && verifyIncrementsByOne(xPositions);
    }

    private boolean isVertical(List<Integer> xPositions, List<Integer> yPositions) {
        return new HashSet<>(xPositions).size() == 1
                && verifyIncrementsByOne(yPositions);
    }

    private boolean verifyIncrementsByOne(List<Integer> integers) {
        Integer min = Collections.min(integers);
        for (int x = min; x < min + integers.size(); x++) {
            if (!integers.contains(x)) return false;
        }
        return true;
    }
}
