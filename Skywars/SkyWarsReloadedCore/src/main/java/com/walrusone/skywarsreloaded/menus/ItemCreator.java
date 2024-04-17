package com.walrusone.skywarsreloaded.menus;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemCreator {
	private static ItemCreator item = new ItemCreator();

	public ItemStack item(Material type, String name) {
		ItemStack item = new ItemStack(type);
		ItemMeta mItem = item.getItemMeta();
		mItem.setDisplayName(name);
		item.setItemMeta(mItem);
		return item;
	}

	public ItemStack itemAmount(Material type, int amount, String name) {
		ItemStack item = new ItemStack(type);
		item.setAmount(amount);
		ItemMeta mItem = item.getItemMeta();
		mItem.setDisplayName(name);
		item.setItemMeta(mItem);
		return item;
	}

	public ItemStack itemAmount(Material type, int id, int amount, String name) {
		ItemStack item = new ItemStack(type, amount, (short) id);
		ItemMeta mItem = item.getItemMeta();
		mItem.setDisplayName(name);
		item.setItemMeta(mItem);
		return item;
	}

	public ItemStack item(Material type, String name, List<String> lore) {
		ItemStack item = new ItemStack(type);
		ItemMeta mItem = item.getItemMeta();
		mItem.setDisplayName(name);
		mItem.setLore(lore);
		item.setItemMeta(mItem);
		return item;
	}

	public ItemStack item(Material type, int id, String name, List<String> lore) {
		ItemStack item = new ItemStack(type, 1, (short) id);
		ItemMeta mItem = item.getItemMeta();
		mItem.setDisplayName(name);
		mItem.setLore(lore);
		item.setItemMeta(mItem);
		return item;
	}

	public ItemStack item(Material type, int id, String name, List<String> lore, Enchantment enchant, int level) {
		ItemStack item = new ItemStack(type, 1, (short) id);
		ItemMeta mItem = item.getItemMeta();
		mItem.setDisplayName(name);
		mItem.addEnchant(enchant, level, true);
		mItem.setLore(lore);
		item.setItemMeta(mItem);
		return item;
	}

	public ItemStack item(Material type, String name, List<String> lore, Enchantment enchant, int level) {
		ItemStack item = new ItemStack(type);
		ItemMeta mItem = item.getItemMeta();
		mItem.setDisplayName(name);
		mItem.addEnchant(enchant, level, true);
		mItem.setLore(lore);
		item.setItemMeta(mItem);
		return item;
	}

	public ItemStack item(Material type, int id, String name, Enchantment enchant, int level) {
		ItemStack item = new ItemStack(type, 1, (short) id);
		ItemMeta mItem = item.getItemMeta();
		mItem.setDisplayName(name);
		mItem.addEnchant(enchant, level, true);
		item.setItemMeta(mItem);
		return item;
	}

	public ItemStack item(Material type, String name, Enchantment enchant, int level) {
		ItemStack item = new ItemStack(type);
		ItemMeta mItem = item.getItemMeta();
		mItem.setDisplayName(name);
		mItem.addEnchant(enchant, level, true);
		item.setItemMeta(mItem);
		return item;
	}

	public ItemStack item(Material type, int id, String name) {
		ItemStack item = new ItemStack(type, 1, (short) id);
		ItemMeta mItem = item.getItemMeta();
		mItem.setDisplayName(name);
		item.setItemMeta(mItem);
		return item;
	}

	public ItemStack item(Player player, Material type, int id, String name, List<String> lore, String permission) {
		if (player.hasPermission(permission)) {
			ItemStack item = new ItemStack(type, 1, (short) id);
			ItemMeta mItem = item.getItemMeta();
			mItem.setDisplayName(name);
			mItem.setLore(lore);
			item.setItemMeta(mItem);
			return item;
		}
		return new ItemStack(Material.AIR);
	}

	public ItemStack item(Player player, Material type, int id, String name, String permission) {
		if (player.hasPermission(permission)) {
			ItemStack item = new ItemStack(type, 1, (short) id);
			ItemMeta mItem = item.getItemMeta();
			mItem.setDisplayName(name);
			item.setItemMeta(mItem);
			return item;
		}
		return new ItemStack(Material.AIR);
	}

	public ItemStack item(Player player, Material type, String name, String permission) {
		if (player.hasPermission(permission)) {
			ItemStack item = new ItemStack(type);
			ItemMeta mItem = item.getItemMeta();
			mItem.setDisplayName(name);
			item.setItemMeta(mItem);
			return item;
		}
		return new ItemStack(Material.AIR);
	}

	public ItemStack item(Player player, Material type, String name, List<String> lore, String permission) {
		if (player.hasPermission(permission)) {
			ItemStack item = new ItemStack(type);
			ItemMeta mItem = item.getItemMeta();
			mItem.setDisplayName(name);
			mItem.setLore(lore);
			item.setItemMeta(mItem);
			return item;
		}
		return new ItemStack(Material.AIR);
	}
	
	public ItemStack item(Player player, Material type, String name, String[] lore, String permission) {
		if (player.hasPermission(permission)) {
			ItemStack item = new ItemStack(type);
			ItemMeta mItem = item.getItemMeta();
			mItem.setDisplayName(name);
			mItem.setLore(Arrays.asList(lore));
			item.setItemMeta(mItem);
			return item;
		}
		return new ItemStack(Material.AIR);
	}

	public ItemStack giveLeatherArmor(Material type, Color color) {
		ItemStack item = new ItemStack(type);
		LeatherArmorMeta itemm = (LeatherArmorMeta) item.getItemMeta();
		itemm.setColor(color);
		item.setItemMeta(itemm);
		return item;
	}

	public ItemStack itemUnbreakable(Material type, String name) {
		ItemStack item = new ItemStack(type);
		ItemMeta mItem = item.getItemMeta();
		mItem.setDisplayName(name);
		mItem.spigot().setUnbreakable(true);
		item.setItemMeta(mItem);
		return item;
	}

	public ItemStack itemUnbreakable(Material type, String name, List<String> lore) {
		ItemStack item = new ItemStack(type);
		ItemMeta mItem = item.getItemMeta();
		mItem.setDisplayName(name);
		mItem.setLore(lore);
		mItem.spigot().setUnbreakable(true);
		item.setItemMeta(mItem);
		return item;
	}

	public ItemStack itemUnbreakable(Material type, String name, Enchantment enchant, int level) {
		ItemStack item = new ItemStack(type);
		ItemMeta mItem = item.getItemMeta();
		mItem.setDisplayName(name);
		mItem.addEnchant(enchant, level, true);
		mItem.spigot().setUnbreakable(true);
		item.setItemMeta(mItem);
		return item;
	}

	public ItemStack itemSkull(String p, String name, List<String> lore) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta mItem = (SkullMeta) item.getItemMeta();
		mItem.setOwner(p);
		mItem.setDisplayName(name);
		mItem.setLore(lore);
		item.setItemMeta(mItem);
		return item;
	}

	public ItemStack itemSkull(String p, String name) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta mItem = (SkullMeta) item.getItemMeta();
		mItem.setOwner(p);
		mItem.setDisplayName(name);
		item.setItemMeta(mItem);
		return item;
	}

	public static ItemCreator getItem() {
		return item;
	}
}
