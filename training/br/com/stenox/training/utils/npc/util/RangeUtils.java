// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.npc.util;

import org.bukkit.util.NumberConversions;
import org.bukkit.Bukkit;
import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RangeUtils
{
    public static final double cosFOV;
    public static final double bukkitRange;
    public static final double d;
    
    public static boolean canSee(final Player player, final Location loc) {
        return inRangeOf(player, loc) && inViewOf(player, loc);
    }
    
    public static boolean inRangeOf(final Player player, final Location loc) {
        if (player == null) {
            return false;
        }
        if (!player.getWorld().equals(loc.getWorld())) {
            return false;
        }
        final double distanceSquared = player.getLocation().distanceSquared(loc);
        return distanceSquared <= RangeUtils.d && distanceSquared <= RangeUtils.bukkitRange;
    }
    
    public static boolean inViewOf(final Player player, final Location loc) {
        final Vector dir = loc.clone().toVector().subtract(player.getEyeLocation().toVector()).normalize();
        return dir.dot(player.getEyeLocation().getDirection()) >= RangeUtils.cosFOV;
    }
    
    static {
        cosFOV = Math.cos(Math.toRadians(60.0));
        bukkitRange = NumberConversions.square((double)(Bukkit.getViewDistance() << 4));
        d = NumberConversions.square(70.0);
    }
}
