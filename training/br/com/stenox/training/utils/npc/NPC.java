// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.npc;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.entity.Witch;
import org.bukkit.inventory.ItemStack;
import br.com.stenox.training.utils.npc.enums.EquipmentSlot;
import br.com.stenox.training.utils.npc.enums.Status;
import br.com.stenox.training.utils.npc.enums.Animation;
import org.bukkit.util.Vector;
import org.bukkit.entity.Entity;
import com.comphenix.protocol.utility.MinecraftReflection;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import br.com.stenox.training.Main;
import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import java.util.NoSuchElementException;
import java.lang.reflect.InvocationTargetException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;
import br.com.stenox.training.utils.npc.nms.v1_8;
import com.comphenix.protocol.ProtocolLibrary;
import br.com.stenox.training.utils.npc.nms.ReflectionUtil;
import com.mojang.authlib.properties.Property;
import java.util.UUID;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.apache.commons.lang3.RandomStringUtils;
import br.com.stenox.training.utils.npc.enums.Action;
import org.bukkit.entity.Player;
import com.mojang.authlib.GameProfile;
import org.bukkit.Location;

public abstract class NPC
{
    private final String name;
    private final String displayName;
    private final String value;
    private final String signature;
    private Location location;
    private GameProfile gameProfile;
    private final Player receiver;
    private Action action;
    private final boolean nameVisible;
    private boolean moving;
    private boolean hidden;
    protected double cosFOV;
    
    public NPC(final String name, final String value, final String signature, final Location location, final Player receiver, final Action action, final boolean nameVisible) {
        this.cosFOV = Math.cos(Math.toRadians(60.0));
        this.name = name;
        this.displayName = "§8[NPC]" + RandomStringUtils.randomAlphanumeric(8);
        this.value = value;
        this.signature = signature;
        this.location = location;
        this.receiver = receiver;
        this.action = action;
        this.nameVisible = nameVisible;
        this.moving = false;
    }
    
    public void cancelMovement() {
        if (this.isMoving()) {
            this.moving = false;
            this.getWitch().remove();
        }
    }
    
