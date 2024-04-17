// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.npc;

import java.util.UUID;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.Reader;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.net.URL;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import com.mojang.authlib.properties.Property;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.plugin.Plugin;
import br.com.stenox.training.utils.npc.enums.Action;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import java.util.Iterator;
import org.bukkit.event.Listener;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.ProtocolLibrary;
import br.com.stenox.training.utils.npc.nms.ReflectionUtil;
import java.util.ArrayList;
import br.com.stenox.training.Main;
import java.util.List;
import br.com.stenox.training.custom.CustomManager;

public class NPCManager extends CustomManager
{
    public List<NPC> npcs;
    
    public NPC getById(final int id) {
        return this.npcs.stream().filter(n -> n.getID() == id).findFirst().orElse(null);
    }
    
    public NPCManager(final Main plugin) {
        super(plugin);
        this.npcs = new ArrayList<NPC>();
    }
    
    @Override
    public void onEnable() {
        ReflectionUtil.init();
        final NPCPacketListener packetListener = new NPCPacketListener(this);
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)packetListener);
        this.registerListener((Listener)new NPCListener());
    }
    
    @Override
    public void onDisable() {
        if (!this.getNpcs().isEmpty()) {
            for (final NPC npc : this.getNpcs()) {
                if (npc.isMoving()) {
                    npc.getWitch().remove();
                }
            }
        }
    }
    
    public NPC createNPC(final String name, final String value, final String signature, final Location location, final Player receiver) {
        final NPC npc = ReflectionUtil.newNPC(name, value, signature, location, receiver, false);
        npc.spawn();
        Bukkit.getScheduler().runTaskLater((Plugin)Main.getInstance(), () -> npc.setAction(Action.REMOVE_PLAYER), 30L);
        this.npcs.add(npc);
        return npc;
    }
    
    public NPC createNPCbyUUID(final String name, final String uuid, final Location location, final Player receiver) {
        final String[] test = this.getFromUUID(uuid);
        final NPC npc = ReflectionUtil.newNPC(name, test[0], test[1], location, receiver, false);
        npc.spawn();
        Bukkit.getScheduler().runTaskLater((Plugin)Main.getInstance(), () -> npc.setAction(Action.REMOVE_PLAYER), 30L);
        this.npcs.add(npc);
        return npc;
    }
    
    public void removeNPC(final NPC npc) {
        this.npcs.remove(npc);
        npc.despawn();
    }
    
    public String[] getFromPlayer(final Player playerBukkit) {
        final EntityPlayer playerNMS = ((CraftPlayer)playerBukkit).getHandle();
        final GameProfile profile = playerNMS.getProfile();
        final Property property = profile.getProperties().get((Object)"textures").iterator().next();
        final String texture = property.getValue();
        final String signature = property.getSignature();
        return new String[] { texture, signature };
    }
    
    public String[] getFromName(final String name) {
        try {
            final URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            final InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());
            final String uuid = new JsonParser().parse((Reader)reader_0).getAsJsonObject().get("id").getAsString();
            final URL url_2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            final InputStreamReader reader_2 = new InputStreamReader(url_2.openStream());
            final JsonObject textureProperty = new JsonParser().parse((Reader)reader_2).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            final String texture = textureProperty.get("value").getAsString();
            final String signature = textureProperty.get("signature").getAsString();
            return new String[] { texture, signature };
        }
        catch (IOException e) {
            System.err.println("Could not get skin data from session servers!");
            e.printStackTrace();
            return null;
        }
    }
    
    public String[] getFromUUID(final String uuid) {
        try {
            final URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            final InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
            final JsonObject textureProperty = new JsonParser().parse((Reader)reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            final String texture = textureProperty.get("value").getAsString();
            final String signature = textureProperty.get("signature").getAsString();
            return new String[] { texture, signature };
        }
        catch (IOException e) {
            System.err.println("Could not get skin data from session servers!");
            e.printStackTrace();
            return null;
        }
    }
    
    public void removePlayerHumans(final UUID uuid) {
        this.getNpcs().removeIf(c -> c.getReceiver().getUniqueId().equals(uuid));
    }
    
    public List<NPC> getNpcs() {
        return this.npcs;
    }
}
