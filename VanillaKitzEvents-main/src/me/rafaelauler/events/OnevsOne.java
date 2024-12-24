package me.rafaelauler.events;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import net.wavemc.core.bukkit.item.ItemBuilder;


public class OnevsOne extends WarpHandle {
	
	@Override
	public void execute(Player player) {
		super.execute(player);
		player.getInventory().clear();
		
		player.getInventory().setHelmet(new ItemBuilder(Material.IRON_HELMET).toStack());
		player.getInventory().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).toStack());
		player.getInventory().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).toStack());
		player.getInventory().setBoots(new ItemBuilder(Material.IRON_BOOTS).toStack());
		
		
		  
		 
		  for (int i = 0; i < 33; i++) {
				player.getInventory().addItem(new ItemStack(Material.MUSHROOM_STEW));
			}
		  ItemStack espada = new ItemBuilder("§fEspada", Material.DIAMOND_SWORD).addFlags(ItemFlag.HIDE_ATTRIBUTES).
					addEnchant(Enchantment.SHARPNESS, 1)
					.nbt("cancel-drop")
					.toStack();
			player.getInventory().setItem(0, espada);
		  player.updateInventory();
	
        player.sendMessage("§aVocê agora está no evento 1v1!");
        player.sendMessage("§aSiga as instruções que serão dadas no chat!");
	}
}