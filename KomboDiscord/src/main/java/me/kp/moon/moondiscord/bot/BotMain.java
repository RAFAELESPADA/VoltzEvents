package me.kp.moon.moondiscord.bot;

import lombok.Getter;
import me.kp.moon.moondiscord.bot.comandos.CheckStaff;
import me.kp.moon.moondiscord.bot.comandos.Console;
import me.kp.moon.moondiscord.bot.comandos.Ip;
import me.kp.moon.moondiscord.bot.comandos.Sync;
import me.kp.moon.moondiscord.bot.sistemas.StaffChat;
import me.kp.moon.moondiscord.bot.utils.BotUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import javax.security.auth.login.LoginException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BotMain {

    public static JDA jda;

    @Getter
    private static final ExecutorService executorService = Executors.newFixedThreadPool(50);
    public static void startBot() throws LoginException, InterruptedException {
        jda = JDABuilder.createDefault(BotUtils.TOKEN , GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS , GatewayIntent.GUILD_VOICE_STATES , GatewayIntent.GUILD_EMOJIS_AND_STICKERS , GatewayIntent.GUILD_EMOJIS_AND_STICKERS , GatewayIntent.SCHEDULED_EVENTS)
                .addEventListeners(new Console())
                .addEventListeners(new Ip())
                .addEventListeners(new StaffChat())
                .addEventListeners(new Sync())
                .addEventListeners(new CheckStaff())
                .build();

        jda.awaitReady();
    }

    public static void stopBot() {
        jda.shutdown();
    }

}
