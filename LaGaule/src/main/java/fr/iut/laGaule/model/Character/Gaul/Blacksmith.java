package fr.iut.laGaule.model.Character.Gaul;


public class Blacksmith extends Gaul{

    public Blacksmith(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }


    public void work() {
        System.out.println(this.name + " forges swords and shields.");
    }
}