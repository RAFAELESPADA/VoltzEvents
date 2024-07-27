package me.rafaelauler.events;


import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


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

    }

    public void init() {
    	Bukkit.getPluginManager().registerEvents(new EventListeners(),this);
        getCommand("event").setExecutor(new EventoComando());
        getCommand("setwarp").setExecutor(new SetWarp(this));
        getCommand("vanish").setExecutor(new Vanish());
		getCommand("v").setExecutor(new Vanish());
        getCommand("event").setTabCompleter(new EventoTabComplete());
    }
}
