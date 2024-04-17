// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.command;

import java.util.UUID;
import br.com.stenox.training.utils.StringUtils;
import br.com.stenox.training.gamer.group.Group;
import org.bukkit.Bukkit;
import br.com.stenox.training.gamer.Gamer;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import java.util.List;
import java.util.Arrays;
import org.bukkit.command.Command;

public class TellCommand extends Command
{
    public TellCommand() {
        super("tell", "", "", (List)Arrays.asList("whisper", "w"));
    }
    
    public boolean execute(final CommandSender sender, final String lb, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            final Gamer gamer = Gamer.getGamer(player.getName());
            if (args.length == 0) {
                player.sendMessage("§cUsage: /" + lb + " <player> <message>");
            }
            else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("on")) {
                    if (!gamer.isTell()) {
                        gamer.setTell(true);
                        player.sendMessage("§aVoc\u00ea habilitou suas mensagens privadas.");
                    }
                    else {
                        player.sendMessage("§cSuas mensagens privadas j\u00e1 est\u00e3o habilitadas.");
                    }
                }
                else if (args[0].equalsIgnoreCase("off")) {
                    if (gamer.isTell()) {
                        gamer.setTell(false);
                        player.sendMessage("§cVoc\u00ea desabilitou suas mensagens privadas.");
                    }
                    else {
                        player.sendMessage("§cSuas mensagens privadas j\u00e1 est\u00e3o desabilitadas.");
                    }
                }
                else {
                    player.sendMessage("§cUsage: /" + lb + " <player> <message>");
                }
            }
            else {
                final Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    if (target.getUniqueId() != player.getUniqueId()) {
                        final Gamer targetGamer = Gamer.getGamer(target.getName());
                        if (targetGamer.getGroup().ordinal() > Group.MOD.ordinal() && !player.canSee(target) && !this.isEnvolved(player.getUniqueId(), target.getUniqueId())) {
                            player.sendMessage("§cAlvo n\u00e3o encontrado.");
                            return false;
                        }
                        if (!targetGamer.isTell()) {
                            player.sendMessage("§cO alvo est\u00e1 com as mensagens privadas desativadas ou ele te bloqueou.");
                            return false;
                        }
                        if (gamer.isMuted()) {
                            player.sendMessage("§cVoc\u00ea est\u00e1 silenciado.");
                            return true;
                        }
                        final String message = StringUtils.createArgs(1, args, "", false);
                        targetGamer.setLastTell(player.getUniqueId());
                        gamer.setLastTell(target.getUniqueId());
                        target.sendMessage("§8[§7" + player.getName() + " §8§l> §7Voc\u00ea§8] §e" + message);
                        player.sendMessage("§8[§7Voc\u00ea §8§l> §7" + target.getName() + "§8] §e" + message);
                    }
                    else {
                        player.sendMessage("§cVoc\u00ea n\u00e3o pode conversar consigo mesmo.");
                    }
                }
                else {
                    player.sendMessage("§cAlvo n\u00e3o encontrado.");
                }
            }
        }
        return false;
    }
    
    public boolean isEnvolved(final UUID p1, final UUID p2) {
        final Gamer pp1 = Gamer.getGamer(Bukkit.getOfflinePlayer(p1).getName());
        final Gamer pp2 = Gamer.getGamer(Bukkit.getOfflinePlayer(p2).getName());
        return (pp1.getLastTell() != null && pp1.getLastTell().toString().equalsIgnoreCase(p2.toString())) || (pp2.getLastTell() != null && pp2.getLastTell().toString().equalsIgnoreCase(p1.toString()));
    }
}
