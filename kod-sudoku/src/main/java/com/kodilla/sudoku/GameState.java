package com.kodilla.sudoku;

public class GameState {
    private SudokuBoard theBoard;
    private int xCoordinate;
    private int yCoordinate;
    private int value;

    public GameState(SudokuBoard theBoard, int xCoordinate, int yCoordinate, int value) {
        try {
            this.theBoard = theBoard.deepCopy();
        } catch (CloneNotSupportedException e) {

        }
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.value = value;
    }

    public SudokuBoard getTheBoard() {
        return theBoard;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public int getValue() {
        return value;
    }
}
