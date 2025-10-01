package net.ccbluex.liquidbounce.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;

@Retention(RetentionPolicy.RUNTIME)
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u000e\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003R\u0015\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0005\u00a8\u0006\u0006"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/SupportsMinecraftVersions;", "", PropertyDescriptor.VALUE, "", "Lnet/ccbluex/liquidbounce/api/MinecraftVersion;", "()[Lnet/ccbluex/liquidbounce/api/MinecraftVersion;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/SupportsMinecraftVersions.class */
public @interface SupportsMinecraftVersions {
    MinecraftVersion[] value();
}
