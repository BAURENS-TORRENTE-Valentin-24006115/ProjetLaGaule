package fr.iut.laGaule.model.Character.Gaul;


import fr.iut.laGaule.model.Character.Roman.Roman;

public class Druid extends Gaul {
    public Druid(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }

    public void concoctPotion() {
        System.out.println(this.name + " is stirring the magic potion in the cauldron.");
    }

    public void command() {
        System.out.println(this.name + " advices the village with wisdom.");
    }

    public void work() {
        System.out.println(this.name + " gathers herbs in the forest.");
    }

    public void fight(Roman character) {
        System.out.println(this.name + " is fighting herbs in the forest.");
        if(character.getHealth() >0){
            System.out.println((int) (this.getStrength()*1.5-(character.getEndurance()/10)-character.getHealth()/4)/4);
            character.receiveDamage((int) (this.getStrength()*1.5-(character.getEndurance()/10)-character.getHealth()/4)/4);
            System.out.println(character.getHealth());
        }
        if(character.getHealth() >0){
            System.out.println((int) (character.getStrength()-(this.getEndurance()/10)-this.getHealth()/4)/4);
            this.receiveDamage((int) (character.getStrength()-(this.getEndurance()/10)-this.getHealth()/4)/4);
            System.out.println(this.getHealth());
        }
    }
}