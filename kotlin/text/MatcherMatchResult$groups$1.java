package kotlin.text;

import java.util.Iterator;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.collections.AbstractCollection;
import kotlin.collections.CollectionsKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd-\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010(\n\ufffd\ufffd*\u0001\ufffd\ufffd\b\n\u0018\ufffd\ufffd2\u00020\u00012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0002J\u0013\u0010\b\u001a\u0004\u0018\u00010\u00032\u0006\u0010\t\u001a\u00020\u0005H\u0096\u0002J\u0013\u0010\b\u001a\u0004\u0018\u00010\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0096\u0002J\b\u0010\f\u001a\u00020\rH\u0016J\u0011\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u000fH\u0096\u0002R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0010"}, m27d2 = {"kotlin/text/MatcherMatchResult$groups$1", "Lkotlin/text/MatchNamedGroupCollection;", "Lkotlin/collections/AbstractCollection;", "Lkotlin/text/MatchGroup;", "size", "", "getSize", "()I", PropertyDescriptor.GET, "index", "name", "", "isEmpty", "", "iterator", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/text/MatcherMatchResult$groups$1.class */
public final class MatcherMatchResult$groups$1 extends AbstractCollection implements MatchNamedGroupCollection {
    final MatcherMatchResult this$0;

    MatcherMatchResult$groups$1(MatcherMatchResult matcherMatchResult) {
        this.this$0 = matcherMatchResult;
    }

    public boolean contains(MatchGroup matchGroup) {
        return super.contains((Object) matchGroup);
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final boolean contains(Object obj) {
        if (obj != null ? obj instanceof MatchGroup : true) {
            return contains((MatchGroup) obj);
        }
        return false;
    }

    @Override // kotlin.collections.AbstractCollection
    public int getSize() {
        return this.this$0.getMatchResult().groupCount() + 1;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection, java.lang.Iterable
    @NotNull
    public Iterator iterator() {
        return SequencesKt.map(CollectionsKt.asSequence(CollectionsKt.getIndices(this)), new Function1(this) { // from class: kotlin.text.MatcherMatchResult$groups$1$iterator$1
            final MatcherMatchResult$groups$1 this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return invoke(((Number) obj).intValue());
            }

            @Nullable
            public final MatchGroup invoke(int i) {
                return this.this$0.get(i);
            }
        }).iterator();
    }

    @Override // kotlin.text.MatchGroupCollection
    @Nullable
    public MatchGroup get(int i) {
        IntRange intRangeRange = RegexKt.range(this.this$0.getMatchResult(), i);
        if (intRangeRange.getStart().intValue() >= 0) {
            String strGroup = this.this$0.getMatchResult().group(i);
            Intrinsics.checkExpressionValueIsNotNull(strGroup, "matchResult.group(index)");
            return new MatchGroup(strGroup, intRangeRange);
        }
        return null;
    }

    @Override // kotlin.text.MatchNamedGroupCollection
    @Nullable
    public MatchGroup get(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return PlatformImplementationsKt.IMPLEMENTATIONS.getMatchResultNamedGroup(this.this$0.getMatchResult(), name);
    }
}
