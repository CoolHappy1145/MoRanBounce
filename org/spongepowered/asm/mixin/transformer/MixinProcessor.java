package org.spongepowered.asm.mixin.transformer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.throwables.ClassAlreadyLoadedException;
import org.spongepowered.asm.mixin.throwables.MixinApplyError;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.MixinConfig;
import org.spongepowered.asm.mixin.transformer.MixinInfo;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckClass;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionClassExporter;
import org.spongepowered.asm.mixin.transformer.throwables.IllegalClassLoadError;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.mixin.transformer.throwables.ReEntrantTransformerError;
import org.spongepowered.asm.service.IMixinAuditTrail;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.ReEntranceLock;
import org.spongepowered.asm.util.perf.Profiler;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinProcessor.class */
public class MixinProcessor {
    static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private final Extensions extensions;
    private final IHotSwap hotSwapper;
    private MixinEnvironment currentEnvironment;
    private final IMixinService service = MixinService.getService();
    private final List<MixinConfig> configs = new ArrayList();
    private final List<MixinConfig> pendingConfigs = new ArrayList();
    private final String sessionId = UUID.randomUUID().toString();
    private Level verboseLoggingLevel = Level.DEBUG;
    private boolean errorState = false;
    private int transformedCount = 0;
    private final ReEntranceLock lock = this.service.getReEntranceLock();
    private final MixinPostProcessor postProcessor = new MixinPostProcessor(this.sessionId);
    private final Profiler profiler = MixinEnvironment.getProfiler();
    private final IMixinAuditTrail auditTrail = this.service.getAuditTrail();

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinProcessor$ErrorPhase.class */
    enum ErrorPhase {
        PREPARE { // from class: org.spongepowered.asm.mixin.transformer.MixinProcessor.ErrorPhase.1
            @Override // org.spongepowered.asm.mixin.transformer.MixinProcessor.ErrorPhase
            IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler iMixinErrorHandler, String str, InvalidMixinException invalidMixinException, IMixinInfo iMixinInfo, IMixinErrorHandler.ErrorAction errorAction) {
                try {
                    return iMixinErrorHandler.onPrepareError(iMixinInfo.getConfig(), invalidMixinException, iMixinInfo, errorAction);
                } catch (AbstractMethodError unused) {
                    return errorAction;
                }
            }

            @Override // org.spongepowered.asm.mixin.transformer.MixinProcessor.ErrorPhase
            protected String getContext(IMixinInfo iMixinInfo, String str) {
                return String.format("preparing %s in %s", iMixinInfo.getName(), str);
            }
        },
        APPLY { // from class: org.spongepowered.asm.mixin.transformer.MixinProcessor.ErrorPhase.2
            @Override // org.spongepowered.asm.mixin.transformer.MixinProcessor.ErrorPhase
            IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler iMixinErrorHandler, String str, InvalidMixinException invalidMixinException, IMixinInfo iMixinInfo, IMixinErrorHandler.ErrorAction errorAction) {
                try {
                    return iMixinErrorHandler.onApplyError(str, invalidMixinException, iMixinInfo, errorAction);
                } catch (AbstractMethodError unused) {
                    return errorAction;
                }
            }

