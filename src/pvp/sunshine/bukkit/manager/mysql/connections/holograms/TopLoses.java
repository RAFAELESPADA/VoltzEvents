package pvp.sunshine.bukkit.manager.mysql.connections.holograms;

import me.filoghost.holographicdisplays.core.api.current.APIHologramManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.SunshineFormat;
import pvp.sunshine.bukkit.manager.mysql.Storage;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class TopLoses extends Storage {

    private static Hologram topLosesHologram;
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
            String groupName = TopDeaths.giveMeADamnUser(offlinePlayer.getUniqueId()).getPrimaryGroup(); // Obtém o grupo do jogador do banco de dados

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

    public static void incrementLoses(Player player) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE 1v1 SET Loses = Loses + 1 WHERE NICK = ?")) {
            ps.setString(1, player.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getTopLoses() {
        List<String> topLoses = new LinkedList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM 1v1 ORDER BY Loses DESC LIMIT 10");
             ResultSet rs = ps.executeQuery()) {
            int index = 1;
            while (rs.next()) {
                String playerName = rs.getString("NICK");
                String tagColor = getTagColor(playerName);
                if (playerName == null) {
                   tagColor = "§7" 
                }
                topLoses.add("§6" + index + "º §7- " + tagColor + playerName + " §f- Derrotas: §e" + SunshineFormat.format(rs.getInt("Loses")));
                index++;
            }
            if (topLoses.isEmpty()) {
                topLoses.add("§61º §7- Carregando...");
                topLoses.add("§62º §7- Carregando...");
                topLoses.add("§63º §7- Carregando...");
                topLoses.add("§64º §7- Carregando...");
                topLoses.add("§65º §7- Carregando...");
                topLoses.add("§66º §7- Carregando...");
                topLoses.add("§67º §7- Carregando...");
                topLoses.add("§68º §7- Carregando...");
                topLoses.add("§69º §7- Carregando...");
                topLoses.add("§610º §7- Carregando...");
            } else {
                for (int i = index; i <= 10; i++) {
                    topLoses.add("§6" + i + "º §7- Carregando...");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topLoses;
    }

    public static void updateHologram() {
        List<String> hologramTopLoses = getTopLoses();
        World world = Bukkit.getWorld("1v1");
        double x = -21.708;
        double y =  47.98041;
        double z =  7.343;
        Location hologramLocation = new Location(world, x, y, z);

        Bukkit.getScheduler().runTask(BukkitMain.getInstance(), () -> {
            if (topLosesHologram == null) {
                topLosesHologram = HologramsAPI.createHologram(BukkitMain.getInstance(), hologramLocation);
            } else {
                topLosesHologram.teleport(hologramLocation);
                topLosesHologram.clearLines();
            }
            topLosesHologram.appendTextLine("§c§l§nTOP 10 LOSES");
            topLosesHologram.appendTextLine("");
            for (String line : hologramTopLoses) {
                topLosesHologram.appendTextLine(line);
            }
        });
    }
}
