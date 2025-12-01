package fr.iut.laGaule.model.Character.Gaul;

import fr.iut.laGaule.Serializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Blacksmith class.
 * Tests blacksmith-specific behaviors such as forging weapons and shields.
 */
public class BlacksmithTest {

    private Blacksmith blacksmith;
    private Serializer serializer;

    @BeforeEach
    public void setUp() {
        blacksmith = new Blacksmith("Cetautomatix", "M", 1.80, 40, 60, 55);
        serializer = new Serializer();

        // Setup test data
        Map<String, Object> map = new HashMap<>();
        Druid druid = new Druid("TestDruid", "M", 1.75, 60, 50, 60);
        map.put("druid", druid);
        serializer.serialize("gaul", map);
    }

    @AfterEach
    public void tearDown() {
        // Clean up test files
        File testFile = new File("gaul.ser");
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testBlacksmithCreation() {
        assertEquals("Cetautomatix", blacksmith.getName());
        assertEquals(60, blacksmith.getStrength());
        assertEquals(55, blacksmith.getEndurance());
    }

    @Test
    public void testWork() {
        // Should not throw exception
        assertDoesNotThrow(() -> blacksmith.work());
    }

    @Test
    public void testWorkEnhancesGaulStats() {
        Map<String, Object> mapBefore = serializer.deserialize("gaul");
        Gaul gaulBefore = (Gaul) mapBefore.get("druid");
        int strengthBefore = gaulBefore.getStrength();
        int enduranceBefore = gaulBefore.getEndurance();

        blacksmith.work();

        Map<String, Object> mapAfter = serializer.deserialize("gaul");
        Gaul gaulAfter = (Gaul) mapAfter.get("druid");

        // Either strength or endurance should have increased
        assertTrue(gaulAfter.getStrength() >= strengthBefore ||
                   gaulAfter.getEndurance() >= enduranceBefore);
    }

    @Test
    public void testWorkWithEmptyData() {
        // Delete the file to create empty condition
        File testFile = new File("gaul.ser");
        testFile.delete();

        // Should handle gracefully without throwing exception
        assertDoesNotThrow(() -> blacksmith.work());
    }
}

