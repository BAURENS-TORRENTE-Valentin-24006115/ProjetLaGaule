package fr.iut.laGaule.model.Character.Roman;

import fr.iut.laGaule.model.Character.Gaul.Gaul;

public class Prefect extends Roman {


    public Prefect(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }


    public void command(Legionary legionary, Gaul g) {
        System.out.println(this.name + " commands the legions.");
        legionary.fight(g);

    }
}