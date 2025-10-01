package com.jagrosh.discordipc;

import com.jagrosh.discordipc.entities.Callback;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.User;
import com.jagrosh.discordipc.entities.pipe.Pipe;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import java.io.Closeable;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: L-out.jar:com/jagrosh/discordipc/IPCClient.class */
public final class IPCClient implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(IPCClient.class);
    private final long clientId;
    private volatile Pipe pipe;
    private final HashMap<String, Callback> callbacks = new HashMap<>();
    private IPCListener listener = null;
    private Thread readThread = null;

    public IPCClient(long clientId) {
        this.clientId = clientId;
    }

    public void setListener(IPCListener listener) {
        this.listener = listener;
        if (this.pipe != null) {
            this.pipe.setListener(listener);
        }
    }

    public void connect(DiscordBuild... preferredOrder) throws NoDiscordClientException {
        checkConnected(false);
        this.callbacks.clear();
        this.pipe = null;
        this.pipe = Pipe.openPipe(this, this.clientId, this.callbacks, preferredOrder);
        LOGGER.debug("Client is now connected and ready!");
        if (this.listener != null) {
            this.listener.onReady(this);
        }
        startReading();
    }

    public void sendRichPresence(RichPresence presence) {
        sendRichPresence(presence, null);
    }

    public void sendRichPresence(RichPresence presence, Callback callback) {
        checkConnected(true);
        LOGGER.debug("Sending RichPresence to discord: " + (presence == null ? null : presence.toJson().toString()));
        this.pipe.send(Packet.OpCode.FRAME, new JSONObject().put("cmd", "SET_ACTIVITY").put("args", new JSONObject().put("pid", getPID()).put("activity", presence == null ? null : presence.toJson())), callback);
    }

    public void subscribe(Event sub) {
        subscribe(sub, null);
    }

    public void subscribe(Event sub, Callback callback) {
        checkConnected(true);
        if (!sub.isSubscribable()) {
            throw new IllegalStateException("Cannot subscribe to " + sub + " event!");
        }
        LOGGER.debug(String.format("Subscribing to Event: %s", sub.name()));
        this.pipe.send(Packet.OpCode.FRAME, new JSONObject().put("cmd", "SUBSCRIBE").put("evt", sub.name()), callback);
    }

    public PipeStatus getStatus() {
        return this.pipe == null ? PipeStatus.UNINITIALIZED : this.pipe.getStatus();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        checkConnected(true);
        try {
            this.pipe.close();
        } catch (IOException e) {
            LOGGER.debug("Failed to close pipe", (Throwable) e);
        }
    }

    public DiscordBuild getDiscordBuild() {
        if (this.pipe == null) {
            return null;
        }
        return this.pipe.getDiscordBuild();
    }

    /* loaded from: L-out.jar:com/jagrosh/discordipc/IPCClient$Event.class */
    public enum Event {
        NULL(false),
        READY(false),
        ERROR(false),
        ACTIVITY_JOIN(true),
        ACTIVITY_SPECTATE(true),
        ACTIVITY_JOIN_REQUEST(true),
        UNKNOWN(false);

        private final boolean subscribable;

        Event(boolean z) {
            this.subscribable = z;
        }

        public boolean isSubscribable() {
            return this.subscribable;
        }

        /* renamed from: of */
        static Event m0of(String str) {
            if (str == null) {
                return NULL;
            }
            for (Event event : values()) {
                if (event != UNKNOWN && event.name().equalsIgnoreCase(str)) {
                    return event;
                }
            }
            return UNKNOWN;
        }
    }

    private void checkConnected(boolean connected) {
        if (connected && getStatus() != PipeStatus.CONNECTED) {
            throw new IllegalStateException(String.format("IPCClient (ID: %d) is not connected!", Long.valueOf(this.clientId)));
        }
        if (!connected && getStatus() == PipeStatus.CONNECTED) {
            throw new IllegalStateException(String.format("IPCClient (ID: %d) is already connected!", Long.valueOf(this.clientId)));
        }
    }

    private void startReading() {
        this.readThread = new Thread(() -> {
            Packet p;
            while (true) {
                try {
                    p = this.pipe.read();
                } catch (IOException | JSONException ex) {
                    if (!(ex instanceof IOException)) {
                    }
                    this.pipe.setStatus(PipeStatus.DISCONNECTED);
                    if (this.listener == null) {
                    }
                }
                if (p.getOp() != Packet.OpCode.CLOSE) {
                    JSONObject json = p.getJson();
                    Event event = Event.m0of(json.optString("evt", null));
                    String nonce = json.optString("nonce", null);
                    switch (C00001.$SwitchMap$com$jagrosh$discordipc$IPCClient$Event[event.ordinal()]) {
                        case 1:
                            if (nonce != null && this.callbacks.containsKey(nonce)) {
                                this.callbacks.remove(nonce).succeed(p);
                                break;
                            }
                            break;
                        case 2:
                            if (nonce != null && this.callbacks.containsKey(nonce)) {
                                this.callbacks.remove(nonce).fail(json.getJSONObject("data").optString("message", null));
                                break;
                            }
                            break;
                        case 3:
                            LOGGER.debug("Reading thread received a 'join' event.");
                            break;
                        case 4:
                            LOGGER.debug("Reading thread received a 'spectate' event.");
                            break;
                        case 5:
                            LOGGER.debug("Reading thread received a 'join request' event.");
                            break;
                        case 6:
                            LOGGER.debug("Reading thread encountered an event with an unknown type: " + json.getString("evt"));
                            break;
                    }
                    if (this.listener != null && json.has("cmd") && json.getString("cmd").equals("DISPATCH")) {
                        try {
                            JSONObject data = json.getJSONObject("data");
                            switch (C00001.$SwitchMap$com$jagrosh$discordipc$IPCClient$Event[Event.m0of(json.getString("evt")).ordinal()]) {
                                case 3:
                                    this.listener.onActivityJoin(this, data.getString("secret"));
                                    break;
                                case 4:
                                    this.listener.onActivitySpectate(this, data.getString("secret"));
                                    break;
                                case 5:
                                    JSONObject u = data.getJSONObject("user");
                                    User user = new User(u.getString("username"), u.getString("discriminator"), Long.parseLong(u.getString("id")), u.optString("avatar", null));
                                    this.listener.onActivityJoinRequest(this, data.optString("secret", null), user);
                                    break;
                            }
                        } catch (Exception e) {
                            LOGGER.error("Exception when handling event: ", (Throwable) e);
                        }
                        return;
                    }
                } else {
                    this.pipe.setStatus(PipeStatus.DISCONNECTED);
                    if (this.listener != null) {
                        this.listener.onClose(this, p.getJson());
                    }
                    return;
                }
                if (!(ex instanceof IOException)) {
                    LOGGER.error("Reading thread encountered an IOException", (Throwable) ex);
                } else {
                    LOGGER.error("Reading thread encountered an JSONException", (Throwable) ex);
                }
                this.pipe.setStatus(PipeStatus.DISCONNECTED);
                if (this.listener == null) {
                    this.listener.onDisconnect(this, ex);
                    return;
                }
                return;
            }
        });
        LOGGER.debug("Starting IPCClient reading thread!");
        this.readThread.start();
    }

    /* renamed from: com.jagrosh.discordipc.IPCClient$1 */
    /* loaded from: L-out.jar:com/jagrosh/discordipc/IPCClient$1.class */
    static /* synthetic */ class C00001 {
        static final int[] $SwitchMap$com$jagrosh$discordipc$IPCClient$Event = new int[Event.values().length];

        static {
            try {
                $SwitchMap$com$jagrosh$discordipc$IPCClient$Event[Event.NULL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$jagrosh$discordipc$IPCClient$Event[Event.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$jagrosh$discordipc$IPCClient$Event[Event.ACTIVITY_JOIN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$jagrosh$discordipc$IPCClient$Event[Event.ACTIVITY_SPECTATE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$jagrosh$discordipc$IPCClient$Event[Event.ACTIVITY_JOIN_REQUEST.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$jagrosh$discordipc$IPCClient$Event[Event.UNKNOWN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private static int getPID() {
        String pr = ManagementFactory.getRuntimeMXBean().getName();
        return Integer.parseInt(pr.substring(0, pr.indexOf(64)));
    }
}
