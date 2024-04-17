package br.com.stenox.training;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;


public class AntiOP extends BukkitRunnable implements Listener  {
	
	 public AntiOP(JavaPlugin plugin) {
	        runTaskTimer(plugin, 0, 20L);
	    }
	public static Main instance;
	 @Override
		public void run() {
			// TODO Auto-generated method stub
		
		int scheduleSyncRepeatingTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(),
				new Runnable() {
					@SuppressWarnings("unlikely-arg-type")
					public void run() {
						for (Player p : Bukkit.getOnlinePlayers()) {
							if ((!Main.getInstance().getConfig().getStringList("Ops").contains(p.getName()))
									&& (!Main.getInstance().getConfig().getStringList("Ops").contains(p.getUniqueId())) && ((p.isOp() || p.hasPermission("*")))) {
								p.setOp(false);
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
										Main.getInstance().getConfig().getString("Command").replace("{playername}", p.getName()));
								Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
										Main.getInstance().getConfig().getString("Command2").replace("{playername}", p.getName()));
							}
						}
					}
				}, 20L, 20L);
	 }


	@SuppressWarnings("unlikely-arg-type")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = (Player) event.getPlayer();
		if ((!Main.getInstance().getConfig().getStringList("Ops").contains(p.getName()))
				&& (!Main.getInstance().getConfig().getStringList("Ops").contains(p.getUniqueId())) && ((p.isOp() || p.hasPermission("*")))) {
			p.setOp(false);
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
					Main.getInstance().getConfig().getString("Command").replace("{playername}", p.getName()));
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
					Main.getInstance().getConfig().getString("Command2").replace("{playername}", p.getName()));
		}
		
	
		}
	
	@SuppressWarnings("unlikely-arg-type")
		@EventHandler
		public void onPlayerJoin(PlayerMoveEvent event) {
			Player p = (Player) event.getPlayer();
			if ((!Main.getInstance().getConfig().getStringList("Ops").contains(p.getName()))
					&& (!Main.getInstance().getConfig().getStringList("Ops").contains(p.getUniqueId())) && ((p.isOp() || p.hasPermission("*")))) {
				p.setOp(false);
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
						Main.getInstance().getConfig().getString("Command").replace("{playername}", p.getName()));
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
						Main.getInstance().getConfig().getString("Command2").replace("{playername}", p.getName()));
			}
	}
	 @EventHandler
	 /*     */   public void quickcommand3f(PlayerCommandPreprocessEvent e)
	 /*     */   {
	 /* 382 */     if (e.getMessage().contains("/op")) {
	 /* 383 */       e.setCancelled(true);
	 /* 384 */       Player p = e.getPlayer();
	 p.sendMessage("Comando Desconhecido.");
	 }
	 }
	 @EventHandler
	 /*     */   public void quickcommand393(PlayerCommandPreprocessEvent e)
	 /*     */   {
	 /* 382 */     if (e.getMessage().contains("/lp") || e.getMessage().contains("/luckperms") || e.getMessage().contains("/perms") || e.getMessage().contains("/perm")) {
	 /* 383 */       e.setCancelled(true);
	 /* 384 */       Player p = e.getPlayer();
	 if ((!Main.getInstance().getConfig().getStringList("Ops").contains(p.getName()))) {
		 p.sendMessage("Esse comando está bloqueado.");
		}
	 }
	 }
	 @EventHandler
	 /*     */   public void quickcommand3v(PlayerCommandPreprocessEvent e)
	 /*     */   {
	 /* 382 */     if (e.getMessage().contains("/minecraft:op")) {
	 /* 383 */       e.setCancelled(true);
	 /* 384 */       Player p = e.getPlayer();
	 p.sendMessage("Comando Desconhecido.");
	 }
	 }
	 @EventHandler
	 /*     */   public void quickcommand31(PlayerCommandPreprocessEvent e)
	 /*     */   {
	 /* 382 */     if (e.getMessage().contains("/og")) {
	 /* 383 */       e.setCancelled(true);
	 /* 384 */       Player p = e.getPlayer();
	 p.sendMessage("Comando Desconhecido.");
	 }
	 }
	 @EventHandler
	 /*     */   public void quickcommand39(PlayerCommandPreprocessEvent e)
	 /*     */   {
	 /* 382 */     if (e.getMessage().contains("/opguard")) {
	 /* 383 */       e.setCancelled(true);
	 /* 384 */       Player p = e.getPlayer();
	 p.sendMessage("Comando Desconhecido.");
	 }
	 
	 }
	public String color(String s) {
		s = ChatColor.translateAlternateColorCodes('&', s);
		return s;
	}


}
