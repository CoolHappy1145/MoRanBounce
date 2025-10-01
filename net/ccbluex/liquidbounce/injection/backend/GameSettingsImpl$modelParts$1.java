package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.WEnumPlayerModelParts;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt;
import net.minecraft.entity.player.EnumPlayerModelParts;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\b\u0004"}, m27d2 = {"<anonymous>", "Lnet/minecraft/entity/player/EnumPlayerModelParts;", "p1", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/WEnumPlayerModelParts;", "invoke"})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/GameSettingsImpl$modelParts$1.class */
final /* synthetic */ class GameSettingsImpl$modelParts$1 extends FunctionReference implements Function1 {
    public static final GameSettingsImpl$modelParts$1 INSTANCE = new GameSettingsImpl$modelParts$1();

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(BackendExtentionsKt.class, LiquidBounce.CLIENT_NAME);
    }

    GameSettingsImpl$modelParts$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public Object invoke(Object obj) {
        return invoke((WEnumPlayerModelParts) obj);
    }

    @NotNull
    public final EnumPlayerModelParts invoke(@NotNull WEnumPlayerModelParts p1) {
        Intrinsics.checkParameterIsNotNull(p1, "p1");
        switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$1[p1.ordinal()]) {
            case 1:
                return EnumPlayerModelParts.CAPE;
            case 2:
                return EnumPlayerModelParts.JACKET;
            case 3:
                return EnumPlayerModelParts.LEFT_SLEEVE;
            case 4:
                return EnumPlayerModelParts.RIGHT_SLEEVE;
            case 5:
                return EnumPlayerModelParts.LEFT_PANTS_LEG;
            case 6:
                return EnumPlayerModelParts.RIGHT_PANTS_LEG;
            case 7:
                return EnumPlayerModelParts.HAT;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
}
