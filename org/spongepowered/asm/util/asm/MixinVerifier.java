package org.spongepowered.asm.util.asm;

import java.util.List;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.analysis.SimpleVerifier;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.util.Constants;

/* loaded from: L-out.jar:org/spongepowered/asm/util/asm/MixinVerifier.class */
public class MixinVerifier extends SimpleVerifier {
    private Type currentClass;
    private Type currentSuperClass;
    private List currentClassInterfaces;
    private boolean isInterface;

    public MixinVerifier(int i, Type type, Type type2, List list, boolean z) {
        super(i, type, type2, list, z);
        this.currentClass = type;
        this.currentSuperClass = type2;
        this.currentClassInterfaces = list;
        this.isInterface = z;
    }

    protected boolean isInterface(Type type) {
        if (this.currentClass != null && type.equals(this.currentClass)) {
            return this.isInterface;
        }
        return ClassInfo.forType(type, ClassInfo.TypeLookup.ELEMENT_TYPE).isInterface();
    }

    protected Type getSuperClass(Type type) {
        if (this.currentClass != null && type.equals(this.currentClass)) {
            return this.currentSuperClass;
        }
        ClassInfo superClass = ClassInfo.forType(type, ClassInfo.TypeLookup.ELEMENT_TYPE).getSuperClass();
        if (superClass == null) {
            return null;
        }
        return Type.getType("L" + superClass.getName() + ";");
    }

    protected boolean isAssignableFrom(Type type, Type type2) {
        if (type.equals(type2)) {
            return true;
        }
        if (this.currentClass != null && type.equals(this.currentClass)) {
            if (getSuperClass(type2) == null) {
                return false;
            }
            if (this.isInterface) {
                return type2.getSort() == 10 || type2.getSort() == 9;
            }
            return isAssignableFrom(type, getSuperClass(type2));
        }
        if (this.currentClass != null && type2.equals(this.currentClass)) {
            if (isAssignableFrom(type, this.currentSuperClass)) {
                return true;
            }
            if (this.currentClassInterfaces != null) {
                for (int i = 0; i < this.currentClassInterfaces.size(); i++) {
                    if (isAssignableFrom(type, (Type) this.currentClassInterfaces.get(i))) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
        ClassInfo classInfoForType = ClassInfo.forType(type, ClassInfo.TypeLookup.ELEMENT_TYPE);
        if (classInfoForType == null) {
            return false;
        }
        if (classInfoForType.isInterface()) {
            classInfoForType = ClassInfo.forName(Constants.OBJECT);
        }
        return ClassInfo.forType(type2, ClassInfo.TypeLookup.ELEMENT_TYPE).hasSuperClass(classInfoForType);
    }
}
