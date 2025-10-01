package net.ccbluex.liquidbounce.discord;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData;
import net.ccbluex.liquidbounce.discord.ClientRichPresence;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import org.lwjgl.opengl.Display;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\ufffd\ufffd\n\u0002\u0010%\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\u0006\u0010\u0016\u001a\u00020\u0015J\u0006\u0010\u0017\u001a\u00020\u0015J\u0006\u0010\u0018\u001a\u00020\u0015R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u001a\u0010\f\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0011\u001a\n \u0013*\u0004\u0018\u00010\u00120\u0012X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0019"}, m27d2 = {"Lnet/ccbluex/liquidbounce/discord/ClientRichPresence;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "appID", "", "assets", "", "", "ipcClient", "Lcom/jagrosh/discordipc/IPCClient;", "running", "", "showRichPresenceValue", "getShowRichPresenceValue", "()Z", "setShowRichPresenceValue", "(Z)V", "timestamp", "Ljava/time/OffsetDateTime;", "kotlin.jvm.PlatformType", "loadConfiguration", "", "setup", "shutdown", "update", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/discord/ClientRichPresence.class */
public final class ClientRichPresence extends MinecraftInstance {
    private IPCClient ipcClient;
    private long appID;
    private boolean running;
    private boolean showRichPresenceValue = true;
    private final Map assets = new LinkedHashMap();
    private final OffsetDateTime timestamp = OffsetDateTime.now();

    public final boolean getShowRichPresenceValue() {
        return this.showRichPresenceValue;
    }

    public final void setShowRichPresenceValue(boolean z) {
        this.showRichPresenceValue = z;
    }

    public final void setup() {
        try {
            this.running = true;
            loadConfiguration();
            this.ipcClient = new IPCClient(this.appID);
            IPCClient iPCClient = this.ipcClient;
            if (iPCClient != null) {
                iPCClient.setListener(new C04001(this));
            }
            IPCClient iPCClient2 = this.ipcClient;
            if (iPCClient2 != null) {
                iPCClient2.connect(new DiscordBuild[0]);
            }
        } catch (Throwable th) {
            ClientUtils.getLogger().error("Failed to setup Discord RPC.", th);
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001f\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\ufffd\ufffd\b\n\u0018\ufffd\ufffd2\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\u0012\u0010\b\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016\u00a8\u0006\t"}, m27d2 = {"net/ccbluex/liquidbounce/discord/ClientRichPresence$setup$1", "Lcom/jagrosh/discordipc/IPCListener;", "onClose", "", "client", "Lcom/jagrosh/discordipc/IPCClient;", "json", "Lorg/json/JSONObject;", "onReady", LiquidBounce.CLIENT_NAME})
    /* renamed from: net.ccbluex.liquidbounce.discord.ClientRichPresence$setup$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/discord/ClientRichPresence$setup$1.class */
    public static final class C04001 implements IPCListener {
        final ClientRichPresence this$0;

        C04001(ClientRichPresence clientRichPresence) {
            this.this$0 = clientRichPresence;
        }

        public void onReady(@Nullable IPCClient iPCClient) {
            ThreadsKt.thread$default(false, false, null, null, 0, new Function0(this) { // from class: net.ccbluex.liquidbounce.discord.ClientRichPresence$setup$1$onReady$1
                final ClientRichPresence.C04001 this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                    this.this$0 = this;
                }

                @Override // kotlin.jvm.functions.Function0
                public Object invoke() throws InterruptedException {
                    m1562invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: collision with other method in class */
                public final void m1562invoke() throws InterruptedException {
                    while (this.this$0.this$0.running) {
                        this.this$0.this$0.update();
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException unused) {
                        }
                    }
                }
            }, 31, null);
        }

        public void onClose(@Nullable IPCClient iPCClient, @Nullable JSONObject jSONObject) {
            this.this$0.running = false;
        }
    }

    public final void update() {
        int i;
        int i2;
        RichPresence.Builder builder = new RichPresence.Builder();
        builder.setStartTimestamp(this.timestamp);
        if (this.assets.containsKey("new")) {
            builder.setLargeImage((String) this.assets.get("new"), "build 1");
        }
        IServerData currentServerData = MinecraftInstance.f157mc.getCurrentServerData();
        builder.setDetails(Display.isActive() ? (MinecraftInstance.f157mc.isIntegratedServerRunning() || currentServerData != null) ? "Playing" : "Idle..." : "AFK");
        builder.setState("Name: " + MinecraftInstance.f157mc.getSession().getUsername());
        if (MinecraftInstance.f157mc.isIntegratedServerRunning() || currentServerData != null) {
            String str = (String) this.assets.get("astolfo");
            StringBuilder sbAppend = new StringBuilder().append((MinecraftInstance.f157mc.isIntegratedServerRunning() || currentServerData == null) ? "Singleplayer" : currentServerData.getServerIP()).append(" - Enabled ");
            TreeSet modules = LiquidBounce.INSTANCE.getModuleManager().getModules();
            if ((modules instanceof Collection) && modules.isEmpty()) {
                i = 0;
            } else {
                int i3 = 0;
                Iterator it = modules.iterator();
                while (it.hasNext()) {
                    if (((Module) it.next()).getState()) {
                        i3++;
                        if (i3 < 0) {
                            CollectionsKt.throwCountOverflow();
                        }
                    }
                }
                i = i3;
            }
            builder.setSmallImage(str, sbAppend.append(i).append('/').append(LiquidBounce.INSTANCE.getModuleManager().getModules().size()).append('.').toString());
        } else {
            String str2 = (String) this.assets.get("astolfo");
            StringBuilder sbAppend2 = new StringBuilder().append("Enabled ");
            TreeSet modules2 = LiquidBounce.INSTANCE.getModuleManager().getModules();
            if ((modules2 instanceof Collection) && modules2.isEmpty()) {
                i2 = 0;
            } else {
                int i4 = 0;
                Iterator it2 = modules2.iterator();
                while (it2.hasNext()) {
                    if (((Module) it2.next()).getState()) {
                        i4++;
                        if (i4 < 0) {
                            CollectionsKt.throwCountOverflow();
                        }
                    }
                }
                i2 = i4;
            }
            builder.setSmallImage(str2, sbAppend2.append(i2).append('/').append(LiquidBounce.INSTANCE.getModuleManager().getModules().size()).append('.').toString());
        }
        IPCClient iPCClient = this.ipcClient;
        if ((iPCClient != null ? iPCClient.getStatus() : null) == PipeStatus.CONNECTED) {
            IPCClient iPCClient2 = this.ipcClient;
            if (iPCClient2 != null) {
                iPCClient2.sendRichPresence(builder.build());
            }
        }
    }

    public final void shutdown() {
        IPCClient iPCClient = this.ipcClient;
        if ((iPCClient != null ? iPCClient.getStatus() : null) != PipeStatus.CONNECTED) {
            return;
        }
        try {
            IPCClient iPCClient2 = this.ipcClient;
            if (iPCClient2 != null) {
                iPCClient2.close();
            }
        } catch (Throwable th) {
            ClientUtils.getLogger().error("Failed to close Discord RPC.", th);
        }
    }

    private final void loadConfiguration() {
        this.appID = 874149528486445106L;
        this.assets.put("new", "new");
        this.assets.put("astolfo", "astolfo");
    }
}
