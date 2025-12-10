package com.example.sudoku;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {

    @FXML private Button btnNewGame;
    @FXML private Button btnResume;
    @FXML private Button btnExit;

    @FXML
    public void initialize() {

        btnNewGame.setOnAction(e -> openSudoku());
        btnExit.setOnAction(e -> System.exit(0));
    }

    private void openSudoku() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("sudoku.fxml"));
            Stage stage = (Stage) btnNewGame.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
