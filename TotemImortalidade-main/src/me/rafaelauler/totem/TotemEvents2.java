package me.rafaelauler.totem;




import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;










public class TotemEvents2 implements Listener {

	public static HashMap<String, Boolean> protector = new HashMap();	
	@EventHandler
	public void onAnvil(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (e.getInventory().getType() == InventoryType.ANVIL) {

            if(e.getSlotType() == InventoryType.SlotType.RESULT) {
ItemStack i2 = new ItemStack(Material.TOTEM_OF_UNDYING);
ItemMeta im = i2.getItemMeta();
im.setDisplayName("§e§oAmuleto da Imortalidade");
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§e§oAmuleto da Imortalidade") || e.getCurrentItem().getItemMeta().getDisplayName().equals("Amuleto da Imortalidade")) {
if (!player.hasPermission("totem.infinito")) {
player.sendMessage(ChatColor.RED + "Você não tem permissão para montar esse amuleto!");
e.setCancelled(true);
	return;
}
if (HelixCooldown.has(player.getName(), "totem")) {
	player.sendMessage(ChatColor.RED + "Aguarde " + HelixCooldown.getTime(player.getName(), "totem") + " segundos para criar o totem novamente!");
	e.setCancelled(true);
	return;
}
NamespacedKey key = new NamespacedKey(Main.plugin, "totem");
e.getCurrentItem().setItemMeta(im);
protector.put(player.getName(), true);
im.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, Math.PI);
player.sendMessage(ChatColor.RED + "Você criou um amuleto da imortalidade!");
                }
            }
        }
    }

@EventHandler
public void onTotem(EntityResurrectEvent e) {
	new BukkitRunnable() {

        @Override
        public void run() {
            
if (!(e.getEntity() instanceof Player)) {
	return;
}

Player p = (Player)e.getEntity();
if (!p.hasPermission("totem.infinito")) {
	return;
}
ItemStack i2 = new ItemStack(Material.TOTEM_OF_UNDYING);
ItemMeta im = i2.getItemMeta();
im.setDisplayName("§e§oAmuleto da Imortalidade");
NamespacedKey key = new NamespacedKey(Main.plugin, "totem");
im.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, Math.PI);
i2.setItemMeta(im);

if (!protector.containsKey(p.getName())) {
	p.sendMessage("SEU AMULETO DA IMORTALIDADE EXPIROU");
	new BukkitRunnable() {
        
        @Override
        public void run() {
        	HelixCooldown.delete(p.getName(), "totem");
        }}.runTaskLater(Main.plugin, 20 * 20 * 60);

        
	
	
	return;
}
            if (HelixCooldown.has(p.getName(), "totem")) {
            	e.setCancelled(true);
        		HelixCooldown.create(p.getName(), "totem", TimeUnit.MINUTES, 20);
        		p.sendMessage("SEU AMULETO DA IMORTALIDADE EXPIROU");
        		p.getInventory().removeItem(i2);
        		new BukkitRunnable() {
                 
        	        @Override
        	        public void run() {
        	        	HelixCooldown.delete(p.getName(), "totem");
((Player) e.getEntity()).getInventory().removeItem(i2);
        	        }}.runTaskLater(Main.plugin, 20 * 20 * 60);

        	        
        		
        		return;
            }
            else {
            	p.sendMessage(ChatColor.GREEN + "Você foi ressucitado!");
            	p.getInventory().addItem(i2);
            	new BukkitRunnable() {
                    
        	        @Override
        	        public void run() {
        	        	 if (!HelixCooldown.has(p.getName(), "totem")) {
            	HelixCooldown.create(p.getName(), "totem", TimeUnit.MINUTES, 20);
            	protector.remove(p.getName());
        	        }}}.runTaskLater(Main.plugin, 20 * 1 * 60);
            }
        }
    }.runTaskLater(Main.plugin, 1);

}

}
