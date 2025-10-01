package org.spongepowered.asm.mixin.injection.selectors;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/selectors/MatchResult.class */
public enum MatchResult {
    NONE,
    WEAK,
    MATCH,
    EXACT_MATCH;

    public boolean isAtLeast(MatchResult matchResult) {
        return matchResult == null || matchResult.ordinal() <= ordinal();
    }

    public boolean isMatch() {
        return ordinal() >= MATCH.ordinal();
    }

    public boolean isExactMatch() {
        return this == EXACT_MATCH;
    }
}
