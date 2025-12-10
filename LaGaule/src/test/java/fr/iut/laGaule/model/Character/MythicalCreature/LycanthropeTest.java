package fr.iut.laGaule.model.Character.MythicalCreature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Lycanthrope class.
 * Tests lycanthrope character creation and basic functionality.
 */
public class LycanthropeTest {

    private Lycanthrope lycanthrope;
    private Lycanthrope alphaMale;
    private Lycanthrope betaFemale;
    private Pack pack;

    @BeforeEach
    public void setUp() {
        lycanthrope = new Lycanthrope("Fenrir", "M", 1.90, 35, 80, 75,
                AgeCategory.ADULT, 5, Rank.BETA, 50, true);

        alphaMale = new Lycanthrope("Alpha", "male", 1.95, 40, 90, 85,
                AgeCategory.ADULT, 20, Rank.ALPHA, 60, true);

        betaFemale = new Lycanthrope("Beta", "female", 1.75, 30, 70, 70,
                AgeCategory.ADULT, 10, Rank.BETA, 40, true);

        pack = new Pack("TestPack");
    }

    @Test
    public void testLycanthropeCreation() {
        assertEquals("Fenrir", lycanthrope.getName());
        assertEquals(80, lycanthrope.getStrength());
        assertEquals(75, lycanthrope.getEndurance());
    }

    @Test
    public void testLycanthropeProperties() {
        assertNull(lycanthrope.getPlaceData(), "La place devrait être null par défaut");
    }

    @Test
    public void testLycanthropeHealthManagement() {
        lycanthrope.receiveDamage(50);
        assertEquals(50, lycanthrope.getHealth());

        lycanthrope.heal(30);
        assertEquals(80, lycanthrope.getHealth());
    }

    @Test
    public void testLycanthropeStrength() {
        lycanthrope.setStrength(90);
        assertEquals(90, lycanthrope.getStrength());
    }

    @Test
    public void testLycanthropeEndurance() {
        lycanthrope.setEndurance(85);
        assertEquals(85, lycanthrope.getEndurance());
    }

    @Test
    public void testIsSolitaryInitially() {
        assertTrue(lycanthrope.isSolitary());
    }

    @Test
    public void testJoinPack() {
        lycanthrope.joinPack(pack);
        assertFalse(lycanthrope.isSolitary());
        assertEquals(pack, lycanthrope.getPack());
    }

    @Test
    public void testLeavePack() {
        lycanthrope.joinPack(pack);
        lycanthrope.leavePack();
        assertTrue(lycanthrope.isSolitary());
        assertNull(lycanthrope.getPack());
    }

    @Test
    public void testBecomeSolitary() {
        lycanthrope.joinPack(pack);
        lycanthrope.becomeSolitary();
        assertTrue(lycanthrope.isSolitary());
        assertEquals(0, lycanthrope.getDominationFactor());
    }

    @Test
    public void testDisplayCharacteristics() {
        assertDoesNotThrow(() -> lycanthrope.displayCharacteristics());
    }

    @Test
    public void testHowlPackAffiliation() {
        pack.addMember(lycanthrope);
        Howl howl = lycanthrope.howlPackAffiliation();
        assertNotNull(howl);
        assertEquals(HowlType.PACK_AFFILIATION, howl.getHowlType());
    }

    @Test
    public void testHowlDomination() {
        Howl howl = lycanthrope.howlDomination();
        assertNotNull(howl);
        assertEquals(HowlType.DOMINATION, howl.getHowlType());
    }

    @Test
    public void testHowlSubmission() {
        Howl howl = lycanthrope.howlSubmission();
        assertNotNull(howl);
        assertEquals(HowlType.SUBMISSION, howl.getHowlType());
    }

    @Test
    public void testHowlAggression() {
        Howl howl = lycanthrope.howlAggression();
        assertNotNull(howl);
        assertEquals(HowlType.AGGRESSION, howl.getHowlType());
    }

