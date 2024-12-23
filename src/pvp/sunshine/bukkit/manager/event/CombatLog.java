package pvp.sunshine.bukkit.manager.event;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.manager.bossbar.BossBarAPI;

public class CombatLog implements Listener {
	public static Map<Player, Player> emcombate = new HashMap<>();

	public static void removeCombat(Player p) {
		emcombate.remove(p);
	}

	@EventHandler
	public void onEventPlayerDeathEvent(PlayerDeathEvent e) {
		CombatLog.emcombate.remove(e.getEntity());
		CombatLog.emcombate.remove(CombatLog.emcombate.get(e.getEntity()));
		BossBarAPI.removeBar(e.getEntity());
	}

	@EventHandler
	public void onEventPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().toLowerCase().startsWith("/") && CombatLog.emcombate.containsKey(e.getPlayer())) {
			e.getPlayer().sendMessage("§c§lCOMBATE §fVocê está em combate, aguarde para executar comandos.");
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void aosair(PlayerQuitEvent e) {
		if (emcombate.containsKey(e.getPlayer())) {
			e.getPlayer().setHealth(0.0D);
			emcombate.remove(e.getPlayer());
			emcombate.remove(emcombate.get(e.getPlayer()));
		}
	}

	@EventHandler
	public void aosair(PlayerKickEvent e) {
		if (emcombate.containsKey(e.getPlayer())) {
			e.getPlayer().setHealth(0.0D);
			emcombate.remove(e.getPlayer());
			emcombate.remove(emcombate.get(e.getPlayer()));
		}
	}

	@EventHandler
	public void onEventCombatLog(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			if (RegisterAbility.getAbility((Player) e.getEntity()) != "Nenhum"
					&& RegisterAbility.getAbility((Player) e.getDamager()) != "Nenhum"
					&& RegisterAbility.getAbility((Player) e.getEntity()) != "1v1"
					&& RegisterAbility.getAbility((Player) e.getDamager()) != "1v1"
					&& RegisterAbility.getAbility((Player) e.getEntity()) != "Lava"
					&& RegisterAbility.getAbility((Player) e.getDamager()) != "Lava"
					&& RegisterAbility.getAbility((Player) e.getEntity()) != "Sumo"
					&& RegisterAbility.getAbility((Player) e.getDamager()) != "Sumo"
					&& RegisterAbility.getAbility((Player) e.getEntity()) != "Evento"
					&& RegisterAbility.getAbility((Player) e.getDamager()) != "Evento"
					&& !CombatLog.emcombate.containsKey(e.getEntity())
					&& !CombatLog.emcombate.containsKey(e.getDamager())) {
				CombatLog.emcombate.put((Player) e.getEntity(), (Player) e.getDamager());
				CombatLog.emcombate.put((Player) e.getDamager(), (Player) e.getEntity());
				BossBarAPI.setMessage((Player)e.getEntity(), "§b" + e.getDamager().getName() + " - " + RegisterAbility.getAbility((Player)e.getDamager()));
				BossBarAPI.setMessage((Player)e.getDamager(), "§b" + e.getEntity().getName() + " - " + RegisterAbility.getAbility((Player)e.getEntity()));
				new BukkitRunnable() {
					public void run() {
						CombatLog.emcombate.remove(e.getEntity());
						CombatLog.emcombate.remove(e.getDamager());
						BossBarAPI.removeBar((Player)e.getEntity());
						BossBarAPI.removeBar((Player)e.getDamager());
					}
				}.runTaskLater(BukkitMain.getInstance(), 20 * 10);
			}
		}
	}


}