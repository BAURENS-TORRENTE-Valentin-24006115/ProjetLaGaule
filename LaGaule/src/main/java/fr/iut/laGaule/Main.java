package fr.iut.laGaule;

import fr.iut.laGaule.model.Character.Gaul.Blacksmith;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.Gaul.Innkeeper;
import fr.iut.laGaule.model.Character.Gaul.Merchant;
import fr.iut.laGaule.model.Character.MythicalCreature.Lycanthrope;
import fr.iut.laGaule.model.Character.Roman.General;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Character.Roman.Prefect;
import fr.iut.laGaule.model.Consumables.Foods.Foods;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Druid dibiazah = new Druid("ya'qub qamar ad-din dibiazah", "male", 1.75, 80, 54, 50);
        Merchant kashmiri = new Merchant("khalid kashmiri", "male", 1.70, 25, 40, 30);
        Prefect karawita = new Prefect("khidir karawita", "male", 1.80, 54, 62, 52);
        General kanabawi = new General("ismail ahmad kanabawi", "female", 1.65, 34, 64, 25);
        Innkeeper sisha = new Innkeeper("usman abdul jalil sisha", "male", 1.79, 56, 12, 78);
        Lycanthrope sumbul = new Lycanthrope("muhammad sumbul", "male", 2.0, 46, 80, 70);
        Blacksmith oui = new Blacksmith("ouioui", "male", 2.0, 46, 80, 70);
        Legionary non =  new Legionary("non", "male", 2.0, 46, 80, 70);

        dibiazah.concoctPotion();

        kashmiri.drinkPotion(50);

        System.out.println(karawita.getHealth());
        karawita.receiveDamage(20);
        System.out.println(karawita.getHealth());
        karawita.heal(35);
        System.out.println(karawita.getHealth());

        sisha.work();

        kanabawi.command(non, sisha);
        dibiazah.eat(Foods.SANGLIER);
        dibiazah.fight(karawita);
        dibiazah.fight(karawita);
        dibiazah.fight(karawita);
        dibiazah.fight(karawita);
        dibiazah.fight(karawita);
        dibiazah.fight(karawita);
        dibiazah.fight(karawita);
        kanabawi.fight(dibiazah);

        Map<String, Object> map = new HashMap();
        map.put("bobi", dibiazah);
        map.put("kashmiri", kashmiri);
        map.put("sisha", sisha);
        map.put("oui", oui);
        Serializer serializer = new Serializer();
        serializer.serialize("gaul", map);
        oui.work();


    }

}
