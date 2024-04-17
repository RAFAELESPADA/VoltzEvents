// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.provider;

import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.Server;
import br.com.stenox.training.Main;

public abstract class Provider
{
    private Main plugin;
    
    public Provider(final Main plugin) {
        this.plugin = plugin;
    }
    
    public abstract void onEnable();
    
    public abstract void onDisable();
    
    public Main getPlugin() {
        return this.plugin;
    }
    
    public Server getServer() {
        return this.plugin.getServer();
    }
    
    public void registerListener(final Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, (Plugin)this.plugin);
    }
}
