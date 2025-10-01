package net.ccbluex.liquidbounce.injection.forge.mixins.client;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.ArrayList;
import net.minecraft.client.resources.ResourcePackRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ResourcePackRepository.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/client/MixinResourcePackRepository.class */
public class MixinResourcePackRepository {

    @Shadow
    @Final
    private static Logger field_177320_c;

    @Shadow
    @Final
    private File field_148534_e;

    @Overwrite
    private void func_183028_i() {
        try {
            ArrayList<File> arrayListNewArrayList = Lists.newArrayList(FileUtils.listFiles(this.field_148534_e, TrueFileFilter.TRUE, (IOFileFilter) null));
            arrayListNewArrayList.sort(LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            int i = 0;
            for (File file : arrayListNewArrayList) {
                int i2 = i;
                i++;
                if (i2 >= 10) {
                    field_177320_c.info("Deleting old server resource pack " + file.getName());
                    FileUtils.deleteQuietly(file);
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
