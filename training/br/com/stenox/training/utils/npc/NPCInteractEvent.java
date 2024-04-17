// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.npc;

import org.bukkit.event.HandlerList;
import br.com.stenox.training.utils.npc.enums.EventAction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class NPCInteractEvent extends Event
{
    private final Player player;
    private final NPC npc;
    private final EventAction action;
    private static final HandlerList handlers;
    
    public NPCInteractEvent(final Player player, final NPC npc, final EventAction action) {
        this.player = player;
        this.npc = npc;
        this.action = action;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public NPC getNPC() {
        return this.npc;
    }
    
    public EventAction getAction() {
        return this.action;
    }
    
    public HandlerList getHandlers() {
        return NPCInteractEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return NPCInteractEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
