// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.command.punishments;

import org.bukkit.OfflinePlayer;
import br.com.stenox.training.utils.StringUtils;
import br.com.stenox.training.Main;
import org.bukkit.Bukkit;
import br.com.stenox.training.gamer.group.Group;
import br.com.stenox.training.gamer.Gamer;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

public class BanCommand extends Command
{
    public BanCommand() {
        super("ban");
    }
    
    public boolean execute(final CommandSender sender, final String lb, final String[] args) {
        if (sender instanceof Player) {
            final Gamer gamer = Gamer.getGamer(sender.getName());
            if (gamer.getGroup().ordinal() > Group.MOD.ordinal()) {
                return false;
            }
        }
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /ban [player] [reason]");
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
        if (gamer2 == null) {
            sender.sendMessage("§cGamer n\u00e3o encontrado.");
            return false;
        }
        final Gamer senderGamer = Gamer.getGamer(sender.getName());
        if (senderGamer != null && gamer2.getGroup().ordinal() <= senderGamer.getGroup().ordinal()) {
            sender.sendMessage("§cVoc\u00ea n\u00e3o pode banir algu\u00e9m com um cargo igual ou superior ao seu.");
            return false;
        }
        final String reason = StringUtils.createArgs(1, args, "", false);
        gamer2.ban(reason, -1L);
        sender.sendMessage("§cJogador banido com sucesso.");
        if (player.isOnline()) {
            Main.getInstance().getGamerRepository().update(gamer2);
            Main.getInstance().getGamerController().remove(gamer2.getName());
            player.getPlayer().kickPlayer("§cVoc\u00ea foi banido do servidor \n \n §cMotivo: " + reason + " \n §cExpira em: Nunca");
            Bukkit.broadcastMessage("§cUm jogador na sua sala foi banido por uso de trapa\u00e7as.");
        }
        return false;
    }
}
