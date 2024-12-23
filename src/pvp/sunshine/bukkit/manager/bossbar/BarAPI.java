package pvp.sunshine.bukkit.manager.bossbar;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pvp.sunshine.bukkit.BukkitMain;

public class BarAPI implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		BossBarAPI.removeBar(e.getPlayer());
	}

	@EventHandler
	public void onKick(PlayerKickEvent e) {
		BossBarAPI.removeBar(e.getPlayer());
	}

	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		this.handlePlayerTeleport(e.getPlayer());
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		this.handlePlayerTeleport(e.getPlayer());
	}

	private void handlePlayerTeleport(Player player) {
		if (BossBarAPI.hasBar(player)) {
			BossBar bar = BossBarAPI.getBossBar(player);
			bar.setVisible(false);
			new BukkitRunnable() {
				public void run() {
					bar.setVisible(true);
				}
			}.runTaskLater(BukkitMain.getInstance(), 2);
		}
	}

	@EventHandler
	public void onMove(final PlayerMoveEvent e) {
		BossBar bar = BossBarAPI.getBossBar(e.getPlayer());
		if (bar != null) {
			new BukkitRunnable() {
				public void run() {
					if (e.getPlayer().isOnline()) {
						bar.updateMovement();
					}
				}
			}.runTaskLater(BukkitMain.getInstance(), 0);
		}
	}

}
