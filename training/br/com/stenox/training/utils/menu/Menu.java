// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.menu;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class Menu implements InventoryHolder
{
    protected PlayerMenuUtility playerMenuUtility;
    protected Inventory inventory;
    
    public Menu(final PlayerMenuUtility playerMenuUtility) {
        this.playerMenuUtility = playerMenuUtility;
    }
    
    public Menu() {
    }
    
    public abstract String getMenuName();
    
    public abstract int getSlots();
    
    public abstract void handleMenu(final InventoryClickEvent p0);
    
    public abstract void setMenuItems();
    
    public void open() {
        this.inventory = Bukkit.createInventory((InventoryHolder)this, this.getSlots(), this.getMenuName());
        this.setMenuItems();
        this.playerMenuUtility.getOwner().openInventory(this.inventory);
    }
    
    public void open(final PlayerMenuUtility playerMenuUtility) {
        this.inventory = Bukkit.createInventory((InventoryHolder)this, this.getSlots(), this.getMenuName());
        this.setMenuItems();
        playerMenuUtility.getOwner().openInventory(this.inventory);
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
}
