package kotlin.text;

import java.util.List;
import java.util.regex.Matcher;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.text.MatchResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\n\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0011\u001a\u00020\u00128BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006\u001d"}, m27d2 = {"Lkotlin/text/MatcherMatchResult;", "Lkotlin/text/MatchResult;", "matcher", "Ljava/util/regex/Matcher;", "input", "", "(Ljava/util/regex/Matcher;Ljava/lang/CharSequence;)V", "groupValues", "", "", "getGroupValues", "()Ljava/util/List;", "groupValues_", "groups", "Lkotlin/text/MatchGroupCollection;", "getGroups", "()Lkotlin/text/MatchGroupCollection;", "matchResult", "Ljava/util/regex/MatchResult;", "getMatchResult", "()Ljava/util/regex/MatchResult;", AsmConstants.CODERANGE, "Lkotlin/ranges/IntRange;", "getRange", "()Lkotlin/ranges/IntRange;", PropertyDescriptor.VALUE, "getValue", "()Ljava/lang/String;", "next", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/text/MatcherMatchResult.class */
final class MatcherMatchResult implements MatchResult {

    @NotNull
    private final MatchGroupCollection groups;
    private List groupValues_;
    private final Matcher matcher;
    private final CharSequence input;

    public MatcherMatchResult(@NotNull Matcher matcher, @NotNull CharSequence input) {
        Intrinsics.checkParameterIsNotNull(matcher, "matcher");
        Intrinsics.checkParameterIsNotNull(input, "input");
        this.matcher = matcher;
        this.input = input;
        this.groups = new MatcherMatchResult$groups$1(this);
    }

    @Override // kotlin.text.MatchResult
    @NotNull
    public MatchResult.Destructured getDestructured() {
        return MatchResult.DefaultImpls.getDestructured(this);
    }

    private final java.util.regex.MatchResult getMatchResult() {
        return this.matcher;
    }

    @Override // kotlin.text.MatchResult
    @NotNull
    public IntRange getRange() {
        return RegexKt.range(getMatchResult());
    }

    @Override // kotlin.text.MatchResult
    @NotNull
    public String getValue() {
        String strGroup = getMatchResult().group();
        Intrinsics.checkExpressionValueIsNotNull(strGroup, "matchResult.group()");
        return strGroup;
    }

    @Override // kotlin.text.MatchResult
    @NotNull
    public MatchGroupCollection getGroups() {
        return this.groups;
    }

    @Override // kotlin.text.MatchResult
    @NotNull
    public List getGroupValues() {
        if (this.groupValues_ == null) {
            this.groupValues_ = new AbstractList(this) { // from class: kotlin.text.MatcherMatchResult$groupValues$1
                final MatcherMatchResult this$0;

                @Override // kotlin.collections.AbstractList, java.util.List
                public Object get(int i) {
                    return get(i);
                }

                {
                    this.this$0 = this;
                }

                public int indexOf(String str) {
                    return super.indexOf((Object) str);
                }

                @Override // kotlin.collections.AbstractList, java.util.List
                public final int indexOf(Object obj) {
                    if (obj instanceof String) {
                        return indexOf((String) obj);
                    }
                    return -1;
                }

                public int lastIndexOf(String str) {
                    return super.lastIndexOf((Object) str);
                }

                @Override // kotlin.collections.AbstractList, java.util.List
                public final int lastIndexOf(Object obj) {
                    if (obj instanceof String) {
                        return lastIndexOf((String) obj);
                    }
                    return -1;
                }

                public boolean contains(String str) {
                    return super.contains((Object) str);
                }

                @Override // kotlin.collections.AbstractCollection, java.util.Collection
                public final boolean contains(Object obj) {
                    if (obj instanceof String) {
                        return contains((String) obj);
                    }
                    return false;
                }

                @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
                public int getSize() {
                    return this.this$0.getMatchResult().groupCount() + 1;
                }

                @Override // kotlin.collections.AbstractList, java.util.List
                @NotNull
                public String get(int i) {
                    String strGroup = this.this$0.getMatchResult().group(i);
                    return strGroup != null ? strGroup : "";
                }
            };
        }
        List list = this.groupValues_;
        if (list == null) {
            Intrinsics.throwNpe();
        }
        return list;
    }

    @Override // kotlin.text.MatchResult
    @Nullable
    public MatchResult next() {
        int iEnd = getMatchResult().end() + (getMatchResult().end() == getMatchResult().start() ? 1 : 0);
        if (iEnd > this.input.length()) {
            return null;
        }
        Matcher matcher = this.matcher.pattern().matcher(this.input);
        Intrinsics.checkExpressionValueIsNotNull(matcher, "matcher.pattern().matcher(input)");
        return RegexKt.findNext(matcher, iEnd, this.input);
    }
}