    public void spawn() {
        try {
            final PlayerConnection playerConnection = ((CraftPlayer)this.getReceiver()).getHandle().playerConnection;
            if (playerConnection.isDisconnected()) {
                return;
            }
            this.gameProfile = new GameProfile(UUID.randomUUID(), this.getDisplayName());
            this.gameProfile.getProperties().put("textures", new Property("textures", this.getValue(), this.getSignature()));
            this.setupEntity();
            ReflectionUtil.sendPacket(this.receiver, this.getPlayerInfoPacket());
            ReflectionUtil.sendPacket(this.receiver, this.getSpawnPacket());
            ReflectionUtil.sendPacket(this.receiver, this.getHeadRotationPacket());
            ReflectionUtil.sendPacket(this.receiver, this.getEntityMetaPacket());
            if (!this.nameVisible) {
                final int batEntityId = next();
                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(this.getReceiver(), v1_8.buildSpawnBatPacket(batEntityId, this.location));
                    final PacketPlayOutAttachEntity packet = new PacketPlayOutAttachEntity();
                    setFieldValue(packet, "a", 0);
                    setFieldValue(packet, "b", batEntityId);
                    setFieldValue(packet, "c", this.getEntity().getId());
                    playerConnection.sendPacket((Packet)buildAttachPacket(batEntityId, this.getEntity().getId()));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.setHidden(false);
        }
        catch (NoSuchElementException ex) {}
    }
    
    private static PacketPlayOutAttachEntity buildAttachPacket(final int a, final int b) {
        final PacketPlayOutAttachEntity packet = new PacketPlayOutAttachEntity();
        setFieldValue(packet, "a", 0);
        setFieldValue(packet, "b", a);
        setFieldValue(packet, "c", b);
        return packet;
    }
    
    private static void setFieldValue(final Object instance, final String fieldName, final Object value) {
        try {
            final Field f = instance.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(instance, value);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void teleport(final Location location) {
        this.setLocation(location);
        ReflectionUtil.sendPacket(this.receiver, this.getTeleportPacket(location));
        ReflectionUtil.sendPacket(this.receiver, this.getHeadRotationPacket());
    }
    
    public void respawn() {
        Main.getInstance().getNpcManager().removeNPC(this);
        Main.getInstance().getNpcManager().createNPC(this.getName(), this.getValue(), this.getSignature(), this.getLocation(), this.getReceiver());
    }
    
    public void setTarget(final Location target, final Float speed, final Double distance_stop) {
        if (this.getLocation().distance(target) > 15.95) {
            System.out.println("[human] The distance between the locations can't be up to 15.0 blocks.");
            return;
        }
        if (this.isMoving()) {
            this.cancelMovement();
        }
        this.targetWitch(target, speed);
        this.moving = true;
        new BukkitRunnable() {
            public void run() {
                if (NPC.this.isMoving()) {
                    if (NPC.this.getWitch().getLocation().distance(target) <= distance_stop) {
                        NPC.this.cancelMovement();
                        this.cancel();
                    }
                    else {
                        NPC.this.setLocation(NPC.this.getWitch().getLocation());
                        ReflectionUtil.sendPacket(NPC.this.receiver, NPC.this.getHeadRotationPacket());
                        NPC.this.teleport(NPC.this.getWitch().getLocation());
                    }
                }
                else {
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 1L);
    }
    
    public static synchronized int next() {
        try {
            final Class<?> clazz = (Class<?>)MinecraftReflection.getEntityClass();
            final Field field = clazz.getDeclaredField("entityCount");
            field.setAccessible(true);
            final int id = field.getInt(null);
            field.set(null, id + 1);
            return id;
        }
        catch (Exception e) {
            return -1;
        }
    }
    
    public void setTarget(final Entity entity, final Float speed, final Double distance_stop) {
        if (this.getLocation().distance(entity.getLocation()) > 15.95) {
            System.out.println("[human] The distance between the locations can't be up to 15.0 blocks.");
            return;
        }
        if (this.isMoving()) {
            this.cancelMovement();
        }
        this.moving = true;
        this.targetWitch(entity.getLocation(), speed);
        new BukkitRunnable() {
            public void run() {
                if (NPC.this.isMoving()) {
                    if (!entity.isValid()) {
                        this.cancel();
                        this.cancel();
                        return;
                    }
                    if (NPC.this.getLocation().distance(entity.getLocation()) > 15.95) {
                        System.out.println("[human] The distance between the locations can't be up to 15.0 blocks.");
                        NPC.this.cancelMovement();
                        this.cancel();
                        return;
                    }
                    if (NPC.this.getWitch().getLocation().distance(entity.getLocation()) <= distance_stop) {
                        final Vector dirBetweenLocations = entity.getLocation().toVector().subtract(NPC.this.getLocation().toVector());
                        final Location target = NPC.this.getWitch().getLocation().clone();
                        target.setDirection(dirBetweenLocations);
                        NPC.this.setLocation(target);
                        NPC.this.teleport(target);
                        NPC.this.removePathFinders((Entity)NPC.this.getWitch());
                    }
                    else {
                        final Vector dirBetweenLocations = entity.getLocation().toVector().subtract(NPC.this.getLocation().toVector());
                        final Location target = NPC.this.getWitch().getLocation().clone();
                        target.setDirection(dirBetweenLocations);
                        NPC.this.setLocation(target);
                        NPC.this.teleport(target);
                        NPC.this.changeTarget((Entity)NPC.this.getWitch(), entity.getLocation(), speed);
                    }
                }
                else {
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)Main.getInstance(), 0L, 1L);
    }
    
    public void playAnimation(final Animation animation) {
        ReflectionUtil.sendPacket(this.receiver, this.getAnimationPacket(animation));
    }
    
    public void playStatus(final Status status) {
        ReflectionUtil.sendPacket(this.receiver, this.getStatusPacket(status));
    }
    
    public void despawn() {
        if (this.getAction() == Action.ADD_PLAYER) {
            this.setAction(Action.REMOVE_PLAYER);
        }
        ReflectionUtil.sendPacket(this.receiver, this.getDestroyPacket());
        this.setHidden(true);
    }
    
    public void setAction(final Action action) {
        this.action = action;
        ReflectionUtil.sendPacket(this.receiver, this.getPlayerInfoPacket());
    }
    
    public void setEquipment(final EquipmentSlot action, final ItemStack itemStack) {
        ReflectionUtil.sendPacket(this.receiver, this.getEquipPacket(itemStack, action));
    }
    
    private void setLocation(final Location location) {
        this.location = location;
    }
    
    public GameProfile getGameProfile() {
        return this.gameProfile;
    }
    
    @Deprecated
    public abstract void setupEntity();
    
    @Deprecated
    public abstract void targetWitch(final Location p0, final Float p1);
    
    @Deprecated
    public abstract void changeTarget(final Entity p0, final Location p1, final Float p2);
    
    @Deprecated
    public abstract void removePathFinders(final Entity p0);
    
    @Deprecated
    public abstract Witch getWitch();
    
    @Deprecated
    public abstract Object getPlayerInfoPacket();
    
    @Deprecated
    public abstract Object getSpawnPacket();
    
    @Deprecated
    public abstract Object getDestroyPacket();
    
    @Deprecated
    public abstract Object getHeadRotationPacket();
    
    public abstract EntityPlayer getEntity();
    
    @Deprecated
    public abstract Object getTeleportPacket(final Location p0);
    
    @Deprecated
    public abstract Object getAnimationPacket(final Animation p0);
    
    @Deprecated
    public abstract Object getStatusPacket(final Status p0);
    
    @Deprecated
    public abstract Object getHideNamePacket();
    
    @Deprecated
    public abstract Object getEntityMetaPacket();
    
    @Deprecated
    public abstract Object getEquipPacket(final ItemStack p0, final EquipmentSlot p1);
    
    @Deprecated
    public abstract int getID();
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public String getSignature() {
        return this.signature;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public Player getReceiver() {
        return this.receiver;
    }
    
    public Action getAction() {
        return this.action;
    }
    
    public boolean isNameVisible() {
        return this.nameVisible;
    }
    
    public boolean isMoving() {
        return this.moving;
    }
    
    public double getCosFOV() {
        return this.cosFOV;
    }
}
