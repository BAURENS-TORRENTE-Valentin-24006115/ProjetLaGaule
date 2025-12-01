package fr.iut.laGaule.model.Place;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.Gaul.Merchant;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Place class.
 * Tests place management functionality.
 */
public class PlaceTest {

    private Place place;
    private ClanLeader leader;
    private ArrayList<Character> characters;
    private ArrayList<Foods> foods;

    @BeforeEach
    public void setUp() {
        leader = new ClanLeader("Chief", "M", 50);
        characters = new ArrayList<>();
        foods = new ArrayList<>();
        place = new GaulVillage("Test Village", 1000, leader, 0, characters, foods);
    }

    @Test
    public void testPlaceCreation() {
        assertEquals("Test Village", place.getName());
        assertNotNull(place.getCharacter());
        assertNotNull(place.getFood());
    }

    @Test
    public void testSetAndGetName() {
        place.setName("New Village");
        assertEquals("New Village", place.getName());
    }

    @Test
    public void testSetAndGetArea() {
        place.setArea(1000);
        assertEquals(1000, place.getArea());
    }

    @Test
    public void testSetAndGetClanLeader() {
        place.setClanLeader(leader);
        assertEquals(leader, place.getClanLeader());
    }

    @Test
    public void testSetAndGetNbCharacter() {
        place.setNbCharacter(5);
        assertEquals(5, place.getNbCharacter());
    }

    @Test
    public void testAddFood() {
        place.addFood(Foods.SANGLIER);
        assertTrue(place.getFood().contains(Foods.SANGLIER));
    }

    @Test
    public void testAddNullFood() {
        int initialSize = place.getFood().size();
        place.addFood(null);
        assertEquals(initialSize, place.getFood().size());
    }

    @Test
    public void testHealCharacters() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        druid.receiveDamage(40);
        place.getCharacter().add(druid);

        place.healCharacters(20);
        assertEquals(80, druid.getHealth());
    }

    @Test
    public void testHealCharactersEmptyPlace() {
        assertDoesNotThrow(() -> place.healCharacters(20));
    }

    @Test
    public void testFeedCharacters() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        place.getCharacter().add(druid);
        place.addFood(Foods.SANGLIER);

        int initialFoodSize = place.getFood().size();
        place.feedCharacters();

        assertEquals(initialFoodSize - 1, place.getFood().size());
    }

    @Test
    public void testFeedCharactersNoFood() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        place.getCharacter().add(druid);

        assertDoesNotThrow(() -> place.feedCharacters());
    }

    @Test
    public void testFeedCharactersNoCharacters() {
        place.addFood(Foods.SANGLIER);
        assertDoesNotThrow(() -> place.feedCharacters());
    }

    @Test
    public void testFeedCharacter() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        place.addCharacter(druid);  // Add character to place first
        place.addFood(Foods.SANGLIER);

        int initialFoodSize = place.getFood().size();
        place.feedCharacter(druid);

        assertEquals(initialFoodSize - 1, place.getFood().size());
    }

    @Test
    public void testFeedNullCharacter() {
        place.addFood(Foods.SANGLIER);
        assertDoesNotThrow(() -> place.feedCharacter(null));
    }

    @Test
    public void testToString() {
        String result = place.toString();
        assertNotNull(result);
        assertTrue(result.contains("Superficie"));
    }

    @Test
    public void testSetAndGetCharacterList() {
        ArrayList<Character> newCharacters = new ArrayList<>();
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        newCharacters.add(druid);

        place.setCharacter(newCharacters);
        assertEquals(1, place.getCharacter().size());
        assertTrue(place.getCharacter().contains(druid));
    }

    @Test
    public void testSetAndGetFoodList() {
        ArrayList<Foods> newFoods = new ArrayList<>();
        newFoods.add(Foods.SANGLIER);
        newFoods.add(Foods.MIEL);

        place.setFood(newFoods);
        assertEquals(2, place.getFood().size());
        assertTrue(place.getFood().contains(Foods.SANGLIER));
    }

    @Test
    public void testAddCharacterIncrementCounter() {
        int initialCount = place.getNbCharacter();
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        place.addCharacter(druid);

        assertEquals(initialCount + 1, place.getNbCharacter());
    }

    @Test
    public void testAddNullCharacter() {
        int initialCount = place.getNbCharacter();
        place.addCharacter(null);

        assertEquals(initialCount, place.getNbCharacter());
    }

    @Test
    public void testFeedCharactersMultiple() {
        Druid druid1 = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        Merchant merchant = new Merchant("Unhygienix", "M", 1.70, 45, 40, 50);

        place.addCharacter(druid1);
        place.addCharacter(merchant);
        place.addFood(Foods.SANGLIER);
        place.addFood(Foods.POISSON_FRAIS);

        place.feedCharacters();

        assertEquals(0, place.getFood().size());
    }

    @Test
    public void testFeedCharactersMoreCharactersThanFood() {
        Druid druid1 = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        Druid druid2 = new Druid("Agecanonix", "M", 1.65, 80, 30, 40);

        place.addCharacter(druid1);
        place.addCharacter(druid2);
        place.addFood(Foods.SANGLIER);

        place.feedCharacters();

        assertEquals(0, place.getFood().size());
    }

    @Test
    public void testFeedCharacterNotInPlace() {
        Druid outsider = new Druid("Outsider", "M", 1.75, 60, 50, 60);
        place.addFood(Foods.SANGLIER);

        int initialFoodSize = place.getFood().size();
        place.feedCharacter(outsider);

        // Food should not be consumed
        assertEquals(initialFoodSize, place.getFood().size());
    }

    @Test
    public void testFeedCharacterNoFood() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        place.addCharacter(druid);

        assertDoesNotThrow(() -> place.feedCharacter(druid));
    }

    @Test
    public void testHealCharactersMultiple() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        Merchant merchant = new Merchant("Unhygienix", "M", 1.70, 45, 40, 50);

        druid.receiveDamage(30);
        merchant.receiveDamage(40);

        place.addCharacter(druid);
        place.addCharacter(merchant);

        place.healCharacters(25);

        assertEquals(95, druid.getHealth());
        assertEquals(85, merchant.getHealth());
    }
}

