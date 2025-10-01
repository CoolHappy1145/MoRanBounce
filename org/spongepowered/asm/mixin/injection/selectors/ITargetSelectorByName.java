package org.spongepowered.asm.mixin.injection.selectors;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/selectors/ITargetSelectorByName.class */
public interface ITargetSelectorByName extends ITargetSelector {
    String getOwner();

    String getName();

    String getDesc();

    boolean isFullyQualified();

    boolean isField();

    boolean isConstructor();

    boolean isClassInitialiser();

    boolean isInitialiser();

    String toDescriptor();

    MatchResult matches(String str, String str2, String str3);
}
