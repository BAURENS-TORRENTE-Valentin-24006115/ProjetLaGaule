package fr.iut.laGaule.model.Character.MythicalCreature;

/**
 * Enum representing the age category of a lycanthrope.
 */
public enum AgeCategory {
    YOUNG("jeune"),
    ADULT("adulte"),
    OLD("vieux");

    private final String label;

    AgeCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}