package fr.iut.laGaule.process;

import fr.iut.laGaule.Serializer;
import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.Gaul.*;
import fr.iut.laGaule.model.Character.MythicalCreature.Lycanthrope;
import fr.iut.laGaule.model.Character.Roman.General;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Character.Roman.Prefect;
import fr.iut.laGaule.model.Character.Roman.Roman;
import fr.iut.laGaule.model.Consumables.Foods.Foods;

import java.util.Random;

public class CharacterThread implements Runnable {
    public Character character;
    private volatile boolean running = true;

    public CharacterThread(Character character){
        this.character = character;
    }

    @Override
    public void run() {
        Serializer serializer = new Serializer();
        Random random = new Random();

        // Première action par défaut
        character.setLastAction("Vient d'arriver dans " + character.getPlace());

        while (running && character.getHealth() > 0) {
            try {
                // Vérification de sécurité
                if(character.getHealth() <= 0){
                    character.setLastAction("Est tombé au combat");
                    stopSimulation();
                    break;
                }

                // --- DRUIDE ---
                if (character instanceof Druid) {
                    Druid druid = (Druid) character;
                    int randomInt = random.nextInt(5);

                    if (randomInt == 0) {
                        druid.concoctPotion();
                        character.setLastAction("Prépare de la potion 🧪");
                        if (randomInt == 3) {
                            druid.work();
                            // AJOUT ICI : Le druide cueille des plantes
                            produceFood(true);
                        }
                        else {
                            character.setLastAction("Médite dans la forêt 🧘");
                        }
                    }
                    else {
                        if (randomInt == 1) {
                            // Cherche un Romain (Ennemi)
                            Character enemy = findEnemy(Roman.class); // <-- UTILISATION ICI
                            if (enemy != null) {
                                druid.fight((Roman) enemy);
                                character.setLastAction("Attaque " + enemy.getName() + " ⚔️");
                            } else {
                                character.setLastAction("Cherche un romain...");
                            }
                        }
                        else if (randomInt == 2) {
                            var randomRoman = serializer.deserializeRandomRoman(character.getPlace());
                            if (randomRoman != null) {
                                druid.fight(randomRoman);
                                character.setLastAction("Se bat contre " + randomRoman.getName() + " ⚔️");
                            } else {
                                character.setLastAction("Cherche un romain à taper...");
                            }
                        }
                        if (randomInt == 3) {
                            druid.work();
                            // AJOUT ICI : Le druide cueille des plantes
                            produceFood(true);
                        }
                        else {
                            character.setLastAction("Médite dans la forêt 🧘");
                        }
                    }
                }

                // --- FORGERON (Blacksmith) ---
                else if (character instanceof Blacksmith) {
                    Blacksmith smith = (Blacksmith) character;
                    int randomInt = random.nextInt(2); // Augmenté pour avoir du "repos"

                    if (randomInt == 0) {
                        smith.work();
                        character.setLastAction("Forge des armes 🔨");
                    } else {
                        character.setLastAction("Entretiens le feu de la forge 🔥");
                    }
                }

                // --- AUBERGISTE (Innkeeper) ---
                else if (character instanceof Innkeeper) {
                    Innkeeper inn = (Innkeeper) character;
                    int randomInt = random.nextInt(2);

                    if (randomInt == 0) {
                        inn.work();
                        character.setLastAction("Sert de la cervoise tiède 🍺");
                    } else {
                        character.setLastAction("Nettoie le comptoir 🧹");
                    }
                }

                // --- MARCHAND (Merchant) ---
                else if (character instanceof Merchant) {
                    Merchant merch = (Merchant) character;
                    int randomInt = random.nextInt(2);

                    if (randomInt == 0) {
                        merch.work();
                        // AJOUT ICI : Le marchand ramène des vivres
                        produceFood(false);
                    } else {
                        character.setLastAction("Compte ses sesterces 💰");
                    }
                }

                // --- GENERAL ---
                else if (character instanceof General) {
                    General gen = (General) character;
                    int randomInt = random.nextInt(3);

                    if (randomInt == 1) { // Combat
                        Character enemy = findEnemy(Gaul.class); // <-- UTILISATION ICI
                        if (enemy != null) {
                            gen.fight((Gaul) enemy);
                            character.setLastAction("Duel contre " + enemy.getName() + " ⚔️");
                        } else {
                            character.setLastAction("Cherche un adversaire...");
                        }
                    }
                    else if (randomInt == 1) {
                        var randomGaul = serializer.deserializeRandomGaul(character.getPlace());
                        if (randomGaul != null) {
                            gen.fight(randomGaul);
                            character.setLastAction("Duel contre " + randomGaul.getName() + " ⚔️");
                        } else {
                            character.setLastAction("Cherche un adversaire digne...");
                        }
                    } else {
                        character.setLastAction("Rédige ses mémoires de guerre 📜");
                    }
                }

                // --- LEGIONNAIRE ---
                else if (character instanceof Legionary) {
                    Legionary leg = (Legionary) character;
                    int randomInt = random.nextInt(3);

                    if (randomInt == 0) {
                        // Cherche un Gaulois (Ennemi)
                        Character enemy = findEnemy(Gaul.class); // <-- UTILISATION ICI

                        if (enemy != null) {
                            leg.fight((Gaul) enemy); // Assurez-vous que fight prend un Gaul
                            character.setLastAction("Combat " + enemy.getName() + " ⚔️");
                        } else {
                            character.setLastAction("Cherche des barbares...");
                        }

                } else if (randomInt == 1) {
                        character.setLastAction("Monte la garde 🛡️");
                    } else {
                        character.setLastAction("Astique son pilum ✨");
                    }
                }

                // --- PREFET ---
                else if (character instanceof Prefect) {
                    Prefect pref = (Prefect) character;
                    int randomInt = random.nextInt(2);

                    if (randomInt == 0) {
                        var randomLegionary = serializer.deserializeRandomLegionary(character.getPlace());
                        var randomGaul = serializer.deserializeRandomGaul(character.getPlace());

                        if (randomLegionary != null && randomGaul != null) {
                            pref.command(randomLegionary, randomGaul);
                            character.setLastAction("Dirige " + randomLegionary.getName() + " vers le combat 👉");
                        } else {
                            character.setLastAction("Cherche des troupes à diriger...");
                        }
                    } else {
                        character.setLastAction("Collecte les impôts 🪙");
                    }
                } else if (character instanceof Lycanthrope) {
                    Lycanthrope wolf = (Lycanthrope) character;
                    int dice = random.nextInt(100);

                    // 1. TRANSFORMATION (5%)
                    if (dice < 5) {
                        Character newHuman = wolf.transformToHuman();
                        if (newHuman != null) {
                            fr.iut.laGaule.model.Place.Place currentPlace = (fr.iut.laGaule.model.Place.Place) wolf.getPlaceData();
                            synchronized (currentPlace) {
                                currentPlace.getCharacter().remove(wolf);
                                currentPlace.getCharacter().add(newHuman);
                            }
                            newHuman.setPlace(currentPlace);
                            newHuman.setLastAction("METAMORPHOSIS ! ✨");
                            this.character = newHuman; // LE THREAD CHANGE DE CIBLE
                            continue;
                        }
                    }

                    // 2. DOMINATION (40%)
                    else if (dice < 45) {
                        // On cherche un voisin manuellement car findEnemy cherche des ennemis
                        Lycanthrope target = null;
                        try {
                            for(Character n : ((fr.iut.laGaule.model.Place.Place)wolf.getPlaceData()).getCharacter()) {
                                if(n instanceof Lycanthrope && n != wolf) { target = (Lycanthrope)n; break; }
                            }
                        } catch(Exception e){}

                        if (target != null) {
                            if (wolf.attemptDomination(target)) character.setLastAction("A dominé " + target.getName() + " 💪");
                            else character.setLastAction("Echec domination sur " + target.getName());
                        } else {
                            character.setLastAction("Cherche sa place dans la meute");
                        }
                    }

                    // 3. HURLEMENTS & REPOS
                    else {
                        if (random.nextBoolean()) {
                            wolf.howlPackAffiliation();
                            character.setLastAction("Hurle avec la meute 🐺");
                        } else {
                            character.setLastAction("Dort dans la tanière 💤");
                        }
                    }
                }

                // --- CAS DEFAUT (GAULOIS DE BASE / ROMAIN DE BASE) ---
                else {
                    int action = random.nextInt(3);
                    if (action == 0) {
                        character.setLastAction("Se promène dans " + character.getPlace());
                    } else if (action == 1) {
                        // Ils peuvent se battre s'ils trouvent un ennemi
                        // (Utilisez findEnemy si vous l'avez implémenté, sinon rien)
                        character.setLastAction("Discute avec les voisins 💬");
                    } else {
                        character.setLastAction("Se repose 💤");
                        // Récupère un peu d'endurance
                        character.setEndurance(character.getEndurance() + 1);
                    }
                }

                // Pause aléatoire entre 2 et 5 secondes pour varier le rythme
                Thread.sleep(random.nextInt(2000, 5000));

            } catch (InterruptedException e) {
                character.setLastAction("Interrompu (Fin de simulation)");
                running = false;
                break;
            } catch (Exception e) {
                // Sécurité pour éviter que le thread ne crash totalement sur une erreur de Serializer
                System.err.println("Erreur thread " + character.getName() + ": " + e.getMessage());
                character.setLastAction("Erreur interne...");
            }
        }

        System.out.println("Fin du thread pour " + character.getName());
    }

