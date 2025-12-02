package fr.iut.laGaule.model.Place;

import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.MythicalCreature.Lycanthrope;
import fr.iut.laGaule.model.Character.Roman.General;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RomanCity class.
 * Tests Roman city specific functionality including character restrictions.
 */
public class RomanCityTest {

    private RomanCity city;
    private ClanLeader leader;

    @BeforeEach
    public void setUp() {
        leader = new ClanLeader("Caesar", "M", 50);
        city = new RomanCity("Roman City", 2000, leader, 0, new ArrayList<>(), new ArrayList<>());
    }

    @Test
    public void testRomanCityCreation() {
        assertEquals("Roman City", city.getName());
        assertEquals(2000, city.getArea());
        assertEquals(leader, city.getClanLeader());
    }

    @Test
    public void testAddRomanCharacter() {
        General general = new General("Pompey", "M", 1.82, 45, 70, 65);
        city.addCharacter(general);

        assertTrue(city.getCharacter().contains(general));
        assertEquals(1, city.getNbCharacter());
    }

    @Test
    public void testAddLycanthrope() {
        Lycanthrope lycanthrope = new Lycanthrope("Fenrir", "M", 1.90, 35, 80, 75);
        city.addCharacter(lycanthrope);

        assertTrue(city.getCharacter().contains(lycanthrope));
    }

    @Test
    public void testAddGaulCharacterIsBlocked() {
        Druid gaul = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        city.addCharacter(gaul);

        assertFalse(city.getCharacter().contains(gaul));
        assertEquals(0, city.getNbCharacter());
    }

    @Test
    public void testConstructorFiltersCharacters() {
        ArrayList<fr.iut.laGaule.model.Character.Character> characters = new ArrayList<>();
        characters.add(new General("Pompey", "M", 1.82, 45, 70, 65));
        characters.add(new Druid("Panoramix", "M", 1.75, 60, 50, 60));
        characters.add(new Legionary("Brutus", "M", 1.75, 30, 60, 55));

        ArrayList<Foods> foods = new ArrayList<>();

        RomanCity filteredCity = new RomanCity("Filtered", 2000, leader, 3, characters, foods);

        // Should only have 2 Romans, not the Gaul
        assertEquals(2, filteredCity.getCharacter().size());
    }
}

