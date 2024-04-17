package com.github.kitpvp.warpstype;// // com.github.kitpvp.warpstype;

import org.bukkit.Material;

public enum WarpEnums {
	FPS("§b§lFps", "§7Lute sem lag", Material.GLASS, 2, "fps"),
	SPAWN("§b§lSPAWN", "§7Lute sem lag", Material.BED, 1, "spawn");

	String name;
	String description;
	Material material;
	int slot;
	String type;

	WarpEnums(String name, String description, Material material, int slot, String type) {

		this.name = name;
		this.description = description;
		this.material = material;
		this.slot = slot;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public Material getMaterial() {
		return this.material;
	}

	public int slot() {
		return this.slot;
	}

	public String getType() {
		return this.type;
	}

}