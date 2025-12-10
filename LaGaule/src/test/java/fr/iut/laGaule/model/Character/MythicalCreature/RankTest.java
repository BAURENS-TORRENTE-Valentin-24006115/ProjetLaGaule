package fr.iut.laGaule.model.Character.MythicalCreature;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Rank enum.
 */
public class RankTest {

    @Test
    public void testAlphaSymbol() {
        assertEquals("α", Rank.ALPHA.getSymbol());
    }

    @Test
    public void testBetaSymbol() {
        assertEquals("β", Rank.BETA.getSymbol());
    }

    @Test
    public void testGammaSymbol() {
        assertEquals("γ", Rank.GAMMA.getSymbol());
    }

    @Test
    public void testDeltaSymbol() {
        assertEquals("δ", Rank.DELTA.getSymbol());
    }

    @Test
    public void testEpsilonSymbol() {
        assertEquals("ε", Rank.EPSILON.getSymbol());
    }

    @Test
    public void testZetaSymbol() {
        assertEquals("ζ", Rank.ZETA.getSymbol());
    }

    @Test
    public void testEtaSymbol() {
        assertEquals("η", Rank.ETA.getSymbol());
    }

    @Test
    public void testOmegaSymbol() {
        assertEquals("ω", Rank.OMEGA.getSymbol());
    }

    @Test
    public void testHierarchyLevels() {
        assertEquals(8, Rank.ALPHA.getHierarchyLevel());
        assertEquals(7, Rank.BETA.getHierarchyLevel());
        assertEquals(6, Rank.GAMMA.getHierarchyLevel());
        assertEquals(5, Rank.DELTA.getHierarchyLevel());
        assertEquals(4, Rank.EPSILON.getHierarchyLevel());
        assertEquals(3, Rank.ZETA.getHierarchyLevel());
        assertEquals(2, Rank.ETA.getHierarchyLevel());
        assertEquals(1, Rank.OMEGA.getHierarchyLevel());
    }

    @Test
    public void testAlphaDominatesBeta() {
        assertTrue(Rank.ALPHA.dominates(Rank.BETA));
    }

    @Test
    public void testBetaDominatesGamma() {
        assertTrue(Rank.BETA.dominates(Rank.GAMMA));
    }

    @Test
    public void testAlphaDominatesOmega() {
        assertTrue(Rank.ALPHA.dominates(Rank.OMEGA));
    }

    @Test
    public void testOmegaDoesNotDominateAlpha() {
        assertFalse(Rank.OMEGA.dominates(Rank.ALPHA));
    }

    @Test
    public void testSameRankDoesNotDominate() {
        assertFalse(Rank.BETA.dominates(Rank.BETA));
    }

    @Test
    public void testRankValuesCount() {
        assertEquals(8, Rank.values().length);
    }

    @Test
    public void testToString() {
        assertEquals("α", Rank.ALPHA.toString());
        assertEquals("ω", Rank.OMEGA.toString());
    }

    @Test
    public void testValueOf() {
        assertEquals(Rank.ALPHA, Rank.valueOf("ALPHA"));
        assertEquals(Rank.BETA, Rank.valueOf("BETA"));
        assertEquals(Rank.GAMMA, Rank.valueOf("GAMMA"));
        assertEquals(Rank.OMEGA, Rank.valueOf("OMEGA"));
    }

    @Test
    public void testInvalidValueOf() {
        assertThrows(IllegalArgumentException.class, () -> {
            Rank.valueOf("INVALID");
        });
    }

    @Test
    public void testAllRanksNotNull() {
        for (Rank rank : Rank.values()) {
            assertNotNull(rank);
            assertNotNull(rank.getSymbol());
            assertTrue(rank.getHierarchyLevel() > 0);
        }
    }

    @Test
    public void testDominationChain() {
        // Test complete domination chain
        assertTrue(Rank.ALPHA.dominates(Rank.BETA));
        assertTrue(Rank.BETA.dominates(Rank.GAMMA));
        assertTrue(Rank.GAMMA.dominates(Rank.DELTA));
        assertTrue(Rank.DELTA.dominates(Rank.EPSILON));
        assertTrue(Rank.EPSILON.dominates(Rank.ZETA));
        assertTrue(Rank.ZETA.dominates(Rank.ETA));
        assertTrue(Rank.ETA.dominates(Rank.OMEGA));
    }
}
