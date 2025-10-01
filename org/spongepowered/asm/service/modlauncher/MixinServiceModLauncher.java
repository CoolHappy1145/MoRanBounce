package org.spongepowered.asm.service.modlauncher;

import com.google.common.collect.ImmutableList;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.ITransformationService;
import java.io.InputStream;
import java.util.Collection;
import org.spongepowered.asm.launch.IClassProcessor;
import org.spongepowered.asm.launch.platform.container.ContainerHandleModLauncher;
import org.spongepowered.asm.launch.platform.container.IContainerHandle;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.MixinTransformationHandler;
import org.spongepowered.asm.service.IClassBytecodeProvider;
import org.spongepowered.asm.service.IClassProvider;
import org.spongepowered.asm.service.IClassTracker;
import org.spongepowered.asm.service.IMixinAuditTrail;
import org.spongepowered.asm.service.MixinServiceAbstract;
import org.spongepowered.asm.util.IConsumer;

/* loaded from: L-out.jar:org/spongepowered/asm/service/modlauncher/MixinServiceModLauncher.class */
public class MixinServiceModLauncher extends MixinServiceAbstract {
    private static final String MODLAUNCHER_SPECIFICATION_VERSION = "4.0";
    private IClassProvider classProvider;
    private IClassBytecodeProvider bytecodeProvider;
    private MixinTransformationHandler transformationHandler;
    private ModLauncherClassTracker classTracker;
    private ModLauncherAuditTrail auditTrail;
    private IConsumer phaseConsumer;
    private boolean initialised;
    private ContainerHandleModLauncher rootContainer;

    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r1v0 ??, still in use, count: 2, list:
          (r1v0 ?? I:org.spongepowered.asm.service.modlauncher.MixinServiceModLauncher) from 0x000f: IPUT 
          (r1v0 ?? I:org.spongepowered.asm.launch.platform.container.ContainerHandleModLauncher)
          (r1v0 ?? I:org.spongepowered.asm.service.modlauncher.MixinServiceModLauncher)
         org.spongepowered.asm.service.modlauncher.MixinServiceModLauncher.rootContainer org.spongepowered.asm.launch.platform.container.ContainerHandleModLauncher
          (r1v0 ?? I:org.spongepowered.asm.launch.platform.container.ContainerHandleModLauncher) from 0x000f: IPUT 
          (r1v0 ?? I:org.spongepowered.asm.launch.platform.container.ContainerHandleModLauncher)
          (r1v0 ?? I:org.spongepowered.asm.service.modlauncher.MixinServiceModLauncher)
         org.spongepowered.asm.service.modlauncher.MixinServiceModLauncher.rootContainer org.spongepowered.asm.launch.platform.container.ContainerHandleModLauncher
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
        	at jadx.core.utils.InsnRemover.addAndUnbind(InsnRemover.java:57)
        	at jadx.core.dex.visitors.ModVisitor.removeStep(ModVisitor.java:463)
        	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:97)
        */
    /* JADX WARN: Type inference failed for: r1v0, types: [org.spongepowered.asm.launch.platform.container.ContainerHandleModLauncher, org.spongepowered.asm.service.modlauncher.MixinServiceModLauncher] */
    public MixinServiceModLauncher() {
        /*
            r6 = this;
            r0 = r6
            r0.<init>()
            r0 = r6
            org.spongepowered.asm.launch.platform.container.ContainerHandleModLauncher r1 = new org.spongepowered.asm.launch.platform.container.ContainerHandleModLauncher
            r2 = r1
            r3 = r6
            java.lang.String r4 = "ModLauncher"
            r3.<init>(r4)
            r1.rootContainer = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongepowered.asm.service.modlauncher.MixinServiceModLauncher.<init>():void");
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public IContainerHandle getPrimaryContainer() {
        return getPrimaryContainer();
    }

    public void onInit(IClassBytecodeProvider iClassBytecodeProvider) {
        if (this.initialised) {
            throw new IllegalStateException("Already initialised");
        }
        this.initialised = true;
        this.bytecodeProvider = iClassBytecodeProvider;
    }

    public void onStartup() {
        this.phaseConsumer.accept(MixinEnvironment.Phase.DEFAULT);
    }

    @Override // org.spongepowered.asm.service.MixinServiceAbstract
    public void wire(MixinEnvironment.Phase phase, IConsumer iConsumer) {
        super.wire(phase, iConsumer);
        this.phaseConsumer = iConsumer;
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public MixinEnvironment.CompatibilityLevel getMinCompatibilityLevel() {
        return MixinEnvironment.CompatibilityLevel.JAVA_8;
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public boolean isValid() {
        try {
            Launcher.INSTANCE.hashCode();
            if (!ITransformationService.class.getPackage().isCompatibleWith(MODLAUNCHER_SPECIFICATION_VERSION)) {
                return false;
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public IClassProvider getClassProvider() {
        if (this.classProvider == null) {
            this.classProvider = new ModLauncherClassProvider();
        }
        return this.classProvider;
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public IClassBytecodeProvider getBytecodeProvider() {
        if (this.bytecodeProvider == null) {
            throw new IllegalStateException("Service initialisation incomplete");
        }
        return this.bytecodeProvider;
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public IClassTracker getClassTracker() {
        if (this.classTracker == null) {
            this.classTracker = new ModLauncherClassTracker();
        }
        return this.classTracker;
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public IMixinAuditTrail getAuditTrail() {
        if (this.auditTrail == null) {
            this.auditTrail = new ModLauncherAuditTrail();
        }
        return this.auditTrail;
    }

    private IClassProcessor getTransformationHandler() {
        if (this.transformationHandler == null) {
            this.transformationHandler = new MixinTransformationHandler();
        }
        return this.transformationHandler;
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public Collection getPlatformAgents() {
        return ImmutableList.of("org.spongepowered.asm.launch.platform.MixinPlatformAgentMinecraftForge");
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public ContainerHandleModLauncher getPrimaryContainer() {
        return this.rootContainer;
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public InputStream getResourceAsStream(String str) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(str);
    }

    public Collection getProcessors() {
        return ImmutableList.of(getTransformationHandler(), (IClassProcessor) getClassTracker());
    }
}
