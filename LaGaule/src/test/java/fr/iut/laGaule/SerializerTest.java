package fr.iut.laGaule;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.Gaul.Gaul;
import fr.iut.laGaule.model.Character.Gaul.Merchant;
import fr.iut.laGaule.model.Character.Roman.General;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Character.Roman.Roman;
import fr.iut.laGaule.model.Place.GaulVillage;
import fr.iut.laGaule.model.Place.Place;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe Serializer.
 */
public class SerializerTest {

    private Serializer serializer;
    private static final String TEST_ZONE_NAME = "TestZone";
    private Place testPlace;

    @BeforeEach
    public void setUp() {
        serializer = new Serializer();
        testPlace = new GaulVillage("Village Test", 100, null, 0, new ArrayList<>(), new ArrayList<>());
        // Nettoyer les fichiers de test existants
        deleteTestFile();
    }

    @AfterEach
    public void tearDown() {
        // Nettoyer après chaque test
        deleteTestFile();
    }

    private void deleteTestFile() {
        File testFile = new File(TEST_ZONE_NAME + ".ser");
        if (testFile.exists()) {
            boolean deleted = testFile.delete();
            // Ignorer le résultat si le fichier n'existe pas encore
        }
    }

    @Test
    public void testSerializeAndDeserializeMap() {
        // Création d'une map de test
        Map<String, Object> testMap = new HashMap<>();
        Gaul gaul = new Merchant("Astérix", "M", 1.70, 35, 100, 80);
        gaul.setPlace(testPlace);
        testMap.put("gaul1", gaul);
        testMap.put("info", "Test Info");

        // Sérialisation
        serializer.serialize(TEST_ZONE_NAME, testMap);

        // Vérifier que le fichier existe
        File serFile = new File(TEST_ZONE_NAME + ".ser");
        assertTrue(serFile.exists(), "Le fichier sérialisé devrait exister");

        // Désérialisation
        Map<String, Object> deserializedMap = serializer.deserialize(TEST_ZONE_NAME);

        // Vérifications
        assertNotNull(deserializedMap, "La map désérialisée ne devrait pas être null");
        assertEquals(testMap.size(), deserializedMap.size(), "La taille de la map devrait être identique");
        assertTrue(deserializedMap.containsKey("gaul1"), "La map devrait contenir la clé 'gaul1'");
        assertTrue(deserializedMap.containsKey("info"), "La map devrait contenir la clé 'info'");
    }

    @Test
    public void testDeserializeNonExistentFile() {
        // Tenter de désérialiser un fichier qui n'existe pas
        Map<String, Object> result = serializer.deserialize("NonExistentZone");
        assertNull(result, "Le résultat devrait être null pour un fichier inexistant");
    }

    @Test
    public void testDeserializeRandomCharacter() {
        // Création d'une map avec des personnages
        Map<String, Object> testMap = new HashMap<>();
        Gaul gaul1 = new Merchant("Astérix", "M", 1.70, 35, 100, 80);
        gaul1.setPlace(testPlace);
        Gaul gaul2 = new Druid("Panoramix", "M", 1.65, 60, 90, 70);
        gaul2.setPlace(testPlace);
        testMap.put("char1", gaul1);
        testMap.put("char2", gaul2);

        // Sérialisation
        serializer.serialize(TEST_ZONE_NAME, testMap);

        // Désérialisation d'un personnage aléatoire
        Character randomChar = serializer.deserializeRandomCharacter(TEST_ZONE_NAME);

        // Vérifications
        assertNotNull(randomChar, "Un personnage devrait être retourné");
        assertInstanceOf(Gaul.class, randomChar, "Le personnage devrait être un Gaulois");
    }

    @Test
    public void testDeserializeRandomCharacterNoFile() {
        // Tester avec un fichier inexistant
        Character result = serializer.deserializeRandomCharacter("NonExistent");
        assertNull(result, "Le résultat devrait être null pour un fichier inexistant");
    }

