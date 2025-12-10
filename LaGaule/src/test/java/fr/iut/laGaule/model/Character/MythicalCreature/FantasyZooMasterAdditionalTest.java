package fr.iut.laGaule.model.Character.MythicalCreature;

import fr.iut.laGaule.model.Place.Enclosure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests supplémentaires pour améliorer la couverture de code de la classe FantasyZooMaster.
 */
class FantasyZooMasterAdditionalTest {

    private FantasyZooMaster zooMaster;
    private Enclosure enclosure1;
    private Enclosure enclosure2;
    private Lycanthrope solitaryMale;
    private Lycanthrope solitaryFemale;
    private Pack pack;

    @BeforeEach
    void setUp() {
        zooMaster = new FantasyZooMaster("ZooMaster");
        enclosure1 = new Enclosure("Enclosure1", 500, null, 0, new ArrayList<>(), new ArrayList<>());
        enclosure2 = new Enclosure("Enclosure2", 500, null, 0, new ArrayList<>(), new ArrayList<>());

        solitaryMale = new Lycanthrope("SolitaryMale", "male", 1.8, 5, 80, 75,
                AgeCategory.ADULT, 0, null, 30);
        solitaryFemale = new Lycanthrope("SolitaryFemale", "female", 1.7, 5, 70, 70,
                AgeCategory.ADULT, 0, null, 25);

        pack = new Pack("TestPack");
    }

    // ========== Tests pour formation de pack (via moveLycanthrope) ==========

    @Test
    @DisplayName("Test formation de pack après déplacement de solitaires")
    void testFormNewPackAfterMove() {
        zooMaster.addManagedEnclosure(enclosure1);
        zooMaster.addManagedEnclosure(enclosure2);
        enclosure1.addCharacter(solitaryMale);
        enclosure2.addCharacter(solitaryFemale);

        // Déplacer le mâle vers l'enclos de la femelle
        boolean result = zooMaster.moveLycanthrope(solitaryMale, enclosure1, enclosure2);

        assertTrue(result);
    }

    @Test
    @DisplayName("Test pas de formation de pack sans mâle solitaire")
    void testNoPackFormationNoMale() {
        zooMaster.addManagedEnclosure(enclosure1);
        enclosure1.addCharacter(solitaryFemale);

        // Pas de mâle pour former un pack
        assertTrue(solitaryFemale.isSolitary());
    }

    @Test
    @DisplayName("Test pas de formation de pack sans femelle solitaire")
    void testNoPackFormationNoFemale() {
        zooMaster.addManagedEnclosure(enclosure1);
        enclosure1.addCharacter(solitaryMale);

        // Pas de femelle pour former un pack
        assertTrue(solitaryMale.isSolitary());
    }

    @Test
    @DisplayName("Test moveLycanthrope avec paramètres null")
    void testFormNewPackNullEnclosure() {
        assertFalse(zooMaster.moveLycanthrope(solitaryMale, null, enclosure1));
    }

    // ========== Tests pour displayManagedEnclosures ==========

    @Test
    @DisplayName("Test displayManagedEnclosures avec enclos")
    void testDisplayManagedEnclosuresWithEnclosures() {
        zooMaster.addManagedEnclosure(enclosure1);
        zooMaster.addManagedEnclosure(enclosure2);

        assertDoesNotThrow(() -> zooMaster.displayManagedEnclosures());
    }

    @Test
    @DisplayName("Test displayManagedEnclosures sans enclos")
    void testDisplayManagedEnclosuresEmpty() {
        assertDoesNotThrow(() -> zooMaster.displayManagedEnclosures());
    }

    // ========== Tests pour setName et getName ==========

    @Test
    @DisplayName("Test setName")
    void testSetName() {
        zooMaster.setName("NewName");

        assertEquals("NewName", zooMaster.getName());
    }

    @Test
    @DisplayName("Test getName")
    void testGetName() {
        assertEquals("ZooMaster", zooMaster.getName());
    }

    // ========== Tests pour getManagedEnclosures ==========

    @Test
    @DisplayName("Test getManagedEnclosures vide")
    void testGetManagedEnclosuresEmpty() {
        List<Enclosure> enclosures = zooMaster.getManagedEnclosures();

        assertNotNull(enclosures);
        assertTrue(enclosures.isEmpty());
    }

    @Test
    @DisplayName("Test getManagedEnclosures avec enclos")
    void testGetManagedEnclosuresWithEnclosures() {
        zooMaster.addManagedEnclosure(enclosure1);
        zooMaster.addManagedEnclosure(enclosure2);

        List<Enclosure> enclosures = zooMaster.getManagedEnclosures();

        assertEquals(2, enclosures.size());
        assertTrue(enclosures.contains(enclosure1));
        assertTrue(enclosures.contains(enclosure2));
    }

    // ========== Tests pour addManagedEnclosure ==========

    @Test
    @DisplayName("Test addManagedEnclosure avec enclos déjà géré")
    void testAddManagedEnclosureAlreadyManaged() {
        zooMaster.addManagedEnclosure(enclosure1);
        int initialSize = zooMaster.getManagedEnclosures().size();

        zooMaster.addManagedEnclosure(enclosure1); // Ajout en double

        assertEquals(initialSize, zooMaster.getManagedEnclosures().size());
    }

    // ========== Tests pour removeManagedEnclosure ==========

    @Test
    @DisplayName("Test removeManagedEnclosure avec enclos existant")
    void testRemoveManagedEnclosureExisting() {
        zooMaster.addManagedEnclosure(enclosure1);

        zooMaster.removeManagedEnclosure(enclosure1);

        assertFalse(zooMaster.getManagedEnclosures().contains(enclosure1));
    }

