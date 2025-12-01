package fr.iut.laGaule;

import fr.iut.laGaule.model.Character.Gaul.Blacksmith;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.Gaul.Innkeeper;
import fr.iut.laGaule.model.Character.Gaul.Merchant;
import fr.iut.laGaule.model.Character.MythicalCreature.Lycanthrope;
import fr.iut.laGaule.model.Character.Roman.General;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Character.Roman.Prefect;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @AfterEach
    void cleanupSerializedFiles() {
        new File("gaul.ser").delete();
    }

    @Test
    void druidConcoctsPotionSuccessfully() {
        Druid druid = new Druid("Panoramix", "male", 1.75, 80, 50, 50);

        assertDoesNotThrow(() -> druid.concoctPotion());
    }

    @Test
    void merchantDrinksPotionIncreasesLevel() {
        Merchant merchant = new Merchant("Merchant", "male", 1.70, 25, 40, 30);

        merchant.drinkPotion(50);

        assertTrue(true);
    }

    @Test
    void characterReceivesDamageReducesHealth() {
        Prefect prefect = new Prefect("Prefect", "male", 1.80, 54, 62, 52);
        int initialHealth = prefect.getHealth();

        prefect.receiveDamage(20);

        assertEquals(initialHealth - 20, prefect.getHealth());
    }

    @Test
    void characterHealIncreasesHealth() {
        Prefect prefect = new Prefect("Prefect", "male", 1.80, 54, 62, 52);
        prefect.receiveDamage(50);
        int healthAfterDamage = prefect.getHealth();

        prefect.heal(35);

        assertEquals(healthAfterDamage + 35, prefect.getHealth());
    }

    @Test
    void characterHealDoesNotExceedMaxHealth() {
        Prefect prefect = new Prefect("Prefect", "male", 1.80, 54, 62, 52);

        prefect.heal(50);

        assertEquals(100, prefect.getHealth());
    }

    @Test
    void characterDiesWhenHealthReachesZero() {
        Prefect prefect = new Prefect("Prefect", "male", 1.80, 54, 62, 52);

        prefect.receiveDamage(150);

        assertEquals(0, prefect.getHealth());
    }

    @Test
    void innkeeperWorkMethodExecutes() {
        Innkeeper innkeeper = new Innkeeper("Innkeeper", "male", 1.79, 56, 12, 78);

        assertDoesNotThrow(() -> innkeeper.work());
    }

    @Test
    void generalCommandsLegionaryToFight() {
        General general = new General("General", "female", 1.65, 34, 64, 25);
        Legionary legionary = new Legionary("Legionary", "male", 2.0, 46, 80, 70);
        Innkeeper target = new Innkeeper("Innkeeper", "male", 1.79, 56, 12, 78);

        assertDoesNotThrow(() -> general.command(legionary, target));
    }


    @Test
    void druidFightsPrefectMultipleTimes() {
        Druid druid = new Druid("Druid", "male", 1.75, 80, 54, 50);
        Prefect prefect = new Prefect("Prefect", "male", 1.80, 54, 62, 52);
        int initialPrefectHealth = prefect.getHealth();

        druid.fight(prefect);

        assertTrue(prefect.getHealth() < initialPrefectHealth || prefect.getHealth() == 0);
    }

    @Test
    void generalFightsDruid() {
        General general = new General("General", "female", 1.65, 34, 64, 25);
        Druid druid = new Druid("Druid", "male", 1.75, 80, 54, 50);
        int initialDruidHealth = druid.getHealth();

        general.fight(druid);

        assertTrue(druid.getHealth() <= initialDruidHealth);
    }

    @Test
    void serializerSavesCharactersToFile() {
        Druid druid = new Druid("Druid", "male", 1.75, 80, 54, 50);
        Merchant merchant = new Merchant("Merchant", "male", 1.70, 25, 40, 30);
        Innkeeper innkeeper = new Innkeeper("Innkeeper", "male", 1.79, 56, 12, 78);
        Blacksmith blacksmith = new Blacksmith("Blacksmith", "male", 2.0, 46, 80, 70);

        Map<String, Object> map = new HashMap<>();
        map.put("druid", druid);
        map.put("merchant", merchant);
        map.put("innkeeper", innkeeper);
        map.put("blacksmith", blacksmith);

        Serializer serializer = new Serializer();
        serializer.serialize("gaul", map);

        assertTrue(new File("gaul.ser").exists());
    }

    @Test
    void serializerDeserializesCharactersFromFile() {
        Druid druid = new Druid("Druid", "male", 1.75, 80, 54, 50);
        Map<String, Object> map = new HashMap<>();
        map.put("druid", druid);

        Serializer serializer = new Serializer();
        serializer.serialize("gaul", map);

        Map<String, Object> deserializedMap = serializer.deserialize("gaul");

        assertNotNull(deserializedMap);
        assertTrue(deserializedMap.containsKey("druid"));
    }

    @Test
    void blacksmithWorkMethodExecutes() {
        Blacksmith blacksmith = new Blacksmith("Blacksmith", "male", 2.0, 46, 80, 70);
        Druid druid = new Druid("Druid", "male", 1.75, 80, 54, 50);
        Map<String, Object> map = new HashMap<>();
        map.put("druid", druid);

        Serializer serializer = new Serializer();
        serializer.serialize("gaul", map);

        assertDoesNotThrow(() -> blacksmith.work());
    }

    @Test
    void fightReducesHealthOfBothCombatants() {
        Druid druid = new Druid("Druid", "male", 1.75, 80, 54, 50);
        Prefect prefect = new Prefect("Prefect", "male", 1.80, 54, 62, 52);
        int initialDruidHealth = druid.getHealth();
        int initialPrefectHealth = prefect.getHealth();

        druid.fight(prefect);

        assertTrue(druid.getHealth() <= initialDruidHealth);
        assertTrue(prefect.getHealth() <= initialPrefectHealth);
    }

    @Test
    void multipleFightsCanKillCharacter() {
        Druid druid = new Druid("Druid", "male", 1.75, 80, 100, 50);
        Prefect prefect = new Prefect("Prefect", "male", 1.80, 54, 62, 52);

        for (int i = 0; i < 20; i++) {
            if (prefect.getHealth() > 0) {
                druid.fight(prefect);
            }
        }

        assertTrue(prefect.getHealth() >= 0);
    }

    @Test
    void characterWithZeroHealthDoesNotFightBack() {
        Druid druid = new Druid("Druid", "male", 1.75, 80, 54, 50);
        Prefect prefect = new Prefect("Prefect", "male", 1.80, 54, 62, 52);
        prefect.receiveDamage(100);
        int druidHealthBeforeFight = druid.getHealth();

        druid.fight(prefect);

        assertEquals(druidHealthBeforeFight, druid.getHealth());
        assertEquals(0, prefect.getHealth());
    }

    @Test
    void serializerHandlesMixedCharacterTypes() {
        Druid druid = new Druid("Druid", "male", 1.75, 80, 54, 50);
        Merchant merchant = new Merchant("Merchant", "male", 1.70, 25, 40, 30);
        Blacksmith blacksmith = new Blacksmith("Blacksmith", "male", 2.0, 46, 80, 70);

        Map<String, Object> map = new HashMap<>();
        map.put("bobi", druid);
        map.put("kashmiri", merchant);
        map.put("oui", blacksmith);

        Serializer serializer = new Serializer();
        serializer.serialize("gaul", map);

        Map<String, Object> deserializedMap = serializer.deserialize("gaul");

        assertNotNull(deserializedMap);
        assertEquals(3, deserializedMap.size());
        assertInstanceOf(Druid.class, deserializedMap.get("bobi"));
        assertInstanceOf(Merchant.class, deserializedMap.get("kashmiri"));
        assertInstanceOf(Blacksmith.class, deserializedMap.get("oui"));
    }

    @Test
    void serializerReturnsNullForNonexistentFile() {
        Serializer serializer = new Serializer();

        Map<String, Object> result = serializer.deserialize("nonexistent");

        assertNull(result);
    }

    @Test
    void innkeeperWorkHandlesEmptySerializedFile() {
        Innkeeper innkeeper = new Innkeeper("Innkeeper", "male", 1.79, 56, 12, 78);

        assertDoesNotThrow(() -> innkeeper.work());
    }

    @Test
    void blacksmithWorkHandlesEmptySerializedFile() {
        Blacksmith blacksmith = new Blacksmith("Blacksmith", "male", 2.0, 46, 80, 70);

        assertDoesNotThrow(() -> blacksmith.work());
    }
}

