package org.spongepowered.asm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.apache.log4j.helpers.DateLayout;
import org.apache.log4j.spi.LocationInfo;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicValue;
import org.objectweb.asm.tree.analysis.Frame;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.util.asm.ASM;
import org.spongepowered.asm.util.asm.MixinVerifier;
import org.spongepowered.asm.util.throwables.LVTGeneratorError;

/* loaded from: L-out.jar:org/spongepowered/asm/util/Locals.class */
public final class Locals {
    private static final String[] FRAME_TYPES = {"TOP", "INTEGER", "FLOAT", "DOUBLE", "LONG", DateLayout.NULL_DATE_FORMAT, "UNINITIALIZED_THIS"};
    private static final Map calculatedLocalVariables = new HashMap();

    private Locals() {
    }

    public static void loadLocals(Type[] typeArr, InsnList insnList, int i, int i2) {
        while (i < typeArr.length && i2 > 0) {
            if (typeArr[i] != null) {
                insnList.add(new VarInsnNode(typeArr[i].getOpcode(21), i));
                i2--;
            }
            i++;
        }
    }

    public static LocalVariableNode[] getLocalsAt(ClassNode classNode, MethodNode methodNode, AbstractInsnNode abstractInsnNode) {
        for (int i = 0; i < 3 && ((abstractInsnNode instanceof LabelNode) || (abstractInsnNode instanceof LineNumberNode)); i++) {
            abstractInsnNode = nextNode(methodNode.instructions, abstractInsnNode);
        }
        ClassInfo classInfoForName = ClassInfo.forName(classNode.name);
        if (classInfoForName == null) {
            throw new LVTGeneratorError("Could not load class metadata for " + classNode.name + " generating LVT for " + methodNode.name);
        }
        ClassInfo.Method methodFindMethod = classInfoForName.findMethod(methodNode, methodNode.access | 262144);
        if (methodFindMethod == null) {
            throw new LVTGeneratorError("Could not locate method metadata for " + methodNode.name + " generating LVT in " + classNode.name);
        }
        List frames = methodFindMethod.getFrames();
        LocalVariableNode[] localVariableNodeArr = new LocalVariableNode[methodNode.maxLocals];
        int size = 0;
        int i2 = 0;
        if ((methodNode.access & 8) == 0) {
            size = 0 + 1;
            localVariableNodeArr[0] = new LocalVariableNode("this", Type.getObjectType(classNode.name).toString(), (String) null, (LabelNode) null, (LabelNode) null, 0);
        }
        for (Type type : Type.getArgumentTypes(methodNode.desc)) {
            int i3 = i2;
            i2++;
            localVariableNodeArr[size] = new LocalVariableNode("arg" + i3, type.toString(), (String) null, (LabelNode) null, (LabelNode) null, size);
            size += type.getSize();
        }
        int i4 = size;
        int adjustedFrameSize = size;
        int i5 = -1;
        int i6 = size;
        VarInsnNode varInsnNode = null;
        ListIterator it = methodNode.instructions.iterator();
        while (it.hasNext()) {
            FrameNode frameNode = (AbstractInsnNode) it.next();
            if (varInsnNode != null) {
                localVariableNodeArr[varInsnNode.var] = getLocalVariableAt(classNode, methodNode, (AbstractInsnNode) frameNode, varInsnNode.var);
                varInsnNode = null;
            }
            if (frameNode instanceof FrameNode) {
                i5++;
                FrameNode frameNode2 = frameNode;
                if (frameNode2.type != 3 && frameNode2.type != 4) {
                    ClassInfo.FrameData frameData = i5 < frames.size() ? (ClassInfo.FrameData) frames.get(i5) : null;
                    if (frameData != null) {
                        if (frameData.type == 0) {
                            adjustedFrameSize = Math.min(adjustedFrameSize, frameData.locals);
                            i6 = adjustedFrameSize;
                        } else {
                            adjustedFrameSize = getAdjustedFrameSize(adjustedFrameSize, frameData);
                        }
                    } else {
                        adjustedFrameSize = getAdjustedFrameSize(adjustedFrameSize, frameNode2);
                    }
                    if (frameNode2.type == 2) {
                        for (int i7 = adjustedFrameSize; i7 < localVariableNodeArr.length; i7++) {
                            localVariableNodeArr[i7] = null;
                        }
                        i6 = adjustedFrameSize;
                    } else {
                        int i8 = frameNode2.type == 1 ? i6 : 0;
                        i6 = adjustedFrameSize;
                        int i9 = 0;
                        while (i8 < localVariableNodeArr.length) {
                            Object obj = i9 < frameNode2.local.size() ? frameNode2.local.get(i9) : null;
                            if (obj instanceof String) {
                                localVariableNodeArr[i8] = getLocalVariableAt(classNode, methodNode, (AbstractInsnNode) frameNode, i8);
                            } else if (obj instanceof Integer) {
                                boolean z = obj == Opcodes.UNINITIALIZED_THIS || obj == Opcodes.NULL;
                                boolean z2 = obj == Opcodes.INTEGER || obj == Opcodes.FLOAT;
                                boolean z3 = obj == Opcodes.DOUBLE || obj == Opcodes.LONG;
                                if (obj == Opcodes.TOP) {
                                    continue;
                                } else if (z) {
                                    localVariableNodeArr[i8] = null;
                                } else if (z2 || z3) {
                                    localVariableNodeArr[i8] = getLocalVariableAt(classNode, methodNode, (AbstractInsnNode) frameNode, i8);
                                    if (z3) {
                                        i8++;
                                        localVariableNodeArr[i8] = null;
                                    }
                                } else {
                                    throw new LVTGeneratorError("Unrecognised locals opcode " + obj + " in locals array at position " + i9 + " in " + classNode.name + "." + methodNode.name + methodNode.desc);
                                }
                            } else if (obj == null) {
                                if (i8 >= i4 && i8 >= adjustedFrameSize && adjustedFrameSize > 0) {
                                    localVariableNodeArr[i8] = null;
                                }
                            } else if (!(obj instanceof LabelNode)) {
                                throw new LVTGeneratorError("Invalid value " + obj + " in locals array at position " + i9 + " in " + classNode.name + "." + methodNode.name + methodNode.desc);
                            }
                            i8++;
                            i9++;
                        }
                    }
                }
            } else if (frameNode instanceof VarInsnNode) {
                VarInsnNode varInsnNode2 = (VarInsnNode) frameNode;
                if (frameNode.getOpcode() >= 21 && frameNode.getOpcode() <= 53) {
                    localVariableNodeArr[varInsnNode2.var] = getLocalVariableAt(classNode, methodNode, (AbstractInsnNode) frameNode, varInsnNode2.var);
                } else {
                    varInsnNode = varInsnNode2;
                }
            }
            if (frameNode == abstractInsnNode) {
                break;
            }
        }
        for (int i10 = 0; i10 < localVariableNodeArr.length; i10++) {
            if (localVariableNodeArr[i10] != null && localVariableNodeArr[i10].desc == null) {
                localVariableNodeArr[i10] = null;
            }
        }
        return localVariableNodeArr;
    }

