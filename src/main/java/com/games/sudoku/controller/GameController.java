package com.games.sudoku.controller;

import com.games.sudoku.domain.BoardDto;
import com.games.sudoku.mapper.BoardMapper;
import com.kodilla.sudoku.SudokuBoard;
import com.kodilla.sudoku.SudokuGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/game")
public class GameController {

    @Autowired
    BoardMapper boardMapper;

    //@RequestMapping(method = RequestMethod.POST, value = "playGame", consumes = APPLICATION_JSON_VALUE)
    @PostMapping(value = "playGame")
    public BoardDto playGame(@RequestBody BoardDto boardDto) {
        SudokuGame game = new SudokuGame();
        SudokuBoard board = boardMapper.mapToBoard(boardDto);
        game.setTheBoard(board);
        game.resolveSudoku();
        return boardMapper.mapToBoardDto(game.getBoard());
    }
}
