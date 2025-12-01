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






    public static void main(String[] args) throws InterruptedException {
        Druid dibiazah = new Druid("ya'qub qamar ad-din dibiazah", "male", 1.75, 80, 54, 50);
        Merchant kashmiri = new Merchant("khalid kashmiri", "male", 1.70, 25, 40, 30);
        Prefect karawita = new Prefect("khidir karawita", "male", 1.80, 54, 62, 52);
        General kanabawi = new General("ismail ahmad kanabawi", "female", 1.65, 34, 64, 25);
        Innkeeper sisha = new Innkeeper("usman abdul jalil sisha", "male", 1.79, 56, 12, 78);
        Lycanthrope sumbul = new Lycanthrope("muhammad sumbul", "male", 2.0, 46, 80, 70);
        Blacksmith oui = new Blacksmith("ouioui", "male", 2.0, 46, 80, 70);
        Legionary non =  new Legionary("non", "male", 2.0, 46, 80, 70);



        Map<String, Object> map = new HashMap();
        map.put("bobi", dibiazah);
        map.put("kashmiri", kashmiri);
        map.put("sisha", sisha);
        map.put("oui", oui);
        map.put("sumbul", sumbul);
        map.put("non", non);
        map.put("kanabawi", kanabawi);
        map.put("karawita", karawita);
        Serializer serializer = new Serializer();
        serializer.serialize("gaul", map);
        oui.work();


        CharacterThread u = new CharacterThread(dibiazah);
        Thread t1 = new Thread(u);
        u = new CharacterThread(kashmiri);
        Thread t2 = new Thread(u);
        u = new CharacterThread(sisha);
        Thread t3 = new Thread(u);
        u = new CharacterThread(oui);
        Thread t4 = new Thread(u);
        u = new CharacterThread(sumbul);
        Thread t5 = new Thread(u);
        u = new CharacterThread(kanabawi);
        Thread t6 = new Thread(u);
        u = new CharacterThread(non);
        Thread t7 = new Thread(u);
        u = new CharacterThread(karawita);
        Thread t8 = new Thread(u);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();


    }

}
