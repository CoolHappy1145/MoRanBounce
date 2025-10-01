package org.spongepowered.asm.mixin;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.extensibility.IEnvironmentTokenProvider;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.IMixinTransformer;
import org.spongepowered.asm.obfuscation.RemapperChain;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.ITransformer;
import org.spongepowered.asm.service.ITransformerProvider;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.service.MixinServiceAbstract;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.IConsumer;
import org.spongepowered.asm.util.ITokenProvider;
import org.spongepowered.asm.util.JavaVersion;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.asm.ASM;
import org.spongepowered.asm.util.perf.Profiler;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/MixinEnvironment.class */
public final class MixinEnvironment implements ITokenProvider {
    private static MixinEnvironment currentEnvironment;
    private static CompatibilityLevel compatibility;
    private static IMixinTransformer transformer;
    private final Phase phase;
    private final GlobalProperties.Keys configsKey;
    private final boolean[] options;
    private Side side;
    private static Phase currentPhase = Phase.NOT_INITIALISED;
    private static boolean showHeader = true;
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private static final Profiler profiler = new Profiler();
    private final Set<String> tokenProviderClasses = new HashSet();
    private final List<TokenProviderWrapper> tokenProviders = new ArrayList();
    private final Map<String, Integer> internalTokens = new HashMap();
    private final RemapperChain remappers = new RemapperChain();
    private String obfuscationContext = null;
    private final IMixinService service = MixinService.getService();

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/MixinEnvironment$Phase.class */
    public static final class Phase {
        static final Phase NOT_INITIALISED = new Phase(-1, "NOT_INITIALISED");
        public static final Phase PREINIT = new Phase(0, "PREINIT");
        public static final Phase INIT = new Phase(1, "INIT");
        public static final Phase DEFAULT = new Phase(2, "DEFAULT");
        static final List phases = ImmutableList.of(PREINIT, INIT, DEFAULT);
        final int ordinal;
        final String name;
        private MixinEnvironment environment;

        private Phase(int i, String str) {
            this.ordinal = i;
            this.name = str;
        }

        public String toString() {
            return this.name;
        }

        public static Phase forName(String str) {
            for (Phase phase : phases) {
                if (phase.name.equals(str)) {
                    return phase;
                }
            }
            return null;
        }

