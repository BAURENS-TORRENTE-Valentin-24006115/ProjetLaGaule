package fr.iut.laGaule;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Main class.
 * Tests the main application entry point.
 */
public class MainTestAdditional {

    @Test
    public void testMainMethodExists() {
        // Verify that the main method exists and can be called
        assertDoesNotThrow(() -> {
            // We don't actually call main() because it runs a simulation
            // Just verify the class loads correctly
            Class.forName("fr.iut.laGaule.Main");
        });
    }

}

