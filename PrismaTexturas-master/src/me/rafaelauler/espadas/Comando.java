package me.rafaelauler.espadas;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


	

		public class Comando
		implements CommandExecutor
		{
		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
		{
			{
				  Player p = (Player)sender;
				  if (label.equalsIgnoreCase("cosmeticos"))
				  {
					  if (!p.hasPermission("comando.cosmeticos")) {
						  p.sendMessage("§cVocê não tem permissão.");
						  return true;
					  }
					 Inventory2.openGUIPrincipal(p, p);
					  
				  return true;
				  }
		}
			return false;
		}

		}
