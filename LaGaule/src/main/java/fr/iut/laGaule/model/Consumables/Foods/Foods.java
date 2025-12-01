package fr.iut.laGaule.model.Consumables.Foods;

import java.util.ArrayList;

/**
 * Enum representing various food items with their properties.
 * Each food item has attributes defining whether it's suitable for Gauls or Romans,
 * if it's edible, and if it's vegetarian.
 */
public enum Foods {
    SANGLIER("Sanglier", true, true, true, false),
    POISSON_FRAIS("Poisson frais", true, true, true, false),
    POISSON_NON_FRAIS("Poisson non frais", false, false, false, false),
    HOMARD("Homard", false, false, true, false),
    GUI("Gui", false, false, false, true),
    FRAISES("Fraises", false, false, true, true),
    CAROTTE("Carotte", false, false, true, true),
    SEL("Sel", false, false, true, true),
    TREFLE_QUATRE_FEUILLES_FRAIS("Trèfle à quatre feuilles frais", false, false, true, true),
    TREFLE_QUATRE_FEUILLES_PAS_FRAIS("Trèfle à quatre feuilles pas frais", false, false, false, true),
    HUILE_DE_ROCHE("Huile de roche", false, false, true, true),
    JUS_DE_BETTERAVE("Jus de betterave", false, false, true, true),
    MIEL("Miel", true, true, true, true),
    VIN("Vin", true, true, true, true),
    HYDROMEL("Hydromel", false, true, true, true),
    LAIT_LICORNE_DEUX_TETES("Lait de licorne à deux têtes", true, false, true, false),
    POILS_IDEFIX("Poils d'Idéfix", false, false, false, false),
    INGREDIENT_SECRET("Ingrédient secret", false, false, true, true);

    private final String name;
    private final boolean isGallicFriendly;
    private final boolean isRomanFriendly;
    private final boolean isComestible;
    private final boolean isVegetarian;

    /**
     * Constructs a food item with its properties.
     *
     * @param name the name of the food item
     * @param isGallicFriendly true if the food is suitable for Gauls
     * @param isRomanFriendly true if the food is suitable for Romans
     * @param isComestible true if the food is edible
     * @param isVegetarian true if the food is vegetarian
     */
    Foods(String name, boolean isGallicFriendly, boolean isRomanFriendly, boolean isComestible, boolean isVegetarian) {
        this.name = name;
        this.isGallicFriendly = isGallicFriendly;
        this.isRomanFriendly = isRomanFriendly;
        this.isComestible = isComestible;
        this.isVegetarian = isVegetarian;
    }

    /**
     * Gets the name of the food item.
     *
     * @return the name of the food
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if the food is suitable for Gauls.
     *
     * @return true if the food is Gallic-friendly, false otherwise
     */
    public boolean isGallicFriendly() {
        return isGallicFriendly;
    }

    /**
     * Checks if the food is suitable for Romans.
     *
     * @return true if the food is Roman-friendly, false otherwise
     */
    public boolean isRomanFriendly() {
        return isRomanFriendly;
    }

    /**
     * Checks if the food is edible.
     *
     * @return true if the food is edible, false otherwise
     */
    public boolean isComestible() {
        return isComestible;
    }

    /**
     * Checks if the food is vegetarian.
     *
     * @return true if the food is vegetarian, false otherwise
     */
    public boolean isVegetarian() {
        return isVegetarian;
    }

    /**
     * Gets a random food item from the enum.
     *
     * @return a random Foods enum value
     */
    public Foods getRandomFood() {
        Foods[] foods = Foods.values();
        int randomIndex = (int) (Math.random() * foods.length);
        return foods[randomIndex];
    }

    /**
     * Gets a list of random food items.
     *
     * @param nbOfFood the number of random food items to retrieve
     * @return an ArrayList of random Foods enum values
     */
    public ArrayList<Foods> getRandomFoods(int nbOfFood) {
        ArrayList<Foods> randomFoods = new ArrayList<>();
        for (int i = 0; i < nbOfFood; i++) {
            randomFoods.add(getRandomFood());
        }
        return randomFoods;
    }
}
