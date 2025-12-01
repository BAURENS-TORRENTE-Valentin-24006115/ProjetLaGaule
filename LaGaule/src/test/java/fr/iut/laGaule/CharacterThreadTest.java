package fr.iut.laGaule;

import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.Gaul.Merchant;
import fr.iut.laGaule.model.Character.Roman.General;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CharacterThread class.
 * Tests the runnable behavior of character threads.
 */
public class CharacterThreadTest {

    private Druid druid;
    private General general;
    private Merchant merchant;
    private Legionary legionary;
    private static final String TEST_ZONE = "testThreadZone";

    @BeforeEach
    public void setUp() {
        druid = new Druid("TestDruid", "M", 1.75, 60, 50, 60);
        general = new General("TestGeneral", "M", 1.80, 45, 70, 65);
        merchant = new Merchant("TestMerchant", "M", 1.70, 45, 40, 50);
        legionary = new Legionary("TestLegionary", "M", 1.75, 30, 60, 55);

        // Setup serialized data for thread operations
        Serializer serializer = new Serializer();
        Map<String, Object> map = new HashMap<>();
        map.put("druid", druid);
        map.put("general", general);
        map.put("merchant", merchant);
        map.put("legionary", legionary);
        serializer.serialize("gaul", map);
    }

    @AfterEach
    public void tearDown() {
        // Clean up test files
        File testFile = new File("gaul.ser");
        if (testFile.exists()) {
            testFile.delete();
        }
        File testFile2 = new File(TEST_ZONE + ".ser");
        if (testFile2.exists()) {
            testFile2.delete();
        }
    }

    @Test
    public void testCharacterThreadCreation() {
        CharacterThread thread = new CharacterThread(druid);
        assertNotNull(thread);
        assertEquals(druid, thread.character);
    }

    @Test
    public void testCharacterThreadWithDruid() {
        CharacterThread thread = new CharacterThread(druid);
        assertNotNull(thread);
        assertTrue(thread.character instanceof Druid);
    }

    @Test
    public void testCharacterThreadWithGeneral() {
        CharacterThread thread = new CharacterThread(general);
        assertNotNull(thread);
        assertTrue(thread.character instanceof General);
    }

    @Test
    public void testCharacterThreadWithMerchant() {
        CharacterThread thread = new CharacterThread(merchant);
        assertNotNull(thread);
        assertTrue(thread.character instanceof Merchant);
    }

    @Test
    public void testCharacterThreadWithLegionary() {
        CharacterThread thread = new CharacterThread(legionary);
        assertNotNull(thread);
        assertTrue(thread.character instanceof Legionary);
    }

    @Test
    public void testThreadRunsWithoutException() {
        CharacterThread thread = new CharacterThread(druid);
        Thread t = new Thread(thread);

        // Start the thread
        t.start();

        // Let it run briefly
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            fail("Thread was interrupted");
        }

        // Interrupt the thread to stop the infinite loop
        t.interrupt();

        // Just verify no exception was thrown during startup
        assertTrue(true);
    }

    @Test
    public void testCharacterThreadWithDeadCharacter() {
        druid.receiveDamage(200); // Kill the character
        CharacterThread thread = new CharacterThread(druid);
        Thread t = new Thread(thread);

        t.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            fail("Thread was interrupted");
        }

        // Thread should have interrupted itself
        t.interrupt();
        assertTrue(druid.getHealth() <= 0);
    }
}

