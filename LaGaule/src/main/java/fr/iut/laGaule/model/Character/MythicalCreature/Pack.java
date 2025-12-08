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
    private List<Lycanthrope> members;
    private Lycanthrope alphaMale;
    private Lycanthrope alphaFemale;

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
    }

    /**
     * Adds a member to the pack.
     * If the lycanthrope is already in another pack, it will leave that pack first.
     *
     * @param lycanthrope the lycanthrope to add
     */
    public void addMember(Lycanthrope lycanthrope) {
        if (!members.contains(lycanthrope)) {
            members.add(lycanthrope);
            lycanthrope.setPack(this);
            lycanthrope.setSolitary(false);
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
            lycanthrope.setPack(null);
            lycanthrope.setSolitary(true);
            System.out.println(lycanthrope.getName() + " has left the pack: " + name);
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
     * Broadcasts a message to all pack members.
     * All members will hear the message if they are healthy enough.
     *
     * @param message the message to broadcast
     * @param sender the lycanthrope sending the message
     */
    public void broadcast(String message, Lycanthrope sender) {
        System.out.println("[Pack " + name + "] " + sender.getName() + " howls: " + message);
        for (Lycanthrope member : members) {
            if (member != sender) {
                member.listenToHowl(message);
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
}

