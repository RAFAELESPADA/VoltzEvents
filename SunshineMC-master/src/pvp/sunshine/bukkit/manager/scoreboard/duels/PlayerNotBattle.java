package pvp.sunshine.bukkit.manager.scoreboard.duels;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.SunshineFormat;
import pvp.sunshine.bukkit.api.WinStreakAPI;
import pvp.sunshine.bukkit.manager.mysql.connections.SQL1v1;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;
import pvp.sunshine.bukkit.manager.scoreboard.SunshineAnimation;

public class PlayerNotBattle {

    private static SunshineAnimation waveAnimation;
    private static String text = "";

    public static void init() {
        waveAnimation = new SunshineAnimation(" DUELS ", "§f§l", "§e§l", "§6§l", 3);
        text = waveAnimation.next();

        Bukkit.getScheduler().runTaskTimer(BukkitMain.getInstance(), () -> {
            text = waveAnimation.next();

            Bukkit.getOnlinePlayers().stream()
                    .filter(Player::isOnline)
                    .filter(player -> !player.isDead())
                    .forEach(onlines -> {
                        Scoreboard score = onlines.getScoreboard();
                        if (score == null) {
                            return;
                        }
                        Objective objective = score.getObjective(DisplaySlot.SIDEBAR);
                        if (objective == null) {
                            return;
                        }
                        if (onlines.getPlayer().getScoreboard().getObjective("1v1") != null) {
                            objective.setDisplayName(text);
                        }
                    });
        }, 40, 2L);
    }

    public static void create(Player p) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("1v1", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        addLine(board, "§b", 9);
        addLine(board, "§f  Vitórias: §7" + SunshineFormat.format(SQL1v1.getWins(p)), 8);
        addLine(board, "§f  Derrotas: §7" + SunshineFormat.format(SQL1v1.getLoses(p)), 7);
        addLine(board, "§f  Winstreak: §7" + SunshineFormat.format(WinStreakAPI.getStreak(p)), 6);
        addLine(board, "§3", 5);
        addLine(board, "§f  Modo: §e1v1",4);
        addLine(board, "§f  Ranking: " + SQLRank.getRank(p) + " " + SQLRank.getRankComplete(SQLRank.getXp(p)), 3);
        addLine(board, "§1", 2);
        addLine(board, "   §7kombopvp.com", 1);

        p.setScoreboard(board);
    }

    public static void update(Player p) {
        Scoreboard scoreboard = p.getScoreboard();
        if (scoreboard.getObjective("PvP") != null) {
            scoreboard.clearSlot(DisplaySlot.SIDEBAR);
            scoreboard.getObjective("PvP").unregister();
        }
        if (scoreboard.getObjective("Lava") != null) {
            scoreboard.clearSlot(DisplaySlot.SIDEBAR);
            scoreboard.getObjective("Lava").unregister();
        }
        if (scoreboard.getObjective("Evento") != null) {
            scoreboard.clearSlot(DisplaySlot.SIDEBAR);
            scoreboard.getObjective("Evento").unregister();
        }
        if (scoreboard.getObjective("1v1") != null) {
            scoreboard.clearSlot(DisplaySlot.SIDEBAR);
            scoreboard.getObjective("1v1").unregister();
            create(p);
        } else {
            create(p);

        }
    }

    private static void addLine(Scoreboard scoreboard, String text, int score) {
        Team team = scoreboard.registerNewTeam("line" + score);
        team.addEntry(text);
        scoreboard.getObjective("1v1").getScore(text).setScore(score);
    }
}
