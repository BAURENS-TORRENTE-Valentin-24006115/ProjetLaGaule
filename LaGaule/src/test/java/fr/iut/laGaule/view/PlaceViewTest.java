package fr.iut.laGaule.view;

import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Place.GaulVillage;
import fr.iut.laGaule.model.Place.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the PlaceView class.
 * Tests view functionality for displaying place information.
 */
public class PlaceViewTest {

    private PlaceView view;
    private Place place;

    @BeforeEach
    public void setUp() {
        view = new PlaceView();
        ClanLeader leader = new ClanLeader("Abraracourcix", "M", 50);
        place = new GaulVillage("Test Village", 1000, leader, 0, new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void testPlaceViewCreation() {
        assertNotNull(view);
    }

    @Test
    public void testDisplayPlaceDetails() {
        assertDoesNotThrow(() -> view.displayPlaceDetails(place));
    }

    @Test
    public void testDisplayPlaceDetailsWithCharacters() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        place.addCharacter(druid);

        assertDoesNotThrow(() -> view.displayPlaceDetails(place));
    }

    @Test
    public void testDisplayMessage() {
        assertDoesNotThrow(() -> view.displayMessage("Test message"));
    }

    @Test
    public void testDisplayError() {
        assertDoesNotThrow(() -> view.displayError("Test error"));
    }

    @Test
    public void testDisplayMultipleMessages() {
        assertDoesNotThrow(() -> {
            view.displayMessage("Message 1");
            view.displayMessage("Message 2");
            view.displayError("Error 1");
        });
    }
}

