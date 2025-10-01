package net.ccbluex.liquidbounce.injection.backend;

import com.mojang.authlib.GameProfile;
import io.netty.buffer.ByteBuf;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.enums.EnchantmentType;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.enums.ItemType;
import net.ccbluex.liquidbounce.api.enums.MaterialType;
import net.ccbluex.liquidbounce.api.enums.StatType;
import net.ccbluex.liquidbounce.api.enums.WDefaultVertexFormats;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.block.material.IMaterial;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityOtherPlayerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.render.ITessellator;
import net.ccbluex.liquidbounce.api.minecraft.client.render.IThreadDownloadImageData;
import net.ccbluex.liquidbounce.api.minecraft.client.render.WIImageBuffer;
import net.ccbluex.liquidbounce.api.minecraft.client.render.texture.IDynamicTexture;
import net.ccbluex.liquidbounce.api.minecraft.client.render.vertex.IVertexFormat;
import net.ccbluex.liquidbounce.api.minecraft.client.renderer.IGlStateManager;
import net.ccbluex.liquidbounce.api.minecraft.client.renderer.vertex.IVertexBuffer;
import net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings;
import net.ccbluex.liquidbounce.api.minecraft.enchantments.IEnchantment;
import net.ccbluex.liquidbounce.api.minecraft.event.IClickEvent;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.nbt.IJsonToNBT;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagDouble;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagList;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagString;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketAnimation;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketClientStatus;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketCloseWindow;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketCustomPayload;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketHeldItemChange;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketKeepAlive;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerBlockPlacement;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerPosLook;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketResourcePackStatus;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotion;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.api.minecraft.stats.IStatBase;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.api.minecraft.util.ISession;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import net.ccbluex.liquidbounce.api.network.IPacketBuffer;
import net.ccbluex.liquidbounce.api.util.IWrappedFontRenderer;
import net.ccbluex.liquidbounce.api.util.WrappedCreativeTabs;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.api.util.WrappedGuiSlot;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt;
import net.ccbluex.liquidbounce.injection.backend.utils.CreativeTabsWrapper;
import net.ccbluex.liquidbounce.injection.backend.utils.FontRendererWrapper;
import net.ccbluex.liquidbounce.injection.backend.utils.GuiPasswordField;
import net.ccbluex.liquidbounce.injection.backend.utils.GuiScreenWrapper;
import net.ccbluex.liquidbounce.injection.backend.utils.GuiSlotWrapper;
import net.ccbluex.liquidbounce.injection.backend.utils.SafeVertexBuffer;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.ClickGui;
import net.ccbluex.liquidbounce.p005ui.client.hud.designer.GuiHudDesigner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlime;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketEncryptionResponse;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketResourcePackStatus;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketCloseWindow;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.SPacketTabComplete;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraft.network.play.server.SPacketWindowItems;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.fml.client.GuiModList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u00cc\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0012\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\bh\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J8\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u000eH\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\"H\u0016J\u0018\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0016J \u0010)\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0016J\u0018\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u000205H\u0016J\u0010\u00106\u001a\u0002072\u0006\u0010 \u001a\u00020\u001dH\u0016J\b\u00108\u001a\u000209H\u0016J\u0010\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020=H\u0016J\u0012\u0010>\u001a\u00020?2\b\u0010@\u001a\u0004\u0018\u00010\"H\u0016J:\u0010>\u001a\u00020?2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020\u001d2\b\u0010D\u001a\u0004\u0018\u00010\"2\u0006\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020F2\u0006\u0010H\u001a\u00020FH\u0016J \u0010I\u001a\u00020\u001f2\u0006\u00104\u001a\u00020J2\u0006\u0010K\u001a\u00020B2\u0006\u0010L\u001a\u00020MH\u0016J \u0010N\u001a\u00020;2\u0006\u0010O\u001a\u00020F2\u0006\u0010P\u001a\u00020F2\u0006\u0010<\u001a\u00020=H\u0016J8\u0010Q\u001a\u00020R2\u0006\u0010S\u001a\u00020\u000e2\u0006\u0010T\u001a\u00020\u000e2\u0006\u0010U\u001a\u00020\u000e2\u0006\u0010O\u001a\u00020F2\u0006\u0010P\u001a\u00020F2\u0006\u0010<\u001a\u00020=H\u0016J(\u0010V\u001a\u00020;2\u0006\u0010S\u001a\u00020\u000e2\u0006\u0010T\u001a\u00020\u000e2\u0006\u0010U\u001a\u00020\u000e2\u0006\u0010<\u001a\u00020=H\u0016J\u0010\u0010W\u001a\u00020\u001f2\u0006\u0010X\u001a\u00020&H\u0016J\u0014\u0010Y\u001a\u0006\u0012\u0002\b\u00030Z2\u0006\u0010[\u001a\u00020\\H\u0016J\u0018\u0010]\u001a\u00020^2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u00020_H\u0016J\u0018\u0010]\u001a\u00020^2\u0006\u0010`\u001a\u0002032\u0006\u0010a\u001a\u00020bH\u0016J\u0010\u0010c\u001a\u00020d2\u0006\u0010X\u001a\u00020&H\u0016J\u0018\u0010e\u001a\u00020f2\u0006\u0010g\u001a\u00020h2\u0006\u0010i\u001a\u00020&H\u0016J\u0010\u0010j\u001a\u00020k2\u0006\u0010l\u001a\u00020mH\u0016J0\u0010n\u001a\u0002032\u0006\u0010o\u001a\u00020p2\u0006\u0010q\u001a\u00020\u000e2\u0006\u0010r\u001a\u00020\u000e2\u0006\u0010s\u001a\u00020\u000e2\u0006\u0010t\u001a\u00020=H\u0016J\u0018\u0010u\u001a\u00020v2\u0006\u0010o\u001a\u00020w2\u0006\u0010x\u001a\u00020yH\u0016J8\u0010z\u001a\u00020{2\u0006\u0010|\u001a\u00020\u001d2\u0006\u0010S\u001a\u00020\u001d2\u0006\u0010T\u001a\u00020\u001d2\u0006\u0010}\u001a\u00020\u001d2\u0006\u0010~\u001a\u00020\u001d2\u0006\u0010X\u001a\u00020&H\u0016J(\u0010z\u001a\u00020{2\u0006\u0010|\u001a\u00020\u001d2\u0006\u0010S\u001a\u00020\u001d2\u0006\u0010T\u001a\u00020\u001d2\u0006\u0010X\u001a\u00020&H\u0016J'\u0010\u007f\u001a\u00030\u0080\u00012\b\u0010\u0081\u0001\u001a\u00030\u0080\u00012\b\u0010\u0082\u0001\u001a\u00030\u0083\u00012\b\u0010\u0084\u0001\u001a\u00030\u0085\u0001H\u0016J\u0014\u0010\u0086\u0001\u001a\u00030\u0080\u00012\b\u0010\u0087\u0001\u001a\u00030\u0080\u0001H\u0016J\u0014\u0010\u0088\u0001\u001a\u00030\u0080\u00012\b\u0010\u0087\u0001\u001a\u00030\u0080\u0001H\u0016J\u001e\u0010\u0089\u0001\u001a\u00030\u0080\u00012\b\u0010\u0087\u0001\u001a\u00030\u0080\u00012\b\u0010\u008a\u0001\u001a\u00030\u008b\u0001H\u0016J<\u0010\u008c\u0001\u001a\u00030\u008d\u00012\u0006\u0010|\u001a\u00020\u001d2\b\u0010\u008e\u0001\u001a\u00030\u008f\u00012\u0006\u0010S\u001a\u00020\u001d2\u0006\u0010T\u001a\u00020\u001d2\u0006\u0010}\u001a\u00020\u001d2\u0006\u0010~\u001a\u00020\u001dH\u0016J\u0014\u0010\u0090\u0001\u001a\u00030\u0080\u00012\b\u0010\u0087\u0001\u001a\u00030\u0080\u0001H\u0016J<\u0010\u0091\u0001\u001a\u00030\u008d\u00012\u0006\u0010|\u001a\u00020\u001d2\b\u0010\u008e\u0001\u001a\u00030\u008f\u00012\u0006\u0010S\u001a\u00020\u001d2\u0006\u0010T\u001a\u00020\u001d2\u0006\u0010}\u001a\u00020\u001d2\u0006\u0010~\u001a\u00020\u001dH\u0016J\u001c\u0010\u0092\u0001\u001a\u00020\u001f2\u0007\u0010\u0093\u0001\u001a\u00020&2\b\u0010\u0094\u0001\u001a\u00030\u0095\u0001H\u0016J\n\u0010\u0096\u0001\u001a\u00030\u0097\u0001H\u0016J\u0013\u0010\u0098\u0001\u001a\u00020\"2\b\u0010\u0099\u0001\u001a\u00030\u009a\u0001H\u0016J\u0013\u0010\u0098\u0001\u001a\u00020\"2\b\u0010\u009b\u0001\u001a\u00030\u0097\u0001H\u0016J%\u0010\u0098\u0001\u001a\u00020\"2\b\u0010\u009b\u0001\u001a\u00030\u0097\u00012\u0007\u0010\u009c\u0001\u001a\u00020\u001d2\u0007\u0010\u009d\u0001\u001a\u00020\u001dH\u0016J\n\u0010\u009e\u0001\u001a\u00030\u009f\u0001H\u0016J\u0012\u0010\u00a0\u0001\u001a\u00030\u00a1\u00012\u0006\u0010i\u001a\u00020\u000eH\u0016J\n\u0010\u00a2\u0001\u001a\u00030\u00a3\u0001H\u0016J\u0013\u0010\u00a4\u0001\u001a\u00030\u00a5\u00012\u0007\u0010\u00a6\u0001\u001a\u00020&H\u0016J\u0013\u0010\u00a7\u0001\u001a\u00020(2\b\u0010\u00a8\u0001\u001a\u00030\u00a9\u0001H\u0016J$\u0010\u00aa\u0001\u001a\u00030\u00ab\u00012\u0006\u0010|\u001a\u00020\u001d2\u0007\u0010\u00ac\u0001\u001a\u00020\u001d2\u0007\u0010\u00ad\u0001\u001a\u00020\u001dH\u0016J\u0013\u0010\u00ae\u0001\u001a\u00030\u00af\u00012\u0007\u0010\u00b0\u0001\u001a\u00020&H\u0016J\u0014\u0010\u00b1\u0001\u001a\u00030\u00b2\u00012\b\u0010\u00b3\u0001\u001a\u00030\u00b4\u0001H\u0016J\u0014\u0010\u00b5\u0001\u001a\u00030\u00b6\u00012\b\u0010\u0082\u0001\u001a\u00030\u0083\u0001H\u0016J.\u0010\u00b7\u0001\u001a\u00030\u00b8\u00012\u0007\u0010\u00b9\u0001\u001a\u00020&2\u0007\u0010\u00ba\u0001\u001a\u00020&2\u0007\u0010\u00bb\u0001\u001a\u00020&2\u0007\u0010\u00bc\u0001\u001a\u00020&H\u0016J5\u0010\u00bd\u0001\u001a\u00030\u00be\u00012\n\u0010\u00bf\u0001\u001a\u0005\u0018\u00010\u00c0\u00012\u0007\u0010\u00c1\u0001\u001a\u00020&2\n\u0010\u00c2\u0001\u001a\u0005\u0018\u00010\u00af\u00012\b\u0010\u00c3\u0001\u001a\u00030\u00c4\u0001H\u0016J\u0014\u0010\u00c5\u0001\u001a\u00030\u009a\u00012\b\u0010\u00c6\u0001\u001a\u00030\u00c7\u0001H\u0016J\u0014\u0010\u00c8\u0001\u001a\u00030\u00c9\u00012\b\u0010\u00c6\u0001\u001a\u00030\u00ca\u0001H\u0016J\u0013\u0010\u00cb\u0001\u001a\u00020M2\b\u0010\u00c6\u0001\u001a\u00030\u00cc\u0001H\u0016J\n\u0010\u00cd\u0001\u001a\u00030\u00ce\u0001H\u0016J\u0014\u0010\u00cf\u0001\u001a\u00030\u0097\u00012\b\u0010\u00c6\u0001\u001a\u00030\u00d0\u0001H\u0016J\u0014\u0010\u00d1\u0001\u001a\u00030\u00d2\u00012\b\u0010\u00c6\u0001\u001a\u00030\u00d3\u0001H\u0016J\u0014\u0010\u00d4\u0001\u001a\u00030\u00d5\u00012\b\u0010\u00c6\u0001\u001a\u00030\u00d6\u0001H\u0016J\u0014\u0010\u00d7\u0001\u001a\u00030\u00d8\u00012\b\u0010\u00c6\u0001\u001a\u00030\u00d9\u0001H\u0016J\u0014\u0010\u00da\u0001\u001a\u00030\u00b4\u00012\b\u0010\u00c6\u0001\u001a\u00030\u00db\u0001H\u0016J\u0015\u0010\u00dc\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00df\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00e0\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00e1\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00e2\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00e3\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00e4\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00e5\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00e6\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00e7\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00e8\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00e9\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00ea\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00eb\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00ec\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00ed\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00ee\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00ef\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00f0\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00f1\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00f2\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00f3\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00f4\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00f5\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00f6\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00f7\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00f8\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00f9\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00fa\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00fb\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00fc\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00fd\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00fe\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00ff\u0001\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0080\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0081\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0082\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0083\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0084\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0085\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0086\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0087\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0088\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0089\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u008a\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u008b\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u008c\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u008d\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u008e\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u008f\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0090\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0091\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0092\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0093\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0094\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0095\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0096\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0097\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0098\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u0099\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u009a\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u009b\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u009c\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u009d\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u009e\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u009f\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00a0\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00a1\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00a2\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00a3\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00a4\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00a5\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00a6\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00a7\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00a8\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00a9\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00aa\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00ab\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00ac\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00ad\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00ae\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00af\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00b0\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00b1\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00b2\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00b3\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00b4\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00b5\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00b6\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00b7\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00b8\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00b9\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00ba\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00bb\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00bc\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00bd\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00be\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00bf\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00c0\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00c1\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00c2\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00c3\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00c4\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u0015\u0010\u00c5\u0002\u001a\u00020=2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\u001d\u0010\u00c6\u0002\u001a\u00030\u00c7\u00022\u0007\u0010\u00b9\u0001\u001a\u00020&2\b\u0010\u00c8\u0002\u001a\u00030\u00c9\u0002H\u0016J\u0014\u0010\u00ca\u0002\u001a\u00030\u008f\u00012\b\u0010\u00cb\u0002\u001a\u00030\u00cc\u0002H\u0016J\u0014\u0010\u00cd\u0002\u001a\u00030\u0080\u00012\b\u0010\u00ce\u0002\u001a\u00030\u00cf\u0002H\u0016JI\u0010\u00d0\u0002\u001a\u00030\u00c7\u00022\b\u0010\u00d1\u0002\u001a\u00030\u00d2\u00022\b\u0010\u0082\u0001\u001a\u00030\u0083\u00012\u0006\u0010}\u001a\u00020\u001d2\u0006\u0010~\u001a\u00020\u001d2\u0007\u0010\u00d3\u0002\u001a\u00020\u001d2\u0007\u0010\u00d4\u0002\u001a\u00020\u001d2\u0007\u0010\u00d5\u0002\u001a\u00020\u001dH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006\u00d6\u0002"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/ClassProviderImpl;", "Lnet/ccbluex/liquidbounce/api/IClassProvider;", "()V", "jsonToNBTInstance", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/IJsonToNBT;", "getJsonToNBTInstance", "()Lnet/ccbluex/liquidbounce/api/minecraft/nbt/IJsonToNBT;", "tessellatorInstance", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/ITessellator;", "getTessellatorInstance", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/render/ITessellator;", "createAxisAlignedBB", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "minX", "", "minY", "minZ", "maxX", "maxY", "maxZ", "createCPacketAnimation", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketAnimation;", "createCPacketClientStatus", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketClientStatus;", "state", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketClientStatus$WEnumState;", "createCPacketCloseWindow", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketCloseWindow;", "windowId", "", "createCPacketCreativeInventoryAction", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "slot", "itemStack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "createCPacketCustomPayload", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketCustomPayload;", "channel", "", "payload", "Lnet/ccbluex/liquidbounce/api/network/IPacketBuffer;", "createCPacketEncryptionResponse", "secretKey", "Ljavax/crypto/SecretKey;", "publicKey", "Ljava/security/PublicKey;", "verifyToken", "", "createCPacketEntityAction", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketEntityAction;", "player", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "wAction", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketEntityAction$WAction;", "createCPacketHeldItemChange", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketHeldItemChange;", "createCPacketKeepAlive", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketKeepAlive;", "createCPacketPlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayer;", "onGround", "", "createCPacketPlayerBlockPlacement", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerBlockPlacement;", "stack", "positionIn", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "placedBlockDirectionIn", "stackIn", "facingXIn", "", "facingYIn", "facingZIn", "createCPacketPlayerDigging", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerDigging$WAction;", "pos", "facing", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "createCPacketPlayerLook", "yaw", "pitch", "createCPacketPlayerPosLook", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerPosLook;", "x", "y", "z", "createCPacketPlayerPosition", "createCPacketTabComplete", "text", "createCPacketTryUseItem", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "hand", "Lnet/ccbluex/liquidbounce/api/enums/WEnumHand;", "createCPacketUseEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketUseEntity;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketUseEntity$WAction;", "entity", "positionVector", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "createChatComponentText", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "createClickEvent", "Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent;", "action", "Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent$WAction;", PropertyDescriptor.VALUE, "createDynamicTexture", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/texture/IDynamicTexture;", "image", "Ljava/awt/image/BufferedImage;", "createEntityLightningBolt", "world", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorld;", "posX", "posY", "posZ", "effectOnly", "createEntityOtherPlayerMP", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityOtherPlayerMP;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "gameProfile", "Lcom/mojang/authlib/GameProfile;", "createGuiButton", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "id", "width", "height", "createGuiConnecting", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "parent", "mc", "Lnet/ccbluex/liquidbounce/api/minecraft/client/IMinecraft;", "serverData", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IServerData;", "createGuiModList", "parentScreen", "createGuiMultiplayer", "createGuiOptions", "gameSettings", "Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IGameSettings;", "createGuiPasswordField", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiTextField;", "iFontRenderer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "createGuiSelectWorld", "createGuiTextField", "createICPacketResourcePackStatus", "hash", "status", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketResourcePackStatus$WAction;", "createItem", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "createItemStack", "blockEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "item", "amount", "meta", "createNBTTagCompound", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;", "createNBTTagDouble", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagDouble;", "createNBTTagList", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagList;", "createNBTTagString", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagString;", "string", "createPacketBuffer", "buffer", "Lio/netty/buffer/ByteBuf;", "createPotionEffect", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotionEffect;", "time", "strength", "createResourceLocation", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "resourceName", "createSafeVertexBuffer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/vertex/IVertexBuffer;", "vertexFormat", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/vertex/IVertexFormat;", "createScaledResolution", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IScaledResolution;", "createSession", "Lnet/ccbluex/liquidbounce/api/minecraft/util/ISession;", "name", "uuid", "accessToken", "accountType", "createThreadDownloadImageData", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/IThreadDownloadImageData;", "cacheFileIn", "Ljava/io/File;", "imageUrlIn", "textureResourceLocation", "imageBufferIn", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/WIImageBuffer;", "getBlockEnum", "type", "Lnet/ccbluex/liquidbounce/api/enums/BlockType;", "getEnchantmentEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/enchantments/IEnchantment;", "Lnet/ccbluex/liquidbounce/api/enums/EnchantmentType;", "getEnumFacing", "Lnet/ccbluex/liquidbounce/api/enums/EnumFacingType;", "getGlStateManager", "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IGlStateManager;", "getItemEnum", "Lnet/ccbluex/liquidbounce/api/enums/ItemType;", "getMaterialEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/block/material/IMaterial;", "Lnet/ccbluex/liquidbounce/api/enums/MaterialType;", "getPotionEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotion;", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/PotionType;", "getStatEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/stats/IStatBase;", "Lnet/ccbluex/liquidbounce/api/enums/StatType;", "getVertexFormatEnum", "Lnet/ccbluex/liquidbounce/api/enums/WDefaultVertexFormats;", "isBlockAir", "obj", "", "isBlockBedrock", "isBlockBush", "isBlockCactus", "isBlockCarpet", "isBlockFence", "isBlockLadder", "isBlockLiquid", "isBlockPane", "isBlockSlab", "isBlockSlime", "isBlockSnow", "isBlockStairs", "isBlockVine", "isCPacketAnimation", "isCPacketChatMessage", "isCPacketClickWindow", "isCPacketClientStatus", "isCPacketCloseWindow", "isCPacketConfirmTransaction", "isCPacketCustomPayload", "isCPacketEntityAction", "isCPacketHandshake", "isCPacketHeldItemChange", "isCPacketKeepAlive", "isCPacketPlayer", "isCPacketPlayerBlockPlacement", "isCPacketPlayerDigging", "isCPacketPlayerLook", "isCPacketPlayerPosLook", "isCPacketPlayerPosition", "isCPacketTryUseItem", "isCPacketUseEntity", "isClickGui", "isEntityAnimal", "isEntityArmorStand", "isEntityArrow", "isEntityBat", "isEntityBoat", "isEntityDragon", "isEntityFallingBlock", "isEntityFireball", "isEntityGhast", "isEntityGolem", "isEntityItem", "isEntityLivingBase", "isEntityMinecart", "isEntityMinecartChest", "isEntityMob", "isEntityPlayer", "isEntityPlayerSP", "isEntityShulker", "isEntitySlime", "isEntitySquid", "isEntityTNTPrimed", "isEntityVillager", "isGuiChat", "isGuiChest", "isGuiContainer", "isGuiGameOver", "isGuiHudDesigner", "isGuiIngameMenu", "isGuiInventory", "isItemAir", "isItemAppleGold", "isItemArmor", "isItemAxe", "isItemBed", "isItemBlock", "isItemBoat", "isItemBow", "isItemBucket", "isItemBucketMilk", "isItemEgg", "isItemEnchantedBook", "isItemEnderPearl", "isItemFishingRod", "isItemFood", "isItemMinecart", "isItemPickaxe", "isItemPotion", "isItemShears", "isItemSnowball", "isItemSword", "isItemTool", "isSPacketAnimation", "isSPacketChat", "isSPacketCloseWindow", "isSPacketEntity", "isSPacketEntityVelocity", "isSPacketExplosion", "isSPacketPlayerPosLook", "isSPacketResourcePackSend", "isSPacketSpawnGlobalEntity", "isSPacketTabComplete", "isSPacketTitle", "isSPacketUpdateHealth", "isSPacketWindowItems", "isTileEntityChest", "isTileEntityDispenser", "isTileEntityEnderChest", "isTileEntityFurnace", "isTileEntityHopper", "isTileEntityShulkerBox", "wrapCreativeTab", "", "wrappedCreativeTabs", "Lnet/ccbluex/liquidbounce/api/util/WrappedCreativeTabs;", "wrapFontRenderer", "fontRenderer", "Lnet/ccbluex/liquidbounce/api/util/IWrappedFontRenderer;", "wrapGuiScreen", "clickGui", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "wrapGuiSlot", "wrappedGuiSlot", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiSlot;", "top", "bottom", "slotHeight", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ClassProviderImpl.class */
public final class ClassProviderImpl implements IClassProvider {
    public static final ClassProviderImpl INSTANCE = new ClassProviderImpl();

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ClassProviderImpl$WhenMappings.class */
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0 = new int[ICPacketUseEntity.WAction.values().length];
        public static final int[] $EnumSwitchMapping$1;
        public static final int[] $EnumSwitchMapping$2;
        public static final int[] $EnumSwitchMapping$3;
        public static final int[] $EnumSwitchMapping$4;
        public static final int[] $EnumSwitchMapping$5;
        public static final int[] $EnumSwitchMapping$6;
        public static final int[] $EnumSwitchMapping$7;
        public static final int[] $EnumSwitchMapping$8;

