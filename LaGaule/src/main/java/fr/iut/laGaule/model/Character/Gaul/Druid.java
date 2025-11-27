package fr.iut.laGaule.model.Character.Gaul;


import fr.iut.laGaule.model.Character.Roman.Roman;

public class Druid extends Gaul {
    public Druid(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }

    public void concoctPotion() {
        System.out.println(this.name + " is stirring the magic potion in the cauldron.");
    }

    public void command(String command, Gaul gaul) {
        System.out.println(this.name + " advices the village with wisdom.");
        if(command.equals("work")){
            gaul.work();
        }
        //todo move to another place
    }

    public void work() {
        System.out.println(this.name + " gathers herbs in the forest.");
        //todo add random plant
    }

    public void fight(Roman character) {
        System.out.println(this.name + " is fighting herbs in the forest.");
        int damage;
        if(character.getHealth() >0){
            damage = (int) (this.getStrength()*1.5-(character.getEndurance()/10)-character.getHealth()/4)/4;
            if (damage < 0){
                damage = 1;
            }
            System.out.println(damage);
            character.receiveDamage(damage);
            System.out.println(character.getHealth());
        }
        if(character.getHealth() >0){
            damage = (int) (character.getStrength()-(this.getEndurance()/10)-this.getHealth()/4)/4;
            System.out.println(damage);
            if (damage < 0){
                damage = 1;
            }
            this.receiveDamage(damage);
            System.out.println(this.getHealth());
        }
    }
}