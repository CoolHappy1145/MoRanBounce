package org.spongepowered.asm.mixin.injection.code;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.throwables.InvalidSliceException;
import org.spongepowered.asm.util.Annotations;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/code/MethodSlices.class */
public final class MethodSlices {
    private final InjectionInfo info;
    private final Map slices = new HashMap(4);

    private MethodSlices(InjectionInfo injectionInfo) {
        this.info = injectionInfo;
    }

    private void add(MethodSlice methodSlice) {
        InjectionInfo injectionInfo = this.info;
        methodSlice.getId();
        if (this.slices.containsKey("")) {
            throw new InvalidSliceException(this.info, methodSlice + " has a duplicate id, '' was already defined");
        }
        this.slices.put("", methodSlice);
    }

    public MethodSlice get(String str) {
        return (MethodSlice) this.slices.get(str);
    }

    public String toString() {
        return String.format("MethodSlices%s", this.slices.keySet());
    }

    public static MethodSlices parse(InjectionInfo injectionInfo) {
        MethodSlices methodSlices = new MethodSlices(injectionInfo);
        AnnotationNode annotation = injectionInfo.getAnnotation();
        if (annotation != null) {
            Iterator it = Annotations.getValue(annotation, "slice", true).iterator();
            while (it.hasNext()) {
                methodSlices.add(MethodSlice.parse(injectionInfo, (AnnotationNode) it.next()));
            }
        }
        return methodSlices;
    }
}
