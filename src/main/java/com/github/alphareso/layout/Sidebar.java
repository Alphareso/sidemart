package com.github.alphareso.layout;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.kordamp.ikonli.javafx.FontIcon;

public class Sidebar extends VBox {
    private static final PseudoClass EXPANDED = PseudoClass.getPseudoClass("expanded");
    private static final double COLLAPSED_WIDTH = 10; // Lebar saat collapsed lebih kecil
    private static final double EXPANDED_WIDTH = 250; // Tetap sama

    private final BooleanProperty expanded = new SimpleBooleanProperty(false);
    private final VBox navList;
    private final Label logoName;
    private final VBox profileDetails;
    private final Button toggleBtn;

    public Sidebar() {
        getStyleClass().add("sidebar");

        // Logo section
        logoName = new Label("Alphareso");
        logoName.getStyleClass().add("logo-name");

        toggleBtn = new Button();
        toggleBtn.getStyleClass().add("toggle-btn");
        FontIcon menuIcon = new FontIcon("bx-menu");
        toggleBtn.setGraphic(menuIcon);
        toggleBtn.setOnAction(e -> toggleSidebar());

        // Add hover effects programmatically
        toggleBtn.setOnMouseEntered(e -> toggleBtn.setStyle("-fx-background-color: #575a89;"));
        toggleBtn.setOnMouseExited(e -> toggleBtn.setStyle("-fx-background-color: transparent;"));

        // Gunakan StackPane untuk posisi absolut
        StackPane logoPane = new StackPane();
        logoPane.getStyleClass().add("logo-pane");
        logoPane.getChildren().addAll(logoName, toggleBtn);

        // Posisi toggleBtn di kanan dengan padding
        //StackPane.setAlignment(toggleBtn, Pos.CENTER_RIGHT);
        //StackPane.setAlignment(logoName, Pos.CENTER_LEFT);

        // Tambahkan ke VBox
        VBox logoDetails = new VBox();
        logoDetails.getStyleClass().add("logo-details");
        logoDetails.getChildren().add(logoPane);

        // Navigation list
        navList = new VBox();
        navList.getStyleClass().add("nav-list");
        createNavigationItems();

        // Profile section
        profileDetails = new VBox();
        profileDetails.getStyleClass().add("profile-details");
        createProfileSection();

        getChildren().addAll(logoDetails, navList, profileDetails);

        // Initialize state
        expanded.addListener((obs, oldVal, newVal) -> {
            pseudoClassStateChanged(EXPANDED, newVal);
            animateWidth(newVal ? EXPANDED_WIDTH : COLLAPSED_WIDTH);
            updateMenuIcon(newVal);

            // Atur posisi toggleBtn untuk collapsed
            if (!newVal) {
                StackPane.setAlignment(toggleBtn, Pos.CENTER_LEFT); // Tengah saat collapsed
            } else {
                StackPane.setAlignment(toggleBtn, Pos.CENTER_RIGHT); // Sebelah kanan saat expanded
            }
        });

    }

    private void createNavigationItems() {
        NavItemConfig[] items = {
            new NavItemConfig("Accueil", "grid-alt"),
            new NavItemConfig("Profil", "user"),
            new NavItemConfig("Messages", "chat"),
            new NavItemConfig("Statistiques", "pie-chart-alt-2"),
            new NavItemConfig("Fichiers", "folder"),
            new NavItemConfig("Boutique", "cart-alt"),
            new NavItemConfig("Coups de coeur", "heart"),
            new NavItemConfig("Paramètres", "cog")
        };

        // Buat daftar NavItem dan tambahkan tooltip
        for (NavItemConfig item : items) {
            NavItem navItem = new NavItem(item.text(), item.icon());
            navItem.setOnAction(() -> handleNavigation(item.text()));

            // Buat tooltip untuk text menu
            Label tooltip = new Label(item.text());
            tooltip.getStyleClass().add("tooltip");
            tooltip.setVisible(!isExpanded()); // Tooltip hanya terlihat saat collapsed
            tooltip.setManaged(!isExpanded());

            // Dengarkan perubahan status expanded
            expanded.addListener((obs, oldVal, newVal) -> {
                navItem.getLabel().setVisible(newVal); // Tampilkan teks hanya jika expanded
                navItem.getLabel().setManaged(newVal);

                tooltip.setVisible(!newVal); // Tampilkan tooltip hanya jika collapsed
                tooltip.setManaged(!newVal);
            });

            // Inisialisasi visibilitas sesuai status awal expanded
            navItem.getLabel().setVisible(isExpanded());
            navItem.getLabel().setManaged(isExpanded());
            tooltip.setVisible(!isExpanded());
            tooltip.setManaged(!isExpanded());

            // Gabungkan NavItem dengan Tooltip
            //navItem.getChildren().add(tooltip);
            navList.getChildren().add(navItem);
        }


    }

    private void handleNavigation(String section) {
        // Handle navigation logic here
        System.out.println("Navigating to: " + section);
    }

    private void createProfileSection() {
        Label nameLabel = new Label("Paulon");
        nameLabel.getStyleClass().add("name");

        Label jobLabel = new Label("Développeur Web");
        jobLabel.getStyleClass().add("job");

        profileDetails.getChildren().addAll(nameLabel, jobLabel);
    }

    private void updateMenuIcon(boolean expanded) {
        FontIcon icon = (FontIcon) toggleBtn.getGraphic();
        icon.setIconLiteral(expanded ? "bx-menu-alt-right" : "bx-menu");
        toggleBtn.setGraphic(icon);
        //toggleBtn.setStyle(expanded ? "-fx-alignment: center-right;" : "-fx-alignment: center;");
    }

    private void toggleSidebar() {
        expanded.set(!expanded.get());
    }

    private void animateWidth(double targetWidth) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(prefWidthProperty(), getWidth())),
                new KeyFrame(Duration.millis(50), new KeyValue(prefWidthProperty(), targetWidth))
        );
        timeline.play();
    }

    public boolean isExpanded() {
        return expanded.get();
    }

    public BooleanProperty expandedProperty() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded.set(expanded);
    }

    private record NavItemConfig(String text, String icon) {}
}