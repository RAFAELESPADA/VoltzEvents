// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.gamer.tag.listener;

import org.bukkit.entity.Player;
import br.com.stenox.training.event.custom.TimeSecondEvent;
import java.util.Iterator;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import br.com.stenox.training.gamer.tag.TagProvider;
import org.bukkit.event.Listener;

public class TagListener implements Listener
{
    private TagProvider provider;
    
    public TagListener(final TagProvider provider) {
        this.provider = provider;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(final PlayerQuitEvent event) {
        this.provider.removePlayerTag(event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerQuitListener(final PlayerQuitEvent e) {
        Scoreboard board = e.getPlayer().getScoreboard();
        if (board != null) {
            for (Team t : board.getTeams()) {
                t.unregister();
                t = null;
            }
            for (Objective ob : board.getObjectives()) {
                ob.unregister();
                ob = null;
            }
            board = null;
        }
        e.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }
    
    @EventHandler
    public void onTimeSecond(final TimeSecondEvent event) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.provider.update(player);
        }
    }
}
