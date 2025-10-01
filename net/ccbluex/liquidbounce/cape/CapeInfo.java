package net.ccbluex.liquidbounce.cape;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd$\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\b\u0086\b\u0018\ufffd\ufffd2\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\u000e\u001a\u00020\ufffd\ufffd2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00052\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0004\u0010\u0007\"\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0015"}, m27d2 = {"Lnet/ccbluex/liquidbounce/cape/CapeInfo;", "", "resourceLocation", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "isCapeAvailable", "", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;Z)V", "()Z", "setCapeAvailable", "(Z)V", "getResourceLocation", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/cape/CapeInfo.class */
public final class CapeInfo {

    @NotNull
    private final IResourceLocation resourceLocation;
    private boolean isCapeAvailable;

    @NotNull
    public final IResourceLocation component1() {
        return this.resourceLocation;
    }

    public final boolean component2() {
        return this.isCapeAvailable;
    }

    @NotNull
    public final CapeInfo copy(@NotNull IResourceLocation resourceLocation, boolean z) {
        Intrinsics.checkParameterIsNotNull(resourceLocation, "resourceLocation");
        return new CapeInfo(resourceLocation, z);
    }

    public static CapeInfo copy$default(CapeInfo capeInfo, IResourceLocation iResourceLocation, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            iResourceLocation = capeInfo.resourceLocation;
        }
        if ((i & 2) != 0) {
            z = capeInfo.isCapeAvailable;
        }
        return capeInfo.copy(iResourceLocation, z);
    }

    @NotNull
    public String toString() {
        return "CapeInfo(resourceLocation=" + this.resourceLocation + ", isCapeAvailable=" + this.isCapeAvailable + ")";
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        IResourceLocation iResourceLocation = this.resourceLocation;
        int iHashCode = (iResourceLocation != null ? iResourceLocation.hashCode() : 0) * 31;
        boolean z = this.isCapeAvailable;
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
        if (!(obj instanceof CapeInfo)) {
            return false;
        }
        CapeInfo capeInfo = (CapeInfo) obj;
        return Intrinsics.areEqual(this.resourceLocation, capeInfo.resourceLocation) && this.isCapeAvailable == capeInfo.isCapeAvailable;
    }

    @NotNull
    public final IResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }

    public final boolean isCapeAvailable() {
        return this.isCapeAvailable;
    }

    public final void setCapeAvailable(boolean z) {
        this.isCapeAvailable = z;
    }

    public CapeInfo(@NotNull IResourceLocation resourceLocation, boolean z) {
        Intrinsics.checkParameterIsNotNull(resourceLocation, "resourceLocation");
        this.resourceLocation = resourceLocation;
        this.isCapeAvailable = z;
    }

    public CapeInfo(IResourceLocation iResourceLocation, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(iResourceLocation, (i & 2) != 0 ? false : z);
    }
}
