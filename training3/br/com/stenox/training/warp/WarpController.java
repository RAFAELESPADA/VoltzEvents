// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.warp;

import java.util.Iterator;
import org.bukkit.plugin.java.JavaPlugin;
import br.com.stenox.training.utils.ClassGetter;
import br.com.stenox.training.Main;
import java.util.HashSet;
import java.util.Set;

public class WarpController
{
    private final Set<Warp> warps;
    
    public WarpController() {
        this.warps = new HashSet<Warp>();
    }
    
    public void create(final Warp model) {
        this.warps.add(model);
    }
    
    public void remove(final String name) {
        this.warps.remove(this.search(name));
    }
    
    public Warp search(final String name) {
        return this.warps.stream().filter(warp -> warp.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
    
    public void loadWarps() {
        for (final Class<?> c : ClassGetter.getClassesForPackage(Main.getInstance(), "br.com.stenox.training.warp.list")) {
            if (Warp.class.isAssignableFrom(c) && c != Warp.class) {
                try {
                    final Warp warp = (Warp)c.getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
                    this.create(warp);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
