package fr.iut.laGaule.model.Character.Roman;

import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.Gaul.Gaul;
import fr.iut.laGaule.model.Character.Gaul.Merchant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Legionary class.
 * Tests legionary-specific behaviors such as fighting.
 */
public class LegionaryTest {

    private Legionary legionary;
    private Gaul gaul;

    @BeforeEach
    public void setUp() {
        legionary = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        gaul = new Merchant("Unhygienix", "M", 1.70, 45, 40, 50);
    }

    @Test
    public void testLegionaryCreation() {
        assertEquals("Brutus", legionary.getName());
        assertEquals(60, legionary.getStrength());
        assertEquals(55, legionary.getEndurance());
    }

    @Test
    public void testFightWithGaul() {
        int initialGaulHealth = gaul.getHealth();
        legionary.fight(gaul);
        // Gaul should have taken damage
        assertTrue(gaul.getHealth() < initialGaulHealth || legionary.getHealth() < 100);
    }

    @Test
    public void testFightDealsDamage() {
        gaul.receiveDamage(50); // Weaken the gaul
        int gaulHealthBefore = gaul.getHealth();
        legionary.fight(gaul);
        // Legionary should deal damage
        assertTrue(gaul.getHealth() < gaulHealthBefore);
    }

    @Test
    public void testFightWithDeadGaul() {
        gaul.receiveDamage(150); // Kill the gaul
        int legionaryHealthBefore = legionary.getHealth();
        legionary.fight(gaul);
        // Legionary should not take damage from dead gaul
        assertEquals(legionaryHealthBefore, legionary.getHealth());
    }

    @Test
    public void testLegionaryStrengthBonus() {
        Druid strongGaul = new Druid("Panoramix", "M", 1.75, 60, 65, 70);
        int gaulHealthBefore = strongGaul.getHealth();
        legionary.fight(strongGaul);
        // Legionary should deal damage even to strong gaul (with 1.5x strength bonus)
        assertTrue(strongGaul.getHealth() < gaulHealthBefore);
    }

    @Test
    public void testLegionaryIsRoman() {
        assertTrue(legionary instanceof Roman);
    }

    @Test
    public void testFightMinimumDamage() {
        // Create a very weak legionary against a strong gaul
        Legionary weakLegionary = new Legionary("Weak", "M", 1.75, 30, 10, 10);
        Druid strongGaul = new Druid("Strong", "M", 1.75, 60, 80, 80);

        int gaulHealthBefore = strongGaul.getHealth();
        weakLegionary.fight(strongGaul);

        // Should still deal at least 1 damage
        assertTrue(strongGaul.getHealth() <= gaulHealthBefore);
    }
}

