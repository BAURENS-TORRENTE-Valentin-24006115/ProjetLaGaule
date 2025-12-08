package fr.iut.laGaule.model.Character.MythicalCreature;

/**
 * Represents a howl emitted by a lycanthrope.
 * A howl is a form of communication that conveys various meanings based on its type.
 * Howls are perceived from anywhere and can trigger responses from other lycanthropes.
 */
public class Howl {



    private final Lycanthrope emitter;
    private final HowlType howlType;
    private final long timestamp;

    /**
     * Constructs a new Howl emitted by a lycanthrope.
     *
     * @param emitter the lycanthrope emitting the howl
     * @param howlType the type of howl
     */
    public Howl(Lycanthrope emitter, HowlType howlType) {
        this.emitter = emitter;
        this.howlType = howlType;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Gets the lycanthrope who emitted this howl.
     *
     * @return the emitter
     */
    public Lycanthrope getEmitter() {
        return emitter;
    }

    /**
     * Gets the type of this howl.
     *
     * @return the howl type
     */
    public HowlType getHowlType() {
        return howlType;
    }

    /**
     * Gets the timestamp when this howl was emitted.
     *
     * @return the timestamp in milliseconds
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Displays the characteristics of the howl including the emitter's information.
     */
    public void displayCharacteristics() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║       " + howlType.getDescription().toUpperCase() + " HOWL        ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println("Emitter: " + emitter.getName());
        System.out.println("Sex: " + emitter.getSex());
        System.out.println("Age Category: " + emitter.getAgeCategory());
        System.out.println("Strength: " + emitter.getStrength());
        System.out.println("Endurance: " + emitter.getEndurance());
        System.out.println("Domination Factor: " + emitter.getDominationFactor());
        System.out.println("Hierarchy Rank: " + emitter.getHierarchyRank().getSymbol() + " (" + emitter.getHierarchyRank().name() + ")");
        System.out.println("Level: " + emitter.getLevel());
        System.out.println("Impetuosity Factor: " + emitter.getImpetuosityFactor());
        System.out.println("Pack: " + (emitter.isSolitary() ? "Solitary" : emitter.getPack() != null ? emitter.getPack().getName() : "None"));
        System.out.println("────────────────────────────────────");
    }

    @Override
    public String toString() {
        return emitter.getName() + " emits a " + howlType.getDescription() + " howl";
    }
}

