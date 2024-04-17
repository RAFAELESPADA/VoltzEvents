// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.command.punishments;

import br.com.stenox.training.utils.StringUtils;
import org.bukkit.Bukkit;
import br.com.stenox.training.gamer.group.Group;
import br.com.stenox.training.gamer.Gamer;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import java.util.List;
import java.util.Collections;
import org.bukkit.command.Command;

public class KickCommand extends Command
{
    public KickCommand() {
        super("kick", "", "", (List)Collections.singletonList("expulsar"));
    }
    
    public boolean execute(final CommandSender sender, final String lb, final String[] args) {
        if (sender instanceof Player) {
            final Gamer gamer = Gamer.getGamer(sender.getName());
            if (gamer.getGroup().ordinal() > Group.MOD.ordinal()) {
                return false;
            }
        }
        if (args.length < 1) {
            sender.sendMessage("§cUsage: /" + lb + " [player] [message]");
            return false;
        }
        if (args.length == 1) {
            final Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage("§cJogador n\u00e3o encontrado.");
                return false;
            }
            player.kickPlayer("§cVoc\u00ea foi expulso do servidor. \n Sem motivo \n Por: " + sender.getName());
            sender.sendMessage("§aVoc\u00ea expulsou o jogador com sucesso.");
        }
        else {
            final Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage("§cJogador n\u00e3o encontrado.");
                return false;
            }
            final String message = StringUtils.createArgs(1, args, "", false);
            player.kickPlayer("§cVoc\u00ea foi expulso do servidor. \n Motivo: " + message + " \n Por: " + sender.getName());
            sender.sendMessage("§aVoc\u00ea expulsou o jogador com sucesso.");
        }
        return false;
    }
}
