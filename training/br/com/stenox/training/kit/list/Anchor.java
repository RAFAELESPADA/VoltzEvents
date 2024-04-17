// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.kit.list;

import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;
import br.com.stenox.training.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.util.Vector;
import org.bukkit.event.player.PlayerVelocityEvent;
import java.util.UUID;
import java.util.ArrayList;
import br.com.stenox.training.kit.Kit;

public class Anchor extends Kit
{
    private ArrayList<UUID> anchoring;
    
    public Anchor() {
        this.anchoring = new ArrayList<UUID>();
        this.setName("Anchor");
    }
    
    @EventHandler
    public void onVelocity(final PlayerVelocityEvent event) {
        if (this.anchoring.contains(event.getPlayer().getUniqueId())) {
            event.setVelocity(new Vector());
            this.anchoring.remove(event.getPlayer().getUniqueId());
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onAnchor(final EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }
        final Player player = (Player)event.getEntity();
        final Player damager = (Player)event.getDamager();
        if (this.hasKit(player.getName()) || this.hasKit(damager.getName())) {
            player.setVelocity(new Vector());
            this.anchoring.add(player.getUniqueId());
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)Main.getInstance(), () -> player.setVelocity(new Vector()));
        }
    }
}
