// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.command;

import org.bukkit.OfflinePlayer;
import br.com.stenox.training.Main;
import org.bukkit.Bukkit;
import br.com.stenox.training.gamer.group.Group;
import br.com.stenox.training.gamer.Gamer;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import java.util.List;
import java.util.Arrays;
import org.bukkit.command.Command;

public class GroupSetCommand extends Command
{
    public GroupSetCommand() {
        super("groupset", "", "", (List)Arrays.asList("setgroup", "setgrupo"));
    }
    
    public boolean execute(final CommandSender sender, final String lb, final String[] args) {
        if (sender instanceof Player) {
            final Gamer gamer = Gamer.getGamer(sender.getName());
            if (gamer.getGroup().ordinal() > Group.ADMIN.ordinal()) {
                return false;
            }
        }
        if (args.length != 2) {
            sender.sendMessage("§cUsage: /" + lb + " [player] [group]");
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
        final Group group = Group.fromString(args[1]);
        if (sender instanceof Player) {
            final Gamer senderGamer = Gamer.getGamer(sender.getName());
            if (gamer2.getGroup().ordinal() <= senderGamer.getGroup().ordinal()) {
                sender.sendMessage("§cVoc\u00ea n\u00e3o pode gerenciar algu\u00e9m com um cargo igual ou superior ao seu.");
                return false;
            }
            if (group != null && group.ordinal() <= senderGamer.getGroup().ordinal()) {
                sender.sendMessage("§cEsse cargo \u00e9 igual ou superior ao seu.");
                return false;
            }
        }
        if (group == null) {
            sender.sendMessage("§cGrupo inexistente.");
            return false;
        }
        gamer2.setGroup(group);
        if (!player.isOnline()) {
            Main.getInstance().getGamerController().update(gamer2);
        }
        else {
            gamer2.updateTag();
        }
        sender.sendMessage("§aGrupo setado com sucesso.");
        return false;
    }
}
