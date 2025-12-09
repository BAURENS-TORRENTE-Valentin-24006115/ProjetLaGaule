package fr.iut.laGaule.model.Character.MythicalCreature;

import fr.iut.laGaule.model.Place.Enclosure;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the master of a fantasy zoo who manages lycanthropes.
 * The zoo master has the ability to move solitary lycanthropes individually
 * from one enclosure to another.
 *
 * According to the rules:
 * - Can move solitary lycanthropes between enclosures
 * - When at least one solitary male and one solitary female meet in an enclosure
 *   where there is no existing pack, a new pack is formed
 * - The lycanthropes in the new pack are no longer considered solitary
 */
public class FantasyZooMaster {

    private String name;
    private List<Enclosure> managedEnclosures;

    /**
     * Constructs a new FantasyZooMaster.
     *
     * @param name the name of the zoo master
     */
    public FantasyZooMaster(String name) {
        this.name = name;
        this.managedEnclosures = new ArrayList<>();
    }

    /**
     * Adds an enclosure to the list of managed enclosures.
     *
     * @param enclosure the enclosure to manage
     */
    public void addManagedEnclosure(Enclosure enclosure) {
        if (!managedEnclosures.contains(enclosure)) {
            managedEnclosures.add(enclosure);
            System.out.println(name + " now manages enclosure: " + enclosure.getName());
        }
    }

    /**
     * Removes an enclosure from management.
     *
     * @param enclosure the enclosure to stop managing
     */
    public void removeManagedEnclosure(Enclosure enclosure) {
        if (managedEnclosures.remove(enclosure)) {
            System.out.println(name + " no longer manages enclosure: " + enclosure.getName());
        }
    }

    /**
     * Moves a solitary lycanthrope from one enclosure to another.
     * Only solitary lycanthropes can be moved individually.
     * After moving, checks if a new pack should be formed.
     *
     * @param lycanthrope the lycanthrope to move
     * @param fromEnclosure the source enclosure
     * @param toEnclosure the destination enclosure
     * @return true if the move was successful, false otherwise
     */
    public boolean moveLycanthrope(Lycanthrope lycanthrope, Enclosure fromEnclosure, Enclosure toEnclosure) {
        if (lycanthrope == null || fromEnclosure == null || toEnclosure == null) {
            System.out.println("Error: Invalid parameters for moving lycanthrope");
            return false;
        }

        // Check if the lycanthrope is solitary
        if (!lycanthrope.isSolitary()) {
            System.out.println("Error: " + name + " can only move solitary lycanthropes.");
            System.out.println(lycanthrope.getName() + " is part of pack " + lycanthrope.getPack().getName());
            return false;
        }

        // Check if both enclosures are managed
        if (!managedEnclosures.contains(fromEnclosure)) {
            System.out.println("Error: " + name + " does not manage enclosure " + fromEnclosure.getName());
            return false;
        }

        if (!managedEnclosures.contains(toEnclosure)) {
            System.out.println("Error: " + name + " does not manage enclosure " + toEnclosure.getName());
            return false;
        }

        // Check if the lycanthrope is in the source enclosure
        if (!fromEnclosure.getCharacter().contains(lycanthrope)) {
            System.out.println("Error: " + lycanthrope.getName() + " is not in enclosure " + fromEnclosure.getName());
            return false;
        }

        System.out.println("\n=== Zoo Master Action ===");
        System.out.println(name + " is moving " + lycanthrope.getName() + " from " +
                          fromEnclosure.getName() + " to " + toEnclosure.getName());

        // Perform the move
        fromEnclosure.getCharacter().remove(lycanthrope);
        fromEnclosure.setNbCharacter(fromEnclosure.getNbCharacter() - 1);
        toEnclosure.addCharacter(lycanthrope);

        System.out.println("Move successful!");
        System.out.println("=== End of Zoo Master Action ===\n");

        // Check if a new pack should be formed in the destination enclosure
        checkAndFormNewPack(toEnclosure);

        return true;
    }

    /**
     * Checks if a new pack should be formed in an enclosure.
     * A new pack is formed when:
     * - There is at least one solitary male and one solitary female
     * - They are in the same enclosure
     * - There is no existing pack in that location
     *
     * @param enclosure the enclosure to check
     */
    private void checkAndFormNewPack(Enclosure enclosure) {
        if (enclosure == null) {
            return;
        }

        // Find all solitary lycanthropes in the enclosure
        List<Lycanthrope> solitaryMales = new ArrayList<>();
        List<Lycanthrope> solitaryFemales = new ArrayList<>();
        boolean hasExistingPack = false;

        for (var character : enclosure.getCharacter()) {
            if (character instanceof Lycanthrope lycan) {
                if (lycan.isSolitary()) {
                    String sex = lycan.getSex().toLowerCase();
                    if (sex.equals("male") || sex.equals("mâle") || sex.equals("m")) {
                        solitaryMales.add(lycan);
                    } else if (sex.equals("female") || sex.equals("femelle") || sex.equals("f")) {
                        solitaryFemales.add(lycan);
                    }
                } else if (lycan.getPack() != null) {
                    hasExistingPack = true;
                }
            }
        }

        // If there's already a pack in this enclosure, don't form a new one
        if (hasExistingPack) {
            System.out.println("Enclosure " + enclosure.getName() + " already has a pack. No new pack will be formed.");
            return;
        }

        // Check if we have at least one male and one female solitary lycanthrope
        if (!solitaryMales.isEmpty() && !solitaryFemales.isEmpty()) {
            formNewPack(enclosure, solitaryMales, solitaryFemales);
        }
    }