    @Test
    public void testListenToHowl() {
        Howl howl = new Howl(alphaMale, HowlType.DOMINATION);
        boolean result = lycanthrope.listenToHowl(howl);
        assertTrue(result);
    }

    @Test
    public void testListenToHowlWhenWeak() {
        lycanthrope.receiveDamage(80); // Health at 20
        Howl howl = new Howl(alphaMale, HowlType.DOMINATION);
        boolean result = lycanthrope.listenToHowl(howl);
        assertFalse(result);
    }

    @Test
    public void testGetAgeCategory() {
        assertEquals(AgeCategory.ADULT, lycanthrope.getAgeCategory());
    }

    @Test
    public void testGetDominationFactor() {
        // Solitary lycanthropes have domination factor 0
        assertEquals(0, lycanthrope.getDominationFactor());
    }

    @Test
    public void testGetLevel() {
        assertTrue(lycanthrope.getLevel() > 0);
    }

    @Test
    public void testGetImpetuosityFactor() {
        assertEquals(50, lycanthrope.getImpetuosityFactor());
    }

    @Test
    public void testUpdateLevel() {
        int initialLevel = lycanthrope.getLevel();
        lycanthrope.setStrength(100);
        lycanthrope.updateLevel();
        // Level should change after strength change
        assertNotEquals(initialLevel, lycanthrope.getLevel());
    }

    @Test
    public void testTransformToHuman() {
        // This method has random behavior, just ensure it doesn't throw
        assertDoesNotThrow(() -> lycanthrope.transformToHuman());
    }

    @Test
    public void testSubmit() {
        pack.addMember(lycanthrope);
        int initialDominationFactor = lycanthrope.getDominationFactor();
        lycanthrope.submit();
        assertEquals(initialDominationFactor - 1, lycanthrope.getDominationFactor());
    }

    @Test
    public void testAttemptDominationWithNullTarget() {
        boolean result = lycanthrope.attemptDomination(null);
        assertFalse(result);
    }

    @Test
    public void testYoungLycanthrope() {
        Lycanthrope young = new Lycanthrope("Young", "M", 1.50, 5, 40, 35,
                AgeCategory.YOUNG, 0, Rank.OMEGA, 30, true);
        assertEquals(AgeCategory.YOUNG, young.getAgeCategory());
    }

    @Test
    public void testOldLycanthrope() {
        Lycanthrope old = new Lycanthrope("Elder", "M", 1.80, 80, 60, 50,
                AgeCategory.OLD, 15, Rank.ALPHA, 20, true);
        assertEquals(AgeCategory.OLD, old.getAgeCategory());
    }

    @Test
    public void testSetHierarchyRank() {
        pack.addMember(lycanthrope);
        lycanthrope.setHierarchyRank(Rank.GAMMA);
        assertEquals(Rank.GAMMA, lycanthrope.getHierarchyRank());
    }

    @Test
    public void testSetDominationFactor() {
        pack.addMember(lycanthrope);
        lycanthrope.setDominationFactor(15);
        assertEquals(15, lycanthrope.getDominationFactor());
    }

    @Test
    public void testSetPack() {
        lycanthrope.setPack(pack);
        assertEquals(pack, lycanthrope.getPack());
    }

    @Test
    public void testSetSolitary() {
        lycanthrope.setSolitary(false);
        assertFalse(lycanthrope.isSolitary());
    }

    @Test
    public void testDominateWithSamePackMembers() {
        pack.addMember(alphaMale);
        pack.addMember(betaFemale);

        // Both are in the same pack
        assertDoesNotThrow(() -> alphaMale.dominate(betaFemale));
    }

    @Test
    public void testDominateSelf() {
        boolean result = lycanthrope.dominate(lycanthrope);
        assertFalse(result);
    }

    @Test
    public void testDominateBetweenSolitaryLycanthropes() {
        // Both are solitary
        boolean result = lycanthrope.dominate(alphaMale);
        assertFalse(result);
    }
}
