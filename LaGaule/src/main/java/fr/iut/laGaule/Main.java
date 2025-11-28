package fr.iut.laGaule;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.Gaul.*;
import fr.iut.laGaule.model.Character.Roman.*;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import fr.iut.laGaule.model.Place.*;
import fr.iut.laGaule.model.InvasionTheater;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
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
    }
}