    /**
     * Forms a new pack with solitary lycanthropes.
     * The new pack hierarchy is established based on level and age.
     *
     * @param enclosure the enclosure where the pack is formed
     * @param solitaryMales list of solitary males
     * @param solitaryFemales list of solitary females
     */
    private void formNewPack(Enclosure enclosure, List<Lycanthrope> solitaryMales, List<Lycanthrope> solitaryFemales) {
        System.out.println("\n*** NEW PACK FORMATION ***");
        System.out.println("Location: " + enclosure.getName());
        System.out.println("Solitary males present: " + solitaryMales.size());
        System.out.println("Solitary females present: " + solitaryFemales.size());

        // Generate a pack name
        String packName = "Pack of " + enclosure.getName();
        Pack newPack = new Pack(packName);

        // Add all solitary males and females to the new pack
        System.out.println("\nAdding members to new pack:");
        for (Lycanthrope male : solitaryMales) {
            male.joinPack(newPack);
            System.out.println("  + " + male.getName() + " (male, level: " + male.getLevel() + ")");
        }

        for (Lycanthrope female : solitaryFemales) {
            female.joinPack(newPack);
            System.out.println("  + " + female.getName() + " (female, level: " + female.getLevel() + ")");
        }

        // Establish alpha couple (highest level adult male and female)
        Lycanthrope bestMale = solitaryMales.stream()
            .filter(l -> l.getAgeCategory() == AgeCategory.ADULT)
            .max((l1, l2) -> Integer.compare(l1.getLevel(), l2.getLevel()))
            .orElse(null);

        Lycanthrope bestFemale = solitaryFemales.stream()
            .filter(l -> l.getAgeCategory() == AgeCategory.ADULT)
            .max((l1, l2) -> Integer.compare(l1.getLevel(), l2.getLevel()))
            .orElse(null);

        if (bestMale != null && bestFemale != null) {
            newPack.setAlphaMale(bestMale);
            newPack.setAlphaFemale(bestFemale);
            System.out.println("\nAlpha couple established:");
            System.out.println("  Alpha Male: " + bestMale.getName() + " (level: " + bestMale.getLevel() + ")");
            System.out.println("  Alpha Female: " + bestFemale.getName() + " (level: " + bestFemale.getLevel() + ")");
        } else {
            System.out.println("\nWarning: No suitable adult pair found for alpha couple.");
            if (bestMale != null) {
                newPack.setAlphaMale(bestMale);
                System.out.println("  Alpha Male: " + bestMale.getName());
            }
            if (bestFemale != null) {
                newPack.setAlphaFemale(bestFemale);
                System.out.println("  Alpha Female: " + bestFemale.getName());
            }
        }

        System.out.println("\nNew pack '" + packName + "' successfully formed with " +
                          newPack.getSize() + " members!");
        System.out.println("These lycanthropes are no longer solitary.");
        System.out.println("*** END OF PACK FORMATION ***\n");
    }

    /**
     * Displays all managed enclosures and their lycanthrope populations.
     */
    public void displayManagedEnclosures() {
        System.out.println("\n=== Zoo Master: " + name + " ===");
        System.out.println("Managed Enclosures: " + managedEnclosures.size());

        for (Enclosure enclosure : managedEnclosures) {
            System.out.println("\n- " + enclosure.getName() + " (Area: " + enclosure.getArea() + " m²)");

            List<Lycanthrope> lycanthropes = new ArrayList<>();
            for (var character : enclosure.getCharacter()) {
                if (character instanceof Lycanthrope lycan) {
                    lycanthropes.add(lycan);
                }
            }

            if (lycanthropes.isEmpty()) {
                System.out.println("  No lycanthropes present");
            } else {
                System.out.println("  Lycanthropes: " + lycanthropes.size());

                long solitaryCount = lycanthropes.stream().filter(Lycanthrope::isSolitary).count();
                System.out.println("  - Solitary: " + solitaryCount);
                System.out.println("  - In packs: " + (lycanthropes.size() - solitaryCount));

                for (Lycanthrope lycan : lycanthropes) {
                    String status = lycan.isSolitary() ? "SOLITARY" : "Pack: " + lycan.getPack().getName();
                    System.out.println("    • " + lycan.getName() + " (" + lycan.getSex() + ", " + status + ")");
                }
            }
        }

        System.out.println("================================\n");
    }

    /**
     * Gets the name of the zoo master.
     *
     * @return the zoo master's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the zoo master.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of managed enclosures.
     *
     * @return unmodifiable list of managed enclosures
     */
    public List<Enclosure> getManagedEnclosures() {
        return List.copyOf(managedEnclosures);
    }
}

