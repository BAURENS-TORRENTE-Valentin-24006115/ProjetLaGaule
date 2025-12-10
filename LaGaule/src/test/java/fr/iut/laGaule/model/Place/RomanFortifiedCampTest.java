package fr.iut.laGaule.model.Place;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Gaul.Gaul;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Character.Roman.General;
import fr.iut.laGaule.model.Character.Roman.Prefect;
import fr.iut.laGaule.model.Character.MythicalCreature.*;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RomanFortifiedCamp class.
 */
public class RomanFortifiedCampTest {

    private RomanFortifiedCamp camp;
    private ArrayList<Character> characters;
    private ArrayList<Foods> foods;

    @BeforeEach
    public void setUp() {
        characters = new ArrayList<>();
        foods = new ArrayList<>();
        camp = new RomanFortifiedCamp("Camp de Babaorum", 500, null, 0, characters, foods);
    }

    @Test
    public void testRomanFortifiedCampCreation() {
        assertEquals("Camp de Babaorum", camp.getName());
        assertEquals(500, camp.getArea());
        assertNotNull(camp.getCharacter());
    }

    @Test
    public void testIsAllowedCharacterGeneral() {
        General general = new General("Pompey", "M", 1.82, 45, 70, 65);
        assertTrue(camp.isAllowedCharacter(general));
    }

    @Test
    public void testIsAllowedCharacterLegionary() {
        Legionary legionary = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        assertTrue(camp.isAllowedCharacter(legionary));
    }

    @Test
    public void testIsAllowedCharacterLycanthrope() {
        Lycanthrope lycan = new Lycanthrope("Fenrir", "M", 1.9, 35, 80, 75,
                AgeCategory.ADULT, 5, Rank.BETA, 60, true);
        assertTrue(camp.isAllowedCharacter(lycan));
    }

    @Test
    public void testIsAllowedCharacterGaul() {
        Gaul gaul = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        assertFalse(camp.isAllowedCharacter(gaul));
    }

    @Test
    public void testIsAllowedCharacterPrefect() {
        Prefect prefect = new Prefect("Karawita", "M", 1.80, 54, 62, 52);
        // Prefect is neither General nor Legionary, so should be false
        assertFalse(camp.isAllowedCharacter(prefect));
    }

    @Test
    public void testAddCharacterGeneral() {
        General general = new General("Pompey", "M", 1.82, 45, 70, 65);
        camp.addCharacter(general);
        assertTrue(camp.getCharacter().contains(general));
    }

    @Test
    public void testAddCharacterLegionary() {
        Legionary legionary = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        camp.addCharacter(legionary);
        assertTrue(camp.getCharacter().contains(legionary));
    }

    @Test
    public void testAddCharacterGaul() {
        Gaul gaul = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        int initialSize = camp.getCharacter().size();
        camp.addCharacter(gaul);
        // Gaul should not be added
        assertEquals(initialSize, camp.getCharacter().size());
    }

    @Test
    public void testAddCharacterNull() {
        int initialSize = camp.getCharacter().size();
        camp.addCharacter(null);
        assertEquals(initialSize, camp.getCharacter().size());
    }

    @Test
    public void testConstructorWithMixedCharacters() {
        ArrayList<Character> mixedChars = new ArrayList<>();
        General general = new General("Pompey", "M", 1.82, 45, 70, 65);
        Gaul gaul = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        mixedChars.add(general);
        mixedChars.add(gaul);

        RomanFortifiedCamp mixedCamp = new RomanFortifiedCamp("Mixed Camp", 500, null, 0, mixedChars, new ArrayList<>());
        // Only General should be added
        assertEquals(1, mixedCamp.getCharacter().size());
        assertTrue(mixedCamp.getCharacter().contains(general));
    }

    @Test
    public void testConstructorWithNullCharacters() {
        RomanFortifiedCamp nullCharCamp = new RomanFortifiedCamp("Test", 100, null, 0, null, new ArrayList<>());
        assertNotNull(nullCharCamp.getCharacter());
    }

    @Test
    public void testSetClanLeader() {
        ClanLeader leader = new ClanLeader("Commander", "M", 45);
        camp.setClanLeader(leader);
        assertEquals(leader, camp.getClanLeader());
    }

    @Test
    public void testAddFood() {
        camp.addFood(Foods.VIN);
        assertTrue(camp.getFood().contains(Foods.VIN));
    }

    @Test
    public void testAddCharacterLycanthrope() {
        Lycanthrope lycan = new Lycanthrope("Fenrir", "M", 1.9, 35, 80, 75,
                AgeCategory.ADULT, 5, Rank.BETA, 60, true);
        camp.addCharacter(lycan);
        assertTrue(camp.getCharacter().contains(lycan));
    }
}
