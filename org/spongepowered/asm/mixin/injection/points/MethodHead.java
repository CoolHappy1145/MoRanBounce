package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.objectweb.asm.tree.InsnList;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint.AtCode("HEAD")
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/points/MethodHead.class */
public class MethodHead extends InjectionPoint {
    public MethodHead(InjectionPointData injectionPointData) {
        super(injectionPointData);
    }

    @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
    public boolean find(String str, InsnList insnList, Collection collection) {
        collection.add(insnList.getFirst());
        return true;
    }
}
