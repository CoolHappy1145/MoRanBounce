package org.spongepowered.asm.service.mojang;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.minecraft.launchwrapper.IClassNameTransformer;
import net.minecraft.launchwrapper.Launch;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.launch.platform.MainAttributes;
import org.spongepowered.asm.launch.platform.container.ContainerHandleURI;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.service.IClassBytecodeProvider;
import org.spongepowered.asm.service.IClassProvider;
import org.spongepowered.asm.service.IClassTracker;
import org.spongepowered.asm.service.ILegacyClassTransformer;
import org.spongepowered.asm.service.ITransformer;
import org.spongepowered.asm.service.ITransformerProvider;
import org.spongepowered.asm.service.MixinServiceAbstract;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.perf.Profiler;

/* loaded from: L-out.jar:org/spongepowered/asm/service/mojang/MixinServiceLaunchWrapper.class */
public class MixinServiceLaunchWrapper extends MixinServiceAbstract implements IClassProvider, IClassBytecodeProvider, ITransformerProvider {
    private static final String MIXIN_TWEAKER_CLASS = "org.spongepowered.asm.launch.MixinTweaker";
    private static final String STATE_TWEAKER = "org.spongepowered.asm.mixin.EnvironmentStateTweaker";
    private static final String TRANSFORMER_PROXY_CLASS = "org.spongepowered.asm.mixin.transformer.Proxy";
    private final LaunchClassLoaderUtil classLoaderUtil = new LaunchClassLoaderUtil(Launch.classLoader);
    private List delegatedTransformers;
    private IClassNameTransformer nameTransformer;
    public static final GlobalProperties.Keys BLACKBOARD_KEY_TWEAKCLASSES = GlobalProperties.Keys.m49of("TweakClasses");
    public static final GlobalProperties.Keys BLACKBOARD_KEY_TWEAKS = GlobalProperties.Keys.m49of("Tweaks");
    private static final Set excludeTransformers = Sets.newHashSet(new String[]{"net.minecraftforge.fml.common.asm.transformers.EventSubscriptionTransformer", "cpw.mods.fml.common.asm.transformers.EventSubscriptionTransformer", "net.minecraftforge.fml.common.asm.transformers.TerminalTransformer", "cpw.mods.fml.common.asm.transformers.TerminalTransformer"});

