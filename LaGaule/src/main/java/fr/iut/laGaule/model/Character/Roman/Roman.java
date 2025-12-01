package fr.iut.laGaule.model.Character.Roman;

import fr.iut.laGaule.model.Character.Character;

/**
 * Represents a Roman character in the game.
 * Romans are the antagonists who oppose the Gauls and can engage in combat.
 * This is a base class for specific Roman character types such as Legionary, Prefect, and General.
 *
 * @see Character
 */
public class Roman extends Character {

    /**
     * Constructs a new Roman character with the specified attributes.
     *
     * @param name the name of the Roman
     * @param sex the sex/gender of the Roman
     * @param height the height of the Roman in meters
     * @param age the age of the Roman in years
     * @param strength the strength attribute of the Roman
     * @param endurance the endurance attribute of the Roman
     */
    public Roman(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }
}