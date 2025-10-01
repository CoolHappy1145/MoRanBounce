package net.ccbluex.liquidbounce.features.command.shortcuts;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/shortcuts/Literal;", "Lnet/ccbluex/liquidbounce/features/command/shortcuts/Token;", "literal", "", "(Ljava/lang/String;)V", "getLiteral", "()Ljava/lang/String;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/shortcuts/Literal.class */
public final class Literal extends Token {

    @NotNull
    private final String literal;

    @NotNull
    public final String getLiteral() {
        return this.literal;
    }

    public Literal(@NotNull String literal) {
        Intrinsics.checkParameterIsNotNull(literal, "literal");
        this.literal = literal;
    }
}
