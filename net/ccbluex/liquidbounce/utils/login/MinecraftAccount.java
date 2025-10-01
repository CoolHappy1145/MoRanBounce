package net.ccbluex.liquidbounce.utils.login;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/login/MinecraftAccount.class */
public final class MinecraftAccount {
    private final String username;
    private String password;
    private String inGameName;

    public MinecraftAccount(String str) {
        this.username = str;
    }

    public MinecraftAccount(String str, String str2) {
        this.username = str;
        this.password = str2;
    }

    public MinecraftAccount(String str, String str2, String str3) {
        this.username = str;
        this.password = str2;
        this.inGameName = str3;
    }

    public boolean isCracked() {
        return this.password == null || this.password.isEmpty();
    }

    public String getName() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getAccountName() {
        return this.inGameName;
    }

    public void setAccountName(String str) {
        this.inGameName = str;
    }
}
