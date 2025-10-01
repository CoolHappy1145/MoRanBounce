package org.spongepowered.asm.mixin.injection.struct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.throwables.InjectionValidationException;
import org.spongepowered.asm.util.Annotations;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/InjectorGroupInfo.class */
public class InjectorGroupInfo {
    private final String name;
    private final List members;
    private final boolean isDefault;
    private int minCallbackCount;
    private int maxCallbackCount;

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/InjectorGroupInfo$Map.class */
    public static final class Map extends HashMap {
        private static final long serialVersionUID = 1;
        private static final InjectorGroupInfo NO_GROUP = new InjectorGroupInfo("NONE", true);

        @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
        public Object get(Object obj) {
            return get(obj);
        }

        @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
        public InjectorGroupInfo get(Object obj) {
            return forName(obj.toString());
        }

        public InjectorGroupInfo forName(String str) {
            InjectorGroupInfo injectorGroupInfo = (InjectorGroupInfo) super.get((Object) str);
            if (injectorGroupInfo == null) {
                injectorGroupInfo = new InjectorGroupInfo(str);
                put(str, injectorGroupInfo);
            }
            return injectorGroupInfo;
        }

        public InjectorGroupInfo parseGroup(MethodNode methodNode, String str) {
            return parseGroup(Annotations.getInvisible(methodNode, Group.class), str);
        }

        public InjectorGroupInfo parseGroup(AnnotationNode annotationNode, String str) {
            if (annotationNode == null) {
                return NO_GROUP;
            }
            String str2 = (String) Annotations.getValue(annotationNode, "name");
            if (str2 == null || str2.isEmpty()) {
                str2 = str;
            }
            InjectorGroupInfo injectorGroupInfoForName = forName(str2);
            Integer num = (Integer) Annotations.getValue(annotationNode, "min");
            if (num != null && num.intValue() != -1) {
                injectorGroupInfoForName.setMinRequired(num.intValue());
            }
            Integer num2 = (Integer) Annotations.getValue(annotationNode, "max");
            if (num2 != null && num2.intValue() != -1) {
                injectorGroupInfoForName.setMaxAllowed(num2.intValue());
            }
            return injectorGroupInfoForName;
        }

        public void validateAll() throws InjectionValidationException {
            Iterator it = values().iterator();
            while (it.hasNext()) {
                ((InjectorGroupInfo) it.next()).validate();
            }
        }
    }

    public InjectorGroupInfo(String str) {
        this(str, false);
    }

    InjectorGroupInfo(String str, boolean z) {
        this.members = new ArrayList();
        this.minCallbackCount = -1;
        this.maxCallbackCount = Integer.MAX_VALUE;
        this.name = str;
        this.isDefault = z;
    }

    public String toString() {
        return String.format("@Group(name=%s, min=%d, max=%d)", getName(), Integer.valueOf(getMinRequired()), Integer.valueOf(getMaxAllowed()));
    }

    public boolean isDefault() {
        return this.isDefault;
    }

    public String getName() {
        return this.name;
    }

    public int getMinRequired() {
        return Math.max(this.minCallbackCount, 1);
    }

    public int getMaxAllowed() {
        return Math.min(this.maxCallbackCount, Integer.MAX_VALUE);
    }

    public Collection getMembers() {
        return Collections.unmodifiableCollection(this.members);
    }

    public void setMinRequired(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("Cannot set zero or negative value for injector group min count. Attempted to set min=" + i + " on " + this);
        }
        if (this.minCallbackCount > 0 && this.minCallbackCount != i) {
            LogManager.getLogger(MixinLaunchPlugin.NAME).warn("Conflicting min value '{}' on @Group({}), previously specified {}", new Object[]{Integer.valueOf(i), this.name, Integer.valueOf(this.minCallbackCount)});
        }
        this.minCallbackCount = Math.max(this.minCallbackCount, i);
    }

    public void setMaxAllowed(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("Cannot set zero or negative value for injector group max count. Attempted to set max=" + i + " on " + this);
        }
        if (this.maxCallbackCount < Integer.MAX_VALUE && this.maxCallbackCount != i) {
            LogManager.getLogger(MixinLaunchPlugin.NAME).warn("Conflicting max value '{}' on @Group({}), previously specified {}", new Object[]{Integer.valueOf(i), this.name, Integer.valueOf(this.maxCallbackCount)});
        }
        this.maxCallbackCount = Math.min(this.maxCallbackCount, i);
    }

    public InjectorGroupInfo add(InjectionInfo injectionInfo) {
        this.members.add(injectionInfo);
        return this;
    }

    public InjectorGroupInfo validate() throws InjectionValidationException {
        if (this.members.size() == 0) {
            return this;
        }
        int injectedCallbackCount = 0;
        Iterator it = this.members.iterator();
        while (it.hasNext()) {
            injectedCallbackCount += ((InjectionInfo) it.next()).getInjectedCallbackCount();
        }
        int minRequired = getMinRequired();
        int maxAllowed = getMaxAllowed();
        if (injectedCallbackCount < minRequired) {
            throw new InjectionValidationException(this, String.format("expected %d invocation(s) but only %d succeeded", Integer.valueOf(minRequired), Integer.valueOf(injectedCallbackCount)));
        }
        if (injectedCallbackCount > maxAllowed) {
            throw new InjectionValidationException(this, String.format("maximum of %d invocation(s) allowed but %d succeeded", Integer.valueOf(maxAllowed), Integer.valueOf(injectedCallbackCount)));
        }
        return this;
    }
}
