// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.command.punishments;

import org.bukkit.OfflinePlayer;
import br.com.stenox.training.Main;
import org.bukkit.Bukkit;
import br.com.stenox.training.gamer.group.Group;
import br.com.stenox.training.gamer.Gamer;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

public class UnbanCommand extends Command
{
    public UnbanCommand() {
        super("unban");
    }
    
    public boolean execute(final CommandSender sender, final String lb, final String[] args) {
        if (sender instanceof Player) {
            final Gamer gamer = Gamer.getGamer(sender.getName());
            if (gamer.getGroup().ordinal() > Group.ADMIN.ordinal()) {
                return false;
            }
        }
        if (args.length != 1) {
            sender.sendMessage("§cUsage: /unban [player]");
            return false;
        }
        final OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
        if (player == null) {
            sender.sendMessage("§cJogador n\u00e3o encontrado.");
            return false;
        }
        final Gamer gamer2 = Main.getInstance().getGamerRepository().fetch(player.getName());
        if (gamer2 == null) {
            sender.sendMessage("§cGamer n\u00e3o encontrado.");
            return false;
        }
        if (!gamer2.isBanned()) {
            sender.sendMessage("§cJogador n\u00e3o est\u00e1 banido.");
            return false;
        }
        gamer2.unban();
        sender.sendMessage("§aJogador desbanido.");
        return false;
    }
}
