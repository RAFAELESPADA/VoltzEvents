package pvp.sunshine.bukkit.manager.mysql.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.manager.mysql.Storage;

public class SQLClan extends Storage {

    public static String getTagPlayer(Player p) {
        if(!clan.get(p.getName()).equalsIgnoreCase("Nenhum")) {
            return " " + SQLRank.getRank(p) + "§e " + tag.get(p.getName());
        } else {
            return " " + SQLRank.getRank(p);
        }
    }

    public static boolean checkClan(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM Clan WHERE ClanName= ?");
            ps.setString(1, name);
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

    public static boolean checkTag(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM ClanRegistry WHERE Nome= ?");
            ps.setString(1, name);
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

    public static String getClanConnection(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM Clan WHERE NICK= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String clan = rs.getString("ClanName");
            rs.close();
            ps.close();
            return clan;
        } catch (Exception localException) {
        }
        return "Nenhum";
    }



    public static void registerClan(String nome, String tag, int xp) {
        try {
            PreparedStatement ps = getStatement("INSERT INTO ClanRegistry (Nome, Tag, Xp) VALUES (?, ?, ?)");
            ResultSet resultSet = ps.executeQuery("select * from ClanRegistry where Nome = '" + nome + "'");
            if (!resultSet.next()) {
            ps.setString(1, nome);
            ps.setString(2, tag);
            ps.setInt(3, xp);
            ps.executeUpdate();
            ps.close();
        }} catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void deleteClan(String nome) {
        try {
            PreparedStatement ps = getStatement("DELETE FROM `ClanRegistry` WHERE `Nome`='" + nome + "'");
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getCargoConnection(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM Clan WHERE NICK= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String Cargo = rs.getString("CargoName");
            rs.close();
            ps.close();
            return Cargo;
        } catch (Exception localException) {
        }
        return "Membro";
    }

    public static String getClanNameConnection(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM ClanRegistry WHERE Nome= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String Tag = rs.getString("Tag");
            rs.close();
            ps.close();
            return Tag;
        } catch (Exception localException) {
        }
        return "Nenhuma";
    }

    public static String getTagConnection(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM ClanRegistry WHERE Nome= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String Tag = rs.getString("Tag");
            rs.close();
            ps.close();
            return Tag;
        } catch (Exception localException) {
        }
        return "Nenhuma";
    }

    public static String getClanTagConnection(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM ClanRegistry WHERE Tag= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String Tag = rs.getString("Nome");
            rs.close();
            ps.close();
            return Tag;
        } catch (Exception localException) {
        }
        return "Nenhuma";
    }