            @Override // org.spongepowered.asm.mixin.transformer.MixinProcessor.ErrorPhase
            protected String getContext(IMixinInfo iMixinInfo, String str) {
                return String.format("%s -> %s", iMixinInfo, str);
            }
        };

        private final String text;

        abstract IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler iMixinErrorHandler, String str, InvalidMixinException invalidMixinException, IMixinInfo iMixinInfo, IMixinErrorHandler.ErrorAction errorAction);

        protected abstract String getContext(IMixinInfo iMixinInfo, String str);

        ErrorPhase(C05701 c05701) {
            this();
        }

        ErrorPhase() {
            this.text = name().toLowerCase(Locale.ROOT);
        }

        public String getLogMessage(String str, InvalidMixinException invalidMixinException, IMixinInfo iMixinInfo) {
            return String.format("Mixin %s failed %s: %s %s", this.text, getContext(iMixinInfo, str), invalidMixinException.getClass().getName(), invalidMixinException.getMessage());
        }

        public String getErrorMessage(IMixinInfo iMixinInfo, IMixinConfig iMixinConfig, MixinEnvironment.Phase phase) {
            return String.format("Mixin [%s] from phase [%s] in config [%s] FAILED during %s", iMixinInfo, phase, iMixinConfig, name());
        }
    }

    MixinProcessor(MixinEnvironment environment, Extensions extensions, IHotSwap hotSwapper) {
        this.extensions = extensions;
        this.hotSwapper = hotSwapper;
    }

    public void audit(MixinEnvironment environment) {
        Set<String> unhandled = new HashSet<>();
        for (MixinConfig config : this.configs) {
            unhandled.addAll(config.getUnhandledTargets());
        }
        Logger auditLogger = LogManager.getLogger("mixin/audit");
        for (String target : unhandled) {
            try {
                auditLogger.info("Force-loading class {}", new Object[]{target});
                this.service.getClassProvider().findClass(target, true);
            } catch (ClassNotFoundException ex) {
                auditLogger.error("Could not force-load " + target, ex);
            }
        }
        for (MixinConfig config2 : this.configs) {
            for (String target2 : config2.getUnhandledTargets()) {
                ClassAlreadyLoadedException ex2 = new ClassAlreadyLoadedException(target2 + " was already classloaded");
                auditLogger.error("Could not force-load " + target2, ex2);
            }
        }
        if (environment.getOption(MixinEnvironment.Option.DEBUG_PROFILER)) {
            this.profiler.printSummary();
        }
    }

    synchronized boolean applyMixins(MixinEnvironment environment, String name, ClassNode targetClassNode) throws Error {
        if (name == null || this.errorState) {
            return false;
        }
        boolean locked = this.lock.push().check();
        Profiler.Section mixinTimer = this.profiler.begin(MixinLaunchPlugin.NAME);
        if (locked) {
            Iterator<MixinConfig> it = this.pendingConfigs.iterator();
            while (it.hasNext()) {
                if (it.next().hasPendingMixinsFor(name)) {
                    ReEntrantTransformerError error = new ReEntrantTransformerError("Re-entrance error.");
                    logger.warn("Re-entrance detected during prepare phase, this will cause serious problems.", error);
                    throw error;
                }
            }
        } else {
            try {
                checkSelect(environment);
            } catch (Exception ex) {
                this.lock.pop();
                mixinTimer.end();
                throw new MixinException(ex);
            }
        }
        boolean success = false;
        try {
            try {
                try {
                    if (this.postProcessor.canProcess(name)) {
                        if (this.auditTrail != null) {
                            this.auditTrail.onPostProcess(name);
                        }
                        Profiler.Section postTimer = this.profiler.begin("postprocessor");
                        boolean success2 = this.postProcessor.processClass(name, targetClassNode);
                        postTimer.end();
                        this.extensions.export(environment, name, false, targetClassNode);
                        this.lock.pop();
                        mixinTimer.end();
                        return success2;
                    }
                    SortedSet<MixinInfo> mixins = null;
                    MixinConfig packageOwnedByConfig = null;
                    for (MixinConfig config : this.configs) {
                        if (config.packageMatch(name)) {
                            int packageLen = packageOwnedByConfig != null ? packageOwnedByConfig.getMixinPackage().length() : 0;
                            if (config.getMixinPackage().length() > packageLen) {
                                packageOwnedByConfig = config;
                            }
                        } else if (config.hasMixinsFor(name)) {
                            if (mixins == null) {
                                mixins = new TreeSet<>();
                            }
                            mixins.addAll(config.getMixinsFor(name));
                        }
                    }
                    if (packageOwnedByConfig != null) {
                        throw new IllegalClassLoadError(getInvalidClassError(name, targetClassNode, packageOwnedByConfig));
                    }
                    if (mixins != null) {
                        if (locked) {
                            ReEntrantTransformerError error2 = new ReEntrantTransformerError("Re-entrance error.");
                            logger.warn("Re-entrance detected, this will cause serious problems.", error2);
                            throw error2;
                        }
                        if (this.hotSwapper != null) {
                            this.hotSwapper.registerTargetClass(name, targetClassNode);
                        }
                        try {
                            Profiler.Section timer = this.profiler.begin("read");
                            TargetClassContext context = new TargetClassContext(environment, this.extensions, this.sessionId, name, targetClassNode, mixins);
                            timer.end();
                            applyMixins(environment, context);
                            this.transformedCount++;
                            success = true;
                        } catch (InvalidMixinException th) {
                            dumpClassOnFailure(name, targetClassNode, environment);
                            handleMixinApplyError(name, th, environment);
                        }
                    }
                    return success;
                } finally {
                    this.lock.pop();
                    mixinTimer.end();
                }
            } catch (MixinTransformerError er) {
                throw er;
            }
        } catch (Throwable th2) {
            th2.printStackTrace();
            dumpClassOnFailure(name, targetClassNode, environment);
            throw new MixinTransformerError("An unexpected critical error was encountered", th2);
        }
    }

    private String getInvalidClassError(String name, ClassNode targetClassNode, MixinConfig ownedByConfig) {
        if (ownedByConfig.getClasses().contains(name)) {
            return String.format("Illegal classload request for %s. Mixin is defined in %s and cannot be referenced directly", name, ownedByConfig);
        }
        AnnotationNode mixin = Annotations.getInvisible(targetClassNode, Mixin.class);
        if (mixin != null) {
            MixinInfo.Variant variant = MixinInfo.getVariant(targetClassNode);
            if (variant == MixinInfo.Variant.ACCESSOR) {
                return String.format("Illegal classload request for accessor mixin %s. The mixin is missing from %s which owns package %s* and the mixin has not been applied.", name, ownedByConfig, ownedByConfig.getMixinPackage());
            }
        }
        return String.format("%s is in a defined mixin package %s* owned by %s and cannot be referenced directly", name, ownedByConfig.getMixinPackage(), ownedByConfig);
    }

    public List<String> reload(String mixinClass, ClassNode classNode) {
        if (this.lock.getDepth() > 0) {
            throw new MixinApplyError("Cannot reload mixin if re-entrant lock entered");
        }
        List<String> targets = new ArrayList<>();
        for (MixinConfig config : this.configs) {
            targets.addAll(config.reloadMixin(mixinClass, classNode));
        }
        return targets;
    }

    private void checkSelect(MixinEnvironment environment) throws Error {
        if (this.currentEnvironment != environment) {
            select(environment);
            return;
        }
        int unvisitedCount = Mixins.getUnvisitedCount();
        if (unvisitedCount > 0 && this.transformedCount == 0) {
            select(environment);
        }
    }

    private void select(MixinEnvironment environment) throws Error {
        this.verboseLoggingLevel = environment.getOption(MixinEnvironment.Option.DEBUG_VERBOSE) ? Level.INFO : Level.DEBUG;
        if (this.transformedCount > 0) {
            logger.log(this.verboseLoggingLevel, "Ending {}, applied {} mixins", new Object[]{this.currentEnvironment, Integer.valueOf(this.transformedCount)});
        }
        String action = this.currentEnvironment == environment ? "Checking for additional" : "Preparing";
        logger.log(this.verboseLoggingLevel, "{} mixins for {}", new Object[]{action, environment});
        this.profiler.setActive(true);
        this.profiler.mark(environment.getPhase().toString() + ":prepare");
        Profiler.Section prepareTimer = this.profiler.begin("prepare");
        selectConfigs(environment);
        this.extensions.select(environment);
        int totalMixins = prepareConfigs(environment);
        this.currentEnvironment = environment;
        this.transformedCount = 0;
        prepareTimer.end();
        long elapsedMs = prepareTimer.getTime();
        double elapsedTime = prepareTimer.getSeconds();
        if (elapsedTime > 0.25d) {
            long loadTime = this.profiler.get("class.load").getTime();
            long transformTime = this.profiler.get("class.transform").getTime();
            long pluginTime = this.profiler.get("mixin.plugin").getTime();
            String elapsed = new DecimalFormat("###0.000").format(elapsedTime);
            String perMixinTime = new DecimalFormat("###0.0").format(elapsedMs / totalMixins);
            logger.log(this.verboseLoggingLevel, "Prepared {} mixins in {} sec ({}ms avg) ({}ms load, {}ms transform, {}ms plugin)", new Object[]{Integer.valueOf(totalMixins), elapsed, perMixinTime, Long.valueOf(loadTime), Long.valueOf(transformTime), Long.valueOf(pluginTime)});
        }
        this.profiler.mark(environment.getPhase().toString() + ":apply");
        this.profiler.setActive(environment.getOption(MixinEnvironment.Option.DEBUG_PROFILER));
    }

    private void selectConfigs(MixinEnvironment environment) {
        Iterator<Config> iter = Mixins.getConfigs().iterator();
        while (iter.hasNext()) {
            Config handle = iter.next();
            try {
                MixinConfig config = handle.get();
                if (config.select(environment)) {
                    iter.remove();
                    logger.log(this.verboseLoggingLevel, "Selecting config {}", new Object[]{config});
                    config.onSelect();
                    this.pendingConfigs.add(config);
                }
            } catch (Exception ex) {
                logger.warn(String.format("Failed to select mixin config: %s", handle), ex);
            }
        }
        Collections.sort(this.pendingConfigs);
    }

    private int prepareConfigs(MixinEnvironment environment) throws Error {
        int totalMixins = 0;
        IHotSwap hotSwapper = this.hotSwapper;
        for (MixinConfig config : this.pendingConfigs) {
            config.addListener(this.postProcessor);
            if (hotSwapper != null) {
                config.addListener(new MixinConfig.IListener(this, hotSwapper) { // from class: org.spongepowered.asm.mixin.transformer.MixinProcessor.1
                    final IHotSwap val$hotSwapper;
                    final MixinProcessor this$0;

                    {
                        this.this$0 = this;
                        this.val$hotSwapper = hotSwapper;
                    }

                    @Override // org.spongepowered.asm.mixin.transformer.MixinConfig.IListener
                    public void onPrepare(MixinInfo mixinInfo) {
                        this.val$hotSwapper.registerMixinClass(mixinInfo.getClassName());
                    }
                });
            }
        }
        for (MixinConfig config2 : this.pendingConfigs) {
            try {
                logger.log(this.verboseLoggingLevel, "Preparing {} ({})", new Object[]{config2, Integer.valueOf(config2.getDeclaredMixinCount())});
                config2.prepare();
                totalMixins += config2.getMixinCount();
            } catch (InvalidMixinException ex) {
                handleMixinPrepareError(config2, ex, environment);
            } catch (Exception ex2) {
                String message = ex2.getMessage();
                logger.error("Error encountered whilst initialising mixin config '" + config2.getName() + "': " + message, ex2);
            }
        }
        for (MixinConfig config3 : this.pendingConfigs) {
            IMixinConfigPlugin plugin = config3.getPlugin();
            if (plugin != null) {
                Set<String> otherTargets = new HashSet<>();
                for (MixinConfig otherConfig : this.pendingConfigs) {
                    if (!otherConfig.equals(config3)) {
                        otherTargets.addAll(otherConfig.getTargets());
                    }
                }
                plugin.acceptTargets(config3.getTargets(), Collections.unmodifiableSet(otherTargets));
            }
        }
        for (MixinConfig config4 : this.pendingConfigs) {
            try {
                config4.postInitialise();
            } catch (InvalidMixinException ex3) {
                handleMixinPrepareError(config4, ex3, environment);
            } catch (Exception ex4) {
                String message2 = ex4.getMessage();
                logger.error("Error encountered during mixin config postInit step'" + config4.getName() + "': " + message2, ex4);
            }
        }
        this.configs.addAll(this.pendingConfigs);
        Collections.sort(this.configs);
        this.pendingConfigs.clear();
        return totalMixins;
    }

    private void applyMixins(MixinEnvironment environment, TargetClassContext context) throws Error {
        Profiler.Section timer = this.profiler.begin("preapply");
        this.extensions.preApply(context);
        Profiler.Section timer2 = timer.next("apply");
        context.applyMixins();
        Profiler.Section timer3 = timer2.next("postapply");
        boolean export = false;
        try {
            this.extensions.postApply(context);
            export = true;
        } catch (ExtensionCheckClass.ValidationFailedException ex) {
            logger.info(ex.getMessage());
            export |= context.isExportForced() || environment.getOption(MixinEnvironment.Option.DEBUG_EXPORT);
        }
        timer3.end();
        if (export) {
            this.extensions.export(this.currentEnvironment, context.getClassName(), context.isExportForced(), context.getClassNode());
        }
        for (InvalidMixinException suppressed : context.getSuppressedExceptions()) {
            handleMixinApplyError(context.getClassName(), suppressed, environment);
        }
    }

    private void handleMixinPrepareError(MixinConfig config, InvalidMixinException ex, MixinEnvironment environment) throws Error {
        handleMixinError(config.getName(), ex, environment, ErrorPhase.PREPARE);
    }

    private void handleMixinApplyError(String targetClass, InvalidMixinException ex, MixinEnvironment environment) throws Error {
        handleMixinError(targetClass, ex, environment, ErrorPhase.APPLY);
    }

    private void handleMixinError(String context, InvalidMixinException ex, MixinEnvironment environment, ErrorPhase errorPhase) throws Error {
        this.errorState = true;
        IMixinInfo mixin = ex.getMixin();
        if (mixin == null) {
            logger.error("InvalidMixinException has no mixin!", ex);
            throw ex;
        }
        IMixinConfig config = mixin.getConfig();
        MixinEnvironment.Phase phase = mixin.getPhase();
        IMixinErrorHandler.ErrorAction action = config.isRequired() ? IMixinErrorHandler.ErrorAction.ERROR : IMixinErrorHandler.ErrorAction.WARN;
        if (environment.getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
            new PrettyPrinter().wrapTo(160).add("Invalid Mixin").centre().m77hr('-').kvWidth(10).m75kv("Action", errorPhase.name()).m75kv("Mixin", mixin.getClassName()).m75kv("Config", config.getName()).m75kv("Phase", phase).m77hr('-').add("    %s", new Object[]{ex.getClass().getName()}).m77hr('-').addWrapped("    %s", new Object[]{ex.getMessage()}).m77hr('-').add((Throwable) ex, 8).log(action.logLevel);
        }
        for (IMixinErrorHandler handler : getErrorHandlers(mixin.getPhase())) {
            IMixinErrorHandler.ErrorAction newAction = errorPhase.onError(handler, context, ex, mixin, action);
            if (newAction != null) {
                action = newAction;
            }
        }
        logger.log(action.logLevel, errorPhase.getLogMessage(context, ex, mixin), ex);
        this.errorState = false;
        if (action == IMixinErrorHandler.ErrorAction.ERROR) {
            throw new MixinApplyError(errorPhase.getErrorMessage(mixin, config, phase), ex);
        }
    }

    private List<IMixinErrorHandler> getErrorHandlers(MixinEnvironment.Phase phase) {
        List<IMixinErrorHandler> handlers = new ArrayList<>();
        for (String handlerClassName : Mixins.getErrorHandlerClasses()) {
            try {
                logger.info("Instancing error handler class {}", new Object[]{handlerClassName});
                Class<?> handlerClass = this.service.getClassProvider().findClass(handlerClassName, true);
                IMixinErrorHandler handler = (IMixinErrorHandler) handlerClass.newInstance();
                if (handler != null) {
                    handlers.add(handler);
                }
            } catch (Throwable th) {
            }
        }
        return handlers;
    }

    private void dumpClassOnFailure(String className, ClassNode classNode, MixinEnvironment env) {
        if (env.getOption(MixinEnvironment.Option.DUMP_TARGET_ON_FAILURE)) {
            ExtensionClassExporter exporter = (ExtensionClassExporter) this.extensions.getExtension(ExtensionClassExporter.class);
            exporter.dumpClass(className.replace('.', '/') + ".target", classNode);
        }
    }
}
