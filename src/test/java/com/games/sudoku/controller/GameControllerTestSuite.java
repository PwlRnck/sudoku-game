package com.games.sudoku.controller;

import com.games.sudoku.domain.BoardDto;
import com.games.sudoku.mapper.BoardMapper;
import com.google.gson.Gson;
import com.kodilla.sudoku.SudokuBoard;
import com.kodilla.sudoku.SudokuGame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardMapper boardMapper;

    @MockBean
    private SudokuGame game;

    @Test
    public void testPlayGame() throws Exception {
        //Given
        BoardDto boardDto = new BoardDto();
        final int TEST_INDEX1x = 0;
        final int TEST_INDEX1y = 1;
        final int TEST_INDEX2x = 8;
        final int TEST_INDEX2y = 7;
        final int TEST_VALUE1 = 2;
        final int TEST_VALUE2 = 3;

        boardDto.getRows().get(TEST_INDEX1x).getElements().get(TEST_INDEX1y).setValue(TEST_VALUE1);
        boardDto.getRows().get(TEST_INDEX2x).getElements().get(TEST_INDEX2y).setValue(TEST_VALUE2);

        when(boardMapper.mapToBoard(any())).thenReturn(new SudokuBoard());
        doNothing().when(game).setTheBoard(any());
        doNothing().when(game).resolveSudoku();
        when(boardMapper.mapToBoardDto(any())).thenReturn(boardDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(boardDto);

        String test1 = String.format("$.rows[%d].elements[%d].value", TEST_INDEX1x, TEST_INDEX1y);
        String test2 = String.format("$.rows[%d].elements[%d].value", TEST_INDEX2x, TEST_INDEX2y);

        //When&Then
        mockMvc.perform(post("/v1/game/playGame")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath(test1, is(TEST_VALUE1)))
                .andExpect(jsonPath(test2, is(TEST_VALUE2)));
    }

}
