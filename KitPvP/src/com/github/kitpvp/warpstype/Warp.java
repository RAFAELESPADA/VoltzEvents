package com.github.kitpvp.warpstype;// // com.github.kitpvp.warpstype;


import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.kitpvp.controllers.WarpController;

public abstract class Warp {

	String name;
	Location loc;

	public Warp(String name, Location loc) {
		this.name = name;
		this.loc = loc;
		WarpController.save(this);
	}

	public abstract void join(Player p);

	public abstract void loadHotBar(Player p);

	public String getName() {
		return name;
	}

	public Location getLoc() {
		return loc;
	}

	// SPAWN("§b§lSPAWN", Material.BED, "", 2, "spawn"),
	// FPS("§b§lGLASS", Material.STAINED_GLASS, "", 2, "fps");

	// String name;
	// Material mate;
	// String description;
	// int slot;
	// String type;

	// private Warp(String name, Material mate, String description, int slot, String
	// type) {
	// this.name = name;
	// this.mate = mate;
	// this.slot = slot;
	// this.type = type;
	// }

	// public Material getMate() {
	// return this.mate;
	// }

	// public String getName() {
	// return this.name;
	// }

	// public String getType() {
	// return this.type;
	// }

	// public int getSlot() {
	// return this.slot;
	// }

	// public String getDescription() {
	// return this.description;
	// }
}
