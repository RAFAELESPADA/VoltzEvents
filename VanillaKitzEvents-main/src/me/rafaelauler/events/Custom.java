package me.rafaelauler.events;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;


public class Custom extends WarpHandle {
	
	@Override
	public void execute(Player player) {
		super.execute(player);
		player.getInventory().clear();

        player.sendMessage("�aVoc� agora est� no evento Customizado ( Escolhido pelo promotor )!");
        player.sendMessage("�aSiga as instru��es que ser�o dadas no chat!");
	}
}
