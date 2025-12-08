package fr.iut.laGaule.model.Character.MythicalCreature;

import fr.iut.laGaule.model.Character.Character;

/**
 * Represents a Lycanthrope (werewolf) character in the game.
 * Lycanthropes are mythical creatures that can inhabit various places
 * and possess unique characteristics including age category, domination factor,
 * rank, level, impetuosity factor, and pack affiliation.
 *
 * @see Character
 */
public class Lycanthrope extends Character {



    private AgeCategory ageCategory;
    private int dominationFactor;
    private Rank hierarchyRank;
    private int level;
    private int impetuosityFactor;
    private Pack pack;
    private boolean isSolitary;

    /**
     * Constructs a new Lycanthrope with the specified attributes.
     *
     * @param name the name of the lycanthrope
     * @param sex the sex/gender of the lycanthrope
     * @param height the height of the lycanthrope in meters
     * @param age the age of the lycanthrope in years
     * @param strength the strength attribute of the lycanthrope
     * @param endurance the endurance attribute of the lycanthrope
     * @param ageCategory the age category (young, adult, or old)
     * @param dominationFactor the domination factor (difference between dominations exercised and received)
     * @param hierarchyRank the rank within the pack hierarchy
     * @param impetuosityFactor the impetuosity factor of the lycanthrope
     */
    public Lycanthrope(String name, String sex, double height, int age, int strength, int endurance,
                       AgeCategory ageCategory, int dominationFactor, Rank hierarchyRank, int impetuosityFactor) {
        super(name, sex, height, age, strength, endurance);
        this.ageCategory = ageCategory;
        this.dominationFactor = dominationFactor;
        this.hierarchyRank = hierarchyRank != null ? hierarchyRank : Rank.OMEGA;
        this.impetuosityFactor = impetuosityFactor;
        this.isSolitary = true;
        this.pack = null;
        this.level = calculateLevel();
    }

    /**
     * Calculates the level of the lycanthrope based on age category, strength,
     * domination factor, and hierarchy rank.
     * Level is a subjective quality criterion for both male and female lycanthropes.
     *
     * @return the calculated level
     */
    private int calculateLevel() {
        int ageFactor = switch (ageCategory) {
            case YOUNG -> 1;
            case ADULT -> 2;
            case OLD -> 3;
        };
        int rankValue = hierarchyRank != null ? hierarchyRank.getHierarchyLevel() : 0;
        return (ageFactor * 10) + strength + dominationFactor + rankValue;
    }

    /**
     * Updates the level of the lycanthrope.
     * Should be called after any change to attributes that affect the level.
     */
    public void updateLevel() {
        this.level = calculateLevel();
    }

    /**
     * Displays the characteristics of the lycanthrope.
     * Shows all attributes including name, sex, age category, strength, domination factor,
     * rank, level, impetuosity factor, and pack affiliation.
     */
    public void displayCharacteristics() {
        System.out.println("=== Lycanthrope Characteristics ===");
        System.out.println("Name: " + name);
        System.out.println("Sex: " + sex);
        System.out.println("Age Category: " + ageCategory);
        System.out.println("Height: " + height + "m");
        System.out.println("Age: " + age + " years");
        System.out.println("Strength: " + strength);
        System.out.println("Endurance: " + endurance);
        System.out.println("Domination Factor: " + dominationFactor);
        System.out.println("Hierarchy Rank: " + (hierarchyRank != null ? hierarchyRank.getSymbol() + " (" + hierarchyRank.name() + ")" : "None"));
        System.out.println("Level: " + level);
        System.out.println("Impetuosity Factor: " + impetuosityFactor);
        System.out.println("Pack: " + (isSolitary ? "Solitary" : pack != null ? pack.getName() : "None"));
        System.out.println("===================================");
    }

