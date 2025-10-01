package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jdk.internal.dynalink.CallSiteDescriptor;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.login.MinecraftAccount;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/file/configs/AccountsConfig.class */
public class AccountsConfig extends FileConfig {
    private final List accounts;

    public AccountsConfig(File file) {
        super(file);
        this.accounts = new ArrayList();
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    protected void loadConfig() {
        clearAccounts();
        try {
            JsonElement jsonElement = new JsonParser().parse(new BufferedReader(new FileReader(getFile())));
            if (jsonElement instanceof JsonNull) {
                return;
            }
            Iterator it = jsonElement.getAsJsonArray().iterator();
            while (it.hasNext()) {
                JsonObject asJsonObject = ((JsonElement) it.next()).getAsJsonObject();
                JsonElement jsonElement2 = asJsonObject.get("name");
                JsonElement jsonElement3 = asJsonObject.get("password");
                JsonElement jsonElement4 = asJsonObject.get("inGameName");
                if (jsonElement4 == null || jsonElement4.isJsonNull()) {
                    addAccount(jsonElement2.getAsString(), jsonElement3.getAsString());
                } else if (jsonElement4.isJsonNull() && jsonElement3.isJsonNull()) {
                    addAccount(jsonElement2.getAsString());
                } else {
                    addAccount(jsonElement2.getAsString(), asJsonObject.get("password").getAsString(), asJsonObject.get("inGameName").getAsString());
                }
            }
        } catch (JsonSyntaxException | IllegalStateException unused) {
            ClientUtils.getLogger().info("[FileManager] Try to load old Accounts config...");
            List list = (List) new Gson().fromJson(new BufferedReader(new FileReader(getFile())), List.class);
            if (list == null) {
                return;
            }
            this.accounts.clear();
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                String[] strArrSplit = ((String) it2.next()).split(CallSiteDescriptor.TOKEN_DELIMITER);
                if (strArrSplit.length >= 3) {
                    this.accounts.add(new MinecraftAccount(strArrSplit[0], strArrSplit[1], strArrSplit[2]));
                } else if (strArrSplit.length == 2) {
                    this.accounts.add(new MinecraftAccount(strArrSplit[0], strArrSplit[1]));
                } else {
                    this.accounts.add(new MinecraftAccount(strArrSplit[0]));
                }
            }
            ClientUtils.getLogger().info("[FileManager] Loaded old Accounts config...");
            saveConfig();
            ClientUtils.getLogger().info("[FileManager] Saved Accounts to new config...");
        }
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    protected void saveConfig() {
        JsonArray jsonArray = new JsonArray();
        for (MinecraftAccount minecraftAccount : this.accounts) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", minecraftAccount.getName());
            jsonObject.addProperty("password", minecraftAccount.getPassword());
            jsonObject.addProperty("inGameName", minecraftAccount.getAccountName());
            jsonArray.add(jsonObject);
        }
        PrintWriter printWriter = new PrintWriter(new FileWriter(getFile()));
        printWriter.println(FileManager.PRETTY_GSON.toJson(jsonArray));
        printWriter.close();
    }

    public void addAccount(String str) {
        if (accountExists(str)) {
            return;
        }
        this.accounts.add(new MinecraftAccount(str));
    }

    public void addAccount(String str, String str2) {
        if (accountExists(str)) {
            return;
        }
        this.accounts.add(new MinecraftAccount(str, str2));
    }

    public void addAccount(String str, String str2, String str3) {
        if (accountExists(str)) {
            return;
        }
        this.accounts.add(new MinecraftAccount(str, str2, str3));
    }

    public void removeAccount(int i) {
        this.accounts.remove(i);
    }

    public void removeAccount(MinecraftAccount minecraftAccount) {
        this.accounts.remove(minecraftAccount);
    }

    public boolean accountExists(String str) {
        Iterator it = this.accounts.iterator();
        while (it.hasNext()) {
            if (((MinecraftAccount) it.next()).getName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public void clearAccounts() {
        this.accounts.clear();
    }

    public List getAccounts() {
        return this.accounts;
    }
}
