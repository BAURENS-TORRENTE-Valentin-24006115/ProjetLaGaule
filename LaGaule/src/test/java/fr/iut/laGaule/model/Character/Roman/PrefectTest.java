package fr.iut.laGaule.model.Character.Roman;

import fr.iut.laGaule.model.Character.Gaul.Gaul;
import fr.iut.laGaule.model.Character.Gaul.Merchant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Prefect class.
 * Tests prefect-specific behaviors such as commanding legionaries.
 */
public class PrefectTest {

    private Prefect prefect;
    private Legionary legionary;
    private Gaul gaul;

    @BeforeEach
    public void setUp() {
        prefect = new Prefect("Karawita", "M", 1.80, 54, 62, 52);
        legionary = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        gaul = new Merchant("Unhygienix", "M", 1.70, 45, 40, 50);
    }

    @Test
    public void testPrefectCreation() {
        assertEquals("Karawita", prefect.getName());
        assertEquals(62, prefect.getStrength());
        assertEquals(52, prefect.getEndurance());
    }

    @Test
    public void testCommand() {
        // Should not throw exception
        assertDoesNotThrow(() -> prefect.command(legionary, gaul));
    }

    @Test
    public void testCommandMakesLegionaryFight() {
        int gaulHealthBefore = gaul.getHealth();
        prefect.command(legionary, gaul);
        // Gaul should have taken damage from legionary
        assertTrue(gaul.getHealth() < gaulHealthBefore);
    }

    @Test
    public void testPrefectIsRoman() {
        assertTrue(prefect instanceof Roman);
    }

    @Test
    public void testPrefectInheritsCharacterProperties() {
        assertEquals(100, prefect.getHealth());
        prefect.receiveDamage(20);
        assertEquals(80, prefect.getHealth());
    }

    @Test
    public void testCommandWithMultipleLegionaries() {
        Legionary legionary2 = new Legionary("Cesar", "M", 1.78, 28, 65, 60);

        int gaulHealthBefore = gaul.getHealth();

        // Command both legionaries
        prefect.command(legionary, gaul);
        if (gaul.getHealth() > 0) {
            prefect.command(legionary2, gaul);
        }

        // Gaul should have taken damage
        assertTrue(gaul.getHealth() < gaulHealthBefore);
    }
}

