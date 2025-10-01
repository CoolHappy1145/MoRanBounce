package net.ccbluex.liquidbounce.injection.backend;

import java.util.Set;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.WEnumPlayerModelParts;
import net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings;
import net.ccbluex.liquidbounce.api.minecraft.client.settings.IKeyBinding;
import net.ccbluex.liquidbounce.api.util.WrappedSet;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EnumPlayerModelParts;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdL\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0004\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u00101\u001a\u00020\u00062\b\u00102\u001a\u0004\u0018\u000103H\u0096\u0002J\u0010\u00104\u001a\u00020\u00062\u0006\u00105\u001a\u00020\u0015H\u0016J\u0018\u00106\u001a\u0002072\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\u0006H\u0016J\u0018\u0010;\u001a\u0002072\u0006\u0010(\u001a\u00020*2\u0006\u0010<\u001a\u00020\u0006H\u0016R$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR$\u0010\r\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\tR\u0014\u0010\u0014\u001a\u00020\u00158VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00158VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u0017R\u0014\u0010\u001a\u001a\u00020\u00158VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u0017R\u0014\u0010\u001c\u001a\u00020\u00158VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u0017R\u0014\u0010\u001e\u001a\u00020\u00158VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010\u0017R\u0014\u0010 \u001a\u00020\u00158VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b!\u0010\u0017R\u0014\u0010\"\u001a\u00020\u00158VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b#\u0010\u0017R\u0014\u0010$\u001a\u00020\u00158VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b%\u0010\u0017R\u0014\u0010&\u001a\u00020\u00158VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b'\u0010\u0017R\u001a\u0010(\u001a\b\u0012\u0004\u0012\u00020*0)8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b+\u0010,R\u0014\u0010-\u001a\u00020\f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b.\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b/\u00100\u00a8\u0006="}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/GameSettingsImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IGameSettings;", "wrapped", "Lnet/minecraft/client/settings/GameSettings;", "(Lnet/minecraft/client/settings/GameSettings;)V", PropertyDescriptor.VALUE, "", "entityShadows", "getEntityShadows", "()Z", "setEntityShadows", "(Z)V", "", "gammaSetting", "getGammaSetting", "()F", "setGammaSetting", "(F)V", "hideGUI", "getHideGUI", "keyBindAttack", "Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IKeyBinding;", "getKeyBindAttack", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IKeyBinding;", "keyBindBack", "getKeyBindBack", "keyBindForward", "getKeyBindForward", "keyBindJump", "getKeyBindJump", "keyBindLeft", "getKeyBindLeft", "keyBindRight", "getKeyBindRight", "keyBindSneak", "getKeyBindSneak", "keyBindSprint", "getKeyBindSprint", "keyBindUseItem", "getKeyBindUseItem", "modelParts", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/WEnumPlayerModelParts;", "getModelParts", "()Ljava/util/Set;", "mouseSensitivity", "getMouseSensitivity", "getWrapped", "()Lnet/minecraft/client/settings/GameSettings;", "equals", "other", "", "isKeyDown", "key", "setKeyBindState", "", "KeyCode", "", "Press", "setModelPartEnabled", "enabled", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/GameSettingsImpl.class */
public final class GameSettingsImpl implements IGameSettings {

    @NotNull
    private final GameSettings wrapped;

    @NotNull
    public final GameSettings getWrapped() {
        return this.wrapped;
    }

