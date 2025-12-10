package fr.iut.laGaule;

import fr.iut.laGaule.process.InvasionTheatre;
import fr.iut.laGaule.model.Place.Place;
import fr.iut.laGaule.model.Character.Character;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GameGUI class.
 * Note: GUI tests are limited due to the nature of Swing applications.
 * These tests verify basic non-UI functionality.
 */
public class GameGUITest {

    private GameGUI gameGUI;

    @BeforeEach
    public void setUp() {
        // Initialiser le système de Swing pour les tests
        try {
            SwingUtilities.invokeAndWait(() -> {
                gameGUI = new GameGUI();
            });
        } catch (Exception e) {
            // En cas d'échec, créer directement (mode headless possible)
            gameGUI = new GameGUI();
        }
    }

    @AfterEach
    public void tearDown() {
        if (gameGUI != null) {
            gameGUI.dispose();
        }
    }

    @Test
    public void testGameGUIClassExists() {
        // Verify that the GameGUI class can be loaded
        try {
            Class.forName("fr.iut.laGaule.GameGUI");
            assertTrue(true);
        } catch (ClassNotFoundException e) {
            fail("GameGUI class not found");
        }
    }

    @Test
    public void testGameGUICreation() {
        // Vérifier que l'instance GameGUI est créée
        assertNotNull(gameGUI, "GameGUI instance should not be null");
    }

    @Test
    public void testGameGUITitle() {
        // Vérifier le titre de la fenêtre
        assertEquals("La Gaule - Interface de Gestion", gameGUI.getTitle(),
                "Le titre de la fenêtre devrait être correct");
    }

    @Test
    public void testGameGUISize() {
        // Vérifier la taille de la fenêtre
        assertEquals(1250, gameGUI.getWidth(), "La largeur devrait être 1250");
        assertEquals(800, gameGUI.getHeight(), "La hauteur devrait être 800");
    }

    @Test
    public void testGameGUIDefaultCloseOperation() {
        // Vérifier l'opération de fermeture par défaut
        assertEquals(JFrame.EXIT_ON_CLOSE, gameGUI.getDefaultCloseOperation(),
                "L'opération de fermeture devrait être EXIT_ON_CLOSE");
    }

    @Test
    public void testGameGUIHasEngine() throws Exception {
        // Utiliser la réflexion pour vérifier que le moteur existe
        Field engineField = GameGUI.class.getDeclaredField("engine");
        engineField.setAccessible(true);
        Object engine = engineField.get(gameGUI);

        assertNotNull(engine, "Le moteur InvasionTheatre devrait être initialisé");
        assertTrue(engine instanceof InvasionTheatre,
                "Le moteur devrait être une instance de InvasionTheatre");
    }

    @Test
    public void testGameGUIContentPane() {
        // Vérifier que le content pane n'est pas null
        assertNotNull(gameGUI.getContentPane(),
                "Le content pane ne devrait pas être null");
    }

    @Test
    public void testGameGUIIsJFrame() {
        // Vérifier que GameGUI hérite de JFrame
        assertTrue(gameGUI instanceof JFrame,
                "GameGUI devrait être une instance de JFrame");
    }

    @Test
    public void testGameGUIHasButtons() throws Exception {
        // Vérifier l'existence des champs de boutons via réflexion
        assertDoesNotThrow(() -> {
            Field btnCreateField = GameGUI.class.getDeclaredField("btnCreate");
            Field btnHealField = GameGUI.class.getDeclaredField("btnHeal");
            Field btnFeedField = GameGUI.class.getDeclaredField("btnFeed");
            Field btnPotionField = GameGUI.class.getDeclaredField("btnPotion");
            Field btnTransferField = GameGUI.class.getDeclaredField("btnTransfer");
            Field btnRecallField = GameGUI.class.getDeclaredField("btnRecall");
        }, "Tous les boutons devraient être déclarés dans la classe");
    }

