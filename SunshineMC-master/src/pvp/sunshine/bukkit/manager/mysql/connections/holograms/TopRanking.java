package pvp.sunshine.bukkit.manager.mysql.connections.holograms;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.SunshineFormat;
import pvp.sunshine.bukkit.manager.mysql.Storage;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class TopRanking extends Storage {

    private static Hologram topKillsHologram;
    public static User giveMeADamnUser(UUID uniqueId) {
   	 RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
   		if (provider == null) return null; 
   		    LuckPerms api = provider.getProvider();
       UserManager userManager = api.getUserManager();
       CompletableFuture<User> userFuture = userManager.loadUser(uniqueId);

       return userFuture.join(); 
   		}
    public static String getTagColor(String playerName) {
        if (playerName == null) {
            return "§7";
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);

        if (offlinePlayer != null) {
            String groupName = giveMeADamnUser(offlinePlayer.getUniqueId()).getPrimaryGroup(); // Obtém o grupo do jogador do banco de dados

            if ("chefia".equalsIgnoreCase(groupName)) {
                return "§4";
            } else if ("admin".equalsIgnoreCase(groupName)) {
                return "§b";
            } else if ("gerente".equalsIgnoreCase(groupName)) {
                return "§3";
            } else if ("mod".equalsIgnoreCase(groupName)) {
                return "§2";
            } else if ("ajudante".equalsIgnoreCase(groupName)) {
                return "§e";
            } else if ("creator".equalsIgnoreCase(groupName)) {
                return "§c";
            } else if ("invest".equalsIgnoreCase(groupName)) {
                return "§a";
            } else if ("construtor".equalsIgnoreCase(groupName)) {
                return "§9";
            } else if ("vip+".equalsIgnoreCase(groupName)) {
                return "§b";
            } else if ("vip".equalsIgnoreCase(groupName)) {
                return "§6";

            } else if ("bughunter".equalsIgnoreCase(groupName)) {
                return "§8";
            } else if ("apoiador".equalsIgnoreCase(groupName)) {
                return "§5";
            } else if ("membro".equalsIgnoreCase(groupName)) {
                return "§7";
            }
        }

        return "§7";
    }

    private static List<String> getTopXP() {
        List<String> topXP = new LinkedList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM Xp ORDER BY EXP DESC LIMIT 10");
             ResultSet rs = ps.executeQuery()) {
            int index = 1;
            while (rs.next()) {
                int xp = rs.getInt("EXP");
                String playerName = rs.getString("NICK");
                String tagColor = getTagColor(playerName);
                topXP.add("§a" + index + "º §7- " + tagColor + playerName + " §f- XP: §e" + SunshineFormat.format(xp) + " §f- Ranking: " + SQLRank.getRankSymbol(xp) + " " + SQLRank.getRankComplete(xp));
                index++;
            }
            if (topXP.isEmpty()) {
                topXP.add("§a1º §7- Carregando...");
                topXP.add("§a2º §7- Carregando...");
                topXP.add("§a3º §7- Carregando...");
                topXP.add("§a4º §7- Carregando...");
                topXP.add("§a5º §7- Carregando...");
                topXP.add("§a6º §7- Carregando...");
                topXP.add("§a7º §7- Carregando...");
                topXP.add("§a8º §7- Carregando...");
                topXP.add("§a9º §7- Carregando...");
                topXP.add("§a10º §7- Carregando...");
            } else {
                for (int i = index; i <= 10; i++) {
                    topXP.add("§a" + i + "º §7- Carregando...");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topXP;
    }

    public static void updateHologram() {
        List<String> hologramTopXP = getTopXP();

        World world = Bukkit.getWorld("lobbypvp");

        double x = -9.893;
        double y =  68.78314;
        double z =  -0.640;
        Location hologramLocation = new Location(world, x, y, z);

        Bukkit.getScheduler().runTask(BukkitMain.getInstance(), () -> {
            if (topKillsHologram == null) {
                topKillsHologram = HologramsAPI.createHologram(BukkitMain.getInstance(), hologramLocation);
            } else {
                topKillsHologram.teleport(hologramLocation);
                topKillsHologram.clearLines();

            }
            topKillsHologram.appendTextLine("§a§l§nTOP 10 RANKING");
            topKillsHologram.appendTextLine("");
            for (String line : hologramTopXP) {
                topKillsHologram.appendTextLine(line);
            }
        });
    }
}
