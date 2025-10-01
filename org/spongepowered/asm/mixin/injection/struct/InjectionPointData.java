package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
import org.spongepowered.asm.mixin.injection.selectors.TargetSelector;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionPointException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/InjectionPointData.class */
public class InjectionPointData {
    private static final Pattern AT_PATTERN = createPattern();
    private final Map args = new HashMap();
    private final IMixinContext context;
    private final MethodNode method;
    private final AnnotationNode parent;

    /* renamed from: at */
    private final String f220at;
    private final String type;
    private final InjectionPoint.Selector selector;
    private final String target;
    private final String slice;
    private final int ordinal;
    private final int opcode;

    /* renamed from: id */
    private final String f221id;

    public InjectionPointData(IMixinContext iMixinContext, MethodNode methodNode, AnnotationNode annotationNode, String str, List list, String str2, String str3, int i, int i2, String str4) {
        this.context = iMixinContext;
        this.method = methodNode;
        this.parent = annotationNode;
        this.f220at = str;
        this.target = str2;
        this.slice = Strings.nullToEmpty(str3);
        this.ordinal = Math.max(-1, i);
        this.opcode = i2;
        this.f221id = str4;
        parseArgs(list);
        this.args.put("target", str2);
        this.args.put("ordinal", String.valueOf(i));
        this.args.put("opcode", String.valueOf(i2));
        Matcher matcher = AT_PATTERN.matcher(str);
        this.type = parseType(matcher, str);
        this.selector = parseSelector(matcher);
    }

    private void parseArgs(List list) {
        if (list == null) {
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str != null) {
                int iIndexOf = str.indexOf(61);
                if (iIndexOf > -1) {
                    this.args.put(str.substring(0, iIndexOf), str.substring(iIndexOf + 1));
                } else {
                    this.args.put(str, "");
                }
            }
        }
    }

    public String getAt() {
        return this.f220at;
    }

    public String getType() {
        return this.type;
    }

    public InjectionPoint.Selector getSelector() {
        return this.selector;
    }

    public IMixinContext getContext() {
        return this.context;
    }

    public MethodNode getMethod() {
        return this.method;
    }

    public Type getMethodReturnType() {
        return Type.getReturnType(this.method.desc);
    }

    public AnnotationNode getParent() {
        return this.parent;
    }

    public String getSlice() {
        return this.slice;
    }

    public LocalVariableDiscriminator getLocalVariableDiscriminator() {
        return LocalVariableDiscriminator.parse(this.parent);
    }

    public String get(String str, String str2) {
        String str3 = (String) this.args.get(str);
        return str3 != null ? str3 : str2;
    }

    public int get(String str, int i) {
        return parseInt(get(str, String.valueOf(i)), i);
    }

    public boolean get(String str, boolean z) {
        return parseBoolean(get(str, String.valueOf(z)), z);
    }

    public ITargetSelector get(String str) {
        try {
            return TargetSelector.parseAndValidate(get(str, ""), this.context);
        } catch (InvalidMemberDescriptorException unused) {
            throw new InvalidInjectionPointException(this.context, "Failed parsing @At(\"%s\").%s descriptor \"%s\" on %s", new Object[]{this.f220at, str, this.target, getDescription()});
        }
    }

    public ITargetSelector getTarget() {
        try {
            return TargetSelector.parseAndValidate(this.target, this.context);
        } catch (InvalidMemberDescriptorException unused) {
            throw new InvalidInjectionPointException(this.context, "Failed parsing @At(\"%s\").target descriptor \"%s\" on %s", new Object[]{this.f220at, this.target, getDescription()});
        }
    }

    public String getDescription() {
        return InjectionInfo.describeInjector(this.context, this.parent, this.method);
    }

    public int getOrdinal() {
        return this.ordinal;
    }

    public int getOpcode() {
        return this.opcode;
    }

    public int getOpcode(int i) {
        return this.opcode > 0 ? this.opcode : i;
    }

    public int getOpcode(int i, int[] iArr) {
        for (int i2 : iArr) {
            if (this.opcode == i2) {
                return this.opcode;
            }
        }
        return i;
    }

    public String getId() {
        return this.f221id;
    }

    public String toString() {
        return this.type;
    }

    private static Pattern createPattern() {
        return Pattern.compile(String.format("^([^:]+):?(%s)?$", Joiner.on('|').join(InjectionPoint.Selector.values())));
    }

    public static String parseType(String str) {
        return parseType(AT_PATTERN.matcher(str), str);
    }

    private static String parseType(Matcher matcher, String str) {
        return matcher.matches() ? matcher.group(1) : str;
    }

    private static InjectionPoint.Selector parseSelector(Matcher matcher) {
        return (!matcher.matches() || matcher.group(2) == null) ? InjectionPoint.Selector.DEFAULT : InjectionPoint.Selector.valueOf(matcher.group(2));
    }

    private static int parseInt(String str, int i) {
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return i;
        }
    }

    private static boolean parseBoolean(String str, boolean z) {
        try {
            return Boolean.parseBoolean(str);
        } catch (Exception unused) {
            return z;
        }
    }
}
