// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.gamer.tag;

import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import br.com.stenox.training.gamer.Gamer;
import java.util.Iterator;
import org.bukkit.event.Listener;
import br.com.stenox.training.gamer.tag.listener.TagListener;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import java.util.HashMap;
import br.com.stenox.training.Main;
import java.util.Map;
import br.com.stenox.training.provider.Provider;

public class TagProvider extends Provider
{
    private Map<String, Tag> ntag;
    
    public TagProvider(final Main plugin) {
        super(plugin);
        this.ntag = new HashMap<String, Tag>();
    }
    
    @Override
    public void onEnable() {
        this.ntag.clear();
        for (final Player o : Bukkit.getOnlinePlayers()) {
            this.ntag.put(o.getName(), Tag.MEMBER);
            this.update(o);
        }
        this.registerListener((Listener)new TagListener(this));
    }
    
    public void updateTag(final Gamer gamer) {
        if (gamer == null) {
            return;
        }
        if (gamer.getPlayer() == null) {
            return;
        }
        Tag tag = gamer.getCurrentTag();
        if (tag == null) {
            tag = Tag.MEMBER;
        }
        this.setTag(gamer.getPlayer(), tag);
    }
    
    public Tag getTag(final Player p) {
        return this.ntag.get(p.getName());
    }
    
    public Tag getTag(final Player p, final Tag defaultTag) {
        return this.ntag.getOrDefault(p.getName(), defaultTag);
    }
    
    public boolean currentTag(final Player p, final Tag tag) {
        return this.ntag.getOrDefault(p.getName(), Tag.MEMBER) == tag;
    }
    
    public void removePlayerTag(final Player p) {
        p.setDisplayName(p.getName());
        p.getScoreboard().getTeams().forEach(t -> {
            if (t.getName().startsWith("tag:")) {
                t.unregister();
            }
            return;
        });
        for (final Player on : Bukkit.getOnlinePlayers()) {
            if (!on.equals(p) && !on.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
                on.getScoreboard().getTeams().forEach(t -> this.removeTeamEntry(t, p.getName()));
            }
        }
        this.ntag.remove(p.getName());
    }
    
    public void removeTeamEntry(final Team team, final String entry) {
        if (team.getName().startsWith("tag:")) {
            team.removeEntry(entry);
            if (team.getEntries().isEmpty()) {
                team.unregister();
            }
        }
    }
    
    public void setTag(final Player player, final Tag tag) {
        this.ntag.put(player.getName(), tag);
        this.update(player);
    }
    
    public void updateCurrent(final Player p) {
        this.update(p);
    }
    
    public void update(final Player p) {
        final Tag tag = this.ntag.getOrDefault(p.getName(), Tag.MEMBER);
        final String prefix = tag.getPrefix();
        final String teamOrder = "tag:" + tag.getPosition() + p.getEntityId();
        p.setDisplayName(prefix + p.getName());
        final Team now = this.createTeamIfNotExists(p, p.getName(), teamOrder, prefix, "");
        now.setCanSeeFriendlyInvisibles(false);
        for (final Team old : p.getScoreboard().getTeams()) {
            if (old.hasEntry(p.getName()) && !old.getName().equals(now.getName())) {
                old.unregister();
            }
        }
        for (final Player o : Bukkit.getOnlinePlayers()) {
            if (o == p) {
                continue;
            }
            final Gamer online = Gamer.getGamer(o.getName());
            if (online == null) {
                continue;
            }
            final Tag t = this.ntag.getOrDefault(o.getName(), Tag.MEMBER);
            final String globalPrefix = t.getPrefix();
            final String globalSuffix = "";
            final String globalTeamOrder = "tag:" + t.getPosition() + o.getEntityId();
            final Team to = this.createTeamIfNotExists(p, o.getName(), globalTeamOrder, globalPrefix, globalSuffix);
            to.setCanSeeFriendlyInvisibles(false);
            for (final Team old2 : p.getScoreboard().getTeams()) {
                if (old2.hasEntry(o.getName()) && !old2.getName().equals(to.getName())) {
                    old2.unregister();
                }
            }
            this.createTeamIfNotExists(o, p.getName(), now.getName(), now.getPrefix(), now.getSuffix());
        }
    }
    
    public Team createTeamIfNotExists(final Player p, final String entry, final String teamID, final String prefix, final String suffix) {
        Scoreboard scoreboard = p.getScoreboard();
        if (scoreboard.equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            p.setScoreboard(scoreboard);
        }
        Team team = scoreboard.getTeam(teamID);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamID);
        }
        if (!team.hasEntry(entry)) {
            team.addEntry(entry);
        }
        team.setPrefix(prefix);
        team.setSuffix(suffix);
        return team;
    }
    
    @Override
    public void onDisable() {
        for (final Player o : Bukkit.getOnlinePlayers()) {
            this.removePlayerTag(o);
        }
        this.ntag.clear();
        this.ntag = null;
    }
}
