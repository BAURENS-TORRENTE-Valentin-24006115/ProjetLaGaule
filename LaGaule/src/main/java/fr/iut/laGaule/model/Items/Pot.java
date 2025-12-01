package fr.iut.laGaule.model.Items;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import fr.iut.laGaule.model.Consumables.Potions.Effects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Represents a magic pot used to concoct potions with various effects.
 * The pot contains ingredients (food items) that are mixed together
 * to create potions with different magical effects based on the combination.
 */
public class Pot {
    /**
     * List of foods required as base ingredients for a normal potion.
     * These ingredients must be present for the potion to have positive effects.
     */
    private final List<Foods> normalPotion = List.of(
            Foods.GUI,
            Foods.CAROTTE,
            Foods.SEL,
            Foods.TREFLE_QUATRE_FEUILLES_FRAIS,
            Foods.POISSON_FRAIS,
            Foods.MIEL,
            Foods.HYDROMEL,
            Foods.INGREDIENT_SECRET
    );

    private final List<Foods> contents = new ArrayList<>();
    private final List<Effects> effect;
    int useAmounts;

    /**
     * Constructs a new empty pot with no effects.
     */
    public Pot() {
        this.effect = new ArrayList<>();
    }

    /**
     * Adds a food item to the pot's contents.
     *
     * @param food the food item to add to the pot
     */
    public void addFood(Foods food) {
        contents.add(food);
    }

    /**
     * Gets the current contents of the pot.
     *
     * @return the list of foods currently in the pot
     */
    public List<Foods> getContents() {
        return contents;
    }

    /**
     * Gets the list of required ingredients for a normal potion.
     *
     * @return the list of base ingredients
     */
    public List<Foods> getNormalPotion() {
        return normalPotion;
    }

    /**
     * Concocts a potion based on the current contents of the pot.
     * The effects are determined by the combination of ingredients:
     * - If all normal potion ingredients are present:
     *   - With rock oil (without beet juice): STRENGTH + INVINCIBILITY
     *   - With beet juice (without rock oil): STRENGTH + INVINCIBILITY + SATURATION
     *   - With two-headed unicorn milk: CLONE
     *   - With Idefix's hair: METAMORPHOSIS
     *   - With lobster: SATURATION
     *   - With strawberries: SATURATION
     * - If normal potion ingredients are missing: DEATH
     *
     * After concoction, the pot is emptied and use amounts are set based on ingredient count.
     */
    public void concoctPotion() {
        if (new HashSet<>(contents).containsAll(normalPotion)) {
            if (contents.contains(Foods.HUILE_DE_ROCHE) && !contents.contains(Foods.JUS_DE_BETTERAVE)) {
                effect.add(Effects.STRENGTH);
                effect.add(Effects.INVINCIBILITY);
            }
            else if (contents.contains(Foods.JUS_DE_BETTERAVE) && !contents.contains(Foods.HUILE_DE_ROCHE)) {
                effect.add(Effects.STRENGTH);
                effect.add(Effects.INVINCIBILITY);
                effect.add(Effects.SATURATION);
            }
            else if (contents.contains(Foods.LAIT_LICORNE_DEUX_TETES)) {
                effect.add(Effects.CLONE);
            }
            else if (contents.contains(Foods.POILS_IDEFIX)) {
                effect.add(Effects.METAMORPHOSIS);
            }
            else if (contents.contains(Foods.HOMARD)) {
                effect.add(Effects.SATURATION);
            }
            else if (contents.contains(Foods.FRAISES)) {
                effect.add(Effects.SATURATION);
            }
        }
        else {
            effect.add(Effects.DEATH);
        }
        useAmounts = contents.size();
        contents.clear();
    }

    /**
     * Makes a character drink all remaining uses of the potion.
     * This consumes all potion uses and clears all effects.
     * TODO: Implement effect application on the character.
     *
     * @param character the character drinking the potion
     */
    public void drinkAll(Character character) {
        // TODO: implement effect application on character
        if (useAmounts > 0) {
            useAmounts = 0;
            effect.clear();
        }
    }

    /**
     * Makes a character drink one use of the potion.
     * This decrements the available uses by one.
     * TODO: Implement effect application on the character.
     *
     * @param character the character drinking the potion
     */
    public void drinkOne(Character character) {
        if (useAmounts > 0)  --useAmounts;
    }

    /**
     * Gets the list of effects of the concocted potion.
     *
     * @return the list of potion effects
     */
    public List<Effects> getEffect() {
        return effect;
    }
}
