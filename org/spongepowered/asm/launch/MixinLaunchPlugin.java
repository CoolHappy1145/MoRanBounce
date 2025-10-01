package org.spongepowered.asm.launch;

import com.google.common.io.Resources;
import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.launch.platform.CommandLineOptions;
import org.spongepowered.asm.service.IClassBytecodeProvider;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.service.modlauncher.MixinServiceModLauncher;
import org.spongepowered.asm.service.modlauncher.ModLauncherAuditTrail;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/MixinLaunchPlugin.class */
public class MixinLaunchPlugin implements ILaunchPluginService, IClassBytecodeProvider {
    public static final String NAME = "mixin";
    private final List processors = new ArrayList();
    private List commandLineMixins;
    private ILaunchPluginService.ITransformerLoader transformerLoader;
    private MixinServiceModLauncher service;
    private ModLauncherAuditTrail auditTrail;

    public EnumSet handlesClass(Type type, boolean z) {
        throw new IllegalStateException("Outdated ModLauncher");
    }

    public boolean processClass(ILaunchPluginService.Phase phase, ClassNode classNode, Type type) {
        throw new IllegalStateException("Outdated ModLauncher");
    }

    public EnumSet handlesClass(Type type, boolean z, String str) {
        if (NAME.equals(str)) {
            return Phases.NONE;
        }
        EnumSet enumSetNoneOf = EnumSet.noneOf(ILaunchPluginService.Phase.class);
        synchronized (this.processors) {
            Iterator it = this.processors.iterator();
            while (it.hasNext()) {
                EnumSet enumSetHandlesClass = ((IClassProcessor) it.next()).handlesClass(type, z, str);
                if (enumSetHandlesClass != null) {
                    enumSetNoneOf.addAll(enumSetHandlesClass);
                }
            }
        }
        return enumSetNoneOf;
    }

    public boolean processClass(ILaunchPluginService.Phase phase, ClassNode classNode, Type type, String str) {
        if (NAME.equals(str)) {
            return false;
        }
        boolean zProcessClass = false;
        synchronized (this.processors) {
            Iterator it = this.processors.iterator();
            while (it.hasNext()) {
                zProcessClass |= ((IClassProcessor) it.next()).processClass(phase, classNode, type, str);
            }
        }
        return zProcessClass;
    }

    void init(IEnvironment iEnvironment, List list) {
        IMixinService service = MixinService.getService();
        if (!(service instanceof MixinServiceModLauncher)) {
            throw new IllegalStateException("Unsupported service type for ModLauncher Mixin Service");
        }
        this.service = (MixinServiceModLauncher) service;
        this.auditTrail = (ModLauncherAuditTrail) this.service.getAuditTrail();
        synchronized (this.processors) {
            this.processors.addAll(this.service.getProcessors());
        }
        this.commandLineMixins = list;
        this.service.onInit(this);
    }

    public void customAuditConsumer(String str, Consumer consumer) {
        if (this.auditTrail != null) {
            this.auditTrail.setConsumer(str, consumer);
        }
    }

    @Deprecated
    public void addResource(Path path, String str) {
        this.service.getPrimaryContainer().addResource(str, path);
    }

    public void addResources(List list) {
        this.service.getPrimaryContainer().addResources(list);
    }

    public void initializeLaunch(ILaunchPluginService.ITransformerLoader iTransformerLoader, Path[] pathArr) {
        this.transformerLoader = iTransformerLoader;
        MixinBootstrap.doInit(CommandLineOptions.m50of(this.commandLineMixins));
        MixinBootstrap.inject();
        this.service.onStartup();
    }

    @Override // org.spongepowered.asm.service.IClassBytecodeProvider
    public ClassNode getClassNode(String str) {
        return getClassNode(str, true);
    }

    @Override // org.spongepowered.asm.service.IClassBytecodeProvider
    public ClassNode getClassNode(String str, boolean z) throws ClassNotFoundException, IOException {
        byte[] bArrBuildTransformedClassNodeFor;
        if (!z) {
            throw new IllegalArgumentException("ModLauncher service does not currently support retrieval of untransformed bytecode");
        }
        try {
            bArrBuildTransformedClassNodeFor = this.transformerLoader.buildTransformedClassNodeFor(str);
        } catch (ClassNotFoundException e) {
            URL resource = Thread.currentThread().getContextClassLoader().getResource(str.replace('.', '/') + ".class");
            if (resource == null) {
                throw e;
            }
            try {
                bArrBuildTransformedClassNodeFor = Resources.asByteSource(resource).read();
            } catch (IOException unused) {
                throw e;
            }
        }
        if (bArrBuildTransformedClassNodeFor == null) {
            throw new ClassNotFoundException(str.replace('/', '.'));
        }
        ClassNode classNode = new ClassNode();
        new ClassReader(bArrBuildTransformedClassNodeFor).accept(classNode, 8);
        return classNode;
    }
}
