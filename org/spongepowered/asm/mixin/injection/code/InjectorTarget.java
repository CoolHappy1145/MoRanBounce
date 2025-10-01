package org.spongepowered.asm.mixin.injection.code;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
import org.spongepowered.asm.util.Annotations;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/code/InjectorTarget.class */
public class InjectorTarget {
    private final ISliceContext context;
    private final Map cache = new HashMap();
    private final Target target;
    private final String mergedBy;
    private final int mergedPriority;

    public InjectorTarget(ISliceContext iSliceContext, Target target) {
        this.context = iSliceContext;
        this.target = target;
        AnnotationNode visible = Annotations.getVisible(target.method, MixinMerged.class);
        this.mergedBy = (String) Annotations.getValue(visible, MixinLaunchPlugin.NAME);
        this.mergedPriority = ((Integer) Annotations.getValue(visible, "priority", (Object) 1000)).intValue();
    }

    public String toString() {
        return this.target.toString();
    }

    public Target getTarget() {
        return this.target;
    }

    public MethodNode getMethod() {
        return this.target.method;
    }

    public boolean isMerged() {
        return this.mergedBy != null;
    }

    public String getMergedBy() {
        return this.mergedBy;
    }

    public int getMergedPriority() {
        return this.mergedPriority;
    }

    public InsnList getSlice(String str) {
        ReadOnlyInsnList readOnlyInsnList = (ReadOnlyInsnList) this.cache.get(str);
        if (readOnlyInsnList == null) {
            MethodSlice slice = this.context.getSlice(str);
            if (slice != null) {
                readOnlyInsnList = slice.getSlice(this.target.method);
            } else {
                readOnlyInsnList = new ReadOnlyInsnList(this.target.method.instructions);
            }
            this.cache.put(str, readOnlyInsnList);
        }
        return readOnlyInsnList;
    }

    public InsnList getSlice(InjectionPoint injectionPoint) {
        return getSlice(injectionPoint.getSlice());
    }

    public void dispose() {
        Iterator it = this.cache.values().iterator();
        while (it.hasNext()) {
            ((ReadOnlyInsnList) it.next()).dispose();
        }
        this.cache.clear();
    }
}
