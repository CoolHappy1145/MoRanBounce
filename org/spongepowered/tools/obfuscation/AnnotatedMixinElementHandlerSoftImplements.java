package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandlerSoftImplements.class */
public class AnnotatedMixinElementHandlerSoftImplements extends AnnotatedMixinElementHandler {
    AnnotatedMixinElementHandlerSoftImplements(IMixinAnnotationProcessor iMixinAnnotationProcessor, AnnotatedMixin annotatedMixin) {
        super(iMixinAnnotationProcessor, annotatedMixin);
    }

    public void process(AnnotationHandle annotationHandle) {
        if (!this.mixin.remap()) {
            return;
        }
        List<AnnotationHandle> annotationList = annotationHandle.getAnnotationList(PropertyDescriptor.VALUE);
        if (annotationList.size() < 1) {
            this.f228ap.printMessage(Diagnostic.Kind.WARNING, (CharSequence) "Empty @Implements annotation", (Element) this.mixin.getMixin(), annotationHandle.asMirror());
            return;
        }
        for (AnnotationHandle annotationHandle2 : annotationList) {
            Interface.Remap remap = (Interface.Remap) annotationHandle2.getValue("remap", Interface.Remap.ALL);
            if (remap != Interface.Remap.NONE) {
                try {
                    processSoftImplements(remap, new TypeHandle((DeclaredType) annotationHandle2.getValue("iface")), (String) annotationHandle2.getValue("prefix"));
                } catch (Exception e) {
                    this.f228ap.printMessage(Diagnostic.Kind.ERROR, (CharSequence) ("Unexpected error: " + e.getClass().getName() + ": " + e.getMessage()), (Element) this.mixin.getMixin(), annotationHandle2.asMirror());
                }
            }
        }
    }

    private void processSoftImplements(Interface.Remap remap, TypeHandle typeHandle, String str) {
        Iterator it = typeHandle.getEnclosedElements(new ElementKind[]{ElementKind.METHOD}).iterator();
        while (it.hasNext()) {
            processMethod(remap, typeHandle, str, (ExecutableElement) it.next());
        }
        Iterator it2 = typeHandle.getInterfaces().iterator();
        while (it2.hasNext()) {
            processSoftImplements(remap, (TypeHandle) it2.next(), str);
        }
    }

    private void processMethod(Interface.Remap remap, TypeHandle typeHandle, String str, ExecutableElement executableElement) {
        MethodHandle methodHandleFindMethod;
        MethodHandle methodHandleFindMethod2;
        String string = executableElement.getSimpleName().toString();
        String javaSignature = TypeUtils.getJavaSignature((Element) executableElement);
        String descriptor = TypeUtils.getDescriptor(executableElement);
        if (remap != Interface.Remap.ONLY_PREFIXED && (methodHandleFindMethod2 = this.mixin.getHandle().findMethod(string, javaSignature)) != null) {
            addInterfaceMethodMapping(remap, typeHandle, null, methodHandleFindMethod2, string, descriptor);
        }
        if (str != null && (methodHandleFindMethod = this.mixin.getHandle().findMethod(str + string, javaSignature)) != null) {
            addInterfaceMethodMapping(remap, typeHandle, str, methodHandleFindMethod, string, descriptor);
        }
    }

    private void addInterfaceMethodMapping(Interface.Remap remap, TypeHandle typeHandle, String str, MethodHandle methodHandle, String str2, String str3) {
        ObfuscationData obfMethod = this.obf.getDataProvider().getObfMethod(new MappingMethod(typeHandle.getName(), str2, str3));
        if (obfMethod.isEmpty()) {
            if (remap.forceRemap()) {
                this.f228ap.printMessage(Diagnostic.Kind.ERROR, "No obfuscation mapping for soft-implementing method", methodHandle.getElement());
                return;
            }
            return;
        }
        addMethodMappings(methodHandle.getName(), str3, applyPrefix(obfMethod, str));
    }

    private ObfuscationData applyPrefix(ObfuscationData obfuscationData, String str) {
        if (str == null) {
            return obfuscationData;
        }
        ObfuscationData obfuscationData2 = new ObfuscationData();
        Iterator it = obfuscationData.iterator();
        while (it.hasNext()) {
            ObfuscationType obfuscationType = (ObfuscationType) it.next();
            obfuscationData2.put(obfuscationType, ((MappingMethod) obfuscationData.get(obfuscationType)).addPrefix(str));
        }
        return obfuscationData2;
    }
}
