// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils;

import com.google.gson.JsonObject;
import br.com.stenox.training.gamer.Gamer;

public class JsonUtils
{
    public static JsonObject makePunishments(final Gamer gamer) {
        final JsonObject object = new JsonObject();
        object.addProperty("isMuted", Boolean.valueOf(gamer.isMuted()));
        object.addProperty("isBanned", Boolean.valueOf(gamer.isBanned()));
        object.addProperty("muteReason", (gamer.getMuteReason() == null) ? "" : gamer.getMuteReason());
        object.addProperty("banReason", (gamer.getBanReason() == null) ? "" : gamer.getBanReason());
        object.addProperty("muteTime", (Number)gamer.getMuteTime());
        object.addProperty("banTime", (Number)gamer.getBanTime());
        return object;
    }
    
    public static void setPunishments(final Gamer gamer, final JsonObject object) {
        gamer.setMuted(object.get("isMuted").getAsBoolean());
        gamer.setBanned(object.get("isBanned").getAsBoolean());
        gamer.setMuteReason(object.get("muteReason").getAsString());
        gamer.setBanReason(object.get("banReason").getAsString());
        gamer.setMuteTime(object.get("muteTime").getAsLong());
        gamer.setBanTime(object.get("banTime").getAsLong());
    }
}
