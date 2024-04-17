// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.npc;

import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.event.Event;
import br.com.stenox.training.utils.npc.enums.EventAction;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import br.com.stenox.training.Main;
import com.comphenix.protocol.events.PacketAdapter;

public class NPCPacketListener extends PacketAdapter
{
    private final NPCManager manager;
    
    public NPCPacketListener(final NPCManager manager) {
        super((Plugin)Main.getInstance(), ListenerPriority.NORMAL, new PacketType[] { PacketType.Play.Client.USE_ENTITY });
        this.manager = manager;
    }
    
    public void onPacketReceiving(final PacketEvent event) {
        final Player player = event.getPlayer();
        final int id = (int)event.getPacket().getIntegers().read(0);
        final EnumWrappers.EntityUseAction action = (EnumWrappers.EntityUseAction)event.getPacket().getEntityUseActions().read(0);
        if (action == EnumWrappers.EntityUseAction.INTERACT_AT) {
            return;
        }
        int n = 3;
        final EnumWrappers.EntityUseAction entityUseAction;
   
    }
    
    private boolean handleNPC(final Player p, final int id, final EnumWrappers.EntityUseAction useAction) {
        final Location pLoc = p.getLocation();
        final NPC npc = this.manager.getById(id);
        if (npc == null) {
            return false;
        }
        if (pLoc.distanceSquared(npc.getLocation()) <= 9.0) {
            final EventAction action = (useAction == EnumWrappers.EntityUseAction.ATTACK) ? EventAction.LEFT_CLICK : EventAction.RIGHT_CLICK;
            Bukkit.getPluginManager().callEvent((Event)new NPCInteractEvent(p, npc, action));
        }
        return true;
    }
    
    
            
        
    }

