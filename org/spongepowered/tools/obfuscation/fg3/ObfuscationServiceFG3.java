package org.spongepowered.tools.obfuscation.fg3;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Set;
import org.spongepowered.tools.obfuscation.SupportedOptions;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.service.IObfuscationService;
import org.spongepowered.tools.obfuscation.service.ObfuscationTypeDescriptor;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/fg3/ObfuscationServiceFG3.class */
public class ObfuscationServiceFG3 implements IObfuscationService {
    public static final String SEARGE = "searge";
    public static final String REOBF_TSRG_FILE = "reobfTsrgFile";
    public static final String REOBF_EXTRA_TSRG_FILES = "reobfTsrgFiles";
    public static final String OUT_TSRG_SRG_FILE = "outTsrgFile";
    public static final String TSRG_OUTPUT_BEHAVIOUR = "mergeBehaviour";

    @Override // org.spongepowered.tools.obfuscation.service.IObfuscationService
    public Set getSupportedOptions() {
        return ImmutableSet.of(REOBF_TSRG_FILE, OUT_TSRG_SRG_FILE, TSRG_OUTPUT_BEHAVIOUR);
    }

    @Override // org.spongepowered.tools.obfuscation.service.IObfuscationService
    public Collection getObfuscationTypes(IMixinAnnotationProcessor iMixinAnnotationProcessor) {
        ImmutableList.Builder builder = ImmutableList.builder();
        if (iMixinAnnotationProcessor.getOptions(SupportedOptions.MAPPING_TYPES).contains("tsrg")) {
            builder.add(new ObfuscationTypeDescriptor("searge", REOBF_TSRG_FILE, REOBF_EXTRA_TSRG_FILES, OUT_TSRG_SRG_FILE, ObfuscationEnvironmentFG3.class));
        }
        return builder.build();
    }
}
