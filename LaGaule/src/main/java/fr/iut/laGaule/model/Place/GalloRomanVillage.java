package fr.iut.laGaule.model.Place;

import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.Gaul.Gaul;
import fr.iut.laGaule.model.Character.Roman.Roman;
import fr.iut.laGaule.model.Consumables.Foods.Foods;

import java.util.ArrayList;

/**
 * Represents a Gallo-Roman village in the game.
 * This place type is a mixed settlement where both Gauls and Romans can coexist peacefully.
 * Only Gaul and Roman characters are allowed to enter this village.
 *
 * @see Place
 */
public class GalloRomanVillage extends Place {

    /**
     * Constructs a new Gallo-Roman Village with the specified attributes.
     * Only allowed characters (Gauls and Romans) from the provided list are added.
     *
     * @param name the name of the village
     * @param area the area/size of the village in square meters
     * @param clanLeader the leader managing this village
     * @param nbCharacter the initial number of characters
     * @param character the list of characters to add (only Gauls and Romans will be added)
     * @param food the list of food items available in the village
     */
    public GalloRomanVillage(String name, int area, ClanLeader clanLeader, int nbCharacter, ArrayList<Character> character, ArrayList<Foods> food) {
        super(name, area, clanLeader, nbCharacter, new ArrayList<>(), food);

        // Ajouter uniquement les personnages autorisés
        if (character != null) {
            for (Character c : character) {
                if (isAllowedCharacter(c)) {
                    this.getCharacter().add(c);
                } else {
                    System.out.println("Attention: " + c.getName() + " n'est pas autorisé dans le village gaulois et n'a pas été ajouté.");
                }
            }
        }
    }

    /**
     * Checks if a character is allowed to enter the Gallo-Roman village.
     * Only Gauls and Romans are permitted.
     *
     * @param character the character to verify
     * @return true if the character is a Gaul or Roman, false otherwise
     */
    public boolean isAllowedCharacter(Character character) {
        return character instanceof Gaul || character instanceof Roman;
    }

    /**
     * Adds a character to the village.
     * Validates that only allowed character types (Gauls and Romans) can enter.
     *
     * @param character the character to add
     */
    @Override
    public void addCharacter(Character character) {
        if (character == null) {
            System.out.println("Personnage invalide.");
            return;
        }

        if (!isAllowedCharacter(character)) {
            System.out.println("Erreur: Seuls les Gaulois et les créatures fantastiques peuvent entrer dans le village gaulois.");
            System.out.println(character.getName() + " n'a pas été ajouté.");
            return;
        }

        super.addCharacter(character);
    }
}