    public static LocalVariableNode getLocalVariableAt(ClassNode classNode, MethodNode methodNode, AbstractInsnNode abstractInsnNode, int i) {
        return getLocalVariableAt(classNode, methodNode, methodNode.instructions.indexOf(abstractInsnNode), i);
    }

    private static LocalVariableNode getLocalVariableAt(ClassNode classNode, MethodNode methodNode, int i, int i2) {
        LocalVariableNode localVariableNode = null;
        LocalVariableNode localVariableNode2 = null;
        for (LocalVariableNode localVariableNode3 : getLocalVariableTable(classNode, methodNode)) {
            if (localVariableNode3.index == i2) {
                if (isOpcodeInRange(methodNode.instructions, localVariableNode3, i)) {
                    localVariableNode = localVariableNode3;
                } else if (localVariableNode == null) {
                    localVariableNode2 = localVariableNode3;
                }
            }
        }
        if (localVariableNode == null && !methodNode.localVariables.isEmpty()) {
            for (LocalVariableNode localVariableNode4 : getGeneratedLocalVariableTable(classNode, methodNode)) {
                if (localVariableNode4.index == i2 && isOpcodeInRange(methodNode.instructions, localVariableNode4, i)) {
                    localVariableNode = localVariableNode4;
                }
            }
        }
        return localVariableNode != null ? localVariableNode : localVariableNode2;
    }

    private static boolean isOpcodeInRange(InsnList insnList, LocalVariableNode localVariableNode, int i) {
        return insnList.indexOf(localVariableNode.start) <= i && insnList.indexOf(localVariableNode.end) > i;
    }

    public static List getLocalVariableTable(ClassNode classNode, MethodNode methodNode) {
        if (methodNode.localVariables.isEmpty()) {
            return getGeneratedLocalVariableTable(classNode, methodNode);
        }
        return methodNode.localVariables;
    }

    public static List getGeneratedLocalVariableTable(ClassNode classNode, MethodNode methodNode) {
        String str = String.format("%s.%s%s", classNode.name, methodNode.name, methodNode.desc);
        List list = (List) calculatedLocalVariables.get(str);
        if (list != null) {
            return list;
        }
        List listGenerateLocalVariableTable = generateLocalVariableTable(classNode, methodNode);
        calculatedLocalVariables.put(str, listGenerateLocalVariableTable);
        return listGenerateLocalVariableTable;
    }

