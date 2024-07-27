package me.rafaelauler.events;


import org.bukkit.entity.Player;


public class OnevsOne extends WarpHandle {
	
	@Override
	public void execute(Player player) {
		super.execute(player);
		player.getInventory().clear();
		EventoComando e =  new EventoComando();
		e.Gladiator(player);

        player.sendMessage("§aVocê agora está no evento 1v1!");
        player.sendMessage("§aSiga as instruções que serão dadas no chat!");
	}
}