package org.spongepowered.asm.launch.platform.container;

import java.io.File;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import org.spongepowered.asm.launch.platform.MainAttributes;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/platform/container/ContainerHandleURI.class */
public class ContainerHandleURI implements IContainerHandle {
    private final URI uri;
    private final File file;
    private final MainAttributes attributes;

    public ContainerHandleURI(URI uri) {
        this.uri = uri;
        this.file = this.uri != null ? new File(this.uri) : null;
        this.attributes = MainAttributes.m52of(uri);
    }

    public URI getURI() {
        return this.uri;
    }

    public File getFile() {
        return this.file;
    }

    @Override // org.spongepowered.asm.launch.platform.container.IContainerHandle
    public String getAttribute(String str) {
        return this.attributes.get(str);
    }

    @Override // org.spongepowered.asm.launch.platform.container.IContainerHandle
    public Collection getNestedContainers() {
        return Collections.emptyList();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ContainerHandleURI)) {
            return false;
        }
        return this.uri.equals(((ContainerHandleURI) obj).uri);
    }

    public int hashCode() {
        return this.uri.hashCode();
    }

    public String toString() {
        return String.format("ContainerHandleURI(%s)", this.uri);
    }
}
