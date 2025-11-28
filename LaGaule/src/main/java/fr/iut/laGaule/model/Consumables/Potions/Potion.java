package fr.iut.laGaule.model.Consumables.Potions;

import java.util.List;

public class Potion {
    String name;
    List<Effects> effect;

    /**
     * Constructor for Potion class.
     * @param name
     * @param effect
     */
    public Potion(String name, List<Effects> effect) {
        this.name = name;
        this.effect = effect;
    }

    public List<Effects> getEffects() {
        return effect;
    }

    /**
     * Method to use the potion.
     */
    public void usePotion() {
        // must use the potion and delete it from inventory
    }

    public String getName() {
        return name;
    }
}
