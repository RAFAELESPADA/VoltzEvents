// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.kit.list;

import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Player;
import java.util.UUID;
import java.util.HashMap;
import br.com.stenox.training.kit.Kit;

public class Ninja extends Kit
{
    public static HashMap<UUID, Player> target;
    
    public Ninja() {
        this.setName("Ninja");
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getEntity() instanceof Player && event.getDamager() instanceof Player && this.hasKit(event.getDamager().getName()) && this.hasKit(event.getDamager().getName())) {
            Ninja.target.put(event.getDamager().getUniqueId(), (Player)event.getEntity());
        }
    }
    
    @EventHandler
    public void onPlayerToggleSneakEvent(final PlayerToggleSneakEvent event) {
        if (event.isSneaking() && Ninja.target.containsKey(event.getPlayer().getUniqueId())) {
            if (!this.hasKit(event.getPlayer().getName())) {
                return;
            }
            if (this.inCooldown(event.getPlayer())) {
                this.sendCooldown(event.getPlayer());
                return;
            }
            if (this.inCombatCooldown(event.getPlayer())) {
                this.sendCombatCooldown(event.getPlayer());
                return;
            }
            final Player targetPlayer = Ninja.target.get(event.getPlayer().getUniqueId());
            if (targetPlayer == null) {
                event.getPlayer().sendMessage("§cO jogador n\u00e3o est\u00e1 online.");
                Ninja.target.remove(event.getPlayer().getUniqueId());
                return;
            }
            if (!targetPlayer.getWorld().equals(event.getPlayer().getWorld())) {
                return;
            }
            if (targetPlayer.isOnline() && targetPlayer.getLocation().distance(event.getPlayer().getLocation()) > 65.0) {
                event.getPlayer().sendMessage("§cO jogador est\u00e1 muito distante de voc\u00ea.");
            }
            else {
                event.getPlayer().teleport(targetPlayer.getLocation());
                Ninja.target.remove(event.getPlayer().getUniqueId());
                event.getPlayer().sendMessage("§aTeleportado!");
                this.addCooldown(event.getPlayer(), 5.0);
            }
        }
    }
    
    @EventHandler
    public void onEntityDamageByEntity2(final EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            final Player player = (Player)event.getEntity();
            if (this.hasKit(player.getName())) {
                if (this.inCombatCooldown(player)) {
                    this.removeCombatCooldown(player);
                }
                this.addCombatCooldown(player, 1.0);
            }
        }
    }
    
    static {
        Ninja.target = new HashMap<UUID, Player>();
    }
}
