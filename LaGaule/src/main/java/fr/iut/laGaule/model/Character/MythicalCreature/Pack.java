package fr.iut.laGaule.model.Character.MythicalCreature;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a pack of lycanthropes organized in a strict hierarchy.
 * The pack is led by an alpha couple (male α and female α) to avoid conflicts.
 * A pack can only have one alpha couple at a time.
 * Omega (ω) lycanthropes are considered the scapegoats of the pack.
 */
public class Pack {
    private String name;
    private final List<Lycanthrope> members;
    private Lycanthrope alphaMale;
    private Lycanthrope alphaFemale;
    private List<Pregnancy> pregnancies;


    /**
     * Constructs a new Pack with the specified name.
     *
     * @param name the name of the pack
     */
    public Pack(String name) {
        this.name = name;
        this.members = new ArrayList<>();
        this.alphaMale = null;
        this.alphaFemale = null;
        this.pregnancies = new ArrayList<>();
    }

    /**
     * Adds a member to the pack.
     * If the lycanthrope is already in another pack, it will leave that pack first.
     * New members are assigned a default subordinate rank unless they become alphas.
     *
     * @param lycanthrope the lycanthrope to add
     */
    public void addMember(Lycanthrope lycanthrope) {
        if (!members.contains(lycanthrope)) {
            members.add(lycanthrope);
            lycanthrope.setPack(this);
            lycanthrope.setSolitary(false);

            // Assign a default rank (SUBORDINATE) if not already set
            // Alphas will get their rank set via setAlphaMale/setAlphaFemale
            if (lycanthrope.getHierarchyRank() == null) {
                lycanthrope.setHierarchyRank(Rank.OMEGA);
            }

            System.out.println(lycanthrope.getName() + " joins the pack: " + name);
        }
    }

    /**
     * Removes a member from the pack.
     * If the member is an alpha, the alpha position will become vacant.
     *
     * @param lycanthrope the lycanthrope to remove
     */
    public void removeMember(Lycanthrope lycanthrope) {
        if (members.remove(lycanthrope)) {
            // Check if the leaving lycanthrope is an alpha
            if (lycanthrope == alphaMale) {
                alphaMale = null;
                System.out.println("The alpha male has left the pack!");
            }
            if (lycanthrope == alphaFemale) {
                alphaFemale = null;
                System.out.println("The alpha female has left the pack!");
            }
            // Use becomeSolitary() to avoid infinite recursion (leavePack calls removeMember)
            lycanthrope.becomeSolitary();
            System.out.println(lycanthrope.getName() + " has left the pack: " + name);
        }
    }

    /**
     * Handles the death of a pack member and reorganizes the pack hierarchy if needed.
     * If an alpha dies, a new alpha will be automatically selected from the remaining members.
     * The new alpha is chosen based on level and age category (must be adult).
     *
     * @param lycanthrope the lycanthrope who died
     */
    public void handleMemberDeath(Lycanthrope lycanthrope) {
        if (!members.contains(lycanthrope)) {
            System.out.println(lycanthrope.getName() + " is not a member of this pack.");
            return;
        }

        System.out.println("\n*** DEATH IN THE PACK ***");
        System.out.println(lycanthrope.getName() + " has died in pack " + name);
        System.out.println("Rank: " + lycanthrope.getHierarchyRank().getSymbol() + " (" + lycanthrope.getHierarchyRank().name() + ")");

        boolean wasAlphaMale = (lycanthrope == alphaMale);
        boolean wasAlphaFemale = (lycanthrope == alphaFemale);

        // Remove the deceased from the pack
        members.remove(lycanthrope);

        // Handle alpha positions
        if (wasAlphaMale) {
            alphaMale = null;
            System.out.println("The alpha male has died!");
            reorganizeAfterAlphaDeath(true);
        }

        if (wasAlphaFemale) {
            alphaFemale = null;
            System.out.println("The alpha female has died!");
            reorganizeAfterAlphaDeath(false);
        }

        // Cancel any ongoing pregnancy if the deceased was pregnant
        pregnancies.removeIf(pregnancy -> pregnancy.getMother() == lycanthrope);

        System.out.println("Pack size now: " + members.size() + " members");
        System.out.println("*** END OF DEATH EVENT ***\n");
    }

