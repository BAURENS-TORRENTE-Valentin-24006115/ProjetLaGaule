package fr.iut.laGaule.model.Character.Gaul;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Merchant class.
 * Tests merchant-specific behaviors such as selling goods.
 */
public class MerchantTest {

    private Merchant merchant;

    @BeforeEach
    public void setUp() {
        merchant = new Merchant("Unhygienix", "M", 1.70, 45, 40, 50);
    }

    @Test
    public void testMerchantCreation() {
        assertEquals("Unhygienix", merchant.getName());
        assertEquals(40, merchant.getStrength());
        assertEquals(50, merchant.getEndurance());
    }

    @Test
    public void testWork() {
        // Should not throw exception
        assertDoesNotThrow(() -> merchant.work());
    }

    @Test
    public void testMerchantIsGaul() {
        assertTrue(merchant instanceof Gaul);
    }

    @Test
    public void testMerchantInheritsCharacterProperties() {
        assertEquals(100, merchant.getHealth());
        merchant.receiveDamage(20);
        assertEquals(80, merchant.getHealth());
    }
}

