// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.menus;

import br.com.stenox.training.utils.ItemCreator;
import org.bukkit.inventory.ItemStack;
import br.com.stenox.training.Main;
import br.com.stenox.training.warp.list.DamagerWarp;
import org.bukkit.Material;
import br.com.stenox.training.gamer.Gamer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import br.com.stenox.training.utils.menu.Menu;

public class DamagerMenu extends Menu
{
    private boolean cocoaInv;
    private boolean selectRecraft;
    
    public DamagerMenu() {
        this.selectRecraft = true;
    }
    
    @Override
    public String getMenuName() {
        return "Damager";
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
        final Player player = (Player)event.getWhoClicked();
        final Gamer gamer = Gamer.getGamer(player.getName());
        final ItemStack item = event.getCurrentItem();
        if (item.getType() == Material.getMaterial(351) && item.getDurability() == 3) {
            this.cocoaInv = true;
            this.selectRecraft = false;
            this.setMenuItems();
        }
        else if (item.getType() == Material.RED_MUSHROOM) {
            this.cocoaInv = false;
            this.selectRecraft = false;
            this.setMenuItems();
        }
        else if (item.getType() == Material.STAINED_GLASS_PANE) {
            final DamagerWarp warp = (DamagerWarp)Main.getInstance().getWarpController().search("Damager");
            if (item.getDurability() == 5) {
                warp.joinPlayer(gamer, 4, this.cocoaInv);
            }
            else if (item.getDurability() == 4) {
                warp.joinPlayer(gamer, 6, this.cocoaInv);
            }
            else if (item.getDurability() == 1) {
                warp.joinPlayer(gamer, 8, this.cocoaInv);
            }
            else if (item.getDurability() == 14) {
                warp.joinPlayer(gamer, 9, this.cocoaInv);
            }
            else if (item.getDurability() == 2) {
                warp.joinPlayer(gamer, -1, this.cocoaInv);
            }
        }
    }
    
    @Override
    public void setMenuItems() {
        this.inventory.clear();
        if (this.selectRecraft) {
            this.inventory.setItem(12, new ItemCreator().setType(Material.getMaterial(351)).setDurability(3).setName("§eInvent\u00e1rio de Cocoa").getStack());
            this.inventory.setItem(14, new ItemCreator().setType(Material.RED_MUSHROOM).setName("§eInvent\u00e1rio de Cogumelo").getStack());
        }
        else {
            this.inventory.setItem(11, new ItemCreator().setType(Material.STAINED_GLASS_PANE).setDurability(5).setName("§aDano baixo").setDescription("§a2\u2764").getStack());
            this.inventory.setItem(12, new ItemCreator().setType(Material.STAINED_GLASS_PANE).setDurability(4).setName("§eDano m\u00e9dio").setDescription("§e3\u2764").getStack());
            this.inventory.setItem(13, new ItemCreator().setType(Material.STAINED_GLASS_PANE).setDurability(1).setName("§6Dano alto").setDescription("§64\u2764").getStack());
            this.inventory.setItem(14, new ItemCreator().setType(Material.STAINED_GLASS_PANE).setDurability(14).setName("§cDano extremo").setDescription("§c4.5\u2764").getStack());
            this.inventory.setItem(15, new ItemCreator().setType(Material.STAINED_GLASS_PANE).setDurability(2).setName("§5Dano variado").setDescription("§52 - 4.5\u2764").getStack());
        }
    }
}
