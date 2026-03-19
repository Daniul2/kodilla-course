package com.kodilla.tictactoe2;

import com.kodilla.tictactoe2.logic.TicTacToeLogic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TicTacToeTestSuite {

    @Test
    void testPlayerXWinsHorizontal() {
        // Given
        TicTacToeLogic game = new TicTacToeLogic(3, 3);
        // When
        game.makeMove(0, 0, 'X');
        game.makeMove(0, 1, 'X');
        game.makeMove(0, 2, 'X');
        assertTrue(game.checkWin('X'));
    }

    @Test
    void testMoveOnOccupiedField() {
        TicTacToeLogic game = new TicTacToeLogic(3, 3);
        game.makeMove(1, 1, 'X');
        boolean result = game.makeMove(1, 1, 'O');
        assertFalse(result);
    }

    @Test
    void testDrawCondition() {
        TicTacToeLogic game = new TicTacToeLogic(3, 3);
        assertFalse(game.checkWin('X'));
        assertFalse(game.checkWin('O'));
    }
}
