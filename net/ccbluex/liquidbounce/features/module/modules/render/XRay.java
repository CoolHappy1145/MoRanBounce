package net.ccbluex.liquidbounce.features.module.modules.render;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/XRay;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "xrayBlocks", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "getXrayBlocks", "()Ljava/util/List;", "onToggle", "", "state", "", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "XRay", description = "Allows you to see ores through walls.", category = ModuleCategory.RENDER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/XRay.class */
public final class XRay extends Module {

    @NotNull
    private final List xrayBlocks = CollectionsKt.mutableListOf(new IBlock[]{MinecraftInstance.classProvider.getBlockEnum(BlockType.COAL_ORE), MinecraftInstance.classProvider.getBlockEnum(BlockType.IRON_ORE), MinecraftInstance.classProvider.getBlockEnum(BlockType.GOLD_ORE), MinecraftInstance.classProvider.getBlockEnum(BlockType.REDSTONE_ORE), MinecraftInstance.classProvider.getBlockEnum(BlockType.LAPIS_ORE), MinecraftInstance.classProvider.getBlockEnum(BlockType.DIAMOND_ORE), MinecraftInstance.classProvider.getBlockEnum(BlockType.EMERALD_ORE), MinecraftInstance.classProvider.getBlockEnum(BlockType.QUARTZ_ORE), MinecraftInstance.classProvider.getBlockEnum(BlockType.CLAY), MinecraftInstance.classProvider.getBlockEnum(BlockType.GLOWSTONE), MinecraftInstance.classProvider.getBlockEnum(BlockType.CRAFTING_TABLE), MinecraftInstance.classProvider.getBlockEnum(BlockType.TORCH), MinecraftInstance.classProvider.getBlockEnum(BlockType.LADDER), MinecraftInstance.classProvider.getBlockEnum(BlockType.TNT), MinecraftInstance.classProvider.getBlockEnum(BlockType.COAL_BLOCK), MinecraftInstance.classProvider.getBlockEnum(BlockType.IRON_BLOCK), MinecraftInstance.classProvider.getBlockEnum(BlockType.GOLD_BLOCK), MinecraftInstance.classProvider.getBlockEnum(BlockType.DIAMOND_BLOCK), MinecraftInstance.classProvider.getBlockEnum(BlockType.EMERALD_BLOCK), MinecraftInstance.classProvider.getBlockEnum(BlockType.REDSTONE_BLOCK), MinecraftInstance.classProvider.getBlockEnum(BlockType.LAPIS_BLOCK), MinecraftInstance.classProvider.getBlockEnum(BlockType.FIRE), MinecraftInstance.classProvider.getBlockEnum(BlockType.MOSSY_COBBLESTONE), MinecraftInstance.classProvider.getBlockEnum(BlockType.MOB_SPAWNER), MinecraftInstance.classProvider.getBlockEnum(BlockType.END_PORTAL_FRAME), MinecraftInstance.classProvider.getBlockEnum(BlockType.ENCHANTING_TABLE), MinecraftInstance.classProvider.getBlockEnum(BlockType.BOOKSHELF), MinecraftInstance.classProvider.getBlockEnum(BlockType.COMMAND_BLOCK), MinecraftInstance.classProvider.getBlockEnum(BlockType.LAVA), MinecraftInstance.classProvider.getBlockEnum(BlockType.FLOWING_LAVA), MinecraftInstance.classProvider.getBlockEnum(BlockType.WATER), MinecraftInstance.classProvider.getBlockEnum(BlockType.FLOWING_WATER), MinecraftInstance.classProvider.getBlockEnum(BlockType.FURNACE), MinecraftInstance.classProvider.getBlockEnum(BlockType.LIT_FURNACE)});

    public XRay() {
        LiquidBounce.INSTANCE.getCommandManager().registerCommand(new Command(this, "xray", new String[0]) { // from class: net.ccbluex.liquidbounce.features.module.modules.render.XRay.1
            final XRay this$0;

            {
                this.this$0 = this;
            }

            @Override // net.ccbluex.liquidbounce.features.command.Command
            public void execute(@NotNull String[] args) {
                IBlock blockById;
                IBlock blockById2;
                Intrinsics.checkParameterIsNotNull(args, "args");
                if (args.length > 1) {
                    if (StringsKt.equals(args[1], "add", true)) {
                        try {
                            if (args.length > 2) {
                                try {
                                    blockById2 = MinecraftInstance.functions.getBlockById(Integer.parseInt(args[2]));
                                } catch (NumberFormatException unused) {
                                    IBlock blockFromName = MinecraftInstance.functions.getBlockFromName(args[2]);
                                    if (blockFromName == null || MinecraftInstance.functions.getIdFromBlock(blockFromName) <= 0) {
                                        chat("\u00a77Block \u00a78" + args[2] + "\u00a77 does not exist!");
                                        return;
                                    }
                                    blockById2 = blockFromName;
                                }
                                IBlock iBlock = blockById2;
                                if (iBlock == null || this.this$0.getXrayBlocks().contains(iBlock)) {
                                    chat("This block is already on the list.");
                                    return;
                                }
                                this.this$0.getXrayBlocks().add(iBlock);
                                LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().xrayConfig);
                                chat("\u00a77Added block \u00a78" + iBlock.getLocalizedName() + "\u00a77.");
                                playEdit();
                                return;
                            }
                            chatSyntax("xray add <block_id>");
                            return;
                        } catch (NumberFormatException unused2) {
                            chatSyntaxError();
                            return;
                        }
                    }
                    if (StringsKt.equals(args[1], "remove", true)) {
                        try {
                            if (args.length > 2) {
                                try {
                                    blockById = MinecraftInstance.functions.getBlockById(Integer.parseInt(args[2]));
                                } catch (NumberFormatException unused3) {
                                    IBlock blockFromName2 = MinecraftInstance.functions.getBlockFromName(args[2]);
                                    if (blockFromName2 == null || MinecraftInstance.functions.getIdFromBlock(blockFromName2) <= 0) {
                                        chat("\u00a77Block \u00a78" + args[2] + "\u00a77 does not exist!");
                                        return;
                                    }
                                    blockById = blockFromName2;
                                }
                                IBlock iBlock2 = blockById;
                                if (iBlock2 == null || !this.this$0.getXrayBlocks().contains(iBlock2)) {
                                    chat("This block is not on the list.");
                                    return;
                                }
                                this.this$0.getXrayBlocks().remove(iBlock2);
                                LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().xrayConfig);
                                chat("\u00a77Removed block \u00a78" + iBlock2.getLocalizedName() + "\u00a77.");
                                playEdit();
                                return;
                            }
                            chatSyntax("xray remove <block_id>");
                            return;
                        } catch (NumberFormatException unused4) {
                            chatSyntaxError();
                            return;
                        }
                    }
                    if (StringsKt.equals(args[1], "list", true)) {
                        chat("\u00a78Xray blocks:");
                        for (IBlock iBlock3 : this.this$0.getXrayBlocks()) {
                            chat("\u00a78" + iBlock3.getLocalizedName() + " \u00a77-\u00a7c " + MinecraftInstance.functions.getIdFromBlock(iBlock3));
                        }
                        return;
                    }
                }
                chatSyntax("xray <add, remove, list>");
            }
        });
    }

    @NotNull
    public final List getXrayBlocks() {
        return this.xrayBlocks;
    }

    public void onToggle(boolean z) {
        MinecraftInstance.f157mc.getRenderGlobal().loadRenderers();
    }
}
