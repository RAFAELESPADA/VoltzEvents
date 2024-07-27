package me.kp.moon.moondiscord.plugin.eventos;

import me.kp.moon.moondiscord.bot.utils.BotUtils;
import me.kp.moon.moondiscord.mysql.MySQL;
import me.kp.moon.moondiscord.plugin.Main;
import me.kp.moon.moondiscord.plugin.utils.PlayerUtils;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PlayerJoin implements Listener {

    public MySQL mySQL = new MySQL();

    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent event) {
        if (!mySQL.uuidExiste(event.getPlayer().getUniqueId().toString())) {
            event.getPlayer().sendMessage("§9[KomboDiscord] §cVocê ainda não vinculou sua conta com o nosso discord");

            event.getPlayer().sendMessage("§9[KomboDiscord] §cEntre no grupo §f( https://discord.gg/SQ48vpV8hM ) §ce depois");

            event.getPlayer().sendMessage("§9[KomboDiscord] §cEscreva §a+vincular §ceno chat #comandos do nosso grupo e siga as instruções para vincular sua conta!");

        }
        Player player = event.getPlayer();
        if (player.hasPermission("*") && !mySQL.uuidExiste(player.getUniqueId().toString())) {
event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "OPA, ESPERA AI! JÁ QUE VOCÊ TEM PERMISSÕES DE OP NO SERVIDOR VOCÊ DEVE VINCULAR SUA CONTA");
return;
        }
        if (mySQL.uuidExiste(player.getUniqueId().toString())) {
            Member b = BotUtils.getGuild().getMemberById(mySQL.getDiscordIDFromUUID(player.getUniqueId().toString()));
            if (b == null) {
                return;
            }
            if (b.isBoosting() && !player.hasPermission("group.nitro")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent addtemp nitro 15d");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "YESSS!");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "VOCÊ VINCULOU SUA CONTA E DEU BOOST NO DC DA KOMBO");
                player.sendMessage(ChatColor.LIGHT_PURPLE + "E TEVE O GRUPO NITRO SETADO POR 15 DIAS!");
            }
        }
        String uuid = player.getUniqueId().toString();
        if (player.hasPermission("kombo.cmd.report")) {
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("HH:mm");

            df.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

            PlayerUtils.staffJoin.put(player.getUniqueId(), df.format(date));

        }
        if (mySQL.uuidExiste(uuid)) {
            Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
                if (player.hasPermission("helix.tag.dono"))
                    mySQL.updateDisplayTAG(uuid, "§4§lDONO §4");// "§4§lDONO §4" + player.getName();// "§4§lS-DONO §4" + player.getName();
                else if (player.hasPermission("helix.tag.developer"))
                    mySQL.updateDisplayTAG(uuid, "§1§lDEV §a");// "§a§lDEV §a" + player.getName();
                else if (player.hasPermission("helix.tag.diretor"))
                    mySQL.updateDisplayTAG(uuid, "§3§lDIRETOR §b");// "§b§lDIRETOR §b" + player.getName();
                else if (player.hasPermission("helix.tag.manager"))
                    mySQL.updateDisplayTAG(uuid, "§d§lMANAGER §d");// "§9§lGERENTE §9" + player.getName();
                else if (player.hasPermission("helix.tag.admin"))
                    mySQL.updateDisplayTAG(uuid, "§c§lADMIN §c");// "§3§lCOORD §3" + player.getName();// "§5§lMODGC §5" + player.getName();
                else if (player.hasPermission("helix.tag.mod"))
                    mySQL.updateDisplayTAG(uuid, "§2§lMOD §2");// "§5§lMOD §5" + player.getName();
                else if (player.hasPermission("helix.tag.invest"))
                    mySQL.updateDisplayTAG(uuid, "§a§lINVEST §a");// "§5§lMOD §5" + player.getName();
                else if (player.hasPermission("helix.tag.builder"))
                    mySQL.updateDisplayTAG(uuid, "§3§lBUILDER §3");// "§e§lHELPER §e" + player.getName();
                else if (player.hasPermission("helix.tag.helper"))
                    mySQL.updateDisplayTAG(uuid, "§e§lHELPER §e");// "§e§lHELPER §e" + player.getName();
                else if (player.hasPermission("betterrtp"))
                    mySQL.updateDisplayTAG(uuid, "§7§lMEMBRO §7");// "§e§lHELPER §e" + player.getName();
                else if (player.hasPermission("helix.tag.estagiario"))
                    mySQL.updateDisplayTAG(uuid, "§d§lESTÁGIO §d");
            });
        }
    }

}
