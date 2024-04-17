package com.github.games647.changeskin.bukkit;

import net.helix.core.util.HelixCooldown;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SkinListener implements Listener {

    private static List<UUID> nicking = new ArrayList<>();

    public static void setNicking(Player player) {
        nicking.add(player.getUniqueId());
    }

    public static boolean isNicking(Player player) {
        return nicking.contains(player.getUniqueId());
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent evt) {
        if (evt.getWhoClicked() instanceof Player) {
            Player player = (Player) evt.getWhoClicked();
            ItemStack item = evt.getCurrentItem();
            Inventory inv = evt.getInventory();
            if (!inv.getTitle().equals("Menu de skins")) {
                return;
            }

            evt.setCancelled(true);

       if (evt.getCurrentItem().getType() == Material.BOOK_AND_QUILL) {
                    player.closeInventory();
           if (!player.hasPermission("cmd.skin")) {
               player.sendMessage(ChatColor.RED + "Você precisa ser " + ChatColor.GREEN + "VIP" + ChatColor.RED + " para escolher sua skin");
               return;
           }
                        if (HelixCooldown.inCooldown(player.getName(), "skin") && !player.hasPermission("kombo.cmd.report")) {
                            player.sendMessage(ChatColor.RED + "Espere " + HelixCooldown.getTime(player.getName(), "skin") + " segundos para trocar sua skin de novo");
                            return;
                        }

                        String messages$command$skin$event = " \n§7Digite o nick da sua nova skin ou digite Cancelar para cancelar a operação";
                        setNicking(player);
                        player.sendMessage(messages$command$skin$event);
                    } else if (evt.getCurrentItem().getType() == Material.DIAMOND) {
                        player.closeInventory();
                        player.chat("/skinupdate");
       } else if (evt.getCurrentItem().getType() == Material.BARRIER) {
           player.closeInventory();
           player.chat("/skin reset");
                    } else if (evt.getCurrentItem().getType() == Material.IRON_CHESTPLATE) {
                        player.closeInventory();
                        player.sendMessage(ChatColor.RED + "Utilize /skin <Nick> para mudar sua skin");
                    } else if (evt.getCurrentItem().getType() == Material.COOKIE) {
                        player.playSound(player.getLocation(), Sound.CLICK, 10F, 1F);
                    }
                }
            }






    @EventHandler(priority = EventPriority.MONITOR)
    public void AsyncPlayerChat (PlayerChatEvent evt) {
        Player player = evt.getPlayer();
        if (isNicking(player)) {
            evt.setCancelled(true);
            nicking.remove(player.getUniqueId());
            if (evt.getMessage().equalsIgnoreCase("cancelar")) {
                player.sendMessage(ChatColor.RED + "Mudança de skin cancelada.");
                return;
            }
            player.chat("/skin " + evt.getMessage());
            HelixCooldown.create(player.getName(), "skin", TimeUnit.SECONDS, 120);
            return;
        }
        if (isNicking(player)) {
            if (evt.getMessage().equalsIgnoreCase("cancelar"))
                evt.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerCommandPreprocess (PlayerCommandPreprocessEvent evt) {
        Player player = evt.getPlayer();
        if (isNicking(evt.getPlayer())) {
            evt.setCancelled(true);
            String messages$command$skin$event = " \n§7Digite o nick da sua nova skin ou digitar Cancelar para cancelar a operação";

            player.sendMessage(messages$command$skin$event);
        }
    }
}
