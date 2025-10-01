package com.thealtening;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thealtening.domain.Account;
import com.thealtening.domain.User;
import com.thealtening.utils.Utilities;
import java.io.IOException;
import java.net.URL;

/* loaded from: L-out.jar:com/thealtening/TheAltening.class */
public final class TheAltening {
    private final String apiKey;
    private final String website = "http://api.thealtening.com/v1/";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public TheAltening(String str) {
        this.apiKey = str;
    }

    public User getUser() throws IOException {
        StringBuilder sb = new StringBuilder();
        getClass();
        return (User) this.gson.fromJson(new String(Utilities.getInstance().readAllBytes(new URL(attach(sb.append("http://api.thealtening.com/v1/").append("license").toString())).openConnection().getInputStream())), User.class);
    }

    public Account generateAccount(User user) throws IOException {
        StringBuilder sb = new StringBuilder();
        getClass();
        String str = new String(Utilities.getInstance().readAllBytes(new URL(attach(sb.append("http://api.thealtening.com/v1/").append("generate").toString())).openConnection().getInputStream()));
        if (user.isPremium()) {
            return (Account) this.gson.fromJson(str, Account.class);
        }
        return null;
    }

    public boolean favoriteAccount(Account account) throws IOException {
        StringBuilder sb = new StringBuilder();
        getClass();
        return new String(Utilities.getInstance().readAllBytes(new URL(attachAccount(sb.append("http://api.thealtening.com/v1/").append("favorite").toString(), account)).openConnection().getInputStream())).isEmpty();
    }

    public boolean privateAccount(Account account) throws IOException {
        StringBuilder sb = new StringBuilder();
        getClass();
        return new String(Utilities.getInstance().readAllBytes(new URL(attachAccount(sb.append("http://api.thealtening.com/v1/").append("private").toString(), account)).openConnection().getInputStream())).isEmpty();
    }

    private String attach(String str) {
        return str + "?token=" + this.apiKey;
    }

    private String attachAccount(String str, Account account) {
        return str + "?token=" + this.apiKey + "&acctoken=" + account.getToken();
    }
}
