package org.spongepowered.asm.obfuscation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

/* loaded from: L-out.jar:org/spongepowered/asm/obfuscation/RemapperChain.class */
public class RemapperChain implements IRemapper {
    private final List remappers = new ArrayList();

    public String toString() {
        return String.format("RemapperChain[%d]", Integer.valueOf(this.remappers.size()));
    }

    public RemapperChain add(IRemapper iRemapper) {
        this.remappers.add(iRemapper);
        return this;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IRemapper
    public String mapMethodName(String str, String str2, String str3) {
        Iterator it = this.remappers.iterator();
        while (it.hasNext()) {
            String strMapMethodName = ((IRemapper) it.next()).mapMethodName(str, str2, str3);
            if (strMapMethodName != null && !strMapMethodName.equals(str2)) {
                str2 = strMapMethodName;
            }
        }
        return str2;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IRemapper
    public String mapFieldName(String str, String str2, String str3) {
        Iterator it = this.remappers.iterator();
        while (it.hasNext()) {
            String strMapFieldName = ((IRemapper) it.next()).mapFieldName(str, str2, str3);
            if (strMapFieldName != null && !strMapFieldName.equals(str2)) {
                str2 = strMapFieldName;
            }
        }
        return str2;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IRemapper, org.spongepowered.asm.util.ObfuscationUtil.IClassRemapper
    public String map(String str) {
        Iterator it = this.remappers.iterator();
        while (it.hasNext()) {
            String map = ((IRemapper) it.next()).map(str);
            if (map != null && !map.equals(str)) {
                str = map;
            }
        }
        return str;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IRemapper, org.spongepowered.asm.util.ObfuscationUtil.IClassRemapper
    public String unmap(String str) {
        Iterator it = this.remappers.iterator();
        while (it.hasNext()) {
            String strUnmap = ((IRemapper) it.next()).unmap(str);
            if (strUnmap != null && !strUnmap.equals(str)) {
                str = strUnmap;
            }
        }
        return str;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IRemapper
    public String mapDesc(String str) {
        Iterator it = this.remappers.iterator();
        while (it.hasNext()) {
            String strMapDesc = ((IRemapper) it.next()).mapDesc(str);
            if (strMapDesc != null && !strMapDesc.equals(str)) {
                str = strMapDesc;
            }
        }
        return str;
    }

    @Override // org.spongepowered.asm.mixin.extensibility.IRemapper
    public String unmapDesc(String str) {
        Iterator it = this.remappers.iterator();
        while (it.hasNext()) {
            String strUnmapDesc = ((IRemapper) it.next()).unmapDesc(str);
            if (strUnmapDesc != null && !strUnmapDesc.equals(str)) {
                str = strUnmapDesc;
            }
        }
        return str;
    }
}
