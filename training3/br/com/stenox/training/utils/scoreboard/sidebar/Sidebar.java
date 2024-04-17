// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.scoreboard.sidebar;

import java.util.HashMap;
import org.bukkit.scoreboard.Team;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.bukkit.ChatColor;
import java.util.Map;
import org.bukkit.scoreboard.Objective;
import br.com.stenox.training.utils.scoreboard.ScoreboardWrapper;

public class Sidebar
{
    private final ScoreboardWrapper scoreboardWrapper;
    private final Objective objective;
    private final Map<Integer, Row> rows;
    private boolean hided;
    private static final int MAX_LINES = 15;
    private static final ChatColor[] colors;
    
    public void setTitle(final String text) {
        if (text == null) {
            throw new NullPointerException("Title cannot be null");
        }
        if (text.length() > 32) {
            throw new IllegalArgumentException("Title too big");
        }
        this.objective.setDisplayName(text);
    }
    
    public void updateRows(final Consumer<List<String>> updateConsumer) {
        final List<String> rows = new ArrayList<String>(15);
        updateConsumer.accept(rows);
        this.setContent(rows);
    }
    
    public void setContent(final List<String> content) {
        if (content == null) {
            throw new NullPointerException("Content cannot be null");
        }
        if (content.size() > 15) {
            throw new IllegalStateException("Content cannot be longer than 15 lines");
        }
        if (content.isEmpty()) {
            this.rows.forEach((key, row) -> row.destroy());
            this.rows.clear();
            return;
        }
        final ListIterator<String> iterator = content.listIterator(content.size());
        for (int x = 1; x <= 15; ++x) {
            if (iterator.hasPrevious()) {
                this.setText(x, iterator.previous());
            }
            else {
                this.removeText(x);
            }
        }
    }
    
    public void setText(final int rowNumber, final String text) {
        this.validatePosition(rowNumber);
        if (text == null) {
            throw new NullPointerException("Text cannot be null");
        }
        final Row row = this.rows.computeIfAbsent(rowNumber, v -> this.createRow(rowNumber));
        String prefix = text;
        String suffix = "";
        if (text.length() > 16) {
            final int substr = (text.charAt(15) == '§') ? 15 : 16;
            prefix = text.substring(0, substr);
            final String lastColors = ChatColor.getLastColors(prefix);
            suffix = lastColors + text.substring(substr);
            if (suffix.length() > 16) {
                suffix = suffix.substring(0, 16);
            }
        }
        row.setPrefix(prefix);
        row.setSuffix(suffix);
    }
    
    public void removeText(final int position) {
        final Row row = this.rows.remove(position);
        if (row != null) {
            row.destroy();
        }
    }
    
    public void hide() {
        if (this.isHided()) {
            return;
        }
        this.setHided(true);
        if (this.objective != null) {
            this.objective.unregister();
        }
        for (int i = 1; i < 16; ++i) {
            final Team team = this.scoreboardWrapper.getTeam("sb_row: " + i);
            if (team != null) {
                team.unregister();
            }
        }
    }
    
    public void cleanup() {
        if (!this.rows.isEmpty()) {
            this.rows.values().forEach(Row::destroy);
            this.rows.clear();
        }
    }
    
    public boolean isInternalUsageTeam(final Team team) {
        return team != null && team.getName().startsWith("sb_row:");
    }
    
    private Row createRow(final int position) {
        final String score = Sidebar.colors[position - 1] + "" + ChatColor.RESET;
        final String teamName = "sb_row:" + position;
        this.objective.getScore(score).setScore(position);
        final Team team = this.scoreboardWrapper.getTeam(teamName, cteam -> cteam.addEntry(score));
        return new Row(score, team);
    }
    
    private void validatePosition(final int position) {
        if (position < 1 || position > 15) {
            throw new IllegalArgumentException("Scoreboard Row position \"" + position + "\" is invalid!");
        }
    }
    
    public Sidebar(final ScoreboardWrapper scoreboardWrapper, final Objective objective) {
        this.rows = new HashMap<Integer, Row>();
        this.hided = false;
        this.scoreboardWrapper = scoreboardWrapper;
        this.objective = objective;
    }
    
    public boolean isHided() {
        return this.hided;
    }
    
    public void setHided(final boolean hided) {
        this.hided = hided;
    }
    
    static {
        colors = ChatColor.values();
    }
    
    static class Row
    {
        private final String score;
        private final Team team;
        
        public void destroy() {
            this.team.getScoreboard().resetScores(this.score);
            this.team.unregister();
        }
        
        public void setPrefix(final String prefix) {
            this.team.setPrefix(prefix);
        }
        
        public void setSuffix(final String suffix) {
            this.team.setSuffix(suffix);
        }
        
        public String getScore() {
            return this.score;
        }
        
        public Team getTeam() {
            return this.team;
        }
        
        public Row(final String score, final Team team) {
            this.score = score;
            this.team = team;
        }
    }
    
    public interface Update
    {
    }
}
