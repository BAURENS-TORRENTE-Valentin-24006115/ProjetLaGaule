package fr.iut.laGaule.model.Consumables.Foods;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Foods enum.
 * Tests food item properties and behaviors.
 */
public class FoodTest {

    @Test
    public void testSanglierProperties() {
        assertEquals("Sanglier", Foods.SANGLIER.getName());
        assertTrue(Foods.SANGLIER.isGallicFriendly());
        assertTrue(Foods.SANGLIER.isRomanFriendly());
        assertTrue(Foods.SANGLIER.isComestible());
        assertFalse(Foods.SANGLIER.isVegetarian());
    }

    @Test
    public void testPoissonFraisProperties() {
        assertEquals("Poisson frais", Foods.POISSON_FRAIS.getName());
        assertTrue(Foods.POISSON_FRAIS.isGallicFriendly());
        assertTrue(Foods.POISSON_FRAIS.isRomanFriendly());
        assertTrue(Foods.POISSON_FRAIS.isComestible());
        assertFalse(Foods.POISSON_FRAIS.isVegetarian());
    }

    @Test
    public void testPoissonNonFraisProperties() {
        assertEquals("Poisson non frais", Foods.POISSON_NON_FRAIS.getName());
        assertFalse(Foods.POISSON_NON_FRAIS.isGallicFriendly());
        assertFalse(Foods.POISSON_NON_FRAIS.isRomanFriendly());
        assertFalse(Foods.POISSON_NON_FRAIS.isComestible());
        assertFalse(Foods.POISSON_NON_FRAIS.isVegetarian());
    }

    @Test
    public void testMielProperties() {
        assertEquals("Miel", Foods.MIEL.getName());
        assertTrue(Foods.MIEL.isGallicFriendly());
        assertTrue(Foods.MIEL.isRomanFriendly());
        assertTrue(Foods.MIEL.isComestible());
        assertTrue(Foods.MIEL.isVegetarian());
    }

    @Test
    public void testVinProperties() {
        assertEquals("Vin", Foods.VIN.getName());
        assertTrue(Foods.VIN.isGallicFriendly());
        assertTrue(Foods.VIN.isRomanFriendly());
        assertTrue(Foods.VIN.isComestible());
        assertTrue(Foods.VIN.isVegetarian());
    }

    @Test
    public void testGuiProperties() {
        assertEquals("Gui", Foods.GUI.getName());
        assertFalse(Foods.GUI.isGallicFriendly());
        assertFalse(Foods.GUI.isRomanFriendly());
        assertFalse(Foods.GUI.isComestible());
        assertTrue(Foods.GUI.isVegetarian());
    }

    @Test
    public void testCarotteProperties() {
        assertEquals("Carotte", Foods.CAROTTE.getName());
        assertFalse(Foods.CAROTTE.isGallicFriendly());
        assertFalse(Foods.CAROTTE.isRomanFriendly());
        assertTrue(Foods.CAROTTE.isComestible());
        assertTrue(Foods.CAROTTE.isVegetarian());
    }

    @Test
    public void testAllFoodsHaveNames() {
        for (Foods food : Foods.values()) {
            assertNotNull(food.getName());
            assertFalse(food.getName().isEmpty());
        }
    }

    @Test
    public void testFoodsEnumSize() {
        // Verify the number of food items
        assertEquals(18, Foods.values().length);
    }

    @Test
    public void testVegetarianFoods() {
        // Test that vegetarian foods don't include meat/fish
        assertTrue(Foods.MIEL.isVegetarian());
        assertTrue(Foods.CAROTTE.isVegetarian());
        assertFalse(Foods.SANGLIER.isVegetarian());
        assertFalse(Foods.POISSON_FRAIS.isVegetarian());
    }

    @Test
    public void testGallicFriendlyFoods() {
        assertTrue(Foods.SANGLIER.isGallicFriendly());
        assertTrue(Foods.POISSON_FRAIS.isGallicFriendly());
        assertTrue(Foods.MIEL.isGallicFriendly());
        assertFalse(Foods.HOMARD.isGallicFriendly());
    }

    @Test
    public void testRomanFriendlyFoods() {
        assertTrue(Foods.SANGLIER.isRomanFriendly());
        assertTrue(Foods.POISSON_FRAIS.isRomanFriendly());
        assertTrue(Foods.MIEL.isRomanFriendly());
        assertFalse(Foods.HOMARD.isRomanFriendly());
    }

    @Test
    public void testComestibleFoods() {
        assertTrue(Foods.SANGLIER.isComestible());
        assertTrue(Foods.MIEL.isComestible());
        assertFalse(Foods.POISSON_NON_FRAIS.isComestible());
        assertFalse(Foods.POILS_IDEFIX.isComestible());
    }

    @Test
    public void testGetRandomFood() {
        Foods randomFood = Foods.SANGLIER.getRandomFood();
        assertNotNull(randomFood);
        assertTrue(randomFood instanceof Foods);
    }

    @Test
    public void testGetRandomFoods() {
        ArrayList<Foods> randomFoods = Foods.SANGLIER.getRandomFoods(5);
        assertNotNull(randomFoods);
        assertEquals(5, randomFoods.size());

        for (Foods food : randomFoods) {
            assertNotNull(food);
        }
    }

    @Test
    public void testGetRandomFoodsEmpty() {
        ArrayList<Foods> randomFoods = Foods.SANGLIER.getRandomFoods(0);
        assertNotNull(randomFoods);
        assertEquals(0, randomFoods.size());
    }

    @Test
    public void testGetRandomFoodsMultiple() {
        ArrayList<Foods> randomFoods1 = Foods.MIEL.getRandomFoods(10);
        ArrayList<Foods> randomFoods2 = Foods.VIN.getRandomFoods(10);

        assertEquals(10, randomFoods1.size());
        assertEquals(10, randomFoods2.size());
    }

    @Test
    public void testAllFoodsHaveValidProperties() {
        for (Foods food : Foods.values()) {
            assertNotNull(food.getName());
            // Test that getters work
            food.isGallicFriendly();
            food.isRomanFriendly();
            food.isComestible();
            food.isVegetarian();
        }
    }
}


