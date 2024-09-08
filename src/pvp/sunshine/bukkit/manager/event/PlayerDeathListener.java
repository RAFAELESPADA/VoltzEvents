package pvp.sunshine.bukkit.manager.event;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.api.KillStreakAPI;
import pvp.sunshine.bukkit.api.WinStreakAPI;
import pvp.sunshine.bukkit.commands.team.EventoCMD;
import pvp.sunshine.bukkit.manager.bossbar.BossBarAPI;
import pvp.sunshine.bukkit.manager.duels.Battle;
import pvp.sunshine.bukkit.manager.mysql.connections.SQL1v1;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLPvP;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;
import pvp.sunshine.bukkit.manager.mysql.connections.holograms.TopDeaths;
import pvp.sunshine.bukkit.manager.mysql.connections.holograms.TopKills;
import pvp.sunshine.bukkit.manager.mysql.connections.holograms.TopLoses;
import pvp.sunshine.bukkit.manager.mysql.connections.holograms.TopWins2;
import pvp.sunshine.bukkit.manager.scoreboard.duels.PlayerNotBattle;
import pvp.sunshine.bukkit.manager.scoreboard.PvP;
import pvp.sunshine.bukkit.utils.PvPUtil;

public class PlayerDeathListener implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		event.setDeathMessage(null);
		Player player = event.getEntity();
		event.getEntity().setAllowFlight(false);
		event.getEntity().setFlying(false);
		if (EventoCMD.participantes.contains(event.getEntity().getName())) {
			event.getDrops().clear();
			EventoCMD.participantes.remove(event.getEntity().getName());
			RegisterAbility.removeAbility(event.getEntity());
			PvP.update(event.getEntity());
			new BukkitRunnable() {
				@Override
				public void run() {

	                Location spawnLocation = new Location(Bukkit.getWorld("lobbypvp2") , 510.137, 12.000000, 620.218 , (float)-89.811 , (float)3.0000000);player.teleport(spawnLocation);
				}
			}.runTaskLater(BukkitMain.getInstance(), 3);
			event.getEntity().sendMessage("§c§lDEATH §fVocê morreu no evento, seus status não foram alterados!");
			if (event.getEntity().getKiller() instanceof Player) {
				event.getEntity().getKiller()
						.sendMessage("§c§lKILL §fVocê matou um jogador no evento, os status de ambos não foram alterados.");
			}
			return;
		}
		event.setDeathMessage(null);
		Player p = event.getEntity();
		if (Battle.partida.containsKey(p.getUniqueId())) {
			Battle.partida.containsKey(p.getUniqueId());
			int killerXP = randomXP();
			int deathXP = randomXPNegative();
			if (event.getEntity().getKiller() instanceof Player) {

				//DEATH
				Inventory defeatedInventory = event.getEntity().getInventory();
				ItemStack soupItem = new ItemStack(Material.MUSHROOM_SOUP);
				int soupCount = 0;
				for (ItemStack item : defeatedInventory.getContents()) {
					if (item != null && item.getType() == soupItem.getType()) {
						soupCount += item.getAmount();
					}
				}
				Inventory killerInventory = event.getEntity().getKiller().getInventory();
				ItemStack soupItemII = new ItemStack(Material.MUSHROOM_SOUP);
				int soupCountII = 0;
				for (ItemStack itemII : defeatedInventory.getContents()) {
					if (itemII != null && itemII.getType() == soupItem.getType()) {
						soupCountII += itemII.getAmount();
					}
				}
				//KILLER
				event.getEntity().getKiller().sendMessage("§a§l1V1 §fParabéns! você venceu o duelo contra o jogador(a) §a" + event.getEntity().getName() + "§f que ficou com um total de §e" + soupCount + " §fsopa(s) em seu inventário.\n§6(+8 coins)\n§b(+" + killerXP + " xp)");
				event.getEntity().sendMessage("§c§l1V1 §fVocê perdeu o duelo contra o jogador(a) §c" + event.getEntity().getKiller().getName() + "§f que ficou com um total de §e" + soupCountII + "§f sopa(s) em seu inventário.");
				event.getEntity().getKiller().playSound(event.getEntity().getKiller().getLocation(),Sound.ANVIL_LAND, 5.0F, 1.0F);
				TopLoses.updateHologram();
				TopWins2.updateHologram();
				PlayerNotBattle.update(event.getEntity());
				PlayerNotBattle.update(event.getEntity().getKiller());
				event.getEntity().playSound(event.getEntity().getLocation(), Sound.EXPLODE, 1.0F, 1.0F);
new BukkitRunnable() {
	@Override
	public void run() {

				SQLPvP.addCoins(event.getEntity().getKiller(), 8);
				SQLPvP.removeCoins(event.getEntity(), 5);
				SQL1v1.addWins(event.getEntity().getKiller());
				SQL1v1.addLoses(event.getEntity());
				SQLRank.addXp(event.getEntity().getKiller(), killerXP);
				SQLRank.removeXP(event.getEntity(), deathXP);
				WinStreakAPI.addStreak(event.getEntity().getKiller());
				WinStreakAPI.removeStreak(event.getEntity());
				TopWins2.incrementWins(event.getEntity().getKiller());
				
				TopLoses.incrementLoses(event.getEntity());
				
			}
}.runTaskAsynchronously(BukkitMain.getInstance());
			return;
		}
			}
		event.setDeathMessage(null);
		Player victim = event.getEntity();
		victim.setAllowFlight(false);
		victim.setFlying(false);
		Player killer = event.getEntity().getKiller();
		PvP.update(victim);
		

		victim.playSound(victim.getLocation(), Sound.CREEPER_DEATH, 10.0F, 10.0F);
		RegisterAbility.powerMap.remove(victim.getName());


	    Location spawnLocation = new Location(Bukkit.getWorld("lobbypvp2") , 510.137, 12.000000, 620.218 , (float)-89.811 , (float)3.0000000);
        (new BukkitRunnable() {
			public void run() {
				 victim.teleport(spawnLocation);
			}
		}).runTaskLater((Plugin) BukkitMain.getInstance(), 3l);
		if (victim.getKiller() instanceof Player) {
		new BukkitRunnable() {	
		public void run() {	
		
			handlePlayerKill(victim, victim.getKiller());
			PvP.update(killer);
			killer.sendMessage("§a§lKILL §fVocê matou §a" + victim.getName() + "\n§6(+8 coins)");
			BossBarAPI.removeBar(killer);
			victim.sendMessage("§c§lDEATH §fVocê morreu para §c" + killer.getName());
			killer.playSound(killer.getLocation(), Sound.ANVIL_LAND, 5.0F, 1.0F);
		}}.runTaskAsynchronously(BukkitMain.getInstance());
		} else if (!RegisterAbility.getAbility(victim).equalsIgnoreCase("Lava") && !RegisterAbility.getAbility(victim).equalsIgnoreCase("Sumo")) {
			new BukkitRunnable() {	
				public void run() {	
				
			handleNonPlayerKill(victim);

				}}.runTask(BukkitMain.getInstance());
		}
	}

	private void handlePlayerKill(Player victim, Player killer) {
		victim.getInventory().clear();
		int killerXP = randomXP();
		int deathXP = randomXPNegative();

		KillStreakAPI.addStreak(killer);
		KillStreakAPI.removeStreak(victim);
		TopKills.incrementKills(killer);
		TopKills.updateHologram();
		SQLPvP.addCoins(killer, 8);
		SQLPvP.removeCoins(victim, 5);
		SQLPvP.addKills(killer);
		SQLPvP.addDeaths(victim);
		SQLRank.addXp(killer, killerXP);
		SQLRank.removeXP(victim, deathXP);
		TopDeaths.incrementDeaths(victim);
		TopDeaths.updateHologram();

	
	}

	private void handleNonPlayerKill(Player victim) {
		victim.getInventory().clear();
		RegisterAbility.removeAbility(victim);
		RegisterAbility.setAbility(victim, "Nenhum");

		victim.sendMessage("§4§lDEATH §fVocê morreu por causas desconhecidas.");
		PvP.update(victim);

		victim.getActivePotionEffects().forEach(potionEffect -> victim.removePotionEffect(potionEffect.getType()));

		Flag.setProtection(victim, true);


        Location spawnLocation = new Location(Bukkit.getWorld("lobbypvp2") , 510.137, 12.000000, 620.218 , (float)-89.811 , (float)3.0000000);
        (new BukkitRunnable() {
			public void run() {
				 victim.teleport(spawnLocation);
			}
		}).runTaskLater((Plugin) BukkitMain.getInstance(), 5l);
	}
	

	



	@EventHandler
	public void respawn(PlayerDeathEvent event) {
		final Player player = event.getEntity();
		(new BukkitRunnable() {
			public void run() {
				player.spigot().respawn();
			}
		}).runTask((Plugin) BukkitMain.getInstance());
	}

	private static int randomXP() {
		Random random = new Random();
		return random.nextInt(10) + 8;
	}

	private static int randomXPNegative() {
		Random random = new Random();
		return random.nextInt(5) + 1;
	}
}
