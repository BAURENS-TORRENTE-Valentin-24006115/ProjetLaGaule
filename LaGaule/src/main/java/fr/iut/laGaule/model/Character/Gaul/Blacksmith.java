package fr.iut.laGaule.model.Character.Gaul;


import fr.iut.laGaule.Serializer;

import java.util.*;

/**
 * Represents a Blacksmith character in the Gaul faction.
 * A Blacksmith can forge weapons and shields, and their work randomly
 * enhances the strength or endurance of other Gaul characters.
 *
 * @see Gaul
 */
public class Blacksmith extends Gaul{

    /**
     * Constructs a new Blacksmith with the specified attributes.
     *
     * @param name the name of the blacksmith
     * @param sex the sex/gender of the blacksmith
     * @param height the height of the blacksmith in meters
     * @param age the age of the blacksmith in years
     * @param strength the strength attribute of the blacksmith
     * @param endurance the endurance attribute of the blacksmith
     */
    public Blacksmith(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }


    /**
     * Performs the blacksmith's work activity.
     * The blacksmith forges swords and shields, and as a result of their work,
     * a random Gaul character from the saved data receives a random bonus
     * to either their strength or endurance (0-19 points).
     *
     *
     * If deserialization fails or returns empty data, an error message is displayed
     * and the method returns without making changes.
     */
    public void work() {
        System.out.println(this.name + " forges swords and shields.");
        Serializer serializer = new Serializer();
        Map<String, Object> map = serializer.deserialize("gaul");
        if (map == null || map.isEmpty()) {
            System.out.println("Erreur : Impossible de charger les données pour 'gaul' (Map vide ou null).");
            return;
        }
        List<String> keys = new ArrayList<>(map.keySet());
        Random rand = new Random();
        String randKey = keys.get(rand.nextInt(keys.size()));
        Object randObj = map.get(randKey);
        if(randObj instanceof Gaul){
            if(rand.nextBoolean()){
                ((Gaul) randObj).setStrength(((Gaul) randObj).getStrength()+rand.nextInt(20));
            }else
                ((Gaul) randObj).setEndurance(((Gaul) randObj).getEndurance()+rand.nextInt(20));

        }
        map.put(randKey, randObj);
        serializer.serialize("gaul",map);
    }
}
