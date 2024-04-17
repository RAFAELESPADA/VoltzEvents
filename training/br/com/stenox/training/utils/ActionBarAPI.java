// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import br.com.stenox.training.Main;
import br.com.stenox.training.utils.protocol.ProtocolGetter;
import br.com.stenox.training.utils.protocol.ProtocolVersion;

public class ActionBarAPI
{
    public static void send(final Player player, final String text) {
        final ProtocolVersion version = ProtocolGetter.getVersion(player);
        if (version.getId() >= ProtocolVersion.MINECRAFT_1_8.getId()) {
            final PacketContainer chatPacket = new PacketContainer(PacketType.Play.Server.CHAT);
            chatPacket.getChatComponents().write(0 , WrappedChatComponent.fromJson("{\"text\":\"" + text + " \"}"));
            chatPacket.getBytes().write(0, (byte)2);
            try {
                Main.getInstance().getProtocolManager().sendServerPacket(player, chatPacket);
            }
            catch (Exception e) {
                throw new RuntimeException("Cannot send packet " + chatPacket, e);
            }
        }
    }
    
    public static void broadcast(final String text) {
        Bukkit.getOnlinePlayers().forEach(p -> send(p, text));
    }
}
