package fr.iut.laGaule.model.Place;

import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.MythicalCreature.Lycanthrope;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Enclosure class.
 * Tests enclosure functionality where only Lycanthropes are allowed.
 */
public class EnclosureTest {

    private Enclosure enclosure;
    private ClanLeader leader;

    @BeforeEach
    public void setUp() {
        leader = new ClanLeader("Keeper", "M", 45);
        enclosure = new Enclosure("Beast Enclosure", 500, leader, 0, new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void testEnclosureCreation() {
        assertEquals("Beast Enclosure", enclosure.getName());
        assertEquals(500, enclosure.getArea());
        assertEquals(leader, enclosure.getClanLeader());
    }

    @Test
    public void testAddLycanthrope() {
        Lycanthrope lycanthrope = new Lycanthrope("Fenrir", "M", 1.90, 35, 80, 75);
        enclosure.addCharacter(lycanthrope);

        assertTrue(enclosure.getCharacter().contains(lycanthrope));
        assertEquals(1, enclosure.getNbCharacter());
    }

    @Test
    public void testAddGaulIsBlocked() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        enclosure.addCharacter(druid);

        assertFalse(enclosure.getCharacter().contains(druid));
        assertEquals(0, enclosure.getNbCharacter());
    }

    @Test
    public void testAddRomanIsBlocked() {
        Legionary legionary = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        enclosure.addCharacter(legionary);

        assertFalse(enclosure.getCharacter().contains(legionary));
        assertEquals(0, enclosure.getNbCharacter());
    }

    @Test
    public void testAddMultipleLycanthropes() {
        Lycanthrope lycan1 = new Lycanthrope("Fenrir", "M", 1.90, 35, 80, 75);
        Lycanthrope lycan2 = new Lycanthrope("Garm", "M", 1.85, 30, 75, 70);

        enclosure.addCharacter(lycan1);
        enclosure.addCharacter(lycan2);

        assertEquals(2, enclosure.getNbCharacter());
        assertTrue(enclosure.getCharacter().contains(lycan1));
        assertTrue(enclosure.getCharacter().contains(lycan2));
    }

    @Test
    public void testConstructorFiltersCharacters() {
        ArrayList<fr.iut.laGaule.model.Character.Character> characters = new ArrayList<>();
        characters.add(new Lycanthrope("Fenrir", "M", 1.90, 35, 80, 75));
        characters.add(new Druid("Panoramix", "M", 1.75, 60, 50, 60));
        characters.add(new Lycanthrope("Garm", "M", 1.85, 30, 75, 70));
        characters.add(new Legionary("Brutus", "M", 1.75, 30, 60, 55));

        ArrayList<Foods> foods = new ArrayList<>();

        Enclosure filteredEnclosure = new Enclosure("Filtered", 500, leader, 4, characters, foods);

        // Should only have 2 Lycanthropes
        assertEquals(2, filteredEnclosure.getCharacter().size());
    }
}