    public void stopSimulation() {
        this.running = false;
    }

    public Character getCharacter() {
        return character;
    }
    private Character findEnemy(Class<?> enemyClass) {
        // 1. Récupérer le lieu actuel (Objet en mémoire)
        // (Supposons que character.getPlace() renvoie l'objet Place, sinon il faut l'attribut Place dans Character)
        if (this.character.getPlaceData() == null || !(this.character.getPlaceData() instanceof fr.iut.laGaule.model.Place.Place)) {
            return null;
        }

        fr.iut.laGaule.model.Place.Place currentPlace = (fr.iut.laGaule.model.Place.Place) this.character.getPlaceData();

        // 2. Faire une copie de la liste pour éviter les bugs si quelqu'un bouge en même temps
        java.util.List<Character> neighbors;
        try {
            neighbors = new java.util.ArrayList<>(currentPlace.getCharacter());
        } catch (Exception e) {
            return null; // Erreur de lecture concurrente, on réessaiera plus tard
        }

        // 3. Filtrer les ennemis vivants
        java.util.List<Character> targets = new java.util.ArrayList<>();
        for (Character c : neighbors) {
            // Est-ce le bon type ? Est-il vivant ? Est-ce que ce n'est pas moi-même ?
            if (enemyClass.isInstance(c) && c.getHealth() > 0 && c != this.character) {
                targets.add(c);
            }
        }

        if (targets.isEmpty()) return null;

        // 4. En choisir un au hasard
        return targets.get(new Random().nextInt(targets.size()));
    }
    // Méthode utilitaire pour générer de la nourriture selon le métier
    private void produceFood(boolean isPlant) {
        if (character.getPlaceData() == null || !(character.getPlaceData() instanceof fr.iut.laGaule.model.Place.Place)) return;

        fr.iut.laGaule.model.Place.Place currentPlace = (fr.iut.laGaule.model.Place.Place) character.getPlaceData();

        // Sélection d'un aliment
        Foods item;
        Random r = new Random();

        if (isPlant) {
            // DRUIDE : Plantes et herbes
            Foods[] plants = {Foods.GUI, Foods.TREFLE_QUATRE_FEUILLES_FRAIS, Foods.FRAISES, Foods.CAROTTE, Foods.INGREDIENT_SECRET};
            item = plants[r.nextInt(plants.length)];
        } else {
            // MARCHAND : Viande, Boisson, Poisson
            Foods[] market = {Foods.SANGLIER, Foods.POISSON_FRAIS, Foods.VIN, Foods.MIEL, Foods.HOMARD, Foods.SANGLIER};
            item = market[r.nextInt(market.length)];
        }

        // Ajout au lieu (Synchronisé pour éviter les bugs)
        synchronized (currentPlace) {
            currentPlace.getFood().add(item);
        }

        character.setLastAction("A produit : " + item.getName() + " 🍎");
    }


}