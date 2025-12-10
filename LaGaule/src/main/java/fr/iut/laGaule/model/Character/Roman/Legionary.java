package fr.iut.laGaule.model.Character.Roman;

import fr.iut.laGaule.model.Character.Gaul.Gaul;

/**
 * Represents a Legionary character in the Roman faction.
 * Legionaries are Roman soldiers who fight against the Gauls.
 * They have enhanced combat abilities with a strength bonus.
 *
 * @see Roman
 */
public class Legionary extends Roman{

    /**
     * Constructs a new Legionary with the specified attributes.
     *
     * @param name the name of the legionary
     * @param sex the sex/gender of the legionary
     * @param height the height of the legionary in meters
     * @param age the age of the legionary in years
     * @param strength the strength attribute of the legionary
     * @param endurance the endurance attribute of the legionary
     */
    public Legionary(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }

    /**
     * Handles combat between the legionary and a Gaul character.
     * The legionary benefits from a strength bonus (x1.5) during attack.
     * Combat proceeds in alternating turns until one combatant is defeated (health <= 0).
     *
     * Damage is calculated using the formula:
     * - Legionary attack: (strength * 1.5 - enemy_endurance/10 - enemy_health/4) / 4
     * - Gaul counterattack: (strength - legionary_endurance/10 - legionary_health/4) / 4
     *
     * Minimum damage is always 1 point.
     *
     * @param character the Gaul character to fight
     */
    /**
     * Combat contre un Gaulois.
     * Le Romain attaque avec discipline militaire.
     * Attention à la riposte si le Gaulois est tombé dans la marmite !
     */
    public void fight(Gaul target) { // Assurez-vous que l'import de Gaul est bon
        if (target == null || target.getHealth() <= 0) {
            System.out.println(this.name + " constate que le barbare est déjà vaincu.");
            return;
        }

        System.out.println("ave " + this.name + " dégaine son glaive face à " + target.getName() + " !");

        // --- 1. ATTAQUE DU ROMAIN ---

        // Facteur de discipline : Les romains sont constants (0.9 à 1.1)
        double disciplineFactor = 0.9 + (Math.random() * 0.2);

        // Bonus de formation "Tortue" : Si le romain a beaucoup de vie, il tape mieux
        double formationBonus = (this.getHealth() > 80) ? 1.2 : 1.0;

        // Calcul : Force * Discipline * Formation - (Endurance Gaulois / 5)
        // L'endurance du Gaulois compte peu (ils se battent souvent torse nu), donc divisée par 5
        int rawDamage = (int) ((this.getStrength() * disciplineFactor * formationBonus) - (target.getEndurance() / 5.0));

        // Coup Spécial (15% de chance) : Jet de Pilum (Javelot)
        boolean pilumThrow = Math.random() < 0.15;
        if (pilumThrow) {
            rawDamage += 15; // Dégâts fixes bonus
            System.out.println("⚡ PILUM ! " + this.name + " lance son javelot avec précision !");
        }

        // Dégâts minimum de 1
        int damageDealt = Math.max(1, rawDamage);

        target.receiveDamage(damageDealt);
        System.out.println("   -> " + this.name + " inflige " + damageDealt + " dégâts. (PV Gaulois: " + target.getHealth() + ")");

        // --- 2. RIPOSTE DU GAULOIS (S'il survit) ---
        if (target.getHealth() > 0) {
            System.out.println("😡 " + target.getName() + " prépare sa baffe !");

            // CHECK DE LA POTION MAGIQUE (C'est là que le Romain prend cher)
            // Note : Il faut un getter ou accès public à magicPotionLevel dans Character/Gaul
            double potionMultiplier = (target.getMagicPotionLevel() > 0) ? 4.0 : 1.0;

            if (potionMultiplier > 1.0) {
                System.out.println("   😱 'Par Jupiter ! Il a de la potion magique !'");
            }

            // Le bouclier romain (Scutum) est efficace : Endurance divise par 2
            int gaulDamage = (int) ((target.getStrength() * potionMultiplier) - (this.getEndurance() / 2.0));

            // Facteur "Baffe aléatoire" (Les gaulois sont moins précis)
            gaulDamage *= (0.5 + Math.random());

            gaulDamage = Math.max(1, gaulDamage);

            this.receiveDamage(gaulDamage);
            System.out.println("   -> " + target.getName() + " inflige " + gaulDamage + " dégâts. (PV Romain: " + this.getHealth() + ")");

            // Petit easter egg si le Romain meurt sur le coup
            if (this.getHealth() <= 0 && potionMultiplier > 1.0) {
                System.out.println("🚀 " + this.name + " décolle vers l'orbite ! *Ding*");
            }
        } else {
            System.out.println("🏆 " + target.getName() + " est K.O. Une victoire pour l'Empire !");
        }
    }
    
}