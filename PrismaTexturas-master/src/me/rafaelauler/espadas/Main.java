package me.rafaelauler.espadas;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin {
	
	
	public static Main getPlugin() {
		return Main.getPlugin(Main.class);
	} 
	
	@Override
	public void onEnable() {
		getCommand("cosmeticos").setExecutor(new Comando());
		PluginManager pm = Bukkit.getPluginManager();
		saveDefaultConfig();
		pm.registerEvents(new ItemBuilderListener(), this);
		pm.registerEvents(new Inventory2(), this);
	}
}