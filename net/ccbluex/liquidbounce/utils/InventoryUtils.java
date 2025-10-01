package net.ccbluex.liquidbounce.utils;

import java.util.Arrays;
import java.util.List;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemBlock;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.event.ClickWindowEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/InventoryUtils.class */
public final class InventoryUtils extends MinecraftInstance implements Listenable {
    public static final MSTimer CLICK_TIMER = new MSTimer();
    public static final List BLOCK_BLACKLIST = Arrays.asList(classProvider.getBlockEnum(BlockType.CHEST), classProvider.getBlockEnum(BlockType.ENDER_CHEST), classProvider.getBlockEnum(BlockType.TRAPPED_CHEST), classProvider.getBlockEnum(BlockType.ANVIL), classProvider.getBlockEnum(BlockType.SAND), classProvider.getBlockEnum(BlockType.WEB), classProvider.getBlockEnum(BlockType.TORCH), classProvider.getBlockEnum(BlockType.CRAFTING_TABLE), classProvider.getBlockEnum(BlockType.FURNACE), classProvider.getBlockEnum(BlockType.WATERLILY), classProvider.getBlockEnum(BlockType.DISPENSER), classProvider.getBlockEnum(BlockType.STONE_PRESSURE_PLATE), classProvider.getBlockEnum(BlockType.WODDEN_PRESSURE_PLATE), classProvider.getBlockEnum(BlockType.NOTEBLOCK), classProvider.getBlockEnum(BlockType.DROPPER), classProvider.getBlockEnum(BlockType.TNT), classProvider.getBlockEnum(BlockType.STANDING_BANNER), classProvider.getBlockEnum(BlockType.WALL_BANNER), classProvider.getBlockEnum(BlockType.REDSTONE_TORCH));

    public static int findItem(int i, int i2, IItem iItem) {
        for (int i3 = i; i3 < i2; i3++) {
            IItemStack stack = f157mc.getThePlayer().getInventoryContainer().getSlot(i3).getStack();
            if (stack != null && stack.getItem().equals(iItem)) {
                return i3;
            }
        }
        return -1;
    }

    public static boolean isBlockListBlock(IItemBlock iItemBlock) {
        IBlock block = iItemBlock.getBlock();
        return BLOCK_BLACKLIST.contains(block) || !block.isFullCube(block.getDefaultState());
    }

    public static boolean hasSpaceHotbar() {
        for (int i = 36; i < 45; i++) {
            if (f157mc.getThePlayer().getInventory().getStackInSlot(i) == null) {
                return true;
            }
        }
        return false;
    }

    public static int findAutoBlockBlock() {
        for (int i = 36; i < 45; i++) {
            IItemStack stack = f157mc.getThePlayer().getInventoryContainer().getSlot(i).getStack();
            if (stack != null && classProvider.isItemBlock(stack.getItem()) && stack.getStackSize() > 0) {
                IBlock block = stack.getItem().asItemBlock().getBlock();
                if (block.isFullCube(block.getDefaultState()) && !BLOCK_BLACKLIST.contains(block) && !classProvider.isBlockBush(block)) {
                    return i;
                }
            }
        }
        for (int i2 = 36; i2 < 45; i2++) {
            IItemStack stack2 = f157mc.getThePlayer().getInventoryContainer().getSlot(i2).getStack();
            if (stack2 != null && classProvider.isItemBlock(stack2.getItem()) && stack2.getStackSize() > 0) {
                IBlock block2 = stack2.getItem().asItemBlock().getBlock();
                if (!BLOCK_BLACKLIST.contains(block2) && !classProvider.isBlockBush(block2)) {
                    return i2;
                }
            }
        }
        return -1;
    }

    @EventTarget
    public void onClick(ClickWindowEvent clickWindowEvent) {
        CLICK_TIMER.reset();
    }

    @EventTarget
    public void onPacket(PacketEvent packetEvent) {
        if (classProvider.isCPacketPlayerBlockPlacement(packetEvent.getPacket())) {
            CLICK_TIMER.reset();
        }
    }
}
