package com.kodilla.sudoku;

import java.util.List;
import java.util.Scanner;

public class InputData {
    private static final int SINGLE_ENTRY_LENGTH = 3;

    public SudokuBoard keyboardInput(SudokuBoard theBoard) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPlease enter the data:");
        String data;

        do {
            data = scanner.nextLine();
            keyboardEntry(theBoard, data);
        } while (!data.equals(FunctionKeys.END.key()));

        return theBoard;
    }

    public SudokuBoard fileInput(SudokuBoard theBoard) {
        FileReader fileReader = new FileReader();

        try {
            List<String> data = fileReader.readFile();
            String[] dataExtracted;
            int counter = 0;

            for (String line : data) {
                counter++;
                line = line.replaceAll("\\D+", ",");
                dataExtracted = line.split("\\D");

                confirmData(dataExtracted, theBoard, counter);
                theBoard.fill(dataExtracted);
            }
        } catch (FileReaderException e) {
            System.out.println("File not found.");
        }
        return theBoard;
    }

    private SudokuBoard keyboardEntry(SudokuBoard theBoard, String data) {
        return entry(theBoard, data);
    }

    private SudokuBoard keyboardEntry(SudokuBoard theBoard) {
        Scanner scanner = new Scanner(System.in);
        String data = scanner.nextLine();

        return entry(theBoard, data);
    }

    private SudokuBoard entry(SudokuBoard theBoard, String data) {
        String[] dataExtracted;

        if (data.equals(FunctionKeys.QUIT.key())) {
            System.exit(0);
        } else if (data.equals(FunctionKeys.END.key())) {
            return theBoard;
        } else {
            data = data.replaceAll("\\D+", ",");
            dataExtracted = data.split("\\D");
            confirmData(dataExtracted, theBoard);
            theBoard.fill(dataExtracted);
        }
        return theBoard;
    }


    private void confirmData(String[] dataExtracted, SudokuBoard theBoard) {
        Scanner scanner = new Scanner(System.in);

        if (dataExtracted.length != SINGLE_ENTRY_LENGTH) {
            System.out.printf("The entered values%n%s%nare not correct. Please try again (%s) or quit (%s)",
                    dataExtracted.toString(), FunctionKeys.AGAIN.key(), FunctionKeys.QUIT.key());
            String decision = scanner.nextLine();
            if (decision.equals(FunctionKeys.AGAIN.key())) {
                keyboardInput(theBoard);
            } else
                System.exit(0);
        }
    }

    private void confirmData(String[] dataExtracted, SudokuBoard theBoard, int counter) {
        Scanner scanner = new Scanner(System.in);

        if (dataExtracted.length != SINGLE_ENTRY_LENGTH) {
            System.out.printf("The entered values%n%s%nin line %d are not correct. Please enter correct values (%s) or quit (%s)",
                    dataExtracted.toString(), counter, FunctionKeys.AGAIN.key(), FunctionKeys.QUIT.key());
            String decision = scanner.nextLine();
            if (decision.equals(FunctionKeys.AGAIN.key())) {
                keyboardEntry(theBoard);
            } else
                System.exit(0);
        }
    }
}
