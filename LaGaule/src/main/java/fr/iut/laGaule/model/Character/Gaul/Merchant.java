package fr.iut.laGaule.model.Character.Gaul;

/**
 * Represents a Merchant character in the Gaul faction.
 * Merchants sell fresh fish and other goods to the village inhabitants.
 *
 * @see Gaul
 */
public class Merchant extends Gaul{


    /**
     * Constructs a new Merchant with the specified attributes.
     *
     * @param name the name of the merchant
     * @param sex the sex/gender of the merchant
     * @param height the height of the merchant in meters
     * @param age the age of the merchant in years
     * @param strength the strength attribute of the merchant
     * @param endurance the endurance attribute of the merchant
     */
    public Merchant(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }

    /**
     * Performs the merchant's work activity by selling fresh fish.
     * TODO: Add random food items to the merchant's inventory.
     */
    public void work() {
        System.out.println(this.name + " sells fresh fish.");
        //todo add random foods
    }
}