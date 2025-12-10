package fr.iut.laGaule.view;

import fr.iut.laGaule.model.Place.GaulVillage;
import fr.iut.laGaule.model.Place.Place;
import fr.iut.laGaule.model.Place.RomanFortifiedCamp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe PlaceView.
 */
public class PlaceViewTest {

    private PlaceView placeView;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUp() {
        placeView = new PlaceView();
        // Rediriger System.out et System.err pour capturer les sorties
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void tearDown() {
        // Restaurer System.out et System.err
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testDisplayPlaceDetails() {
        // Créer un lieu de test
        Place village = new GaulVillage("Village des Irréductibles", 100, null, 0, new ArrayList<>(), new ArrayList<>());

        // Afficher les détails
        placeView.displayPlaceDetails(village);

        // Vérifier la sortie
        String output = outContent.toString();
        assertTrue(output.contains("Village des Irréductibles"),
                "La sortie devrait contenir le nom du village");
        assertTrue(output.contains("==="),
                "La sortie devrait contenir les séparateurs");
    }

    @Test
    public void testDisplayPlaceDetailsWithDifferentPlace() {
        // Créer un autre type de lieu
        Place camp = new RomanFortifiedCamp("Camp Romain", 150, null, 0, new ArrayList<>(), new ArrayList<>());

        // Afficher les détails
        placeView.displayPlaceDetails(camp);

        // Vérifier la sortie
        String output = outContent.toString();
        assertTrue(output.contains("Camp Romain"),
                "La sortie devrait contenir le nom du camp");
    }

    @Test
    public void testDisplayMessage() {
        // Message de test
        String testMessage = "Ceci est un message de test";

        // Afficher le message
        placeView.displayMessage(testMessage);

        // Vérifier la sortie
        String output = outContent.toString().trim();
        assertEquals(testMessage, output,
                "Le message affiché devrait correspondre au message d'entrée");
    }

    @Test
    public void testDisplayMessageEmpty() {
        // Tester avec un message vide
        placeView.displayMessage("");

        // Vérifier que quelque chose a été écrit (une ligne vide)
        assertNotNull(outContent.toString());
    }

    @Test
    public void testDisplayError() {
        // Message d'erreur de test
        String errorMessage = "Une erreur s'est produite";

        // Afficher l'erreur
        placeView.displayError(errorMessage);

        // Vérifier la sortie d'erreur
        String errorOutput = errContent.toString().trim();
        assertTrue(errorOutput.contains("Erreur"),
                "La sortie d'erreur devrait contenir le mot 'Erreur'");
        assertTrue(errorOutput.contains(errorMessage),
                "La sortie d'erreur devrait contenir le message d'erreur");
    }

    @Test
    public void testDisplayErrorFormat() {
        // Tester le format du message d'erreur
        String errorMessage = "Test d'erreur";

        placeView.displayError(errorMessage);

        // Vérifier que le format est correct
        String errorOutput = errContent.toString().trim();
        assertEquals("Erreur : " + errorMessage, errorOutput,
                "Le format du message d'erreur devrait être 'Erreur : [message]'");
    }

    @Test
    public void testDisplayMultipleMessages() {
        // Tester l'affichage de plusieurs messages
        placeView.displayMessage("Message 1");
        placeView.displayMessage("Message 2");
        placeView.displayMessage("Message 3");

        // Vérifier que tous les messages sont affichés
        String output = outContent.toString();
        assertTrue(output.contains("Message 1"), "Devrait contenir le premier message");
        assertTrue(output.contains("Message 2"), "Devrait contenir le deuxième message");
        assertTrue(output.contains("Message 3"), "Devrait contenir le troisième message");
    }

    @Test
    public void testDisplayMultipleErrors() {
        // Tester l'affichage de plusieurs erreurs
        placeView.displayError("Erreur 1");
        placeView.displayError("Erreur 2");

        // Vérifier que toutes les erreurs sont affichées
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Erreur 1"), "Devrait contenir la première erreur");
        assertTrue(errorOutput.contains("Erreur 2"), "Devrait contenir la deuxième erreur");
    }

    @Test
    public void testPlaceViewCreation() {
        // Vérifier que l'objet PlaceView peut être créé
        assertNotNull(placeView, "PlaceView ne devrait pas être null");
    }

    @Test
    public void testDisplayMessageWithSpecialCharacters() {
        // Tester avec des caractères spéciaux
        String specialMessage = "Message avec des caractères spéciaux: àéèêô ñ ü";

        placeView.displayMessage(specialMessage);

        String output = outContent.toString().trim();
        assertEquals(specialMessage, output,
                "Le message avec caractères spéciaux devrait être affiché correctement");
    }

    @Test
    public void testDisplayLongMessage() {
        // Tester avec un message long
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            longMessage.append("Test ");
        }

        placeView.displayMessage(longMessage.toString());

        String output = outContent.toString();
        assertTrue(output.contains("Test"),
                "Le message long devrait être affiché");
    }
}

