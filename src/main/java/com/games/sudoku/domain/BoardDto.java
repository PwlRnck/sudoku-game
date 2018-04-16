package com.games.sudoku.domain;

import com.kodilla.sudoku.SudokuRow;

import java.util.ArrayList;
import java.util.List;

public class BoardDto {

    public static final int BOARD_SIZE = 9;
    List<SudokuRow> rows;

    public BoardDto() {
        rows = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            rows.add(new SudokuRow());
        }
    }

    public List<SudokuRow> getRows() {
        return rows;
    }

    public void setRows(List<SudokuRow> rows) {
        this.rows = rows;
    }
}