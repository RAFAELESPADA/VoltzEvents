package me.rafaelauler.events;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import net.wavemc.core.util.WaveCooldown;





public class ArenaNinja extends WarpHandle {
	public static final HashMap<String, String> map = new HashMap<>();


	
	@Override
	public void execute(Player player) {
		super.execute(player);
		player.getInventory().clear();
		EventoComando e =  new EventoComando();
		e.Gladiator(player);

        player.sendMessage("§aVocê agora está no evento Arena Ninja!");
        player.sendMessage("§aSiga as instruções que serão dadas no chat!");
	}


@EventHandler(ignoreCancelled = true)
public void onDamage(EntityDamageByEntityEvent event) {
	if (!(event.getEntity() instanceof Player) 
			|| (!(event.getDamager() instanceof Player))) {
		return;
	}
	Player victim = (Player) event.getEntity();
	Player damager = (Player) event.getDamager();
	if (event.isCancelled()) {
		return;
	}
	if (WaveWarp.ARENANINJA.hasPlayer(damager.getName()) 
			&& WaveWarp.ARENANINJA.hasPlayer(victim.getName())) {

 
	
		map.put(damager.getName(), victim.getName());
	}
}

@EventHandler
public void onSneaking(PlayerToggleSneakEvent event) {
	if (event.isSneaking()) return;
	Player player = event.getPlayer();
	Player mage = event.getPlayer();
	
	if (WaveWarp.ARENANINJA.hasPlayer(player.getName())
 
			&& map.containsKey(player.getName())) {
		
		
		String targetName = map.get(player.getName());
		Player targetPlayer;
		if (WaveCooldown.has(player.getName(), "ninja")) {
			  player.sendMessage(ChatColor.RED + "Aguarde " + WaveCooldown.getTime(player.getName(), "ninja") +  " segundos para usar o ninja novamente!");
			  return;
		  }
		if ((targetPlayer = Bukkit.getPlayer(targetName)) != null) {
			if (player.getLocation().distance(targetPlayer.getLocation()) >= 50) {
				player.sendMessage("§cJogador está muito longe (50+ blocos).");
				return;
			}
			if (!WaveWarp.ARENANINJA.hasPlayer(player.getName())) {
				player.sendMessage("§cO Jogador não está mais nesse evento.");
				return;
			}

			WaveCooldown.create(player.getName(), "ninja", TimeUnit.SECONDS, 15);
			player.teleport(targetPlayer);
			player.sendMessage("§aTeleportado para §f" + targetName);
			player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 10);
		}
	}
}

@EventHandler
public void onDeath(PlayerDeathEvent event) {
	Player player = event.getEntity();
	
	if (WaveWarp.ARENANINJA.hasPlayer(player.getName())) {
		map.remove(player.getName());
	}
	if (map.containsValue(player.getName())) {
		map.entrySet().removeIf(entry -> entry.getValue().equalsIgnoreCase(player.getName()));
	}
}
}


