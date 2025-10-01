package net.ccbluex.liquidbounce.api;

import com.mojang.authlib.GameProfile;
import io.netty.buffer.ByteBuf;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
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
import net.ccbluex.liquidbounce.injection.backend.PacketImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u00c2\u0004\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0012\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\bj\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\ufffd\ufffd2\u00020\u0001J8\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\rH&J\b\u0010\u0013\u001a\u00020\u0014H&J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H&J\b\u0010\u0019\u001a\u00020\u001aH&J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH&J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020!H&J\u0018\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H&J \u0010(\u001a\u00020\u001e2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H&J\u0018\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H&J\u0010\u00105\u001a\u0002062\u0006\u0010\u001f\u001a\u00020\u001cH&J\b\u00107\u001a\u000208H&J\u0010\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020<H&J\u0012\u0010=\u001a\u00020>2\b\u0010?\u001a\u0004\u0018\u00010!H'J:\u0010=\u001a\u00020>2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020\u001c2\b\u0010C\u001a\u0004\u0018\u00010!2\u0006\u0010D\u001a\u00020E2\u0006\u0010F\u001a\u00020E2\u0006\u0010G\u001a\u00020EH&J \u0010H\u001a\u00020\u001e2\u0006\u00103\u001a\u00020I2\u0006\u0010J\u001a\u00020A2\u0006\u0010K\u001a\u00020LH&J \u0010M\u001a\u00020:2\u0006\u0010N\u001a\u00020E2\u0006\u0010O\u001a\u00020E2\u0006\u0010;\u001a\u00020<H&J8\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020\r2\u0006\u0010S\u001a\u00020\r2\u0006\u0010T\u001a\u00020\r2\u0006\u0010N\u001a\u00020E2\u0006\u0010O\u001a\u00020E2\u0006\u0010;\u001a\u00020<H&J(\u0010U\u001a\u00020:2\u0006\u0010R\u001a\u00020\r2\u0006\u0010S\u001a\u00020\r2\u0006\u0010T\u001a\u00020\r2\u0006\u0010;\u001a\u00020<H&J\u0010\u0010V\u001a\u00020\u001e2\u0006\u0010W\u001a\u00020%H&J\u0014\u0010X\u001a\u0006\u0012\u0002\b\u00030Y2\u0006\u0010Z\u001a\u00020[H'J\u0018\u0010\\\u001a\u00020]2\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u00020^H&J\u0018\u0010\\\u001a\u00020]2\u0006\u0010_\u001a\u0002022\u0006\u0010`\u001a\u00020aH&J\u0010\u0010b\u001a\u00020c2\u0006\u0010W\u001a\u00020%H&J\u0018\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020g2\u0006\u0010h\u001a\u00020%H&J\u0010\u0010i\u001a\u00020j2\u0006\u0010k\u001a\u00020lH&J0\u0010m\u001a\u0002022\u0006\u0010n\u001a\u00020o2\u0006\u0010p\u001a\u00020\r2\u0006\u0010q\u001a\u00020\r2\u0006\u0010r\u001a\u00020\r2\u0006\u0010s\u001a\u00020<H&J\u0018\u0010t\u001a\u00020u2\u0006\u0010n\u001a\u00020v2\u0006\u0010w\u001a\u00020xH&J8\u0010y\u001a\u00020z2\u0006\u0010{\u001a\u00020\u001c2\u0006\u0010R\u001a\u00020\u001c2\u0006\u0010S\u001a\u00020\u001c2\u0006\u0010|\u001a\u00020\u001c2\u0006\u0010}\u001a\u00020\u001c2\u0006\u0010W\u001a\u00020%H&J(\u0010y\u001a\u00020z2\u0006\u0010{\u001a\u00020\u001c2\u0006\u0010R\u001a\u00020\u001c2\u0006\u0010S\u001a\u00020\u001c2\u0006\u0010W\u001a\u00020%H&J%\u0010~\u001a\u00020\u007f2\u0007\u0010\u0080\u0001\u001a\u00020\u007f2\b\u0010\u0081\u0001\u001a\u00030\u0082\u00012\b\u0010\u0083\u0001\u001a\u00030\u0084\u0001H&J\u0012\u0010\u0085\u0001\u001a\u00020\u007f2\u0007\u0010\u0086\u0001\u001a\u00020\u007fH&J\u0012\u0010\u0087\u0001\u001a\u00020\u007f2\u0007\u0010\u0086\u0001\u001a\u00020\u007fH&J\u001c\u0010\u0088\u0001\u001a\u00020\u007f2\u0007\u0010\u0086\u0001\u001a\u00020\u007f2\b\u0010\u0089\u0001\u001a\u00030\u008a\u0001H&J<\u0010\u008b\u0001\u001a\u00030\u008c\u00012\u0006\u0010{\u001a\u00020\u001c2\b\u0010\u008d\u0001\u001a\u00030\u008e\u00012\u0006\u0010R\u001a\u00020\u001c2\u0006\u0010S\u001a\u00020\u001c2\u0006\u0010|\u001a\u00020\u001c2\u0006\u0010}\u001a\u00020\u001cH&J\u0012\u0010\u008f\u0001\u001a\u00020\u007f2\u0007\u0010\u0086\u0001\u001a\u00020\u007fH&J<\u0010\u0090\u0001\u001a\u00030\u008c\u00012\u0006\u0010{\u001a\u00020\u001c2\b\u0010\u008d\u0001\u001a\u00030\u008e\u00012\u0006\u0010R\u001a\u00020\u001c2\u0006\u0010S\u001a\u00020\u001c2\u0006\u0010|\u001a\u00020\u001c2\u0006\u0010}\u001a\u00020\u001cH&J\u001c\u0010\u0091\u0001\u001a\u00020\u001e2\u0007\u0010\u0092\u0001\u001a\u00020%2\b\u0010\u0093\u0001\u001a\u00030\u0094\u0001H&J\n\u0010\u0095\u0001\u001a\u00030\u0096\u0001H&J\u0013\u0010\u0097\u0001\u001a\u00020!2\b\u0010\u0098\u0001\u001a\u00030\u0099\u0001H&J\u0013\u0010\u0097\u0001\u001a\u00020!2\b\u0010\u009a\u0001\u001a\u00030\u0096\u0001H&J%\u0010\u0097\u0001\u001a\u00020!2\b\u0010\u009a\u0001\u001a\u00030\u0096\u00012\u0007\u0010\u009b\u0001\u001a\u00020\u001c2\u0007\u0010\u009c\u0001\u001a\u00020\u001cH&J\n\u0010\u009d\u0001\u001a\u00030\u009e\u0001H&J\u0012\u0010\u009f\u0001\u001a\u00030\u00a0\u00012\u0006\u0010h\u001a\u00020\rH&J\n\u0010\u00a1\u0001\u001a\u00030\u00a2\u0001H&J\u0013\u0010\u00a3\u0001\u001a\u00030\u00a4\u00012\u0007\u0010\u00a5\u0001\u001a\u00020%H&J\u0013\u0010\u00a6\u0001\u001a\u00020'2\b\u0010\u00a7\u0001\u001a\u00030\u00a8\u0001H&J$\u0010\u00a9\u0001\u001a\u00030\u00aa\u00012\u0006\u0010{\u001a\u00020\u001c2\u0007\u0010\u00ab\u0001\u001a\u00020\u001c2\u0007\u0010\u00ac\u0001\u001a\u00020\u001cH&J\u0013\u0010\u00ad\u0001\u001a\u00030\u00ae\u00012\u0007\u0010\u00af\u0001\u001a\u00020%H&J\u0014\u0010\u00b0\u0001\u001a\u00030\u00b1\u00012\b\u0010\u00b2\u0001\u001a\u00030\u00b3\u0001H&J\u0014\u0010\u00b4\u0001\u001a\u00030\u00b5\u00012\b\u0010\u0081\u0001\u001a\u00030\u0082\u0001H&J.\u0010\u00b6\u0001\u001a\u00030\u00b7\u00012\u0007\u0010\u00b8\u0001\u001a\u00020%2\u0007\u0010\u00b9\u0001\u001a\u00020%2\u0007\u0010\u00ba\u0001\u001a\u00020%2\u0007\u0010\u00bb\u0001\u001a\u00020%H&J5\u0010\u00bc\u0001\u001a\u00030\u00bd\u00012\n\u0010\u00be\u0001\u001a\u0005\u0018\u00010\u00bf\u00012\u0007\u0010\u00c0\u0001\u001a\u00020%2\n\u0010\u00c1\u0001\u001a\u0005\u0018\u00010\u00ae\u00012\b\u0010\u00c2\u0001\u001a\u00030\u00c3\u0001H&J\u0014\u0010\u00c4\u0001\u001a\u00030\u0099\u00012\b\u0010\u00c5\u0001\u001a\u00030\u00c6\u0001H&J\u0014\u0010\u00c7\u0001\u001a\u00030\u00c8\u00012\b\u0010\u00c5\u0001\u001a\u00030\u00c9\u0001H&J\u0013\u0010\u00ca\u0001\u001a\u00020L2\b\u0010\u00c5\u0001\u001a\u00030\u00cb\u0001H&J\n\u0010\u00cc\u0001\u001a\u00030\u00cd\u0001H&J\u0014\u0010\u00ce\u0001\u001a\u00030\u0096\u00012\b\u0010\u00c5\u0001\u001a\u00030\u00cf\u0001H&J\u0014\u0010\u00d0\u0001\u001a\u00030\u00d1\u00012\b\u0010\u00c5\u0001\u001a\u00030\u00d2\u0001H&J\u0014\u0010\u00d3\u0001\u001a\u00030\u00d4\u00012\b\u0010\u00c5\u0001\u001a\u00030\u00d5\u0001H&J\u0014\u0010\u00d6\u0001\u001a\u00030\u00d7\u00012\b\u0010\u00c5\u0001\u001a\u00030\u00d8\u0001H&J\u0014\u0010\u00d9\u0001\u001a\u00030\u00b3\u00012\b\u0010\u00c5\u0001\u001a\u00030\u00da\u0001H&J\u0014\u0010\u00db\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00dd\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00de\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00df\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00e0\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00e1\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00e2\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00e3\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00e4\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00e5\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00e6\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00e7\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00e8\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00e9\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00ea\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00eb\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00ec\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00ed\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00ee\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00ef\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00f0\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00f1\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00f2\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00f3\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00f4\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00f5\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00f6\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00f7\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00f8\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00f9\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00fa\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00fb\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00fc\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00fd\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00fe\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00ff\u0001\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0080\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0081\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0082\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0083\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0084\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0085\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0086\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0087\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0088\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0089\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u008a\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u008b\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u008c\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u008d\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u008e\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u008f\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0090\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0091\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0092\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0093\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0094\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0095\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0096\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0097\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0098\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u0099\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u009a\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u009b\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u009c\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u009d\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u009e\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u009f\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00a0\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00a1\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00a2\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00a3\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00a4\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00a5\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00a6\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00a7\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00a8\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00a9\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00aa\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00ab\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00ac\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00ad\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00ae\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00af\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00b0\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00b1\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00b2\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00b3\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00b4\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00b5\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00b6\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00b7\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00b8\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00b9\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00ba\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00bb\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00bc\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00bd\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00be\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00bf\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00c0\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00c1\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00c2\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u0014\u0010\u00c3\u0002\u001a\u00020<2\t\u0010\u00dc\u0001\u001a\u0004\u0018\u00010\u0001H&J\u001d\u0010\u00c4\u0002\u001a\u00030\u00c5\u00022\u0007\u0010\u00b8\u0001\u001a\u00020%2\b\u0010\u00c6\u0002\u001a\u00030\u00c7\u0002H&J\u0014\u0010\u00c8\u0002\u001a\u00030\u008e\u00012\b\u0010\u00c9\u0002\u001a\u00030\u00ca\u0002H&J\u0013\u0010\u00cb\u0002\u001a\u00020\u007f2\b\u0010\u00cc\u0002\u001a\u00030\u00cd\u0002H&JI\u0010\u00ce\u0002\u001a\u00030\u00c5\u00022\b\u0010\u00cf\u0002\u001a\u00030\u00d0\u00022\b\u0010\u0081\u0001\u001a\u00030\u0082\u00012\u0006\u0010|\u001a\u00020\u001c2\u0006\u0010}\u001a\u00020\u001c2\u0007\u0010\u00d1\u0002\u001a\u00020\u001c2\u0007\u0010\u00d2\u0002\u001a\u00020\u001c2\u0007\u0010\u00d3\u0002\u001a\u00020\u001cH&R\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006\u00d4\u0002"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/IClassProvider;", "", "jsonToNBTInstance", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/IJsonToNBT;", "getJsonToNBTInstance", "()Lnet/ccbluex/liquidbounce/api/minecraft/nbt/IJsonToNBT;", "tessellatorInstance", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/ITessellator;", "getTessellatorInstance", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/render/ITessellator;", "createAxisAlignedBB", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "minX", "", "minY", "minZ", "maxX", "maxY", "maxZ", "createCPacketAnimation", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketAnimation;", "createCPacketClientStatus", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketClientStatus;", "state", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketClientStatus$WEnumState;", "createCPacketCloseWindow", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketCloseWindow;", "windowId", "", "createCPacketCreativeInventoryAction", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "slot", "itemStack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "createCPacketCustomPayload", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketCustomPayload;", "channel", "", "payload", "Lnet/ccbluex/liquidbounce/api/network/IPacketBuffer;", "createCPacketEncryptionResponse", "secretKey", "Ljavax/crypto/SecretKey;", "publicKey", "Ljava/security/PublicKey;", "VerifyToken", "", "createCPacketEntityAction", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketEntityAction;", "player", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "wAction", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketEntityAction$WAction;", "createCPacketHeldItemChange", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketHeldItemChange;", "createCPacketKeepAlive", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketKeepAlive;", "createCPacketPlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayer;", "onGround", "", "createCPacketPlayerBlockPlacement", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerBlockPlacement;", "stack", "positionIn", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "placedBlockDirectionIn", "stackIn", "facingXIn", "", "facingYIn", "facingZIn", "createCPacketPlayerDigging", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerDigging$WAction;", "pos", "facing", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "createCPacketPlayerLook", "yaw", "pitch", "createCPacketPlayerPosLook", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketPlayerPosLook;", "x", "y", "z", "createCPacketPlayerPosition", "createCPacketTabComplete", "text", "createCPacketTryUseItem", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "hand", "Lnet/ccbluex/liquidbounce/api/enums/WEnumHand;", "createCPacketUseEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketUseEntity;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketUseEntity$WAction;", "entity", "positionVector", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "createChatComponentText", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "createClickEvent", "Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent;", "action", "Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent$WAction;", PropertyDescriptor.VALUE, "createDynamicTexture", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/texture/IDynamicTexture;", "image", "Ljava/awt/image/BufferedImage;", "createEntityLightningBolt", "world", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorld;", "posX", "posY", "posZ", "effectOnly", "createEntityOtherPlayerMP", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityOtherPlayerMP;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "GameProfile", "Lcom/mojang/authlib/GameProfile;", "createGuiButton", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "id", "width", "height", "createGuiConnecting", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "parent", "mc", "Lnet/ccbluex/liquidbounce/api/minecraft/client/IMinecraft;", "serverData", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IServerData;", "createGuiModList", "parentScreen", "createGuiMultiplayer", "createGuiOptions", "gameSettings", "Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IGameSettings;", "createGuiPasswordField", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiTextField;", "iFontRenderer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IFontRenderer;", "createGuiSelectWorld", "createGuiTextField", "createICPacketResourcePackStatus", "hash", "status", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/client/ICPacketResourcePackStatus$WAction;", "createItem", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "createItemStack", "blockEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "item", "amount", "meta", "createNBTTagCompound", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;", "createNBTTagDouble", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagDouble;", "createNBTTagList", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagList;", "createNBTTagString", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagString;", "string", "createPacketBuffer", "buffer", "Lio/netty/buffer/ByteBuf;", "createPotionEffect", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotionEffect;", "time", "strength", "createResourceLocation", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "resourceName", "createSafeVertexBuffer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/vertex/IVertexBuffer;", "vertexFormat", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/vertex/IVertexFormat;", "createScaledResolution", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IScaledResolution;", "createSession", "Lnet/ccbluex/liquidbounce/api/minecraft/util/ISession;", "name", "uuid", "accessToken", "accountType", "createThreadDownloadImageData", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/IThreadDownloadImageData;", "cacheFileIn", "Ljava/io/File;", "imageUrlIn", "textureResourceLocation", "imageBufferIn", "Lnet/ccbluex/liquidbounce/api/minecraft/client/render/WIImageBuffer;", "getBlockEnum", "type", "Lnet/ccbluex/liquidbounce/api/enums/BlockType;", "getEnchantmentEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/enchantments/IEnchantment;", "Lnet/ccbluex/liquidbounce/api/enums/EnchantmentType;", "getEnumFacing", "Lnet/ccbluex/liquidbounce/api/enums/EnumFacingType;", "getGlStateManager", "Lnet/ccbluex/liquidbounce/api/minecraft/client/renderer/IGlStateManager;", "getItemEnum", "Lnet/ccbluex/liquidbounce/api/enums/ItemType;", "getMaterialEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/block/material/IMaterial;", "Lnet/ccbluex/liquidbounce/api/enums/MaterialType;", "getPotionEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotion;", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/PotionType;", "getStatEnum", "Lnet/ccbluex/liquidbounce/api/minecraft/stats/IStatBase;", "Lnet/ccbluex/liquidbounce/api/enums/StatType;", "getVertexFormatEnum", "Lnet/ccbluex/liquidbounce/api/enums/WDefaultVertexFormats;", "isBlockAir", "obj", "isBlockBedrock", "isBlockBush", "isBlockCactus", "isBlockCarpet", "isBlockFence", "isBlockLadder", "isBlockLiquid", "isBlockPane", "isBlockSlab", "isBlockSlime", "isBlockSnow", "isBlockStairs", "isBlockVine", "isCPacketAnimation", "isCPacketChatMessage", "isCPacketClickWindow", "isCPacketClientStatus", "isCPacketCloseWindow", "isCPacketConfirmTransaction", "isCPacketCustomPayload", "isCPacketEntityAction", "isCPacketHandshake", "isCPacketHeldItemChange", "isCPacketKeepAlive", "isCPacketPlayer", "isCPacketPlayerBlockPlacement", "isCPacketPlayerDigging", "isCPacketPlayerLook", "isCPacketPlayerPosLook", "isCPacketPlayerPosition", "isCPacketTryUseItem", "isCPacketUseEntity", "isClickGui", "isEntityAnimal", "isEntityArmorStand", "isEntityArrow", "isEntityBat", "isEntityBoat", "isEntityDragon", "isEntityFallingBlock", "isEntityFireball", "isEntityGhast", "isEntityGolem", "isEntityItem", "isEntityLivingBase", "isEntityMinecart", "isEntityMinecartChest", "isEntityMob", "isEntityPlayer", "isEntityPlayerSP", "isEntityShulker", "isEntitySlime", "isEntitySquid", "isEntityTNTPrimed", "isEntityVillager", "isGuiChat", "isGuiChest", "isGuiContainer", "isGuiGameOver", "isGuiHudDesigner", "isGuiIngameMenu", "isGuiInventory", "isItemAir", "isItemAppleGold", "isItemArmor", "isItemAxe", "isItemBed", "isItemBlock", "isItemBoat", "isItemBow", "isItemBucket", "isItemBucketMilk", "isItemEgg", "isItemEnchantedBook", "isItemEnderPearl", "isItemFishingRod", "isItemFood", "isItemMinecart", "isItemPickaxe", "isItemPotion", "isItemShears", "isItemSnowball", "isItemSword", "isItemTool", "isSPacketAnimation", "isSPacketChat", "isSPacketCloseWindow", "isSPacketEntity", "isSPacketEntityVelocity", "isSPacketExplosion", "isSPacketPlayerPosLook", "isSPacketResourcePackSend", "isSPacketSpawnGlobalEntity", "isSPacketTabComplete", "isSPacketTitle", "isSPacketUpdateHealth", "isSPacketWindowItems", "isTileEntityChest", "isTileEntityDispenser", "isTileEntityEnderChest", "isTileEntityFurnace", "isTileEntityHopper", "isTileEntityShulkerBox", "wrapCreativeTab", "", "wrappedCreativeTabs", "Lnet/ccbluex/liquidbounce/api/util/WrappedCreativeTabs;", "wrapFontRenderer", "fontRenderer", "Lnet/ccbluex/liquidbounce/api/util/IWrappedFontRenderer;", "wrapGuiScreen", "clickGui", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "wrapGuiSlot", "wrappedGuiSlot", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiSlot;", "top", "bottom", "slotHeight", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/IClassProvider.class */
public interface IClassProvider {
    @NotNull
    ITessellator getTessellatorInstance();

    @NotNull
    IJsonToNBT getJsonToNBTInstance();

    @NotNull
    IResourceLocation createResourceLocation(@NotNull String str);

    @NotNull
    IThreadDownloadImageData createThreadDownloadImageData(@Nullable File file, @NotNull String str, @Nullable IResourceLocation iResourceLocation, @NotNull WIImageBuffer wIImageBuffer);

    @NotNull
    IPacketBuffer createPacketBuffer(@NotNull ByteBuf byteBuf);

    @NotNull
    IIChatComponent createChatComponentText(@NotNull String str);

    @NotNull
    IClickEvent createClickEvent(@NotNull IClickEvent.WAction wAction, @NotNull String str);

    @NotNull
    IGuiTextField createGuiTextField(int i, @NotNull IFontRenderer iFontRenderer, int i2, int i3, int i4, int i5);

    @NotNull
    IGuiTextField createGuiPasswordField(int i, @NotNull IFontRenderer iFontRenderer, int i2, int i3, int i4, int i5);

    @NotNull
    IGuiButton createGuiButton(int i, int i2, int i3, int i4, int i5, @NotNull String str);

    @NotNull
    IGuiButton createGuiButton(int i, int i2, int i3, @NotNull String str);

    @NotNull
    ISession createSession(@NotNull String str, @NotNull String str2, @NotNull String str3, @NotNull String str4);

    @NotNull
    IDynamicTexture createDynamicTexture(@NotNull BufferedImage bufferedImage);

    @NotNull
    IItem createItem();

    @NotNull
    IItemStack createItemStack(@NotNull IItem iItem, int i, int i2);

    @NotNull
    IItemStack createItemStack(@NotNull IItem iItem);

    @NotNull
    IItemStack createItemStack(@NotNull IBlock iBlock);

    @NotNull
    IAxisAlignedBB createAxisAlignedBB(double d, double d2, double d3, double d4, double d5, double d6);

    @NotNull
    IScaledResolution createScaledResolution(@NotNull IMinecraft iMinecraft);

    @NotNull
    INBTTagCompound createNBTTagCompound();

    @NotNull
    INBTTagList createNBTTagList();

    @NotNull
    INBTTagString createNBTTagString(@NotNull String str);

    @NotNull
    INBTTagDouble createNBTTagDouble(double d);

    @NotNull
    IEntityOtherPlayerMP createEntityOtherPlayerMP(@NotNull IWorldClient iWorldClient, @NotNull GameProfile gameProfile);

    @NotNull
    IEntity createEntityLightningBolt(@NotNull IWorld iWorld, double d, double d2, double d3, boolean z);

    @NotNull
    IPotionEffect createPotionEffect(int i, int i2, int i3);

    @NotNull
    IGuiScreen createGuiOptions(@NotNull IGuiScreen iGuiScreen, @NotNull IGameSettings iGameSettings);

    @NotNull
    IGuiScreen createGuiSelectWorld(@NotNull IGuiScreen iGuiScreen);

    @NotNull
    IGuiScreen createGuiMultiplayer(@NotNull IGuiScreen iGuiScreen);

    @NotNull
    IGuiScreen createGuiModList(@NotNull IGuiScreen iGuiScreen);

    @NotNull
    IGuiScreen createGuiConnecting(@NotNull IGuiScreen iGuiScreen, @NotNull IMinecraft iMinecraft, @NotNull IServerData iServerData);

    @NotNull
    ICPacketPlayerBlockPlacement createCPacketPlayerBlockPlacement(@NotNull WBlockPos wBlockPos, int i, @Nullable IItemStack iItemStack, float f, float f2, float f3);

    @NotNull
    ICPacketHeldItemChange createCPacketHeldItemChange(int i);

    @SupportsMinecraftVersions({MinecraftVersion.MC_1_12})
    @NotNull
    ICPacketPlayerBlockPlacement createCPacketPlayerBlockPlacement(@Nullable IItemStack iItemStack);

    @NotNull
    ICPacketPlayerPosLook createCPacketPlayerPosLook(double d, double d2, double d3, float f, float f2, boolean z);

    @NotNull
    ICPacketClientStatus createCPacketClientStatus(@NotNull ICPacketClientStatus.WEnumState wEnumState);

    @NotNull
    IPacket createCPacketPlayerDigging(@NotNull ICPacketPlayerDigging.WAction wAction, @NotNull WBlockPos wBlockPos, @NotNull IEnumFacing iEnumFacing);

    @NotNull
    ICPacketPlayer createCPacketPlayerPosition(double d, double d2, double d3, boolean z);

    @NotNull
    IPacket createICPacketResourcePackStatus(@NotNull String str, @NotNull ICPacketResourcePackStatus.WAction wAction);

    @NotNull
    ICPacketPlayer createCPacketPlayerLook(float f, float f2, boolean z);

    @NotNull
    ICPacketUseEntity createCPacketUseEntity(@NotNull IEntity iEntity, @NotNull ICPacketUseEntity.WAction wAction);

    @NotNull
    ICPacketUseEntity createCPacketUseEntity(@NotNull IEntity iEntity, @NotNull WVec3 wVec3);

    @NotNull
    IPacket createCPacketCreativeInventoryAction(int i, @NotNull IItemStack iItemStack);

    @NotNull
    ICPacketEntityAction createCPacketEntityAction(@NotNull IEntity iEntity, @NotNull ICPacketEntityAction.WAction wAction);

    @NotNull
    ICPacketCustomPayload createCPacketCustomPayload(@NotNull String str, @NotNull IPacketBuffer iPacketBuffer);

    @NotNull
    ICPacketCloseWindow createCPacketCloseWindow(int i);

    @NotNull
    ICPacketCloseWindow createCPacketCloseWindow();

    @NotNull
    ICPacketPlayer createCPacketPlayer(boolean z);

    @NotNull
    IPacket createCPacketTabComplete(@NotNull String str);

    @NotNull
    ICPacketAnimation createCPacketAnimation();

    @NotNull
    ICPacketKeepAlive createCPacketKeepAlive();

    boolean isEntityAnimal(@Nullable Object obj);

    boolean isEntitySquid(@Nullable Object obj);

    boolean isEntityBat(@Nullable Object obj);

    boolean isEntityGolem(@Nullable Object obj);

    boolean isEntityMob(@Nullable Object obj);

    boolean isEntityVillager(@Nullable Object obj);

    boolean isEntityFireball(@Nullable Object obj);

    boolean isEntitySlime(@Nullable Object obj);

    boolean isEntityGhast(@Nullable Object obj);

    boolean isEntityDragon(@Nullable Object obj);

    boolean isEntityLivingBase(@Nullable Object obj);

    boolean isEntityPlayer(@Nullable Object obj);

    boolean isEntityPlayerSP(@Nullable Object obj);

    boolean isEntityArmorStand(@Nullable Object obj);

    boolean isEntityTNTPrimed(@Nullable Object obj);

    boolean isEntityBoat(@Nullable Object obj);

    boolean isEntityMinecart(@Nullable Object obj);

    boolean isEntityItem(@Nullable Object obj);

    boolean isEntityArrow(@Nullable Object obj);

    boolean isEntityFallingBlock(@Nullable Object obj);

    boolean isEntityMinecartChest(@Nullable Object obj);

    boolean isEntityShulker(@Nullable Object obj);

    boolean isTileEntityChest(@Nullable Object obj);

    boolean isTileEntityEnderChest(@Nullable Object obj);

    boolean isTileEntityFurnace(@Nullable Object obj);

    boolean isTileEntityDispenser(@Nullable Object obj);

    boolean isTileEntityHopper(@Nullable Object obj);

    boolean isSPacketChat(@Nullable Object obj);

    boolean isSPacketEntity(@Nullable Object obj);

    boolean isSPacketResourcePackSend(@Nullable Object obj);

    boolean isSPacketPlayerPosLook(@Nullable Object obj);

    boolean isSPacketTitle(@Nullable Object obj);

    boolean isSPacketAnimation(@Nullable Object obj);

    boolean isSPacketUpdateHealth(@Nullable Object obj);

    boolean isSPacketEntityVelocity(@Nullable Object obj);

    boolean isSPacketExplosion(@Nullable Object obj);

    boolean isSPacketCloseWindow(@Nullable Object obj);

    boolean isSPacketSpawnGlobalEntity(@Nullable Object obj);

    boolean isSPacketTabComplete(@Nullable Object obj);

    boolean isCPacketPlayer(@Nullable Object obj);

    boolean isCPacketPlayerBlockPlacement(@Nullable Object obj);

    boolean isCPacketTryUseItem(@Nullable Object obj);

    boolean isCPacketConfirmTransaction(@Nullable Object obj);

    boolean isCPacketUseEntity(@Nullable Object obj);

    boolean isCPacketCloseWindow(@Nullable Object obj);

    boolean isCPacketChatMessage(@Nullable Object obj);

    boolean isCPacketKeepAlive(@Nullable Object obj);

    boolean isCPacketPlayerPosition(@Nullable Object obj);

    boolean isCPacketPlayerPosLook(@Nullable Object obj);

    boolean isCPacketClientStatus(@Nullable Object obj);

    boolean isCPacketAnimation(@Nullable Object obj);

    boolean isCPacketEntityAction(@Nullable Object obj);

    boolean isSPacketWindowItems(@Nullable Object obj);

    boolean isCPacketHeldItemChange(@Nullable Object obj);

    boolean isCPacketClickWindow(@Nullable Object obj);

    boolean isCPacketPlayerLook(@Nullable Object obj);

    boolean isCPacketCustomPayload(@Nullable Object obj);

    boolean isCPacketHandshake(@Nullable Object obj);

    boolean isCPacketPlayerDigging(@Nullable Object obj);

    boolean isItemSword(@Nullable Object obj);

    boolean isItemTool(@Nullable Object obj);

    boolean isItemArmor(@Nullable Object obj);

    boolean isItemPotion(@Nullable Object obj);

    boolean isItemBlock(@Nullable Object obj);

    boolean isItemBow(@Nullable Object obj);

    boolean isItemBucket(@Nullable Object obj);

    boolean isItemFood(@Nullable Object obj);

    boolean isItemBucketMilk(@Nullable Object obj);

    boolean isItemPickaxe(@Nullable Object obj);

    boolean isItemShears(@Nullable Object obj);

    boolean isItemAxe(@Nullable Object obj);

    boolean isItemBed(@Nullable Object obj);

    boolean isItemEnderPearl(@Nullable Object obj);

    boolean isItemEnchantedBook(@Nullable Object obj);

    boolean isItemBoat(@Nullable Object obj);

    boolean isItemMinecart(@Nullable Object obj);

    boolean isItemAppleGold(@Nullable Object obj);

    boolean isItemSnowball(@Nullable Object obj);

    boolean isItemEgg(@Nullable Object obj);

    boolean isItemFishingRod(@Nullable Object obj);

    boolean isItemAir(@Nullable Object obj);

    boolean isBlockAir(@Nullable Object obj);

    boolean isBlockFence(@Nullable Object obj);

    boolean isBlockSnow(@Nullable Object obj);

    boolean isBlockLadder(@Nullable Object obj);

    boolean isBlockVine(@Nullable Object obj);

    boolean isBlockSlime(@Nullable Object obj);

    boolean isBlockSlab(@Nullable Object obj);

    boolean isBlockStairs(@Nullable Object obj);

    boolean isBlockCarpet(@Nullable Object obj);

    boolean isBlockPane(@Nullable Object obj);

    boolean isBlockLiquid(@Nullable Object obj);

    boolean isBlockCactus(@Nullable Object obj);

    boolean isBlockBedrock(@Nullable Object obj);

    boolean isBlockBush(@Nullable Object obj);

    boolean isGuiInventory(@Nullable Object obj);

    boolean isGuiContainer(@Nullable Object obj);

    boolean isGuiGameOver(@Nullable Object obj);

    boolean isGuiChat(@Nullable Object obj);

    boolean isGuiIngameMenu(@Nullable Object obj);

    boolean isGuiChest(@Nullable Object obj);

    boolean isGuiHudDesigner(@Nullable Object obj);

    boolean isClickGui(@Nullable Object obj);

    @NotNull
    IPotion getPotionEnum(@NotNull PotionType potionType);

    @NotNull
    IEnumFacing getEnumFacing(@NotNull EnumFacingType enumFacingType);

    @NotNull
    IBlock getBlockEnum(@NotNull BlockType blockType);

    @NotNull
    IMaterial getMaterialEnum(@NotNull MaterialType materialType);

    @NotNull
    IStatBase getStatEnum(@NotNull StatType statType);

    @NotNull
    IItem getItemEnum(@NotNull ItemType itemType);

    @NotNull
    IEnchantment getEnchantmentEnum(@NotNull EnchantmentType enchantmentType);

    @NotNull
    IVertexFormat getVertexFormatEnum(@NotNull WDefaultVertexFormats wDefaultVertexFormats);

    @NotNull
    IFontRenderer wrapFontRenderer(@NotNull IWrappedFontRenderer iWrappedFontRenderer);

    @NotNull
    IGuiScreen wrapGuiScreen(@NotNull WrappedGuiScreen wrappedGuiScreen);

    @NotNull
    IVertexBuffer createSafeVertexBuffer(@NotNull IVertexFormat iVertexFormat);

    void wrapCreativeTab(@NotNull String str, @NotNull WrappedCreativeTabs wrappedCreativeTabs);

    void wrapGuiSlot(@NotNull WrappedGuiSlot wrappedGuiSlot, @NotNull IMinecraft iMinecraft, int i, int i2, int i3, int i4, int i5);

    @NotNull
    IGlStateManager getGlStateManager();

    @NotNull
    IPacket createCPacketEncryptionResponse(@NotNull SecretKey secretKey, @NotNull PublicKey publicKey, @NotNull byte[] bArr);

    @SupportsMinecraftVersions({MinecraftVersion.MC_1_12})
    @NotNull
    PacketImpl createCPacketTryUseItem(@NotNull WEnumHand wEnumHand);

    boolean isTileEntityShulkerBox(@Nullable Object obj);
}
