package com.github.alphareso.layout;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.util.Duration;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.javafx.FontIcon;

public class NavItem extends HBox {
    private final Label label;
    private final FontIcon icon;

    public NavItem(String text, String iconLiteral) {
        getStyleClass().add("nav-item");

        Button button = new Button();
        button.getStyleClass().add("nav-button");

        icon = new FontIcon();
        icon.setIconLiteral("bxs-" + iconLiteral);
        icon.getStyleClass().add("nav-icon");

        label = new Label(text);
        label.getStyleClass().add("links_name");

        button.setGraphic(new HBox(10, icon, label));
        getChildren().add(button);

        // Add hover effects programmatically
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #575a89;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent;"));

        // Add tooltip for collapsed state


    }

    public void setOnAction(Runnable action) {
        Button button = (Button) getChildren().get(0); // Ambil elemen Button langsung
        button.setOnAction(e -> action.run());
    }

    public Label getLabel() {
        return label;
    }

    public FontIcon getIcon() {
        return icon;
    }

}