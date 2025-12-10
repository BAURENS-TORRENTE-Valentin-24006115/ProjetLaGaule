package fr.iut.laGaule;

import fr.iut.laGaule.process.InvasionTheatre;
import fr.iut.laGaule.model.Place.*;
import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Gaul.Gaul;
import fr.iut.laGaule.model.Character.Gaul.Druid; // Import nécessaire pour la potion
import fr.iut.laGaule.model.Character.Roman.Roman;
import fr.iut.laGaule.model.Consumables.Foods.Foods;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameGUI extends JFrame {

    private InvasionTheatre engine;
    private Timer gameTimer;

    // --- COMPOSANTS UI ---
    private JList<Place> placeList;
    private DefaultListModel<Place> placeListModel;
    private JList<Character> characterList;
    private DefaultListModel<Character> characterListModel;
    private JTextArea detailsArea;
    private JLabel infoLabel;

    // --- DÉCLARATION DES BOUTONS (Indispensable ici pour être vus partout) ---
    private JButton btnCreate, btnHeal, btnFeed, btnPotion, btnTransfer, btnRecall;

    // --- SÉLECTION ACTUELLE ---
    private Place selectedPlace;
    private Character selectedCharacter;
    private boolean isInspectPlace = false; // Pour savoir si on affiche un lieu ou un perso

    public GameGUI() {
        engine = new InvasionTheatre();
        setTitle("La Gaule - Interface de Gestion");
        setSize(1250, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } catch (Exception ignored) {}

        initSetupScreen();
    }

    // --- 1. ÉCRAN DE CONFIGURATION ---
    private void initSetupScreen() {
        getContentPane().removeAll();
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.DARK_GRAY);

        JPanel card = new JPanel(new GridLayout(6, 1, 10, 10));
        card.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        card.setBackground(Color.WHITE);

        JTextField txtZones = new JTextField("5");
        JTextField txtChars = new JTextField("20");
        JTextField txtTime = new JTextField("300");
        JButton btnStart = new JButton("LANCER LA SIMULATION");
        styleButton(btnStart, new Color(0, 120, 215));

        card.add(new JLabel("Nombre de Zones :")); card.add(txtZones);
        card.add(new JLabel("Nombre de Personnages :")); card.add(txtChars);
        card.add(new JLabel("Durée (secondes) :")); card.add(txtTime);
        card.add(new JLabel("")); card.add(btnStart);

        panel.add(card);
        setContentPane(panel);
        revalidate(); repaint();

        btnStart.addActionListener(e -> {
            try {
                // Lancement du moteur
                engine.setupSimulation(Integer.parseInt(txtZones.getText()), Integer.parseInt(txtChars.getText()));
                engine.startSimulationInBackground(Integer.parseInt(txtTime.getText()));
                // Passage à l'écran de jeu
                initGameScreen();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        });
    }

    // --- 2. ÉCRAN DE JEU ---
    private void initGameScreen() {
        getContentPane().removeAll();
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBackground(Color.LIGHT_GRAY);

        // A. LISTE LIEUX
        placeListModel = new DefaultListModel<>();
        placeList = new JList<>(placeListModel);
        placeList.setCellRenderer(new PlaceListRenderer());
        placeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane placeScroll = new JScrollPane(placeList);
        placeScroll.setBorder(BorderFactory.createTitledBorder(" Lieux "));
        placeScroll.setPreferredSize(new Dimension(280, 0));

        // B. LISTE PERSONNAGES
        characterListModel = new DefaultListModel<>();
        characterList = new JList<>(characterListModel);
        characterList.setCellRenderer(new CharacterListRenderer());
        characterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane charScroll = new JScrollPane(characterList);
        charScroll.setBorder(BorderFactory.createTitledBorder(" Habitants "));

        // C. INSPECTEUR
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.BOLD, 12));
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        JScrollPane detailsScroll = new JScrollPane(detailsArea);
        detailsScroll.setBorder(BorderFactory.createTitledBorder(" Inspecteur "));
        detailsScroll.setPreferredSize(new Dimension(250, 0));
        detailsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // D. BARRE D'ACTIONS
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        actionPanel.setBackground(Color.WHITE);

        // Initialisation des boutons (C'est ici qu'on les crée)
        btnCreate = new JButton("Recruter");
        btnHeal = new JButton("Soigner");
        btnFeed = new JButton("Nourrir");
        btnPotion = new JButton("Potion");
        btnTransfer = new JButton("Transférer");
        btnRecall = new JButton("Rappeler");

        // Styles
        styleButton(btnCreate, new Color(46, 204, 113));
        styleButton(btnHeal, new Color(52, 152, 219));
        styleButton(btnFeed, new Color(230, 126, 34));
        styleButton(btnPotion, new Color(155, 89, 182));
        styleButton(btnTransfer, new Color(231, 76, 60));
        styleButton(btnRecall, new Color(44, 62, 80));

        // Ajout au panel
        actionPanel.add(btnCreate); actionPanel.add(btnHeal); actionPanel.add(btnFeed);
        actionPanel.add(btnPotion); actionPanel.add(btnTransfer); actionPanel.add(btnRecall);

        enableActionButtons(false); // Désactivés par défaut

        // E. HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        JButton btnMenu = new JButton(" MENU ");
        styleButton(btnMenu, new Color(192, 57, 43));
        btnMenu.addActionListener(e -> goToMenu());

        infoLabel = new JLabel("Chargement...");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        header.add(btnMenu, BorderLayout.WEST);
        header.add(infoLabel, BorderLayout.CENTER);

        // Assemblage
        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(placeScroll, BorderLayout.WEST);
        mainPanel.add(charScroll, BorderLayout.CENTER);
        mainPanel.add(detailsScroll, BorderLayout.EAST);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        revalidate(); repaint();

        // Listeners de sélection
        placeList.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) {
                selectedPlace = placeList.getSelectedValue();
                if (selectedPlace != null) {
                    isInspectPlace = true;
                    characterList.clearSelection();
                    selectedCharacter = null;
                }
                updateCharacterList();
                checkClanLeaderCapabilities();
                updateInspector();
            }
        });

        characterList.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) {
                selectedCharacter = characterList.getSelectedValue();
                if (selectedCharacter != null) {
                    isInspectPlace = false;
                    updateInspector();
                }
            }
        });

        // Appel de la configuration des actions des boutons
        setupButtonActions();

        // Timer de rafraîchissement
        gameTimer = new Timer(500, e -> {
            refreshData();
            if (engine.isSimulationActive()) {
                int remaining = engine.getRemainingTime();
                String timeStr = String.format("%02d:%02d", remaining / 60, remaining % 60);
                infoLabel.setText("TEMPS: " + timeStr + "  |  POPULATION: " + engine.getTotalPopulation());
                infoLabel.setForeground(remaining < 10 ? Color.RED : Color.BLACK);
            } else {
                gameTimer.stop();
                JOptionPane.showMessageDialog(this, engine.getEndMessage(), "Fin", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        gameTimer.start();
    }

    // --- 3. LOGIQUE DES BOUTONS ---
    private void setupButtonActions() {
        // RECRUTER
        btnCreate.addActionListener(e -> {
            if (selectedPlace == null || selectedPlace.getClanLeader() == null) return;
            String[] types = {"gaul_merchant", "gaul_druid", "roman_legionary", "roman_general"};
            String type = (String) JOptionPane.showInputDialog(this, "Qui recruter ?", "Recrutement", JOptionPane.QUESTION_MESSAGE, null, types, types[0]);
            if (type != null) {
                selectedPlace.getClanLeader().createCharacter(type, "Recrue " + type, "M", 1.8, 20, 10, 10);
                refreshData(); // Refresh immédiat pour voir le perso
            }
        });

        // SOIGNER
        btnHeal.addActionListener(e -> {
            if (selectedPlace != null && selectedPlace.getClanLeader() != null) {
                selectedPlace.getClanLeader().healAllCharacters(20);
                JOptionPane.showMessageDialog(this, "Tous les personnages ont été soignés !");
            }
        });

        // NOURRIR
        btnFeed.addActionListener(e -> {
            // 1. Vérifications
            if (selectedCharacter == null || selectedPlace == null) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un personnage et un lieu !");
                return;
            }
            if (selectedPlace.getFood() == null || selectedPlace.getFood().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Le garde-manger est vide !");
                return;
            }

            // 2. Pioche aléatoire (SANS retirer tout de suite)
            List<Foods> stock = selectedPlace.getFood();
            int idx = (int)(Math.random() * stock.size());
            Foods foodItem = stock.get(idx);

            // 3. Tentative
            boolean aMange = selectedCharacter.eat(foodItem);

            // 4. Résultat
            if (aMange) {
                // IL A MANGÉ : On retire l'item du stock
                stock.remove(idx);
                refreshData(); // Met à jour l'affichage
                // Petit feedback optionnel (peut être retiré si trop intrusif)
                // JOptionPane.showMessageDialog(this, selectedCharacter.getName() + " a mangé " + foodItem.getName());
            } else {
                // IL A REFUSÉ : On ne touche pas au stock
                refreshData(); // Juste pour afficher l'action "Refuse de manger..." dans l'inspecteur
                JOptionPane.showMessageDialog(this,
                        selectedCharacter.getName() + " refuse de manger " + foodItem.getName() + ".\n(Régime alimentaire incompatible)",
                        "Refus",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        // POTION (NOUVEAU)
        btnPotion.addActionListener(e -> {
            if (selectedCharacter == null || selectedPlace == null) return;

            // On cherche un druide dans le lieu
            boolean druidPresent = selectedPlace.getCharacter().stream().anyMatch(c -> c instanceof Druid);

            if (druidPresent) {
                selectedCharacter.drinkPotion(10); // Augmente la magie
                selectedCharacter.setLastAction("A bu de la potion magique ! ✨");
                refreshData();
                JOptionPane.showMessageDialog(this, selectedCharacter.getName() + " est maintenant invincible !");
            } else {
                JOptionPane.showMessageDialog(this, "Il faut un Druide dans la zone pour faire de la potion !");
            }
        });

        // TRANSFERT
        btnTransfer.addActionListener(e -> {
            if (selectedCharacter == null || selectedPlace == null) return;

            // Liste des destinations possibles
            Place[] destinations = engine.getPlaces().stream().filter(p -> p != selectedPlace).toArray(Place[]::new);
            String[] names = new String[destinations.length];
            for(int i=0; i<destinations.length; i++) names[i] = destinations[i].getName();

            String choice = (String) JOptionPane.showInputDialog(this, "Destination ?", "Transfert", JOptionPane.QUESTION_MESSAGE, null, names, names[0]);
            if (choice != null) {
                Place target = null;
                for(Place p : destinations) if(p.getName().equals(choice)) target = p;

                if (target != null) {
                    boolean success = selectedPlace.getClanLeader().transferCharacter(selectedCharacter, target);
                    if (success) {
                        refreshData();
                        JOptionPane.showMessageDialog(this, "Transfert réussi vers " + target.getName());
                    } else {
                        JOptionPane.showMessageDialog(this, "Transfert refusé (Zone interdite).");
                    }
                }
            }
        });

        // RAPPEL
        btnRecall.addActionListener(e -> {
            if (selectedPlace == null || selectedPlace.getClanLeader() == null) return;
            ClanLeader leader = selectedPlace.getClanLeader();
            boolean isGaulLeader = (selectedPlace instanceof GaulVillage);

            List<Character> outsiders = new ArrayList<>();
            List<Place> locations = new ArrayList<>();

            for (Place p : engine.getPlaces()) {
                if (p == selectedPlace) continue;
                try {
                    for (Character c : new ArrayList<>(p.getCharacter())) {
                        if ((isGaulLeader && c instanceof Gaul) || (!isGaulLeader && c instanceof Roman)) {
                            outsiders.add(c);
                            locations.add(p);
                        }
                    }
                } catch(Exception ignored){}
            }

            if (outsiders.isEmpty()) { JOptionPane.showMessageDialog(this, "Personne à rappeler."); return; }

            String[] choices = new String[outsiders.size()];
            for(int i=0; i<outsiders.size(); i++) choices[i] = outsiders.get(i).getName() + " (@ " + locations.get(i).getName() + ")";

            String picked = (String) JOptionPane.showInputDialog(this, "Qui ?", "Rappel", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
            if (picked != null) {
                for(int i=0; i<choices.length; i++) if(choices[i].equals(picked)) leader.recallCharacter(outsiders.get(i), locations.get(i));
                refreshData();
            }
        });
    }

    // --- OUTILS ---
    private void goToMenu() {
        if (gameTimer != null) gameTimer.stop();
        engine.stopSimulation();
        initSetupScreen();
    }

    private void refreshData() {
        int pIdx = placeList.getSelectedIndex();
        int cIdx = characterList.getSelectedIndex();

        placeListModel.clear();
        for (Place p : engine.getPlaces()) placeListModel.addElement(p);

        if (pIdx >= 0 && pIdx < placeListModel.getSize()) placeList.setSelectedIndex(pIdx);
        updateCharacterList();
        if (cIdx >= 0 && cIdx < characterListModel.getSize()) characterList.setSelectedIndex(cIdx);
        updateInspector();
    }

    private void updateCharacterList() {
        characterListModel.clear();
        if (selectedPlace != null) {
            try { new ArrayList<>(selectedPlace.getCharacter()).forEach(characterListModel::addElement); } catch (Exception ignored) {}
        }
    }

    private void updateInspector() {
        if (isInspectPlace && selectedPlace != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("=== ZONE : ").append(selectedPlace.getName()).append(" ===\n\n");

            if (selectedPlace.getFood() != null && !selectedPlace.getFood().isEmpty()) {
                Map<String, Long> counts = selectedPlace.getFood().stream()
                        .collect(Collectors.groupingBy(Foods::getName, Collectors.counting()));
                sb.append("--- GARDE-MANGER ---\n");
                counts.forEach((name, count) -> sb.append("- ").append(name).append(" x").append(count).append("\n"));
            } else {
                sb.append("Garde-manger vide.\n");
            }
            sb.append("\nPopulation : ").append(selectedPlace.getCharacter().size());
            detailsArea.setText(sb.toString());
            detailsArea.setCaretPosition(0);

        } else if (!isInspectPlace && selectedCharacter != null) {
            String placeName = "Inconnu";
            try {
                Object p = selectedCharacter.getPlace();
                if (p != null) placeName = (p instanceof Place) ? ((Place) p).getName() : p.toString();
            } catch (Exception ignored) {}

            String action = selectedCharacter.getLastAction();
            if (action == null) action = "---";

            String text = "=== INFO ===\nNom: " + selectedCharacter.getName() +
                    "\nClasse: " + selectedCharacter.getClass().getSimpleName() +
                    "\nLieu: " + placeName +
                    "\n\n=== STATS ===\nSanté: " + selectedCharacter.getHealth() +
                    "\nFaim : " + selectedCharacter.getHunger() + // <--- AJOUTEZ LE GETTER SI DISPO
                    "\nForce: " + selectedCharacter.getStrength() +
                    "\n\n=== ACTION ===\n" + action;
            detailsArea.setText(text);
            detailsArea.setCaretPosition(0);
        } else {
            detailsArea.setText("");
        }
    }

    private void checkClanLeaderCapabilities() {
        if (selectedPlace != null && selectedPlace.getClanLeader() != null) enableActionButtons(true);
        else enableActionButtons(false);
    }

    private void enableActionButtons(boolean enable) {
        btnCreate.setEnabled(enable); btnHeal.setEnabled(enable); btnFeed.setEnabled(enable);
        btnPotion.setEnabled(enable); btnTransfer.setEnabled(enable); btnRecall.setEnabled(enable);
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color); btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false); btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setOpaque(true); btn.setBorderPainted(false);
    }

    // --- RENDERERS ---
    class PlaceListRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            panel.setOpaque(true);

            if (value instanceof Place) {
                Place p = (Place) value;
                JLabel icon = new JLabel("  ");
                icon.setOpaque(true);
                icon.setPreferredSize(new Dimension(15, 15));
                if (p instanceof GaulVillage) icon.setBackground(new Color(46, 204, 113));
                else if (p instanceof RomanFortifiedCamp) icon.setBackground(new Color(192, 57, 43));
                else icon.setBackground(Color.GRAY);

                int foodCount = (p.getFood() != null) ? p.getFood().size() : 0;
                String txt = String.format("<html><b>%s</b><br/><span style='font-size:10px;color:gray'>Pop: %d | Food: %d</span></html>",
                        p.getName(), p.getCharacter().size(), foodCount);
                JLabel label = new JLabel(txt);

                panel.add(icon, BorderLayout.WEST);
                panel.add(label, BorderLayout.CENTER);

                if (isSelected) { panel.setBackground(new Color(0, 120, 215)); label.setForeground(Color.WHITE); }
                else { panel.setBackground(Color.WHITE); label.setForeground(Color.BLACK); }
            }
            return panel;
        }
    }

    class CharacterListRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Character) {
                Character c = (Character) value;
                setText(c.getName() + " (" + c.getHealth() + "%)");
                if (c.getHealth() < 30) setForeground(Color.RED); else setForeground(Color.BLACK);
                if (isSelected) setBackground(Color.LIGHT_GRAY);
            }
            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameGUI().setVisible(true));
    }
}