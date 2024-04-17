// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.event;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Event;

public class CustomEvent extends Event
{
    private static final HandlerList handlers;
    
    public HandlerList getHandlers() {
        return CustomEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return CustomEvent.handlers;
    }
    
    public void call() {
        Bukkit.getPluginManager().callEvent((Event)this);
    }
    
    static {
        handlers = new HandlerList();
    }
}
