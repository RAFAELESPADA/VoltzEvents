// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.npc;

import java.util.Iterator;
import br.com.stenox.training.utils.hologram.individual.HologramLine;
import br.com.stenox.training.utils.hologram.individual.IndividualHolograms;
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
        final Player player2;
        final int n;
        final EnumWrappers.EntityUseAction entityUseAction;
        Bukkit.getScheduler().runTask(this.getPlugin(), () -> {
            if (!this.handleNPC(player2, n, entityUseAction)) {
                this.handleHolograms(player2, n, entityUseAction);
            }
        });
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
    
    private void handleHolograms(final Player p, final int id, final EnumWrappers.EntityUseAction useAction) {
        boolean found = false;
        for (final IndividualHolograms h : Main.getInstance().getHologramManager().getHologramsList()) {
            if (found) {
                break;
            }
            if (h.isHided()) {
                continue;
            }
            if (h.getTarget() != p) {
                continue;
            }
            if (h.getClickHandler() == null) {
                continue;
            }
            for (int i = 0; i < h.getHologramLines().size(); ++i) {
                final HologramLine e = h.getHologramLines().get(i);
                if (e.getSlime().getId() == id) {
                    found = true;
                    h.getClickHandler().onClick(p, h, i, (useAction == EnumWrappers.EntityUseAction.ATTACK) ? EventAction.LEFT_CLICK : EventAction.RIGHT_CLICK);
                    break;
                }
            }
        }
    }
}