    /**
     * Reorganizes the pack hierarchy after an alpha dies.
     * Selects a new alpha from the remaining adult members based on level.
     *
     * @param isAlphaMale true if reorganizing for alpha male position, false for alpha female
     */
    private void reorganizeAfterAlphaDeath(boolean isAlphaMale) {
        if (members.isEmpty()) {
            System.out.println("The pack is now empty.");
            return;
        }

        System.out.println("\n--- Reorganizing Pack After Alpha Death ---");

        String targetSex = isAlphaMale ? "male" : "female";
        List<String> sexVariants = isAlphaMale ?
            Arrays.asList("male", "mâle", "m") :
            Arrays.asList("female", "femelle", "f");

        // Find suitable replacement: adult members of the correct sex, sorted by level
        List<Lycanthrope> candidates = members.stream()
            .filter(l -> sexVariants.stream().anyMatch(s -> l.getSex().equalsIgnoreCase(s)))
            .filter(l -> l.getAgeCategory() == AgeCategory.ADULT)
            .sorted((l1, l2) -> Integer.compare(l2.getLevel(), l1.getLevel()))
            .collect(Collectors.toList());

        if (candidates.isEmpty()) {
            System.out.println("No suitable adult " + targetSex + " found to replace the deceased alpha!");

            // Try to find ANY adult regardless of original alpha's sex
            candidates = members.stream()
                .filter(l -> l.getAgeCategory() == AgeCategory.ADULT)
                .sorted((l1, l2) -> Integer.compare(l2.getLevel(), l1.getLevel()))
                .collect(Collectors.toList());

            if (candidates.isEmpty()) {
                System.out.println("No adult members available. Pack has no alpha!");
                System.out.println("--- End of Reorganization ---\n");
                return;
            }
        }

        Lycanthrope newAlpha = candidates.get(0);

        // Set the new alpha
        if (isAlphaMale || sexVariants.stream().anyMatch(s -> newAlpha.getSex().equalsIgnoreCase(s))) {
            if (newAlpha.getSex().equalsIgnoreCase("male") ||
                newAlpha.getSex().equalsIgnoreCase("mâle") ||
                newAlpha.getSex().equalsIgnoreCase("m")) {
                setAlphaMale(newAlpha);
                System.out.println("New alpha male appointed: " + newAlpha.getName() + " (Level: " + newAlpha.getLevel() + ")");
            } else {
                setAlphaFemale(newAlpha);
                System.out.println("New alpha female appointed: " + newAlpha.getName() + " (Level: " + newAlpha.getLevel() + ")");
            }
        }

        // Check if we need to establish a complete alpha couple
        if (alphaMale != null && alphaFemale == null) {
            establishAlphaCouple(false);
        } else if (alphaFemale != null && alphaMale == null) {
            establishAlphaCouple(true);
        }

        System.out.println("--- End of Reorganization ---\n");
    }

    /**
     * Attempts to establish a complete alpha couple by finding a suitable mate for the existing alpha.
     *
     * @param needsMale true if we need to find an alpha male, false if we need an alpha female
     */
    private void establishAlphaCouple(boolean needsMale) {
        System.out.println("Attempting to establish complete alpha couple...");

        String targetSex = needsMale ? "male" : "female";
        List<String> sexVariants = needsMale ?
            Arrays.asList("male", "mâle", "m") :
            Arrays.asList("female", "femelle", "f");

        List<Lycanthrope> candidates = members.stream()
            .filter(l -> sexVariants.stream().anyMatch(s -> l.getSex().equalsIgnoreCase(s)))
            .filter(l -> l.getAgeCategory() == AgeCategory.ADULT)
            .filter(l -> needsMale ? (l != alphaFemale) : (l != alphaMale))
            .sorted((l1, l2) -> Integer.compare(l2.getLevel(), l1.getLevel()))
            .collect(Collectors.toList());

        if (!candidates.isEmpty()) {
            Lycanthrope newAlpha = candidates.get(0);
            if (needsMale) {
                setAlphaMale(newAlpha);
            } else {
                setAlphaFemale(newAlpha);
            }
            System.out.println("Alpha couple is now complete!");
        } else {
            System.out.println("No suitable " + targetSex + " found to complete the alpha couple.");
        }
    }

