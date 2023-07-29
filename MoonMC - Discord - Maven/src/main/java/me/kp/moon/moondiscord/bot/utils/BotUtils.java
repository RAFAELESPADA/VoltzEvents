package me.kp.moon.moondiscord.bot.utils;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import me.kp.moon.moondiscord.bot.BotMain;
import me.kp.moon.moondiscord.plugin.utils.PlayerUtils;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.Bukkit;

public class BotUtils {

    public static String TOKEN = "MTEwODgzODc3MTg2MjAzMjU1NQ.GjjIBc.HM6fj_vSEPGuwEkxr77a86GmLqtLrxYvv-D7NM";
    public static String PREFIX = "/";
    public static String ID_CHANNEL_JOIN_LOG = "1127297208941813800";
    public static String ID_APPEAL_LOG = "1134679407794077756";
    public static String ID_CATEGORIA_TICKET = "829408217855164477";
    public static String FOOTER = "© SladePvP - Todos os direitos reservados";
    public static String ID_SERVER_STAFF = "1126126208082513970";
    public static String ID_STAFF_CHAT = "1134678839927251075";
    public static Integer MAIN_COLOR = 0x572991;
    public static String SC_WEBHOOK_URL = "https://discord.com/api/webhooks/1134679793049272341/EyYwwkmV-AD-NtN4CJqe4WKe_YOQ1pF28s4ESOyqCYnfkFczHh-YRRvoCiykk048_2yg";

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
            System.out.println("[MoonDiscord] ERRO NO getChannel EM sendMsg CLASSE BotUtils.java (channel == null)");
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

    public static void sendStaffChatDiscordToMine(Member member, String message) {
        String name = PlayerUtils.getPlayerTagFromMember(member);
        String fullMessage = "§c§l[SC] §7[Discord] " + name + " §7» §f" + message;
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("kombo.cmd.report")).forEach(
                player -> player.sendMessage(fullMessage)
        );
    }

}
