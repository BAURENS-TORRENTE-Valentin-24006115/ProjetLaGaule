package fr.iut.laGaule.model.Character;

import fr.iut.laGaule.Serializer;

import java.io.Serializable;

public class Character implements Serializable {
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

    public void receiveDamage(int amount) {
        this.health -= amount;
        if (this.health <= 0) {
            this.health = 0;
            die();
        }
    }

    public void heal(int amount) {
        this.health += amount;
        if (this.health > 100) this.health = 100;
        System.out.println(this.name + " feels better. Health: " + this.health);
    }

    public void drinkPotion(int amount) {
        this.magicPotionLevel += amount;
        System.out.println(this.name + " drinks magic potion! Power level: " + this.magicPotionLevel);
    }

    public void eat(int amount) {
        this.hunger += amount;
        if (this.hunger > 100) this.hunger = 100;
        System.out.println(this.name + " eat some food! Hunger: " + this.hunger);
    }

    public void die() {
        System.out.println("XXX " + this.name + " has passed away. XXX");
    }
    public String getName() { return name; }
    public int getEndurance() { return endurance; }
    public int getHealth() { return health; }
    public int getStrength() { return strength; }
}
