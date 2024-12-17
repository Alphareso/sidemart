package com.github.alphareso.layout;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.javafx.FontIcon;

public class Navigator extends HBox {
    private final VBox sidebar;
    private final VBox mainContent;

    private boolean isExpanded = false;
    private static final double COLLAPSED_WIDTH = 78;
    private static final double EXPANDED_WIDTH = 250;

    public Navigator() {
        sidebar = createSidebar();
        mainContent = createMainContent();

        getChildren().addAll(sidebar, mainContent);
        getStyleClass().add("root");
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(COLLAPSED_WIDTH);
        sidebar.getStyleClass().add("sidebar");

        // Logo
        HBox header = new HBox();
        Label logo = new Label("Menu");
        logo.getStyleClass().add("logo-text");

        // toggle button
        Button toggleBtn = new Button();
        toggleBtn.getStyleClass().add("toggle-btn");
        FontIcon menuIcon = new FontIcon("bx-menu");
        toggleBtn.setGraphic(menuIcon);
        toggleBtn.setOnAction(e -> toggleSidebar());
        header.getChildren().addAll(logo, toggleBtn);
        header.getStyleClass().add("logo-details");

        // Main content area (nav items)
        VBox mainArea = new VBox();
        mainArea.getStyleClass().add("main-area");
        VBox.setVgrow(mainArea, Priority.ALWAYS);

        // Navigation items
        VBox navList = createNavList();
        mainArea.getChildren().add(navList);

        // Profile section (now at bottom)
        HBox profile = createProfileSection();

        sidebar.getChildren().addAll(header, mainArea, profile);
        return sidebar;
    }

    private VBox createMainContent() {
        VBox content = new VBox();
        content.getStyleClass().add("home-section");

        // Main content header
        Label title = new Label("Accueil");
        title.getStyleClass().add("text");

        // Example content area (you can customize this based on your needs)
        VBox contentArea = new VBox(20);
        contentArea.getStyleClass().add("content-area");
        contentArea.setPadding(new javafx.geometry.Insets(20));

        // Add some sample content
        Label welcomeText = new Label("Bienvenue dans votre espace personnel");
        welcomeText.getStyleClass().add("welcome-text");

        // Sample cards or widgets
        HBox cards = new HBox(20);
        cards.getStyleClass().add("cards-container");

        for (int i = 1; i <= 3; i++) {
            VBox card = new VBox(10);
            card.getStyleClass().add("card");
            card.setPadding(new javafx.geometry.Insets(15));

            Label cardTitle = new Label("Card " + i);
            Label cardContent = new Label("Sample content for card " + i);

            card.getChildren().addAll(cardTitle, cardContent);
            cardTitle.getStyleClass().add("card-title");
            cards.getChildren().add(card);
        }

        contentArea.getChildren().addAll(welcomeText, cards);
        content.getChildren().addAll(title, contentArea);

        return content;
    }

    private VBox createNavList() {
        VBox navList = new VBox(10);
        navList.getStyleClass().add("nav-list");

        // Search
        HBox searchBox = new HBox();
        searchBox.getStyleClass().add("search-box");
        TextField searchField = new TextField();
        searchField.setPromptText("Recherche...");
        searchField.getStyleClass().add("search-field");
        Button searchBtn = new Button("", new FontIcon(BoxiconsRegular.SEARCH));
        searchBox.getChildren().addAll(searchBtn, searchField);

        navList.getChildren().add(searchBox);

        // Nav items
        var menuItems = new MenuItem[] {
                new MenuItem("Dasboard", BoxiconsRegular.GRID_ALT),
                new MenuItem("Profile", BoxiconsRegular.USER),
                new MenuItem("Messages", BoxiconsRegular.CHAT),
                new MenuItem("Statistic", BoxiconsRegular.PIE_CHART_ALT_2),
                new MenuItem("Files", BoxiconsRegular.FOLDER),
                new MenuItem("Store", BoxiconsRegular.CART_ALT),
                new MenuItem("Favorite", BoxiconsRegular.HEART),
                new MenuItem("Parameter", BoxiconsRegular.COG)
        };

        for (MenuItem item : menuItems) {
            navList.getChildren().add(item);
        }

        return navList;
    }

    private HBox createProfileSection() {
        HBox profile = new HBox();
        profile.getStyleClass().add("profile");

        // Profile details container
        HBox profileDetails = new HBox();
        profileDetails.getStyleClass().add("profile-details");

        ImageView profileImg = new ImageView("/popol.jpg");
        profileImg.setFitHeight(45);
        profileImg.setFitWidth(45);
        profileImg.getStyleClass().add("profile-img");

        VBox profileInfo = new VBox();
        profileInfo.getStyleClass().add("profile-info");
        Label name = new Label("Paulon");
        Label job = new Label("Développeur Web");
        profileInfo.getChildren().addAll(name, job);

        profileDetails.getChildren().addAll(profileImg, profileInfo);

        Button logoutBtn = new Button("", new FontIcon(BoxiconsRegular.LOG_OUT));
        logoutBtn.setOnAction(e -> quit());
        logoutBtn.getStyleClass().add("logout-btn");

        profile.getChildren().addAll(profileDetails, logoutBtn);
        return profile;
    }

    private void quit() {
        System.exit(0);
    }

    private void toggleSidebar() {
        double targetWidth = isExpanded ? COLLAPSED_WIDTH : EXPANDED_WIDTH;

        TranslateTransition sidebarTransition = new TranslateTransition(Duration.millis(500), sidebar);
        sidebar.setPrefWidth(targetWidth);

        TranslateTransition contentTransition = new TranslateTransition(Duration.millis(500), mainContent);
        contentTransition.setToX(targetWidth - COLLAPSED_WIDTH);

        sidebar.getStyleClass().removeAll("expanded", "collapsed");
        sidebar.getStyleClass().add(isExpanded ? "collapsed" : "expanded");

        sidebarTransition.play();
        contentTransition.play();

        isExpanded = !isExpanded;
    }

    private static class MenuItem extends HBox {
        public MenuItem(String text, BoxiconsRegular icon) {
            super(10);
            setAlignment(Pos.CENTER_LEFT);
            getStyleClass().add("menu-item");

            Button iconBtn = new Button("", new FontIcon(icon));
            iconBtn.getStyleClass().add("menu-icon");
            Label label = new Label(text);
            label.getStyleClass().add("menu-text");

            Tooltip tooltip = new Tooltip(text);
            Tooltip.install(this, tooltip);

            getChildren().addAll(iconBtn, label);

            setOnMouseClicked(e -> System.out.println("Clicked: " + text));
        }
    }
}
