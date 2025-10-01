package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.spongepowered.asm.launch.MixinInitialisationError;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
import org.spongepowered.asm.mixin.refmap.RemappingReferenceMapper;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.VersionNumber;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinConfig.class */
final class MixinConfig implements Comparable, IMixinConfig {
    private static int configOrder = 0;
    private static final Set globalMixinList = new HashSet();
    private Config handle;
    private MixinConfig parent;

    @SerializedName("parent")
    private String parentName;

    @SerializedName("target")
    private String selector;

    @SerializedName("minVersion")
    private String version;

    @SerializedName("compatibilityLevel")
    private String compatibility;

    @SerializedName("required")
    private Boolean requiredValue;
    private boolean required;

    @SerializedName("package")
    private String mixinPackage;

    @SerializedName("mixins")
    private List mixinClasses;

    @SerializedName("client")
    private List mixinClassesClient;

    @SerializedName("server")
    private List mixinClassesServer;

    @SerializedName("refmap")
    private String refMapperConfig;

    @SerializedName("verbose")
    private boolean verboseLogging;
    private final int order;
    private final List listeners;
    private IMixinService service;
    private MixinEnvironment env;
    private String name;

    @SerializedName("plugin")
    private String pluginClassName;

    @SerializedName("injectors")
    private InjectorOptions injectorOptions;

    @SerializedName("overwrites")
    private OverwriteOptions overwriteOptions;
    private PluginHandle plugin;
    private IReferenceMapper refMapper;
    private boolean initialised;
    private boolean prepared;
    private boolean visited;
    private final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private final Map mixinMapping = new HashMap();
    private final Set unhandledTargets = new HashSet();
    private final List pendingMixins = new ArrayList();
    private final List mixins = new ArrayList();

    @SerializedName("priority")
    private int priority = -1;

    @SerializedName("mixinPriority")
    private int mixinPriority = -1;

    @SerializedName("setSourceFile")
    private boolean setSourceFile = false;

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinConfig$IListener.class */
    interface IListener {
        void onPrepare(MixinInfo mixinInfo);

        void onInit(MixinInfo mixinInfo);
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return compareTo((MixinConfig) obj);
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinConfig$InjectorOptions.class */
    static class InjectorOptions {

        @SerializedName("injectionPoints")
        List injectionPoints;

        @SerializedName("defaultRequire")
        int defaultRequireValue = 0;

        @SerializedName("defaultGroup")
        String defaultGroup = "default";

        @SerializedName("maxShiftBy")
        int maxShiftBy = 0;

        InjectorOptions() {
        }

