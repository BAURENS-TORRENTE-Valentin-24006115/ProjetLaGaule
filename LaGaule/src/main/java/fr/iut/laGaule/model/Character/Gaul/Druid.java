package fr.iut.laGaule.model.Character.Gaul;


import fr.iut.laGaule.model.Character.Roman.Roman;

import java.util.Random;

/**
 * Represents a Druid character in the Gaul faction.
 * Druids are special characters capable of concocting magic potions,
 * advising the village, and fighting Romans with enhanced strength.
 *
 * @see Gaul
 */
public class Druid extends Gaul {

    /**
     * Constructs a new Druid with the specified attributes.
     *
     * @param name the name of the druid
     * @param sex the sex/gender of the druid
     * @param height the height of the druid in meters
     * @param age the age of the druid in years
     * @param strength the strength attribute of the druid
     * @param endurance the endurance attribute of the druid
     */
    public Druid(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }

    /**
     * Allows the druid to concoct a magic potion in the cauldron.
     * Displays a message indicating the druid is preparing the potion.
     */
    public void concoctPotion() {
        System.out.println(this.name + " is stirring the magic potion in the cauldron.");
    }

    /**
     * Allows the druid to give orders and advice to other Gauls.
     * The druid commands the specified Gaul to work.
     *
     * @param gaul the Gaul who should follow the druid's advice
     */
    public void command(Gaul gaul) {
        System.out.println(this.name + " advices the village with wisdom.");
        gaul.work();

    }

    /**
     * The druid performs their daily work by gathering herbs in the forest.
     * These herbs are used to prepare magic potions.
     */
    public void work() {
        System.out.println(this.name + " gathers herbs in the forest.");
        //todo add random plant
    }

    /**
     * Handles combat between the druid and a Roman character.
     * The druid benefits from a strength bonus (x1.5) during attack.
     * Combat proceeds in alternating turns until one combatant is defeated (health <= 0).
     *
     * Damage is calculated using the formula:
     * - Druid attack: (strength * 1.5 - enemy_endurance/10 - enemy_health/4) / 4
     * - Roman counterattack: (strength - druid_endurance/10 - druid_health/4) / 4
     *
     * Minimum damage is always 1 point.
     *
     * @param character the Roman character to fight
     */
    /**
     * Combat contre un Romain.
     * Le Druide attaque en premier (et peut-être avec magie/potion).
     * Si le Romain survit, il riposte.
     */
    public void fight(Roman character) {
        if (character == null || character.getHealth() <= 0) {
            System.out.println(this.name + " regarde le Romain déjà à terre.");
            return;
        }

        System.out.println("⚔️ " + this.name + " lève sa serpe d'or contre " + character.getName() + " !");

        // --- 1. TOUR DU DRUIDE ---

        // Bonus énorme si sous potion magique
        double potionMultiplier = (this.magicPotionLevel > 0) ? 3.0 : 1.0;

        // Facteur aléatoire (entre 0.8 et 1.2) pour varier les dégâts de +/- 20%
        double randomFactor = 0.8 + (Math.random() * 0.4);

        // Calcul : Force * Potion * Random - (Endurance ennemie / 3)
        // On divise l'endurance par 3 au lieu de 10 pour que l'armure serve à quelque chose,
        // mais on ne divise plus l'attaque par 4.
        int rawDamage = (int) ((this.getStrength() * 1.2 * potionMultiplier * randomFactor) - (character.getEndurance() / 3.0));

        // Coup Critique (20% de chance) : La serpe d'or touche un point sensible !
        boolean isCrit = Math.random() < 0.2;
        if (isCrit) {
            rawDamage *= 2;
            System.out.println("✨ COUP CRITIQUE ! La serpe d'or étincelle !");
        }

        // Dégâts minimum de 1 garantis
        int damageDealt = Math.max(1, rawDamage);

        // Application des dégâts
        character.receiveDamage(damageDealt);
        System.out.println("   -> " + this.name + " inflige " + damageDealt + " dégâts. (PV Romain: " + character.getHealth() + ")");

        // --- 2. RIPOSTE DU ROMAIN (S'il est encore vivant) ---
        if (character.getHealth() > 0) {
            System.out.println("🛡️ " + character.getName() + " riposte avec son glaive !");

            // Les romains sont disciplinés : moins d'aléatoire, dégâts constants
            // Formule : Force Ennemie - (Endurance Druide / 2) -> Les druides ont des robes, pas d'armure lourde
            int romanDamage = (int) (character.getStrength() - (this.getEndurance() / 4.0));

            // Le romain est affaibli s'il a pris cher juste avant
            if (character.getHealth() < 20) {
                romanDamage /= 2;
                System.out.println("   (Le Romain chancelle et frappe moins fort)");
            }

            romanDamage = Math.max(1, romanDamage);

            this.receiveDamage(romanDamage);
            System.out.println("   -> " + character.getName() + " inflige " + romanDamage + " dégâts. (PV Druide: " + this.getHealth() + ")");
        } else {
            System.out.println("💀 " + character.getName() + " s'effondre. Victoire pour la Gaule !");
        }
    }
}