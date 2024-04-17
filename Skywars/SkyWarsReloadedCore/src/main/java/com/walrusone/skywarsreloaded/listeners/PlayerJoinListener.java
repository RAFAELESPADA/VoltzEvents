package com.walrusone.skywarsreloaded.listeners;

import com.walrusone.skywarsreloaded.SkyWarsReloaded;
import com.walrusone.skywarsreloaded.enums.GameType;
import com.walrusone.skywarsreloaded.game.GameMap;
import com.walrusone.skywarsreloaded.managers.MatchManager;
import com.walrusone.skywarsreloaded.managers.PlayerStat;
import com.walrusone.skywarsreloaded.menus.playeroptions.OptionsSelectionMenu;
import com.walrusone.skywarsreloaded.utilities.Util;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

public class PlayerJoinListener implements Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(final NPCLeftClickEvent event) {

        final Player player = event.getClicker();
if (event.getNPC() == CitizensAPI.getNPCRegistry().getById(1)) {
    Bukkit.dispatchCommand(player , "gui open jogar");
}
        NPC npc3 = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, ChatColor.GREEN + "Opções");
        if (event.getNPC() == CitizensAPI.getNPCRegistry().getById(2)) {
            new OptionsSelectionMenu(player);
        }
        }
        @EventHandler(priority = EventPriority.LOWEST)
        public void onJoin2(final NPCRightClickEvent event) {

            final Player player = event.getClicker();
            if (event.getNPC() == CitizensAPI.getNPCRegistry().getById(1)) {
               Bukkit.dispatchCommand(player , "gui open jogar");
            }
            if (event.getNPC() == CitizensAPI.getNPCRegistry().getById(2)) {
                new OptionsSelectionMenu(player);

            }
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(final PlayerJoinEvent event) {

        final Player player = event.getPlayer();
        ItemStack i = new ItemStack(Material.COMPASS);
        ItemMeta i2 = i.getItemMeta();
        i2.setDisplayName("§bServidores §7(Clique)");
        i.setItemMeta(i2);
player.getInventory().setItem(0 , i);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (SkyWarsReloaded.getCfg().getSpawn() != null && SkyWarsReloaded.getCfg().teleportOnJoin()) {
                    player.teleport(SkyWarsReloaded.getCfg().getSpawn());
                }
            }
        }.runTaskLater(SkyWarsReloaded.get(), 1);

        if (SkyWarsReloaded.getCfg().promptForResource()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.setResourcePack(SkyWarsReloaded.getCfg().getResourceLink());
                }
            }.runTaskLater(SkyWarsReloaded.get(), 20);
        }

        if (PlayerStat.getPlayerStats(player) != null) {
            PlayerStat.removePlayer(player.getUniqueId().toString());
        }

        if (!SkyWarsReloaded.getCfg().bungeeMode()) {
            for (GameMap gMap : GameMap.getMapsCopy()) {
                if (gMap.getCurrentWorld() != null && gMap.getCurrentWorld().equals(player.getWorld())) {
                    if (SkyWarsReloaded.getCfg().getSpawn() != null) {
                        player.teleport(SkyWarsReloaded.getCfg().getSpawn());
                    }

                    //-11.665 68.00000 0.302

                    player.teleport(new Location(Bukkit.getWorld("lobby") , 0.500, 68.00000, 0.500));
                }
            }
        }

        PlayerStat pStats = new PlayerStat(player);
        PlayerStat.getPlayers().add(pStats);
        pStats.updatePlayerIfInLobby(player);
        // Load player data
        pStats.loadStats(() -> {
            if (!postLoadStats(player)) return;

            SkyWarsReloaded.get().getUpdater().handleJoiningPlayer(player);
        });


    }

    /**
     * Handle bungeecord join
     * @param player The joining player
     * @return Whether the player was kicked when trying to join
     */
    public boolean postLoadStats(Player player) {
        // After stats are done loading, move to a game if in bungeecord mode
        if (SkyWarsReloaded.getCfg().bungeeMode()) {
            if (player != null) {
                if (!SkyWarsReloaded.getCfg().isLobbyServer()) {
                    Bukkit.getLogger().log(Level.WARNING, "Trying to let " + player.getName() + " join a game");

                    boolean joined = MatchManager.get().joinGame(player, GameType.ALL);
                    if (!joined) {
                        Bukkit.getLogger().log(Level.WARNING, "Failed to put " + player.getName() + " in a game");
                        if (SkyWarsReloaded.getCfg().debugEnabled()) {
                            Util.get().logToFile(ChatColor.YELLOW + "Couldn't find an arena for player " + player.getName() + ". Sending the player back to the skywars lobby.");
                        }
                        if (player.hasPermission("sw.admin")) {
                            player.sendMessage(ChatColor.RED +
                                    "Skywars encountered an issue while joining this bungeecord mode server.\n" +
                                    "However, since you have the sw.admin permissions, you will not be kicked to the lobby.");
                        } else {
                            SkyWarsReloaded.get().sendBungeeMsg(player, "Connect", SkyWarsReloaded.getCfg().getBungeeLobby());
                            kickPlayerIfStillOnline(player, 20);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // UTILS

    public void kickPlayerIfStillOnline(Player player, long ticks) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline()) player.kickPlayer("");
            }
        }.runTaskLater(SkyWarsReloaded.get(), ticks);
    }
}