package fr.iut.laGaule;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.Gaul.Gaul;
import fr.iut.laGaule.model.Character.Roman.General;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Character.Roman.Roman;
import fr.iut.laGaule.model.Consumables.Foods.Foods;

import java.io.*;
import java.util.*;


public class Serializer{

    public void serialize(String zoneName, Map<String, Object> map){
        try {
            // Saving of object in a file
            FileOutputStream file = new FileOutputStream(zoneName + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(map);
            out.close();
            file.close();

        } catch (IOException ex) {
            System.out.println("IOException is caught");
        }
    }

    public Map<String, Object> deserialize(String zoneName) {
        try (FileInputStream file = new FileInputStream(zoneName + ".ser");
             ObjectInputStream in = new ObjectInputStream(file)) {

            // On lit L'ENSEMBLE de la map d'un seul coup
            Map<String, Object> mapRecuperee = (Map<String, Object>) in.readObject();

            System.out.println("Objet désérialisé avec succès.");
            return mapRecuperee;

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Character deserializeRandomCharacter(String zoneName) {
        Random random = new Random();
        try (FileInputStream file = new FileInputStream(zoneName + ".ser");
             ObjectInputStream in = new ObjectInputStream(file)) {

            // On lit L'ENSEMBLE de la map d'un seul coup
            Map<String, Object> map = (Map<String, Object>) in.readObject();

            System.out.println("Objet désérialisé avec succès.");
            List<String> keys = new ArrayList<>(map.keySet());
            Random rand = new Random();
            String randKey = keys.get(rand.nextInt(keys.size()));
            Object randObj = map.get(randKey);
            return randObj instanceof Character ? (Character) randObj : null;

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public Gaul deserializeRandomGaul(String zoneName) {
        Random random = new Random();
        Map<String, Object> map = (Map<String, Object>) deserialize(zoneName);
        if (map == null) {
            return null;
        }
        ArrayList<Gaul> gauls = new ArrayList<>();
        for (String key : map.keySet()) {
            if (map.get(key) instanceof Gaul) {
                gauls.add((Gaul) map.get(key));
            }
        }
        if (gauls.isEmpty()) {
            return null;
        }
        return gauls.get(random.nextInt(gauls.size()));

    }
    public Roman deserializeRandomRoman(String zoneName) {
        Random random = new Random();
        Map<String, Object> map = (Map<String, Object>) deserialize(zoneName);
        if (map == null) {
            return null;
        }
        ArrayList<Roman> Romans = new ArrayList<>();

        for (String key : map.keySet()) {
            if (map.get(key) instanceof Roman) {
                Romans.add((Roman) map.get(key));
            }
        }
        if (Romans.isEmpty()) {
            return null;
        }
        return Romans.get(random.nextInt(Romans.size()));

    }
    public Legionary deserializeRandomLegionary(String zoneName) {
        Random random = new Random();
        Map<String, Object> map = (Map<String, Object>) deserialize(zoneName);
        if (map == null) {
            return null;
        }
        ArrayList<Legionary> legionarys = new ArrayList<>();
        for (String key : map.keySet()) {
            if (map.get(key) instanceof Legionary) {
                legionarys.add((Legionary) map.get(key));
            }
        }
        if (legionarys.isEmpty()) {
            return null;
        }
        return legionarys.get(random.nextInt(legionarys.size()));

    }


    public static void main(String[] args) {
        Druid charle = new Druid("charle","female",25,16,48,56);
        General bobi = new General("bobi","female",25,15,48,46);
        Map<String,Object> map = new HashMap<>();
        map.put("charle",charle);
        Serializer serializer = new Serializer();
        serializer.serialize("france", map);
        map = null;
        map = serializer.deserialize("france");
        System.out.println(map);
        map.put("bobi",bobi);
        serializer.serialize("france", map);
        serializer.deserialize("france");
        System.out.println(map);
        General bob = (General) map.get("bobi");
        bob.receiveDamage(50);
        map.remove("bobi");
        map.put(bob.getName(),bob);
        serializer.serialize("truck", map);
        map = null;
        map = serializer.deserialize("truck");
        System.out.println(map);
        System.out.println(((General) map.get("bobi")).getHealth());
    }
}
