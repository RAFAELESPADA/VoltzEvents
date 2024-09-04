package pvp.sunshine.bukkit.utils;

import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.api.TagAPI;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLClan;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;

public class TagUtil {

    public static void tagUpdater(Player player) {
        if (player.getName().equalsIgnoreCase("Rafael_Auler")) {
            TagAPI.setNameTag(player.getName(), "a", "§6§lCHEFIA §6", " " + SQLClan.getTagPlayer(player));
            player.setDisplayName("§6§lCHEFIA §6" + player.getName());
        } else if (player.hasPermission("tag.chefia")) {
            TagAPI.setNameTag(player.getName(), "b", "§6§lCHEFIA §6", " " + SQLClan.getTagPlayer(player));
            player.setDisplayName("§6§lCHEFIA §6" + player.getName());
        } else if (player.hasPermission("tag.admin")) {
            TagAPI.setNameTag(player.getName(), "c", "§b§lADMIN §b", " " + SQLClan.getTagPlayer(player));
            player.setDisplayName("§b§lADMIN §b" + player.getName());
        } else if (player.hasPermission("tag.gerente")) {
            TagAPI.setNameTag(player.getName(), "d", "§3§lGERENTE §3", " " + SQLClan.getTagPlayer(player));
            player.setDisplayName("§3§lGERENTE §3" + player.getName());
        } else if (player.hasPermission("tag.mod")) {
            TagAPI.setNameTag(player.getName(), "e", "§2§lMOD §2", " " + SQLClan.getTagPlayer(player));
            player.setDisplayName("§2§lMOD §2" + player.getName());
        } else if (player.hasPermission("tag.ajudante")) {
            TagAPI.setNameTag(player.getName(), "f", "§e§lAJUDANTE §e", " " + SQLClan.getTagPlayer(player));
            player.setDisplayName("§e§lAJUDANTE §e" + player.getName());
        } else if (player.hasPermission("tag.construtor")) {
            TagAPI.setNameTag(player.getName(), "g", "§9§lCONSTRUTOR §9", " " + SQLClan.getTagPlayer(player));
            player.setDisplayName("§9§lCONSTRUTOR §9" + player.getName());
        } else if (player.hasPermission("tag.creator")) {
            TagAPI.setNameTag(player.getName(), "h", "§c§lCREATOR §c", " " + SQLClan.getTagPlayer(player));
            player.setDisplayName("§c§lCREATOR §c" + player.getName());
        } else if (player.hasPermission("tag.invest")) {
            TagAPI.setNameTag(player.getName(), "i", "§a§lINVEST §a", " " + SQLClan.getTagPlayer(player));
            player.setDisplayName("§a§lINVEST §a" + player.getName());
        } else if (player.hasPermission("tag.vip+")) {
            TagAPI.setNameTag(player.getName(), "j", "§b§lVIP+ §b", " " + SQLClan.getTagPlayer(player));
            player.setDisplayName("§b§lVIP+ §b" + player.getName());
        } else if (player.hasPermission("tag.vip")) {
            TagAPI.setNameTag(player.getName(), "k", "§9§lVIP §9", " " + SQLClan.getTagPlayer(player));
            player.setDisplayName("§9§lVIP §9" + player.getName());
        } else if (player.hasPermission("tag.bughunter")) {
            TagAPI.setNameTag(player.getName(), "l", "§8§lBUGHUNTER §8", " " + SQLClan.getTagPlayer(player));
            player.setDisplayName("§8§lBUGHUNTER §8" + player.getName());
        } else if (player.hasPermission("tag.apoiador")) {
            TagAPI.setNameTag(player.getName(), "m", "§5§lAPOIADOR §5", " " + SQLClan.getTagPlayer(player));
            player.setDisplayName("§5§lAPOIADOR §5" + player.getName());
        } else if (player.hasPermission("tag.membro")) {
            TagAPI.setNameTag(player.getName(), "u", "§7", " " + SQLClan.getTagPlayer(player));
            player.setDisplayName("§7" + player.getName());
        }
    }
}
