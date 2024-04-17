// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.npc.nms;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import java.lang.reflect.Field;

public class PacketInjector
{
    private Field EntityPlayer_playerConnection;
    private Class<?> PlayerConnection;
    private Field PlayerConnection_networkManager;
    private Class<?> NetworkManager;
    private Field a;
    private Field b;
    
    public PacketInjector() {
        try {
            this.EntityPlayer_playerConnection = ReflectionUtil.getField(ReflectionUtil.getCraftClass("EntityPlayer"), "playerConnection");
            this.PlayerConnection = ReflectionUtil.getCraftClass("PlayerConnection");
            this.PlayerConnection_networkManager = ReflectionUtil.getField(this.PlayerConnection, "networkManager");
            this.NetworkManager = ReflectionUtil.getCraftClass("NetworkManager");
            this.a = ReflectionUtil.getField(this.NetworkManager, "b");
            this.b = ReflectionUtil.getField(this.NetworkManager, "channel");
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public void addPlayer(final Player p) {
        try {
            final Channel ch = this.getChannel(this.getNetworkManager(ReflectionUtil.getHandle((Entity)p)));
            if (ch.pipeline().get("PacketInjector") == null) {
                final PacketReader h = new PacketReader(p);
                ch.pipeline().addBefore("packet_handler", "PacketInjector", (ChannelHandler)h);
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public void removePlayer(final Player p) {
        try {
            final Channel ch = this.getChannel(this.getNetworkManager(ReflectionUtil.getHandle((Entity)p)));
            if (ch.pipeline().get("PacketInjector") != null) {
                ch.pipeline().remove("PacketInjector");
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    private Object getNetworkManager(final Object ep) {
        try {
            return ReflectionUtil.getFieldValue(this.PlayerConnection_networkManager, this.EntityPlayer_playerConnection.get(ep));
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private Channel getChannel(final Object networkManager) {
        Channel ch = null;
        try {
            ch = ReflectionUtil.getFieldValue(this.a, networkManager);
        }
        catch (Exception e) {
            ch = ReflectionUtil.getFieldValue(this.b, networkManager);
        }
        return ch;
    }
}
