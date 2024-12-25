package me.rafaelauler.events;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.wavemc.core.bukkit.WaveBukkit;


public final class Main extends JavaPlugin {

    public static final PluginManager pm = Bukkit.getPluginManager();
    public static final String prefix = "§7[§6KomboEventos§7] ";
    public static Main instance;

	private ScoreboardBuilder scoreboardBuilder;
    @Override
    public void onEnable() {
        instance = this;

        EventoUtils.evento = true;
        this.init();
        
    	
Bukkit.getConsoleSender().sendMessage(prefix + " PLUGIN ENABLED!");
    }
    public ScoreboardBuilder getScoreboardBuilder() {
		return scoreboardBuilder;
	}
    @Override
    public void onDisable() {
    	for (Location l : EventoUtils.blocks) {
			l.getBlock().setType(Material.AIR);
		}
    }
	 private void setupRecipes() {
		 ShapelessRecipe cocoa = new ShapelessRecipe(new NamespacedKey(instance, "soup2"),
	                new ItemStack(Material.MUSHROOM_STEW));
	        cocoa.addIngredient(1, Material.COCOA_BEANS);
	        cocoa.addIngredient(1, Material.BOWL);
	        Bukkit.getServer().addRecipe(cocoa);
	    }

    public void init() {
    	WaveBukkit.getExecutorService().submit(() -> {
			new BukkitRunnable() {
				@Override
				public void run() {
					if (!EventoUtils.blocks.isEmpty()) {
				EventoUtils.clearBlocks();
				EventoComando2 e = new EventoComando2();
				for (Location l : EventoUtils.blocks) {
				e.removeFluid(l);
				}
				Bukkit.broadcastMessage(ChatColor.RED + "A Arena do Evento está sendo limpa automaticamente!");
				}}}.runTaskTimer(Main.instance, 10, 300 * 20L);
		
		});
    	this.scoreboardBuilder = new ScoreboardBuilder();
    	ScoreboardBuilder.init();
    	setupRecipes();
        Bukkit.getPluginManager().registerEvents(new EventListeners(),this);
        getCommand("event").setExecutor(new EventoComando2());
        getCommand("setwarp").setExecutor(new SetWarp(this));
        getCommand("vanish").setExecutor(new Vanish());
		getCommand("v").setExecutor(new Vanish());
		getCommand("gamemode").setExecutor(new GamemodeCMD());
		getCommand("gm").setExecutor(new GamemodeCMD());
        getCommand("event").setTabCompleter(new EventoTabComplete());
    }
}
