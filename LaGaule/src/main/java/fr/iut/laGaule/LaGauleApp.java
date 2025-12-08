package fr.iut.laGaule;

import fr.iut.laGaule.view.MenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main JavaFX Application class for La Gaule simulation.
 * This class initializes and launches the graphical user interface.
 */
public class LaGauleApp extends Application {

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;

    @Override
    public void start(Stage primaryStage) {
        MenuView menuView = new MenuView(primaryStage);
        Scene scene = new Scene(menuView.getRoot(), WINDOW_WIDTH, WINDOW_HEIGHT);

        // Load CSS stylesheet
        java.net.URL cssResource = getClass().getResource("/styles/main.css");
        if (cssResource != null) {
            scene.getStylesheets().add(cssResource.toExternalForm());
        }

        primaryStage.setTitle("La Gaule - Simulation d'Invasion");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

