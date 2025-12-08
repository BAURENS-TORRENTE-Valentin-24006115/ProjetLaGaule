package fr.iut.laGaule;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Gaul.*;

import fr.iut.laGaule.model.Character.Roman.*;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import fr.iut.laGaule.model.Place.*;
import fr.iut.laGaule.model.InvasionTheater;
import fr.iut.laGaule.model.Character.MythicalCreature.Lycanthrope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Bienvenue en Armorique !\n");

        // Créer le théâtre
        InvasionTheater armorique = new InvasionTheater("Armorique", 10);

        // Créer des Gaulois
        Druid panoramix = new Druid("Panoramix", "M", 1.75, 60, 5, 8);
        Merchant unhygienix = new Merchant("Unhygiénix", "M", 1.70, 45, 6, 7);
        Blacksmith cetautomatix = new Blacksmith("Cétautomatix", "M", 1.80, 40, 9, 10);

        // Créer des Romains
        Legionary brutus = new Legionary("Brutus", "M", 1.75, 30, 8, 7);
        Legionary cesar = new Legionary("César", "M", 1.78, 28, 7, 6);
        General pompey = new General("Pompey", "M", 1.82, 45, 9, 9);

        // Listes de personnages
        ArrayList<Character> gaulois = new ArrayList<>();
        gaulois.add(panoramix);
        gaulois.add(unhygienix);
        gaulois.add(cetautomatix);

        ArrayList<Character> romains = new ArrayList<>();
        romains.add(brutus);
        romains.add(cesar);
        romains.add(pompey);

        // Listes d'aliments
        ArrayList<Foods> alimentsVillage = new ArrayList<>();
        alimentsVillage.add(Foods.SANGLIER);
        alimentsVillage.add(Foods.POISSON_FRAIS);
        alimentsVillage.add(Foods.VIN);

        ArrayList<Foods> alimentsCamp = new ArrayList<>();
        alimentsCamp.add(Foods.MIEL);
        alimentsCamp.add(Foods.HYDROMEL);

        // Créer les lieux
        GaulVillage village = new GaulVillage("Village des Irréductibles", 5000, null, 0, gaulois, alimentsVillage);
        RomanFortifiedCamp camp = new RomanFortifiedCamp("Camp de Petitbonum", 8000, null, 0, romains, alimentsCamp);
        BattleFields champBataille = new BattleFields("Plaine de la Discorde", 10000, null, 0, new ArrayList<>(), new ArrayList<>());

        // Ajouter les lieux au théâtre
        armorique.ajouterLieu(village);
        armorique.ajouterLieu(camp);
        armorique.ajouterLieu(champBataille);

        // Afficher l'état initial
        armorique.afficherLieux();
        armorique.afficherTousLesPersonnages();

        // Lancer la simulation
        armorique.lancerSimulation(5);

        // Afficher l'état final
        System.out.println("\nÉtat final :");
        armorique.afficherTousLesPersonnages();

        System.out.println("\nFin de la partie !");

  
  
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
        t6.start();
        ClanLeader leader = new ClanLeader("name","sex",12);
        System.out.println(dibiazah.getPlace());
        System.out.println(champBataille.getName());
        leader.transferCharacter(dibiazah,village);
        System.out.println(champBataille.getName());
        TimeUnit.SECONDS.sleep(10);
        leader.transferCharacter(kanabawi,champBataille);
        System.out.println(kanabawi.getPlace());
        leader.transferCharacter(kanabawi,village);
        System.out.println(kanabawi.getPlace());
        System.out.println(serializer.deserialize(champBataille.getName()));

    }

}

