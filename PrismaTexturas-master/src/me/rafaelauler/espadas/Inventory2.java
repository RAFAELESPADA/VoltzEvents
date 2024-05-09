package me.rafaelauler.espadas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.chat.TextComponent;



public class Inventory2 implements Listener {
	public static Map<UUID, Integer> metad = new HashMap<>();
	public static Map<UUID, Integer> metad2 = new HashMap<>();
	public final char COLOR_CHAR = ChatColor.COLOR_CHAR;

	public static void openGUIPrincipal(Player player, Player target) {
        Inventory inv = Bukkit.createInventory(null, 54, Main.getPlugin().getConfig().getString("EspadaMenuNome"));
    
        
ItemStack visualItem = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§f").toStack();

		
			 
				inv.setItem(0, visualItem);
				inv.setItem(1, visualItem);
				inv.setItem(2, visualItem);
				inv.setItem(3, visualItem);
				inv.setItem(5, visualItem);
				inv.setItem(6, visualItem);
				inv.setItem(7, visualItem);
				inv.setItem(8, visualItem);
				
			
			for (int i = 45; i <= 53; i++) {
				inv.setItem(i, visualItem);
			}
			
			for (int i = 0; i <= 45; i += 9) {
				inv.setItem(i, visualItem);
			}
			
			for (int i = 8; i <= 53; i += 9) {
				inv.setItem(i, visualItem);
			}
			ArrayList rb1 = new ArrayList();
			rb1.add(ChatColor.translateAlternateColorCodes('&',Main.getPlugin().getConfig().getString("EspadaDescInvBlocked")));
			
			final boolean canEnchant = metad.containsValue(Main.getPlugin().getConfig().getInt("EspadaMeta-Data1")) && metad.containsKey(player.getUniqueId()) ? true : false;
			final boolean canEnchant2 = metad.containsValue(Main.getPlugin().getConfig().getInt("EspadaMeta-Data2")) && metad.containsKey(player.getUniqueId()) ? true : false;
			final boolean canEnchant3 = metad.containsValue(Main.getPlugin().getConfig().getInt("EspadaMeta-Data3")) && metad.containsKey(player.getUniqueId()) ? true : false;
			final boolean canEnchant4 = metad.containsValue(Main.getPlugin().getConfig().getInt("EspadaMeta-Data4")) && metad.containsKey(player.getUniqueId()) ? true : false;
			final boolean canEnchant5 = metad.containsValue(Main.getPlugin().getConfig().getInt("EspadaMeta-Data5")) && metad.containsKey(player.getUniqueId()) ? true : false;
			
			ArrayList r = new ArrayList();
			List<String> lore = colorList(Main.getPlugin().getConfig().getStringList("Espada1Desc"));
				for (String s : lore) {
					r.add(s);
					r.add(metad.containsValue(Main.getPlugin().getConfig().getInt("EspadaMeta-Data1")) && metad.containsKey(player.getUniqueId()) ? ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Selected")) : ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("NotSelected")));
					     
			}
				ArrayList r2 = new ArrayList();
				List<String> lore2 = colorList(Main.getPlugin().getConfig().getStringList("Espada2Desc"));
					for (String s : lore2) {
			r2.add(s);
			r2.add(metad.containsValue(Main.getPlugin().getConfig().getInt("EspadaMeta-Data2")) && metad.containsKey(player.getUniqueId()) ? ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Selected")) : ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("NotSelected")));
					}
			ArrayList r3 = new ArrayList();
			List<String> lore3 = colorList(Main.getPlugin().getConfig().getStringList("Espada3Desc"));
			for (String s : lore3) {
	r3.add(s);
			r3.add(metad.containsValue(Main.getPlugin().getConfig().getInt("EspadaMeta-Data3")) && metad.containsKey(player.getUniqueId()) ? ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Selected")) : ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("NotSelected")));
			}
			List<String> lore4 = colorList(Main.getPlugin().getConfig().getStringList("Espada4Desc"));

			ArrayList r4 = new ArrayList();
			for (String s : lore4) {
			r4.add(s);
			r4.add(metad.containsValue(Main.getPlugin().getConfig().getInt("EspadaMeta-Data4")) && metad.containsKey(player.getUniqueId()) ? ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Selected")) : ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("NotSelected")));
			
			
			}

			ArrayList r5 = new ArrayList();
			List<String> lore5 = colorList(Main.getPlugin().getConfig().getStringList("Espada5Desc"));
			for (String s : lore5) {
			r5.add(s);
			r5.add(metad.containsValue(Main.getPlugin().getConfig().getInt("EspadaMeta-Data5")) && metad.containsKey(player.getUniqueId()) ? ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Selected")) : ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("NotSelected")));
			}
        inv.setItem(4, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("LivroInfoPrincipal")), Material.BOOK)
				.nbt("cancel-drop").lore("§f" + Main.getPlugin().getConfig().getStringList("LivroInfoDesc"))
				.nbt("cancel-click")
				.nbt("main", "visual")
				.toStack())
		;
        if (player.hasPermission("espada.one")) {
        	if (canEnchant) {
        inv.setItem(10, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Espada1-NOME")), Material.NETHERITE_SWORD)
    				.nbt("cancel-drop")
    				.nbt("cancel-click").lore(r)
    				.nbt("espada", "one").custom(Main.getPlugin().getConfig().getInt("EspadaMeta-Data1")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
    				.toStack()
    		);
        	} else {
        		inv.setItem(10, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Espada1-NOME")), Material.NETHERITE_SWORD)
        				.nbt("cancel-drop")
        				.nbt("cancel-click").lore(r)
        				.nbt("espada", "one").custom(Main.getPlugin().getConfig().getInt("EspadaMeta-Data1")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addFlags(ItemFlag.HIDE_ENCHANTS)
        				.toStack()
        		);
        	}
        } else {
        	
            inv.setItem(10, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Espada1-NOME")), Material.BARRIER)
    				.nbt("cancel-drop")
    				.nbt("cancel-click").lore(rb1)
    				.nbt("espadablocked").custom(Main.getPlugin().getConfig().getInt("EspadaMeta-Data1")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
    				.toStack()
    		);	
        }
        if (player.hasPermission("espada.two")) {
        	if (canEnchant2) {
            inv.setItem(11, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Espada2-NOME")), Material.NETHERITE_SWORD)
    				.nbt("cancel-drop")
    				.nbt("cancel-click").lore(r2)
    				.nbt("espada", "two").custom(Main.getPlugin().getConfig().getInt("EspadaMeta-Data2")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
    				.toStack()
    		);
        	} else {
        		inv.setItem(11, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Espada2-NOME")), Material.NETHERITE_SWORD)
        				.nbt("cancel-drop")
        				.nbt("cancel-click").lore(r2)
        				.nbt("espada", "two").custom(Main.getPlugin().getConfig().getInt("EspadaMeta-Data2")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addFlags(ItemFlag.HIDE_ENCHANTS)
        				.toStack()
        		);
        	}
        } else {
        	inv.setItem(11, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Espada2-NOME")), Material.BARRIER)
    				.nbt("cancel-drop")
    				.nbt("cancel-click").lore(rb1)
    				.nbt("espadablocked").custom(Main.getPlugin().getConfig().getInt("EspadaMeta-Data2")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
    				.toStack()
    		);
        }
        if (player.hasPermission("espada.three")) {
        	if (canEnchant3) {
            inv.setItem(12, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Espada3-NOME")), Material.NETHERITE_SWORD)
    				.nbt("cancel-drop")
    				.nbt("cancel-click").lore(r3)
    				.nbt("espada", "three").custom(Main.getPlugin().getConfig().getInt("EspadaMeta-Data3")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
    				.toStack()
    		);
        	} else {
        		inv.setItem(12, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Espada3-NOME")), Material.NETHERITE_SWORD)
        				.nbt("cancel-drop")
        				.nbt("cancel-click").lore(r3)
        				.nbt("espada", "three").custom(Main.getPlugin().getConfig().getInt("EspadaMeta-Data3")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addFlags(ItemFlag.HIDE_ENCHANTS)
        				.toStack()
        		);
            }
        } else {
        	inv.setItem(12, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Espada3-NOME")), Material.BARRIER)
    				.nbt("cancel-drop")
    				.nbt("cancel-click").lore(rb1)
    				.nbt("espadablocked").custom(Main.getPlugin().getConfig().getInt("EspadaMeta-Data3")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
    				.toStack()
    		);
        }
        if (player.hasPermission("espada.four")) {
        	if (canEnchant4) {
            inv.setItem(13, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Espada4-NOME")), Material.NETHERITE_SWORD)
    				.nbt("cancel-drop")
    				.nbt("cancel-click").lore(r4)
    				.nbt("espada", "four").custom(Main.getPlugin().getConfig().getInt("EspadaMeta-Data4")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
    				.toStack()
    		);
        	} else {
        		inv.setItem(13, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Espada4-NOME")), Material.NETHERITE_SWORD)
        				.nbt("cancel-drop")
        				.nbt("cancel-click").lore(r4)
        				.nbt("espada", "four").custom(Main.getPlugin().getConfig().getInt("EspadaMeta-Data4")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addFlags(ItemFlag.HIDE_ENCHANTS)
        				.toStack()
        		);
        	}
        } else {
        	inv.setItem(13, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Espada4-NOME")), Material.BARRIER)
    				.nbt("cancel-drop")
    				.nbt("cancel-click").lore(rb1)
    				.nbt("espadablocked").custom(Main.getPlugin().getConfig().getInt("EspadaMeta-Data4")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
    				.toStack()
    		);
        
        }
        if (player.hasPermission("espada.five")) {
        	if (canEnchant5) {
            inv.setItem(14, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Espada5-NOME")), Material.NETHERITE_SWORD)
    				.nbt("cancel-drop")
    				.nbt("cancel-click").lore(r5)
    				.nbt("espada", "five").custom(Main.getPlugin().getConfig().getInt("EspadaMeta-Data5")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
    				.toStack()
    		);
        	} else {
        		  inv.setItem(14, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Espada5-NOME")), Material.NETHERITE_SWORD)
          				.nbt("cancel-drop")
          				.nbt("cancel-click").lore(r5)
          				.nbt("espada", "five").custom(Main.getPlugin().getConfig().getInt("EspadaMeta-Data5")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addFlags(ItemFlag.HIDE_ENCHANTS)
          				.toStack()
          		);
        	}
        } else {
        	inv.setItem(14, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Espada5-NOME")), Material.BARRIER)
    				.nbt("cancel-drop")
    				.nbt("cancel-click").lore(rb1)
    				.nbt("espadablocked").custom(Main.getPlugin().getConfig().getInt("EspadaMeta-Data5")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
    				.toStack()
    		);
        }
        inv.setItem(41, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("EspadaPrincipal")), Material.DIAMOND_SWORD)
				.nbt("cancel-drop")
				.nbt("cancel-click")
				.nbt("main", "espada").addEnchant(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ATTRIBUTES).addFlags(ItemFlag.HIDE_ENCHANTS)
				.toStack()
		);
        inv.setItem(40, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("MachadoPrincipal")), Material.DIAMOND_AXE)
				.nbt("cancel-drop")
				.nbt("cancel-click")
				.nbt("main", "machado").addFlags(ItemFlag.HIDE_ATTRIBUTES)
				.toStack()
		);
        inv.setItem(39, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("ArmaduraPrincipal")), Material.DIAMOND_CHESTPLATE)
				.nbt("cancel-drop")
				.nbt("cancel-click")
				.nbt("main", "armor").addFlags(ItemFlag.HIDE_ATTRIBUTES)
				.toStack()
		);
       
        target.openInventory(inv);
    }
	
    
    public static void openGUIMachado(Player player, Player target) {
        Inventory inv = Bukkit.createInventory(null, 54, Main.getPlugin().getConfig().getString("MachadoMenuNome"));

ItemStack visualItem = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("§f").toStack();
		
        inv.setItem(4, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("LivroInfoPrincipal2")), Material.BOOK)
				.nbt("cancel-drop").lore("§f" + Main.getPlugin().getConfig().getStringList("LivroInfoDesc2"))
				.nbt("cancel-click")
				.nbt("main", "visual")
				.toStack())
		;
		inv.setItem(0, visualItem);
		inv.setItem(1, visualItem);
		inv.setItem(2, visualItem);
		inv.setItem(3, visualItem);
		inv.setItem(5, visualItem);
		inv.setItem(6, visualItem);
		inv.setItem(7, visualItem);
		inv.setItem(8, visualItem);
		ArrayList rb1 = new ArrayList();
		rb1.add(ChatColor.translateAlternateColorCodes('&',Main.getPlugin().getConfig().getString("MachadoDescInvBlocked")));
	
		ArrayList r = new ArrayList();
		r.add(ChatColor.translateAlternateColorCodes('&',Main.getPlugin().getConfig().getString("Machado1Desc")));
		r.add(metad2.containsValue(Main.getPlugin().getConfig().getInt("MachadoMeta-Data1")) && metad2.containsKey(player.getUniqueId()) ? ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Selected")) : ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("NotSelected")));
		ArrayList r2 = new ArrayList();
		r2.add(ChatColor.translateAlternateColorCodes('&',Main.getPlugin().getConfig().getString("Machado2Desc")));
		r2.add(metad2.containsValue(Main.getPlugin().getConfig().getInt("MachadoMeta-Data2")) && metad2.containsKey(player.getUniqueId()) ? ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Selected")) : ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("NotSelected")));
		ArrayList r3 = new ArrayList();
		r3.add(ChatColor.translateAlternateColorCodes('&',Main.getPlugin().getConfig().getString("Machado3Desc")));
		r3.add(metad2.containsValue(Main.getPlugin().getConfig().getInt("MachadoMeta-Data3")) && metad2.containsKey(player.getUniqueId()) ? ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Selected")) : ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("NotSelected")));
		ArrayList r4 = new ArrayList();
		r4.add(ChatColor.translateAlternateColorCodes('&',Main.getPlugin().getConfig().getString("Machado4Desc")));
		r4.add(metad2.containsValue(Main.getPlugin().getConfig().getInt("MachadoMeta-Data4")) && metad2.containsKey(player.getUniqueId()) ? ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Selected")) : ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("NotSelected")));
		ArrayList r5 = new ArrayList();
		r5.add(ChatColor.translateAlternateColorCodes('&',Main.getPlugin().getConfig().getString("Machado5Desc")));
		r5.add(metad2.containsValue(Main.getPlugin().getConfig().getInt("MachadoMeta-Data5")) && metad2.containsKey(player.getUniqueId()) ? ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Selected")) : ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("NotSelected")));
   
		
		final boolean canEnchant = metad2.containsValue(Main.getPlugin().getConfig().getInt("MachadoMeta-Data1")) && metad2.containsKey(player.getUniqueId()) ? true : false;
		final boolean canEnchant2 = metad2.containsValue(Main.getPlugin().getConfig().getInt("MachadoMeta-Data2")) && metad2.containsKey(player.getUniqueId()) ? true : false;
		final boolean canEnchant3 = metad2.containsValue(Main.getPlugin().getConfig().getInt("MachadoMeta-Data3")) && metad2.containsKey(player.getUniqueId()) ? true : false;
		final boolean canEnchant4 = metad2.containsValue(Main.getPlugin().getConfig().getInt("MachadoMeta-Data4")) && metad2.containsKey(player.getUniqueId()) ? true : false;
		final boolean canEnchant5 = metad2.containsValue(Main.getPlugin().getConfig().getInt("MachadoMeta-Data5")) && metad2.containsKey(player.getUniqueId()) ? true : false;
		
	for (int i = 45; i <= 53; i++) {
		inv.setItem(i, visualItem);
	}
	
	for (int i = 0; i <= 45; i += 9) {
		inv.setItem(i, visualItem);
	}
	
	for (int i = 8; i <= 53; i += 9) {
		inv.setItem(i, visualItem);
	}
	
	if (player.hasPermission("machado.one")) {
		if (canEnchant) {
        inv.setItem(10, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Machado1-NOME")), Material.NETHERITE_AXE)
				.nbt("cancel-drop")
				.nbt("cancel-click").lore(r)
				.nbt("machado-one").custom(Main.getPlugin().getConfig().getInt("MachadoMeta-Data1")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
				.toStack()
		);
		}
		else {
			inv.setItem(10, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Machado1-NOME")), Material.NETHERITE_AXE)
					.nbt("cancel-drop")
					.nbt("cancel-click").lore(r)
					.nbt("machado-one").custom(Main.getPlugin().getConfig().getInt("MachadoMeta-Data1")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addFlags(ItemFlag.HIDE_ENCHANTS)
					.toStack()
			);
		}
	} else {
		inv.setItem(10, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Machado1-NOME")), Material.BARRIER)
				.nbt("cancel-drop")
				.nbt("cancel-click").lore(rb1)
				.nbt("machadoblocked").custom(Main.getPlugin().getConfig().getInt("MachadoMeta-Data1")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
				.toStack()
		);
	}
	if (player.hasPermission("machado.two")) {
		if (canEnchant2) {
        inv.setItem(11, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Machado2-NOME")), Material.NETHERITE_AXE)
				.nbt("cancel-drop")
				.nbt("cancel-click").lore(r2)
				.nbt("machado-two").custom(Main.getPlugin().getConfig().getInt("MachadoMeta-Data2")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
				.toStack()
		);
		}  else {
			inv.setItem(11, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Machado2-NOME")), Material.NETHERITE_AXE)
					.nbt("cancel-drop")
					.nbt("cancel-click").lore(r2)
					.nbt("machado-two").custom(Main.getPlugin().getConfig().getInt("MachadoMeta-Data2")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addFlags(ItemFlag.HIDE_ENCHANTS)
					.toStack());	
        }
	} else {
	    inv.setItem(11, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Machado2-NOME")), Material.BARRIER)
					.nbt("cancel-drop")
					.nbt("cancel-click").lore(rb1)
					.nbt("machadoblocked").custom(Main.getPlugin().getConfig().getInt("MachadoMeta-Data2")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
					.toStack()
			);	
	}
	if (player.hasPermission("machado.three")) {
		if (canEnchant3) {
        inv.setItem(12, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Machado3-NOME")), Material.NETHERITE_AXE)
				.nbt("cancel-drop")
				.nbt("cancel-click").lore(r3)
				.nbt("machado-three").custom(Main.getPlugin().getConfig().getInt("MachadoMeta-Data3")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
				.toStack()
		);
		} else {
			inv.setItem(12, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Machado3-NOME")), Material.NETHERITE_AXE)
					.nbt("cancel-drop")
					.nbt("cancel-click").lore(r3)
					.nbt("machado-three").custom(Main.getPlugin().getConfig().getInt("MachadoMeta-Data3")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addFlags(ItemFlag.HIDE_ENCHANTS)
					.toStack()
			);
		}
	} else {
		inv.setItem(12, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Machado3-NOME")), Material.BARRIER)
				.nbt("cancel-drop")
				.nbt("cancel-click").lore(rb1)
				.nbt("machadoblocked").custom(Main.getPlugin().getConfig().getInt("MachadoMeta-Data3")).addFlags(ItemFlag.HIDE_ATTRIBUTES).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
				.toStack()
		);
	}
	if (player.hasPermission("machado.four")) {
		if (canEnchant4) {
        inv.setItem(13, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Machado4-NOME")), Material.NETHERITE_AXE).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
				.nbt("cancel-drop")
				.nbt("cancel-click").lore(r4)
				.nbt("machado-four").custom(Main.getPlugin().getConfig().getInt("MachadoMeta-Data4")).addFlags(ItemFlag.HIDE_ATTRIBUTES)
				.toStack()
		);
		} else {
		    inv.setItem(13, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Machado4-NOME")), Material.NETHERITE_AXE).addFlags(ItemFlag.HIDE_ENCHANTS)
					.nbt("cancel-drop")
					.nbt("cancel-click").lore(r4)
					.nbt("machado-four").custom(Main.getPlugin().getConfig().getInt("MachadoMeta-Data4")).addFlags(ItemFlag.HIDE_ATTRIBUTES)
					.toStack()
			);
		}
	} else {
		inv.setItem(13, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Machado4-NOME")), Material.BARRIER).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
				.nbt("cancel-drop")
				.nbt("cancel-click").lore(rb1)
				.nbt("machadoblocked").custom(Main.getPlugin().getConfig().getInt("MachadoMeta-Data4")).addFlags(ItemFlag.HIDE_ATTRIBUTES)
				.toStack()
		);
	}
	if (player.hasPermission("machado.five")) {
		if (canEnchant5) {
        inv.setItem(14, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Machado5-NOME")), Material.NETHERITE_AXE).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
				.nbt("cancel-drop")
				.nbt("cancel-click").lore(r5)
				.nbt("machado-five").custom(Main.getPlugin().getConfig().getInt("MachadoMeta-Data5")).addFlags(ItemFlag.HIDE_ATTRIBUTES)
				.toStack()
		);
		} else {
			inv.setItem(14, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Machado5-NOME")), Material.NETHERITE_AXE).addFlags(ItemFlag.HIDE_ENCHANTS)
					.nbt("cancel-drop")
					.nbt("cancel-click").lore(r5)
					.nbt("machado-five").custom(Main.getPlugin().getConfig().getInt("MachadoMeta-Data5")).addFlags(ItemFlag.HIDE_ATTRIBUTES)
					.toStack()
			);
		}
	} else {
		inv.setItem(14, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("Machado5-NOME")), Material.BARRIER).addEnchantif(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ENCHANTS)
				.nbt("cancel-drop")
				.nbt("cancel-click").lore(rb1)
				.nbt("machadoblocked").custom(Main.getPlugin().getConfig().getInt("MachadoMeta-Data5")).addFlags(ItemFlag.HIDE_ATTRIBUTES)
				.toStack()
		);
	}
        inv.setItem(41, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("EspadaPrincipal")), Material.DIAMOND_SWORD)
				.nbt("cancel-drop")
				.nbt("cancel-click")
				.nbt("main", "espada").addFlags(ItemFlag.HIDE_ATTRIBUTES)
				.toStack()
		);
        inv.setItem(40, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("MachadoPrincipal")), Material.DIAMOND_AXE)
				.nbt("cancel-drop")
				.nbt("cancel-click")
				.nbt("main", "machado").addEnchant(Enchantment.DAMAGE_ALL, 1).addFlags(ItemFlag.HIDE_ATTRIBUTES).addFlags(ItemFlag.HIDE_ENCHANTS)
				.toStack()
		);
        inv.setItem(39, new ItemBuilder(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("ArmaduraPrincipal")), Material.DIAMOND_CHESTPLATE)
				.nbt("cancel-drop")
				.nbt("cancel-click")
				.nbt("main", "armor").addFlags(ItemFlag.HIDE_ATTRIBUTES)
				.toStack()
		);
       
        target.openInventory(inv);
    }
    public String translateHexColorCodes(String startTag, String endTag, String message)
    {
        final Pattern hexPattern = Pattern.compile(startTag + "([A-Fa-f0-9]{6})" + endTag);
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
        while (matcher.find())
        {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
                    );
        }
        return matcher.appendTail(buffer).toString();
    }
    public static boolean hasSword(final Player player) {
        for (final ItemStack item : player.getInventory().getContents()){
        	if (item != null) {
            if (item.getType().name().startsWith("DIAMOND_SWOR") || item.getType().name().startsWith("NETHERITE_SWOR")) {
                return true;
            }
        }
        }
        return false;
    }
    public static boolean hasAxe(final Player player) {
        for (final ItemStack item : player.getInventory().getContents()){
        	if (item != null) {
            if (item.getType().name().contains("AXE")) {
                return true;
            }
        }
        }
        return false;
    }
    
    @EventHandler
   	public void onInvClicvk4(PlayerJoinEvent event) {
    	if (metad.containsKey(event.getPlayer().getUniqueId())) {
    		Bukkit.getConsoleSender().sendMessage("PLAYER: " + event.getPlayer());
    		Bukkit.getConsoleSender().sendMessage("METADATA DA ESPADA DO PLAYER : " + metad.get(event.getPlayer().getUniqueId()));
    	}	else {
    		Bukkit.getConsoleSender().sendMessage("PLAYER: " + event.getPlayer() + " NÃO TEM METADATA NA ESPADA");
    		
    }
    	Bukkit.getConsoleSender().sendMessage("LISTA DE UUIDS (ESPADA): " + metad.toString());

    	Bukkit.getConsoleSender().sendMessage("LISTA DE METADATAS (ESPADA): " + metad.values());
	
    	if (metad2.containsKey(event.getPlayer().getUniqueId())) {
    		Bukkit.getConsoleSender().sendMessage("PLAYER: " + event.getPlayer());
    		Bukkit.getConsoleSender().sendMessage("METADATA DO MACHADO DO PLAYER : " + metad2.get(event.getPlayer().getUniqueId()));
    	}	else {
    		Bukkit.getConsoleSender().sendMessage("PLAYER: " + event.getPlayer() + " NÃO TEM METADATA NO MACHADO");
    		
    }
    	Bukkit.getConsoleSender().sendMessage("LISTA DE UUIDS (MACHADOS): " + metad2.toString());

    	Bukkit.getConsoleSender().sendMessage("LISTA DE METADATAS (MACHADOS): " + metad2.values());
	
}

    @EventHandler
   	public void onInvClick4bv(PlayerMoveEvent event) {
    	if (event.getPlayer().getWorld() == Bukkit.getWorld(Main.getPlugin().getConfig().getString("MundoBloqueado"))) {
    		return;
    	}
    	if (event.getPlayer().getWorld() == Bukkit.getWorld(Main.getPlugin().getConfig().getString("MundoBloqueado2"))) {
    		return;
    	}
    	if (event.getPlayer().getWorld() == Bukkit.getWorld(Main.getPlugin().getConfig().getString("MundoBloqueado3"))) {
    		return;
    	}
    		if (metad2.containsKey(event.getPlayer().getUniqueId())) {
	   	   			 for (final ItemStack item : event.getPlayer().getInventory().getContents()) {
	   	   			if (item != null) {
  	   				if (item.getType() == Material.NETHERITE_AXE || item.getType() == Material.DIAMOND_AXE) {
  	   				ItemMeta itemMeta = item.getItemMeta();
  	   			ArrayList r = new ArrayList();
  				r.add(ChatColor.translateAlternateColorCodes('&',Main.getPlugin().getConfig().getString("MachadoInventarioDesc")));
  	   				itemMeta.setCustomModelData(metad2.get(event.getPlayer().getUniqueId()));
  	   			itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("MachadoNoInv-NOME")));
  	   				itemMeta.setLore(r);
  	   				 item.setItemMeta(itemMeta);
  	   			 }
	   	   			}
	   	   			}
    		}	 else {

  	   			 for (final ItemStack item : event.getPlayer().getInventory().getContents()) {
  	   			if (item != null) {
	   				if (item.getType() == Material.NETHERITE_AXE || item.getType() == Material.DIAMOND_AXE) {
	   				ItemMeta itemMeta = item.getItemMeta();
	   				itemMeta.setCustomModelData(0);
	   				
	   				 item.setItemMeta(itemMeta);
	   	   			 }
  	   			}
  	   			 }
    		}
	   			
    	
    }
    @EventHandler
   	public void onInvClick4(PlayerMoveEvent event) {
    	if (event.getPlayer().getWorld() == Bukkit.getWorld(Main.getPlugin().getConfig().getString("MundoBloqueado"))) {
    		return;
    	}
    	if (event.getPlayer().getWorld() == Bukkit.getWorld(Main.getPlugin().getConfig().getString("MundoBloqueado2"))) {
    		return;
    	}
    	if (event.getPlayer().getWorld() == Bukkit.getWorld(Main.getPlugin().getConfig().getString("MundoBloqueado3"))) {
    		return;
    	}
    	ArrayList r = new ArrayList();
			r.add(ChatColor.translateAlternateColorCodes('&',Main.getPlugin().getConfig().getString("EspadaInventarioDesc")));
 			
   		if (metad.containsKey(event.getPlayer().getUniqueId())) {		
   					for (final ItemStack item : event.getPlayer().getInventory().getContents()) {
   						if (item != null) {
   					
   	   	   				ItemMeta itemMeta = item.getItemMeta();
   	   	   				if (item.getType() == Material.NETHERITE_SWORD || item.getType() == Material.DIAMOND_SWORD) {	
   	   	   				itemMeta.setCustomModelData(metad.get(event.getPlayer().getUniqueId()));
   	  	   				itemMeta.setLore(r);
   	   	   				itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("EspadaNoInv-NOME")));
   	   	   				item.setItemMeta(itemMeta);
   	   	   				}
   	   	   				}
   	   	   			 }
   		}else {
   			for (final ItemStack item : event.getPlayer().getInventory().getContents()) {
					
   			
   			if (item != null) {		
   				ItemMeta itemMeta = item.getItemMeta();
   			itemMeta.setCustomModelData(metad.get(event.getPlayer().getUniqueId()));
   			if (item.getType() == Material.NETHERITE_SWORD || item.getType() == Material.DIAMOND_SWORD) {	
   				itemMeta.setCustomModelData(0);
   				item.setItemMeta(itemMeta);
  	   							
   					}
   			}
   		}
   		}
   					}
   		

    @EventHandler
   	public void onInvClickvb4(PlayerMoveEvent event) {
    	
   		if (metad.containsKey(event.getPlayer().getUniqueId())) {		
   				for (final ItemStack item : event.getPlayer().getInventory().getContents()) {
   						if (item != null) {

   				   		ItemMeta itemMeta = item.getItemMeta();
   	   	   				if (item.getType() == Material.GOLDEN_SWORD) {	

   	   	     			itemMeta.setCustomModelData(metad.get(event.getPlayer().getUniqueId()));
   	   	   				item.setItemMeta(itemMeta);
   	   	   				}
   	   	   				}
   	   	   			 }
   		}else {
   			for (final ItemStack item : event.getPlayer().getInventory().getContents()) {
					
   			
   			if (item != null) {
   				ItemMeta itemMeta = item.getItemMeta();
   			itemMeta.setCustomModelData(metad.get(event.getPlayer().getUniqueId()));
   			if (item.getType() == Material.GOLDEN_SWORD) {	
   				itemMeta.setCustomModelData(0);
   				item.setItemMeta(itemMeta);
  	   				
   					}
   			}}}
   		}
	    @EventHandler
	   	public void onInvClickvbgb4(InventoryClickEvent event) {
	    	
	 	if (event.getWhoClicked().getWorld() == Bukkit.getWorld(Main.getPlugin().getConfig().getString("MundoBloqueado"))) {
		return;
	}
	if (event.getWhoClicked().getWorld() == Bukkit.getWorld(Main.getPlugin().getConfig().getString("MundoBloqueado2"))) {
		return;
	}
	if (event.getWhoClicked().getWorld() == Bukkit.getWorld(Main.getPlugin().getConfig().getString("MundoBloqueado3"))) {
		return;
	}
	if (metad2.containsKey(event.getWhoClicked().getUniqueId())) {
 			 for (final ItemStack item : event.getWhoClicked().getInventory().getContents()) {
 			if (item != null) {
			if (item.getType() == Material.NETHERITE_AXE || item.getType() == Material.DIAMOND_AXE) {
			ItemMeta itemMeta = item.getItemMeta();
		ArrayList r = new ArrayList();
		r.add(ChatColor.translateAlternateColorCodes('&',Main.getPlugin().getConfig().getString("MachadoInventarioDesc")));
			itemMeta.setCustomModelData(metad2.get(event.getWhoClicked().getUniqueId()));
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("MachadoNoInv-NOME")));
			itemMeta.setLore(r);
			 item.setItemMeta(itemMeta);
		 }
 			}
 			}
}	 else {

		 for (final ItemStack item : event.getWhoClicked().getInventory().getContents()) {
		if (item != null) {
			if (item.getType() == Material.NETHERITE_AXE || item.getType() == Material.DIAMOND_AXE) {
			ItemMeta itemMeta = item.getItemMeta();
			itemMeta.setCustomModelData(0);
			
			 item.setItemMeta(itemMeta);
 			 }
		}
		 }}
	    }
   	    @EventHandler
   	   	public void onInvClickvgb4(InventoryClickEvent event) {
   	    	
   	 	if (event.getWhoClicked().getWorld() == Bukkit.getWorld(Main.getPlugin().getConfig().getString("MundoBloqueado"))) {
    		return;
    	}
    	if (event.getWhoClicked().getWorld() == Bukkit.getWorld(Main.getPlugin().getConfig().getString("MundoBloqueado2"))) {
    		return;
    	}
    	if (event.getWhoClicked().getWorld() == Bukkit.getWorld(Main.getPlugin().getConfig().getString("MundoBloqueado3"))) {
    		return;
    	}
    	ArrayList r = new ArrayList();
			r.add(ChatColor.translateAlternateColorCodes('&',Main.getPlugin().getConfig().getString("EspadaInventarioDesc")));
 			
   		if (metad.containsKey(event.getWhoClicked().getUniqueId())) {		
   					for (final ItemStack item : event.getWhoClicked().getInventory().getContents()) {
   						if (item != null) {
   					
   	   	   				ItemMeta itemMeta = item.getItemMeta();
   	   	   				if (item.getType() == Material.NETHERITE_SWORD || item.getType() == Material.DIAMOND_SWORD) {	
   	   	   				itemMeta.setCustomModelData(metad.get(event.getWhoClicked().getUniqueId()));
   	  	   				itemMeta.setLore(r);
   	   	   				itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("EspadaNoInv-NOME")));
   	   	   				item.setItemMeta(itemMeta);
   	   	   				}
   	   	   				}
   	   	   			 }
   		}else {
   			for (final ItemStack item : event.getWhoClicked().getInventory().getContents()) {
					
   			
   			if (item != null) {		
   				ItemMeta itemMeta = item.getItemMeta();
   			itemMeta.setCustomModelData(metad.get(event.getWhoClicked().getUniqueId()));
   			if (item.getType() == Material.NETHERITE_SWORD || item.getType() == Material.DIAMOND_SWORD) {	
   				itemMeta.setCustomModelData(0);
   				item.setItemMeta(itemMeta);
  	   							
   					}
   			}
   	   		}}
   						
   					}				

   	    @EventHandler
   	   	public void onInvClickvgb4p(InventoryClickEvent event) {
   	    	
   	   		if (metad.containsKey(event.getWhoClicked().getUniqueId())) {		
   	   				for (final ItemStack item : event.getWhoClicked().getInventory().getContents()) {
   	   						if (item != null) {

   	   				   		ItemMeta itemMeta = item.getItemMeta();
   	   	   	   				if (item.getType() == Material.GOLDEN_SWORD) {	

   	   	   	     			itemMeta.setCustomModelData(metad.get(event.getWhoClicked().getUniqueId()));
   	   	   	   				item.setItemMeta(itemMeta);
   	   	   	   				}
   	   	   	   				}
   	   	   	   			 }
   	   		}else {
   	   			for (final ItemStack item : event.getWhoClicked().getInventory().getContents()) {
   						
   	   			
   	   			if (item != null) {
   	   				ItemMeta itemMeta = item.getItemMeta();
   	   			itemMeta.setCustomModelData(metad.get(event.getWhoClicked().getUniqueId()));
   	   			if (item.getType() == Material.GOLDEN_SWORD) {	
   	   				itemMeta.setCustomModelData(0);
   	   				item.setItemMeta(itemMeta);
   	  	   				
   	   					}
   	   			}}
   	   		}
   	   		}
   		
   		
   					
   		
   			
   		
    
    @EventHandler
   	public void onInvClivbcki4(InventoryClickEvent event) {
   		if (!(event.getView().getTitle().equals(Main.getPlugin().getConfig().getString("MachadoMenuNome")) || event.getView().getTitle().equals(Main.getPlugin().getConfig().getString("EspadaMenuNome")))) {
   			return;
   		}
   		if (event.getCurrentItem() == null) {
   			return;
   		}
		if (ItemBuilder.has(event.getCurrentItem(), "main", "espada")) {
			openGUIPrincipal((Player)event.getWhoClicked(), (Player)event.getWhoClicked());
		}
		if (ItemBuilder.has(event.getCurrentItem(), "main", "machado")) {
			openGUIMachado((Player)event.getWhoClicked(), (Player)event.getWhoClicked());
		}
   		event.setCancelled(true);
    }


    @EventHandler
	public void onInvClicki4(InventoryClickEvent event) {
		if (!event.getView().getTitle().equals(Main.getPlugin().getConfig().getString("EspadaMenuNome")) && !event.getView().getTitle().equals(Main.getPlugin().getConfig().getString("MachadoMenuNome"))) {
			return;
		}
		if (event.getCurrentItem() == null) {
			return;
		}
		Player p = (Player)event.getWhoClicked();
		event.setCancelled(true);
		if (ItemBuilder.has(event.getCurrentItem(), "espada", "one")) {			
			if (metad.containsValue(Main.getPlugin().getConfig().getInt("EspadaMeta-Data1")) &&metad.containsKey(event.getWhoClicked().getUniqueId())) {
				metad.remove(event.getWhoClicked().getUniqueId());
				event.setCancelled(true);
					openGUIPrincipal((Player)event.getWhoClicked(), (Player)event.getWhoClicked());
				event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaEspada-Removed")));
				p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);
						return;
			}
			ItemStack customItem = new ItemStack(Material.NETHERITE_SWORD);
			ItemMeta meta = customItem.getItemMeta();
			event.setCancelled(true);
	        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
			metad.put(event.getWhoClicked().getUniqueId(), Main.getPlugin().getConfig().getInt("EspadaMeta-Data1"));
			

			event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaEspada-Selected")).replace("%number%", "1"));
			p.playSound(p.getLocation(), Sound.ITEM_AXE_SCRAPE, 1.0f, 1.0f);
				openGUIPrincipal((Player)event.getWhoClicked(), (Player)event.getWhoClicked());
			return;
		}
		if (ItemBuilder.has(event.getCurrentItem(), "espada", "two")) {			
			if (metad.containsValue(Main.getPlugin().getConfig().getInt("EspadaMeta-Data2")) &&metad.containsKey(event.getWhoClicked().getUniqueId())) {
				metad.remove(event.getWhoClicked().getUniqueId());
				event.setCancelled(true);
					openGUIPrincipal((Player)event.getWhoClicked(), (Player)event.getWhoClicked());
				event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaEspada-Removed")));

				p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);
				return;
			}
			metad.put(event.getWhoClicked().getUniqueId(), Main.getPlugin().getConfig().getInt("EspadaMeta-Data2"));

			event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaEspada-Selected")).replace("%number%", "2"));
			p.playSound(p.getLocation(), Sound.ITEM_AXE_SCRAPE, 1.0f, 1.0f);
				openGUIPrincipal((Player)event.getWhoClicked(), (Player)event.getWhoClicked());
			return;
		}
		if (ItemBuilder.has(event.getCurrentItem(), "espada", "three")) {	
			if (metad.containsValue(Main.getPlugin().getConfig().getInt("EspadaMeta-Data3")) &&metad.containsKey(event.getWhoClicked().getUniqueId())) {
			metad.remove(event.getWhoClicked().getUniqueId());
			event.setCancelled(true);
					openGUIPrincipal((Player)event.getWhoClicked(), (Player)event.getWhoClicked());
				event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaEspada-Removed")));

				p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);
				return;
			}
			metad.put(event.getWhoClicked().getUniqueId(), Main.getPlugin().getConfig().getInt("EspadaMeta-Data3"));

			event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaEspada-Selected")).replace("%number%", "3"));
				openGUIPrincipal((Player)event.getWhoClicked(), (Player)event.getWhoClicked());

				p.playSound(p.getLocation(), Sound.ITEM_AXE_SCRAPE, 1.0f, 1.0f);
			return;
		}
		if (ItemBuilder.has(event.getCurrentItem(), "espada", "four")) {	
			if (metad.containsValue(Main.getPlugin().getConfig().getInt("EspadaMeta-Data4")) &&metad.containsKey(event.getWhoClicked().getUniqueId())) {
		
			metad.remove(event.getWhoClicked().getUniqueId());
			event.setCancelled(true);
					openGUIPrincipal((Player)event.getWhoClicked(), (Player)event.getWhoClicked());
				event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaEspada-Removed")));

				p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);
				return;
			}
			metad.put(event.getWhoClicked().getUniqueId(), Main.getPlugin().getConfig().getInt("EspadaMeta-Data4"));
			event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaEspada-Selected")).replace("%number%", "4"));
				openGUIPrincipal((Player)event.getWhoClicked(), (Player)event.getWhoClicked());

				p.playSound(p.getLocation(), Sound.ITEM_AXE_SCRAPE, 1.0f, 1.0f);
			return;
		}
		if (ItemBuilder.has(event.getCurrentItem(), "espada", "five")) {			
			if (metad.containsValue(Main.getPlugin().getConfig().getInt("EspadaMeta-Data5")) && metad.containsKey(event.getWhoClicked().getUniqueId())) {

				metad.remove(event.getWhoClicked().getUniqueId(), Main.getPlugin().getConfig().getInt("EspadaMeta-Data5"));

				p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);
				event.setCancelled(true);
					openGUIPrincipal((Player)event.getWhoClicked(), (Player)event.getWhoClicked());
				event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaEspada-Removed")));
				return;
			}
			metad.put(event.getWhoClicked().getUniqueId(), Main.getPlugin().getConfig().getInt("EspadaMeta-Data5"));

			event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaEspada-Selected")).replace("%number%", "5"));
				openGUIPrincipal((Player)event.getWhoClicked(), (Player)event.getWhoClicked());

				p.playSound(p.getLocation(), Sound.ITEM_AXE_SCRAPE, 1.0f, 1.0f);
			return;
		}
		if (ItemBuilder.has(event.getCurrentItem(), "espadablocked")) {
			event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',Main.getPlugin().getConfig().getString("EspadaBloqueada")));

			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
			return;
		}
		if (ItemBuilder.has(event.getCurrentItem(), "machadoblocked")) {
			event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&',Main.getPlugin().getConfig().getString("MachadoBloqueado")));

			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
			return;
		}
		if (ItemBuilder.has(event.getCurrentItem(), "main", "visual")) {
			List<String> lore = colorList(Main.getPlugin().getConfig().getStringList("LivroMessage"));
			if (lore != null) {
				for (String s : lore) {
			     p.sendMessage(s);
			}

			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1.0f, 1.0f);
			return;
		}

		if (ItemBuilder.has(event.getCurrentItem(), "machado-one")) {			
			if (metad2.containsValue(Main.getPlugin().getConfig().getInt("MachadoMeta-Data1")) &&metad2.containsKey(event.getWhoClicked().getUniqueId())) {
				metad2.remove(event.getWhoClicked().getUniqueId());
				event.setCancelled(true);
				openGUIMachado((Player)event.getWhoClicked(), (Player)event.getWhoClicked());
				p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);
				event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaMachado-Removed")));
				return;
			}
			event.setCancelled(true);	

			p.playSound(p.getLocation(), Sound.ITEM_AXE_SCRAPE, 1.0f, 1.0f);
			metad2.put(event.getWhoClicked().getUniqueId(), Main.getPlugin().getConfig().getInt("MachadoMeta-Data1"));

			event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaMachado-Selected")).replace("%number%", "1"));
			openGUIMachado((Player)event.getWhoClicked(), (Player)event.getWhoClicked());return;		
		}
		if (ItemBuilder.has(event.getCurrentItem(), "machado-two")) {			
			if (metad2.containsValue(Main.getPlugin().getConfig().getInt("MachadoMeta-Data2")) &&metad2.containsKey(event.getWhoClicked().getUniqueId())) {
				metad2.remove(event.getWhoClicked().getUniqueId());
				event.setCancelled(true);
				openGUIMachado((Player)event.getWhoClicked(), (Player)event.getWhoClicked());
				p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);

				event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaMachado-Removed")));
				return;
			}
event.setCancelled(true);
			
			metad2.put(event.getWhoClicked().getUniqueId(), Main.getPlugin().getConfig().getInt("MachadoMeta-Data2"));
			event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaMachado-Selected")).replace("%number%", "2"));

			p.playSound(p.getLocation(), Sound.ITEM_AXE_SCRAPE, 1.0f, 1.0f);
			openGUIMachado((Player)event.getWhoClicked(), (Player)event.getWhoClicked());return;
		}
		if (ItemBuilder.has(event.getCurrentItem(), "machado-three")) {			
			if (metad2.containsValue(Main.getPlugin().getConfig().getInt("MachadoMeta-Data3")) &&metad2.containsKey(event.getWhoClicked().getUniqueId())) {
				metad2.remove(event.getWhoClicked().getUniqueId());
				event.setCancelled(true);

				p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);
				openGUIMachado((Player)event.getWhoClicked(), (Player)event.getWhoClicked());
				
				event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaMachado-Removed")));

				return;
			}
event.setCancelled(true);
			metad2.put(event.getWhoClicked().getUniqueId(), Main.getPlugin().getConfig().getInt("MachadoMeta-Data3"));

			event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaMachado-Selected")).replace("%number%", "3"));
			p.playSound(p.getLocation(), Sound.ITEM_AXE_SCRAPE, 1.0f, 1.0f);
			openGUIMachado((Player)event.getWhoClicked(), (Player)event.getWhoClicked());return;
		}
		if (ItemBuilder.has(event.getCurrentItem(), "machado-four")) {			
			if (metad2.containsValue(Main.getPlugin().getConfig().getInt("MachadoMeta-Data4")) &&metad2.containsKey(event.getWhoClicked().getUniqueId())) {
				metad2.remove(event.getWhoClicked().getUniqueId());
				event.setCancelled(true);
				openGUIMachado((Player)event.getWhoClicked(), (Player)event.getWhoClicked());
				p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);

				event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaMachado-Removed")));
				return;
			}
event.setCancelled(true);
			
