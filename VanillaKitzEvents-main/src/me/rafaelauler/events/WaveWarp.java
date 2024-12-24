package me.rafaelauler.events;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;

public enum WaveWarp {

	SPAWN("Spawn", new Spawn(),new ItemStack( Material.AIR)),

	ARENANINJA("ArenaNinja", new ArenaNinja(),new ItemStack( Material.AIR)),
	MESTREMANDOU("MestreMandou", new MestreMandou(),new ItemStack( Material.AIR)),
	CUSTOM("Custom", new Custom(),new ItemStack( Material.AIR)),
	
	ARENAPVP("ArenaPvP", new Arena(),new ItemStack( Material.AIR)),
	SUMO("Sumo", new Sumo(),new  ItemStack( Material.AIR)),
    LAVACHALLENGE("LavaChallenge", new LavaChallenge(),  new ItemStack( Material.AIR)),
	ONEVSONE("OnevsOne", new OnevsOne(), new ItemStack(Material.AIR));
	
	static {
		getWarps().forEach(warp -> 
			Bukkit.getPluginManager().registerEvents(warp.getHandler(), Main.instance)
		);
	}

	
	Path path1 = Paths.get(Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/plugins/WaveCore/", "warps.yml");
	File file = new File(path1.toAbsolutePath().toString());
	YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
	private final String name;
	private final WarpHandle handler;
	private final ItemStack icon;
	private final Set<String> players;
	
	public static List<WaveWarp> getWarps() {
		return Arrays.asList(values());
	}
	
	public static Optional<WaveWarp> findWarp(String warpName) {
		return getWarps().stream().filter(
				warp -> warp.getName().equalsIgnoreCase(warpName)
		).findFirst();
	}

	public static void removeHandle(String username) {
		getWarps().stream().filter(
				warp -> warp.players.contains(username)
		).forEach(warp -> warp.players.remove(username));
	}
	
	WaveWarp(String name, WarpHandle handle, ItemStack icon) {
		this.name = name;
		this.handler = handle;
		this.icon = icon;
		this.players = new LinkedHashSet<>();
	}

	public void send(Player player) {
		send(player, false);
	}
	
	public void send(Player player, boolean silent) {
		
	if (yaml.getString("Mundo-" + name.toLowerCase()) == null) {
		Bukkit.getConsoleSender().sendMessage("Mundo-" + name.toLowerCase());
		player.sendMessage(ChatColor.RED + "O Local " + name + " não está setado.");
		return;
	}

		getWarps().stream().filter(
				warp -> warp != this && warp.players.contains(player.getName())
		).forEach(warp -> warp.players.remove(player.getName()));

		players.add(player.getName());
		handler.execute(player);
		
        
		Location l = new Location(Bukkit.getWorld(yaml.getString("Mundo-" + name.toLowerCase())), yaml.getInt("X-" +name.toLowerCase()) , yaml.getInt("Y-" +name.toLowerCase()), yaml.getInt("Z-" +name.toLowerCase()));
		l.setPitch(yaml.getInt("PITCH-" +name.toLowerCase()));
		l.setYaw(yaml.getInt("YAW-" +name.toLowerCase()));	
		player.teleport(l);
		Bukkit.getConsoleSender().sendMessage(player.getName()+ " se teleportou para a warp " + name.toLowerCase());
		Bukkit.getConsoleSender().sendMessage("Mundo: " + yaml.getString("Mundo-" + name.toLowerCase()));
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		Main.instance.getScoreboardBuilder().build(player);
	}
	
	public String getName() {
		return name;
	}
	
	public int getPlayerCount() {
		return players.size();
	}
	
	public Location getLocation() {

		Location l = new Location(Bukkit.getWorld(yaml.getString("Mundo-" + name.toLowerCase())), yaml.getInt("X-" +name.toLowerCase()) , yaml.getInt("Y-" +name.toLowerCase()), yaml.getInt("Z-" +name.toLowerCase()));
		l.setPitch(yaml.getInt("PITCH-" +name.toLowerCase()));
		l.setYaw(yaml.getInt("YAW-" +name.toLowerCase()));	
		return l;
	}
	public WarpHandle getHandler() {
		return handler;
	}
	
	public ItemStack getIcon() {
		return icon;
	}
	
	public boolean hasPlayer(String username) {
		return players.contains(username);
	}
}
	
