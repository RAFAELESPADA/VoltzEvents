package com.github.kitpvp.warp;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.kitpvp.api.API;
import com.github.kitpvp.controllers.WarpController;
import com.github.kitpvp.warpstype.Warp;
import com.github.kitpvp.warpstype.WarpEnums;
import com.github.kitpvp.warpstype.warps.Fps;

import net.md_5.bungee.api.ChatColor;

import com.github.kitpvp.warpstype.WarpEnums;

import java.util.Arrays;

public class WarpGUI implements Listener {

	public static String inv_name = "§b§lWARPS";

	/*
	 * public static final ItemStack ICON =
	 * ItemUtils.getCustomItemStack(Material.COMPASS, "§bWarps", (String) null);
	 * 
	 * @EventHandler private void onPlayerInteract(PlayerInteractEvent event) {
	 * Player player = event.getPlayer(); if (event.hasItem() &&
	 * event.getItem().isSimilar(WarpsGUI.ICON)) { openGUI(player); } }
	 */

	@EventHandler
	public void onIntereact(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (p.getItemInHand().getItemMeta() == null)
			return;
		if (p.getItemInHand().getItemMeta().getDisplayName() == null)
			return;
		if (p.getItemInHand().getType() == null)
			return;
		if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(inv_name)) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR) {
				openGUI(p);
			}
		}
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent event) {

		Player p = (Player) event.getWhoClicked();
		ItemStack current = event.getCurrentItem();
		if (event.isCancelled())
			return;

		if (event.getInventory().getTitle().equalsIgnoreCase(inv_name)) {
			if (current == null) {
				event.setCancelled(true);
				return;
			}
			if (current.getType() == WarpEnums.FPS.getMaterial()) {
				if (!API.getWarp(p).equalsIgnoreCase("fps")) {
					WarpController.get("fps").join(p);
					p.closeInventory();

				}else{
					p.sendMessage(ChatColor.RED + "Você já está na FPS");
				}
			}

		}
	}

	public static void openGUI(Player player) {

		Inventory inv = Bukkit.createInventory(player, InventoryType.HOPPER, inv_name);
		for (WarpEnums item : WarpEnums.values()) {
			inv.setItem(item.slot(), API.itemStack(item.getMaterial(), item.getName()));
		}
		player.openInventory(inv);

	}
}