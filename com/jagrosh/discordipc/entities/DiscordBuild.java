package com.jagrosh.discordipc.entities;

/* loaded from: L-out.jar:com/jagrosh/discordipc/entities/DiscordBuild.class */
public enum DiscordBuild {
    CANARY("//canary.discordapp.com/api"),
    PTB("//ptb.discordapp.com/api"),
    STABLE("//discordapp.com/api"),
    ANY;

    private final String endpoint;

    DiscordBuild(String str) {
        this.endpoint = str;
    }

    DiscordBuild() {
        this(null);
    }

    public static DiscordBuild from(String str) {
        for (DiscordBuild discordBuild : values()) {
            if (discordBuild.endpoint != null && discordBuild.endpoint.equals(str)) {
                return discordBuild;
            }
        }
        return ANY;
    }
}
