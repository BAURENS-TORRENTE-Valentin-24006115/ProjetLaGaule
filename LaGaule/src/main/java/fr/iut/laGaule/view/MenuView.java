package fr.iut.laGaule.view;

import fr.iut.laGaule.model.InvasionTheater;
import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Gaul.*;
import fr.iut.laGaule.model.Character.Roman.*;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import fr.iut.laGaule.model.Place.*;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

/**
 * Main menu view for La Gaule application.
 * Provides a beautiful animated menu with options to start simulation,
 * view settings, and access other features.
 */
public class MenuView {

    private StackPane root;
    private Stage primaryStage;
    private VBox menuContainer;
    private static final String TITLE_FONT = "Papyrus";
    private static final String MENU_FONT = "Georgia";

    public MenuView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createMenu();
    }

    private void createMenu() {
        root = new StackPane();
        root.setStyle("-fx-background-color: #1a1a2e;");

        // Background with animated gradient
        Rectangle background = createAnimatedBackground();
        root.getChildren().add(background);

        // Add decorative particles
        addParticles();

        // Main menu content
        menuContainer = new VBox(30);
        menuContainer.setAlignment(Pos.CENTER);
        menuContainer.setPadding(new Insets(50));

        // Title section
        VBox titleSection = createTitleSection();

        // Menu buttons
        VBox buttonSection = createButtonSection();

        // Footer
        HBox footer = createFooter();

        menuContainer.getChildren().addAll(titleSection, buttonSection, footer);
        root.getChildren().add(menuContainer);

        // Fade in animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.5), menuContainer);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    private Rectangle createAnimatedBackground() {
        Rectangle rect = new Rectangle();
        rect.widthProperty().bind(root.widthProperty());
        rect.heightProperty().bind(root.heightProperty());

        Stop[] stops = new Stop[] {
            new Stop(0, Color.web("#1a1a2e")),
            new Stop(0.5, Color.web("#16213e")),
            new Stop(1, Color.web("#0f3460"))
        };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        rect.setFill(gradient);

        return rect;
    }

    private void addParticles() {
        Pane particlePane = new Pane();
        particlePane.setMouseTransparent(true);
        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            Circle particle = new Circle(random.nextDouble() * 3 + 1);
            particle.setFill(Color.web("#e94560", random.nextDouble() * 0.5 + 0.1));
            particle.setCenterX(random.nextDouble() * 1200);
            particle.setCenterY(random.nextDouble() * 800);

            // Float animation
            TranslateTransition tt = new TranslateTransition(
                Duration.seconds(random.nextDouble() * 10 + 5), particle);
            tt.setByY(-100 - random.nextDouble() * 200);
            tt.setByX(random.nextDouble() * 100 - 50);
            tt.setCycleCount(Timeline.INDEFINITE);
            tt.setAutoReverse(true);
            tt.play();

            // Fade animation
            FadeTransition ft = new FadeTransition(
                Duration.seconds(random.nextDouble() * 5 + 2), particle);
            ft.setFromValue(particle.getOpacity());
            ft.setToValue(0.1);
            ft.setCycleCount(Timeline.INDEFINITE);
            ft.setAutoReverse(true);
            ft.play();

            particlePane.getChildren().add(particle);
        }

        root.getChildren().add(particlePane);
    }

    private VBox createTitleSection() {
        VBox titleBox = new VBox(10);
        titleBox.setAlignment(Pos.CENTER);

        // Main title with glow effect
        Text mainTitle = new Text("LA GAULE");
        mainTitle.setFont(Font.font(TITLE_FONT, FontWeight.BOLD, 72));
        mainTitle.setFill(Color.web("#e94560"));

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web("#e94560"));
        shadow.setRadius(20);
        shadow.setSpread(0.3);

        Glow glow = new Glow(0.8);
        glow.setInput(shadow);
        mainTitle.setEffect(glow);

        // Animated glow
        Timeline glowAnimation = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(glow.levelProperty(), 0.3)),
            new KeyFrame(Duration.seconds(1.5), new KeyValue(glow.levelProperty(), 0.8))
        );
        glowAnimation.setCycleCount(Timeline.INDEFINITE);
        glowAnimation.setAutoReverse(true);
        glowAnimation.play();

        // Subtitle
        Text subtitle = new Text("Simulation d'Invasion de l'Armorique");
        subtitle.setFont(Font.font(MENU_FONT, FontWeight.NORMAL, 24));
        subtitle.setFill(Color.web("#f8b500"));

        // Decorative line
        Rectangle line = new Rectangle(300, 2);
        line.setFill(Color.web("#e94560"));
        line.setArcWidth(2);
        line.setArcHeight(2);

        // Description
        Text description = new Text("An temps de l'Empire autocratique romain\net de la Gaule résistante");
        description.setFont(Font.font(MENU_FONT, FontWeight.NORMAL, 16));
        description.setFill(Color.web("#cccccc"));
        description.setTextAlignment(TextAlignment.CENTER);

        titleBox.getChildren().addAll(mainTitle, subtitle, line, description);
        VBox.setMargin(line, new Insets(20, 0, 10, 0));

        return titleBox;
    }

    private VBox createButtonSection() {
        VBox buttonBox = new VBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(40, 0, 40, 0));

        Button startButton = createMenuButton("⚔ LANCER LA SIMULATION", "#e94560");
        startButton.setOnAction(e -> startSimulation());

        Button quickStartButton = createMenuButton("🏃 PARTIE RAPIDE", "#f8b500");
        quickStartButton.setOnAction(e -> startQuickSimulation());

        Button settingsButton = createMenuButton("⚙ PARAMÈTRES", "#0f3460");
        settingsButton.setOnAction(e -> openSettings());

        Button helpButton = createMenuButton("📜 AIDE & HISTOIRE", "#16213e");
        helpButton.setOnAction(e -> showHelp());

        Button quitButton = createMenuButton("🚪 QUITTER", "#333333");
        quitButton.setOnAction(e -> primaryStage.close());

        buttonBox.getChildren().addAll(startButton, quickStartButton, settingsButton, helpButton, quitButton);

        return buttonBox;
    }

    private Button createMenuButton(String text, String baseColor) {
        Button button = new Button(text);
        button.setPrefWidth(350);
        button.setPrefHeight(55);
        button.setFont(Font.font(MENU_FONT, FontWeight.BOLD, 18));
        button.getStyleClass().add("menu-button");

        String normalStyle = String.format(
            "-fx-background-color: linear-gradient(to right, %s, derive(%s, 20%%));" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 30;" +
            "-fx-border-radius: 30;" +
            "-fx-border-color: rgba(255,255,255,0.3);" +
            "-fx-border-width: 1;" +
            "-fx-cursor: hand;",
            baseColor, baseColor
        );

        String hoverStyle = String.format(
            "-fx-background-color: linear-gradient(to right, derive(%s, 30%%), derive(%s, 50%%));" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 30;" +
            "-fx-border-radius: 30;" +
            "-fx-border-color: white;" +
            "-fx-border-width: 2;" +
            "-fx-cursor: hand;",
            baseColor, baseColor
        );

        button.setStyle(normalStyle);

        // Hover effects
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web(baseColor));
        shadow.setRadius(0);

        button.setOnMouseEntered(e -> {
            button.setStyle(hoverStyle);

            Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(shadow.radiusProperty(), 0)),
                new KeyFrame(Duration.millis(200), new KeyValue(shadow.radiusProperty(), 20))
            );
            timeline.play();
            button.setEffect(shadow);

            ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        button.setOnMouseExited(e -> {
            button.setStyle(normalStyle);

            Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(shadow.radiusProperty(), 20)),
                new KeyFrame(Duration.millis(200), new KeyValue(shadow.radiusProperty(), 0))
            );
            timeline.play();

            ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        // Click effect
        button.setOnMousePressed(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(50), button);
            st.setToX(0.95);
            st.setToY(0.95);
            st.play();
        });

        button.setOnMouseReleased(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(50), button);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        return button;
    }

    private HBox createFooter() {
        HBox footer = new HBox(20);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(20, 0, 0, 0));

        Text version = new Text("Version 1.0 | ");
        version.setFill(Color.web("#666666"));
        version.setFont(Font.font(MENU_FONT, 12));

        Text credits = new Text("Projet SAE - IUT");
        credits.setFill(Color.web("#888888"));
        credits.setFont(Font.font(MENU_FONT, 12));

        footer.getChildren().addAll(version, credits);

        return footer;
    }

    private void startSimulation() {
        SimulationConfigView configView = new SimulationConfigView(primaryStage, this);
        primaryStage.getScene().setRoot(configView.getRoot());
    }

    private void startQuickSimulation() {
        // Create default simulation
        InvasionTheater theater = createDefaultTheater();
        SimulationView simulationView = new SimulationView(primaryStage, theater, this);
        primaryStage.getScene().setRoot(simulationView.getRoot());
    }

    private InvasionTheater createDefaultTheater() {
        InvasionTheater theater = new InvasionTheater("Armorique", 10);

        // Create Gaul Village
        ClanLeader abraracourcix = new ClanLeader("Abraracourcix", "M", 45);
        ArrayList<fr.iut.laGaule.model.Character.Character> gaulCharacters = new ArrayList<>();
        gaulCharacters.add(new Druid("Panoramix", "M", 1.80, 80, 50, 70));
        gaulCharacters.add(new Blacksmith("Cétautomatix", "M", 1.85, 35, 90, 85));
        GaulVillage village = new GaulVillage("Village Gaulois", 1000, abraracourcix,
            gaulCharacters.size(), gaulCharacters, new ArrayList<>());
        theater.ajouterLieu(village);

        // Create Roman Camp
        ClanLeader centurion = new ClanLeader("Caius Bonus", "M", 40);
        ArrayList<fr.iut.laGaule.model.Character.Character> romanCharacters = new ArrayList<>();
        romanCharacters.add(new Legionary("Marcus", "M", 1.75, 25, 65, 75));
        romanCharacters.add(new Legionary("Brutus", "M", 1.80, 28, 70, 70));
        RomanFortifiedCamp camp = new RomanFortifiedCamp("Camp de Babaorum", 2000, centurion,
            romanCharacters.size(), romanCharacters, new ArrayList<>());
        theater.ajouterLieu(camp);

        return theater;
    }

    private void openSettings() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Paramètres");
        alert.setHeaderText("Paramètres de la simulation");
        alert.setContentText("Les paramètres seront disponibles dans une prochaine version.");
        styleAlert(alert);
        alert.showAndWait();
    }

    private void showHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aide & Histoire");
        alert.setHeaderText("L'Histoire de la Gaule");
        alert.setContentText(
            "Nous sommes en 50 avant Jésus-Christ. Toute la Gaule est occupée par les Romains...\n" +
            "Toute ? Non ! Un village peuplé d'irréductibles Gaulois résiste encore et toujours à l'envahisseur.\n\n" +
            "Dans cette simulation, vous pourrez :\n" +
            "• Observer les interactions entre Gaulois et Romains\n" +
            "• Voir les personnages évoluer tour par tour\n" +
            "• Gérer la nourriture et la santé des personnages\n" +
            "• Assister aux événements aléatoires"
        );
        styleAlert(alert);
        alert.showAndWait();
    }

    private void styleAlert(Alert alert) {
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
            "-fx-background-color: #1a1a2e;" +
            "-fx-border-color: #e94560;" +
            "-fx-border-width: 2;"
        );
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: white;");
        dialogPane.lookup(".header-panel").setStyle("-fx-background-color: #16213e;");
        dialogPane.lookup(".header-panel .label").setStyle("-fx-text-fill: #f8b500; -fx-font-size: 16px;");
    }

    public Parent getRoot() {
        return root;
    }

    public void returnToMenu() {
        primaryStage.getScene().setRoot(root);
    }
}

