// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.command;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

public class PingCommand extends Command
{
    public PingCommand() {
        super("ping");
    }
    
    public boolean execute(final CommandSender sender, final String lb, final String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        final Player player = (Player)sender;
        if (args.length == 0) {
            if (this.getPing(player) == 0) {
                sender.sendMessage("§aSeu ping \u00e9 §a...ms");
            }
            else {
                sender.sendMessage("§aSeu ping \u00e9 " + this.getPing(player) + "ms");
            }
        }
        else {
            final Player p = Bukkit.getPlayer(args[0]);
            if (p == null) {
                sender.sendMessage("§cAlvo n\u00e3o encontrado.");
                return false;
            }
            if (!player.canSee(p)) {
                sender.sendMessage("§cAlvo n\u00e3o encontrado.");
                return false;
            }
            if (this.getPing(p) == 0) {
                sender.sendMessage("§aO ping de " + p.getName() + " ...ms");
            }
            else {
                sender.sendMessage("§aO ping de " + p.getName() + " \u00e9 de " + this.getPing(p) + "ms");
            }
        }
        return false;
    }
    
    public int getPing(final Player player) {
        return ((CraftPlayer)player).getHandle().ping;
    }
}
