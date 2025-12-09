package fr.iut.laGaule;

import fr.iut.laGaule.process.InvasionTheatre;
import fr.iut.laGaule.model.Place.*;
import fr.iut.laGaule.model.Character.Character;
import fr.iut.laGaule.model.Character.ClanLeader;
import fr.iut.laGaule.model.Character.Gaul.Gaul;
import fr.iut.laGaule.model.Character.Roman.Roman;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameGUI extends JFrame {

    private InvasionTheatre engine;

    // UI Components
    private JList<Place> placeList;
    private DefaultListModel<Place> placeListModel;
    private JList<Character> characterList;
    private DefaultListModel<Character> characterListModel;
    private JTextArea detailsArea;
    private JLabel infoLabel;

    // Boutons
    private JButton btnCreate, btnHeal, btnFeed, btnPotion, btnTransfer, btnRecall; // Ajout de btnRecall

    // Sélection
    private Place selectedPlace;
    private Character selectedCharacter;

    public GameGUI() {
        engine = new InvasionTheatre();
        setTitle("La Gaule - Interface de Gestion");
        setSize(1250, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } catch (Exception ignored) {}

        initSetupScreen();
    }

    private void initSetupScreen() {
        // (Code identique à la version précédente pour le setup...)
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.DARK_GRAY);
        JPanel card = new JPanel(new GridLayout(5, 1, 10, 10));
        card.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        card.setBackground(Color.WHITE);

        JTextField txtZones = new JTextField("5");
        JTextField txtChars = new JTextField("20");
        JTextField txtTime = new JTextField("300");
        JButton btnStart = new JButton("LANCER LA SIMULATION");
        btnStart.setBackground(new Color(0, 120, 215));
        btnStart.setForeground(Color.WHITE);
        btnStart.setOpaque(true);

        card.add(new JLabel("Nombre de Zones :")); card.add(txtZones);
        card.add(new JLabel("Nombre de Personnages :")); card.add(txtChars);
        card.add(new JLabel("Durée (secondes) :")); card.add(txtTime);
        card.add(new JLabel("")); card.add(btnStart);
        panel.add(card);
        setContentPane(panel);

        btnStart.addActionListener(e -> {
            try {
                engine.setupSimulation(Integer.parseInt(txtZones.getText()), Integer.parseInt(txtChars.getText()));
                engine.startSimulationInBackground(Integer.parseInt(txtTime.getText()));
                initGameScreen();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage()); }
        });
    }

    private void initGameScreen() {
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBackground(Color.LIGHT_GRAY);

        // Listes et Panels (Identique version précédente)
        placeListModel = new DefaultListModel<>();
        placeList = new JList<>(placeListModel);
        placeList.setCellRenderer(new PlaceListRenderer());
        placeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane placeScroll = new JScrollPane(placeList);
        placeScroll.setBorder(BorderFactory.createTitledBorder(" Lieux "));
        placeScroll.setPreferredSize(new Dimension(300, 0));

        characterListModel = new DefaultListModel<>();
        characterList = new JList<>(characterListModel);
        characterList.setCellRenderer(new CharacterListRenderer());
        characterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane charScroll = new JScrollPane(characterList);
        charScroll.setBorder(BorderFactory.createTitledBorder(" Habitants "));

        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.BOLD, 12));
        JScrollPane detailsScroll = new JScrollPane(detailsArea);
        detailsScroll.setBorder(BorderFactory.createTitledBorder(" Inspecteur "));
        detailsScroll.setPreferredSize(new Dimension(250, 0));

        // --- PANEL ACTIONS ---
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        actionPanel.setBackground(Color.WHITE);

        btnCreate = new JButton("Recruter");
        btnHeal = new JButton("Soigner");
        btnFeed = new JButton("Nourrir");
        btnPotion = new JButton("Potion");
        btnTransfer = new JButton("Transférer");
        btnRecall = new JButton("Rappeler Troupes"); // Nouveau Bouton

        // Styles
        styleButton(btnCreate, new Color(46, 204, 113));
        styleButton(btnHeal, new Color(52, 152, 219));
        styleButton(btnFeed, new Color(230, 126, 34));
        styleButton(btnPotion, new Color(155, 89, 182));
        styleButton(btnTransfer, new Color(231, 76, 60));
        styleButton(btnRecall, new Color(44, 62, 80)); // Bleu foncé pour le rappel

        actionPanel.add(btnCreate);
        actionPanel.add(btnHeal);
        actionPanel.add(btnFeed);
        actionPanel.add(btnPotion);
        actionPanel.add(btnTransfer);
        actionPanel.add(btnRecall); // Ajout au panel

        enableActionButtons(false);

        infoLabel = new JLabel("Simulation en cours...");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        infoLabel.setOpaque(true);
        infoLabel.setBackground(Color.WHITE);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        mainPanel.add(infoLabel, BorderLayout.NORTH);
        mainPanel.add(placeScroll, BorderLayout.WEST);
        mainPanel.add(charScroll, BorderLayout.CENTER);
        mainPanel.add(detailsScroll, BorderLayout.EAST);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        revalidate();

        // Listeners
        placeList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedPlace = placeList.getSelectedValue();
                updateCharacterList();
                checkClanLeaderCapabilities();
            }
        });

        characterList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedCharacter = characterList.getSelectedValue();
                updateDetailsPanel();
            }
        });

        setupButtonActions();

        Timer timer = new Timer(500, e -> {
            if (!engine.isSimulationActive()) {
                infoLabel.setText("FIN DE LA SIMULATION");
                infoLabel.setBackground(Color.RED);
                infoLabel.setForeground(Color.WHITE);
                ((Timer)e.getSource()).stop();
            }
            refreshData();
        });
        timer.start();
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
    }

    private void setupButtonActions() {
        btnCreate.addActionListener(e -> {
            String[] types = {"gaul_merchant", "roman_legionary"};
            String type = (String) JOptionPane.showInputDialog(this, "Type ?", "Recrutement", JOptionPane.QUESTION_MESSAGE, null, types, types[0]);
            if(type != null) selectedPlace.getClanLeader().createCharacter(type, "Nouveau", "M", 1.8, 20, 10, 10);
        });

        btnHeal.addActionListener(e -> { if(selectedPlace.getClanLeader() != null) selectedPlace.getClanLeader().healAllCharacters(10); });

        // --- TRANSFERT (AMÉLIORÉ AVEC NOMS SEULEMENT) ---
        btnTransfer.addActionListener(e -> {
            if (selectedCharacter == null || selectedPlace == null) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un personnage d'abord !");
                return;
            }
            ClanLeader leader = selectedPlace.getClanLeader();

            // 1. Création de la liste des noms (Strings)
            List<Place> destinations = new ArrayList<>(engine.getPlaces());
            destinations.remove(selectedPlace); // On ne transfère pas vers soi-même

            String[] placeNames = destinations.stream().map(Place::getName).toArray(String[]::new);

            // 2. Affichage du Popup avec des Strings (plus propre)
            String targetName = (String) JOptionPane.showInputDialog(
                    this, "Vers où transférer " + selectedCharacter.getName() + " ?",
                    "Transfert", JOptionPane.QUESTION_MESSAGE, null, placeNames, placeNames[0]);

            if (targetName != null) {
                // 3. Retrouver l'objet Place à partir du nom
                Place target = destinations.stream()
                        .filter(p -> p.getName().equals(targetName))
                        .findFirst().orElse(null);

                if (target != null) {
                    leader.transferCharacter(selectedCharacter, target);

                    // Force update mémoire GUI
                    synchronized(selectedPlace) { selectedPlace.removeCharacter(selectedCharacter); }
                    synchronized(target) { target.addCharacter(selectedCharacter); }
                    selectedCharacter.setPlace(target);

                    refreshData();
                    JOptionPane.showMessageDialog(this, "Transféré vers " + targetName);
                }
            }
        });

        // --- RAPPEL (NOUVELLE FONCTIONNALITÉ) ---
        btnRecall.addActionListener(e -> {
            if (selectedPlace == null || selectedPlace.getClanLeader() == null) return;

            ClanLeader leader = selectedPlace.getClanLeader();
            boolean isGaulLeader = selectedPlace.getName().toLowerCase().contains("gaul") || selectedPlace instanceof GaulVillage;

            // 1. Trouver les candidats au rappel (Qui sont ailleurs)
            List<Character> outsiders = new ArrayList<>();
            List<Place> locations = new ArrayList<>(); // Pour savoir où ils sont

            for (Place p : engine.getPlaces()) {
                if (p == selectedPlace) continue; // On ne scanne pas le village actuel

                // Copie pour éviter concurrence
                List<Character> charsInPlace = new ArrayList<>(p.getCharacter());
                for (Character c : charsInPlace) {
                    // Filtrage : Un chef Gaulois ne rappelle que des Gaulois
                    boolean isGaulChar = c instanceof Gaul; // Ou checker le nom/classe
                    boolean isRomanChar = c instanceof Roman;

                    if ((isGaulLeader && isGaulChar) || (!isGaulLeader && isRomanChar)) {
                        outsiders.add(c);
                        locations.add(p);
                    }
                }
            }

            if (outsiders.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun de vos hommes n'est en dehors du village.");
                return;
            }

            // 2. Création de la liste d'affichage
            String[] choices = new String[outsiders.size()];
            for (int i = 0; i < outsiders.size(); i++) {
                choices[i] = outsiders.get(i).getName() + " (Actuellement à : " + locations.get(i).getName() + ")";
            }

            // 3. Popup de choix
            String choice = (String) JOptionPane.showInputDialog(
                    this, "Qui voulez-vous rappeler au bercail ?",
                    "Rappel des Troupes", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

            if (choice != null) {
                // Retrouver l'index choisi
                int index = -1;
                for(int i=0; i<choices.length; i++) if(choices[i].equals(choice)) index = i;

                if (index != -1) {
                    Character targetChar = outsiders.get(index);
                    Place currentLoc = locations.get(index);

                    // Action de Rappel
                    leader.recallCharacter(targetChar, currentLoc);
                    refreshData();
                    JOptionPane.showMessageDialog(this, targetChar.getName() + " est de retour !");
                }
            }
        });
    }

    private void refreshData() {
        int pIdx = placeList.getSelectedIndex();
        int cIdx = characterList.getSelectedIndex();

        placeListModel.clear();
        for (Place p : engine.getPlaces()) placeListModel.addElement(p);

        if (pIdx >= 0 && pIdx < placeListModel.getSize()) placeList.setSelectedIndex(pIdx);

        updateCharacterList();

        if (cIdx >= 0 && cIdx < characterListModel.getSize()) characterList.setSelectedIndex(cIdx);

        updateDetailsPanel();
    }

    private void updateCharacterList() {
        characterListModel.clear();
        if (selectedPlace != null) {
            try {
                ArrayList<Character> chars = new ArrayList<>(selectedPlace.getCharacter());
                for (Character c : chars) characterListModel.addElement(c);
            } catch (Exception e) {}
        }
    }

    private void updateDetailsPanel() {
        if (selectedCharacter != null) {
            detailsArea.setText(
                    "Nom   : " + selectedCharacter.getName() + "\n" +
                            "Classe: " + selectedCharacter.getClass().getSimpleName() + "\n" +
                            "Lieu  : " + selectedCharacter.getPlace() + "\n" +
                            "Santé : " + selectedCharacter.getHealth() + " / 100\n" +
                            "Force : " + selectedCharacter.getStrength() + "\n" +
                            "Endu. : " + selectedCharacter.getEndurance()
            );
        } else {
            detailsArea.setText("Aucune sélection.");
        }
    }

    private void checkClanLeaderCapabilities() {
        if (selectedPlace != null && selectedPlace.getClanLeader() != null) {
            enableActionButtons(true);
            infoLabel.setText("QG : " + selectedPlace.getName() + " | Chef : " + selectedPlace.getClanLeader().getName());
            infoLabel.setBackground(new Color(220, 255, 220)); // Fond vert clair
            infoLabel.setForeground(new Color(0, 100, 0));
        } else {
            enableActionButtons(false);
            if (selectedPlace != null) {
                infoLabel.setText("Zone : " + selectedPlace.getName());
                infoLabel.setBackground(Color.WHITE);
                infoLabel.setForeground(Color.BLACK);
            }
        }
    }

    private void enableActionButtons(boolean enable) {
        btnCreate.setEnabled(enable);
        btnHeal.setEnabled(enable);
        btnFeed.setEnabled(enable);
        btnPotion.setEnabled(enable);
        btnTransfer.setEnabled(enable);
        btnRecall.setEnabled(enable);
    }

    // --- RENDERERS ---

    class PlaceListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            panel.setOpaque(true);

            if (value instanceof Place) {
                Place p = (Place) value;
                JLabel icon = new JLabel("  ");
                icon.setOpaque(true);
                icon.setPreferredSize(new Dimension(15, 15));

                // Code couleur simple
                if (p instanceof GaulVillage) icon.setBackground(new Color(46, 204, 113));
                else if (p instanceof RomanFortifiedCamp || p instanceof RomanCity) icon.setBackground(new Color(192, 57, 43));
                else icon.setBackground(Color.GRAY);

                JLabel label = new JLabel(p.getName() + " (" + p.getCharacter().size() + ")");
                label.setFont(new Font("SansSerif", Font.PLAIN, 14));
                label.setOpaque(false);

                panel.add(icon, BorderLayout.WEST);
                panel.add(label, BorderLayout.CENTER);

                if (isSelected) {
                    panel.setBackground(new Color(0, 120, 215));
                    label.setForeground(Color.WHITE);
                } else {
                    panel.setBackground(Color.WHITE);
                    label.setForeground(Color.BLACK);
                }
            }
            return panel;
        }
    }

    class CharacterListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            panel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
            panel.setOpaque(true);

            if (value instanceof Character) {
                Character c = (Character) value;
                JLabel label = new JLabel(c.getName());
                label.setOpaque(false);

                JProgressBar hp = new JProgressBar(0, 100);
                hp.setValue(c.getHealth());
                hp.setPreferredSize(new Dimension(60, 10));
                if(c.getHealth() > 50) hp.setForeground(new Color(46, 204, 113));
                else if(c.getHealth() > 20) hp.setForeground(Color.ORANGE);
                else hp.setForeground(Color.RED);

                panel.add(label, BorderLayout.CENTER);
                panel.add(hp, BorderLayout.EAST);

                if (isSelected) {
                    panel.setBackground(new Color(0, 120, 215));
                    label.setForeground(Color.WHITE);
                } else {
                    panel.setBackground(Color.WHITE);
                    label.setForeground(Color.BLACK);
                }
            }
            return panel;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameGUI().setVisible(true));
    }
}