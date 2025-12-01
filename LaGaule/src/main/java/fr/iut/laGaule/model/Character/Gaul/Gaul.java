package fr.iut.laGaule.model.Character.Gaul;

import fr.iut.laGaule.model.Character.Character;

/**
 * Represents a Gaul character in the game.
 * Gauls are inhabitants of the Gallic village and can perform various work activities.
 * This is a base class for specific Gaul character types such as Blacksmith, Druid, Innkeeper, and Merchant.
 *
 * @see Character
 */
public class Gaul extends Character {

    /**
     * Constructs a new Gaul character with the specified attributes.
     *
     * @param name the name of the Gaul
     * @param sex the sex/gender of the Gaul
     * @param height the height of the Gaul in meters
     * @param age the age of the Gaul in years
     * @param strength the strength attribute of the Gaul
     * @param endurance the endurance attribute of the Gaul
     */
    public Gaul(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }

    /**
     * Performs the work activity of the Gaul.
     * This method is intended to be overridden by specific Gaul character types
     * to define their unique work behaviors.
     */
    protected void work() {
    }
}