package fr.iut.laGaule.model.Items;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import fr.iut.laGaule.model.Consumables.Potions.Effects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Pot {
    /**
     * List of foods required for a normal potion.
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

    public Pot() {
        this.effect = new ArrayList<>();
    }

    /**
     * Method to add food to the pot at a specific index.
     * @param food The food item to add.
     */
    public void addFood(Foods food) {
        contents.add(food);
    }

    public List<Foods> getContents() {
        return contents;
    }

    public List<Foods> getNormalPotion() {
        return normalPotion;
    }

    /**
     * Method to concoct a potion based on the contents of the pot.
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
     * Method to drink all uses of the potion.
     * @param character The character drinking the potion.
     */
    public void drinkAll(Character character) {
        // TODO: implement effect application on character
        if (useAmounts > 0) {
            useAmounts = 0;
            effect.clear();
        }
    }

    /**
     * Method to drink one use of the potion.
     * @param character The character drinking the potion.
     */
    public void drinkOne(Character character) {
        if (useAmounts > 0)  --useAmounts;
    }

    /**
     * Getter for the effects of the potion.
     * @return List of effects.
     */
    public List<Effects> getEffect() {
        return effect;
    }
}