    /**
     * Sets the alpha male of the pack.
     * Only one alpha male can exist at a time.
     *
     * @param lycanthrope the lycanthrope to set as alpha male
     * @throws IllegalArgumentException if the lycanthrope is not male or not in the pack
     */
    public void setAlphaMale(Lycanthrope lycanthrope) {
        if (lycanthrope == null) {
            this.alphaMale = null;
            return;
        }

        if (!members.contains(lycanthrope)) {
            throw new IllegalArgumentException("Lycanthrope must be a member of the pack");
        }

        if (!lycanthrope.getSex().equalsIgnoreCase("male") &&
            !lycanthrope.getSex().equalsIgnoreCase("m") &&
            !lycanthrope.getSex().equalsIgnoreCase("mâle")) {
            throw new IllegalArgumentException("Alpha male must be male");
        }

        // Remove previous alpha male from alpha position
        if (this.alphaMale != null && this.alphaMale != lycanthrope) {
            System.out.println(this.alphaMale.getName() + " is no longer the alpha male");
        }

        this.alphaMale = lycanthrope;
        lycanthrope.setHierarchyRank(Rank.ALPHA);
        System.out.println(lycanthrope.getName() + " is now the alpha male of " + name);
    }

    /**
     * Sets the alpha female of the pack.
     * Only one alpha female can exist at a time.
     *
     * @param lycanthrope the lycanthrope to set as alpha female
     * @throws IllegalArgumentException if the lycanthrope is not female or not in the pack
     */
    public void setAlphaFemale(Lycanthrope lycanthrope) {
        if (lycanthrope == null) {
            this.alphaFemale = null;
            return;
        }

        if (!members.contains(lycanthrope)) {
            throw new IllegalArgumentException("Lycanthrope must be a member of the pack");
        }

        if (!lycanthrope.getSex().equalsIgnoreCase("female") &&
            !lycanthrope.getSex().equalsIgnoreCase("f") &&
            !lycanthrope.getSex().equalsIgnoreCase("femelle")) {
            throw new IllegalArgumentException("Alpha female must be female");
        }

        // Remove previous alpha female from alpha position
        if (this.alphaFemale != null && this.alphaFemale != lycanthrope) {
            System.out.println(this.alphaFemale.getName() + " is no longer the alpha female");
        }

        this.alphaFemale = lycanthrope;
        lycanthrope.setHierarchyRank(Rank.ALPHA);
        System.out.println(lycanthrope.getName() + " is now the alpha female of " + name);
    }

    /**
     * Gets the alpha male of the pack.
     *
     * @return the alpha male, or null if there is none
     */
    public Lycanthrope getAlphaMale() {
        return alphaMale;
    }

    /**
     * Gets the alpha female of the pack.
     *
     * @return the alpha female, or null if there is none
     */
    public Lycanthrope getAlphaFemale() {
        return alphaFemale;
    }

    /**
     * Checks if the pack has a complete alpha couple.
     *
     * @return true if both alpha male and alpha female are present
     */
    public boolean hasAlphaCouple() {
        return alphaMale != null && alphaFemale != null;
    }

    /**
     * Manages the alpha couple dynamics.
     * Ensures the alpha male maintains dominance over the alpha female.
     * If the male loses dominance, a new couple is formed with the highest-level adult female.
     */
    public void manageAlphaCouple() {
        if (!hasAlphaCouple()) {
            return;
        }

        System.out.println("\n=== Managing Alpha Couple in " + name + " ===");

        // Check if alpha male still dominates alpha female
        if (alphaMale.getDominationFactor() <= alphaFemale.getDominationFactor()) {
            System.out.println("Warning: Alpha male " + alphaMale.getName() +
                " is losing dominance over alpha female " + alphaFemale.getName());

            // Try to restore dominance
            boolean dominationSuccess = alphaMale.dominate(alphaFemale);

            if (!dominationSuccess || alphaMale.getDominationFactor() <= alphaFemale.getDominationFactor()) {
                System.out.println("Alpha male cannot maintain dominance! Reforming couple...");
                reformAlphaCouple();
            }
        } else {
            System.out.println("Alpha couple is stable: " + alphaMale.getName() +
                " maintains dominance over " + alphaFemale.getName());
        }

        System.out.println("=== End of Alpha Couple Management ===\n");
    }

