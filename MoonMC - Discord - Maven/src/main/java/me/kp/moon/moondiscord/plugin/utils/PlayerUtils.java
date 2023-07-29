package me.kp.moon.moondiscord.plugin.utils;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import me.kp.moon.moondiscord.mysql.MySQL;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerUtils {

    public static HashMap<UUID, String> staffJoin = new HashMap<>();

    public static List<UUID> inStaffChat = new ArrayList<>();

    public static MySQL mySQL = new MySQL();

    public static String getPlayerTagFromMember(Member member) {
        if (!mySQL.discordIDExiste(member.getIdLong())) return "§9§lDC §9" + member.getEffectiveName();
        UUID uuid = UUID.fromString(mySQL.getUUIDFromDiscordID(member.getIdLong()));
        String displayTag = mySQL.getDisplayTAGFromUUID(uuid.toString());
        if (displayTag == null)
            return "§9§lDC §9" + member.getEffectiveName();
        if (displayTag.contains("§"))
            return displayTag + mySQL.getPlayerNameFromDiscordID(member.getIdLong());
        return "§9§lDC §9" + member.getEffectiveName();
    }

    public static void sendStaffChatMineToDiscord(Player player, String message, WebhookClient client) {
        WebhookMessageBuilder builder = new WebhookMessageBuilder()
                .setUsername(player.getName())
                .setAvatarUrl("https://minotar.net/avatar/" + player.getName())
                .setContent(message);
        client.send(builder.build());
    }
    
    public static String getPlayerTag(Player player) {
        if (player.hasPermission("helix.tag.dono")) return "§4§lDONO §4";// "§4§lDONO §4" + player.getName();
        else if (player.hasPermission("helix.tag.diretor")) return "§3§lDIRETOR §b";
        else if (player.hasPermission("helix.tag.developer")) return "§1§lDEV §a";// "§a§lDEV §a" + player.getName();
            // "§b§lDIRETOR §b" + player.getName();
        else if (player.hasPermission("helix.tag.manager"))
            return "§d§lMANAGER §d";// "§9§lGERENTE §9" + player.getName();
        else if (player.hasPermission("helix.tag.admin")) return "§c§lADMIN §c";// "§3§lCOORD §3" + player.getName();
        else if (player.hasPermission("helix.tag.mod")) return "§2§lMOD §2";// "§2§lMOD+ §2" + player.getName();
        else if (player.hasPermission("helix.tag.invest"))
            return "§a§lINVEST §a";// "§2§lBUILDER §2" + player.getName();
        else if (player.hasPermission("helix.tag.helper"))
            return "§e§lHELPER §e";// "§5§lMODGC §5" + player.getName();"§5§lMOD §5" + player.getName();
        else if (player.hasPermission("displayname.builder")) return "§2§lBUILDER §2";
        else if (player.hasPermission("betterrtp")) return "§7§lMEMBRO §7";// "§e§lHELPER §e" + player.getName();
        // "§e§lHELPER §e" + player.getName();
        return null;
    }
    }
