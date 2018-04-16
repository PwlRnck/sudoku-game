package com.kodilla.sudoku;

import java.util.Scanner;

public class RunSudoku {

    public static void main(String[] args) {

        boolean gameFinished = false;
        Scanner scanner = new Scanner(System.in);
        String answer;

        while (!gameFinished) {
            SudokuGame theGame = new SudokuGame();
            SudokuBoard theBoard = theGame.getBoard();
            InputData inputData = new InputData();

            System.out.printf("Hello. How would you like to enter the sudoku data? By keyboard (%s) or from file (%s)?",
                    FunctionKeys.KEYBOARD.key(), FunctionKeys.FILE.key());
            answer = scanner.nextLine();

            if (answer.equals(FunctionKeys.KEYBOARD.key())) {
                System.out.println("Please enter sudoku numbers in the form: \"x,y,z\", where:" +
                        "\nx - number of sudoku board row [1-9];" +
                        "\ny - number of sudoku board column [1-9];" +
                        "\nz - number to be entered to field (x,y) of the board [1-9]." +
                        "\nOr else \"" + FunctionKeys.END.key() + "\" to finish the input and move to solving the sudoku" +
                        "\nor \"" + FunctionKeys.QUIT.key() + "\" if you want to quit");

                theBoard = inputData.keyboardInput(theBoard);
            } else {
                theBoard = inputData.fileInput(theBoard);
            }
            System.out.println("\nThis is the initial sudoku board:\n" + theBoard.toString());
            theGame.resolveSudoku();
            gameFinished = theGame.finalQuestion();
        }
        System.exit(0);
    }
}