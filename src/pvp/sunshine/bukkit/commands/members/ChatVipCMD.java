package pvp.sunshine.bukkit.commands.members;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class ChatVipCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        if (!sender.hasPermission("pvp.chatvip")) {
            sender.sendMessage("§c§lERRO §fVocê não tem permissão para executar este comando.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§c§lERRO §fComando inválido, use: /cvip (mensagem)");
            return true;
        }

        String message = String.join(" ", args);

        for (Player cvip : Bukkit.getOnlinePlayers()) {
            if (cvip.hasPermission("pvp.chatvip.see")) {
                String tag = getVipTag(sender);
                cvip.sendMessage("§7[§6§lCHATVIP§7] " + tag + sender.getName() + "§f: §e" + message);
            }
        }
        return true;
    }


    private String getVipTag(CommandSender sender) {
        if (sender.hasPermission("tag.galatic")) {
            return "§a§lGALATIC §a";
        } else if (sender.hasPermission("tag.aurora")) {
            return "§d§lAURORA §d";
        } else if (sender.hasPermission("tag.celeste")) {
            return "§b§lCELESTE §b";
        } else if (sender.hasPermission("tag.astrion")) {
            return "§e§lASTRION §e";
        } else if (sender.hasPermission("tag.seraph")) {
            return "§3§lSERAPH §3";
        }
        return ChatColor.GRAY + "";
    }
}