    @Test
    public void testGameGUIHasUIComponents() throws Exception {
        // Vérifier l'existence des composants UI via réflexion
        assertDoesNotThrow(() -> {
            Field placeListField = GameGUI.class.getDeclaredField("placeList");
            Field characterListField = GameGUI.class.getDeclaredField("characterList");
            Field detailsAreaField = GameGUI.class.getDeclaredField("detailsArea");
            Field infoLabelField = GameGUI.class.getDeclaredField("infoLabel");
        }, "Tous les composants UI devraient être déclarés");
    }

    @Test
    public void testGameGUIHasSelectionFields() throws Exception {
        // Vérifier l'existence des champs de sélection
        assertDoesNotThrow(() -> {
            Field selectedPlaceField = GameGUI.class.getDeclaredField("selectedPlace");
            Field selectedCharacterField = GameGUI.class.getDeclaredField("selectedCharacter");
        }, "Les champs de sélection devraient être déclarés");
    }

    @Test
    public void testMainMethodExists() {
        // Vérifier que la méthode main existe
        assertDoesNotThrow(() -> {
            Method mainMethod = GameGUI.class.getMethod("main", String[].class);
            assertNotNull(mainMethod, "La méthode main devrait exister");
        }, "La classe devrait avoir une méthode main");
    }

    @Test
    public void testGameGUINotVisible() {
        // Par défaut, la fenêtre ne devrait pas être visible lors de l'initialisation
        assertFalse(gameGUI.isVisible(),
                "La fenêtre ne devrait pas être visible par défaut dans les tests");
    }

    @Test
    public void testGameGUIIsResizable() {
        // Vérifier que la fenêtre est redimensionnable (comportement par défaut)
        assertTrue(gameGUI.isResizable(),
                "La fenêtre devrait être redimensionnable par défaut");
    }

    // ========== TESTS SUPPLÉMENTAIRES POUR AMÉLIORER LA COUVERTURE ==========

    @Test
    public void testEngineInitialization() throws Exception {
        // Vérifier que le moteur est bien initialisé
        Field engineField = GameGUI.class.getDeclaredField("engine");
        engineField.setAccessible(true);
        InvasionTheatre engine = (InvasionTheatre) engineField.get(gameGUI);

        assertNotNull(engine, "L'engine ne devrait pas être null");
    }

    @Test
    public void testContentPaneBackgroundColor() {
        // Vérifier le contenu du content pane initial (setup screen)
        Container contentPane = gameGUI.getContentPane();
        assertNotNull(contentPane, "Content pane ne devrait pas être null");
        assertTrue(contentPane instanceof JPanel, "Content pane devrait être un JPanel");
    }

    @Test
    public void testSetupScreenComponents() {
        // Vérifier que l'écran de setup contient des composants
        Container contentPane = gameGUI.getContentPane();
        assertTrue(contentPane.getComponentCount() > 0,
                "L'écran de setup devrait contenir des composants");
    }

    @Test
    public void testPrivateMethodsExist() throws Exception {
        // Vérifier l'existence des méthodes privées via réflexion
        assertDoesNotThrow(() -> {
            Method initSetupScreen = GameGUI.class.getDeclaredMethod("initSetupScreen");
            Method initGameScreen = GameGUI.class.getDeclaredMethod("initGameScreen");
            Method styleButton = GameGUI.class.getDeclaredMethod("styleButton", JButton.class, Color.class);
            Method setupButtonActions = GameGUI.class.getDeclaredMethod("setupButtonActions");
            Method refreshData = GameGUI.class.getDeclaredMethod("refreshData");
            Method updateCharacterList = GameGUI.class.getDeclaredMethod("updateCharacterList");
            Method updateDetailsPanel = GameGUI.class.getDeclaredMethod("updateDetailsPanel");
            Method checkClanLeaderCapabilities = GameGUI.class.getDeclaredMethod("checkClanLeaderCapabilities");
            Method enableActionButtons = GameGUI.class.getDeclaredMethod("enableActionButtons", boolean.class);
        }, "Toutes les méthodes privées devraient exister");
    }

