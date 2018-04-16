package com.kodilla.sudoku;


import java.util.*;

public class SudokuGame {

    private SudokuBoard theBoard;
    private SudokuBoard boardCopy;
    private List<GameState> backtrack;
    private int eventCounter;
    private Scanner scanner = new Scanner(System.in);

    public SudokuGame() {
        theBoard = new SudokuBoard();
        backtrack = new ArrayList<>();
    }

    public void setTheBoard(SudokuBoard theBoard) {
        this.theBoard = theBoard;
    }

    public SudokuBoard getBoard() {
        return theBoard;
    }

    public void resolveSudoku() {
        int errorCode;
        int iterationCounter = 0;

        do {
            eventCounter = 0;
            try {
                boardCopy = theBoard.deepCopy();
            } catch (CloneNotSupportedException e) {
            }

            errorCode = resolveIteration();

            if (errorCode == 1) {
                if (backtrack.size() > 0) {
                    restoreTheBoard();
                    eventCounter = 1;
                } else {
                    System.out.println("This sudoku has a fatal error and cannot be solved.");
                    boolean decision = finalQuestion();
                    if (decision) {
                        RunSudoku.main(null);
                    } else
                        System.exit(0);
                }
            } else if (eventCounter == 0 && theBoard.countEmpty() > 0) {
                int initialCount = theBoard.countEmpty();
                theBoard.randomFill(backtrack, boardCopy);
                if (theBoard.countEmpty() < initialCount) {
                    eventCounter = 1;
                } else {
                    eventCounter = 0;
                }
            }
            iterationCounter++;
        } while (eventCounter > 0 && theBoard.countEmpty() > 0);

        System.out.println("\nThis is the solution reached after " + iterationCounter + " iterations:\n" + theBoard.toString());

    }

    private int resolveIteration() {
        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            for (int j = 0; j < SudokuBoard.BOARD_SIZE; j++) {
                Set<Integer> filledInNumbers = new HashSet<>();
                Set<Integer> availableNumbers = new HashSet<>();

                SudokuElement element = theBoard.getRows().get(i).getElements().get(j);

                if (element.getValue() > 0) {
                    continue;
                } else {
                    checkNumbers(i, j, theBoard.getBlocks()[i / SudokuBoard.BLOCK_SIZE][j / SudokuBoard.BLOCK_SIZE], filledInNumbers, availableNumbers);
                }

                for (Integer number : filledInNumbers) {
                    element.getValueSet().remove(number);
                }

                if (element.getValueSet().size() == 0) {
                    return 1;
                }

                if (element.getValueSet().size() == 1) {
                    element.setValue(element.getValueSet().iterator().next());
                    element.getValueSet().remove(element.getValueSet().iterator().next());
                    eventCounter++;
                } else {
                    for (Integer number : element.getValueSet()) {
                        if (!availableNumbers.contains(number)) {
                            element.setValue(number);
                            element.getValueSet().remove(number);
                            eventCounter++;
                            break;
                        }
                    }
                }
            }
        }
        return 0;
    }

    public boolean finalQuestion() {
        System.out.println("\nWould you like to play again? (y/n)");
        String answer = scanner.nextLine();
        if (answer.equals("y")) {
            return false;
        } else {
            return true;
        }
    }

    private void checkNumbers(int i, int j, SudokuBoard.Block block, Set<Integer> filledInNumbers, Set<Integer> availableNumbers) {
        for (int k = 0; k < 9; k++) {
            filledInNumbers.add(theBoard.getRows().get(i).getElements().get(k).getValue());
            filledInNumbers.add(theBoard.getRows().get(k).getElements().get(j).getValue());
            filledInNumbers.add(theBoard.getRows().get(block.getCoordinates().get(k).get(0))
                    .getElements().get(block.getCoordinates().get(k).get(1)).getValue());

            availableNumbers.addAll(theBoard.getRows().get(i).getElements().get(k).getValueSet());
            availableNumbers.addAll(theBoard.getRows().get(k).getElements().get(j).getValueSet());
            availableNumbers.addAll(theBoard.getRows().get(block.getCoordinates().get(k).get(0))
                    .getElements().get(block.getCoordinates().get(k).get(1)).getValueSet());
        }
    }

    private void restoreTheBoard() {
        int backtrackIndex = backtrack.size() - 1;
        int backtrackXCoord = backtrack.get(backtrackIndex).getxCoordinate();
        int backtrackYCoord = backtrack.get(backtrackIndex).getyCoordinate();
        int valueToRemove = backtrack.get(backtrackIndex).getValue();
        theBoard = backtrack.get(backtrackIndex).getTheBoard();
        theBoard.getRows().get(backtrackXCoord).getElements().get(backtrackYCoord).getValueSet().remove(valueToRemove);

        if (backtrack.size() >= 1) {
            backtrack.remove(backtrack.size() - 1);
        }
    }

}