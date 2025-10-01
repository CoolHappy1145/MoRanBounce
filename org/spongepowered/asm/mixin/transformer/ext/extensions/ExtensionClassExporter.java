package org.spongepowered.asm.mixin.transformer.ext.extensions;

import com.google.common.io.Files;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import org.apache.log4j.spi.LocationInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Marker;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.ext.IDecompiler;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.transformers.MixinClassWriter;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.perf.Profiler;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ext/extensions/ExtensionClassExporter.class */
public class ExtensionClassExporter implements IExtension {
    private static final String DECOMPILER_CLASS = "org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler";
    private static final String EXPORT_CLASS_DIR = "class";
    private static final String EXPORT_JAVA_DIR = "java";
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private final File classExportDir = new File(Constants.DEBUG_OUTPUT_DIR, EXPORT_CLASS_DIR);
    private final IDecompiler decompiler;

    public ExtensionClassExporter(MixinEnvironment mixinEnvironment) {
        this.decompiler = initDecompiler(mixinEnvironment, new File(Constants.DEBUG_OUTPUT_DIR, EXPORT_JAVA_DIR));
        try {
            MoreFiles.deleteRecursively(this.classExportDir.toPath(), new RecursiveDeleteOption[]{RecursiveDeleteOption.ALLOW_INSECURE});
        } catch (IOException e) {
            logger.debug("Error cleaning class output directory: {}", new Object[]{e.getMessage()});
        }
    }

    public boolean isDecompilerActive() {
        return this.decompiler != null;
    }

    private IDecompiler initDecompiler(MixinEnvironment mixinEnvironment, File file) {
        if (!mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE)) {
            return null;
        }
        try {
            boolean option = mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE_THREADED);
            Logger logger2 = logger;
            Object[] objArr = new Object[1];
            objArr[0] = option ? " (Threaded mode)" : "";
            logger2.info("Attempting to load Fernflower decompiler{}", objArr);
            IDecompiler iDecompiler = (IDecompiler) Class.forName(DECOMPILER_CLASS + (option ? "Async" : "")).getDeclaredConstructor(File.class).newInstance(file);
            Logger logger3 = logger;
            Object[] objArr2 = new Object[1];
            objArr2[0] = option ? " in a separate thread" : "";
            logger3.info("Fernflower decompiler was successfully initialised, exported classes will be decompiled{}", objArr2);
            return iDecompiler;
        } catch (Throwable th) {
            logger.info("Fernflower could not be loaded, exported classes will not be decompiled. {}: {}", new Object[]{th.getClass().getSimpleName(), th.getMessage()});
            return null;
        }
    }

    private String prepareFilter(String str) {
        return ("^\\Q" + str.replace("**", "\u0081").replace(Marker.ANY_MARKER, "\u0082").replace(LocationInfo.f204NA, "\u0083") + "\\E$").replace("\u0081", "\\E.*\\Q").replace("\u0082", "\\E[^\\.]+\\Q").replace("\u0083", "\\E.\\Q").replace("\\Q\\E", "");
    }

    private boolean applyFilter(String str, String str2) {
        return Pattern.compile(prepareFilter(str), 2).matcher(str2).matches();
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IExtension
    public void export(MixinEnvironment mixinEnvironment, String str, boolean z, ClassNode classNode) {
        if (z || mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_EXPORT)) {
            String optionValue = mixinEnvironment.getOptionValue(MixinEnvironment.Option.DEBUG_EXPORT_FILTER);
            if (z || optionValue == null || applyFilter(optionValue, str)) {
                Profiler.Section sectionBegin = MixinEnvironment.getProfiler().begin("debug.export");
                File fileDumpClass = dumpClass(str.replace('.', '/'), classNode);
                if (this.decompiler != null) {
                    this.decompiler.decompile(fileDumpClass);
                }
                sectionBegin.end();
            }
        }
    }

    public File dumpClass(String str, ClassNode classNode) {
        File file = new File(this.classExportDir, str + ".class");
        file.getParentFile().mkdirs();
        try {
            byte[] classBytes = getClassBytes(classNode, true);
            if (classBytes != null) {
                Files.write(classBytes, file);
            }
        } catch (IOException unused) {
        }
        return file;
    }

    private static byte[] getClassBytes(ClassNode classNode, boolean z) {
        byte[] byteArray = null;
        try {
            MixinClassWriter mixinClassWriter = new MixinClassWriter(z ? 2 : 0);
            classNode.accept(mixinClassWriter);
            byteArray = mixinClassWriter.toByteArray();
        } catch (NegativeArraySizeException e) {
            if (z) {
                logger.warn("Exporting class {} with COMPUTE_FRAMES failed! Trying a raw export.", new Object[]{classNode.name});
                return getClassBytes(classNode, false);
            }
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return byteArray;
    }
}
