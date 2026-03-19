package com.kodilla.tictactoe2.logic;


import java.util.Random;

public class TicTacToeLogic {
    private final char[][] board;
    private final int boardSize;
    private final int winCondition;
    private final char emptyChar = ' ';

    public TicTacToeLogic(int boardSize, int winCondition) {
        this.boardSize = boardSize;
        this.winCondition = winCondition;
        this.board = new char[boardSize][boardSize];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) board[i][j] = emptyChar;
        }
    }

    public boolean makeMove(int row, int col, char player) {
        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize || board[row][col] != emptyChar) {
            return false;
        }
        board[row][col] = player;
        return true;
    }

    public int[] makeComputerMove(char computerChar) {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(boardSize);
            col = random.nextInt(boardSize);
        } while (board[row][col] != emptyChar);

        board[row][col] = computerChar;
        return new int[]{row, col};
    }

    public boolean checkWin(char player) {
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                if (checkDirection(r, c, 1, 0, player) ||
                        checkDirection(r, c, 0, 1, player) ||
                        checkDirection(r, c, 1, 1, player) ||
                        checkDirection(r, c, 1, -1, player))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int col, int dRow, int dCol, char player) {
        int count = 0;
        for (int i = 0; i < winCondition; i++) {
            int r = row + i * dRow;
            int c = col + i * dCol;
            if (r >= 0 && r < boardSize && c >= 0 && c < boardSize && board[r][c] == player) {
                count++;
            } else {
                break;
            }
        }
        return count == winCondition;
    }

    public boolean isBoardFull() {
        for (char[] row : board) {
            for (char cell : row) if (cell == emptyChar) return false;
        }
        return true;
    }

    public char[][] getBoard() { return board; }
}
