package fr.iut.laGaule.model.Character;

import fr.iut.laGaule.model.Character.Gaul.*;
import fr.iut.laGaule.model.Character.Roman.*;
import fr.iut.laGaule.model.Character.MythicalCreature.Lycanthrope;
import fr.iut.laGaule.model.Consumables.Foods.Foods;
import fr.iut.laGaule.model.Place.GaulVillage;
import fr.iut.laGaule.model.Place.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ClanLeader class.
 * Tests clan leader management capabilities.
 */
public class ClanLeaderTest {

    private ClanLeader leader;
    private Place village;

    @BeforeEach
    public void setUp() {
        leader = new ClanLeader("Abraracourcix", "M", 50);
        village = new GaulVillage("Village Gaulois", 1000, leader, 0, new java.util.ArrayList<>(), new java.util.ArrayList<>());
        leader.setManagedPlace(village);
    }

    @Test
    public void testClanLeaderCreation() {
        assertEquals("Abraracourcix", leader.getName());
        assertEquals("M", leader.getSex());
        assertEquals(50, leader.getAge());
    }

    @Test
    public void testClanLeaderWithPlace() {
        ClanLeader leaderWithPlace = new ClanLeader("Chief", "M", 45, village);
        assertEquals(village, leaderWithPlace.getManagedPlace());
    }

    @Test
    public void testSettersAndGetters() {
        leader.setName("NewName");
        assertEquals("NewName", leader.getName());

        leader.setSex("F");
        assertEquals("F", leader.getSex());

        leader.setAge(60);
        assertEquals(60, leader.getAge());

        ClanLeader newLeader = new ClanLeader("NewLeader", "M", 45);
        Place newPlace = new GaulVillage("New Village", 1000, newLeader, 0, new java.util.ArrayList<>(), new java.util.ArrayList<>());
        leader.setManagedPlace(newPlace);
        assertEquals(newPlace, leader.getManagedPlace());
    }

    @Test
    public void testExamineManagedPlace() {
        assertDoesNotThrow(() -> leader.examineManagedPlace());
    }

    @Test
    public void testExamineManagedPlaceWithoutPlace() {
        ClanLeader noPlaceLeader = new ClanLeader("NoPlace", "M", 40);
        assertDoesNotThrow(() -> noPlaceLeader.examineManagedPlace());
    }

    @Test
    public void testCreateMerchant() {
        Character merchant = leader.createCharacter("merchant", "Unhygienix", "M", 1.70, 45, 40, 50);
        assertNotNull(merchant);
        assertTrue(merchant instanceof Merchant);
        assertEquals("Unhygienix", merchant.getName());
    }

    @Test
    public void testCreateInnkeeper() {
        Character innkeeper = leader.createCharacter("innkeeper", "Ordralfabetix", "M", 1.75, 50, 45, 55);
        assertNotNull(innkeeper);
        assertTrue(innkeeper instanceof Innkeeper);
    }

    @Test
    public void testCreateBlacksmith() {
        Character blacksmith = leader.createCharacter("blacksmith", "Cetautomatix", "M", 1.80, 40, 60, 55);
        assertNotNull(blacksmith);
        assertTrue(blacksmith instanceof Blacksmith);
    }

    @Test
    public void testCreateDruid() {
        Character druid = leader.createCharacter("druid", "Panoramix", "M", 1.75, 60, 50, 60);
        assertNotNull(druid);
        assertTrue(druid instanceof Druid);
    }

    @Test
    public void testCreateLegionary() {
        Character legionary = leader.createCharacter("legionary", "Brutus", "M", 1.75, 30, 60, 55);
        assertNotNull(legionary);
        assertTrue(legionary instanceof Legionary);
    }

    @Test
    public void testCreatePrefect() {
        Character prefect = leader.createCharacter("prefect", "Karawita", "M", 1.80, 54, 62, 52);
        assertNotNull(prefect);
        assertTrue(prefect instanceof Prefect);
    }

    @Test
    public void testCreateGeneral() {
        Character general = leader.createCharacter("general", "Pompey", "M", 1.82, 45, 70, 65);
        assertNotNull(general);
        assertTrue(general instanceof General);
    }

    @Test
    public void testCreateLycanthrope() {
        Character lycanthrope = leader.createCharacter("lycanthrope", "Wolf", "M", 1.85, 35, 75, 70);
        assertNotNull(lycanthrope);
        assertTrue(lycanthrope instanceof Lycanthrope);
    }

