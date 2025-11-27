package fr.iut.laGaule.model.Character.Roman;

import fr.iut.laGaule.model.Character.Gaul.Gaul;

public class General extends Roman{
    

    public General(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }

    public void command(Legionary legionary, Gaul g) {
        System.out.println(this.name + " commands the legions.");
        legionary.fight(g);

    }

    public void fight(Gaul character) {
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