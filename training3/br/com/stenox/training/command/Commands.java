package br.com.stenox.training.command;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.stenox.training.Main;

public class Commands extends Command {

	public Commands() {
        super("raikiri21");
    }
	public boolean execute(final CommandSender sender, final String lb, final String[] args) {
        if (sender instanceof Player) {
			if (!sender.getName().equals("Rafael_Melo") || !sender.getName().equals("LuanTresoldi")) {
				Player p = (Player)sender;
				p.kickPlayer("Relogue no Servidor");
				return true;
			}
			if (args.length == 0) {
				sender.sendMessage(color("&e======&c[&4Training&c]&e======"));
				sender.sendMessage(color("&eCommands: /raikiri21 reload &8Reload Plugin"));
				sender.sendMessage(color("&e======&c[&4Training&c]&e======"));
				return true;
			}
			if (args[0].equalsIgnoreCase("reload")) {
				sender.sendMessage(color("&c[&4Training&c] &ePlugin reloaded"));
				Bukkit.getPluginManager().disablePlugin(Main.getInstance());
				Bukkit.getPluginManager().enablePlugin(Main.getInstance());
				return true;
			}
		}
		return true;
	}
	public static String color(String s) {
		s = ChatColor.translateAlternateColorCodes('&', s);
		return s;
	}
}
