package org.spongepowered.asm.launch.platform.container;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/platform/container/ContainerHandleModLauncher.class */
public class ContainerHandleModLauncher extends ContainerHandleVirtual {

    /* loaded from: L-out.jar:org/spongepowered/asm/launch/platform/container/ContainerHandleModLauncher$Resource.class */
    public class Resource extends ContainerHandleURI {
        private String name;
        private Path path;
        final ContainerHandleModLauncher this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Resource(ContainerHandleModLauncher containerHandleModLauncher, String str, Path path) {
            super(path.toUri());
            this.this$0 = containerHandleModLauncher;
            this.name = str;
            this.path = path;
        }

        public String getName() {
            return this.name;
        }

        public Path getPath() {
            return this.path;
        }

        @Override // org.spongepowered.asm.launch.platform.container.ContainerHandleURI
        public String toString() {
            return String.format("ContainerHandleModLauncher.Resource(%s:%s)", this.name, this.path);
        }
    }

    public ContainerHandleModLauncher(String str) {
        super(str);
    }

    public void addResource(String str, Path path) {
        add(new Resource(this, str, path));
    }

    public void addResources(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            addResource((String) entry.getKey(), (Path) entry.getValue());
        }
    }

    @Override // org.spongepowered.asm.launch.platform.container.ContainerHandleVirtual
    public String toString() {
        return String.format("ModLauncher Root Container(%x)", Integer.valueOf(hashCode()));
    }
}
