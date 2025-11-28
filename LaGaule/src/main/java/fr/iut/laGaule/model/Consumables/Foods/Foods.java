package fr.iut.laGaule.model.Consumables.Foods;
/**
 * Enum representing various food items with their properties.
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

    Foods(String name, boolean isGallicFriendly, boolean isRomanFriendly, boolean isComestible, boolean isVegetarian) {
        this.name = name;
        this.isGallicFriendly = isGallicFriendly;
        this.isRomanFriendly = isRomanFriendly;
        this.isComestible = isComestible;
        this.isVegetarian = isVegetarian;
    }

    public String getName() {
        return name;
    }

    public boolean isGallicFriendly() {
        return isGallicFriendly;
    }

    public boolean isRomanFriendly() {
        return isRomanFriendly;
    }

    public boolean isComestible() {
        return isComestible;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }
}
