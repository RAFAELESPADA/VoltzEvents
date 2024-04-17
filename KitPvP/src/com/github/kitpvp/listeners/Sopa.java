package com.github.kitpvp.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Sopa implements Listener {


@EventHandler
public void onTomarSopa(PlayerInteractEvent e) {
  Player p = e.getPlayer();
  if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && 
    p.getItemInHand().getType() == Material.MUSHROOM_SOUP && 
    p.getHealth() < 20.0D && p.getItemInHand().getType() == Material.MUSHROOM_SOUP) {
    e.setCancelled(true);
    p.setHealth((p.getHealth() + 7.0D >= 20.0D) ? 20.0D : (p.getHealth() + 7.0D));
    p.setFoodLevel(20);
    p.setItemInHand(new ItemStack(Material.BOWL));
    p.updateInventory();
  } 
}
}

      	 
