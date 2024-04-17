// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.npc.nms;

import org.bukkit.World;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import org.bukkit.entity.Entity;
import java.lang.reflect.InvocationTargetException;
import br.com.stenox.training.utils.npc.enums.Action;
import br.com.stenox.training.utils.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.Bukkit;

public class ReflectionUtil
{
    public static String version;
    public static Class<?> npc_class;
    
    @Deprecated
    public static void init() {
        final String name = Bukkit.getServer().getClass().getPackage().getName();
        final String mcVersion = name.substring(name.lastIndexOf(46) + 1);
        final String[] versions = mcVersion.split("_");
        if (versions[0].equals("v1")) {
            final int minor = Integer.parseInt(versions[1]);
            if (minor == 8) {
                ReflectionUtil.npc_class = v1_8.class;
            }
        }
        ReflectionUtil.version = mcVersion + ".";
    }
    
    @Deprecated
    public static NPC newNPC(final String name, final String value, final String signature, final Location location, final Player receiver, final Boolean name_visible) {
        NPC npc = null;
        try {
            npc = (NPC)ReflectionUtil.npc_class.getConstructor(String.class, String.class, String.class, Location.class, Player.class, Action.class, Boolean.class).newInstance(name, value, signature, location, receiver, Action.ADD_PLAYER, name_visible);
        }
        catch (IllegalArgumentException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
        return npc;
    }
    
    @Deprecated
    public static void sendPacket(final Player p, final Object packet) {
        if (p == null) {
            return;
        }
        try {
            final Object nmsPlayer = getHandle((Entity)p);
            final Field con_field = nmsPlayer.getClass().getField("playerConnection");
            final Object con = con_field.get(nmsPlayer);
            final Method packet_method = getMethod(con.getClass(), "sendPacket");
            packet_method.invoke(con, packet);
        }
        catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchFieldException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    @Deprecated
    public static Class<?> getCraftClass(final String ClassName) {
        final String className = "net.minecraft.server." + ReflectionUtil.version + ClassName;
        Class<?> c = null;
        try {
            c = Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
    }
    
    @Deprecated
    public static Class<?> getBukkitClass(final String ClassName) {
        final String className = "org.bukkit.craftbukkit." + ReflectionUtil.version + ClassName;
        Class<?> c = null;
        try {
            c = Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
    }
    
    @Deprecated
    public static Field getField(final Class<?> clazz, final String fieldName) throws Exception {
        final Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }
    
    @Deprecated
    public static Object getHandle(final Entity entity) {
        Object nms_entity = null;
        final Method entity_getHandle = getMethod(entity.getClass(), "getHandle");
        try {
            nms_entity = entity_getHandle.invoke(entity, new Object[0]);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
        return nms_entity;
    }
    
    public static Object getHandle(final World world) {
        Object nms_entity = null;
        final Method entity_getHandle = getMethod(world.getClass(), "getHandle");
        try {
            nms_entity = entity_getHandle.invoke(world, new Object[0]);
        }
        catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
        return nms_entity;
    }
    
    @Deprecated
    public static Method getMethod(final Class<?> cl, final String method, final Class<?>[] args) {
        for (final Method m : cl.getMethods()) {
            if (m.getName().equals(method) && ClassListEqual(args, m.getParameterTypes())) {
                return m;
            }
        }
        return null;
    }
    
    @Deprecated
    public static Method getMethod(final Class<?> cl, final String method) {
        for (final Method m : cl.getMethods()) {
            if (m.getName().equals(method)) {
                return m;
            }
        }
        return null;
    }
    
    @Deprecated
    public static boolean ClassListEqual(final Class<?>[] l1, final Class<?>[] l2) {
        boolean equal = true;
        if (l1.length != l2.length) {
            return false;
        }
        for (int i = 0; i < l1.length; ++i) {
            if (l1[i] != l2[i]) {
                equal = false;
                break;
            }
        }
        return equal;
    }
    
    public static void setFieldValue(final Object instance, final String fieldName, final Object value) {
        try {
            final Field f = instance.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(instance, value);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Deprecated
    public static Object getFieldValue(final Object instance, final String fieldName) throws Exception {
        final Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(instance);
    }
    
    @Deprecated
    public static Object getFieldValue(final Class<?> instance, final String fieldName) throws Exception {
        final Field field = instance.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(instance);
    }
    
    @Deprecated
    public static <T> T getFieldValue(final Field field, final Object obj) {
        try {
            return (T)field.get(obj);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    static {
        ReflectionUtil.npc_class = v1_8.class;
    }
}
