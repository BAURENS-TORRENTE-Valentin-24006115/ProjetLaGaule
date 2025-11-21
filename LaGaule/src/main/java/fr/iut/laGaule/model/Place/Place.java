package fr.iut.laGaule.model.Place;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Food.Food;

import java.util.ArrayList;

public abstract class Place {
    private String name;
    private int area;
    private ClanLeader clanLeader;
    private int nbCharacter;
    private ArrayList<Character> Character;
    private ArrayList<Food> food;

    public Place(String name, int area, ClanLeader clanLeader, int nbCharacter, ArrayList<Character> character, ArrayList<Food> food) {
        this.name = name;
        this.area = area;
        this.clanLeader = clanLeader;
        this.nbCharacter = nbCharacter;
        Character = character;
        this.food = food;
    }

    public ClanLeader getClanLeader() {
        return clanLeader;
    }

    public void setClanLeader(ClanLeader clanLeader) {
        this.clanLeader = clanLeader;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getNbCharacter() {
        return nbCharacter;
    }

    public void setNbCharacter(int nbCharacter) {
        this.nbCharacter = nbCharacter;
    }

    public ArrayList<Character> getCharacter() {
        return Character;
    }

    public void setCharacter(ArrayList<Character> character) {
        Character = character;
    }

    public ArrayList<Food> getFood() {
        return food;
    }

    public void setFood(ArrayList<Food> food) {
        this.food = food;
    }

    public String getName() {
        return name;
    }

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
            for (Food f : food) {
                sb.append("- ").append(f.toString()).append("\n");
            }
        } else {
            sb.append("Aucun aliment disponible.\n");
        }

        return sb.toString();
    }

    public void addFood(Food foodItem) {
        if (foodItem != null) {
            food.add(foodItem);
            System.out.println(foodItem.getName() + " a été ajouté à " + name + ".");
        } else {
            System.out.println("Aliment invalide.");
        }
    }

    /**
     * Heals all the characters present in the location
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
     * Feeds all characters with the food available in the location
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
                Food f = food.getFirst(); // Prend le premier aliment disponible
                c.eat(f);
                food.removeFirst(); // Retire l'aliment consommé
                System.out.println(c.getName() + " a mangé " + f.getName());
            } else {
                System.out.println("Plus d'aliments disponibles pour nourrir " + c.getName());
                break;
            }
        }
    }

    /**
     * Feeds a specific character with a local food item
     * @param character The character to feed
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

        Food f = food.get(0);
        character.eat(f);
        food.remove(0);
        System.out.println(character.getName() + " a mangé " + f.getName() + " dans " + name + ".");
    }

    public void addCharacter(Character character) {
        if (character != null) {
            Character.add(character);
            nbCharacter++;
            System.out.println(character.getName() + " a été ajouté à " + name + ".");
        } else {
            System.out.println("Personnage invalide.");
        }
    }
}