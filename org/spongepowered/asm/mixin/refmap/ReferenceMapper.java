package org.spongepowered.asm.mixin.refmap;

import com.google.common.collect.Maps;
import com.google.common.io.Closeables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.tools.Diagnostic;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.util.logging.MessageRouter;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/refmap/ReferenceMapper.class */
public final class ReferenceMapper implements IReferenceMapper, Serializable {
    private static final long serialVersionUID = 2;
    public static final String DEFAULT_RESOURCE = "mixin.refmap.json";
    public static final ReferenceMapper DEFAULT_MAPPER = new ReferenceMapper(true, "invalid");
    private final Map mappings;
    private final Map data;
    private final boolean readOnly;
    private String context;
    private String resource;

    public ReferenceMapper() {
        this(false, DEFAULT_RESOURCE);
    }

    private ReferenceMapper(boolean z, String str) {
        this.mappings = Maps.newHashMap();
        this.data = Maps.newHashMap();
        this.context = null;
        this.readOnly = z;
        this.resource = str;
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public boolean isDefault() {
        return this.readOnly;
    }

    private void setResourceName(String str) {
        if (!this.readOnly) {
            this.resource = str != null ? str : "<unknown resource>";
        }
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public String getResourceName() {
        return this.resource;
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public String getStatus() {
        return isDefault() ? "No refMap loaded." : "Using refmap " + getResourceName();
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public String getContext() {
        return this.context;
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public void setContext(String str) {
        this.context = str;
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public String remap(String str, String str2) {
        return remapWithContext(this.context, str, str2);
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public String remapWithContext(String str, String str2, String str3) {
        Map map = this.mappings;
        if (str != null) {
            map = (Map) this.data.get(str);
            if (map == null) {
                map = this.mappings;
            }
        }
        return remap(map, str2, str3);
    }

    private String remap(Map map, String str, String str2) {
        if (str == null) {
            for (Map map2 : map.values()) {
                if (map2.containsKey(str2)) {
                    return (String) map2.get(str2);
                }
            }
        }
        Map map3 = (Map) map.get(str);
        if (map3 == null) {
            return str2;
        }
        String str3 = (String) map3.get(str2);
        return str3 != null ? str3 : str2;
    }

    public String addMapping(String str, String str2, String str3, String str4) {
        if (this.readOnly || str3 == null || str4 == null) {
            return null;
        }
        String strReplaceAll = str3.replaceAll("\\s", "");
        if (strReplaceAll.equals(str4)) {
            return null;
        }
        Map mapNewHashMap = this.mappings;
        if (str != null) {
            mapNewHashMap = (Map) this.data.get(str);
            if (mapNewHashMap == null) {
                mapNewHashMap = Maps.newHashMap();
                this.data.put(str, mapNewHashMap);
            }
        }
        Map map = (Map) mapNewHashMap.get(str2);
        if (map == null) {
            map = new HashMap();
            mapNewHashMap.put(str2, map);
        }
        return (String) map.put(strReplaceAll, str4);
    }

    public void write(Appendable appendable) {
        new GsonBuilder().setPrettyPrinting().create().toJson(this, appendable);
    }

    public static ReferenceMapper read(String str) {
        InputStream resourceAsStream;
        InputStreamReader inputStreamReader = null;
        try {
            try {
                resourceAsStream = MixinService.getService().getResourceAsStream(str);
            } catch (JsonParseException e) {
                MessageRouter.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("Invalid REFMAP JSON in %s: %s %s", str, e.getClass().getName(), e.getMessage()));
                Closeables.closeQuietly(inputStreamReader);
            } catch (Exception e2) {
                MessageRouter.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("Failed reading REFMAP JSON from %s: %s %s", str, e2.getClass().getName(), e2.getMessage()));
                Closeables.closeQuietly(inputStreamReader);
            }
            if (resourceAsStream == null) {
                Closeables.closeQuietly((Reader) null);
                return DEFAULT_MAPPER;
            }
            inputStreamReader = new InputStreamReader(resourceAsStream);
            ReferenceMapper json = readJson(inputStreamReader);
            json.setResourceName(str);
            Closeables.closeQuietly(inputStreamReader);
            return json;
        } catch (Throwable th) {
            Closeables.closeQuietly(inputStreamReader);
            throw th;
        }
    }

    public static ReferenceMapper read(Reader reader, String str) {
        try {
            ReferenceMapper json = readJson(reader);
            json.setResourceName(str);
            return json;
        } catch (Exception unused) {
            return DEFAULT_MAPPER;
        }
    }

    private static ReferenceMapper readJson(Reader reader) {
        return (ReferenceMapper) new Gson().fromJson(reader, ReferenceMapper.class);
    }
}
