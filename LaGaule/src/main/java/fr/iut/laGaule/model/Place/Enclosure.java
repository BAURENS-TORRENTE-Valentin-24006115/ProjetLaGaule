package fr.iut.laGaule.model.Place;

import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Food.Food;

import java.util.ArrayList;

public class Enclosure extends Place {
    public Enclosure(String name, int area, ClanLeader clanLeader, int nbCharacter, ArrayList<Character> character, ArrayList<Food> food) {
        super(name, area, clanLeader, nbCharacter, character, food);
    }



}
