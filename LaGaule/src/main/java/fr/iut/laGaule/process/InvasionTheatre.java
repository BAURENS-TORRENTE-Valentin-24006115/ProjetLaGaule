package fr.iut.laGaule.process;

import fr.iut.laGaule.process.CharacterThread;
import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.ClanLeader; // Import du Chef
import fr.iut.laGaule.model.Character.Gaul.*;
import fr.iut.laGaule.model.Character.Roman.*;
import fr.iut.laGaule.model.Place.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main class managing the invasion simulation.
 * It configures the locations, the characters, and controls the progress of the simulation.
 */
public class InvasionTheatre {

    // Attributs de classe pour stocker l'état du jeu (accessibles par le GUI)
    private List<Place> places;
    private List<CharacterThread> runnables;
    private List<Thread> activeThreads;
    private volatile boolean simulationActive; // volatile car modifié par un thread et lu par un autre

    public InvasionTheatre() {
        this.places = new ArrayList<>();
        this.runnables = new ArrayList<>();
        this.activeThreads = new ArrayList<>();
        this.simulationActive = false;
    }

    // --- Getters pour l'interface graphique ---
    public List<Place> getPlaces() { return places; }
    public boolean isSimulationActive() { return simulationActive; }
    // ------------------------------------------

    /**
     * Delete last saves
     */
    private void deleteSaveFiles() {
        System.out.println("--- Nettoyage des anciennes sauvegardes (.ser) ---");
        File currentDir = new File(".");
        File[] files = currentDir.listFiles((dir, name) -> name.endsWith(".ser"));
        if (files != null) {
            for (File file : files) file.delete();
        }
    }

