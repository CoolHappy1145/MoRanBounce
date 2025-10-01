package net.ccbluex.liquidbounce.chat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import java.net.URI;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.chat.packet.PacketDeserializer;
import net.ccbluex.liquidbounce.chat.packet.PacketSerializer;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientErrorPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientMessagePacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientMojangInfoPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientNewJWTPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientPrivateMessagePacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientSuccessPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.Packet;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerBanUserPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerLoginJWTPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerLoginMojangPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerMessagePacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerPrivateMessagePacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerRequestJWTPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerRequestMojangInfoPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerUnbanUserPacket;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.login.UserUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdD\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\ufffd\ufffd2\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0018J\u0006\u0010 \u001a\u00020\u001eJ\u0006\u0010!\u001a\u00020\u001eJ\u0006\u0010\"\u001a\u00020\rJ\u000e\u0010#\u001a\u00020\u001e2\u0006\u0010$\u001a\u00020\u0018J\u0006\u0010%\u001a\u00020\u001eJ\u0015\u0010&\u001a\u00020\u001e2\u0006\u0010'\u001a\u00020\u0018H\ufffd\ufffd\u00a2\u0006\u0002\b(J\u000e\u0010)\u001a\u00020\u001e2\u0006\u0010'\u001a\u00020\u0018J\u000e\u0010*\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020,J\u0016\u0010-\u001a\u00020\u001e2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010'\u001a\u00020\u0018J\u0010\u0010.\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020\u0018H\u0002J\u000e\u0010/\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0018R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0080\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001a\u0010\u0017\u001a\u00020\u0018X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001c\u00a8\u00060"}, m27d2 = {"Lnet/ccbluex/liquidbounce/chat/Client;", "Lnet/ccbluex/liquidbounce/chat/ClientListener;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "channel", "Lio/netty/channel/Channel;", "getChannel$LiquidSense", "()Lio/netty/channel/Channel;", "setChannel$LiquidSense", "(Lio/netty/channel/Channel;)V", "deserializer", "Lnet/ccbluex/liquidbounce/chat/packet/PacketDeserializer;", "jwt", "", "getJwt", "()Z", "setJwt", "(Z)V", "loggedIn", "getLoggedIn", "setLoggedIn", "serializer", "Lnet/ccbluex/liquidbounce/chat/packet/PacketSerializer;", "username", "", "getUsername", "()Ljava/lang/String;", "setUsername", "(Ljava/lang/String;)V", "banUser", "", "target", "connect", "disconnect", "isConnected", "loginJWT", "token", "loginMojang", "onMessage", "message", "onMessage$LiquidSense", "sendMessage", "sendPacket", "packet", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "sendPrivateMessage", "toUUID", "unbanUser", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/chat/Client.class */
public abstract class Client extends MinecraftInstance implements ClientListener {

    @Nullable
    private Channel channel;
    private boolean jwt;
    private boolean loggedIn;

    @NotNull
    private String username = "";
    private final PacketSerializer serializer = new PacketSerializer();
    private final PacketDeserializer deserializer = new PacketDeserializer();

    public Client() {
        this.serializer.registerPacket("RequestMojangInfo", ServerRequestMojangInfoPacket.class);
        this.serializer.registerPacket("LoginMojang", ServerLoginMojangPacket.class);
        this.serializer.registerPacket("Message", ServerMessagePacket.class);
        this.serializer.registerPacket("PrivateMessage", ServerPrivateMessagePacket.class);
        this.serializer.registerPacket("BanUser", ServerBanUserPacket.class);
        this.serializer.registerPacket("UnbanUser", ServerUnbanUserPacket.class);
        this.serializer.registerPacket("RequestJWT", ServerRequestJWTPacket.class);
        this.serializer.registerPacket("LoginJWT", ServerLoginJWTPacket.class);
        this.deserializer.registerPacket("MojangInfo", ClientMojangInfoPacket.class);
        this.deserializer.registerPacket("NewJWT", ClientNewJWTPacket.class);
        this.deserializer.registerPacket("Message", ClientMessagePacket.class);
        this.deserializer.registerPacket("PrivateMessage", ClientPrivateMessagePacket.class);
        this.deserializer.registerPacket("Error", ClientErrorPacket.class);
        this.deserializer.registerPacket("Success", ClientSuccessPacket.class);
    }

    @Nullable
    public final Channel getChannel$LiquidSense() {
        return this.channel;
    }

    public final void setChannel$LiquidSense(@Nullable Channel channel) {
        this.channel = channel;
    }

    @NotNull
    public final String getUsername() {
        return this.username;
    }

    public final void setUsername(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.username = str;
    }

    public final boolean getJwt() {
        return this.jwt;
    }

    public final void setJwt(boolean z) {
        this.jwt = z;
    }

    public final boolean getLoggedIn() {
        return this.loggedIn;
    }

    public final void setLoggedIn(boolean z) {
        this.loggedIn = z;
    }

    public final void connect() {
        onConnect();
        URI uri = new URI("wss://chat.liquidbounce.net:7886/ws");
        SslContext sslContextNewClientContext = StringsKt.equals(uri.getScheme(), "wss", true) ? SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE) : null;
        EventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        WebSocketClientHandshaker webSocketClientHandshakerNewHandshaker = WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, (String) null, true, new DefaultHttpHeaders());
        Intrinsics.checkExpressionValueIsNotNull(webSocketClientHandshakerNewHandshaker, "WebSocketClientHandshake\u2026ue, DefaultHttpHeaders())");
        ClientHandler clientHandler = new ClientHandler(this, webSocketClientHandshakerNewHandshaker);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(nioEventLoopGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer(sslContextNewClientContext, clientHandler) { // from class: net.ccbluex.liquidbounce.chat.Client.connect.1
            final SslContext $sslContext;
            final ClientHandler $handler;

            public void initChannel(Channel channel) {
                initChannel((SocketChannel) channel);
            }

            {
                this.$sslContext = sslContextNewClientContext;
                this.$handler = clientHandler;
            }

            protected void initChannel(@NotNull SocketChannel ch) {
                Intrinsics.checkParameterIsNotNull(ch, "ch");
                ChannelPipeline channelPipelinePipeline = ch.pipeline();
                if (this.$sslContext != null) {
                    channelPipelinePipeline.addLast(new ChannelHandler[]{(ChannelHandler) this.$sslContext.newHandler(ch.alloc())});
                }
                channelPipelinePipeline.addLast(new ChannelHandler[]{(ChannelHandler) new HttpClientCodec(), (ChannelHandler) new HttpObjectAggregator(8192), (ChannelHandler) this.$handler});
            }
        });
        this.channel = bootstrap.connect(uri.getHost(), uri.getPort()).sync().channel();
        clientHandler.getHandshakeFuture().sync();
        if (isConnected()) {
            onConnected();
        }
    }

    public final void disconnect() {
        Channel channel = this.channel;
        if (channel != null) {
            channel.close();
        }
        this.channel = (Channel) null;
        this.username = "";
        this.jwt = false;
    }

    public final void loginMojang() {
        sendPacket(new ServerRequestMojangInfoPacket());
    }

    public final void loginJWT(@NotNull String token) {
        Intrinsics.checkParameterIsNotNull(token, "token");
        onLogon();
        sendPacket(new ServerLoginJWTPacket(token, true));
        this.jwt = true;
    }

    public final boolean isConnected() {
        if (this.channel != null) {
            Channel channel = this.channel;
            if (channel == null) {
                Intrinsics.throwNpe();
            }
            if (channel.isOpen()) {
                return true;
            }
        }
        return false;
    }

    public final void onMessage$LiquidSense(@NotNull String message) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        Packet packet = (Packet) new GsonBuilder().registerTypeAdapter(Packet.class, this.deserializer).create().fromJson(message, Packet.class);
        if (packet instanceof ClientMojangInfoPacket) {
            onLogon();
            try {
                MinecraftInstance.functions.sessionServiceJoinServer(MinecraftInstance.f157mc.getSession().getProfile(), MinecraftInstance.f157mc.getSession().getToken(), ((ClientMojangInfoPacket) packet).getSessionHash());
                this.username = MinecraftInstance.f157mc.getSession().getUsername();
                this.jwt = false;
                String username = MinecraftInstance.f157mc.getSession().getUsername();
                UUID id = MinecraftInstance.f157mc.getSession().getProfile().getId();
                Intrinsics.checkExpressionValueIsNotNull(id, "mc.session.profile.id");
                sendPacket(new ServerLoginMojangPacket(username, id, true));
                return;
            } catch (Throwable th) {
                onError(th);
                return;
            }
        }
        Intrinsics.checkExpressionValueIsNotNull(packet, "packet");
        onPacket(packet);
    }

    public final void sendPacket(@NotNull Packet packet) {
        Intrinsics.checkParameterIsNotNull(packet, "packet");
        Gson gsonCreate = new GsonBuilder().registerTypeAdapter(Packet.class, this.serializer).create();
        Channel channel = this.channel;
        if (channel != null) {
            channel.writeAndFlush(new TextWebSocketFrame(gsonCreate.toJson(packet, Packet.class)));
        }
    }

    public final void sendMessage(@NotNull String message) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        sendPacket(new ServerMessagePacket(message));
    }

    public final void sendPrivateMessage(@NotNull String username, @NotNull String message) {
        Intrinsics.checkParameterIsNotNull(username, "username");
        Intrinsics.checkParameterIsNotNull(message, "message");
        sendPacket(new ServerPrivateMessagePacket(username, message));
    }

    public final void banUser(@NotNull String target) {
        Intrinsics.checkParameterIsNotNull(target, "target");
        sendPacket(new ServerBanUserPacket(toUUID(target)));
    }

    public final void unbanUser(@NotNull String target) {
        Intrinsics.checkParameterIsNotNull(target, "target");
        sendPacket(new ServerUnbanUserPacket(toUUID(target)));
    }

    private final String toUUID(String str) {
        String str2;
        try {
            UUID.fromString(str);
            str2 = str;
        } catch (IllegalArgumentException unused) {
            String uuid = UserUtils.INSTANCE.getUUID(str);
            if (StringsKt.isBlank(uuid)) {
                return "";
            }
            String string = new StringBuffer(uuid).insert(20, '-').insert(16, '-').insert(12, '-').insert(8, '-').toString();
            Intrinsics.checkExpressionValueIsNotNull(string, "uuid.toString()");
            str2 = string;
        }
        return str2;
    }
}
