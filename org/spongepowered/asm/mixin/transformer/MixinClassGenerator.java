package org.spongepowered.asm.mixin.transformer;

import java.util.Locale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
import org.spongepowered.asm.service.IMixinAuditTrail;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.perf.Profiler;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinClassGenerator.class */
public class MixinClassGenerator {
    static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private final Extensions extensions;
    private final Profiler profiler = MixinEnvironment.getProfiler();
    private final IMixinAuditTrail auditTrail = MixinService.getService().getAuditTrail();

    MixinClassGenerator(MixinEnvironment mixinEnvironment, Extensions extensions) {
        this.extensions = extensions;
    }

    boolean generateClass(MixinEnvironment mixinEnvironment, String str, ClassNode classNode) {
        if (str == null) {
            logger.warn("MixinClassGenerator tried to generate a class with no name!");
            return false;
        }
        for (IClassGenerator iClassGenerator : this.extensions.getGenerators()) {
            Profiler.Section sectionBegin = this.profiler.begin("generator", iClassGenerator.getClass().getSimpleName().toLowerCase(Locale.ROOT));
            boolean zGenerate = iClassGenerator.generate(str, classNode);
            sectionBegin.end();
            if (zGenerate) {
                if (this.auditTrail != null) {
                    this.auditTrail.onGenerate(str, iClassGenerator.getName());
                }
                this.extensions.export(mixinEnvironment, str.replace('.', '/'), false, classNode);
                return true;
            }
        }
        return false;
    }
}