    /**
     * STEP 1: Setting up the simulation (Creating locations and characters).
     * This method does NOT start time yet.
     */
    public void setupSimulation(int nbZones, int nbCharacters) {
        deleteSaveFiles();

        // Réinitialisation des listes
        places.clear();
        runnables.clear();
        activeThreads.clear();

        if (nbZones < 3) {
            System.err.println("Erreur: Il faut au moins 3 zones.");
            return;
        }

        System.out.println("=== PRÉPARATION DU THÉÂTRE D'ENVAHISSEMENT ===");
        Random rand = new Random();

        // --- A. Création des 3 Lieux Obligatoires et des Chefs Principaux ---

        // 1. Village Gaulois + Chef
        GaulVillage village = new GaulVillage("Village des Irréductibles", 200, null, 0, new ArrayList<>(), new ArrayList<>());
        ClanLeader chefGaulois = new ClanLeader("Abraracourcix", "M", 50, village);
        village.setClanLeader(chefGaulois);
        places.add(village);

        // 2. Camp Romain + Chef
        RomanFortifiedCamp camp = new RomanFortifiedCamp("Camp de Babaorum", 200, null, 0, new ArrayList<>(), new ArrayList<>());
        ClanLeader chefRomain = new ClanLeader("Jules César", "M", 55, camp);
        camp.setClanLeader(chefRomain);
        places.add(camp);

        // 3. Champ de Bataille (Pas de chef)
        BattleFields battlefield = new BattleFields("Grande Plaine", 1000, null, 0, new ArrayList<>(), new ArrayList<>());
        places.add(battlefield);

        // --- B. Création des Lieux Supplémentaires ---
        for (int i = 3; i < nbZones; i++) {
            Place p = null;
            String nom = "Zone " + (i - 2);
            int typeLieu = rand.nextInt(5);

            switch (typeLieu) {
                case 0:
                    p = new BattleFields("Maquis de " + nom, 500, null, 0, new ArrayList<>(), new ArrayList<>());
                    break;
                case 1:
                    p = new GalloRomanVillage("Bourgade " + nom, 150, null, 0, new ArrayList<>(), new ArrayList<>());
                    break;
                case 2:
                    p = new GaulVillage("Hameau " + nom, 100, null, 0, new ArrayList<>(), new ArrayList<>());
                    // Ajout d'un chef pour ce nouveau village
                    p.setClanLeader(new ClanLeader("Chef Hameau " + i, "M", 40, p));
                    break;
                case 3:
                    p = new RomanCity("Civitas " + nom, 300, null, 0, new ArrayList<>(), new ArrayList<>());
                    break;
                case 4:
                    p = new RomanFortifiedCamp("Fort " + nom, 150, null, 0, new ArrayList<>(), new ArrayList<>());
                    // Ajout d'un chef pour ce nouveau camp
                    p.setClanLeader(new ClanLeader("Centurion " + i, "M", 35, p));
                    break;
            }

            if (p != null) {
                places.add(p);
                System.out.println("Lieu créé : " + p.getName() + (p.getClanLeader() != null ? " (Chef: " + p.getClanLeader().getName() + ")" : ""));
            }
        }

        // --- C. Création des Personnages ---
        System.out.println("Création de " + nbCharacters + " personnages...");
        for (int i = 0; i < nbCharacters; i++) {
            Character c = null;
            Place startPlace;

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

                // IMPORTANT : On passe 'places' au thread pour qu'il puisse se déplacer
                CharacterThread runnable = new CharacterThread(c);
                runnables.add(runnable);

                Thread t = new Thread(runnable);
                activeThreads.add(t);
            }
        }
    }

    /**
     * STEP 2 : Simulation launched in the background.
     */
    public void startSimulationInBackground(int durationSeconds) {
        if (activeThreads.isEmpty()) {
            System.out.println("Aucune simulation configurée. Lancez setupSimulation d'abord.");
            return;
        }

        simulationActive = true;

        // 1. Démarrer les threads de personnages
        for (Thread t : activeThreads) {
            t.start();
        }

        // 2. Lancer le thread de supervision (Timer & Conditions de victoire)
        // On utilise un Thread à part pour ne pas bloquer l'interface graphique
        new Thread(() -> {
            System.out.println("--- Simulation lancée pour " + durationSeconds + " secondes ---");
            long startTime = System.currentTimeMillis();
            long maxDurationMs = durationSeconds * 1000L;

            while (simulationActive) {
                try {
                    Thread.sleep(1000); // Pause 1 seconde

                    // Vérification Temps
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    if (elapsedTime > maxDurationMs) {
                        System.out.println(">>> FIN: Temps écoulé !");
                        simulationActive = false;
                    }

                    // Vérification Survivants
                    long gauloisVivant = countSurvivors("Gaulois")
                            + countSurvivors("Forgeron") + countSurvivors("Druide")
                            + countSurvivors("Aubergiste") + countSurvivors("Marchand");

                    long romainsVivant = countSurvivors("Romain")
                            + countSurvivors("Général") + countSurvivors("Légionnaire")
                            + countSurvivors("Préfet");

                    // Logs console (optionnel, car le GUI affichera les infos)
                    // System.out.println("[MOTEUR] G:" + gauloisVivant + " vs R:" + romainsVivant);

                    if (gauloisVivant == 0 || romainsVivant == 0) {
                        System.out.println(">>> FIN: Victoire par élimination !");
                        simulationActive = false;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Une fois la boucle finie, on arrête tout proprement
            stopSimulation();

        }).start();
    }

    /**
     * Manual or automatic shutdown of the simulation.
     */
    public void stopSimulation() {
        System.out.println("Arrêt de la simulation...");
        simulationActive = false;

        // Arrêt logique des runnables
        for (CharacterThread runnable : runnables) {
            runnable.stopSimulation();
        }

        // Interruption physique des threads
        for (Thread t : activeThreads) {
            if (t.isAlive()) {
                t.interrupt();
            }
        }
    }

    private long countSurvivors(String nameStart) {
        return runnables.stream()
                .map(CharacterThread::getCharacter)
                .filter(c -> c.getHealth() > 0)
                .filter(c -> c.getName().startsWith(nameStart))
                .count();
    }
}