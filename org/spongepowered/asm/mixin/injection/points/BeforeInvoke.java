package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import java.util.ListIterator;
import java.util.Locale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

@InjectionPoint.AtCode("INVOKE")
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/points/BeforeInvoke.class */
public class BeforeInvoke extends InjectionPoint {
    protected final ITargetSelector target;
    protected final boolean allowPermissive;
    protected final int ordinal;
    protected final String className;
    protected final IMixinContext context;
    protected final Logger logger;
    private boolean log;

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/points/BeforeInvoke$SearchType.class */
    public enum SearchType {
        STRICT,
        PERMISSIVE
    }

    public BeforeInvoke(InjectionPointData data) {
        super(data);
        this.logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
        this.log = false;
        this.target = data.getTarget();
        this.ordinal = data.getOrdinal();
        this.log = data.get("log", false);
        this.className = getClassName();
        this.context = data.getContext();
        this.allowPermissive = this.context.getOption(MixinEnvironment.Option.REFMAP_REMAP) && this.context.getOption(MixinEnvironment.Option.REFMAP_REMAP_ALLOW_PERMISSIVE) && !this.context.getReferenceMapper().isDefault();
    }

    private String getClassName() {
        InjectionPoint.AtCode atCode = (InjectionPoint.AtCode) getClass().getAnnotation(InjectionPoint.AtCode.class);
        Object[] objArr = new Object[1];
        objArr[0] = atCode != null ? atCode.value() : getClass().getSimpleName().toUpperCase(Locale.ROOT);
        return String.format("@At(%s)", objArr);
    }

    public BeforeInvoke setLogging(boolean logging) {
        this.log = logging;
        return this;
    }

    @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
    public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
        log("{} is searching for an injection point in method with descriptor {}", this.className, desc);
        boolean hasDescriptor = (this.target instanceof ITargetSelectorByName) && ((ITargetSelectorByName) this.target).getDesc() == null;
        boolean found = find(desc, insns, nodes, this.target, SearchType.STRICT);
        if (!found && hasDescriptor && this.allowPermissive) {
            this.logger.warn("STRICT match for {} using \"{}\" in {} returned 0 results, attempting permissive search. To inhibit permissive search set mixin.env.allowPermissiveMatch=false", new Object[]{this.className, this.target, this.context});
            found = find(desc, insns, nodes, this.target, SearchType.PERMISSIVE);
        }
        return found;
    }

    protected boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes, ITargetSelector selector, SearchType searchType) {
        if (selector == null) {
            return false;
        }
        ITargetSelector target = searchType == SearchType.PERMISSIVE ? selector.configure(new String[]{"permissive"}) : selector;
        int ordinal = 0;
        int found = 0;
        ListIterator<AbstractInsnNode> iter = insns.iterator();
        while (iter.hasNext()) {
            AbstractInsnNode insn = iter.next();
            if (matchesInsn(insn)) {
                MemberInfo nodeInfo = new MemberInfo(insn);
                log("{} is considering insn {}", this.className, nodeInfo);
                if (target.match(insn).isExactMatch()) {
                    log("{} > found a matching insn, checking preconditions...", this.className);
                    if (matchesOrdinal(ordinal)) {
                        log("{} > > > found a matching insn at ordinal {}", this.className, Integer.valueOf(ordinal));
                        if (addInsn(insns, nodes, insn)) {
                            found++;
                        }
                        if (this.ordinal == ordinal) {
                            break;
                        }
                    }
                    ordinal++;
                } else {
                    continue;
                }
            }
            inspectInsn(desc, insns, insn);
        }
        if (searchType == SearchType.PERMISSIVE && found > 1) {
            this.logger.warn("A permissive match for {} using \"{}\" in {} matched {} instructions, this may cause unexpected behaviour. To inhibit permissive search set mixin.env.allowPermissiveMatch=false", new Object[]{this.className, selector, this.context, Integer.valueOf(found)});
        }
        return found > 0;
    }

    protected boolean addInsn(InsnList insns, Collection<AbstractInsnNode> nodes, AbstractInsnNode insn) {
        nodes.add(insn);
        return true;
    }

    protected boolean matchesInsn(AbstractInsnNode insn) {
        return insn instanceof MethodInsnNode;
    }

    protected void inspectInsn(String desc, InsnList insns, AbstractInsnNode insn) {
    }

    protected boolean matchesOrdinal(int ordinal) {
        log("{} > > comparing target ordinal {} with current ordinal {}", this.className, Integer.valueOf(this.ordinal), Integer.valueOf(ordinal));
        return this.ordinal == -1 || this.ordinal == ordinal;
    }

    protected void log(String message, Object... params) {
        if (this.log) {
            this.logger.info(message, params);
        }
    }
}
