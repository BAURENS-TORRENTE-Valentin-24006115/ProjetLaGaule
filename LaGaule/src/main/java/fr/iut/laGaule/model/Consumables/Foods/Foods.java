package fr.iut.laGaule.model.Consumables.Foods;

import java.util.ArrayList;
import java.util.Random;

public enum Foods {
    // Nom, Gaulois?, Romain?, Comestible?, Végétarien?

    // --- VIANDES & POISSONS ---
    SANGLIER("Sanglier", true, true, true, false),
    POISSON_FRAIS("Poisson frais", true, false, true, false), // Gaulois aime, Romain non précisé (donc false)
    POISSON_NON_FRAIS("Poisson non frais", false, false, true, false), // Mauvais pour tous
    HOMARD("Homard", false, false, true, false), // Luxe, neutre

    // --- PLANTES & LÉGUMES (Végétarien = true) ---
    GUI("Gui", false, false, false, true), // Non comestible sauf potion
    FRAISES("Fraises", false, false, true, true),
    CAROTTE("Carotte", false, false, true, true),
    NAVET("Navet", false, false, true, true),
    TREFLE_QUATRE_FEUILLES_FRAIS("Trèfle frais", false, false, true, true),
    TREFLE_QUATRE_FEUILLES_PAS_FRAIS("Trèfle pas frais", false, false, false, true),

    // --- ASSAISONNEMENT & LIQUIDES ---
    SEL("Sel", false, false, true, true),
    HUILE_DE_ROCHE("Huile de roche", false, false, true, true),
    JUS_DE_BETTERAVE("Jus de betterave", false, false, true, true),

    // --- SUCRERIES & BOISSONS ---
    MIEL("Miel", false, true, true, true), // Romain aime
    VIN("Vin", true, true, true, true), // Les deux aiment
    HYDROMEL("Hydromel", false, true, true, true), // Romain aime

    // --- SPÉCIAL ---
    LAIT_LICORNE_DEUX_TETES("Lait de licorne", true, false, true, false),
    POILS_IDEFIX("Poils d'Idéfix", false, false, false, false),
    INGREDIENT_SECRET("Ingrédient secret", false, false, true, true);

    private final String name;
    private final boolean isGallicFriendly;
    private final boolean isRomanFriendly;
    private final boolean isComestible;
    private final boolean isVegetarian;

    Foods(String name, boolean isGallicFriendly, boolean isRomanFriendly, boolean isComestible, boolean isVegetarian) {
        this.name = name;
        this.isGallicFriendly = isGallicFriendly;
        this.isRomanFriendly = isRomanFriendly;
        this.isComestible = isComestible;
        this.isVegetarian = isVegetarian;
    }

    public String getName() { return name; }
    public boolean isGallicFriendly() { return isGallicFriendly; }
    public boolean isRomanFriendly() { return isRomanFriendly; }
    public boolean isComestible() { return isComestible; }
    public boolean isVegetarian() { return isVegetarian; }

    public Foods getRandomFood() {
        return values()[(int) (Math.random() * values().length)];
    }

    public ArrayList<Foods> getRandomFoods(int nbOfFood) {
        ArrayList<Foods> randomFoods = new ArrayList<>();
        for (int i = 0; i < nbOfFood; i++) randomFoods.add(getRandomFood());
        return randomFoods;
    }
}