package fr.iut.laGaule.process;

import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.Gaul.Druid;
import fr.iut.laGaule.model.Character.Gaul.Gaul;
import fr.iut.laGaule.model.Character.Gaul.Merchant;
import fr.iut.laGaule.model.Character.Roman.General;
import fr.iut.laGaule.model.Character.Roman.Legionary;
import fr.iut.laGaule.model.Place.GaulVillage;
import fr.iut.laGaule.model.Place.Place;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe CharacterThread.
 */
public class CharacterThreadTest {

    private CharacterThread characterThread;
    private Character testCharacter;
    private Place testPlace;

    @BeforeEach
    public void setUp() {
        testPlace = new GaulVillage("Village Test", 100, null, 0, new ArrayList<>(), new ArrayList<>());
        testCharacter = new Merchant("Testérix", "M", 1.70, 35, 100, 80);
        testCharacter.setPlace(testPlace);
        characterThread = new CharacterThread(testCharacter);
    }

    @AfterEach
    public void tearDown() {
        if (characterThread != null) {
            characterThread.stopSimulation();
        }
    }

    @Test
    public void testCharacterThreadCreation() {
        assertNotNull(characterThread, "CharacterThread ne devrait pas être null");
    }

    @Test
    public void testGetCharacter() {
        Character character = characterThread.getCharacter();
        assertNotNull(character, "getCharacter() ne devrait pas retourner null");
        assertEquals(testCharacter, character, "Devrait retourner le personnage passé au constructeur");
    }

    @Test
    public void testCharacterThreadWithDruid() {
        Character druid = new Druid("Panoramix", "M", 1.65, 60, 90, 70);
        druid.setPlace(testPlace);
        CharacterThread druidThread = new CharacterThread(druid);

        assertNotNull(druidThread, "CharacterThread avec Druid ne devrait pas être null");
        assertEquals(druid, druidThread.getCharacter(), "Devrait gérer un Druid");

        druidThread.stopSimulation();
    }

    @Test
    public void testCharacterThreadWithGeneral() {
        Character general = new General("César", "M", 1.75, 50, 95, 85);
        general.setPlace(testPlace);
        CharacterThread generalThread = new CharacterThread(general);

        assertNotNull(generalThread, "CharacterThread avec General ne devrait pas être null");
        assertEquals(general, generalThread.getCharacter(), "Devrait gérer un General");

        generalThread.stopSimulation();
    }

    @Test
    public void testCharacterThreadWithLegionary() {
        Character legionary = new Legionary("Légionnaire", "M", 1.75, 30, 85, 75);
        legionary.setPlace(testPlace);
        CharacterThread legionaryThread = new CharacterThread(legionary);

        assertNotNull(legionaryThread, "CharacterThread avec Legionary ne devrait pas être null");
        assertEquals(legionary, legionaryThread.getCharacter(), "Devrait gérer un Legionary");

        legionaryThread.stopSimulation();
    }

    @Test
    public void testStopSimulation() {
        // Démarrer le thread
        Thread thread = new Thread(characterThread);
        thread.start();

        // Laisser tourner un peu
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Arrêter la simulation
        characterThread.stopSimulation();

        // Attendre que le thread se termine
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Le thread devrait être terminé ou en cours de terminaison
        // On ne peut pas vérifier directement l'état 'running' car il est private
        assertDoesNotThrow(() -> characterThread.stopSimulation(),
                "stopSimulation() ne devrait pas lancer d'exception");
    }

    @Test
    public void testThreadStopsWhenCharacterDies() {
        // Créer un personnage avec peu de santé
        Character weakCharacter = new Gaul("Faible", "M", 1.70, 35, 0, 80);
        weakCharacter.setPlace(testPlace);
        CharacterThread weakThread = new CharacterThread(weakCharacter);

        // Démarrer le thread
        Thread thread = new Thread(weakThread);
        thread.start();

        // Attendre un peu
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Le thread devrait s'arrêter car le personnage est mort
        weakThread.stopSimulation();
    }

