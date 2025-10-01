package net.ccbluex.liquidbounce.injection.backend;

import com.mojang.authlib.GameProfile;
import java.lang.reflect.Field;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IExtractedFunctions;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.enchantments.IEnchantment;
import net.ccbluex.liquidbounce.api.minecraft.entity.IEnumCreatureAttribute;
import net.ccbluex.liquidbounce.api.minecraft.inventory.ISlot;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotion;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam;
import net.ccbluex.liquidbounce.api.minecraft.tileentity.ITileEntity;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.api.util.WrappedCollection;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.util.registry.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u00a0\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006H\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016J\b\u0010\u000f\u001a\u00020\rH\u0016J)\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0012\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00110\u0014\"\u00020\u0011H\u0016\u00a2\u0006\u0002\u0010\u0015J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u001b\u001a\u00020\u0011H\u0016J\u000e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0016J\b\u0010\u001f\u001a\u00020\u001eH\u0016J\u0012\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\"\u001a\u00020\u0019H\u0016J\u0012\u0010#\u001a\u0004\u0018\u00010!2\u0006\u0010$\u001a\u00020\u0011H\u0016J\u000e\u0010%\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0016J\u0010\u0010&\u001a\u00020\u00192\u0006\u0010'\u001a\u00020\u0017H\u0016J\u0010\u0010(\u001a\u00020\u00192\u0006\u0010)\u001a\u00020*H\u0016J\u0012\u0010+\u001a\u0004\u0018\u00010*2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0012\u0010,\u001a\u0004\u0018\u00010*2\u0006\u0010\u001b\u001a\u00020\u0011H\u0016J\u000e\u0010-\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0016J\u001a\u0010.\u001a\u00020/2\b\u00100\u001a\u0004\u0018\u00010\n2\u0006\u00101\u001a\u000202H\u0016J\u0012\u00103\u001a\u0004\u0018\u00010*2\u0006\u00104\u001a\u00020\u001eH\u0016J\u0010\u00105\u001a\u0002062\u0006\u00107\u001a\u00020\u0019H\u0016J\u0010\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\u0011H\u0016J \u0010;\u001a\u00020\r2\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020/2\u0006\u0010?\u001a\u00020\u0019H\u0016J\u001a\u0010@\u001a\u00020\u00112\b\u0010A\u001a\u0004\u0018\u00010B2\u0006\u0010C\u001a\u00020\u0011H\u0016J \u0010D\u001a\u00020\r2\u0006\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020\u00112\u0006\u0010H\u001a\u00020\u0011H\u0016J\b\u0010I\u001a\u00020\rH\u0016J\b\u0010J\u001a\u00020\rH\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006K"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/ExtractedFunctionsImpl;", "Lnet/ccbluex/liquidbounce/api/IExtractedFunctions;", "()V", "fastRenderField", "Ljava/lang/reflect/Field;", "canAddItemToSlot", "", "slotIn", "Lnet/ccbluex/liquidbounce/api/minecraft/inventory/ISlot;", "stack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "stackSizeMatters", "disableFastRender", "", "disableStandardItemLighting", "enableStandardItemLighting", "formatI18n", "", "key", "values", "", "(Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;", "getBlockById", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "id", "", "getBlockFromName", "name", "getBlockRegistryKeys", "", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "getDefaultSkinLegacy", "getEnchantmentById", "Lnet/ccbluex/liquidbounce/api/minecraft/enchantments/IEnchantment;", "enchantID", "getEnchantmentByLocation", "location", "getEnchantments", "getIdFromBlock", "block", "getIdFromItem", "sb", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItem;", "getItemById", "getItemByName", "getItemRegistryKeys", "getModifierForCreature", "", "heldItem", "creatureAttribute", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/IEnumCreatureAttribute;", "getObjectFromItemRegistry", "res", "getPotionById", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotion;", "potionID", "jsonToComponent", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "toString", "renderTileEntity", "tileEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/tileentity/ITileEntity;", "partialTicks", "destroyStage", "scoreboardFormatPlayerName", "scorePlayerTeam", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/ITeam;", "playerName", "sessionServiceJoinServer", "profile", "Lcom/mojang/authlib/GameProfile;", "token", "sessionHash", "setActiveTextureDefaultTexUnit", "setActiveTextureLightMapTexUnit", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ExtractedFunctionsImpl.class */
public final class ExtractedFunctionsImpl implements IExtractedFunctions {
    private static Field fastRenderField;
    public static final ExtractedFunctionsImpl INSTANCE = new ExtractedFunctionsImpl();

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\b\u0004"}, m27d2 = {"<anonymous>", "Lnet/minecraft/util/ResourceLocation;", "p1", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.ExtractedFunctionsImpl$getBlockRegistryKeys$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ExtractedFunctionsImpl$getBlockRegistryKeys$1.class */
    static final /* synthetic */ class C04381 extends FunctionReference implements Function1 {
        public static final C04381 INSTANCE = new C04381();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(ResourceLocationImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04381() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return invoke((IResourceLocation) obj);
        }

        @NotNull
        public final ResourceLocation invoke(@NotNull IResourceLocation p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return ((ResourceLocationImpl) p1).getWrapped();
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\b\u0004"}, m27d2 = {"<anonymous>", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "p1", "Lnet/minecraft/util/ResourceLocation;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.ExtractedFunctionsImpl$getBlockRegistryKeys$2 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ExtractedFunctionsImpl$getBlockRegistryKeys$2.class */
    static final /* synthetic */ class C04392 extends FunctionReference implements Function1 {
        public static final C04392 INSTANCE = new C04392();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(ResourceLocationImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04392() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return invoke((ResourceLocation) obj);
        }

        @NotNull
        public final IResourceLocation invoke(@NotNull ResourceLocation p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return new ResourceLocationImpl(p1);
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\b\u0004"}, m27d2 = {"<anonymous>", "Lnet/minecraft/util/ResourceLocation;", "p1", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.ExtractedFunctionsImpl$getEnchantments$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ExtractedFunctionsImpl$getEnchantments$1.class */
    static final /* synthetic */ class C04401 extends FunctionReference implements Function1 {
        public static final C04401 INSTANCE = new C04401();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(ResourceLocationImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04401() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return invoke((IResourceLocation) obj);
        }

        @NotNull
        public final ResourceLocation invoke(@NotNull IResourceLocation p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return ((ResourceLocationImpl) p1).getWrapped();
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\b\u0004"}, m27d2 = {"<anonymous>", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "p1", "Lnet/minecraft/util/ResourceLocation;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.ExtractedFunctionsImpl$getEnchantments$2 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ExtractedFunctionsImpl$getEnchantments$2.class */
    static final /* synthetic */ class C04412 extends FunctionReference implements Function1 {
        public static final C04412 INSTANCE = new C04412();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(ResourceLocationImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04412() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return invoke((ResourceLocation) obj);
        }

        @NotNull
        public final IResourceLocation invoke(@NotNull ResourceLocation p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return new ResourceLocationImpl(p1);
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\b\u0004"}, m27d2 = {"<anonymous>", "Lnet/minecraft/util/ResourceLocation;", "p1", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.ExtractedFunctionsImpl$getItemRegistryKeys$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ExtractedFunctionsImpl$getItemRegistryKeys$1.class */
    static final /* synthetic */ class C04421 extends FunctionReference implements Function1 {
        public static final C04421 INSTANCE = new C04421();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(ResourceLocationImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04421() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return invoke((IResourceLocation) obj);
        }

        @NotNull
        public final ResourceLocation invoke(@NotNull IResourceLocation p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return ((ResourceLocationImpl) p1).getWrapped();
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\b\u0004"}, m27d2 = {"<anonymous>", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IResourceLocation;", "p1", "Lnet/minecraft/util/ResourceLocation;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.ExtractedFunctionsImpl$getItemRegistryKeys$2 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ExtractedFunctionsImpl$getItemRegistryKeys$2.class */
    static final /* synthetic */ class C04432 extends FunctionReference implements Function1 {
        public static final C04432 INSTANCE = new C04432();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(ResourceLocationImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04432() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return invoke((ResourceLocation) obj);
        }

        @NotNull
        public final IResourceLocation invoke(@NotNull ResourceLocation p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return new ResourceLocationImpl(p1);
        }
    }

    private ExtractedFunctionsImpl() {
    }

    static {
        try {
            Field declaredField = GameSettings.class.getDeclaredField("ofFastRender");
            fastRenderField = declaredField;
            Intrinsics.checkExpressionValueIsNotNull(declaredField, "declaredField");
            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
            }
        } catch (NoSuchFieldException unused) {
        }
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    @Nullable
    public IBlock getBlockById(int i) {
        Block blockFunc_149729_e = Block.func_149729_e(i);
        return blockFunc_149729_e != null ? new BlockImpl(blockFunc_149729_e) : null;
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    public int getIdFromBlock(@NotNull IBlock block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        return Block.func_149682_b(((BlockImpl) block).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    public float getModifierForCreature(@Nullable IItemStack iItemStack, @NotNull IEnumCreatureAttribute creatureAttribute) {
        ItemStack wrapped;
        Intrinsics.checkParameterIsNotNull(creatureAttribute, "creatureAttribute");
        if (iItemStack == null) {
            wrapped = null;
        } else {
            if (iItemStack == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.ItemStackImpl");
            }
            wrapped = ((ItemStackImpl) iItemStack).getWrapped();
        }
        return EnchantmentHelper.func_152377_a(wrapped, ((EnumCreatureAttributeImpl) creatureAttribute).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    @Nullable
    public IItem getObjectFromItemRegistry(@NotNull IResourceLocation res) {
        Intrinsics.checkParameterIsNotNull(res, "res");
        Item item = (Item) Item.field_150901_e.func_82594_a(((ResourceLocationImpl) res).getWrapped());
        if (item != null) {
            return new ItemImpl(item);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    public void renderTileEntity(@NotNull ITileEntity tileEntity, float f, int i) {
        Intrinsics.checkParameterIsNotNull(tileEntity, "tileEntity");
        TileEntityRendererDispatcher.field_147556_a.func_180546_a(((TileEntityImpl) tileEntity).getWrapped(), f, i);
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    @Nullable
    public IBlock getBlockFromName(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Block blockFunc_149684_b = Block.func_149684_b(name);
        if (blockFunc_149684_b != null) {
            return new BlockImpl(blockFunc_149684_b);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    @Nullable
    public IItem getItemByName(@NotNull String name) throws IllegalAccessException, IllegalArgumentException {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Object obj = Items.class.getField(name).get(null);
        if (obj == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.item.Item");
        }
        return new ItemImpl((Item) obj);
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    @NotNull
    public IResourceLocation getDefaultSkinLegacy() {
        ResourceLocation resourceLocationFunc_177335_a = DefaultPlayerSkin.func_177335_a();
        Intrinsics.checkExpressionValueIsNotNull(resourceLocationFunc_177335_a, "DefaultPlayerSkin.getDefaultSkinLegacy()");
        return new ResourceLocationImpl(resourceLocationFunc_177335_a);
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    @Nullable
    public IEnchantment getEnchantmentByLocation(@NotNull String location) {
        Intrinsics.checkParameterIsNotNull(location, "location");
        Enchantment enchantmentFunc_180305_b = Enchantment.func_180305_b(location);
        if (enchantmentFunc_180305_b != null) {
            return new EnchantmentImpl(enchantmentFunc_180305_b);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    @Nullable
    public IEnchantment getEnchantmentById(int i) {
        Enchantment enchantmentFunc_185262_c = Enchantment.func_185262_c(i);
        if (enchantmentFunc_185262_c != null) {
            return new EnchantmentImpl(enchantmentFunc_185262_c);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    @NotNull
    public Collection getEnchantments() {
        RegistryNamespaced registryNamespaced = Enchantment.field_185264_b;
        Intrinsics.checkExpressionValueIsNotNull(registryNamespaced, "Enchantment.REGISTRY");
        return new WrappedCollection(registryNamespaced.func_148742_b(), C04401.INSTANCE, C04412.INSTANCE);
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    @NotNull
    public Collection getItemRegistryKeys() {
        RegistryNamespaced registryNamespaced = Item.field_150901_e;
        Intrinsics.checkExpressionValueIsNotNull(registryNamespaced, "Item.REGISTRY");
        return new WrappedCollection(registryNamespaced.func_148742_b(), C04421.INSTANCE, C04432.INSTANCE);
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    @NotNull
    public Collection getBlockRegistryKeys() {
        RegistryNamespacedDefaultedByKey registryNamespacedDefaultedByKey = Block.field_149771_c;
        Intrinsics.checkExpressionValueIsNotNull(registryNamespacedDefaultedByKey, "Block.REGISTRY");
        return new WrappedCollection(registryNamespacedDefaultedByKey.func_148742_b(), C04381.INSTANCE, C04392.INSTANCE);
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    public void disableStandardItemLighting() {
        RenderHelper.func_74518_a();
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    @NotNull
    public String formatI18n(@NotNull String key, @NotNull String[] values) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(values, "values");
        String strFunc_135052_a = I18n.func_135052_a(key, new Object[]{values});
        Intrinsics.checkExpressionValueIsNotNull(strFunc_135052_a, "I18n.format(key, values)");
        return strFunc_135052_a;
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    public void sessionServiceJoinServer(@NotNull GameProfile profile, @NotNull String token, @NotNull String sessionHash) {
        Intrinsics.checkParameterIsNotNull(profile, "profile");
        Intrinsics.checkParameterIsNotNull(token, "token");
        Intrinsics.checkParameterIsNotNull(sessionHash, "sessionHash");
        Minecraft minecraftFunc_71410_x = Minecraft.func_71410_x();
        Intrinsics.checkExpressionValueIsNotNull(minecraftFunc_71410_x, "Minecraft.getMinecraft()");
        minecraftFunc_71410_x.func_152347_ac().joinServer(profile, token, sessionHash);
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    @NotNull
    public IPotion getPotionById(int i) {
        Potion potionFunc_188412_a = Potion.func_188412_a(i);
        if (potionFunc_188412_a == null) {
            Intrinsics.throwNpe();
        }
        Intrinsics.checkExpressionValueIsNotNull(potionFunc_188412_a, "Potion.getPotionById(potionID)!!");
        return new PotionImpl(potionFunc_188412_a);
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    public void enableStandardItemLighting() {
        RenderHelper.func_74519_b();
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    @NotNull
    public String scoreboardFormatPlayerName(@Nullable ITeam iTeam, @NotNull String playerName) {
        Team wrapped;
        Intrinsics.checkParameterIsNotNull(playerName, "playerName");
        if (iTeam == null) {
            wrapped = null;
        } else {
            if (iTeam == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.TeamImpl");
            }
            wrapped = ((TeamImpl) iTeam).getWrapped();
        }
        String strFunc_96667_a = ScorePlayerTeam.func_96667_a(wrapped, playerName);
        Intrinsics.checkExpressionValueIsNotNull(strFunc_96667_a, "ScorePlayerTeam.formatPl\u2026am?.unwrap(), playerName)");
        return strFunc_96667_a;
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    public void disableFastRender() throws IllegalAccessException, IllegalArgumentException {
        try {
            Field field = fastRenderField;
            if (field != null) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                field.setBoolean(Minecraft.func_71410_x().field_71474_y, false);
            }
        } catch (IllegalAccessException unused) {
        }
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    @NotNull
    public IIChatComponent jsonToComponent(@NotNull String toString) {
        Intrinsics.checkParameterIsNotNull(toString, "toString");
        ITextComponent iTextComponentFunc_150699_a = ITextComponent.Serializer.func_150699_a(toString);
        if (iTextComponentFunc_150699_a == null) {
            Intrinsics.throwNpe();
        }
        Intrinsics.checkExpressionValueIsNotNull(iTextComponentFunc_150699_a, "ITextComponent.Serialize\u2026onToComponent(toString)!!");
        return new IChatComponentImpl(iTextComponentFunc_150699_a);
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    public void setActiveTextureLightMapTexUnit() {
        GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    public void setActiveTextureDefaultTexUnit() {
        GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    @Nullable
    public IItem getItemById(int i) {
        Item itemFunc_150899_d = Item.func_150899_d(i);
        Intrinsics.checkExpressionValueIsNotNull(itemFunc_150899_d, "Item.getItemById(id)");
        return new ItemImpl(itemFunc_150899_d);
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    public int getIdFromItem(@NotNull IItem sb) {
        Intrinsics.checkParameterIsNotNull(sb, "sb");
        return Item.func_150891_b(((ItemImpl) sb).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.IExtractedFunctions
    public boolean canAddItemToSlot(@NotNull ISlot slotIn, @NotNull IItemStack stack, boolean z) {
        Intrinsics.checkParameterIsNotNull(slotIn, "slotIn");
        Intrinsics.checkParameterIsNotNull(stack, "stack");
        return Container.func_94527_a(((SlotImpl) slotIn).getWrapped(), ((ItemStackImpl) stack).getWrapped(), z);
    }
}
