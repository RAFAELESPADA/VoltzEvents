// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training;

import java.util.List;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import java.util.Iterator;
import br.com.stenox.training.gamer.Gamer;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandMap;
import br.com.stenox.training.event.custom.TimeSecondEvent;
import org.bukkit.scheduler.BukkitRunnable;
import br.com.stenox.training.command.TellCommand;
import br.com.stenox.training.command.TagCommand;
import br.com.stenox.training.command.SpawnCommand;
import br.com.stenox.training.command.ReportCommand;
import br.com.stenox.training.command.PingCommand;
import br.com.stenox.training.command.InvseeCommand;
import br.com.stenox.training.command.GroupSetCommand;
import br.com.stenox.training.command.ClearDropsCommand;
import br.com.stenox.training.command.Commands;
import br.com.stenox.training.command.AddTagCommand;
import br.com.stenox.training.command.punishments.UnmuteCommand;
import br.com.stenox.training.command.punishments.UnbanCommand;
import br.com.stenox.training.command.punishments.TempMuteCommand;
import br.com.stenox.training.command.punishments.TempBanCommand;
import br.com.stenox.training.command.punishments.MuteCommand;
import br.com.stenox.training.command.punishments.KickCommand;
import org.bukkit.command.Command;
import br.com.stenox.training.command.punishments.BanCommand;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import br.com.stenox.training.listeners.SignListeners;
import br.com.stenox.training.listeners.PlayerListeners;
import br.com.stenox.training.listeners.NPCListener;
import br.com.stenox.training.listeners.PlayerKillStreakListener;
import br.com.stenox.training.listeners.MotdListener;
import br.com.stenox.training.listeners.MenuListeners;
import br.com.stenox.training.listeners.DamageListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import com.comphenix.protocol.ProtocolLibrary;
import javax.sql.DataSource;
import org.sqlite.SQLiteDataSource;
import org.bukkit.Difficulty;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.Location;
import br.com.stenox.training.database.SQLite;
import com.comphenix.protocol.ProtocolManager;
import br.com.stenox.training.gamer.tag.TagProvider;
import br.com.stenox.training.kit.manager.KitManager;
import br.com.stenox.training.utils.npc.NPCManager;
import br.com.stenox.training.utils.hologram.individual.HologramManager;
import br.com.stenox.training.gamer.GamerRepository;
import br.com.stenox.training.gamer.GamerController;
import br.com.stenox.training.warp.WarpController;
import net.helix.pvp.command.Regras;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    private static Main instance;
    private WarpController warpController;
    private GamerController gamerController;
    private GamerRepository gamerRepository;
    private HologramManager hologramManager;
    private NPCManager npcManager;
    private KitManager kitManager;
    private ProtocolManager protocolManager;
    private SQLite connection;
    public static Location SPAWN;
    
    public void onEnable() {
        Main.instance = this;
        this.getLogger().info("Plugin initialising...");
        final World world = Bukkit.getWorlds().get(0);
        world.setDifficulty(Difficulty.NORMAL);
        world.setWeatherDuration(0);
        world.setThunderDuration(0);
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("doMobLoot", "false");
        world.setGameRuleValue("mobGriefing", "false");
        new AntiOP(this);
        world.setGameRuleValue("doFireTick", "false");
        (this.connection = new SQLite(this)).openConnection();
        this.connection.createTables();
        Main.SPAWN = new Location(Bukkit.getWorlds().get(0), Main.getInstance().getConfig().getInt("SpawnX"), Main.getInstance().getConfig().getInt("SpawnY"), Main.getInstance().getConfig().getInt("SpawnZ"), (float)Main.getInstance().getConfig().getInt("SpawnPitch"), (float)Main.getInstance().getConfig().getInt("SpawnYaw"));
        (this.kitManager = new KitManager()).loadKits();
        (this.warpController = new WarpController()).loadWarps();
        final SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(this.connection.getUrl());
        this.gamerRepository = new GamerRepository((DataSource)dataSource);
        this.gamerController = new GamerController(this);
        (this.npcManager = new NPCManager(this)).onEnable();
        (this.hologramManager = new HologramManager(this)).onEnable();
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.unregisterCommands("me", "pl", "plugins", "icanhasbukkit", "ver", "version", "?", "help", "viaversion", "viaver", "vvbukkit", "protocolsupport", "ps", "holograms", "hd", "holo", "hologram", "ban", "me", "say", "about", "pardon", "pardon-ip", "ban-ip", "ipwhitelist", "trigger", "testforblock", "tellraw", "testfor", "testforblocks", "playsounds", "title", "summon", "fill", "entitydata", "particle", "replaceitem", "execute", "clone", "debug", "packet_filter", "achievement", "kick", "help", "tell", "ban", "ban-ip", "banlist", "message", "msg", "xp", "stats", "kill", "spawnpoint", "spreadplayers", "filter", "list", "playsound", "rl", "reload", "seed", "setidletimeout", "scoreboard", "tp", "teleport", "gamemode", "packet", "protocol", "packetlog", "blockdata", "defaultgamemode", "filter", "restart");
        Bukkit.getPluginManager().registerEvents(new PlayerKillStreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuListeners(), this);
        Bukkit.getPluginManager().registerEvents(new MotdListener(), this);
        Bukkit.getPluginManager().registerEvents(new NPCListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListeners(), this);
        Bukkit.getPluginManager().registerEvents(new SignListeners(), this);
        final CommandMap map = (CommandMap)((CraftServer)Bukkit.getServer()).getCommandMap();
        map.register("ban", (Command)new BanCommand());
        map.register("kick", (Command)new KickCommand());
        map.register("mute", (Command)new MuteCommand());
        map.register("tempban", (Command)new TempBanCommand());
        map.register("tempmute", (Command)new TempMuteCommand());
        map.register("unban", (Command)new UnbanCommand());
        getCommand("tag").setExecutor(new TagCommand());
        map.register("unmute", (Command)new UnmuteCommand());
        map.register("cleardrops", (Command)new ClearDropsCommand());
        map.register("groupset", (Command)new GroupSetCommand());
        map.register("raikiri21", new Commands());
        map.register("ping", (Command)new PingCommand());
        map.register("report", (Command)new ReportCommand());
        map.register("spawn", (Command)new SpawnCommand());
        map.register("tell", (Command)new TellCommand());
        new BukkitRunnable() {
            public void run() {
                new TimeSecondEvent(TimeSecondEvent.TimeType.SECONDS).call();
            }
        }.runTaskTimer((Plugin)this, 0L, 20L);
        new BukkitRunnable() {
            public void run() {
                new TimeSecondEvent(TimeSecondEvent.TimeType.MILLISECONDS).call();
            }
        }.runTaskTimer((Plugin)this, 0L, 1L);
        this.generateRecipes();
        this.getLogger().info("Plugin initialized.");
    }
    
    public void onDisable() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            final Gamer gamer = Gamer.getGamer(player.getName());
            this.getGamerController().update(gamer);
            player.kickPlayer("§cServidor reiniciando...");
        }
        this.npcManager.onDisable();
        this.hologramManager.onDisable();
    }
    
    public void generateRecipes() {
        final ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP);
        final ShapelessRecipe cocoa = new ShapelessRecipe(soup);
        cocoa.addIngredient(Material.BOWL);
        cocoa.addIngredient(Material.INK_SACK, 3);
        Bukkit.addRecipe((Recipe)cocoa);
    }
    public String color(String s) {
		s = ChatColor.translateAlternateColorCodes('&', s);
		return s;
	}
    private void unregisterCommands(final String... commands) {
        try {
            final Field f1 = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f1.setAccessible(true);
            final CommandMap commandMap = (CommandMap)f1.get(Bukkit.getServer());
            final Field f2 = commandMap.getClass().getDeclaredField("knownCommands");
            f2.setAccessible(true);
            final HashMap<String, Command> knownCommands = (HashMap<String, Command>)f2.get(commandMap);
            for (final String command : commands) {
                if (knownCommands.containsKey(command)) {
                    knownCommands.remove(command);
                    final List<String> aliases = new ArrayList<String>();
                    for (final String key : knownCommands.keySet()) {
                        if (!key.contains(":")) {
                            continue;
                        }
                        final String substr = key.substring(key.indexOf(":") + 1);
                        if (!substr.equalsIgnoreCase(command)) {
                            continue;
                        }
                        aliases.add(key);
                    }
                    for (final String alias : aliases) {
                        knownCommands.remove(alias);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public WarpController getWarpController() {
        return this.warpController;
    }
    
    public GamerController getGamerController() {
        return this.gamerController;
    }
    
    public GamerRepository getGamerRepository() {
        return this.gamerRepository;
    }
    
    public HologramManager getHologramManager() {
        return this.hologramManager;
    }
    
    public NPCManager getNpcManager() {
        return this.npcManager;
    }
    
    public KitManager getKitManager() {
        return this.kitManager;
    }
    
    
    public ProtocolManager getProtocolManager() {
        return this.protocolManager;
    }
    
    public SQLite getConnection() {
        return this.connection;
    }
    
    public static Main getInstance() {
        return Main.instance;
    }
}