    public GameSettingsImpl(@NotNull GameSettings wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    public boolean getEntityShadows() {
        return this.wrapped.field_181151_V;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    public void setEntityShadows(boolean z) {
        this.wrapped.field_181151_V = z;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    public float getGammaSetting() {
        return this.wrapped.field_74333_Y;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    public void setGammaSetting(float f) {
        this.wrapped.field_74333_Y = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    @NotNull
    public Set getModelParts() {
        Set setFunc_178876_d = this.wrapped.func_178876_d();
        Intrinsics.checkExpressionValueIsNotNull(setFunc_178876_d, "wrapped.modelParts");
        return new WrappedSet(setFunc_178876_d, GameSettingsImpl$modelParts$1.INSTANCE, GameSettingsImpl$modelParts$2.INSTANCE);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    public float getMouseSensitivity() {
        return this.wrapped.field_74341_c;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    public boolean getHideGUI() {
        return this.wrapped.field_74319_N;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    @NotNull
    public IKeyBinding getKeyBindAttack() {
        KeyBinding keyBinding = this.wrapped.field_74312_F;
        Intrinsics.checkExpressionValueIsNotNull(keyBinding, "wrapped.keyBindAttack");
        return new KeyBindingImpl(keyBinding);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    @NotNull
    public IKeyBinding getKeyBindUseItem() {
        KeyBinding keyBinding = this.wrapped.field_74313_G;
        Intrinsics.checkExpressionValueIsNotNull(keyBinding, "wrapped.keyBindUseItem");
        return new KeyBindingImpl(keyBinding);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    @NotNull
    public IKeyBinding getKeyBindJump() {
        KeyBinding keyBinding = this.wrapped.field_74314_A;
        Intrinsics.checkExpressionValueIsNotNull(keyBinding, "wrapped.keyBindJump");
        return new KeyBindingImpl(keyBinding);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    @NotNull
    public IKeyBinding getKeyBindSneak() {
        KeyBinding keyBinding = this.wrapped.field_74311_E;
        Intrinsics.checkExpressionValueIsNotNull(keyBinding, "wrapped.keyBindSneak");
        return new KeyBindingImpl(keyBinding);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    @NotNull
    public IKeyBinding getKeyBindForward() {
        KeyBinding keyBinding = this.wrapped.field_74351_w;
        Intrinsics.checkExpressionValueIsNotNull(keyBinding, "wrapped.keyBindForward");
        return new KeyBindingImpl(keyBinding);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    @NotNull
    public IKeyBinding getKeyBindBack() {
        KeyBinding keyBinding = this.wrapped.field_74368_y;
        Intrinsics.checkExpressionValueIsNotNull(keyBinding, "wrapped.keyBindBack");
        return new KeyBindingImpl(keyBinding);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    @NotNull
    public IKeyBinding getKeyBindRight() {
        KeyBinding keyBinding = this.wrapped.field_74366_z;
        Intrinsics.checkExpressionValueIsNotNull(keyBinding, "wrapped.keyBindRight");
        return new KeyBindingImpl(keyBinding);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    @NotNull
    public IKeyBinding getKeyBindLeft() {
        KeyBinding keyBinding = this.wrapped.field_74370_x;
        Intrinsics.checkExpressionValueIsNotNull(keyBinding, "wrapped.keyBindLeft");
        return new KeyBindingImpl(keyBinding);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    @NotNull
    public IKeyBinding getKeyBindSprint() {
        KeyBinding keyBinding = this.wrapped.field_151444_V;
        Intrinsics.checkExpressionValueIsNotNull(keyBinding, "wrapped.keyBindSprint");
        return new KeyBindingImpl(keyBinding);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    public boolean isKeyDown(@NotNull IKeyBinding key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        return GameSettings.func_100015_a(((KeyBindingImpl) key).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    public void setKeyBindState(int i, boolean z) {
        KeyBinding.func_74510_a(i, z);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings
    public void setModelPartEnabled(@NotNull WEnumPlayerModelParts modelParts, boolean z) {
        EnumPlayerModelParts enumPlayerModelParts;
        Intrinsics.checkParameterIsNotNull(modelParts, "modelParts");
        GameSettings gameSettings = this.wrapped;
        switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$1[modelParts.ordinal()]) {
            case 1:
                enumPlayerModelParts = EnumPlayerModelParts.CAPE;
                break;
            case 2:
                enumPlayerModelParts = EnumPlayerModelParts.JACKET;
                break;
            case 3:
                enumPlayerModelParts = EnumPlayerModelParts.LEFT_SLEEVE;
                break;
            case 4:
                enumPlayerModelParts = EnumPlayerModelParts.RIGHT_SLEEVE;
                break;
            case 5:
                enumPlayerModelParts = EnumPlayerModelParts.LEFT_PANTS_LEG;
                break;
            case 6:
                enumPlayerModelParts = EnumPlayerModelParts.RIGHT_PANTS_LEG;
                break;
            case 7:
                enumPlayerModelParts = EnumPlayerModelParts.HAT;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        gameSettings.func_178878_a(enumPlayerModelParts, z);
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof GameSettingsImpl) && Intrinsics.areEqual(((GameSettingsImpl) obj).wrapped, this.wrapped);
    }
}
