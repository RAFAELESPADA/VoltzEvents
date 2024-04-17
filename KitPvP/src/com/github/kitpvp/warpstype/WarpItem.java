package com.github.kitpvp.warpstype;

import org.bukkit.Material;

public enum WarpItem {

	FPS(Material.IRON_HELMET, Material.IRON_LEGGINGS, Material.IRON_CHESTPLATE, Material.IRON_BOOTS, "fps");

	Material helmet;
	Material leggins;
	Material chestplate;
	Material boots;

	private WarpItem(Material boots, Material leggins, Material chestplate, Material helmet, String type) {

		this.boots = boots;
		this.leggins = leggins;
		this.chestplate = chestplate;
		this.helmet = helmet;

	}
	public Material getBoots() {
		return this.boots;
	}
	public Material getChestplate() {
		return this.chestplate;
	}
	public Material getHelmet() {
		return this.helmet;
	}
	public Material getLeggins() {
		return this.leggins;
	}
}
