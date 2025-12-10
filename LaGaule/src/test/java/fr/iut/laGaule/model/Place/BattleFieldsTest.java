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
 * Unit tests for the BattleFields class.
 */
class BattleFieldsTest {

    private BattleFields battlefield;
    private ArrayList<Character> characters;
    private ArrayList<Foods> foods;

    @BeforeEach
    void setUp() {
        characters = new ArrayList<>();
        foods = new ArrayList<>();
        battlefield = new BattleFields("Grande Plaine", 1000, null, 0, characters, foods);
    }

    @Test
    void testConstructor() {
        assertEquals("Grande Plaine", battlefield.getName());
        assertEquals(1000, battlefield.getArea());
        assertNotNull(battlefield.getCharacter());
    }

    @Test
    void testIsAllowedCharacterGaul() {
        Gaul gaul = new Gaul("Astérix", "M", 1.50, 35, 80, 70);
        assertTrue(battlefield.isAllowedCharacter(gaul));
    }

    @Test
    void testIsAllowedCharacterRoman() {
        Roman roman = new Roman("Marcus", "M", 1.75, 30, 60, 65);
        assertTrue(battlefield.isAllowedCharacter(roman));
    }

    @Test
    void testIsAllowedCharacterLycanthrope() {
        Lycanthrope lycan = new Lycanthrope("Fenrir", "M", 2.0, 100, 90, 85,
                AgeCategory.ADULT, 5, Rank.BETA, 60);
        assertTrue(battlefield.isAllowedCharacter(lycan));
    }

    @Test
    void testAddCharacterGaul() {
        Gaul gaul = new Gaul("Astérix", "M", 1.50, 35, 80, 70);
        battlefield.addCharacter(gaul);
        assertTrue(battlefield.getCharacter().contains(gaul));
    }

    @Test
    void testAddCharacterRoman() {
        Roman roman = new Roman("Marcus", "M", 1.75, 30, 60, 65);
        battlefield.addCharacter(roman);
        assertTrue(battlefield.getCharacter().contains(roman));
    }

    @Test
    void testAddCharacterNull() {
        int initialSize = battlefield.getCharacter().size();
        battlefield.addCharacter(null);
        assertEquals(initialSize, battlefield.getCharacter().size());
    }

    @Test
    void testConstructorWithMixedCharacters() {
        ArrayList<Character> mixedChars = new ArrayList<>();
        mixedChars.add(new Gaul("Astérix", "M", 1.50, 35, 80, 70));
        mixedChars.add(new Roman("Marcus", "M", 1.75, 30, 60, 65));

        BattleFields mixedBattlefield = new BattleFields("Test Battlefield", 500, null, 0, mixedChars, new ArrayList<>());

        // Both should be added
        assertEquals(2, mixedBattlefield.getCharacter().size());
    }

    @Test
    void testSetClanLeader() {
        ClanLeader leader = new ClanLeader("Commander", "M", 45);
        battlefield.setClanLeader(leader);
        assertEquals(leader, battlefield.getClanLeader());
    }

    @Test
    void testAddFood() {
        battlefield.addFood(Foods.SANGLIER);
        assertTrue(battlefield.getFood().contains(Foods.SANGLIER));
    }

    @Test
    void testHealCharacters() {
        Gaul gaul = new Gaul("Astérix", "M", 1.50, 35, 80, 70);
        gaul.receiveDamage(40);
        battlefield.addCharacter(gaul);

        battlefield.healCharacters(20);
        assertEquals(80, gaul.getHealth());
    }

    @Test
    void testFeedCharacters() {
        Gaul gaul = new Gaul("Astérix", "M", 1.50, 35, 80, 70);
        battlefield.addCharacter(gaul);
        battlefield.addFood(Foods.SANGLIER);

        int initialFoodSize = battlefield.getFood().size();
        battlefield.feedCharacters();
        assertEquals(initialFoodSize - 1, battlefield.getFood().size());
    }

    @Test
    void testToString() {
        String result = battlefield.toString();
        assertNotNull(result);
        assertTrue(result.contains("Superficie"));
    }

    @Test
    void testAddLycanthrope() {
        Lycanthrope lycan = new Lycanthrope("Fenrir", "M", 2.0, 100, 90, 85,
                AgeCategory.ADULT, 5, Rank.BETA, 60);
        battlefield.addCharacter(lycan);
        assertTrue(battlefield.getCharacter().contains(lycan));
    }

    @Test
    void testConstructorWithNullCharacters() {
        BattleFields nullCharBattlefield = new BattleFields("Test", 100, null, 0, new ArrayList<>(), new ArrayList<>());
        assertNotNull(nullCharBattlefield.getCharacter());
        assertTrue(nullCharBattlefield.getCharacter().isEmpty());
    }
}
