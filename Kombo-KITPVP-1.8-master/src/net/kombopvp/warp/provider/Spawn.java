package net.kombopvp.warp.provider;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scoreboard.DisplaySlot;

import net.helix.core.util.HelixCooldown;
import net.kombopvp.pvp.KomboPvP2;
import net.kombopvp.pvp.cooldown2.WaveCooldown2;
import net.kombopvp.pvp.kit.Habilidade;
import net.kombopvp.pvp.kit.Habilidade2;
import net.kombopvp.pvp.kit.KitManager;
import net.kombopvp.pvp.kit.KitManager2;
import net.kombopvp.pvp.kit.provider.Ninja;
import net.kombopvp.pvp.kit.provider.WaveActionBar;
import net.kombopvp.pvp.listener.Jump;
import net.kombopvp.pvp.listener.PlayerDeathListener;
import net.kombopvp.pvp.warp.WarpHandle;
import net.wavemc.core.bukkit.WaveBukkit;
import net.wavemc.core.bukkit.api.ActionBar;
import net.wavemc.core.bukkit.item.ItemBuilder;

public class Spawn extends WarpHandle {
	Path path1 = Paths.get(Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/plugins/WaveCore/", "warps.yml");
	File file = new File(path1.toAbsolutePath().toString());
	YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
	private ArrayList<String> fall = new ArrayList<String>();
  public void execute(Player player) {
	  Location l2 = new Location(Bukkit.getWorld(yaml.getString("Mundo-spawn")), yaml.getInt("X-spawn") , yaml.getInt("Y-spawn"), yaml.getInt("Z-spawn"));
   
    player.teleport(l2);
    player.getInventory().clear();
    if (Jump.recebeu.containsKey(player.getName())) {
    Jump.recebeu.remove(player.getName());
    }
    Jump.caiu.remove(player.getName());
 	HelixCooldown.delete(player.getName(), "meteor");
    WaveActionBar.send(player, "");
    WaveCooldown2.removeCooldown(player, KitManager2.getPlayer(player.getName()).getkit2().getName());
    net.kombopvp.pvp.cooldown1.WaveCooldown2.removeCooldown(player, KitManager.getPlayer(player.getName()).getKit().getName());
    player.getInventory().setArmorContents(null);
    player.setGameMode(GameMode.ADVENTURE);
    player.setMaxHealth(20.0D);
    
    if (net.kombopvp.kit2.Ninja.map.containsValue(player.getName())) {
    	net.kombopvp.kit2.Ninja.map.entrySet().removeIf(entry -> entry.getValue().equalsIgnoreCase(player.getName()));
	}
    if (Ninja.map.containsValue(player.getName())) {
    	Ninja.map.entrySet().removeIf(entry -> entry.getValue().equalsIgnoreCase(player.getName()));
	}
    if (Habilidade.ContainsAbility(player)) {
		Habilidade.removeAbility(player);
		}
    if (Habilidade2.ContainsAbility(player)) {
		Habilidade2.removeAbility(player);
		}
    if (Duels.protector.containsKey(player.getName())) {
    Duels.protector.remove(player.getName());
    }
    player.setHealth(player.getMaxHealth());
    player.getActivePotionEffects().forEach(potion -> player.removePotionEffect(potion.getType()));
    player.setFireTicks(0);
    player.setFoodLevel(20);
    player.getInventory().setHeldItemSlot(0);
    player.getInventory().setItem(0, (new ItemBuilder("§aKits 1", Material.CHEST))
        .nbt("spawn-item", "kits")
        .nbt("cancel-drop")
        .nbt("cancel-click")
        .toStack());
    player.getInventory().setItem(1, (new ItemBuilder("§aKits 2", Material.CHEST))
            .nbt("spawn-item", "kits2")
            .nbt("cancel-drop")
            .nbt("cancel-click")
            .toStack());
    player.getInventory().setItem(2, (new ItemBuilder("§aLoja", Material.EMERALD))
            .nbt("spawn-item", "loja")
            .nbt("cancel-drop")
            .nbt("cancel-click")
            .toStack());
    player.getInventory().setItem(4, (new ItemBuilder("§aKit Díario", Material.STORAGE_MINECART))
            .nbt("spawn-item", "kitdiario")
            .nbt("cancel-drop")
            .nbt("cancel-click")
            .toStack());
    player.getInventory().setItem(7, (new ItemBuilder("§aExtras", Material.REDSTONE_COMPARATOR))
            .nbt("spawn-item", "extras")
            .nbt("cancel-drop")
            .nbt("cancel-click")
            .toStack());
    player.getInventory().setItem(8, (new ItemBuilder("§aVoltar ao Lobby", Material.NETHER_STAR))
            .nbt("spawn-item", "voltar")
            .nbt("cancel-drop")
            .nbt("cancel-click")
            .toStack());
    
    if (PlayerDeathListener.lastKit.containsKey(player.getName())) {
    	KitManager.getPlayer(player.getName()).setKit(PlayerDeathListener.lastKit.get(player.getName()));
    	KitManager2.getPlayer(player.getName()).setkit2(PlayerDeathListener.lastKit2.get(player.getName()));
    	player.sendMessage("§aSeus kits antigos foram re-equipados.");
    }
	KomboPvP2.getInstance().getScoreboardBuilder().build(player);
  }
	public static ItemStack getHead(Player player) {
	      ItemStack item = new ItemStack(Material.SKULL_ITEM);
	      ItemMeta i = item.getItemMeta();
	      i.setDisplayName("§aSeu perfil §7(Clique)");
	      ArrayList<String> lore = new ArrayList<String>();
	      lore.add("§7Clique para ver seu perfil");
	      i.setLore(lore);
	      item.setItemMeta(i);
	      return item;
	  }
}
