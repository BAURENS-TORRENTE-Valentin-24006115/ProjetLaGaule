package fr.iut.laGaule.model.Place;

import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.Gaul.Gaul;
import fr.iut.laGaule.model.Character.MythicalCreature.Lycanthrope;
import fr.iut.laGaule.model.Consumables.Foods.Foods;

import java.util.ArrayList;

/**
 * Represents a Gaul Village in the game.
 * This place type only allows Gaul characters and mythical creatures (Lycanthropes) to enter.
 * Romans and other character types are forbidden from entering the village.
 *
 * @see Place
 */
public class GaulVillage extends Place {

    /**
     * Constructs a new Gaul Village with the specified attributes.
     * Only allowed characters (Gauls and Lycanthropes) from the provided list are added.
     *
     * @param name the name of the village
     * @param area the area/size of the village in square meters
     * @param clanLeader the leader managing this village
     * @param nbCharacter the initial number of characters
     * @param character the list of characters to add (only allowed types will be added)
     * @param food the list of food items available in the village
     */
    public GaulVillage(String name, int area, ClanLeader clanLeader, int nbCharacter, ArrayList<Character> character, ArrayList<Foods> food) {
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
     * Checks if a character is allowed to enter the Gaul village.
     * Only Gauls and Lycanthropes are permitted.
     *
     * @param character the character to verify
     * @return true if the character is a Gaul or Lycanthrope, false otherwise
     */
    private boolean isAllowedCharacter(Character character) {
        return character instanceof Gaul || character instanceof Lycanthrope;
    }

    /**
     * Adds a character to the village.
     * Validates that only allowed character types (Gauls and Lycanthropes) can enter.
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
