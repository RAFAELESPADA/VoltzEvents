package com.walrusone.skywarsreloaded.menus;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Manager {

	public static void setItemHG(String ip, int porta, int tempo, final int slot, final Inventory inventory,
			String servidor) {
		CPing c = new CPing(ip, porta, tempo);
		if (!c.estaFechado()) {
			if (c.pegarMotd() != null) {
				String motd = c.pegarMotd();
				if (motd.toLowerCase().contains("inicia")) {
					inventory.setItem(slot, ItemCreator.getItem().item(Material.MUSHROOM_SOUP, servidor,
							Arrays.asList(" ", "§fEstado: §aIniciando", "§fJogadores: §a" + c.JogadoresOnline + "/" + c.JogadoresMaximo, "§fStatus: §a§lONLINE", " ")));
				} else if (motd.toLowerCase().contains("invencibilidade")) {
					inventory.setItem(slot, ItemCreator.getItem().item(Material.MUSHROOM_SOUP, servidor,
							Arrays.asList(" ", "§fEstado: §aInvencibilidade", "§fJogadores: §a" + c.JogadoresOnline + "/" + c.JogadoresMaximo, "§fStatus: §a§lONLINE", " ")));
				} else if (motd.toLowerCase().contains("tempo")) {
					inventory.setItem(slot, ItemCreator.getItem().item(Material.MUSHROOM_SOUP, servidor,
							Arrays.asList(" ", "§fEstado: §aJogo", "§fJogadores: §a" + c.JogadoresOnline + "/" + c.JogadoresMaximo, "§fStatus: §a§lONLINE", " ")));
				} else if (motd.toLowerCase().contains("server")) {
					inventory.setItem(slot, ItemCreator.getItem().item(Material.MUSHROOM_SOUP, servidor,
							Arrays.asList(" ", "§fEstado: §aIniciando", "§fJogadores: §a" + c.JogadoresOnline + "/" + c.JogadoresMaximo, "§fStatus: §a§lONLINE", " ")));
				}
			}
		} else {
			inventory.setItem(slot, ItemCreator.getItem().item(Material.REDSTONE, "§cOffline"));
		}
	}
	
	public static void setItemEvento(String ip, int porta, int tempo, final int slot, final Inventory inventory,
			String servidor) {
		CPing c = new CPing(ip, porta, tempo);
		if (!c.estaFechado()) {
			if (c.pegarMotd() != null) {
				String motd = c.pegarMotd();
				if (motd.toLowerCase().contains("inicia")) {
					inventory.setItem(slot, ItemCreator.getItem().item(Material.CAKE, servidor,
							Arrays.asList(" ", "§7Status: §eIniciando", " ")));
				} else if (motd.toLowerCase().contains("invencibilidade")) {
					inventory.setItem(slot, ItemCreator.getItem().item(Material.CAKE, servidor,
							Arrays.asList(" ", "§7Status: §eInvencibilidade", " ")));
				} else if (motd.toLowerCase().contains("tempo")) {
					inventory.setItem(slot, ItemCreator.getItem().item(Material.CAKE, servidor,
							Arrays.asList(" ", "§7Status: §eJogo", " ")));
				} else if (motd.toLowerCase().contains("server")) {
					inventory.setItem(slot, ItemCreator.getItem().item(Material.CAKE, servidor,
							Arrays.asList(" ", "§7Status: §eIniciando", " ")));
				}
			}
		}
	}

	public static void setItemPvP(String ip, int porta, int tempo, int slot, Inventory inventory, String servidor) {
		CPing c = new CPing(ip, porta, tempo);
		if (!c.estaFechado()) {
			inventory.setItem(slot, ItemCreator.getItem().item(Material.IRON_SWORD, servidor,
					Arrays.asList("§b§lModo: Simulator", "", "§fVenha jogar PVP", "§fem varias §e§lWARPS", "§fdiferentes!", "§fe DoubleKit", "§fJogadores: §a" + c.JogadoresOnline + "/" + c.JogadoresMaximo, "", "§bClique para ir ao servidor")));
		}
		else {
			inventory.setItem(slot, ItemCreator.getItem().item(Material.IRON_SWORD, servidor,
					Arrays.asList("§b§lModo: Simulator", "", "§fVenha jogar PVP", "§fem varias §e§lWARPS", "§fdiferentes!", "§fe DoubleKit", "§fJogadores: §a" + c.JogadoresOnline + "/" + c.JogadoresMaximo, "", "§cEsse servidor está offline")));
		}
	}
	public static void setItemSW(String ip, int porta, int tempo, int slot, Inventory inventory, String servidor) {
		CPing c = new CPing(ip, porta, tempo);
		if (!c.estaFechado()) {
			inventory.setItem(slot, ItemCreator.getItem().item(Material.GRASS, servidor,
					Arrays.asList("§8Modo: Competitivo", "", "§fVocê tem medo de altura?", "§fEntão skywars não é para você", "§fKits e Habilidades!", "§fGritos e muito mais", "§fJogadores: §a" + c.JogadoresOnline + "/" + c.JogadoresMaximo, "", "§bClique para ir ao servidor")));
		}
		else {
			inventory.setItem(slot, ItemCreator.getItem().item(Material.GRASS, servidor,
					Arrays.asList("§8Modo: Competitivo", "", "§fVocê tem medo de altura?", "§fEntão skywars não é para você", "§fKits e Habilidades!", "§fGritos e muito mais", "§fJogadores: §a" + c.JogadoresOnline + "/" + c.JogadoresMaximo, "", "§cEsse servidor está offline")));
		}
	}
	
	public static void setItemGladiator(String ip, int porta, int tempo, int slot, Inventory inventory, String servidor) {
		CPing c = new CPing(ip, porta, tempo);
		if (!c.estaFechado()) {
			inventory.setItem(slot, ItemCreator.getItem().item(Material.MUSHROOM_SOUP, servidor,
					Arrays.asList("§8Modo de Jogo", "", "§fTreine seu pvp no nosso academy", "§fTemos treino de hg e pvp", "§fLava Challenge e ect", "", "§fJogadores: §a" + c.pegarJogadoresOnline() + "/" + c.pegarJogadoresMaximo(), "", "§bClique para ir ao servidor")));
		}
			else {
				inventory.setItem(slot, ItemCreator.getItem().item(Material.MUSHROOM_SOUP, servidor,
						Arrays.asList("§8Modo de Jogo", "", "§fTreine seu pvp no nosso academy", "§fTemos treino de hg e pvp", "§fLava Challenge e ect", "", "§fJogadores: §a" + c.pegarJogadoresOnline() + "/" + c.pegarJogadoresMaximo(), "", "§cEsse servidor está offline")));
			}
		
	}

	public static String getTag(Player player) {
		return "§7???";
	}
}
