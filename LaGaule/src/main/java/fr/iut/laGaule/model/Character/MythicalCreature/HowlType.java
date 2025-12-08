package fr.iut.laGaule.model.Character.MythicalCreature;

/**
 * Enum representing the different types of howls.
 */
public enum HowlType {
    PACK_AFFILIATION("Pack Affiliation"),
    DOMINATION("Domination"),
    SUBMISSION("Submission"),
    AGGRESSION("Aggression");

    private final String description;

    HowlType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}