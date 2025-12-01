package fr.iut.laGaule.model.Character.Gaul;

import fr.iut.laGaule.model.Character.Character;

public abstract class Gaul extends Character {

    public Gaul(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }

    protected void work() {
    }
}