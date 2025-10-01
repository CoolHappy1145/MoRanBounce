package org.spongepowered.tools.agent;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.transformer.IMixinTransformer;
import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;
import org.spongepowered.asm.mixin.transformer.throwables.MixinReloadException;
import org.spongepowered.asm.service.IMixinService;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.asm.ASM;

/* loaded from: L-out.jar:org/spongepowered/tools/agent/MixinAgent.class */
public class MixinAgent implements IHotSwap {
    public static final byte[] ERROR_BYTECODE = {1};
    static final MixinAgentClassLoader classLoader = new MixinAgentClassLoader();
    static final Logger logger = LogManager.getLogger("mixin.agent");
    static Instrumentation instrumentation = null;
    private static List agents = new ArrayList();
    final IMixinTransformer classTransformer;

    /* loaded from: L-out.jar:org/spongepowered/tools/agent/MixinAgent$Transformer.class */
    class Transformer implements ClassFileTransformer {
        final MixinAgent this$0;

        Transformer(MixinAgent mixinAgent) {
            this.this$0 = mixinAgent;
        }

        public byte[] transform(ClassLoader classLoader, String str, Class cls, ProtectionDomain protectionDomain, byte[] bArr) {
            if (cls == null) {
                return null;
            }
            byte[] fakeMixinBytecode = MixinAgent.classLoader.getFakeMixinBytecode(cls);
            if (fakeMixinBytecode != null) {
                ClassNode classNode = new ClassNode(ASM.API_VERSION);
                new ClassReader(bArr).accept(classNode, 8);
                List listReloadMixin = reloadMixin(str, classNode);
                if (listReloadMixin == null || !reApplyMixins(listReloadMixin)) {
                    return MixinAgent.ERROR_BYTECODE;
                }
                return fakeMixinBytecode;
            }
            try {
                MixinAgent.logger.info("Redefining class " + str);
                return this.this$0.classTransformer.transformClassBytes(null, str, bArr);
            } catch (Throwable th) {
                MixinAgent.logger.error("Error while re-transforming class " + str, th);
                return MixinAgent.ERROR_BYTECODE;
            }
        }

        private List reloadMixin(String str, ClassNode classNode) {
            MixinAgent.logger.info("Redefining mixin {}", new Object[]{str});
            try {
                return this.this$0.classTransformer.reload(str.replace('/', '.'), classNode);
            } catch (MixinReloadException e) {
                MixinAgent.logger.error("Mixin {} cannot be reloaded, needs a restart to be applied: {} ", new Object[]{e.getMixinInfo(), e.getMessage()});
                return null;
            } catch (Throwable th) {
                MixinAgent.logger.error("Error while finding targets for mixin " + str, th);
                return null;
            }
        }

        private boolean reApplyMixins(List list) {
            IMixinService service = MixinService.getService();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                String strReplace = str.replace('/', '.');
                MixinAgent.logger.debug("Re-transforming target class {}", new Object[]{str});
                try {
                    Class clsFindClass = service.getClassProvider().findClass(strReplace);
                    byte[] originalTargetBytecode = MixinAgent.classLoader.getOriginalTargetBytecode(strReplace);
                    if (originalTargetBytecode == null) {
                        MixinAgent.logger.error("Target class {} bytecode is not registered", new Object[]{strReplace});
                        return false;
                    }
                    MixinAgent.instrumentation.redefineClasses(new ClassDefinition[]{new ClassDefinition(clsFindClass, this.this$0.classTransformer.transformClassBytes(null, strReplace, originalTargetBytecode))});
                } catch (Throwable th) {
                    MixinAgent.logger.error("Error while re-transforming target class " + str, th);
                    return false;
                }
            }
            return true;
        }
    }

    public MixinAgent(IMixinTransformer iMixinTransformer) {
        this.classTransformer = iMixinTransformer;
        agents.add(this);
        if (instrumentation != null) {
            initTransformer();
        }
    }

    private void initTransformer() {
        instrumentation.addTransformer(new Transformer(this), true);
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IHotSwap
    public void registerMixinClass(String str) {
        classLoader.addMixinClass(str);
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IHotSwap
    public void registerTargetClass(String str, ClassNode classNode) {
        classLoader.addTargetClass(str, classNode);
    }

    public static void init(Instrumentation instrumentation2) {
        instrumentation = instrumentation2;
        if (!instrumentation.isRedefineClassesSupported()) {
            logger.error("The instrumentation doesn't support re-definition of classes");
        }
        Iterator it = agents.iterator();
        while (it.hasNext()) {
            ((MixinAgent) it.next()).initTransformer();
        }
    }

    public static void premain(String str, Instrumentation instrumentation2) {
        System.setProperty("mixin.hotSwap", "true");
        init(instrumentation2);
    }

    public static void agentmain(String str, Instrumentation instrumentation2) {
        init(instrumentation2);
    }
}
