package com.github.kitpvp.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

import com.github.kitpvp.controllers.Control;
import com.github.kitpvp.controllers.PlayerControl;
import com.github.kitpvp.warpstype.WarpEnums;

public class API {
	
	public static HashMap<UUID, WarpEnums> map = new HashMap<>();
	public static ArrayList<String> fall = new ArrayList<String>();
	
	public static String getWarp(Player p){
		return  "" +map.get(p.getUniqueId());
	}
	
	public static void setWarp(Player p, WarpEnums type){
		
		if(!map.containsKey(p.getUniqueId())){
			map.put(p.getUniqueId(), type);
		}
		
	}
	public static void removeWarp(Player p){
		map.remove(p.getUniqueId());
	}
	
	public static void loadHotbar(Player p, WarpEnums type){
		Control playeritems = new PlayerControl();
		if(type.name().equalsIgnoreCase("fps")){
			playeritems.joinFPS(p);
		} if(type.getName().equalsIgnoreCase("spawn")){
			playeritems.joinSpawn();
		}
	}
	



public static void Atirar(Player p) {
	
	int y = 8;
	Block block = p.getLocation().getBlock().getRelative(0, -1, 0);
	if (block.getType() == Material.SPONGE) {
		Vector vector = new Vector(0, y, 0);
		p.setVelocity(vector);
		fall.remove(p.getName());
		p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 4.0F, 4.0F);
		p.getPlayer().getWorld().playEffect(p.getPlayer().getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
		fall.add(p.getName());
	}
}

public static void Atirar2(Player p) {
	final Location loc = p.getEyeLocation();

	final Vector sponge = p.getLocation().getDirection().multiply(3.8).setY(0.45);
	Block block = p.getLocation().getBlock().getRelative(0, -1, 0);
	if (block.getType() == Material.SLIME_BLOCK) {
		p.setVelocity(sponge);
		p.playEffect(loc, Effect.MOBSPAWNER_FLAMES, (Object) null);
		fall.remove(p.getName());
		p.playSound(p.getLocation(), Sound.SLIME_WALK, 4.0F, 4.0F);
		fall.add(p.getName());
	}
}

	public static ItemStack itemStack(Material material, String nome, String lore, Enchantment enchant,
			int levelEnchant) {
		ItemStack item = new ItemStack(material);
		item.addUnsafeEnchantment(enchant, levelEnchant);
		ItemMeta itemmeta = item.getItemMeta();
		List<String> iteml = new ArrayList<String>();
		iteml.add(lore);
		itemmeta.setLore(iteml);
		itemmeta.setDisplayName(nome);
		item.setItemMeta(itemmeta);
		return item;
	}

	public static ItemStack itemStack(Material material, String nome, int amount, int durability) {
		ItemStack item = new ItemStack(material, amount, (short) durability);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(nome);
		item.setItemMeta(itemmeta);
		return item;
	}

	public static ItemStack itemStack(Material material, String nome, List<String> lore, int amount, int durability) {
		ItemStack item = new ItemStack(material, amount, (short) durability);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(nome);
		itemmeta.setLore(lore);
		item.setItemMeta(itemmeta);

		return item;
	}

	public static void setItem(Player p, int i, ItemStack item) {
		i--;
		p.getInventory().setItem(i, item);
		p.updateInventory();
	}

	public static ItemStack itemStack(Material material, String nome) {
		ItemStack item = new ItemStack(material);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(nome);
		item.setItemMeta(itemmeta);
		return item;
	}

	public static ItemStack itemStack(Material material, String nome, List<String> lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setLore(lore);
		itemmeta.setDisplayName(nome);
		item.setItemMeta(itemmeta);
		return item;
	}

	public static ItemStack itemSkull(String p, String name, List<String> lore) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta mItem = (SkullMeta) item.getItemMeta();
		mItem.setOwner(p);
		mItem.setDisplayName(name);
		mItem.setLore(lore);
		item.setItemMeta(mItem);
		return item;
	}
}
