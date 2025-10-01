package org.spongepowered.asm.mixin.injection.points;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Constants;

@InjectionPoint.AtCode("CONSTANT")
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/points/BeforeConstant.class */
public class BeforeConstant extends InjectionPoint {
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private final int ordinal;
    private final boolean nullValue;
    private final Integer intValue;
    private final Float floatValue;
    private final Long longValue;
    private final Double doubleValue;
    private final String stringValue;
    private final Type typeValue;
    private final int[] expandOpcodes;
    private final boolean expand;
    private final String matchByType;
    private final boolean log;

    public BeforeConstant(IMixinContext iMixinContext, AnnotationNode annotationNode, String str) {
        super((String) Annotations.getValue(annotationNode, "slice", ""), InjectionPoint.Selector.DEFAULT, null);
        Boolean bool = (Boolean) Annotations.getValue(annotationNode, "nullValue", (Boolean) null);
        this.ordinal = ((Integer) Annotations.getValue(annotationNode, "ordinal", (Object) (-1))).intValue();
        this.nullValue = bool != null && bool.booleanValue();
        this.intValue = (Integer) Annotations.getValue(annotationNode, "intValue", (Integer) null);
        this.floatValue = (Float) Annotations.getValue(annotationNode, "floatValue", (Float) null);
        this.longValue = (Long) Annotations.getValue(annotationNode, "longValue", (Long) null);
        this.doubleValue = (Double) Annotations.getValue(annotationNode, "doubleValue", (Double) null);
        this.stringValue = (String) Annotations.getValue(annotationNode, "stringValue", (String) null);
        this.typeValue = (Type) Annotations.getValue(annotationNode, "classValue", (Type) null);
        this.matchByType = validateDiscriminator(iMixinContext, str, bool, "on @Constant annotation");
        this.expandOpcodes = parseExpandOpcodes(Annotations.getValue(annotationNode, "expandZeroConditions", true, Constant.Condition.class));
        this.expand = this.expandOpcodes.length > 0;
        this.log = ((Boolean) Annotations.getValue(annotationNode, "log", Boolean.FALSE)).booleanValue();
    }

    public BeforeConstant(InjectionPointData injectionPointData) {
        super(injectionPointData);
        String str = injectionPointData.get("nullValue", (String) null);
        Boolean boolValueOf = str != null ? Boolean.valueOf(Boolean.parseBoolean(str)) : null;
        this.ordinal = injectionPointData.getOrdinal();
        this.nullValue = boolValueOf != null && boolValueOf.booleanValue();
        this.intValue = Ints.tryParse(injectionPointData.get("intValue", ""));
        this.floatValue = Floats.tryParse(injectionPointData.get("floatValue", ""));
        this.longValue = Longs.tryParse(injectionPointData.get("longValue", ""));
        this.doubleValue = Doubles.tryParse(injectionPointData.get("doubleValue", ""));
        this.stringValue = injectionPointData.get("stringValue", (String) null);
        String str2 = injectionPointData.get("classValue", (String) null);
        this.typeValue = str2 != null ? Type.getObjectType(str2.replace('.', '/')) : null;
        this.matchByType = validateDiscriminator(injectionPointData.getContext(), "V", boolValueOf, "in @At(\"CONSTANT\") args");
        if ("V".equals(this.matchByType)) {
            throw new InvalidInjectionException(injectionPointData.getContext(), "No constant discriminator could be parsed in @At(\"CONSTANT\") args");
        }
        ArrayList arrayList = new ArrayList();
        String lowerCase = injectionPointData.get("expandZeroConditions", "").toLowerCase(Locale.ROOT);
        for (Constant.Condition condition : Constant.Condition.values()) {
            if (lowerCase.contains(condition.name().toLowerCase(Locale.ROOT))) {
                arrayList.add(condition);
            }
        }
        this.expandOpcodes = parseExpandOpcodes(arrayList);
        this.expand = this.expandOpcodes.length > 0;
        this.log = injectionPointData.get("log", false);
    }

    private String validateDiscriminator(IMixinContext iMixinContext, String str, Boolean bool, String str2) {
        int i = 0;
        for (Object obj : new Object[]{bool, this.intValue, this.floatValue, this.longValue, this.doubleValue, this.stringValue, this.typeValue}) {
            if (obj != null) {
                i++;
            }
        }
        int i2 = i;
        if (i2 == 1) {
            str = null;
        } else if (i2 > 1) {
            throw new InvalidInjectionException(iMixinContext, "Conflicting constant discriminators specified " + str2 + " for " + iMixinContext);
        }
        return str;
    }

