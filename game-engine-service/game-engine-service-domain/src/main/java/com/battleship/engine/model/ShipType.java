package com.battleship.engine.model;

public enum ShipType {
    AIRCRAFT_CARRIER(5),
    BATTLESHIP(4),
    CRUISER(3),
    SUBMARINE(3),
    DESTROYER(2);
    private final int cellSize;

    ShipType(int cellSize) {
        this.cellSize = cellSize;
    }

    public int getSize() {
        return cellSize;
    }
}
