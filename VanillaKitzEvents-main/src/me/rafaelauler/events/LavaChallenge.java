package me.rafaelauler.events;

import net.wavemc.core.bukkit.WaveBukkit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LavaChallenge extends WarpHandle {
	
	@Override
	public void execute(Player player) {
		super.execute(player);
		ItemStack vermelho = new ItemStack(Material.RED_MUSHROOM, 64);
		  ItemStack marrom = new ItemStack(Material.BROWN_MUSHROOM, 64);
		  
		  
		  ItemStack item = new ItemStack(Material.BOWL, 64);

		  player.getInventory().setItem(13, item);
		  for (int i = 0; i < 36; i++) {
				player.getInventory().addItem(new ItemStack(Material.MUSHROOM_STEW));
			}
		  player.getInventory().setItem(14, vermelho);
		  player.getInventory().setItem(15, marrom);
		  player.getInventory().setItem(16, vermelho);

		  player.getInventory().setItem(17, marrom);

	        player.sendMessage("§aVocê agora está no evento LAVA!");
	        player.sendMessage("§aSiga as instruções que serão dadas no chat!");
	}
}