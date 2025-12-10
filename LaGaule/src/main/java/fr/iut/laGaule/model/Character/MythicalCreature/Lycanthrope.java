package fr.iut.laGaule.model.Character.MythicalCreature;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.Gaul.Gaul;
import fr.iut.laGaule.model.Character.Roman.Roman;

import java.util.List;

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
    private List<String> DominationHistory;

    /**
     * Constructs a new Lycanthrope with the specified attributes.
     *
     * @param name              the name of the lycanthrope
     * @param sex               the sex/gender of the lycanthrope
     * @param height            the height of the lycanthrope in meters
     * @param age               the age of the lycanthrope in years
     * @param strength          the strength attribute of the lycanthrope
     * @param endurance         the endurance attribute of the lycanthrope
     * @param ageCategory       the age category (young, adult, or old)
     * @param dominationFactor  the domination factor (difference between dominations exercised and received)
     * @param hierarchyRank     the rank within the pack hierarchy
     * @param impetuosityFactor the impetuosity factor of the lycanthrope
     * @param originGaul
     */
    public Lycanthrope(String name, String sex, double height, int age, int strength, int endurance,
                       AgeCategory ageCategory, int dominationFactor, Rank hierarchyRank, int impetuosityFactor, boolean originGaul) {
        super(name, sex, height, age, strength, endurance);
        this.ageCategory = ageCategory;
        this.impetuosityFactor = impetuosityFactor;

        // By default, lycanthropes are created as solitary
        this.pack = null;
        this.isSolitary = true;
        // Solitary lycanthropes have no domination factor or hierarchy rank
        this.dominationFactor = 0;
        this.hierarchyRank = null;

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
     * Emits a pack affiliation howl to express belonging to a pack.
     * When a pack member emits this howl, all other members of the pack who hear it respond with the same type of howl.
     * Other packs can also respond with their own pack affiliation howl.
     *
     * @return the emitted howl
     */
    public Howl howlPackAffiliation() {
        Howl howl = new Howl(this, HowlType.PACK_AFFILIATION);
        howl.displayCharacteristics();
        System.out.println(name + " emits a PACK AFFILIATION howl!\n");

        if (!isSolitary && pack != null) {
            System.out.println("→ All pack members respond with the same howl:");
            pack.getMembers().stream()
                    .filter(member -> member != this)
                    .filter(member -> member.listenToHowl(howl))
                    .forEach(member -> System.out.println("  " + member.getName() + " responds: PACK AFFILIATION howl!"));
        }
        return howl;
    }

    /**
     * Emits a domination howl to express dominance over other lycanthropes.
     *
     * @return the emitted howl
     */
    public Howl howlDomination() {
        Howl howl = new Howl(this, HowlType.DOMINATION);
        howl.displayCharacteristics();
        System.out.println(name + " emits a DOMINATION howl!\n");
        return howl;
    }

    /**
     * Emits a submission howl in response to a domination howl.
     * This howl is emitted to acknowledge another lycanthrope's dominance.
     *
     * @return the emitted howl
     */
    public Howl howlSubmission() {
        Howl howl = new Howl(this, HowlType.SUBMISSION);
        howl.displayCharacteristics();
        System.out.println(name + " emits a SUBMISSION howl!\n");
        return howl;
    }

    /**
     * Emits an aggression howl in response to a domination howl or towards an omega member.
     * This howl expresses aggressive intent.
     *
     * @return the emitted howl
     */
    public Howl howlAggression() {
        Howl howl = new Howl(this, HowlType.AGGRESSION);
        howl.displayCharacteristics();
        System.out.println(name + " emits an AGGRESSION howl!\n");
        return howl;
    }

    /**
     * Makes the lycanthrope listen to a howl from another lycanthrope.
     * The lycanthrope can only hear howls if they are not too sick/weak.
     *
     * @param howl the howl to listen to
     * @return true if the howl was heard successfully, false otherwise
     */
    public boolean listenToHowl(Howl howl) {
        if (health < 30) {
            System.out.println(name + " is too weak to hear properly.");
            return false;
        }
        System.out.println(name + " hears the " + howl.getHowlType().getDescription() + " howl from " + howl.getEmitter().getName());
        return true;
    }

    /**
     * Makes the lycanthrope leave its current pack and become solitary.
     * When a lycanthrope becomes solitary, it loses its domination factor and hierarchy rank.
     */
    public void leavePack() {
        if (pack != null) {
            System.out.println(name + " leaves the pack: " + pack.getName());
            pack.removeMember(this);
            // Don't set pack to null here - removeMember will call becomeSolitary()
        } else {
            // If not in a pack, just become solitary
            becomeSolitary();
        }
    }

    /**
     * Internal method to make the lycanthrope solitary without triggering pack removal.
     * This is called by Pack.removeMember() to avoid infinite recursion.
     */
    void becomeSolitary() {
        this.pack = null;
        this.isSolitary = true;
        // Solitary lycanthropes have no domination factor or hierarchy rank
        this.dominationFactor = 0;
        this.hierarchyRank = null;
        this.updateLevel();
        System.out.println(name + " is now solitary with no domination rank.");
    }

    /**
     * Makes the lycanthrope join a pack.
     * When joining a pack, the lycanthrope is no longer solitary and will receive a rank.
     *
     * @param pack the pack to join
     */
    public void joinPack(Pack pack) {
        if (this.pack != null) {
            leavePack();
        }
        pack.addMember(this); // Pack will set the pack reference and isSolitary
        // Pack should also assign a rank when the lycanthrope joins
    }

    /**
     * Transforms the lycanthrope into human form.
     * There is a probability, based on the lycanthrope's level, that it may leave
     * its pack and enclosure (which is equivalent to the death of the lycanthrope).
     * This can disrupt the organization of a pack.
     * If the lycanthrope leaves, a new human character (Gaul or Roman) is created.
     *
     * @return the new human Character if the lycanthrope left permanently, null otherwise
     */
    public Character transformToHuman() {
        System.out.println("\n=== " + name + " transforms into human form ===");

        // Calculate the probability of leaving the pack based on level
        // Higher level = higher chance to leave (level is typically between 0-100)
        // Probability = level / 100 (e.g., level 50 = 50% chance)
        java.util.Random random = new java.util.Random();
        int chanceToLeave = Math.min(level, 100); // Cap at 100%
        int roll = random.nextInt(100);

        System.out.println("Level: " + level + " | Chance to leave pack: " + chanceToLeave + "%");

        if (roll < chanceToLeave) {
            System.out.println(">>> " + name + " decides to leave their lycanthrope life behind!");

            // If the lycanthrope is in a pack, handle the departure (equivalent to death)
            if (pack != null) {
                System.out.println(name + " leaves the pack: " + pack.getName());
                pack.handleMemberDeath(this);
            } else {
                System.out.println(name + " was already solitary.");
            }

            // Mark the lycanthrope as "dead" (left the lycanthrope life)
            this.health = 0;
            this.isSolitary = true;
            this.pack = null;

            // Create a new human character randomly (Gaul or Roman)
            Character newHuman = createRandomHumanCharacter(random);

            System.out.println("*** " + name + " has permanently left the lycanthrope world ***");
            System.out.println("*** A new human has been born: " + newHuman.getName() + " (" + newHuman.getClass().getSimpleName() + ") ***");
            System.out.println("=== End of Transformation ===\n");
            return newHuman;
        } else {
            System.out.println(name + " transforms back but stays with the pack.");
            System.out.println("=== End of Transformation ===\n");
            return null;
        }
    }

    /**
     * Creates a new random human character (Gaul or Roman) based on the lycanthrope's attributes.
     * The new character inherits some attributes from the former lycanthrope.
     *
     * @param random the Random instance to use for randomization
     * @return a new Gaul or Roman character
     */
    private Character createRandomHumanCharacter(java.util.Random random) {
        // Generate a new name for the human
        String humanName = name + " (Human)";

        // Randomly choose between Gaul and Roman
        int characterType = random.nextInt(2);

        // The new human inherits some attributes but with reduced strength (no longer a lycanthrope)
        int humanStrength = Math.max(1, strength / 2);
        int humanEndurance = Math.max(1, endurance / 2);

        if (characterType == 0) {
            // Create a Gaul
            return new Gaul(humanName, sex, height, age, humanStrength, humanEndurance);
        } else {
            // Create a Roman
            return new Roman(humanName, sex, height, age, humanStrength, humanEndurance);
        }
    }


    // ===== Domination and Submission Methods ===
    /**
     * Attempts to dominate another lycanthrope.
     * Can only dominate if the target is considered inferior or equal based on impetuosity factor.
     * Cannot dominate the alpha female or omega lycanthropes under certain conditions.
     *
     * @param target the lycanthrope to attempt to dominate
     * @return true if domination was successful, false otherwise
     */
    public boolean attemptDomination(Lycanthrope target) {
        // Cannot dominate if target is null
        if (target == null) {
            System.out.println(name + " cannot dominate a null target.");
            return false;
        }

        // Check if target is considered inferior or equal (based on impetuosity)
        int targetPower = target.strength + target.impetuosityFactor;
        int myPower = this.strength + this.impetuosityFactor;

        if (targetPower > myPower) {
            System.out.println(name + " considers " + target.name + " too strong to dominate.");
            return false;
        }

        // Determine domination success
        boolean dominationSuccess = false;

        // Success if aggressor has higher level
        if (this.level > target.level) {
            dominationSuccess = true;
        }
        // Success if target is omega (very weak)
        else if (target.dominationFactor < -5) {
            dominationSuccess = true;
        }
        else {
            // Target resists and becomes aggressive
            System.out.println(target.name + " resists and shows aggression towards " + name);
            target.receiveDamage(5); // Minor damage from the confrontation
            return false;
        }

        // Apply domination effects if successful
        if (dominationSuccess) {
            System.out.println(name + " successfully dominates " + target.name + "!");

            // Increase domination factor for aggressor
            this.dominationFactor++;
            this.updateLevel();

            // Decrease domination factor for target (submission)
            target.submit();

            // Exchange ranks
            Rank tempRank = this.hierarchyRank;
            this.hierarchyRank = target.hierarchyRank;
            target.hierarchyRank = tempRank;

            System.out.println(name + " and " + target.name + " have exchanged ranks.");
            return true;
        }

        return false;
    }

    /**
     * Makes this lycanthrope submit to another.
     * Reduces the domination factor as a result of submission.
     */
    public void submit() {
        this.dominationFactor--;
        this.updateLevel();
        System.out.println(name + " submits and lowers its domination factor to " + dominationFactor);
    }




    // ========== Hierarchy Management Methods ==========

    /**
     * Attempts to dominate another lycanthrope.
     * The outcome depends on multiple factors including rank, strength, level, and impetuosity.
     * If successful, both lycanthropes' domination factors are updated.
     *
     * @param target the lycanthrope to dominate
     * @return true if the domination was successful, false otherwise
     */
    public boolean dominate(Lycanthrope target) {
        if (target == null) {
            System.out.println(name + " cannot dominate a null target.");
            return false;
        }

        if (target == this) {
            System.out.println(name + " cannot dominate itself!");
            return false;
        }

        if (this.isSolitary && target.isSolitary) {
            System.out.println("Solitary lycanthropes do not establish domination relationships!");
            return false;
        }

        if (this.pack != null && target.pack != null && this.pack != target.pack) {
            System.out.println(name + " and " + target.getName() + " are in different packs!");
            return false;
        }

        System.out.println("\n--- " + name + " attempts to dominate " + target.getName() + " ---");

        // Calculate domination probability based on multiple factors
        int dominationScore = calculateDominationScore(target);

        // Add randomness based on impetuosity
        java.util.Random random = new java.util.Random();
        int randomFactor = random.nextInt(impetuosityFactor + 1);
        dominationScore += randomFactor;

        boolean success = dominationScore > 0;

        if (success) {
            System.out.println("✓ " + name + " successfully dominates " + target.getName() + "!");
            this.dominationFactor++;
            target.dominationFactor--;
            this.updateLevel();
            target.updateLevel();

            // Check if rank should change
            checkRankChange(target);
        } else {
            System.out.println("✗ " + name + " failed to dominate " + target.getName() + "!");
            // Failed domination can lower domination factor
            this.dominationFactor--;
            target.dominationFactor++;
            this.updateLevel();
            target.updateLevel();
        }

        return success;
    }

    /**
     * Calculates the domination score when attempting to dominate another lycanthrope.
     * Takes into account rank hierarchy, strength, level, and domination factor.
     *
     * @param target the target lycanthrope
     * @return the domination score (positive favors attacker, negative favors defender)
     */
    private int calculateDominationScore(Lycanthrope target) {
        int score = 0;

        // Rank difference (most important factor)
        if (this.hierarchyRank != null && target.hierarchyRank != null) {
            score += (this.hierarchyRank.getHierarchyLevel() - target.hierarchyRank.getHierarchyLevel()) * 10;
        }

        // Strength difference
        score += (this.strength - target.strength);

        // Level difference
        score += (this.level - target.level) / 2;

        // Domination factor difference
        score += (this.dominationFactor - target.dominationFactor);

        return score;
    }

    /**
     * Checks if a rank change should occur after a domination event.
     * If the dominated lycanthrope has a higher rank, there may be a rank swap.
     *
     * @param dominated the dominated lycanthrope
     */
    private void checkRankChange(Lycanthrope dominated) {
        if (this.hierarchyRank == null || dominated.hierarchyRank == null) {
            return;
        }

        // If dominator has lower rank than dominated, they may swap ranks
        if (dominated.hierarchyRank.getHierarchyLevel() > this.hierarchyRank.getHierarchyLevel()) {
            // Check if domination factor justifies rank change
            if (this.dominationFactor > dominated.dominationFactor + 5) {
                System.out.println(">>> Rank change! " + name + " rises from " + this.hierarchyRank.getSymbol()
                    + " to " + dominated.hierarchyRank.getSymbol());

                Rank temp = this.hierarchyRank;
                this.hierarchyRank = dominated.hierarchyRank;
                dominated.hierarchyRank = temp;

                this.updateLevel();
                dominated.updateLevel();

                // Notify pack if they belong to one
                if (pack != null) {
                    pack.notifyRankChange(this, dominated);
                }
            }
        }
    }

    /**
     * Receives domination from another lycanthrope.
     * This is a passive response to being dominated.
     *
     * @param dominator the lycanthrope that dominates this one
     */
    public void receiveDomination(Lycanthrope dominator) {
        System.out.println(name + " is dominated by " + dominator.getName());
        this.dominationFactor--;
        this.updateLevel();
    }

    /**
     * Submits to another lycanthrope, acknowledging their superiority.
     * This is a voluntary submission that affects domination factor.
     *
     * @param superior the lycanthrope to submit to
     */
    public void submitTo(Lycanthrope superior) {
        if (superior == null) {
            System.out.println(name + " cannot submit to a null superior.");
            return;
        }

        if (superior == this) {
            return;
        }

        System.out.println(name + " submits to " + superior.getName());
        this.dominationFactor--;
        superior.dominationFactor++;
        this.updateLevel();
        superior.updateLevel();
    }

    /**
     * Checks if this lycanthrope can dominate another based on rank hierarchy.
     *
     * @param other the other lycanthrope
     * @return true if this lycanthrope's rank dominates the other's rank
     */
    public boolean canDominateByRank(Lycanthrope other) {
        if (this.hierarchyRank == null || other.hierarchyRank == null) {
            return false;
        }
        return this.hierarchyRank.dominates(other.hierarchyRank);
    }

    /**
     * Shows aggression towards another lycanthrope.
     * This is a warning display that may lead to domination attempt.
     *
     * @param target the target of aggression
     */
    public void showAggression(Lycanthrope target) {
        if (target == null) {
            System.out.println(name + " cannot show aggression to a null target.");
            return;
        }

        System.out.println(name + " shows aggression towards " + target.getName() + "!");
        System.out.println(name + " growls menacingly...");

        // Impetuosity may cause immediate domination attempt
        java.util.Random random = new java.util.Random();
        if (random.nextInt(100) < impetuosityFactor) {
            System.out.println(name + "'s impetuosity triggers a domination attempt!");
            dominate(target);
        }
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

    public List<String> getDominationHistory() {
        return DominationHistory;
    }
}