        static {
            $EnumSwitchMapping$0[ICPacketUseEntity.WAction.INTERACT.ordinal()] = 1;
            $EnumSwitchMapping$0[ICPacketUseEntity.WAction.ATTACK.ordinal()] = 2;
            $EnumSwitchMapping$0[ICPacketUseEntity.WAction.INTERACT_AT.ordinal()] = 3;
            $EnumSwitchMapping$1 = new int[PotionType.values().length];
            $EnumSwitchMapping$1[PotionType.HEAL.ordinal()] = 1;
            $EnumSwitchMapping$1[PotionType.REGENERATION.ordinal()] = 2;
            $EnumSwitchMapping$1[PotionType.BLINDNESS.ordinal()] = 3;
            $EnumSwitchMapping$1[PotionType.MOVE_SPEED.ordinal()] = 4;
            $EnumSwitchMapping$1[PotionType.HUNGER.ordinal()] = 5;
            $EnumSwitchMapping$1[PotionType.DIG_SLOWDOWN.ordinal()] = 6;
            $EnumSwitchMapping$1[PotionType.CONFUSION.ordinal()] = 7;
            $EnumSwitchMapping$1[PotionType.WEAKNESS.ordinal()] = 8;
            $EnumSwitchMapping$1[PotionType.MOVE_SLOWDOWN.ordinal()] = 9;
            $EnumSwitchMapping$1[PotionType.HARM.ordinal()] = 10;
            $EnumSwitchMapping$1[PotionType.WITHER.ordinal()] = 11;
            $EnumSwitchMapping$1[PotionType.POISON.ordinal()] = 12;
            $EnumSwitchMapping$1[PotionType.NIGHT_VISION.ordinal()] = 13;
            $EnumSwitchMapping$1[PotionType.JUMP.ordinal()] = 14;
            $EnumSwitchMapping$2 = new int[EnumFacingType.values().length];
            $EnumSwitchMapping$2[EnumFacingType.DOWN.ordinal()] = 1;
            $EnumSwitchMapping$2[EnumFacingType.UP.ordinal()] = 2;
            $EnumSwitchMapping$2[EnumFacingType.NORTH.ordinal()] = 3;
            $EnumSwitchMapping$2[EnumFacingType.SOUTH.ordinal()] = 4;
            $EnumSwitchMapping$2[EnumFacingType.WEST.ordinal()] = 5;
            $EnumSwitchMapping$2[EnumFacingType.EAST.ordinal()] = 6;
            $EnumSwitchMapping$3 = new int[BlockType.values().length];
            $EnumSwitchMapping$3[BlockType.ENCHANTING_TABLE.ordinal()] = 1;
            $EnumSwitchMapping$3[BlockType.CHEST.ordinal()] = 2;
            $EnumSwitchMapping$3[BlockType.ENDER_CHEST.ordinal()] = 3;
            $EnumSwitchMapping$3[BlockType.TRAPPED_CHEST.ordinal()] = 4;
            $EnumSwitchMapping$3[BlockType.ANVIL.ordinal()] = 5;
            $EnumSwitchMapping$3[BlockType.SAND.ordinal()] = 6;
            $EnumSwitchMapping$3[BlockType.WEB.ordinal()] = 7;
            $EnumSwitchMapping$3[BlockType.TORCH.ordinal()] = 8;
            $EnumSwitchMapping$3[BlockType.CRAFTING_TABLE.ordinal()] = 9;
            $EnumSwitchMapping$3[BlockType.FURNACE.ordinal()] = 10;
            $EnumSwitchMapping$3[BlockType.WATERLILY.ordinal()] = 11;
            $EnumSwitchMapping$3[BlockType.DISPENSER.ordinal()] = 12;
            $EnumSwitchMapping$3[BlockType.STONE_PRESSURE_PLATE.ordinal()] = 13;
            $EnumSwitchMapping$3[BlockType.WODDEN_PRESSURE_PLATE.ordinal()] = 14;
            $EnumSwitchMapping$3[BlockType.TNT.ordinal()] = 15;
            $EnumSwitchMapping$3[BlockType.STANDING_BANNER.ordinal()] = 16;
            $EnumSwitchMapping$3[BlockType.WALL_BANNER.ordinal()] = 17;
            $EnumSwitchMapping$3[BlockType.REDSTONE_TORCH.ordinal()] = 18;
            $EnumSwitchMapping$3[BlockType.NOTEBLOCK.ordinal()] = 19;
            $EnumSwitchMapping$3[BlockType.DROPPER.ordinal()] = 20;
            $EnumSwitchMapping$3[BlockType.SNOW_LAYER.ordinal()] = 21;
            $EnumSwitchMapping$3[BlockType.STONE.ordinal()] = 22;
            $EnumSwitchMapping$3[BlockType.AIR.ordinal()] = 23;
            $EnumSwitchMapping$3[BlockType.ICE_PACKED.ordinal()] = 24;
            $EnumSwitchMapping$3[BlockType.ICE.ordinal()] = 25;
            $EnumSwitchMapping$3[BlockType.WATER.ordinal()] = 26;
            $EnumSwitchMapping$3[BlockType.BARRIER.ordinal()] = 27;
            $EnumSwitchMapping$3[BlockType.FLOWING_WATER.ordinal()] = 28;
            $EnumSwitchMapping$3[BlockType.COAL_ORE.ordinal()] = 29;
            $EnumSwitchMapping$3[BlockType.IRON_ORE.ordinal()] = 30;
            $EnumSwitchMapping$3[BlockType.GOLD_ORE.ordinal()] = 31;
            $EnumSwitchMapping$3[BlockType.REDSTONE_ORE.ordinal()] = 32;
            $EnumSwitchMapping$3[BlockType.LAPIS_ORE.ordinal()] = 33;
            $EnumSwitchMapping$3[BlockType.DIAMOND_ORE.ordinal()] = 34;
            $EnumSwitchMapping$3[BlockType.EMERALD_ORE.ordinal()] = 35;
            $EnumSwitchMapping$3[BlockType.QUARTZ_ORE.ordinal()] = 36;
            $EnumSwitchMapping$3[BlockType.CLAY.ordinal()] = 37;
            $EnumSwitchMapping$3[BlockType.GLOWSTONE.ordinal()] = 38;
            $EnumSwitchMapping$3[BlockType.LADDER.ordinal()] = 39;
            $EnumSwitchMapping$3[BlockType.COAL_BLOCK.ordinal()] = 40;
            $EnumSwitchMapping$3[BlockType.IRON_BLOCK.ordinal()] = 41;
            $EnumSwitchMapping$3[BlockType.GOLD_BLOCK.ordinal()] = 42;
            $EnumSwitchMapping$3[BlockType.DIAMOND_BLOCK.ordinal()] = 43;
            $EnumSwitchMapping$3[BlockType.EMERALD_BLOCK.ordinal()] = 44;
            $EnumSwitchMapping$3[BlockType.REDSTONE_BLOCK.ordinal()] = 45;
            $EnumSwitchMapping$3[BlockType.LAPIS_BLOCK.ordinal()] = 46;
            $EnumSwitchMapping$3[BlockType.FIRE.ordinal()] = 47;
            $EnumSwitchMapping$3[BlockType.MOSSY_COBBLESTONE.ordinal()] = 48;
            $EnumSwitchMapping$3[BlockType.MOB_SPAWNER.ordinal()] = 49;
            $EnumSwitchMapping$3[BlockType.END_PORTAL_FRAME.ordinal()] = 50;
            $EnumSwitchMapping$3[BlockType.BOOKSHELF.ordinal()] = 51;
            $EnumSwitchMapping$3[BlockType.COMMAND_BLOCK.ordinal()] = 52;
            $EnumSwitchMapping$3[BlockType.LAVA.ordinal()] = 53;
            $EnumSwitchMapping$3[BlockType.FLOWING_LAVA.ordinal()] = 54;
            $EnumSwitchMapping$3[BlockType.LIT_FURNACE.ordinal()] = 55;
            $EnumSwitchMapping$3[BlockType.DRAGON_EGG.ordinal()] = 56;
            $EnumSwitchMapping$3[BlockType.BROWN_MUSHROOM_BLOCK.ordinal()] = 57;
            $EnumSwitchMapping$3[BlockType.RED_MUSHROOM_BLOCK.ordinal()] = 58;
            $EnumSwitchMapping$3[BlockType.FARMLAND.ordinal()] = 59;
            $EnumSwitchMapping$4 = new int[MaterialType.values().length];
            $EnumSwitchMapping$4[MaterialType.AIR.ordinal()] = 1;
            $EnumSwitchMapping$4[MaterialType.WATER.ordinal()] = 2;
            $EnumSwitchMapping$4[MaterialType.LAVA.ordinal()] = 3;
            $EnumSwitchMapping$5 = new int[StatType.values().length];
            $EnumSwitchMapping$5[StatType.JUMP_STAT.ordinal()] = 1;
            $EnumSwitchMapping$6 = new int[ItemType.values().length];
            $EnumSwitchMapping$6[ItemType.MUSHROOM_STEW.ordinal()] = 1;
            $EnumSwitchMapping$6[ItemType.BOWL.ordinal()] = 2;
            $EnumSwitchMapping$6[ItemType.FLINT_AND_STEEL.ordinal()] = 3;
            $EnumSwitchMapping$6[ItemType.LAVA_BUCKET.ordinal()] = 4;
            $EnumSwitchMapping$6[ItemType.WRITABLE_BOOK.ordinal()] = 5;
            $EnumSwitchMapping$6[ItemType.WATER_BUCKET.ordinal()] = 6;
            $EnumSwitchMapping$6[ItemType.COMMAND_BLOCK_MINECART.ordinal()] = 7;
            $EnumSwitchMapping$6[ItemType.POTION_ITEM.ordinal()] = 8;
            $EnumSwitchMapping$6[ItemType.SKULL.ordinal()] = 9;
            $EnumSwitchMapping$6[ItemType.GOLDEN_APPLE.ordinal()] = 10;
            $EnumSwitchMapping$6[ItemType.ARMOR_STAND.ordinal()] = 11;
            $EnumSwitchMapping$7 = new int[EnchantmentType.values().length];
            $EnumSwitchMapping$7[EnchantmentType.SHARPNESS.ordinal()] = 1;
            $EnumSwitchMapping$7[EnchantmentType.POWER.ordinal()] = 2;
            $EnumSwitchMapping$7[EnchantmentType.PROTECTION.ordinal()] = 3;
            $EnumSwitchMapping$7[EnchantmentType.FEATHER_FALLING.ordinal()] = 4;
            $EnumSwitchMapping$7[EnchantmentType.PROJECTILE_PROTECTION.ordinal()] = 5;
            $EnumSwitchMapping$7[EnchantmentType.THORNS.ordinal()] = 6;
            $EnumSwitchMapping$7[EnchantmentType.FIRE_PROTECTION.ordinal()] = 7;
            $EnumSwitchMapping$7[EnchantmentType.RESPIRATION.ordinal()] = 8;
            $EnumSwitchMapping$7[EnchantmentType.AQUA_AFFINITY.ordinal()] = 9;
            $EnumSwitchMapping$7[EnchantmentType.BLAST_PROTECTION.ordinal()] = 10;
            $EnumSwitchMapping$7[EnchantmentType.UNBREAKING.ordinal()] = 11;
            $EnumSwitchMapping$8 = new int[WDefaultVertexFormats.values().length];
            $EnumSwitchMapping$8[WDefaultVertexFormats.POSITION.ordinal()] = 1;
            $EnumSwitchMapping$8[WDefaultVertexFormats.POSITION_TEX.ordinal()] = 2;
            $EnumSwitchMapping$8[WDefaultVertexFormats.POSITION_COLOR.ordinal()] = 3;
        }
    }

    private ClassProviderImpl() {
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public ITessellator getTessellatorInstance() {
        Tessellator tessellatorFunc_178181_a = Tessellator.func_178181_a();
        Intrinsics.checkExpressionValueIsNotNull(tessellatorFunc_178181_a, "Tessellator.getInstance()");
        return new TessellatorImpl(tessellatorFunc_178181_a);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IJsonToNBT getJsonToNBTInstance() {
        return JsonToNBTImpl.INSTANCE;
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IResourceLocation createResourceLocation(@NotNull String resourceName) {
        Intrinsics.checkParameterIsNotNull(resourceName, "resourceName");
        return new ResourceLocationImpl(new ResourceLocation(resourceName));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IThreadDownloadImageData createThreadDownloadImageData(@Nullable File file, @NotNull String imageUrlIn, @Nullable IResourceLocation iResourceLocation, @NotNull WIImageBuffer imageBufferIn) {
        ResourceLocation wrapped;
        Intrinsics.checkParameterIsNotNull(imageUrlIn, "imageUrlIn");
        Intrinsics.checkParameterIsNotNull(imageBufferIn, "imageBufferIn");
        File file2 = file;
        String str = imageUrlIn;
        if (iResourceLocation == null) {
            wrapped = null;
        } else {
            if (iResourceLocation == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.ResourceLocationImpl");
            }
            file2 = file2;
            str = str;
            wrapped = ((ResourceLocationImpl) iResourceLocation).getWrapped();
        }
        return new ThreadDownloadImageDataImpl(new ThreadDownloadImageData(file2, str, wrapped, new IImageBuffer(imageBufferIn) { // from class: net.ccbluex.liquidbounce.injection.backend.ClassProviderImpl.createThreadDownloadImageData.1
            final WIImageBuffer $imageBufferIn;

            {
                this.$imageBufferIn = imageBufferIn;
            }

            @Nullable
            public BufferedImage func_78432_a(@Nullable BufferedImage bufferedImage) {
                return this.$imageBufferIn.parseUserSkin(bufferedImage);
            }

            public void func_152634_a() {
                this.$imageBufferIn.skinAvailable();
            }
        }));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IPacketBuffer createPacketBuffer(@NotNull ByteBuf buffer) {
        Intrinsics.checkParameterIsNotNull(buffer, "buffer");
        return new PacketBufferImpl(new PacketBuffer(buffer));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IIChatComponent createChatComponentText(@NotNull String text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        return new IChatComponentImpl(new TextComponentString(text));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IClickEvent createClickEvent(@NotNull IClickEvent.WAction action, @NotNull String value) {
        Intrinsics.checkParameterIsNotNull(action, "action");
        Intrinsics.checkParameterIsNotNull(value, "value");
        switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$11[action.ordinal()]) {
            case 1:
                return new ClickEventImpl(new ClickEvent(ClickEvent.Action.OPEN_URL, value));
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IGuiTextField createGuiTextField(int i, @NotNull IFontRenderer iFontRenderer, int i2, int i3, int i4, int i5) {
        Intrinsics.checkParameterIsNotNull(iFontRenderer, "iFontRenderer");
        return new GuiTextFieldImpl(new GuiTextField(i, ((FontRendererImpl) iFontRenderer).getWrapped(), i2, i3, i4, i5));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IGuiTextField createGuiPasswordField(int i, @NotNull IFontRenderer iFontRenderer, int i2, int i3, int i4, int i5) {
        Intrinsics.checkParameterIsNotNull(iFontRenderer, "iFontRenderer");
        return new GuiTextFieldImpl(new GuiPasswordField(i, ((FontRendererImpl) iFontRenderer).getWrapped(), i2, i3, i4, i5));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IGuiButton createGuiButton(int i, int i2, int i3, int i4, int i5, @NotNull String text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        return new GuiButtonImpl(new GuiButton(i, i2, i3, i4, i5, text));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IGuiButton createGuiButton(int i, int i2, int i3, @NotNull String text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        return new GuiButtonImpl(new GuiButton(i, i2, i3, text));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public ISession createSession(@NotNull String name, @NotNull String uuid, @NotNull String accessToken, @NotNull String accountType) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(uuid, "uuid");
        Intrinsics.checkParameterIsNotNull(accessToken, "accessToken");
        Intrinsics.checkParameterIsNotNull(accountType, "accountType");
        return new SessionImpl(new Session(name, uuid, accessToken, accountType));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IDynamicTexture createDynamicTexture(@NotNull BufferedImage image) {
        Intrinsics.checkParameterIsNotNull(image, "image");
        return new DynamicTextureImpl(new DynamicTexture(image));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IItem createItem() {
        return new ItemImpl(new Item());
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IItemStack createItemStack(@NotNull IItem item, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(item, "item");
        return new ItemStackImpl(new ItemStack(((ItemImpl) item).getWrapped(), i, i2));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IItemStack createItemStack(@NotNull IItem item) {
        Intrinsics.checkParameterIsNotNull(item, "item");
        return new ItemStackImpl(new ItemStack(((ItemImpl) item).getWrapped()));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IItemStack createItemStack(@NotNull IBlock blockEnum) {
        Intrinsics.checkParameterIsNotNull(blockEnum, "blockEnum");
        return new ItemStackImpl(new ItemStack(((BlockImpl) blockEnum).getWrapped()));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IAxisAlignedBB createAxisAlignedBB(double d, double d2, double d3, double d4, double d5, double d6) {
        return new AxisAlignedBBImpl(new AxisAlignedBB(d, d2, d3, d4, d5, d6));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IScaledResolution createScaledResolution(@NotNull IMinecraft mc) {
        Intrinsics.checkParameterIsNotNull(mc, "mc");
        return new ScaledResolutionImpl(new ScaledResolution(((MinecraftImpl) mc).getWrapped()));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public INBTTagCompound createNBTTagCompound() {
        return new NBTTagCompoundImpl(new NBTTagCompound());
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public INBTTagList createNBTTagList() {
        return new NBTTagListImpl(new NBTTagList());
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public INBTTagString createNBTTagString(@NotNull String string) {
        Intrinsics.checkParameterIsNotNull(string, "string");
        return new NBTTagStringImpl(new NBTTagString(string));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public INBTTagDouble createNBTTagDouble(double d) {
        return new NBTTagDoubleImpl(new NBTTagDouble(d));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IEntityOtherPlayerMP createEntityOtherPlayerMP(@NotNull IWorldClient world, @NotNull GameProfile gameProfile) {
        Intrinsics.checkParameterIsNotNull(world, "world");
        Intrinsics.checkParameterIsNotNull(gameProfile, "gameProfile");
        return new EntityOtherPlayerMPImpl(new EntityOtherPlayerMP((WorldClient) ((WorldClientImpl) world).getWrapped(), gameProfile));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IEntity createEntityLightningBolt(@NotNull IWorld world, double d, double d2, double d3, boolean z) {
        Intrinsics.checkParameterIsNotNull(world, "world");
        return new EntityImpl(new EntityLightningBolt(((WorldImpl) world).getWrapped(), d, d2, d3, z));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IPotionEffect createPotionEffect(int i, int i2, int i3) {
        return new PotionEffectImpl(new PotionEffect(Potion.func_188412_a(i), i2, i3));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IGuiScreen createGuiOptions(@NotNull IGuiScreen parentScreen, @NotNull IGameSettings gameSettings) {
        Intrinsics.checkParameterIsNotNull(parentScreen, "parentScreen");
        Intrinsics.checkParameterIsNotNull(gameSettings, "gameSettings");
        return new GuiScreenImpl(new GuiOptions(((GuiScreenImpl) parentScreen).getWrapped(), ((GameSettingsImpl) gameSettings).getWrapped()));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IGuiScreen createGuiSelectWorld(@NotNull IGuiScreen parentScreen) {
        Intrinsics.checkParameterIsNotNull(parentScreen, "parentScreen");
        return new GuiScreenImpl(new GuiWorldSelection(((GuiScreenImpl) parentScreen).getWrapped()));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IGuiScreen createGuiMultiplayer(@NotNull IGuiScreen parentScreen) {
        Intrinsics.checkParameterIsNotNull(parentScreen, "parentScreen");
        return new GuiScreenImpl(new GuiMultiplayer(((GuiScreenImpl) parentScreen).getWrapped()));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IGuiScreen createGuiModList(@NotNull IGuiScreen parentScreen) {
        Intrinsics.checkParameterIsNotNull(parentScreen, "parentScreen");
        return new GuiScreenImpl(new GuiModList(((GuiScreenImpl) parentScreen).getWrapped()));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IGuiScreen createGuiConnecting(@NotNull IGuiScreen parent, @NotNull IMinecraft mc, @NotNull IServerData serverData) {
        Intrinsics.checkParameterIsNotNull(parent, "parent");
        Intrinsics.checkParameterIsNotNull(mc, "mc");
        Intrinsics.checkParameterIsNotNull(serverData, "serverData");
        return new GuiScreenImpl(new GuiConnecting(((GuiScreenImpl) parent).getWrapped(), ((MinecraftImpl) mc).getWrapped(), ((ServerDataImpl) serverData).getWrapped()));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public ICPacketHeldItemChange createCPacketHeldItemChange(int i) {
        return new CPacketHeldItemChangeImpl(new CPacketHeldItemChange(i));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public ICPacketPlayerBlockPlacement createCPacketPlayerBlockPlacement(@Nullable IItemStack iItemStack) {
        Backend backend = Backend.INSTANCE;
        throw new NotImplementedError("1.12.2 doesn't support this feature'");
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public ICPacketPlayerBlockPlacement createCPacketPlayerBlockPlacement(@NotNull WBlockPos positionIn, int i, @Nullable IItemStack iItemStack, float f, float f2, float f3) {
        Intrinsics.checkParameterIsNotNull(positionIn, "positionIn");
        return new CPacketPlayerBlockPlacementImpl(new CPacketPlayerTryUseItemOnBlock(new BlockPos(positionIn.getX(), positionIn.getY(), positionIn.getZ()), EnumFacing.values()[i], EnumHand.MAIN_HAND, f, f2, f3));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public ICPacketPlayerPosLook createCPacketPlayerPosLook(double d, double d2, double d3, float f, float f2, boolean z) {
        return new CPacketPlayerPosLookImpl(new CPacketPlayer.PositionRotation(d, d2, d3, f, f2, z));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public ICPacketClientStatus createCPacketClientStatus(@NotNull ICPacketClientStatus.WEnumState state) {
        CPacketClientStatus.State state2;
        Intrinsics.checkParameterIsNotNull(state, "state");
        switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$12[state.ordinal()]) {
            case 1:
                state2 = CPacketClientStatus.State.PERFORM_RESPAWN;
                break;
            case 2:
                state2 = CPacketClientStatus.State.REQUEST_STATS;
                break;
            case 3:
                Backend backend = Backend.INSTANCE;
                throw new NotImplementedError("1.12.2 doesn't support this feature'");
            default:
                throw new NoWhenBranchMatchedException();
        }
        return new CPacketClientStatusImpl(new CPacketClientStatus(state2));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IPacket createCPacketPlayerDigging(@NotNull ICPacketPlayerDigging.WAction wAction, @NotNull WBlockPos pos, @NotNull IEnumFacing facing) {
        CPacketPlayerDigging.Action action;
        Intrinsics.checkParameterIsNotNull(wAction, "wAction");
        Intrinsics.checkParameterIsNotNull(pos, "pos");
        Intrinsics.checkParameterIsNotNull(facing, "facing");
        switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$10[wAction.ordinal()]) {
            case 1:
                action = CPacketPlayerDigging.Action.START_DESTROY_BLOCK;
                break;
            case 2:
                action = CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK;
                break;
            case 3:
                action = CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK;
                break;
            case 4:
                action = CPacketPlayerDigging.Action.DROP_ALL_ITEMS;
                break;
            case 5:
                action = CPacketPlayerDigging.Action.DROP_ITEM;
                break;
            case 6:
                action = CPacketPlayerDigging.Action.RELEASE_USE_ITEM;
                break;
            case 7:
                action = CPacketPlayerDigging.Action.SWAP_HELD_ITEMS;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        return new PacketImpl(new CPacketPlayerDigging(action, new BlockPos(pos.getX(), pos.getY(), pos.getZ()), ((EnumFacingImpl) facing).getWrapped()));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public PacketImpl createCPacketTryUseItem(@NotNull WEnumHand hand) {
        EnumHand enumHand;
        Intrinsics.checkParameterIsNotNull(hand, "hand");
        switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$17[hand.ordinal()]) {
            case 1:
                enumHand = EnumHand.MAIN_HAND;
                break;
            case 2:
                enumHand = EnumHand.OFF_HAND;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        return new PacketImpl(new CPacketPlayerTryUseItem(enumHand));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public ICPacketPlayer createCPacketPlayerPosition(double d, double d2, double d3, boolean z) {
        return new CPacketPlayerImpl(new CPacketPlayer.Position(d, d2, d3, z));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IPacket createICPacketResourcePackStatus(@NotNull String hash, @NotNull ICPacketResourcePackStatus.WAction status) {
        CPacketResourcePackStatus.Action action;
        Intrinsics.checkParameterIsNotNull(hash, "hash");
        Intrinsics.checkParameterIsNotNull(status, "status");
        switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$13[status.ordinal()]) {
            case 1:
                action = CPacketResourcePackStatus.Action.SUCCESSFULLY_LOADED;
                break;
            case 2:
                action = CPacketResourcePackStatus.Action.DECLINED;
                break;
            case 3:
                action = CPacketResourcePackStatus.Action.FAILED_DOWNLOAD;
                break;
            case 4:
                action = CPacketResourcePackStatus.Action.ACCEPTED;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        return new PacketImpl(new CPacketResourcePackStatus(action));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public ICPacketPlayer createCPacketPlayerLook(float f, float f2, boolean z) {
        return new CPacketPlayerImpl(new CPacketPlayer.Rotation(f, f2, z));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public ICPacketUseEntity createCPacketUseEntity(@NotNull IEntity player, @NotNull ICPacketUseEntity.WAction wAction) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        Intrinsics.checkParameterIsNotNull(wAction, "wAction");
        switch (WhenMappings.$EnumSwitchMapping$0[wAction.ordinal()]) {
            case 1:
                return new CPacketUseEntityImpl(new CPacketUseEntity(((EntityImpl) player).getWrapped(), EnumHand.MAIN_HAND));
            case 2:
                return new CPacketUseEntityImpl(new CPacketUseEntity(((EntityImpl) player).getWrapped()));
            case 3:
                Backend backend = Backend.INSTANCE;
                throw new NotImplementedError("1.12.2 doesn't support this feature'");
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public ICPacketUseEntity createCPacketUseEntity(@NotNull IEntity entity, @NotNull WVec3 positionVector) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        Intrinsics.checkParameterIsNotNull(positionVector, "positionVector");
        return new CPacketUseEntityImpl(new CPacketUseEntity(((EntityImpl) entity).getWrapped(), EnumHand.MAIN_HAND, new Vec3d(positionVector.getXCoord(), positionVector.getYCoord(), positionVector.getZCoord())));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IPacket createCPacketCreativeInventoryAction(int i, @NotNull IItemStack itemStack) {
        Intrinsics.checkParameterIsNotNull(itemStack, "itemStack");
        return new PacketImpl(new CPacketCreativeInventoryAction(i, ((ItemStackImpl) itemStack).getWrapped()));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public ICPacketEntityAction createCPacketEntityAction(@NotNull IEntity player, @NotNull ICPacketEntityAction.WAction wAction) {
        CPacketEntityAction.Action action;
        Intrinsics.checkParameterIsNotNull(player, "player");
        Intrinsics.checkParameterIsNotNull(wAction, "wAction");
        Entity wrapped = ((EntityImpl) player).getWrapped();
        switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$15[wAction.ordinal()]) {
            case 1:
                action = CPacketEntityAction.Action.START_SNEAKING;
                break;
            case 2:
                action = CPacketEntityAction.Action.STOP_SNEAKING;
                break;
            case 3:
                action = CPacketEntityAction.Action.STOP_SLEEPING;
                break;
            case 4:
                action = CPacketEntityAction.Action.START_SPRINTING;
                break;
            case 5:
                action = CPacketEntityAction.Action.STOP_SPRINTING;
                break;
            case 6:
                action = CPacketEntityAction.Action.OPEN_INVENTORY;
                break;
            case 7:
                action = CPacketEntityAction.Action.START_RIDING_JUMP;
                break;
            case 8:
                action = CPacketEntityAction.Action.STOP_RIDING_JUMP;
                break;
            case 9:
                action = CPacketEntityAction.Action.START_FALL_FLYING;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        return new CPacketEntityActionImpl(new CPacketEntityAction(wrapped, action));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public ICPacketCustomPayload createCPacketCustomPayload(@NotNull String channel, @NotNull IPacketBuffer payload) {
        Intrinsics.checkParameterIsNotNull(channel, "channel");
        Intrinsics.checkParameterIsNotNull(payload, "payload");
        return new CPacketCustomPayloadImpl(new CPacketCustomPayload(channel, ((PacketBufferImpl) payload).getWrapped()));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public ICPacketCloseWindow createCPacketCloseWindow(int i) {
        return new CPacketCloseWindowImpl(new CPacketCloseWindow(i));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public ICPacketCloseWindow createCPacketCloseWindow() {
        return new CPacketCloseWindowImpl(new CPacketCloseWindow());
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public ICPacketPlayer createCPacketPlayer(boolean z) {
        return new CPacketPlayerImpl(new CPacketPlayer(z));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IPacket createCPacketTabComplete(@NotNull String text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        return new PacketImpl(new CPacketTabComplete(text, (BlockPos) null, false));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public ICPacketAnimation createCPacketAnimation() {
        return new CPacketAnimationImpl(new CPacketAnimation(EnumHand.MAIN_HAND));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public ICPacketKeepAlive createCPacketKeepAlive() {
        return new CPacketKeepAliveImpl(new CPacketKeepAlive());
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityAnimal(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityAnimal);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntitySquid(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntitySquid);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityBat(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityBat);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityGolem(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityGolem);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityMob(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityMob);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityVillager(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityVillager);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntitySlime(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntitySlime);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityFireball(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityFireball);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityGhast(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityGhast);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityDragon(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityDragon);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityLivingBase(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityLivingBase);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityPlayerSP(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityPlayerSP);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityPlayer(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityPlayer);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityArmorStand(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityArmorStand);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityTNTPrimed(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityTNTPrimed);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityBoat(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityBoat);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityMinecart(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityMinecart);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityItem(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityItem);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityArrow(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityArrow);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityFallingBlock(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityFallingBlock);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityMinecartChest(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityMinecartChest);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isEntityShulker(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && (((EntityImpl) obj).getWrapped() instanceof EntityShulker);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isTileEntityChest(@Nullable Object obj) {
        return (obj instanceof TileEntityImpl) && (((TileEntityImpl) obj).getWrapped() instanceof TileEntityChest);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isTileEntityEnderChest(@Nullable Object obj) {
        return (obj instanceof TileEntityImpl) && (((TileEntityImpl) obj).getWrapped() instanceof TileEntityEnderChest);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isTileEntityFurnace(@Nullable Object obj) {
        return (obj instanceof TileEntityImpl) && (((TileEntityImpl) obj).getWrapped() instanceof TileEntityFurnace);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isTileEntityDispenser(@Nullable Object obj) {
        return (obj instanceof TileEntityImpl) && (((TileEntityImpl) obj).getWrapped() instanceof TileEntityDispenser);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isTileEntityHopper(@Nullable Object obj) {
        return (obj instanceof TileEntityImpl) && (((TileEntityImpl) obj).getWrapped() instanceof TileEntityHopper);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isTileEntityShulkerBox(@Nullable Object obj) {
        return (obj instanceof TileEntityImpl) && (((TileEntityImpl) obj).getWrapped() instanceof TileEntityShulkerBox);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isSPacketChat(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof SPacketChat);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isSPacketEntity(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof SPacketEntity);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isSPacketResourcePackSend(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof SPacketResourcePackSend);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isSPacketPlayerPosLook(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof SPacketPlayerPosLook);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isSPacketTitle(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof SPacketTitle);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isSPacketAnimation(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof SPacketAnimation);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isSPacketEntityVelocity(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof SPacketEntityVelocity);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isSPacketExplosion(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof SPacketExplosion);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isSPacketCloseWindow(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof SPacketCloseWindow);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isSPacketUpdateHealth(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof SPacketUpdateHealth);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isSPacketTabComplete(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof SPacketTabComplete);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isSPacketSpawnGlobalEntity(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof SPacketSpawnGlobalEntity);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketPlayer(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof CPacketPlayer);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketConfirmTransaction(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof CPacketConfirmTransaction);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketPlayerBlockPlacement(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof CPacketPlayerTryUseItemOnBlock);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketUseEntity(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof CPacketUseEntity);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketCloseWindow(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof CPacketCloseWindow);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketChatMessage(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof CPacketChatMessage);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketKeepAlive(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof CPacketKeepAlive);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketPlayerPosition(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof CPacketPlayer.Position);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketPlayerPosLook(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof CPacketPlayer.PositionRotation);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketClientStatus(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof CPacketClientStatus);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketAnimation(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof CPacketAnimation);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketClickWindow(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof CPacketClickWindow);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketEntityAction(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof CPacketEntityAction);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isSPacketWindowItems(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof SPacketWindowItems);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketHeldItemChange(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof CPacketHeldItemChange);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketPlayerLook(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof CPacketPlayer.Rotation);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketCustomPayload(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof CPacketCustomPayload);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketHandshake(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof C00Handshake);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketPlayerDigging(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof CPacketPlayerDigging);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isCPacketTryUseItem(@Nullable Object obj) {
        return (obj instanceof PacketImpl) && (((PacketImpl) obj).getWrapped() instanceof CPacketPlayerTryUseItem);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemSword(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemSword);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemTool(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemTool);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemArmor(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemArmor);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemPotion(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemPotion);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemBlock(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemBlock);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemBow(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemBow);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemBucket(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemBucket);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemFood(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemFood);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemBucketMilk(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemBucketMilk);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemPickaxe(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemPickaxe);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemAxe(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemAxe);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemShears(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemShears);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemBed(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemBed);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemEnderPearl(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemEnderPearl);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemEnchantedBook(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemEnchantedBook);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemBoat(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemBoat);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemMinecart(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemMinecart);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemAppleGold(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemAppleGold);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemSnowball(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemSnowball);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemEgg(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemEgg);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemFishingRod(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemFishingRod);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isItemAir(@Nullable Object obj) {
        return (obj instanceof ItemImpl) && (((ItemImpl) obj).getWrapped() instanceof ItemAir);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isBlockAir(@Nullable Object obj) {
        return (obj instanceof BlockImpl) && (((BlockImpl) obj).getWrapped() instanceof BlockAir);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isBlockFence(@Nullable Object obj) {
        return (obj instanceof BlockImpl) && (((BlockImpl) obj).getWrapped() instanceof BlockFence);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isBlockSnow(@Nullable Object obj) {
        return (obj instanceof BlockImpl) && (((BlockImpl) obj).getWrapped() instanceof BlockSnow);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isBlockLadder(@Nullable Object obj) {
        return (obj instanceof BlockImpl) && (((BlockImpl) obj).getWrapped() instanceof BlockLadder);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isBlockVine(@Nullable Object obj) {
        return (obj instanceof BlockImpl) && (((BlockImpl) obj).getWrapped() instanceof BlockVine);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isBlockSlime(@Nullable Object obj) {
        return (obj instanceof BlockImpl) && (((BlockImpl) obj).getWrapped() instanceof BlockSlime);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isBlockSlab(@Nullable Object obj) {
        return (obj instanceof BlockImpl) && (((BlockImpl) obj).getWrapped() instanceof BlockSlab);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isBlockStairs(@Nullable Object obj) {
        return (obj instanceof BlockImpl) && (((BlockImpl) obj).getWrapped() instanceof BlockStairs);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isBlockCarpet(@Nullable Object obj) {
        return (obj instanceof BlockImpl) && (((BlockImpl) obj).getWrapped() instanceof BlockCarpet);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isBlockPane(@Nullable Object obj) {
        return (obj instanceof BlockImpl) && (((BlockImpl) obj).getWrapped() instanceof BlockPane);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isBlockLiquid(@Nullable Object obj) {
        return (obj instanceof BlockImpl) && (((BlockImpl) obj).getWrapped() instanceof BlockLiquid);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isBlockCactus(@Nullable Object obj) {
        return (obj instanceof BlockImpl) && (((BlockImpl) obj).getWrapped() instanceof BlockCactus);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isBlockBedrock(@Nullable Object obj) {
        return (obj instanceof BlockImpl) && Intrinsics.areEqual(((BlockImpl) obj).getWrapped(), Blocks.field_150357_h);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isBlockBush(@Nullable Object obj) {
        return (obj instanceof BlockImpl) && (((BlockImpl) obj).getWrapped() instanceof BlockBush);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isGuiInventory(@Nullable Object obj) {
        return (obj instanceof GuiImpl) && (((GuiImpl) obj).getWrapped() instanceof GuiInventory);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isGuiContainer(@Nullable Object obj) {
        return (obj instanceof GuiImpl) && (((GuiImpl) obj).getWrapped() instanceof GuiContainer);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isGuiGameOver(@Nullable Object obj) {
        return (obj instanceof GuiImpl) && (((GuiImpl) obj).getWrapped() instanceof GuiGameOver);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isGuiChat(@Nullable Object obj) {
        return (obj instanceof GuiImpl) && (((GuiImpl) obj).getWrapped() instanceof GuiChat);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isGuiIngameMenu(@Nullable Object obj) {
        return (obj instanceof GuiImpl) && (((GuiImpl) obj).getWrapped() instanceof GuiIngameMenu);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isGuiChest(@Nullable Object obj) {
        return (obj instanceof GuiImpl) && (((GuiImpl) obj).getWrapped() instanceof GuiChest);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isGuiHudDesigner(@Nullable Object obj) {
        return (obj instanceof GuiScreenImpl) && (((GuiScreenImpl) obj).getWrapped() instanceof GuiScreenWrapper) && (((GuiScreenImpl) obj).getWrapped().getWrapped() instanceof GuiHudDesigner);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public boolean isClickGui(@Nullable Object obj) {
        return (obj instanceof GuiScreenImpl) && (((GuiScreenImpl) obj).getWrapped() instanceof GuiScreenWrapper) && (((GuiScreenImpl) obj).getWrapped().getWrapped() instanceof ClickGui);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IPotion getPotionEnum(@NotNull PotionType type) {
        Potion potion;
        Intrinsics.checkParameterIsNotNull(type, "type");
        switch (WhenMappings.$EnumSwitchMapping$1[type.ordinal()]) {
            case 1:
                potion = MobEffects.field_76432_h;
                break;
            case 2:
                potion = MobEffects.field_76428_l;
                break;
            case 3:
                potion = MobEffects.field_76440_q;
                break;
            case 4:
                potion = MobEffects.field_76424_c;
                break;
            case 5:
                potion = MobEffects.field_76438_s;
                break;
            case 6:
                potion = MobEffects.field_76419_f;
                break;
            case 7:
                potion = MobEffects.field_76431_k;
                break;
            case 8:
                potion = MobEffects.field_76437_t;
                break;
            case 9:
                potion = MobEffects.field_76421_d;
                break;
            case 10:
                potion = MobEffects.field_76433_i;
                break;
            case 11:
                potion = MobEffects.field_82731_v;
                break;
            case 12:
                potion = MobEffects.field_76436_u;
                break;
            case CharacterType.ALNUM /* 13 */:
                potion = MobEffects.field_76439_r;
                break;
            case 14:
                potion = MobEffects.field_76430_j;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        Intrinsics.checkExpressionValueIsNotNull(potion, "when (type) {\n          \u2026ects.JUMP_BOOST\n        }");
        return new PotionImpl(potion);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IEnumFacing getEnumFacing(@NotNull EnumFacingType type) {
        EnumFacing enumFacing;
        Intrinsics.checkParameterIsNotNull(type, "type");
        switch (WhenMappings.$EnumSwitchMapping$2[type.ordinal()]) {
            case 1:
                enumFacing = EnumFacing.DOWN;
                break;
            case 2:
                enumFacing = EnumFacing.UP;
                break;
            case 3:
                enumFacing = EnumFacing.NORTH;
                break;
            case 4:
                enumFacing = EnumFacing.SOUTH;
                break;
            case 5:
                enumFacing = EnumFacing.WEST;
                break;
            case 6:
                enumFacing = EnumFacing.EAST;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        return new EnumFacingImpl(enumFacing);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IBlock getBlockEnum(@NotNull BlockType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        switch (WhenMappings.$EnumSwitchMapping$3[type.ordinal()]) {
            case 1:
                Block block = Blocks.field_150381_bn;
                Intrinsics.checkExpressionValueIsNotNull(block, "Blocks.ENCHANTING_TABLE");
                return new BlockImpl(block);
            case 2:
                Block block2 = Blocks.field_150486_ae;
                Intrinsics.checkExpressionValueIsNotNull(block2, "Blocks.CHEST");
                return new BlockImpl(block2);
            case 3:
                Block block3 = Blocks.field_150477_bB;
                Intrinsics.checkExpressionValueIsNotNull(block3, "Blocks.ENDER_CHEST");
                return new BlockImpl(block3);
            case 4:
                Block block4 = Blocks.field_150447_bR;
                Intrinsics.checkExpressionValueIsNotNull(block4, "Blocks.TRAPPED_CHEST");
                return new BlockImpl(block4);
            case 5:
                Block block5 = Blocks.field_150467_bQ;
                Intrinsics.checkExpressionValueIsNotNull(block5, "Blocks.ANVIL");
                return new BlockImpl(block5);
            case 6:
                Block block6 = Blocks.field_150354_m;
                Intrinsics.checkExpressionValueIsNotNull(block6, "Blocks.SAND");
                return new BlockImpl(block6);
            case 7:
                Block block7 = Blocks.field_150321_G;
                Intrinsics.checkExpressionValueIsNotNull(block7, "Blocks.WEB");
                return new BlockImpl(block7);
            case 8:
                Block block8 = Blocks.field_150478_aa;
                Intrinsics.checkExpressionValueIsNotNull(block8, "Blocks.TORCH");
                return new BlockImpl(block8);
            case 9:
                Block block9 = Blocks.field_150462_ai;
                Intrinsics.checkExpressionValueIsNotNull(block9, "Blocks.CRAFTING_TABLE");
                return new BlockImpl(block9);
            case 10:
                Block block10 = Blocks.field_150460_al;
                Intrinsics.checkExpressionValueIsNotNull(block10, "Blocks.FURNACE");
                return new BlockImpl(block10);
            case 11:
                Block block11 = Blocks.field_150392_bi;
                Intrinsics.checkExpressionValueIsNotNull(block11, "Blocks.WATERLILY");
                return new BlockImpl(block11);
            case 12:
                Block block12 = Blocks.field_150367_z;
                Intrinsics.checkExpressionValueIsNotNull(block12, "Blocks.DISPENSER");
                return new BlockImpl(block12);
            case CharacterType.ALNUM /* 13 */:
                Block block13 = Blocks.field_150456_au;
                Intrinsics.checkExpressionValueIsNotNull(block13, "Blocks.STONE_PRESSURE_PLATE");
                return new BlockImpl(block13);
            case 14:
                Block block14 = Blocks.field_150452_aw;
                Intrinsics.checkExpressionValueIsNotNull(block14, "Blocks.WOODEN_PRESSURE_PLATE");
                return new BlockImpl(block14);
            case OPCode.EXACTN_IC /* 15 */:
                Block block15 = Blocks.field_150335_W;
                Intrinsics.checkExpressionValueIsNotNull(block15, "Blocks.TNT");
                return new BlockImpl(block15);
            case 16:
                Block block16 = Blocks.field_180393_cK;
                Intrinsics.checkExpressionValueIsNotNull(block16, "Blocks.STANDING_BANNER");
                return new BlockImpl(block16);
            case OPCode.CCLASS_MB /* 17 */:
                Block block17 = Blocks.field_180394_cL;
                Intrinsics.checkExpressionValueIsNotNull(block17, "Blocks.WALL_BANNER");
                return new BlockImpl(block17);
            case OPCode.CCLASS_MIX /* 18 */:
                Block block18 = Blocks.field_150429_aA;
                Intrinsics.checkExpressionValueIsNotNull(block18, "Blocks.REDSTONE_TORCH");
                return new BlockImpl(block18);
            case OPCode.CCLASS_NOT /* 19 */:
                Block block19 = Blocks.field_150323_B;
                Intrinsics.checkExpressionValueIsNotNull(block19, "Blocks.NOTEBLOCK");
                return new BlockImpl(block19);
            case 20:
                Block block20 = Blocks.field_150409_cd;
                Intrinsics.checkExpressionValueIsNotNull(block20, "Blocks.DROPPER");
                return new BlockImpl(block20);
            case OPCode.CCLASS_MIX_NOT /* 21 */:
                Block block21 = Blocks.field_150431_aC;
                Intrinsics.checkExpressionValueIsNotNull(block21, "Blocks.SNOW_LAYER");
                return new BlockImpl(block21);
            case OPCode.CCLASS_NODE /* 22 */:
                Block block22 = Blocks.field_150348_b;
                Intrinsics.checkExpressionValueIsNotNull(block22, "Blocks.STONE");
                return new BlockImpl(block22);
            case OPCode.ANYCHAR /* 23 */:
                Block block23 = Blocks.field_150350_a;
                Intrinsics.checkExpressionValueIsNotNull(block23, "Blocks.AIR");
                return new BlockImpl(block23);
            case 24:
                Block block24 = Blocks.field_150403_cj;
                Intrinsics.checkExpressionValueIsNotNull(block24, "Blocks.PACKED_ICE");
                return new BlockImpl(block24);
            case OPCode.ANYCHAR_STAR /* 25 */:
                Block block25 = Blocks.field_150432_aD;
                Intrinsics.checkExpressionValueIsNotNull(block25, "Blocks.ICE");
                return new BlockImpl(block25);
            case OPCode.ANYCHAR_ML_STAR /* 26 */:
                Block block26 = Blocks.field_150355_j;
                Intrinsics.checkExpressionValueIsNotNull(block26, "Blocks.WATER");
                return new BlockImpl(block26);
            case OPCode.ANYCHAR_STAR_PEEK_NEXT /* 27 */:
                Block block27 = Blocks.field_180401_cv;
                Intrinsics.checkExpressionValueIsNotNull(block27, "Blocks.BARRIER");
                return new BlockImpl(block27);
            case OPCode.ANYCHAR_ML_STAR_PEEK_NEXT /* 28 */:
                Block block28 = Blocks.field_150358_i;
                Intrinsics.checkExpressionValueIsNotNull(block28, "Blocks.FLOWING_WATER");
                return new BlockImpl(block28);
            case OPCode.WORD /* 29 */:
                Block block29 = Blocks.field_150365_q;
                Intrinsics.checkExpressionValueIsNotNull(block29, "Blocks.COAL_ORE");
                return new BlockImpl(block29);
            case 30:
                Block block30 = Blocks.field_150366_p;
                Intrinsics.checkExpressionValueIsNotNull(block30, "Blocks.IRON_ORE");
                return new BlockImpl(block30);
            case 31:
                Block block31 = Blocks.field_150352_o;
                Intrinsics.checkExpressionValueIsNotNull(block31, "Blocks.GOLD_ORE");
                return new BlockImpl(block31);
            case 32:
                Block block32 = Blocks.field_150450_ax;
                Intrinsics.checkExpressionValueIsNotNull(block32, "Blocks.REDSTONE_ORE");
                return new BlockImpl(block32);
            case OPCode.WORD_BEGIN /* 33 */:
                Block block33 = Blocks.field_150369_x;
                Intrinsics.checkExpressionValueIsNotNull(block33, "Blocks.LAPIS_ORE");
                return new BlockImpl(block33);
            case 34:
                Block block34 = Blocks.field_150482_ag;
                Intrinsics.checkExpressionValueIsNotNull(block34, "Blocks.DIAMOND_ORE");
                return new BlockImpl(block34);
            case OPCode.BEGIN_BUF /* 35 */:
                Block block35 = Blocks.field_150412_bA;
                Intrinsics.checkExpressionValueIsNotNull(block35, "Blocks.EMERALD_ORE");
                return new BlockImpl(block35);
            case 36:
                Block block36 = Blocks.field_150449_bY;
                Intrinsics.checkExpressionValueIsNotNull(block36, "Blocks.QUARTZ_ORE");
                return new BlockImpl(block36);
            case OPCode.BEGIN_LINE /* 37 */:
                Block block37 = Blocks.field_150435_aG;
                Intrinsics.checkExpressionValueIsNotNull(block37, "Blocks.CLAY");
                return new BlockImpl(block37);
            case 38:
                Block block38 = Blocks.field_150426_aN;
                Intrinsics.checkExpressionValueIsNotNull(block38, "Blocks.GLOWSTONE");
                return new BlockImpl(block38);
            case OPCode.SEMI_END_BUF /* 39 */:
                Block block39 = Blocks.field_150468_ap;
                Intrinsics.checkExpressionValueIsNotNull(block39, "Blocks.LADDER");
                return new BlockImpl(block39);
            case 40:
                Block block40 = Blocks.field_150402_ci;
                Intrinsics.checkExpressionValueIsNotNull(block40, "Blocks.COAL_BLOCK");
                return new BlockImpl(block40);
            case OPCode.BACKREF1 /* 41 */:
                Block block41 = Blocks.field_150339_S;
                Intrinsics.checkExpressionValueIsNotNull(block41, "Blocks.IRON_BLOCK");
                return new BlockImpl(block41);
            case OPCode.BACKREF2 /* 42 */:
                Block block42 = Blocks.field_150340_R;
                Intrinsics.checkExpressionValueIsNotNull(block42, "Blocks.GOLD_BLOCK");
                return new BlockImpl(block42);
            case OPCode.BACKREFN /* 43 */:
                Block block43 = Blocks.field_150484_ah;
                Intrinsics.checkExpressionValueIsNotNull(block43, "Blocks.DIAMOND_BLOCK");
                return new BlockImpl(block43);
            case OPCode.BACKREFN_IC /* 44 */:
                Block block44 = Blocks.field_150475_bE;
                Intrinsics.checkExpressionValueIsNotNull(block44, "Blocks.EMERALD_BLOCK");
                return new BlockImpl(block44);
            case OPCode.BACKREF_MULTI /* 45 */:
                Block block45 = Blocks.field_150451_bX;
                Intrinsics.checkExpressionValueIsNotNull(block45, "Blocks.REDSTONE_BLOCK");
                return new BlockImpl(block45);
            case OPCode.BACKREF_MULTI_IC /* 46 */:
                Block block46 = Blocks.field_150368_y;
                Intrinsics.checkExpressionValueIsNotNull(block46, "Blocks.LAPIS_BLOCK");
                return new BlockImpl(block46);
            case OPCode.BACKREF_WITH_LEVEL /* 47 */:
                Block block47 = Blocks.field_150480_ab;
                Intrinsics.checkExpressionValueIsNotNull(block47, "Blocks.FIRE");
                return new BlockImpl(block47);
            case 48:
                Block block48 = Blocks.field_150341_Y;
                Intrinsics.checkExpressionValueIsNotNull(block48, "Blocks.MOSSY_COBBLESTONE");
                return new BlockImpl(block48);
            case OPCode.MEMORY_START_PUSH /* 49 */:
                Block block49 = Blocks.field_150474_ac;
                Intrinsics.checkExpressionValueIsNotNull(block49, "Blocks.MOB_SPAWNER");
                return new BlockImpl(block49);
            case OPCode.MEMORY_END_PUSH /* 50 */:
                Block block50 = Blocks.field_150378_br;
                Intrinsics.checkExpressionValueIsNotNull(block50, "Blocks.END_PORTAL_FRAME");
                return new BlockImpl(block50);
            case OPCode.MEMORY_END_PUSH_REC /* 51 */:
                Block block51 = Blocks.field_150342_X;
                Intrinsics.checkExpressionValueIsNotNull(block51, "Blocks.BOOKSHELF");
                return new BlockImpl(block51);
            case OPCode.MEMORY_END /* 52 */:
                Block block52 = Blocks.field_150483_bI;
                Intrinsics.checkExpressionValueIsNotNull(block52, "Blocks.COMMAND_BLOCK");
                return new BlockImpl(block52);
            case OPCode.MEMORY_END_REC /* 53 */:
                Block block53 = Blocks.field_150353_l;
                Intrinsics.checkExpressionValueIsNotNull(block53, "Blocks.LAVA");
                return new BlockImpl(block53);
            case OPCode.FAIL /* 54 */:
                Block block54 = Blocks.field_150356_k;
                Intrinsics.checkExpressionValueIsNotNull(block54, "Blocks.FLOWING_LAVA");
                return new BlockImpl(block54);
            case OPCode.JUMP /* 55 */:
                Block block55 = Blocks.field_150470_am;
                Intrinsics.checkExpressionValueIsNotNull(block55, "Blocks.LIT_FURNACE");
                return new BlockImpl(block55);
            case 56:
                Block block56 = Blocks.field_150380_bt;
                Intrinsics.checkExpressionValueIsNotNull(block56, "Blocks.DRAGON_EGG");
                return new BlockImpl(block56);
            case OPCode.POP /* 57 */:
                Block block57 = Blocks.field_150420_aW;
                Intrinsics.checkExpressionValueIsNotNull(block57, "Blocks.BROWN_MUSHROOM_BLOCK");
                return new BlockImpl(block57);
            case OPCode.PUSH_OR_JUMP_EXACT1 /* 58 */:
                Block block58 = Blocks.field_150419_aX;
                Intrinsics.checkExpressionValueIsNotNull(block58, "Blocks.RED_MUSHROOM_BLOCK");
                return new BlockImpl(block58);
            case OPCode.PUSH_IF_PEEK_NEXT /* 59 */:
                Block block59 = Blocks.field_150458_ak;
                Intrinsics.checkExpressionValueIsNotNull(block59, "Blocks.FARMLAND");
                return new BlockImpl(block59);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IMaterial getMaterialEnum(@NotNull MaterialType type) {
        Material material;
        Intrinsics.checkParameterIsNotNull(type, "type");
        switch (WhenMappings.$EnumSwitchMapping$4[type.ordinal()]) {
            case 1:
                material = Material.field_151579_a;
                break;
            case 2:
                material = Material.field_151586_h;
                break;
            case 3:
                material = Material.field_151587_i;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        Intrinsics.checkExpressionValueIsNotNull(material, "when (type) {\n          \u2026al.LAVA\n                }");
        return new MaterialImpl(material);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IStatBase getStatEnum(@NotNull StatType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        switch (WhenMappings.$EnumSwitchMapping$5[type.ordinal()]) {
            case 1:
                StatBase statBase = StatList.field_75953_u;
                Intrinsics.checkExpressionValueIsNotNull(statBase, "when (type) {\n          \u2026st.JUMP\n                }");
                return new StatBaseImpl(statBase);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IItem getItemEnum(@NotNull ItemType type) {
        Item item;
        Intrinsics.checkParameterIsNotNull(type, "type");
        switch (WhenMappings.$EnumSwitchMapping$6[type.ordinal()]) {
            case 1:
                item = Items.field_151009_A;
                break;
            case 2:
                item = Items.field_151054_z;
                break;
            case 3:
                item = Items.field_151033_d;
                break;
            case 4:
                item = Items.field_151129_at;
                break;
            case 5:
                item = Items.field_151099_bA;
                break;
            case 6:
                item = Items.field_151131_as;
                break;
            case 7:
                item = Items.field_151095_cc;
                break;
            case 8:
                item = (Item) Items.field_151068_bn;
                break;
            case 9:
                item = Items.field_151144_bL;
                break;
            case 10:
                item = Items.field_151153_ao;
                break;
            case 11:
                item = Items.field_179565_cj;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        return new ItemImpl(item);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IEnchantment getEnchantmentEnum(@NotNull EnchantmentType type) {
        Enchantment enchantment;
        Intrinsics.checkParameterIsNotNull(type, "type");
        switch (WhenMappings.$EnumSwitchMapping$7[type.ordinal()]) {
            case 1:
                enchantment = Enchantments.field_185302_k;
                break;
            case 2:
                enchantment = Enchantments.field_185309_u;
                break;
            case 3:
                enchantment = Enchantments.field_180310_c;
                break;
            case 4:
                enchantment = Enchantments.field_180309_e;
                break;
            case 5:
                enchantment = Enchantments.field_180308_g;
                break;
            case 6:
                enchantment = Enchantments.field_92091_k;
                break;
            case 7:
                enchantment = Enchantments.field_77329_d;
                break;
            case 8:
                enchantment = Enchantments.field_185298_f;
                break;
            case 9:
                enchantment = Enchantments.field_185299_g;
                break;
            case 10:
                enchantment = Enchantments.field_185297_d;
                break;
            case 11:
                enchantment = Enchantments.field_185307_s;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        Intrinsics.checkExpressionValueIsNotNull(enchantment, "when (type) {\n          \u2026REAKING\n                }");
        return new EnchantmentImpl(enchantment);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IVertexFormat getVertexFormatEnum(@NotNull WDefaultVertexFormats type) {
        VertexFormat vertexFormat;
        Intrinsics.checkParameterIsNotNull(type, "type");
        switch (WhenMappings.$EnumSwitchMapping$8[type.ordinal()]) {
            case 1:
                vertexFormat = DefaultVertexFormats.field_181705_e;
                break;
            case 2:
                vertexFormat = DefaultVertexFormats.field_181707_g;
                break;
            case 3:
                vertexFormat = DefaultVertexFormats.field_181706_f;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        Intrinsics.checkExpressionValueIsNotNull(vertexFormat, "when (type) {\n          \u2026N_COLOR\n                }");
        return new VertexFormatImpl(vertexFormat);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IFontRenderer wrapFontRenderer(@NotNull IWrappedFontRenderer fontRenderer) {
        Intrinsics.checkParameterIsNotNull(fontRenderer, "fontRenderer");
        return new FontRendererImpl(new FontRendererWrapper(fontRenderer));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IGuiScreen wrapGuiScreen(@NotNull WrappedGuiScreen clickGui) {
        Intrinsics.checkParameterIsNotNull(clickGui, "clickGui");
        GuiScreenImpl guiScreenImpl = new GuiScreenImpl(new GuiScreenWrapper(clickGui));
        clickGui.setRepresentedScreen(guiScreenImpl);
        return guiScreenImpl;
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IVertexBuffer createSafeVertexBuffer(@NotNull IVertexFormat vertexFormat) {
        Intrinsics.checkParameterIsNotNull(vertexFormat, "vertexFormat");
        return new VertexBufferImpl(new SafeVertexBuffer(((VertexFormatImpl) vertexFormat).getWrapped()));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public void wrapCreativeTab(@NotNull String name, @NotNull WrappedCreativeTabs wrappedCreativeTabs) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(wrappedCreativeTabs, "wrappedCreativeTabs");
        wrappedCreativeTabs.setRepresentedType(new CreativeTabsImpl(new CreativeTabsWrapper(wrappedCreativeTabs, name)));
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    public void wrapGuiSlot(@NotNull WrappedGuiSlot wrappedGuiSlot, @NotNull IMinecraft mc, int i, int i2, int i3, int i4, int i5) {
        Intrinsics.checkParameterIsNotNull(wrappedGuiSlot, "wrappedGuiSlot");
        Intrinsics.checkParameterIsNotNull(mc, "mc");
        new GuiSlotWrapper(wrappedGuiSlot, mc, i, i2, i3, i4, i5);
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IGlStateManager getGlStateManager() {
        return GlStateManagerImpl.INSTANCE;
    }

    @Override // net.ccbluex.liquidbounce.api.IClassProvider
    @NotNull
    public IPacket createCPacketEncryptionResponse(@NotNull SecretKey secretKey, @NotNull PublicKey publicKey, @NotNull byte[] verifyToken) {
        Intrinsics.checkParameterIsNotNull(secretKey, "secretKey");
        Intrinsics.checkParameterIsNotNull(publicKey, "publicKey");
        Intrinsics.checkParameterIsNotNull(verifyToken, "verifyToken");
        return new PacketImpl(new CPacketEncryptionResponse(secretKey, publicKey, verifyToken));
    }
}
