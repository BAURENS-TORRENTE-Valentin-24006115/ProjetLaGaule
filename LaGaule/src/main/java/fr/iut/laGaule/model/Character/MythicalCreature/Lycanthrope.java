package fr.iut.laGaule.model.Character.MythicalCreature;

import fr.iut.laGaule.model.Character.Character;

/**
 * Represents a Lycanthrope (werewolf) character in the game.
 * Lycanthropes are mythical creatures that can inhabit various places
 * and possess unique characteristics.
 *
 * @see Character
 */
public class Lycanthrope extends Character{

    /**
     * Constructs a new Lycanthrope with the specified attributes.
     *
     * @param name the name of the lycanthrope
     * @param sex the sex/gender of the lycanthrope
     * @param height the height of the lycanthrope in meters
     * @param age the age of the lycanthrope in years
     * @param strength the strength attribute of the lycanthrope
     * @param endurance the endurance attribute of the lycanthrope
     */
    public Lycanthrope(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }
}