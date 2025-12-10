package fr.iut.laGaule.model.Character.MythicalCreature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Pack functionality including alpha couple management and reproduction.
 */
class PackTest {

    private Pack pack;
    private Lycanthrope alphaMale;
    private Lycanthrope alphaFemale;

    /**
     * Sets up the test environment before each test.
     * Creates a pack with an alpha couple.
     */
    @BeforeEach
    void setUp() {
        pack = new Pack("TestPack");

        // Create alpha male
        alphaMale = new Lycanthrope(
            "AlphaMale",
            "male",
            1.8,
            5,
            90,
            85,
            AgeCategory.ADULT,
            50,
            Rank.ALPHA,
            30,
                originGaul);

        // Create alpha female
        alphaFemale = new Lycanthrope(
            "AlphaFemale",
            "female",
            1.7,
            5,
            80,
            80,
            AgeCategory.ADULT,
            40,
            Rank.ALPHA,
            25,
                originGaul);

        pack.addMember(alphaMale);
        pack.addMember(alphaFemale);
        pack.setAlphaMale(alphaMale);
        pack.setAlphaFemale(alphaFemale);
    }

    @Test
    @DisplayName("Test that pack has alpha couple after setup")
    void testHasAlphaCouple() {
        assertTrue(pack.hasAlphaCouple(), "Pack should have a complete alpha couple");
    }

    @Test
    @DisplayName("Test alpha couple management with stable dominance")
    void testManageAlphaCoupleStable() {
        // Ensure alpha male has higher domination
        alphaMale.setDominationFactor(60);
        alphaFemale.setDominationFactor(40);

        pack.manageAlphaCouple();

        // Alpha couple should remain the same
        assertEquals(alphaMale, pack.getAlphaMale());
        assertEquals(alphaFemale, pack.getAlphaFemale());
    }

    @Test
    @DisplayName("Test alpha couple reformation when male loses dominance")
    void testAlphaCoupleReformation() {
        // Create another adult male with higher level
        Lycanthrope betaMale = new Lycanthrope(
            "BetaMale",
            "male",
            1.85,
            6,
            95,
            90,
            AgeCategory.ADULT,
            55,
            Rank.BETA,
            35,
                originGaul);
        pack.addMember(betaMale);

        // Make alpha male lose dominance
        alphaMale.setDominationFactor(30);
        alphaFemale.setDominationFactor(45);

        pack.manageAlphaCouple();

        // The couple should have been reformed
        assertTrue(pack.hasAlphaCouple());
    }

    @Test
    @DisplayName("Test reproduction initiation with valid alpha couple")
    void testReproductionInitiation() {
        boolean result = pack.initiateReproduction();

        assertTrue(result, "Reproduction should be initiated successfully");
        assertTrue(pack.hasPregnancies(), "Pack should have ongoing pregnancy");
    }

    @Test
    @DisplayName("Test reproduction fails without alpha couple")
    void testReproductionWithoutAlphaCouple() {
        Pack emptyPack = new Pack("EmptyPack");

        boolean result = emptyPack.initiateReproduction();

        assertFalse(result, "Reproduction should fail without alpha couple");
        assertFalse(emptyPack.hasPregnancies(), "Pack should have no pregnancies");
    }

    @Test
    @DisplayName("Test reproduction fails if female is already pregnant")
    void testReproductionWhilePregnant() {
        pack.initiateReproduction();
        boolean secondAttempt = pack.initiateReproduction();

        assertFalse(secondAttempt, "Second reproduction attempt should fail while pregnant");
    }

    @Test
    @DisplayName("Test reproduction fails with young lycanthropes")
    void testReproductionWithYoungLycanthropes() {
        Lycanthrope youngMale = new Lycanthrope(
            "YoungMale",
            "male",
            1.5,
            1,
            50,
            50,
            AgeCategory.YOUNG,
            10,
            Rank.ALPHA,
            20,
                originGaul);

        Pack youngPack = new Pack("YoungPack");
        youngPack.addMember(youngMale);
        youngPack.addMember(alphaFemale);
        youngPack.setAlphaMale(youngMale);
        youngPack.setAlphaFemale(alphaFemale);

        boolean result = youngPack.initiateReproduction();

        assertFalse(result, "Reproduction should fail with young alpha male");
    }