    private int[] parseExpandOpcodes(List list) {
        HashSet hashSet = new HashSet();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            for (int i : ((Constant.Condition) it.next()).getEquivalentCondition().getOpcodes()) {
                hashSet.add(Integer.valueOf(i));
            }
        }
        return Ints.toArray(hashSet);
    }

    @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
    public boolean find(String str, InsnList insnList, Collection collection) {
        boolean z = false;
        log("BeforeConstant is searching for constants in method with descriptor {}", new Object[]{str});
        ListIterator it = insnList.iterator();
        int i = 0;
        int opcode = 0;
        while (it.hasNext()) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode) it.next();
            if (this.expand ? matchesConditionalInsn(opcode, abstractInsnNode) : matchesConstantInsn(abstractInsnNode)) {
                Object[] objArr = new Object[2];
                objArr[0] = this.matchByType != null ? " TYPE" : " value";
                objArr[1] = Integer.valueOf(i);
                log("    BeforeConstant found a matching constant{} at ordinal {}", objArr);
                if (this.ordinal == -1 || this.ordinal == i) {
                    log("      BeforeConstant found {}", new Object[]{Bytecode.describeNode(abstractInsnNode).trim()});
                    collection.add(abstractInsnNode);
                    z = true;
                }
                i++;
            }
            if (!(abstractInsnNode instanceof LabelNode) && !(abstractInsnNode instanceof FrameNode)) {
                opcode = abstractInsnNode.getOpcode();
            }
        }
        return z;
    }

    private boolean matchesConditionalInsn(int i, AbstractInsnNode abstractInsnNode) {
        for (int i2 : this.expandOpcodes) {
            int opcode = abstractInsnNode.getOpcode();
            if (opcode == i2) {
                if (i == 148 || i == 149 || i == 150 || i == 151 || i == 152) {
                    log("  BeforeConstant is ignoring {} following {}", new Object[]{Bytecode.getOpcodeName(opcode), Bytecode.getOpcodeName(i)});
                    return false;
                }
                log("  BeforeConstant found {} instruction", new Object[]{Bytecode.getOpcodeName(opcode)});
                return true;
            }
        }
        if (this.intValue != null && this.intValue.intValue() == 0 && Bytecode.isConstant(abstractInsnNode)) {
            Object constant = Bytecode.getConstant(abstractInsnNode);
            log("  BeforeConstant found INTEGER constant: value = {}", new Object[]{constant});
            return (constant instanceof Integer) && ((Integer) constant).intValue() == 0;
        }
        return false;
    }

    private boolean matchesConstantInsn(AbstractInsnNode abstractInsnNode) {
        if (!Bytecode.isConstant(abstractInsnNode)) {
            return false;
        }
        Object constant = Bytecode.getConstant(abstractInsnNode);
        if (constant == null) {
            log("  BeforeConstant found NULL constant: nullValue = {}", new Object[]{Boolean.valueOf(this.nullValue)});
            return this.nullValue || Constants.OBJECT_DESC.equals(this.matchByType);
        }
        if (constant instanceof Integer) {
            log("  BeforeConstant found INTEGER constant: value = {}, intValue = {}", new Object[]{constant, this.intValue});
            return constant.equals(this.intValue) || "I".equals(this.matchByType);
        }
        if (constant instanceof Float) {
            log("  BeforeConstant found FLOAT constant: value = {}, floatValue = {}", new Object[]{constant, this.floatValue});
            return constant.equals(this.floatValue) || "F".equals(this.matchByType);
        }
        if (constant instanceof Long) {
            log("  BeforeConstant found LONG constant: value = {}, longValue = {}", new Object[]{constant, this.longValue});
            return constant.equals(this.longValue) || "J".equals(this.matchByType);
        }
        if (constant instanceof Double) {
            log("  BeforeConstant found DOUBLE constant: value = {}, doubleValue = {}", new Object[]{constant, this.doubleValue});
            return constant.equals(this.doubleValue) || "D".equals(this.matchByType);
        }
        if (constant instanceof String) {
            log("  BeforeConstant found STRING constant: value = {}, stringValue = {}", new Object[]{constant, this.stringValue});
            return constant.equals(this.stringValue) || Constants.STRING_DESC.equals(this.matchByType);
        }
        if (constant instanceof Type) {
            log("  BeforeConstant found CLASS constant: value = {}, typeValue = {}", new Object[]{constant, this.typeValue});
            return constant.equals(this.typeValue) || Constants.CLASS_DESC.equals(this.matchByType);
        }
        return false;
    }

    protected void log(String str, Object[] objArr) {
        if (this.log) {
            logger.info(str, objArr);
        }
    }
}