    @Override // org.spongepowered.asm.service.ITransformerProvider
    public Collection getDelegatedTransformers() {
        return getDelegatedTransformers();
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public boolean isValid() {
        try {
            Launch.classLoader.hashCode();
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public void prepare() {
        Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.launch.");
    }

    @Override // org.spongepowered.asm.service.MixinServiceAbstract, org.spongepowered.asm.service.IMixinService
    public MixinEnvironment.Phase getInitialPhase() {
        String property = System.getProperty("sun.java.command");
        if (property != null && property.contains("GradleStart")) {
            System.setProperty("mixin.env.remapRefMap", "true");
        }
        if (findInStackTrace("net.minecraft.launchwrapper.Launch", "launch") > 132) {
            return MixinEnvironment.Phase.DEFAULT;
        }
        return MixinEnvironment.Phase.PREINIT;
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public MixinEnvironment.CompatibilityLevel getMaxCompatibilityLevel() {
        return MixinEnvironment.CompatibilityLevel.JAVA_8;
    }

    @Override // org.spongepowered.asm.service.MixinServiceAbstract, org.spongepowered.asm.service.IMixinService
    public void init() {
        if (findInStackTrace("net.minecraft.launchwrapper.Launch", "launch") < 4) {
            MixinServiceAbstract.logger.error("MixinBootstrap.doInit() called during a tweak constructor!");
        }
        List list = (List) GlobalProperties.get(BLACKBOARD_KEY_TWEAKCLASSES);
        if (list != null) {
            list.add(STATE_TWEAKER);
        }
        super.init();
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public Collection getPlatformAgents() {
        return ImmutableList.of("org.spongepowered.asm.launch.platform.MixinPlatformAgentFMLLegacy", "org.spongepowered.asm.launch.platform.MixinPlatformAgentLiteLoaderLegacy");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v1 org.spongepowered.asm.launch.platform.container.ContainerHandleVirtual, still in use, count: 1, list:
          (r0v1 org.spongepowered.asm.launch.platform.container.ContainerHandleVirtual) from 0x0030: RETURN (r0v1 org.spongepowered.asm.launch.platform.container.ContainerHandleVirtual)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
        	at jadx.core.utils.InsnRemover.addAndUnbind(InsnRemover.java:57)
        	at jadx.core.dex.visitors.ModVisitor.removeStep(ModVisitor.java:463)
        	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:97)
        */
    @Override // org.spongepowered.asm.service.IMixinService
    public org.spongepowered.asm.launch.platform.container.IContainerHandle getPrimaryContainer() {
        /*
            r5 = this;
            r0 = r5
            java.lang.Class r0 = r0.getClass()     // Catch: java.net.URISyntaxException -> L21
            java.security.ProtectionDomain r0 = r0.getProtectionDomain()     // Catch: java.net.URISyntaxException -> L21
            java.security.CodeSource r0 = r0.getCodeSource()     // Catch: java.net.URISyntaxException -> L21
            java.net.URL r0 = r0.getLocation()     // Catch: java.net.URISyntaxException -> L21
            java.net.URI r0 = r0.toURI()     // Catch: java.net.URISyntaxException -> L21
            r6 = r0
            r0 = r6
            if (r0 == 0) goto L1e
            org.spongepowered.asm.launch.platform.container.ContainerHandleURI r0 = new org.spongepowered.asm.launch.platform.container.ContainerHandleURI     // Catch: java.net.URISyntaxException -> L21
            r1 = r0
            r2 = r6
            r1.<init>(r2)     // Catch: java.net.URISyntaxException -> L21
            return r0
        L1e:
            goto L26
        L21:
            r7 = move-exception
            r0 = r7
            r0.printStackTrace()
        L26:
            org.spongepowered.asm.launch.platform.container.ContainerHandleVirtual r0 = new org.spongepowered.asm.launch.platform.container.ContainerHandleVirtual
            r1 = r0
            r2 = r5
            java.lang.String r3 = "LaunchWrapper"
            r2.<init>(r3)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongepowered.asm.service.mojang.MixinServiceLaunchWrapper.getPrimaryContainer():org.spongepowered.asm.launch.platform.container.IContainerHandle");
    }

    @Override // org.spongepowered.asm.service.MixinServiceAbstract, org.spongepowered.asm.service.IMixinService
    public Collection getMixinContainers() throws URISyntaxException {
        ImmutableList.Builder builder = ImmutableList.builder();
        getContainersFromClassPath(builder);
        getContainersFromAgents(builder);
        return builder.build();
    }

    private void getContainersFromClassPath(ImmutableList.Builder builder) throws URISyntaxException {
        URL[] classPath = getClassPath();
        if (classPath != null) {
            for (URL url : classPath) {
                try {
                    URI uri = url.toURI();
                    MixinServiceAbstract.logger.debug("Scanning {} for mixin tweaker", new Object[]{uri});
                    if ("file".equals(uri.getScheme()) && new File(uri).exists()) {
                        if (MIXIN_TWEAKER_CLASS.equals(MainAttributes.m52of(uri).get(Constants.ManifestAttributes.TWEAKER))) {
                            builder.add(new ContainerHandleURI(uri));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public IClassTracker getClassTracker() {
        return this.classLoaderUtil;
    }

    @Override // org.spongepowered.asm.service.IClassProvider
    public Class findClass(String str) {
        return Launch.classLoader.findClass(str);
    }

    @Override // org.spongepowered.asm.service.IClassProvider
    public Class findClass(String str, boolean z) {
        return Class.forName(str, z, Launch.classLoader);
    }

    @Override // org.spongepowered.asm.service.IClassProvider
    public Class findAgentClass(String str, boolean z) {
        return Class.forName(str, z, Launch.class.getClassLoader());
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public void beginPhase() {
        Launch.classLoader.registerTransformer(TRANSFORMER_PROXY_CLASS);
        this.delegatedTransformers = null;
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public void checkEnv(Object obj) {
        if (obj.getClass().getClassLoader() != Launch.class.getClassLoader()) {
            throw new MixinException("Attempted to init the mixin environment in the wrong classloader");
        }
    }

    @Override // org.spongepowered.asm.service.IMixinService
    public InputStream getResourceAsStream(String str) {
        return Launch.classLoader.getResourceAsStream(str);
    }

    @Override // org.spongepowered.asm.service.IClassProvider
    @Deprecated
    public URL[] getClassPath() {
        return (URL[]) Launch.classLoader.getSources().toArray(new URL[0]);
    }

    @Override // org.spongepowered.asm.service.ITransformerProvider
    public Collection getTransformers() {
        List<ITransformer> transformers = Launch.classLoader.getTransformers();
        ArrayList arrayList = new ArrayList(transformers.size());
        for (ITransformer iTransformer : transformers) {
            if (iTransformer instanceof ITransformer) {
                arrayList.add(iTransformer);
            } else {
                arrayList.add(new LegacyTransformerHandle(iTransformer));
            }
            if (iTransformer instanceof IClassNameTransformer) {
                MixinServiceAbstract.logger.debug("Found name transformer: {}", new Object[]{iTransformer.getClass().getName()});
                this.nameTransformer = (IClassNameTransformer) iTransformer;
            }
        }
        return arrayList;
    }

    @Override // org.spongepowered.asm.service.ITransformerProvider
    public List getDelegatedTransformers() {
        return Collections.unmodifiableList(getDelegatedLegacyTransformers());
    }

    private List getDelegatedLegacyTransformers() {
        if (this.delegatedTransformers == null) {
            buildTransformerDelegationList();
        }
        return this.delegatedTransformers;
    }

    private void buildTransformerDelegationList() {
        MixinServiceAbstract.logger.debug("Rebuilding transformer delegation list:");
        this.delegatedTransformers = new ArrayList();
        for (ITransformer iTransformer : getTransformers()) {
            if (iTransformer instanceof ILegacyClassTransformer) {
                ILegacyClassTransformer iLegacyClassTransformer = (ILegacyClassTransformer) iTransformer;
                String name = iLegacyClassTransformer.getName();
                boolean z = true;
                Iterator it = excludeTransformers.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (name.contains((String) it.next())) {
                            z = false;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (z && !iLegacyClassTransformer.isDelegationExcluded()) {
                    MixinServiceAbstract.logger.debug("  Adding:    {}", new Object[]{name});
                    this.delegatedTransformers.add(iLegacyClassTransformer);
                } else {
                    MixinServiceAbstract.logger.debug("  Excluding: {}", new Object[]{name});
                }
            }
        }
        MixinServiceAbstract.logger.debug("Transformer delegation list created with {} entries", new Object[]{Integer.valueOf(this.delegatedTransformers.size())});
    }

    @Override // org.spongepowered.asm.service.ITransformerProvider
    public void addTransformerExclusion(String str) {
        excludeTransformers.add(str);
        this.delegatedTransformers = null;
    }

    @Deprecated
    public byte[] getClassBytes(String str, String str2) {
        URLClassLoader uRLClassLoader;
        byte[] classBytes = Launch.classLoader.getClassBytes(str);
        if (classBytes != null) {
            return classBytes;
        }
        if (Launch.class.getClassLoader() instanceof URLClassLoader) {
            uRLClassLoader = (URLClassLoader) Launch.class.getClassLoader();
        } else {
            uRLClassLoader = new URLClassLoader(new URL[0], Launch.class.getClassLoader());
        }
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = uRLClassLoader.getResourceAsStream(str2.replace('.', '/').concat(".class"));
            byte[] byteArray = ByteStreams.toByteArray(resourceAsStream);
            Closeables.closeQuietly(resourceAsStream);
            return byteArray;
        } catch (Exception unused) {
            Closeables.closeQuietly(resourceAsStream);
            return null;
        } catch (Throwable th) {
            Closeables.closeQuietly(resourceAsStream);
            throw th;
        }
    }

    @Deprecated
    public byte[] getClassBytes(String str, boolean z) throws ClassNotFoundException {
        String strReplace = str.replace('/', '.');
        String strUnmapClassName = unmapClassName(strReplace);
        Profiler profiler = MixinEnvironment.getProfiler();
        Profiler.Section sectionBegin = profiler.begin(1, "class.load");
        byte[] classBytes = getClassBytes(strUnmapClassName, strReplace);
        sectionBegin.end();
        if (z) {
            Profiler.Section sectionBegin2 = profiler.begin(1, "class.transform");
            classBytes = applyTransformers(strUnmapClassName, strReplace, classBytes, profiler);
            sectionBegin2.end();
        }
        if (classBytes == null) {
            throw new ClassNotFoundException(String.format("The specified class '%s' was not found", strReplace));
        }
        return classBytes;
    }

    private byte[] applyTransformers(String str, String str2, byte[] bArr, Profiler profiler) {
        if (this.classLoaderUtil.isClassExcluded(str, str2)) {
            return bArr;
        }
        for (ILegacyClassTransformer iLegacyClassTransformer : getDelegatedLegacyTransformers()) {
            this.lock.clear();
            Profiler.Section sectionBegin = profiler.begin(2, iLegacyClassTransformer.getName().substring(iLegacyClassTransformer.getName().lastIndexOf(46) + 1).toLowerCase(Locale.ROOT));
            sectionBegin.setInfo(iLegacyClassTransformer.getName());
            bArr = iLegacyClassTransformer.transformClassBytes(str, str2, bArr);
            sectionBegin.end();
            if (this.lock.isSet()) {
                addTransformerExclusion(iLegacyClassTransformer.getName());
                this.lock.clear();
                MixinServiceAbstract.logger.info("A re-entrant transformer '{}' was detected and will no longer process meta class data", new Object[]{iLegacyClassTransformer.getName()});
            }
        }
        return bArr;
    }

    private String unmapClassName(String str) {
        if (this.nameTransformer == null) {
            findNameTransformer();
        }
        if (this.nameTransformer != null) {
            return this.nameTransformer.unmapClassName(str);
        }
        return str;
    }

    private void findNameTransformer() {
        for (IClassNameTransformer iClassNameTransformer : Launch.classLoader.getTransformers()) {
            if (iClassNameTransformer instanceof IClassNameTransformer) {
                MixinServiceAbstract.logger.debug("Found name transformer: {}", new Object[]{iClassNameTransformer.getClass().getName()});
                this.nameTransformer = iClassNameTransformer;
            }
        }
    }

    @Override // org.spongepowered.asm.service.IClassBytecodeProvider
    public ClassNode getClassNode(String str) {
        return getClassNode(getClassBytes(str, true), 8);
    }

    @Override // org.spongepowered.asm.service.IClassBytecodeProvider
    public ClassNode getClassNode(String str, boolean z) {
        return getClassNode(getClassBytes(str, true), 8);
    }

    private ClassNode getClassNode(byte[] bArr, int i) {
        ClassNode classNode = new ClassNode();
        new ClassReader(bArr).accept(classNode, i);
        return classNode;
    }

    private static int findInStackTrace(String str, String str2) {
        Thread threadCurrentThread = Thread.currentThread();
        if (!"main".equals(threadCurrentThread.getName())) {
            return 0;
        }
        for (StackTraceElement stackTraceElement : threadCurrentThread.getStackTrace()) {
            if (str.equals(stackTraceElement.getClassName()) && str2.equals(stackTraceElement.getMethodName())) {
                return stackTraceElement.getLineNumber();
            }
        }
        return 0;
    }
}