    public static List generateLocalVariableTable(ClassNode classNode, MethodNode methodNode) {
        ArrayList arrayList = null;
        if (classNode.interfaces != null) {
            arrayList = new ArrayList();
            Iterator it = classNode.interfaces.iterator();
            while (it.hasNext()) {
                arrayList.add(Type.getObjectType((String) it.next()));
            }
        }
        Type objectType = null;
        if (classNode.superName != null) {
            objectType = Type.getObjectType(classNode.superName);
        }
        Analyzer analyzer = new Analyzer(new MixinVerifier(ASM.API_VERSION, Type.getObjectType(classNode.name), objectType, arrayList, false));
        try {
            analyzer.analyze(classNode.name, methodNode);
        } catch (AnalyzerException e) {
            e.printStackTrace();
        }
        Frame[] frames = analyzer.getFrames();
        int size = methodNode.instructions.size();
        ArrayList arrayList2 = new ArrayList();
        LocalVariableNode[] localVariableNodeArr = new LocalVariableNode[methodNode.maxLocals];
        BasicValue[] basicValueArr = new BasicValue[methodNode.maxLocals];
        AbstractInsnNode[] abstractInsnNodeArr = new LabelNode[size];
        String[] strArr = new String[methodNode.maxLocals];
        for (int i = 0; i < size; i++) {
            Frame frame = frames[i];
            if (frame != null) {
                LabelNode labelNode = null;
                for (int i2 = 0; i2 < frame.getLocals(); i2++) {
                    BasicValue basicValue = (BasicValue) frame.getLocal(i2);
                    if ((basicValue != null || basicValueArr[i2] != null) && (basicValue == null || !basicValue.equals(basicValueArr[i2]))) {
                        if (labelNode == null) {
                            AbstractInsnNode abstractInsnNode = methodNode.instructions.get(i);
                            if (abstractInsnNode instanceof LabelNode) {
                                labelNode = (LabelNode) abstractInsnNode;
                            } else {
                                LabelNode labelNode2 = new LabelNode();
                                labelNode = labelNode2;
                                abstractInsnNodeArr[i] = labelNode2;
                            }
                        }
                        if (basicValue == null && basicValueArr[i2] != null) {
                            arrayList2.add(localVariableNodeArr[i2]);
                            localVariableNodeArr[i2].end = labelNode;
                            localVariableNodeArr[i2] = null;
                        } else if (basicValue != null) {
                            if (basicValueArr[i2] != null) {
                                arrayList2.add(localVariableNodeArr[i2]);
                                localVariableNodeArr[i2].end = labelNode;
                                localVariableNodeArr[i2] = null;
                            }
                            String descriptor = basicValue.getType() != null ? basicValue.getType().getDescriptor() : strArr[i2];
                            localVariableNodeArr[i2] = new LocalVariableNode("var" + i2, descriptor, (String) null, labelNode, (LabelNode) null, i2);
                            if (descriptor != null) {
                                strArr[i2] = descriptor;
                            }
                        }
                        basicValueArr[i2] = basicValue;
                    }
                }
            }
        }
        AbstractInsnNode labelNode3 = null;
        for (int i3 = 0; i3 < localVariableNodeArr.length; i3++) {
            if (localVariableNodeArr[i3] != null) {
                if (labelNode3 == null) {
                    labelNode3 = new LabelNode();
                    methodNode.instructions.add(labelNode3);
                }
                localVariableNodeArr[i3].end = labelNode3;
                arrayList2.add(localVariableNodeArr[i3]);
            }
        }
        for (int i4 = size - 1; i4 >= 0; i4--) {
            if (abstractInsnNodeArr[i4] != null) {
                methodNode.instructions.insert(methodNode.instructions.get(i4), abstractInsnNodeArr[i4]);
            }
        }
        return arrayList2;
    }

    private static AbstractInsnNode nextNode(InsnList insnList, AbstractInsnNode abstractInsnNode) {
        int iIndexOf = insnList.indexOf(abstractInsnNode) + 1;
        if (iIndexOf > 0 && iIndexOf < insnList.size()) {
            return insnList.get(iIndexOf);
        }
        return abstractInsnNode;
    }

    private static int getAdjustedFrameSize(int i, FrameNode frameNode) {
        int i2 = frameNode.type;
        int iComputeFrameSize = computeFrameSize(frameNode);
        switch (i2) {
            case -1:
            case 0:
                return iComputeFrameSize;
            case 1:
                return i + iComputeFrameSize;
            case 2:
                return i - iComputeFrameSize;
            case 3:
            case 4:
                return i;
            default:
                return i;
        }
    }

    private static int getAdjustedFrameSize(int i, ClassInfo.FrameData frameData) {
        int i2 = frameData.type;
        int i3 = frameData.size;
        switch (i2) {
            case -1:
            case 0:
                return i3;
            case 1:
                return i + i3;
            case 2:
                return i - i3;
            case 3:
            case 4:
                return i;
            default:
                return i;
        }
    }

    public static int computeFrameSize(FrameNode frameNode) {
        if (frameNode.local == null) {
            return 0;
        }
        int i = 0;
        Iterator it = frameNode.local.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof Integer) {
                i += (next == Opcodes.DOUBLE || next == Opcodes.LONG) ? 2 : 1;
            } else {
                i++;
            }
        }
        return i;
    }

    public static String getFrameTypeName(Object obj) {
        if (obj == null) {
            return DateLayout.NULL_DATE_FORMAT;
        }
        if (obj instanceof String) {
            return Bytecode.getSimpleName(obj.toString());
        }
        if (obj instanceof Integer) {
            int iIntValue = ((Integer) obj).intValue();
            if (iIntValue >= FRAME_TYPES.length) {
                return "INVALID";
            }
            return FRAME_TYPES[iIntValue];
        }
        return LocationInfo.f204NA;
    }
}
