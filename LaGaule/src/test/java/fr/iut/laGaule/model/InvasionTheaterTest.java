package fr.iut.laGaule.model;

import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Place.GaulVillage;
import fr.iut.laGaule.model.Place.Place;
import fr.iut.laGaule.model.Place.RomanCity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the InvasionTheater class.
 * Tests theater management functionality.
 */
public class InvasionTheaterTest {

    private InvasionTheater theater;
    private Place gaulVillage;
    private Place romanCity;
    private ClanLeader gaulLeader;
    private ClanLeader romanLeader;

    @BeforeEach
    public void setUp() {
        theater = new InvasionTheater("Gaul", 10);

        gaulLeader = new ClanLeader("Abraracourcix", "M", 50);
        romanLeader = new ClanLeader("Caesar", "M", 50);

        gaulVillage = new GaulVillage("Village Gaulois", 1000, gaulLeader, 0, new ArrayList<>(), new ArrayList<>());
        romanCity = new RomanCity("Roman City", 2000, romanLeader, 0, new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void testInvasionTheaterCreation() {
        assertEquals("Gaul", theater.getNom());
        assertEquals(10, theater.getMaxLieux());
        assertNotNull(theater.getLieux());
        assertNotNull(theater.getChefsClans());
    }

    @Test
    public void testAjouterLieu() {
        theater.ajouterLieu(gaulVillage);

        assertTrue(theater.getLieux().contains(gaulVillage));
        assertEquals(1, theater.getLieux().size());
    }

    @Test
    public void testAjouterLieuWithLeader() {
        theater.ajouterLieu(gaulVillage);

        assertTrue(theater.getChefsClans().contains(gaulLeader));
    }

    @Test
    public void testAjouterMultipleLieux() {
        theater.ajouterLieu(gaulVillage);
        theater.ajouterLieu(romanCity);

        assertEquals(2, theater.getLieux().size());
        assertEquals(2, theater.getChefsClans().size());
    }

    @Test
    public void testAjouterLieuAtMaxCapacity() {
        InvasionTheater smallTheater = new InvasionTheater("Small", 1);
        smallTheater.ajouterLieu(gaulVillage);
        smallTheater.ajouterLieu(romanCity);

        // Should only have 1 place (max capacity)
        assertEquals(1, smallTheater.getLieux().size());
    }

    @Test
    public void testCompterPersonnages() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        gaulVillage.addCharacter(druid);

        Legionary legionary = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        romanCity.addCharacter(legionary);

        theater.ajouterLieu(gaulVillage);
        theater.ajouterLieu(romanCity);

        assertEquals(2, theater.compterPersonnages());
    }

    @Test
    public void testCompterPersonnagesEmpty() {
        theater.ajouterLieu(gaulVillage);
        assertEquals(0, theater.compterPersonnages());
    }

    @Test
    public void testAfficherLieux() {
        theater.ajouterLieu(gaulVillage);
        assertDoesNotThrow(() -> theater.afficherLieux());
    }

    @Test
    public void testAfficherLieuxEmpty() {
        assertDoesNotThrow(() -> theater.afficherLieux());
    }

    @Test
    public void testAfficherTousLesPersonnages() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        gaulVillage.addCharacter(druid);
        theater.ajouterLieu(gaulVillage);

        assertDoesNotThrow(() -> theater.afficherTousLesPersonnages());
    }

    @Test
    public void testLancerSimulation() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        gaulVillage.addCharacter(druid);
        theater.ajouterLieu(gaulVillage);

        // Run a short simulation
        assertDoesNotThrow(() -> theater.lancerSimulation(1));
    }
}

