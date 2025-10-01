package org.spongepowered.asm.mixin.injection.invoke.arg;

import java.util.HashMap;
import java.util.Map;
import kotlin.text.Typography;
import org.apache.log4j.net.SyslogAppender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.CheckClassAdapter;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.SyntheticClassInfo;
import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
import org.spongepowered.asm.service.ISyntheticClassInfo;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.IConsumer;
import org.spongepowered.asm.util.SignaturePrinter;
import org.spongepowered.asm.util.asm.MethodVisitorEx;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/arg/ArgsClassGenerator.class */
public final class ArgsClassGenerator implements IClassGenerator {
    public static final String GETTER_PREFIX = "$";
    private static final String CLASS_NAME_BASE = "org.spongepowered.asm.synthetic.args.Args$";
    private static final String OBJECT = "java/lang/Object";
    private static final String OBJECT_ARRAY = "[Ljava/lang/Object;";
    private static final String VALUES_FIELD = "values";
    private static final String CTOR_DESC = "([Ljava/lang/Object;)V";
    private static final String SET = "set";
    private static final String SET_DESC = "(ILjava/lang/Object;)V";
    private static final String SETALL = "setAll";
    private static final String SETALL_DESC = "([Ljava/lang/Object;)V";
    private static final String NPE = "java/lang/NullPointerException";
    private static final String NPE_CTOR_DESC = "(Ljava/lang/String;)V";
    private static final String AIOOBE = "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException";
    private static final String AIOOBE_CTOR_DESC = "(I)V";
    private static final String ACE = "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException";
    private static final String ACE_CTOR_DESC = "(IILjava/lang/String;)V";
    private final IConsumer registry;
    private int nextIndex = 1;
    private final Map descToClass = new HashMap();
    private final Map nameToClass = new HashMap();
    public static final String ARGS_NAME = Args.class.getName();
    public static final String ARGS_REF = ARGS_NAME.replace('.', '/');
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/arg/ArgsClassGenerator$ArgsClassInfo.class */
    class ArgsClassInfo extends SyntheticClassInfo {
        final String desc;
        final Type[] args;
        int loaded;
        final ArgsClassGenerator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ArgsClassInfo(ArgsClassGenerator argsClassGenerator, IMixinInfo iMixinInfo, String str, String str2) {
            super(iMixinInfo, str);
            this.this$0 = argsClassGenerator;
            this.loaded = 0;
            this.desc = str2;
            this.args = Type.getArgumentTypes(str2);
        }

        @Override // org.spongepowered.asm.service.ISyntheticClassInfo
        public boolean isLoaded() {
            return this.loaded > 0;
        }

        String getSignature() {
            return new SignaturePrinter("", (Type) null, this.args).setFullyQualified(true).getFormattedArgs();
        }
    }

    public ArgsClassGenerator(IConsumer iConsumer) {
        this.registry = iConsumer;
    }

