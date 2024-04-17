// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.gamer;

import br.com.stenox.training.utils.JsonUtils;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class GamerRepository
{
    private final DataSource dataSource;
    private final GamerAdapter adapter;
    
    public Gamer fetch(final String name) {
        try (final Connection connection = this.dataSource.getConnection()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM `players` WHERE `name` = '" + name + "';");
            final ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            final Gamer gamer = this.adapter.read(resultSet);
            resultSet.close();
            statement.close();
            return gamer;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void insert(final Gamer occurrence) {
        try (final Connection connection = this.dataSource.getConnection()) {
            final PreparedStatement statement = connection.prepareStatement("INSERT INTO `players`(`name`, `tags`, `group`, `punishments`) VALUES ('" + occurrence.getName() + "', '" + "', '" + occurrence.getGroup().getName() + "', '" + JsonUtils.makePunishments(occurrence) + "');");
            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean contains(final String name) {
        try (final Connection connection = this.dataSource.getConnection()) {
            final PreparedStatement statement = connection.prepareStatement("SELECT * FROM `players` WHERE `name` = '" + name + "';");
            final ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return false;
            }
            rs.close();
            statement.close();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void update(final Gamer occurrence) {
        try (final Connection connection = this.dataSource.getConnection()) {
            if (this.contains(occurrence.getName())) {
                final PreparedStatement statement = connection.prepareStatement("UPDATE `players` SET `tags` = '" + "', `group` = '" + occurrence.getGroup() + "', `punishments` = '" + JsonUtils.makePunishments(occurrence) + "' WHERE `name` = '" + occurrence.getName() + "';");
                statement.executeUpdate();
                statement.close();
            }
            else {
                this.insert(occurrence);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public GamerRepository(final DataSource dataSource) {
        this.adapter = new GamerAdapter();
        this.dataSource = dataSource;
    }
}
