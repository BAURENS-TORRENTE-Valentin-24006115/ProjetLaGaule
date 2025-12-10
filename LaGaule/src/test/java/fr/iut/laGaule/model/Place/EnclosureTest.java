package fr.iut.laGaule.model.Place;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Gaul.Gaul;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.Roman.Roman;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Character.MythicalCreature.*;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Enclosure class.
 */
public class EnclosureTest {

    private Enclosure enclosure;
    private ArrayList<Character> characters;
    private ArrayList<Foods> foods;

    @BeforeEach
    public void setUp() {
        characters = new ArrayList<>();
        foods = new ArrayList<>();
        enclosure = new Enclosure("Wolf Enclosure", 500, null, 0, characters, foods);
    }

    @Test
    public void testEnclosureCreation() {
        assertEquals("Wolf Enclosure", enclosure.getName());
        assertEquals(500, enclosure.getArea());
        assertNotNull(enclosure.getCharacter());
    }

    @Test
    public void testIsAllowedCharacterLycanthrope() {
        Lycanthrope lycan = new Lycanthrope("Fenrir", "M", 1.9, 35, 80, 75,
                AgeCategory.ADULT, 5, Rank.BETA, 60, true);
        assertTrue(enclosure.isAllowedCharacter(lycan));
    }

    @Test
    public void testIsAllowedCharacterGaul() {
        Gaul gaul = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        assertFalse(enclosure.isAllowedCharacter(gaul));
    }

    @Test
    public void testIsAllowedCharacterRoman() {
        Roman roman = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        assertFalse(enclosure.isAllowedCharacter(roman));
    }

    @Test
    public void testAddCharacterLycanthrope() {
        Lycanthrope lycan = new Lycanthrope("Fenrir", "M", 1.9, 35, 80, 75,
                AgeCategory.ADULT, 5, Rank.BETA, 60, true);
        enclosure.addCharacter(lycan);
        assertTrue(enclosure.getCharacter().contains(lycan));
    }

    @Test
    public void testAddCharacterGaul() {
        Gaul gaul = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        int initialSize = enclosure.getCharacter().size();
        enclosure.addCharacter(gaul);
        // Gaul should not be added
        assertEquals(initialSize, enclosure.getCharacter().size());
    }

    @Test
    public void testAddCharacterRoman() {
        Roman roman = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        int initialSize = enclosure.getCharacter().size();
        enclosure.addCharacter(roman);
        // Roman should not be added
        assertEquals(initialSize, enclosure.getCharacter().size());
    }

    @Test
    public void testAddCharacterNull() {
        int initialSize = enclosure.getCharacter().size();
        enclosure.addCharacter(null);
        assertEquals(initialSize, enclosure.getCharacter().size());
    }

    @Test
    public void testConstructorWithMixedCharacters() {
        ArrayList<Character> mixedChars = new ArrayList<>();
        Lycanthrope lycan = new Lycanthrope("Fenrir", "M", 1.9, 35, 80, 75,
                AgeCategory.ADULT, 5, Rank.BETA, 60, true);
        Gaul gaul = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        mixedChars.add(lycan);
        mixedChars.add(gaul);

        Enclosure mixedEnclosure = new Enclosure("Mixed Enclosure", 500, null, 0, mixedChars, new ArrayList<>());
        // Only lycanthrope should be added
        assertEquals(1, mixedEnclosure.getCharacter().size());
        assertTrue(mixedEnclosure.getCharacter().contains(lycan));
    }

    @Test
    public void testConstructorWithNullCharacters() {
        Enclosure nullCharEnclosure = new Enclosure("Test", 100, null, 0, null, new ArrayList<>());
        assertNotNull(nullCharEnclosure.getCharacter());
    }

    @Test
    public void testSetClanLeader() {
        ClanLeader leader = new ClanLeader("ZooKeeper", "M", 40);
        enclosure.setClanLeader(leader);
        assertEquals(leader, enclosure.getClanLeader());
    }

    @Test
    public void testAddFood() {
        enclosure.addFood(Foods.SANGLIER);
        assertTrue(enclosure.getFood().contains(Foods.SANGLIER));
    }
}
