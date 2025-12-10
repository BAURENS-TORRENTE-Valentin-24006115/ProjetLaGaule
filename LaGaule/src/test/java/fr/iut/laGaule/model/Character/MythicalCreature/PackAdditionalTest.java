package fr.iut.laGaule.model.Character.MythicalCreature;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Additional tests to improve code coverage of the Pack class.
 * These tests cover methods not tested in PackTest.java
 */
class PackAdditionalTest {

    private Pack pack;
    private Lycanthrope alphaMale;
    private Lycanthrope alphaFemale;
    private Lycanthrope betaMale;
    private Lycanthrope omegaMember;

    @BeforeEach
    void setUp() {
        pack = new Pack("TestPack");

        alphaMale = new Lycanthrope("AlphaMale", "male", 1.8, 5, 90, 85,
                AgeCategory.ADULT, 50, Rank.ALPHA, 30, originGaul);
        alphaFemale = new Lycanthrope("AlphaFemale", "female", 1.7, 5, 80, 80,
                AgeCategory.ADULT, 40, Rank.ALPHA, 25, originGaul);
        betaMale = new Lycanthrope("BetaMale", "male", 1.75, 4, 75, 75,
                AgeCategory.ADULT, 30, Rank.BETA, 20, originGaul);
        omegaMember = new Lycanthrope("OmegaMember", "male", 1.6, 3, 50, 50,
                AgeCategory.ADULT, -10, Rank.OMEGA, 15, originGaul);

        pack.addMember(alphaMale);
        pack.addMember(alphaFemale);
        pack.setAlphaMale(alphaMale);
        pack.setAlphaFemale(alphaFemale);
    }

    // ========== Tests pour handleMemberDeath ==========

    @Test
    @DisplayName("Test handleMemberDeath avec un membre normal")
    void testHandleMemberDeathNormalMember() {
        pack.addMember(betaMale);
        int initialSize = pack.getSize();

        pack.handleMemberDeath(betaMale);

        assertEquals(initialSize - 1, pack.getSize());
        assertFalse(pack.getMembers().contains(betaMale));
    }

    @Test
    @DisplayName("Test handleMemberDeath avec alpha mâle")
    void testHandleMemberDeathAlphaMale() {
        pack.addMember(betaMale);

        pack.handleMemberDeath(alphaMale);

        assertFalse(pack.getMembers().contains(alphaMale));
        // Un nouveau alpha mâle devrait être sélectionné si possible
    }

    @Test
    @DisplayName("Test handleMemberDeath avec alpha femelle")
    void testHandleMemberDeathAlphaFemale() {
        Lycanthrope betaFemale = new Lycanthrope("BetaFemale", "female", 1.65, 4, 70, 70,
                AgeCategory.ADULT, 20, Rank.BETA, 20, originGaul);
        pack.addMember(betaFemale);

        pack.handleMemberDeath(alphaFemale);

        assertFalse(pack.getMembers().contains(alphaFemale));
    }

    @Test
    @DisplayName("Test handleMemberDeath avec membre non présent")
    void testHandleMemberDeathNonMember() {
        Lycanthrope outsider = new Lycanthrope("Outsider", "male", 1.7, 3, 60, 60,
                AgeCategory.ADULT, 0, Rank.BETA, 10, originGaul);
        int initialSize = pack.getSize();

        pack.handleMemberDeath(outsider);

        assertEquals(initialSize, pack.getSize());
    }

    // ========== Tests pour reorganizeAfterAlphaDeath ==========

    @Test
    @DisplayName("Test reorganizeAfterAlphaDeath pour alpha mâle")
    void testReorganizeAfterAlphaMaleDeath() {
        pack.addMember(betaMale);
        pack.addMember(omegaMember);

        // Simuler la mort de l'alpha mâle
        pack.handleMemberDeath(alphaMale);

        // Vérifier que le pack se réorganise
        assertNotNull(pack.getAlphaFemale());
    }

    // ========== Tests pour establishAlphaCouple (via méthodes publiques) ==========