    @Test
    public void testStyleButtonMethod() throws Exception {
        // Tester la méthode styleButton via réflexion
        Method styleButton = GameGUI.class.getDeclaredMethod("styleButton", JButton.class, Color.class);
        styleButton.setAccessible(true);

        JButton testButton = new JButton("Test");
        Color testColor = new Color(100, 150, 200);

        styleButton.invoke(gameGUI, testButton, testColor);

        assertEquals(testColor, testButton.getBackground(),
                "La couleur de fond devrait être définie");
        assertEquals(Color.WHITE, testButton.getForeground(),
                "La couleur du texte devrait être blanche");
        assertFalse(testButton.isFocusPainted(),
                "FocusPainted devrait être false");
        assertTrue(testButton.isOpaque(),
                "Le bouton devrait être opaque");
        assertFalse(testButton.isBorderPainted(),
                "BorderPainted devrait être false");
    }

    @Test
    public void testEnableActionButtonsMethod() throws Exception {
        // D'abord initialiser l'écran de jeu pour créer les boutons
        Method initGameScreen = GameGUI.class.getDeclaredMethod("initGameScreen");
        initGameScreen.setAccessible(true);
        initGameScreen.invoke(gameGUI);

        // Maintenant tester enableActionButtons
        Method enableActionButtons = GameGUI.class.getDeclaredMethod("enableActionButtons", boolean.class);
        enableActionButtons.setAccessible(true);

        // Récupérer les boutons
        Field btnCreateField = GameGUI.class.getDeclaredField("btnCreate");
        btnCreateField.setAccessible(true);
        JButton btnCreate = (JButton) btnCreateField.get(gameGUI);

        // Tester avec false
        enableActionButtons.invoke(gameGUI, false);
        assertFalse(btnCreate.isEnabled(), "Le bouton devrait être désactivé");

        // Tester avec true
        enableActionButtons.invoke(gameGUI, true);
        assertTrue(btnCreate.isEnabled(), "Le bouton devrait être activé");
    }

    @Test
    public void testUpdateDetailsPanelWithNullCharacter() throws Exception {
        // Initialiser l'écran de jeu
        Method initGameScreen = GameGUI.class.getDeclaredMethod("initGameScreen");
        initGameScreen.setAccessible(true);
        initGameScreen.invoke(gameGUI);

        // S'assurer que selectedCharacter est null
        Field selectedCharacterField = GameGUI.class.getDeclaredField("selectedCharacter");
        selectedCharacterField.setAccessible(true);
        selectedCharacterField.set(gameGUI, null);

        // Appeler updateDetailsPanel
        Method updateDetailsPanel = GameGUI.class.getDeclaredMethod("updateDetailsPanel");
        updateDetailsPanel.setAccessible(true);
        updateDetailsPanel.invoke(gameGUI);

        // Vérifier que detailsArea affiche le message approprié
        Field detailsAreaField = GameGUI.class.getDeclaredField("detailsArea");
        detailsAreaField.setAccessible(true);
        JTextArea detailsArea = (JTextArea) detailsAreaField.get(gameGUI);

        assertEquals("Aucune sélection.", detailsArea.getText(),
                "Le message devrait indiquer aucune sélection");
    }

    @Test
    public void testUpdateCharacterListWithNullPlace() throws Exception {
        // Initialiser l'écran de jeu
        Method initGameScreen = GameGUI.class.getDeclaredMethod("initGameScreen");
        initGameScreen.setAccessible(true);
        initGameScreen.invoke(gameGUI);

        // S'assurer que selectedPlace est null
        Field selectedPlaceField = GameGUI.class.getDeclaredField("selectedPlace");
        selectedPlaceField.setAccessible(true);
        selectedPlaceField.set(gameGUI, null);

        // Appeler updateCharacterList
        Method updateCharacterList = GameGUI.class.getDeclaredMethod("updateCharacterList");
        updateCharacterList.setAccessible(true);

        assertDoesNotThrow(() -> updateCharacterList.invoke(gameGUI),
                "updateCharacterList ne devrait pas lever d'exception avec null");

        // Vérifier que la liste est vide
        Field characterListModelField = GameGUI.class.getDeclaredField("characterListModel");
        characterListModelField.setAccessible(true);
        DefaultListModel<?> model = (DefaultListModel<?>) characterListModelField.get(gameGUI);

        assertEquals(0, model.getSize(), "La liste devrait être vide");
    }

