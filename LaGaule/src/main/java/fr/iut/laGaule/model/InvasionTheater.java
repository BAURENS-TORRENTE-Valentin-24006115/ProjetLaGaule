package fr.iut.laGaule.model;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import fr.iut.laGaule.model.Place.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Classe qui gère le théâtre d'envahissement
 */
public class InvasionTheater {

    private String nom;
    private int maxLieux;
    private ArrayList<Place> lieux;
    private ArrayList<ClanLeader> chefsClans;
    private Random random;

    public InvasionTheater(String name, int maxPlaces) {
        this.nom = name;
        this.maxLieux = maxPlaces;
        this.lieux = new ArrayList<>();
        this.chefsClans = new ArrayList<>();
        this.random = new Random();

        System.out.println("Théâtre d'envahissement créé : " + nom);
        System.out.println("Capacité max : " + maxLieux + " lieux\n");
    }

    public void ajouterLieu(Place lieu) {
        if (lieux.size() >= maxLieux) {
            System.out.println("Impossible d'ajouter " + lieu.getName() + " (capacité max atteinte)");
            return;
        }

        lieux.add(lieu);
        System.out.println("Lieu ajouté : " + lieu.getName());

        if (lieu.getClanLeader() != null) {
            chefsClans.add(lieu.getClanLeader());
        }
    }

    public void afficherLieux() {
        System.out.println("\n--- Lieux dans " + nom + " ---");
        System.out.println("Nombre de lieux : " + lieux.size() + "/" + maxLieux);

        if (lieux.isEmpty()) {
            System.out.println("Aucun lieu.");
            return;
        }

        for (int i = 0; i < lieux.size(); i++) {
            Place lieu = lieux.get(i);
            System.out.println("[" + (i+1) + "] " + lieu.getName() + " (" + lieu.getClass().getSimpleName() + ")");
        }
    }

    public int compterPersonnages() {
        int total = 0;
        for (Place lieu : lieux) {
            total += lieu.getNbCharacter();
        }
        return total;
    }

    public void afficherTousLesPersonnages() {
        System.out.println("\n--- Tous les personnages ---");

        for (Place lieu : lieux) {
            System.out.println("\n" + lieu.getName() + " :");

            if (lieu.getCharacter().isEmpty()) {
                System.out.println("  (vide)");
            } else {
                for (Character perso : lieu.getCharacter()) {
                    System.out.println("  - " + perso.getName() + " (PV: " + perso.getHealth() + ")");
                }
            }
        }
    }

    public void lancerSimulation(int nombreTours) {
        System.out.println("\n\n*** Début de la simulation (" + nombreTours + " tours) ***\n");

        for (int tour = 1; tour <= nombreTours; tour++) {
            System.out.println("--- Tour " + tour + " ---");

            faireApparaitreAliments();
            modifierEtatsPersonnages();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // rien
            }
        }

        System.out.println("\n*** Fin de la simulation ***\n");
        afficherResumeFinal();
    }

    private void faireApparaitreAliments() {
        System.out.println("Apparition d'aliments...");

        for (Place lieu : lieux) {
            if (lieu instanceof BattleFields) {
                continue;
            }

            if (random.nextInt(100) < 30) {
                Foods[] aliments = Foods.values();
                Foods aliment = aliments[random.nextInt(aliments.length)];
                lieu.addFood(aliment);
            }
        }
    }

    private void modifierEtatsPersonnages() {
        System.out.println("Modification des états...");

        int modifs = 0;

        for (Place lieu : lieux) {
            for (Character perso : lieu.getCharacter()) {
                if (random.nextInt(100) < 20) {
                    int degats = random.nextInt(10) + 1;
                    perso.receiveDamage(degats);
                    System.out.println("  " + perso.getName() + " perd " + degats + " PV");
                    modifs++;
                }
            }
        }

        if (modifs == 0) {
            System.out.println("  Rien ce tour-ci");
        }
        System.out.println();
    }

    private void afficherResumeFinal() {
        System.out.println("Résumé :");
        System.out.println("- Lieux : " + lieux.size());
        System.out.println("- Chefs de clan : " + chefsClans.size());

        int survivants = 0;
        for (Place lieu : lieux) {
            for (Character perso : lieu.getCharacter()) {
                if (perso.getHealth() > 0) {
                    survivants++;
                }
            }
        }
        System.out.println("- Survivants : " + survivants);
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public ArrayList<Place> getLieux() {
        return lieux;
    }

    public ArrayList<ClanLeader> getChefsClans() {
        return chefsClans;
    }

    public int getMaxLieux() {
        return maxLieux;
    }
}