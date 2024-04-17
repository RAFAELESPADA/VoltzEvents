// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.hologram;

import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.Location;
import net.minecraft.server.v1_8_R3.EntityArmorStand;

public class EntityHologramStand extends EntityArmorStand implements ArmorHologram
{
    private final HologramLine line;
    
    public EntityHologramStand(final Location toSpawn, final HologramLine line) {
        super((World)((CraftWorld)toSpawn.getWorld()).getHandle());
        this.setArms(false);
        this.setBasePlate(true);
        this.setInvisible(true);
        this.setGravity(false);
        this.setSmall(true);
        this.line = line;
        this.setPosition(toSpawn.getX(), toSpawn.getY(), toSpawn.getZ());
        try {
            final Field blockedSlots = EntityArmorStand.class.getDeclaredField("bi");
            blockedSlots.setAccessible(true);
            blockedSlots.set(this, Integer.MAX_VALUE);
        }
        catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        this.a((AxisAlignedBB)new NullBoundingBox());
    }
    
    public void t_() {
        this.ticksLived = 0;
        if (this.dead) {
            super.t_();
        }
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
    }
    
    public boolean c(final NBTTagCompound nbttagcompound) {
        return false;
    }
    
    public void e(final NBTTagCompound nbttagcompound) {
    }
    
    public void setCustomName(final String s) {
    }
    
    public void setCustomNameVisible(final boolean flag) {
    }
    
    public boolean a(final EntityHuman entityhuman, final Vec3D vec3d) {
        return true;
    }
    
    public void setText(String text) {
        if (text != null && text.length() > 300) {
            text = text.substring(0, 300);
        }
        super.setCustomName((text == null) ? "" : text);
        super.setCustomNameVisible(text != null && !text.isEmpty());
    }
    
    public void killEntity() {
        super.die();
    }
    
    public HologramLine getLine() {
        return this.line;
    }
    
    public Hologram getHologram() {
        return (this.line == null) ? null : this.line.getHologram();
    }
    
    public CraftEntity getBukkitEntity() {
        if (this.bukkitEntity == null) {
            this.bukkitEntity = (CraftEntity)new CraftHologramStand(this);
        }
        return super.getBukkitEntity();
    }
    
    static class CraftHologramStand extends CraftArmorStand
    {
        public CraftHologramStand(final EntityHologramStand entity) {
            super(entity.world.getServer(), (EntityArmorStand)entity);
        }
        
        public void remove() {
        }
    }
}
