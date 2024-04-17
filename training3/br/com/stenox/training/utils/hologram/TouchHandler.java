// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.hologram;

import org.bukkit.entity.Player;

public interface TouchHandler
{
    void onTouch(final Hologram p0, final Player p1, final TouchType p2);
    
    public enum TouchType
    {
        LEFT, 
        RIGHT;
    }
}