    public ISyntheticClassInfo getArgsClass(String str, IMixinInfo iMixinInfo) {
        String strChangeDescriptorReturnType = Bytecode.changeDescriptorReturnType(str, "V");
        ArgsClassInfo argsClassInfo = (ArgsClassInfo) this.descToClass.get(strChangeDescriptorReturnType);
        if (argsClassInfo == null) {
            int i = this.nextIndex;
            this.nextIndex = i + 1;
            String str2 = String.format("%s%d", CLASS_NAME_BASE, Integer.valueOf(i));
            logger.debug("ArgsClassGenerator assigning {} for descriptor {}", new Object[]{str2, strChangeDescriptorReturnType});
            argsClassInfo = new ArgsClassInfo(this, iMixinInfo, str2, strChangeDescriptorReturnType);
            this.descToClass.put(strChangeDescriptorReturnType, argsClassInfo);
            this.nameToClass.put(str2, argsClassInfo);
            this.registry.accept(argsClassInfo);
        }
        return argsClassInfo;
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IClassGenerator
    public boolean generate(String str, ClassNode classNode) {
        ArgsClassInfo argsClassInfo = (ArgsClassInfo) this.nameToClass.get(str);
        if (argsClassInfo == null) {
            return false;
        }
        if (argsClassInfo.loaded > 0) {
            logger.debug("ArgsClassGenerator is re-generating {}, already did this {} times!", new Object[]{str, Integer.valueOf(argsClassInfo.loaded)});
        }
        ClassNode checkClassAdapter = classNode;
        if (MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
            checkClassAdapter = new CheckClassAdapter(classNode);
        }
        checkClassAdapter.visit(50, 4129, argsClassInfo.getName(), (String) null, ARGS_REF, (String[]) null);
        checkClassAdapter.visitSource(str.substring(str.lastIndexOf(46) + 1) + ".java", (String) null);
        generateCtor(argsClassInfo, checkClassAdapter);
        generateToString(argsClassInfo, checkClassAdapter);
        generateFactory(argsClassInfo, checkClassAdapter);
        generateSetters(argsClassInfo, checkClassAdapter);
        generateGetters(argsClassInfo, checkClassAdapter);
        checkClassAdapter.visitEnd();
        argsClassInfo.loaded++;
        return true;
    }

    private void generateCtor(ArgsClassInfo argsClassInfo, ClassVisitor classVisitor) {
        MethodVisitor methodVisitorVisitMethod = classVisitor.visitMethod(2, Constants.CTOR, "([Ljava/lang/Object;)V", (String) null, (String[]) null);
        methodVisitorVisitMethod.visitCode();
        methodVisitorVisitMethod.visitVarInsn(25, 0);
        methodVisitorVisitMethod.visitVarInsn(25, 1);
        methodVisitorVisitMethod.visitMethodInsn(Typography.middleDot, ARGS_REF, Constants.CTOR, "([Ljava/lang/Object;)V", false);
        methodVisitorVisitMethod.visitInsn(Typography.plusMinus);
        methodVisitorVisitMethod.visitMaxs(2, 2);
        methodVisitorVisitMethod.visitEnd();
    }

    private void generateToString(ArgsClassInfo argsClassInfo, ClassVisitor classVisitor) {
        MethodVisitor methodVisitorVisitMethod = classVisitor.visitMethod(1, "toString", "()Ljava/lang/String;", (String) null, (String[]) null);
        methodVisitorVisitMethod.visitCode();
        methodVisitorVisitMethod.visitLdcInsn("Args" + argsClassInfo.getSignature());
        methodVisitorVisitMethod.visitInsn(176);
        methodVisitorVisitMethod.visitMaxs(1, 1);
        methodVisitorVisitMethod.visitEnd();
    }

    private void generateFactory(ArgsClassInfo argsClassInfo, ClassVisitor classVisitor) {
        String name = argsClassInfo.getName();
        MethodVisitorEx methodVisitorEx = new MethodVisitorEx(classVisitor.visitMethod(9, "of", Bytecode.changeDescriptorReturnType(argsClassInfo.desc, "L" + name + ";"), (String) null, (String[]) null));
        methodVisitorEx.visitCode();
        methodVisitorEx.visitTypeInsn(Typography.rightGuillemete, name);
        methodVisitorEx.visitInsn(89);
        methodVisitorEx.visitConstant((byte) argsClassInfo.args.length);
        methodVisitorEx.visitTypeInsn(Typography.half, "java/lang/Object");
        byte size = 0;
        for (byte b = 0; b < argsClassInfo.args.length; b = (byte) (b + 1)) {
            Type type = argsClassInfo.args[b];
            methodVisitorEx.visitInsn(89);
            methodVisitorEx.visitConstant(b);
            methodVisitorEx.visitVarInsn(type.getOpcode(21), size);
            box(methodVisitorEx, type);
            methodVisitorEx.visitInsn(83);
            size = (byte) (size + type.getSize());
        }
        methodVisitorEx.visitMethodInsn(Typography.middleDot, name, Constants.CTOR, "([Ljava/lang/Object;)V", false);
        methodVisitorEx.visitInsn(176);
        methodVisitorEx.visitMaxs(6, Bytecode.getArgsSize(argsClassInfo.args));
        methodVisitorEx.visitEnd();
    }

    private void generateGetters(ArgsClassInfo argsClassInfo, ClassVisitor classVisitor) {
        byte b = 0;
        for (Type type : argsClassInfo.args) {
            MethodVisitorEx methodVisitorEx = new MethodVisitorEx(classVisitor.visitMethod(1, GETTER_PREFIX + ((int) b), "()" + type.getDescriptor(), (String) null, (String[]) null));
            methodVisitorEx.visitCode();
            methodVisitorEx.visitVarInsn(25, 0);
            methodVisitorEx.visitFieldInsn(180, argsClassInfo.getName(), VALUES_FIELD, OBJECT_ARRAY);
            methodVisitorEx.visitConstant(b);
            methodVisitorEx.visitInsn(50);
            unbox(methodVisitorEx, type);
            methodVisitorEx.visitInsn(type.getOpcode(172));
            methodVisitorEx.visitMaxs(2, 1);
            methodVisitorEx.visitEnd();
            b = (byte) (b + 1);
        }
    }

    private void generateSetters(ArgsClassInfo argsClassInfo, ClassVisitor classVisitor) {
        generateIndexedSetter(argsClassInfo, classVisitor);
        generateMultiSetter(argsClassInfo, classVisitor);
    }

    private void generateIndexedSetter(ArgsClassInfo argsClassInfo, ClassVisitor classVisitor) {
        MethodVisitorEx methodVisitorEx = new MethodVisitorEx(classVisitor.visitMethod(1, "set", SET_DESC, (String) null, (String[]) null));
        methodVisitorEx.visitCode();
        Label label = new Label();
        Label label2 = new Label();
        Label[] labelArr = new Label[argsClassInfo.args.length];
        for (int i = 0; i < labelArr.length; i++) {
            labelArr[i] = new Label();
        }
        methodVisitorEx.visitVarInsn(25, 0);
        methodVisitorEx.visitFieldInsn(180, argsClassInfo.getName(), VALUES_FIELD, OBJECT_ARRAY);
        byte b = 0;
        while (true) {
            byte b2 = b;
            if (b2 >= argsClassInfo.args.length) {
                break;
            }
            methodVisitorEx.visitVarInsn(21, 1);
            methodVisitorEx.visitConstant(b2);
            methodVisitorEx.visitJumpInsn(159, labelArr[b2]);
            b = (byte) (b2 + 1);
        }
        throwAIOOBE(methodVisitorEx, 1);
        for (int i2 = 0; i2 < argsClassInfo.args.length; i2++) {
            String boxingType = Bytecode.getBoxingType(argsClassInfo.args[i2]);
            methodVisitorEx.visitLabel(labelArr[i2]);
            methodVisitorEx.visitVarInsn(21, 1);
            methodVisitorEx.visitVarInsn(25, 2);
            methodVisitorEx.visitTypeInsn(192, boxingType != null ? boxingType : argsClassInfo.args[i2].getInternalName());
            methodVisitorEx.visitJumpInsn(Typography.section, boxingType != null ? label2 : label);
        }
        methodVisitorEx.visitLabel(label2);
        methodVisitorEx.visitInsn(89);
        methodVisitorEx.visitJumpInsn(199, label);
        throwNPE(methodVisitorEx, "Argument with primitive type cannot be set to NULL");
        methodVisitorEx.visitLabel(label);
        methodVisitorEx.visitInsn(83);
        methodVisitorEx.visitInsn(Typography.plusMinus);
        methodVisitorEx.visitMaxs(6, 3);
        methodVisitorEx.visitEnd();
    }

    private void generateMultiSetter(ArgsClassInfo argsClassInfo, ClassVisitor classVisitor) {
        MethodVisitorEx methodVisitorEx = new MethodVisitorEx(classVisitor.visitMethod(1, SETALL, "([Ljava/lang/Object;)V", (String) null, (String[]) null));
        methodVisitorEx.visitCode();
        Label label = new Label();
        Label label2 = new Label();
        int i = 6;
        methodVisitorEx.visitVarInsn(25, 1);
        methodVisitorEx.visitInsn(190);
        methodVisitorEx.visitInsn(89);
        methodVisitorEx.visitConstant((byte) argsClassInfo.args.length);
        methodVisitorEx.visitJumpInsn(159, label);
        methodVisitorEx.visitTypeInsn(Typography.rightGuillemete, ACE);
        methodVisitorEx.visitInsn(89);
        methodVisitorEx.visitInsn(93);
        methodVisitorEx.visitInsn(88);
        methodVisitorEx.visitConstant((byte) argsClassInfo.args.length);
        methodVisitorEx.visitLdcInsn(argsClassInfo.getSignature());
        methodVisitorEx.visitMethodInsn(Typography.middleDot, ACE, Constants.CTOR, ACE_CTOR_DESC, false);
        methodVisitorEx.visitInsn(191);
        methodVisitorEx.visitLabel(label);
        methodVisitorEx.visitInsn(87);
        methodVisitorEx.visitVarInsn(25, 0);
        methodVisitorEx.visitFieldInsn(180, argsClassInfo.getName(), VALUES_FIELD, OBJECT_ARRAY);
        byte b = 0;
        while (true) {
            byte b2 = b;
            if (b2 < argsClassInfo.args.length) {
                methodVisitorEx.visitInsn(89);
                methodVisitorEx.visitConstant(b2);
                methodVisitorEx.visitVarInsn(25, 1);
                methodVisitorEx.visitConstant(b2);
                methodVisitorEx.visitInsn(50);
                String boxingType = Bytecode.getBoxingType(argsClassInfo.args[b2]);
                methodVisitorEx.visitTypeInsn(192, boxingType != null ? boxingType : argsClassInfo.args[b2].getInternalName());
                if (boxingType != null) {
                    methodVisitorEx.visitInsn(89);
                    methodVisitorEx.visitJumpInsn(198, label2);
                    i = 7;
                }
                methodVisitorEx.visitInsn(83);
                b = (byte) (b2 + 1);
            } else {
                methodVisitorEx.visitInsn(Typography.plusMinus);
                methodVisitorEx.visitLabel(label2);
                throwNPE(methodVisitorEx, "Argument with primitive type cannot be set to NULL");
                methodVisitorEx.visitInsn(Typography.plusMinus);
                methodVisitorEx.visitMaxs(i, 2);
                methodVisitorEx.visitEnd();
                return;
            }
        }
    }

    private static void throwNPE(MethodVisitorEx methodVisitorEx, String str) {
        methodVisitorEx.visitTypeInsn(Typography.rightGuillemete, NPE);
        methodVisitorEx.visitInsn(89);
        methodVisitorEx.visitLdcInsn(str);
        methodVisitorEx.visitMethodInsn(Typography.middleDot, NPE, Constants.CTOR, NPE_CTOR_DESC, false);
        methodVisitorEx.visitInsn(191);
    }

    private static void throwAIOOBE(MethodVisitorEx methodVisitorEx, int i) {
        methodVisitorEx.visitTypeInsn(Typography.rightGuillemete, AIOOBE);
        methodVisitorEx.visitInsn(89);
        methodVisitorEx.visitVarInsn(21, i);
        methodVisitorEx.visitMethodInsn(Typography.middleDot, AIOOBE, Constants.CTOR, AIOOBE_CTOR_DESC, false);
        methodVisitorEx.visitInsn(191);
    }

    private static void box(MethodVisitor methodVisitor, Type type) {
        String boxingType = Bytecode.getBoxingType(type);
        if (boxingType != null) {
            methodVisitor.visitMethodInsn(SyslogAppender.LOG_LOCAL7, boxingType, "valueOf", String.format("(%s)L%s;", type.getDescriptor(), boxingType), false);
        }
    }

    private static void unbox(MethodVisitor methodVisitor, Type type) {
        String boxingType = Bytecode.getBoxingType(type);
        if (boxingType != null) {
            String unboxingMethod = Bytecode.getUnboxingMethod(type);
            String str = "()" + type.getDescriptor();
            methodVisitor.visitTypeInsn(192, boxingType);
            methodVisitor.visitMethodInsn(Typography.paragraph, boxingType, unboxingMethod, str, false);
            return;
        }
        methodVisitor.visitTypeInsn(192, type.getInternalName());
    }
}
