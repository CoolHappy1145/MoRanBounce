package org.spongepowered.tools.obfuscation;

import com.google.common.collect.ImmutableList;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.util.ITokenProvider;
import org.spongepowered.asm.util.VersionNumber;
import org.spongepowered.asm.util.logging.MessageRouter;
import org.spongepowered.tools.obfuscation.interfaces.IJavadocProvider;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.interfaces.ITypeHandleProvider;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandleSimulated;
import org.spongepowered.tools.obfuscation.mirror.TypeReference;
import org.spongepowered.tools.obfuscation.struct.InjectorRemap;
import org.spongepowered.tools.obfuscation.validation.ParentValidator;
import org.spongepowered.tools.obfuscation.validation.TargetValidator;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/AnnotatedMixins.class */
final class AnnotatedMixins implements IMixinAnnotationProcessor, ITokenProvider, ITypeHandleProvider, IJavadocProvider {
    private static final String MAPID_SYSTEM_PROPERTY = "mixin.target.mapid";
    private static final String RECOMMENDED_MIXINGRADLE_VERSION = "0.7";
    private static Map instances = new HashMap();
    private final IMixinAnnotationProcessor.CompilerEnvironment env;
    private final ProcessingEnvironment processingEnv;
    private final IObfuscationManager obf;
    private final List validators;
    private final TargetMap targets;
    private Properties properties;
    private final Map mixins = new HashMap();
    private final List mixinsForPass = new ArrayList();
    private final Map tokenCache = new HashMap();

    private AnnotatedMixins(ProcessingEnvironment processingEnvironment) {
        this.env = detectEnvironment(processingEnvironment);
        this.processingEnv = processingEnvironment;
        MessageRouter.setMessager(processingEnvironment.getMessager());
        String strCheckPluginVersion = checkPluginVersion(getOption(SupportedOptions.PLUGIN_VERSION));
        printMessage(Diagnostic.Kind.NOTE, "SpongePowered MIXIN Annotation Processor Version=0.8" + (strCheckPluginVersion != null ? String.format(" (MixinGradle Version=%s)", strCheckPluginVersion) : ""));
        this.targets = initTargetMap();
        this.obf = new ObfuscationManager(this);
        this.obf.init();
        this.validators = ImmutableList.of(new ParentValidator(this), new TargetValidator(this));
        initTokenCache(getOption(SupportedOptions.TOKENS));
    }

    private String checkPluginVersion(String str) {
        if (str == null) {
            return null;
        }
        VersionNumber versionNumber = VersionNumber.parse(str);
        VersionNumber versionNumber2 = VersionNumber.parse(RECOMMENDED_MIXINGRADLE_VERSION);
        if (versionNumber.compareTo(versionNumber2) < 0) {
            printMessage(Diagnostic.Kind.WARNING, String.format("MixinGradle version %s is out of date. Update to the recommended version %s", versionNumber, versionNumber2));
        }
        return versionNumber.toString();
    }

    protected TargetMap initTargetMap() {
        TargetMap targetMapCreate = TargetMap.create(System.getProperty(MAPID_SYSTEM_PROPERTY));
        System.setProperty(MAPID_SYSTEM_PROPERTY, targetMapCreate.getSessionId());
        String option = getOption(SupportedOptions.DEPENDENCY_TARGETS_FILE);
        if (option != null) {
            try {
                targetMapCreate.readImports(new File(option));
            } catch (IOException unused) {
                printMessage(Diagnostic.Kind.WARNING, "Could not read from specified imports file: " + option);
            }
        }
        return targetMapCreate;
    }

