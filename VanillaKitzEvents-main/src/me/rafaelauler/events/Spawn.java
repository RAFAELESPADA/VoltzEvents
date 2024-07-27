package me.rafaelauler.events;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;


public class Spawn extends WarpHandle {
	Path path1 = Paths.get(Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/plugins/WaveCore/", "warps.yml");
	File file = new File(path1.toAbsolutePath().toString());
	YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
	private ArrayList<String> fall = new ArrayList<String>();
  public void execute(Player player) {
	  Location l2 = new Location(Bukkit.getWorld(yaml.getString("Mundo-spawn")), yaml.getInt("X-spawn") , yaml.getInt("Y-spawn"), yaml.getInt("Z-spawn"));
   
    player.teleport(l2);
    player.getInventory().clear();
     player.getInventory().setArmorContents(null);
    player.setGameMode(GameMode.ADVENTURE);
    player.getActivePotionEffects().forEach(potion -> player.removePotionEffect(potion.getType()));
    player.setFireTicks(0);
    player.setFoodLevel(20);
    player.getInventory().setHeldItemSlot(0);
   
    
    
	Main.instance.getScoreboardBuilder().build(player);
  }
  @EventHandler
  public void onInteract22(EntityDamageByEntityEvent event) {
  	if (!(event.getEntity() instanceof Player)) {
  		return;
  	}
  	Player player =  (Player)event.getEntity();
  		
  	if (WaveWarp.SPAWN.hasPlayer(player.getName())) {
  		event.setCancelled(true);
  	}
  		}


  @EventHandler
  public void onInteract22V(EntityDamageEvent event) {
  	if (!(event.getEntity() instanceof Player)) {
  		return;
  	}
  	Player player =  (Player)event.getEntity();
  		
  	if (WaveWarp.SPAWN.hasPlayer(player.getName())) {
  		event.setCancelled(true);
  	}
}
}
