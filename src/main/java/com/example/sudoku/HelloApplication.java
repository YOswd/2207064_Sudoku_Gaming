package com.example.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(
                FXMLLoader.load(getClass().getResource("hello-view.fxml")),
                500, 600
        );
        stage.setTitle("Sudoku Desktop App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
