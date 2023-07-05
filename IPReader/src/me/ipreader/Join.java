package me.ipreader;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class Join implements Listener {

@EventHandler
public void aosair(PlayerJoinEvent e) {
    final Player p = e.getPlayer();
   p.teleport(new Location(p.getWorld(), 416111.394, 64.00000, 61616.523));
    }
}
