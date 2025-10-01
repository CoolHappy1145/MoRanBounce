package org.spongepowered.asm.util;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.apache.log4j.spi.Configurator;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.TraceClassVisitor;
import org.spongepowered.asm.util.asm.ASM;
import org.spongepowered.asm.util.throwables.SyntheticBridgeException;

/* loaded from: L-out.jar:org/spongepowered/asm/util/Bytecode.class */
public final class Bytecode {
    public static final int[] CONSTANTS_INT = {2, 3, 4, 5, 6, 7, 8};
    public static final int[] CONSTANTS_FLOAT = {11, 12, 13};
    public static final int[] CONSTANTS_DOUBLE = {14, 15};
    public static final int[] CONSTANTS_LONG = {9, 10};
    public static final int[] CONSTANTS_ALL = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 192, 193};
    private static final Object[] CONSTANTS_VALUES = {Type.VOID_TYPE, -1, 0, 1, 2, 3, 4, 5, 0L, 1L, Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(2.0f), Double.valueOf(0.0d), Double.valueOf(1.0d)};
    private static final String[] CONSTANTS_TYPES = {null, "I", "I", "I", "I", "I", "I", "I", "J", "J", "F", "F", "F", "D", "D", "I", "I"};
    private static final String[] BOXING_TYPES = {null, "java/lang/Boolean", "java/lang/Character", "java/lang/Byte", "java/lang/Short", "java/lang/Integer", "java/lang/Float", "java/lang/Long", "java/lang/Double", null, null, null};
    private static final String[] UNBOXING_METHODS = {null, "booleanValue", "charValue", "byteValue", "shortValue", "intValue", "floatValue", "longValue", "doubleValue", null, null, null};

    /* loaded from: L-out.jar:org/spongepowered/asm/util/Bytecode$Visibility.class */
    public enum Visibility {
        PRIVATE(2),
        PROTECTED(4),
        PACKAGE(0),
        PUBLIC(1);

        static final int MASK = 7;
        final int access;

        Visibility(int i) {
            this.access = i;
        }

        public boolean isAtLeast(Visibility visibility) {
            return visibility == null || visibility.ordinal() <= ordinal();
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/util/Bytecode$DelegateInitialiser.class */
    public static class DelegateInitialiser {
        public static final DelegateInitialiser NONE = new DelegateInitialiser(null, false);
        public final MethodInsnNode insn;
        public final boolean isSuper;
        public final boolean isPresent;

        DelegateInitialiser(MethodInsnNode methodInsnNode, boolean z) {
            this.insn = methodInsnNode;
            this.isSuper = z;
            this.isPresent = methodInsnNode != null;
        }

        public String toString() {
            return this.isSuper ? "super" : "this";
        }
    }

    private Bytecode() {
    }

    public static MethodNode findMethod(ClassNode classNode, String str, String str2) {
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals(str) && methodNode.desc.equals(str2)) {
                return methodNode;
            }
        }
        return null;
    }

    public static AbstractInsnNode findInsn(MethodNode methodNode, int i) {
        ListIterator it = methodNode.instructions.iterator();
        while (it.hasNext()) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode) it.next();
            if (abstractInsnNode.getOpcode() == i) {
                return abstractInsnNode;
            }
        }
        return null;
    }

    public static DelegateInitialiser findDelegateInit(MethodNode methodNode, String str, String str2) {
        if (!Constants.CTOR.equals(methodNode.name)) {
            return DelegateInitialiser.NONE;
        }
        int i = 0;
        ListIterator it = methodNode.instructions.iterator();
        while (it.hasNext()) {
            MethodInsnNode methodInsnNode = (AbstractInsnNode) it.next();
            if ((methodInsnNode instanceof TypeInsnNode) && methodInsnNode.getOpcode() == 187) {
                i++;
            } else if ((methodInsnNode instanceof MethodInsnNode) && methodInsnNode.getOpcode() == 183) {
                MethodInsnNode methodInsnNode2 = methodInsnNode;
                if (!Constants.CTOR.equals(methodInsnNode2.name)) {
                    continue;
                } else if (i > 0) {
                    i--;
                } else {
                    boolean zEquals = methodInsnNode2.owner.equals(str);
                    if (zEquals || methodInsnNode2.owner.equals(str2)) {
                        return new DelegateInitialiser(methodInsnNode2, zEquals);
                    }
                }
            }
        }
        return DelegateInitialiser.NONE;
    }

    public static void textify(ClassNode classNode, OutputStream outputStream) {
        classNode.accept(new TraceClassVisitor(new PrintWriter(outputStream)));
    }

    public static void textify(MethodNode methodNode, OutputStream outputStream) {
        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(new PrintWriter(outputStream));
        methodNode.accept(traceClassVisitor.visitMethod(methodNode.access, methodNode.name, methodNode.desc, methodNode.signature, (String[]) methodNode.exceptions.toArray(new String[0])));
        traceClassVisitor.visitEnd();
    }

    public static void dumpClass(ClassNode classNode) {
        ClassWriter classWriter = new ClassWriter(3);
        classNode.accept(classWriter);
        dumpClass(classWriter.toByteArray());
    }

    public static void dumpClass(byte[] bArr) {
        CheckClassAdapter.verify(new ClassReader(bArr), true, new PrintWriter(System.out));
    }

    public static void printMethodWithOpcodeIndices(MethodNode methodNode) {
        System.err.printf("%s%s\n", methodNode.name, methodNode.desc);
        int i = 0;
        ListIterator it = methodNode.instructions.iterator();
        while (it.hasNext()) {
            int i2 = i;
            i++;
            System.err.printf("[%4d] %s\n", Integer.valueOf(i2), describeNode((AbstractInsnNode) it.next()));
        }
    }

    public static void printMethod(MethodNode methodNode) {
        System.err.printf("%s%s maxStack=%d maxLocals=%d\n", methodNode.name, methodNode.desc, Integer.valueOf(methodNode.maxStack), Integer.valueOf(methodNode.maxLocals));
        int i = 0;
        ListIterator it = methodNode.instructions.iterator();
        while (it.hasNext()) {
            int i2 = i;
            i++;
            System.err.printf("%-4d  ", Integer.valueOf(i2));
            printNode((AbstractInsnNode) it.next());
        }
    }

    public static void printNode(AbstractInsnNode abstractInsnNode) {
        System.err.printf("%s\n", describeNode(abstractInsnNode));
    }

    public static String describeNode(AbstractInsnNode abstractInsnNode) {
        String str;
        if (abstractInsnNode == null) {
            return String.format("   %-14s ", Configurator.NULL);
        }
        if (abstractInsnNode instanceof LabelNode) {
            return String.format("[%s]", ((LabelNode) abstractInsnNode).getLabel());
        }
        String str2 = String.format("   %-14s ", abstractInsnNode.getClass().getSimpleName().replace("Node", ""));
        if (abstractInsnNode instanceof JumpInsnNode) {
            str = str2 + String.format("[%s] [%s]", getOpcodeName(abstractInsnNode), ((JumpInsnNode) abstractInsnNode).label.getLabel());
        } else if (abstractInsnNode instanceof VarInsnNode) {
            str = str2 + String.format("[%s] %d", getOpcodeName(abstractInsnNode), Integer.valueOf(((VarInsnNode) abstractInsnNode).var));
        } else if (abstractInsnNode instanceof MethodInsnNode) {
            MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
            str = str2 + String.format("[%s] %s::%s%s", getOpcodeName(abstractInsnNode), methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);
        } else if (abstractInsnNode instanceof FieldInsnNode) {
            FieldInsnNode fieldInsnNode = (FieldInsnNode) abstractInsnNode;
            str = str2 + String.format("[%s] %s::%s:%s", getOpcodeName(abstractInsnNode), fieldInsnNode.owner, fieldInsnNode.name, fieldInsnNode.desc);
        } else if (abstractInsnNode instanceof InvokeDynamicInsnNode) {
            InvokeDynamicInsnNode invokeDynamicInsnNode = (InvokeDynamicInsnNode) abstractInsnNode;
            str = str2 + String.format("[%s] %s%s { %s %s::%s%s }", getOpcodeName(abstractInsnNode), invokeDynamicInsnNode.name, invokeDynamicInsnNode.desc, getOpcodeName(invokeDynamicInsnNode.bsm.getTag(), "H_GETFIELD", 1), invokeDynamicInsnNode.bsm.getOwner(), invokeDynamicInsnNode.bsm.getName(), invokeDynamicInsnNode.bsm.getDesc());
        } else if (abstractInsnNode instanceof LineNumberNode) {
            LineNumberNode lineNumberNode = (LineNumberNode) abstractInsnNode;
            str = str2 + String.format("LINE=[%d] LABEL=[%s]", Integer.valueOf(lineNumberNode.line), lineNumberNode.start.getLabel());
        } else if (abstractInsnNode instanceof LdcInsnNode) {
            str = str2 + ((LdcInsnNode) abstractInsnNode).cst;
        } else if (abstractInsnNode instanceof IntInsnNode) {
            str = str2 + ((IntInsnNode) abstractInsnNode).operand;
        } else {
            str = abstractInsnNode instanceof FrameNode ? str2 + String.format("[%s] ", getOpcodeName(((FrameNode) abstractInsnNode).type, "H_INVOKEINTERFACE", -1)) : str2 + String.format("[%s] ", getOpcodeName(abstractInsnNode));
        }
        return str;
    }

    public static String getOpcodeName(AbstractInsnNode abstractInsnNode) {
        return abstractInsnNode != null ? getOpcodeName(abstractInsnNode.getOpcode()) : "";
    }

    public static String getOpcodeName(int i) {
        return getOpcodeName(i, "UNINITIALIZED_THIS", 1);
    }

    private static String getOpcodeName(int i, String str, int i2) {
        if (i >= i2) {
            boolean z = false;
            try {
                for (Field field : Opcodes.class.getDeclaredFields()) {
                    if (z || field.getName().equals(str)) {
                        z = true;
                        if (field.getType() == Integer.TYPE && field.getInt(null) == i) {
                            return field.getName();
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
        return i >= 0 ? String.valueOf(i) : Constants.SIDE_UNKNOWN;
    }

    public static boolean methodHasLineNumbers(MethodNode methodNode) {
        ListIterator it = methodNode.instructions.iterator();
        while (it.hasNext()) {
            if (it.next() instanceof LineNumberNode) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStatic(MethodNode methodNode) {
        return (methodNode.access & 8) == 8;
    }

    public static boolean isStatic(FieldNode fieldNode) {
        return (fieldNode.access & 8) == 8;
    }

    public static int getFirstNonArgLocalIndex(MethodNode methodNode) {
        return getFirstNonArgLocalIndex(Type.getArgumentTypes(methodNode.desc), !isStatic(methodNode));
    }

    public static int getFirstNonArgLocalIndex(Type[] typeArr, boolean z) {
        return getArgsSize(typeArr) + (z ? 1 : 0);
    }

    public static int getArgsSize(Type[] typeArr) {
        return getArgsSize(typeArr, 0, typeArr.length);
    }

    public static int getArgsSize(Type[] typeArr, int i, int i2) {
        int size = 0;
        for (int i3 = i; i3 < typeArr.length && i3 < i2; i3++) {
            size += typeArr[i3].getSize();
        }
        return size;
    }

    public static void loadArgs(Type[] typeArr, InsnList insnList, int i) {
        loadArgs(typeArr, insnList, i, -1);
    }

    public static void loadArgs(Type[] typeArr, InsnList insnList, int i, int i2) {
        loadArgs(typeArr, insnList, i, i2, null);
    }

    public static void loadArgs(Type[] typeArr, InsnList insnList, int i, int i2, Type[] typeArr2) {
        int size = i;
        for (int i3 = 0; i3 < typeArr.length; i3++) {
            insnList.add(new VarInsnNode(typeArr[i3].getOpcode(21), size));
            if (typeArr2 != null && i3 < typeArr2.length && typeArr2[i3] != null) {
                insnList.add(new TypeInsnNode(192, typeArr2[i3].getInternalName()));
            }
            size += typeArr[i3].getSize();
            if (i2 >= i && size >= i2) {
                return;
            }
        }
    }

    public static Map cloneLabels(InsnList insnList) {
        HashMap map = new HashMap();
        ListIterator it = insnList.iterator();
        while (it.hasNext()) {
            LabelNode labelNode = (AbstractInsnNode) it.next();
            if (labelNode instanceof LabelNode) {
                map.put(labelNode, new LabelNode(labelNode.getLabel()));
            }
        }
        return map;
    }

    public static String generateDescriptor(Type type, Type[] typeArr) {
        return generateDescriptor((Object) type, (Object[]) typeArr);
    }

    public static String generateDescriptor(Object obj, Object[] objArr) {
        StringBuilder sbAppend = new StringBuilder().append('(');
        for (Object obj2 : objArr) {
            sbAppend.append(toDescriptor(obj2));
        }
        return sbAppend.append(')').append(obj != null ? toDescriptor(obj) : "V").toString();
    }

    private static String toDescriptor(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Type) {
            return obj.toString();
        }
        if (obj instanceof Class) {
            return Type.getDescriptor((Class) obj);
        }
        return obj == null ? "" : obj.toString();
    }

    public static String getDescriptor(Type[] typeArr) {
        return "(" + Joiner.on("").join(typeArr) + ")";
    }

    public static String getDescriptor(Type type, Type[] typeArr) {
        return getDescriptor(typeArr) + type.toString();
    }

    public static String changeDescriptorReturnType(String str, String str2) {
        if (str == null || !str.startsWith("(") || str.lastIndexOf(41) < 1) {
            return null;
        }
        if (str2 == null) {
            return str;
        }
        return str.substring(0, str.lastIndexOf(41) + 1) + str2;
    }

    public static String getSimpleName(Class cls) {
        return cls.getSimpleName();
    }

    public static String getSimpleName(Type type) {
        return type.getSort() < 9 ? type.getDescriptor() : getSimpleName(type.getClassName());
    }

    public static String getSimpleName(AnnotationNode annotationNode) {
        return getSimpleName(annotationNode.desc);
    }

    public static String getSimpleName(String str) {
        return str.substring(Math.max(str.lastIndexOf(47), 0) + 1).replace(";", "");
    }

    public static boolean isConstant(AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode == null) {
            return false;
        }
        return Ints.contains(CONSTANTS_ALL, abstractInsnNode.getOpcode());
    }

    public static Object getConstant(AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode == null) {
            return null;
        }
        if (abstractInsnNode instanceof LdcInsnNode) {
            return ((LdcInsnNode) abstractInsnNode).cst;
        }
        if (abstractInsnNode instanceof IntInsnNode) {
            int i = ((IntInsnNode) abstractInsnNode).operand;
            if (abstractInsnNode.getOpcode() == 16 || abstractInsnNode.getOpcode() == 17) {
                return Integer.valueOf(i);
            }
            throw new IllegalArgumentException("IntInsnNode with invalid opcode " + abstractInsnNode.getOpcode() + " in getConstant");
        }
        if (abstractInsnNode instanceof TypeInsnNode) {
            if (abstractInsnNode.getOpcode() < 192) {
                return null;
            }
            return Type.getObjectType(((TypeInsnNode) abstractInsnNode).desc);
        }
        int iIndexOf = Ints.indexOf(CONSTANTS_ALL, abstractInsnNode.getOpcode());
        if (iIndexOf < 0) {
            return null;
        }
        return CONSTANTS_VALUES[iIndexOf];
    }

    public static Type getConstantType(AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode == null) {
            return null;
        }
        if (abstractInsnNode instanceof LdcInsnNode) {
            Object obj = ((LdcInsnNode) abstractInsnNode).cst;
            if (obj instanceof Integer) {
                return Type.getType("I");
            }
            if (obj instanceof Float) {
                return Type.getType("F");
            }
            if (obj instanceof Long) {
                return Type.getType("J");
            }
            if (obj instanceof Double) {
                return Type.getType("D");
            }
            if (obj instanceof String) {
                return Type.getType(Constants.STRING_DESC);
            }
            if (obj instanceof Type) {
                return Type.getType(Constants.CLASS_DESC);
            }
            throw new IllegalArgumentException("LdcInsnNode with invalid payload type " + obj.getClass() + " in getConstant");
        }
        if (abstractInsnNode instanceof TypeInsnNode) {
            if (abstractInsnNode.getOpcode() < 192) {
                return null;
            }
            return Type.getType(Constants.CLASS_DESC);
        }
        int iIndexOf = Ints.indexOf(CONSTANTS_ALL, abstractInsnNode.getOpcode());
        if (iIndexOf < 0) {
            return null;
        }
        return Type.getType(CONSTANTS_TYPES[iIndexOf]);
    }

    public static boolean hasFlag(ClassNode classNode, int i) {
        return (classNode.access & i) == i;
    }

    public static boolean hasFlag(MethodNode methodNode, int i) {
        return (methodNode.access & i) == i;
    }

    public static boolean hasFlag(FieldNode fieldNode, int i) {
        return (fieldNode.access & i) == i;
    }

    public static boolean compareFlags(MethodNode methodNode, MethodNode methodNode2, int i) {
        return hasFlag(methodNode, i) == hasFlag(methodNode2, i);
    }

    public static boolean compareFlags(FieldNode fieldNode, FieldNode fieldNode2, int i) {
        return hasFlag(fieldNode, i) == hasFlag(fieldNode2, i);
    }

    public static boolean isVirtual(MethodNode methodNode) {
        return (methodNode == null || isStatic(methodNode) || !getVisibility(methodNode).isAtLeast(Visibility.PROTECTED)) ? false : true;
    }

    public static Visibility getVisibility(MethodNode methodNode) {
        return getVisibility(methodNode.access & 7);
    }

    public static Visibility getVisibility(FieldNode fieldNode) {
        return getVisibility(fieldNode.access & 7);
    }

    private static Visibility getVisibility(int i) {
        if ((i & 4) != 0) {
            return Visibility.PROTECTED;
        }
        if ((i & 2) != 0) {
            return Visibility.PRIVATE;
        }
        if ((i & 1) != 0) {
            return Visibility.PUBLIC;
        }
        return Visibility.PACKAGE;
    }

    public static void setVisibility(MethodNode methodNode, Visibility visibility) {
        methodNode.access = (methodNode.access & (-8)) | (visibility.access & 7);
    }

    public static void setVisibility(FieldNode fieldNode, Visibility visibility) {
        fieldNode.access = (fieldNode.access & (-8)) | (visibility.access & 7);
    }

    public static void setVisibility(MethodNode methodNode, int i) {
        methodNode.access = (methodNode.access & (-8)) | (i & 7);
    }

    public static void setVisibility(FieldNode fieldNode, int i) {
        fieldNode.access = (fieldNode.access & (-8)) | (i & 7);
    }

    public static int getMaxLineNumber(ClassNode classNode, int i, int i2) {
        int iMax = 0;
        Iterator it = classNode.methods.iterator();
        while (it.hasNext()) {
            ListIterator it2 = ((MethodNode) it.next()).instructions.iterator();
            while (it2.hasNext()) {
                LineNumberNode lineNumberNode = (AbstractInsnNode) it2.next();
                if (lineNumberNode instanceof LineNumberNode) {
                    iMax = Math.max(iMax, lineNumberNode.line);
                }
            }
        }
        return Math.max(i, iMax + i2);
    }

    public static String getBoxingType(Type type) {
        if (type == null) {
            return null;
        }
        return BOXING_TYPES[type.getSort()];
    }

    public static String getUnboxingMethod(Type type) {
        if (type == null) {
            return null;
        }
        return UNBOXING_METHODS[type.getSort()];
    }

    public static void compareBridgeMethods(MethodNode methodNode, MethodNode methodNode2) {
        ListIterator it = methodNode.instructions.iterator();
        ListIterator it2 = methodNode2.instructions.iterator();
        int i = 0;
        while (it.hasNext() && it2.hasNext()) {
            MethodInsnNode methodInsnNode = (AbstractInsnNode) it.next();
            MethodInsnNode methodInsnNode2 = (AbstractInsnNode) it2.next();
            if (!(methodInsnNode instanceof LabelNode)) {
                if (methodInsnNode instanceof MethodInsnNode) {
                    MethodInsnNode methodInsnNode3 = methodInsnNode;
                    MethodInsnNode methodInsnNode4 = methodInsnNode2;
                    if (!methodInsnNode3.name.equals(methodInsnNode4.name)) {
                        throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INVOKE_NAME, methodNode.name, methodNode.desc, i, methodInsnNode, methodInsnNode2);
                    }
                    if (!methodInsnNode3.desc.equals(methodInsnNode4.desc)) {
                        throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INVOKE_DESC, methodNode.name, methodNode.desc, i, methodInsnNode, methodInsnNode2);
                    }
                } else {
                    if (methodInsnNode.getOpcode() != methodInsnNode2.getOpcode()) {
                        throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INSN, methodNode.name, methodNode.desc, i, methodInsnNode, methodInsnNode2);
                    }
                    if (methodInsnNode instanceof VarInsnNode) {
                        if (((VarInsnNode) methodInsnNode).var != ((VarInsnNode) methodInsnNode2).var) {
                            throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_LOAD, methodNode.name, methodNode.desc, i, methodInsnNode, methodInsnNode2);
                        }
                    } else if (methodInsnNode instanceof TypeInsnNode) {
                        TypeInsnNode typeInsnNode = (TypeInsnNode) methodInsnNode;
                        TypeInsnNode typeInsnNode2 = (TypeInsnNode) methodInsnNode2;
                        if (typeInsnNode.getOpcode() == 192 && !typeInsnNode.desc.equals(typeInsnNode2.desc)) {
                            throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_CAST, methodNode.name, methodNode.desc, i, methodInsnNode, methodInsnNode2);
                        }
                    } else {
                        continue;
                    }
                }
            }
            i++;
        }
        if (it.hasNext() || it2.hasNext()) {
            throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_LENGTH, methodNode.name, methodNode.desc, i, null, null);
        }
    }

    public static void merge(ClassNode classNode, ClassNode classNode2) {
        if (classNode == null) {
            return;
        }
        if (classNode2 == null) {
            throw new NullPointerException("Target ClassNode for merge must not be null");
        }
        classNode2.version = Math.max(classNode.version, classNode2.version);
        classNode2.interfaces = merge(classNode.interfaces, classNode2.interfaces);
        classNode2.invisibleAnnotations = merge(classNode.invisibleAnnotations, classNode2.invisibleAnnotations);
        classNode2.visibleAnnotations = merge(classNode.visibleAnnotations, classNode2.visibleAnnotations);
        classNode2.visibleTypeAnnotations = merge(classNode.visibleTypeAnnotations, classNode2.visibleTypeAnnotations);
        classNode2.invisibleTypeAnnotations = merge(classNode.invisibleTypeAnnotations, classNode2.invisibleTypeAnnotations);
        classNode2.attrs = merge(classNode.attrs, classNode2.attrs);
        classNode2.innerClasses = merge(classNode.innerClasses, classNode2.innerClasses);
        classNode2.fields = merge(classNode.fields, classNode2.fields);
        classNode2.methods = merge(classNode.methods, classNode2.methods);
    }

    public static void replace(ClassNode classNode, ClassNode classNode2) {
        if (classNode == null) {
            return;
        }
        if (classNode2 == null) {
            throw new NullPointerException("Target ClassNode for replace must not be null");
        }
        classNode2.name = classNode.name;
        classNode2.signature = classNode.signature;
        classNode2.superName = classNode.superName;
        classNode2.version = classNode.version;
        classNode2.access = classNode.access;
        classNode2.sourceDebug = classNode.sourceDebug;
        classNode2.sourceFile = classNode.sourceFile;
        classNode2.outerClass = classNode.outerClass;
        classNode2.outerMethod = classNode.outerMethod;
        classNode2.outerMethodDesc = classNode.outerMethodDesc;
        clear(classNode2.interfaces);
        clear(classNode2.visibleAnnotations);
        clear(classNode2.invisibleAnnotations);
        clear(classNode2.visibleTypeAnnotations);
        clear(classNode2.invisibleTypeAnnotations);
        clear(classNode2.attrs);
        clear(classNode2.innerClasses);
        clear(classNode2.fields);
        clear(classNode2.methods);
        if (ASM.API_VERSION >= 393216) {
            classNode2.module = classNode.module;
        }
        merge(classNode, classNode2);
    }

    private static void clear(List list) {
        if (list != null) {
            list.clear();
        }
    }

    private static List merge(List list, List list2) {
        if (list == null || list.isEmpty()) {
            return list2;
        }
        if (list2 == null) {
            return new ArrayList(list);
        }
        list2.addAll(list);
        return list2;
    }
}
