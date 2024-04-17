// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.command.punishments;

import org.bukkit.OfflinePlayer;
import br.com.stenox.training.utils.StringUtils;
import br.com.stenox.training.utils.LongManager;
import br.com.stenox.training.Main;
import org.bukkit.Bukkit;
import br.com.stenox.training.gamer.group.Group;
import br.com.stenox.training.gamer.Gamer;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

public class TempBanCommand extends Command
{
    public TempBanCommand() {
        super("tempban");
    }
    
    public boolean execute(final CommandSender sender, final String lb, final String[] args) {
        if (sender instanceof Player) {
            final Gamer gamer = Gamer.getGamer(sender.getName());
            if (gamer.getGroup().ordinal() > Group.MOD.ordinal()) {
                return false;
            }
        }
        if (args.length < 3) {
            sender.sendMessage("§cUsage: /tempban [player] [time] [reason]");
            return false;
        }
        final OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
        if (player == null) {
            sender.sendMessage("§cJogador n\u00e3o encontrado.");
            return false;
        }
        Gamer gamer2;
        if (player.isOnline()) {
            gamer2 = Gamer.getGamer(player.getName());
        }
        else {
            gamer2 = Main.getInstance().getGamerRepository().fetch(player.getName());
        }
        final Gamer senderGamer = Gamer.getGamer(sender.getName());
        if (gamer2.getGroup().ordinal() <= senderGamer.getGroup().ordinal()) {
            sender.sendMessage("§cVoc\u00ea n\u00e3o pode banir algu\u00e9m com um cargo igual ou superior ao seu.");
            return false;
        }
        if (gamer2 == null) {
            sender.sendMessage("§cGamer n\u00e3o encontrado.");
            return false;
        }
        final long time = LongManager.stringToLong(args[1]);
        final String reason = StringUtils.createArgs(2, args, "", false);
        gamer2.ban(reason, time);
        sender.sendMessage("§cJogador banido com sucesso.");
        if (player.isOnline()) {
            player.getPlayer().kickPlayer("§cVoc\u00ea foi banido do servidor \n \n §cMotivo: " + reason + " \n §cExpira em: " + LongManager.formatLong(time));
        }
        return false;
    }
}
