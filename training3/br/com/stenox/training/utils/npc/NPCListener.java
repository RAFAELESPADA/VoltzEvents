// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.npc;

import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Bukkit;
import br.com.stenox.training.utils.npc.util.RangeUtils;
import java.util.Objects;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import java.util.Iterator;
import org.bukkit.Chunk;
import br.com.stenox.training.Main;
import java.util.ArrayList;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.Listener;

public class NPCListener implements Listener
{
    @EventHandler
    public void onChunkUnload(final ChunkUnloadEvent event) {
        final Chunk chunk = event.getChunk();
        final ArrayList<NPC> n = new ArrayList<NPC>();
        for (final NPC npc : Main.getInstance().getNpcManager().getNpcs()) {
            if (npc.getLocation() != null) {
                if (!isSameChunk(npc.getLocation(), chunk)) {
                    continue;
                }
                final Player p = npc.getReceiver();
                if (npc.isHidden()) {
                    continue;
                }
                if (p == null) {
                    continue;
                }
                n.add(npc);
            }
        }
        for (final NPC wantedNpcs : n) {
            wantedNpcs.despawn();
            wantedNpcs.setHidden(true);
        }
    }
    
    @EventHandler
    public void onChunkLoad(final ChunkLoadEvent event) {
        final Chunk chunk = event.getChunk();
        final ArrayList<NPC> n = new ArrayList<NPC>();
        for (final NPC npc : Main.getInstance().getNpcManager().getNpcs()) {
            if ((npc.getLocation() == null || !isSameChunk(npc.getLocation(), chunk)) && !npc.isHidden()) {
                return;
            }
            final Player player = npc.getReceiver();
            if (player == null) {
                return;
            }
            if (!Objects.equals(npc.getLocation().getWorld(), player.getWorld())) {
                continue;
            }
            final double distanceSquared = player.getLocation().distanceSquared(npc.getLocation());
            final boolean inRange = distanceSquared <= RangeUtils.d || distanceSquared <= Bukkit.getViewDistance() << 4;
            if (!inRange) {
                continue;
            }
            n.add(npc);
        }
        for (final NPC wantedNpcs : n) {
            wantedNpcs.despawn();
        }
    }
    
    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Location from = event.getFrom();
        final Location to = event.getTo();
        if (to == null || from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {
            this.handleMove(event.getPlayer());
        }
    }
    
    private static int getChunkCoordinate(final int coordinate) {
        return coordinate >> 4;
    }
    
    private static boolean isSameChunk(final Location loc, final Chunk chunk) {
        return getChunkCoordinate(loc.getBlockX()) == chunk.getX() && getChunkCoordinate(loc.getBlockZ()) == chunk.getZ();
    }
    
    private void handleMove(final Player player) {
        final List<NPC> npcs = new ArrayList<NPC>();
        for (final NPC npc : Main.getInstance().getNpcManager().getNpcs()) {
            if (npc.getReceiver() == player) {
                npcs.add(npc);
            }
        }
        for (final NPC npc : npcs) {
            final boolean inRange = RangeUtils.inRangeOf(player, npc.getLocation());
            final boolean isViewing = RangeUtils.inViewOf(player, npc.getLocation());
            if (npc.isHidden() && inRange && isViewing) {
                npc.respawn();
            }
            else {
                if (npc.isHidden() || inRange) {
                    continue;
                }
                npc.despawn();
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerNPC(final PlayerQuitEvent e) {
        Main.getInstance().getNpcManager().removePlayerHumans(e.getPlayer().getUniqueId());
    }
}
