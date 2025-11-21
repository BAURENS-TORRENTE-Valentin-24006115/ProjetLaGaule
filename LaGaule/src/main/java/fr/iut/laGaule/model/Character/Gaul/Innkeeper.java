package fr.iut.laGaule.model.Character.Gaul;

public class Innkeeper extends Gaul{


    public Innkeeper(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }
    public void work() {
        System.out.println(this.name + " serves boar.");
    }
}