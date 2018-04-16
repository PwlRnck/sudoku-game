package com.games.sudoku.mapper;

import com.games.sudoku.domain.BoardDto;
import com.kodilla.sudoku.SudokuBoard;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper {

    public SudokuBoard mapToBoard(BoardDto boardDto) {
        SudokuBoard board = new SudokuBoard();
        board.setRows(boardDto.getRows());
        return board;
    }

    public BoardDto mapToBoardDto(SudokuBoard board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setRows(board.getRows());
        return boardDto;
    }


}