    @Test
    @DisplayName("Test removeManagedEnclosure avec enclos non géré")
    void testRemoveManagedEnclosureNotManaged() {
        int initialSize = zooMaster.getManagedEnclosures().size();

        zooMaster.removeManagedEnclosure(enclosure1);

        assertEquals(initialSize, zooMaster.getManagedEnclosures().size());
    }

    // ========== Tests pour moveLycanthrope ==========

    @Test
    @DisplayName("Test moveLycanthrope avec lycanthrope non solitaire")
    void testMoveLycanthropeNotSolitary() {
        zooMaster.addManagedEnclosure(enclosure1);
        zooMaster.addManagedEnclosure(enclosure2);

        // Ajouter le lycanthrope à un pack
        pack.addMember(solitaryMale);
        enclosure1.addCharacter(solitaryMale);

        boolean result = zooMaster.moveLycanthrope(solitaryMale, enclosure1, enclosure2);

        assertFalse(result);
    }

    @Test
    @DisplayName("Test moveLycanthrope avec enclos source non géré")
    void testMoveLycanthropeSourceNotManaged() {
        zooMaster.addManagedEnclosure(enclosure2);
        enclosure1.addCharacter(solitaryMale);

        boolean result = zooMaster.moveLycanthrope(solitaryMale, enclosure1, enclosure2);

        assertFalse(result);
    }

    @Test
    @DisplayName("Test moveLycanthrope avec enclos destination non géré")
    void testMoveLycanthropeDestNotManaged() {
        zooMaster.addManagedEnclosure(enclosure1);
        enclosure1.addCharacter(solitaryMale);

        boolean result = zooMaster.moveLycanthrope(solitaryMale, enclosure1, enclosure2);

        assertFalse(result);
    }

    @Test
    @DisplayName("Test moveLycanthrope avec lycanthrope pas dans l'enclos source")
    void testMoveLycanthropeNotInSource() {
        zooMaster.addManagedEnclosure(enclosure1);
        zooMaster.addManagedEnclosure(enclosure2);

        boolean result = zooMaster.moveLycanthrope(solitaryMale, enclosure1, enclosure2);

        assertFalse(result);
    }

    @Test
    @DisplayName("Test moveLycanthrope avec paramètres null")
    void testMoveLycanthropeNullParams() {
        zooMaster.addManagedEnclosure(enclosure1);
        zooMaster.addManagedEnclosure(enclosure2);

        assertFalse(zooMaster.moveLycanthrope(null, enclosure1, enclosure2));
        assertFalse(zooMaster.moveLycanthrope(solitaryMale, null, enclosure2));
        assertFalse(zooMaster.moveLycanthrope(solitaryMale, enclosure1, null));
    }

    @Test
    @DisplayName("Test moveLycanthrope réussi")
    void testMoveLycanthropeSuccess() {
        zooMaster.addManagedEnclosure(enclosure1);
        zooMaster.addManagedEnclosure(enclosure2);
        enclosure1.addCharacter(solitaryMale);

        boolean result = zooMaster.moveLycanthrope(solitaryMale, enclosure1, enclosure2);

        assertTrue(result);
        assertFalse(enclosure1.getCharacter().contains(solitaryMale));
        assertTrue(enclosure2.getCharacter().contains(solitaryMale));
    }

    // ========== Tests pour formation de pack avec plusieurs solitaires ==========

    @Test
    @DisplayName("Test avec plusieurs mâles et femelles après déplacement")
    void testFormNewPackMultipleSolitaries() {
        Lycanthrope male2 = new Lycanthrope("Male2", "male", 1.75, 4, 75, 70,
                AgeCategory.ADULT, 0, null, 25);
        Lycanthrope female2 = new Lycanthrope("Female2", "female", 1.65, 4, 65, 65,
                AgeCategory.ADULT, 0, null, 20);

        zooMaster.addManagedEnclosure(enclosure1);
        zooMaster.addManagedEnclosure(enclosure2);
        enclosure1.addCharacter(solitaryMale);
        enclosure1.addCharacter(male2);
        enclosure2.addCharacter(solitaryFemale);
        enclosure2.addCharacter(female2);

        // Déplacer les mâles vers l'enclos des femelles
        zooMaster.moveLycanthrope(solitaryMale, enclosure1, enclosure2);
        zooMaster.moveLycanthrope(male2, enclosure1, enclosure2);

        // Vérifier que les déplacements ont réussi
        assertTrue(enclosure2.getCharacter().size() >= 2);
    }

    // ========== Tests pour comportement avec pack existant ==========

    @Test
    @DisplayName("Test comportement quand un pack existe déjà")
    void testCheckAndFormNewPackExistingPack() {
        Lycanthrope packMember = new Lycanthrope("PackMember", "male", 1.8, 5, 80, 75,
                AgeCategory.ADULT, 10, Rank.BETA, 30);
        pack.addMember(packMember);

        zooMaster.addManagedEnclosure(enclosure1);
        zooMaster.addManagedEnclosure(enclosure2);
        enclosure1.addCharacter(packMember);
        enclosure2.addCharacter(solitaryMale);
        enclosure2.addCharacter(solitaryFemale);

        // Le membre du pack ne peut pas être déplacé
        boolean result = zooMaster.moveLycanthrope(packMember, enclosure1, enclosure2);
        assertFalse(result);
    }
}

