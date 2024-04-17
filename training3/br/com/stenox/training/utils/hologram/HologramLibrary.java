// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.hologram;

import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.server.v1_8_R3.EntityTypes;
import java.util.ArrayList;
import java.util.Collection;
import com.google.common.collect.ImmutableList;
import org.bukkit.Location;
import java.util.List;

public class HologramLibrary
{
    private static final List<Hologram> holograms;
    
    public static Hologram createHologram(final Location location, final String... lines) {
        final Hologram hologram = new Hologram(location, lines);
        HologramLibrary.holograms.add(hologram);
        return hologram;
    }
    
    public static void removeHologram(final Hologram hologram) {
        HologramLibrary.holograms.remove(hologram);
        hologram.despawn();
    }
    
    public static List<Hologram> listHolograms() {
        return (List<Hologram>)ImmutableList.copyOf((Collection)HologramLibrary.holograms);
    }
    
    static {
        holograms = new ArrayList<Hologram>();
        try {
            final Field classToString = EntityTypes.class.getDeclaredField("d");
            classToString.setAccessible(true);
            Map map = (Map)classToString.get(null);
            map.put(EntityHologramStand.class, "ArmorStand");
            final Field classToId = EntityTypes.class.getDeclaredField("f");
            classToId.setAccessible(true);
            map = (Map)classToId.get(null);
            map.put(EntityHologramStand.class, 30);
        }
        catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }
}
