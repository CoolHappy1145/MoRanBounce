package kotlin.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd~\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010%\n\ufffd\ufffd\n\u0002\u0010&\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010(\n\u0002\u0010)\n\u0002\u0010'\n\u0002\b\n\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0016\u001a\u001e\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005\u001a1\u0010\u0006\u001a\u001e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0007j\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005`\b\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005H\u0087\b\u001a_\u0010\u0006\u001a\u001e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0007j\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005`\b\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u00052*\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n\"\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b\u00a2\u0006\u0002\u0010\f\u001a1\u0010\r\u001a\u001e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000ej\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005`\u000f\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005H\u0087\b\u001a_\u0010\r\u001a\u001e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000ej\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005`\u000f\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u00052*\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n\"\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b\u00a2\u0006\u0002\u0010\u0010\u001a\u0010\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0001H\u0001\u001a!\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005H\u0087\b\u001aO\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u00052*\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n\"\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b\u00a2\u0006\u0002\u0010\u0014\u001a!\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0016\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005H\u0087\b\u001aO\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0016\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u00052*\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n\"\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b\u00a2\u0006\u0002\u0010\u0014\u001a*\u0010\u0017\u001a\u0002H\u0004\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018H\u0087\n\u00a2\u0006\u0002\u0010\u0019\u001a*\u0010\u001a\u001a\u0002H\u0005\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018H\u0087\n\u00a2\u0006\u0002\u0010\u0019\u001a9\u0010\u001b\u001a\u00020\u001c\"\t\b\ufffd\ufffd\u0010\u0004\u00a2\u0006\u0002\b\u001d\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\u001e\u001a\u0002H\u0004H\u0087\n\u00a2\u0006\u0002\u0010\u001f\u001a1\u0010 \u001a\u00020\u001c\"\t\b\ufffd\ufffd\u0010\u0004\u00a2\u0006\u0002\b\u001d*\u000e\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0002\b\u00030\u00032\u0006\u0010\u001e\u001a\u0002H\u0004H\u0087\b\u00a2\u0006\u0002\u0010\u001f\u001a7\u0010!\u001a\u00020\u001c\"\u0004\b\ufffd\ufffd\u0010\u0004\"\t\b\u0001\u0010\u0005\u00a2\u0006\u0002\b\u001d*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\"\u001a\u0002H\u0005H\u0087\b\u00a2\u0006\u0002\u0010\u001f\u001aS\u0010#\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u001e\u0010$\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u00020\u001c0%H\u0086\b\u001aG\u0010&\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u00020\u001c0%H\u0086\b\u001aS\u0010'\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u001e\u0010$\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u00020\u001c0%H\u0086\b\u001an\u0010(\u001a\u0002H)\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0018\b\u0002\u0010)*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0004\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00050\u0016*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010*\u001a\u0002H)2\u001e\u0010$\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u00020\u001c0%H\u0086\b\u00a2\u0006\u0002\u0010+\u001an\u0010,\u001a\u0002H)\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0018\b\u0002\u0010)*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0004\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00050\u0016*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010*\u001a\u0002H)2\u001e\u0010$\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u00020\u001c0%H\u0086\b\u00a2\u0006\u0002\u0010+\u001aG\u0010-\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u00020\u001c0%H\u0086\b\u001a;\u0010.\u001a\u0004\u0018\u0001H\u0005\"\t\b\ufffd\ufffd\u0010\u0004\u00a2\u0006\u0002\b\u001d\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\u001e\u001a\u0002H\u0004H\u0087\n\u00a2\u0006\u0002\u0010/\u001a@\u00100\u001a\u0002H\u0005\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\u001e\u001a\u0002H\u00042\f\u00101\u001a\b\u0012\u0004\u0012\u0002H\u000502H\u0087\b\u00a2\u0006\u0002\u00103\u001a@\u00104\u001a\u0002H\u0005\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\u001e\u001a\u0002H\u00042\f\u00101\u001a\b\u0012\u0004\u0012\u0002H\u000502H\u0080\b\u00a2\u0006\u0002\u00103\u001a@\u00105\u001a\u0002H\u0005\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\u0006\u0010\u001e\u001a\u0002H\u00042\f\u00101\u001a\b\u0012\u0004\u0012\u0002H\u000502H\u0086\b\u00a2\u0006\u0002\u00103\u001a1\u00106\u001a\u0002H\u0005\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\u001e\u001a\u0002H\u0004H\u0007\u00a2\u0006\u0002\u0010/\u001a<\u00107\u001a\u0002H8\"\u0014\b\ufffd\ufffd\u0010)*\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003*\u0002H8\"\u0004\b\u0001\u00108*\u0002H)2\f\u00101\u001a\b\u0012\u0004\u0012\u0002H802H\u0087\b\u00a2\u0006\u0002\u00109\u001a'\u0010:\u001a\u00020\u001c\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0087\b\u001a:\u0010;\u001a\u00020\u001c\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005\u0018\u00010\u0003H\u0087\b\u0082\u0002\u000e\n\f\b\ufffd\ufffd\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\ufffd\ufffd\u001a9\u0010<\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00180=\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0087\n\u001a<\u0010<\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050?0>\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0016H\u0087\n\u00a2\u0006\u0002\b@\u001aY\u0010A\u001a\u000e\u0012\u0004\u0012\u0002H8\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0004\b\u0002\u00108*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u001e\u0010B\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u0002H80%H\u0086\b\u001at\u0010C\u001a\u0002H)\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0004\b\u0002\u00108\"\u0018\b\u0003\u0010)*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H8\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00050\u0016*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010*\u001a\u0002H)2\u001e\u0010B\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u0002H80%H\u0086\b\u00a2\u0006\u0002\u0010+\u001aY\u0010D\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H80\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0004\b\u0002\u00108*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u001e\u0010B\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u0002H80%H\u0086\b\u001at\u0010E\u001a\u0002H)\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0004\b\u0002\u00108\"\u0018\b\u0003\u0010)*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0004\u0012\u0006\b\ufffd\ufffd\u0012\u0002H80\u0016*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010*\u001a\u0002H)2\u001e\u0010B\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018\u0012\u0004\u0012\u0002H80%H\u0086\b\u00a2\u0006\u0002\u0010+\u001a@\u0010F\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010\u001e\u001a\u0002H\u0004H\u0087\u0002\u00a2\u0006\u0002\u0010G\u001aH\u0010F\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u000e\u0010H\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00040\nH\u0087\u0002\u00a2\u0006\u0002\u0010I\u001aA\u0010F\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\f\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u00040JH\u0087\u0002\u001aA\u0010F\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\f\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u00040KH\u0087\u0002\u001a2\u0010L\u001a\u00020M\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\u0006\u0010\u001e\u001a\u0002H\u0004H\u0087\n\u00a2\u0006\u0002\u0010N\u001a:\u0010L\u001a\u00020M\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\u000e\u0010H\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00040\nH\u0087\n\u00a2\u0006\u0002\u0010O\u001a3\u0010L\u001a\u00020M\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\f\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u00040JH\u0087\n\u001a3\u0010L\u001a\u00020M\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\f\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u00040KH\u0087\n\u001a0\u0010P\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\ufffd\ufffd\u001a3\u0010Q\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u0005\u0018\u00010\u0003H\u0087\b\u001aT\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u001a\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\nH\u0086\u0002\u00a2\u0006\u0002\u0010S\u001aG\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000bH\u0086\u0002\u001aM\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0JH\u0086\u0002\u001aI\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0014\u0010U\u001a\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0086\u0002\u001aM\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0KH\u0086\u0002\u001aJ\u0010V\u001a\u00020M\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0004\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00050\u00162\u001a\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\nH\u0087\n\u00a2\u0006\u0002\u0010W\u001a=\u0010V\u001a\u00020M\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0004\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00050\u00162\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000bH\u0087\n\u001aC\u0010V\u001a\u00020M\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0004\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00050\u00162\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0JH\u0087\n\u001a=\u0010V\u001a\u00020M\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0004\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00050\u00162\u0012\u0010U\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0087\n\u001aC\u0010V\u001a\u00020M\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0004\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00050\u00162\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0KH\u0087\n\u001aG\u0010X\u001a\u00020M\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0004\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00050\u00162\u001a\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n\u00a2\u0006\u0002\u0010W\u001a@\u0010X\u001a\u00020M\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0004\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00050\u00162\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0J\u001a@\u0010X\u001a\u00020M\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0004\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00050\u00162\u0018\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0K\u001a;\u0010Y\u001a\u0004\u0018\u0001H\u0005\"\t\b\ufffd\ufffd\u0010\u0004\u00a2\u0006\u0002\b\u001d\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\u0006\u0010\u001e\u001a\u0002H\u0004H\u0087\b\u00a2\u0006\u0002\u0010/\u001a:\u0010Z\u001a\u00020M\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00162\u0006\u0010\u001e\u001a\u0002H\u00042\u0006\u0010\"\u001a\u0002H\u0005H\u0087\n\u00a2\u0006\u0002\u0010[\u001a;\u0010\\\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n\u00a2\u0006\u0002\u0010\u0014\u001aQ\u0010\\\u001a\u0002H)\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0018\b\u0002\u0010)*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0004\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00050\u0016*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0\n2\u0006\u0010*\u001a\u0002H)\u00a2\u0006\u0002\u0010]\u001a4\u0010\\\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0J\u001aO\u0010\\\u001a\u0002H)\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0018\b\u0002\u0010)*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0004\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00050\u0016*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0J2\u0006\u0010*\u001a\u0002H)\u00a2\u0006\u0002\u0010^\u001a2\u0010\\\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0007\u001aM\u0010\\\u001a\u0002H)\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0018\b\u0002\u0010)*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0004\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00050\u0016*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00032\u0006\u0010*\u001a\u0002H)H\u0007\u00a2\u0006\u0002\u0010_\u001a4\u0010\\\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0K\u001aO\u0010\\\u001a\u0002H)\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005\"\u0018\b\u0002\u0010)*\u0012\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u0004\u0012\u0006\b\ufffd\ufffd\u0012\u0002H\u00050\u0016*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b0K2\u0006\u0010*\u001a\u0002H)\u00a2\u0006\u0002\u0010`\u001a2\u0010a\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0016\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0003H\u0007\u001a1\u0010b\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u000b\"\u0004\b\ufffd\ufffd\u0010\u0004\"\u0004\b\u0001\u0010\u0005*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u0018H\u0087\b\"\u000e\u0010\ufffd\ufffd\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006c"}, m27d2 = {"INT_MAX_POWER_OF_TWO", "", "emptyMap", "", "K", "V", "hashMapOf", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "pairs", "", "Lkotlin/Pair;", "([Lkotlin/Pair;)Ljava/util/HashMap;", "linkedMapOf", "Ljava/util/LinkedHashMap;", "Lkotlin/collections/LinkedHashMap;", "([Lkotlin/Pair;)Ljava/util/LinkedHashMap;", "mapCapacity", "expectedSize", "mapOf", "([Lkotlin/Pair;)Ljava/util/Map;", "mutableMapOf", "", "component1", "", "(Ljava/util/Map$Entry;)Ljava/lang/Object;", "component2", "contains", "", "Lkotlin/internal/OnlyInputTypes;", "key", "(Ljava/util/Map;Ljava/lang/Object;)Z", "containsKey", "containsValue", PropertyDescriptor.VALUE, "filter", "predicate", "Lkotlin/Function1;", "filterKeys", "filterNot", "filterNotTo", "M", "destination", "(Ljava/util/Map;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "filterTo", "filterValues", PropertyDescriptor.GET, "(Ljava/util/Map;Ljava/lang/Object;)Ljava/lang/Object;", "getOrElse", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "getOrElseNullable", "getOrPut", "getValue", "ifEmpty", "R", "(Ljava/util/Map;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotEmpty", "isNullOrEmpty", "iterator", "", "", "", "mutableIterator", "mapKeys", "transform", "mapKeysTo", "mapValues", "mapValuesTo", "minus", "(Ljava/util/Map;Ljava/lang/Object;)Ljava/util/Map;", "keys", "(Ljava/util/Map;[Ljava/lang/Object;)Ljava/util/Map;", "", "Lkotlin/sequences/Sequence;", "minusAssign", "", "(Ljava/util/Map;Ljava/lang/Object;)V", "(Ljava/util/Map;[Ljava/lang/Object;)V", "optimizeReadOnlyMap", "orEmpty", "plus", "(Ljava/util/Map;[Lkotlin/Pair;)Ljava/util/Map;", "pair", "map", "plusAssign", "(Ljava/util/Map;[Lkotlin/Pair;)V", "putAll", "remove", PropertyDescriptor.SET, "(Ljava/util/Map;Ljava/lang/Object;Ljava/lang/Object;)V", "toMap", "([Lkotlin/Pair;Ljava/util/Map;)Ljava/util/Map;", "(Ljava/lang/Iterable;Ljava/util/Map;)Ljava/util/Map;", "(Ljava/util/Map;Ljava/util/Map;)Ljava/util/Map;", "(Lkotlin/sequences/Sequence;Ljava/util/Map;)Ljava/util/Map;", "toMutableMap", "toPair", "kotlin-stdlib"}, m28xs = "kotlin/collections/MapsKt")
/* loaded from: L-out.jar:kotlin/collections/MapsKt__MapsKt.class */
class MapsKt__MapsKt extends MapsKt__MapsJVMKt {
    private static final int INT_MAX_POWER_OF_TWO = 1073741824;

    @NotNull
    public static final Map emptyMap() {
        EmptyMap emptyMap = EmptyMap.INSTANCE;
        if (emptyMap == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
        }
        return emptyMap;
    }

    @NotNull
    public static final Map mapOf(@NotNull Pair[] pairs) {
        Intrinsics.checkParameterIsNotNull(pairs, "pairs");
        return pairs.length > 0 ? MapsKt.toMap(pairs, new LinkedHashMap(MapsKt.mapCapacity(pairs.length))) : MapsKt.emptyMap();
    }

    @InlineOnly
    private static final Map mapOf() {
        return MapsKt.emptyMap();
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final Map mutableMapOf() {
        return new LinkedHashMap();
    }

    @NotNull
    public static final Map mutableMapOf(@NotNull Pair[] pairs) {
        Intrinsics.checkParameterIsNotNull(pairs, "pairs");
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(pairs.length));
        MapsKt.putAll(linkedHashMap, pairs);
        return linkedHashMap;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final HashMap hashMapOf() {
        return new HashMap();
    }

    @NotNull
    public static final HashMap hashMapOf(@NotNull Pair[] pairs) {
        Intrinsics.checkParameterIsNotNull(pairs, "pairs");
        HashMap map = new HashMap(MapsKt.mapCapacity(pairs.length));
        MapsKt.putAll(map, pairs);
        return map;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final LinkedHashMap linkedMapOf() {
        return new LinkedHashMap();
    }

    @NotNull
    public static final LinkedHashMap linkedMapOf(@NotNull Pair[] pairs) {
        Intrinsics.checkParameterIsNotNull(pairs, "pairs");
        return (LinkedHashMap) MapsKt.toMap(pairs, new LinkedHashMap(MapsKt.mapCapacity(pairs.length)));
    }

    @InlineOnly
    private static final boolean isNotEmpty(@NotNull Map map) {
        return !map.isEmpty();
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final boolean isNullOrEmpty(@Nullable Map map) {
        return map == null || map.isEmpty();
    }

    @InlineOnly
    private static final Map orEmpty(@Nullable Map map) {
        return map != null ? map : MapsKt.emptyMap();
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object ifEmpty(Map map, Function0 function0) {
        return map.isEmpty() ? function0.invoke() : map;
    }

    @InlineOnly
    private static final boolean contains(@NotNull Map contains, Object obj) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.containsKey(obj);
    }

    @InlineOnly
    private static final Object get(@NotNull Map get, Object obj) {
        Intrinsics.checkParameterIsNotNull(get, "$this$get");
        return get.get(obj);
    }

    @InlineOnly
    private static final void set(@NotNull Map set, Object obj, Object obj2) {
        Intrinsics.checkParameterIsNotNull(set, "$this$set");
        set.put(obj, obj2);
    }

    @InlineOnly
    private static final boolean containsKey(@NotNull Map map, Object obj) {
        if (map == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, *>");
        }
        return map.containsKey(obj);
    }

    @InlineOnly
    private static final boolean containsValue(@NotNull Map map, Object obj) {
        return map.containsValue(obj);
    }

    @InlineOnly
    private static final Object remove(@NotNull Map map, Object obj) {
        if (map == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap<K, V>");
        }
        return TypeIntrinsics.asMutableMap(map).remove(obj);
    }

    @InlineOnly
    private static final Object component1(@NotNull Map.Entry component1) {
        Intrinsics.checkParameterIsNotNull(component1, "$this$component1");
        return component1.getKey();
    }

    @InlineOnly
    private static final Object component2(@NotNull Map.Entry component2) {
        Intrinsics.checkParameterIsNotNull(component2, "$this$component2");
        return component2.getValue();
    }

    @InlineOnly
    private static final Pair toPair(@NotNull Map.Entry entry) {
        return new Pair(entry.getKey(), entry.getValue());
    }

    @InlineOnly
    private static final Object getOrElse(@NotNull Map map, Object obj, Function0 function0) {
        Object obj2 = map.get(obj);
        return obj2 != null ? obj2 : function0.invoke();
    }

    public static final Object getOrElseNullable(@NotNull Map getOrElseNullable, Object obj, @NotNull Function0 defaultValue) {
        Intrinsics.checkParameterIsNotNull(getOrElseNullable, "$this$getOrElseNullable");
        Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
        Object obj2 = getOrElseNullable.get(obj);
        if (obj2 == null && !getOrElseNullable.containsKey(obj)) {
            return defaultValue.invoke();
        }
        return obj2;
    }

    @SinceKotlin(version = "1.1")
    public static final Object getValue(@NotNull Map getValue, Object obj) {
        Intrinsics.checkParameterIsNotNull(getValue, "$this$getValue");
        return MapsKt.getOrImplicitDefaultNullable(getValue, obj);
    }

    public static final Object getOrPut(@NotNull Map getOrPut, Object obj, @NotNull Function0 defaultValue) {
        Intrinsics.checkParameterIsNotNull(getOrPut, "$this$getOrPut");
        Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
        Object obj2 = getOrPut.get(obj);
        if (obj2 == null) {
            Object objInvoke = defaultValue.invoke();
            getOrPut.put(obj, objInvoke);
            return objInvoke;
        }
        return obj2;
    }

    @InlineOnly
    private static final Iterator iterator(@NotNull Map iterator) {
        Intrinsics.checkParameterIsNotNull(iterator, "$this$iterator");
        return iterator.entrySet().iterator();
    }

    @JvmName(name = "mutableIterator")
    @InlineOnly
    private static final Iterator mutableIterator(@NotNull Map iterator) {
        Intrinsics.checkParameterIsNotNull(iterator, "$this$iterator");
        return iterator.entrySet().iterator();
    }

    @NotNull
    public static final Map mapValuesTo(@NotNull Map mapValuesTo, @NotNull Map destination, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(mapValuesTo, "$this$mapValuesTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        for (Object obj : mapValuesTo.entrySet()) {
            destination.put(((Map.Entry) obj).getKey(), transform.invoke(obj));
        }
        return destination;
    }

    @NotNull
    public static final Map mapKeysTo(@NotNull Map mapKeysTo, @NotNull Map destination, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(mapKeysTo, "$this$mapKeysTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        for (Object obj : mapKeysTo.entrySet()) {
            destination.put(transform.invoke(obj), ((Map.Entry) obj).getValue());
        }
        return destination;
    }

    public static final void putAll(@NotNull Map putAll, @NotNull Pair[] pairs) {
        Intrinsics.checkParameterIsNotNull(putAll, "$this$putAll");
        Intrinsics.checkParameterIsNotNull(pairs, "pairs");
        for (Pair pair : pairs) {
            putAll.put(pair.component1(), pair.component2());
        }
    }

    public static final void putAll(@NotNull Map putAll, @NotNull Iterable pairs) {
        Intrinsics.checkParameterIsNotNull(putAll, "$this$putAll");
        Intrinsics.checkParameterIsNotNull(pairs, "pairs");
        Iterator it = pairs.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            putAll.put(pair.component1(), pair.component2());
        }
    }

    public static final void putAll(@NotNull Map putAll, @NotNull Sequence pairs) {
        Intrinsics.checkParameterIsNotNull(putAll, "$this$putAll");
        Intrinsics.checkParameterIsNotNull(pairs, "pairs");
        Iterator it = pairs.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            putAll.put(pair.component1(), pair.component2());
        }
    }

    @NotNull
    public static final Map mapValues(@NotNull Map mapValues, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(mapValues, "$this$mapValues");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(mapValues.size()));
        for (Object obj : mapValues.entrySet()) {
            linkedHashMap.put(((Map.Entry) obj).getKey(), transform.invoke(obj));
        }
        return linkedHashMap;
    }

    @NotNull
    public static final Map mapKeys(@NotNull Map mapKeys, @NotNull Function1 transform) {
        Intrinsics.checkParameterIsNotNull(mapKeys, "$this$mapKeys");
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(mapKeys.size()));
        for (Object obj : mapKeys.entrySet()) {
            linkedHashMap.put(transform.invoke(obj), ((Map.Entry) obj).getValue());
        }
        return linkedHashMap;
    }

    @NotNull
    public static final Map filterKeys(@NotNull Map filterKeys, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(filterKeys, "$this$filterKeys");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : filterKeys.entrySet()) {
            if (((Boolean) predicate.invoke(entry.getKey())).booleanValue()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return linkedHashMap;
    }

    @NotNull
    public static final Map filterValues(@NotNull Map filterValues, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(filterValues, "$this$filterValues");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : filterValues.entrySet()) {
            if (((Boolean) predicate.invoke(entry.getValue())).booleanValue()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return linkedHashMap;
    }

    @NotNull
    public static final Map filterTo(@NotNull Map filterTo, @NotNull Map destination, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(filterTo, "$this$filterTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (Map.Entry entry : filterTo.entrySet()) {
            if (((Boolean) predicate.invoke(entry)).booleanValue()) {
                destination.put(entry.getKey(), entry.getValue());
            }
        }
        return destination;
    }

    @NotNull
    public static final Map filter(@NotNull Map filter, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(filter, "$this$filter");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : filter.entrySet()) {
            if (((Boolean) predicate.invoke(entry)).booleanValue()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return linkedHashMap;
    }

    @NotNull
    public static final Map filterNotTo(@NotNull Map filterNotTo, @NotNull Map destination, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(filterNotTo, "$this$filterNotTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        for (Map.Entry entry : filterNotTo.entrySet()) {
            if (!((Boolean) predicate.invoke(entry)).booleanValue()) {
                destination.put(entry.getKey(), entry.getValue());
            }
        }
        return destination;
    }

    @NotNull
    public static final Map filterNot(@NotNull Map filterNot, @NotNull Function1 predicate) {
        Intrinsics.checkParameterIsNotNull(filterNot, "$this$filterNot");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : filterNot.entrySet()) {
            if (!((Boolean) predicate.invoke(entry)).booleanValue()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return linkedHashMap;
    }

    @NotNull
    public static final Map toMap(@NotNull Iterable toMap) {
        Intrinsics.checkParameterIsNotNull(toMap, "$this$toMap");
        if (toMap instanceof Collection) {
            switch (((Collection) toMap).size()) {
                case 0:
                    return MapsKt.emptyMap();
                case 1:
                    return MapsKt.mapOf(toMap instanceof List ? (Pair) ((List) toMap).get(0) : (Pair) toMap.iterator().next());
                default:
                    return MapsKt.toMap(toMap, new LinkedHashMap(MapsKt.mapCapacity(((Collection) toMap).size())));
            }
        }
        return MapsKt.optimizeReadOnlyMap(MapsKt.toMap(toMap, new LinkedHashMap()));
    }

    @NotNull
    public static final Map toMap(@NotNull Iterable toMap, @NotNull Map destination) {
        Intrinsics.checkParameterIsNotNull(toMap, "$this$toMap");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        MapsKt.putAll(destination, toMap);
        return destination;
    }

    @NotNull
    public static final Map toMap(@NotNull Pair[] toMap) {
        Intrinsics.checkParameterIsNotNull(toMap, "$this$toMap");
        switch (toMap.length) {
            case 0:
                return MapsKt.emptyMap();
            case 1:
                return MapsKt.mapOf(toMap[0]);
            default:
                return MapsKt.toMap(toMap, new LinkedHashMap(MapsKt.mapCapacity(toMap.length)));
        }
    }

    @NotNull
    public static final Map toMap(@NotNull Pair[] toMap, @NotNull Map destination) {
        Intrinsics.checkParameterIsNotNull(toMap, "$this$toMap");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        MapsKt.putAll(destination, toMap);
        return destination;
    }

    @NotNull
    public static final Map toMap(@NotNull Sequence toMap) {
        Intrinsics.checkParameterIsNotNull(toMap, "$this$toMap");
        return MapsKt.optimizeReadOnlyMap(MapsKt.toMap(toMap, new LinkedHashMap()));
    }

    @NotNull
    public static final Map toMap(@NotNull Sequence toMap, @NotNull Map destination) {
        Intrinsics.checkParameterIsNotNull(toMap, "$this$toMap");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        MapsKt.putAll(destination, toMap);
        return destination;
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Map toMap(@NotNull Map toMap) {
        Intrinsics.checkParameterIsNotNull(toMap, "$this$toMap");
        switch (toMap.size()) {
            case 0:
                return MapsKt.emptyMap();
            case 1:
                return MapsKt.toSingletonMap(toMap);
            default:
                return MapsKt.toMutableMap(toMap);
        }
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Map toMutableMap(@NotNull Map toMutableMap) {
        Intrinsics.checkParameterIsNotNull(toMutableMap, "$this$toMutableMap");
        return new LinkedHashMap(toMutableMap);
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Map toMap(@NotNull Map toMap, @NotNull Map destination) {
        Intrinsics.checkParameterIsNotNull(toMap, "$this$toMap");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        destination.putAll(toMap);
        return destination;
    }

    @NotNull
    public static final Map plus(@NotNull Map plus, @NotNull Pair pair) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(pair, "pair");
        if (plus.isEmpty()) {
            return MapsKt.mapOf(pair);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(plus);
        linkedHashMap.put(pair.getFirst(), pair.getSecond());
        return linkedHashMap;
    }

    @NotNull
    public static final Map plus(@NotNull Map plus, @NotNull Iterable pairs) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(pairs, "pairs");
        if (plus.isEmpty()) {
            return MapsKt.toMap(pairs);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(plus);
        MapsKt.putAll(linkedHashMap, pairs);
        return linkedHashMap;
    }

    @NotNull
    public static final Map plus(@NotNull Map plus, @NotNull Pair[] pairs) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(pairs, "pairs");
        if (plus.isEmpty()) {
            return MapsKt.toMap(pairs);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(plus);
        MapsKt.putAll(linkedHashMap, pairs);
        return linkedHashMap;
    }

    @NotNull
    public static final Map plus(@NotNull Map plus, @NotNull Sequence pairs) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(pairs, "pairs");
        LinkedHashMap linkedHashMap = new LinkedHashMap(plus);
        MapsKt.putAll(linkedHashMap, pairs);
        return MapsKt.optimizeReadOnlyMap(linkedHashMap);
    }

    @NotNull
    public static final Map plus(@NotNull Map plus, @NotNull Map map) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(map, "map");
        LinkedHashMap linkedHashMap = new LinkedHashMap(plus);
        linkedHashMap.putAll(map);
        return linkedHashMap;
    }

    @InlineOnly
    private static final void plusAssign(@NotNull Map plusAssign, Pair pair) {
        Intrinsics.checkParameterIsNotNull(plusAssign, "$this$plusAssign");
        plusAssign.put(pair.getFirst(), pair.getSecond());
    }

    @InlineOnly
    private static final void plusAssign(@NotNull Map plusAssign, Iterable iterable) {
        Intrinsics.checkParameterIsNotNull(plusAssign, "$this$plusAssign");
        MapsKt.putAll(plusAssign, iterable);
    }

    @InlineOnly
    private static final void plusAssign(@NotNull Map plusAssign, Pair[] pairArr) {
        Intrinsics.checkParameterIsNotNull(plusAssign, "$this$plusAssign");
        MapsKt.putAll(plusAssign, pairArr);
    }

    @InlineOnly
    private static final void plusAssign(@NotNull Map plusAssign, Sequence sequence) {
        Intrinsics.checkParameterIsNotNull(plusAssign, "$this$plusAssign");
        MapsKt.putAll(plusAssign, sequence);
    }

    @InlineOnly
    private static final void plusAssign(@NotNull Map plusAssign, Map map) {
        Intrinsics.checkParameterIsNotNull(plusAssign, "$this$plusAssign");
        plusAssign.putAll(map);
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Map minus(@NotNull Map minus, Object obj) {
        Intrinsics.checkParameterIsNotNull(minus, "$this$minus");
        Map mutableMap = MapsKt.toMutableMap(minus);
        mutableMap.remove(obj);
        return MapsKt.optimizeReadOnlyMap(mutableMap);
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Map minus(@NotNull Map minus, @NotNull Iterable keys) {
        Intrinsics.checkParameterIsNotNull(minus, "$this$minus");
        Intrinsics.checkParameterIsNotNull(keys, "keys");
        Map mutableMap = MapsKt.toMutableMap(minus);
        CollectionsKt.removeAll(mutableMap.keySet(), keys);
        return MapsKt.optimizeReadOnlyMap(mutableMap);
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Map minus(@NotNull Map minus, @NotNull Object[] keys) {
        Intrinsics.checkParameterIsNotNull(minus, "$this$minus");
        Intrinsics.checkParameterIsNotNull(keys, "keys");
        Map mutableMap = MapsKt.toMutableMap(minus);
        CollectionsKt.removeAll(mutableMap.keySet(), keys);
        return MapsKt.optimizeReadOnlyMap(mutableMap);
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final Map minus(@NotNull Map minus, @NotNull Sequence keys) {
        Intrinsics.checkParameterIsNotNull(minus, "$this$minus");
        Intrinsics.checkParameterIsNotNull(keys, "keys");
        Map mutableMap = MapsKt.toMutableMap(minus);
        CollectionsKt.removeAll(mutableMap.keySet(), keys);
        return MapsKt.optimizeReadOnlyMap(mutableMap);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final void minusAssign(@NotNull Map minusAssign, Object obj) {
        Intrinsics.checkParameterIsNotNull(minusAssign, "$this$minusAssign");
        minusAssign.remove(obj);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final void minusAssign(@NotNull Map minusAssign, Iterable iterable) {
        Intrinsics.checkParameterIsNotNull(minusAssign, "$this$minusAssign");
        CollectionsKt.removeAll(minusAssign.keySet(), iterable);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final void minusAssign(@NotNull Map minusAssign, Object[] objArr) {
        Intrinsics.checkParameterIsNotNull(minusAssign, "$this$minusAssign");
        CollectionsKt.removeAll(minusAssign.keySet(), objArr);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final void minusAssign(@NotNull Map minusAssign, Sequence sequence) {
        Intrinsics.checkParameterIsNotNull(minusAssign, "$this$minusAssign");
        CollectionsKt.removeAll(minusAssign.keySet(), sequence);
    }

    @NotNull
    public static final Map optimizeReadOnlyMap(@NotNull Map optimizeReadOnlyMap) {
        Intrinsics.checkParameterIsNotNull(optimizeReadOnlyMap, "$this$optimizeReadOnlyMap");
        switch (optimizeReadOnlyMap.size()) {
            case 0:
                return MapsKt.emptyMap();
            case 1:
                return MapsKt.toSingletonMap(optimizeReadOnlyMap);
            default:
                return optimizeReadOnlyMap;
        }
    }
}
