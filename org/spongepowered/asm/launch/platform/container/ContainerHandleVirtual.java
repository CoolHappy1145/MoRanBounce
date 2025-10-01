package org.spongepowered.asm.launch.platform.container;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/platform/container/ContainerHandleVirtual.class */
public class ContainerHandleVirtual implements IContainerHandle {
    private final String name;
    private final Map attributes = new HashMap();
    private final Set nestedContainers = new LinkedHashSet();

    public ContainerHandleVirtual(String str) {
        this.name = str;
    }

    public ContainerHandleVirtual setAttribute(String str, String str2) {
        this.attributes.put(str, str2);
        return this;
    }

    public ContainerHandleVirtual add(IContainerHandle iContainerHandle) {
        this.nestedContainers.add(iContainerHandle);
        return this;
    }

    @Override // org.spongepowered.asm.launch.platform.container.IContainerHandle
    public String getAttribute(String str) {
        return (String) this.attributes.get(str);
    }

    @Override // org.spongepowered.asm.launch.platform.container.IContainerHandle
    public Collection getNestedContainers() {
        return Collections.unmodifiableSet(this.nestedContainers);
    }

    public boolean equals(Object obj) {
        return (obj instanceof String) && obj.toString().equals(this.name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return String.format("ContainerHandleVirtual(%s:%x)", this.name, Integer.valueOf(hashCode()));
    }
}
