// src/main/java/com/sidebar/Launcher.java
package com.github.alphareso;

import com.github.alphareso.layout.Navigator;
import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Launcher extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Initialize CSSFX for hot-reloading of CSS
        CSSFX.start();

        var root = new Navigator();
        Scene scene = new Scene(root, 1024, 768);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/main.css")).toExternalForm());

        primaryStage.setTitle("Sidebar Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


