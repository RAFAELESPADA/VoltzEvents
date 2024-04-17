// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.npc.nms;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.events.PacketContainer;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import br.com.stenox.training.utils.npc.enums.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import java.util.Collection;
import br.com.stenox.training.utils.npc.enums.Status;
import br.com.stenox.training.utils.npc.enums.Animation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Array;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import java.lang.reflect.Field;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.EntityWitch;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.Bukkit;
import br.com.stenox.training.utils.npc.enums.Action;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.entity.Witch;
import br.com.stenox.training.utils.npc.NPC;

public class v1_8 extends NPC
{
    private Object entity_player;
    private Witch entity_witch;
    private int id;
    
    public v1_8(final String name, final String value, final String signature, final Location location, final Player receiver, final Action action, final Boolean name_visible) {
        super(name, value, signature, location, receiver, action, name_visible);
    }
    
    @Override
    public void setupEntity() {
        final Class<?> EntityPlayer = ReflectionUtil.getCraftClass("EntityPlayer");
        Object MinecraftServer = null;
        Object WorldServer = null;
        Object InteractManager = null;
        try {
            MinecraftServer = ReflectionUtil.getMethod(ReflectionUtil.getBukkitClass("CraftServer"), "getServer").invoke(Bukkit.getServer(), new Object[0]);
            WorldServer = ReflectionUtil.getBukkitClass("CraftWorld").getMethod("getHandle", (Class<?>[])new Class[0]).invoke(this.getLocation().getWorld(), new Object[0]);
            InteractManager = ReflectionUtil.getCraftClass("PlayerInteractManager").getDeclaredConstructors()[0].newInstance(WorldServer);
            this.entity_player = EntityPlayer.getConstructors()[0].newInstance(MinecraftServer, WorldServer, this.getGameProfile(), InteractManager);
            this.entity_player.getClass().getMethod("setLocation", Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE).invoke(this.entity_player, this.getLocation().getX(), this.getLocation().getY(), this.getLocation().getZ(), this.getLocation().getYaw(), this.getLocation().getPitch());
            final Method getId = ReflectionUtil.getMethod(EntityPlayer, "getId", new Class[0]);
            this.id = (int)getId.invoke(this.entity_player, new Object[0]);
        }
        catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException ex2) {
            final ReflectiveOperationException ex;
            final ReflectiveOperationException e = ex;
            e.printStackTrace();
        }
    }
    
    @Override
    public void targetWitch(final Location location, final Float speed) {
        final EntityWitch witch = new EntityWitch((World)((CraftWorld)location.getWorld()).getHandle());
        this.overrideBehavior((EntityCreature)witch, location, speed);
        witch.setLocation(this.getLocation().getX(), this.getLocation().getY(), this.getLocation().getZ(), this.getLocation().getYaw(), this.getLocation().getPitch());
        witch.setInvisible(true);
        ((CraftWorld)location.getWorld()).getHandle().addEntity((Entity)witch);
        (this.entity_witch = (Witch)witch.getBukkitEntity()).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000000, 1, false, false));
        this.entity_witch.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1000000000, 3, false, false));
        this.entity_witch.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000000, 3, false, false));
        this.entity_witch.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000000, 100, false, false));
        this.entity_witch.setNoDamageTicks(1000000000);
    }
    
    @Override
    public Witch getWitch() {
        return this.entity_witch;
    }
    
    @Override
    public void removePathFinders(final org.bukkit.entity.Entity entity) {
        final EntityCreature c = (EntityCreature)((CraftEntity)entity).getHandle();
        try {
            final Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            final Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            bField.set(c.goalSelector, new UnsafeList());
            bField.set(c.targetSelector, new UnsafeList());
            cField.set(c.goalSelector, new UnsafeList());
            cField.set(c.targetSelector, new UnsafeList());
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    
    private void overrideBehavior(final EntityCreature c, final Location location, final Float speed) {
        try {
            final Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            final Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            bField.set(c.goalSelector, new UnsafeList());
            bField.set(c.targetSelector, new UnsafeList());
            cField.set(c.goalSelector, new UnsafeList());
            cField.set(c.targetSelector, new UnsafeList());
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        c.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue((double)speed);
        c.targetSelector.a(3, (PathfinderGoal)new v1_8Path(c, speed, location.getX(), location.getY(), location.getZ()));
    }
    
    @Override
    public void changeTarget(final org.bukkit.entity.Entity entity, final Location location, final Float speed) {
        final EntityCreature c = (EntityCreature)((CraftEntity)entity).getHandle();
        try {
            final Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
            bField.setAccessible(true);
            final Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
            cField.setAccessible(true);
            bField.set(c.goalSelector, new UnsafeList());
            bField.set(c.targetSelector, new UnsafeList());
            cField.set(c.goalSelector, new UnsafeList());
            cField.set(c.targetSelector, new UnsafeList());
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        c.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue((double)speed);
        c.targetSelector.a(3, (PathfinderGoal)new v1_8Path(c, speed, location.getX(), location.getY(), location.getZ()));
    }
    
    @Override
    public Object getPlayerInfoPacket() {
        Object packet = null;
        try {
            final Object PlayerEnum = ReflectionUtil.getCraftClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction").getField(this.getAction().name()).get(null);
            final Constructor<?> PacketPlayOutPlayerInfoConstructor = ReflectionUtil.getCraftClass("PacketPlayOutPlayerInfo").getConstructor(ReflectionUtil.getCraftClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction"), Class.forName("[Lnet.minecraft.server." + ReflectionUtil.version + "EntityPlayer;"));
            final Object array = Array.newInstance(ReflectionUtil.getCraftClass("EntityPlayer"), 1);
            Array.set(array, 0, this.entity_player);
            packet = PacketPlayOutPlayerInfoConstructor.newInstance(PlayerEnum, array);
        }
        catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | ClassNotFoundException | InvocationTargetException | InstantiationException ex2) {
            final ReflectiveOperationException ex;
            final ReflectiveOperationException e = ex;
            e.printStackTrace();
        }
        return packet;
    }
    
    @Override
    public Object getSpawnPacket() {
        Object packet = null;
        try {
            final Constructor<?> PacketPlayOutNamedEntitySpawnConstructor = ReflectionUtil.getCraftClass("PacketPlayOutNamedEntitySpawn").getConstructor(ReflectionUtil.getCraftClass("EntityHuman"));
            packet = PacketPlayOutNamedEntitySpawnConstructor.newInstance(this.entity_player);
        }
        catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
        return packet;
    }
    
    @Override
    public Object getHeadRotationPacket() {
        Object packet = null;
        try {
            final Constructor<?> PacketPlayOutEntityHeadRotationConstructor = ReflectionUtil.getCraftClass("PacketPlayOutEntityHeadRotation").getConstructor(ReflectionUtil.getCraftClass("Entity"), Byte.TYPE);
            final float yaw = this.getLocation().getYaw();
            packet = PacketPlayOutEntityHeadRotationConstructor.newInstance(this.entity_player, (byte)(yaw * 256.0f / 360.0f));
        }
        catch (SecurityException | InvocationTargetException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
        return packet;
    }
    
    @Override
    public Object getDestroyPacket() {
        Object packet = null;
        try {
            final Constructor<?> PacketPlayOutEntityDestroyConstructor = ReflectionUtil.getCraftClass("PacketPlayOutEntityDestroy").getConstructor(int[].class);
            packet = PacketPlayOutEntityDestroyConstructor.newInstance(new int[] { this.getID() });
        }
        catch (SecurityException | NoSuchMethodException | InvocationTargetException | IllegalArgumentException | IllegalAccessException | InstantiationException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
        return packet;
    }
    
    @Override
    public Object getTeleportPacket(final Location loc) {
        final Class<?> PacketPlayOutEntityTeleport = ReflectionUtil.getCraftClass("PacketPlayOutEntityTeleport");
        Object packet = null;
        try {
            packet = PacketPlayOutEntityTeleport.getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Byte.TYPE, Byte.TYPE, Boolean.TYPE).newInstance(this.id, this.getFixLocation(loc.getX()), this.getFixLocation(loc.getY()), this.getFixLocation(loc.getZ()), (byte)((int)loc.getYaw() * 256 / 360), (byte)((int)loc.getPitch() * 256 / 360), false);
        }
        catch (IllegalArgumentException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
        return packet;
    }
    
    @Override
    public Object getAnimationPacket(final Animation animation) {
        final Class<?> Entity = ReflectionUtil.getCraftClass("Entity");
        final Class<?> PacketPlayOutAnimation = ReflectionUtil.getCraftClass("PacketPlayOutAnimation");
        Object packet = null;
        try {
            packet = PacketPlayOutAnimation.getConstructor(Entity, Integer.TYPE).newInstance(this.entity_player, animation.getValue());
        }
        catch (IllegalArgumentException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
        return packet;
    }
    
    @Override
    public Object getStatusPacket(final Status status) {
        final Class<?> Entity = ReflectionUtil.getCraftClass("Entity");
        final Class<?> PacketPlayOutAnimation = ReflectionUtil.getCraftClass("PacketPlayOutEntityStatus");
        Object packet = null;
        try {
            packet = PacketPlayOutAnimation.getConstructor(Entity, Byte.TYPE).newInstance(this.entity_player, status.getValue());
        }
        catch (IllegalArgumentException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
        return packet;
    }
    
    @Override
    public Object getHideNamePacket() {
        final Class<?> PacketPlayOutScoreboardTeam = ReflectionUtil.getCraftClass("PacketPlayOutScoreboardTeam");
        Object packet = null;
        try {
            packet = PacketPlayOutScoreboardTeam.newInstance();
            final Field a = ReflectionUtil.getField(packet.getClass(), "a");
            a.setAccessible(true);
            final Field b = ReflectionUtil.getField(packet.getClass(), "b");
            b.setAccessible(true);
            final Field c = ReflectionUtil.getField(packet.getClass(), "c");
            c.setAccessible(true);
            final Field d = ReflectionUtil.getField(packet.getClass(), "d");
            d.setAccessible(true);
            final Field e = ReflectionUtil.getField(packet.getClass(), "e");
            e.setAccessible(true);
            final Field f = ReflectionUtil.getField(packet.getClass(), "f");
            f.setAccessible(true);
            final Field g = ReflectionUtil.getField(packet.getClass(), "g");
            g.setAccessible(true);
            final Field h = ReflectionUtil.getField(packet.getClass(), "h");
            h.setAccessible(true);
            final Field i = ReflectionUtil.getField(packet.getClass(), "i");
            i.setAccessible(true);
            a.set(packet, this.getGameProfile().getName() + this.getID());
            b.set(packet, this.getGameProfile().getName() + this.getID());
            e.set(packet, "never");
            h.set(packet, 0);
            i.set(packet, 1);
            ((Collection)g.get(packet)).add(this.getGameProfile().getName());
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        return packet;
    }
    
    @Override
    public Object getEquipPacket(final ItemStack itemStack, final EquipmentSlot action) {
        final Class<?> PacketPlayOutEntityEquipment = ReflectionUtil.getCraftClass("PacketPlayOutEntityEquipment");
        final Class<?> ItemStack = ReflectionUtil.getCraftClass("ItemStack");
        Object packet = null;
        Object item = null;
        try {
            item = ReflectionUtil.getBukkitClass("inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(item, itemStack);
            packet = PacketPlayOutEntityEquipment.getConstructor(Integer.TYPE, Integer.TYPE, ItemStack).newInstance(this.getID(), action.getValue(), item);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return packet;
    }
    
    @Override
    public Object getEntityMetaPacket() {
        final Class<?> PacketPlayOutEntityMetadata = ReflectionUtil.getCraftClass("PacketPlayOutEntityMetadata");
        final Class<?> DataWatcher = ReflectionUtil.getCraftClass("DataWatcher");
        Object packet = null;
        try {
            packet = PacketPlayOutEntityMetadata.getConstructor(Integer.TYPE, DataWatcher, Boolean.TYPE).newInstance(this.getID(), this.getWatcher(), true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return packet;
    }
    
    public Object getWatcher() {
        final Class<?> Entity = ReflectionUtil.getCraftClass("Entity");
        final Class<?> DataWatcher = ReflectionUtil.getCraftClass("DataWatcher");
        Object watcher = null;
        try {
            watcher = DataWatcher.getConstructor(Entity).newInstance(this.entity_player);
            final Method a = ReflectionUtil.getMethod(DataWatcher, "a", new Class[] { Integer.TYPE, Object.class });
            a.invoke(watcher, 6, 20.0f);
            a.invoke(watcher, 10, 127);
        }
        catch (IllegalArgumentException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException | SecurityException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
        return watcher;
    }
    
    @Override
    public int getID() {
        return this.id;
    }
    
    @Override
    public EntityPlayer getEntity() {
        return (EntityPlayer)this.entity_player;
    }
    
    private int getFixLocation(final double pos) {
        return MathHelper.floor(pos * 32.0);
    }
    
    public static PacketContainer buildSpawnBatPacket(final int entityId, final Location loc) {
        final WrappedDataWatcher watcher = new WrappedDataWatcher();
        watcher.setObject(0, (Object)32);
        final PacketContainer packet = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
        packet.getIntegers().write(0, (Object)entityId);
        packet.getIntegers().write(1, (Object)65);
        packet.getIntegers().write(2, (Object)floor(loc.getX() * 32.0));
        packet.getIntegers().write(3, (Object)floor(loc.getY() * 32.0));
        packet.getIntegers().write(4, (Object)floor(loc.getZ() * 32.0));
        packet.getDataWatcherModifier().write(0, (Object)watcher);
        return packet;
    }
    
    public static PacketContainer buildSpawnSlimePacket(final int entityId, final Location loc) {
        final WrappedDataWatcher watcher = new WrappedDataWatcher();
        watcher.setObject(0, (Object)32);
        final PacketContainer packet = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
        packet.getIntegers().write(0, (Object)entityId);
        packet.getIntegers().write(1, (Object)55);
        packet.getIntegers().write(2, (Object)floor(loc.getX() * 32.0));
        packet.getIntegers().write(3, (Object)floor(loc.getY() * 32.0));
        packet.getIntegers().write(4, (Object)floor(loc.getZ() * 32.0));
        packet.getDataWatcherModifier().write(0, (Object)watcher);
        return packet;
    }
    
    private static int floor(final double var0) {
        final int var = (int)var0;
        return (var0 < var) ? (var - 1) : var;
    }
}
