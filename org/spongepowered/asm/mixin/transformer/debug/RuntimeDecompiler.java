package org.spongepowered.asm.mixin.transformer.debug;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.jetbrains.java.decompiler.main.extern.IResultSaver;
import org.jetbrains.java.decompiler.util.InterpreterUtil;
import org.spongepowered.asm.mixin.transformer.ext.IDecompiler;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/debug/RuntimeDecompiler.class */
public class RuntimeDecompiler extends IFernflowerLogger implements IDecompiler, IResultSaver {
    private static final Level[] SEVERITY_LEVELS = {Level.TRACE, Level.INFO, Level.WARN, Level.ERROR};
    private final File outputPath;
    private final Map options = ImmutableMap.builder().put("din", "0").put("rbr", "0").put("dgs", "1").put("asc", "1").put("den", "1").put("hdc", "1").put("ind", "    ").build();
    protected final Logger logger = LogManager.getLogger("fernflower");

    public RuntimeDecompiler(File file) {
        this.outputPath = file;
        if (this.outputPath.exists()) {
            try {
                MoreFiles.deleteRecursively(this.outputPath.toPath(), new RecursiveDeleteOption[]{RecursiveDeleteOption.ALLOW_INSECURE});
            } catch (IOException e) {
                this.logger.debug("Error cleaning output directory: {}", new Object[]{e.getMessage()});
            }
        }
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IDecompiler
    public void decompile(File file) {
        try {
            Fernflower fernflower = new Fernflower(new IBytecodeProvider(this) { // from class: org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler.1
                private byte[] byteCode;
                final RuntimeDecompiler this$0;

                {
                    this.this$0 = this;
                }

                public byte[] getBytecode(String str, String str2) {
                    if (this.byteCode == null) {
                        this.byteCode = InterpreterUtil.getBytes(new File(str));
                    }
                    return this.byteCode;
                }
            }, this, this.options, this);
            fernflower.getStructContext().addSpace(file, true);
            fernflower.decompileContext();
        } catch (Throwable unused) {
            this.logger.warn("Decompilation error while processing {}", new Object[]{file.getName()});
        }
    }

    public void saveClassFile(String str, String str2, String str3, String str4, int[] iArr) {
        File file = new File(this.outputPath, str2 + ".java");
        file.getParentFile().mkdirs();
        try {
            this.logger.info("Writing {}", new Object[]{file.getAbsolutePath()});
            Files.write(str4, file, Charsets.UTF_8);
        } catch (IOException e) {
            writeMessage("Cannot write source file " + file, e);
        }
    }

    public void startReadingClass(String str) {
        this.logger.info("Decompiling {}", new Object[]{str});
    }

    public void writeMessage(String str, IFernflowerLogger.Severity severity) {
        this.logger.log(SEVERITY_LEVELS[severity.ordinal()], str);
    }

    public void writeMessage(String str, Throwable th) {
        this.logger.warn("{} {}: {}", new Object[]{str, th.getClass().getSimpleName(), th.getMessage()});
    }

    public void writeMessage(String str, IFernflowerLogger.Severity severity, Throwable th) {
        this.logger.log(SEVERITY_LEVELS[severity.ordinal()], str, th);
    }
}
