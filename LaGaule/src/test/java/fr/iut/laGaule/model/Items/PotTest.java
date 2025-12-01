package fr.iut.laGaule.model.Items;

import fr.iut.laGaule.model.Consumables.Foods.Foods;
import fr.iut.laGaule.model.Consumables.Potions.Effects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PotTest {

    private Pot pot;

    @BeforeEach
    void setUp() {
        pot = new Pot();
    }

    @Test
    void concoctPotionWithNormalIngredientsAndOilWithoutBeetJuiceAddsStrengthAndInvincibility() {
        pot.getNormalPotion().forEach(pot::addFood);
        pot.addFood(Foods.HUILE_DE_ROCHE);

        pot.concoctPotion();

        assertTrue(pot.getEffect().containsAll(List.of(Effects.STRENGTH, Effects.INVINCIBILITY)));
        assertEquals(2, pot.getEffect().size());
    }

    @Test
    void concoctPotionWithNormalIngredientsAndBeetJuiceWithoutOilAddsStrengthInvincibilityAndSaturation() {
        pot.getNormalPotion().forEach(pot::addFood);
        pot.addFood(Foods.JUS_DE_BETTERAVE);

        pot.concoctPotion();

        assertTrue(pot.getEffect().containsAll(List.of(Effects.STRENGTH, Effects.INVINCIBILITY, Effects.SATURATION)));
        assertEquals(3, pot.getEffect().size());
    }

    @Test
    void concoctPotionWithNormalIngredientsAndUnicornMilkAddsCloneEffect() {
        pot.getNormalPotion().forEach(pot::addFood);
        pot.addFood(Foods.LAIT_LICORNE_DEUX_TETES);

        pot.concoctPotion();

        assertTrue(pot.getEffect().contains(Effects.CLONE));
        assertEquals(1, pot.getEffect().size());
    }

    @Test
    void concoctPotionWithNormalIngredientsAndDogHairAddsMetamorphosisEffect() {
        pot.getNormalPotion().forEach(pot::addFood);
        pot.addFood(Foods.POILS_IDEFIX);

        pot.concoctPotion();

        assertTrue(pot.getEffect().contains(Effects.METAMORPHOSIS));
        assertEquals(1, pot.getEffect().size());
    }

    @Test
    void concoctPotionWithNormalIngredientsAndLobsterAddsSaturationEffect() {
        pot.getNormalPotion().forEach(pot::addFood);
        pot.addFood(Foods.HOMARD);

        pot.concoctPotion();

        assertTrue(pot.getEffect().contains(Effects.SATURATION));
        assertEquals(1, pot.getEffect().size());
    }

    @Test
    void concoctPotionWithNormalIngredientsAndStrawberriesAddsSaturationEffect() {
        pot.getNormalPotion().forEach(pot::addFood);
        pot.addFood(Foods.FRAISES);

        pot.concoctPotion();

        assertTrue(pot.getEffect().contains(Effects.SATURATION));
        assertEquals(1, pot.getEffect().size());
    }

    @Test
    void concoctPotionWithMissingIngredientsAddsDeathEffect() {
        pot.addFood(Foods.GUI);

        pot.concoctPotion();

        assertTrue(pot.getEffect().contains(Effects.DEATH));
        assertEquals(1, pot.getEffect().size());
    }

    @Test
    void concoctPotionClearsContentsAfterConcoction() {
        pot.getNormalPotion().forEach(pot::addFood);

        pot.concoctPotion();

        assertTrue(pot.getContents().isEmpty());
    }

    @Test
    void testAddFood() {
        pot.addFood(Foods.GUI);
        assertEquals(1, pot.getContents().size());
        assertTrue(pot.getContents().contains(Foods.GUI));
    }

    @Test
    void testGetNormalPotion() {
        List<Foods> normalPotion = pot.getNormalPotion();
        assertEquals(8, normalPotion.size());
        assertTrue(normalPotion.contains(Foods.GUI));
        assertTrue(normalPotion.contains(Foods.CAROTTE));
        assertTrue(normalPotion.contains(Foods.SEL));
    }

    @Test
    void testDrinkAll() {
        pot.getNormalPotion().forEach(pot::addFood);
        pot.addFood(Foods.HUILE_DE_ROCHE);
        pot.concoctPotion();

        fr.iut.laGaule.model.Character.Gaul.Druid druid =
            new fr.iut.laGaule.model.Character.Gaul.Druid("Test", "M", 1.75, 60, 50, 60);

        pot.drinkAll(druid);
        assertTrue(pot.getEffect().isEmpty());
    }

    @Test
    void testDrinkOne() {
        pot.getNormalPotion().forEach(pot::addFood);
        pot.addFood(Foods.HUILE_DE_ROCHE);
        pot.concoctPotion();

        int initialUseAmounts = pot.useAmounts;

        fr.iut.laGaule.model.Character.Gaul.Druid druid =
            new fr.iut.laGaule.model.Character.Gaul.Druid("Test", "M", 1.75, 60, 50, 60);

        pot.drinkOne(druid);
        assertEquals(initialUseAmounts - 1, pot.useAmounts);
    }

    @Test
    void testPotInitialization() {
        Pot newPot = new Pot();
        assertTrue(newPot.getContents().isEmpty());
        assertTrue(newPot.getEffect().isEmpty());
    }

    @Test
    void testMultipleFoodsCanBeAdded() {
        pot.addFood(Foods.GUI);
        pot.addFood(Foods.CAROTTE);
        pot.addFood(Foods.SEL);

        assertEquals(3, pot.getContents().size());
    }
}

