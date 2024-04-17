// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.listeners;

import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockBreakEvent;
import br.com.stenox.training.event.custom.TimeSecondEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.Location;
import org.bukkit.Effect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import java.util.Collection;
import org.bukkit.plugin.Plugin;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.EventPriority;
import br.com.stenox.training.utils.ActionBarAPI;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.block.Block;
import org.bukkit.Sound;
import org.bukkit.util.Vector;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import java.util.Iterator;
import br.com.stenox.training.utils.npc.NPC;
import org.bukkit.event.world.ChunkUnloadEvent;
import br.com.stenox.training.warp.Warp;
import net.helix.core.bukkit.HelixBukkit;
import net.helix.core.bukkit.account.HelixPlayer;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;

import org.bukkit.event.player.PlayerQuitEvent;
import br.com.stenox.training.utils.scoreboard.sidebar.Sidebar;
import java.util.Arrays;
import br.com.stenox.training.utils.scoreboard.ScoreboardWrapper;
import org.bukkit.GameMode;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import br.com.stenox.training.utils.LongManager;
import br.com.stenox.training.gamer.Gamer;
import br.com.stenox.training.Main;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import java.util.ArrayList;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.Random;

import org.bukkit.event.Listener;

public class PlayerListeners implements Listener
{
    private final List<ItemStack> drops;
    
    public PlayerListeners() {
        this.drops = new ArrayList<ItemStack>();
    }
    
