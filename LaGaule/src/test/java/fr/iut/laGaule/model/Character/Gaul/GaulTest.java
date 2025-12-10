package fr.iut.laGaule.model.Character.Gaul;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Gaul class.
 * Tests basic Gaul character functionality.
 */
public class GaulTest {

    private Gaul gaul;

    @BeforeEach
    public void setUp() {
        gaul = new Merchant("TestGaul", "M", 1.70, 45, 40, 50);
    }

    @Test
    public void testGaulCreation() {
        assertEquals("TestGaul", gaul.getName());
        assertEquals(40, gaul.getStrength());
        assertEquals(50, gaul.getEndurance());
    }

    @Test
    public void testGaulInheritsFromCharacter() {
        assertEquals(100, gaul.getHealth());
        gaul.receiveDamage(30);
        assertEquals(70, gaul.getHealth());
    }

    @Test
    public void testWork() {
        // Work method should exist
        assertDoesNotThrow(() -> gaul.work());
    }

    @Test
    public void testGaulProperties() {
        assertNull(gaul.getPlace(), "La place devrait être null par défaut");
    }

    @Test
    public void testGaulCanEat() {
        assertDoesNotThrow(() -> gaul.eat(fr.iut.laGaule.model.Consumables.Foods.Foods.SANGLIER));
    }

    @Test
    public void testGaulCanDrinkPotion() {
        assertDoesNotThrow(() -> gaul.drinkPotion(10));
    }

    @Test
    public void testGaulCanBeHealed() {
        gaul.receiveDamage(50);
        assertEquals(50, gaul.getHealth());
        gaul.heal(30);
        assertEquals(80, gaul.getHealth());
    }

    @Test
    public void testGaulSettersAndGetters() {
        gaul.setStrength(60);
        assertEquals(60, gaul.getStrength());

        gaul.setEndurance(70);
        assertEquals(70, gaul.getEndurance());
    }
}

