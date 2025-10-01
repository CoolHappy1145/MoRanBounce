package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.Random;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.TextValue;

/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
@ModuleInfo(name = "Spammer", description = "Spams the chat with a given message.", category = ModuleCategory.MISC)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/misc/Spammer.class */
public class Spammer extends Module {
    private final IntegerValue maxDelayValue = new IntegerValue(this, "MaxDelay", 1000, 0, 5000) { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.Spammer.1
        final Spammer this$0;

        {
            this.this$0 = this;
        }

        protected void onChanged(Object obj, Object obj2) {
            onChanged((Integer) obj, (Integer) obj2);
        }

        /* JADX WARN: Failed to check method for inline after forced processnet.ccbluex.liquidbounce.features.module.modules.misc.Spammer.access$102(net.ccbluex.liquidbounce.features.module.modules.misc.Spammer, long):long */
        protected void onChanged(Integer num, Integer num2) {
            int iIntValue = ((Integer) this.this$0.minDelayValue.get()).intValue();
            if (iIntValue > num2.intValue()) {
                set((Object) Integer.valueOf(iIntValue));
            }
            Spammer.access$102(this.this$0, TimeUtils.randomDelay(((Integer) this.this$0.minDelayValue.get()).intValue(), ((Integer) this.this$0.maxDelayValue.get()).intValue()));
        }
    };
    private final IntegerValue minDelayValue = new IntegerValue(this, "MinDelay", SharedScopeCall.SLOW_SCOPE_CALL_THRESHOLD, 0, 5000) { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.Spammer.2
        final Spammer this$0;

        {
            this.this$0 = this;
        }

        protected void onChanged(Object obj, Object obj2) {
            onChanged((Integer) obj, (Integer) obj2);
        }

        /* JADX WARN: Failed to check method for inline after forced processnet.ccbluex.liquidbounce.features.module.modules.misc.Spammer.access$102(net.ccbluex.liquidbounce.features.module.modules.misc.Spammer, long):long */
        protected void onChanged(Integer num, Integer num2) {
            int iIntValue = ((Integer) this.this$0.maxDelayValue.get()).intValue();
            if (iIntValue < num2.intValue()) {
                set((Object) Integer.valueOf(iIntValue));
            }
            Spammer.access$102(this.this$0, TimeUtils.randomDelay(((Integer) this.this$0.minDelayValue.get()).intValue(), ((Integer) this.this$0.maxDelayValue.get()).intValue()));
        }
    };
    private final TextValue messageValue = new TextValue("Message", "LiquidSense Client | liquidbounce(.net) | CCBlueX on yt");
    private final BoolValue customValue = new BoolValue("Custom", false);
    private final MSTimer msTimer = new MSTimer();
    private long delay = TimeUtils.randomDelay(((Integer) this.minDelayValue.get()).intValue(), ((Integer) this.maxDelayValue.get()).intValue());

    /*  JADX ERROR: Failed to decode insn: 0x0002: MOVE_MULTI
        java.lang.ArrayIndexOutOfBoundsException: arraycopy: source index -1 out of bounds for object array[6]
        	at java.base/java.lang.System.arraycopy(Native Method)
        	at jadx.plugins.input.java.data.code.StackState.insert(StackState.java:52)
        	at jadx.plugins.input.java.data.code.CodeDecodeState.insert(CodeDecodeState.java:137)
        	at jadx.plugins.input.java.data.code.JavaInsnsRegister.dup2x1(JavaInsnsRegister.java:313)
        	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
        	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
        	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
        	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
        	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
        	at jadx.core.ProcessClass.process(ProcessClass.java:69)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
        	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
        	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
        */
    static long access$102(net.ccbluex.liquidbounce.features.module.modules.misc.Spammer r6, long r7) {
        /*
            r0 = r6
            r1 = r7
            // decode failed: arraycopy: source index -1 out of bounds for object array[6]
            r0.delay = r1
            return r-1
        */
        throw new UnsupportedOperationException("Method not decompiled: net.ccbluex.liquidbounce.features.module.modules.misc.Spammer.access$102(net.ccbluex.liquidbounce.features.module.modules.misc.Spammer, long):long");
    }

    @EventTarget
    public void onUpdate(UpdateEvent updateEvent) {
        if (this.msTimer.hasTimePassed(this.delay)) {
            f157mc.getThePlayer().sendChatMessage(((Boolean) this.customValue.get()).booleanValue() ? replace((String) this.messageValue.get()) : ((String) this.messageValue.get()) + " >" + RandomUtils.randomString(5 + new Random().nextInt(5)) + "<");
            this.msTimer.reset();
            this.delay = TimeUtils.randomDelay(((Integer) this.minDelayValue.get()).intValue(), ((Integer) this.maxDelayValue.get()).intValue());
        }
    }

    private String replace(String str) {
        Random random = new Random();
        while (str.contains("%f")) {
            str = str.substring(0, str.indexOf("%f")) + random.nextFloat() + str.substring(str.indexOf("%f") + 2);
        }
        while (str.contains("%i")) {
            str = str.substring(0, str.indexOf("%i")) + random.nextInt(10000) + str.substring(str.indexOf("%i") + 2);
        }
        while (str.contains("%s")) {
            str = str.substring(0, str.indexOf("%s")) + RandomUtils.randomString(random.nextInt(8) + 1) + str.substring(str.indexOf("%s") + 2);
        }
        while (str.contains("%ss")) {
            str = str.substring(0, str.indexOf("%ss")) + RandomUtils.randomString(random.nextInt(4) + 1) + str.substring(str.indexOf("%ss") + 3);
        }
        while (str.contains("%ls")) {
            str = str.substring(0, str.indexOf("%ls")) + RandomUtils.randomString(random.nextInt(15) + 1) + str.substring(str.indexOf("%ls") + 3);
        }
        return str;
    }
}
