package com.example.sudoku;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class HelloController {

    @FXML private GridPane grid;
    @FXML private Button btnSolve, btnClear, btnNew, btnSave, btnExit;

    private TextField[][] cells = new TextField[9][9];

    @FXML
    public void initialize() {
        createGrid();

        if (GameState.hasSavedGame() && GameState.loadFromFile()) {
            loadSavedGame();
        } else {
            startNewGame();
        }

        btnSolve.setOnAction(e -> solveSudoku());
        btnClear.setOnAction(e -> clearGrid());
        btnNew.setOnAction(e -> startNewGame());
        btnSave.setOnAction(e -> saveGame());
        btnExit.setOnAction(e -> goToMenu());
    }

    private void createGrid() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                TextField tf = new TextField();
                tf.setPrefSize(50, 50);
                tf.setStyle("-fx-font-size:18; -fx-alignment:center;");

                int row = r, col = c;
                tf.textProperty().addListener((obs, o, n) -> {
                    if (!n.matches("[1-9]?")) tf.setText(o);
                });

                cells[r][c] = tf;
                grid.add(tf, c, r);
            }
        }
    }

    private void startNewGame() {
        int[][] puzzle = {
                {5,3,0,0,7,0,0,0,0},
                {6,0,0,1,9,5,0,0,0},
                {0,9,8,0,0,0,0,6,0},
                {8,0,0,0,6,0,0,0,3},
                {4,0,0,8,0,3,0,0,1},
                {7,0,0,0,2,0,0,0,6},
                {0,6,0,0,0,0,2,8,0},
                {0,0,0,4,1,9,0,0,5},
                {0,0,0,0,8,0,0,7,9}
        };

        GameState.initialBoard = copy(puzzle);
        GameState.currentBoard = copy(puzzle);
        loadBoard(GameState.initialBoard);
    }

    private void loadSavedGame() {
        loadBoard(GameState.currentBoard);
    }

    private void loadBoard(int[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                TextField tf = cells[r][c];
                int val = board[r][c];
                if (val != 0) {
                    tf.setText(String.valueOf(val));
                    tf.setEditable(GameState.initialBoard[r][c] == 0);
                } else {
                    tf.clear();
                    tf.setEditable(true);
                }
            }
        }
    }

    private void saveGame() {
        GameState.currentBoard = getBoard();
        GameState.saveToFile();
    }

    private int[][] getBoard() {
        int[][] b = new int[9][9];
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                b[r][c] = cells[r][c].getText().isEmpty()
                        ? 0 : Integer.parseInt(cells[r][c].getText());
        return b;
    }

    private int[][] copy(int[][] src) {
        int[][] d = new int[9][9];
        for (int i = 0; i < 9; i++)
            System.arraycopy(src[i], 0, d[i], 0, 9);
        return d;
    }

    private void solveSudoku() {
        int[][] board = getBoard();

        if (solve(board)) {
            loadBoard(board);
        } else {
            System.out.println("No solution exists!");
        }
    }


    private boolean solve(int[][] b) {

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {

                if (b[r][c] == 0) {

                    for (int n = 1; n <= 9; n++) {
                        if (isSafe(b, r, c, n)) {
                            b[r][c] = n;

                            if (solve(b)) {
                                return true;
                            }

                            b[r][c] = 0;
                        }
                    }

                    return false;
                }
            }
        }

        return true;
    }


    private boolean isSafe(int[][] b, int r, int c, int n) {
        for (int i = 0; i < 9; i++)
            if (b[r][i] == n || b[i][c] == n) return false;
        int sr = (r / 3) * 3, sc = (c / 3) * 3;
        for (int i = sr; i < sr + 3; i++)
            for (int j = sc; j < sc + 3; j++)
                if (b[i][j] == n) return false;
        return true;
    }

    private void goToMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("menu.fxml")
            );
            grid.getScene().setRoot(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void clearGrid() {
        startNewGame();
    }
}