    @Test
    public void testDeserializeRandomGaul() {
        // Création d'une map avec des Gaulois
        Map<String, Object> testMap = new HashMap<>();
        Gaul gaul1 = new Merchant("Astérix", "M", 1.70, 35, 100, 80);
        gaul1.setPlace(testPlace);
        Gaul gaul2 = new Druid("Panoramix", "M", 1.65, 60, 90, 70);
        gaul2.setPlace(testPlace);
        Roman roman = new Legionary("Légionnaire", "M", 1.75, 30, 85, 75);
        roman.setPlace(testPlace);
        testMap.put("gaul1", gaul1);
        testMap.put("gaul2", gaul2);
        testMap.put("roman1", roman);

        // Sérialisation
        serializer.serialize(TEST_ZONE_NAME, testMap);

        // Désérialisation d'un Gaulois aléatoire
        Gaul randomGaul = serializer.deserializeRandomGaul(TEST_ZONE_NAME);

        // Vérifications
        assertNotNull(randomGaul, "Un Gaulois devrait être retourné");
        assertInstanceOf(Gaul.class, randomGaul, "L'objet devrait être un Gaulois");
    }

    @Test
    public void testDeserializeRandomGaulNoGauls() {
        // Création d'une map sans Gaulois
        Map<String, Object> testMap = new HashMap<>();
        Roman roman = new Legionary("Légionnaire", "M", 1.75, 30, 85, 75);
        roman.setPlace(testPlace);
        testMap.put("roman1", roman);

        // Sérialisation
        serializer.serialize(TEST_ZONE_NAME, testMap);

        // Désérialisation - devrait retourner null car pas de Gaulois
        Gaul result = serializer.deserializeRandomGaul(TEST_ZONE_NAME);
        assertNull(result, "Le résultat devrait être null car il n'y a pas de Gaulois");
    }

    @Test
    public void testDeserializeRandomGaulNoFile() {
        // Tester avec un fichier inexistant
        Gaul result = serializer.deserializeRandomGaul("NonExistent");
        assertNull(result, "Le résultat devrait être null pour un fichier inexistant");
    }

    @Test
    public void testDeserializeRandomRoman() {
        // Création d'une map avec des Romains
        Map<String, Object> testMap = new HashMap<>();
        Roman roman1 = new Legionary("Légionnaire", "M", 1.75, 30, 85, 75);
        roman1.setPlace(testPlace);
        Roman roman2 = new General("Général", "M", 1.80, 45, 90, 80);
        roman2.setPlace(testPlace);
        Gaul gaul = new Merchant("Astérix", "M", 1.70, 35, 100, 80);
        gaul.setPlace(testPlace);
        testMap.put("roman1", roman1);
        testMap.put("roman2", roman2);
        testMap.put("gaul1", gaul);

        // Sérialisation
        serializer.serialize(TEST_ZONE_NAME, testMap);

        // Désérialisation d'un Romain aléatoire
        Roman randomRoman = serializer.deserializeRandomRoman(TEST_ZONE_NAME);

        // Vérifications
        assertNotNull(randomRoman, "Un Romain devrait être retourné");
        assertInstanceOf(Roman.class, randomRoman, "L'objet devrait être un Romain");
    }

    @Test
    public void testDeserializeRandomRomanNoRomans() {
        // Création d'une map sans Romains
        Map<String, Object> testMap = new HashMap<>();
        Gaul gaul = new Merchant("Astérix", "M", 1.70, 35, 100, 80);
        gaul.setPlace(testPlace);
        testMap.put("gaul1", gaul);

        // Sérialisation
        serializer.serialize(TEST_ZONE_NAME, testMap);

        // Désérialisation - devrait retourner null car pas de Romains
        Roman result = serializer.deserializeRandomRoman(TEST_ZONE_NAME);
        assertNull(result, "Le résultat devrait être null car il n'y a pas de Romains");
    }

