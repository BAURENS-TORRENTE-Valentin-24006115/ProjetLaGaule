package fr.iut.laGaule.model.Character.Roman;

import fr.iut.laGaule.model.Character.Gaul.Gaul;

/**
 * Represents a Legionary character in the Roman faction.
 * Legionaries are Roman soldiers who fight against the Gauls.
 * They have enhanced combat abilities with a strength bonus.
 *
 * @see Roman
 */
public class Legionary extends Roman{

    /**
     * Constructs a new Legionary with the specified attributes.
     *
     * @param name the name of the legionary
     * @param sex the sex/gender of the legionary
     * @param height the height of the legionary in meters
     * @param age the age of the legionary in years
     * @param strength the strength attribute of the legionary
     * @param endurance the endurance attribute of the legionary
     */
    public Legionary(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }

    /**
     * Handles combat between the legionary and a Gaul character.
     * The legionary benefits from a strength bonus (x1.5) during attack.
     * Combat proceeds in alternating turns until one combatant is defeated (health <= 0).
     *
     * Damage is calculated using the formula:
     * - Legionary attack: (strength * 1.5 - enemy_endurance/10 - enemy_health/4) / 4
     * - Gaul counterattack: (strength - legionary_endurance/10 - legionary_health/4) / 4
     *
     * Minimum damage is always 1 point.
     *
     * @param character the Gaul character to fight
     */
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