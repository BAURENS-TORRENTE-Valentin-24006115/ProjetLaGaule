package fr.iut.laGaule.model.Character.Roman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Roman class.
 * Tests basic Roman character functionality.
 */
public class RomanTest {

    private Roman roman;

    @BeforeEach
    public void setUp() {
        roman = new Legionary("TestRoman", "M", 1.75, 30, 60, 55);
    }

    @Test
    public void testRomanCreation() {
        assertEquals("TestRoman", roman.getName());
        assertEquals(60, roman.getStrength());
        assertEquals(55, roman.getEndurance());
    }

    @Test
    public void testRomanInheritsFromCharacter() {
        assertEquals(100, roman.getHealth());
        roman.receiveDamage(30);
        assertEquals(70, roman.getHealth());
    }

    @Test
    public void testRomanProperties() {
        assertNull(roman.getPlace(), "La place devrait être null par défaut");
    }

    @Test
    public void testRomanHealthManagement() {
        roman.receiveDamage(50);
        assertEquals(50, roman.getHealth());

        roman.heal(30);
        assertEquals(80, roman.getHealth());
    }
}

