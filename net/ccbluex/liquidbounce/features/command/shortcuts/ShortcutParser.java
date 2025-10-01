package net.ccbluex.liquidbounce.features.command.shortcuts;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.tools.obfuscation.SupportedOptions;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd8\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010!\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\"\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\n\u0010\n\u001a\u00060\u000bj\u0002`\fH\u0002J\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\u000e2\u0006\u0010\u0010\u001a\u00020\u000fJ\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\u000e2\u0006\u0010\u0010\u001a\u00020\u000fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0012"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/shortcuts/ShortcutParser;", "", "()V", "SEPARATOR", "", "finishLiteral", "", SupportedOptions.TOKENS, "", "Lnet/ccbluex/liquidbounce/features/command/shortcuts/Token;", "tokenBuf", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "parse", "", "", "script", "tokenize", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/shortcuts/ShortcutParser.class */
public final class ShortcutParser {
    public static final ShortcutParser INSTANCE = new ShortcutParser();
    private static final int SEPARATOR = ";".codePointAt(0);

    private ShortcutParser() {
    }

    @NotNull
    public final List parse(@NotNull String script) {
        Intrinsics.checkParameterIsNotNull(script, "script");
        List<Token> list = tokenize(script);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Token token : list) {
            if (token instanceof Literal) {
                arrayList2.add(((Literal) token).getLiteral());
            } else if (token instanceof StatementEnd) {
                arrayList.add(CollectionsKt.toList(arrayList2));
                arrayList2.clear();
            }
        }
        if (!arrayList2.isEmpty()) {
            throw new IllegalArgumentException("Unexpected end of statement!");
        }
        return arrayList;
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [java.util.PrimitiveIterator$OfInt] */
    private final List tokenize(String str) {
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        ?? it = str.codePoints().iterator();
        while (it.hasNext()) {
            Integer code = it.next();
            Intrinsics.checkExpressionValueIsNotNull(code, "code");
            if (Character.isWhitespace(code.intValue())) {
                finishLiteral(arrayList, sb);
            } else {
                if (code.intValue() == SEPARATOR) {
                    finishLiteral(arrayList, sb);
                    arrayList.add(new StatementEnd());
                } else {
                    sb.appendCodePoint(code.intValue());
                }
            }
        }
        if (sb.length() > 0) {
            throw new IllegalArgumentException("Unexpected end of literal!");
        }
        return arrayList;
    }

    private final void finishLiteral(List list, StringBuilder sb) {
        if (sb.length() > 0) {
            String string = sb.toString();
            Intrinsics.checkExpressionValueIsNotNull(string, "tokenBuf.toString()");
            list.add(new Literal(string));
            StringsKt.clear(sb);
        }
    }
}
