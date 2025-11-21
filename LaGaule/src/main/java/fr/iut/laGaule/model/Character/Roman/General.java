package fr.iut.laGaule.model.Character.Roman;

public class General extends Roman{
    

    public General(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }

    public void command() {
        System.out.println(this.name + " commands the legions.");
    }
}