    @EventHandler
    public void onPlayerLogin(final PlayerLoginEvent event) {
        final Player player = event.getPlayer();
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            return;
        }
        Gamer gamer = Main.getInstance().getGamerRepository().fetch(player.getName());
        if (gamer == null) {
            gamer = new Gamer(player.getName());
        }
        if (gamer.isBanned()) {
            final long banTime = gamer.getBanTime() / 1000L - System.currentTimeMillis() / 1000L;
            if (gamer.getBanTime() == -1L || banTime == -1L || banTime > 0L) {
                event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "§cVoc\u00ea est\u00e1 banido do servidor. \n \n §cMotivo: " + gamer.getBanReason() + " \n Expira em: " + LongManager.formatLong(gamer.getBanTime()));
                return;
            }
            gamer.unban();
        }
        Main.getInstance().getGamerController().create(gamer);
    }
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        event.setJoinMessage((String)null);
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        player.getInventory().clear();
        player.getInventory().setArmorContents((ItemStack[])null);
        player.setFireTicks(0);
        player.setMaxHealth(20.0);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.SURVIVAL);
        player.setLevel(0);
        player.setExp(0.0f);
        player.setAllowFlight(false);
        player.setFlying(false);
    	LuckPerms api2 = LuckPermsProvider.get();
        player.teleport(Main.SPAWN);
        final Gamer gamer = Gamer.getGamer(player.getName());
        final ScoreboardWrapper wrapper = new ScoreboardWrapper(player.getScoreboard());
        final Sidebar sidebar = wrapper.getSidebar();
        sidebar.setTitle("§b§lSLADE");
        HelixPlayer killerHelixPlayer = HelixBukkit.getInstance().getPlayerManager().getPlayer(player.getName());
        sidebar.setContent(Arrays.asList(" ", "Rank: " +  api2.getUserManager().getUser(player.getName()).getCachedData().getMetaData().getPrefix().replace("&", "§"), "Kills: " +  killerHelixPlayer.getPvp().getKills() , "Deaths: " +  killerHelixPlayer.getPvp().getDeaths() , "Jogadores: §7" + Bukkit.getOnlinePlayers().size(), " ", "Kit: §a" + gamer.getKitName(), " ", "Warp: §a" + gamer.getWarpName(), " ", "§esladepvp.com"));

    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        event.setQuitMessage((String)null);
        final Gamer gamer = Gamer.getGamer(player.getName());
        if (gamer == null) {
            return;
        }
        if (player.hasMetadata("combatPlayer")) {
            player.setHealth(0.0);
        }
        else if (gamer.getWarp() != null) {
            gamer.getWarp().removePlayer(player);
            gamer.setWarp(null);
        }
        Main.getInstance().getGamerController().remove(player.getName());
    }
    

        
    
    
    @EventHandler
    public void onFoodLevelChange(final FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onEntityDamage(final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player player = (Player)event.getEntity();
            final Gamer gamer = Gamer.getGamer(player.getName());
            if (gamer == null) {
                return;
            }
            if (gamer.getWarp() == null) {
                final EntityDamageEvent.DamageCause cause = event.getCause();
                if (!cause.equals((Object)EntityDamageEvent.DamageCause.LAVA) && !cause.equals((Object)EntityDamageEvent.DamageCause.FIRE_TICK) && !cause.equals((Object)EntityDamageEvent.DamageCause.FIRE)) {
                    event.setCancelled(true);
                }
                if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                    player.teleport(Main.SPAWN);
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerMoveEvent(final PlayerMoveEvent event) {
        final Block block = event.getFrom().getBlock().getRelative(BlockFace.DOWN);
        if (block.getType().equals((Object)Material.SLIME_BLOCK)) {
            event.getPlayer().setVelocity(new Vector(2, 1, 0));
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (!event.getAction().name().contains("RIGHT")) {
            return;
        }
        if (event.getItem() == null) {
            return;
        }
        if (event.getItem().getType() != Material.MUSHROOM_SOUP) {
            return;
        }
        event.setCancelled(true);
        final Player player = event.getPlayer();
        final double beforeHealth = player.getHealth();
        if (beforeHealth < player.getMaxHealth()) {
            if (beforeHealth + 7.0 > player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
                if (player.getFoodLevel() < 20) {
                    final int i = this.toInt(beforeHealth) + 7 - this.toInt(player.getMaxHealth());
                    player.setFoodLevel(Math.min(player.getFoodLevel() + i, 20));
                    player.setSaturation(3.0f);
                }
            }
            else {
                player.setHealth(player.getHealth() + 7.0);
            }
            ActionBarAPI.send(player, "§c+3,5 §4\u2764");
            player.setItemInHand(new ItemStack(Material.BOWL));
        }
        else if (player.getFoodLevel() < 20) {
            event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 7);
            player.setSaturation(3.0f);
            player.setItemInHand(new ItemStack(Material.BOWL));
        }
    }
    
    private int toInt(final Double d) {
        return d.intValue();
    }
    
    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent event) {
        event.setDeathMessage((String)null);
        final Player player = event.getEntity();
        if (!(event.getEntity().getKiller() instanceof Player)) {
        	return;
        }
        final Player killer = event.getEntity().getKiller();
        final Gamer gamer = Gamer.getGamer(player.getName());
        if (gamer.getWarp() != null) {
            gamer.getWarp().removePlayer(player);
            gamer.setWarp(null);
        }
        if (player.hasMetadata("combatPlayer")) {
            final Player combat = Bukkit.getPlayer(player.getMetadata("combatPlayer").get(0).asString());
            player.removeMetadata("combatPlayer", (Plugin)Main.getInstance());
            if (combat != null && combat.hasMetadata("combatPlayer")) {
                combat.removeMetadata("combatPlayer", (Plugin)Main.getInstance());
            }
        }
        final List<ItemStack> drops = new ArrayList<ItemStack>(event.getDrops());
        event.getDrops().clear();
        drops.forEach(drop -> {
            if (drop != null) {
                player.getWorld().dropItemNaturally(player.getLocation(), drop);
            }
            return;
        });
        player.setHealth(20.0);
        final Gamer gamer2;
        HelixPlayer killerHelixPlayer = HelixBukkit.getInstance().getPlayerManager().getPlayer(killer.getName());
        HelixPlayer killerHelixPlaye2r = HelixBukkit.getInstance().getPlayerManager().getPlayer(player.getName());
		killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 10.0f, 10.0f);
		killer.sendMessage("§3Você matou " + player.getName());
		Random random = new Random();
			int killerAddCoins = !killer.hasPermission("kombo.doublexp") ? random.nextInt(15) + 15 : random.nextInt(30) + 20;
			killerHelixPlayer.getPvp().addKills(1);
			killerHelixPlayer.getPvp().addCoins(killerAddCoins);
			killerHelixPlayer.getPvp().addKillstreak(1);
			killerHelixPlaye2r.getPvp().addDeaths(1);
			killer.sendMessage("§6§l[+] §6" + killerAddCoins + " coins" + (killer.hasPermission("kombo.doublexp") ? " [2X]" : "!"));
        Bukkit.getScheduler().runTaskLater((Plugin)Main.getInstance(), () -> {
            player.teleport(Main.SPAWN);
        }, 2L);
    }
    
    @EventHandler
    public void onPlayerDropItem(final PlayerDropItemEvent event) {
        final Gamer gamer = Gamer.getGamer(event.getPlayer().getName());
        if (gamer != null && gamer.getWarp() == null) {
            event.getItemDrop().remove();
        }
    }
    
    @EventHandler
    public void onItemSpawn(final ItemSpawnEvent event) {
        this.drops.add(event.getEntity().getItemStack());
        new BukkitRunnable() {
            public void run() {
                if (event.getEntity() != null && !event.getEntity().isDead() && PlayerListeners.this.drops.contains(event.getEntity().getItemStack())) {
                    final Location location = event.getEntity().getLocation();
                    location.getWorld().playEffect(location, Effect.WITCH_MAGIC, 5);
                    PlayerListeners.this.drops.remove(event.getEntity().getItemStack());
                    event.getEntity().remove();
                }
            }
        }.runTaskLaterAsynchronously((Plugin)Main.getInstance(), 60L);
    }
    @EventHandler
    public void a(final PlayerAchievementAwardedEvent e) {
    	e.setCancelled(true);
    }
    
    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            final Player player = (Player)event.getEntity();
            final Gamer gamer = Gamer.getGamer(player.getName());
            if (gamer.getWarp() != null && !gamer.getWarp().isPvp()) {
                event.setCancelled(true);
            }
            else {
                final Player damager = (Player)event.getDamager();
                if (damager.hasMetadata("combatPlayer") && !damager.getMetadata("combatPlayer").get(0).asString().equalsIgnoreCase(gamer.getName())) {
                    damager.sendMessage("§cVoc\u00ea est\u00e1 em combate com outro jogador.");
                    event.setCancelled(true);
                    return;
                }
                if (player.hasMetadata("combatPlayer") && !player.getMetadata("combatPlayer").get(0).asString().equalsIgnoreCase(damager.getName())) {
                    event.getDamager().sendMessage("§cEsse jogador j\u00e1 est\u00e1 em combate com: " + player.getMetadata("combatPlayer").get(0).asString());
                    event.setCancelled(true);
                }
                else {
                    player.setMetadata("combatPlayer", (MetadataValue)new FixedMetadataValue((Plugin)Main.getInstance(), (Object)damager.getName()));
                    damager.setMetadata("combatPlayer", (MetadataValue)new FixedMetadataValue((Plugin)Main.getInstance(), (Object)player.getName()));
                    player.setMetadata("combatTime", (MetadataValue)new FixedMetadataValue((Plugin)Main.getInstance(), (Object)System.currentTimeMillis()));
                    damager.setMetadata("combatTime", (MetadataValue)new FixedMetadataValue((Plugin)Main.getInstance(), (Object)System.currentTimeMillis()));
                }
            }
        }
    }
    
    @EventHandler
    public void onTimeSecond(final TimeSecondEvent event) {
        if (event.getType() == TimeSecondEvent.TimeType.SECONDS) {
            for (final Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasMetadata("combatTime") && player.hasMetadata("combatPlayer")) {
                    final double seconds = player.getMetadata("combatTime").get(0).asLong() / 1000.0 + 20.0 - System.currentTimeMillis() / 1000.0;
                    if (seconds > 0.0) {
                        continue;
                    }
                    player.removeMetadata("combatPlayer", (Plugin)Main.getInstance());
                }
            }
        	LuckPerms api2 = LuckPermsProvider.get();
            for (final Gamer gamer : Main.getInstance().getGamerController().getGamers()) {
            	Player player = gamer.getPlayer();
            	HelixPlayer killerHelixPlayer = HelixBukkit.getInstance().getPlayerManager().getPlayer(player.getName());
                gamer.getWrapper().getSidebar().setContent(Arrays.asList(" ", "Rank: " +  api2.getUserManager().getUser(player.getName()).getCachedData().getMetaData().getPrefix().replace("&", "§") , "Kills: " +  killerHelixPlayer.getPvp().getKills() , "Deaths: " +  killerHelixPlayer.getPvp().getDeaths(), "Jogadores: §7" + Bukkit.getOnlinePlayers().size(), " ", "Kit: §a" + gamer.getKitName(), " ", "Warp: §a" + gamer.getWarpName(), " ", "§esladepvp.com"));
            }
        }
    }
    
    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }
}
