package com.walrusone.skywarsreloaded.menus;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Arrays;

import com.walrusone.skywarsreloaded.SkyWarsReloaded;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;


public class ServidoresInventory implements Listener {

	public static void abrir(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 27, "§3Escolha um servidor:");
if (!SkyWarsReloaded.get().getConfig().getBoolean("HGDisabled")) {
		inventory.setItem(SkyWarsReloaded.get().getConfig().getInt("HGSLOT"),			ItemCreator.getItem().item(Material.WORKBENCH, "§3HARDCOREGAMES",						Arrays.asList("§8Modo de Jogo", "", "§fColete seus itens e", "§fseja o ultimo §c§lSOBREVIVENTE",							"", "§bClique para ir ao servidor")));
}
if (!SkyWarsReloaded.get().getConfig().getBoolean("AcademyDisabled")) {
	Manager.setItemGladiator(SkyWarsReloaded.get().getConfig().getString("IPACADEMY"), SkyWarsReloaded.get().getConfig().getInt("PORTACADEMY"), 55, SkyWarsReloaded.get().getConfig().getInt("ACADEMYSLOT"), inventory, "§3ACADEMY HG");
}
if (!SkyWarsReloaded.get().getConfig().getBoolean("ArenaDisabled")) {
		Manager.setItemPvP(SkyWarsReloaded.get().getConfig().getString("IPARENA"), SkyWarsReloaded.get().getConfig().getInt("PORTARENA"), 55, SkyWarsReloaded.get().getConfig().getInt("PVPSLOT"), inventory, "§3PVP");
}
if (!SkyWarsReloaded.get().getConfig().getBoolean("SkywarsDisabled")) {
	Manager.setItemSW(SkyWarsReloaded.get().getConfig().getString("IPSW"), SkyWarsReloaded.get().getConfig().getInt("PORTSW"), 55, SkyWarsReloaded.get().getConfig().getInt("SWSLOT"), inventory, "§3SKYWARS");
}
		player.openInventory(inventory);
		player.updateInventory();
	}

	public static void abrirHG(Player player) {
		Inventory inventory = Bukkit.createInventory(null, 27, "§3Escolha uma sala:");

		Manager.setItemHG(SkyWarsReloaded.get().getConfig().getString("IPHG1"), SkyWarsReloaded.get().getConfig().getInt("PORTHG1"), 55, 12, inventory, "§bA1");
		Manager.setItemHG(SkyWarsReloaded.get().getConfig().getString("IPHG2"), SkyWarsReloaded.get().getConfig().getInt("PORTHG2"), 55, 13, inventory, "§bA2");
		Manager.setItemHG(SkyWarsReloaded.get().getConfig().getString("IPHG3"), SkyWarsReloaded.get().getConfig().getInt("PORTHG3"), 55, 14, inventory, "§bA3");

		player.openInventory(inventory);
		player.updateInventory();
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getItem() == null) {
			return;
		}
		if ((event.getAction().toString().contains("RIGHT")) && (event.getItem().getType() == Material.COMPASS)
				&& (event.getItem().hasItemMeta())
				&& (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bServidores §7(Clique)"))) {
			abrir(player);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if ((event.getInventory().getName().equalsIgnoreCase("§3Escolha um servidor:")) && (event.getCurrentItem() != null)
				&& (event.getCurrentItem().getTypeId() != 0)) {
			event.setCancelled(true);
			if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§3HARDCOREGAMES")) {
				abrirHG(player);
			}
			if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§3PVP")) {
				Connect(player, "arena");
				player.sendMessage("§b§lCONNECT §fVocê está sendo conectado ao §aPvP");
				player.closeInventory();
			}
			if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§3ACADEMY HG")) {
				Connect(player, "academy");
				player.sendMessage("§a§lCONNECT §fVocê está sendo conectado ao §aAcademy");
				player.closeInventory();
			}
			if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§3SKYWARS")) {
				player.sendMessage("§a§lCONNECT §fVocê já está conectado ao §aSkywars");
				player.closeInventory();
			}
			if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§3POTPVP")) {
				Connect(player, "potpvp");
				player.sendMessage("§a§lCONNECT §fVocê está sendo conectado ao §apotpvp");
				player.closeInventory();
			}
		}
		if ((event.getInventory().getName().equalsIgnoreCase("§3Escolha uma sala:"))
				&& (event.getCurrentItem() != null) && (event.getCurrentItem().getTypeId() != 0)) {
			event.setCancelled(true);
			if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§bA1")) {
				player.closeInventory();
				player.sendMessage("§a§lCONNECT §fVocê está sendo conectado ao §ahg-1");
				Connect(player, "hg1");
			}
			if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§bA2")) {
				player.closeInventory();
				player.sendMessage("§a§lCONNECT §fVocê está sendo conectado ao §ahg-2");
				Connect(player, "hg2");
			}
			if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§bA3")) {
				player.closeInventory();
				player.sendMessage("§a§lCONNECT §fVocê está sendo conectado ao §ahg-3");
				Connect(player, "hg3");
			}
		}
	}

	private void Connect(Player player, String server) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("Connect");
			out.writeUTF(server);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		player.sendPluginMessage(SkyWarsReloaded.get(), "BungeeCord", b.toByteArray());
	}

}
