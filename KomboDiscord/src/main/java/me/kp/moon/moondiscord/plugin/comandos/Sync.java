package me.kp.moon.moondiscord.plugin.comandos;

import me.kp.moon.moondiscord.bot.utils.BotUtils;
import me.kp.moon.moondiscord.mysql.MySQL;
import me.kp.moon.moondiscord.system.SysUtils;
import me.kp.moon.moondiscord.plugin.enums.DefaultMessages;
import me.kp.moon.moondiscord.plugin.utils.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sync implements CommandExecutor {

    public MySQL mySQL = new MySQL();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(DefaultMessages.APENAS_PLAYERS.getMessage());
            return true;
        }

        Player player = (Player) sender;



        if (mySQL.uuidExiste(player.getUniqueId().toString())) {
            player.sendMessage("§9[KomboDiscord] §cVocê já vinculou essa conta a um discord. §7(caso isso seja um erro, " +
                    "contate rafael_auler2 ( Rafael Auler)");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§9[KomboDiscord] §cArgumentos insuficientes. §7(/sync <key>)");
            return true;
        }
        Long discordID = SysUtils.keys.get(args[0]);
        if (discordID == null) {
            player.sendMessage("§9[KomboDiscord] §cA key mencionada é inválida. §7(revise a key mencionada)");
            return true;
        }

        BotUtils.giveRole(discordID, 1155592056681541652L);
        if (player.hasPermission("group.default")) {
            BotUtils.giveRole(discordID, 1155592021986254849L);
        }
        if (player.hasPermission("group.apoiador")) {
            BotUtils.giveRole(discordID, 1208597821394133012L);
        }
        if (player.hasPermission("group.vip")) {
            BotUtils.giveRole(discordID, 1155591998519132271L);
        }

        if (player.hasPermission("group.vip+")) {
            BotUtils.giveRole(discordID, 1155591995922849792L);
        }

        if (player.hasPermission("group.creator")) {
            BotUtils.giveRole(discordID, 1155591936900612177L);
        }

        if (player.hasPermission("group.invest")) {
            BotUtils.giveRole(discordID, 1194769181686640760L);
        }
        if (player.hasPermission("group.ajudante")) {
            BotUtils.giveRole(discordID, 1155591901605548134L);
        }
        if (player.hasPermission("group.mod")) {
            BotUtils.giveRole(discordID, 1155591898552078337L);
        }
        mySQL.createUser(player.getUniqueId().toString(), player.getName(), discordID, PlayerUtils.getPlayerTag(player));
        player.sendMessage(mySQL.uuidExiste(player.getUniqueId().toString()) ? "§9[KomboDiscord] §aSua conta foi " +
                "vinculada ao Discord de ID §e" + discordID : "§9[KomboDiscord] §cOcorreu um erro ao vincular sua conta.");
        SysUtils.keys.remove(args[0]);


        return false;
    }
}
