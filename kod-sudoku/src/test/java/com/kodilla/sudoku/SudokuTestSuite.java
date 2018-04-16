package com.kodilla.sudoku;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class SudokuTestSuite {

    @Rule
    public final TextFromStandardInputStream systemInMock
            = emptyStandardInputStream();

    @Test
    public void testDeepCopy() {
        //Given
        SudokuBoard originalBoard = new SudokuBoard();
        int testValue1 = 1;
        int testValue2 = 5;
        int testValue3 = 9;
        final int TEST_INDEX_1 = 0;
        final int TEST_INDEX_2 = 4;
        final int TEST_INDEX_3 = 8;

        originalBoard.getRows().get(TEST_INDEX_1).getElements().get(TEST_INDEX_1).setValue(testValue1);
        originalBoard.getRows().get(TEST_INDEX_2).getElements().get(TEST_INDEX_2).setValue(testValue2);
        originalBoard.getRows().get(TEST_INDEX_3).getElements().get(TEST_INDEX_3).setValue(testValue3);
        //When
        SudokuBoard copiedBoard = new SudokuBoard();
        try {
            copiedBoard = originalBoard.deepCopy();
        } catch(CloneNotSupportedException e) {}
        originalBoard.getRows().get(TEST_INDEX_1).getElements().get(TEST_INDEX_1).setValue(testValue1 + 1);
        originalBoard.getRows().get(TEST_INDEX_2).getElements().get(TEST_INDEX_2).setValue(testValue2 + 1);
        originalBoard.getRows().get(TEST_INDEX_3).getElements().get(TEST_INDEX_3).setValue(testValue3 + 1);
        //Then
        for(int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            for(int j = 0; j < SudokuBoard.BOARD_SIZE; j++) {
                if(i == TEST_INDEX_1 && j == TEST_INDEX_1) {
                    assertEquals(copiedBoard.getRows().get(i).getElements().get(j).getValue(),testValue1);
                } else if(i == TEST_INDEX_2 && j == TEST_INDEX_2) {
                    assertEquals(copiedBoard.getRows().get(i).getElements().get(j).getValue(),testValue2);
                } else if(i == TEST_INDEX_3 && j == TEST_INDEX_3) {
                    assertEquals(copiedBoard.getRows().get(i).getElements().get(j).getValue(), testValue3);
                } else
                    assertEquals(copiedBoard.getRows().get(i).getElements().get(j).getValue(), copiedBoard.getRows().get(i).getElements().get(j).getValue());
            }
        }
    }

    @Test
    public void testFill() {
        SudokuBoard theBoard = new SudokuBoard();
        String testValue1 = "1";
        String testValue2 = "2";
        String testValue3 = "3";
        String[] entry = new String[] {testValue1, testValue2, testValue3};

        //When
        theBoard.fill(entry);

        //Then
        assertEquals(theBoard.getRows().get(Integer.parseInt(testValue1)-1).getElements().get(Integer.parseInt(testValue2)-1).getValue(),Integer.parseInt(testValue3));
        }

    @Test
    public void testRandomFill() {
        //Given
        SudokuBoard theBoard = new SudokuBoard();
        SudokuBoard boardCopy = new SudokuBoard();
        List<GameState> backtrack = new ArrayList<>();
        final int TEST_INDEX_1 = 0;
        //When
        theBoard.randomFill(backtrack, boardCopy);
        int setValue = theBoard.getRows().get(TEST_INDEX_1).getElements().get(TEST_INDEX_1).getValue();
        Set<Integer> valuesSet = theBoard.getRows().get(TEST_INDEX_1).getElements().get(TEST_INDEX_1).getValueSet();
        //Then
        assertTrue(setValue > 0);
        assertTrue(!valuesSet.contains(setValue));
    }

    @Test
    public void testKeyboardInput() {
        //Given
        InputData inputData = new InputData();
        SudokuBoard theBoard = new SudokuBoard();
        final int TEST_INDEX_1x = 0;
        final int TEST_INDEX_1y = 0;
        final int TEST_INDEX_2x = 0;
        final int TEST_INDEX_2y = 1;
        final int TEST_INDEX_3x = 4;
        final int TEST_INDEX_3y = 4;
        final int OFFSET = 1;
        final int TEST_VALUE_1 = 1;
        final int TEST_VALUE_2 = 2;
        final int TEST_VALUE_3 = 9;

        systemInMock.provideLines(Integer.toString(TEST_INDEX_1x + OFFSET) + "," + Integer.toString(TEST_INDEX_1y + OFFSET) + "," + TEST_VALUE_1,
                Integer.toString(TEST_INDEX_2x + OFFSET) + "," + Integer.toString(TEST_INDEX_2y + OFFSET) + "," + TEST_VALUE_2,
                Integer.toString(TEST_INDEX_3x + OFFSET) + "," + Integer.toString(TEST_INDEX_3y + OFFSET) + "," + TEST_VALUE_3,
                FunctionKeys.END.key());

        //When
        theBoard = inputData.keyboardInput(theBoard);

        //Then
        assertEquals(TEST_VALUE_1,theBoard.getRows().get(TEST_INDEX_1x).getElements().get(TEST_INDEX_1y).getValue());
        assertEquals(TEST_VALUE_2,theBoard.getRows().get(TEST_INDEX_2x).getElements().get(TEST_INDEX_2y).getValue());
        assertEquals(TEST_VALUE_3,theBoard.getRows().get(TEST_INDEX_3x).getElements().get(TEST_INDEX_3y).getValue());
    }

}
