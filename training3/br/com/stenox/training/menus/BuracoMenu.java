package br.com.stenox.training.menus;

//
//Decompiled by Procyon v0.5.36
//



import br.com.stenox.training.utils.ItemCreator;
import org.bukkit.inventory.ItemStack;
import br.com.stenox.training.Main;
import br.com.stenox.training.warp.list.AnchorWarp;
import br.com.stenox.training.warp.list.Buraco;

import org.bukkit.Material;
import br.com.stenox.training.gamer.Gamer;
import org.bukkit.event.inventory.InventoryClickEvent;
import br.com.stenox.training.utils.menu.Menu;

public class BuracoMenu extends Menu
{
 @Override
 public String getMenuName() {
     return "Buraco";
 }
 
 @Override
 public int getSlots() {
     return 27;
 }
 
 @Override
 public void handleMenu(final InventoryClickEvent event) {
     if (event.getCurrentItem() == null || event.getCurrentItem().getType() == null) {
         return;
     }
     event.setCancelled(true);
     final Gamer gamer = Gamer.getGamer(event.getWhoClicked().getName());
     final ItemStack item = event.getCurrentItem();
     boolean cocoaInv;
     if (item.getType() == Material.getMaterial(351) && item.getDurability() == 3) {
         cocoaInv = true;
     }
     else {
         if (item.getType() != Material.RED_MUSHROOM) {
             return;
         }
         cocoaInv = false;
     }
     final Buraco warp = (Buraco)Main.getInstance().getWarpController().search("Buraco");
     warp.joinPlayer(gamer, cocoaInv);
 }
 
 @Override
 public void setMenuItems() {
     this.inventory.setItem(12, new ItemCreator().setType(Material.getMaterial(351)).setDurability(3).setName("§eInvent\u00e1rio de Cocoa").getStack());
     this.inventory.setItem(14, new ItemCreator().setType(Material.RED_MUSHROOM).setName("§eInvent\u00e1rio de Cogumelo").getStack());
 }
}

