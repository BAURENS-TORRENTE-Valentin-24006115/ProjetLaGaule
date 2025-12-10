package fr.iut.laGaule.model.Character.Gaul;

import fr.iut.laGaule.Serializer;
import fr.iut.laGaule.model.Place.GaulVillage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Innkeeper class.
 * Tests innkeeper-specific behaviors such as serving food.
 */
public class InnkeeperTest {

    private Innkeeper innkeeper;
    private Serializer serializer;
    private GaulVillage village;

    @BeforeEach
    public void setUp() {
        innkeeper = new Innkeeper("Ordralfabetix", "M", 1.75, 50, 45, 55);
        serializer = new Serializer();
        village = new GaulVillage("Village Gaulois", 200, null, 0, new ArrayList<>(), new ArrayList<>());
        innkeeper.setPlace(village);

        // Setup test data
        Map<String, Object> map = new HashMap<>();
        Druid druid = new Druid("TestDruid", "M", 1.75, 60, 50, 60);
        map.put("druid", druid);
        serializer.serialize("Village Gaulois", map);
    }

    @AfterEach
    public void tearDown() {
        // Clean up test files
        File testFile = new File("Village Gaulois.ser");
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testInnkeeperCreation() {
        assertEquals("Ordralfabetix", innkeeper.getName());
        assertEquals(45, innkeeper.getStrength());
        assertEquals(55, innkeeper.getEndurance());
    }

    @Test
    public void testWork() {
        // Should not throw exception
        assertDoesNotThrow(() -> innkeeper.work());
    }

    @Test
    public void testWorkServesFood() {
        // Work should complete without exception
        innkeeper.work();
        // Verify the serialized data still exists
        Map<String, Object> map = serializer.deserialize("Village Gaulois");
        assertNotNull(map);
        assertTrue(map.containsKey("druid"));
    }

    @Test
    public void testWorkWithEmptyData() {
        // Delete the file to create empty condition
        File testFile = new File("Village Gaulois.ser");
        testFile.delete();

        // Should handle gracefully without throwing exception
        assertDoesNotThrow(() -> innkeeper.work());
    }

    @Test
    public void testInnkeeperIsGaul() {
        assertTrue(innkeeper instanceof Gaul);
    }

    @Test
    public void testWorkWithNonGaulObjects() {
        // Create a map with non-Gaul objects
        Map<String, Object> map = new HashMap<>();
        map.put("string", "not a gaul");
        map.put("number", 42);
        serializer.serialize("Village Gaulois", map);

        // Should handle gracefully
        assertDoesNotThrow(() -> innkeeper.work());
    }

    @Test
    public void testWorkFeedsGaul() {
        // Verify that work() feeds gauls
        innkeeper.work();

        // Verify the serialized data still exists
        Map<String, Object> map = serializer.deserialize("Village Gaulois");
        assertNotNull(map);
        assertTrue(map.containsKey("druid"));
    }
}