    /**
     * Reforms the alpha couple when the current alpha male loses dominance.
     * The alpha female keeps her rank and mates with the adult male with the highest level.
     * The previous alpha female keeps the same domination rank as her former mate.
     */
    private void reformAlphaCouple() {
        if (alphaFemale == null) {
            return;
        }

        System.out.println("\n--- Reforming Alpha Couple ---");

        // Store the old alpha female's domination level
        Lycanthrope oldAlphaFemale = alphaFemale;
        int oldDominationLevel = oldAlphaFemale.getDominationFactor();

        // Find the adult male with the highest level (excluding current alpha male)
        List<Lycanthrope> adultMales = members.stream()
            .filter(l -> l != alphaMale)
            .filter(l -> l.getSex().equalsIgnoreCase("male") ||
                        l.getSex().equalsIgnoreCase("mâle") ||
                        l.getSex().equalsIgnoreCase("m"))
            .filter(l -> l.getAgeCategory() == AgeCategory.ADULT)
            .sorted((l1, l2) -> Integer.compare(l2.getLevel(), l1.getLevel()))
            .collect(Collectors.toList());

        if (adultMales.isEmpty()) {
            System.out.println("No suitable adult male found to replace alpha male!");
            return;
        }

        Lycanthrope newAlphaMale = adultMales.get(0);

        // Demote old alpha male
        if (alphaMale != null) {
            System.out.println(alphaMale.getName() + " is demoted from alpha male position");
            alphaMale.setHierarchyRank(Rank.BETA);
        }

        // Find the adult female with highest level for new alpha female
        List<Lycanthrope> adultFemales = members.stream()
            .filter(l -> l.getSex().equalsIgnoreCase("female") ||
                        l.getSex().equalsIgnoreCase("femelle") ||
                        l.getSex().equalsIgnoreCase("f"))
            .filter(l -> l.getAgeCategory() == AgeCategory.ADULT)
            .sorted((l1, l2) -> Integer.compare(l2.getLevel(), l1.getLevel()))
            .collect(Collectors.toList());

        if (!adultFemales.isEmpty()) {
            Lycanthrope newAlphaFemale = adultFemales.get(0);

            // Old alpha female keeps same domination rank as old mate
            oldAlphaFemale.setDominationFactor(oldDominationLevel);
            oldAlphaFemale.setHierarchyRank(Rank.BETA);

            // Set new alpha couple
            setAlphaMale(newAlphaMale);
            setAlphaFemale(newAlphaFemale);

            System.out.println("New alpha couple formed:");
            System.out.println("  Alpha Male: " + newAlphaMale.getName());
            System.out.println("  Alpha Female: " + newAlphaFemale.getName());
        }

        System.out.println("--- End of Couple Reform ---\n");
    }

    /**
     * Gets all omega (ω) lycanthropes in the pack.
     * These are the scapegoats of the pack.
     *
     * @return list of omega lycanthropes
     */
    public List<Lycanthrope> getOmegas() {
        return members.stream()
                .filter(l -> l.getHierarchyRank() == Rank.OMEGA)
                .collect(Collectors.toList());
    }

    /**
     * Gets all members of a specific rank.
     *
     * @param rank the rank to filter by
     * @return list of lycanthropes with the specified rank
     */
    public List<Lycanthrope> getMembersByRank(Rank rank) {
        return members.stream()
                .filter(l -> l.getHierarchyRank() == rank)
                .collect(Collectors.toList());
    }

