package fr.iut.laGaule.process;

import fr.iut.laGaule.model.Character.MythicalCreature.AgeCategory;
import fr.iut.laGaule.model.Character.MythicalCreature.Lycanthrope;
import fr.iut.laGaule.model.Character.MythicalCreature.Pack;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import fr.iut.laGaule.process.CharacterThread;
import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.ClanLeader;
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

    private List<Place> places;
    private List<CharacterThread> runnables;
    private List<Thread> activeThreads;
    private volatile boolean simulationActive;
    private long startTime;
    private int durationSeconds;
    private String endMessage = "";

    public InvasionTheatre() {
        this.places = new ArrayList<>();
        this.runnables = new ArrayList<>();
        this.activeThreads = new ArrayList<>();
        this.simulationActive = false;
    }

    public List<Place> getPlaces() { return places; }
    public boolean isSimulationActive() { return simulationActive; }
    public String getEndMessage() { return endMessage; }

    public int getRemainingTime() {
        if (!simulationActive) return 0;
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        return (int) Math.max(0, durationSeconds - elapsed);
    }

    public int getTotalPopulation() {
        return (int) runnables.stream()
                .map(CharacterThread::getCharacter)
                .filter(c -> c.getHealth() > 0)
                .count();
    }

    private void deleteSaveFiles() {
        File currentDir = new File(".");
        File[] files = currentDir.listFiles((dir, name) -> name.endsWith(".ser"));
        if (files != null) for (File f : files) f.delete();
    }

    public void setupSimulation(int nbZones, int nbCharacters) {
        stopSimulation();
        deleteSaveFiles();

        places.clear();
        runnables.clear();
        activeThreads.clear();

        if (nbZones < 3) nbZones = 3; // Sécurité minimum

        Random rand = new Random();

        // --- 1. CRÉATION DES LIEUX ---

        // A. Village Gaulois
        GaulVillage village = new GaulVillage("Village Gaulois", 200, null, 0, new ArrayList<>(), new ArrayList<>());
        village.setClanLeader(new ClanLeader("Abraracourcix", "M", 50, village));
        // Stock nourriture
        village.setFood(Foods.SANGLIER.getRandomFoods(rand.nextInt(20, 50)));
        places.add(village);

        // B. Camp Romain
        RomanFortifiedCamp camp = new RomanFortifiedCamp("Camp Romain", 200, null, 0, new ArrayList<>(), new ArrayList<>());
        camp.setClanLeader(new ClanLeader("César", "M", 50, camp));
        // Stock nourriture
        camp.setFood(Foods.VIN.getRandomFoods(rand.nextInt(20, 50)));
        places.add(camp);

        // C. Champ de Bataille
        places.add(new BattleFields("Champ de Bataille", 1000, null, 0, new ArrayList<>(), new ArrayList<>()));


        Enclosure enclosure = new Enclosure("enclo", 1000, null, 0, new ArrayList<>(), new ArrayList<>());
        enclosure.setFood(Foods.SANGLIER.getRandomFoods(10));
        places.add(enclosure);

        // MEUTE PRINCIPALE
        Pack pack = new Pack("Meute de la Lune");

        // D. Lieux Supplémentaires (C'EST ICI QUE CELA MANQUAIT)
        for (int i = 3; i < nbZones; i++) {
            Place p = null;
            String n = "Zone " + (i - 2);
            int type = rand.nextInt(5);

            switch(type) {
                case 0: p = new BattleFields("Maquis " + n, 500, null, 0, new ArrayList<>(), new ArrayList<>()); break;
                case 1: p = new GalloRomanVillage("Bourgade " + n, 150, null, 0, new ArrayList<>(), new ArrayList<>()); break;
                case 2: p = new GaulVillage("Hameau " + n, 100, null, 0, new ArrayList<>(), new ArrayList<>()); break;
                case 3: p = new RomanCity("Civitas " + n, 300, null, 0, new ArrayList<>(), new ArrayList<>()); break;
                case 4: p = new RomanFortifiedCamp("Fort " + n, 150, null, 0, new ArrayList<>(), new ArrayList<>()); break;
            }

            if (p != null) {
                // On ajoute un peu de nourriture aléatoire aussi dans les zones sauvages
                p.setFood(Foods.INGREDIENT_SECRET.getRandomFoods(rand.nextInt(5, 15)));
                places.add(p);
            }
        }

        // --- 2. CRÉATION DES PERSONNAGES ---
        System.out.println(">>> DÉBUT GÉNÉRATION : " + nbCharacters + " demandés.");

        for (int i = 0; i < nbCharacters; i++) {
            Character c;
            Place startPlace;
            String suffix = " " + (i + 1);

            if (rand.nextBoolean()) {
                startPlace = village;
                int type = rand.nextInt(5);
                if (type == 0) c = new Druid("Druide" + suffix, "M", 1.8, 60, 10, 10);
                else if (type == 1) c = new Blacksmith("Forgeron" + suffix, "M", 1.9, 40, 20, 15);
                else if (type == 2) c = new Merchant("Marchand" + suffix, "M", 1.6, 35, 10, 10);
                else if (type == 3) c = new Innkeeper("Aubergiste" + suffix, "M", 1.7, 45, 15, 15);
                else c = new Gaul("Gaulois" + suffix, "M", 1.75, 25, 12, 12);
            } else if (rand.nextBoolean()) {
                startPlace = camp;
                int type = rand.nextInt(4);
                if (type == 0) c = new General("Général" + suffix, "M", 1.8, 50, 15, 15);
                else if (type == 1) c = new Prefect("Préfet" + suffix, "M", 1.7, 45, 10, 10);
                else if (type == 2) c = new Legionary("Légionnaire" + suffix, "M", 1.8, 25, 15, 15);
                else c = new Roman("Romain" + suffix, "M", 1.75, 20, 12, 12);
            }else{
                // LYCANTHROPE
                startPlace = enclosure;
                boolean originGaul = rand.nextBoolean();

                // Création
                c = new Lycanthrope(
                        "Loup" + suffix, "M", 1.9, 30, 25, 20,
                        AgeCategory.ADULT, 0, null, 20, originGaul
                );

                // Ajout à la meute
                ((Lycanthrope) c).joinPack(pack);
            }

            // Force la santé au max
            if (c.getHealth() <= 0) c.heal(100);

            // Ajout au lieu
            startPlace.addCharacter(c);
            if (!startPlace.getCharacter().contains(c)) {
                startPlace.getCharacter().add(c); // Force add si bug
            }

            c.setPlace(startPlace);
            c.setLastAction("Vient d'arriver");

            CharacterThread runnable = new CharacterThread(c);
            runnables.add(runnable);
            activeThreads.add(new Thread(runnable));
        }
        System.out.println(">>> FIN GÉNÉRATION : " + runnables.size() + " créés.");
    }

    public void startSimulationInBackground(int durationSeconds) {
        if (activeThreads.isEmpty()) return;
        this.durationSeconds = durationSeconds;
        this.simulationActive = true;
        this.endMessage = "";

        for (Thread t : activeThreads) t.start();

        new Thread(() -> {
            this.startTime = System.currentTimeMillis();
            long maxDurationMs = durationSeconds * 1000L;

            while (simulationActive) {
                try {
                    Thread.sleep(1000);

                    if (System.currentTimeMillis() - startTime > maxDurationMs) {
                        endMessage = "TEMPS ÉCOULÉ - ÉGALITÉ";
                        simulationActive = false;
                    }

                    long nbGaul = countSurvivorsByType(Gaul.class);
                    long nbRom = countSurvivorsByType(Roman.class);

                    if (nbGaul == 0 && nbRom > 0) { endMessage = "VICTOIRE ROMAINE !"; simulationActive = false; }
                    else if (nbRom == 0 && nbGaul > 0) { endMessage = "VICTOIRE GAULOISE !"; simulationActive = false; }
                    else if (nbRom == 0 && nbGaul == 0) { endMessage = "DESTRUCTION TOTALE"; simulationActive = false; }

                } catch (InterruptedException e) { e.printStackTrace(); }
            }
            stopSimulation();
        }).start();
    }

    public void stopSimulation() {
        System.out.println("Arrêt de la simulation...");
        simulationActive = false;
        for (CharacterThread r : runnables) r.stopSimulation();
        for (Thread t : activeThreads) if (t.isAlive()) t.interrupt();
    }

    private long countSurvivorsByType(Class<?> type) {
        return runnables.stream()
                .map(CharacterThread::getCharacter)
                .filter(c -> c.getHealth() > 0)
                .filter(type::isInstance)
                .count();
    }
}