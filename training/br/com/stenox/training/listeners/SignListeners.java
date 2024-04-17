// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.listeners;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.event.block.Action;
import org.bukkit.GameMode;
import org.bukkit.block.Sign;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.Listener;

public class SignListeners implements Listener
{
    @EventHandler
    public void onSignChange(final SignChangeEvent event) {
        if (event.getLine(0).equalsIgnoreCase("[sopa]")) {
            event.setLine(0, "§c-§6-§e-§a-§b-");
            event.setLine(1, "§b§lKONOHA");
            event.setLine(2, "§lSOPA");
            event.setLine(3, "§c-§6-§e-§a-§b-");
        }
        else if (event.getLine(0).equalsIgnoreCase("[recraft]")) {
            event.setLine(0, "§c-§6-§e-§a-§b-");
            event.setLine(1, "§b§lKONOHA");
            event.setLine(2, "§lRECRAFT");
            event.setLine(3, "§c-§6-§e-§a-§b-");
        }
    }
    
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getState() instanceof Sign) {
            if (event.getPlayer().getGameMode() == GameMode.CREATIVE && event.getAction() == Action.LEFT_CLICK_BLOCK) {
                return;
            }
            final Sign sign = (Sign)event.getClickedBlock().getState();
            final boolean soup = sign.getLine(2).equals("§lSOPA");
            if (soup) {
                final Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54, "Sopas");
                for (int i = 0; i < inventory.getSize(); ++i) {
                    inventory.setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
                }
                event.getPlayer().openInventory(inventory);
                return;
            }
            final boolean recraft = sign.getLine(2).equals("§lRECRAFT");
            if (recraft) {
                final Inventory inventory2 = Bukkit.createInventory((InventoryHolder)null, 54, "Recraft");
                int j = 0;
                for (int ii = 0; ii < 27; ++ii) {
                    if (j == 3) {
                        j = 0;
                    }
                    if (j == 0) {
                        inventory2.addItem(new ItemStack[] { new ItemStack(Material.BOWL, 64) });
                    }
                    else if (j == 1) {
                        inventory2.addItem(new ItemStack[] { new ItemStack(Material.RED_MUSHROOM, 64) });
                    }
                    else if (j == 2) {
                        inventory2.addItem(new ItemStack[] { new ItemStack(Material.BROWN_MUSHROOM, 64) });
                    }
                    ++j;
                }
                j = 0;
                for (int ii = 0; ii < 27; ++ii) {
                    if (j == 2) {
                        j = 0;
                    }
                    if (j == 0) {
                        inventory2.addItem(new ItemStack[] { new ItemStack(Material.BOWL, 64) });
                    }
                    else if (j == 1) {
                        inventory2.addItem(new ItemStack[] { new ItemStack(Material.getMaterial(351), 64, (short)3) });
                    }
                    ++j;
                }
                event.getPlayer().openInventory(inventory2);
            }
        }
    }
}
