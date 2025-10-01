package net.mcleaks;

/* loaded from: L-out.jar:net/mcleaks/Session.class */
public class Session {
    private final String username;
    private final String token;

    public Session(String str, String str2) {
        this.username = str;
        this.token = str2;
    }

    public String getUsername() {
        return this.username;
    }

    public String getToken() {
        return this.token;
    }
}
