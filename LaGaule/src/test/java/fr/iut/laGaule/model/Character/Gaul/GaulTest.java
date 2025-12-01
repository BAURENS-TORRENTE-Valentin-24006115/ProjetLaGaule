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
        assertEquals("gaul", gaul.getPlace());
        gaul.setPlace("village");
        assertEquals("village", gaul.getPlace());
    }
}

