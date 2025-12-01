package fr.iut.laGaule.model.Character.Roman;

import fr.iut.laGaule.model.Character.Gaul.Gaul;

/**
 * Represents a Prefect character in the Roman faction.
 * Prefects are Roman officers who command legionaries and coordinate attacks against the Gauls.
 *
 * @see Roman
 */
public class Prefect extends Roman {


    /**
     * Constructs a new Prefect with the specified attributes.
     *
     * @param name the name of the prefect
     * @param sex the sex/gender of the prefect
     * @param height the height of the prefect in meters
     * @param age the age of the prefect in years
     * @param strength the strength attribute of the prefect
     * @param endurance the endurance attribute of the prefect
     */
    public Prefect(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }


    /**
     * Commands a legionary to fight against a Gaul character.
     * The prefect displays a command message and delegates the fight to the legionary.
     *
     * @param legionary the legionary who will execute the fight
     * @param g the Gaul character to fight against
     */
    public void command(Legionary legionary, Gaul g) {
        System.out.println(this.name + " commands the legions.");
        legionary.fight(g);

    }
}