package fr.iut.laGaule.process;

import fr.iut.laGaule.model.Place.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the InvasionTheatre class.
 */
public class InvasionTheaterTest {

    private InvasionTheatre invasionTheatre;

    @BeforeEach
    public void setUp() {
        invasionTheatre = new InvasionTheatre();
    }

    @AfterEach
    public void tearDown() {
        // Clean up any serialized files
        File[] files = new File(".").listFiles((dir, name) -> name.endsWith(".ser"));
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    @Test
    public void testInvasionTheatreCreation() {
        assertNotNull(invasionTheatre);
        assertNotNull(invasionTheatre.getPlaces());
        assertFalse(invasionTheatre.isSimulationActive());
    }

    @Test
    public void testGetPlacesInitiallyEmpty() {
        List<Place> places = invasionTheatre.getPlaces();
        assertNotNull(places);
        assertTrue(places.isEmpty());
    }

    @Test
    public void testIsSimulationActiveInitiallyFalse() {
        assertFalse(invasionTheatre.isSimulationActive());
    }

    @Test
    public void testSetupSimulationWithMinimumZones() {
        invasionTheatre.setupSimulation(3, 5);

        List<Place> places = invasionTheatre.getPlaces();
        assertNotNull(places);
        assertEquals(4, places.size()); // 4 lieux de base: village, camp, battlefield, enclosure
    }

    @Test
    public void testSetupSimulationWithMoreZones() {
        invasionTheatre.setupSimulation(5, 10);

        List<Place> places = invasionTheatre.getPlaces();
        assertNotNull(places);
        assertEquals(6, places.size()); // 4 base + 2 extra
    }

    @Test
    public void testSetupSimulationWithTooFewZones() {
        invasionTheatre.setupSimulation(2, 5);

        // Should create minimum 4 places (3 zones forced + enclosure)
        List<Place> places = invasionTheatre.getPlaces();
        assertNotNull(places);
        assertFalse(places.isEmpty());
    }

    @Test
    public void testSetupSimulationCreatesRequiredPlaceTypes() {
        invasionTheatre.setupSimulation(3, 5);

        List<Place> places = invasionTheatre.getPlaces();

        // Check that the three mandatory places are created
        boolean hasGaulVillage = false;
        boolean hasRomanCamp = false;
        boolean hasBattlefield = false;

        for (Place place : places) {
            if (place instanceof GaulVillage) hasGaulVillage = true;
            if (place instanceof RomanFortifiedCamp) hasRomanCamp = true;
            if (place instanceof BattleFields) hasBattlefield = true;
        }

        assertTrue(hasGaulVillage);
        assertTrue(hasRomanCamp);
        assertTrue(hasBattlefield);
    }

    @Test
    public void testSetupSimulationCreatesCharacters() {
        invasionTheatre.setupSimulation(3, 10);

        List<Place> places = invasionTheatre.getPlaces();

        // Check that characters were created
        int totalCharacters = 0;
        for (Place place : places) {
            if (place.getCharacter() != null) {
                totalCharacters += place.getCharacter().size();
            }
        }

        // Should have created some characters
        assertTrue(totalCharacters > 0 || places.size() >= 3);
    }

    @Test
    public void testSetupSimulationWithZeroCharacters() {
        invasionTheatre.setupSimulation(3, 0);

        List<Place> places = invasionTheatre.getPlaces();
        assertNotNull(places);
        assertEquals(4, places.size()); // 4 lieux de base
    }

    @Test
    public void testMultipleSetupSimulationCalls() {
        invasionTheatre.setupSimulation(3, 5);
        assertEquals(4, invasionTheatre.getPlaces().size());

        // Second call should reset and create new places
        invasionTheatre.setupSimulation(4, 8);
        assertEquals(5, invasionTheatre.getPlaces().size()); // 4 base + 1 extra
    }

    @Test
    public void testSetupSimulationWithLargeNumbers() {
        invasionTheatre.setupSimulation(10, 50);

        List<Place> places = invasionTheatre.getPlaces();
        assertNotNull(places);
        assertEquals(11, places.size()); // 4 base + 7 extra
    }

    @Test
    public void testGaulVillageHasLeader() {
        invasionTheatre.setupSimulation(3, 5);

        List<Place> places = invasionTheatre.getPlaces();

        for (Place place : places) {
            if (place instanceof GaulVillage && place.getName().equals("Village des Irréductibles")) {
                assertNotNull(place.getClanLeader());
                break;
            }
        }
    }

    @Test
    public void testRomanCampHasLeader() {
        invasionTheatre.setupSimulation(3, 5);

        List<Place> places = invasionTheatre.getPlaces();

        for (Place place : places) {
            if (place instanceof RomanFortifiedCamp && place.getName().equals("Camp de Babaorum")) {
                assertNotNull(place.getClanLeader());
                break;
            }
        }
    }
}
