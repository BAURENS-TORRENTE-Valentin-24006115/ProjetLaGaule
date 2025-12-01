package fr.iut.laGaule.model.Character.Roman;

import fr.iut.laGaule.model.Character.Gaul.Gaul;

/**
 * Represents a General character in the Roman faction.
 * Generals are high-ranking Roman officers who can both command legionaries
 * and engage in combat themselves with enhanced fighting abilities.
 *
 * @see Roman
 */
public class General extends Roman{
    

    /**
     * Constructs a new General with the specified attributes.
     *
     * @param name the name of the general
     * @param sex the sex/gender of the general
     * @param height the height of the general in meters
     * @param age the age of the general in years
     * @param strength the strength attribute of the general
     * @param endurance the endurance attribute of the general
     */
    public General(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }

    /**
     * Commands a legionary to fight against a Gaul character.
     * The general displays a command message and delegates the fight to the legionary.
     *
     * @param legionary the legionary who will execute the fight
     * @param g the Gaul character to fight against
     */
    public void command(Legionary legionary, Gaul g) {
        System.out.println(this.name + " commands the legions.");
        legionary.fight(g);

    }

    /**
     * Handles combat between the general and a Gaul character.
     * The general benefits from a strength bonus (x1.5) during attack.
     * Combat proceeds in alternating turns until one combatant is defeated (health <= 0).
     *
     * Damage is calculated using the formula:
     * - General attack: (strength * 1.5 - enemy_endurance/10 - enemy_health/4) / 4
     * - Gaul counterattack: (strength - general_endurance/10 - general_health/4) / 4
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