        MixinEnvironment getEnvironment() {
            if (this.ordinal < 0) {
                throw new IllegalArgumentException("Cannot access the NOT_INITIALISED environment");
            }
            if (this.environment == null) {
                this.environment = new MixinEnvironment(this);
            }
            return this.environment;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/MixinEnvironment$Side.class */
    public enum Side {
        UNKNOWN { // from class: org.spongepowered.asm.mixin.MixinEnvironment.Side.1
        },
        CLIENT { // from class: org.spongepowered.asm.mixin.MixinEnvironment.Side.2
            @Override // org.spongepowered.asm.mixin.MixinEnvironment.Side
            protected boolean detect() {
                return Constants.SIDE_CLIENT.equals(MixinService.getService().getSideName());
            }
        },
        SERVER { // from class: org.spongepowered.asm.mixin.MixinEnvironment.Side.3
            @Override // org.spongepowered.asm.mixin.MixinEnvironment.Side
            protected boolean detect() {
                String sideName = MixinService.getService().getSideName();
                return Constants.SIDE_SERVER.equals(sideName) || Constants.SIDE_DEDICATEDSERVER.equals(sideName);
            }
        };

        protected abstract boolean detect();

        Side(C05491 c05491) {
            this();
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/MixinEnvironment$Option.class */
    public enum Option {
        DEBUG_ALL("debug"),
        DEBUG_EXPORT(DEBUG_ALL, "export"),
        DEBUG_EXPORT_FILTER(DEBUG_EXPORT, "filter", false),
        DEBUG_EXPORT_DECOMPILE(DEBUG_EXPORT, Inherit.ALLOW_OVERRIDE, "decompile"),
        DEBUG_EXPORT_DECOMPILE_THREADED(DEBUG_EXPORT_DECOMPILE, Inherit.ALLOW_OVERRIDE, "async"),
        DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES(DEBUG_EXPORT_DECOMPILE, Inherit.ALLOW_OVERRIDE, "mergeGenericSignatures"),
        DEBUG_VERIFY(DEBUG_ALL, "verify"),
        DEBUG_VERBOSE(DEBUG_ALL, "verbose"),
        DEBUG_INJECTORS(DEBUG_ALL, "countInjections"),
        DEBUG_STRICT(DEBUG_ALL, Inherit.INDEPENDENT, "strict"),
        DEBUG_UNIQUE(DEBUG_STRICT, "unique"),
        DEBUG_TARGETS(DEBUG_STRICT, "targets"),
        DEBUG_PROFILER(DEBUG_ALL, Inherit.ALLOW_OVERRIDE, "profiler"),
        DUMP_TARGET_ON_FAILURE("dumpTargetOnFailure"),
        CHECK_ALL("checks"),
        CHECK_IMPLEMENTS(CHECK_ALL, "interfaces"),
        CHECK_IMPLEMENTS_STRICT(CHECK_IMPLEMENTS, Inherit.ALLOW_OVERRIDE, "strict"),
        IGNORE_CONSTRAINTS("ignoreConstraints"),
        HOT_SWAP("hotSwap"),
        ENVIRONMENT(Inherit.ALWAYS_FALSE, "env"),
        OBFUSCATION_TYPE(ENVIRONMENT, Inherit.ALWAYS_FALSE, "obf"),
        DISABLE_REFMAP(ENVIRONMENT, Inherit.INDEPENDENT, "disableRefMap"),
        REFMAP_REMAP(ENVIRONMENT, Inherit.INDEPENDENT, "remapRefMap"),
        REFMAP_REMAP_RESOURCE(ENVIRONMENT, Inherit.INDEPENDENT, "refMapRemappingFile", ""),
        REFMAP_REMAP_SOURCE_ENV(ENVIRONMENT, Inherit.INDEPENDENT, "refMapRemappingEnv", "searge"),
        REFMAP_REMAP_ALLOW_PERMISSIVE(ENVIRONMENT, Inherit.INDEPENDENT, "allowPermissiveMatch", true, "true"),
        IGNORE_REQUIRED(ENVIRONMENT, Inherit.INDEPENDENT, "ignoreRequired"),
        DEFAULT_COMPATIBILITY_LEVEL(ENVIRONMENT, Inherit.INDEPENDENT, "compatLevel"),
        SHIFT_BY_VIOLATION_BEHAVIOUR(ENVIRONMENT, Inherit.INDEPENDENT, "shiftByViolation", "warn"),
        INITIALISER_INJECTION_MODE("initialiserInjectionMode", "default");

        private static final String PREFIX = "mixin";
        final Option parent;
        final Inherit inheritance;
        final String property;
        final String defaultValue;
        final boolean isFlag;
        final int depth;

        /* loaded from: L-out.jar:org/spongepowered/asm/mixin/MixinEnvironment$Option$Inherit.class */
        private enum Inherit {
            INHERIT,
            ALLOW_OVERRIDE,
            INDEPENDENT,
            ALWAYS_FALSE
        }

        Option(String str) {
            this((Option) null, str, true);
        }

        Option(Inherit inherit, String str) {
            this((Option) null, inherit, str, true);
        }

        Option(String str, boolean z) {
            this((Option) null, str, z);
        }

        Option(String str, String str2) {
            this(null, Inherit.INDEPENDENT, str, false, str2);
        }

        Option(Option option, String str) {
            this(option, Inherit.INHERIT, str, true);
        }

        Option(Option option, Inherit inherit, String str) {
            this(option, inherit, str, true);
        }

        Option(Option option, String str, boolean z) {
            this(option, Inherit.INHERIT, str, z, null);
        }

        Option(Option option, Inherit inherit, String str, boolean z) {
            this(option, inherit, str, z, null);
        }

        Option(Option option, String str, String str2) {
            this(option, Inherit.INHERIT, str, false, str2);
        }

        Option(Option option, Inherit inherit, String str, String str2) {
            this(option, inherit, str, false, str2);
        }

        Option(Option option, Inherit inherit, String str, boolean z, String str2) {
            this.parent = option;
            this.inheritance = inherit;
            this.property = (option != null ? option.property : "mixin") + "." + str;
            this.defaultValue = str2;
            this.isFlag = z;
            int i = 0;
            while (option != null) {
                option = option.parent;
                i++;
            }
            this.depth = i;
        }

        Option getParent() {
            return this.parent;
        }

        String getProperty() {
            return this.property;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.isFlag ? String.valueOf(getBooleanValue()) : getStringValue();
        }

        private boolean getLocalBooleanValue(boolean z) {
            return Boolean.parseBoolean(System.getProperty(this.property, Boolean.toString(z)));
        }

        private boolean getInheritedBooleanValue() {
            return this.parent != null && this.parent.getBooleanValue();
        }

        final boolean getBooleanValue() {
            if (this.inheritance == Inherit.ALWAYS_FALSE) {
                return false;
            }
            boolean localBooleanValue = getLocalBooleanValue(false);
            if (this.inheritance == Inherit.INDEPENDENT) {
                return localBooleanValue;
            }
            boolean z = localBooleanValue || getInheritedBooleanValue();
            return this.inheritance == Inherit.INHERIT ? z : getLocalBooleanValue(z);
        }

        final String getStringValue() {
            return (this.inheritance == Inherit.INDEPENDENT || this.parent == null || this.parent.getBooleanValue()) ? System.getProperty(this.property, this.defaultValue) : this.defaultValue;
        }

        Enum getEnumValue(Enum r5) {
            try {
                return Enum.valueOf(r5.getClass(), System.getProperty(this.property, r5.name()).toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException unused) {
                return r5;
            }
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/MixinEnvironment$CompatibilityLevel.class */
    public enum CompatibilityLevel {
        JAVA_6(6, 50, 0),
        JAVA_7(7, 51, 0) { // from class: org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel.1
            @Override // org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel
            boolean isSupported() {
                return JavaVersion.current() >= 1.7d;
            }
        },
        JAVA_8(8, 52, 1) { // from class: org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel.2
            @Override // org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel
            boolean isSupported() {
                return JavaVersion.current() >= 1.8d;
            }
        },
        JAVA_9(9, 53, 3) { // from class: org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel.3
            @Override // org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel
            boolean isSupported() {
                return JavaVersion.current() >= 9.0d;
            }
        },
        JAVA_10(10, 54, 3) { // from class: org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel.4
            @Override // org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel
            boolean isSupported() {
                return JavaVersion.current() >= 10.0d;
            }
        },
        JAVA_11(11, 55, 15) { // from class: org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel.5
            @Override // org.spongepowered.asm.mixin.MixinEnvironment.CompatibilityLevel
            boolean isSupported() {
                return JavaVersion.current() >= 11.0d;
            }
        };

        private final int ver;
        private final int classVersion;
        private final int languageFeatures;
        private CompatibilityLevel maxCompatibleLevel;
        public static CompatibilityLevel DEFAULT = JAVA_6;

        /* loaded from: L-out.jar:org/spongepowered/asm/mixin/MixinEnvironment$CompatibilityLevel$LanguageFeature.class */
        public static class LanguageFeature {
            public static final int METHODS_IN_INTERFACES = 1;
            public static final int PRIVATE_METHODS_IN_INTERFACES = 2;
            public static final int NESTING = 4;
            public static final int DYNAMIC_CONSTANTS = 8;
        }

        CompatibilityLevel(int ver, int classVersion, int languageFeatures) {
            this.ver = ver;
            this.classVersion = classVersion;
            this.languageFeatures = languageFeatures;
        }

        boolean isSupported() {
            return true;
        }

        public int classVersion() {
            return this.classVersion;
        }

        @Deprecated
        public boolean supportsMethodsInInterfaces() {
            return (this.languageFeatures & 1) != 0;
        }

        public boolean supports(int languageFeature) {
            return (this.languageFeatures & languageFeature) != 0;
        }

        public boolean isAtLeast(CompatibilityLevel level) {
            return level == null || this.ver >= level.ver;
        }

        public boolean isLessThan(CompatibilityLevel level) {
            return level == null || this.ver < level.ver;
        }

        public boolean canElevateTo(CompatibilityLevel level) {
            return level == null || this.maxCompatibleLevel == null || level.ver <= this.maxCompatibleLevel.ver;
        }

        public boolean canSupport(CompatibilityLevel level) {
            if (level == null) {
                return true;
            }
            return level.canElevateTo(this);
        }

        static String getSupportedVersions() {
            StringBuilder sb = new StringBuilder();
            boolean comma = false;
            for (CompatibilityLevel level : values()) {
                if (level.isSupported()) {
                    if (comma) {
                        sb.append(", ");
                    }
                    sb.append(level.ver);
                    comma = true;
                }
            }
            return sb.toString();
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/MixinEnvironment$TokenProviderWrapper.class */
    static class TokenProviderWrapper implements Comparable {
        private static int nextOrder = 0;
        private final int priority;
        private final int order;
        private final IEnvironmentTokenProvider provider;
        private final MixinEnvironment environment;

        @Override // java.lang.Comparable
        public int compareTo(Object obj) {
            return compareTo((TokenProviderWrapper) obj);
        }

        public TokenProviderWrapper(IEnvironmentTokenProvider iEnvironmentTokenProvider, MixinEnvironment mixinEnvironment) {
            this.provider = iEnvironmentTokenProvider;
            this.environment = mixinEnvironment;
            int i = nextOrder;
            nextOrder = i + 1;
            this.order = i;
            this.priority = iEnvironmentTokenProvider.getPriority();
        }

        public int compareTo(TokenProviderWrapper tokenProviderWrapper) {
            if (tokenProviderWrapper == null) {
                return 0;
            }
            if (tokenProviderWrapper.priority == this.priority) {
                return tokenProviderWrapper.order - this.order;
            }
            return tokenProviderWrapper.priority - this.priority;
        }

        public IEnvironmentTokenProvider getProvider() {
            return this.provider;
        }

        Integer getToken(String str) {
            return this.provider.getToken(str, this.environment);
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/MixinEnvironment$PhaseConsumer.class */
    static class PhaseConsumer implements IConsumer {
        PhaseConsumer() {
        }

        @Override // org.spongepowered.asm.util.IConsumer
        public void accept(Object obj) {
            accept((Phase) obj);
        }

        public void accept(Phase phase) {
            MixinEnvironment.gotoPhase(phase);
        }
    }

    MixinEnvironment(Phase phase) {
        this.phase = phase;
        this.configsKey = GlobalProperties.Keys.m49of(GlobalProperties.Keys.CONFIGS + "." + this.phase.name.toLowerCase(Locale.ROOT));
        Object version = getVersion();
        if (version == null || !MixinBootstrap.VERSION.equals(version)) {
            throw new MixinException("Environment conflict, mismatched versions or you didn't call MixinBootstrap.init()");
        }
        this.service.checkEnv(this);
        this.options = new boolean[Option.values().length];
        for (Option option : Option.values()) {
            this.options[option.ordinal()] = option.getBooleanValue();
        }
        if (showHeader) {
            showHeader = false;
            printHeader(version);
        }
    }

    private void printHeader(Object version) {
        Object codeSource = getCodeSource();
        Object serviceName = this.service.getName();
        Side side = getSide();
        logger.info("SpongePowered MIXIN Subsystem Version={} Source={} Service={} Env={}", new Object[]{version, codeSource, serviceName, side});
        boolean verbose = getOption(Option.DEBUG_VERBOSE);
        if (verbose || getOption(Option.DEBUG_EXPORT) || getOption(Option.DEBUG_PROFILER)) {
            PrettyPrinter printer = new PrettyPrinter(32);
            Object[] objArr = new Object[1];
            objArr[0] = verbose ? " (Verbose debugging enabled)" : "";
            printer.add("SpongePowered MIXIN%s", objArr).centre().m76hr();
            printer.m75kv("Code source", codeSource);
            printer.m75kv("Internal Version", version);
            printer.m75kv("Java Versions Supported", CompatibilityLevel.getSupportedVersions());
            printer.m75kv("Current Compatibility Level", getCompatibilityLevel());
            printer.m75kv("Detected ASM API Version", ASM.getApiVersionString()).m76hr();
            printer.m75kv("Service Name", serviceName);
            printer.m75kv("Mixin Service Class", this.service.getClass().getName());
            printer.m75kv("Global Property Service Class", MixinService.getGlobalPropertyService().getClass().getName()).m76hr();
            for (Option option : Option.values()) {
                StringBuilder indent = new StringBuilder();
                for (int i = 0; i < option.depth; i++) {
                    indent.append("- ");
                }
                printer.m74kv(option.property, "%s<%s>", new Object[]{indent, option});
            }
            printer.m76hr().m75kv("Detected Side", side);
            printer.print(System.err);
        }
    }

    private String getCodeSource() {
        try {
            return getClass().getProtectionDomain().getCodeSource().getLocation().toString();
        } catch (Throwable th) {
            return "Unknown";
        }
    }

    private Level getVerboseLoggingLevel() {
        return getOption(Option.DEBUG_VERBOSE) ? Level.INFO : Level.DEBUG;
    }

    public Phase getPhase() {
        return this.phase;
    }

    @Deprecated
    public List<String> getMixinConfigs() {
        List<String> mixinConfigs = (List) GlobalProperties.get(this.configsKey);
        if (mixinConfigs == null) {
            mixinConfigs = new ArrayList();
            GlobalProperties.put(this.configsKey, mixinConfigs);
        }
        return mixinConfigs;
    }

    @Deprecated
    public MixinEnvironment addConfiguration(String config) {
        logger.warn("MixinEnvironment::addConfiguration is deprecated and will be removed. Use Mixins::addConfiguration instead!");
        Mixins.addConfiguration(config, this);
        return this;
    }

    void registerConfig(String config) {
        List<String> configs = getMixinConfigs();
        if (!configs.contains(config)) {
            configs.add(config);
        }
    }

    public MixinEnvironment registerTokenProviderClass(String providerName) {
        if (!this.tokenProviderClasses.contains(providerName)) {
            try {
                Class<? extends IEnvironmentTokenProvider> providerClass = this.service.getClassProvider().findClass(providerName, true);
                IEnvironmentTokenProvider provider = (IEnvironmentTokenProvider) providerClass.newInstance();
                registerTokenProvider(provider);
            } catch (Throwable th) {
                logger.error("Error instantiating " + providerName, th);
            }
        }
        return this;
    }

    public MixinEnvironment registerTokenProvider(IEnvironmentTokenProvider provider) {
        if (provider != null && !this.tokenProviderClasses.contains(provider.getClass().getName())) {
            String providerName = provider.getClass().getName();
            TokenProviderWrapper wrapper = new TokenProviderWrapper(provider, this);
            logger.log(getVerboseLoggingLevel(), "Adding new token provider {} to {}", new Object[]{providerName, this});
            this.tokenProviders.add(wrapper);
            this.tokenProviderClasses.add(providerName);
            Collections.sort(this.tokenProviders);
        }
        return this;
    }

    @Override // org.spongepowered.asm.util.ITokenProvider
    public Integer getToken(String token) {
        String token2 = token.toUpperCase(Locale.ROOT);
        for (TokenProviderWrapper provider : this.tokenProviders) {
            Integer value = provider.getToken(token2);
            if (value != null) {
                return value;
            }
        }
        return this.internalTokens.get(token2);
    }

    @Deprecated
    public Set<String> getErrorHandlerClasses() {
        return Mixins.getErrorHandlerClasses();
    }

    public Object getActiveTransformer() {
        return transformer;
    }

    public void setActiveTransformer(IMixinTransformer transformer2) {
        if (transformer2 != null) {
            transformer = transformer2;
        }
    }

    public MixinEnvironment setSide(Side side) {
        if (side != null && getSide() == Side.UNKNOWN && side != Side.UNKNOWN) {
            this.side = side;
        }
        return this;
    }

    public Side getSide() {
        if (this.side == null) {
            Side[] sideArrValues = Side.values();
            int length = sideArrValues.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Side side = sideArrValues[i];
                if (!side.detect()) {
                    i++;
                } else {
                    this.side = side;
                    break;
                }
            }
        }
        return this.side != null ? this.side : Side.UNKNOWN;
    }

    public String getVersion() {
        return (String) GlobalProperties.get(GlobalProperties.Keys.INIT);
    }

    public boolean getOption(Option option) {
        return this.options[option.ordinal()];
    }

    public void setOption(Option option, boolean value) {
        this.options[option.ordinal()] = value;
    }

    public String getOptionValue(Option option) {
        return option.getStringValue();
    }

    public <E extends Enum<E>> E getOption(Option option, E e) {
        return (E) option.getEnumValue(e);
    }

    public void setObfuscationContext(String context) {
        this.obfuscationContext = context;
    }

    public String getObfuscationContext() {
        return this.obfuscationContext;
    }

    public String getRefmapObfuscationContext() {
        String overrideObfuscationType = Option.OBFUSCATION_TYPE.getStringValue();
        if (overrideObfuscationType != null) {
            return overrideObfuscationType;
        }
        return this.obfuscationContext;
    }

    public RemapperChain getRemappers() {
        return this.remappers;
    }

    public void audit() {
        Object activeTransformer = getActiveTransformer();
        if (activeTransformer instanceof IMixinTransformer) {
            ((IMixinTransformer) activeTransformer).audit(this);
        }
    }

    @Deprecated
    public List<ITransformer> getTransformers() {
        logger.warn("MixinEnvironment::getTransformers is deprecated!");
        ITransformerProvider transformers = this.service.getTransformerProvider();
        return transformers != null ? (List) transformers.getTransformers() : Collections.emptyList();
    }

    @Deprecated
    public void addTransformerExclusion(String name) {
        logger.warn("MixinEnvironment::addTransformerExclusion is deprecated!");
        ITransformerProvider transformers = this.service.getTransformerProvider();
        if (transformers != null) {
            transformers.addTransformerExclusion(name);
        }
    }

    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(), this.phase);
    }

    private static Phase getCurrentPhase() {
        if (currentPhase == Phase.NOT_INITIALISED) {
            init(Phase.PREINIT);
        }
        return currentPhase;
    }

    public static void init(Phase phase) {
        if (currentPhase == Phase.NOT_INITIALISED) {
            currentPhase = phase;
            MixinEnvironment env = getEnvironment(phase);
            getProfiler().setActive(env.getOption(Option.DEBUG_PROFILER));
            IMixinService service = MixinService.getService();
            if (service instanceof MixinServiceAbstract) {
                ((MixinServiceAbstract) service).wire(phase, new PhaseConsumer());
            }
        }
    }

    public static MixinEnvironment getEnvironment(Phase phase) {
        if (phase == null) {
            return Phase.DEFAULT.getEnvironment();
        }
        return phase.getEnvironment();
    }

    public static MixinEnvironment getDefaultEnvironment() {
        return getEnvironment(Phase.DEFAULT);
    }

    public static MixinEnvironment getCurrentEnvironment() {
        if (currentEnvironment == null) {
            currentEnvironment = getEnvironment(getCurrentPhase());
        }
        return currentEnvironment;
    }

    public static CompatibilityLevel getCompatibilityLevel() {
        if (compatibility == null) {
            CompatibilityLevel minLevel = getMinCompatibilityLevel();
            CompatibilityLevel optionLevel = (CompatibilityLevel) Option.DEFAULT_COMPATIBILITY_LEVEL.getEnumValue(minLevel);
            compatibility = optionLevel.isAtLeast(minLevel) ? optionLevel : minLevel;
        }
        return compatibility;
    }

    private static CompatibilityLevel getMinCompatibilityLevel() {
        CompatibilityLevel minLevel = MixinService.getService().getMinCompatibilityLevel();
        return minLevel == null ? CompatibilityLevel.DEFAULT : minLevel;
    }

    @Deprecated
    public static void setCompatibilityLevel(CompatibilityLevel level) throws IllegalArgumentException {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (!"org.spongepowered.asm.mixin.transformer.MixinConfig".equals(stackTrace[2].getClassName())) {
            logger.warn("MixinEnvironment::setCompatibilityLevel is deprecated and will be removed. Set level via config instead!");
        }
        CompatibilityLevel currentLevel = getCompatibilityLevel();
        if (level != currentLevel && level.isAtLeast(currentLevel)) {
            if (!level.isSupported()) {
                throw new IllegalArgumentException("The requested compatibility level " + level + " could not be set. Level is not supported");
            }
            IMixinService service = MixinService.getService();
            CompatibilityLevel maxLevel = service.getMaxCompatibilityLevel();
            if (maxLevel != null && maxLevel.isLessThan(level)) {
                logger.warn("The requested compatibility level {} is higher than the level supported by the active subsystem '{}' which supports {}. This is not a supported configuration and instability may occur.", new Object[]{level, service.getName(), maxLevel});
            }
            compatibility = level;
            logger.info("Compatibility level set to {}", new Object[]{level});
        }
    }

    public static Profiler getProfiler() {
        return profiler;
    }

    static void gotoPhase(Phase phase) {
        if (phase == null || phase.ordinal < 0) {
            throw new IllegalArgumentException("Cannot go to the specified phase, phase is null or invalid");
        }
        IMixinService service = MixinService.getService();
        if (phase.ordinal > getCurrentPhase().ordinal) {
            service.beginPhase();
        }
        currentPhase = phase;
        currentEnvironment = getEnvironment(getCurrentPhase());
        if ((service instanceof MixinServiceAbstract) && phase == Phase.DEFAULT) {
            ((MixinServiceAbstract) service).unwire();
        }
    }
}
