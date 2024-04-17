package com.github.kitpvp.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.kitpvp.listeners.EventosGerais;
import com.github.kitpvp.listeners.Sopa;
import com.github.kitpvp.warp.WarpGUI;


public class Main extends JavaPlugin {

	public static Main instance;

	public static Main getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;
		Bukkit.getPluginManager().registerEvents(new EventosGerais(), this);
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new EventosGerais(), this);
		pm.registerEvents(new Sopa(), this);
		pm.registerEvents(new Sopa(), this);
		pm.registerEvents(new WarpGUI(), this);
		setupRecipes();
	}

	@Override
	public void onDisable() {

	}

private void setupRecipes() {
    ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP);
    ItemStack cocoa = new ItemStack(Material.INK_SACK, 1, (short) 3);
    ShapelessRecipe soupRecipe = new ShapelessRecipe(soup);
    soupRecipe.addIngredient(Material.BOWL);
    soupRecipe.addIngredient(cocoa.getData());
    getServer().addRecipe(soupRecipe);
}
}