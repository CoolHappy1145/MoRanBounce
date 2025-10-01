package com.jagrosh.discordipc.entities;

import java.nio.ByteBuffer;
import org.json.JSONObject;

/* loaded from: L-out.jar:com/jagrosh/discordipc/entities/Packet.class */
public class Packet {

    /* renamed from: op */
    private final OpCode f0op;
    private final JSONObject data;

    /* loaded from: L-out.jar:com/jagrosh/discordipc/entities/Packet$OpCode.class */
    public enum OpCode {
        HANDSHAKE,
        FRAME,
        CLOSE,
        PING,
        PONG
    }

    public Packet(OpCode opCode, JSONObject jSONObject) {
        this.f0op = opCode;
        this.data = jSONObject;
    }

    public byte[] toBytes() {
        byte[] bytes = this.data.toString().getBytes();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bytes.length + 8);
        byteBufferAllocate.putInt(Integer.reverseBytes(this.f0op.ordinal()));
        byteBufferAllocate.putInt(Integer.reverseBytes(bytes.length));
        byteBufferAllocate.put(bytes);
        return byteBufferAllocate.array();
    }

    public OpCode getOp() {
        return this.f0op;
    }

    public JSONObject getJson() {
        return this.data;
    }

    public String toString() {
        return "Pkt:" + getOp() + getJson().toString();
    }
}
