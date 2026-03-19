package com.kodilla.tictactoe2.logic;


import java.util.Scanner;


public class TicTacToeRunner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select Game Mode:");
        System.out.println("1. Classic (3x3, win with 3)");
        System.out.println("2. Big Board (10x10, win with 5)");
        int mode = scanner.nextInt();

        int size = (mode == 2) ? 10 : 3;
        int win = (mode == 2) ? 5 : 3;

        System.out.println("Play against: 1. Human, 2. Computer");
        int opponent = scanner.nextInt();

        TicTacToeLogic game = new TicTacToeLogic(size, win);
        char currentPlayer = 'X';
        boolean gameEnded = false;

        while (!gameEnded) {
            printBoard(game.getBoard());

            if (currentPlayer == 'O' && opponent == 2) {
                System.out.println("Computer is moving...");
                int[] move = game.makeComputerMove('O');
                System.out.println("Computer chose: " + move[0] + " " + move[1]);
            } else {
                System.out.println("Player " + currentPlayer + ", enter move (row col):");
                int r = scanner.nextInt();
                int c = scanner.nextInt();
                if (!game.makeMove(r, c, currentPlayer)) {
                    System.out.println("Invalid move, try again.");
                    continue;
                }
            }

            if (game.checkWin(currentPlayer)) {
                printBoard(game.getBoard());
                System.out.println("Player " + currentPlayer + " WINS!");
                gameEnded = true;
            } else if (game.isBoardFull()) {
                System.out.println("DRAW!");
                gameEnded = true;
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    private static void printBoard(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) System.out.print("[" + cell + "]");
            System.out.println();
        }
    }
}