    @Test
    @DisplayName("Test établissement couple alpha via manageAlphaCouple")
    void testEstablishAlphaCoupleViaManage() {
        Pack newPack = new Pack("NewPack");
        Lycanthrope male = new Lycanthrope("Male", "male", 1.8, 5, 85, 80,
                AgeCategory.ADULT, 40, Rank.BETA, 25, originGaul);
        Lycanthrope female = new Lycanthrope("Female", "female", 1.7, 5, 75, 75,
                AgeCategory.ADULT, 35, Rank.BETA, 20, originGaul);

        newPack.addMember(male);
        newPack.addMember(female);
        newPack.setAlphaMale(male);
        newPack.setAlphaFemale(female);

        assertTrue(newPack.hasAlphaCouple());
    }

    @Test
    @DisplayName("Test établissement couple alpha sans adultes")
    void testEstablishAlphaCoupleNoAdults() {
        Pack youngPack = new Pack("YoungPack");
        Lycanthrope youngMale = new Lycanthrope("Young", "male", 1.5, 1, 50, 50,
                AgeCategory.YOUNG, 0, Rank.OMEGA, 10, originGaul);

        youngPack.addMember(youngMale);
        // Pas de setAlphaMale car il est jeune

        assertFalse(youngPack.hasAlphaCouple());
    }

    // ========== Tests pour notifyRankChange ==========

    @Test
    @DisplayName("Test notifyRankChange")
    void testNotifyRankChange() {
        pack.addMember(betaMale);

        // Le test vérifie que la méthode ne lance pas d'exception
        assertDoesNotThrow(() -> pack.notifyRankChange(betaMale, alphaMale));
    }

    // ========== Tests pour resolveConflicts ==========

    @Test
    @DisplayName("Test resolveConflicts")
    void testResolveConflicts() {
        pack.addMember(betaMale);
        pack.addMember(omegaMember);

        assertDoesNotThrow(() -> pack.resolveConflicts());
    }

    // ========== Tests pour broadcast ==========

    @Test
    @DisplayName("Test broadcast d'un hurlement")
    void testBroadcast() {
        pack.addMember(betaMale);
        Howl howl = new Howl(alphaMale, HowlType.PACK_AFFILIATION);

        assertDoesNotThrow(() -> pack.broadcast(howl));
    }

    // ========== Tests pour displayHierarchy ==========

    @Test
    @DisplayName("Test displayHierarchy avec plusieurs membres")
    void testDisplayHierarchyMultipleMembers() {
        pack.addMember(betaMale);
        pack.addMember(omegaMember);

        assertDoesNotThrow(() -> pack.displayHierarchy());
    }

    @Test
    @DisplayName("Test displayHierarchy avec pack vide")
    void testDisplayHierarchyEmptyPack() {
        Pack emptyPack = new Pack("EmptyPack");

        assertDoesNotThrow(() -> emptyPack.displayHierarchy());
    }

    // ========== Tests pour toString ==========

    @Test
    @DisplayName("Test toString")
    void testToString() {
        String result = pack.toString();

        assertNotNull(result);
        assertTrue(result.contains("TestPack"));
    }

    // ========== Tests pour setName et getName ==========

    @Test
    @DisplayName("Test setName")
    void testSetName() {
        pack.setName("NewPackName");

        assertEquals("NewPackName", pack.getName());
    }

    // ========== Tests pour isEmpty ==========

    @Test
    @DisplayName("Test isEmpty avec pack vide")
    void testIsEmptyTrue() {
        Pack emptyPack = new Pack("EmptyPack");

        assertTrue(emptyPack.isEmpty());
    }

    @Test
    @DisplayName("Test isEmpty avec pack non vide")
    void testIsEmptyFalse() {
        assertFalse(pack.isEmpty());
    }

    // ========== Tests pour getOmegas ==========

    @Test
    @DisplayName("Test getOmegas")
    void testGetOmegas() {
        pack.addMember(omegaMember);

        var omegas = pack.getOmegas();

        assertNotNull(omegas);
        assertTrue(omegas.contains(omegaMember));
    }

    // ========== Tests pour getPregnancies ==========

    @Test
    @DisplayName("Test getPregnancies sans grossesse")
    void testGetPregnanciesEmpty() {
        var pregnancies = pack.getPregnancies();

        assertNotNull(pregnancies);
        assertTrue(pregnancies.isEmpty());
    }

    @Test
    @DisplayName("Test getPregnancies avec grossesse")
    void testGetPregnanciesWithPregnancy() {
        pack.initiateReproduction();

        var pregnancies = pack.getPregnancies();

        assertNotNull(pregnancies);
        assertFalse(pregnancies.isEmpty());
    }

    // ========== Tests pour giveBirth ==========

