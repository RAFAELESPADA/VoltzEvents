package me.rafaelauler.events;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.wavemc.core.bukkit.WaveBukkit;
import net.wavemc.core.util.WaveCooldown;





public class ArenaNinja extends WarpHandle {
	public static final HashMap<String, String> map = new HashMap<>();


	
	@Override
	public void execute(Player player) {
		super.execute(player);
		player.getInventory().clear();
		EventoComando2 e =  new EventoComando2();
		e.Gladiator(player);

        player.sendMessage("�aVoc� agora est� no evento Arena Ninja!");
        player.sendMessage("�aSiga as instru��es que ser�o dadas no chat!");
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
				player.sendMessage("�cJogador est� muito longe (50+ blocos).");
				return;
			}
			if (!WaveWarp.ARENANINJA.hasPlayer(player.getName())) {
				player.sendMessage("�cO Jogador n�o est� mais nesse evento.");
				return;
			}

			WaveCooldown.create(player.getName(), "ninja", TimeUnit.SECONDS, 15);
			WaveBukkit.getExecutorService().submit(() -> {
				new BukkitRunnable() {
					@Override
					public void run() {
					WaveCooldown.delete(player.getName(), "ninja");
					player.sendMessage("�cO Cooldown do Ninja acabou.");
					}}.runTaskLater(Main.instance, 15 * 20L);
			
			});
			player.teleport(targetPlayer);
			player.sendMessage("�aTeleportado para �f" + targetName);
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


