package fr.iut.laGaule.process;

import fr.iut.laGaule.Serializer;
import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.Gaul.*;
import fr.iut.laGaule.model.Character.Roman.General;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Character.Roman.Prefect;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * A thread that simulates the actions of a character in the game.
 * Each character performs random actions based on their type until they are no longer active.
 */

public class CharacterThread implements Runnable{
    public Character character;
    private volatile boolean running = true;

    public CharacterThread(Character character){
        this.character = character;
    }

    @Override
    public void run() {
        Serializer serializer = new Serializer();
        Random random = new Random();


        while (running && character.getHealth() > 0) {
            try {

                if(character.getHealth()<=0){
                    stopSimulation();
                }
                // --- DRUID ---
                if (character instanceof Druid) {
                    int randomInt = random.nextInt(5);
                    if (randomInt == 0) {
                        ((Druid) character).concoctPotion();
                    }
                    if (randomInt == 1) {
                        var randomGaul = serializer.deserializeRandomGaul(character.getPlace());
                        if (randomGaul != null) {
                            ((Druid) character).command(randomGaul);
                        } else {
                            System.out.println("Aucun Gaulois trouvé pour commander.");
                        }
                    }
                    if (randomInt == 2) {
                        var randomRoman = serializer.deserializeRandomRoman(character.getPlace());
                        if (randomRoman != null) {
                            ((Druid) character).fight(randomRoman);
                        }
                    }
                    if (randomInt == 3) {
                        ((Druid) character).work();
                    }
                    System.out.println(character.getName() + " a travail: " + randomInt);
                }

                // --- Blacksmith ---
                if (character instanceof Blacksmith) {
                    int randomInt = random.nextInt(2);
                    if (randomInt == 0) {
                        ((Blacksmith) character).work();
                    }
                    System.out.println(character.getName() + " a travail: " + randomInt);
                }

                // --- Innkeeper ---
                if (character instanceof Innkeeper) {
                    int randomInt = random.nextInt(2);
                    if (randomInt == 0) {
                        ((Innkeeper) character).work();
                    }
                    System.out.println(character.getName() + " a travail: " + randomInt);
                }

                // --- Merchant ---
                if (character instanceof Merchant) {
                    int randomInt = random.nextInt(2);
                    if (randomInt == 0) {
                        ((Merchant) character).work();
                    }
                    System.out.println(character.getName() + " a travail: " + randomInt);
                }

                // --- General ---
                if (character instanceof General) {
                    int randomInt = random.nextInt(3);
                    if (randomInt == 0) {
                        // Ici il faut vérifier les deux paramètres
                        var randomLegionary = serializer.deserializeRandomLegionary(character.getPlace());
                        var randomGaul = serializer.deserializeRandomGaul(character.getPlace());

                        if (randomLegionary != null && randomGaul != null) {
                            ((General) character).command(randomLegionary, randomGaul);
                        }
                    }
                    if (randomInt == 1) {
                        var randomGaul = serializer.deserializeRandomGaul(character.getPlace());
                        if (randomGaul != null) {
                            ((General) character).fight(randomGaul);
                        }
                    }
                    System.out.println(character.getName() + " a travail: " + randomInt);
                }

                // --- Legionary ---
                if (character instanceof Legionary) {
                    int randomInt = random.nextInt(2);
                    if (randomInt == 0) {
                        var randomGaul = serializer.deserializeRandomGaul(character.getPlace());
                        if (randomGaul != null) {
                            ((Legionary) character).fight(randomGaul);
                        }
                    }
                    System.out.println(character.getName() + " a travail: " + randomInt);
                }

                // --- Prefect ---
                if (character instanceof Prefect) {
                    int randomInt = random.nextInt(2);
                    if (randomInt == 0) {
                        // Vérification des deux paramètres
                        var randomLegionary = serializer.deserializeRandomLegionary(character.getPlace());
                        var randomGaul = serializer.deserializeRandomGaul(character.getPlace());

                        if (randomLegionary != null && randomGaul != null) {
                            ((Prefect) character).command(randomLegionary, randomGaul);
                        }
                    }
                    System.out.println(character.getName() + " a travail: " + randomInt);

            }
                Thread.sleep(random.nextInt(5000,10000));
            }catch (InterruptedException e) {
                running = false;
                break;
            }


        }
    }

    public void stopSimulation() {
        this.running = false;
    }

    public Character getCharacter() {
        return character;
    }
}
