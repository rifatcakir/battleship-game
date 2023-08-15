package com.battleship.engine.engine.rule.rules;

import com.battleship.engine.engine.rule.model.CellPosition;
import com.battleship.engine.engine.rule.parameters.Parameter;
import com.battleship.engine.engine.rule.parameters.ShipPlacementParameters;
import com.battleship.engine.model.BoardCell;
import com.battleship.engine.model.PlayerBoardDomain;
import com.battleship.engine.model.ShipInfo;
import com.battleship.engine.model.ShipType;
import com.battleship.engine.model.enums.CellStateDomain;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Getter
@Setter
@Component
public class ShipPlacementRule implements Rule {

    private PlayerBoardDomain playerBoardDomain;
    private ShipType shipType;
    private List<CellPosition> positions;


    @Override
    public void applyRule(Parameter param) {
        if (!(param instanceof ShipPlacementParameters)) return;

        initWithParams((ShipPlacementParameters) param);

        if (!verifyCellPositionsAreValid()) {
            throw new IllegalArgumentException("Cell positions are not valid!");
        }

        UUID shipGroupId = UUID.randomUUID();
        for (CellPosition position : positions) {
            placeShipAtPosition(shipGroupId, position);
        }
    }

    private void placeShipAtPosition(UUID shipGroupId, CellPosition position) {
        BoardCell[][] boardCell = playerBoardDomain.getBoardCells();
        boardCell[position.getX()][position.getY()] = createNewCell(shipGroupId);
    }

    private BoardCell createNewCell(UUID shipGroupId) {
        return new BoardCell(new ShipInfo(shipGroupId, shipType), CellStateDomain.SHIP);
    }

    private boolean verifyCellPositionsAreValid() {
        if (positions.size() != shipType.getSize()) return false;

        List<Integer> xPositions = positions.stream().map(CellPosition::getX).collect(Collectors.toList());
        List<Integer> yPositions = positions.stream().map(CellPosition::getY).collect(Collectors.toList());

        return isVertical(xPositions, yPositions) || isHorizontal(xPositions, yPositions);
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

    private void initWithParams(ShipPlacementParameters parameters) {
        playerBoardDomain = parameters.getPlayerBoardDomain();
        shipType = parameters.getShipType();
        positions = parameters.getPositions();
    }
}
