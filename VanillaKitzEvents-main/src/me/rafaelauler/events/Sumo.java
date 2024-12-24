package me.rafaelauler.events;



import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;


public class Sumo extends WarpHandle {
	
	@Override
	public void execute(Player player) {
		super.execute(player);
		player.getInventory().clear();

        player.sendMessage("§aVocê agora está no evento Sumo!");
        player.sendMessage("§aSiga as instruções que serão dadas no chat!");
	}
	
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)
                || !(event.getDamager() instanceof Player)) {
            return;
        }

        Player damager = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();


        if (WaveWarp.SUMO.hasPlayer(damager.getName()) && WaveWarp.SUMO.hasPlayer(victim.getName())) {
            event.setDamage(0);
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);

        if (!WaveWarp.SUMO.hasPlayer(player.getName())) {
            return;
        }

        if (!block.getType().toString().contains("WATER")) {
            return;
        }
        if (event.getPlayer().getLastDamageCause().getDamageSource().getCausingEntity() != null) {
        player.damage(player.getHealth(), event.getPlayer().getLastDamageCause().getDamageSource().getCausingEntity());
        }
player.sendMessage(ChatColor.RED + "Você perdeu o evento sumo!");
WaveWarp.SPAWN.send(player);
player.spigot().respawn();
            }
    
}