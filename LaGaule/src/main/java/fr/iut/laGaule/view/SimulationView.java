package fr.iut.laGaule.view;

import fr.iut.laGaule.model.InvasionTheater;
import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.Gaul.*;
import fr.iut.laGaule.model.Character.Roman.*;
import fr.iut.laGaule.model.Place.*;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

/**
 * View for the simulation in progress.
 * Displays places, characters, and simulation events in real-time.
 */
public class SimulationView {

    private StackPane root;
    private Stage primaryStage;
    private MenuView menuView;
    private InvasionTheater theater;
    private VBox logArea;
    private ScrollPane logScroll;
    private int currentTurn = 0;
    private boolean isRunning = false;
    private Timeline simulationTimeline;
    private Random random = new Random();
    private VBox placesContainer;

    public SimulationView(Stage primaryStage, InvasionTheater theater, MenuView menuView) {
        this.primaryStage = primaryStage;
        this.theater = theater;
        this.menuView = menuView;
        createView();
    }

    private void createView() {
        root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e);");

        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(20));

        // Top - Header
        mainLayout.setTop(createHeader());

        // Center - Main content
        mainLayout.setCenter(createMainContent());

        // Bottom - Controls
        mainLayout.setBottom(createControls());

        root.getChildren().add(mainLayout);

        // Fade in
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), mainLayout);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    private HBox createHeader() {
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 20, 0));

        Text title = new Text("⚔ Simulation en cours");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 32));
        title.setFill(Color.web("#e94560"));

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web("#e94560"));
        shadow.setRadius(10);
        title.setEffect(shadow);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Text turnIndicator = new Text("Tour: 0");
        turnIndicator.setFont(Font.font("Georgia", FontWeight.BOLD, 24));
        turnIndicator.setFill(Color.web("#f8b500"));
        turnIndicator.setId("turnIndicator");

        header.getChildren().addAll(title, spacer, turnIndicator);
        return header;
    }

    private HBox createMainContent() {
        HBox content = new HBox(20);
        content.setAlignment(Pos.TOP_CENTER);
        HBox.setHgrow(content, Priority.ALWAYS);

        // Left - Places view
        VBox placesSection = createPlacesSection();
        HBox.setHgrow(placesSection, Priority.ALWAYS);

        // Right - Event log
        VBox logSection = createLogSection();
        logSection.setMinWidth(350);
        logSection.setMaxWidth(400);

        content.getChildren().addAll(placesSection, logSection);
        return content;
    }

    private VBox createPlacesSection() {
        VBox section = new VBox(15);
        section.setAlignment(Pos.TOP_CENTER);

        Text sectionTitle = new Text("📍 Lieux");
        sectionTitle.setFont(Font.font("Georgia", FontWeight.BOLD, 20));
        sectionTitle.setFill(Color.WHITE);

        placesContainer = new VBox(15);
        placesContainer.setAlignment(Pos.TOP_CENTER);

        updatePlacesDisplay();

        ScrollPane scrollPane = new ScrollPane(placesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        section.getChildren().addAll(sectionTitle, scrollPane);
        return section;
    }

    private void updatePlacesDisplay() {
        placesContainer.getChildren().clear();

        for (Place place : theater.getLieux()) {
            VBox placeCard = createPlaceCard(place);
            placesContainer.getChildren().add(placeCard);
        }
    }

    private VBox createPlaceCard(Place place) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setMaxWidth(500);
        card.setMinWidth(400);

        String bgColor = getPlaceColor(place);
        card.setStyle(
            "-fx-background-color: " + bgColor + ";" +
            "-fx-background-radius: 15;" +
            "-fx-border-radius: 15;" +
            "-fx-border-color: rgba(255,255,255,0.2);" +
            "-fx-border-width: 1;"
        );

        // Place header
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Text icon = new Text(getPlaceIcon(place));
        icon.setFont(Font.font(24));

        Text placeName = new Text(place.getName());
        placeName.setFont(Font.font("Georgia", FontWeight.BOLD, 18));
        placeName.setFill(Color.WHITE);

        Text placeType = new Text("(" + place.getClass().getSimpleName() + ")");
        placeType.setFont(Font.font("Georgia", 12));
        placeType.setFill(Color.web("#cccccc"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Text characterCount = new Text("👥 " + place.getNbCharacter());
        characterCount.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        characterCount.setFill(Color.web("#f8b500"));

        header.getChildren().addAll(icon, placeName, placeType, spacer, characterCount);

        // Characters list
        FlowPane charactersPane = new FlowPane(10, 10);
        charactersPane.setAlignment(Pos.CENTER_LEFT);

        for (Character character : place.getCharacter()) {
            HBox characterBadge = createCharacterBadge(character);
            charactersPane.getChildren().add(characterBadge);
        }

        card.getChildren().addAll(header, new Separator(), charactersPane);

        // Hover effect
        card.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), card);
            st.setToX(1.02);
            st.setToY(1.02);
            st.play();
        });
        card.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), card);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        return card;
    }

    private HBox createCharacterBadge(Character character) {
        HBox badge = new HBox(5);
        badge.setAlignment(Pos.CENTER);
        badge.setPadding(new Insets(5, 10, 5, 10));

        String color = getCharacterColor(character);
        badge.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-background-radius: 20;"
        );

        Text icon = new Text(getCharacterIcon(character));

        Text name = new Text(character.getName());
        name.setFont(Font.font("Georgia", FontWeight.BOLD, 11));
        name.setFill(Color.WHITE);

        // Health indicator
        ProgressBar healthBar = new ProgressBar(character.getHealth() / 100.0);
        healthBar.setPrefWidth(40);
        healthBar.setPrefHeight(8);
        healthBar.setStyle("-fx-accent: " + getHealthColor(character.getHealth()) + ";");

        badge.getChildren().addAll(icon, name, healthBar);

        // Tooltip
        Tooltip tooltip = new Tooltip(
            character.getName() + "\n" +
            "PV: " + character.getHealth() + "/100\n" +
            "Force: " + character.getStrength() + "\n" +
            "Endurance: " + character.getEndurance()
        );
        Tooltip.install(badge, tooltip);

        return badge;
    }

    private String getPlaceColor(Place place) {
        if (place instanceof GaulVillage) return "rgba(139, 69, 19, 0.8)";
        if (place instanceof RomanFortifiedCamp) return "rgba(139, 0, 0, 0.8)";
        if (place instanceof RomanCity) return "rgba(128, 0, 32, 0.8)";
        if (place instanceof BattleFields) return "rgba(64, 64, 64, 0.8)";
        return "rgba(70, 70, 70, 0.8)";
    }

    private String getPlaceIcon(Place place) {
        if (place instanceof GaulVillage) return "🏘️";
        if (place instanceof RomanFortifiedCamp) return "⛺";
        if (place instanceof RomanCity) return "🏛️";
        if (place instanceof BattleFields) return "⚔️";
        return "📍";
    }

    private String getCharacterIcon(Character character) {
        if (character instanceof Druid) return "🧙";
        if (character instanceof Blacksmith) return "🔨";
        if (character instanceof Legionary) return "🛡️";
        if (character instanceof General) return "👑";
        return "👤";
    }

    private String getCharacterColor(Character character) {
        String className = character.getClass().getPackage().getName();
        if (className.contains("Gaul")) return "rgba(46, 139, 87, 0.9)";
        if (className.contains("Roman")) return "rgba(178, 34, 34, 0.9)";
        return "rgba(100, 100, 100, 0.9)";
    }

    private String getHealthColor(int health) {
        if (health > 70) return "#2ecc71";
        if (health > 30) return "#f39c12";
        return "#e74c3c";
    }

    private VBox createLogSection() {
        VBox section = new VBox(10);
        section.setStyle(
            "-fx-background-color: rgba(0,0,0,0.3);" +
            "-fx-background-radius: 15;" +
            "-fx-padding: 15;"
        );

        Text sectionTitle = new Text("📜 Journal des événements");
        sectionTitle.setFont(Font.font("Georgia", FontWeight.BOLD, 18));
        sectionTitle.setFill(Color.WHITE);

        logArea = new VBox(5);
        logArea.setStyle("-fx-padding: 10;");

        logScroll = new ScrollPane(logArea);
        logScroll.setFitToWidth(true);
        logScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        logScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        VBox.setVgrow(logScroll, Priority.ALWAYS);
        logScroll.setMinHeight(400);

        addLogEntry("Simulation initialisée.", "#2ecc71");
        addLogEntry("En attente du démarrage...", "#f8b500");

        section.getChildren().addAll(sectionTitle, logScroll);
        return section;
    }

    private void addLogEntry(String message, String color) {
        HBox entry = new HBox(10);
        entry.setAlignment(Pos.CENTER_LEFT);

        Circle dot = new Circle(4);
        dot.setFill(Color.web(color));

        Text text = new Text("[Tour " + currentTurn + "] " + message);
        text.setFont(Font.font("Consolas", 12));
        text.setFill(Color.WHITE);
        text.setWrappingWidth(300);

        entry.getChildren().addAll(dot, text);

        FadeTransition ft = new FadeTransition(Duration.millis(300), entry);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        logArea.getChildren().add(entry);

        // Auto-scroll to bottom
        Platform.runLater(() -> logScroll.setVvalue(1.0));
    }

    private HBox createControls() {
        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(20, 0, 0, 0));

        Button playButton = createControlButton("▶ Démarrer", "#2ecc71");
        Button pauseButton = createControlButton("⏸ Pause", "#f39c12");
        Button stepButton = createControlButton("⏭ Tour suivant", "#3498db");
        Button backButton = createControlButton("↩ Retour au menu", "#e74c3c");

        pauseButton.setDisable(true);

        playButton.setOnAction(e -> {
            startSimulation();
            playButton.setDisable(true);
            pauseButton.setDisable(false);
        });

        pauseButton.setOnAction(e -> {
            pauseSimulation();
            playButton.setDisable(false);
            pauseButton.setDisable(true);
        });

        stepButton.setOnAction(e -> {
            if (!isRunning) {
                executeTurn();
            }
        });

        backButton.setOnAction(e -> {
            pauseSimulation();
            menuView.returnToMenu();
        });

        controls.getChildren().addAll(playButton, pauseButton, stepButton, backButton);
        return controls;
    }

    private Button createControlButton(String text, String color) {
        Button button = new Button(text);
        button.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        button.setPrefHeight(45);
        button.setPadding(new Insets(10, 25, 10, 25));

        String style = String.format(
            "-fx-background-color: %s;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 25;" +
            "-fx-cursor: hand;",
            color
        );
        button.setStyle(style);

        button.setOnMouseEntered(e -> {
            button.setStyle(style.replace(color, "derive(" + color + ", 20%)"));
            ScaleTransition st = new ScaleTransition(Duration.millis(100), button);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        button.setOnMouseExited(e -> {
            button.setStyle(style);
            ScaleTransition st = new ScaleTransition(Duration.millis(100), button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        return button;
    }

    private void startSimulation() {
        isRunning = true;
        addLogEntry("Simulation démarrée !", "#2ecc71");

        simulationTimeline = new Timeline(
            new KeyFrame(Duration.seconds(2), e -> executeTurn())
        );
        simulationTimeline.setCycleCount(Timeline.INDEFINITE);
        simulationTimeline.play();
    }

    private void pauseSimulation() {
        isRunning = false;
        if (simulationTimeline != null) {
            simulationTimeline.pause();
        }
        addLogEntry("Simulation en pause.", "#f39c12");
    }

    private void executeTurn() {
        currentTurn++;

        // Update turn indicator
        Text turnIndicator = (Text) root.lookup("#turnIndicator");
        if (turnIndicator != null) {
            turnIndicator.setText("Tour: " + currentTurn);
        }

        addLogEntry("--- Début du tour " + currentTurn + " ---", "#e94560");

        // Random events
        for (Place place : theater.getLieux()) {
            // Food spawn
            if (random.nextInt(100) < 30) {
                addLogEntry("De la nourriture apparaît à " + place.getName(), "#f8b500");
            }

            // Character events
            for (Character character : place.getCharacter()) {
                if (random.nextInt(100) < 15) {
                    int damage = random.nextInt(10) + 1;
                    character.receiveDamage(damage);
                    addLogEntry(character.getName() + " perd " + damage + " PV", "#e74c3c");
                } else if (random.nextInt(100) < 10) {
                    character.heal(5);
                    addLogEntry(character.getName() + " récupère 5 PV", "#2ecc71");
                }
            }
        }

        // Update display
        Platform.runLater(this::updatePlacesDisplay);
    }

    public Parent getRoot() {
        return root;
    }
}

