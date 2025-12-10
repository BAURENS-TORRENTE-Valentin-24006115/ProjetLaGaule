package fr.iut.laGaule.model.Character.Gaul;


import fr.iut.laGaule.Serializer;
import fr.iut.laGaule.model.Character.Character;

import java.util.*;

/**
 * Represents a Blacksmith character in the Gaul faction.
 * A Blacksmith can forge weapons and shields, and their work randomly
 * enhances the strength or endurance of other Gaul characters.
 *
 * @see Gaul
 */
public class Blacksmith extends Gaul{

    /**
     * Constructs a new Blacksmith with the specified attributes.
     *
     * @param name the name of the blacksmith
     * @param sex the sex/gender of the blacksmith
     * @param height the height of the blacksmith in meters
     * @param age the age of the blacksmith in years
     * @param strength the strength attribute of the blacksmith
     * @param endurance the endurance attribute of the blacksmith
     */
    public Blacksmith(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }


    /**
     * Performs the blacksmith's work activity.
     * The blacksmith forges swords and shields, and as a result of their work,
     * a random Gaul character from the saved data receives a random bonus
     * to either their strength or endurance (0-19 points).
     *
     *
     * If deserialization fails or returns empty data, an error message is displayed
     * and the method returns without making changes.
     */
    public void work() {
        // 1. Vérifier si on est bien dans un lieu en mémoire
        if (this.place == null) {
            System.out.println("Erreur : Le forgeron " + this.getName() + " n'est nulle part (place est null).");
            return;
        }

        // 2. Récupérer la vraie liste des gens présents ici
        // (On travaille sur la référence mémoire, donc l'affichage se mettra à jour)
        ArrayList<fr.iut.laGaule.model.Character.Character> neighbors = this.place.getCharacter();

        if (neighbors == null || neighbors.isEmpty()) {
            this.setLastAction("Personne à équiper ici...");
            return;
        }

        // 3. Filtrer pour trouver des cibles (Gaulois)
        java.util.List<Gaul> potentialTargets = new java.util.ArrayList<>();
        for (Character c : neighbors) {
            // On équipe les autres Gaulois (et soi-même si on veut)
            if (c instanceof Gaul) {
                potentialTargets.add((Gaul) c);
            }
        }

        if (potentialTargets.isEmpty()) {
            this.setLastAction("Pas de Gaulois à équiper.");
            return;
        }

        // 4. Choisir un Gaulois au hasard
        Random rand = new Random();
        Gaul target = potentialTargets.get(rand.nextInt(potentialTargets.size()));

        // 5. Améliorer ses stats (DIRECTEMENT SUR L'OBJET)
        int boost = 5 + rand.nextInt(6);

        if (rand.nextBoolean()) {
            // Améliore la force (épée)
            target.setStrength(target.getStrength() + boost);
            this.setLastAction("Aiguise l'épée de " + target.getName() + " (Force +" + boost + ") ⚔️");

            // Petit log console pour vérifier
            System.out.println("FORGERON: " + target.getName() + " passe à " + target.getStrength() + " de Force.");
        } else {
            // Améliore l'endurance (bouclier)
            target.setEndurance(target.getEndurance() + boost);
            this.setLastAction("Renforce le bouclier de " + target.getName() + " (Endu +" + boost + ") 🛡️");

            System.out.println("FORGERON: " + target.getName() + " passe à " + target.getEndurance() + " d'Endurance.");
        }
    }
}
