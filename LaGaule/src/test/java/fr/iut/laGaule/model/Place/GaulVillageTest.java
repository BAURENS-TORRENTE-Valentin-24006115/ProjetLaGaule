package fr.iut.laGaule.model.Place;

import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.Gaul.Merchant;
import fr.iut.laGaule.model.Character.MythicalCreature.Lycanthrope;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GaulVillage class.
 * Tests Gaul village specific functionality including character restrictions.
 */
public class GaulVillageTest {

    private GaulVillage village;
    private ClanLeader leader;

    @BeforeEach
    public void setUp() {
        leader = new ClanLeader("Abraracourcix", "M", 50);
        village = new GaulVillage("Village Gaulois", 1000, leader, 0, new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void testGaulVillageCreation() {
        assertEquals("Village Gaulois", village.getName());
        assertEquals(1000, village.getArea());
        assertEquals(leader, village.getClanLeader());
    }

    @Test
    public void testAddGaulCharacter() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        village.addCharacter(druid);

        assertTrue(village.getCharacter().contains(druid));
        assertEquals(1, village.getNbCharacter());
    }

    @Test
    public void testAddLycanthrope() {
        Lycanthrope lycanthrope = new Lycanthrope("Fenrir", "M", 1.90, 35, 80, 75);
        village.addCharacter(lycanthrope);

        assertTrue(village.getCharacter().contains(lycanthrope));
    }

    @Test
    public void testAddRomanCharacterIsBlocked() {
        Legionary roman = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        village.addCharacter(roman);

        assertFalse(village.getCharacter().contains(roman));
        assertEquals(0, village.getNbCharacter());
    }

    @Test
    public void testAddNullCharacter() {
        int initialSize = village.getNbCharacter();
        village.addCharacter(null);
        assertEquals(initialSize, village.getNbCharacter());
    }

    @Test
    public void testConstructorFiltersCharacters() {
        ArrayList<fr.iut.laGaule.model.Character.Character> characters = new ArrayList<>();
        characters.add(new Druid("Panoramix", "M", 1.75, 60, 50, 60));
        characters.add(new Legionary("Brutus", "M", 1.75, 30, 60, 55));
        characters.add(new Merchant("Unhygienix", "M", 1.70, 45, 40, 50));

        ArrayList<Foods> foods = new ArrayList<>();

        GaulVillage filteredVillage = new GaulVillage("Filtered", 1000, leader, 3, characters, foods);

        // Should only have 2 Gauls, not the Roman
        assertEquals(2, filteredVillage.getCharacter().size());
    }

    @Test
    public void testAddMultipleGauls() {
        village.addCharacter(new Druid("Panoramix", "M", 1.75, 60, 50, 60));
        village.addCharacter(new Merchant("Unhygienix", "M", 1.70, 45, 40, 50));

        assertEquals(2, village.getNbCharacter());
    }
}