			metad2.put(event.getWhoClicked().getUniqueId(), Main.getPlugin().getConfig().getInt("MachadoMeta-Data4"));

			event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaMachado-Selected")).replace("%number%", "4"));
			p.playSound(p.getLocation(), Sound.ITEM_AXE_SCRAPE, 1.0f, 1.0f);
			openGUIMachado((Player)event.getWhoClicked(), (Player)event.getWhoClicked());return;
		}
		if (ItemBuilder.has(event.getCurrentItem(), "limpar")) {			
			event.setCancelled(true);

			p.playSound(p.getLocation(), Sound.ITEM_AXE_SCRAPE, 1.0f, 1.0f);		
						metad2.remove(event.getWhoClicked().getUniqueId());
						metad.remove(event.getWhoClicked().getUniqueId());
						event.getWhoClicked().sendMessage(ChatColor.GREEN + "Texturas removidas e retornadas ao padrão.");
						return;
					}
		if (metad2.containsValue(Main.getPlugin().getConfig().getInt("MachadoMeta-Data5")) &&ItemBuilder.has(event.getCurrentItem(), "machado-five")) {			
			if (metad2.containsKey(event.getWhoClicked().getUniqueId())) {
				metad2.remove(event.getWhoClicked().getUniqueId());
				event.setCancelled(true);
				openGUIMachado((Player)event.getWhoClicked(), (Player)event.getWhoClicked());
				p.playSound(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1.0f, 1.0f);

				event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaMachado-Removed")));
				return;
			}

			p.playSound(p.getLocation(), Sound.ITEM_AXE_SCRAPE, 1.0f, 1.0f);
			metad2.put(event.getWhoClicked().getUniqueId(), Main.getPlugin().getConfig().getInt("MachadoMeta-Data5"));

			event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getConfig().getString("TexturaMachado-Selected")).replace("%number%", "5"));	openGUIMachado((Player)event.getWhoClicked(), (Player)event.getWhoClicked());
			return;
		}
		event.setCancelled(true);
		}
    }
    	
	
    
	

    public static List<String> colorList(List<String> list) {
        List<String> colored = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            colored.set(i, color(list.get(i)));
        }
        return colored;
    }
    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static ItemStack editItemStack(ItemStack itemStack, String name, List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getPlayerSkull(String name) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        meta.setOwner(name);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

public static ItemStack getCustomItemStack(Material material, String name, String lore) {
    ItemStack itemStack = new ItemStack(material);
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.setDisplayName(name);
    if (lore != null) {
        List<String> l = Collections.singletonList(lore);
        itemMeta.setLore(l);
    }
    itemStack.setItemMeta(itemMeta);
    return itemStack;
}

public static ItemStack getCustomItemStack(Material material, String name, List<String> lore) {
    ItemStack itemStack = new ItemStack(material);
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.setDisplayName(name);
    itemMeta.setLore(lore);
    itemStack.setItemMeta(itemMeta);
    return itemStack;
}
}


