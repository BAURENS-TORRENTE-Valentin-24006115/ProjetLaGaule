package fr.iut.laGaule.Consumables.Foods;

public abstract class Foods {
    private String name;
    private boolean isGallicFriendly;
    private boolean isRomanFriendly;
    private boolean isComestible;
    private boolean isVegetarian;

    public Foods(String name, boolean isGallicFriendly, boolean isRomanFriendly, boolean isComestible, boolean isVegetarian) {
        this.name = name;
        this.isGallicFriendly = isGallicFriendly;
        this.isRomanFriendly = isRomanFriendly;
        this.isComestible = isComestible;
        this.isVegetarian = isVegetarian;
    }

    public String getName() {
        return name;
    }


}
