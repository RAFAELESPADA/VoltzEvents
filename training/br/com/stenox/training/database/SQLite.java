// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.io.File;
import br.com.stenox.training.Main;
import java.sql.Connection;

public class SQLite
{
    private Connection connection;
    private final Main plugin;
    private String url;
    
    public SQLite(final Main plugin) {
        this.plugin = plugin;
    }
    
    public void openConnection() {
        if (!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdir();
        }
        final File file = new File(this.plugin.getDataFolder(), "database.db");
        this.url = "jdbc:sqlite:" + file;
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(this.url);
            System.out.println("[training] - SQLite Connected.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
                this.connection = null;
                System.out.println("[training] - SQLite Closed.");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void createTables() {
        try {
            final PreparedStatement ps = this.connection.prepareStatement("CREATE TABLE IF NOT EXISTS `players`(`name` TEXT, `tags` TEXT, `group` TEXT, `punishments` TEXT);");
            ps.execute();
            ps.close();
            System.out.println("[training] - SQLite Tables Created");
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    
    public Connection getConnection() {
        return this.connection;
    }
    
    public Main getPlugin() {
        return this.plugin;
    }
    
    public String getUrl() {
        return this.url;
    }
}
