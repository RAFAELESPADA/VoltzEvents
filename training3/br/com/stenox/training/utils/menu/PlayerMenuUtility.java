// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.menu;

import org.bukkit.entity.Player;

public class PlayerMenuUtility
{
    private Player owner;
    private Player playerToKill;
    
    public PlayerMenuUtility(final Player p) {
        this.owner = p;
    }
    
    public Player getOwner() {
        return this.owner;
    }
    
    public Player getPlayerToKill() {
        return this.playerToKill;
    }
    
    public void setPlayerToKill(final Player playerToKill) {
        this.playerToKill = playerToKill;
    }
}
