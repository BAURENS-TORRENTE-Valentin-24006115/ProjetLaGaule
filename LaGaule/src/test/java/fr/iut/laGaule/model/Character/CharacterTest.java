package fr.iut.laGaule.model.Character;

import fr.iut.laGaule.model.Consumables.Foods.Foods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Character class.
 * Tests common character behaviors such as health management, damage, healing, and food consumption.
 */
public class CharacterTest {

    private TestCharacter character;

    /**
     * Concrete implementation of Character for testing purposes.
     */
    private static class TestCharacter extends Character {
        public TestCharacter(String name, String sex, double height, int age, int strength, int endurance) {
            super(name, sex, height, age, strength, endurance);
        }
    }

    @BeforeEach
    public void setUp() {
        character = new TestCharacter("TestHero", "M", 1.75, 30, 50, 60);
    }

    @Test
    public void testCharacterInitialization() {
        assertEquals("TestHero", character.getName());
        assertEquals(100, character.getHealth());
        assertEquals(50, character.getStrength());
        assertEquals(60, character.getEndurance());
    }

    @Test
    public void testReceiveDamage() {
        character.receiveDamage(30);
        assertEquals(70, character.getHealth());
    }

    @Test
    public void testReceiveFatalDamage() {
        character.receiveDamage(150);
        assertEquals(0, character.getHealth());
    }

    @Test
    public void testHeal() {
        character.receiveDamage(40);
        character.heal(20);
        assertEquals(80, character.getHealth());
    }

    @Test
    public void testHealBeyondMax() {
        character.heal(50);
        assertEquals(100, character.getHealth());
    }

    @Test
    public void testDrinkPotion() {
        character.drinkPotion(10);
        // Just verify no exception is thrown
        assertTrue(true);
    }

    @Test
    public void testEat() {
        character.eat(Foods.SANGLIER);
        // Just verify no exception is thrown
        assertTrue(true);
    }

    @Test
    public void testSettersAndGetters() {
        character.setStrength(75);
        assertEquals(75, character.getStrength());

        character.setEndurance(85);
        assertEquals(85, character.getEndurance());
    }

    @Test
    public void testGetPlace() {
        assertNull(character.getPlace(), "La place devrait être null par défaut");
    }

    @Test
    public void testDrinkPotionMultipleTimes() {
        character.drinkPotion(10);
        character.drinkPotion(5);
        // Verify no exception
        assertTrue(true);
    }

    @Test
    public void testEatMultipleTimes() {
        character.eat(Foods.SANGLIER);
        character.eat(Foods.POISSON_FRAIS);
        character.eat(Foods.MIEL);
        // Verify no exception
        assertTrue(true);
    }

    @Test
    public void testDieWhenHealthDropsToZero() {
        character.receiveDamage(100);
        assertEquals(0, character.getHealth());
    }

    @Test
    public void testDieWhenHealthDropsBelowZero() {
        character.receiveDamage(150);
        assertEquals(0, character.getHealth());
    }

    @Test
    public void testHealToMax() {
        character.receiveDamage(50);
        character.heal(100);
        assertEquals(100, character.getHealth());
    }

    @Test
    public void testMultipleDamageAndHeal() {
        character.receiveDamage(20);
        assertEquals(80, character.getHealth());

        character.heal(10);
        assertEquals(90, character.getHealth());

        character.receiveDamage(30);
        assertEquals(60, character.getHealth());

        character.heal(40);
        assertEquals(100, character.getHealth());
    }

    @Test
    public void testAllGetters() {
        assertEquals("TestHero", character.getName());
        assertEquals(100, character.getHealth());
        assertEquals(50, character.getStrength());
        assertEquals(60, character.getEndurance());
        assertNull(character.getPlace(), "La place devrait être null par défaut");
    }
}

