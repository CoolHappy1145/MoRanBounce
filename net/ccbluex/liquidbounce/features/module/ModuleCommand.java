package net.ccbluex.liquidbounce.features.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\ufffd\ufffd2\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\b\u0002\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u001b\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016\u00a2\u0006\u0002\u0010\u0011J!\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u00052\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016\u00a2\u0006\u0002\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\tR\u001b\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0014"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/ModuleCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "module", "Lnet/ccbluex/liquidbounce/features/module/Module;", "values", "", "Lnet/ccbluex/liquidbounce/value/Value;", "(Lnet/ccbluex/liquidbounce/features/module/Module;Ljava/util/List;)V", "getModule", "()Lnet/ccbluex/liquidbounce/features/module/Module;", "getValues", "()Ljava/util/List;", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "tabComplete", "([Ljava/lang/String;)Ljava/util/List;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/ModuleCommand.class */
public final class ModuleCommand extends Command {

    @NotNull
    private final Module module;

    @NotNull
    private final List values;

    @NotNull
    public final Module getModule() {
        return this.module;
    }

    @NotNull
    public final List getValues() {
        return this.values;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ModuleCommand(@NotNull Module module, @NotNull List values) {
        Intrinsics.checkParameterIsNotNull(module, "module");
        Intrinsics.checkParameterIsNotNull(values, "values");
        String name = module.getName();
        if (name == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = name.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        super(lowerCase, new String[0]);
        this.module = module;
        this.values = values;
        if (this.values.isEmpty()) {
            throw new IllegalArgumentException("Values are empty!");
        }
    }

    public ModuleCommand(Module module, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(module, (i & 2) != 0 ? module.getValues() : list);
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) throws NumberFormatException {
        int iIntValue;
        Intrinsics.checkParameterIsNotNull(args, "args");
        List list = this.values;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (!(((Value) obj) instanceof FontValue)) {
                arrayList.add(obj);
            }
        }
        String strJoinToString$default = CollectionsKt.joinToString$default(arrayList, "/", null, null, 0, null, new Function1() { // from class: net.ccbluex.liquidbounce.features.module.ModuleCommand$execute$valueNames$2
            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj2) {
                return invoke((Value) obj2);
            }

            @NotNull
            public final String invoke(@NotNull Value it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                String name = it.getName();
                if (name == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String lowerCase = name.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                return lowerCase;
            }
        }, 30, null);
        String name = this.module.getName();
        if (name == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = name.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        if (args.length < 2) {
            chatSyntax(this.values.size() == 1 ? lowerCase + ' ' + strJoinToString$default + " <value>" : lowerCase + " <" + strJoinToString$default + '>');
            return;
        }
        Value value = this.module.getValue(args[1]);
        if (value == null) {
            chatSyntax(lowerCase + " <" + strJoinToString$default + '>');
            return;
        }
        if (value instanceof BoolValue) {
            boolean z = !((Boolean) ((BoolValue) value).get()).booleanValue();
            ((BoolValue) value).set(Boolean.valueOf(z));
            chat("\u00a77" + this.module.getName() + " \u00a78" + args[1] + "\u00a77 was toggled " + (z ? "\u00a78on\u00a77" : "\u00a78off\u00a77."));
            playEdit();
            return;
        }
        if (args.length < 3) {
            if ((value instanceof IntegerValue) || (value instanceof FloatValue) || (value instanceof TextValue)) {
                StringBuilder sbAppend = new StringBuilder().append(lowerCase).append(' ');
                String str = args[1];
                if (str == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String lowerCase2 = str.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase2, "(this as java.lang.String).toLowerCase()");
                chatSyntax(sbAppend.append(lowerCase2).append(" <value>").toString());
                return;
            }
            if (value instanceof ListValue) {
                StringBuilder sbAppend2 = new StringBuilder().append(lowerCase).append(' ');
                String str2 = args[1];
                if (str2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String lowerCase3 = str2.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase3, "(this as java.lang.String).toLowerCase()");
                StringBuilder sbAppend3 = sbAppend2.append(lowerCase3).append(" <");
                String strJoinToString$default2 = ArraysKt.joinToString$default(((ListValue) value).getValues(), "/", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
                if (strJoinToString$default2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String lowerCase4 = strJoinToString$default2.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase4, "(this as java.lang.String).toLowerCase()");
                chatSyntax(sbAppend3.append(lowerCase4).append('>').toString());
                return;
            }
            return;
        }
        try {
            if (value instanceof BlockValue) {
                try {
                    iIntValue = Integer.parseInt(args[2]);
                } catch (NumberFormatException unused) {
                    IBlock blockFromName = MinecraftInstance.functions.getBlockFromName(args[2]);
                    Integer numValueOf = blockFromName != null ? Integer.valueOf(MinecraftInstance.functions.getIdFromBlock(blockFromName)) : null;
                    if (numValueOf == null || numValueOf.intValue() <= 0) {
                        chat("\u00a77Block \u00a78" + args[2] + "\u00a77 does not exist!");
                        return;
                    }
                    iIntValue = numValueOf.intValue();
                }
                int i = iIntValue;
                ((BlockValue) value).set((Object) Integer.valueOf(i));
                StringBuilder sbAppend4 = new StringBuilder().append("\u00a77").append(this.module.getName()).append(" \u00a78");
                String str3 = args[1];
                if (str3 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String lowerCase5 = str3.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase5, "(this as java.lang.String).toLowerCase()");
                chat(sbAppend4.append(lowerCase5).append("\u00a77 was set to \u00a78").append(BlockUtils.getBlockName(i)).append("\u00a77.").toString());
                playEdit();
                return;
            }
            if (value instanceof IntegerValue) {
                ((IntegerValue) value).set((Object) Integer.valueOf(Integer.parseInt(args[2])));
            } else if (value instanceof FloatValue) {
                ((FloatValue) value).set((Object) Float.valueOf(Float.parseFloat(args[2])));
            } else if (value instanceof ListValue) {
                if (!((ListValue) value).contains(args[2])) {
                    StringBuilder sbAppend5 = new StringBuilder().append(lowerCase).append(' ');
                    String str4 = args[1];
                    if (str4 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    String lowerCase6 = str4.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(lowerCase6, "(this as java.lang.String).toLowerCase()");
                    StringBuilder sbAppend6 = sbAppend5.append(lowerCase6).append(" <");
                    String strJoinToString$default3 = ArraysKt.joinToString$default(((ListValue) value).getValues(), "/", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null);
                    if (strJoinToString$default3 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    String lowerCase7 = strJoinToString$default3.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(lowerCase7, "(this as java.lang.String).toLowerCase()");
                    chatSyntax(sbAppend6.append(lowerCase7).append('>').toString());
                    return;
                }
                ((ListValue) value).set(args[2]);
            } else if (value instanceof TextValue) {
                String completeString = StringUtils.toCompleteString(args, 2);
                Intrinsics.checkExpressionValueIsNotNull(completeString, "StringUtils.toCompleteString(args, 2)");
                ((TextValue) value).set(completeString);
            }
            chat("\u00a77" + this.module.getName() + " \u00a78" + args[1] + "\u00a77 was set to \u00a78" + value.get() + "\u00a77.");
            playEdit();
        } catch (NumberFormatException unused2) {
            chat("\u00a78" + args[2] + "\u00a77 cannot be converted to number!");
        }
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    @NotNull
    public List tabComplete(@NotNull String[] args) {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length == 0) {
            return CollectionsKt.emptyList();
        }
        switch (args.length) {
            case 1:
                List list = this.values;
                ArrayList arrayList = new ArrayList();
                for (Object obj : list) {
                    Value value = (Value) obj;
                    if (!(value instanceof FontValue) && StringsKt.startsWith(value.getName(), args[0], true)) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = arrayList;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    String name = ((Value) it.next()).getName();
                    if (name == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    String lowerCase = name.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                    arrayList3.add(lowerCase);
                }
                return arrayList3;
            case 2:
                Value value2 = this.module.getValue(args[0]);
                if (value2 instanceof BlockValue) {
                    Collection itemRegistryKeys = MinecraftInstance.functions.getItemRegistryKeys();
                    ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(itemRegistryKeys, 10));
                    Iterator it2 = itemRegistryKeys.iterator();
                    while (it2.hasNext()) {
                        String resourcePath = ((IResourceLocation) it2.next()).getResourcePath();
                        if (resourcePath == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                        String lowerCase2 = resourcePath.toLowerCase();
                        Intrinsics.checkExpressionValueIsNotNull(lowerCase2, "(this as java.lang.String).toLowerCase()");
                        arrayList4.add(lowerCase2);
                    }
                    ArrayList arrayList5 = arrayList4;
                    ArrayList arrayList6 = new ArrayList();
                    for (Object obj2 : arrayList5) {
                        if (StringsKt.startsWith((String) obj2, args[1], true)) {
                            arrayList6.add(obj2);
                        }
                    }
                    return arrayList6;
                }
                if (!(value2 instanceof ListValue)) {
                    return CollectionsKt.emptyList();
                }
                for (Value value3 : this.values) {
                    if (StringsKt.equals(value3.getName(), args[0], true) && (value3 instanceof ListValue)) {
                        String[] values = ((ListValue) value3).getValues();
                        ArrayList arrayList7 = new ArrayList();
                        for (String str : values) {
                            if (StringsKt.startsWith(str, args[1], true)) {
                                arrayList7.add(str);
                            }
                        }
                        return arrayList7;
                    }
                }
                return CollectionsKt.emptyList();
            default:
                return CollectionsKt.emptyList();
        }
    }
}