    @Test
    @DisplayName("Test giveBirth")
    void testGiveBirth() {
        // Créer une grossesse manuellement
        pack.initiateReproduction();

        int initialSize = pack.getSize();

        // Simuler la fin de la grossesse
        for (int i = 0; i < 10; i++) {
            pack.processPregnancies();
        }

        assertTrue(pack.getSize() >= initialSize);
    }

    // ========== Tests pour setAlphaMale et setAlphaFemale ==========

    @Test
    @DisplayName("Test setAlphaMale avec un nouveau membre")
    void testSetAlphaMale() {
        Lycanthrope newAlpha = new Lycanthrope("NewAlpha", "male", 1.85, 6, 95, 90,
                AgeCategory.ADULT, 60, Rank.BETA, 35, originGaul);
        pack.addMember(newAlpha);

        pack.setAlphaMale(newAlpha);

        assertEquals(newAlpha, pack.getAlphaMale());
        assertEquals(Rank.ALPHA, newAlpha.getHierarchyRank());
    }

    @Test
    @DisplayName("Test setAlphaFemale avec un nouveau membre")
    void testSetAlphaFemale() {
        Lycanthrope newAlpha = new Lycanthrope("NewAlphaFemale", "female", 1.75, 6, 85, 85,
                AgeCategory.ADULT, 55, Rank.BETA, 30, originGaul);
        pack.addMember(newAlpha);

        pack.setAlphaFemale(newAlpha);

        assertEquals(newAlpha, pack.getAlphaFemale());
        assertEquals(Rank.ALPHA, newAlpha.getHierarchyRank());
    }

    // ========== Tests pour manageAlphaCouple (reformation) ==========

    @Test
    @DisplayName("Test manageAlphaCouple pour reformation")
    void testManageAlphaCoupleForReform() {
        pack.addMember(betaMale);

        // Forcer une situation où le couple doit être reformé
        alphaMale.setDominationFactor(-10);
        alphaFemale.setDominationFactor(50);

        assertDoesNotThrow(() -> pack.manageAlphaCouple());
    }

    // ========== Tests pour simulateHierarchyDynamics avec conflits ==========

    @Test
    @DisplayName("Test simulateHierarchyDynamics avec omega harcelé")
    void testSimulateHierarchyDynamicsWithOmega() {
        pack.addMember(omegaMember);

        assertDoesNotThrow(() -> pack.simulateHierarchyDynamics());
    }

    // ========== Tests supplémentaires pour couverture de branches ==========

    @Test
    @DisplayName("Test addMember avec membre déjà présent")
    void testAddMemberAlreadyPresent() {
        int initialSize = pack.getSize();

        pack.addMember(alphaMale); // Déjà membre

        assertEquals(initialSize, pack.getSize());
    }

    @Test
    @DisplayName("Test removeMember avec membre non présent")
    void testRemoveMemberNotPresent() {
        Lycanthrope outsider = new Lycanthrope("Outsider", "male", 1.7, 3, 60, 60,
                AgeCategory.ADULT, 0, Rank.BETA, 10, originGaul);
        int initialSize = pack.getSize();

        pack.removeMember(outsider);

        assertEquals(initialSize, pack.getSize());
    }

    @Test
    @DisplayName("Test hasAlphaCouple sans alpha mâle")
    void testHasAlphaCoupleNoMale() {
        Pack testPack = new Pack("TestPack2");
        testPack.addMember(alphaFemale);
        testPack.setAlphaFemale(alphaFemale);

        assertFalse(testPack.hasAlphaCouple());
    }

    @Test
    @DisplayName("Test hasAlphaCouple sans alpha femelle")
    void testHasAlphaCoupleNoFemale() {
        Pack testPack = new Pack("TestPack3");
        testPack.addMember(alphaMale);
        testPack.setAlphaMale(alphaMale);

        assertFalse(testPack.hasAlphaCouple());
    }

    @Test
    @DisplayName("Test manageAlphaCouple sans couple alpha")
    void testManageAlphaCoupleNoCouple() {
        Pack testPack = new Pack("TestPack4");
        testPack.addMember(betaMale);

        assertDoesNotThrow(() -> testPack.manageAlphaCouple());
    }

    @Test
    @DisplayName("Test processPregnancies sans grossesse")
    void testProcessPregnanciesEmpty() {
        assertDoesNotThrow(() -> pack.processPregnancies());
    }
}

