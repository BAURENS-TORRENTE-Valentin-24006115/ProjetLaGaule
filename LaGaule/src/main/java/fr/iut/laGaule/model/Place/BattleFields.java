package fr.iut.laGaule.model.Place;

import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.Gaul.Gaul;
import fr.iut.laGaule.model.Character.MythicalCreature.Lycanthrope;
import fr.iut.laGaule.model.Character.Roman.Roman;
import fr.iut.laGaule.model.Consumables.Foods.Foods;

import java.util.ArrayList;

 /**
 * Represents a battlefield in the game.
 * This place type allows Gauls, Romans, and Lycanthropes to be present for combat.
 * Battlefields are neutral zones where opposing factions can engage in fights.
 *
 * @see Place
 */
public class BattleFields extends Place {

    /**
     * Constructs a new Battlefield with the specified attributes.
     * Only allowed characters (Gauls, Romans, and Lycanthropes) from the provided list are added.
     *
     * @param name the name of the battlefield
     * @param area the area/size of the battlefield in square meters
     * @param clanLeader the leader managing this battlefield
     * @param nbCharacter the initial number of characters
     * @param character the list of characters to add (only allowed types will be added)
     * @param food the list of food items available on the battlefield
     */
    public BattleFields(String name, int area, ClanLeader clanLeader, int nbCharacter, ArrayList<Character> character, ArrayList<Foods> food) {
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
     * Checks if a character is allowed to enter the battlefield.
     * Gauls, Romans, and Lycanthropes are permitted.
     *
     * @param character the character to verify
     * @return true if the character is a Gaul, Roman, or Lycanthrope, false otherwise
     */
    private boolean isAllowedCharacter(Character character) {
        return character instanceof Gaul || character instanceof Roman || character instanceof Lycanthrope;
    }

    /**
     * Adds a character to the battlefield.
     * Validates that only allowed character types can enter.
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