    /**
     * Displays the pack hierarchy.
     * Shows all members organized by rank from Alpha to Omega.
     */
    public void displayHierarchy() {
        System.out.println("\n=== Pack Hierarchy: " + name + " ===");

        if (hasAlphaCouple()) {
            System.out.println("\nAlpha Couple:");
            System.out.println("  ♂ " + alphaMale.getName() + " (Level: " + alphaMale.getLevel() + ")");
            System.out.println("  ♀ " + alphaFemale.getName() + " (Level: " + alphaFemale.getLevel() + ")");
        } else {
            if (alphaMale != null) {
                System.out.println("\nAlpha Male: " + alphaMale.getName());
            }
            if (alphaFemale != null) {
                System.out.println("Alpha Female: " + alphaFemale.getName());
            }
        }

        for (Rank rank : Rank.values()) {
            if (rank == Rank.ALPHA) continue; // Already displayed

            List<Lycanthrope> rankMembers = getMembersByRank(rank);
            if (!rankMembers.isEmpty()) {
                System.out.println("\n" + rank.getSymbol() + " (" + rank.name() + "):");
                for (Lycanthrope l : rankMembers) {
                    System.out.println("  - " + l.getName() + " (" + l.getSex() + ", Level: " + l.getLevel() + ")");
                }
            }
        }

        System.out.println("\nTotal members: " + members.size());
        System.out.println("=====================================\n");
    }

    /**
     * Gets the name of the pack.
     *
     * @return the pack name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pack.
     *
     * @param name the new pack name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of pack members.
     *
     * @return an unmodifiable list of lycanthropes in the pack
     */
    public List<Lycanthrope> getMembers() {
        return Collections.unmodifiableList(members);
    }

    /**
     * Gets the number of members in the pack.
     *
     * @return the pack size
     */
    public int getSize() {
        return members.size();
    }

    /**
     * Checks if the pack is empty.
     *
     * @return true if the pack has no members
     */
    public boolean isEmpty() {
        return members.isEmpty();
    }

    /**
     * Broadcasts a howl to all pack members.
     * All members will hear the howl if they are healthy enough.
     *
     * @param howl the howl to broadcast
     */
    public void broadcast(Howl howl) {
        System.out.println("[Pack " + name + "] " + howl.getEmitter().getName() + " broadcasts: " + howl.getHowlType().getDescription());
        for (Lycanthrope member : members) {
            if (member != howl.getEmitter()) {
                member.listenToHowl(howl);
            }
        }
    }

    /**
     * Notifies the pack of a rank change between two members.
     * This may trigger additional hierarchy adjustments.
     *
     * @param riser the lycanthrope who gained rank
     * @param demoted the lycanthrope who lost rank
     */
    public void notifyRankChange(Lycanthrope riser, Lycanthrope demoted) {
        System.out.println("[Pack " + name + "] Hierarchy change detected!");
        System.out.println("  " + riser.getName() + " has risen to " + riser.getHierarchyRank().getSymbol());
        System.out.println("  " + demoted.getName() + " has fallen to " + demoted.getHierarchyRank().getSymbol());

        // Check if alpha positions need to be updated
        if (riser.getHierarchyRank() == Rank.ALPHA) {
            if (riser.getSex().equalsIgnoreCase("male") || riser.getSex().equalsIgnoreCase("mâle") || riser.getSex().equalsIgnoreCase("m")) {
                if (alphaMale != riser) {
                    System.out.println("  New alpha male: " + riser.getName());
                    alphaMale = riser;
                }
            } else {
                if (alphaFemale != riser) {
                    System.out.println("  New alpha female: " + riser.getName());
                    alphaFemale = riser;
                }
            }
        }
    }

