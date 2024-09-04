package pvp.sunshine.bukkit.manager.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.SunshineFormat;
import pvp.sunshine.bukkit.api.KillStreakAPI;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLClan;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLPvP;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;
import pvp.sunshine.bukkit.utils.GroupUtil;

public class PvP {

	private static SunshineAnimation waveAnimation;
	private static String text = "";

	public static void init() {
		waveAnimation = new SunshineAnimation(" KITPVP ", "§f§l", "§e§l", "§6§l", 3);
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
						if (onlines.getPlayer().getScoreboard().getObjective("PvP") != null) {
							objective.setDisplayName(text);
						}
					});
		}, 40, 2L);
	}

	public static void create(Player p) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective obj = board.registerNewObjective("PvP", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		if (SQLPvP.getKills(p) == null || SQLPvP.getDeaths(p) == null || SQLPvP.getCoins(p) == null) {
        	p.kickPlayer(ChatColor.RED + "ENCONTRAMOS UM ERRO COM SUA CONTA! RELOGUE PARA ARRUMAR");
        }
		addLine(board, "§3", 9);
		addLine(board, "§f  Kills: §7" + SunshineFormat.format(SQLPvP.getKills(p)), 8);
		addLine(board, "§f  Deaths: §7" + SunshineFormat.format(SQLPvP.getDeaths(p)), 7);
		addLine(board, "§f  Killstreak: §7" + SunshineFormat.format(KillStreakAPI.getStreak(p)), 6);
		addLine(board, "§0", 5);
		addLine(board, "§f  Coins: §e" + SunshineFormat.format(SQLPvP.getCoins(p)), 4);
		addLine(board, "§f  Ranking: " + SQLRank.getRank(p) + " " + SQLRank.getRankComplete(SQLRank.getXp(p)), 3);
		addLine(board, "§1", 2);
		addLine(board, "§7" + BukkitMain.getInstance().getConfig().getString("IP"), 1);

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
		if (scoreboard.getObjective("PlayerInBattle") != null) {
			scoreboard.clearSlot(DisplaySlot.SIDEBAR);
			scoreboard.getObjective("PlayerInBattle").unregister();
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
		scoreboard.getObjective("PvP").getScore(text).setScore(score);
	}
}
