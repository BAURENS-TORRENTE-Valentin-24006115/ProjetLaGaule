package fr.iut.laGaule.model.Character.Gaul;

import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Character.Roman.General;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Druid class.
 * Tests druid-specific behaviors such as potion concoction, commanding, and fighting.
 */
public class DruidTest {

    private Druid druid;
    private Gaul gaul;
    private Legionary roman;

    @BeforeEach
    public void setUp() {
        druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        gaul = new Merchant("Unhygienix", "M", 1.70, 45, 40, 50);
        roman = new Legionary("Brutus", "M", 1.75, 30, 45, 50);
    }

    @Test
    public void testDruidCreation() {
        assertEquals("Panoramix", druid.getName());
        assertEquals(50, druid.getStrength());
        assertEquals(60, druid.getEndurance());
    }

    @Test
    public void testConcoctPotion() {
        // Should not throw exception
        assertDoesNotThrow(() -> druid.concoctPotion());
    }

    @Test
    public void testCommand() {
        // Should not throw exception
        assertDoesNotThrow(() -> druid.command(gaul));
    }

    @Test
    public void testWork() {
        // Should not throw exception
        assertDoesNotThrow(() -> druid.work());
    }

    @Test
    public void testFightWithRoman() {
        int initialRomanHealth = roman.getHealth();
        druid.fight(roman);
        // Roman should have taken damage
        assertTrue(roman.getHealth() < initialRomanHealth || druid.getHealth() < 100);
    }

    @Test
    public void testFightDealsDamage() {
        roman.receiveDamage(50); // Weaken the roman
        int romanHealthBefore = roman.getHealth();
        druid.fight(roman);
        // Druid should deal damage
        assertTrue(roman.getHealth() < romanHealthBefore);
    }

    @Test
    public void testFightWithDeadRoman() {
        roman.receiveDamage(150); // Kill the roman
        int druidHealthBefore = druid.getHealth();
        druid.fight(roman);
        // Druid should not take damage from dead roman
        assertEquals(druidHealthBefore, druid.getHealth());
    }

    @Test
    public void testDruidStrengthBonus() {
        General strongRoman = new General("Pompey", "M", 1.82, 45, 70, 65);
        int romanHealthBefore = strongRoman.getHealth();
        druid.fight(strongRoman);
        // Druid should deal damage even to strong roman (with 1.5x strength bonus)
        assertTrue(strongRoman.getHealth() < romanHealthBefore);
    }
}

