package pvp.sunshine.bukkit.manager.mysql.connections.holograms;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
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

public class TopDeaths extends Storage {

    private static Hologram topDeathsHologram;
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

    public static void incrementDeaths(Player player) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE PvP SET Deaths = Deaths + 1 WHERE UUID = ?")) {
            ps.setString(1, player.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getTopDeaths() {
        List<String> topDeaths = new LinkedList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM PvP ORDER BY Deaths DESC LIMIT 10");
             ResultSet rs = ps.executeQuery()) {
            int index = 1;
            while (rs.next()) {
                String playerName = rs.getString("UUID");

                String Name = Bukkit.getOfflinePlayer(UUID.fromString(playerName)).getName();
                String tagColor = "";
                if (playerName == null) {
                   tagColor = "§7" ;
                } else {
                   
                
                    tagColor = getTagColor(playerName);
                    }
                topDeaths.add("§c" + index + "º §7- " + tagColor + Name + " §f- Deaths: §e" + SunshineFormat.format(rs.getInt("Deaths")));
                index++;
            }
            if (topDeaths.isEmpty()) {
                topDeaths.add("§c1º §7- Carregando...");
                topDeaths.add("§c2º §7- Carregando...");
                topDeaths.add("§c3º §7- Carregando...");
                topDeaths.add("§c4º §7- Carregando...");
                topDeaths.add("§c5º §7- Carregando...");
                topDeaths.add("§c6º §7- Carregando...");
                topDeaths.add("§c7º §7- Carregando...");
                topDeaths.add("§c8º §7- Carregando...");
                topDeaths.add("§c9º §7- Carregando...");
                topDeaths.add("§c10º §7- Carregando...");
            } else {
                for (int i = index; i <= 10; i++) {
                    topDeaths.add("§c" + i + "º §7- Carregando...");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topDeaths;
    }

    public static void updateHologram() {
        List<String> hologramTopDeaths = getTopDeaths();

        World world = Bukkit.getWorld("lobbypvp2");
        double x = 521.261;
        double y =  17.84010 ;
        double z = 627.213;
        Location hologramLocation = new Location(world, x, y, z);

        Bukkit.getScheduler().runTask(BukkitMain.getInstance(), () -> {
            if (topDeathsHologram == null) {
                topDeathsHologram = HologramsAPI.createHologram(BukkitMain.getInstance(), hologramLocation);
            } else {
                topDeathsHologram.teleport(hologramLocation);
                topDeathsHologram.clearLines();
            }
            topDeathsHologram.appendTextLine("§c§l§nTOP 10 DEATHS");
            topDeathsHologram.appendTextLine("");
            for (String line : hologramTopDeaths) {
                topDeathsHologram.appendTextLine(line);
            }
        });
    }
}