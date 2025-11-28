package fr.iut.laGaule.model;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import fr.iut.laGaule.model.Place.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Random;

/**
 * Classe qui gère le théâtre d'envahissement
 */
public class InvasionTheater {

    private String nom;
    private int maxLieux;
    private LinkedList<Place> lieux;
    private LinkedList<ClanLeader> chefsClans;
    private Random random;

    public InvasionTheater(String name, int maxPlaces) {
        this.nom = name;
        this.maxLieux = maxPlaces;
        this.lieux = new LinkedList<>();
        this.chefsClans = new LinkedList<>();
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

        Iterator<Place> it = lieux.iterator();
        int i = 1;
        while (it.hasNext()) {
            Place lieu = it.next();
            System.out.println("[" + i + "] " + lieu.getName() + " (" + lieu.getClass().getSimpleName() + ")");
            i++;
        }
    }

    public int compterPersonnages() {
        int total = 0;
        Iterator<Place> it = lieux.iterator();
        while (it.hasNext()) {
            Place lieu = it.next();
            total += lieu.getNbCharacter();
        }
        return total;
    }

    public void afficherTousLesPersonnages() {
        System.out.println("\n--- Tous les personnages ---");

        Iterator<Place> it = lieux.iterator();
        while (it.hasNext()) {
            Place lieu = it.next();
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

        Iterator<Place> it = lieux.iterator();
        while (it.hasNext()) {
            Place lieu = it.next();

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

        Iterator<Place> it = lieux.iterator();
        while (it.hasNext()) {
            Place lieu = it.next();

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
        Iterator<Place> it = lieux.iterator();
        while (it.hasNext()) {
            Place lieu = it.next();

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

    public LinkedList<Place> getLieux() {
        return lieux;
    }

    public LinkedList<ClanLeader> getChefsClans() {
        return chefsClans;
    }

    public int getMaxLieux() {
        return maxLieux;
    }
}