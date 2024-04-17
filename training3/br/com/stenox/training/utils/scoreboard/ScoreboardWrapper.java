// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.scoreboard;

import java.util.Set;
import java.util.function.Consumer;
import org.bukkit.scoreboard.Objective;
import java.util.function.Predicate;
import org.bukkit.scoreboard.Team;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import br.com.stenox.training.utils.scoreboard.sidebar.Sidebar;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardWrapper
{
    private final Scoreboard bukkitScoreboard;
    private final Sidebar sidebar;
    
    public ScoreboardWrapper(final Scoreboard bukkitScoreboard) {
        this.bukkitScoreboard = bukkitScoreboard;
        this.sidebar = new Sidebar(this, this.getObjective(DisplaySlot.SIDEBAR));
    }
    
    public void removePlayerFromTeams(final Player player) {
        this.bukkitScoreboard.getTeams().forEach(t -> {
            t.removeEntry(player.getName());
            if (t.getEntries().isEmpty()) {
                t.unregister();
            }
        });
    }
    
    public boolean removePlayerFromTeam(final String teamName, final Player player) {
        final Team team = this.bukkitScoreboard.getTeam(teamName);
        if (team != null && team.hasEntry(player.getName())) {
            team.removeEntry(player.getName());
            if (team.getEntries().isEmpty()) {
                team.unregister();
            }
            return true;
        }
        return false;
    }
    
    public void destroyTeams(final Predicate<Team> predicate) {
        this.bukkitScoreboard.getTeams().forEach(t -> {
            if (predicate.test(t)) {
                t.unregister();
            }
        });
    }
    
    public void unregisterObjective(final DisplaySlot displaySlot) {
        final Objective obj = this.bukkitScoreboard.getObjective(displaySlot.name());
        if (obj != null) {
            obj.unregister();
        }
    }
    
    public Objective getObjective(final DisplaySlot displaySlot) {
        return this.getObjective(displaySlot, "dummy", null);
    }
    
    public Objective getObjective(final DisplaySlot displaySlot, final String criteria) {
        return this.getObjective(displaySlot, criteria, null);
    }
    
    public Objective getObjective(final DisplaySlot displaySlot, final String criteria, final Consumer<Objective> onNewObjective) {
        Objective obj = this.bukkitScoreboard.getObjective(displaySlot.name());
        if (obj == null) {
            obj = this.bukkitScoreboard.registerNewObjective(displaySlot.name(), criteria);
            obj.setDisplaySlot(displaySlot);
            if (onNewObjective != null) {
                onNewObjective.accept(obj);
            }
        }
        return obj;
    }
    
    public Team getTeam(final String name) {
        return this.getTeam(name, null);
    }
    
    public Team getTeam(final String name, final Consumer<Team> onNewTeamCreated) {
        Team team = this.bukkitScoreboard.getTeam(name);
        if (team == null) {
            team = this.bukkitScoreboard.registerNewTeam(name);
            if (onNewTeamCreated != null) {
                onNewTeamCreated.accept(team);
            }
        }
        return team;
    }
    
    public Set<Team> getTeams() {
        return (Set<Team>)this.bukkitScoreboard.getTeams();
    }
    
    public Scoreboard getBukkitScoreboard() {
        return this.bukkitScoreboard;
    }
    
    public Sidebar getSidebar() {
        return this.sidebar;
    }
}
