package org.spongepowered.tools.obfuscation;

import com.google.common.collect.ImmutableList;
import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IOptionProvider;
import org.spongepowered.tools.obfuscation.service.ObfuscationTypeDescriptor;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/ObfuscationType.class */
public final class ObfuscationType {
    public static final String DEFAULT_TYPE = "searge";
    private static final Map types = new LinkedHashMap();
    private final String key;
    private final ObfuscationTypeDescriptor descriptor;

    /* renamed from: ap */
    private final IMixinAnnotationProcessor f234ap;
    private final IOptionProvider options;

    private ObfuscationType(ObfuscationTypeDescriptor obfuscationTypeDescriptor, IMixinAnnotationProcessor iMixinAnnotationProcessor) {
        this.key = obfuscationTypeDescriptor.getKey();
        this.descriptor = obfuscationTypeDescriptor;
        this.f234ap = iMixinAnnotationProcessor;
        this.options = iMixinAnnotationProcessor;
    }

    public final ObfuscationEnvironment createEnvironment() throws NoSuchMethodException, SecurityException {
        try {
            Constructor declaredConstructor = this.descriptor.getEnvironmentType().getDeclaredConstructor(ObfuscationType.class);
            declaredConstructor.setAccessible(true);
            return (ObfuscationEnvironment) declaredConstructor.newInstance(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return this.key;
    }

    public String getKey() {
        return this.key;
    }

    public ObfuscationTypeDescriptor getConfig() {
        return this.descriptor;
    }

    public IMixinAnnotationProcessor getAnnotationProcessor() {
        return this.f234ap;
    }

    public boolean isDefault() {
        return this.key.equals(this.options.getOption(SupportedOptions.DEFAULT_OBFUSCATION_ENV, "searge").toLowerCase(Locale.ROOT));
    }

    public boolean isSupported() {
        return getInputFileNames().size() > 0;
    }

    public List getInputFileNames() {
        ImmutableList.Builder builder = ImmutableList.builder();
        String option = this.options.getOption(this.descriptor.getInputFileOption());
        if (option != null) {
            builder.add(option);
        }
        String option2 = this.options.getOption(this.descriptor.getExtraInputFilesOption());
        if (option2 != null) {
            for (String str : option2.split(";")) {
                builder.add(str.trim());
            }
        }
        return builder.build();
    }

    public String getOutputFileName() {
        return this.options.getOption(this.descriptor.getOutputFileOption());
    }

    public static Iterable types() {
        return types.values();
    }

    public static ObfuscationType create(ObfuscationTypeDescriptor obfuscationTypeDescriptor, IMixinAnnotationProcessor iMixinAnnotationProcessor) {
        String key = obfuscationTypeDescriptor.getKey();
        if (types.containsKey(key)) {
            throw new IllegalArgumentException("Obfuscation type with key " + key + " was already registered");
        }
        ObfuscationType obfuscationType = new ObfuscationType(obfuscationTypeDescriptor, iMixinAnnotationProcessor);
        types.put(key, obfuscationType);
        return obfuscationType;
    }

    public static ObfuscationType get(String str) {
        ObfuscationType obfuscationType = (ObfuscationType) types.get(str);
        if (obfuscationType == null) {
            throw new IllegalArgumentException("Obfuscation type with key " + str + " was not registered");
        }
        return obfuscationType;
    }
}
