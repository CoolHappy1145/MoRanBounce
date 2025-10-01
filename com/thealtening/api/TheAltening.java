package com.thealtening.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.thealtening.api.data.AccountData;
import com.thealtening.api.data.LicenseData;
import com.thealtening.api.utils.HttpUtils;
import com.thealtening.api.utils.exceptions.TheAlteningException;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/* loaded from: L-out.jar:com/thealtening/api/TheAltening.class */
public class TheAltening extends HttpUtils {
    private final String apiKey;
    private final String endpoint = "http://api.thealtening.com/v1/";
    private final Logger logger = Logger.getLogger("TheAltening");
    private final Gson gson = new Gson();

    public TheAltening(String str) {
        this.apiKey = str;
    }

    public LicenseData getLicenseData() {
        try {
            String strConnect = connect("http://api.thealtening.com/v1/license?token=" + this.apiKey);
            JsonObject jsonObject = (JsonObject) this.gson.fromJson(strConnect, JsonObject.class);
            if (jsonObject == null) {
                throw new TheAlteningException("JSON", "Unable to parse JSON data, here's what is in there: \n" + strConnect);
            }
            if (jsonObject.has("error") && jsonObject.has("errorMessage")) {
                throw new TheAlteningException(jsonObject.get("error").getAsString(), jsonObject.get("errorMessage").getAsString());
            }
            return (LicenseData) this.gson.fromJson(jsonObject, LicenseData.class);
        } catch (IOException e) {
            throw new TheAlteningException("IOException", "Unable to establish a connection, here's why: \n" + e.getCause());
        }
    }

    public AccountData getAccountData() {
        try {
            String strConnect = connect("http://api.thealtening.com/v1/generate?info=true&token=" + this.apiKey);
            JsonObject jsonObject = (JsonObject) this.gson.fromJson(strConnect, JsonObject.class);
            if (jsonObject == null) {
                throw new TheAlteningException("JSON", "Unable to parse JSON data, here's what is in there: \n" + strConnect);
            }
            if (jsonObject.has("error") && jsonObject.has("errorMessage")) {
                throw new TheAlteningException(jsonObject.get("error").getAsString(), jsonObject.get("errorMessage").getAsString());
            }
            return (AccountData) this.gson.fromJson(jsonObject, AccountData.class);
        } catch (IOException e) {
            throw new TheAlteningException("IOException", "Unable to establish a connection, here's why: \n" + e.getCause());
        }
    }

    public boolean isPrivate(String str) {
        try {
            String strConnect = connect("http://api.thealtening.com/v1/private?acctoken=" + str + "&token=" + this.apiKey);
            JsonObject jsonObject = (JsonObject) this.gson.fromJson(strConnect, JsonObject.class);
            if (jsonObject == null) {
                throw new TheAlteningException("JSON", "Unable to parse JSON data, here's what is in there: \n" + strConnect);
            }
            if (jsonObject.has("success")) {
                return jsonObject.get("success").getAsBoolean();
            }
            if (jsonObject.has("error") && jsonObject.has("errorMessage")) {
                throw new TheAlteningException(jsonObject.get("error").getAsString(), jsonObject.get("errorMessage").getAsString());
            }
            return false;
        } catch (IOException e) {
            throw new TheAlteningException("IOException", "Unable to establish a connection, here's why: \n" + e.getCause());
        }
    }

    public boolean isFavorite(String str) {
        try {
            String strConnect = connect("http://api.thealtening.com/v1/favorite?acctoken=" + str + "&token=" + this.apiKey);
            JsonObject jsonObject = (JsonObject) this.gson.fromJson(strConnect, JsonObject.class);
            if (jsonObject == null) {
                throw new TheAlteningException("JSON", "Unable to parse JSON data, here's what is in there: \n" + strConnect);
            }
            if (jsonObject.has("success")) {
                return jsonObject.get("success").getAsBoolean();
            }
            if (jsonObject.has("error") && jsonObject.has("errorMessage")) {
                throw new TheAlteningException(jsonObject.get("error").getAsString(), jsonObject.get("errorMessage").getAsString());
            }
            return false;
        } catch (IOException e) {
            throw new TheAlteningException("IOException", "Unable to establish a connection, here's why: \n" + e.getCause());
        }
    }

    /* loaded from: L-out.jar:com/thealtening/api/TheAltening$Asynchronous.class */
    public static class Asynchronous {
        private final TheAltening instance;

        public Asynchronous(TheAltening theAltening) {
            this.instance = theAltening;
        }

        public CompletableFuture getLicenseData() {
            CompletableFuture completableFuture = new CompletableFuture();
            try {
                completableFuture.complete(this.instance.getLicenseData());
            } catch (TheAlteningException e) {
                completableFuture.completeExceptionally(e);
            }
            return completableFuture;
        }

        public CompletableFuture getAccountData() {
            CompletableFuture completableFuture = new CompletableFuture();
            try {
                completableFuture.complete(this.instance.getAccountData());
            } catch (TheAlteningException e) {
                completableFuture.completeExceptionally(e);
            }
            return completableFuture;
        }

        public CompletableFuture isPrivate(String str) {
            CompletableFuture completableFuture = new CompletableFuture();
            try {
                completableFuture.complete(Boolean.valueOf(this.instance.isPrivate(str)));
            } catch (TheAlteningException e) {
                completableFuture.completeExceptionally(e);
            }
            return completableFuture;
        }

        public CompletableFuture isFavorite(String str) {
            CompletableFuture completableFuture = new CompletableFuture();
            try {
                completableFuture.complete(Boolean.valueOf(this.instance.isFavorite(str)));
            } catch (TheAlteningException e) {
                completableFuture.completeExceptionally(e);
            }
            return completableFuture;
        }
    }
}
