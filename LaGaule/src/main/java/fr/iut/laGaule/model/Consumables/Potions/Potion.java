package fr.iut.laGaule.model.Consumables.Potions;

import java.util.List;

public class Potion {
    String name;
    List<Effects> effect;

    public Potion(String name, List<Effects> effect) {
        this.name = name;
        this.effect = effect;
    }

    public void usePotion() {
        // Implementation for using the potion
    }
}
