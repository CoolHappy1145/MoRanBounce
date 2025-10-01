package net.ccbluex.liquidbounce.features.module;

import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.Retention;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.MinecraftVersion;

@Retention(AnnotationRetention.RUNTIME)
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0087\u0002\u0018\ufffd\ufffd2\u00020\u0001BP\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003R\u000f\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\u0010R\u000f\u0010\t\u001a\u00020\n\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\u0010R\u000f\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0011R\u000f\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0012R\u000f\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0012R\u000f\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\u0013R\u000f\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0012R\u0015\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\u0014\u00a8\u0006\u0015"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/ModuleInfo;", "", "name", "", "description", "category", "Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "keyBind", "", "canEnable", "", "array", "supportedVersions", "", "Lnet/ccbluex/liquidbounce/api/MinecraftVersion;", "fakeName", "()Z", "()Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "()Ljava/lang/String;", "()I", "()[Lnet/ccbluex/liquidbounce/api/MinecraftVersion;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/ModuleInfo.class */
public @interface ModuleInfo {
    String name();

    String description();

    ModuleCategory category();

    int keyBind() default 0;

    boolean canEnable() default true;

    boolean array() default true;

    MinecraftVersion[] supportedVersions() default {MinecraftVersion.MC_1_8, MinecraftVersion.MC_1_12};

    String fakeName() default "";
}
