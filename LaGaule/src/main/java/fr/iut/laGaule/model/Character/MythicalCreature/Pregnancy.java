package fr.iut.laGaule.model.Character.MythicalCreature;

/**
 * Represents a pregnancy in progress within a lycanthrope pack.
 * Tracks the mother, gestation period, and expected litter size.
 */
public class Pregnancy {
    private final Lycanthrope mother;
    private int gestationTurnsRemaining;
    private final int litterSize;

    /**
     * Constructs a new Pregnancy.
     *
     * @param mother the pregnant lycanthrope (alpha female)
     * @param gestationTurns the number of turns until birth
     * @param litterSize the expected number of pups
     */
    public Pregnancy(Lycanthrope mother, int gestationTurns, int litterSize) {
        this.mother = mother;
        this.gestationTurnsRemaining = gestationTurns;
        this.litterSize = litterSize;
    }

    /**
     * Gets the mother lycanthrope.
     *
     * @return the pregnant lycanthrope
     */
    public Lycanthrope getMother() {
        return mother;
    }

    /**
     * Gets the remaining gestation time in turns.
     *
     * @return the number of turns remaining until birth
     */
    public int getGestationTurnsRemaining() {
        return gestationTurnsRemaining;
    }

    /**
     * Decrements the gestation period by one turn.
     */
    public void decrementGestation() {
        gestationTurnsRemaining--;
    }

    /**
     * Checks if the pregnancy is ready for birth.
     *
     * @return true if gestation period is complete
     */
    public boolean isReadyToBirth() {
        return gestationTurnsRemaining <= 0;
    }

    /**
     * Gets the expected litter size.
     *
     * @return the number of pups expected
     */
    public int getLitterSize() {
        return litterSize;
    }

    @Override
    public String toString() {
        return "Pregnancy{" +
                "mother=" + mother.getName() +
                ", gestationTurnsRemaining=" + gestationTurnsRemaining +
                ", litterSize=" + litterSize +
                '}';
    }
}

