package fr.iut.laGaule.model.Character.Gaul;

import fr.iut.laGaule.Serializer;
import fr.iut.laGaule.model.Consumables.Foods.Foods;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents an Innkeeper character in the Gaul faction.
 * The Innkeeper serves food (specifically boar) to other Gaul characters
 * and manages the sustenance of the village.
 *
 * @see Gaul
 */
public class Innkeeper extends Gaul{


    /**
     * Constructs a new Innkeeper with the specified attributes.
     *
     * @param name the name of the innkeeper
     * @param sex the sex/gender of the innkeeper
     * @param height the height of the innkeeper in meters
     * @param age the age of the innkeeper in years
     * @param strength the strength attribute of the innkeeper
     * @param endurance the endurance attribute of the innkeeper
     */
    public Innkeeper(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }

    /**
     * Performs the innkeeper's work activity by serving boar to the villagers.
     * The innkeeper randomly selects a Gaul from the saved data and feeds them.
     * The changes are then persisted back to the serialized data.
     *
     * If deserialization fails or returns empty data, an error message is displayed
     * and the method returns without making changes.
     */
    public void work() {
        System.out.println(this.name + " serves boar.");
        Serializer serializer = new Serializer();
        Map<String, Object> map = serializer.deserialize(getPlace());
        if (map == null || map.isEmpty()) {
            System.out.println("Erreur : Impossible de charger les données pour 'gaul' (Map vide ou null).");
            return;
        }
        List<String> keys = new ArrayList<>(map.keySet());
        Random rand = new Random();
        String randKey = keys.get(rand.nextInt(keys.size()));
        Object randObj = map.get(randKey);
        if(randObj instanceof Gaul){
            Foods food = Foods.SANGLIER;
            ((Gaul) randObj).eat(food);
        }
        map.put(randKey, randObj);
        serializer.serialize(getPlace(), map);
    }
}