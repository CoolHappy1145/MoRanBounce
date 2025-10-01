package org.spongepowered.asm.mixin.refmap;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.MixinEnvironment;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/refmap/RemappingReferenceMapper.class */
public final class RemappingReferenceMapper implements IReferenceMapper {
    private static final String DEFAULT_RESOURCE_PATH_PROPERTY = "net.minecraftforge.gradle.GradleStart.srg.srg-mcp";
    private static final String DEFAULT_MAPPING_ENV = "searge";
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private static final Map srgs = new HashMap();
    private final IReferenceMapper refMap;
    private final Map mappings;
    private final Map cache = new HashMap();

    private RemappingReferenceMapper(MixinEnvironment mixinEnvironment, IReferenceMapper iReferenceMapper) {
        this.refMap = iReferenceMapper;
        this.refMap.setContext(getMappingEnv(mixinEnvironment));
        String resource = getResource(mixinEnvironment);
        this.mappings = loadSrgs(resource);
        logger.info("Remapping refMap {} using {}", new Object[]{iReferenceMapper.getResourceName(), resource});
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public boolean isDefault() {
        return this.refMap.isDefault();
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public String getResourceName() {
        return this.refMap.getResourceName();
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public String getStatus() {
        return this.refMap.getStatus();
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public String getContext() {
        return this.refMap.getContext();
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public String remap(String str, String str2) {
        Map cache = getCache(str);
        String strRemap = (String) cache.get(str2);
        if (strRemap == null) {
            strRemap = this.refMap.remap(str, str2);
            for (Map.Entry entry : this.mappings.entrySet()) {
                strRemap = strRemap.replace((CharSequence) entry.getKey(), (CharSequence) entry.getValue());
            }
            cache.put(str2, strRemap);
        }
        return strRemap;
    }

    private Map getCache(String str) {
        Map map = (Map) this.cache.get(str);
        if (map == null) {
            map = new HashMap();
            this.cache.put(str, map);
        }
        return map;
    }

    @Override // org.spongepowered.asm.mixin.refmap.IReferenceMapper
    public String remapWithContext(String str, String str2, String str3) {
        return this.refMap.remapWithContext(str, str2, str3);
    }

    private static Map loadSrgs(String str) {
        if (srgs.containsKey(str)) {
            return (Map) srgs.get(str);
        }
        HashMap map = new HashMap();
        srgs.put(str, map);
        File file = new File(str);
        if (!file.isFile()) {
            return map;
        }
        try {
            Files.readLines(file, Charsets.UTF_8, new LineProcessor(map) { // from class: org.spongepowered.asm.mixin.refmap.RemappingReferenceMapper.1
                final Map val$map;

                {
                    this.val$map = map;
                }

                public boolean processLine(String str2) {
                    if (Strings.isNullOrEmpty(str2) || str2.startsWith("#")) {
                        return true;
                    }
                    char c = str2.startsWith("MD: ") ? (char) 2 : str2.startsWith("FD: ") ? (char) 1 : (char) 0;
                    char c2 = c;
                    if (c > 0) {
                        String[] strArrSplit = str2.substring(4).split(" ", 4);
                        this.val$map.put(strArrSplit[0].substring(strArrSplit[0].lastIndexOf(47) + 1), strArrSplit[c2].substring(strArrSplit[c2].lastIndexOf(47) + 1));
                        return true;
                    }
                    return true;
                }
            });
        } catch (IOException e) {
            logger.warn("Could not read input SRG file: {}", new Object[]{str});
            logger.catching(e);
        }
        return map;
    }

    /* renamed from: of */
    public static IReferenceMapper m67of(MixinEnvironment mixinEnvironment, IReferenceMapper iReferenceMapper) {
        if (!iReferenceMapper.isDefault() && hasData(mixinEnvironment)) {
            return new RemappingReferenceMapper(mixinEnvironment, iReferenceMapper);
        }
        return iReferenceMapper;
    }

    private static boolean hasData(MixinEnvironment mixinEnvironment) {
        String resource = getResource(mixinEnvironment);
        return resource != null && new File(resource).exists();
    }

    private static String getResource(MixinEnvironment mixinEnvironment) {
        String optionValue = mixinEnvironment.getOptionValue(MixinEnvironment.Option.REFMAP_REMAP_RESOURCE);
        return Strings.isNullOrEmpty(optionValue) ? System.getProperty(DEFAULT_RESOURCE_PATH_PROPERTY) : optionValue;
    }

    private static String getMappingEnv(MixinEnvironment mixinEnvironment) {
        String optionValue = mixinEnvironment.getOptionValue(MixinEnvironment.Option.REFMAP_REMAP_SOURCE_ENV);
        return Strings.isNullOrEmpty(optionValue) ? "searge" : optionValue;
    }
}
