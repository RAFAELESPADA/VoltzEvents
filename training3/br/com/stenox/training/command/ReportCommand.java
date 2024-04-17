// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.command;

import br.com.stenox.training.gamer.Gamer;
import org.bukkit.entity.Player;
import br.com.stenox.training.gamer.group.Group;
import br.com.stenox.training.Main;
import br.com.stenox.training.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import java.util.List;
import java.util.Arrays;
import org.bukkit.command.Command;

public class ReportCommand extends Command
{
    public ReportCommand() {
        super("report", "", "", (List)Arrays.asList("reportar", "rp"));
    }
    
    public boolean execute(final CommandSender sender, final String lb, final String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /" + lb + " [player] [reason]");
            return false;
        }
        final Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage("§cAlvo n\u00e3o encontrado.");
            return false;
        }
        final String reason = StringUtils.createArgs(1, args, "", false);
        final Player player2;
        final String str;
        Main.getInstance().getGamerController().getGamers().stream().filter(g -> g.getGroup().ordinal() <= Group.MOD.ordinal()).forEach(g -> {
            g.getPlayer().sendMessage("");
            g.getPlayer().sendMessage("§e§lREPORT");
            g.getPlayer().sendMessage("§fV\u00edtima: §a" + sender.getName());
            g.getPlayer().sendMessage("§fAcusado: §a" + player2.getName());
            g.getPlayer().sendMessage("§fMotivo: §e" + str);
            g.getPlayer().sendMessage("");
            return;
        });
        sender.sendMessage("§aDen\u00fancia enviada.");
        return true;
    }
}
