package net.ccbluex.liquidbounce.file.configs;

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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jdk.internal.dynalink.CallSiteDescriptor;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.utils.ClientUtils;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/file/configs/FriendsConfig.class */
public class FriendsConfig extends FileConfig {
    private final List friends;

    public FriendsConfig(File file) {
        super(file);
        this.friends = new ArrayList();
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    protected void loadConfig() throws IOException {
        clearFriends();
        try {
            JsonElement jsonElement = new JsonParser().parse(new BufferedReader(new FileReader(getFile())));
            if (jsonElement instanceof JsonNull) {
                return;
            }
            Iterator it = jsonElement.getAsJsonArray().iterator();
            while (it.hasNext()) {
                JsonObject asJsonObject = ((JsonElement) it.next()).getAsJsonObject();
                addFriend(asJsonObject.get("playerName").getAsString(), asJsonObject.get("alias").getAsString());
            }
        } catch (JsonSyntaxException | IllegalStateException unused) {
            ClientUtils.getLogger().info("[FileManager] Try to load old Friends config...");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile()));
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    if (!line.contains("{") && !line.contains("}")) {
                        String strReplace = line.replace(" ", "").replace("\"", "").replace(",", "");
                        if (strReplace.contains(CallSiteDescriptor.TOKEN_DELIMITER)) {
                            String[] strArrSplit = strReplace.split(CallSiteDescriptor.TOKEN_DELIMITER);
                            addFriend(strArrSplit[0], strArrSplit[1]);
                        } else {
                            addFriend(strReplace);
                        }
                    }
                } else {
                    bufferedReader.close();
                    ClientUtils.getLogger().info("[FileManager] Loaded old Friends config...");
                    saveConfig();
                    ClientUtils.getLogger().info("[FileManager] Saved Friends to new config...");
                    return;
                }
            }
        }
    }

    @Override // net.ccbluex.liquidbounce.file.FileConfig
    protected void saveConfig() {
        JsonArray jsonArray = new JsonArray();
        for (Friend friend : getFriends()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("playerName", friend.getPlayerName());
            jsonObject.addProperty("alias", friend.getAlias());
            jsonArray.add(jsonObject);
        }
        PrintWriter printWriter = new PrintWriter(new FileWriter(getFile()));
        printWriter.println(FileManager.PRETTY_GSON.toJson(jsonArray));
        printWriter.close();
    }

    public boolean addFriend(String str) {
        return addFriend(str, str);
    }

    public boolean addFriend(String str, String str2) {
        if (isFriend(str)) {
            return false;
        }
        this.friends.add(new Friend(this, str, str2));
        return true;
    }

    public boolean removeFriend(String str) {
        if (!isFriend(str)) {
            return false;
        }
        this.friends.removeIf((v1) -> {
            return lambda$removeFriend$0(r1, v1);
        });
        return true;
    }

    private static boolean lambda$removeFriend$0(String str, Friend friend) {
        return friend.getPlayerName().equals(str);
    }

    public boolean isFriend(String str) {
        Iterator it = this.friends.iterator();
        while (it.hasNext()) {
            if (((Friend) it.next()).getPlayerName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public void clearFriends() {
        this.friends.clear();
    }

    public List getFriends() {
        return this.friends;
    }

    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/file/configs/FriendsConfig$Friend.class */
    public class Friend {
        private final String playerName;
        private final String alias;
        final FriendsConfig this$0;

        Friend(FriendsConfig friendsConfig, String str, String str2) {
            this.this$0 = friendsConfig;
            this.playerName = str;
            this.alias = str2;
        }

        public String getPlayerName() {
            return this.playerName;
        }

        public String getAlias() {
            return this.alias;
        }
    }
}
