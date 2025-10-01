package net.ccbluex.liquidbounce.p005ui.font;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd,\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\t\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\ufffd\ufffd\b\u0086\b\u0018\ufffd\ufffd2\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0007H\u00c6\u0003J'\u0010\u0016\u001a\u00020\ufffd\ufffd2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0017\u001a\u00020\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\b\u0010\u0019\u001a\u00020\u001aH\u0004J\t\u0010\u001b\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001e"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/font/CachedFont;", "", "displayList", "", "lastUsage", "", "deleted", "", "(IJZ)V", "getDeleted", "()Z", "setDeleted", "(Z)V", "getDisplayList", "()I", "getLastUsage", "()J", "setLastUsage", "(J)V", "component1", "component2", "component3", "copy", "equals", "other", "finalize", "", "hashCode", "toString", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/font/CachedFont.class */
public final class CachedFont {
    private final int displayList;
    private long lastUsage;
    private boolean deleted;

    public final int component1() {
        return this.displayList;
    }

    public final long component2() {
        return this.lastUsage;
    }

    public final boolean component3() {
        return this.deleted;
    }

    @NotNull
    public final CachedFont copy(int i, long j, boolean z) {
        return new CachedFont(i, j, z);
    }

    public static CachedFont copy$default(CachedFont cachedFont, int i, long j, boolean z, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = cachedFont.displayList;
        }
        if ((i2 & 2) != 0) {
            j = cachedFont.lastUsage;
        }
        if ((i2 & 4) != 0) {
            z = cachedFont.deleted;
        }
        return cachedFont.copy(i, j, z);
    }

    @NotNull
    public String toString() {
        return "CachedFont(displayList=" + this.displayList + ", lastUsage=" + this.lastUsage + ", deleted=" + this.deleted + ")";
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = ((Integer.hashCode(this.displayList) * 31) + Long.hashCode(this.lastUsage)) * 31;
        boolean z = this.deleted;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return iHashCode + i;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CachedFont)) {
            return false;
        }
        CachedFont cachedFont = (CachedFont) obj;
        return this.displayList == cachedFont.displayList && this.lastUsage == cachedFont.lastUsage && this.deleted == cachedFont.deleted;
    }

    public final int getDisplayList() {
        return this.displayList;
    }

    public final long getLastUsage() {
        return this.lastUsage;
    }

    public final void setLastUsage(long j) {
        this.lastUsage = j;
    }

    public final boolean getDeleted() {
        return this.deleted;
    }

    public final void setDeleted(boolean z) {
        this.deleted = z;
    }

    public CachedFont(int i, long j, boolean z) {
        this.displayList = i;
        this.lastUsage = j;
        this.deleted = z;
    }

    public CachedFont(int i, long j, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, j, (i2 & 4) != 0 ? false : z);
    }

    protected final void finalize() {
        if (!this.deleted) {
            GL11.glDeleteLists(this.displayList, 1);
        }
    }
}
