// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.command;

import org.bukkit.inventory.Inventory;
import org.bukkit.Bukkit;
import br.com.stenox.training.gamer.group.Group;
import br.com.stenox.training.gamer.Gamer;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import java.util.List;
import java.util.Collections;
import org.bukkit.command.Command;

public class InvseeCommand extends Command
{
    public InvseeCommand() {
        super("invsee", "", "", (List)Collections.singletonList("inv"));
    }
    
    public boolean execute(final CommandSender sender, final String lb, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cEste comando \u00e9 apenas para jogadores.");
            return false;
        }
        final Gamer gamer = Gamer.getGamer(sender.getName());
        if (gamer.getGroup().ordinal() > Group.MOD.ordinal()) {
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage("§cUsage: /" + lb + " [player]");
            return false;
        }
        final Player target = Bukkit.getPlayer(args[0]);
        if (target != null) {
            ((Player)sender).openInventory((Inventory)target.getInventory());
            sender.sendMessage("§aAbrindo invent\u00e1rio...");
        }
        else {
            sender.sendMessage("§cJogador n\u00e3o encontrado.");
        }
        return false;
    }
}