    /**
     * Makes the lycanthrope howl to communicate with other lycanthropes.
     * The howl type depends on the howl parameter.
     *
     * @param howlType the type of howl to emit
     */
    public void howl(String howlType) {
        System.out.println(name + " howls: " + howlType);
        // Additional logic for howling can be implemented here
    }

    /**
     * Makes the lycanthrope listen to a howl from another lycanthrope.
     * The lycanthrope can only hear howls if they are not too sick/weak.
     *
     * @param howl the howl message to listen to
     * @return true if the howl was heard successfully, false otherwise
     */
    public boolean listenToHowl(String howl) {
        if (health < 30) {
            System.out.println(name + " is too weak to hear properly.");
            return false;
        }
        System.out.println(name + " hears: " + howl);
        return true;
    }

    /**
     * Makes the lycanthrope leave its current pack and become solitary.
     */
    public void leavePack() {
        if (pack != null) {
            System.out.println(name + " leaves the pack: " + pack.getName());
            pack.removeMember(this);
            this.pack = null;
        }
        this.isSolitary = true;
    }

    /**
     * Makes the lycanthrope join a pack.
     *
     * @param pack the pack to join
     */
    public void joinPack(Pack pack) {
        if (this.pack != null) {
            leavePack();
        }
        pack.addMember(this); // Pack will set the pack reference and isSolitary
    }

    /**
     * Transforms the lycanthrope into human form.
     */
    public void transformToHuman() {
        System.out.println(name + " transforms into human form.");
        // Additional logic for transformation can be implemented here
    }

    // Getters and Setters

    /**
     * Gets the age category of the lycanthrope.
     *
     * @return the age category
     */
    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    /**
     * Sets the age category of the lycanthrope.
     *
     * @param ageCategory the new age category
     */
    public void setAgeCategory(AgeCategory ageCategory) {
        this.ageCategory = ageCategory;
        updateLevel();
    }

    /**
     * Gets the domination factor of the lycanthrope.
     *
     * @return the domination factor
     */
    public int getDominationFactor() {
        return dominationFactor;
    }

    /**
     * Sets the domination factor of the lycanthrope.
     *
     * @param dominationFactor the new domination factor
     */
    public void setDominationFactor(int dominationFactor) {
        this.dominationFactor = dominationFactor;
        updateLevel();
    }

    /**
     * Gets the hierarchy rank of the lycanthrope.
     *
     * @return the hierarchy rank
     */
    public Rank getHierarchyRank() {
        return hierarchyRank;
    }

    /**
     * Sets the hierarchy rank of the lycanthrope.
     *
     * @param hierarchyRank the new hierarchy rank
     */
    public void setHierarchyRank(Rank hierarchyRank) {
        this.hierarchyRank = hierarchyRank;
        updateLevel();
    }

    /**
     * Gets the level of the lycanthrope.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the impetuosity factor of the lycanthrope.
     *
     * @return the impetuosity factor
     */
    public int getImpetuosityFactor() {
        return impetuosityFactor;
    }

    /**
     * Sets the impetuosity factor of the lycanthrope.
     *
     * @param impetuosityFactor the new impetuosity factor
     */
    public void setImpetuosityFactor(int impetuosityFactor) {
        this.impetuosityFactor = impetuosityFactor;
    }

    /**
     * Gets the pack the lycanthrope belongs to.
     *
     * @return the pack, or null if solitary
     */
    public Pack getPack() {
        return pack;
    }

    /**
     * Sets the pack the lycanthrope belongs to.
     * This method is used internally by the Pack class.
     *
     * @param pack the pack to set
     */
    void setPack(Pack pack) {
        this.pack = pack;
    }

    /**
     * Checks if the lycanthrope is solitary.
     *
     * @return true if solitary, false otherwise
     */
    public boolean isSolitary() {
        return isSolitary;
    }

    /**
     * Sets whether the lycanthrope is solitary.
     *
     * @param solitary true if solitary, false otherwise
     */
    public void setSolitary(boolean solitary) {
        isSolitary = solitary;
    }
}