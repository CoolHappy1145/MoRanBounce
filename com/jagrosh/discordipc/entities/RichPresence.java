package com.jagrosh.discordipc.entities;

import java.time.OffsetDateTime;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: L-out.jar:com/jagrosh/discordipc/entities/RichPresence.class */
public class RichPresence {
    private final String state;
    private final String details;
    private final OffsetDateTime startTimestamp;
    private final OffsetDateTime endTimestamp;
    private final String largeImageKey;
    private final String largeImageText;
    private final String smallImageKey;
    private final String smallImageText;
    private final String partyId;
    private final int partySize;
    private final int partyMax;
    private final String matchSecret;
    private final String joinSecret;
    private final String spectateSecret;
    private final boolean instance;

    public RichPresence(String str, String str2, OffsetDateTime offsetDateTime, OffsetDateTime offsetDateTime2, String str3, String str4, String str5, String str6, String str7, int i, int i2, String str8, String str9, String str10, boolean z) {
        this.state = str;
        this.details = str2;
        this.startTimestamp = offsetDateTime;
        this.endTimestamp = offsetDateTime2;
        this.largeImageKey = str3;
        this.largeImageText = str4;
        this.smallImageKey = str5;
        this.smallImageText = str6;
        this.partyId = str7;
        this.partySize = i;
        this.partyMax = i2;
        this.matchSecret = str8;
        this.joinSecret = str9;
        this.spectateSecret = str10;
        this.instance = z;
    }

    public JSONObject toJson() {
        return new JSONObject().put("state", this.state).put("details", this.details).put("timestamps", new JSONObject().put("start", this.startTimestamp == null ? null : Long.valueOf(this.startTimestamp.toEpochSecond())).put(AsmConstants.END, this.endTimestamp == null ? null : Long.valueOf(this.endTimestamp.toEpochSecond()))).put("assets", new JSONObject().put("large_image", this.largeImageKey).put("large_text", this.largeImageText).put("small_image", this.smallImageKey).put("small_text", this.smallImageText)).put("party", this.partyId == null ? null : new JSONObject().put("id", this.partyId).put("size", new JSONArray().put(this.partySize).put(this.partyMax))).put("secrets", new JSONObject().put("join", this.joinSecret).put("spectate", this.spectateSecret).put("match", this.matchSecret)).put("instance", this.instance);
    }

    /* loaded from: L-out.jar:com/jagrosh/discordipc/entities/RichPresence$Builder.class */
    public static class Builder {
        private String state;
        private String details;
        private OffsetDateTime startTimestamp;
        private OffsetDateTime endTimestamp;
        private String largeImageKey;
        private String largeImageText;
        private String smallImageKey;
        private String smallImageText;
        private String partyId;
        private int partySize;
        private int partyMax;
        private String matchSecret;
        private String joinSecret;
        private String spectateSecret;
        private boolean instance;

        public RichPresence build() {
            return new RichPresence(this.state, this.details, this.startTimestamp, this.endTimestamp, this.largeImageKey, this.largeImageText, this.smallImageKey, this.smallImageText, this.partyId, this.partySize, this.partyMax, this.matchSecret, this.joinSecret, this.spectateSecret, this.instance);
        }

        public Builder setState(String str) {
            this.state = str;
            return this;
        }

        public Builder setDetails(String str) {
            this.details = str;
            return this;
        }

        public Builder setStartTimestamp(OffsetDateTime offsetDateTime) {
            this.startTimestamp = offsetDateTime;
            return this;
        }

        public Builder setEndTimestamp(OffsetDateTime offsetDateTime) {
            this.endTimestamp = offsetDateTime;
            return this;
        }

        public Builder setLargeImage(String str, String str2) {
            this.largeImageKey = str;
            this.largeImageText = str2;
            return this;
        }

        public Builder setLargeImage(String str) {
            return setLargeImage(str, null);
        }

        public Builder setSmallImage(String str, String str2) {
            this.smallImageKey = str;
            this.smallImageText = str2;
            return this;
        }

        public Builder setSmallImage(String str) {
            return setSmallImage(str, null);
        }

        public Builder setParty(String str, int i, int i2) {
            this.partyId = str;
            this.partySize = i;
            this.partyMax = i2;
            return this;
        }

        public Builder setMatchSecret(String str) {
            this.matchSecret = str;
            return this;
        }

        public Builder setJoinSecret(String str) {
            this.joinSecret = str;
            return this;
        }

        public Builder setSpectateSecret(String str) {
            this.spectateSecret = str;
            return this;
        }

        public Builder setInstance(boolean z) {
            this.instance = z;
            return this;
        }
    }
}
