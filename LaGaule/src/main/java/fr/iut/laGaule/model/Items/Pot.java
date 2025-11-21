package fr.iut.laGaule.model.Items;

import fr.iut.laGaule.model.Consumables.Foods.Foods;
import fr.iut.laGaule.model.Consumables.Potions.Effects;
import fr.iut.laGaule.model.Consumables.Potions.Potion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Pot {
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
    List<Effects> effect;
    int useAmounts;

    public Pot() {}

    public void addFood(Foods food, int index) {
        contents.add(index, food);
    }

    public List<Foods> getContents() {
        return contents;
    }

    public List<Foods> getNormalPotion() {
        return normalPotion;
    }

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

    public void drinkAll(Character character) {
        // TODO: implement effect application on character
        useAmounts = 0;
    }

    public void drinkOne(Character character) {
        //TODO: implement effect application on character
        --useAmounts;
    }

    public Potion addInPotion() {
        return new Potion("Custom Potion", effect);
    }

    public List<Effects> getEffect() {
        return effect;
    }
}
