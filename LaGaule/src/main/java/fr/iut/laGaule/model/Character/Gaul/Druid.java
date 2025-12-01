package fr.iut.laGaule.model.Character.Gaul;


import fr.iut.laGaule.model.Character.Roman.Roman;

/**
 * Represents a Druid character in the Gaul faction.
 * Druids are special characters capable of concocting magic potions,
 * advising the village, and fighting Romans with enhanced strength.
 *
 * @see Gaul
 */
public class Druid extends Gaul {

    /**
     * Constructs a new Druid with the specified attributes.
     *
     * @param name the name of the druid
     * @param sex the sex/gender of the druid
     * @param height the height of the druid in meters
     * @param age the age of the druid in years
     * @param strength the strength attribute of the druid
     * @param endurance the endurance attribute of the druid
     */
    public Druid(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }

    /**
     * Allows the druid to concoct a magic potion in the cauldron.
     * Displays a message indicating the druid is preparing the potion.
     */
    public void concoctPotion() {
        System.out.println(this.name + " is stirring the magic potion in the cauldron.");
    }

    /**
     * Allows the druid to give orders and advice to other Gauls.
     * The druid commands the specified Gaul to work.
     *
     * @param gaul the Gaul who should follow the druid's advice
     */
    public void command(Gaul gaul) {
        System.out.println(this.name + " advices the village with wisdom.");
        gaul.work();

    }

    /**
     * The druid performs their daily work by gathering herbs in the forest.
     * These herbs are used to prepare magic potions.
     */
    public void work() {
        System.out.println(this.name + " gathers herbs in the forest.");
        //todo add random plant
    }

    /**
     * Handles combat between the druid and a Roman character.
     * The druid benefits from a strength bonus (x1.5) during attack.
     * Combat proceeds in alternating turns until one combatant is defeated (health <= 0).
     *
     * Damage is calculated using the formula:
     * - Druid attack: (strength * 1.5 - enemy_endurance/10 - enemy_health/4) / 4
     * - Roman counterattack: (strength - druid_endurance/10 - druid_health/4) / 4
     *
     * Minimum damage is always 1 point.
     *
     * @param character the Roman character to fight
     */
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