        void mergeFrom(InjectorOptions injectorOptions) {
            if (this.defaultRequireValue == 0) {
                this.defaultRequireValue = injectorOptions.defaultRequireValue;
            }
            if ("default".equals(this.defaultGroup)) {
                this.defaultGroup = injectorOptions.defaultGroup;
            }
            if (this.maxShiftBy == 0) {
                this.maxShiftBy = injectorOptions.maxShiftBy;
            }
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinConfig$OverwriteOptions.class */
    static class OverwriteOptions {

        @SerializedName("conformVisibility")
        boolean conformAccessModifiers;

        @SerializedName("requireAnnotations")
        boolean requireOverwriteAnnotations;

        OverwriteOptions() {
        }

        void mergeFrom(OverwriteOptions overwriteOptions) {
            this.conformAccessModifiers |= overwriteOptions.conformAccessModifiers;
            this.requireOverwriteAnnotations |= overwriteOptions.requireOverwriteAnnotations;
        }
    }

    private MixinConfig() {
        int i = configOrder;
        configOrder = i + 1;
        this.order = i;
        this.listeners = new ArrayList();
        this.initialised = false;
        this.prepared = false;
        this.visited = false;
    }

    private boolean onLoad(IMixinService iMixinService, String str, MixinEnvironment mixinEnvironment) {
        this.service = iMixinService;
        this.name = str;
        if (!Strings.isNullOrEmpty(this.parentName)) {
            return true;
        }
        this.env = parseSelector(this.selector, mixinEnvironment);
        this.required = (this.requiredValue == null || !this.requiredValue.booleanValue() || this.env.getOption(MixinEnvironment.Option.IGNORE_REQUIRED)) ? false : true;
        initPriority(1000, 1000);
        if (this.injectorOptions == null) {
            this.injectorOptions = new InjectorOptions();
        }
        if (this.overwriteOptions == null) {
            this.overwriteOptions = new OverwriteOptions();
        }
        return postInit();
    }

    String getParentName() {
        return this.parentName;
    }

    boolean assignParent(Config config) {
        boolean z;
        if (this.parent != null) {
            throw new MixinInitialisationError("Mixin config " + this.name + " was already initialised");
        }
        if (config.get() == this) {
            throw new MixinInitialisationError("Mixin config " + this.name + " cannot be its own parent");
        }
        this.parent = config.get();
        if (!this.parent.initialised) {
            throw new MixinInitialisationError("Mixin config " + this.name + " attempted to assign uninitialised parent config. This probably means that there is an indirect loop in the mixin configs: child -> parent -> child");
        }
        this.env = parseSelector(this.selector, this.parent.env);
        if (this.requiredValue == null) {
            z = this.parent.required;
        } else {
            z = this.requiredValue.booleanValue() && !this.env.getOption(MixinEnvironment.Option.IGNORE_REQUIRED);
        }
        this.required = z;
        initPriority(this.parent.priority, this.parent.mixinPriority);
        if (this.injectorOptions == null) {
            this.injectorOptions = this.parent.injectorOptions;
        } else {
            this.injectorOptions.mergeFrom(this.parent.injectorOptions);
        }
        if (this.overwriteOptions == null) {
            this.overwriteOptions = this.parent.overwriteOptions;
        } else {
            this.overwriteOptions.mergeFrom(this.parent.overwriteOptions);
        }
        this.setSourceFile |= this.parent.setSourceFile;
        this.verboseLogging |= this.parent.verboseLogging;
        return postInit();
    }

    private void initPriority(int i, int i2) {
        if (this.priority < 0) {
            this.priority = i;
        }
        if (this.mixinPriority < 0) {
            this.mixinPriority = i2;
        }
    }

    private boolean postInit() throws IllegalArgumentException {
        if (this.initialised) {
            throw new MixinInitialisationError("Mixin config " + this.name + " was already initialised.");
        }
        this.initialised = true;
        initCompatibilityLevel();
        initInjectionPoints();
        return checkVersion();
    }

    private void initCompatibilityLevel() throws IllegalArgumentException {
        MixinEnvironment.CompatibilityLevel compatibilityLevelValueOf;
        MixinEnvironment.CompatibilityLevel compatibilityLevel;
        if (this.compatibility == null || (compatibilityLevelValueOf = MixinEnvironment.CompatibilityLevel.valueOf(this.compatibility.trim().toUpperCase(Locale.ROOT))) == (compatibilityLevel = MixinEnvironment.getCompatibilityLevel())) {
            return;
        }
        if (compatibilityLevel.isAtLeast(compatibilityLevelValueOf) && !compatibilityLevel.canSupport(compatibilityLevelValueOf)) {
            throw new MixinInitialisationError("Mixin config " + this.name + " requires compatibility level " + compatibilityLevelValueOf + " which is too old");
        }
        if (!compatibilityLevel.canElevateTo(compatibilityLevelValueOf)) {
            throw new MixinInitialisationError("Mixin config " + this.name + " requires compatibility level " + compatibilityLevelValueOf + " which is prohibited by " + compatibilityLevel);
        }
        MixinEnvironment.setCompatibilityLevel(compatibilityLevelValueOf);
    }

    private MixinEnvironment parseSelector(String str, MixinEnvironment mixinEnvironment) {
        if (str != null) {
            for (String str2 : str.split("[&\\| ]")) {
                Matcher matcher = Pattern.compile("^@env(?:ironment)?\\(([A-Z]+)\\)$").matcher(str2.trim());
                if (matcher.matches()) {
                    return MixinEnvironment.getEnvironment(MixinEnvironment.Phase.forName(matcher.group(1)));
                }
            }
            MixinEnvironment.Phase phaseForName = MixinEnvironment.Phase.forName(str);
            if (phaseForName != null) {
                return MixinEnvironment.getEnvironment(phaseForName);
            }
        }
        return mixinEnvironment;
    }

    private void initInjectionPoints() {
        if (this.injectorOptions.injectionPoints == null) {
            return;
        }
        Iterator it = this.injectorOptions.injectionPoints.iterator();
        while (it.hasNext()) {
            initInjectionPoint((String) it.next());
        }
    }

    public void initInjectionPoint(String str) {
        try {
            try {
                Class clsFindClass = this.service.getClassProvider().findClass(str, true);
                if (!InjectionPoint.class.isAssignableFrom(clsFindClass)) {
                    this.logger.error("Unable to register injection point {} for {}, class must extend InjectionPoint", new Object[]{str, this});
                    return;
                }
                try {
                    clsFindClass.getDeclaredMethod("find", String.class, InsnList.class, Collection.class);
                    InjectionPoint.register(clsFindClass);
                } catch (NoSuchMethodException e) {
                    this.logger.error("Unable to register injection point {} for {}, the class is not compatible with this version of Mixin", new Object[]{str, this, e});
                }
            } catch (ClassNotFoundException e2) {
                this.logger.error("Unable to register injection point {} for {}, the specified class was not found", new Object[]{str, this, e2});
            }
        } catch (Throwable th) {
            this.logger.catching(th);
        }
    }

    private boolean checkVersion() {
        if (this.version == null) {
            if (this.parent != null && this.parent.version != null) {
                return true;
            }
            this.logger.error("Mixin config {} does not specify \"minVersion\" property", new Object[]{this.name});
        }
        VersionNumber versionNumber = VersionNumber.parse(this.version);
        VersionNumber versionNumber2 = VersionNumber.parse(this.env.getVersion());
        if (versionNumber.compareTo(versionNumber2) > 0) {
            this.logger.warn("Mixin config {} requires mixin subsystem version {} but {} was found. The mixin config will not be applied.", new Object[]{this.name, versionNumber, versionNumber2});
            if (this.required) {
                throw new MixinInitialisationError("Required mixin config " + this.name + " requires mixin subsystem version " + versionNumber);
            }
            return false;
        }
        return true;
    }

    void addListener(IListener iListener) {
        this.listeners.add(iListener);
    }

    void onSelect() {
        this.plugin = new PluginHandle(this, this.service, this.pluginClassName);
        this.plugin.onLoad(this.mixinPackage);
        if (!this.mixinPackage.endsWith(".")) {
            this.mixinPackage += ".";
        }
        boolean z = false;
        if (this.refMapperConfig == null) {
            this.refMapperConfig = this.plugin.getRefMapperConfig();
            if (this.refMapperConfig == null) {
                z = true;
                this.refMapperConfig = ReferenceMapper.DEFAULT_RESOURCE;
            }
        }
        this.refMapper = ReferenceMapper.read(this.refMapperConfig);
        this.verboseLogging |= this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
        if (!z && this.refMapper.isDefault() && !this.env.getOption(MixinEnvironment.Option.DISABLE_REFMAP)) {
            this.logger.warn("Reference map '{}' for {} could not be read. If this is a development environment you can ignore this message", new Object[]{this.refMapperConfig, this});
        }
        if (this.env.getOption(MixinEnvironment.Option.REFMAP_REMAP)) {
            this.refMapper = RemappingReferenceMapper.m67of(this.env, this.refMapper);
        }
    }

    void prepare() {
        if (this.prepared) {
        }
        this.prepared = true;
        prepareMixins(this.mixinClasses, false);
        switch (C05681.$SwitchMap$org$spongepowered$asm$mixin$MixinEnvironment$Side[this.env.getSide().ordinal()]) {
            case 1:
                prepareMixins(this.mixinClassesClient, false);
                break;
            case 2:
                prepareMixins(this.mixinClassesServer, false);
                break;
            case 3:
            default:
                this.logger.warn("Mixin environment was unable to detect the current side, sided mixins will not be applied");
                break;
        }
    }

    /* renamed from: org.spongepowered.asm.mixin.transformer.MixinConfig$1 */
    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinConfig$1.class */
    static /* synthetic */ class C05681 {
        static final int[] $SwitchMap$org$spongepowered$asm$mixin$MixinEnvironment$Side = new int[MixinEnvironment.Side.values().length];

        static {
            try {
                $SwitchMap$org$spongepowered$asm$mixin$MixinEnvironment$Side[MixinEnvironment.Side.CLIENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$spongepowered$asm$mixin$MixinEnvironment$Side[MixinEnvironment.Side.SERVER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$spongepowered$asm$mixin$MixinEnvironment$Side[MixinEnvironment.Side.UNKNOWN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    void postInitialise() {
        if (this.plugin != null) {
            prepareMixins(this.plugin.getMixins(), true);
        }
        Iterator it = this.mixins.iterator();
        while (it.hasNext()) {
            MixinInfo mixinInfo = (MixinInfo) it.next();
            try {
                mixinInfo.validate();
                Iterator it2 = this.listeners.iterator();
                while (it2.hasNext()) {
                    ((IListener) it2.next()).onInit(mixinInfo);
                }
            } catch (InvalidMixinException e) {
                this.logger.error(e.getMixin() + ": " + e.getMessage(), e);
                removeMixin(mixinInfo);
                it.remove();
            } catch (Exception e2) {
                this.logger.error(e2.getMessage(), e2);
                removeMixin(mixinInfo);
                it.remove();
            }
        }
    }

    private void removeMixin(MixinInfo mixinInfo) {
        Iterator it = this.mixinMapping.values().iterator();
        while (it.hasNext()) {
            Iterator it2 = ((List) it.next()).iterator();
            while (it2.hasNext()) {
                if (mixinInfo == it2.next()) {
                    it2.remove();
                }
            }
        }
    }

    private void prepareMixins(List list, boolean z) {
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            String str2 = this.mixinPackage + str;
            if (str != null && !globalMixinList.contains(str2)) {
                MixinInfo mixinInfo = null;
                try {
                    List list2 = this.pendingMixins;
                    MixinInfo mixinInfo2 = new MixinInfo(this.service, this, str, this.plugin, z);
                    mixinInfo = mixinInfo2;
                    list2.add(mixinInfo2);
                    globalMixinList.add(str2);
                } catch (InvalidMixinException e) {
                    if (this.required) {
                        throw e;
                    }
                    this.logger.error(e.getMessage(), e);
                } catch (Exception e2) {
                    if (this.required) {
                        throw new InvalidMixinException(mixinInfo, "Error initialising mixin " + mixinInfo + " - " + e2.getClass() + ": " + e2.getMessage(), e2);
                    }
                    this.logger.error(e2.getMessage(), e2);
                }
            }
        }
        for (MixinInfo mixinInfo3 : this.pendingMixins) {
            try {
                mixinInfo3.parseTargets();
                if (mixinInfo3.getTargetClasses().size() > 0) {
                    Iterator it2 = mixinInfo3.getTargetClasses().iterator();
                    while (it2.hasNext()) {
                        String strReplace = ((String) it2.next()).replace('/', '.');
                        mixinsFor(strReplace).add(mixinInfo3);
                        this.unhandledTargets.add(strReplace);
                    }
                    Iterator it3 = this.listeners.iterator();
                    while (it3.hasNext()) {
                        ((IListener) it3.next()).onPrepare(mixinInfo3);
                    }
                    this.mixins.add(mixinInfo3);
                }
            } catch (InvalidMixinException e3) {
                if (this.required) {
                    throw e3;
                }
                this.logger.error(e3.getMessage(), e3);
            } catch (Exception e4) {
                if (this.required) {
                    throw new InvalidMixinException(mixinInfo3, "Error initialising mixin " + mixinInfo3 + " - " + e4.getClass() + ": " + e4.getMessage(), e4);
                }
                this.logger.error(e4.getMessage(), e4);
            }
        }
        this.pendingMixins.clear();
    }

    void postApply(String str, ClassNode classNode) {
        this.unhandledTargets.remove(str);
    }

    public Config getHandle() {
        if (this.handle == null) {
            this.handle = new Config(this);
        }
        return this.handle;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IMixinConfig
    public boolean isRequired() {
        return this.required;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IMixinConfig
    public MixinEnvironment getEnvironment() {
        return this.env;
    }

    MixinConfig getParent() {
        return this.parent;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IMixinConfig
    public String getName() {
        return this.name;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IMixinConfig
    public String getMixinPackage() {
        return this.mixinPackage;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IMixinConfig
    public int getPriority() {
        return this.priority;
    }

    public int getDefaultMixinPriority() {
        return this.mixinPriority;
    }

    public int getDefaultRequiredInjections() {
        return this.injectorOptions.defaultRequireValue;
    }

    public String getDefaultInjectorGroup() {
        String str = this.injectorOptions.defaultGroup;
        return (str == null || str.isEmpty()) ? "default" : str;
    }

    public boolean conformOverwriteVisibility() {
        return this.overwriteOptions.conformAccessModifiers;
    }

    public boolean requireOverwriteAnnotations() {
        return this.overwriteOptions.requireOverwriteAnnotations;
    }

    public int getMaxShiftByValue() {
        return Math.min(Math.max(this.injectorOptions.maxShiftBy, 0), 5);
    }

    public boolean select(MixinEnvironment mixinEnvironment) {
        this.visited = true;
        return this.env == mixinEnvironment;
    }

    boolean isVisited() {
        return this.visited;
    }

    int getDeclaredMixinCount() {
        return getCollectionSize(new Collection[]{this.mixinClasses, this.mixinClassesClient, this.mixinClassesServer});
    }

    int getMixinCount() {
        return this.mixins.size();
    }

    public List getClasses() {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (List list : new List[]{this.mixinClasses, this.mixinClassesClient, this.mixinClassesServer}) {
            if (list != null) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    builder.add(this.mixinPackage + ((String) it.next()));
                }
            }
        }
        return builder.build();
    }

    public boolean shouldSetSourceFile() {
        return this.setSourceFile;
    }

    public IReferenceMapper getReferenceMapper() {
        if (this.env.getOption(MixinEnvironment.Option.DISABLE_REFMAP)) {
            return ReferenceMapper.DEFAULT_MAPPER;
        }
        this.refMapper.setContext(this.env.getRefmapObfuscationContext());
        return this.refMapper;
    }

    String remapClassName(String str, String str2) {
        return getReferenceMapper().remap(str, str2);
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IMixinConfig
    public IMixinConfigPlugin getPlugin() {
        return this.plugin.get();
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IMixinConfig
    public Set getTargets() {
        return Collections.unmodifiableSet(this.mixinMapping.keySet());
    }

    public Set getUnhandledTargets() {
        return Collections.unmodifiableSet(this.unhandledTargets);
    }

    public Level getLoggingLevel() {
        return this.verboseLogging ? Level.INFO : Level.DEBUG;
    }

    public boolean packageMatch(String str) {
        return str.startsWith(this.mixinPackage);
    }

    public boolean hasMixinsFor(String str) {
        return this.mixinMapping.containsKey(str);
    }

    boolean hasPendingMixinsFor(String str) {
        if (packageMatch(str)) {
            return false;
        }
        Iterator it = this.pendingMixins.iterator();
        while (it.hasNext()) {
            if (((MixinInfo) it.next()).hasDeclaredTarget(str)) {
                return true;
            }
        }
        return false;
    }

    public List getMixinsFor(String str) {
        return mixinsFor(str);
    }

    private List mixinsFor(String str) {
        List arrayList = (List) this.mixinMapping.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.mixinMapping.put(str, arrayList);
        }
        return arrayList;
    }

    public List reloadMixin(String str, ClassNode classNode) {
        for (MixinInfo mixinInfo : this.mixins) {
            if (mixinInfo.getClassName().equals(str)) {
                mixinInfo.reloadMixin(classNode);
                return mixinInfo.getTargetClasses();
            }
        }
        return Collections.emptyList();
    }

    public String toString() {
        return this.name;
    }

    public int compareTo(MixinConfig mixinConfig) {
        if (mixinConfig == null) {
            return 0;
        }
        if (mixinConfig.priority == this.priority) {
            return this.order - mixinConfig.order;
        }
        return this.priority - mixinConfig.priority;
    }

    static Config create(String str, MixinEnvironment mixinEnvironment) {
        try {
            IMixinService service = MixinService.getService();
            InputStream resourceAsStream = service.getResourceAsStream(str);
            if (resourceAsStream == null) {
                throw new IllegalArgumentException(String.format("The specified resource '%s' was invalid or could not be read", str));
            }
            MixinConfig mixinConfig = (MixinConfig) new Gson().fromJson(new InputStreamReader(resourceAsStream), MixinConfig.class);
            if (mixinConfig.onLoad(service, str, mixinEnvironment)) {
                return mixinConfig.getHandle();
            }
            return null;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e2) {
            throw new IllegalArgumentException(String.format("The specified resource '%s' was invalid or could not be read", str), e2);
        }
    }

    private static int getCollectionSize(Collection[] collectionArr) {
        int size = 0;
        for (Collection collection : collectionArr) {
            if (collection != null) {
                size += collection.size();
            }
        }
        return size;
    }
}