    @Test
    public void testCreateCharacterWithInvalidType() {
        Character result = leader.createCharacter("invalid", "Test", "M", 1.70, 30, 50, 50);
        assertNull(result);
    }

    @Test
    public void testCreateCharacterWithoutManagedPlace() {
        ClanLeader noPlaceLeader = new ClanLeader("NoPlace", "M", 40);
        Character result = noPlaceLeader.createCharacter("merchant", "Test", "M", 1.70, 30, 50, 50);
        assertNull(result);
    }

    @Test
    public void testHealCharacter() {
        Character druid = leader.createCharacter("druid", "Panoramix", "M", 1.75, 60, 50, 60);
        druid.receiveDamage(40);

        leader.healCharacter(druid, 20);
        assertEquals(80, druid.getHealth());
    }

    @Test
    public void testHealCharacterNotInPlace() {
        Character outsider = new Druid("Outsider", "M", 1.75, 60, 50, 60);
        assertDoesNotThrow(() -> leader.healCharacter(outsider, 20));
    }

    @Test
    public void testHealCharacterWithoutManagedPlace() {
        ClanLeader noPlaceLeader = new ClanLeader("NoPlace", "M", 40);
        Character druid = new Druid("Test", "M", 1.75, 60, 50, 60);
        assertDoesNotThrow(() -> noPlaceLeader.healCharacter(druid, 20));
    }

    @Test
    public void testHealAllCharacters() {
        Character druid = leader.createCharacter("druid", "Panoramix", "M", 1.75, 60, 50, 60);
        Character merchant = leader.createCharacter("merchant", "Unhygienix", "M", 1.70, 45, 40, 50);

        druid.receiveDamage(30);
        merchant.receiveDamage(40);

        leader.healAllCharacters(20);

        assertEquals(90, druid.getHealth());
        assertEquals(80, merchant.getHealth());
    }

    @Test
    public void testHealAllCharactersWithoutManagedPlace() {
        ClanLeader noPlaceLeader = new ClanLeader("NoPlace", "M", 40);
        assertDoesNotThrow(() -> noPlaceLeader.healAllCharacters(20));
    }

    @Test
    public void testFeedCharacter() {
        Character druid = leader.createCharacter("druid", "Panoramix", "M", 1.75, 60, 50, 60);
        village.addFood(Foods.SANGLIER);

        assertDoesNotThrow(() -> leader.feedCharacter(druid));
    }

    @Test
    public void testFeedCharacterWithoutManagedPlace() {
        ClanLeader noPlaceLeader = new ClanLeader("NoPlace", "M", 40);
        Character druid = new Druid("Test", "M", 1.75, 60, 50, 60);

        assertDoesNotThrow(() -> noPlaceLeader.feedCharacter(druid));
    }

    @Test
    public void testFeedAllCharacters() {
        leader.createCharacter("druid", "Panoramix", "M", 1.75, 60, 50, 60);
        leader.createCharacter("merchant", "Unhygienix", "M", 1.70, 45, 40, 50);
        village.addFood(Foods.SANGLIER);
        village.addFood(Foods.POISSON_FRAIS);

        assertDoesNotThrow(() -> leader.feedAllCharacters());
    }

    @Test
    public void testFeedAllCharactersWithoutManagedPlace() {
        ClanLeader noPlaceLeader = new ClanLeader("NoPlace", "M", 40);
        assertDoesNotThrow(() -> noPlaceLeader.feedAllCharacters());
    }

    @Test
    public void testAskDruidForMagicPotion() {
        Druid druid = (Druid) leader.createCharacter("druid", "Panoramix", "M", 1.75, 60, 50, 60);
        assertDoesNotThrow(() -> leader.askDruidForMagicPotion(druid));
    }

    @Test
    public void testAskDruidForMagicPotionWithoutManagedPlace() {
        ClanLeader noPlaceLeader = new ClanLeader("NoPlace", "M", 40);
        Druid druid = new Druid("Test", "M", 1.75, 60, 50, 60);

        assertDoesNotThrow(() -> noPlaceLeader.askDruidForMagicPotion(druid));
    }

    @Test
    public void testAskDruidForMagicPotionWithNullDruid() {
        assertDoesNotThrow(() -> leader.askDruidForMagicPotion(null));
    }

    @Test
    public void testAskDruidForMagicPotionNotInPlace() {
        Druid outsider = new Druid("Outsider", "M", 1.75, 60, 50, 60);
        assertDoesNotThrow(() -> leader.askDruidForMagicPotion(outsider));
    }
}


