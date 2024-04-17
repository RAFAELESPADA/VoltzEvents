// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.kit.manager;

import java.util.Iterator;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import br.com.stenox.training.utils.ClassGetter;
import br.com.stenox.training.Main;
import java.util.ArrayList;
import br.com.stenox.training.kit.Kit;
import java.util.List;

public class KitManager
{
    private final List<Kit> kits;
    
    public KitManager() {
        this.kits = new ArrayList<Kit>();
    }
    
    public void loadKits() {
        for (final Class<?> c : ClassGetter.getClassesForPackage(Main.getInstance(), "br.com.stenox.training.kit.list")) {
            if (Kit.class.isAssignableFrom(c) && c != Kit.class) {
                try {
                    final Kit kit = (Kit)c.getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
                    Bukkit.getPluginManager().registerEvents((Listener)kit, (Plugin)Main.getInstance());
                    this.kits.add(kit);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public Kit searchKit(final String name) {
        return this.kits.stream().filter(k -> k.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
    
    public List<Kit> getKits() {
        return this.kits;
    }
}
