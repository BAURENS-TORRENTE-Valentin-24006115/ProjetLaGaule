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
 * Unit tests for the GalloRomanVillage class.
 */
public class GalloRomanVillageTest {

    private GalloRomanVillage village;
    private ArrayList<Character> characters;
    private ArrayList<Foods> foods;

    @BeforeEach
    public void setUp() {
        characters = new ArrayList<>();
        foods = new ArrayList<>();
        village = new GalloRomanVillage("Village Gallo-Romain", 800, null, 0, characters, foods);
    }

    @Test
    public void testGalloRomanVillageCreation() {
        assertEquals("Village Gallo-Romain", village.getName());
        assertEquals(800, village.getArea());
        assertNotNull(village.getCharacter());
    }

    @Test
    public void testIsAllowedCharacterGaul() {
        Gaul gaul = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        assertTrue(village.isAllowedCharacter(gaul));
    }

    @Test
    public void testIsAllowedCharacterRoman() {
        Roman roman = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        assertTrue(village.isAllowedCharacter(roman));
    }

    @Test
    public void testIsAllowedCharacterLycanthrope() {
        Lycanthrope lycan = new Lycanthrope("Fenrir", "M", 1.9, 35, 80, 75,
                AgeCategory.ADULT, 5, Rank.BETA, 60, true);
        // Lycanthrope is not a Gaul or Roman
        assertFalse(village.isAllowedCharacter(lycan));
    }

    @Test
    public void testAddCharacterGaul() {
        Gaul gaul = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        village.addCharacter(gaul);
        assertTrue(village.getCharacter().contains(gaul));
    }

    @Test
    public void testAddCharacterRoman() {
        Roman roman = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        village.addCharacter(roman);
        assertTrue(village.getCharacter().contains(roman));
    }

    @Test
    public void testAddCharacterLycanthrope() {
        Lycanthrope lycan = new Lycanthrope("Fenrir", "M", 1.9, 35, 80, 75,
                AgeCategory.ADULT, 5, Rank.BETA, 60, true);
        int initialSize = village.getCharacter().size();
        village.addCharacter(lycan);
        // Lycanthrope should not be added
        assertEquals(initialSize, village.getCharacter().size());
    }

    @Test
    public void testAddCharacterNull() {
        int initialSize = village.getCharacter().size();
        village.addCharacter(null);
        assertEquals(initialSize, village.getCharacter().size());
    }

    @Test
    public void testConstructorWithMixedCharacters() {
        ArrayList<Character> mixedChars = new ArrayList<>();
        Gaul gaul = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        Roman roman = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        Lycanthrope lycan = new Lycanthrope("Fenrir", "M", 1.9, 35, 80, 75,
                AgeCategory.ADULT, 5, Rank.BETA, 60, true);
        mixedChars.add(gaul);
        mixedChars.add(roman);
        mixedChars.add(lycan);

        GalloRomanVillage mixedVillage = new GalloRomanVillage("Mixed Village", 500, null, 0, mixedChars, new ArrayList<>());
        // Only Gaul and Roman should be added (2 characters)
        assertEquals(2, mixedVillage.getCharacter().size());
        assertTrue(mixedVillage.getCharacter().contains(gaul));
        assertTrue(mixedVillage.getCharacter().contains(roman));
    }

    @Test
    public void testConstructorWithNullCharacters() {
        GalloRomanVillage nullCharVillage = new GalloRomanVillage("Test", 100, null, 0, null, new ArrayList<>());
        assertNotNull(nullCharVillage.getCharacter());
    }

    @Test
    public void testSetClanLeader() {
        ClanLeader leader = new ClanLeader("Mayor", "M", 50);
        village.setClanLeader(leader);
        assertEquals(leader, village.getClanLeader());
    }

    @Test
    public void testAddFood() {
        village.addFood(Foods.VIN);
        assertTrue(village.getFood().contains(Foods.VIN));
    }

    @Test
    public void testHealCharacters() {
        Gaul gaul = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        gaul.receiveDamage(40);
        village.addCharacter(gaul);

        village.healCharacters(20);
        assertEquals(80, gaul.getHealth());
    }

    @Test
    public void testFeedCharacters() {
        Gaul gaul = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        village.addCharacter(gaul);
        village.addFood(Foods.SANGLIER);

        int initialFoodSize = village.getFood().size();
        village.feedCharacters();
        assertEquals(initialFoodSize - 1, village.getFood().size());
    }
}
