package fr.iut.laGaule.model.Character;

import fr.iut.laGaule.model.Consumables.Foods.Foods;

import java.io.Serializable;

/**
 * Abstract base class representing a character in the game.
 * This class provides common attributes and behaviors for all character types
 * including Gauls, Romans, and mythical creatures.
 *
 * @see java.io.Serializable
 */
public abstract class Character implements Serializable {
	 protected String name;
    protected String sex;
    protected double height;
    protected int age;
    protected int strength;
    protected int endurance;
    protected int health;       
    protected int hunger;       
    protected int belligerence; 
    protected int magicPotionLevel;
    protected String place = "gaul";


    /**
     * Constructs a new Character with the specified attributes.
     * Default values are set for health (100), hunger (100), belligerence (0), and magic potion level (0).
     *
     * @param name the name of the character
     * @param sex the sex/gender of the character
     * @param height the height of the character in meters
     * @param age the age of the character in years
     * @param strength the strength attribute of the character
     * @param endurance the endurance attribute of the character
     */
    public Character(String name, String sex, double height, int age, int strength, int endurance) {
        this.name = name;
        this.sex = sex;
        this.height = height;
        this.age = age;
        this.strength = strength;
        this.endurance = endurance;
        
        // Default values
        this.health = 100;
        this.hunger = 100;
        this.belligerence = 0;
        this.magicPotionLevel = 0;
    }

    /**
     * Makes the character receive damage and reduces their health.
     * If health drops to or below 0, the character dies.
     *
     * @param amount the amount of damage to receive
     */
    public void receiveDamage(int amount) {
        this.health -= amount;
        if (this.health <= 0) {
            this.health = 0;
            die();
        }
    }

    /**
     * Heals the character by the specified amount.
     * Health cannot exceed the maximum value of 100.
     *
     * @param amount the amount of health points to restore
     */
    public void heal(int amount) {
        this.health += amount;
        if (this.health > 100) this.health = 100;
        System.out.println(this.name + " feels better. Health: " + this.health);
    }

    /**
     * Makes the character drink a magic potion, increasing their magic potion level.
     *
     * @param amount the amount of magic potion to drink
     */
    public void drinkPotion(int amount) {
        this.magicPotionLevel += amount;
        System.out.println(this.name + " drinks magic potion! Power level: " + this.magicPotionLevel);
    }

    /**
     * Makes the character eat food to reduce hunger.
     * Hunger cannot exceed the maximum value of 100.
     *
     * @param food the food item to consume
     */
    public void eat(Foods food) {
        this.hunger += 10;
        if (this.hunger > 100) this.hunger = 100;
        System.out.println(this.name + " eat some food! Hunger: " + this.hunger);
    }

    /**
     * Handles the death of the character.
     * Displays a death message to the console.
     */
    public void die() {
        System.out.println("XXX " + this.name + " has passed away. XXX");
    }

    /**
     * Gets the character's name.
     *
     * @return the name of the character
     */
    public String getName() { return name; }

    /**
     * Gets the character's sex/gender.
     *
     * @return the sex of the character
     */
    public String getSex() { return sex; }

    /**
     * Gets the character's endurance.
     *
     * @return the endurance attribute
     */
    public int getEndurance() { return endurance; }

    /**
     * Gets the character's current health.
     *
     * @return the current health points
     */
    public int getHealth() { return health; }

    /**
     * Gets the character's strength.
     *
     * @return the strength attribute
     */
    public int getStrength() { return strength; }

    /**
     * Sets the character's strength.
     *
     * @param strength the new strength value
     */
    public void setStrength(int strength) { this.strength = strength; }

    /**
     * Sets the character's endurance.
     *
     * @param endurance the new endurance value
     */
    public void setEndurance(int endurance) { this.endurance = endurance; }

    /**
     * Sets the character's current place/location.
     *
     * @param place the name of the new location
     */
    public void setPlace(String place) { this.place = place; }

    /**
     * Gets the character's current place/location.
     *
     * @return the name of the current location
     */
    public String getPlace() { return place; }
}