    public static Integer getXp(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM ClanRegistry WHERE Nome= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int Tag = rs.getInt("Xp");
            rs.close();
            ps.close();
            return Tag;
        } catch (Exception localException) {
        }
        return 0;
    }

    public static void updateData(Player p) {
        if (clan.containsKey(p.getName()) && tag.containsKey(p.getName())) {
            String tagc = tag.get(p.getName());
            int totalx = 0;
            try {
                connection.createStatement().executeUpdate("UPDATE `Clan` SET `NICK`='" + p.getName() + "',`ClanName`='"
                        + getClan(p) + "',`CargoName`='" + getTag(p) + "' WHERE `NICK`='" + p.getName() + "';");

                PreparedStatement ps = SQLClan.connection
                        .prepareStatement("SELECT `NICK`, `ClanName` FROM `Clan` WHERE `ClanName`='"
                                + SQLClan.clan.get(p.getName()) + "'");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String tempcachename = rs.getString("NICK");
                    int xp = Integer.valueOf(SQLRank.getXpConnection(tempcachename));
                    totalx = totalx + xp;
                }
            } catch (SQLException localSQLException) {
                localSQLException.printStackTrace();

            }
            try {
                connection.createStatement()
                        .executeUpdate("UPDATE `ClanRegistry` SET `Nome`='" + clan.get(p.getName()) + "',`Tag`='"
                                + tagc + "',`Xp`='" + totalx + "' WHERE `Nome`='" + clan.get(p.getName()) + "';");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                cargo.remove(p.getName());
                clan.remove(p.getName());
                if (tag.containsKey(p.getName())) {
                    tag.remove(p.getName());
                }
            }
        }

    }


    public static void removeXp(int xp, String clan) throws SQLException {
        int XpClan = getXp(clan);
        XpClan = XpClan - xp;
        connection.createStatement()
                .executeUpdate("UPDATE `ClanRegistry` SET `Nome`='" + clan + "',`Tag`='"
                        + SQLClan.getTagConnection(clan) + "',`Xp`='" + XpClan
                        + "' WHERE `Nome`='" + clan + "';");
    }

    public static void updatePlayer(Player p) {
        if (clan.containsKey(p.getName()) && tag.containsKey(p.getName())) {
            String tagc = tag.get(p.getName());
            int totalx = 0;
            try {
                connection.createStatement().executeUpdate("UPDATE `Clan` SET `NICK`='" + p.getName() + "',`ClanName`='"
                        + getClan(p) + "',`CargoName`='" + getTag(p) + "' WHERE `NICK`='" + p.getName() + "';");
                PreparedStatement ps = SQLClan.connection
                        .prepareStatement("SELECT `NICK`, `ClanName`, `CargoName` FROM `Clan` WHERE `ClanName`='"
                                + SQLClan.clan.get(p.getName()) + "'");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String tempcachename = rs.getString("NICK");
                    int xp = Integer.valueOf(SQLRank.getXpConnection(tempcachename));
                    totalx = totalx + xp;
                }
            } catch (SQLException localSQLException) {
                localSQLException.printStackTrace();

            }
            try {
                connection.createStatement()
                        .executeUpdate("UPDATE `ClanRegistry` SET `Nome`='" + clan.get(p.getName()) + "',`Tag`='"
                                + tagc + "',`Xp`='" + totalx + "' WHERE `Nome`='" + clan.get(p.getName()) + "';");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                cargo.remove(p.getName());
                clan.remove(p.getName());
                if (tag.containsKey(p.getName())) {
                    tag.remove(p.getName());
                }
                loadCache(p.getName());
            }
        }

    }

    public static void updateConvite(Player p) {
        if (clan.containsKey(p.getName()) && tag.containsKey(p.getName())) {
            String tagc = tag.get(p.getName());
            int totalx = 0;
            try {
                connection.createStatement().executeUpdate("UPDATE `Clan` SET `NICK`='" + p.getName() + "',`ClanName`='"
                        + getClan(p) + "',`CargoName`='" + getTag(p) + "' WHERE `NICK`='" + p.getName() + "';");
                PreparedStatement ps = SQLClan.connection
                        .prepareStatement("SELECT `NICK`, `ClanName`, `CargoName` FROM `Clan` WHERE `ClanName`='"
                                + SQLClan.clan.get(p.getName()) + "'");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String tempcachename = rs.getString("NICK");
                    int xp = Integer.valueOf(SQLRank.getXpConnection(tempcachename));
                    totalx = totalx + xp;
                }
            } catch (SQLException localSQLException) {
                localSQLException.printStackTrace();

            }
            try {

                connection.createStatement()
                        .executeUpdate("UPDATE `ClanRegistry` SET `Nome`='" + clan.get(p.getName()) + "',`Tag`='"
                                + tagc + "',`Xp`='" + totalx + "' WHERE `Nome`='"
                                + clan.get(p.getName()) + "';");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static void removeAllClans(String name) {
        try {
            connection.createStatement().executeUpdate("UPDATE `Clan` SET `NICK`='" + name
                    + "',`ClanName`='Nenhum',`CargoName`='Membro' WHERE `NICK`='" + name + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> clan = new HashMap<String, String>();
    public static Map<String, String> cargo = new HashMap<String, String>();
    public static Map<String, String> tag = new HashMap<String, String>();

    public static void loadCache(String p) {
        String sql = SQLClan.getClanConnection(p);
        if (!sql.equalsIgnoreCase("null")) {
            cargo.put(p, SQLClan.getCargoConnection(p));
            clan.put(p, sql);
            String tagS = getTagConnection(sql);
            if (!tagS.equalsIgnoreCase("Nenhuma")) {
                tag.put(p, tagS);
            }
        } else {
            cargo.put(p, "Membro");
            clan.put(p, "Nenhum");
        }
    }

    public static String getTag(Player p) {
        return cargo.get(p.getName());
    }

    public static String getClan(Player p) {
        return clan.get(p.getName());
    }

    public static boolean checkName(String name) {
        return Pattern.compile("[a-zA-Z0-9_]{1,12}").matcher(name).matches();
    }

    public static boolean checkClanPlayer(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM Clan WHERE NICK= ?");
            ps.setString(1, name);
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

    public static void registerClan(String nick) {
        try {
            String query = "INSERT INTO `Clan`(`NICK`, `ClanName`, `CargoName`) VALUES (?, 'Nenhum', 'Membro')";
            
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery("select * from Clan where NICK = '" + nick + "'");
            if (!resultSet.next()) {
            statement.setString(1, nick);
            statement.executeUpdate();
            statement.close();
            }} catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
