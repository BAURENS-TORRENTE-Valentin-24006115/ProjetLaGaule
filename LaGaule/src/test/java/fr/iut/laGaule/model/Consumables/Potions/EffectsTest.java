package fr.iut.laGaule.model.Consumables.Potions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Effects enum.
 * Tests potion effect types.
 */
public class EffectsTest {

    @Test
    public void testEffectsEnumValues() {
        assertEquals(6, Effects.values().length);
    }

    @Test
    public void testStrengthEffect() {
        assertEquals("STRENGTH", Effects.STRENGTH.name());
    }

    @Test
    public void testInvincibilityEffect() {
        assertEquals("INVINCIBILITY", Effects.INVINCIBILITY.name());
    }

    @Test
    public void testCloneEffect() {
        assertEquals("CLONE", Effects.CLONE.name());
    }

    @Test
    public void testMetamorphosisEffect() {
        assertEquals("METAMORPHOSIS", Effects.METAMORPHOSIS.name());
    }

    @Test
    public void testSaturationEffect() {
        assertEquals("SATURATION", Effects.SATURATION.name());
    }

    @Test
    public void testDeathEffect() {
        assertEquals("DEATH", Effects.DEATH.name());
    }

    @Test
    public void testAllEffectsExist() {
        assertNotNull(Effects.STRENGTH);
        assertNotNull(Effects.INVINCIBILITY);
        assertNotNull(Effects.CLONE);
        assertNotNull(Effects.METAMORPHOSIS);
        assertNotNull(Effects.SATURATION);
        assertNotNull(Effects.DEATH);
    }

    @Test
    public void testEffectsValueOf() {
        assertEquals(Effects.STRENGTH, Effects.valueOf("STRENGTH"));
        assertEquals(Effects.INVINCIBILITY, Effects.valueOf("INVINCIBILITY"));
        assertEquals(Effects.CLONE, Effects.valueOf("CLONE"));
        assertEquals(Effects.METAMORPHOSIS, Effects.valueOf("METAMORPHOSIS"));
        assertEquals(Effects.SATURATION, Effects.valueOf("SATURATION"));
        assertEquals(Effects.DEATH, Effects.valueOf("DEATH"));
    }

    @Test
    public void testInvalidEffectValueOf() {
        assertThrows(IllegalArgumentException.class, () -> {
            Effects.valueOf("INVALID_EFFECT");
        });
    }
}

