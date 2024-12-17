package com.github.alphareso;

import com.github.alphareso.layout.Sidebar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Sidebar sidebar = new Sidebar();
        root.setLeft(sidebar);

        Scene scene = new Scene(root, 1000, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/sidebar.css")).toExternalForm());

        primaryStage.setTitle("Sidebar Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}