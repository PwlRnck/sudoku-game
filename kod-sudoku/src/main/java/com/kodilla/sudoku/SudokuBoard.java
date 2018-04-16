package com.kodilla.sudoku;

import java.util.*;

public class SudokuBoard extends Prototype {
    public static final int BOARD_SIZE = 9;
    public static final int BLOCK_SIZE = 3;
    public static final int BOARD_SIZE_BLOCKS = 3;
    List<SudokuRow> rows;
    private Block[][] blocks;

    public SudokuBoard() {
        rows = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            rows.add(new SudokuRow());
        }

        blocks = new Block[BOARD_SIZE_BLOCKS][BOARD_SIZE_BLOCKS];
        for (int i = 0; i < BOARD_SIZE_BLOCKS; i++) {
            for (int j = 0; j < BOARD_SIZE_BLOCKS; j++) {
                blocks[i][j] = new Block(i, j);
            }
        }
    }

    public void fill(String[] dataExtracted) {
        int boardX = Integer.parseInt(dataExtracted[0]) - 1;
        int boardY = Integer.parseInt(dataExtracted[1]) - 1;
        int boardValue = Integer.parseInt(dataExtracted[2]);

        this.getRows().get(boardX).getElements().get(boardY).setValue(boardValue);
    }

    public int countEmpty() {
        int c = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (getRows().get(i).getElements().get(j).getValue() < 0) {
                    c++;
                }
            }
        }
        return c;
    }

    public SudokuBoard deepCopy() throws CloneNotSupportedException {
        SudokuBoard clonedBoard = (SudokuBoard) super.clone();
        clonedBoard.rows = new ArrayList<>();

        for (SudokuRow row : rows) {
            SudokuRow clonedRow = new SudokuRow();
            clonedRow.elements = new ArrayList<>();

            for (SudokuElement element : row.getElements()) {
                SudokuElement clonedElement = new SudokuElement();
                clonedElement.setValue(element.getValue());
                clonedElement.valueSet = new HashSet<>();

                for (Integer value : element.getValueSet()) {
                    clonedElement.getValueSet().add(value);
                }
                clonedRow.getElements().add(clonedElement);
            }
            clonedBoard.getRows().add(clonedRow);
        }
        return clonedBoard;
    }


    public List<SudokuRow> getRows() {
        return rows;
    }

    public void setRows(List<SudokuRow> rows) {
        this.rows = rows;
    }

    public Block[][] getBlocks() {
        return blocks;
    }

    public String toString() {
        String output = "";

        for (int i = 0; i < BOARD_SIZE; i++) {
            output = output + "\n";
            if (i > 0) {
                if (i % BLOCK_SIZE == 0) {
                    output = output + "===|===|===||===|===|===||===|===|===\n";
                } else {
                    output = output + "---|---|---||---|---|---||---|---|---\n";
                }
            }
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (rows.get(i).getElements().get(j).getValue() > 0) {
                    if (j < BOARD_SIZE - 1) {
                        if ((j + 1) % BLOCK_SIZE == 0) {
                            output = output + " " + rows.get(i).getElements().get(j).getValue() + " ||";
                        } else {

                            output = output + " " + rows.get(i).getElements().get(j).getValue() + " |";
                        }
                    } else {
                        output = output + " " + rows.get(i).getElements().get(j).getValue();
                    }
                } else {
                    if (j < BOARD_SIZE - 1) {
                        if ((j + 1) % BLOCK_SIZE == 0) {
                            output = output + "   ||";
                        } else {
                            output = output + "   |";
                        }
                    }
                }
            }
        }
        return output;
    }

    public void randomFill(List<GameState> backtrack, SudokuBoard boardCopy) {
        SudokuElement element;
        Random generator = new Random();
        int index;
        int valueToFill = -1;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                element = this.getRows().get(i).getElements().get(j);
                if (element.getValue() > 0) {
                    continue;
                } else {
                    Set<Integer> values = element.getValueSet();
                    if (values.size() == 0) {
                        continue;
                    }
                    Iterator<Integer> valuesIterator = values.iterator();
                    index = generator.nextInt(values.size());
                    for (int k = 0; k <= index; k++) {
                        valueToFill = valuesIterator.next();
                    }
                    try {
                        backtrack.add(new GameState(boardCopy.deepCopy(), i, j, valueToFill));
                    } catch (CloneNotSupportedException e) {
                    }

                    element.setValue(valueToFill);
                    element.getValueSet().remove(valueToFill);
                    return;
                }
            }
        }
    }

    class Block {
        private List<List<Integer>> coordinates = new ArrayList<>();

        public Block(int x, int y) {
            for (int i = 0; i < BOARD_SIZE_BLOCKS; i++) {
                for (int j = 0; j < BOARD_SIZE_BLOCKS; j++) {
                    List<Integer> pair = new ArrayList<>();
                    pair.add(3 * x + i);
                    pair.add(3 * y + j);
                    coordinates.add(pair);
                }
            }
        }

        public List<List<Integer>> getCoordinates() {
            return coordinates;
        }
    }

}