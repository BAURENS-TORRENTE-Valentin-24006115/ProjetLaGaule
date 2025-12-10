package fr.iut.laGaule.model.Character.MythicalCreature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Pregnancy class.
 */
public class PregnancyTest {

    private Lycanthrope mother;
    private Pregnancy pregnancy;

    @BeforeEach
    public void setUp() {
        mother = new Lycanthrope(
                "AlphaFemale", "female", 1.7, 5, 80, 80,
                AgeCategory.ADULT, 40, Rank.ALPHA, 25
        );
        pregnancy = new Pregnancy(mother, 5, 3);
    }

    @Test
    public void testPregnancyCreation() {
        assertNotNull(pregnancy);
        assertEquals(mother, pregnancy.getMother());
        assertEquals(5, pregnancy.getGestationTurnsRemaining());
        assertEquals(3, pregnancy.getLitterSize());
    }

    @Test
    public void testGetMother() {
        assertEquals(mother, pregnancy.getMother());
    }

    @Test
    public void testGetGestationTurnsRemaining() {
        assertEquals(5, pregnancy.getGestationTurnsRemaining());
    }

    @Test
    public void testGetLitterSize() {
        assertEquals(3, pregnancy.getLitterSize());
    }

    @Test
    public void testDecrementGestation() {
        pregnancy.decrementGestation();
        assertEquals(4, pregnancy.getGestationTurnsRemaining());
    }

    @Test
    public void testMultipleDecrements() {
        pregnancy.decrementGestation();
        pregnancy.decrementGestation();
        pregnancy.decrementGestation();
        assertEquals(2, pregnancy.getGestationTurnsRemaining());
    }

    @Test
    public void testIsReadyToBirthFalse() {
        assertFalse(pregnancy.isReadyToBirth());
    }

    @Test
    public void testIsReadyToBirthTrue() {
        // Decrement 5 times to reach 0
        for (int i = 0; i < 5; i++) {
            pregnancy.decrementGestation();
        }
        assertTrue(pregnancy.isReadyToBirth());
    }

    @Test
    public void testIsReadyToBirthNegative() {
        // Decrement more than gestation time
        for (int i = 0; i < 7; i++) {
            pregnancy.decrementGestation();
        }
        assertTrue(pregnancy.isReadyToBirth());
    }

    @Test
    public void testToString() {
        String result = pregnancy.toString();
        assertNotNull(result);
        assertTrue(result.contains("Pregnancy"));
        assertTrue(result.contains("AlphaFemale"));
        assertTrue(result.contains("5"));
        assertTrue(result.contains("3"));
    }

    @Test
    public void testPregnancyWithDifferentValues() {
        Pregnancy shortPregnancy = new Pregnancy(mother, 1, 1);
        assertEquals(1, shortPregnancy.getGestationTurnsRemaining());
        assertEquals(1, shortPregnancy.getLitterSize());

        shortPregnancy.decrementGestation();
        assertTrue(shortPregnancy.isReadyToBirth());
    }

    @Test
    public void testPregnancyWithLargeLitter() {
        Pregnancy largePregnancy = new Pregnancy(mother, 3, 6);
        assertEquals(6, largePregnancy.getLitterSize());
    }
}
