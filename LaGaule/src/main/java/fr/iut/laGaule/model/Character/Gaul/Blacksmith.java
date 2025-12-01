package fr.iut.laGaule.model.Character.Gaul;


import fr.iut.laGaule.Serializer;

import java.util.*;

public class Blacksmith extends Gaul{

    public Blacksmith(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }


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