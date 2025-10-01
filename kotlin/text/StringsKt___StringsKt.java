package kotlin.text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CharIterator;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.IndexingIterable;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.collections.SlidingWindowKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\u00dc\u0001\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\u0010\r\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u001c\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u001f\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\ufffd\ufffd\n\u0002\b\b\n\u0002\u0010\u000f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a!\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002\u001a!\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0010\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b*\u00020\u0002\u001a\u0010\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n*\u00020\u0002\u001aE\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\ufffd\ufffd\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00100\u0004H\u0086\b\u001a3\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00050\f\"\u0004\b\ufffd\ufffd\u0010\r*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u001aM\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\ufffd\ufffd\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u001aN\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\ufffd\ufffd\u0010\r\"\u0018\b\u0001\u0010\u0015*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\r\u0012\u0006\b\ufffd\ufffd\u0012\u00020\u00050\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u00a2\u0006\u0002\u0010\u0018\u001ah\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\ufffd\ufffd\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u0018\b\u0002\u0010\u0015*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\r\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u00a2\u0006\u0002\u0010\u0019\u001a`\u0010\u001a\u001a\u0002H\u0015\"\u0004\b\ufffd\ufffd\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u0018\b\u0002\u0010\u0015*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\r\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u000e0\u00100\u0004H\u0086\b\u00a2\u0006\u0002\u0010\u0018\u001a3\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\f\"\u0004\b\ufffd\ufffd\u0010\u000e*\u00020\u00022\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0087\b\u001aN\u0010\u001d\u001a\u0002H\u0015\"\u0004\b\ufffd\ufffd\u0010\u000e\"\u0018\b\u0001\u0010\u0015*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u00020\u0005\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u000e0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0087\b\u00a2\u0006\u0002\u0010\u0018\u001a\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f*\u00020\u00022\u0006\u0010!\u001a\u00020\"H\u0007\u001a4\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\ufffd\ufffd\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a\u001a\u0010$\u001a\b\u0012\u0004\u0012\u00020 0\n*\u00020\u00022\u0006\u0010!\u001a\u00020\"H\u0007\u001a4\u0010$\u001a\b\u0012\u0004\u0012\u0002H#0\n\"\u0004\b\ufffd\ufffd\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a\r\u0010%\u001a\u00020\"*\u00020\u0002H\u0087\b\u001a!\u0010%\u001a\u00020\"*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0012\u0010&\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a\u0012\u0010(\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0012\u0010(\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a!\u0010)\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010)\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010*\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010*\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a)\u0010+\u001a\u00020\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u00050\u0004H\u0087\b\u001a\u001c\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"H\u0087\b\u00a2\u0006\u0002\u0010/\u001a!\u00100\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u00100\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a6\u00101\u001a\u00020\u0002*\u00020\u00022'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000102H\u0086\b\u001a6\u00101\u001a\u00020 *\u00020 2'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000102H\u0086\b\u001aQ\u00105\u001a\u0002H6\"\f\b\ufffd\ufffd\u00106*\u000607j\u0002`8*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62'\u0010\u0003\u001a#\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000102H\u0086\b\u00a2\u0006\u0002\u00109\u001a!\u0010:\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010:\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a<\u0010;\u001a\u0002H6\"\f\b\ufffd\ufffd\u00106*\u000607j\u0002`8*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u00a2\u0006\u0002\u0010<\u001a<\u0010=\u001a\u0002H6\"\f\b\ufffd\ufffd\u00106*\u000607j\u0002`8*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u00a2\u0006\u0002\u0010<\u001a(\u0010>\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0087\b\u00a2\u0006\u0002\u0010?\u001a(\u0010@\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0087\b\u00a2\u0006\u0002\u0010?\u001a\n\u0010A\u001a\u00020\u0005*\u00020\u0002\u001a!\u0010A\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0011\u0010B\u001a\u0004\u0018\u00010\u0005*\u00020\u0002\u00a2\u0006\u0002\u0010C\u001a(\u0010B\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u00a2\u0006\u0002\u0010?\u001a3\u0010D\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\ufffd\ufffd\u0010#*\u00020\u00022\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H#0\b0\u0004H\u0086\b\u001aL\u0010E\u001a\u0002H6\"\u0004\b\ufffd\ufffd\u0010#\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H#0\b0\u0004H\u0086\b\u00a2\u0006\u0002\u0010G\u001aI\u0010H\u001a\u0002H#\"\u0004\b\ufffd\ufffd\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2'\u0010J\u001a#\u0012\u0013\u0012\u0011H#\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0086\b\u00a2\u0006\u0002\u0010L\u001a^\u0010M\u001a\u0002H#\"\u0004\b\ufffd\ufffd\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2<\u0010J\u001a8\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0013\u0012\u0011H#\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0NH\u0086\b\u00a2\u0006\u0002\u0010O\u001aI\u0010P\u001a\u0002H#\"\u0004\b\ufffd\ufffd\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2'\u0010J\u001a#\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u0011H#\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u0002H#02H\u0086\b\u00a2\u0006\u0002\u0010L\u001a^\u0010Q\u001a\u0002H#\"\u0004\b\ufffd\ufffd\u0010#*\u00020\u00022\u0006\u0010I\u001a\u0002H#2<\u0010J\u001a8\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u0011H#\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u0002H#0NH\u0086\b\u00a2\u0006\u0002\u0010O\u001a!\u0010R\u001a\u00020S*\u00020\u00022\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020S0\u0004H\u0086\b\u001a6\u0010U\u001a\u00020S*\u00020\u00022'\u0010T\u001a#\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020S02H\u0086\b\u001a)\u0010V\u001a\u00020\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u00050\u0004H\u0087\b\u001a\u0019\u0010W\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0006\u0010,\u001a\u00020\"\u00a2\u0006\u0002\u0010/\u001a9\u0010X\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u001f0\f\"\u0004\b\ufffd\ufffd\u0010\r*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u001aS\u0010X\u001a\u0014\u0012\u0004\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u001f0\f\"\u0004\b\ufffd\ufffd\u0010\r\"\u0004\b\u0001\u0010\u000e*\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u001aR\u0010Y\u001a\u0002H\u0015\"\u0004\b\ufffd\ufffd\u0010\r\"\u001c\b\u0001\u0010\u0015*\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050Z0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0086\b\u00a2\u0006\u0002\u0010\u0018\u001al\u0010Y\u001a\u0002H\u0015\"\u0004\b\ufffd\ufffd\u0010\r\"\u0004\b\u0001\u0010\u000e\"\u001c\b\u0002\u0010\u0015*\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\r\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0Z0\u0016*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H\u00152\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u00042\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\b\u00a2\u0006\u0002\u0010\u0019\u001a5\u0010[\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\\\"\u0004\b\ufffd\ufffd\u0010\r*\u00020\u00022\u0014\b\u0004\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\r0\u0004H\u0087\b\u001a!\u0010]\u001a\u00020\"*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a!\u0010^\u001a\u00020\"*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\n\u0010_\u001a\u00020\u0005*\u00020\u0002\u001a!\u0010_\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0011\u0010`\u001a\u0004\u0018\u00010\u0005*\u00020\u0002\u00a2\u0006\u0002\u0010C\u001a(\u0010`\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u00a2\u0006\u0002\u0010?\u001a-\u0010a\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\ufffd\ufffd\u0010#*\u00020\u00022\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b\u001aB\u0010b\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\ufffd\ufffd\u0010#*\u00020\u00022'\u0010\u000f\u001a#\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0086\b\u001aH\u0010c\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\b\b\ufffd\ufffd\u0010#*\u00020d*\u00020\u00022)\u0010\u000f\u001a%\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#02H\u0086\b\u001aa\u0010e\u001a\u0002H6\"\b\b\ufffd\ufffd\u0010#*\u00020d\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62)\u0010\u000f\u001a%\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#02H\u0086\b\u00a2\u0006\u0002\u0010f\u001a[\u0010g\u001a\u0002H6\"\u0004\b\ufffd\ufffd\u0010#\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62'\u0010\u000f\u001a#\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#02H\u0086\b\u00a2\u0006\u0002\u0010f\u001a3\u0010h\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\b\b\ufffd\ufffd\u0010#*\u00020d*\u00020\u00022\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#0\u0004H\u0086\b\u001aL\u0010i\u001a\u0002H6\"\b\b\ufffd\ufffd\u0010#*\u00020d\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0014\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u0001H#0\u0004H\u0086\b\u00a2\u0006\u0002\u0010G\u001aF\u0010j\u001a\u0002H6\"\u0004\b\ufffd\ufffd\u0010#\"\u0010\b\u0001\u00106*\n\u0012\u0006\b\ufffd\ufffd\u0012\u0002H#0F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H62\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b\u00a2\u0006\u0002\u0010G\u001a\u0011\u0010k\u001a\u0004\u0018\u00010\u0005*\u00020\u0002\u00a2\u0006\u0002\u0010C\u001a8\u0010l\u001a\u0004\u0018\u00010\u0005\"\u000e\b\ufffd\ufffd\u0010#*\b\u0012\u0004\u0012\u0002H#0m*\u00020\u00022\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b\u00a2\u0006\u0002\u0010?\u001a-\u0010o\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u001a\u0010p\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u00020\u00050qj\n\u0012\u0006\b\ufffd\ufffd\u0012\u00020\u0005`r\u00a2\u0006\u0002\u0010s\u001a\u0011\u0010t\u001a\u0004\u0018\u00010\u0005*\u00020\u0002\u00a2\u0006\u0002\u0010C\u001a8\u0010u\u001a\u0004\u0018\u00010\u0005\"\u000e\b\ufffd\ufffd\u0010#*\b\u0012\u0004\u0012\u0002H#0m*\u00020\u00022\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H#0\u0004H\u0086\b\u00a2\u0006\u0002\u0010?\u001a-\u0010v\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u001a\u0010p\u001a\u0016\u0012\u0006\b\ufffd\ufffd\u0012\u00020\u00050qj\n\u0012\u0006\b\ufffd\ufffd\u0012\u00020\u0005`r\u00a2\u0006\u0002\u0010s\u001a\n\u0010w\u001a\u00020\u0001*\u00020\u0002\u001a!\u0010w\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a0\u0010x\u001a\u0002Hy\"\b\b\ufffd\ufffd\u0010y*\u00020\u0002*\u0002Hy2\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020S0\u0004H\u0087\b\u00a2\u0006\u0002\u0010z\u001a-\u0010{\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0010*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a-\u0010{\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020 0\u0010*\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\r\u0010|\u001a\u00020\u0005*\u00020\u0002H\u0087\b\u001a\u0014\u0010|\u001a\u00020\u0005*\u00020\u00022\u0006\u0010|\u001a\u00020}H\u0007\u001a6\u0010~\u001a\u00020\u0005*\u00020\u00022'\u0010J\u001a#\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000502H\u0086\b\u001aK\u0010\u007f\u001a\u00020\u0005*\u00020\u00022<\u0010J\u001a8\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050NH\u0086\b\u001a7\u0010\u0080\u0001\u001a\u00020\u0005*\u00020\u00022'\u0010J\u001a#\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u000502H\u0086\b\u001aL\u0010\u0081\u0001\u001a\u00020\u0005*\u00020\u00022<\u0010J\u001a8\u0012\u0013\u0012\u00110\"\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(,\u0012\u0004\u0012\u00020\u0005\u0012\u0013\u0012\u00110\u0005\u00a2\u0006\f\b3\u0012\b\b4\u0012\u0004\b\b(K\u0012\u0004\u0012\u00020\u00050NH\u0086\b\u001a\u000b\u0010\u0082\u0001\u001a\u00020\u0002*\u00020\u0002\u001a\u000e\u0010\u0082\u0001\u001a\u00020 *\u00020 H\u0087\b\u001a\u000b\u0010\u0083\u0001\u001a\u00020\u0005*\u00020\u0002\u001a\"\u0010\u0083\u0001\u001a\u00020\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\u0012\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u0002\u00a2\u0006\u0002\u0010C\u001a)\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u0005*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u00a2\u0006\u0002\u0010?\u001a\u001a\u0010\u0085\u0001\u001a\u00020\u0002*\u00020\u00022\r\u0010\u0086\u0001\u001a\b\u0012\u0004\u0012\u00020\"0\b\u001a\u0015\u0010\u0085\u0001\u001a\u00020\u0002*\u00020\u00022\b\u0010\u0086\u0001\u001a\u00030\u0087\u0001\u001a\u001d\u0010\u0085\u0001\u001a\u00020 *\u00020 2\r\u0010\u0086\u0001\u001a\b\u0012\u0004\u0012\u00020\"0\bH\u0087\b\u001a\u0015\u0010\u0085\u0001\u001a\u00020 *\u00020 2\b\u0010\u0086\u0001\u001a\u00030\u0087\u0001\u001a\"\u0010\u0088\u0001\u001a\u00020\"*\u00020\u00022\u0012\u0010n\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\"0\u0004H\u0086\b\u001a$\u0010\u0089\u0001\u001a\u00030\u008a\u0001*\u00020\u00022\u0013\u0010n\u001a\u000f\u0012\u0004\u0012\u00020\u0005\u0012\u0005\u0012\u00030\u008a\u00010\u0004H\u0086\b\u001a\u0013\u0010\u008b\u0001\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0013\u0010\u008b\u0001\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a\u0013\u0010\u008c\u0001\u001a\u00020\u0002*\u00020\u00022\u0006\u0010'\u001a\u00020\"\u001a\u0013\u0010\u008c\u0001\u001a\u00020 *\u00020 2\u0006\u0010'\u001a\u00020\"\u001a\"\u0010\u008d\u0001\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u008d\u0001\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u008e\u0001\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a\"\u0010\u008e\u0001\u001a\u00020 *\u00020 2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\b\u001a+\u0010\u008f\u0001\u001a\u0002H6\"\u0010\b\ufffd\ufffd\u00106*\n\u0012\u0006\b\ufffd\ufffd\u0012\u00020\u00050F*\u00020\u00022\u0006\u0010\u0017\u001a\u0002H6\u00a2\u0006\u0003\u0010\u0090\u0001\u001a\u001d\u0010\u0091\u0001\u001a\u0014\u0012\u0004\u0012\u00020\u00050\u0092\u0001j\t\u0012\u0004\u0012\u00020\u0005`\u0093\u0001*\u00020\u0002\u001a\u0011\u0010\u0094\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050\u001f*\u00020\u0002\u001a\u0011\u0010\u0095\u0001\u001a\b\u0012\u0004\u0012\u00020\u00050Z*\u00020\u0002\u001a\u0012\u0010\u0096\u0001\u001a\t\u0012\u0004\u0012\u00020\u00050\u0097\u0001*\u00020\u0002\u001a1\u0010\u0098\u0001\u001a\b\u0012\u0004\u0012\u00020 0\u001f*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u0099\u0001\u001a\u00020\"2\t\b\u0002\u0010\u009a\u0001\u001a\u00020\u0001H\u0007\u001aK\u0010\u0098\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\ufffd\ufffd\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u0099\u0001\u001a\u00020\"2\t\b\u0002\u0010\u009a\u0001\u001a\u00020\u00012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a1\u0010\u009b\u0001\u001a\b\u0012\u0004\u0012\u00020 0\n*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u0099\u0001\u001a\u00020\"2\t\b\u0002\u0010\u009a\u0001\u001a\u00020\u0001H\u0007\u001aK\u0010\u009b\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\n\"\u0004\b\ufffd\ufffd\u0010#*\u00020\u00022\u0006\u0010!\u001a\u00020\"2\t\b\u0002\u0010\u0099\u0001\u001a\u00020\"2\t\b\u0002\u0010\u009a\u0001\u001a\u00020\u00012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H#0\u0004H\u0007\u001a\u0018\u0010\u009c\u0001\u001a\u000f\u0012\u000b\u0012\t\u0012\u0004\u0012\u00020\u00050\u009d\u00010\b*\u00020\u0002\u001a)\u0010\u009e\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00100\u001f*\u00020\u00022\u0007\u0010\u009f\u0001\u001a\u00020\u0002H\u0086\u0004\u001a]\u0010\u009e\u0001\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u001f\"\u0004\b\ufffd\ufffd\u0010\u000e*\u00020\u00022\u0007\u0010\u009f\u0001\u001a\u00020\u000228\u0010\u000f\u001a4\u0012\u0014\u0012\u00120\u0005\u00a2\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b(\u00a0\u0001\u0012\u0014\u0012\u00120\u0005\u00a2\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b(\u00a1\u0001\u0012\u0004\u0012\u0002H\u000e02H\u0086\b\u001a\u001f\u0010\u00a2\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00100\u001f*\u00020\u0002H\u0007\u001aT\u0010\u00a2\u0001\u001a\b\u0012\u0004\u0012\u0002H#0\u001f\"\u0004\b\ufffd\ufffd\u0010#*\u00020\u000228\u0010\u000f\u001a4\u0012\u0014\u0012\u00120\u0005\u00a2\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b(\u00a0\u0001\u0012\u0014\u0012\u00120\u0005\u00a2\u0006\r\b3\u0012\t\b4\u0012\u0005\b\b(\u00a1\u0001\u0012\u0004\u0012\u0002H#02H\u0087\b\u00a8\u0006\u00a3\u0001"}, m27d2 = {"all", "", "", "predicate", "Lkotlin/Function1;", "", "any", "asIterable", "", "asSequence", "Lkotlin/sequences/Sequence;", "associate", "", "K", "V", "transform", "Lkotlin/Pair;", "associateBy", "keySelector", "valueTransform", "associateByTo", "M", "", "destination", "(Ljava/lang/CharSequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "(Ljava/lang/CharSequence;Ljava/util/Map;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "associateTo", "associateWith", "valueSelector", "associateWithTo", "chunked", "", "", "size", "", "R", "chunkedSequence", "count", "drop", "n", "dropLast", "dropLastWhile", "dropWhile", "elementAtOrElse", "index", "defaultValue", "elementAtOrNull", "(Ljava/lang/CharSequence;I)Ljava/lang/Character;", "filter", "filterIndexed", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "filterIndexedTo", "C", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "(Ljava/lang/CharSequence;Ljava/lang/Appendable;Lkotlin/jvm/functions/Function2;)Ljava/lang/Appendable;", "filterNot", "filterNotTo", "(Ljava/lang/CharSequence;Ljava/lang/Appendable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Appendable;", "filterTo", "find", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/Character;", "findLast", "first", "firstOrNull", "(Ljava/lang/CharSequence;)Ljava/lang/Character;", "flatMap", "flatMapTo", "", "(Ljava/lang/CharSequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "fold", "initial", "operation", "acc", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "foldIndexed", "Lkotlin/Function3;", "(Ljava/lang/CharSequence;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "foldRight", "foldRightIndexed", "forEach", "", "action", "forEachIndexed", "getOrElse", "getOrNull", "groupBy", "groupByTo", "", "groupingBy", "Lkotlin/collections/Grouping;", "indexOfFirst", "indexOfLast", "last", "lastOrNull", "map", "mapIndexed", "mapIndexedNotNull", "", "mapIndexedNotNullTo", "(Ljava/lang/CharSequence;Ljava/util/Collection;Lkotlin/jvm/functions/Function2;)Ljava/util/Collection;", "mapIndexedTo", "mapNotNull", "mapNotNullTo", "mapTo", "max", "maxBy", "", "selector", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/lang/CharSequence;Ljava/util/Comparator;)Ljava/lang/Character;", "min", "minBy", "minWith", "none", "onEach", "S", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function1;)Ljava/lang/CharSequence;", "partition", "random", "Lkotlin/random/Random;", "reduce", "reduceIndexed", "reduceRight", "reduceRightIndexed", "reversed", "single", "singleOrNull", "slice", "indices", "Lkotlin/ranges/IntRange;", "sumBy", "sumByDouble", "", "take", "takeLast", "takeLastWhile", "takeWhile", "toCollection", "(Ljava/lang/CharSequence;Ljava/util/Collection;)Ljava/util/Collection;", "toHashSet", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "toList", "toMutableList", "toSet", "", "windowed", "step", "partialWindows", "windowedSequence", "withIndex", "Lkotlin/collections/IndexedValue;", "zip", "other", "a", "b", "zipWithNext", "kotlin-stdlib"}, m28xs = "kotlin/text/StringsKt")
/* loaded from: L-out.jar:kotlin/text/StringsKt___StringsKt.class */
class StringsKt___StringsKt extends StringsKt___StringsJvmKt {
    @InlineOnly
    private static final char elementAtOrElse(@NotNull CharSequence charSequence, int i, Function1 function1) {
        return (i < 0 || i > StringsKt.getLastIndex(charSequence)) ? ((Character) function1.invoke(Integer.valueOf(i))).charValue() : charSequence.charAt(i);
    }

    @InlineOnly
    private static final Character elementAtOrNull(@NotNull CharSequence charSequence, int i) {
        return StringsKt.getOrNull(charSequence, i);
    }

    @InlineOnly
    private static final Character find(@NotNull CharSequence charSequence, Function1 function1) {
        for (int i = 0; i < charSequence.length(); i++) {
            char cCharAt = charSequence.charAt(i);
            if (((Boolean) function1.invoke(Character.valueOf(cCharAt))).booleanValue()) {
                return Character.valueOf(cCharAt);
            }
        }
        return null;
    }

    @InlineOnly
    private static final Character findLast(@NotNull CharSequence charSequence, Function1 function1) {
        char cCharAt;
        int length = charSequence.length();
        do {
            length--;
            if (length >= 0) {
                cCharAt = charSequence.charAt(length);
            } else {
                return null;
            }
        } while (!((Boolean) function1.invoke(Character.valueOf(cCharAt))).booleanValue());
        return Character.valueOf(cCharAt);
    }

    public static final char first(@NotNull CharSequence first) {
        Intrinsics.checkParameterIsNotNull(first, "$this$first");
        if (first.length() == 0) {
            throw new NoSuchElementException("Char sequence is empty.");
        }
        return first.charAt(0);
    }

    public static final char first(@NotNull CharSequence first, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(first, "$this$first");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int i = 0; i < first.length(); i++) {
            char cCharAt = first.charAt(i);
            if (((Boolean) predicate.invoke(Character.valueOf(cCharAt))).booleanValue()) {
                return cCharAt;
            }
        }
        throw new NoSuchElementException("Char sequence contains no character matching the predicate.");
    }

    @Nullable
    public static final Character firstOrNull(@NotNull CharSequence firstOrNull) {
        Intrinsics.checkParameterIsNotNull(firstOrNull, "$this$firstOrNull");
        if (firstOrNull.length() == 0) {
            return null;
        }
        return Character.valueOf(firstOrNull.charAt(0));
    }

    @Nullable
    public static final Character firstOrNull(@NotNull CharSequence firstOrNull, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(firstOrNull, "$this$firstOrNull");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int i = 0; i < firstOrNull.length(); i++) {
            char cCharAt = firstOrNull.charAt(i);
            if (((Boolean) predicate.invoke(Character.valueOf(cCharAt))).booleanValue()) {
                return Character.valueOf(cCharAt);
            }
        }
        return null;
    }

    @InlineOnly
    private static final char getOrElse(@NotNull CharSequence charSequence, int i, Function1 function1) {
        return (i < 0 || i > StringsKt.getLastIndex(charSequence)) ? ((Character) function1.invoke(Integer.valueOf(i))).charValue() : charSequence.charAt(i);
    }

    @Nullable
    public static final Character getOrNull(@NotNull CharSequence getOrNull, int i) {
        Intrinsics.checkParameterIsNotNull(getOrNull, "$this$getOrNull");
        if (i < 0 || i > StringsKt.getLastIndex(getOrNull)) {
            return null;
        }
        return Character.valueOf(getOrNull.charAt(i));
    }

    public static final int indexOfFirst(@NotNull CharSequence indexOfFirst, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(indexOfFirst, "$this$indexOfFirst");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = indexOfFirst.length();
        for (int i = 0; i < length; i++) {
            if (((Boolean) predicate.invoke(Character.valueOf(indexOfFirst.charAt(i)))).booleanValue()) {
                return i;
            }
        }
        return -1;
    }

    public static final int indexOfLast(@NotNull CharSequence indexOfLast, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(indexOfLast, "$this$indexOfLast");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = indexOfLast.length();
        do {
            length--;
            if (length < 0) {
                return -1;
            }
        } while (!((Boolean) predicate.invoke(Character.valueOf(indexOfLast.charAt(length)))).booleanValue());
        return length;
    }

    public static final char last(@NotNull CharSequence last) {
        Intrinsics.checkParameterIsNotNull(last, "$this$last");
        if (last.length() == 0) {
            throw new NoSuchElementException("Char sequence is empty.");
        }
        return last.charAt(StringsKt.getLastIndex(last));
    }

    public static final char last(@NotNull CharSequence last, @NotNull Function1 predicate) {
        char cCharAt;
        Intrinsics.checkParameterIsNotNull(last, "$this$last");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = last.length();
        do {
            length--;
            if (length >= 0) {
                cCharAt = last.charAt(length);
            } else {
                throw new NoSuchElementException("Char sequence contains no character matching the predicate.");
            }
        } while (!((Boolean) predicate.invoke(Character.valueOf(cCharAt))).booleanValue());
        return cCharAt;
    }

    @Nullable
    public static final Character lastOrNull(@NotNull CharSequence lastOrNull) {
        Intrinsics.checkParameterIsNotNull(lastOrNull, "$this$lastOrNull");
        if (lastOrNull.length() == 0) {
            return null;
        }
        return Character.valueOf(lastOrNull.charAt(lastOrNull.length() - 1));
    }

    @Nullable
    public static final Character lastOrNull(@NotNull CharSequence lastOrNull, @NotNull Function1 predicate) {
        char cCharAt;
        Intrinsics.checkParameterIsNotNull(lastOrNull, "$this$lastOrNull");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = lastOrNull.length();
        do {
            length--;
            if (length >= 0) {
                cCharAt = lastOrNull.charAt(length);
            } else {
                return null;
            }
        } while (!((Boolean) predicate.invoke(Character.valueOf(cCharAt))).booleanValue());
        return Character.valueOf(cCharAt);
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final char random(@NotNull CharSequence charSequence) {
        return StringsKt.random(charSequence, Random.Default);
    }

    @SinceKotlin(version = "1.3")
    public static final char random(@NotNull CharSequence random, @NotNull Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        if (random.length() == 0) {
            throw new NoSuchElementException("Char sequence is empty.");
        }
        return random.charAt(random2.nextInt(random.length()));
    }

    public static final char single(@NotNull CharSequence single) {
        Intrinsics.checkParameterIsNotNull(single, "$this$single");
        switch (single.length()) {
            case 0:
                throw new NoSuchElementException("Char sequence is empty.");
            case 1:
                return single.charAt(0);
            default:
                throw new IllegalArgumentException("Char sequence has more than one element.");
        }
    }

    public static final char single(@NotNull CharSequence single, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(single, "$this$single");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        Character chValueOf = (Character) null;
        boolean z = false;
        for (int i = 0; i < single.length(); i++) {
            char cCharAt = single.charAt(i);
            if (((Boolean) predicate.invoke(Character.valueOf(cCharAt))).booleanValue()) {
                if (z) {
                    throw new IllegalArgumentException("Char sequence contains more than one matching element.");
                }
                chValueOf = Character.valueOf(cCharAt);
                z = true;
            }
        }
        if (!z) {
            throw new NoSuchElementException("Char sequence contains no character matching the predicate.");
        }
        Character ch = chValueOf;
        if (ch == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Char");
        }
        return ch.charValue();
    }

    @Nullable
    public static final Character singleOrNull(@NotNull CharSequence singleOrNull) {
        Intrinsics.checkParameterIsNotNull(singleOrNull, "$this$singleOrNull");
        if (singleOrNull.length() == 1) {
            return Character.valueOf(singleOrNull.charAt(0));
        }
        return null;
    }

    @Nullable
    public static final Character singleOrNull(@NotNull CharSequence singleOrNull, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(singleOrNull, "$this$singleOrNull");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        Character chValueOf = (Character) null;
        boolean z = false;
        for (int i = 0; i < singleOrNull.length(); i++) {
            char cCharAt = singleOrNull.charAt(i);
            if (((Boolean) predicate.invoke(Character.valueOf(cCharAt))).booleanValue()) {
                if (z) {
                    return null;
                }
                chValueOf = Character.valueOf(cCharAt);
                z = true;
            }
        }
        if (z) {
            return chValueOf;
        }
        return null;
    }

    @NotNull
    public static final CharSequence drop(@NotNull CharSequence drop, int i) {
        Intrinsics.checkParameterIsNotNull(drop, "$this$drop");
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("Requested character count " + i + " is less than zero.").toString());
        }
        return drop.subSequence(RangesKt.coerceAtMost(i, drop.length()), drop.length());
    }

    @NotNull
    public static final String drop(@NotNull String drop, int i) {
        Intrinsics.checkParameterIsNotNull(drop, "$this$drop");
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("Requested character count " + i + " is less than zero.").toString());
        }
        String strSubstring = drop.substring(RangesKt.coerceAtMost(i, drop.length()));
        Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.String).substring(startIndex)");
        return strSubstring;
    }

    @NotNull
    public static final CharSequence dropLast(@NotNull CharSequence dropLast, int i) {
        Intrinsics.checkParameterIsNotNull(dropLast, "$this$dropLast");
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("Requested character count " + i + " is less than zero.").toString());
        }
        return StringsKt.take(dropLast, RangesKt.coerceAtLeast(dropLast.length() - i, 0));
    }

    @NotNull
    public static final String dropLast(@NotNull String dropLast, int i) {
        Intrinsics.checkParameterIsNotNull(dropLast, "$this$dropLast");
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("Requested character count " + i + " is less than zero.").toString());
        }
        return StringsKt.take(dropLast, RangesKt.coerceAtLeast(dropLast.length() - i, 0));
    }

    @NotNull
    public static final CharSequence dropLastWhile(@NotNull CharSequence dropLastWhile, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(dropLastWhile, "$this$dropLastWhile");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int lastIndex = StringsKt.getLastIndex(dropLastWhile); lastIndex >= 0; lastIndex--) {
            if (!((Boolean) predicate.invoke(Character.valueOf(dropLastWhile.charAt(lastIndex)))).booleanValue()) {
                return dropLastWhile.subSequence(0, lastIndex + 1);
            }
        }
        return "";
    }

    @NotNull
    public static final String dropLastWhile(@NotNull String dropLastWhile, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(dropLastWhile, "$this$dropLastWhile");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int lastIndex = StringsKt.getLastIndex(dropLastWhile); lastIndex >= 0; lastIndex--) {
            if (!((Boolean) predicate.invoke(Character.valueOf(dropLastWhile.charAt(lastIndex)))).booleanValue()) {
                String strSubstring = dropLastWhile.substring(0, lastIndex + 1);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                return strSubstring;
            }
        }
        return "";
    }

    @NotNull
    public static final CharSequence dropWhile(@NotNull CharSequence dropWhile, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(dropWhile, "$this$dropWhile");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = dropWhile.length();
        for (int i = 0; i < length; i++) {
            if (!((Boolean) predicate.invoke(Character.valueOf(dropWhile.charAt(i)))).booleanValue()) {
                return dropWhile.subSequence(i, dropWhile.length());
            }
        }
        return "";
    }

    @NotNull
    public static final String dropWhile(@NotNull String dropWhile, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(dropWhile, "$this$dropWhile");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = dropWhile.length();
        for (int i = 0; i < length; i++) {
            if (!((Boolean) predicate.invoke(Character.valueOf(dropWhile.charAt(i)))).booleanValue()) {
                String strSubstring = dropWhile.substring(i);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.String).substring(startIndex)");
                return strSubstring;
            }
        }
        return "";
    }

    @NotNull
    public static final CharSequence filter(@NotNull CharSequence filter, @NotNull Function1 predicate) throws IOException {
        Intrinsics.checkParameterIsNotNull(filter, "$this$filter");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        StringBuilder sb = new StringBuilder();
        int length = filter.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = filter.charAt(i);
            if (((Boolean) predicate.invoke(Character.valueOf(cCharAt))).booleanValue()) {
                sb.append(cCharAt);
            }
        }
        return sb;
    }

    @NotNull
    public static final String filter(@NotNull String filter, @NotNull Function1 predicate) throws IOException {
        Intrinsics.checkParameterIsNotNull(filter, "$this$filter");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        String str = filter;
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (((Boolean) predicate.invoke(Character.valueOf(cCharAt))).booleanValue()) {
                sb.append(cCharAt);
            }
        }
        String string = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "filterTo(StringBuilder(), predicate).toString()");
        return string;
    }

    @NotNull
    public static final CharSequence filterIndexed(@NotNull CharSequence filterIndexed, @NotNull Function2 predicate) throws IOException {
        Intrinsics.checkParameterIsNotNull(filterIndexed, "$this$filterIndexed");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (int i2 = 0; i2 < filterIndexed.length(); i2++) {
            char cCharAt = filterIndexed.charAt(i2);
            int i3 = i;
            i++;
            if (((Boolean) predicate.invoke(Integer.valueOf(i3), Character.valueOf(cCharAt))).booleanValue()) {
                sb.append(cCharAt);
            }
        }
        return sb;
    }

    @NotNull
    public static final String filterIndexed(@NotNull String filterIndexed, @NotNull Function2 predicate) throws IOException {
        Intrinsics.checkParameterIsNotNull(filterIndexed, "$this$filterIndexed");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        String str = filterIndexed;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            int i3 = i;
            i++;
            if (((Boolean) predicate.invoke(Integer.valueOf(i3), Character.valueOf(cCharAt))).booleanValue()) {
                sb.append(cCharAt);
            }
        }
        String string = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "filterIndexedTo(StringBu\u2026(), predicate).toString()");
        return string;
    }

    @NotNull
    public static final Appendable filterIndexedTo(@NotNull CharSequence filterIndexedTo, @NotNull Appendable destination, @NotNull Function2 predicate) throws IOException {
        Intrinsics.checkParameterIsNotNull(filterIndexedTo, "$this$filterIndexedTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int i = 0;
        for (int i2 = 0; i2 < filterIndexedTo.length(); i2++) {
            char cCharAt = filterIndexedTo.charAt(i2);
            int i3 = i;
            i++;
            if (((Boolean) predicate.invoke(Integer.valueOf(i3), Character.valueOf(cCharAt))).booleanValue()) {
                destination.append(cCharAt);
            }
        }
        return destination;
    }

    @NotNull
    public static final CharSequence filterNot(@NotNull CharSequence filterNot, @NotNull Function1 predicate) throws IOException {
        Intrinsics.checkParameterIsNotNull(filterNot, "$this$filterNot");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filterNot.length(); i++) {
            char cCharAt = filterNot.charAt(i);
            if (!((Boolean) predicate.invoke(Character.valueOf(cCharAt))).booleanValue()) {
                sb.append(cCharAt);
            }
        }
        return sb;
    }

    @NotNull
    public static final String filterNot(@NotNull String filterNot, @NotNull Function1 predicate) throws IOException {
        Intrinsics.checkParameterIsNotNull(filterNot, "$this$filterNot");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        String str = filterNot;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (!((Boolean) predicate.invoke(Character.valueOf(cCharAt))).booleanValue()) {
                sb.append(cCharAt);
            }
        }
        String string = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "filterNotTo(StringBuilder(), predicate).toString()");
        return string;
    }

    @NotNull
    public static final Appendable filterNotTo(@NotNull CharSequence filterNotTo, @NotNull Appendable destination, @NotNull Function1 predicate) throws IOException {
        Intrinsics.checkParameterIsNotNull(filterNotTo, "$this$filterNotTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int i = 0; i < filterNotTo.length(); i++) {
            char cCharAt = filterNotTo.charAt(i);
            if (!((Boolean) predicate.invoke(Character.valueOf(cCharAt))).booleanValue()) {
                destination.append(cCharAt);
            }
        }
        return destination;
    }

    @NotNull
    public static final Appendable filterTo(@NotNull CharSequence filterTo, @NotNull Appendable destination, @NotNull Function1 predicate) throws IOException {
        Intrinsics.checkParameterIsNotNull(filterTo, "$this$filterTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = filterTo.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = filterTo.charAt(i);
            if (((Boolean) predicate.invoke(Character.valueOf(cCharAt))).booleanValue()) {
                destination.append(cCharAt);
            }
        }
        return destination;
    }

    @NotNull
    public static final CharSequence slice(@NotNull CharSequence slice, @NotNull IntRange indices) {
        Intrinsics.checkParameterIsNotNull(slice, "$this$slice");
        Intrinsics.checkParameterIsNotNull(indices, "indices");
        return indices.isEmpty() ? "" : StringsKt.subSequence(slice, indices);
    }

    @NotNull
    public static final String slice(@NotNull String slice, @NotNull IntRange indices) {
        Intrinsics.checkParameterIsNotNull(slice, "$this$slice");
        Intrinsics.checkParameterIsNotNull(indices, "indices");
        return indices.isEmpty() ? "" : StringsKt.substring(slice, indices);
    }

    @NotNull
    public static final CharSequence slice(@NotNull CharSequence slice, @NotNull Iterable indices) {
        Intrinsics.checkParameterIsNotNull(slice, "$this$slice");
        Intrinsics.checkParameterIsNotNull(indices, "indices");
        int iCollectionSizeOrDefault = CollectionsKt.collectionSizeOrDefault(indices, 10);
        if (iCollectionSizeOrDefault == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(iCollectionSizeOrDefault);
        Iterator it = indices.iterator();
        while (it.hasNext()) {
            sb.append(slice.charAt(((Number) it.next()).intValue()));
        }
        return sb;
    }

    @InlineOnly
    private static final String slice(@NotNull String str, Iterable iterable) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        return StringsKt.slice((CharSequence) str, iterable).toString();
    }

    @NotNull
    public static final CharSequence take(@NotNull CharSequence take, int i) {
        Intrinsics.checkParameterIsNotNull(take, "$this$take");
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("Requested character count " + i + " is less than zero.").toString());
        }
        return take.subSequence(0, RangesKt.coerceAtMost(i, take.length()));
    }

    @NotNull
    public static final String take(@NotNull String take, int i) {
        Intrinsics.checkParameterIsNotNull(take, "$this$take");
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("Requested character count " + i + " is less than zero.").toString());
        }
        String strSubstring = take.substring(0, RangesKt.coerceAtMost(i, take.length()));
        Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return strSubstring;
    }

    @NotNull
    public static final CharSequence takeLast(@NotNull CharSequence takeLast, int i) {
        Intrinsics.checkParameterIsNotNull(takeLast, "$this$takeLast");
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("Requested character count " + i + " is less than zero.").toString());
        }
        int length = takeLast.length();
        return takeLast.subSequence(length - RangesKt.coerceAtMost(i, length), length);
    }

    @NotNull
    public static final String takeLast(@NotNull String takeLast, int i) {
        Intrinsics.checkParameterIsNotNull(takeLast, "$this$takeLast");
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("Requested character count " + i + " is less than zero.").toString());
        }
        int length = takeLast.length();
        String strSubstring = takeLast.substring(length - RangesKt.coerceAtMost(i, length));
        Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.String).substring(startIndex)");
        return strSubstring;
    }

    @NotNull
    public static final CharSequence takeLastWhile(@NotNull CharSequence takeLastWhile, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(takeLastWhile, "$this$takeLastWhile");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int lastIndex = StringsKt.getLastIndex(takeLastWhile); lastIndex >= 0; lastIndex--) {
            if (!((Boolean) predicate.invoke(Character.valueOf(takeLastWhile.charAt(lastIndex)))).booleanValue()) {
                return takeLastWhile.subSequence(lastIndex + 1, takeLastWhile.length());
            }
        }
        return takeLastWhile.subSequence(0, takeLastWhile.length());
    }

    @NotNull
    public static final String takeLastWhile(@NotNull String takeLastWhile, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(takeLastWhile, "$this$takeLastWhile");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int lastIndex = StringsKt.getLastIndex(takeLastWhile); lastIndex >= 0; lastIndex--) {
            if (!((Boolean) predicate.invoke(Character.valueOf(takeLastWhile.charAt(lastIndex)))).booleanValue()) {
                String strSubstring = takeLastWhile.substring(lastIndex + 1);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.String).substring(startIndex)");
                return strSubstring;
            }
        }
        return takeLastWhile;
    }

    @NotNull
    public static final CharSequence takeWhile(@NotNull CharSequence takeWhile, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(takeWhile, "$this$takeWhile");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = takeWhile.length();
        for (int i = 0; i < length; i++) {
            if (!((Boolean) predicate.invoke(Character.valueOf(takeWhile.charAt(i)))).booleanValue()) {
                return takeWhile.subSequence(0, i);
            }
        }
        return takeWhile.subSequence(0, takeWhile.length());
    }

    @NotNull
    public static final String takeWhile(@NotNull String takeWhile, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(takeWhile, "$this$takeWhile");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int length = takeWhile.length();
        for (int i = 0; i < length; i++) {
            if (!((Boolean) predicate.invoke(Character.valueOf(takeWhile.charAt(i)))).booleanValue()) {
                String strSubstring = takeWhile.substring(0, i);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                return strSubstring;
            }
        }
        return takeWhile;
    }

    @NotNull
    public static final CharSequence reversed(@NotNull CharSequence reversed) {
        Intrinsics.checkParameterIsNotNull(reversed, "$this$reversed");
        StringBuilder sbReverse = new StringBuilder(reversed).reverse();
        Intrinsics.checkExpressionValueIsNotNull(sbReverse, "StringBuilder(this).reverse()");
        return sbReverse;
    }

    @InlineOnly
    private static final String reversed(@NotNull String str) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        return StringsKt.reversed((CharSequence) str).toString();
    }

    @NotNull
    public static final Map associate(@NotNull CharSequence associate, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(associate, "$this$associate");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(associate.length()), 16));
        for (int i = 0; i < associate.length(); i++) {
            Pair pair = (Pair) transform.invoke(Character.valueOf(associate.charAt(i)));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return linkedHashMap;
    }

    @NotNull
    public static final Map associateBy(@NotNull CharSequence associateBy, @NotNull Function1 keySelector) {
        Intrinsics.checkParameterIsNotNull(associateBy, "$this$associateBy");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(associateBy.length()), 16));
        for (int i = 0; i < associateBy.length(); i++) {
            char cCharAt = associateBy.charAt(i);
            linkedHashMap.put(keySelector.invoke(Character.valueOf(cCharAt)), Character.valueOf(cCharAt));
        }
        return linkedHashMap;
    }

    @NotNull
    public static final Map associateBy(@NotNull CharSequence associateBy, @NotNull Function1 keySelector, @NotNull Function1 valueTransform) {
        Intrinsics.checkParameterIsNotNull(associateBy, "$this$associateBy");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(associateBy.length()), 16));
        for (int i = 0; i < associateBy.length(); i++) {
            char cCharAt = associateBy.charAt(i);
            linkedHashMap.put(keySelector.invoke(Character.valueOf(cCharAt)), valueTransform.invoke(Character.valueOf(cCharAt)));
        }
        return linkedHashMap;
    }

    @NotNull
    public static final Map associateByTo(@NotNull CharSequence associateByTo, @NotNull Map destination, @NotNull Function1 keySelector) {
        Intrinsics.checkParameterIsNotNull(associateByTo, "$this$associateByTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        for (int i = 0; i < associateByTo.length(); i++) {
            char cCharAt = associateByTo.charAt(i);
            destination.put(keySelector.invoke(Character.valueOf(cCharAt)), Character.valueOf(cCharAt));
        }
        return destination;
    }

    @NotNull
    public static final Map associateByTo(@NotNull CharSequence associateByTo, @NotNull Map destination, @NotNull Function1 keySelector, @NotNull Function1 valueTransform) {
        Intrinsics.checkParameterIsNotNull(associateByTo, "$this$associateByTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
        for (int i = 0; i < associateByTo.length(); i++) {
            char cCharAt = associateByTo.charAt(i);
            destination.put(keySelector.invoke(Character.valueOf(cCharAt)), valueTransform.invoke(Character.valueOf(cCharAt)));
        }
        return destination;
    }

    @NotNull
    public static final Map associateTo(@NotNull CharSequence associateTo, @NotNull Map destination, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(associateTo, "$this$associateTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        for (int i = 0; i < associateTo.length(); i++) {
            Pair pair = (Pair) transform.invoke(Character.valueOf(associateTo.charAt(i)));
            destination.put(pair.getFirst(), pair.getSecond());
        }
        return destination;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final Map associateWith(@NotNull CharSequence associateWith, @NotNull Function1 valueSelector) {
        Intrinsics.checkParameterIsNotNull(associateWith, "$this$associateWith");
        Intrinsics.checkParameterIsNotNull(valueSelector, "valueSelector");
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(associateWith.length()), 16));
        for (int i = 0; i < associateWith.length(); i++) {
            char cCharAt = associateWith.charAt(i);
            linkedHashMap.put(Character.valueOf(cCharAt), valueSelector.invoke(Character.valueOf(cCharAt)));
        }
        return linkedHashMap;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final Map associateWithTo(@NotNull CharSequence associateWithTo, @NotNull Map destination, @NotNull Function1 valueSelector) {
        Intrinsics.checkParameterIsNotNull(associateWithTo, "$this$associateWithTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(valueSelector, "valueSelector");
        for (int i = 0; i < associateWithTo.length(); i++) {
            char cCharAt = associateWithTo.charAt(i);
            destination.put(Character.valueOf(cCharAt), valueSelector.invoke(Character.valueOf(cCharAt)));
        }
        return destination;
    }

    @NotNull
    public static final Collection toCollection(@NotNull CharSequence toCollection, @NotNull Collection destination) {
        Intrinsics.checkParameterIsNotNull(toCollection, "$this$toCollection");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        for (int i = 0; i < toCollection.length(); i++) {
            destination.add(Character.valueOf(toCollection.charAt(i)));
        }
        return destination;
    }

    @NotNull
    public static final HashSet toHashSet(@NotNull CharSequence toHashSet) {
        Intrinsics.checkParameterIsNotNull(toHashSet, "$this$toHashSet");
        return (HashSet) StringsKt.toCollection(toHashSet, new HashSet(MapsKt.mapCapacity(toHashSet.length())));
    }

    @NotNull
    public static final List toList(@NotNull CharSequence toList) {
        Intrinsics.checkParameterIsNotNull(toList, "$this$toList");
        switch (toList.length()) {
            case 0:
                return CollectionsKt.emptyList();
            case 1:
                return CollectionsKt.listOf(Character.valueOf(toList.charAt(0)));
            default:
                return StringsKt.toMutableList(toList);
        }
    }

    @NotNull
    public static final List toMutableList(@NotNull CharSequence toMutableList) {
        Intrinsics.checkParameterIsNotNull(toMutableList, "$this$toMutableList");
        return (List) StringsKt.toCollection(toMutableList, new ArrayList(toMutableList.length()));
    }

    @NotNull
    public static final Set toSet(@NotNull CharSequence toSet) {
        Intrinsics.checkParameterIsNotNull(toSet, "$this$toSet");
        switch (toSet.length()) {
            case 0:
                return SetsKt.emptySet();
            case 1:
                return SetsKt.setOf(Character.valueOf(toSet.charAt(0)));
            default:
                return (Set) StringsKt.toCollection(toSet, new LinkedHashSet(MapsKt.mapCapacity(toSet.length())));
        }
    }

    @NotNull
    public static final List flatMap(@NotNull CharSequence flatMap, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(flatMap, "$this$flatMap");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < flatMap.length(); i++) {
            CollectionsKt.addAll(arrayList, (Iterable) transform.invoke(Character.valueOf(flatMap.charAt(i))));
        }
        return arrayList;
    }

    @NotNull
    public static final Collection flatMapTo(@NotNull CharSequence flatMapTo, @NotNull Collection destination, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(flatMapTo, "$this$flatMapTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        for (int i = 0; i < flatMapTo.length(); i++) {
            CollectionsKt.addAll(destination, (Iterable) transform.invoke(Character.valueOf(flatMapTo.charAt(i))));
        }
        return destination;
    }

    @NotNull
    public static final Map groupBy(@NotNull CharSequence groupBy, @NotNull Function1 keySelector) {
        Object obj;
        Intrinsics.checkParameterIsNotNull(groupBy, "$this$groupBy");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (int i = 0; i < groupBy.length(); i++) {
            char cCharAt = groupBy.charAt(i);
            Object objInvoke = keySelector.invoke(Character.valueOf(cCharAt));
            Object obj2 = linkedHashMap.get(objInvoke);
            if (obj2 == null) {
                ArrayList arrayList = new ArrayList();
                linkedHashMap.put(objInvoke, arrayList);
                obj = arrayList;
            } else {
                obj = obj2;
            }
            ((List) obj).add(Character.valueOf(cCharAt));
        }
        return linkedHashMap;
    }

    @NotNull
    public static final Map groupBy(@NotNull CharSequence groupBy, @NotNull Function1 keySelector, @NotNull Function1 valueTransform) {
        Object obj;
        Intrinsics.checkParameterIsNotNull(groupBy, "$this$groupBy");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (int i = 0; i < groupBy.length(); i++) {
            char cCharAt = groupBy.charAt(i);
            Object objInvoke = keySelector.invoke(Character.valueOf(cCharAt));
            Object obj2 = linkedHashMap.get(objInvoke);
            if (obj2 == null) {
                ArrayList arrayList = new ArrayList();
                linkedHashMap.put(objInvoke, arrayList);
                obj = arrayList;
            } else {
                obj = obj2;
            }
            ((List) obj).add(valueTransform.invoke(Character.valueOf(cCharAt)));
        }
        return linkedHashMap;
    }

    @NotNull
    public static final Map groupByTo(@NotNull CharSequence groupByTo, @NotNull Map destination, @NotNull Function1 keySelector) {
        Object obj;
        Intrinsics.checkParameterIsNotNull(groupByTo, "$this$groupByTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        for (int i = 0; i < groupByTo.length(); i++) {
            char cCharAt = groupByTo.charAt(i);
            Object objInvoke = keySelector.invoke(Character.valueOf(cCharAt));
            Object obj2 = destination.get(objInvoke);
            if (obj2 == null) {
                ArrayList arrayList = new ArrayList();
                destination.put(objInvoke, arrayList);
                obj = arrayList;
            } else {
                obj = obj2;
            }
            ((List) obj).add(Character.valueOf(cCharAt));
        }
        return destination;
    }

    @NotNull
    public static final Map groupByTo(@NotNull CharSequence groupByTo, @NotNull Map destination, @NotNull Function1 keySelector, @NotNull Function1 valueTransform) {
        Object obj;
        Intrinsics.checkParameterIsNotNull(groupByTo, "$this$groupByTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        Intrinsics.checkParameterIsNotNull(valueTransform, "valueTransform");
        for (int i = 0; i < groupByTo.length(); i++) {
            char cCharAt = groupByTo.charAt(i);
            Object objInvoke = keySelector.invoke(Character.valueOf(cCharAt));
            Object obj2 = destination.get(objInvoke);
            if (obj2 == null) {
                ArrayList arrayList = new ArrayList();
                destination.put(objInvoke, arrayList);
                obj = arrayList;
            } else {
                obj = obj2;
            }
            ((List) obj).add(valueTransform.invoke(Character.valueOf(cCharAt)));
        }
        return destination;
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Grouping groupingBy(@NotNull CharSequence groupingBy, @NotNull Function1 keySelector) {
        Intrinsics.checkParameterIsNotNull(groupingBy, "$this$groupingBy");
        Intrinsics.checkParameterIsNotNull(keySelector, "keySelector");
        return new Grouping(groupingBy, keySelector) { // from class: kotlin.text.StringsKt___StringsKt.groupingBy.1
            final CharSequence $this_groupingBy;
            final Function1 $keySelector;

            @Override // kotlin.collections.Grouping
            public Object keyOf(Object obj) {
                return keyOf(((Character) obj).charValue());
            }

            {
                this.$this_groupingBy = groupingBy;
                this.$keySelector = keySelector;
            }

            @Override // kotlin.collections.Grouping
            @NotNull
            public Iterator sourceIterator() {
                return StringsKt.iterator(this.$this_groupingBy);
            }

            public Object keyOf(char c) {
                return this.$keySelector.invoke(Character.valueOf(c));
            }
        };
    }

    @NotNull
    public static final List map(@NotNull CharSequence map, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(map, "$this$map");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        ArrayList arrayList = new ArrayList(map.length());
        for (int i = 0; i < map.length(); i++) {
            arrayList.add(transform.invoke(Character.valueOf(map.charAt(i))));
        }
        return arrayList;
    }

    @NotNull
    public static final List mapIndexed(@NotNull CharSequence mapIndexed, @NotNull Function2 transform) {
        Intrinsics.checkParameterIsNotNull(mapIndexed, "$this$mapIndexed");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        ArrayList arrayList = new ArrayList(mapIndexed.length());
        int i = 0;
        for (int i2 = 0; i2 < mapIndexed.length(); i2++) {
            char cCharAt = mapIndexed.charAt(i2);
            Integer numValueOf = Integer.valueOf(i);
            i++;
            arrayList.add(transform.invoke(numValueOf, Character.valueOf(cCharAt)));
        }
        return arrayList;
    }

    @NotNull
    public static final List mapIndexedNotNull(@NotNull CharSequence mapIndexedNotNull, @NotNull Function2 transform) {
        Intrinsics.checkParameterIsNotNull(mapIndexedNotNull, "$this$mapIndexedNotNull");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        ArrayList arrayList = new ArrayList();
        int i = 0;
        for (int i2 = 0; i2 < mapIndexedNotNull.length(); i2++) {
            int i3 = i;
            i++;
            Object objInvoke = transform.invoke(Integer.valueOf(i3), Character.valueOf(mapIndexedNotNull.charAt(i2)));
            if (objInvoke != null) {
                arrayList.add(objInvoke);
            }
        }
        return arrayList;
    }

    @NotNull
    public static final Collection mapIndexedNotNullTo(@NotNull CharSequence mapIndexedNotNullTo, @NotNull Collection destination, @NotNull Function2 transform) {
        Intrinsics.checkParameterIsNotNull(mapIndexedNotNullTo, "$this$mapIndexedNotNullTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        int i = 0;
        for (int i2 = 0; i2 < mapIndexedNotNullTo.length(); i2++) {
            int i3 = i;
            i++;
            Object objInvoke = transform.invoke(Integer.valueOf(i3), Character.valueOf(mapIndexedNotNullTo.charAt(i2)));
            if (objInvoke != null) {
                destination.add(objInvoke);
            }
        }
        return destination;
    }

    @NotNull
    public static final Collection mapIndexedTo(@NotNull CharSequence mapIndexedTo, @NotNull Collection destination, @NotNull Function2 transform) {
        Intrinsics.checkParameterIsNotNull(mapIndexedTo, "$this$mapIndexedTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        int i = 0;
        for (int i2 = 0; i2 < mapIndexedTo.length(); i2++) {
            char cCharAt = mapIndexedTo.charAt(i2);
            Integer numValueOf = Integer.valueOf(i);
            i++;
            destination.add(transform.invoke(numValueOf, Character.valueOf(cCharAt)));
        }
        return destination;
    }

    @NotNull
    public static final List mapNotNull(@NotNull CharSequence mapNotNull, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(mapNotNull, "$this$mapNotNull");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < mapNotNull.length(); i++) {
            Object objInvoke = transform.invoke(Character.valueOf(mapNotNull.charAt(i)));
            if (objInvoke != null) {
                arrayList.add(objInvoke);
            }
        }
        return arrayList;
    }

    @NotNull
    public static final Collection mapNotNullTo(@NotNull CharSequence mapNotNullTo, @NotNull Collection destination, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(mapNotNullTo, "$this$mapNotNullTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        for (int i = 0; i < mapNotNullTo.length(); i++) {
            Object objInvoke = transform.invoke(Character.valueOf(mapNotNullTo.charAt(i)));
            if (objInvoke != null) {
                destination.add(objInvoke);
            }
        }
        return destination;
    }

    @NotNull
    public static final Collection mapTo(@NotNull CharSequence mapTo, @NotNull Collection destination, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(mapTo, "$this$mapTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        for (int i = 0; i < mapTo.length(); i++) {
            destination.add(transform.invoke(Character.valueOf(mapTo.charAt(i))));
        }
        return destination;
    }

    @NotNull
    public static final Iterable withIndex(@NotNull CharSequence withIndex) {
        Intrinsics.checkParameterIsNotNull(withIndex, "$this$withIndex");
        return new IndexingIterable(new Function0(withIndex) { // from class: kotlin.text.StringsKt___StringsKt.withIndex.1
            final CharSequence $this_withIndex;

            @Override // kotlin.jvm.functions.Function0
            public Object invoke() {
                return invoke();
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.$this_withIndex = withIndex;
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final CharIterator invoke() {
                return StringsKt.iterator(this.$this_withIndex);
            }
        });
    }

    public static final boolean all(@NotNull CharSequence all, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(all, "$this$all");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int i = 0; i < all.length(); i++) {
            if (!((Boolean) predicate.invoke(Character.valueOf(all.charAt(i)))).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    public static final boolean any(@NotNull CharSequence any) {
        Intrinsics.checkParameterIsNotNull(any, "$this$any");
        return !(any.length() == 0);
    }

    public static final boolean any(@NotNull CharSequence any, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(any, "$this$any");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int i = 0; i < any.length(); i++) {
            if (((Boolean) predicate.invoke(Character.valueOf(any.charAt(i)))).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    @InlineOnly
    private static final int count(@NotNull CharSequence charSequence) {
        return charSequence.length();
    }

    public static final int count(@NotNull CharSequence count, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(count, "$this$count");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        int i = 0;
        for (int i2 = 0; i2 < count.length(); i2++) {
            if (((Boolean) predicate.invoke(Character.valueOf(count.charAt(i2)))).booleanValue()) {
                i++;
            }
        }
        return i;
    }

    public static final Object fold(@NotNull CharSequence fold, Object obj, @NotNull Function2 operation) {
        Intrinsics.checkParameterIsNotNull(fold, "$this$fold");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Object objInvoke = obj;
        for (int i = 0; i < fold.length(); i++) {
            objInvoke = operation.invoke(objInvoke, Character.valueOf(fold.charAt(i)));
        }
        return objInvoke;
    }

    public static final Object foldIndexed(@NotNull CharSequence foldIndexed, Object obj, @NotNull Function3 operation) {
        Intrinsics.checkParameterIsNotNull(foldIndexed, "$this$foldIndexed");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        int i = 0;
        Object objInvoke = obj;
        for (int i2 = 0; i2 < foldIndexed.length(); i2++) {
            char cCharAt = foldIndexed.charAt(i2);
            Integer numValueOf = Integer.valueOf(i);
            i++;
            objInvoke = operation.invoke(numValueOf, objInvoke, Character.valueOf(cCharAt));
        }
        return objInvoke;
    }

    public static final Object foldRight(@NotNull CharSequence foldRight, Object obj, @NotNull Function2 operation) {
        Intrinsics.checkParameterIsNotNull(foldRight, "$this$foldRight");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        int lastIndex = StringsKt.getLastIndex(foldRight);
        Object objInvoke = obj;
        while (true) {
            Object obj2 = objInvoke;
            if (lastIndex >= 0) {
                int i = lastIndex;
                lastIndex--;
                objInvoke = operation.invoke(Character.valueOf(foldRight.charAt(i)), obj2);
            } else {
                return obj2;
            }
        }
    }

    public static final Object foldRightIndexed(@NotNull CharSequence foldRightIndexed, Object obj, @NotNull Function3 operation) {
        Intrinsics.checkParameterIsNotNull(foldRightIndexed, "$this$foldRightIndexed");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        Object objInvoke = obj;
        for (int lastIndex = StringsKt.getLastIndex(foldRightIndexed); lastIndex >= 0; lastIndex--) {
            objInvoke = operation.invoke(Integer.valueOf(lastIndex), Character.valueOf(foldRightIndexed.charAt(lastIndex)), objInvoke);
        }
        return objInvoke;
    }

    public static final void forEach(@NotNull CharSequence forEach, @NotNull Function1 action) {
        Intrinsics.checkParameterIsNotNull(forEach, "$this$forEach");
        Intrinsics.checkParameterIsNotNull(action, "action");
        for (int i = 0; i < forEach.length(); i++) {
            action.invoke(Character.valueOf(forEach.charAt(i)));
        }
    }

    public static final void forEachIndexed(@NotNull CharSequence forEachIndexed, @NotNull Function2 action) {
        Intrinsics.checkParameterIsNotNull(forEachIndexed, "$this$forEachIndexed");
        Intrinsics.checkParameterIsNotNull(action, "action");
        int i = 0;
        for (int i2 = 0; i2 < forEachIndexed.length(); i2++) {
            char cCharAt = forEachIndexed.charAt(i2);
            Integer numValueOf = Integer.valueOf(i);
            i++;
            action.invoke(numValueOf, Character.valueOf(cCharAt));
        }
    }

    @Nullable
    public static final Character max(@NotNull CharSequence max) {
        Intrinsics.checkParameterIsNotNull(max, "$this$max");
        if (max.length() == 0) {
            return null;
        }
        char cCharAt = max.charAt(0);
        int i = 1;
        int lastIndex = StringsKt.getLastIndex(max);
        if (1 <= lastIndex) {
            while (true) {
                char cCharAt2 = max.charAt(i);
                if (cCharAt < cCharAt2) {
                    cCharAt = cCharAt2;
                }
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return Character.valueOf(cCharAt);
    }

    @Nullable
    public static final Character maxBy(@NotNull CharSequence maxBy, @NotNull Function1 selector) {
        Intrinsics.checkParameterIsNotNull(maxBy, "$this$maxBy");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        if (maxBy.length() == 0) {
            return null;
        }
        char cCharAt = maxBy.charAt(0);
        int lastIndex = StringsKt.getLastIndex(maxBy);
        if (lastIndex == 0) {
            return Character.valueOf(cCharAt);
        }
        Comparable comparable = (Comparable) selector.invoke(Character.valueOf(cCharAt));
        int i = 1;
        if (1 <= lastIndex) {
            while (true) {
                char cCharAt2 = maxBy.charAt(i);
                Comparable comparable2 = (Comparable) selector.invoke(Character.valueOf(cCharAt2));
                if (comparable.compareTo(comparable2) < 0) {
                    cCharAt = cCharAt2;
                    comparable = comparable2;
                }
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return Character.valueOf(cCharAt);
    }

    @Nullable
    public static final Character maxWith(@NotNull CharSequence maxWith, @NotNull Comparator comparator) {
        Intrinsics.checkParameterIsNotNull(maxWith, "$this$maxWith");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        if (maxWith.length() == 0) {
            return null;
        }
        char cCharAt = maxWith.charAt(0);
        int i = 1;
        int lastIndex = StringsKt.getLastIndex(maxWith);
        if (1 <= lastIndex) {
            while (true) {
                char cCharAt2 = maxWith.charAt(i);
                if (comparator.compare(Character.valueOf(cCharAt), Character.valueOf(cCharAt2)) < 0) {
                    cCharAt = cCharAt2;
                }
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return Character.valueOf(cCharAt);
    }

    @Nullable
    public static final Character min(@NotNull CharSequence min) {
        Intrinsics.checkParameterIsNotNull(min, "$this$min");
        if (min.length() == 0) {
            return null;
        }
        char cCharAt = min.charAt(0);
        int i = 1;
        int lastIndex = StringsKt.getLastIndex(min);
        if (1 <= lastIndex) {
            while (true) {
                char cCharAt2 = min.charAt(i);
                if (cCharAt > cCharAt2) {
                    cCharAt = cCharAt2;
                }
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return Character.valueOf(cCharAt);
    }

    @Nullable
    public static final Character minBy(@NotNull CharSequence minBy, @NotNull Function1 selector) {
        Intrinsics.checkParameterIsNotNull(minBy, "$this$minBy");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        if (minBy.length() == 0) {
            return null;
        }
        char cCharAt = minBy.charAt(0);
        int lastIndex = StringsKt.getLastIndex(minBy);
        if (lastIndex == 0) {
            return Character.valueOf(cCharAt);
        }
        Comparable comparable = (Comparable) selector.invoke(Character.valueOf(cCharAt));
        int i = 1;
        if (1 <= lastIndex) {
            while (true) {
                char cCharAt2 = minBy.charAt(i);
                Comparable comparable2 = (Comparable) selector.invoke(Character.valueOf(cCharAt2));
                if (comparable.compareTo(comparable2) > 0) {
                    cCharAt = cCharAt2;
                    comparable = comparable2;
                }
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return Character.valueOf(cCharAt);
    }

    @Nullable
    public static final Character minWith(@NotNull CharSequence minWith, @NotNull Comparator comparator) {
        Intrinsics.checkParameterIsNotNull(minWith, "$this$minWith");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        if (minWith.length() == 0) {
            return null;
        }
        char cCharAt = minWith.charAt(0);
        int i = 1;
        int lastIndex = StringsKt.getLastIndex(minWith);
        if (1 <= lastIndex) {
            while (true) {
                char cCharAt2 = minWith.charAt(i);
                if (comparator.compare(Character.valueOf(cCharAt), Character.valueOf(cCharAt2)) > 0) {
                    cCharAt = cCharAt2;
                }
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return Character.valueOf(cCharAt);
    }

    public static final boolean none(@NotNull CharSequence none) {
        Intrinsics.checkParameterIsNotNull(none, "$this$none");
        return none.length() == 0;
    }

    public static final boolean none(@NotNull CharSequence none, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(none, "$this$none");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (int i = 0; i < none.length(); i++) {
            if (((Boolean) predicate.invoke(Character.valueOf(none.charAt(i)))).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final CharSequence onEach(@NotNull CharSequence onEach, @NotNull Function1 action) {
        Intrinsics.checkParameterIsNotNull(onEach, "$this$onEach");
        Intrinsics.checkParameterIsNotNull(action, "action");
        for (int i = 0; i < onEach.length(); i++) {
            action.invoke(Character.valueOf(onEach.charAt(i)));
        }
        return onEach;
    }

    public static final char reduce(@NotNull CharSequence reduce, @NotNull Function2 operation) {
        Intrinsics.checkParameterIsNotNull(reduce, "$this$reduce");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        if (reduce.length() == 0) {
            throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
        }
        char cCharAt = reduce.charAt(0);
        int i = 1;
        int lastIndex = StringsKt.getLastIndex(reduce);
        if (1 <= lastIndex) {
            while (true) {
                cCharAt = ((Character) operation.invoke(Character.valueOf(cCharAt), Character.valueOf(reduce.charAt(i)))).charValue();
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return cCharAt;
    }

    public static final char reduceIndexed(@NotNull CharSequence reduceIndexed, @NotNull Function3 operation) {
        Intrinsics.checkParameterIsNotNull(reduceIndexed, "$this$reduceIndexed");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        if (reduceIndexed.length() == 0) {
            throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
        }
        char cCharAt = reduceIndexed.charAt(0);
        int i = 1;
        int lastIndex = StringsKt.getLastIndex(reduceIndexed);
        if (1 <= lastIndex) {
            while (true) {
                cCharAt = ((Character) operation.invoke(Integer.valueOf(i), Character.valueOf(cCharAt), Character.valueOf(reduceIndexed.charAt(i)))).charValue();
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        return cCharAt;
    }

    public static final char reduceRight(@NotNull CharSequence reduceRight, @NotNull Function2 operation) {
        Intrinsics.checkParameterIsNotNull(reduceRight, "$this$reduceRight");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        int lastIndex = StringsKt.getLastIndex(reduceRight);
        if (lastIndex < 0) {
            throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
        }
        int i = lastIndex - 1;
        char cCharAt = reduceRight.charAt(lastIndex);
        while (true) {
            char c = cCharAt;
            if (i >= 0) {
                int i2 = i;
                i--;
                cCharAt = ((Character) operation.invoke(Character.valueOf(reduceRight.charAt(i2)), Character.valueOf(c))).charValue();
            } else {
                return c;
            }
        }
    }

    public static final char reduceRightIndexed(@NotNull CharSequence reduceRightIndexed, @NotNull Function3 operation) {
        Intrinsics.checkParameterIsNotNull(reduceRightIndexed, "$this$reduceRightIndexed");
        Intrinsics.checkParameterIsNotNull(operation, "operation");
        int lastIndex = StringsKt.getLastIndex(reduceRightIndexed);
        if (lastIndex < 0) {
            throw new UnsupportedOperationException("Empty char sequence can't be reduced.");
        }
        char cCharAt = reduceRightIndexed.charAt(lastIndex);
        for (int i = lastIndex - 1; i >= 0; i--) {
            cCharAt = ((Character) operation.invoke(Integer.valueOf(i), Character.valueOf(reduceRightIndexed.charAt(i)), Character.valueOf(cCharAt))).charValue();
        }
        return cCharAt;
    }

    public static final int sumBy(@NotNull CharSequence sumBy, @NotNull Function1 selector) {
        Intrinsics.checkParameterIsNotNull(sumBy, "$this$sumBy");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        int iIntValue = 0;
        for (int i = 0; i < sumBy.length(); i++) {
            iIntValue += ((Number) selector.invoke(Character.valueOf(sumBy.charAt(i)))).intValue();
        }
        return iIntValue;
    }

    public static final double sumByDouble(@NotNull CharSequence sumByDouble, @NotNull Function1 selector) {
        Intrinsics.checkParameterIsNotNull(sumByDouble, "$this$sumByDouble");
        Intrinsics.checkParameterIsNotNull(selector, "selector");
        double dDoubleValue = 0.0d;
        for (int i = 0; i < sumByDouble.length(); i++) {
            dDoubleValue += ((Number) selector.invoke(Character.valueOf(sumByDouble.charAt(i)))).doubleValue();
        }
        return dDoubleValue;
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final List chunked(@NotNull CharSequence chunked, int i) {
        Intrinsics.checkParameterIsNotNull(chunked, "$this$chunked");
        return StringsKt.windowed(chunked, i, i, true);
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final List chunked(@NotNull CharSequence chunked, int i, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(chunked, "$this$chunked");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        return StringsKt.windowed(chunked, i, i, true, transform);
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final Sequence chunkedSequence(@NotNull CharSequence chunkedSequence, int i) {
        Intrinsics.checkParameterIsNotNull(chunkedSequence, "$this$chunkedSequence");
        return StringsKt.chunkedSequence(chunkedSequence, i, new Function1() { // from class: kotlin.text.StringsKt___StringsKt.chunkedSequence.1
            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return invoke((CharSequence) obj);
            }

            @NotNull
            public final String invoke(@NotNull CharSequence it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return it.toString();
            }
        });
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final Sequence chunkedSequence(@NotNull CharSequence chunkedSequence, int i, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(chunkedSequence, "$this$chunkedSequence");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        return StringsKt.windowedSequence(chunkedSequence, i, i, true, transform);
    }

    @NotNull
    public static final Pair partition(@NotNull CharSequence partition, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(partition, "$this$partition");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < partition.length(); i++) {
            char cCharAt = partition.charAt(i);
            if (((Boolean) predicate.invoke(Character.valueOf(cCharAt))).booleanValue()) {
                sb.append(cCharAt);
            } else {
                sb2.append(cCharAt);
            }
        }
        return new Pair(sb, sb2);
    }

    @NotNull
    public static final Pair partition(@NotNull String partition, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(partition, "$this$partition");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        int length = partition.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = partition.charAt(i);
            if (((Boolean) predicate.invoke(Character.valueOf(cCharAt))).booleanValue()) {
                sb.append(cCharAt);
            } else {
                sb2.append(cCharAt);
            }
        }
        return new Pair(sb.toString(), sb2.toString());
    }

    public static List windowed$default(CharSequence charSequence, int i, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 1;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return StringsKt.windowed(charSequence, i, i2, z);
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final List windowed(@NotNull CharSequence windowed, int i, int i2, boolean z) {
        Intrinsics.checkParameterIsNotNull(windowed, "$this$windowed");
        return StringsKt.windowed(windowed, i, i2, z, new Function1() { // from class: kotlin.text.StringsKt___StringsKt.windowed.1
            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return invoke((CharSequence) obj);
            }

            @NotNull
            public final String invoke(@NotNull CharSequence it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return it.toString();
            }
        });
    }

    public static List windowed$default(CharSequence charSequence, int i, int i2, boolean z, Function1 function1, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 1;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return StringsKt.windowed(charSequence, i, i2, z, function1);
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final List windowed(@NotNull CharSequence windowed, int i, int i2, boolean z, @NotNull Function1 transform) {
        int i3;
        Intrinsics.checkParameterIsNotNull(windowed, "$this$windowed");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        SlidingWindowKt.checkWindowSizeStep(i, i2);
        int length = windowed.length();
        ArrayList arrayList = new ArrayList((length / i2) + (length % i2 == 0 ? 0 : 1));
        int i4 = 0;
        while (true) {
            int i5 = i4;
            if (0 > i5 || length <= i5) {
                break;
            }
            int i6 = i5 + i;
            if (i6 >= 0 && i6 <= length) {
                i3 = i6;
            } else {
                if (!z) {
                    break;
                }
                i3 = length;
            }
            arrayList.add(transform.invoke(windowed.subSequence(i5, i3)));
            i4 = i5 + i2;
        }
        return arrayList;
    }

    public static Sequence windowedSequence$default(CharSequence charSequence, int i, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 1;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return StringsKt.windowedSequence(charSequence, i, i2, z);
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final Sequence windowedSequence(@NotNull CharSequence windowedSequence, int i, int i2, boolean z) {
        Intrinsics.checkParameterIsNotNull(windowedSequence, "$this$windowedSequence");
        return StringsKt.windowedSequence(windowedSequence, i, i2, z, new Function1() { // from class: kotlin.text.StringsKt___StringsKt.windowedSequence.1
            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return invoke((CharSequence) obj);
            }

            @NotNull
            public final String invoke(@NotNull CharSequence it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return it.toString();
            }
        });
    }

    public static Sequence windowedSequence$default(CharSequence charSequence, int i, int i2, boolean z, Function1 function1, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 1;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return StringsKt.windowedSequence(charSequence, i, i2, z, function1);
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final Sequence windowedSequence(@NotNull CharSequence windowedSequence, int i, int i2, boolean z, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(windowedSequence, "$this$windowedSequence");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        SlidingWindowKt.checkWindowSizeStep(i, i2);
        return SequencesKt.map(CollectionsKt.asSequence(RangesKt.step(z ? StringsKt.getIndices(windowedSequence) : RangesKt.until(0, (windowedSequence.length() - i) + 1), i2)), new Function1(windowedSequence, i, transform) { // from class: kotlin.text.StringsKt___StringsKt.windowedSequence.2
            final CharSequence $this_windowedSequence;
            final int $size;
            final Function1 $transform;

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return invoke(((Number) obj).intValue());
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.$this_windowedSequence = windowedSequence;
                this.$size = i;
                this.$transform = transform;
            }

            public final Object invoke(int i3) {
                int i4 = i3 + this.$size;
                return this.$transform.invoke(this.$this_windowedSequence.subSequence(i3, (i4 < 0 || i4 > this.$this_windowedSequence.length()) ? this.$this_windowedSequence.length() : i4));
            }
        });
    }

    @NotNull
    public static final List zip(@NotNull CharSequence zip, @NotNull CharSequence other) {
        Intrinsics.checkParameterIsNotNull(zip, "$this$zip");
        Intrinsics.checkParameterIsNotNull(other, "other");
        int iMin = Math.min(zip.length(), other.length());
        ArrayList arrayList = new ArrayList(iMin);
        for (int i = 0; i < iMin; i++) {
            arrayList.add(TuplesKt.m32to(Character.valueOf(zip.charAt(i)), Character.valueOf(other.charAt(i))));
        }
        return arrayList;
    }

    @NotNull
    public static final List zip(@NotNull CharSequence zip, @NotNull CharSequence other, @NotNull Function2 transform) {
        Intrinsics.checkParameterIsNotNull(zip, "$this$zip");
        Intrinsics.checkParameterIsNotNull(other, "other");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        int iMin = Math.min(zip.length(), other.length());
        ArrayList arrayList = new ArrayList(iMin);
        for (int i = 0; i < iMin; i++) {
            arrayList.add(transform.invoke(Character.valueOf(zip.charAt(i)), Character.valueOf(other.charAt(i))));
        }
        return arrayList;
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final List zipWithNext(@NotNull CharSequence zipWithNext) {
        Intrinsics.checkParameterIsNotNull(zipWithNext, "$this$zipWithNext");
        int length = zipWithNext.length() - 1;
        if (length < 1) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList(length);
        for (int i = 0; i < length; i++) {
            arrayList.add(TuplesKt.m32to(Character.valueOf(zipWithNext.charAt(i)), Character.valueOf(zipWithNext.charAt(i + 1))));
        }
        return arrayList;
    }

    @SinceKotlin(version = "1.2")
    @NotNull
    public static final List zipWithNext(@NotNull CharSequence zipWithNext, @NotNull Function2 transform) {
        Intrinsics.checkParameterIsNotNull(zipWithNext, "$this$zipWithNext");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        int length = zipWithNext.length() - 1;
        if (length < 1) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList(length);
        for (int i = 0; i < length; i++) {
            arrayList.add(transform.invoke(Character.valueOf(zipWithNext.charAt(i)), Character.valueOf(zipWithNext.charAt(i + 1))));
        }
        return arrayList;
    }

    @NotNull
    public static final Iterable asIterable(@NotNull CharSequence asIterable) {
        Intrinsics.checkParameterIsNotNull(asIterable, "$this$asIterable");
        if (asIterable instanceof String) {
            if (asIterable.length() == 0) {
                return CollectionsKt.emptyList();
            }
        }
        return new StringsKt___StringsKt$asIterable$$inlined$Iterable$1(asIterable);
    }

    @NotNull
    public static final Sequence asSequence(@NotNull final CharSequence asSequence) {
        Intrinsics.checkParameterIsNotNull(asSequence, "$this$asSequence");
        if (asSequence instanceof String) {
            if (asSequence.length() == 0) {
                return SequencesKt.emptySequence();
            }
        }
        return new Sequence(asSequence) { // from class: kotlin.text.StringsKt___StringsKt$asSequence$$inlined$Sequence$1
            final CharSequence $this_asSequence$inlined;

            {
                this.$this_asSequence$inlined = asSequence;
            }

            @Override // kotlin.sequences.Sequence
            @NotNull
            public Iterator iterator() {
                return StringsKt.iterator(this.$this_asSequence$inlined);
            }
        };
    }
}