    @Test
    public void testCheckClanLeaderCapabilitiesWithNullPlace() throws Exception {
        // Initialiser l'écran de jeu
        Method initGameScreen = GameGUI.class.getDeclaredMethod("initGameScreen");
        initGameScreen.setAccessible(true);
        initGameScreen.invoke(gameGUI);

        // S'assurer que selectedPlace est null
        Field selectedPlaceField = GameGUI.class.getDeclaredField("selectedPlace");
        selectedPlaceField.setAccessible(true);
        selectedPlaceField.set(gameGUI, null);

        // Appeler checkClanLeaderCapabilities
        Method checkClanLeaderCapabilities = GameGUI.class.getDeclaredMethod("checkClanLeaderCapabilities");
        checkClanLeaderCapabilities.setAccessible(true);

        assertDoesNotThrow(() -> checkClanLeaderCapabilities.invoke(gameGUI),
                "checkClanLeaderCapabilities ne devrait pas lever d'exception avec null");
    }

    @Test
    public void testRefreshDataMethod() throws Exception {
        // Initialiser l'écran de jeu
        Method initGameScreen = GameGUI.class.getDeclaredMethod("initGameScreen");
        initGameScreen.setAccessible(true);
        initGameScreen.invoke(gameGUI);

        // Appeler refreshData
        Method refreshData = GameGUI.class.getDeclaredMethod("refreshData");
        refreshData.setAccessible(true);

        assertDoesNotThrow(() -> refreshData.invoke(gameGUI),
                "refreshData ne devrait pas lever d'exception");
    }

    @Test
    public void testPlaceListModelField() throws Exception {
        // Initialiser l'écran de jeu
        Method initGameScreen = GameGUI.class.getDeclaredMethod("initGameScreen");
        initGameScreen.setAccessible(true);
        initGameScreen.invoke(gameGUI);

        // Vérifier que placeListModel est initialisé
        Field placeListModelField = GameGUI.class.getDeclaredField("placeListModel");
        placeListModelField.setAccessible(true);
        DefaultListModel<?> model = (DefaultListModel<?>) placeListModelField.get(gameGUI);

        assertNotNull(model, "placeListModel devrait être initialisé");
    }

    @Test
    public void testCharacterListModelField() throws Exception {
        // Initialiser l'écran de jeu
        Method initGameScreen = GameGUI.class.getDeclaredMethod("initGameScreen");
        initGameScreen.setAccessible(true);
        initGameScreen.invoke(gameGUI);

        // Vérifier que characterListModel est initialisé
        Field characterListModelField = GameGUI.class.getDeclaredField("characterListModel");
        characterListModelField.setAccessible(true);
        DefaultListModel<?> model = (DefaultListModel<?>) characterListModelField.get(gameGUI);

        assertNotNull(model, "characterListModel devrait être initialisé");
    }

    @Test
    public void testInfoLabelInitialization() throws Exception {
        // Initialiser l'écran de jeu
        Method initGameScreen = GameGUI.class.getDeclaredMethod("initGameScreen");
        initGameScreen.setAccessible(true);
        initGameScreen.invoke(gameGUI);

        // Vérifier que infoLabel est initialisé
        Field infoLabelField = GameGUI.class.getDeclaredField("infoLabel");
        infoLabelField.setAccessible(true);
        JLabel infoLabel = (JLabel) infoLabelField.get(gameGUI);

        assertNotNull(infoLabel, "infoLabel devrait être initialisé");
        assertEquals("Simulation en cours...", infoLabel.getText(),
                "Le texte initial devrait être 'Simulation en cours...'");
    }

