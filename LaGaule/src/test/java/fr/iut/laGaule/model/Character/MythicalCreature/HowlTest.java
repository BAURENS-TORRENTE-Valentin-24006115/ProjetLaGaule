package fr.iut.laGaule.model.Character.MythicalCreature;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Howl system in Lycanthropes.
 */
public class HowlTest {

    @Test
    public void testHowlCreation() {
        // Create a simple lycanthrope
        Lycanthrope lycan = new Lycanthrope(
                "Wolf", "male", 1.8, 25, 15, 14,
                AgeCategory.ADULT, 5, Rank.ALPHA, 8
        );

        // Create a howl
        Howl howl = new Howl(lycan, HowlType.DOMINATION);

        // Verify howl properties
        assertEquals(lycan, howl.getEmitter());
        assertEquals(HowlType.DOMINATION, howl.getHowlType());
        assertNotEquals(0, howl.getTimestamp());
    }

    @Test
    public void testPackAffiliationHowl() {
        // Create a pack
        Pack pack = new Pack("Wolf Pack");

        // Create pack members
        Lycanthrope alphaMale = new Lycanthrope(
                "Alpha", "male", 1.9, 30, 18, 16,
                AgeCategory.ADULT, 10, Rank.ALPHA, 10
        );

        Lycanthrope betaMember = new Lycanthrope(
                "Beta", "male", 1.8, 25, 15, 14,
                AgeCategory.ADULT, 5, Rank.BETA, 8
        );

        // Add members to pack
        pack.addMember(alphaMale);
        pack.addMember(betaMember);

        // Test pack affiliation howl
        Howl howl = alphaMale.howlPackAffiliation();

        assertNotNull(howl);
        assertEquals(alphaMale, howl.getEmitter());
        assertEquals(HowlType.PACK_AFFILIATION, howl.getHowlType());
    }

    @Test
    public void testDominationHowl() {
        Lycanthrope lycan = new Lycanthrope(
                "Dominant", "female", 1.7, 28, 16, 15,
                AgeCategory.ADULT, 8, Rank.ALPHA, 9
        );

        Howl howl = lycan.howlDomination();

        assertNotNull(howl);
        assertEquals(lycan, howl.getEmitter());
        assertEquals(HowlType.DOMINATION, howl.getHowlType());
    }

    @Test
    public void testSubmissionHowl() {
        Lycanthrope lycan = new Lycanthrope(
                "Submissive", "male", 1.6, 22, 12, 13,
                AgeCategory.YOUNG, 2, Rank.OMEGA, 5
        );

        Howl howl = lycan.howlSubmission();

        assertNotNull(howl);
        assertEquals(lycan, howl.getEmitter());
        assertEquals(HowlType.SUBMISSION, howl.getHowlType());
    }

    @Test
    public void testAggressionHowl() {
        Lycanthrope lycan = new Lycanthrope(
                "Aggressive", "male", 1.75, 26, 17, 15,
                AgeCategory.ADULT, 7, Rank.GAMMA, 12
        );

        Howl howl = lycan.howlAggression();

        assertNotNull(howl);
        assertEquals(lycan, howl.getEmitter());
        assertEquals(HowlType.AGGRESSION, howl.getHowlType());
    }

    @Test
    public void testListenToHowl() {
        Lycanthrope emitter = new Lycanthrope(
                "Emitter", "male", 1.8, 25, 15, 14,
                AgeCategory.ADULT, 5, Rank.BETA, 8
        );

        Lycanthrope listener = new Lycanthrope(
                "Listener", "female", 1.7, 24, 14, 13,
                AgeCategory.ADULT, 4, Rank.GAMMA, 7
        );

        Howl howl = new Howl(emitter, HowlType.DOMINATION);

        // Listener should hear the howl
        boolean heard = listener.listenToHowl(howl);
        assertTrue(heard);

        // Test weak listener
        listener.receiveDamage(75); // Reduce health to 25
        heard = listener.listenToHowl(howl);
        assertFalse(heard);
    }

    @Test
    public void testHowlDisplayCharacteristics() {
        Lycanthrope lycan = new Lycanthrope(
                "Test Wolf", "male", 1.8, 25, 15, 14,
                AgeCategory.ADULT, 5, Rank.ALPHA, 8
        );

        Howl howl = new Howl(lycan, HowlType.PACK_AFFILIATION);

        // Just verify that the method doesn't throw an exception
        assertDoesNotThrow(() -> howl.displayCharacteristics());
    }
}

