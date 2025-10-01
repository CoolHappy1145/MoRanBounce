package net.ccbluex.liquidbounce.features.command.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.elements.NotifyType;
import net.ccbluex.liquidbounce.utils.SettingsUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.util.Constants;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdB\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\t\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001b\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u0016\u00a2\u0006\u0002\u0010\fJ;\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0018\u0010\u0012\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u0014\u0012\u0004\u0012\u00020\t0\u0013H\u0002\u00a2\u0006\u0002\u0010\u0015J!\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u00142\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u0016\u00a2\u0006\u0002\u0010\u0017R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0018"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/AutoSettingsCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "autoSettingFiles", "", "", "loadingLock", Constants.OBJECT_DESC, "execute", "", "args", "", "([Ljava/lang/String;)V", "loadSettings", "useCached", "", "join", "", "callback", "Lkotlin/Function1;", "", "(ZLjava/lang/Long;Lkotlin/jvm/functions/Function1;)V", "tabComplete", "([Ljava/lang/String;)Ljava/util/List;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/command/commands/AutoSettingsCommand.class */
public final class AutoSettingsCommand extends Command {
    private final Object loadingLock;
    private List autoSettingFiles;

    public AutoSettingsCommand() {
        super("autosettings", new String[]{"setting", "settings", "config", "autosetting"});
        this.loadingLock = new Object();
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    public void execute(@NotNull String[] args) throws InterruptedException {
        String string;
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length <= 1) {
            chatSyntax("settings <load/list>");
            return;
        }
        if (StringsKt.equals(args[1], "load", true)) {
            if (args.length < 3) {
                chatSyntax("settings load <name/url>");
                return;
            }
            if (StringsKt.startsWith$default(args[2], "http", false, 2, (Object) null)) {
                string = args[2];
            } else {
                StringBuilder sbAppend = new StringBuilder().append("https://cloud.liquidbounce.net/LiquidBounce/settings/");
                String str = args[2];
                if (str == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String lowerCase = str.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                string = sbAppend.append(lowerCase).toString();
            }
            chat("Loading settings...");
            ThreadsKt.thread$default(false, false, null, null, 0, new Function0(this, string) { // from class: net.ccbluex.liquidbounce.features.command.commands.AutoSettingsCommand.execute.1
                final AutoSettingsCommand this$0;
                final String $url;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                    this.this$0 = this;
                    this.$url = string;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() {
                    m1564invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: collision with other method in class */
                public final void m1564invoke() {
                    try {
                        String str2 = HttpUtils.get(this.$url);
                        this.this$0.chat("Applying settings...");
                        SettingsUtils.INSTANCE.executeScript(str2);
                        this.this$0.chat("\u00a76Settings applied successfully");
                        LiquidBounce.INSTANCE.getHud().addNotification(new Notification("AutoSettings", "Updated Settings", NotifyType.SUCCESS, 0, 0, 24, null));
                        this.this$0.playEdit();
                    } catch (Exception e) {
                        e.printStackTrace();
                        this.this$0.chat("Failed to fetch auto settings.");
                    }
                }
            }, 31, null);
            return;
        }
        if (StringsKt.equals(args[1], "list", true)) {
            chat("Loading settings...");
            loadSettings$default(this, false, null, new Function1(this) { // from class: net.ccbluex.liquidbounce.features.command.commands.AutoSettingsCommand.execute.2
                final AutoSettingsCommand this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                    this.this$0 = this;
                }

                @Override // kotlin.jvm.functions.Function1
                public Object invoke(Object obj) {
                    invoke((List) obj);
                    return Unit.INSTANCE;
                }

                public final void invoke(@NotNull List it) {
                    Intrinsics.checkParameterIsNotNull(it, "it");
                    Iterator it2 = it.iterator();
                    while (it2.hasNext()) {
                        this.this$0.chat("> " + ((String) it2.next()));
                    }
                }
            }, 2, null);
        }
    }

    static void loadSettings$default(AutoSettingsCommand autoSettingsCommand, boolean z, Long l, Function1 function1, int i, Object obj) throws InterruptedException {
        if ((i & 2) != 0) {
            l = (Long) null;
        }
        autoSettingsCommand.loadSettings(z, l, function1);
    }

    private final void loadSettings(final boolean z, Long l, final Function1 function1) throws InterruptedException {
        Thread threadThread$default = ThreadsKt.thread$default(false, false, null, null, 0, new Function0(this, z, function1) { // from class: net.ccbluex.liquidbounce.features.command.commands.AutoSettingsCommand$loadSettings$thread$1
            final AutoSettingsCommand this$0;
            final boolean $useCached;
            final Function1 $callback;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
                this.$useCached = z;
                this.$callback = function1;
            }

            @Override // kotlin.jvm.functions.Function0
            public Object invoke() {
                m1565invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: collision with other method in class */
            public final void m1565invoke() {
                synchronized (this.this$0.loadingLock) {
                    if (this.$useCached && this.this$0.autoSettingFiles != null) {
                        Function1 function12 = this.$callback;
                        List list = this.this$0.autoSettingFiles;
                        if (list == null) {
                            Intrinsics.throwNpe();
                        }
                        function12.invoke(list);
                        return;
                    }
                    try {
                        JsonArray jsonArray = new JsonParser().parse(HttpUtils.get("https://api.github.com/repos/CCBlueX/LiquidCloud/contents/LiquidBounce/settings"));
                        ArrayList arrayList = new ArrayList();
                        if (jsonArray instanceof JsonArray) {
                            Iterator it = jsonArray.iterator();
                            while (it.hasNext()) {
                                JsonElement setting = (JsonElement) it.next();
                                Intrinsics.checkExpressionValueIsNotNull(setting, "setting");
                                JsonElement jsonElement = setting.getAsJsonObject().get("name");
                                Intrinsics.checkExpressionValueIsNotNull(jsonElement, "setting.asJsonObject[\"name\"]");
                                String asString = jsonElement.getAsString();
                                Intrinsics.checkExpressionValueIsNotNull(asString, "setting.asJsonObject[\"name\"].asString");
                                arrayList.add(asString);
                            }
                        }
                        this.$callback.invoke(arrayList);
                        this.this$0.autoSettingFiles = arrayList;
                    } catch (Exception unused) {
                        this.this$0.chat("Failed to fetch auto settings list.");
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
        }, 31, null);
        if (l != null) {
            threadThread$default.join(l.longValue());
        }
    }

    @Override // net.ccbluex.liquidbounce.features.command.Command
    @NotNull
    public List tabComplete(@NotNull String[] args) throws InterruptedException {
        Intrinsics.checkParameterIsNotNull(args, "args");
        if (args.length == 0) {
            return CollectionsKt.emptyList();
        }
        switch (args.length) {
            case 1:
                List listListOf = CollectionsKt.listOf((Object[]) new String[]{"list", "load"});
                ArrayList arrayList = new ArrayList();
                for (Object obj : listListOf) {
                    if (StringsKt.startsWith((String) obj, args[0], true)) {
                        arrayList.add(obj);
                    }
                }
                break;
            case 2:
                if (StringsKt.equals(args[0], "load", true)) {
                    if (this.autoSettingFiles == null) {
                        loadSettings(true, 500L, new Function1() { // from class: net.ccbluex.liquidbounce.features.command.commands.AutoSettingsCommand.tabComplete.2
                            @Override // kotlin.jvm.functions.Function1
                            public Object invoke(Object obj2) {
                                invoke((List) obj2);
                                return Unit.INSTANCE;
                            }

                            public final void invoke(@NotNull List it) {
                                Intrinsics.checkParameterIsNotNull(it, "it");
                            }
                        });
                    }
                    if (this.autoSettingFiles != null) {
                        List list = this.autoSettingFiles;
                        if (list == null) {
                            Intrinsics.throwNpe();
                        }
                        List list2 = list;
                        ArrayList arrayList2 = new ArrayList();
                        for (Object obj2 : list2) {
                            if (StringsKt.startsWith((String) obj2, args[1], true)) {
                                arrayList2.add(obj2);
                            }
                        }
                        break;
                    }
                }
                break;
        }
        return CollectionsKt.emptyList();
    }
}