    /**
     * Simulates pack dynamics where members may compete for rank.
     * This method triggers random dominance displays based on hierarchy.
     */
    public void simulateHierarchyDynamics() {
        if (members.size() < 2) {
            return;
        }

        System.out.println("\n=== Pack Dynamics: " + name + " ===");
        java.util.Random random = new java.util.Random();

        // Omegas are often targeted
        List<Lycanthrope> omegas = getOmegas();
        if (!omegas.isEmpty() && members.size() > omegas.size()) {
            Lycanthrope omega = omegas.get(random.nextInt(omegas.size()));
            List<Lycanthrope> nonOmegas = members.stream()
                .filter(l -> l.getHierarchyRank() != Rank.OMEGA && l != omega)
                .collect(Collectors.toList());

            if (!nonOmegas.isEmpty()) {
                Lycanthrope aggressor = nonOmegas.get(random.nextInt(nonOmegas.size()));
                System.out.println("Omega " + omega.getName() + " is targeted by " + aggressor.getName());
                aggressor.dominate(omega);
            }
        }

        // Random challenges between adjacent ranks
        for (int i = 0; i < members.size() - 1; i++) {
            if (random.nextInt(100) < 20) { // 20% chance of challenge
                Lycanthrope challenger = members.get(i);
                Lycanthrope target = members.get(i + 1);

                if (challenger.getHierarchyRank() != null && target.getHierarchyRank() != null) {
                    if (challenger.getHierarchyRank().getHierarchyLevel() < target.getHierarchyRank().getHierarchyLevel()) {
                        challenger.showAggression(target);
                    }
                }
            }
        }

        System.out.println("=== End of Pack Dynamics ===\n");
    }

    /**
     * Resolves conflicts within the pack by establishing clear dominance.
     * This is useful when the pack hierarchy becomes unstable.
     */
    public void resolveConflicts() {
        System.out.println("\n--- Resolving conflicts in " + name + " ---");

        // Sort members by level to establish baseline hierarchy
        List<Lycanthrope> sortedMembers = new ArrayList<>(members);
        sortedMembers.sort((l1, l2) -> Integer.compare(l2.getLevel(), l1.getLevel()));

        System.out.println("Establishing order based on level:");
        for (int i = 0; i < sortedMembers.size(); i++) {
            Lycanthrope lycan = sortedMembers.get(i);
            System.out.println((i+1) + ". " + lycan.getName() + " (Level: " + lycan.getLevel() +
                ", Rank: " + lycan.getHierarchyRank().getSymbol() + ")");
        }

        System.out.println("--- Conflicts resolved ---\n");
    }

    @Override
    public String toString() {
        return "Pack{" +
                "name='" + name + '\'' +
                ", members=" + members.size() +
                ", hasAlphaCouple=" + hasAlphaCouple() +
                '}';
    }

    // ========== Reproduction System ==========

    /**
     * Initiates reproduction for the alpha couple.
     * Only the alpha couple (α) has the right to reproduce during mating season.
     * A litter of 1 to 7 young lycanthropes is expected after a gestation period.
     *
     * @return true if reproduction was initiated successfully, false otherwise
     */
    public boolean initiateReproduction() {
        if (!hasAlphaCouple()) {
            System.out.println("Cannot reproduce: pack does not have a complete alpha couple");
            return false;
        }

        // Check if alpha female is already pregnant
        for (Pregnancy pregnancy : pregnancies) {
            if (pregnancy.getMother() == alphaFemale) {
                System.out.println(alphaFemale.getName() + " is already pregnant");
                return false;
            }
        }

        // Check age requirements
        if (alphaFemale.getAgeCategory() != AgeCategory.ADULT) {
            System.out.println("Alpha female must be adult to reproduce");
            return false;
        }

        if (alphaMale.getAgeCategory() != AgeCategory.ADULT) {
            System.out.println("Alpha male must be adult to reproduce");
            return false;
        }

        System.out.println("\n=== Reproduction Initiated ===");
        System.out.println("Alpha couple " + alphaMale.getName() + " and " + alphaFemale.getName() + " are mating");

        // Random gestation period and litter size
        Random random = new Random();
        int gestationTurns = 5 + random.nextInt(4); // 5-8 turns
        int litterSize = 1 + random.nextInt(7); // 1-7 pups

        Pregnancy pregnancy = new Pregnancy(alphaFemale, gestationTurns, litterSize);
        pregnancies.add(pregnancy);

        System.out.println(alphaFemale.getName() + " is now pregnant");
        System.out.println("Expected litter size: " + litterSize + " pups");
        System.out.println("Gestation period: " + gestationTurns + " turns");
        System.out.println("=== End of Reproduction ===\n");

        return true;
    }

