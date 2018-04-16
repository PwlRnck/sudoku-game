package com.kodilla.sudoku;

import java.util.HashSet;
import java.util.Set;

public class SudokuElement {
    public static int EMPTY = -1;
    Set<Integer> valueSet;
    private int value;

    public SudokuElement() {
        value = EMPTY;
        valueSet = new HashSet<>();
        for (int i = 1; i <= SudokuBoard.BOARD_SIZE; i++) {
            valueSet.add(i);
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Set<Integer> getValueSet() {
        return valueSet;
    }

}
