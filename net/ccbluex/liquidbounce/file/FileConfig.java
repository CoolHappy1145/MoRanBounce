package net.ccbluex.liquidbounce.file;

import java.io.File;
import java.io.IOException;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/file/FileConfig.class */
public abstract class FileConfig {
    private final File file;

    protected abstract void loadConfig();

    protected abstract void saveConfig();

    public FileConfig(File file) {
        this.file = file;
    }

    public void createConfig() throws IOException {
        this.file.createNewFile();
    }

    public boolean hasConfig() {
        return this.file.exists();
    }

    public File getFile() {
        return this.file;
    }
}
