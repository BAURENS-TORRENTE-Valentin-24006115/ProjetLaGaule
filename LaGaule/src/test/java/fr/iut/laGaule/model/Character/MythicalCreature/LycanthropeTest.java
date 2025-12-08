package fr.iut.laGaule.model.Character.MythicalCreature;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Lycanthrope class.
 * Tests lycanthrope character creation and basic functionality.
 */
public class LycanthropeTest {

    private Lycanthrope lycanthrope;

    @BeforeEach
    public void setUp() {
        lycanthrope = new Lycanthrope("Fenrir", "M", 1.90, 35, 80, 75);
    }

    @Test
    public void testLycanthropeCreation() {
        assertEquals("Fenrir", lycanthrope.getName());
        assertEquals(80, lycanthrope.getStrength());
        assertEquals(75, lycanthrope.getEndurance());
    }

    @Test
    public void testLycanthropeInheritsFromCharacter() {
        assertEquals(100, lycanthrope.getHealth());
        lycanthrope.receiveDamage(30);
        assertEquals(70, lycanthrope.getHealth());
    }

    @Test
    public void testLycanthropeHealthManagement() {
        lycanthrope.receiveDamage(50);
        assertEquals(50, lycanthrope.getHealth());

        lycanthrope.heal(30);
        assertEquals(80, lycanthrope.getHealth());
    }

    @Test
    public void testLycanthropeProperties() {
        assertEquals("gaul", lycanthrope.getPlace());
        lycanthrope.setPlace("forest");
        assertEquals("forest", lycanthrope.getPlace());
    }

    @Test
    public void testLycanthropeStrength() {
        lycanthrope.setStrength(90);
        assertEquals(90, lycanthrope.getStrength());
    }

    @Test
    public void testLycanthropeEndurance() {
        lycanthrope.setEndurance(85);
        assertEquals(85, lycanthrope.getEndurance());
    }
}

