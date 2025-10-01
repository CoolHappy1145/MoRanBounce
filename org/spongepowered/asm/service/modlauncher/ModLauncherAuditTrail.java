package org.spongepowered.asm.service.modlauncher;

import java.util.function.Consumer;
import org.spongepowered.asm.service.IMixinAuditTrail;

/* loaded from: L-out.jar:org/spongepowered/asm/service/modlauncher/ModLauncherAuditTrail.class */
public class ModLauncherAuditTrail implements IMixinAuditTrail {
    private static final String APPLY_MIXIN_ACTIVITY = "APP";
    private static final String POST_PROCESS_ACTIVITY = "DEC";
    private static final String GENERATE_ACTIVITY = "GEN";
    private String currentClass;
    private Consumer consumer;

    public void setConsumer(String str, Consumer consumer) {
        this.currentClass = str;
        this.consumer = consumer;
    }

    @Override // org.spongepowered.asm.service.IMixinAuditTrail
    public void onApply(String str, String str2) {
        writeActivity(str, new String[]{APPLY_MIXIN_ACTIVITY, str2});
    }

    @Override // org.spongepowered.asm.service.IMixinAuditTrail
    public void onPostProcess(String str) {
        writeActivity(str, new String[]{POST_PROCESS_ACTIVITY});
    }

    @Override // org.spongepowered.asm.service.IMixinAuditTrail
    public void onGenerate(String str, String str2) {
        writeActivity(str, new String[]{GENERATE_ACTIVITY});
    }

    private void writeActivity(String str, String[] strArr) {
        if (this.consumer != null && str.equals(this.currentClass)) {
            this.consumer.accept(strArr);
        }
    }
}
