package fr.iut.laGaule.model.Place;

import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.Gaul.Merchant;
import fr.iut.laGaule.model.Character.MythicalCreature.Lycanthrope;
import fr.iut.laGaule.model.Character.Roman.General;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BattleFields class.
 * Tests battlefield functionality where Gauls, Romans, and Lycanthropes can all be present.
 */
public class BattleFieldsTest {

    private BattleFields battlefield;
    private ClanLeader leader;

    @BeforeEach
    public void setUp() {
        leader = new ClanLeader("Warlord", "M", 40);
        battlefield = new BattleFields("Alesia", 3000, leader, 0, new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void testBattleFieldsCreation() {
        assertEquals("Alesia", battlefield.getName());
        assertEquals(3000, battlefield.getArea());
        assertEquals(leader, battlefield.getClanLeader());
    }

    @Test
    public void testAddGaulCharacter() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        battlefield.addCharacter(druid);

        assertTrue(battlefield.getCharacter().contains(druid));
        assertEquals(1, battlefield.getNbCharacter());
    }

    @Test
    public void testAddRomanCharacter() {
        Legionary legionary = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        battlefield.addCharacter(legionary);

        assertTrue(battlefield.getCharacter().contains(legionary));
        assertEquals(1, battlefield.getNbCharacter());
    }

    @Test
    public void testAddLycanthrope() {
        Lycanthrope lycanthrope = new Lycanthrope("Fenrir", "M", 1.90, 35, 80, 75);
        battlefield.addCharacter(lycanthrope);

        assertTrue(battlefield.getCharacter().contains(lycanthrope));
        assertEquals(1, battlefield.getNbCharacter());
    }

    @Test
    public void testAddAllCharacterTypes() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        General general = new General("Pompey", "M", 1.82, 45, 70, 65);
        Lycanthrope lycanthrope = new Lycanthrope("Fenrir", "M", 1.90, 35, 80, 75);

        battlefield.addCharacter(druid);
        battlefield.addCharacter(general);
        battlefield.addCharacter(lycanthrope);

        assertEquals(3, battlefield.getNbCharacter());
        assertTrue(battlefield.getCharacter().contains(druid));
        assertTrue(battlefield.getCharacter().contains(general));
        assertTrue(battlefield.getCharacter().contains(lycanthrope));
    }

    @Test
    public void testConstructorAcceptsAllAllowedCharacters() {
        ArrayList<fr.iut.laGaule.model.Character.Character> characters = new ArrayList<>();
        characters.add(new Druid("Panoramix", "M", 1.75, 60, 50, 60));
        characters.add(new Lycanthrope("Fenrir", "M", 1.90, 35, 80, 75));
        characters.add(new Merchant("Unhygienix", "M", 1.70, 45, 40, 50));
        characters.add(new General("Pompey", "M", 1.82, 45, 70, 65));
        characters.add(new Legionary("Brutus", "M", 1.75, 30, 60, 55));

        ArrayList<Foods> foods = new ArrayList<>();

        BattleFields populatedBattlefield = new BattleFields("Gergovie", 3000, leader, 5, characters, foods);

        // All 5 characters should be added
        assertEquals(5, populatedBattlefield.getCharacter().size());
    }
}

