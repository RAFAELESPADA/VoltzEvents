// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.listeners;

import org.bukkit.event.EventHandler;
import br.com.stenox.training.utils.menu.Menu;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;

public class MenuListeners implements Listener
{
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof Menu) {
            final Menu menu = (Menu)event.getInventory().getHolder();
            menu.handleMenu(event);
        }
    }
}
