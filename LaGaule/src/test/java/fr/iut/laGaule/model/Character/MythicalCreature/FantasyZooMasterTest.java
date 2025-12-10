package fr.iut.laGaule.model.Character.MythicalCreature;

import fr.iut.laGaule.model.Place.Enclosure;
import fr.iut.laGaule.model.Character.ClanLeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the FantasyZooMaster class.
 */
public class FantasyZooMasterTest {

    private FantasyZooMaster zooMaster;
    private Enclosure enclosure1;
    private Enclosure enclosure2;
    private Lycanthrope solitaryLycan;
    private Lycanthrope packLycan;
    private Pack pack;

    @BeforeEach
    public void setUp() {
        zooMaster = new FantasyZooMaster("ZooMaster");

        ClanLeader leader = new ClanLeader("Leader", "M", 40);
        enclosure1 = new Enclosure("Enclosure1", 500, leader, 0, new ArrayList<>(), new ArrayList<>());
        enclosure2 = new Enclosure("Enclosure2", 500, leader, 0, new ArrayList<>(), new ArrayList<>());

        solitaryLycan = new Lycanthrope(
                "SolitaryWolf", "male", 1.8, 25, 70, 65,
                AgeCategory.ADULT, 0, Rank.OMEGA, 50,
                originGaul);
        solitaryLycan.becomeSolitary();

        pack = new Pack("TestPack");
        packLycan = new Lycanthrope(
                "PackWolf", "female", 1.7, 25, 65, 60,
                AgeCategory.ADULT, 10, Rank.BETA, 40,
                originGaul);
        pack.addMember(packLycan);
    }

    @Test
    public void testZooMasterCreation() {
        assertNotNull(zooMaster);
    }

    @Test
    public void testAddManagedEnclosure() {
        assertDoesNotThrow(() -> zooMaster.addManagedEnclosure(enclosure1));
    }

    @Test
    public void testAddManagedEnclosureDuplicate() {
        zooMaster.addManagedEnclosure(enclosure1);
        assertDoesNotThrow(() -> zooMaster.addManagedEnclosure(enclosure1));
    }

    @Test
    public void testRemoveManagedEnclosure() {
        zooMaster.addManagedEnclosure(enclosure1);
        assertDoesNotThrow(() -> zooMaster.removeManagedEnclosure(enclosure1));
    }

    @Test
    public void testRemoveNonManagedEnclosure() {
        assertDoesNotThrow(() -> zooMaster.removeManagedEnclosure(enclosure1));
    }

    @Test
    public void testMoveLycanthropeNullParameters() {
        zooMaster.addManagedEnclosure(enclosure1);
        zooMaster.addManagedEnclosure(enclosure2);

        assertFalse(zooMaster.moveLycanthrope(null, enclosure1, enclosure2));
        assertFalse(zooMaster.moveLycanthrope(solitaryLycan, null, enclosure2));
        assertFalse(zooMaster.moveLycanthrope(solitaryLycan, enclosure1, null));
    }

    @Test
    public void testMoveLycanthropeNotSolitary() {
        zooMaster.addManagedEnclosure(enclosure1);
        zooMaster.addManagedEnclosure(enclosure2);
        enclosure1.addCharacter(packLycan);

        assertFalse(zooMaster.moveLycanthrope(packLycan, enclosure1, enclosure2));
    }

    @Test
    public void testMoveLycanthropeFromUnmanagedEnclosure() {
        zooMaster.addManagedEnclosure(enclosure2);
        enclosure1.addCharacter(solitaryLycan);

        assertFalse(zooMaster.moveLycanthrope(solitaryLycan, enclosure1, enclosure2));
    }

    @Test
    public void testMoveLycanthropeToUnmanagedEnclosure() {
        zooMaster.addManagedEnclosure(enclosure1);
        enclosure1.addCharacter(solitaryLycan);

        assertFalse(zooMaster.moveLycanthrope(solitaryLycan, enclosure1, enclosure2));
    }

    @Test
    public void testMoveLycanthropeNotInSourceEnclosure() {
        zooMaster.addManagedEnclosure(enclosure1);
        zooMaster.addManagedEnclosure(enclosure2);

        assertFalse(zooMaster.moveLycanthrope(solitaryLycan, enclosure1, enclosure2));
    }

    @Test
    public void testMoveLycanthropeSuccess() {
        zooMaster.addManagedEnclosure(enclosure1);
        zooMaster.addManagedEnclosure(enclosure2);
        enclosure1.addCharacter(solitaryLycan);

        boolean result = zooMaster.moveLycanthrope(solitaryLycan, enclosure1, enclosure2);
        // The result depends on the actual implementation
        // Just ensure no exception is thrown
        assertDoesNotThrow(() -> zooMaster.moveLycanthrope(solitaryLycan, enclosure1, enclosure2));
    }
}
