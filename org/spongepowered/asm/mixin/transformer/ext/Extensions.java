package org.spongepowered.asm.mixin.transformer.ext;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.service.ISyntheticClassRegistry;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ext/Extensions.class */
public final class Extensions implements IExtensionRegistry {
    private final ISyntheticClassRegistry syntheticClassRegistry;
    private final List extensions = new ArrayList();
    private final Map extensionMap = new HashMap();
    private final List generators = new ArrayList();
    private final List generatorsView = Collections.unmodifiableList(this.generators);
    private final Map generatorMap = new HashMap();
    private List activeExtensions = Collections.emptyList();

    public Extensions(ISyntheticClassRegistry iSyntheticClassRegistry) {
        this.syntheticClassRegistry = iSyntheticClassRegistry;
    }

    public void add(IExtension iExtension) {
        this.extensions.add(iExtension);
        this.extensionMap.put(iExtension.getClass(), iExtension);
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IExtensionRegistry
    public List getExtensions() {
        return Collections.unmodifiableList(this.extensions);
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IExtensionRegistry
    public List getActiveExtensions() {
        return this.activeExtensions;
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IExtensionRegistry
    public IExtension getExtension(Class cls) {
        return (IExtension) lookup(cls, this.extensionMap, this.extensions);
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IExtensionRegistry
    public ISyntheticClassRegistry getSyntheticClassRegistry() {
        return this.syntheticClassRegistry;
    }

    public void select(MixinEnvironment mixinEnvironment) {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (IExtension iExtension : this.extensions) {
            if (iExtension.checkActive(mixinEnvironment)) {
                builder.add(iExtension);
            }
        }
        this.activeExtensions = builder.build();
    }

    public void preApply(ITargetClassContext iTargetClassContext) {
        Iterator it = this.activeExtensions.iterator();
        while (it.hasNext()) {
            ((IExtension) it.next()).preApply(iTargetClassContext);
        }
    }

    public void postApply(ITargetClassContext iTargetClassContext) {
        Iterator it = this.activeExtensions.iterator();
        while (it.hasNext()) {
            ((IExtension) it.next()).postApply(iTargetClassContext);
        }
    }

    public void export(MixinEnvironment mixinEnvironment, String str, boolean z, ClassNode classNode) {
        Iterator it = this.activeExtensions.iterator();
        while (it.hasNext()) {
            ((IExtension) it.next()).export(mixinEnvironment, str, z, classNode);
        }
    }

    public void add(IClassGenerator iClassGenerator) {
        this.generators.add(iClassGenerator);
        this.generatorMap.put(iClassGenerator.getClass(), iClassGenerator);
    }

    public List getGenerators() {
        return this.generatorsView;
    }

    public IClassGenerator getGenerator(Class cls) {
        return (IClassGenerator) lookup(cls, this.generatorMap, this.generators);
    }

    private static Object lookup(Class cls, Map map, List list) {
        Object obj = map.get(cls);
        if (obj == null) {
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                if (cls.isAssignableFrom(next.getClass())) {
                    obj = next;
                    break;
                }
            }
            if (obj == null) {
                throw new IllegalArgumentException("Extension for <" + cls.getName() + "> could not be found");
            }
            map.put(cls, obj);
        }
        return obj;
    }
}