    private void initTokenCache(String str) {
        if (str != null) {
            Pattern patternCompile = Pattern.compile("^([A-Z0-9\\-_\\.]+)=([0-9]+)$");
            for (String str2 : str.replaceAll("\\s", "").toUpperCase(Locale.ROOT).split("[;,]")) {
                Matcher matcher = patternCompile.matcher(str2);
                if (matcher.matches()) {
                    this.tokenCache.put(matcher.group(1), Integer.valueOf(Integer.parseInt(matcher.group(2))));
                }
            }
        }
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor
    public IObfuscationManager getObfuscationManager() {
        return this.obf;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor
    public ProcessingEnvironment getProcessingEnvironment() {
        return this.processingEnv;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor
    public IMixinAnnotationProcessor.CompilerEnvironment getCompilerEnvironment() {
        return this.env;
    }

    @Override // org.spongepowered.asm.util.ITokenProvider
    public Integer getToken(String str) {
        if (this.tokenCache.containsKey(str)) {
            return (Integer) this.tokenCache.get(str);
        }
        Integer numValueOf = null;
        try {
            numValueOf = Integer.valueOf(Integer.parseInt(getOption(str)));
        } catch (Exception unused) {
        }
        this.tokenCache.put(str, numValueOf);
        return numValueOf;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IOptionProvider
    public String getOption(String str) {
        if (str == null) {
            return null;
        }
        String str2 = (String) this.processingEnv.getOptions().get(str);
        if (str2 != null) {
            return str2;
        }
        return getProperties().getProperty(str);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IOptionProvider
    public String getOption(String str, String str2) {
        String option = getOption(str);
        return option != null ? option : str2;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IOptionProvider
    public boolean getOption(String str, boolean z) {
        String option = getOption(str);
        return option != null ? Boolean.parseBoolean(option) : z;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IOptionProvider
    public List getOptions(String str) {
        ImmutableList.Builder builder = ImmutableList.builder();
        String option = getOption(str);
        if (option != null) {
            for (String str2 : option.split(",")) {
                builder.add(str2);
            }
        }
        return builder.build();
    }

    public Properties getProperties() throws IOException {
        if (this.properties == null) {
            this.properties = new Properties();
            try {
                FileObject resource = this.processingEnv.getFiler().getResource(StandardLocation.SOURCE_PATH, "", "mixin.properties");
                if (resource != null) {
                    InputStream inputStreamOpenInputStream = resource.openInputStream();
                    this.properties.load(inputStreamOpenInputStream);
                    inputStreamOpenInputStream.close();
                }
            } catch (Exception unused) {
            }
        }
        return this.properties;
    }

    private IMixinAnnotationProcessor.CompilerEnvironment detectEnvironment(ProcessingEnvironment processingEnvironment) {
        if (processingEnvironment.getClass().getName().contains("jdt")) {
            return IMixinAnnotationProcessor.CompilerEnvironment.JDT;
        }
        return IMixinAnnotationProcessor.CompilerEnvironment.JAVAC;
    }

    public void writeMappings() {
        this.obf.writeMappings();
    }

    public void writeReferences() {
        this.obf.writeReferences();
    }

    public void clear() {
        this.mixins.clear();
    }

    public void registerMixin(TypeElement typeElement) {
        String string = typeElement.getQualifiedName().toString();
        if (!this.mixins.containsKey(string)) {
            AnnotatedMixin annotatedMixin = new AnnotatedMixin(this, typeElement);
            this.targets.registerTargets(annotatedMixin);
            annotatedMixin.runValidators(IMixinValidator.ValidationPass.EARLY, this.validators);
            this.mixins.put(string, annotatedMixin);
            this.mixinsForPass.add(annotatedMixin);
        }
    }

    public AnnotatedMixin getMixin(TypeElement typeElement) {
        return getMixin(typeElement.getQualifiedName().toString());
    }

    public AnnotatedMixin getMixin(String str) {
        return (AnnotatedMixin) this.mixins.get(str);
    }

    public Collection getMixinsTargeting(TypeMirror typeMirror) {
        return getMixinsTargeting((TypeElement) ((DeclaredType) typeMirror).asElement());
    }

    public Collection getMixinsTargeting(TypeElement typeElement) {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.targets.getMixinsTargeting(typeElement).iterator();
        while (it.hasNext()) {
            TypeHandle handle = ((TypeReference) it.next()).getHandle(this.processingEnv);
            if (handle != null) {
                arrayList.add(handle.getType());
            }
        }
        return arrayList;
    }

    public void registerAccessor(TypeElement typeElement, ExecutableElement executableElement) {
        AnnotatedMixin mixin = getMixin(typeElement);
        if (mixin == null) {
            printMessage(Diagnostic.Kind.ERROR, "Found @Accessor annotation on a non-mixin method", executableElement);
        } else {
            AnnotationHandle annotationHandleM82of = AnnotationHandle.m82of(executableElement, Accessor.class);
            mixin.registerAccessor(executableElement, annotationHandleM82of, shouldRemap(mixin, annotationHandleM82of));
        }
    }

    public void registerInvoker(TypeElement typeElement, ExecutableElement executableElement) {
        AnnotatedMixin mixin = getMixin(typeElement);
        if (mixin == null) {
            printMessage(Diagnostic.Kind.ERROR, "Found @Accessor annotation on a non-mixin method", executableElement);
        } else {
            AnnotationHandle annotationHandleM82of = AnnotationHandle.m82of(executableElement, Invoker.class);
            mixin.registerInvoker(executableElement, annotationHandleM82of, shouldRemap(mixin, annotationHandleM82of));
        }
    }

    public void registerOverwrite(TypeElement typeElement, ExecutableElement executableElement) {
        AnnotatedMixin mixin = getMixin(typeElement);
        if (mixin == null) {
            printMessage(Diagnostic.Kind.ERROR, "Found @Overwrite annotation on a non-mixin method", executableElement);
        } else {
            AnnotationHandle annotationHandleM82of = AnnotationHandle.m82of(executableElement, Overwrite.class);
            mixin.registerOverwrite(executableElement, annotationHandleM82of, shouldRemap(mixin, annotationHandleM82of));
        }
    }

    public void registerShadow(TypeElement typeElement, VariableElement variableElement, AnnotationHandle annotationHandle) {
        AnnotatedMixin mixin = getMixin(typeElement);
        if (mixin == null) {
            printMessage(Diagnostic.Kind.ERROR, "Found @Shadow annotation on a non-mixin field", variableElement);
        } else {
            mixin.registerShadow(variableElement, annotationHandle, shouldRemap(mixin, annotationHandle));
        }
    }

    public void registerShadow(TypeElement typeElement, ExecutableElement executableElement, AnnotationHandle annotationHandle) {
        AnnotatedMixin mixin = getMixin(typeElement);
        if (mixin == null) {
            printMessage(Diagnostic.Kind.ERROR, "Found @Shadow annotation on a non-mixin method", executableElement);
        } else {
            mixin.registerShadow(executableElement, annotationHandle, shouldRemap(mixin, annotationHandle));
        }
    }

    public void registerInjector(TypeElement typeElement, ExecutableElement executableElement, AnnotationHandle annotationHandle) {
        AnnotatedMixin mixin = getMixin(typeElement);
        if (mixin == null) {
            printMessage(Diagnostic.Kind.ERROR, "Found " + annotationHandle + " annotation on a non-mixin method", executableElement);
            return;
        }
        InjectorRemap injectorRemap = new InjectorRemap(shouldRemap(mixin, annotationHandle));
        mixin.registerInjector(executableElement, annotationHandle, injectorRemap);
        injectorRemap.dispatchPendingMessages(this);
    }

    public void registerSoftImplements(TypeElement typeElement, AnnotationHandle annotationHandle) {
        AnnotatedMixin mixin = getMixin(typeElement);
        if (mixin == null) {
            printMessage(Diagnostic.Kind.ERROR, "Found @Implements annotation on a non-mixin class");
        } else {
            mixin.registerSoftImplements(annotationHandle);
        }
    }

    public void onPassStarted() {
        this.mixinsForPass.clear();
    }

    public void onPassCompleted(RoundEnvironment roundEnvironment) {
        if (!"true".equalsIgnoreCase(getOption(SupportedOptions.DISABLE_TARGET_EXPORT))) {
            this.targets.write(true);
        }
        Iterator it = (roundEnvironment.processingOver() ? this.mixins.values() : this.mixinsForPass).iterator();
        while (it.hasNext()) {
            ((AnnotatedMixin) it.next()).runValidators(roundEnvironment.processingOver() ? IMixinValidator.ValidationPass.FINAL : IMixinValidator.ValidationPass.LATE, this.validators);
        }
    }

    private static boolean shouldRemap(AnnotatedMixin annotatedMixin, AnnotationHandle annotationHandle) {
        return annotationHandle.getBoolean("remap", annotatedMixin.remap());
    }

    private static boolean shouldSuppress(Element element, SuppressedBy suppressedBy) {
        if (element == null || suppressedBy == null) {
            return false;
        }
        if (AnnotationHandle.m82of(element, SuppressWarnings.class).getList().contains(suppressedBy.getToken())) {
            return true;
        }
        return shouldSuppress(element.getEnclosingElement(), suppressedBy);
    }

    public void printMessage(Diagnostic.Kind kind, CharSequence charSequence) {
        if (this.env == IMixinAnnotationProcessor.CompilerEnvironment.JAVAC || kind != Diagnostic.Kind.NOTE) {
            this.processingEnv.getMessager().printMessage(kind, charSequence);
        }
    }

    public void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element) {
        this.processingEnv.getMessager().printMessage(kind, charSequence, element);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IMessagerSuppressible
    public void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element, SuppressedBy suppressedBy) {
        if (kind != Diagnostic.Kind.WARNING || !shouldSuppress(element, suppressedBy)) {
            this.processingEnv.getMessager().printMessage(kind, charSequence, element);
        }
    }

    public void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element, AnnotationMirror annotationMirror) {
        this.processingEnv.getMessager().printMessage(kind, charSequence, element, annotationMirror);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IMessagerSuppressible
    public void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element, AnnotationMirror annotationMirror, SuppressedBy suppressedBy) {
        if (kind != Diagnostic.Kind.WARNING || !shouldSuppress(element, suppressedBy)) {
            this.processingEnv.getMessager().printMessage(kind, charSequence, element, annotationMirror);
        }
    }

    public void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue) {
        this.processingEnv.getMessager().printMessage(kind, charSequence, element, annotationMirror, annotationValue);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IMessagerSuppressible
    public void printMessage(Diagnostic.Kind kind, CharSequence charSequence, Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue, SuppressedBy suppressedBy) {
        if (kind != Diagnostic.Kind.WARNING || !shouldSuppress(element, suppressedBy)) {
            this.processingEnv.getMessager().printMessage(kind, charSequence, element, annotationMirror, annotationValue);
        }
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.ITypeHandleProvider
    public TypeHandle getTypeHandle(String str) {
        PackageElement packageElement;
        String strReplace = str.replace('/', '.');
        Elements elementUtils = this.processingEnv.getElementUtils();
        TypeElement typeElement = elementUtils.getTypeElement(strReplace);
        if (typeElement != null) {
            try {
                return new TypeHandle(typeElement);
            } catch (NullPointerException unused) {
            }
        }
        int iLastIndexOf = strReplace.lastIndexOf(46);
        if (iLastIndexOf > -1 && (packageElement = elementUtils.getPackageElement(strReplace.substring(0, iLastIndexOf))) != null) {
            return new TypeHandle(packageElement, strReplace);
        }
        return null;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.ITypeHandleProvider
    public TypeHandle getSimulatedHandle(String str, TypeMirror typeMirror) {
        String strReplace = str.replace('/', '.');
        int iLastIndexOf = strReplace.lastIndexOf(46);
        if (iLastIndexOf > -1) {
            PackageElement packageElement = this.processingEnv.getElementUtils().getPackageElement(strReplace.substring(0, iLastIndexOf));
            if (packageElement != null) {
                return new TypeHandleSimulated(packageElement, strReplace, typeMirror);
            }
        }
        return new TypeHandleSimulated(strReplace, typeMirror);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IJavadocProvider
    public String getJavadoc(Element element) {
        return this.processingEnv.getElementUtils().getDocComment(element);
    }

    public static AnnotatedMixins getMixinsForEnvironment(ProcessingEnvironment processingEnvironment) {
        AnnotatedMixins annotatedMixins = (AnnotatedMixins) instances.get(processingEnvironment);
        if (annotatedMixins == null) {
            annotatedMixins = new AnnotatedMixins(processingEnvironment);
            instances.put(processingEnvironment, annotatedMixins);
        }
        return annotatedMixins;
    }
}
