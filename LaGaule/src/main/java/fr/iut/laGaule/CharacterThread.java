package fr.iut.laGaule;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.Gaul.Gaul;
import fr.iut.laGaule.model.Character.Roman.Roman;

import java.util.Random;

public class CharacterThread implements Runnable{
    public Character character;

    public CharacterThread(Character character){
        this.character = character;
    }

    @Override
    public void run() {
        Serializer serializer = new Serializer();
        Random random = new Random();
        while(true){
            if(character instanceof Druid){
                int randomInt = random.nextInt(5);
                if(randomInt == 0){
                    ((Druid) character).concoctPotion();
                }
                if(randomInt == 1){
                    fr.iut.laGaule.model.Character.Character oui = serializer.deserializeRandomCharacter(character.getPlace());
                    while(!(oui instanceof Gaul)){
                        oui = serializer.deserializeRandomCharacter(character.getPlace());
                        System.out.println(oui.getName());
                    }
                    ((Druid) character).command((Gaul) oui);
                }
                if(randomInt == 2){
                    Character oui = serializer.deserializeRandomCharacter(character.getPlace());
                    while(!(oui instanceof Roman)){
                        oui = serializer.deserializeRandomCharacter(character.getPlace());
                        System.out.println(oui.getName());
                    }
                    ((Druid) character).fight((Roman) oui);
                }if(randomInt == 3){
                    ((Druid) character).work();
                }
                System.out.println(character.getName()+"a travail: "+randomInt);
                try {
                    wait(12);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

}
