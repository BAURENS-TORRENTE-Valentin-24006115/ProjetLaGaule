package fr.iut.laGaule.model.Character.Roman;

import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.Gaul.Gaul;
import fr.iut.laGaule.model.Character.Gaul.Merchant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the General class.
 * Tests general-specific behaviors such as commanding and fighting.
 */
public class GeneralTest {

    private General general;
    private Legionary legionary;
    private Gaul gaul;

    @BeforeEach
    public void setUp() {
        general = new General("Pompey", "M", 1.82, 45, 70, 65);
        legionary = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        gaul = new Merchant("Unhygienix", "M", 1.70, 45, 40, 50);
    }

    @Test
    public void testGeneralCreation() {
        assertEquals("Pompey", general.getName());
        assertEquals(70, general.getStrength());
        assertEquals(65, general.getEndurance());
    }

    @Test
    public void testCommand() {
        // Should not throw exception
        assertDoesNotThrow(() -> general.command(legionary, gaul));
    }

    @Test
    public void testCommandMakesLegionaryFight() {
        int gaulHealthBefore = gaul.getHealth();
        general.command(legionary, gaul);
        // Gaul should have taken damage from legionary
        assertTrue(gaul.getHealth() < gaulHealthBefore);
    }

    @Test
    public void testFightWithGaul() {
        int initialGaulHealth = gaul.getHealth();
        general.fight(gaul);
        // Gaul should have taken damage
        assertTrue(gaul.getHealth() < initialGaulHealth || general.getHealth() < 100);
    }

    @Test
    public void testFightDealsDamage() {
        gaul.receiveDamage(50); // Weaken the gaul
        int gaulHealthBefore = gaul.getHealth();
        general.fight(gaul);
        // General should deal damage
        assertTrue(gaul.getHealth() < gaulHealthBefore);
    }

    @Test
    public void testFightWithDeadGaul() {
        gaul.receiveDamage(150); // Kill the gaul
        int generalHealthBefore = general.getHealth();
        general.fight(gaul);
        // General should not take damage from dead gaul
        assertEquals(generalHealthBefore, general.getHealth());
    }

    @Test
    public void testGeneralStrengthBonus() {
        Druid strongGaul = new Druid("Panoramix", "M", 1.75, 60, 65, 70);
        int gaulHealthBefore = strongGaul.getHealth();
        general.fight(strongGaul);
        // General should deal damage even to strong gaul (with 1.5x strength bonus)
        assertTrue(strongGaul.getHealth() < gaulHealthBefore);
    }

    @Test
    public void testGeneralIsRoman() {
        assertTrue(general instanceof Roman);
    }

    @Test
    public void testFightWithMinimumDamage() {
        // Create a gaul with very high endurance and health to test minimum damage (1)
        Gaul strongGaul = new Druid("SuperDruid", "M", 1.75, 60, 5, 200);
        strongGaul.heal(1000); // Max health

        int gaulHealthBefore = strongGaul.getHealth();
        general.fight(strongGaul);

        // Should deal at least 1 damage even with negative calculated damage
        assertTrue(strongGaul.getHealth() < gaulHealthBefore);
    }

    @Test
    public void testFightCounterAttackMinimumDamage() {
        // Create a general with very high endurance and health to test minimum damage from gaul
        General strongGeneral = new General("SuperGeneral", "M", 1.82, 45, 5, 200);
        strongGeneral.heal(1000); // Max health

        Gaul weakGaul = new Druid("WeakDruid", "M", 1.75, 60, 5, 5);

        int generalHealthBefore = strongGeneral.getHealth();
        strongGeneral.fight(weakGaul);

        // If gaul survives, it should deal at least 1 damage to general
        if (weakGaul.getHealth() > 0) {
            assertTrue(strongGeneral.getHealth() < generalHealthBefore);
        }
    }

    @Test
    public void testFightKillsGaulBeforeCounterAttack() {
        Gaul weakGaul = new Druid("WeakDruid", "M", 1.75, 60, 10, 10);
        weakGaul.receiveDamage(99); // Leave at 1 health

        int generalHealthBefore = general.getHealth();
        general.fight(weakGaul);

        // If gaul dies from first attack, general should not take damage
        if (weakGaul.getHealth() == 0) {
            assertEquals(generalHealthBefore, general.getHealth());
        }
    }
}

