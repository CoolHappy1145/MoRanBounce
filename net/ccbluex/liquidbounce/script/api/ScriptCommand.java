package net.ccbluex.liquidbounce.script.api;

import java.util.Arrays;
import java.util.HashMap;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptUtils;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\b\u0005\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001b\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\fH\u0016\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0003R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR*\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00030\u0006j\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0003`\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0011"}, m27d2 = {"Lnet/ccbluex/liquidbounce/script/api/ScriptCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "commandObject", "Ljdk/nashorn/api/scripting/JSObject;", "(Ljdk/nashorn/api/scripting/JSObject;)V", "events", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "execute", "", "args", "", "([Ljava/lang/String;)V", "on", "eventName", InjectionInfo.DEFAULT_PREFIX, LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/script/api/ScriptCommand.class */
public final class ScriptCommand extends Command {
    private final HashMap events;
    private final JSObject commandObject;

    /* JADX WARN: Illegal instructions before constructor call */
    public ScriptCommand(@NotNull JSObject commandObject) {
        Intrinsics.checkParameterIsNotNull(commandObject, "commandObject");
        Object member = commandObject.getMember("name");
        if (member == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
        }
        String str = (String) member;
        Object objConvert = ScriptUtils.convert(commandObject.getMember("aliases"), String[].class);
        if (objConvert == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<out kotlin.String>");
        }
        String[] strArr = (String[]) objConvert;
        super(str, (String[]) Arrays.copyOf(strArr, strArr.length));
        this.commandObject = commandObject;
        this.events = new HashMap();
    }

    /* renamed from: on */
    public final void m46on(@NotNull String eventName, @NotNull JSObject handler) {
        Intrinsics.checkParameterIsNotNull(eventName, "eventName");
        Intrinsics.checkParameterIsNotNull(handler, "handler");
        this.events.put(eventName, handler);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        try {
            JSObject jSObject = (JSObject) this.events.get("execute");
            if (jSObject != null) {
                jSObject.call(this.commandObject, new Object[]{args});
            }
        } catch (Throwable th) {
            ClientUtils.getLogger().error("[ScriptAPI] Exception in command '" + getCommand() + "'!", th);
        }
    }
}
