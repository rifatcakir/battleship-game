package com.battleship.rest.utils;

import com.battleship.engine.rule.model.CellPosition;
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

    public String convert(int row, int column) {
        if (row < 0 || column < 0) {
            throw new IllegalArgumentException("Invalid coordinates");
        }
        char letter = (char) ('A' + row);
        int number = column + 1;
        return letter + Integer.toString(number);
    }
}
