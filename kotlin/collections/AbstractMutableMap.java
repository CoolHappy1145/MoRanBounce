package kotlin.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.markers.KMutableMap;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.1")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0006\b'\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0004B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0005J\u001f\u0010\u0006\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0007\u001a\u00028\ufffd\ufffd2\u0006\u0010\b\u001a\u00028\u0001H&\u00a2\u0006\u0002\u0010\t\u00a8\u0006\n"}, m27d2 = {"Lkotlin/collections/AbstractMutableMap;", "K", "V", "", "Ljava/util/AbstractMap;", "()V", "put", "key", PropertyDescriptor.VALUE, "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/collections/AbstractMutableMap.class */
public abstract class AbstractMutableMap extends java.util.AbstractMap implements Map, KMutableMap {
    @Override // java.util.AbstractMap, java.util.Map
    @Nullable
    public abstract Object put(Object obj, Object obj2);

    public abstract Set getEntries();

    protected AbstractMutableMap() {
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set entrySet() {
        return getEntries();
    }

    public Set getKeys() {
        return super.keySet();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set keySet() {
        return getKeys();
    }

    public int getSize() {
        return super.size();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        return getSize();
    }

    public Collection getValues() {
        return super.values();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Collection values() {
        return getValues();
    }
}
