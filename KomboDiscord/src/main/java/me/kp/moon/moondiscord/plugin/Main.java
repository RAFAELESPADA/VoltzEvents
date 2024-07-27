package me.kp.moon.moondiscord.plugin;

import me.kp.moon.moondiscord.bot.sistemas.StaffChat;
import me.kp.moon.moondiscord.bot.BotMain;
import me.kp.moon.moondiscord.bot.utils.BotUtils;
import me.kp.moon.moondiscord.mysql.MySQL;
import me.kp.moon.moondiscord.plugin.comandos.Sync;
import me.kp.moon.moondiscord.plugin.eventos.PlayerJoin;
import me.kp.moon.moondiscord.plugin.eventos.PlayerQuit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.security.auth.login.LoginException;

public class Main extends JavaPlugin {

    private final MySQL mySQL = new MySQL();

    public static Main getInstance() {
        return getPlugin(Main.class);
    }

    @Override
    public void onLoad() {
        Bukkit.getConsoleSender().sendMessage("§9[SladeDiscord] §aIniciando instancia do bot...");
    }

    @Override
    public void onEnable() {
        registerEvents();
        registerCommands();
        mySQL.connectToDBS();
        try {
            BotMain.startBot();
            BotMain.getExecutorService().submit(() -> {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p.hasPermission("*") && !mySQL.uuidExiste(p.getUniqueId().toString())) {

                                p.kickPlayer("OPA, PERA AI, VOCê TEM OP NO JOGO MAS NÃO ESTÁ VINCULADO COM O DC DA KOMBO!");
                            }
                            if (!mySQL.uuidExiste(p.getUniqueId().toString())) {
                                p.sendMessage("§9[KomboDiscord] §cVocê ainda não vinculou sua conta com o nosso discord");

                                p.sendMessage("§9[KomboDiscord] §cEntre no grupo §f( https://discord.gg/SQ48vpV8hM ) §ce depois");

                                p.sendMessage("§9[KomboDiscord] §cEscreva §a+vincular §ceno chat #comandos do nosso grupo e siga as instruções para vincular sua conta!");

                                return;
                            }
                        }};

                    }.runTaskTimer(this, 0, 20 * 360L);
            });
            Bukkit.getConsoleSender().sendMessage("§9[KomboDiscord] §aBot iniciado!");
        } catch (LoginException | InterruptedException e) {
            Bukkit.getConsoleSender().sendMessage("§9[KomboDiscord] §cErro ao iniciar bot!");
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§9[SladeDiscord] §cBot desligado!");
        BotMain.stopBot();
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
        Bukkit.getPluginManager().registerEvents(new StaffChat(), this);
    }

    private void registerCommands() {
        getCommand("vinculardiscord").setExecutor(new Sync());
    }


}
