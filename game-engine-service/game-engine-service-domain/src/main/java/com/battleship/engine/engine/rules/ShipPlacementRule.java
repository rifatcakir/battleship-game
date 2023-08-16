package com.battleship.engine.engine.rules;

import com.battleship.engine.engine.model.CellPosition;
import com.battleship.engine.engine.parameters.Parameter;
import com.battleship.engine.engine.parameters.ShipPlacementParameter;
import com.battleship.engine.model.BoardCell;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.ShipInfo;
import com.battleship.engine.model.ShipType;
import com.battleship.engine.model.enums.CellStateDomain;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class ShipPlacementRule implements Rule {
    @Override
    public void applyRule(Parameter param) {
        if (!(param instanceof ShipPlacementParameter)) return;
        var shipPlacementParameter = (ShipPlacementParameter) param;

        if (!verifyCellPositionsAreValid(shipPlacementParameter)) {
            throw new IllegalArgumentException("Cell positions are not valid!"); // TODO FIX
        }

        UUID shipGroupId = UUID.randomUUID();
        for (CellPosition position : shipPlacementParameter.getPositions()) {
            placeShipAtPosition(shipGroupId, position, shipPlacementParameter);
        }
    }

    private boolean verifyCellPositionsAreValid(ShipPlacementParameter param) {
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

    private void placeShipAtPosition(UUID shipGroupId, CellPosition position, ShipPlacementParameter parameters) {
        BoardCell[][] boardCell = parameters.getPlayerBoardDomain().getBoardCells();
        boardCell[position.getX()][position.getY()] = createNewCell(shipGroupId, parameters.getShipType());
    }

    private BoardCell createNewCell(UUID shipGroupId, ShipType shipType) {
        return new BoardCell(new ShipInfo(shipGroupId, shipType), CellStateDomain.SHIP);
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
