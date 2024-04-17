// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.hologram.individual;

import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import br.com.stenox.training.utils.npc.nms.ReflectionUtil;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
import us.myles.ViaVersion.api.Via;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import org.bukkit.entity.ArmorStand;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntitySlime;

public class HologramLine
{
    private EntitySlime slime;
    private EntityArmorStand armorStand;
    private Location location;
    private String text;
    private Player viewer;
    
    public HologramLine(final Player viewer, final Location textLocation, final String text) {
        this.viewer = viewer;
        this.location = textLocation;
        this.text = text;
    }
    
    public void build() {
        (this.armorStand = new EntityArmorStand((World)((CraftWorld)this.location.getWorld()).getHandle(), this.location.getX(), this.location.getY(), this.location.getZ())).setCustomName(this.getText());
        this.armorStand.setCustomNameVisible(true);
        this.armorStand.setInvisible(true);
        this.armorStand.setGravity(false);
        this.armorStand.setSmall(true);
        this.armorStand.setArms(false);
        this.armorStand.n(true);
        final ArmorStand a = (ArmorStand)this.armorStand.getBukkitEntity();
        this.armorStand.a((AxisAlignedBB)new CustomBoundingBox());
        final Location slimeLocation = this.location.clone().add(0.0, 0.1, 0.0);
        final int size = (this.getText() == null) ? 1 : (this.getText().length() / 2 / 3);
        (this.slime = new EntitySlime((World)((CraftWorld)this.location.getWorld()).getHandle())).setLocation(slimeLocation.getX(), slimeLocation.getY(), slimeLocation.getZ(), slimeLocation.getYaw(), slimeLocation.getPitch());
        this.slime.getDataWatcher().watch(0, (Object)32);
        this.slime.getDataWatcher().watch(16, (Object)(byte)((size < 1) ? 1 : Math.min(size, 100)));
    }
    
    public void show() {
        final PlayerConnection conn = ((CraftPlayer)this.getViewer()).getHandle().playerConnection;
        final PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving((EntityLiving)this.armorStand);
        conn.sendPacket((Packet)packet);
        final PacketPlayOutSpawnEntityLiving packet2 = new PacketPlayOutSpawnEntityLiving((EntityLiving)this.slime);
        conn.sendPacket((Packet)packet2);
        if (Via.getAPI().getPlayerVersion((Object)this.getViewer()) == 47) {
            final PacketPlayOutAttachEntity packet3 = new PacketPlayOutAttachEntity();
            ReflectionUtil.setFieldValue(packet3, "a", 0);
            ReflectionUtil.setFieldValue(packet3, "b", this.slime.getId());
            ReflectionUtil.setFieldValue(packet3, "c", this.armorStand.getId());
            conn.sendPacket((Packet)packet3);
        }
    }
    
    public void hide() {
        final PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { this.getSlime().getId(), this.getArmorStand().getId() });
        ((CraftPlayer)this.getViewer()).getHandle().playerConnection.sendPacket((Packet)packet);
    }
    
    public void update(final String t) {
        this.text = t;
        final EntityArmorStand entityArmorStand = this.getArmorStand();
        entityArmorStand.setCustomName(t);
        final PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(entityArmorStand.getId(), entityArmorStand.getDataWatcher(), true);
        ((CraftPlayer)this.getViewer()).getHandle().playerConnection.sendPacket((Packet)metadata);
        final int size = (this.getText() == null) ? 1 : (this.getText().length() / 2 / 3);
        this.slime.getDataWatcher().watch(16, (Object)(byte)((size < 1) ? 1 : Math.min(size, 100)));
        final PacketPlayOutEntityMetadata slimeMeta = new PacketPlayOutEntityMetadata(this.getSlime().getId(), this.getSlime().getDataWatcher(), true);
        ((CraftPlayer)this.getViewer()).getHandle().playerConnection.sendPacket((Packet)slimeMeta);
    }
    
    public EntitySlime getSlime() {
        return this.slime;
    }
    
    public EntityArmorStand getArmorStand() {
        return this.armorStand;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public String getText() {
        return this.text;
    }
    
    public Player getViewer() {
        return this.viewer;
    }
    
    public void setSlime(final EntitySlime slime) {
        this.slime = slime;
    }
    
    public void setArmorStand(final EntityArmorStand armorStand) {
        this.armorStand = armorStand;
    }
    
    public void setLocation(final Location location) {
        this.location = location;
    }
    
    public void setText(final String text) {
        this.text = text;
    }
    
    public void setViewer(final Player viewer) {
        this.viewer = viewer;
    }
}
