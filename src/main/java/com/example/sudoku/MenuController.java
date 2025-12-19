package com.example.sudoku;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {

    @FXML private Button btnNewGame;
    @FXML private Button btnResume;
    @FXML private Button btnExit;

    @FXML
    public void initialize() {

        btnNewGame.setOnAction(e -> {
            GameState.clear();
            openSudoku();
        });

        btnResume.setOnAction(e -> {
            if (!GameState.hasSavedGame()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Saved Game");
                alert.setHeaderText(null);
                alert.setContentText("No saved game found!");
                alert.showAndWait();
            } else {
                openSudoku();
            }
        });

        btnExit.setOnAction(e -> goToWelcome());
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

    private void goToWelcome() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("hello-view.fxml")
            );
            btnExit.getScene().setRoot(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
