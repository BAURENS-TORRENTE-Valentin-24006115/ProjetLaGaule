package fr.iut.laGaule;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.Gaul.Gaul;
import fr.iut.laGaule.model.Character.Gaul.Merchant;
import fr.iut.laGaule.model.Character.Roman.General;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Character.Roman.Roman;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Serializer class.
 * Tests serialization and deserialization of game characters.
 */
public class SerializerTest {

    private Serializer serializer;
    private static final String TEST_ZONE = "testZone";

    @BeforeEach
    public void setUp() {
        serializer = new Serializer();
    }

    @AfterEach
    public void tearDown() {
        // Clean up test files
        File testFile = new File(TEST_ZONE + ".ser");
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testSerializeAndDeserialize() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        Map<String, Object> map = new HashMap<>();
        map.put("druid", druid);

        serializer.serialize(TEST_ZONE, map);
        Map<String, Object> deserializedMap = serializer.deserialize(TEST_ZONE);

        assertNotNull(deserializedMap);
        assertTrue(deserializedMap.containsKey("druid"));
        assertEquals(druid.getName(), ((Druid) deserializedMap.get("druid")).getName());
    }

    @Test
    public void testDeserializeNonExistentFile() {
        Map<String, Object> result = serializer.deserialize("nonExistent");
        assertNull(result);
    }

    @Test
    public void testDeserializeRandomCharacter() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        General general = new General("Pompey", "M", 1.80, 45, 70, 65);

        Map<String, Object> map = new HashMap<>();
        map.put("druid", druid);
        map.put("general", general);

        serializer.serialize(TEST_ZONE, map);
        Character randomChar = serializer.deserializeRandomCharacter(TEST_ZONE);

        assertNotNull(randomChar);
        assertTrue(randomChar instanceof Character);
    }

    @Test
    public void testDeserializeRandomGaul() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        Merchant merchant = new Merchant("Unhygienix", "M", 1.70, 45, 40, 50);
        General general = new General("Pompey", "M", 1.80, 45, 70, 65);

        Map<String, Object> map = new HashMap<>();
        map.put("druid", druid);
        map.put("merchant", merchant);
        map.put("general", general);

        serializer.serialize(TEST_ZONE, map);
        Gaul randomGaul = serializer.deserializeRandomGaul(TEST_ZONE);

        assertNotNull(randomGaul);
        assertTrue(randomGaul instanceof Gaul);
    }

    @Test
    public void testDeserializeRandomRoman() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);
        General general = new General("Pompey", "M", 1.80, 45, 70, 65);
        Legionary legionary = new Legionary("Brutus", "M", 1.75, 30, 60, 55);

        Map<String, Object> map = new HashMap<>();
        map.put("druid", druid);
        map.put("general", general);
        map.put("legionary", legionary);

        serializer.serialize(TEST_ZONE, map);
        Roman randomRoman = serializer.deserializeRandomRoman(TEST_ZONE);

        assertNotNull(randomRoman);
        assertTrue(randomRoman instanceof Roman);
    }

    @Test
    public void testDeserializeRandomLegionary() {
        Legionary legionary1 = new Legionary("Brutus", "M", 1.75, 30, 60, 55);
        Legionary legionary2 = new Legionary("Cesar", "M", 1.78, 28, 65, 60);

        Map<String, Object> map = new HashMap<>();
        map.put("leg1", legionary1);
        map.put("leg2", legionary2);

        serializer.serialize(TEST_ZONE, map);
        Legionary randomLegionary = serializer.deserializeRandomLegionary(TEST_ZONE);

        assertNotNull(randomLegionary);
        assertTrue(randomLegionary instanceof Legionary);
    }

    @Test
    public void testDeserializeRandomGaulFromEmptyMap() {
        General general = new General("Pompey", "M", 1.80, 45, 70, 65);

        Map<String, Object> map = new HashMap<>();
        map.put("general", general);

        serializer.serialize(TEST_ZONE, map);
        Gaul randomGaul = serializer.deserializeRandomGaul(TEST_ZONE);

        assertNull(randomGaul);
    }

    @Test
    public void testDeserializeRandomRomanFromEmptyMap() {
        Druid druid = new Druid("Panoramix", "M", 1.75, 60, 50, 60);

        Map<String, Object> map = new HashMap<>();
        map.put("druid", druid);

        serializer.serialize(TEST_ZONE, map);
        Roman randomRoman = serializer.deserializeRandomRoman(TEST_ZONE);

        assertNull(randomRoman);
    }

    @Test
    public void testSerializeMultipleCharacters() {
        Map<String, Object> map = new HashMap<>();
        map.put("char1", new Druid("Druid1", "M", 1.75, 60, 50, 60));
        map.put("char2", new General("General1", "M", 1.80, 45, 70, 65));
        map.put("char3", new Merchant("Merchant1", "M", 1.70, 45, 40, 50));

        serializer.serialize(TEST_ZONE, map);
        Map<String, Object> deserializedMap = serializer.deserialize(TEST_ZONE);

        assertNotNull(deserializedMap);
        assertEquals(3, deserializedMap.size());
    }
}

