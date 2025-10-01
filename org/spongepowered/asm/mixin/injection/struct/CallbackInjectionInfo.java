package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Strings;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInjector;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;

@InjectionInfo.AnnotationType(Inject.class)
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/CallbackInjectionInfo.class */
public class CallbackInjectionInfo extends InjectionInfo {
    protected CallbackInjectionInfo(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode) {
        super(mixinTargetContext, methodNode, annotationNode);
    }

    @Override // org.spongepowered.asm.mixin.injection.struct.InjectionInfo
    protected Injector parseInjector(AnnotationNode annotationNode) {
        return new CallbackInjector(this, ((Boolean) Annotations.getValue(annotationNode, "cancellable", Boolean.FALSE)).booleanValue(), (LocalCapture) Annotations.getValue(annotationNode, "locals", LocalCapture.class, LocalCapture.NO_CAPTURE), (String) Annotations.getValue(annotationNode, "id", ""));
    }

    public String getSliceId(String str) {
        return Strings.nullToEmpty(str);
    }
}