    /**
     * Processes pregnancies in the pack, advancing gestation and handling births.
     * Should be called each turn/season.
     */
    public void processPregnancies() {
        if (pregnancies.isEmpty()) {
            return;
        }

        System.out.println("\n--- Processing Pregnancies in " + name + " ---");

        List<Pregnancy> completedPregnancies = new ArrayList<>();

        for (Pregnancy pregnancy : pregnancies) {
            pregnancy.decrementGestation();

            if (pregnancy.isReadyToBirth()) {
                completedPregnancies.add(pregnancy);
            } else {
                System.out.println(pregnancy.getMother().getName() + " - " +
                    pregnancy.getGestationTurnsRemaining() + " turns remaining");
            }
        }

        // Handle births
        for (Pregnancy pregnancy : completedPregnancies) {
            giveBirth(pregnancy);
            pregnancies.remove(pregnancy);
        }

        System.out.println("--- End of Pregnancy Processing ---\n");
    }

    /**
     * Handles the birth of young lycanthropes.
     *
     * @param pregnancy the completed pregnancy
     */
    private void giveBirth(Pregnancy pregnancy) {
        System.out.println("\n*** BIRTH EVENT ***");
        System.out.println(pregnancy.getMother().getName() + " is giving birth!");

        Random random = new Random();
        int actualLitterSize = pregnancy.getLitterSize();

        // Small chance of complications reducing litter size
        if (random.nextInt(100) < 10) {
            actualLitterSize = Math.max(1, actualLitterSize - random.nextInt(3));
            System.out.println("Complications during birth! Litter size reduced.");
        }

        System.out.println("Number of pups born: " + actualLitterSize);

        // Generate young lycanthropes
        for (int i = 0; i < actualLitterSize; i++) {
            String sex = random.nextBoolean() ? "male" : "female";
            String puppyName = generatePuppyName(sex, i + 1);

            // Young lycanthropes have lower stats
            int strength = 5 + random.nextInt(10);
            int endurance = 5 + random.nextInt(10);
            double height = 0.3 + (random.nextDouble() * 0.2); // 0.3-0.5m for pups
            int impetuosity = random.nextInt(30);

            Lycanthrope pup = new Lycanthrope(
                puppyName,
                sex,
                height,
                0, // newborn
                strength,
                endurance,
                AgeCategory.YOUNG,
                0, // no domination factor yet
                Rank.OMEGA, // pups start at lowest rank
                impetuosity
            );

            addMember(pup);
            System.out.println("  - Born: " + puppyName + " (" + sex + ")");
        }

        System.out.println("Mother " + pregnancy.getMother().getName() + " and all pups are healthy!");
        System.out.println("*** END OF BIRTH EVENT ***\n");
    }

    /**
     * Generates a name for a newborn lycanthrope pup.
     *
     * @param sex the sex of the pup
     * @param number the pup number in the litter
     * @return a generated name
     */
    private String generatePuppyName(String sex, int number) {
        String[] maleNames = {"Fenrir Jr.", "Lupus", "Shadow", "Hunter", "Fang", "Ash", "Storm"};
        String[] femaleNames = {"Luna Jr.", "Selene", "Mist", "Willow", "Nova", "Ember", "Rain"};

        Random random = new Random();

        if (sex.equalsIgnoreCase("male") || sex.equalsIgnoreCase("mâle") || sex.equalsIgnoreCase("m")) {
            return maleNames[random.nextInt(maleNames.length)] + number;
        } else {
            return femaleNames[random.nextInt(femaleNames.length)] + number;
        }
    }

    /**
     * Gets the list of ongoing pregnancies in the pack.
     *
     * @return list of pregnancies
     */
    public List<Pregnancy> getPregnancies() {
        return Collections.unmodifiableList(pregnancies);
    }

    /**
     * Checks if there are any ongoing pregnancies.
     *
     * @return true if at least one pregnancy is in progress
     */
    public boolean hasPregnancies() {
        return !pregnancies.isEmpty();
    }
}

