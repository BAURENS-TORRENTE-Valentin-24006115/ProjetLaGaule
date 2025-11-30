package fr.iut.laGaule;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.Gaul.*;
import fr.iut.laGaule.model.Character.MythicalCreature.Lycanthrope;
import fr.iut.laGaule.model.Character.Roman.General;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Character.Roman.Prefect;
import fr.iut.laGaule.model.Character.Roman.Roman;
import fr.iut.laGaule.model.Consumables.Foods.Foods;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {


    public void testSimulation(Character character) throws InterruptedException {
        Serializer serializer = new Serializer();
        Random random = new Random();

        if(character instanceof Druid){
            int randomInt = random.nextInt(5);
            if(randomInt == 0){
                ((Druid) character).concoctPotion();
            }
            if(randomInt == 1){
                Character oui = serializer.deserializeRandomCharacter(character.getPlace());
                while(!(oui instanceof Gaul)){
                    oui = serializer.deserializeRandomCharacter(character.getPlace());
                    System.out.println(oui.getName());
                }
                ((Druid) character).command((Gaul) oui);
            }
            if(randomInt == 2){
                Character oui = serializer.deserializeRandomCharacter(character.getPlace());
                while(!(oui instanceof Roman)){
                    oui = serializer.deserializeRandomCharacter(character.getPlace());
                    System.out.println(oui.getName());
                }
                ((Druid) character).fight((Roman) oui);
            }if(randomInt == 3){
                ((Druid) character).work();
            }
            System.out.println(character.getName()+"a travail: "+randomInt);
        }

    }



    public static void main(String[] args) throws InterruptedException {
        Druid dibiazah = new Druid("ya'qub qamar ad-din dibiazah", "male", 1.75, 80, 54, 50);
        Merchant kashmiri = new Merchant("khalid kashmiri", "male", 1.70, 25, 40, 30);
        Prefect karawita = new Prefect("khidir karawita", "male", 1.80, 54, 62, 52);
        General kanabawi = new General("ismail ahmad kanabawi", "female", 1.65, 34, 64, 25);
        Innkeeper sisha = new Innkeeper("usman abdul jalil sisha", "male", 1.79, 56, 12, 78);
        Lycanthrope sumbul = new Lycanthrope("muhammad sumbul", "male", 2.0, 46, 80, 70);
        Blacksmith oui = new Blacksmith("ouioui", "male", 2.0, 46, 80, 70);
        Legionary non =  new Legionary("non", "male", 2.0, 46, 80, 70);

        dibiazah.concoctPotion();

        kashmiri.drinkPotion(50);

        System.out.println(karawita.getHealth());
        karawita.receiveDamage(20);
        System.out.println(karawita.getHealth());
        karawita.heal(35);
        System.out.println(karawita.getHealth());

        sisha.work();

        kanabawi.command(non, sisha);
        dibiazah.eat(Foods.SANGLIER);
        dibiazah.fight(karawita);
        dibiazah.fight(karawita);
        dibiazah.fight(karawita);
        dibiazah.fight(karawita);
        dibiazah.fight(karawita);
        dibiazah.fight(karawita);
        dibiazah.fight(karawita);
        kanabawi.fight(dibiazah);

        Map<String, Object> map = new HashMap();
        map.put("bobi", dibiazah);
        map.put("kashmiri", kashmiri);
        map.put("sisha", sisha);
        map.put("oui", oui);
        map.put("sumbul", sumbul);
        map.put("non", non);
        map.put("kanabawi", kanabawi);
        Serializer serializer = new Serializer();
        serializer.serialize("gaul", map);
        oui.work();

        Main main = new Main();
        main.testSimulation(dibiazah);

        CharacterThread u = new CharacterThread(dibiazah);
        Thread t1 = new Thread(u);
        t1.start();


    }

}
