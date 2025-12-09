package fr.iut.laGaule.process;

import fr.iut.laGaule.model.Place.*;
import fr.iut.laGaule.process.CharacterThread; // Ton Runnable
import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.Gaul.*;
import fr.iut.laGaule.model.Character.Roman.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InvasionTheatre {
    private void deleteSaveFiles() {
        System.out.println("--- Nettoyage des anciennes sauvegardes (.ser) ---");
        File currentDir = new File(".");

        // Filtre pour ne prendre que les fichiers finissant par .ser
        File[] files = currentDir.listFiles((dir, name) -> name.endsWith(".ser"));

        if (files != null) {
            for (File file : files) {
                if (file.delete()) {
                    System.out.println("Supprimé : " + file.getName());
                } else {
                    System.err.println("Impossible de supprimer : " + file.getName());
                }
            }
        }
        System.out.println("--------------------------------------------------");
    }

    public void invasionTheatre(int nbZones, int nbCharacters, int durationSeconds) {
        deleteSaveFiles();
        if (nbZones < 3) {
            System.err.println("Erreur: Il faut au moins 3 zones.");
            return;

        }

        System.out.println("=== DÉBUT DU THÉÂTRE D'ENVAHISSEMENT ===");

        // 1. Création des Lieux
        List<Place> places = new ArrayList<>();
        Random rand = new Random();

        // --- A. Les 3 Lieux Obligatoires (Hardcodés pour garantir leur présence) ---

        // Index 0 : Le Village Gaulois (QG des Gaulois)
        Place village = new GaulVillage("Village des Irréductibles", 200, null, 0, new ArrayList<>(), new ArrayList<>());

        // Index 1 : Le Camp Romain (QG des Romains)
        Place camp = new RomanFortifiedCamp("Camp de Babaorum", 200, null, 0, new ArrayList<>(), new ArrayList<>());

        // Index 2 : Le Champ de Bataille (Zone neutre de combat)
        Place battlefield = new BattleFields("Grande Plaine", 1000, null, 0, new ArrayList<>(), new ArrayList<>());

        places.add(village);
        places.add(camp);
        places.add(battlefield);

        // --- B. Les Lieux Supplémentaires (Choisis aléatoirement parmi les types existants) ---

        for (int i = 3; i < nbZones; i++) {
            Place p = null;
            String nom = "Zone " + (i - 2); // Nom générique

            // On tire un nombre entre 0 et 4 pour choisir le type de lieu
            int typeLieu = rand.nextInt(5);

            switch (typeLieu) {
                case 0:
                    // Champ de bataille supplémentaire
                    p = new BattleFields("Maquis de " + nom, 500, null, 0, new ArrayList<>(), new ArrayList<>());
                    break;
                case 1:
                    // Bourgade Gallo-Romaine (Accessible à tous)
                    p = new GalloRomanVillage("Bourgade " + nom, 150, null, 0, new ArrayList<>(), new ArrayList<>());
                    break;
                case 2:
                    // Village Gaulois supplémentaire (Gaulois uniquement)
                    p = new GaulVillage("Hameau " + nom, 100, null, 0, new ArrayList<>(), new ArrayList<>());
                    break;
                case 3:
                    // Ville Romaine (Romains uniquement)
                    p = new RomanCity("Civitas " + nom, 300, null, 0, new ArrayList<>(), new ArrayList<>());
                    break;
                case 4:
                    // Camp Romain supplémentaire (Romains uniquement)
                    p = new RomanFortifiedCamp("Camp Fortifié " + nom, 150, null, 0, new ArrayList<>(), new ArrayList<>());
                    break;
            }

            if (p != null) {
                places.add(p);
                System.out.println("Lieu créé : " + p.getName() + " (" + p.getClass().getSimpleName() + ")");
            }
        }

        // 2. Création des Personnages et des Runnables
        // On stocke les Runnables pour pouvoir accéder aux stats (santé) et les arrêter plus tard
        List<CharacterThread> runnables = new ArrayList<>();
        rand = new Random();

        System.out.println("Création et lancement de " + nbCharacters + " personnages...");

        for (int i = 0; i < nbCharacters; i++) {
            Character c = null;
            Place startPlace;

            // Génération aléatoire des stats et du type (Code identique au précédent...)
            double height = 1.50 + (rand.nextDouble() * 0.50);
            int age = 18 + rand.nextInt(50);
            int strength = 10 + rand.nextInt(20);
            int endurance = 10 + rand.nextInt(20);
            boolean isGaulois = rand.nextBoolean();
            String nameSuffix = " " + i;

            if (isGaulois) {
                startPlace = village;
                int type = rand.nextInt(5);
                switch (type) {
                    case 0: c = new Blacksmith("Forgeron" + nameSuffix, "M", height, age, strength, endurance); break;
                    case 1: c = new Druid("Druide" + nameSuffix, "M", height, age, strength, endurance); break;
                    case 2: c = new Innkeeper("Aubergiste" + nameSuffix, "M", height, age, strength, endurance); break;
                    case 3: c = new Merchant("Marchand" + nameSuffix, "M", height, age, strength, endurance); break;
                    default: c = new Gaul("Gaulois" + nameSuffix, "M", height, age, strength, endurance); break;
                }
            } else {
                startPlace = camp;
                int type = rand.nextInt(4);
                switch (type) {
                    case 0: c = new General("Général" + nameSuffix, "M", height, age, strength, endurance); break;
                    case 1: c = new Legionary("Légionnaire" + nameSuffix, "M", height, age, strength, endurance); break;
                    case 2: c = new Prefect("Préfet" + nameSuffix, "M", height, age, strength, endurance); break;
                    default: c = new Roman("Romain" + nameSuffix, "M", height, age, strength, endurance); break;
                }
            }

            if (c != null) {
                startPlace.addCharacter(c);
                c.setPlace(startPlace);

                // --- CHANGEMENT ICI ---
                // 1. On crée le Runnable
                CharacterThread runnable = new CharacterThread(c);

                // 2. On l'ajoute à notre liste pour le surveiller
                runnables.add(runnable);

                // 3. On crée un Thread pour exécuter le Runnable et on le lance
                Thread thread = new Thread(runnable);
                thread.start();
            }
        }

        // 3. Boucle de surveillance (Reste identique, mais utilise la liste 'runnables')
        long startTime = System.currentTimeMillis();
        long maxDurationMs = durationSeconds * 1000L;
        boolean simulationActive = true;

        while (simulationActive) {
            try {
                Thread.sleep(1000);

                long elapsedTime = System.currentTimeMillis() - startTime;

                if (elapsedTime > maxDurationMs) {
                    System.out.println(">>> FIN: Temps écoulé !");
                    simulationActive = false;
                }

                // Note : countSurvivors prend maintenant une liste de CharacterThread (qui sont des Runnables)
                long gauloisVivant = countSurvivors(runnables, "Gaulois")
                        + countSurvivors(runnables, "Forgeron")
                        + countSurvivors(runnables, "Druide")
                        + countSurvivors(runnables, "Aubergiste")
                        + countSurvivors(runnables, "Marchand");

                long romainsVivant = countSurvivors(runnables, "Romain")
                        + countSurvivors(runnables, "Général")
                        + countSurvivors(runnables, "Légionnaire")
                        + countSurvivors(runnables, "Préfet");

                System.out.println("[INFO] " + (elapsedTime/1000) + "s | Gaulois: " + gauloisVivant + " | Romains: " + romainsVivant);

                if (gauloisVivant == 0 || romainsVivant == 0) {
                    System.out.println(">>> FIN: Une équipe a été éliminée !");
                    simulationActive = false;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 4. Arrêt propre via la méthode stopSimulation() du Runnable
        System.out.println("Arrêt de la simulation...");
        for (CharacterThread runnable : runnables) {
            runnable.stopSimulation(); // Assure-toi que cette méthode existe dans ton Runnable
        }
    }

    private long countSurvivors(List<CharacterThread> runnables, String nameStart) {
        return runnables.stream()
                .map(CharacterThread::getCharacter)
                .filter(c -> c.getHealth() > 0)
                .filter(c -> c.getName().startsWith(nameStart))
                .count();
    }
}