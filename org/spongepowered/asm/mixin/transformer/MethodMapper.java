package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.MixinInfo;
import org.spongepowered.asm.util.Counter;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MethodMapper.class */
public class MethodMapper {
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private static final List classes = new ArrayList();
    private static final Map methods = new HashMap();
    private final ClassInfo info;
    private int nextUniqueMethodIndex;
    private int nextUniqueFieldIndex;

    public MethodMapper(MixinEnvironment mixinEnvironment, ClassInfo classInfo) {
        this.info = classInfo;
    }

    public ClassInfo getClassInfo() {
        return this.info;
    }

    public void remapHandlerMethod(MixinInfo mixinInfo, MethodNode methodNode, ClassInfo.Method method) {
        if (!(methodNode instanceof MixinInfo.MixinMethodNode) || !((MixinInfo.MixinMethodNode) methodNode).isInjector()) {
            return;
        }
        if (method.isUnique()) {
            logger.warn("Redundant @Unique on injector method {} in {}. Injectors are implicitly unique", new Object[]{method, mixinInfo});
        }
        if (method.isRenamed()) {
            methodNode.name = method.getName();
        } else {
            methodNode.name = method.conform(getHandlerName((MixinInfo.MixinMethodNode) methodNode));
        }
    }

    public String getHandlerName(MixinInfo.MixinMethodNode mixinMethodNode) {
        return String.format("%s$%s%s$%s", InjectionInfo.getInjectorPrefix(mixinMethodNode.getInjectorAnnotation()), getClassUID(mixinMethodNode.getOwner().getClassRef()), getMethodUID(mixinMethodNode.name, mixinMethodNode.desc, !mixinMethodNode.isSurrogate()), mixinMethodNode.name);
    }

    public String getUniqueName(MethodNode methodNode, String str, boolean z) {
        int i = this.nextUniqueMethodIndex;
        this.nextUniqueMethodIndex = i + 1;
        return String.format(z ? "%2$s_$md$%1$s$%3$s" : "md%s$%s$%s", str.substring(30), methodNode.name, Integer.toHexString(i));
    }

    public String getUniqueName(FieldNode fieldNode, String str) {
        int i = this.nextUniqueFieldIndex;
        this.nextUniqueFieldIndex = i + 1;
        return String.format("fd%s$%s$%s", str.substring(30), fieldNode.name, Integer.toHexString(i));
    }

    private static String getClassUID(String str) {
        int iIndexOf = classes.indexOf(str);
        if (iIndexOf < 0) {
            iIndexOf = classes.size();
            classes.add(str);
        }
        return finagle(iIndexOf);
    }

    private static String getMethodUID(String str, String str2, boolean z) {
        String str3 = String.format("%s%s", str, str2);
        Counter counter = (Counter) methods.get(str3);
        if (counter == null) {
            counter = new Counter();
            methods.put(str3, counter);
        } else if (z) {
            counter.value++;
        }
        return String.format("%03x", Integer.valueOf(counter.value));
    }

    private static String finagle(int i) {
        String hexString = Integer.toHexString(i);
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < hexString.length(); i2++) {
            char cCharAt = hexString.charAt(i2);
            sb.append((char) (cCharAt + (cCharAt < ':' ? '1' : '\n')));
        }
        return Strings.padStart(sb.toString(), 3, 'z');
    }
}