    @Test
    public void testDeserializeRandomRomanNoFile() {
        // Tester avec un fichier inexistant
        Roman result = serializer.deserializeRandomRoman("NonExistent");
        assertNull(result, "Le résultat devrait être null pour un fichier inexistant");
    }

    @Test
    public void testDeserializeRandomLegionary() {
        // Création d'une map avec des Légionnaires
        Map<String, Object> testMap = new HashMap<>();
        Legionary leg1 = new Legionary("Légionnaire1", "M", 1.75, 30, 85, 75);
        leg1.setPlace(testPlace);
        Legionary leg2 = new Legionary("Légionnaire2", "M", 1.78, 28, 80, 70);
        leg2.setPlace(testPlace);
        Roman general = new General("Général", "M", 1.80, 45, 90, 80);
        general.setPlace(testPlace);
        testMap.put("leg1", leg1);
        testMap.put("leg2", leg2);
        testMap.put("general", general);

        // Sérialisation
        serializer.serialize(TEST_ZONE_NAME, testMap);

        // Désérialisation d'un Légionnaire aléatoire
        Legionary randomLegionary = serializer.deserializeRandomLegionary(TEST_ZONE_NAME);

        // Vérifications
        assertNotNull(randomLegionary, "Un Légionnaire devrait être retourné");
        assertInstanceOf(Legionary.class, randomLegionary, "L'objet devrait être un Légionnaire");
    }

    @Test
    public void testDeserializeRandomLegionaryNoLegionaries() {
        // Création d'une map sans Légionnaires
        Map<String, Object> testMap = new HashMap<>();
        Roman general = new General("Général", "M", 1.80, 45, 90, 80);
        general.setPlace(testPlace);
        testMap.put("general", general);

        // Sérialisation
        serializer.serialize(TEST_ZONE_NAME, testMap);

        // Désérialisation - devrait retourner null car pas de Légionnaires
        Legionary result = serializer.deserializeRandomLegionary(TEST_ZONE_NAME);
        assertNull(result, "Le résultat devrait être null car il n'y a pas de Légionnaires");
    }

    @Test
    public void testDeserializeRandomLegionaryNoFile() {
        // Tester avec un fichier inexistant
        Legionary result = serializer.deserializeRandomLegionary("NonExistent");
        assertNull(result, "Le résultat devrait être null pour un fichier inexistant");
    }

    @Test
    public void testSerializeEmptyMap() {
        // Sérialiser une map vide
        Map<String, Object> emptyMap = new HashMap<>();
        serializer.serialize(TEST_ZONE_NAME, emptyMap);

        // Vérifier que le fichier existe
        File serFile = new File(TEST_ZONE_NAME + ".ser");
        assertTrue(serFile.exists(), "Le fichier devrait exister même avec une map vide");

        // Désérialiser
        Map<String, Object> result = serializer.deserialize(TEST_ZONE_NAME);
        assertNotNull(result, "La map ne devrait pas être null");
        assertTrue(result.isEmpty(), "La map devrait être vide");
    }

    @Test
    public void testSerializeMultipleTypes() {
        // Tester avec différents types d'objets
        Map<String, Object> testMap = new HashMap<>();
        testMap.put("string", "Test");
        testMap.put("integer", 42);
        testMap.put("double", 3.14);
        Gaul gaul = new Merchant("Astérix", "M", 1.70, 35, 100, 80);
        gaul.setPlace(testPlace);
        testMap.put("gaul", gaul);

        // Sérialisation
        serializer.serialize(TEST_ZONE_NAME, testMap);

        // Désérialisation
        Map<String, Object> result = serializer.deserialize(TEST_ZONE_NAME);

        // Vérifications
        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals("Test", result.get("string"));
        assertEquals(42, result.get("integer"));
        assertEquals(3.14, result.get("double"));
        assertInstanceOf(Gaul.class, result.get("gaul"));
    }
}

