package kotlin;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.1")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\ufffd\ufffd \u00172\b\u0012\u0004\u0012\u00020\ufffd\ufffd0\u0001:\u0001\u0017B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0007J\u0011\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\ufffd\ufffdH\u0096\u0002J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u000e\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0003H\u0016J\u0016\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003J\u001e\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0003J\b\u0010\u0014\u001a\u00020\u0015H\u0016J \u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0003H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\tR\u000e\u0010\f\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0018"}, m27d2 = {"Lkotlin/KotlinVersion;", "", "major", "", "minor", "(II)V", "patch", "(III)V", "getMajor", "()I", "getMinor", "getPatch", "version", "compareTo", "other", "equals", "", "", "hashCode", "isAtLeast", "toString", "", "versionOf", "Companion", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/KotlinVersion.class */
public final class KotlinVersion implements Comparable {
    private final int version;
    private final int major;
    private final int minor;
    private final int patch;
    public static final int MAX_COMPONENT_VALUE = 255;
    public static final Companion Companion = new Companion(null);

    @JvmField
    @NotNull
    public static final KotlinVersion CURRENT = new KotlinVersion(1, 3, 61);

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return compareTo((KotlinVersion) obj);
    }

    public final int getMajor() {
        return this.major;
    }

    public final int getMinor() {
        return this.minor;
    }

    public final int getPatch() {
        return this.patch;
    }

    public KotlinVersion(int i, int i2, int i3) {
        this.major = i;
        this.minor = i2;
        this.patch = i3;
        this.version = versionOf(this.major, this.minor, this.patch);
    }

    public KotlinVersion(int i, int i2) {
        this(i, i2, 0);
    }

    private final int versionOf(int i, int i2, int i3) {
        if (!(0 <= i && 255 >= i && 0 <= i2 && 255 >= i2 && 0 <= i3 && 255 >= i3)) {
            throw new IllegalArgumentException(("Version components are out of range: " + i + '.' + i2 + '.' + i3).toString());
        }
        return (i << 16) + (i2 << 8) + i3;
    }

    @NotNull
    public String toString() {
        return new StringBuilder().append(this.major).append('.').append(this.minor).append('.').append(this.patch).toString();
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        Object obj2 = obj;
        if (!(obj2 instanceof KotlinVersion)) {
            obj2 = null;
        }
        KotlinVersion kotlinVersion = (KotlinVersion) obj2;
        return kotlinVersion != null && this.version == kotlinVersion.version;
    }

    public int hashCode() {
        return this.version;
    }

    public int compareTo(@NotNull KotlinVersion other) {
        Intrinsics.checkParameterIsNotNull(other, "other");
        return this.version - other.version;
    }

    public final boolean isAtLeast(int i, int i2) {
        return this.major > i || (this.major == i && this.minor >= i2);
    }

    public final boolean isAtLeast(int i, int i2, int i3) {
        return this.major > i || (this.major == i && (this.minor > i2 || (this.minor == i2 && this.patch >= i3)));
    }

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0007"}, m27d2 = {"Lkotlin/KotlinVersion$Companion;", "", "()V", "CURRENT", "Lkotlin/KotlinVersion;", "MAX_COMPONENT_VALUE", "", "kotlin-stdlib"})
    /* loaded from: L-out.jar:kotlin/KotlinVersion$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
