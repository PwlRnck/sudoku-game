package com.kodilla.sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuRow {

    List<SudokuElement> elements;

    public SudokuRow() {
        elements = new ArrayList<>();
        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            elements.add(new SudokuElement());
        }
    }

    public List<SudokuElement> getElements() {
        return elements;
    }
}
