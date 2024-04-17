// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.protocol;

import java.lang.reflect.Method;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

public class ProtocolGetter
{
    private static boolean viaVersion;
    private static boolean protocolSupport;
    private static boolean protocolHack;
    private static ProtocolVersion nativeVersion;
    
    public static void foundDependencies() {
        final String version = Bukkit.getServer().getClass().getPackage().getName().substring(23);
        try {
            Class.forName("protocolsupport.api.ProtocolSupportAPI");
            ProtocolGetter.protocolSupport = true;
        }
        catch (ClassNotFoundException e) {
            if (version.equals("v1_7_R4")) {
                try {
                    Class.forName("org.spigotmc.ProtocolInjector");
                    ProtocolGetter.protocolHack = true;
                }
                catch (ClassNotFoundException ex) {}
            }
        }
        try {
            Class.forName("us.myles.ViaVersion.api.Via");
            ProtocolGetter.viaVersion = true;
        }
        catch (ClassNotFoundException ex2) {}
        if (version.startsWith("v1_7_R")) {
            ProtocolGetter.nativeVersion = ((version.endsWith("R1") || version.endsWith("R2")) ? ProtocolVersion.MINECRAFT_1_7_5 : ProtocolVersion.MINECRAFT_1_7_10);
        }
        else if (version.startsWith("v1_8_R")) {
            ProtocolGetter.nativeVersion = ProtocolVersion.MINECRAFT_1_8;
        }
        else if (version.startsWith("v1_9_R")) {
            ProtocolGetter.nativeVersion = ProtocolVersion.MINECRAFT_1_9;
        }
        else if (version.startsWith("v1_10_R")) {
            ProtocolGetter.nativeVersion = ProtocolVersion.MINECRAFT_1_10;
        }
        else if (version.startsWith("v1_11_R")) {
            ProtocolGetter.nativeVersion = ProtocolVersion.MINECRAFT_1_11;
        }
    }
    
    public static ProtocolVersion getVersion(final Player player) {
        try {
            if (ProtocolGetter.viaVersion) {
                final Class<?> clazz = Class.forName("us.myles.ViaVersion.api.Via");
                final Object object = clazz.getDeclaredMethod("getAPI", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
                final Method method = object.getClass().getMethod("getPlayerVersion", UUID.class);
                return ProtocolVersion.getById((int)method.invoke(object, player.getUniqueId()));
            }
            if (ProtocolGetter.protocolSupport) {
                final Class<?> clazz = Class.forName("protocolsupport.api.ProtocolSupportAPI");
                final Method method2 = clazz.getDeclaredMethod("getProtocolVersion", Player.class);
                return ProtocolVersion.valueOf((String)method2.invoke(null, player));
            }
            if (ProtocolGetter.protocolHack) {
                final Object handle = player.getClass().getMethod("getHandle", (Class<?>[])new Class[0]).invoke(player, new Object[0]);
                final Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
                final Object networkManager = playerConnection.getClass().getField("networkManager").get(playerConnection);
                return ProtocolVersion.getById((int)networkManager.getClass().getMethod("getVersion", (Class<?>[])new Class[0]).invoke(networkManager, new Object[0]));
            }
            return ProtocolGetter.nativeVersion;
        }
        catch (Exception ex) {
            return ProtocolVersion.UNKNOWN;
        }
    }
    
    public static boolean isViaVersion() {
        return ProtocolGetter.viaVersion;
    }
    
    public static boolean isProtocolSupport() {
        return ProtocolGetter.protocolSupport;
    }
    
    public static boolean isProtocolHack() {
        return ProtocolGetter.protocolHack;
    }
    
    static {
        ProtocolGetter.nativeVersion = ProtocolVersion.UNKNOWN;
    }
}
