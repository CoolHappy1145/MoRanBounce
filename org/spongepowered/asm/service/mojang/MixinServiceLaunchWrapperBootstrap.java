package org.spongepowered.asm.service.mojang;

import org.spongepowered.asm.service.IMixinServiceBootstrap;

/* loaded from: L-out.jar:org/spongepowered/asm/service/mojang/MixinServiceLaunchWrapperBootstrap.class */
public class MixinServiceLaunchWrapperBootstrap implements IMixinServiceBootstrap {
    private static final String SERVICE_PACKAGE = "org.spongepowered.asm.service.";
    private static final String MIXIN_UTIL_PACKAGE = "org.spongepowered.asm.util.";
    private static final String LEGACY_ASM_PACKAGE = "org.spongepowered.asm.lib.";
    private static final String ASM_PACKAGE = "org.objectweb.asm.";
    private static final String MIXIN_PACKAGE = "org.spongepowered.asm.mixin.";

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
            net.minecraft.launchwrapper.LaunchClassLoader r0 = net.minecraft.launchwrapper.Launch.classLoader     // Catch: java.lang.Throwable -> La
            int r0 = r0.hashCode()     // Catch: java.lang.Throwable -> La
            goto L28
        La:
            org.spongepowered.asm.service.ServiceInitialisationException r0 = new org.spongepowered.asm.service.ServiceInitialisationException
            r1 = r0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = r2
            r3.<init>()
            r3 = r6
            java.lang.String r4 = "LaunchWrapper"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = " is not available"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r1
        L28:
            net.minecraft.launchwrapper.LaunchClassLoader r0 = net.minecraft.launchwrapper.Launch.classLoader
            java.lang.String r1 = "org.spongepowered.asm.service."
            r0.addClassLoaderExclusion(r1)
            net.minecraft.launchwrapper.LaunchClassLoader r0 = net.minecraft.launchwrapper.Launch.classLoader
            java.lang.String r1 = "org.objectweb.asm."
            r0.addClassLoaderExclusion(r1)
            net.minecraft.launchwrapper.LaunchClassLoader r0 = net.minecraft.launchwrapper.Launch.classLoader
            java.lang.String r1 = "org.spongepowered.asm.lib."
            r0.addClassLoaderExclusion(r1)
            net.minecraft.launchwrapper.LaunchClassLoader r0 = net.minecraft.launchwrapper.Launch.classLoader
            java.lang.String r1 = "org.spongepowered.asm.mixin."
            r0.addClassLoaderExclusion(r1)
            net.minecraft.launchwrapper.LaunchClassLoader r0 = net.minecraft.launchwrapper.Launch.classLoader
            java.lang.String r1 = "org.spongepowered.asm.util."
            r0.addClassLoaderExclusion(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongepowered.asm.service.mojang.MixinServiceLaunchWrapperBootstrap.bootstrap():void");
    }
}
