package pvp.sunshine.bukkit.manager.mysql.connections;

import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.manager.mysql.Storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SQLShop extends Storage {

    public static Map<String, String> cacheshop = new HashMap<>();

    public static String getKits(Player p) {
        return cacheshop.get(p.getName());
    }

    public static void createTable() {
        try {
            PreparedStatement ps = getStatement("CREATE TABLE IF NOT EXISTS ShopKit (NICK VARCHAR(100), Kits VARCHAR(300))");
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getKits(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM ShopKit WHERE NICK= '" + name + "'");
            ResultSet rs = ps.executeQuery();
            rs.next();
            String kits = rs.getString("Kits");
            rs.close();
            ps.close();
            return kits;
        } catch (Exception localException) {
        }
        return "Nenhum";
    }

    public static void registerShop(String nick) {
        try {
           
            PreparedStatement ps = getStatement("SELECT * FROM ShopKit WHERE NICK= '" + nick + "'");
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
            	 connection.createStatement()
                 .executeUpdate("INSERT INTO `ShopKit`(`NICK`, `Kits`) VALUES ('" + nick + "','Nenhum')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkShop(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM ShopKit WHERE NICK='" + name + "'");
            ResultSet rs = ps.executeQuery();
            boolean user = rs.next();
            rs.close();
            ps.close();
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void updateShop(Player p) {
        try {
            if (cacheshop.containsKey(p.getName())) {
                connection.createStatement().executeUpdate("UPDATE `ShopKit` SET `NICK`='" + p.getName()
                        + "',`Kits`='" + getKits(p) + "' WHERE `NICK`='" + p.getName() + "';");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cacheshop.remove(p.getName());
        }
    }

    public static void cacheLoad(String name) {
        String kits = getKits(name);
        if (kits.equalsIgnoreCase("null")) {
            cacheshop.put(name, "nenhum");
        } else {
            cacheshop.put(name, getKits(name).toLowerCase());
        }
    }

    public static void addkit(Player p, String kit) {
        cacheshop.replace(p.getName(), cacheshop.get(p.getName()) + " " + kit);
    }
}
