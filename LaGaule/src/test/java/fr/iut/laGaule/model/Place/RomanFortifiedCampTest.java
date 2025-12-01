package fr.iut.laGaule.model.Place;

import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.MythicalCreature.Lycanthrope;
import fr.iut.laGaule.model.Character.Roman.General;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Character.Roman.Prefect;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RomanFortifiedCamp class.
 * Tests fortified camp specific functionality including character restrictions.
 */
public class RomanFortifiedCampTest {

    private RomanFortifiedCamp camp;
    private ClanLeader leader;

    @BeforeEach
    public void setUp() {
        leader = new ClanLeader("Commander", "M", 45);
        camp = new RomanFortifiedCamp("Fortified Camp", 1500, leader, 0, new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void testRomanFortifiedCampCreation() {
        assertEquals("Fortified Camp", camp.getName());
        assertEquals(1500, camp.getArea());
        assertEquals(leader, camp.getClanLeader());
    }

    @Test
    public void testAddGeneral() {
        General general = new General("Pompey", "M", 1.82, 45, 70, 65);
        camp.addCharacter(general);

        assertTrue(camp.getCharacter().contains(general));
        assertEquals(1, camp.getNbCharacter());
    }

    @Test
    public void testAddLegionary() {
        Legionary legionary = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        camp.addCharacter(legionary);

        assertTrue(camp.getCharacter().contains(legionary));
    }

    @Test
    public void testAddLycanthrope() {
        Lycanthrope lycanthrope = new Lycanthrope("Fenrir", "M", 1.90, 35, 80, 75);
        camp.addCharacter(lycanthrope);

        assertTrue(camp.getCharacter().contains(lycanthrope));
    }

    @Test
    public void testAddPrefectIsBlocked() {
        Prefect prefect = new Prefect("Karawita", "M", 1.80, 54, 62, 52);
        camp.addCharacter(prefect);

        // Prefect is not allowed (only General and Legionary)
        assertFalse(camp.getCharacter().contains(prefect));
    }

    @Test
    public void testAddGaulIsBlocked() {
        Druid gaul = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        camp.addCharacter(gaul);

        assertFalse(camp.getCharacter().contains(gaul));
    }

    @Test
    public void testConstructorFiltersCharacters() {
        ArrayList<fr.iut.laGaule.model.Character.Character> characters = new ArrayList<>();
        characters.add(new General("Pompey", "M", 1.82, 45, 70, 65));
        characters.add(new Druid("Panoramix", "M", 1.75, 60, 50, 60));
        characters.add(new Legionary("Brutus", "M", 1.75, 30, 60, 55));
        characters.add(new Prefect("Karawita", "M", 1.80, 54, 62, 52));

        ArrayList<Foods> foods = new ArrayList<>();

        RomanFortifiedCamp filteredCamp = new RomanFortifiedCamp("Filtered", 1500, leader, 4, characters, foods);

        // Should only have General and Legionary
        assertEquals(2, filteredCamp.getCharacter().size());
    }
}

