package fr.iut.laGaule.model.Character.MythicalCreature;

/**
 * Enum representing the hierarchical ranks within a lycanthrope pack.
 * The ranks are based on Greek letters in lexicographic order.
 * Alpha (α) individuals dominate Beta (β) individuals, who dominate Gamma (γ), etc.
 * Omega (ω) lycanthropes are the scapegoats of the entire pack.
 */
public enum Rank {
    ALPHA("α", 8),
    BETA("β", 7),
    GAMMA("γ", 6),
    DELTA("δ", 5),
    EPSILON("ε", 4),
    ZETA("ζ", 3),
    ETA("η", 2),
    OMEGA("ω", 1);

    private final String symbol;
    private final int hierarchyLevel;

    /**
     * Constructs a rank with its Greek symbol and hierarchy level.
     *
     * @param symbol the Greek letter representing this rank
     * @param hierarchyLevel the numeric hierarchy level (higher = more dominant)
     */
    Rank(String symbol, int hierarchyLevel) {
        this.symbol = symbol;
        this.hierarchyLevel = hierarchyLevel;
    }

    /**
     * Gets the Greek symbol for this rank.
     *
     * @return the Greek letter symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gets the hierarchy level of this rank.
     *
     * @return the numeric hierarchy level
     */
    public int getHierarchyLevel() {
        return hierarchyLevel;
    }

    /**
     * Checks if this rank dominates another rank.
     *
     * @param other the rank to compare with
     * @return true if this rank is higher in hierarchy
     */
    public boolean dominates(Rank other) {
        return this.hierarchyLevel > other.hierarchyLevel;
    }

    @Override
    public String toString() {
        return symbol;
    }
}