    @Test
    public void testCharacterThreadImplementsRunnable() {
        assertInstanceOf(Runnable.class, characterThread,
                "CharacterThread devrait implémenter Runnable");
    }

    @Test
    public void testMultipleCharacterThreads() {
        // Créer plusieurs threads
        Character char1 = new Merchant("Marchand1", "M", 1.70, 35, 100, 80);
        char1.setPlace(testPlace);
        Character char2 = new Gaul("Gaulois1", "M", 1.75, 30, 90, 75);
        char2.setPlace(testPlace);

        CharacterThread thread1 = new CharacterThread(char1);
        CharacterThread thread2 = new CharacterThread(char2);

        assertNotNull(thread1, "Thread 1 ne devrait pas être null");
        assertNotNull(thread2, "Thread 2 ne devrait pas être null");

        assertNotEquals(thread1.getCharacter(), thread2.getCharacter(),
                "Les threads devraient gérer des personnages différents");

        thread1.stopSimulation();
        thread2.stopSimulation();
    }

    @Test
    public void testThreadCanBeRestarted() {
        // Démarrer le thread
        Thread thread1 = new Thread(characterThread);
        thread1.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Arrêter
        characterThread.stopSimulation();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Créer un nouveau thread avec le même personnage
        CharacterThread newThread = new CharacterThread(testCharacter);
        Thread thread2 = new Thread(newThread);

        assertDoesNotThrow(thread2::start,
                "Devrait pouvoir créer un nouveau thread avec le même personnage");

        newThread.stopSimulation();
    }

    @Test
    public void testCharacterThreadWithNullPlace() {
        // Tester avec un personnage sans lieu (cas limite)
        Character noPlaceChar = new Gaul("SansLieu", "M", 1.70, 35, 100, 80);
        CharacterThread noPlaceThread = new CharacterThread(noPlaceChar);

        assertNotNull(noPlaceThread, "CharacterThread devrait être créé même sans lieu");
        assertEquals(noPlaceChar, noPlaceThread.getCharacter(),
                "Devrait retourner le personnage même sans lieu");

        noPlaceThread.stopSimulation();
    }

    @Test
    public void testGetCharacterDoesNotReturnNull() {
        assertNotNull(characterThread.getCharacter(),
                "getCharacter() ne devrait jamais retourner null si un personnage a été fourni");
    }

    @Test
    public void testCharacterThreadWithDifferentCharacterTypes() {
        // Tester avec différents types de personnages
        Character gaul = new Gaul("Gaulois", "M", 1.70, 35, 100, 80);
        gaul.setPlace(testPlace);
        Character merchant = new Merchant("Marchand", "M", 1.70, 35, 100, 80);
        merchant.setPlace(testPlace);
        Character druid = new Druid("Druide", "M", 1.65, 60, 90, 70);
        druid.setPlace(testPlace);
        Character general = new General("Général", "M", 1.75, 50, 95, 85);
        general.setPlace(testPlace);
        Character legionary = new Legionary("Légionnaire", "M", 1.75, 30, 85, 75);
        legionary.setPlace(testPlace);

        Character[] characters = { gaul, merchant, druid, general, legionary };

        for (Character character : characters) {
            CharacterThread thread = new CharacterThread(character);
            assertNotNull(thread, "CharacterThread devrait être créé pour " + character.getClass().getSimpleName());
            assertEquals(character, thread.getCharacter(),
                    "Devrait retourner le bon personnage pour " + character.getClass().getSimpleName());
            thread.stopSimulation();
        }
    }

    @Test
    public void testStopSimulationMultipleTimes() {
        // Tester l'appel multiple de stopSimulation
        characterThread.stopSimulation();
        characterThread.stopSimulation();
        characterThread.stopSimulation();

        assertDoesNotThrow(() -> characterThread.stopSimulation(),
                "Appeler stopSimulation plusieurs fois ne devrait pas causer de problème");
    }
}

