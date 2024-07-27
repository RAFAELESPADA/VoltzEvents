package me.kp.moon.moondiscord.bot.utils;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import me.kp.moon.moondiscord.bot.BotMain;
import me.kp.moon.moondiscord.plugin.utils.PlayerUtils;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.Bukkit;

import java.util.List;

public class BotUtils {

    public static String TOKEN = "MTIxMTg3MzIwMTE5ODQ2NTA5NA.G4YgAY.Cu4M-taQDQsVkm2KfFmKfTW1bxBZ8pITMGD51s";
    public static String PREFIX = "/";
    public static String ID_CHANNEL_JOIN_LOG = "1265028291493105664";
    public static String ID_APPEAL_LOG = "1265028465250275471";
    public static String ID_CATEGORIA_TICKET = "829408217855164477";
    public static String FOOTER = "© KomboPvP - Todos os direitos reservados";
    public static String ID_SERVER_STAFF = "1013325201653645324";
    public static String ID_STAFF_CHAT = "1262925319069765683";
    public static Integer MAIN_COLOR = 0x572991;
    public static String SC_WEBHOOK_URL = "https://discord.com/api/webhooks/1265029276017758320/RPRua5gSSZeq-ovYwhwy1SuvhJJhQGG5lCShxM8dxmHZa4JwdGAYU8DEZozeFe2iS1YI";

    public static WebhookClientBuilder builder = new WebhookClientBuilder(SC_WEBHOOK_URL) // or id, token
            .setThreadFactory((job) -> {
                Thread thread = new Thread(job);
                thread.setName("Hello");
                thread.setDaemon(true);
                return thread;
            })
            .setWait(true);
    public static WebhookClient webhookClient = builder.build();


    public static void sendMsg(String id, String message, MessageEmbed embed) {
        TextChannel channel = BotMain.jda.getTextChannelById(id);

        if (channel == null) {
            System.out.println("[KomboDiscord] ERRO NO getChannel EM sendMsg CLASSE BotUtils.java (channel == null)");
            return;
        }

        if (message == null) {
            channel.sendMessageEmbeds(embed).queue();
        } else {
            channel.sendMessage(message).queue();
        }
    }
    public static void giveRole(long id , long role) {
        Bukkit.getConsoleSender().sendMessage("Adicionando cargo com o ID " + role + " para a pessoa com o ID " + id);
        BotMain.jda.getGuilds().forEach(guild -> guild.addRoleToMember(guild.retrieveMemberById(id).complete() , guild.getRoleById(role)).queue());
        Bukkit.getConsoleSender().sendMessage("ADICIONADO!");
    }
    public static void removeRole(long id , long role) {
        Bukkit.getConsoleSender().sendMessage("Removendo cargo com o ID " + role + " para a pessoa com o ID" + id);
        BotMain.jda.getGuilds().forEach(guild -> guild.removeRoleFromMember(guild.retrieveMemberById(id).complete() , guild.getRoleById(role)).queue());
        Bukkit.getConsoleSender().sendMessage("REMOVIDO!");
    }
    public static Role findRole(Member member, String name) {
        List<Role> roles = member.getRoles();
        return roles.stream()
                .filter(role -> role.getName().equals(name)) // filter by role name
                .findFirst() // take first result
                .orElse(null); // else return null
    }
    public static String hasRole(Member member) {
        List<Role> roles = member.getRoles();
        return findRole(member, "Chefia").getName();
    }
    public static String hasRole2(Member member) {
        List<Role> roles = member.getRoles();
        return findRole(member, "Jogadores").getName();
    }
    public static Guild getGuild() {
       return BotMain.jda.getGuildById(ID_SERVER_STAFF);

    }

    public static void sendStaffChatDiscordToMine(Member member, String message) {
        String name = PlayerUtils.getPlayerTagFromMember(member);
        String fullMessage = "§c§l[SC] §7[Discord] " + name + " §7» §f" + message;
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("kombo.cmd.report")).forEach(
                player -> player.sendMessage(fullMessage)
        );
    }

}
