package fr.iut.laGaule.model.Character.Gaul;

import fr.iut.laGaule.Serializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Innkeeper extends Gaul{


    public Innkeeper(String name, String sex, double height, int age, int strength, int endurance) {
        super(name, sex, height, age, strength, endurance);
    }
    public void work() {
        System.out.println(this.name + " serves boar.");
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
            ((Gaul) randObj).eat(rand.nextInt(50));
        }
        map.put(randKey, randObj);
        serializer.serialize("gaul", map);
    }
}