    @Test
    @DisplayName("Test pregnancy processing decrements gestation time")
    void testPregnancyProcessing() {
        pack.initiateReproduction();

        int initialSize = pack.getSize();

        // Process pregnancy multiple times
        for (int i = 0; i < 10; i++) {
            pack.processPregnancies();
        }

        // After enough turns, birth should have occurred
        assertTrue(pack.getSize() > initialSize, "Pack size should increase after birth");
        assertFalse(pack.hasPregnancies(), "Pregnancy should be completed");
    }

    @Test
    @DisplayName("Test that pups are added to pack after birth")
    void testPupsAddedToPack() {
        int initialSize = pack.getSize();

        pack.initiateReproduction();

        // Process pregnancy until birth
        for (int i = 0; i < 10; i++) {
            pack.processPregnancies();
        }

        int finalSize = pack.getSize();

        assertTrue(finalSize > initialSize, "Pack should have more members after birth");
        assertTrue(finalSize >= initialSize + 1 && finalSize <= initialSize + 7,
            "Should have 1-7 new pups");
    }

    @Test
    @DisplayName("Test that pups have YOUNG age category and OMEGA rank")
    void testPupCharacteristics() {
        pack.initiateReproduction();

        // Process pregnancy until birth
        for (int i = 0; i < 10; i++) {
            pack.processPregnancies();
        }

        // Check that new pups have correct characteristics
        long youngCount = pack.getMembers().stream()
            .filter(l -> l.getAgeCategory() == AgeCategory.YOUNG)
            .count();

        assertTrue(youngCount > 0, "Should have young lycanthropes after birth");

        long omegaCount = pack.getMembers().stream()
            .filter(l -> l.getHierarchyRank() == Rank.OMEGA)
            .count();

        assertTrue(omegaCount > 0, "Pups should have OMEGA rank");
    }

    @Test
    @DisplayName("Test adding and removing members from pack")
    void testAddRemoveMember() {
        Lycanthrope newMember = new Lycanthrope(
            "NewMember",
            "male",
            1.75,
            4,
            70,
            70,
            AgeCategory.ADULT,
            20,
            Rank.BETA,
            25,
                originGaul);

        int initialSize = pack.getSize();
        pack.addMember(newMember);

        assertEquals(initialSize + 1, pack.getSize(), "Pack size should increase by 1");
        assertFalse(newMember.isSolitary(), "New member should not be solitary");

        pack.removeMember(newMember);

        assertEquals(initialSize, pack.getSize(), "Pack size should return to initial");
        assertTrue(newMember.isSolitary(), "Removed member should be solitary");
    }

    @Test
    @DisplayName("Test pack hierarchy display")
    void testDisplayHierarchy() {
        // This test just ensures the method doesn't throw exceptions
        assertDoesNotThrow(() -> pack.displayHierarchy());
    }

    @Test
    @DisplayName("Test getting members by rank")
    void testGetMembersByRank() {
        var alphaMembers = pack.getMembersByRank(Rank.ALPHA);

        assertEquals(2, alphaMembers.size(), "Should have 2 alpha members");
    }

    @Test
    @DisplayName("Test pack dynamics simulation")
    void testSimulateHierarchyDynamics() {
        // Add some omega members
        for (int i = 0; i < 3; i++) {
            Lycanthrope omega = new Lycanthrope(
                "Omega" + i,
                i % 2 == 0 ? "male" : "female",
                1.6,
                3,
                40,
                40,
                AgeCategory.ADULT,
                -10,
                Rank.OMEGA,
                15,
                    originGaul);
            pack.addMember(omega);
        }

        // This should not throw exceptions
        assertDoesNotThrow(() -> pack.simulateHierarchyDynamics());
    }
}

