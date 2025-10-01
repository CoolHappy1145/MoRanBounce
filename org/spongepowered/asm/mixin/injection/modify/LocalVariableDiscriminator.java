package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Locals;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/modify/LocalVariableDiscriminator.class */
public class LocalVariableDiscriminator {
    private final boolean argsOnly;
    private final int ordinal;
    private final int index;
    private final Set names;
    private final boolean print;

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/modify/LocalVariableDiscriminator$Context.class */
    public static class Context implements PrettyPrinter.IPrettyPrintable {
        final Target target;
        final Type returnType;
        final AbstractInsnNode node;
        final int baseArgIndex;
        final Local[] locals;
        private final boolean isStatic;

        /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/modify/LocalVariableDiscriminator$Context$Local.class */
        public class Local {
            int ord = 0;
            String name;
            Type type;
            final Context this$0;

            public Local(Context context, String str, Type type) {
                this.this$0 = context;
                this.name = str;
                this.type = type;
            }

            public String toString() {
                return String.format("Local[ordinal=%d, name=%s, type=%s]", Integer.valueOf(this.ord), this.name, this.type);
            }
        }

        public Context(Type type, boolean z, Target target, AbstractInsnNode abstractInsnNode) {
            this.isStatic = Bytecode.isStatic(target.method);
            this.returnType = type;
            this.target = target;
            this.node = abstractInsnNode;
            this.baseArgIndex = this.isStatic ? 0 : 1;
            this.locals = initLocals(target, z, abstractInsnNode);
            initOrdinals();
        }

        private Local[] initLocals(Target target, boolean z, AbstractInsnNode abstractInsnNode) {
            LocalVariableNode[] localsAt;
            if (!z && (localsAt = Locals.getLocalsAt(target.classNode, target.method, abstractInsnNode)) != null) {
                Local[] localArr = new Local[localsAt.length];
                for (int i = 0; i < localsAt.length; i++) {
                    if (localsAt[i] != null) {
                        localArr[i] = new Local(this, localsAt[i].name, Type.getType(localsAt[i].desc));
                    }
                }
                return localArr;
            }
            Local[] localArr2 = new Local[this.baseArgIndex + target.arguments.length];
            if (!this.isStatic) {
                localArr2[0] = new Local(this, "this", Type.getObjectType(target.classNode.name));
            }
            for (int i2 = this.baseArgIndex; i2 < localArr2.length; i2++) {
                localArr2[i2] = new Local(this, "arg" + i2, target.arguments[i2 - this.baseArgIndex]);
            }
            return localArr2;
        }

        private void initOrdinals() {
            HashMap map = new HashMap();
            for (int i = 0; i < this.locals.length; i++) {
                Integer.valueOf(0);
                if (this.locals[i] != null) {
                    Integer num = (Integer) map.get(this.locals[i].type);
                    Type type = this.locals[i].type;
                    Integer numValueOf = Integer.valueOf(num == null ? 0 : num.intValue() + 1);
                    map.put(type, numValueOf);
                    this.locals[i].ord = numValueOf.intValue();
                }
            }
        }

        @Override // org.spongepowered.asm.util.PrettyPrinter.IPrettyPrintable
        public void print(PrettyPrinter prettyPrinter) {
            prettyPrinter.add("%5s  %7s  %30s  %-50s  %s", new Object[]{"INDEX", "ORDINAL", "TYPE", "NAME", "CANDIDATE"});
            for (int i = this.baseArgIndex; i < this.locals.length; i++) {
                Local local = this.locals[i];
                if (local != null) {
                    Type type = local.type;
                    prettyPrinter.add("[%3d]    [%3d]  %30s  %-50s  %s", new Object[]{Integer.valueOf(i), Integer.valueOf(local.ord), SignaturePrinter.getTypeName(type, false), local.name, this.returnType.equals(type) ? "YES" : "-"});
                } else if (i > 0) {
                    Local local2 = this.locals[i - 1];
                    boolean z = (local2 == null || local2.type == null || local2.type.getSize() <= 1) ? false : true;
                    Object[] objArr = new Object[2];
                    objArr[0] = Integer.valueOf(i);
                    objArr[1] = z ? "<top>" : "-";
                    prettyPrinter.add("[%3d]           %30s", objArr);
                }
            }
        }
    }

    public LocalVariableDiscriminator(boolean z, int i, int i2, Set set, boolean z2) {
        this.argsOnly = z;
        this.ordinal = i;
        this.index = i2;
        this.names = Collections.unmodifiableSet(set);
        this.print = z2;
    }

    public boolean isArgsOnly() {
        return this.argsOnly;
    }

    public int getOrdinal() {
        return this.ordinal;
    }

    public int getIndex() {
        return this.index;
    }

    public Set getNames() {
        return this.names;
    }

    public boolean hasNames() {
        return !this.names.isEmpty();
    }

    public boolean printLVT() {
        return this.print;
    }

    protected boolean isImplicit(Context context) {
        return this.ordinal < 0 && this.index < context.baseArgIndex && this.names.isEmpty();
    }

    public int findLocal(Type type, boolean z, Target target, AbstractInsnNode abstractInsnNode) {
        try {
            return findLocal(new Context(type, z, target, abstractInsnNode));
        } catch (InvalidImplicitDiscriminatorException unused) {
            return -2;
        }
    }

    public int findLocal(Context context) {
        if (isImplicit(context)) {
            return findImplicitLocal(context);
        }
        return findExplicitLocal(context);
    }

    private int findImplicitLocal(Context context) {
        int i = 0;
        int i2 = 0;
        for (int i3 = context.baseArgIndex; i3 < context.locals.length; i3++) {
            Context.Local local = context.locals[i3];
            if (local != null && local.type.equals(context.returnType)) {
                i2++;
                i = i3;
            }
        }
        if (i2 == 1) {
            return i;
        }
        throw new InvalidImplicitDiscriminatorException("Found " + i2 + " candidate variables but exactly 1 is required.");
    }

    private int findExplicitLocal(Context context) {
        for (int i = context.baseArgIndex; i < context.locals.length; i++) {
            Context.Local local = context.locals[i];
            if (local != null && local.type.equals(context.returnType)) {
                if (this.ordinal > -1) {
                    if (this.ordinal == local.ord) {
                        return i;
                    }
                } else if (this.index >= context.baseArgIndex) {
                    if (this.index == i) {
                        return i;
                    }
                } else if (this.names.contains(local.name)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static LocalVariableDiscriminator parse(AnnotationNode annotationNode) {
        boolean zBooleanValue = ((Boolean) Annotations.getValue(annotationNode, "argsOnly", Boolean.FALSE)).booleanValue();
        int iIntValue = ((Integer) Annotations.getValue(annotationNode, "ordinal", (Object) (-1))).intValue();
        int iIntValue2 = ((Integer) Annotations.getValue(annotationNode, "index", (Object) (-1))).intValue();
        boolean zBooleanValue2 = ((Boolean) Annotations.getValue(annotationNode, "print", Boolean.FALSE)).booleanValue();
        HashSet hashSet = new HashSet();
        List list = (List) Annotations.getValue(annotationNode, "name", (List) null);
        if (list != null) {
            hashSet.addAll(list);
        }
        return new LocalVariableDiscriminator(zBooleanValue, iIntValue, iIntValue2, hashSet, zBooleanValue2);
    }
}
