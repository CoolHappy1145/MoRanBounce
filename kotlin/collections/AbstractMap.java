package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.1")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdD\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\"\n\ufffd\ufffd\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010&\n\u0002\b\b\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\b'\u0018\ufffd\ufffd )*\u0004\b\ufffd\ufffd\u0010\u0001*\u0006\b\u0001\u0010\u0002 \u00012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003:\u0001)B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0004J\u001f\u0010\u0013\u001a\u00020\u00142\u0010\u0010\u0015\u001a\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010\u0016H\ufffd\ufffd\u00a2\u0006\u0002\b\u0017J\u0015\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00028\ufffd\ufffdH\u0016\u00a2\u0006\u0002\u0010\u001aJ\u0015\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00028\u0001H\u0016\u00a2\u0006\u0002\u0010\u001aJ\u0013\u0010\u001d\u001a\u00020\u00142\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0096\u0002J\u0018\u0010 \u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0019\u001a\u00028\ufffd\ufffdH\u0096\u0002\u00a2\u0006\u0002\u0010!J\b\u0010\"\u001a\u00020\rH\u0016J#\u0010#\u001a\u0010\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u00162\u0006\u0010\u0019\u001a\u00028\ufffd\ufffdH\u0002\u00a2\u0006\u0002\u0010$J\b\u0010%\u001a\u00020\u0014H\u0016J\b\u0010&\u001a\u00020'H\u0016J\u0012\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010\u001fH\u0002J\u001c\u0010&\u001a\u00020'2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u00010\u0016H\bR\u0016\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\ufffd\ufffd\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0016\u0010\u0007\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\bX\u0088\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006*"}, m27d2 = {"Lkotlin/collections/AbstractMap;", "K", "V", "", "()V", "_keys", "", "_values", "", "keys", "getKeys", "()Ljava/util/Set;", "size", "", "getSize", "()I", "values", "getValues", "()Ljava/util/Collection;", "containsEntry", "", "entry", "", "containsEntry$kotlin_stdlib", "containsKey", "key", "(Ljava/lang/Object;)Z", "containsValue", PropertyDescriptor.VALUE, "equals", "other", "", PropertyDescriptor.GET, "(Ljava/lang/Object;)Ljava/lang/Object;", "hashCode", "implFindEntry", "(Ljava/lang/Object;)Ljava/util/Map$Entry;", "isEmpty", "toString", "", "o", "Companion", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/collections/AbstractMap.class */
public abstract class AbstractMap implements Map, KMappedMarker {
    private Set _keys;
    private Collection _values;
    public static final Companion Companion = new Companion(null);

    public abstract Set getEntries();

    @Override // java.util.Map
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public Object put(Object obj, Object obj2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public void putAll(Map map) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public Object remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public final int size() {
        return getSize();
    }

    @Override // java.util.Map
    public final Set keySet() {
        return getKeys();
    }

    @Override // java.util.Map
    public final Collection values() {
        return getValues();
    }

    protected AbstractMap() {
    }

    @Override // java.util.Map
    public final Set entrySet() {
        return getEntries();
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return implFindEntry(obj) != null;
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        Set setEntrySet = entrySet();
        if ((setEntrySet instanceof Collection) && setEntrySet.isEmpty()) {
            return false;
        }
        Iterator it = setEntrySet.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(((Map.Entry) it.next()).getValue(), obj)) {
                return true;
            }
        }
        return false;
    }

    public final boolean containsEntry$kotlin_stdlib(@Nullable Map.Entry entry) {
        if (!(entry instanceof Map.Entry)) {
            return false;
        }
        Object key = entry.getKey();
        Object value = entry.getValue();
        if (this == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
        }
        Object obj = get(key);
        if (!Intrinsics.areEqual(value, obj)) {
            return false;
        }
        if (obj != null) {
            return true;
        }
        if (this == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, *>");
        }
        if (!containsKey(key)) {
            return false;
        }
        return true;
    }

    @Override // java.util.Map
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map) || size() != ((Map) obj).size()) {
            return false;
        }
        Set setEntrySet = ((Map) obj).entrySet();
        if ((setEntrySet instanceof Collection) && setEntrySet.isEmpty()) {
            return true;
        }
        Iterator it = setEntrySet.iterator();
        while (it.hasNext()) {
            if (!containsEntry$kotlin_stdlib((Map.Entry) it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Map
    @Nullable
    public Object get(Object obj) {
        Map.Entry entryImplFindEntry = implFindEntry(obj);
        if (entryImplFindEntry != null) {
            return entryImplFindEntry.getValue();
        }
        return null;
    }

    @Override // java.util.Map
    public int hashCode() {
        return entrySet().hashCode();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    public int getSize() {
        return entrySet().size();
    }

    @NotNull
    public Set getKeys() {
        if (this._keys == null) {
            this._keys = new AbstractSet(this) { // from class: kotlin.collections.AbstractMap$keys$1
                final AbstractMap this$0;

                {
                    this.this$0 = this;
                }

                @Override // kotlin.collections.AbstractCollection, java.util.Collection
                public boolean contains(Object obj) {
                    return this.this$0.containsKey(obj);
                }

                @Override // kotlin.collections.AbstractSet, kotlin.collections.AbstractCollection, java.util.Collection, java.lang.Iterable
                @NotNull
                public Iterator iterator() {
                    return new AbstractMap$keys$1$iterator$1(this.this$0.entrySet().iterator());
                }

                @Override // kotlin.collections.AbstractCollection
                public int getSize() {
                    return this.this$0.size();
                }
            };
        }
        Set set = this._keys;
        if (set == null) {
            Intrinsics.throwNpe();
        }
        return set;
    }

    @NotNull
    public String toString() {
        return CollectionsKt.joinToString$default(entrySet(), ", ", "{", "}", 0, null, new Function1(this) { // from class: kotlin.collections.AbstractMap.toString.1
            final AbstractMap this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return invoke((Map.Entry) obj);
            }

            @NotNull
            public final String invoke(@NotNull Map.Entry it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return this.this$0.toString(it);
            }
        }, 24, null);
    }

    private final String toString(Map.Entry entry) {
        return toString(entry.getKey()) + "=" + toString(entry.getValue());
    }

    private final String toString(Object obj) {
        return obj == this ? "(this Map)" : String.valueOf(obj);
    }

    @NotNull
    public Collection getValues() {
        if (this._values == null) {
            this._values = new AbstractCollection(this) { // from class: kotlin.collections.AbstractMap.values.1
                final AbstractMap this$0;

                {
                    this.this$0 = this;
                }

                @Override // kotlin.collections.AbstractCollection, java.util.Collection
                public boolean contains(Object obj) {
                    return this.this$0.containsValue(obj);
                }

                @Override // kotlin.collections.AbstractCollection, java.util.Collection, java.lang.Iterable
                @NotNull
                public Iterator iterator() {
                    return new AbstractMap$values$1$iterator$1(this.this$0.entrySet().iterator());
                }

                @Override // kotlin.collections.AbstractCollection
                public int getSize() {
                    return this.this$0.size();
                }
            };
        }
        Collection collection = this._values;
        if (collection == null) {
            Intrinsics.throwNpe();
        }
        return collection;
    }

    private final Map.Entry implFindEntry(Object obj) {
        Object obj2;
        Iterator it = entrySet().iterator();
        while (true) {
            if (it.hasNext()) {
                Object next = it.next();
                if (Intrinsics.areEqual(((Map.Entry) next).getKey(), obj)) {
                    obj2 = next;
                    break;
                }
            } else {
                obj2 = null;
                break;
            }
        }
        return (Map.Entry) obj2;
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd*\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010&\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0080\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J'\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001H\ufffd\ufffd\u00a2\u0006\u0002\b\bJ\u001d\u0010\t\u001a\u00020\n2\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006H\ufffd\ufffd\u00a2\u0006\u0002\b\u000bJ\u001d\u0010\f\u001a\u00020\r2\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006H\ufffd\ufffd\u00a2\u0006\u0002\b\u000e\u00a8\u0006\u000f"}, m27d2 = {"Lkotlin/collections/AbstractMap$Companion;", "", "()V", "entryEquals", "", "e", "", "other", "entryEquals$kotlin_stdlib", "entryHashCode", "", "entryHashCode$kotlin_stdlib", "entryToString", "", "entryToString$kotlin_stdlib", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/collections/AbstractMap$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final int entryHashCode$kotlin_stdlib(@NotNull Map.Entry e) {
            Intrinsics.checkParameterIsNotNull(e, "e");
            Object key = e.getKey();
            int iHashCode = key != null ? key.hashCode() : 0;
            Object value = e.getValue();
            return iHashCode ^ (value != null ? value.hashCode() : 0);
        }

        @NotNull
        public final String entryToString$kotlin_stdlib(@NotNull Map.Entry e) {
            Intrinsics.checkParameterIsNotNull(e, "e");
            return new StringBuilder().append(e.getKey()).append('=').append(e.getValue()).toString();
        }

        public final boolean entryEquals$kotlin_stdlib(@NotNull Map.Entry e, @Nullable Object obj) {
            Intrinsics.checkParameterIsNotNull(e, "e");
            return (obj instanceof Map.Entry) && Intrinsics.areEqual(e.getKey(), ((Map.Entry) obj).getKey()) && Intrinsics.areEqual(e.getValue(), ((Map.Entry) obj).getValue());
        }
    }
}
