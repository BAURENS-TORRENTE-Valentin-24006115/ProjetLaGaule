package fr.iut.laGaule.controller;

import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.Gaul.Merchant;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import fr.iut.laGaule.model.Place.GaulVillage;
import fr.iut.laGaule.model.Place.Place;
import fr.iut.laGaule.view.PlaceView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the PlaceController class.
 * Tests controller functionality for managing places.
 */
public class PlaceControllerTest {

    private PlaceController controller;
    private Place place;
    private PlaceView view;

    @BeforeEach
    public void setUp() {
        ClanLeader leader = new ClanLeader("Abraracourcix", "M", 50);
        place = new GaulVillage("Test Village", 1000, leader, 0, new ArrayList<>(), new ArrayList<>());
        view = new PlaceView();
        controller = new PlaceController(place, view);
    }

    @Test
    public void testPlaceControllerCreation() {
        assertNotNull(controller);
    }

    @Test
    public void testDisplayPlaceInfo() {
        assertDoesNotThrow(() -> controller.displayPlaceInfo());
    }

    @Test
    public void testAddCharacterToPlace() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        controller.addCharacterToPlace(druid);

        assertTrue(place.getCharacter().contains(druid));
    }

    @Test
    public void testHealAllCharacters() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        druid.receiveDamage(40);
        place.addCharacter(druid);

        controller.healAllCharacters(20);
        assertEquals(80, druid.getHealth());
    }

    @Test
    public void testFeedAllCharacters() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        place.addCharacter(druid);
        place.addFood(Foods.SANGLIER);

        assertDoesNotThrow(() -> controller.feedAllCharacters());
    }

    @Test
    public void testFeedSpecificCharacter() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        place.addCharacter(druid);
        place.addFood(Foods.SANGLIER);

        assertDoesNotThrow(() -> controller.feedSpecificCharacter(druid));
    }

    @Test
    public void testMultipleOperations() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        Merchant merchant = new Merchant("Unhygienix", "M", 1.70, 45, 40, 50);

        controller.addCharacterToPlace(druid);
        controller.addCharacterToPlace(merchant);

        assertEquals(2, place.getNbCharacter());

        druid.receiveDamage(30);
        controller.healAllCharacters(20);

        assertEquals(90, druid.getHealth());
    }
}

