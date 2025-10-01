package org.spongepowered.asm.service.modlauncher;

import org.spongepowered.asm.service.IMixinServiceBootstrap;

/* loaded from: L-out.jar:org/spongepowered/asm/service/modlauncher/MixinServiceModLauncherBootstrap.class */
public class MixinServiceModLauncherBootstrap implements IMixinServiceBootstrap {
    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Null bind block in PHI insn: 0x000a: PHI (r2v0 ?? I:java.lang.StringBuilder) =  binds: [] A[DONT_GENERATE, DONT_INLINE]
        	at jadx.core.dex.instructions.PhiInsn.bindArg(PhiInsn.java:47)
        	at jadx.core.dex.visitors.ConstructorVisitor.insertPhiInsn(ConstructorVisitor.java:156)
        	at jadx.core.dex.visitors.ConstructorVisitor.processInvoke(ConstructorVisitor.java:91)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:56)
        	at jadx.core.dex.visitors.ConstructorVisitor.visit(ConstructorVisitor.java:42)
        */
    @Override // org.spongepowered.asm.service.IMixinServiceBootstrap
    public void bootstrap() {
        /*
            r6 = this;
            cpw.mods.modlauncher.Launcher r0 = cpw.mods.modlauncher.Launcher.INSTANCE     // Catch: java.lang.Throwable -> La
            int r0 = r0.hashCode()     // Catch: java.lang.Throwable -> La
            goto L28
        La:
            org.spongepowered.asm.service.ServiceInitialisationException r0 = new org.spongepowered.asm.service.ServiceInitialisationException
            r1 = r0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = r2
            r3.<init>()
            r3 = r6
            java.lang.String r4 = "ModLauncher"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = " is not available"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r1
        L28:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongepowered.asm.service.modlauncher.MixinServiceModLauncherBootstrap.bootstrap():void");
    }
}
