package net.ccbluex.liquidbounce.api.minecraft.client.settings;

import java.util.Set;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.WEnumPlayerModelParts;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd<\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0004\bf\u0018\ufffd\ufffd2\u00020\u0001J\u0010\u0010+\u001a\u00020\u00032\u0006\u0010,\u001a\u00020\u0011H&J\u0018\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u0003H&J\u0018\u00102\u001a\u00020.2\u0006\u0010$\u001a\u00020&2\u0006\u00103\u001a\u00020\u0003H&R\u0018\u0010\u0002\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\tX\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005R\u0012\u0010\u0010\u001a\u00020\u0011X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0012\u0010\u0014\u001a\u00020\u0011X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0013R\u0012\u0010\u0016\u001a\u00020\u0011X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0013R\u0012\u0010\u0018\u001a\u00020\u0011X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u0013R\u0012\u0010\u001a\u001a\u00020\u0011X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u0013R\u0012\u0010\u001c\u001a\u00020\u0011X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u0013R\u0012\u0010\u001e\u001a\u00020\u0011X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010\u0013R\u0012\u0010 \u001a\u00020\u0011X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b!\u0010\u0013R\u0012\u0010\"\u001a\u00020\u0011X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b#\u0010\u0013R\u0018\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b'\u0010(R\u0012\u0010)\u001a\u00020\tX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b*\u0010\u000b\u00a8\u00064"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IGameSettings;", "", "entityShadows", "", "getEntityShadows", "()Z", "setEntityShadows", "(Z)V", "gammaSetting", "", "getGammaSetting", "()F", "setGammaSetting", "(F)V", "hideGUI", "getHideGUI", "keyBindAttack", "Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IKeyBinding;", "getKeyBindAttack", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IKeyBinding;", "keyBindBack", "getKeyBindBack", "keyBindForward", "getKeyBindForward", "keyBindJump", "getKeyBindJump", "keyBindLeft", "getKeyBindLeft", "keyBindRight", "getKeyBindRight", "keyBindSneak", "getKeyBindSneak", "keyBindSprint", "getKeyBindSprint", "keyBindUseItem", "getKeyBindUseItem", "modelParts", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/WEnumPlayerModelParts;", "getModelParts", "()Ljava/util/Set;", "mouseSensitivity", "getMouseSensitivity", "isKeyDown", "key", "setKeyBindState", "", "KeyCode", "", "Press", "setModelPartEnabled", "enabled", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/client/settings/IGameSettings.class */
public interface IGameSettings {
    boolean getEntityShadows();

    void setEntityShadows(boolean z);

    float getGammaSetting();

    void setGammaSetting(float f);

    @NotNull
    Set getModelParts();

    float getMouseSensitivity();

    boolean getHideGUI();

    @NotNull
    IKeyBinding getKeyBindAttack();

    @NotNull
    IKeyBinding getKeyBindUseItem();

    @NotNull
    IKeyBinding getKeyBindJump();

    @NotNull
    IKeyBinding getKeyBindSneak();

    @NotNull
    IKeyBinding getKeyBindForward();

    @NotNull
    IKeyBinding getKeyBindBack();

    @NotNull
    IKeyBinding getKeyBindRight();

    @NotNull
    IKeyBinding getKeyBindLeft();

    @NotNull
    IKeyBinding getKeyBindSprint();

    boolean isKeyDown(@NotNull IKeyBinding iKeyBinding);

    void setKeyBindState(int i, boolean z);

    void setModelPartEnabled(@NotNull WEnumPlayerModelParts wEnumPlayerModelParts, boolean z);
}
