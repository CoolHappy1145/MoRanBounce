package org.spongepowered.asm.mixin.injection.points;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorConstructor;
import org.spongepowered.asm.mixin.injection.selectors.TargetSelector;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionPointException;
import org.spongepowered.asm.util.Constants;

@InjectionPoint.AtCode("NEW")
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/points/BeforeNew.class */
public class BeforeNew extends InjectionPoint {
    private final String target;
    private final String desc;
    private final int ordinal;

    public BeforeNew(InjectionPointData injectionPointData) {
        super(injectionPointData);
        this.ordinal = injectionPointData.getOrdinal();
        String strEmptyToNull = Strings.emptyToNull(injectionPointData.get("class", injectionPointData.get("target", "")).replace('.', '/'));
        ITargetSelector andValidate = TargetSelector.parseAndValidate(strEmptyToNull, injectionPointData.getContext());
        if (!(andValidate instanceof ITargetSelectorConstructor)) {
            throw new InvalidInjectionPointException(injectionPointData.getContext(), "Failed parsing @At(\"NEW\") target descriptor \"%s\" on %s", new Object[]{strEmptyToNull, injectionPointData.getDescription()});
        }
        ITargetSelectorConstructor iTargetSelectorConstructor = (ITargetSelectorConstructor) andValidate;
        this.target = iTargetSelectorConstructor.toCtorType();
        this.desc = iTargetSelectorConstructor.toCtorDesc();
    }

    public boolean hasDescriptor() {
        return this.desc != null;
    }

    @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
    public boolean find(String str, InsnList insnList, Collection collection) {
        boolean z = false;
        int i = 0;
        ArrayList<TypeInsnNode> arrayList = new ArrayList();
        Collection collection2 = this.desc != null ? arrayList : collection;
        ListIterator it = insnList.iterator();
        while (it.hasNext()) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode) it.next();
            if ((abstractInsnNode instanceof TypeInsnNode) && abstractInsnNode.getOpcode() == 187 && matchesOwner((TypeInsnNode) abstractInsnNode)) {
                if (this.ordinal == -1 || this.ordinal == i) {
                    collection2.add(abstractInsnNode);
                    z = this.desc == null;
                }
                i++;
            }
        }
        if (this.desc != null) {
            for (TypeInsnNode typeInsnNode : arrayList) {
                if (findCtor(insnList, typeInsnNode)) {
                    collection.add(typeInsnNode);
                    z = true;
                }
            }
        }
        return z;
    }

    protected boolean findCtor(InsnList insnList, TypeInsnNode typeInsnNode) {
        ListIterator it = insnList.iterator(insnList.indexOf(typeInsnNode));
        while (it.hasNext()) {
            MethodInsnNode methodInsnNode = (AbstractInsnNode) it.next();
            if ((methodInsnNode instanceof MethodInsnNode) && methodInsnNode.getOpcode() == 183) {
                MethodInsnNode methodInsnNode2 = methodInsnNode;
                if (Constants.CTOR.equals(methodInsnNode2.name) && methodInsnNode2.owner.equals(typeInsnNode.desc) && methodInsnNode2.desc.equals(this.desc)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean matchesOwner(TypeInsnNode typeInsnNode) {
        return this.target == null || this.target.equals(typeInsnNode.desc);
    }
}
