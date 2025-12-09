package fr.iut.laGaule.model.Place;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Consumables.Foods.Foods;

import java.util.ArrayList;

/**
 * Abstract base class representing a place in the game world.
 * Places can contain characters, food supplies, and are managed by a clan leader.
 * This class provides common functionality for all place types including villages,
 * cities, camps, and battlefields.
 */
public abstract class Place {
    private String name;
    private int area;
    private ClanLeader clanLeader;
    private int nbCharacter;
    private ArrayList<Character> Character;
    private ArrayList<Foods> food;

    /**
     * Constructs a new Place with the specified attributes.
     *
     * @param name the name of the place
     * @param area the area/size of the place in square meters
     * @param clanLeader the leader managing this place
     * @param nbCharacter the initial number of characters
     * @param character the list of characters present in the place
     * @param food the list of food items available in the place
     */
    public Place(String name, int area, ClanLeader clanLeader, int nbCharacter, ArrayList<Character> character, ArrayList<Foods> food) {
        this.name = name;
        this.area = area;
        this.clanLeader = clanLeader;
        this.nbCharacter = nbCharacter;
        Character = character;
        this.food = food;
    }

    /**
     * Gets the clan leader managing this place.
     *
     * @return the clan leader
     */
    public ClanLeader getClanLeader() {
        return clanLeader;
    }

    /**
     * Sets the clan leader managing this place.
     *
     * @param clanLeader the new clan leader
     */
    public void setClanLeader(ClanLeader clanLeader) {
        this.clanLeader = clanLeader;
    }

    /**
     * Gets the area/size of the place.
     *
     * @return the area in square meters
     */
    public int getArea() {
        return area;
    }

    /**
     * Sets the area/size of the place.
     *
     * @param area the new area in square meters
     */
    public void setArea(int area) {
        this.area = area;
    }

    /**
     * Gets the number of characters in the place.
     *
     * @return the number of characters
     */
    public int getNbCharacter() {
        return nbCharacter;
    }

    /**
     * Sets the number of characters in the place.
     *
     * @param nbCharacter the new number of characters
     */
    public void setNbCharacter(int nbCharacter) {
        this.nbCharacter = nbCharacter;
    }

    /**
     * Gets the list of characters present in the place.
     *
     * @return the ArrayList of characters
     */
    public ArrayList<Character> getCharacter() {
        return Character;
    }

    /**
     * Sets the list of characters present in the place.
     *
     * @param character the new ArrayList of characters
     */
    public void setCharacter(ArrayList<Character> character) {
        Character = character;
    }

    /**
     * Gets the list of food items available in the place.
     *
     * @return the ArrayList of food items
     */
    public ArrayList<Foods> getFood() {
        return food;
    }

    /**
     * Sets the list of food items available in the place.
     *
     * @param food the new ArrayList of food items
     */
    public void setFood(ArrayList<Foods> food) {
        this.food = food;
    }

    /**
     * Gets the name of the place.
     *
     * @return the name of the place
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the place.
     *
     * @param name the new name of the place
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Superficie: ").append(area).append(" m²\n");
        sb.append("Nombre de personnages: ").append(nbCharacter).append("\n\n");

        // Affichage des personnages
        if (Character != null && !Character.isEmpty()) {
            sb.append("--- Personnages ---\n");
            for (Character c : Character) {
                sb.append(c.toString()).append("\n");
            }
        } else {
            sb.append("Aucun personnage dans ce lieu.\n");
        }

        sb.append("\n");

        // Affichage des aliments
        if (food != null && !food.isEmpty()) {
            sb.append("--- Aliments disponibles ---\n");
            for (Foods f : food) {
                sb.append("- ").append(f.getName()).append("\n");
            }
        } else {
            sb.append("Aucun aliment disponible.\n");
        }

        return sb.toString();
    }

    /**
     * Adds a food item to the place's inventory.
     *
     * @param foodItem the food item to add
     */
    public void addFood(Foods foodItem) {
        if (foodItem != null) {
            food.add(foodItem);
            System.out.println(foodItem.getName() + " a été ajouté à " + name + ".");
        } else {
            System.out.println("Aliment invalide.");
        }
    }

    /**
     * Heals all the characters present in the location.
     * Applies the specified healing amount to each character.
     *
     * @param healingAmount the amount of health points to restore to each character
     */
    public void healCharacters(int healingAmount) {
        if (Character != null && !Character.isEmpty()) {
            for (Character c : Character) {
                c.heal(healingAmount);
            }
            System.out.println("Tous les personnages de " + name + " ont été soignés.");
        } else {
            System.out.println("Aucun personnage à soigner dans " + name + ".");
        }
    }

    /**
     * Feeds all characters with the food available in the location.
     * Each character receives one food item until all food or characters are processed.
     */
    public void feedCharacters() {
        if (Character == null || Character.isEmpty()) {
            System.out.println("Aucun personnage à nourrir dans " + name + ".");
            return;
        }

        if (food == null || food.isEmpty()) {
            System.out.println("Aucun aliment disponible dans " + name + " pour nourrir les personnages.");
            return;
        }

        for (Character c : Character) {
            if (!food.isEmpty()) {
                Foods f = food.get(0);
                c.eat(f);
                food.remove(0);
                System.out.println(c.getName() + " a mangé " + f.getName());
            } else {
                System.out.println("Plus d'aliments disponibles pour nourrir " + c.getName());
                break;
            }
        }
    }

    /**
     * Feeds a specific character with a food item from the place's inventory.
     * The first available food item is given to the character and removed from inventory.
     *
     * @param character the character to feed
     */
    public void feedCharacter(Character character) {
        if (character == null) {
            System.out.println("Personnage invalide.");
            return;
        }

        if (!Character.contains(character)) {
            System.out.println(character.getName() + " n'est pas présent dans " + name + ".");
            return;
        }

        if (food == null || food.isEmpty()) {
            System.out.println("Aucun aliment disponible dans " + name + ".");
            return;
        }

        Foods f = food.get(0);
        character.eat(f);
        food.remove(0);
        System.out.println(character.getName() + " a mangé " + f.getName() + " dans " + name + ".");
    }

    /**
     * Adds a character to the place.
     * Increments the character count and adds the character to the list.
     *
     * @param character the character to add to the place
     */
    public void addCharacter(Character character) {
        if (character != null) {
            Character.add(character);
            nbCharacter++;
            System.out.println(character.getName() + " a été ajouté à " + name + ".");
        } else {
            System.out.println("Personnage invalide.");
        }
    }

    /**
     * Removes a character from the place.
     * Decrements the character count and removes the character from the list.
     *
     * @param character the character to remove from the place
     * @return true if the character was removed, false otherwise
     */
    public boolean removeCharacter(Character character) {
        if (character != null && Character.contains(character)) {
            Character.remove(character);
            nbCharacter--;
            System.out.println(character.getName() + " a été retiré de " + name + ".");
            return true;
        } else {
            System.out.println("Personnage non trouvé ou invalide.");
            return false;
        }
    }
}

