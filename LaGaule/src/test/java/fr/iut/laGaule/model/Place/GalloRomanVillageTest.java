package fr.iut.laGaule.model.Place;

import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.Gaul.Merchant;
import fr.iut.laGaule.model.Character.MythicalCreature.Lycanthrope;
import fr.iut.laGaule.model.Character.Roman.General;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GalloRomanVillage class.
 * Tests mixed village functionality where both Gauls and Romans can coexist.
 */
public class GalloRomanVillageTest {

    private GalloRomanVillage village;
    private ClanLeader leader;

    @BeforeEach
    public void setUp() {
        leader = new ClanLeader("Mediator", "M", 50);
        village = new GalloRomanVillage("Mixed Village", 1200, leader, 0, new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void testGalloRomanVillageCreation() {
        assertEquals("Mixed Village", village.getName());
        assertEquals(1200, village.getArea());
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
    public void testAddRomanCharacter() {
        Legionary legionary = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        village.addCharacter(legionary);

        assertTrue(village.getCharacter().contains(legionary));
        assertEquals(1, village.getNbCharacter());
    }

    @Test
    public void testAddBothGaulAndRoman() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        General general = new General("Pompey", "M", 1.82, 45, 70, 65);

        village.addCharacter(druid);
        village.addCharacter(general);

        assertTrue(village.getCharacter().contains(druid));
        assertTrue(village.getCharacter().contains(general));
        assertEquals(2, village.getNbCharacter());
    }

    @Test
    public void testAddLycanthropeIsBlocked() {
        Lycanthrope lycanthrope = new Lycanthrope("Fenrir", "M", 1.90, 35, 80, 75);
        village.addCharacter(lycanthrope);

        assertFalse(village.getCharacter().contains(lycanthrope));
        assertEquals(0, village.getNbCharacter());
    }

    @Test
    public void testConstructorFiltersCharacters() {
        ArrayList<fr.iut.laGaule.model.Character.Character> characters = new ArrayList<>();
        characters.add(new Druid("Panoramix", "M", 1.75, 60, 50, 60));
        characters.add(new Lycanthrope("Fenrir", "M", 1.90, 35, 80, 75));
        characters.add(new Merchant("Unhygienix", "M", 1.70, 45, 40, 50));
        characters.add(new General("Pompey", "M", 1.82, 45, 70, 65));

        ArrayList<Foods> foods = new ArrayList<>();

        GalloRomanVillage filteredVillage = new GalloRomanVillage("Filtered", 1200, leader, 4, characters, foods);

        // Should have 3 characters (Gauls and Romans, no Lycanthrope)
        assertEquals(3, filteredVillage.getCharacter().size());
    }
}

