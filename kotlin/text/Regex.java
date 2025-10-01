package kotlin.text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdf\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\r\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0003\u0018\ufffd\ufffd ,2\u00060\u0001j\u0002`\u0002:\u0002,-B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bB\u001d\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n\u00a2\u0006\u0002\u0010\u000bB\u000f\b\u0001\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u001a\u001a\u00020\u001bJ\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00190\u001d2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u001a\u001a\u00020\u001bJ\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0016\u001a\u00020\u0017J\u0011\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086\u0004J\"\u0010 \u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00170\"J\u0016\u0010 \u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0004J\u0016\u0010$\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0004J\u001e\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00040&2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010'\u001a\u00020\u001bJ\u0006\u0010(\u001a\u00020\rJ\b\u0010)\u001a\u00020\u0004H\u0016J\b\u0010*\u001a\u00020+H\u0002R\u0016\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n8F\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0003\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006."}, m27d2 = {"Lkotlin/text/Regex;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "pattern", "", "(Ljava/lang/String;)V", "option", "Lkotlin/text/RegexOption;", "(Ljava/lang/String;Lkotlin/text/RegexOption;)V", "options", "", "(Ljava/lang/String;Ljava/util/Set;)V", "nativePattern", "Ljava/util/regex/Pattern;", "(Ljava/util/regex/Pattern;)V", "_options", "getOptions", "()Ljava/util/Set;", "getPattern", "()Ljava/lang/String;", "containsMatchIn", "", "input", "", "find", "Lkotlin/text/MatchResult;", "startIndex", "", "findAll", "Lkotlin/sequences/Sequence;", "matchEntire", "matches", "replace", "transform", "Lkotlin/Function1;", "replacement", "replaceFirst", "split", "", "limit", "toPattern", "toString", "writeReplace", "", "Companion", "Serialized", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/text/Regex.class */
public final class Regex implements Serializable {
    private Set _options;
    private final Pattern nativePattern;
    public static final Companion Companion = new Companion(null);

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\n\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\ufffd\ufffd\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0002\b\u0003"}, m27d2 = {"<anonymous>", "Lkotlin/text/MatchResult;", "p1", "invoke"})
    /* renamed from: kotlin.text.Regex$findAll$2 */
    /* loaded from: L-out.jar:kotlin/text/Regex$findAll$2.class */
    static final /* synthetic */ class C03882 extends FunctionReference implements Function1 {
        public static final C03882 INSTANCE = new C03882();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(MatchResult.class);
        }

        C03882() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return invoke((MatchResult) obj);
        }

        @Nullable
        public final MatchResult invoke(@NotNull MatchResult p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return p1.next();
        }
    }

    @PublishedApi
    public Regex(@NotNull Pattern nativePattern) {
        Intrinsics.checkParameterIsNotNull(nativePattern, "nativePattern");
        this.nativePattern = nativePattern;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Regex(@NotNull String pattern) {
        Intrinsics.checkParameterIsNotNull(pattern, "pattern");
        Pattern patternCompile = Pattern.compile(pattern);
        Intrinsics.checkExpressionValueIsNotNull(patternCompile, "Pattern.compile(pattern)");
        this(patternCompile);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Regex(@NotNull String pattern, @NotNull RegexOption option) {
        Intrinsics.checkParameterIsNotNull(pattern, "pattern");
        Intrinsics.checkParameterIsNotNull(option, "option");
        Pattern patternCompile = Pattern.compile(pattern, Companion.access$ensureUnicodeCase(Companion, option.getValue()));
        Intrinsics.checkExpressionValueIsNotNull(patternCompile, "Pattern.compile(pattern,\u2026nicodeCase(option.value))");
        this(patternCompile);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Regex(@NotNull String pattern, @NotNull Set options) {
        Intrinsics.checkParameterIsNotNull(pattern, "pattern");
        Intrinsics.checkParameterIsNotNull(options, "options");
        Pattern patternCompile = Pattern.compile(pattern, Companion.access$ensureUnicodeCase(Companion, RegexKt.toInt(options)));
        Intrinsics.checkExpressionValueIsNotNull(patternCompile, "Pattern.compile(pattern,\u2026odeCase(options.toInt()))");
        this(patternCompile);
    }

    @NotNull
    public final String getPattern() {
        String strPattern = this.nativePattern.pattern();
        Intrinsics.checkExpressionValueIsNotNull(strPattern, "nativePattern.pattern()");
        return strPattern;
    }

    @NotNull
    public final Set getOptions() {
        Set set = this._options;
        if (set != null) {
            return set;
        }
        final int iFlags = this.nativePattern.flags();
        EnumSet enumSetAllOf = EnumSet.allOf(RegexOption.class);
        CollectionsKt.retainAll(enumSetAllOf, new Function1(iFlags) { // from class: kotlin.text.Regex$fromInt$$inlined$apply$lambda$1
            final int $value$inlined;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.$value$inlined = iFlags;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return Boolean.valueOf(invoke((Enum) obj));
            }

            /* JADX WARN: Multi-variable type inference failed */
            public final boolean invoke(Enum r4) {
                return (this.$value$inlined & ((FlagEnum) r4).getMask()) == ((FlagEnum) r4).getValue();
            }
        });
        Set setUnmodifiableSet = Collections.unmodifiableSet(enumSetAllOf);
        Intrinsics.checkExpressionValueIsNotNull(setUnmodifiableSet, "Collections.unmodifiable\u2026mask == it.value }\n    })");
        this._options = setUnmodifiableSet;
        return setUnmodifiableSet;
    }

    public final boolean matches(@NotNull CharSequence input) {
        Intrinsics.checkParameterIsNotNull(input, "input");
        return this.nativePattern.matcher(input).matches();
    }

    public final boolean containsMatchIn(@NotNull CharSequence input) {
        Intrinsics.checkParameterIsNotNull(input, "input");
        return this.nativePattern.matcher(input).find();
    }

    public static MatchResult find$default(Regex regex, CharSequence charSequence, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return regex.find(charSequence, i);
    }

    @Nullable
    public final MatchResult find(@NotNull CharSequence input, int i) {
        Intrinsics.checkParameterIsNotNull(input, "input");
        Matcher matcher = this.nativePattern.matcher(input);
        Intrinsics.checkExpressionValueIsNotNull(matcher, "nativePattern.matcher(input)");
        return RegexKt.findNext(matcher, i, input);
    }

    public static Sequence findAll$default(Regex regex, CharSequence charSequence, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return regex.findAll(charSequence, i);
    }

    @NotNull
    public final Sequence findAll(@NotNull CharSequence input, int i) {
        Intrinsics.checkParameterIsNotNull(input, "input");
        return SequencesKt.generateSequence(new Function0(this, input, i) { // from class: kotlin.text.Regex.findAll.1
            final Regex this$0;
            final CharSequence $input;
            final int $startIndex;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
                this.$input = input;
                this.$startIndex = i;
            }

            @Override // kotlin.jvm.functions.Function0
            public Object invoke() {
                return invoke();
            }

            @Override // kotlin.jvm.functions.Function0
            @Nullable
            public final MatchResult invoke() {
                return this.this$0.find(this.$input, this.$startIndex);
            }
        }, (Function1) C03882.INSTANCE);
    }

    @Nullable
    public final MatchResult matchEntire(@NotNull CharSequence input) {
        Intrinsics.checkParameterIsNotNull(input, "input");
        Matcher matcher = this.nativePattern.matcher(input);
        Intrinsics.checkExpressionValueIsNotNull(matcher, "nativePattern.matcher(input)");
        return RegexKt.matchEntire(matcher, input);
    }

    @NotNull
    public final String replace(@NotNull CharSequence input, @NotNull String replacement) {
        Intrinsics.checkParameterIsNotNull(input, "input");
        Intrinsics.checkParameterIsNotNull(replacement, "replacement");
        String strReplaceAll = this.nativePattern.matcher(input).replaceAll(replacement);
        Intrinsics.checkExpressionValueIsNotNull(strReplaceAll, "nativePattern.matcher(in\u2026).replaceAll(replacement)");
        return strReplaceAll;
    }

    @NotNull
    public final String replace(@NotNull CharSequence input, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(input, "input");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        MatchResult matchResultFind$default = find$default(this, input, 0, 2, null);
        if (matchResultFind$default == null) {
            return input.toString();
        }
        MatchResult next = matchResultFind$default;
        int iIntValue = 0;
        int length = input.length();
        StringBuilder sb = new StringBuilder(length);
        do {
            MatchResult matchResult = next;
            if (matchResult == null) {
                Intrinsics.throwNpe();
            }
            sb.append(input, iIntValue, matchResult.getRange().getStart().intValue());
            sb.append((CharSequence) transform.invoke(matchResult));
            iIntValue = matchResult.getRange().getEndInclusive().intValue() + 1;
            next = matchResult.next();
            if (iIntValue >= length) {
                break;
            }
        } while (next != null);
        if (iIntValue < length) {
            sb.append(input, iIntValue, length);
        }
        String string = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "sb.toString()");
        return string;
    }

    @NotNull
    public final String replaceFirst(@NotNull CharSequence input, @NotNull String replacement) {
        Intrinsics.checkParameterIsNotNull(input, "input");
        Intrinsics.checkParameterIsNotNull(replacement, "replacement");
        String strReplaceFirst = this.nativePattern.matcher(input).replaceFirst(replacement);
        Intrinsics.checkExpressionValueIsNotNull(strReplaceFirst, "nativePattern.matcher(in\u2026replaceFirst(replacement)");
        return strReplaceFirst;
    }

    public static List split$default(Regex regex, CharSequence charSequence, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return regex.split(charSequence, i);
    }

    @NotNull
    public final List split(@NotNull CharSequence input, int i) {
        Intrinsics.checkParameterIsNotNull(input, "input");
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("Limit must be non-negative, but was " + i + '.').toString());
        }
        Matcher matcher = this.nativePattern.matcher(input);
        if (!matcher.find() || i == 1) {
            return CollectionsKt.listOf(input.toString());
        }
        ArrayList arrayList = new ArrayList(i > 0 ? RangesKt.coerceAtMost(i, 10) : 10);
        int iEnd = 0;
        int i2 = i - 1;
        do {
            arrayList.add(input.subSequence(iEnd, matcher.start()).toString());
            iEnd = matcher.end();
            if (i2 >= 0 && arrayList.size() == i2) {
                break;
            }
        } while (matcher.find());
        arrayList.add(input.subSequence(iEnd, input.length()).toString());
        return arrayList;
    }

    @NotNull
    public String toString() {
        String string = this.nativePattern.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "nativePattern.toString()");
        return string;
    }

    @NotNull
    public final Pattern toPattern() {
        return this.nativePattern;
    }

    private final Object writeReplace() {
        String strPattern = this.nativePattern.pattern();
        Intrinsics.checkExpressionValueIsNotNull(strPattern, "nativePattern.pattern()");
        return new Serialized(strPattern, this.nativePattern.flags());
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\b\u0002\u0018\ufffd\ufffd \u000e2\u00060\u0001j\u0002`\u0002:\u0001\u000eB\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\b\u0010\f\u001a\u00020\rH\u0002R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000f"}, m27d2 = {"Lkotlin/text/Regex$Serialized;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "pattern", "", "flags", "", "(Ljava/lang/String;I)V", "getFlags", "()I", "getPattern", "()Ljava/lang/String;", "readResolve", "", "Companion", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/text/Regex$Serialized.class */
    private static final class Serialized implements Serializable {

        @NotNull
        private final String pattern;
        private final int flags;
        private static final long serialVersionUID = 0;
        public static final Companion Companion = new Companion(null);

        @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\t\n\ufffd\ufffd\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0005"}, m27d2 = {"Lkotlin/text/Regex$Serialized$Companion;", "", "()V", "serialVersionUID", "", "kotlin-stdlib"})
        /* loaded from: L-out.jar:kotlin/text/Regex$Serialized$Companion.class */
        public static final class Companion {
            private Companion() {
            }

            public Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        @NotNull
        public final String getPattern() {
            return this.pattern;
        }

        public final int getFlags() {
            return this.flags;
        }

        public Serialized(@NotNull String pattern, int i) {
            Intrinsics.checkParameterIsNotNull(pattern, "pattern");
            this.pattern = pattern;
            this.flags = i;
        }

        private final Object readResolve() {
            Pattern patternCompile = Pattern.compile(this.pattern, this.flags);
            Intrinsics.checkExpressionValueIsNotNull(patternCompile, "Pattern.compile(pattern, flags)");
            return new Regex(patternCompile);
        }
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\"\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010\t\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u0007\u00a8\u0006\f"}, m27d2 = {"Lkotlin/text/Regex$Companion;", "", "()V", "ensureUnicodeCase", "", "flags", "escape", "", "literal", "escapeReplacement", "fromLiteral", "Lkotlin/text/Regex;", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/text/Regex$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        /*  JADX ERROR: Types fix failed
            java.lang.NullPointerException
            */
        /* JADX WARN: Failed to calculate best type for var: r1v5 ??
        java.lang.NullPointerException
         */
        /* JADX WARN: Failed to calculate best type for var: r7v0 ??
        java.lang.NullPointerException
         */
        /* JADX WARN: Not initialized variable reg: 7, insn: 0x0003: MOVE (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r7 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) (LINE:252), block:B:2:0x0000 */
        public static final int access$ensureUnicodeCase(kotlin.text.Regex.Companion r4, int r5) {
            /*
                r0 = r4
                r1 = r5
                r6 = r1
                r1 = r7
                r2 = 2
                r1 = r1 & r2
                if (r1 == 0) goto L10
                r1 = r7
                r2 = 64
                r1 = r1 | r2
                goto L11
            L10:
                r1 = r7
            L11:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.text.Regex.Companion.access$ensureUnicodeCase(kotlin.text.Regex$Companion, int):int");
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final Regex fromLiteral(@NotNull String literal) {
            Intrinsics.checkParameterIsNotNull(literal, "literal");
            return new Regex(literal, RegexOption.LITERAL);
        }

        @NotNull
        public final String escape(@NotNull String literal) {
            Intrinsics.checkParameterIsNotNull(literal, "literal");
            String strQuote = Pattern.quote(literal);
            Intrinsics.checkExpressionValueIsNotNull(strQuote, "Pattern.quote(literal)");
            return strQuote;
        }

        @NotNull
        public final String escapeReplacement(@NotNull String literal) {
            Intrinsics.checkParameterIsNotNull(literal, "literal");
            String strQuoteReplacement = Matcher.quoteReplacement(literal);
            Intrinsics.checkExpressionValueIsNotNull(strQuoteReplacement, "Matcher.quoteReplacement(literal)");
            return strQuoteReplacement;
        }
    }
}
