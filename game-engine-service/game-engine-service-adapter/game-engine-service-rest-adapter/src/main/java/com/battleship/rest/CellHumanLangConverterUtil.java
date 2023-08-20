package com.battleship.rest;

import com.battleship.engine.engine.model.CellPosition;
import org.springframework.stereotype.Component;

@Component
public class CellHumanLangConverterUtil {

    public CellPosition convert(String input) {
        input = input.replace(" ", "");
        if (input == null || input.length() < 2) {
            throw new IllegalArgumentException("Invalid input");
        }

        char letter = Character.toUpperCase(input.charAt(0));
        int column = Integer.parseInt(input.substring(1));
        int row = letter - 'A';
        return new CellPosition(row, column);
    }
}
