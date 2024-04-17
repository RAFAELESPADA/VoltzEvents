// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.hologram.individual;

import br.com.stenox.training.utils.npc.util.RangeUtils;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Bukkit;
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

public class HologramListeners implements Listener
{
    private final double d = 4900.0;
    
    @EventHandler
    public void onChunkUnload(final ChunkUnloadEvent event) {
        final Chunk chunk = event.getChunk();
        ArrayList<IndividualHolograms> n = new ArrayList<IndividualHolograms>();
        for (final IndividualHolograms hl : Main.getInstance().getHologramManager().getHologramsList()) {
            if (!isSameChunk(hl.getLocation(), chunk)) {
                continue;
            }
            final Player p = hl.getTarget();
            if (hl.isHided()) {
                continue;
            }
            if (p == null) {
                continue;
            }
            n.add(hl);
        }
        for (final IndividualHolograms individualHolograms : n) {
            individualHolograms.hide();
        }
        n = null;
    }
    
    @EventHandler
    public void onChunkLoad(final ChunkLoadEvent event) {
        final Chunk chunk = event.getChunk();
        ArrayList<IndividualHolograms> n = new ArrayList<IndividualHolograms>();
        for (final IndividualHolograms npc : Main.getInstance().getHologramManager().getHologramsList()) {
            if ((npc.getLocation() == null || !isSameChunk(npc.getLocation(), chunk)) && !npc.isHided()) {
                return;
            }
            final Player player = npc.getTarget();
            if (player == null) {
                return;
            }
            if (!Objects.equals(npc.getLocation().getWorld(), player.getWorld())) {
                continue;
            }
            final double distanceSquared = player.getLocation().distanceSquared(npc.getLocation());
            final boolean inRange = distanceSquared <= 4900.0 || distanceSquared <= Bukkit.getViewDistance() << 4;
            if (!inRange) {
                continue;
            }
            n.add(npc);
        }
        for (final IndividualHolograms wantedNpcs : n) {
            wantedNpcs.hide();
        }
        n = null;
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Location from = event.getFrom();
        final Location to = event.getTo();
        if (to == null || from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {
            this.handleMove(event.getPlayer());
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTeleport(final PlayerTeleportEvent event) {
        Bukkit.getScheduler().runTask((Plugin)Main.getInstance(), () -> this.handleMove(event.getPlayer()));
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(final PlayerQuitEvent e) {
        Main.getInstance().getHologramManager().removePlayerHologram(e.getPlayer().getUniqueId());
    }
    
    private void handleMove(final Player player) {
        for (final IndividualHolograms hl : Main.getInstance().getHologramManager().getHologramsList()) {
            if (hl.getTarget() == player) {
                final boolean nearby = RangeUtils.inRangeOf(player, hl.getLocation());
                if (hl.isHided() && nearby) {
                    hl.show();
                }
                else {
                    if (hl.isHided() || nearby) {
                        continue;
                    }
                    hl.hide();
                }
            }
        }
    }
    
    private static int getChunkCoordinate(final int coordinate) {
        return coordinate >> 4;
    }
    
    private static boolean isSameChunk(final Location loc, final Chunk chunk) {
        return getChunkCoordinate(loc.getBlockX()) == chunk.getX() && getChunkCoordinate(loc.getBlockZ()) == chunk.getZ();
    }
}
