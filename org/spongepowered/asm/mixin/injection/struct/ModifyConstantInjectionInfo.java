package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.List;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.ModifyConstantInjector;
import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

@InjectionInfo.AnnotationType(ModifyConstant.class)
@InjectionInfo.HandlerPrefix("constant")
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/ModifyConstantInjectionInfo.class */
public class ModifyConstantInjectionInfo extends InjectionInfo {
    private static final String CONSTANT_ANNOTATION_CLASS = Constant.class.getName().replace('.', '/');

    public ModifyConstantInjectionInfo(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode) {
        super(mixinTargetContext, methodNode, annotationNode, "constant");
    }

    @Override // org.spongepowered.asm.mixin.injection.struct.InjectionInfo
    protected List readInjectionPoints() {
        List injectionPoints = super.readInjectionPoints();
        if (injectionPoints.isEmpty()) {
            AnnotationNode annotationNode = new AnnotationNode(CONSTANT_ANNOTATION_CLASS);
            annotationNode.visit("log", Boolean.TRUE);
            injectionPoints = ImmutableList.of(annotationNode);
        }
        return injectionPoints;
    }

    @Override // org.spongepowered.asm.mixin.injection.struct.InjectionInfo
    protected void parseInjectionPoints(List list) {
        Type returnType = Type.getReturnType(this.method.desc);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            this.injectionPoints.add(new BeforeConstant(getContext(), (AnnotationNode) it.next(), returnType.getDescriptor()));
        }
    }

    @Override // org.spongepowered.asm.mixin.injection.struct.InjectionInfo
    protected Injector parseInjector(AnnotationNode annotationNode) {
        return new ModifyConstantInjector(this);
    }

    public String getSliceId(String str) {
        return Strings.nullToEmpty(str);
    }
}
