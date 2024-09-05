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

import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.SunshineFormat;
import pvp.sunshine.bukkit.manager.mysql.Storage;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class TopWins2 extends Storage {

    private static Hologram topWinsHologram;
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

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(playerName));

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

    public static void incrementWins(Player player) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE 1v1 SET Wins = Wins + 1 WHERE UUID = ?")) {
            ps.setString(1, player.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getTopWins() {
        List<String> topWins = new LinkedList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM 1v1 ORDER BY Wins DESC LIMIT 10");
             ResultSet rs = ps.executeQuery()) {
            int index = 1;
            while (rs.next()) {
                String playerName = rs.getString("UUID");

                String Name = Bukkit.getOfflinePlayer(UUID.fromString(playerName)).getName();
                String tagColor = "";
                if (Name == null) {
                   tagColor = "§7" ;
                } else {
                   
                
                    tagColor = getTagColor(playerName);
                    }
                topWins.add("§6" + index + "º §7- " + tagColor + Name + " §f- Vitórias: §e" + SunshineFormat.format(rs.getInt("Wins")));
                index++;
            }
            if (topWins.isEmpty()) {
                topWins.add("§61º §7- Carregando...");
                topWins.add("§62º §7- Carregando...");
                topWins.add("§63º §7- Carregando...");
                topWins.add("§64º §7- Carregando...");
                topWins.add("§65º §7- Carregando...");
                topWins.add("§66º §7- Carregando...");
                topWins.add("§67º §7- Carregando...");
                topWins.add("§68º §7- Carregando...");
                topWins.add("§69º §7- Carregando...");
                topWins.add("§610º §7- Carregando...");
            } else {
                for (int i = index; i <= 10; i++) {
                    topWins.add("§6" + i + "º §7- Carregando...");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topWins;
    }

    public static void updateHologram() {
        List<String> hologramTopWins = getTopWins();

        World world = Bukkit.getWorld("1v1");
        double x = -5.887;
        double y =  47.98041;
        double z =  9.836;
        Location hologramLocation = new Location(world, x, y, z);

        Bukkit.getScheduler().runTask(BukkitMain.getInstance(), () -> {
            if (topWinsHologram == null) {
                topWinsHologram = HologramsAPI.createHologram(BukkitMain.getInstance(), hologramLocation);
            } else {
                topWinsHologram.teleport(hologramLocation);
                topWinsHologram.clearLines();
            }
            topWinsHologram.appendTextLine("§a§l§nTOP 10 WINS");
            topWinsHologram.appendTextLine("");
            for (String line : hologramTopWins) {
                topWinsHologram.appendTextLine(line);
            }
        });
    }
}