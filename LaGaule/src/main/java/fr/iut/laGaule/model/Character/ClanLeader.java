package fr.iut.laGaule.model.Character;

import fr.iut.laGaule.Serializer;
import fr.iut.laGaule.model.Character.Gaul.*;
import fr.iut.laGaule.model.Character.MythicalCreature.Lycanthrope;
import fr.iut.laGaule.model.Character.Roman.*;
import fr.iut.laGaule.model.Place.*;

import java.io.Serializable;
import java.util.Map;

/**
 * Class representing a clan leader who manages a place
 */
public class ClanLeader implements Serializable {
    private String name;
    private String sex;
    private int age;
    private Place managedPlace;

    /**
     * Clan Chief Builder
     * @param name chief's name
     * @param sex chief's sex
     * @param age chief's age
     */
    public ClanLeader(String name, String sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.managedPlace = null;
    }

    /**
     * Clan leader builder with an assigned location
     * @param name chief's name
     * @param sex chief's sex
     * @param age chief's age
     * @param managedPlace chief's managed place
     */
    public ClanLeader(String name, String sex, int age, Place managedPlace) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.managedPlace = managedPlace;
    }

    // Getters et setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Place getManagedPlace() {
        return managedPlace;
    }

    public void setManagedPlace(Place managedPlace) {
        this.managedPlace = managedPlace;
    }

    /**
     * Examine the managed location and display its characteristics as well as the list of characters and food items
     */
    public void examineManagedPlace() {
        if (managedPlace == null) {
            System.out.println(name + " ne gère aucun lieu.");
            return;
        }

        System.out.println("=== Examen du lieu par " + name + " ===");
        System.out.println("Lieu: " + managedPlace.getName());
        System.out.println(managedPlace.toString());
        System.out.println("===========================================");
    }

    /**
     * Create a new character and add it to the managed location
     * @param characterType The type of character (gaul_merchant, gaul_innkeeper, etc.)
     * @param name The character's name
     * @param sex The gender of the character
     * @param height The size of the character
     * @param age The character's age
     * @param strength The strength of the character
     * @param endurance The character's endurance
     * @return The character created or null if failure
     */
    public Character createCharacter(String characterType, String name, String sex, double height,
                                     int age, int strength, int endurance) {
        if (managedPlace == null) {
            System.out.println(this.name + " ne peut pas créer de personnage sans lieu assigné.");
            return null;
        }

        Character newCharacter = null;

        switch (characterType.toLowerCase()) {
            // Gaulois
            case "gaul_merchant":
            case "merchant":
                newCharacter = new Merchant(name, sex, height, age, strength, endurance);
                break;
            case "gaul_innkeeper":
            case "innkeeper":
                newCharacter = new Innkeeper(name, sex, height, age, strength, endurance);
                break;
            case "gaul_blacksmith":
            case "blacksmith":
                newCharacter = new Blacksmith(name, sex, height, age, strength, endurance);
                break;
            case "gaul_druid":
            case "druid":
                newCharacter = new Druid(name, sex, height, age, strength, endurance);
                break;

            // Romains
            case "roman_legionary":
            case "legionary":
                newCharacter = new Legionary(name, sex, height, age, strength, endurance);
                break;
            case "roman_prefect":
            case "prefect":
                newCharacter = new Prefect(name, sex, height, age, strength, endurance);
                break;
            case "roman_general":
            case "general":
                newCharacter = new General(name, sex, height, age, strength, endurance);
                break;

            // Créatures fantastiques
            case "lycanthrope":
                newCharacter = new Lycanthrope(name, sex, height, age, strength, endurance);
                break;

            default:
                System.out.println("Type de personnage inconnu: " + characterType);
                return null;
        }

        if (newCharacter != null) {
            managedPlace.addCharacter(newCharacter);
            System.out.println(this.name + " a créé " + newCharacter.getName() + " (" + characterType + ")");
        }

        return newCharacter;
    }

    /**
     * Heals a specific character in the managed location
     * @param character The character to care for
     * @param healingAmount The amount of care
     */
    public void healCharacter(Character character, int healingAmount) {
        if (managedPlace == null) {
            System.out.println(this.name + " ne peut pas soigner sans lieu assigné.");
            return;
        }

        if (character == null) {
            System.out.println("Personnage invalide.");
            return;
        }

        if (!managedPlace.getCharacter().contains(character)) {
            System.out.println(character.getName() + " n'est pas dans " + managedPlace.getName());
            return;
        }

        System.out.println(this.name + " soigne " + character.getName());
        character.heal(healingAmount);
    }

    /**
     * Heals all characters in the managed location
     * @param healingAmount The amount of care for each character
     */
    public void healAllCharacters(int healingAmount) {
        if (managedPlace == null) {
            System.out.println(this.name + " ne peut pas soigner sans lieu assigné.");
            return;
        }

        System.out.println(this.name + " soigne tous les personnages de " + managedPlace.getName());
        managedPlace.healCharacters(healingAmount);
    }

    /**
     * Feeds a specific character in the managed location
     * @param character The character to feed
     */
    public void feedCharacter(Character character) {
        if (managedPlace == null) {
            System.out.println(this.name + " ne peut pas nourrir sans lieu assigné.");
            return;
        }

        System.out.println(this.name + " nourrit " + character.getName());
        managedPlace.feedCharacter(character);
    }

    /**
     * Feeds all the characters in the managed location
     */
    public void feedAllCharacters() {
        if (managedPlace == null) {
            System.out.println(this.name + " ne peut pas nourrir sans lieu assigné.");
            return;
        }

        System.out.println(this.name + " nourrit tous les personnages de " + managedPlace.getName());
        managedPlace.feedCharacters();
    }

    /**
     * Ask a druid to make a magic potion
     * @param druid The druid who will concoct the potion
     */
    public void askDruidForMagicPotion(Druid druid) {
        if (managedPlace == null) {
            System.out.println(this.name + " ne peut pas commander de potion sans lieu assigné.");
            return;
        }

        if (druid == null) {
            System.out.println("Druide invalide.");
            return;
        }

        if (!managedPlace.getCharacter().contains(druid)) {
            System.out.println(druid.getName() + " n'est pas dans " + managedPlace.getName());
            return;
        }

        System.out.println(this.name + " demande à " + druid.getName() + " de préparer la potion magique.");
        druid.concoctPotion();
    }

    /**
     * Give a magic potion to a character in the place
     * @param character The character who is going to drink the potion
     * @param potionAmount The amount of potion
     */
    public void giveMagicPotion(Character character, int potionAmount) {
        if (managedPlace == null) {
            System.out.println(this.name + " ne peut pas donner de potion sans lieu assigné.");
            return;
        }

        if (character == null) {
            System.out.println("Personnage invalide.");
            return;
        }

        if (!managedPlace.getCharacter().contains(character)) {
            System.out.println(character.getName() + " n'est pas dans " + managedPlace.getName());
            return;
        }

        System.out.println(this.name + " donne de la potion magique à " + character.getName());
        character.drinkPotion(potionAmount);
    }

    /**
     * Transfers a character from the managed location to a battlefield
     * @param character The character to transfer
     * @param battleField The destination battlefield
     */
    public void transferToBattleField(Character character, BattleFields battleField) {
        if (managedPlace == null) {
            System.out.println(this.name + " ne peut pas transférer sans lieu assigné.");
            return;
        }

        if (character == null || battleField == null) {
            System.out.println("Personnage ou champ de bataille invalide.");
            return;
        }

        if (!managedPlace.getCharacter().contains(character)) {
            System.out.println(character.getName() + " n'est pas dans " + managedPlace.getName());
            return;
        }

        // Retirer le personnage du lieu actuel
        managedPlace.getCharacter().remove(character);
        managedPlace.setNbCharacter(managedPlace.getNbCharacter() - 1);

        // Ajouter au champ de bataille
        battleField.addCharacter(character);

        System.out.println(this.name + " a transféré " + character.getName() +
                         " de " + managedPlace.getName() + " vers " + battleField.getName());
    }

    /**
     * Transfers a character from the managed place to an enclosure
     * @param character The character to transfer
     * @param enclosure The destination enclosure
     */
    public void transferToEnclosure(Character character, Enclosure enclosure) {
        if (managedPlace == null) {
            System.out.println(this.name + " ne peut pas transférer sans lieu assigné.");
            return;
        }

        if (character == null || enclosure == null) {
            System.out.println("Personnage ou enclos invalide.");
            return;
        }

        if (!managedPlace.getCharacter().contains(character)) {
            System.out.println(character.getName() + " n'est pas dans " + managedPlace.getName());
            return;
        }

        // Retirer le personnage du lieu actuel
        managedPlace.getCharacter().remove(character);
        managedPlace.setNbCharacter(managedPlace.getNbCharacter() - 1);

        // Ajouter à l'enclos
        enclosure.addCharacter(character);

        System.out.println(this.name + " a transféré " + character.getName() +
                         " de " + managedPlace.getName() + " vers " + enclosure.getName());
    }
    public void transferCharacter(Character character,Place oui) {
        Serializer serializer =  new Serializer();
        if(oui.isAllowedCharacter(character)){
            Map<String, Object> fi = serializer.deserialize(character.getPlace());
            fi.remove(character.getName());
            serializer.serialize(character.getPlace(),fi);
            fi = serializer.deserialize(oui.getName());
            fi.put(character.getName(),character);
            serializer.serialize(oui.getName(),fi);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Chef de clan: ").append(name).append("\n");
        sb.append("Sexe: ").append(sex).append("\n");
        sb.append("Âge: ").append(age).append(" ans\n");
        if (managedPlace != null) {
            sb.append("Gère le lieu: ").append(managedPlace.getName()).append("\n");
        } else {
            sb.append("Ne gère aucun lieu actuellement.\n");
        }
        return sb.toString();
    }
}