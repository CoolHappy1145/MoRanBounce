package org.spongepowered.asm.mixin.transformer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.service.ISyntheticClassInfo;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.IConsumer;
import org.spongepowered.asm.util.asm.ASM;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/InnerClassGenerator.class */
final class InnerClassGenerator implements IClassGenerator {
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private final IConsumer registry;
    private final Map innerClassNames = new HashMap();
    private final Map innerClasses = new HashMap();

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/InnerClassGenerator$InnerClassInfo.class */
    static class InnerClassInfo extends Remapper implements ISyntheticClassInfo {
        private final IMixinInfo mixin;
        private final String name;
        private final String originalName;
        private final MixinInfo owner;
        private final MixinTargetContext target;
        private final String ownerName;
        private final String targetName;
        private int loadCounter;

        InnerClassInfo(IMixinInfo iMixinInfo, String str, String str2, MixinInfo mixinInfo, MixinTargetContext mixinTargetContext) {
            this.mixin = iMixinInfo;
            this.name = str;
            this.originalName = str2;
            this.owner = mixinInfo;
            this.ownerName = mixinInfo.getClassRef();
            this.target = mixinTargetContext;
            this.targetName = mixinTargetContext.getTargetClassRef();
        }

        @Override // org.spongepowered.asm.service.ISyntheticClassInfo
        public IMixinInfo getMixin() {
            return this.mixin;
        }

        @Override // org.spongepowered.asm.service.ISyntheticClassInfo
        public boolean isLoaded() {
            return this.loadCounter > 0;
        }

        @Override // org.spongepowered.asm.service.ISyntheticClassInfo
        public String getName() {
            return this.name;
        }

        @Override // org.spongepowered.asm.service.ISyntheticClassInfo
        public String getClassName() {
            return this.name.replace('/', '.');
        }

        String getOriginalName() {
            return this.originalName;
        }

        MixinInfo getOwner() {
            return this.owner;
        }

        MixinTargetContext getTarget() {
            return this.target;
        }

        String getOwnerName() {
            return this.ownerName;
        }

        String getTargetName() {
            return this.targetName;
        }

        void accept(ClassVisitor classVisitor) {
            MixinService.getService().getBytecodeProvider().getClassNode(this.originalName).accept(classVisitor);
            this.loadCounter++;
        }

        public String mapMethodName(String str, String str2, String str3) {
            ClassInfo.Method methodFindMethod;
            if (this.ownerName.equalsIgnoreCase(str) && (methodFindMethod = this.owner.getClassInfo().findMethod(str2, str3, 10)) != null) {
                return methodFindMethod.getName();
            }
            return super.mapMethodName(str, str2, str3);
        }

        public String map(String str) {
            if (this.originalName.equals(str)) {
                return this.name;
            }
            if (this.ownerName.equals(str)) {
                return this.targetName;
            }
            return str;
        }

        public String toString() {
            return this.name;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/InnerClassGenerator$InnerClassAdapter.class */
    static class InnerClassAdapter extends ClassRemapper {
        private final InnerClassInfo info;

        public InnerClassAdapter(ClassVisitor classVisitor, InnerClassInfo innerClassInfo) {
            super(ASM.API_VERSION, classVisitor, innerClassInfo);
            this.info = innerClassInfo;
        }

        public void visitSource(String str, String str2) {
            super.visitSource(str, str2);
            AnnotationVisitor annotationVisitorVisitAnnotation = this.cv.visitAnnotation("Lorg/spongepowered/asm/mixin/transformer/meta/MixinInner;", false);
            annotationVisitorVisitAnnotation.visit(MixinLaunchPlugin.NAME, this.info.getOwner().toString());
            annotationVisitorVisitAnnotation.visit("name", this.info.getOriginalName().substring(this.info.getOriginalName().lastIndexOf(47) + 1));
            annotationVisitorVisitAnnotation.visitEnd();
        }

        public void visitInnerClass(String str, String str2, String str3, int i) {
            if (str.startsWith(this.info.getOriginalName() + ArgsClassGenerator.GETTER_PREFIX)) {
                throw new InvalidMixinException(this.info.getOwner(), "Found unsupported nested inner class " + str + " in " + this.info.getOriginalName());
            }
            super.visitInnerClass(str, str2, str3, i);
        }
    }

    public InnerClassGenerator(IConsumer iConsumer) {
        this.registry = iConsumer;
    }

    public String registerInnerClass(MixinInfo mixinInfo, String str, MixinTargetContext mixinTargetContext) {
        String str2 = String.format("%s%s", str, mixinTargetContext);
        String uniqueReference = (String) this.innerClassNames.get(str2);
        if (uniqueReference == null) {
            uniqueReference = getUniqueReference(str, mixinTargetContext);
            InnerClassInfo innerClassInfo = new InnerClassInfo(mixinInfo, uniqueReference, str, mixinInfo, mixinTargetContext);
            this.innerClassNames.put(str2, uniqueReference);
            this.innerClasses.put(uniqueReference, innerClassInfo);
            this.registry.accept(innerClassInfo);
            logger.debug("Inner class {} in {} on {} gets unique name {}", new Object[]{str, mixinInfo.getClassRef(), mixinTargetContext.getTargetClassRef(), uniqueReference});
        }
        return uniqueReference;
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IClassGenerator
    public boolean generate(String str, ClassNode classNode) {
        InnerClassInfo innerClassInfo = (InnerClassInfo) this.innerClasses.get(str.replace('.', '/'));
        if (innerClassInfo == null) {
            return false;
        }
        return generate(innerClassInfo, classNode);
    }

    private boolean generate(InnerClassInfo innerClassInfo, ClassNode classNode) {
        try {
            logger.debug("Generating mapped inner class {} (originally {})", new Object[]{innerClassInfo.getName(), innerClassInfo.getOriginalName()});
            innerClassInfo.accept(new InnerClassAdapter(classNode, innerClassInfo));
            return true;
        } catch (InvalidMixinException e) {
            throw e;
        } catch (Exception e2) {
            logger.catching(e2);
            return false;
        }
    }

    private static String getUniqueReference(String str, MixinTargetContext mixinTargetContext) {
        String strSubstring = str.substring(str.lastIndexOf(36) + 1);
        if (strSubstring.matches("^[0-9]+$")) {
            strSubstring = "Anonymous";
        }
        return String.format("%s$%s$%s", mixinTargetContext.getTargetClassRef(), strSubstring, UUID.randomUUID().toString().replace("-", ""));
    }
}
