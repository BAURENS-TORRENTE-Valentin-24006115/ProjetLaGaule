package fr.iut.laGaule.view;

import fr.iut.laGaule.model.InvasionTheater;
import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Gaul.*;
import fr.iut.laGaule.model.Character.Roman.*;
import fr.iut.laGaule.model.Place.*;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * Configuration view for setting up a new simulation.
 * Allows users to customize the simulation parameters before starting.
 */
public class SimulationConfigView {

    private StackPane root;
    private Stage primaryStage;
    private MenuView menuView;

    // Configuration fields
    private TextField theaterNameField;
    private Spinner<Integer> maxPlacesSpinner;
    private Spinner<Integer> turnsSpinner;
    private CheckBox autoStartCheck;

    public SimulationConfigView(Stage primaryStage, MenuView menuView) {
        this.primaryStage = primaryStage;
        this.menuView = menuView;
        createView();
    }

    private void createView() {
        root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #1a1a2e, #16213e);");

        VBox mainContainer = new VBox(30);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(50));
        mainContainer.setMaxWidth(600);

        // Title
        Text title = new Text("⚙ Configuration de la Simulation");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 32));
        title.setFill(Color.web("#e94560"));

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web("#e94560"));
        shadow.setRadius(15);
        title.setEffect(shadow);

        // Configuration form
        VBox formContainer = createFormContainer();

        // Buttons
        HBox buttons = createButtons();

        mainContainer.getChildren().addAll(title, formContainer, buttons);
        root.getChildren().add(mainContainer);

        // Animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), mainContainer);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    private VBox createFormContainer() {
        VBox form = new VBox(20);
        form.setStyle(
            "-fx-background-color: rgba(0,0,0,0.3);" +
            "-fx-background-radius: 20;" +
            "-fx-padding: 30;"
        );

        // Theater name
        VBox nameSection = createFormSection("🏟️ Nom du Théâtre d'Invasion");
        theaterNameField = new TextField("Armorique");
        styleTextField(theaterNameField);
        nameSection.getChildren().add(theaterNameField);

        // Max places
        VBox placesSection = createFormSection("📍 Nombre maximum de lieux");
        maxPlacesSpinner = new Spinner<>(2, 20, 10);
        styleSpinner(maxPlacesSpinner);
        placesSection.getChildren().add(maxPlacesSpinner);

        // Number of turns
        VBox turnsSection = createFormSection("🔄 Nombre de tours de simulation");
        turnsSpinner = new Spinner<>(5, 100, 20);
        styleSpinner(turnsSpinner);
        turnsSection.getChildren().add(turnsSpinner);

        // Auto-start option
        VBox optionsSection = createFormSection("⚡ Options");
        autoStartCheck = new CheckBox("Démarrer automatiquement la simulation");
        autoStartCheck.setFont(Font.font("Georgia", 14));
        autoStartCheck.setTextFill(Color.WHITE);
        autoStartCheck.setStyle("-fx-cursor: hand;");
        optionsSection.getChildren().add(autoStartCheck);

        // Places configuration
        VBox placesConfigSection = createPlacesConfiguration();

        form.getChildren().addAll(nameSection, placesSection, turnsSection, optionsSection, placesConfigSection);
        return form;
    }

    private VBox createFormSection(String labelText) {
        VBox section = new VBox(8);

        Text label = new Text(labelText);
        label.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        label.setFill(Color.web("#f8b500"));

        section.getChildren().add(label);
        return section;
    }

    private void styleTextField(TextField field) {
        field.setStyle(
            "-fx-background-color: rgba(255,255,255,0.1);" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 10;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 10;" +
            "-fx-border-color: rgba(255,255,255,0.3);" +
            "-fx-border-width: 1;"
        );
        field.setFont(Font.font("Georgia", 14));
    }

    private void styleSpinner(Spinner<?> spinner) {
        spinner.setEditable(true);
        spinner.setPrefWidth(150);
        spinner.setStyle(
            "-fx-background-color: rgba(255,255,255,0.1);" +
            "-fx-font-size: 14px;"
        );
    }

    private VBox createPlacesConfiguration() {
        VBox section = createFormSection("🏘️ Lieux de départ");

        Text description = new Text("La simulation démarrera avec un village gaulois et un camp romain par défaut.");
        description.setFont(Font.font("Georgia", 12));
        description.setFill(Color.web("#cccccc"));
        description.setWrappingWidth(500);

        HBox placeTags = new HBox(10);
        placeTags.setAlignment(Pos.CENTER_LEFT);
        placeTags.setPadding(new Insets(10, 0, 0, 0));

        Label gaulTag = createPlaceTag("🏘️ Village Gaulois", "#8b4513");
        Label romanTag = createPlaceTag("⛺ Camp Romain", "#8b0000");

        placeTags.getChildren().addAll(gaulTag, romanTag);

        section.getChildren().addAll(description, placeTags);
        return section;
    }

    private Label createPlaceTag(String text, String color) {
        Label tag = new Label(text);
        tag.setFont(Font.font("Georgia", FontWeight.BOLD, 12));
        tag.setTextFill(Color.WHITE);
        tag.setPadding(new Insets(8, 15, 8, 15));
        tag.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-background-radius: 20;"
        );
        return tag;
    }

    private HBox createButtons() {
        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(20, 0, 0, 0));

        Button startButton = createButton("🚀 Lancer la Simulation", "#e94560");
        startButton.setOnAction(e -> startSimulation());

        Button backButton = createButton("↩ Retour", "#666666");
        backButton.setOnAction(e -> menuView.returnToMenu());

        buttons.getChildren().addAll(backButton, startButton);
        return buttons;
    }

    private Button createButton(String text, String color) {
        Button button = new Button(text);
        button.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        button.setPrefHeight(50);
        button.setPadding(new Insets(10, 30, 10, 30));

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
            st.setToX(1.05);
            st.setToY(1.05);
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
        String theaterName = theaterNameField.getText();
        int maxPlaces = maxPlacesSpinner.getValue();

        InvasionTheater theater = new InvasionTheater(theaterName, maxPlaces);

        // Create default Gaul Village
        ClanLeader abraracourcix = new ClanLeader("Abraracourcix", "M", 45);
        ArrayList<Character> gaulCharacters = new ArrayList<>();
        gaulCharacters.add(new Druid("Panoramix", "M", 1.80, 80, 50, 70));
        gaulCharacters.add(new Blacksmith("Cétautomatix", "M", 1.85, 35, 90, 85));
        GaulVillage village = new GaulVillage("Village des Irréductibles", 1000, abraracourcix,
            gaulCharacters.size(), gaulCharacters, new ArrayList<>());
        theater.ajouterLieu(village);

        // Create default Roman Camp
        ClanLeader centurion = new ClanLeader("Caius Bonus", "M", 40);
        ArrayList<Character> romanCharacters = new ArrayList<>();
        romanCharacters.add(new Legionary("Marcus", "M", 1.75, 25, 65, 75));
        romanCharacters.add(new Legionary("Brutus", "M", 1.80, 28, 70, 70));
        RomanFortifiedCamp camp = new RomanFortifiedCamp("Camp de Babaorum", 2000, centurion,
            romanCharacters.size(), romanCharacters, new ArrayList<>());
        theater.ajouterLieu(camp);

        // Start simulation view
        SimulationView simulationView = new SimulationView(primaryStage, theater, menuView);
        primaryStage.getScene().setRoot(simulationView.getRoot());
    }

    public Parent getRoot() {
        return root;
    }
}

