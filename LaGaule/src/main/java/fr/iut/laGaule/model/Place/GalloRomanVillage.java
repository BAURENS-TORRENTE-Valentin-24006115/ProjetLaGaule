package fr.iut.laGaule.model.Place;

import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.Gaul.Gaul;
import fr.iut.laGaule.model.Character.Roman.Roman;
import fr.iut.laGaule.model.Food.Food;

import java.util.ArrayList;

public class GalloRomanVillage extends Place {
    public GalloRomanVillage(String name, int area, ClanLeader clanLeader, int nbCharacter, ArrayList<Character> character, ArrayList<Food> food) {
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
     * Vérifie si un personnage est autorisé dans le village gaulois
     * @param character Le personnage à vérifier
     * @return true si le personnage est un Gaulois ou une créature fantastique
     */
    private boolean isAllowedCharacter(Character character) {
        return character instanceof Gaul || character instanceof Roman;
    }

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