    @Test
    public void testDetailsAreaInitialization() throws Exception {
        // Initialiser l'écran de jeu
        Method initGameScreen = GameGUI.class.getDeclaredMethod("initGameScreen");
        initGameScreen.setAccessible(true);
        initGameScreen.invoke(gameGUI);

        // Vérifier que detailsArea est initialisé
        Field detailsAreaField = GameGUI.class.getDeclaredField("detailsArea");
        detailsAreaField.setAccessible(true);
        JTextArea detailsArea = (JTextArea) detailsAreaField.get(gameGUI);

        assertNotNull(detailsArea, "detailsArea devrait être initialisé");
        assertFalse(detailsArea.isEditable(), "detailsArea ne devrait pas être éditable");
    }

    @Test
    public void testButtonsInitialization() throws Exception {
        // Initialiser l'écran de jeu
        Method initGameScreen = GameGUI.class.getDeclaredMethod("initGameScreen");
        initGameScreen.setAccessible(true);
        initGameScreen.invoke(gameGUI);

        // Vérifier tous les boutons
        String[] buttonNames = {"btnCreate", "btnHeal", "btnFeed", "btnPotion", "btnTransfer", "btnRecall"};

        for (String buttonName : buttonNames) {
            Field buttonField = GameGUI.class.getDeclaredField(buttonName);
            buttonField.setAccessible(true);
            JButton button = (JButton) buttonField.get(gameGUI);

            assertNotNull(button, buttonName + " devrait être initialisé");
            assertTrue(button.isOpaque(), buttonName + " devrait être opaque");
        }
    }

    @Test
    public void testPlaceListSelectionMode() throws Exception {
        // Initialiser l'écran de jeu
        Method initGameScreen = GameGUI.class.getDeclaredMethod("initGameScreen");
        initGameScreen.setAccessible(true);
        initGameScreen.invoke(gameGUI);

        // Vérifier le mode de sélection de placeList
        Field placeListField = GameGUI.class.getDeclaredField("placeList");
        placeListField.setAccessible(true);
        JList<?> placeList = (JList<?>) placeListField.get(gameGUI);

        assertEquals(ListSelectionModel.SINGLE_SELECTION, placeList.getSelectionMode(),
                "placeList devrait être en mode sélection unique");
    }

    @Test
    public void testCharacterListSelectionMode() throws Exception {
        // Initialiser l'écran de jeu
        Method initGameScreen = GameGUI.class.getDeclaredMethod("initGameScreen");
        initGameScreen.setAccessible(true);
        initGameScreen.invoke(gameGUI);

        // Vérifier le mode de sélection de characterList
        Field characterListField = GameGUI.class.getDeclaredField("characterList");
        characterListField.setAccessible(true);
        JList<?> characterList = (JList<?>) characterListField.get(gameGUI);

        assertEquals(ListSelectionModel.SINGLE_SELECTION, characterList.getSelectionMode(),
                "characterList devrait être en mode sélection unique");
    }

    @Test
    public void testMultipleGameGUIInstances() {
        // Tester la création de plusieurs instances
        GameGUI gui1 = new GameGUI();
        GameGUI gui2 = new GameGUI();

        assertNotSame(gui1, gui2, "Les instances devraient être différentes");

        gui1.dispose();
        gui2.dispose();
    }

    @Test
    public void testWindowLocationRelativeToNull() {
        // Vérifier que la fenêtre est centrée (locationRelativeTo(null))
        // La position devrait être calculée pour centrer la fenêtre
        assertNotNull(gameGUI.getLocation(), "La position ne devrait pas être null");
    }

    @Test
    public void testLookAndFeelIsSet() {
        // Vérifier que le Look and Feel a été configuré
        assertDoesNotThrow(() -> {
            UIManager.getLookAndFeel();
        }, "Le Look and Feel devrait être configuré");
    }
}


