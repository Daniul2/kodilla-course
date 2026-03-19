package com.kodilla.tictactoe2;

import com.kodilla.tictactoe2.logic.TicTacToeLogic;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;



public class TicTacToeFX extends Application {
    private TicTacToeLogic game = new TicTacToeLogic(3, 3);
    private Button[][] buttons = new Button[3][3];
    private char currentPlayer = 'X';

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                Button btn = new Button("");
                btn.setPrefSize(100, 100);
                btn.setStyle("-fx-font-size: 2em;");
                int finalR = r;
                int finalC = c;

                btn.setOnAction(e -> handleMove(finalR, finalC, btn));

                buttons[r][c] = btn;
                grid.add(btn, c, r);
            }
        }

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setTitle("Tic Tac Toe - Kodilla");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleMove(int r, int c, Button btn) {
        if (game.makeMove(r, c, currentPlayer)) {
            btn.setText(String.valueOf(currentPlayer));

            if (game.checkWin(currentPlayer)) {
                showAlert("Winner!", "Player " + currentPlayer + " wins!");
                resetGame();
            } else if (game.isBoardFull()) {
                showAlert("Draw!", "No more moves!");
                resetGame();
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void resetGame() {
        game = new TicTacToeLogic(3, 3);
        for (Button[] row : buttons) {
            for (Button btn : row) btn.setText("");
        }
        currentPlayer = 'X';
    }

    public static void main(String[] args) {
        launch(args);
    }
}
