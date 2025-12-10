package fr.iut.laGaule.model.Place;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Gaul.Gaul;
import fr.iut.laGaule.model.Character.Roman.Roman;
import fr.iut.laGaule.model.Character.MythicalCreature.*;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GaulVillage class.
 */
class GaulVillageTest {

    private GaulVillage village;
    private ArrayList<Character> characters;
    private ArrayList<Foods> foods;

    @BeforeEach
    void setUp() {
        characters = new ArrayList<>();
        foods = new ArrayList<>();
        foods.add(Foods.SANGLIER);
        foods.add(Foods.POISSON_FRAIS);
        village = new GaulVillage("Village des Irréductibles", 200, null, 0, characters, foods);
    }

    @Test
    void testConstructor() {
        assertEquals("Village des Irréductibles", village.getName());
        assertEquals(200, village.getArea());
        assertNotNull(village.getCharacter());
        assertNotNull(village.getFood());
    }

    @Test
    void testIsAllowedCharacterGaul() {
        Gaul gaul = new Gaul("Astérix", "M", 1.50, 35, 80, 70);
        assertTrue(village.isAllowedCharacter(gaul));
    }

    @Test
    void testIsAllowedCharacterLycanthrope() {
        Lycanthrope lycan = new Lycanthrope("Fenrir", "M", 2.0, 100, 90, 85,
                AgeCategory.ADULT, 5, Rank.BETA, 60, true);
        assertTrue(village.isAllowedCharacter(lycan));
    }

    @Test
    void testIsAllowedCharacterRoman() {
        Roman roman = new Roman("Marcus", "M", 1.75, 30, 60, 65);
        assertFalse(village.isAllowedCharacter(roman));
    }

    @Test
    void testAddCharacterGaul() {
        Gaul gaul = new Gaul("Astérix", "M", 1.50, 35, 80, 70);
        village.addCharacter(gaul);
        assertTrue(village.getCharacter().contains(gaul));
    }

    @Test
    void testAddCharacterRoman() {
        Roman roman = new Roman("Marcus", "M", 1.75, 30, 60, 65);
        int initialSize = village.getCharacter().size();
        village.addCharacter(roman);
        // Roman should not be added
        assertEquals(initialSize, village.getCharacter().size());
    }

    @Test
    void testAddCharacterNull() {
        int initialSize = village.getCharacter().size();
        village.addCharacter(null);
        assertEquals(initialSize, village.getCharacter().size());
    }

    @Test
    void testConstructorWithMixedCharacters() {
        ArrayList<Character> mixedChars = new ArrayList<>();
        mixedChars.add(new Gaul("Astérix", "M", 1.50, 35, 80, 70));
        mixedChars.add(new Roman("Marcus", "M", 1.75, 30, 60, 65)); // Should be filtered out

        GaulVillage mixedVillage = new GaulVillage("Test Village", 100, null, 0, mixedChars, new ArrayList<>());

        // Only Gaul should be added
        assertEquals(1, mixedVillage.getCharacter().size());
    }

    @Test
    void testSetClanLeader() {
        ClanLeader leader = new ClanLeader("Abraracourcix", "M", 50);
        village.setClanLeader(leader);
        assertEquals(leader, village.getClanLeader());
    }

    @Test
    void testConstructorWithNullCharacters() {
        GaulVillage v = new GaulVillage("Test", 100, null, 0, null, new ArrayList<>());
        assertNotNull(v.getCharacter());
    }

    @Test
    void testAddFood() {
        village.addFood(Foods.MIEL);
        assertTrue(village.getFood().contains(Foods.MIEL));
    }

    @Test
    void testHealCharacters() {
        Gaul gaul = new Gaul("Astérix", "M", 1.50, 35, 80, 70);
        gaul.receiveDamage(40);
        village.addCharacter(gaul);

        village.healCharacters(20);
        assertEquals(80, gaul.getHealth());
    }

    @Test
    void testFeedCharacters() {
        Gaul gaul = new Gaul("Astérix", "M", 1.50, 35, 80, 70);
        village.addCharacter(gaul);

        int initialFoodSize = village.getFood().size();
        village.feedCharacters();
        assertEquals(initialFoodSize - 1, village.getFood().size());
    }

    @Test
    void testAddLycanthrope() {
        Lycanthrope lycan = new Lycanthrope("Fenrir", "M", 2.0, 100, 90, 85,
                AgeCategory.ADULT, 5, Rank.BETA, 60, true);
        village.addCharacter(lycan);
        assertTrue(village.getCharacter().contains(lycan));
    }

    @Test
    void testToString() {
        String result = village.toString();
        assertNotNull(result);
        assertTrue(result.contains("Superficie"));
    }
}
