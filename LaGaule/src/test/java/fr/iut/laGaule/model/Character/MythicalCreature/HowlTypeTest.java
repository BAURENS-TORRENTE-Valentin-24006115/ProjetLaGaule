package fr.iut.laGaule.model.Character.MythicalCreature;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the HowlType enum.
 */
public class HowlTypeTest {

    @Test
    public void testPackAffiliationDescription() {
        assertEquals("Pack Affiliation", HowlType.PACK_AFFILIATION.getDescription());
    }

    @Test
    public void testDominationDescription() {
        assertEquals("Domination", HowlType.DOMINATION.getDescription());
    }

    @Test
    public void testSubmissionDescription() {
        assertEquals("Submission", HowlType.SUBMISSION.getDescription());
    }

    @Test
    public void testAggressionDescription() {
        assertEquals("Aggression", HowlType.AGGRESSION.getDescription());
    }

    @Test
    public void testHowlTypeValuesCount() {
        assertEquals(4, HowlType.values().length);
    }

    @Test
    public void testValueOf() {
        assertEquals(HowlType.PACK_AFFILIATION, HowlType.valueOf("PACK_AFFILIATION"));
        assertEquals(HowlType.DOMINATION, HowlType.valueOf("DOMINATION"));
        assertEquals(HowlType.SUBMISSION, HowlType.valueOf("SUBMISSION"));
        assertEquals(HowlType.AGGRESSION, HowlType.valueOf("AGGRESSION"));
    }

    @Test
    public void testInvalidValueOf() {
        assertThrows(IllegalArgumentException.class, () -> {
            HowlType.valueOf("INVALID");
        });
    }

    @Test
    public void testAllHowlTypesNotNull() {
        for (HowlType howlType : HowlType.values()) {
            assertNotNull(howlType);
            assertNotNull(howlType.getDescription());
            assertFalse(howlType.getDescription().isEmpty());
        }
    }

    @Test
    public void testHowlTypeName() {
        assertEquals("PACK_AFFILIATION", HowlType.PACK_AFFILIATION.name());
        assertEquals("DOMINATION", HowlType.DOMINATION.name());
        assertEquals("SUBMISSION", HowlType.SUBMISSION.name());
        assertEquals("AGGRESSION", HowlType.AGGRESSION.name());
    }
}
