// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.hologram;

import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;

public class HologramLine
{
    private final Hologram hologram;
    private final Location location;
    private String text;
    private ArmorHologram entity;
    
    public HologramLine(final Hologram hologram, final Location location, final String text) {
        this.hologram = hologram;
        this.location = location;
        this.text = ChatColor.translateAlternateColorCodes('&', text);
    }
    
    private ArmorHologram createEntity(final Location location, final String text) {
        final EntityHologramStand entity = new EntityHologramStand(location, this);
        try {
            final int chunkX = location.getBlockX() >> 4;
            final int chunkZ = location.getBlockZ() >> 4;
            if (!location.getWorld().isChunkLoaded(chunkX, chunkZ)) {
                return null;
            }
            if (entity.world.addEntity((Entity)entity, CreatureSpawnEvent.SpawnReason.CUSTOM)) {
                entity.setText(text);
                return entity;
            }
            return null;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public void spawn() {
        if (this.entity == null) {
            this.entity = this.createEntity(this.location, this.text);
        }
    }
    
    public void despawn() {
        if (this.entity != null) {
            this.entity.killEntity();
            this.entity = null;
        }
    }
    
    public void setText(final String text) {
        this.text = ChatColor.translateAlternateColorCodes('&', text);
        if (this.entity != null) {
            this.entity.setText(this.text);
        }
    }
    
    public String getText() {
        return this.text;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public Hologram getHologram() {
        return this.hologram;
    }
    
    public ArmorHologram getEntity() {
        return this.entity;
    }
}
