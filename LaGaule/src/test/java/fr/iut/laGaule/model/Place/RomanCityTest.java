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
 * Unit tests for the RomanCity class.
 */
public class RomanCityTest {

    private RomanCity city;
    private ArrayList<Character> characters;
    private ArrayList<Foods> foods;

    @BeforeEach
    public void setUp() {
        characters = new ArrayList<>();
        foods = new ArrayList<>();
        city = new RomanCity("Lutetia", 1000, null, 0, characters, foods);
    }

    @Test
    public void testRomanCityCreation() {
        assertEquals("Lutetia", city.getName());
        assertEquals(1000, city.getArea());
        assertNotNull(city.getCharacter());
    }

    @Test
    public void testIsAllowedCharacterRoman() {
        Roman roman = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        assertTrue(city.isAllowedCharacter(roman));
    }

    @Test
    public void testIsAllowedCharacterLycanthrope() {
        Lycanthrope lycan = new Lycanthrope("Fenrir", "M", 1.9, 35, 80, 75,
                AgeCategory.ADULT, 5, Rank.BETA, 60, originGaul);
        assertTrue(city.isAllowedCharacter(lycan));
    }

    @Test
    public void testIsAllowedCharacterGaul() {
        Gaul gaul = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        assertFalse(city.isAllowedCharacter(gaul));
    }

    @Test
    public void testAddCharacterRoman() {
        Roman roman = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        city.addCharacter(roman);
        assertTrue(city.getCharacter().contains(roman));
    }

    @Test
    public void testAddCharacterLycanthrope() {
        Lycanthrope lycan = new Lycanthrope("Fenrir", "M", 1.9, 35, 80, 75,
                AgeCategory.ADULT, 5, Rank.BETA, 60, originGaul);
        city.addCharacter(lycan);
        assertTrue(city.getCharacter().contains(lycan));
    }

    @Test
    public void testAddCharacterGaul() {
        Gaul gaul = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        int initialSize = city.getCharacter().size();
        city.addCharacter(gaul);
        // Gaul should not be added
        assertEquals(initialSize, city.getCharacter().size());
    }

    @Test
    public void testAddCharacterNull() {
        int initialSize = city.getCharacter().size();
        city.addCharacter(null);
        assertEquals(initialSize, city.getCharacter().size());
    }

    @Test
    public void testConstructorWithMixedCharacters() {
        ArrayList<Character> mixedChars = new ArrayList<>();
        Roman roman = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        Gaul gaul = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        mixedChars.add(roman);
        mixedChars.add(gaul);

        RomanCity mixedCity = new RomanCity("Mixed City", 500, null, 0, mixedChars, new ArrayList<>());
        // Only Roman should be added
        assertEquals(1, mixedCity.getCharacter().size());
        assertTrue(mixedCity.getCharacter().contains(roman));
    }

    @Test
    public void testConstructorWithNullCharacters() {
        RomanCity nullCharCity = new RomanCity("Test", 100, null, 0, null, new ArrayList<>());
        assertNotNull(nullCharCity.getCharacter());
    }

    @Test
    public void testSetClanLeader() {
        ClanLeader leader = new ClanLeader("Prefect", "M", 45);
        city.setClanLeader(leader);
        assertEquals(leader, city.getClanLeader());
    }

    @Test
    public void testAddFood() {
        city.addFood(Foods.VIN);
        assertTrue(city.getFood().contains(Foods.VIN));
    }
}
