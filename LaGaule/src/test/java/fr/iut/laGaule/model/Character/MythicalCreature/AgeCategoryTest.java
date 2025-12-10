package fr.iut.laGaule.model.Character.MythicalCreature;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the AgeCategory enum.
 */
public class AgeCategoryTest {

    @Test
    public void testYoungLabel() {
        assertEquals("jeune", AgeCategory.YOUNG.getLabel());
    }

    @Test
    public void testAdultLabel() {
        assertEquals("adulte", AgeCategory.ADULT.getLabel());
    }

    @Test
    public void testOldLabel() {
        assertEquals("vieux", AgeCategory.OLD.getLabel());
    }

    @Test
    public void testAgeCategoryValuesCount() {
        assertEquals(3, AgeCategory.values().length);
    }

    @Test
    public void testYoungToString() {
        assertEquals("jeune", AgeCategory.YOUNG.toString());
    }

    @Test
    public void testAdultToString() {
        assertEquals("adulte", AgeCategory.ADULT.toString());
    }

    @Test
    public void testOldToString() {
        assertEquals("vieux", AgeCategory.OLD.toString());
    }

    @Test
    public void testValueOf() {
        assertEquals(AgeCategory.YOUNG, AgeCategory.valueOf("YOUNG"));
        assertEquals(AgeCategory.ADULT, AgeCategory.valueOf("ADULT"));
        assertEquals(AgeCategory.OLD, AgeCategory.valueOf("OLD"));
    }

    @Test
    public void testInvalidValueOf() {
        assertThrows(IllegalArgumentException.class, () -> {
            AgeCategory.valueOf("INVALID");
        });
    }

    @Test
    public void testAllAgeCategoriesNotNull() {
        assertNotNull(AgeCategory.YOUNG);
        assertNotNull(AgeCategory.ADULT);
        assertNotNull(AgeCategory.OLD);
    }
}
