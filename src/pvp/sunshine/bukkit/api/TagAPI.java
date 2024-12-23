package pvp.sunshine.bukkit.api;

import java.util.Collections;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.manager.TagPacket;
import pvp.sunshine.bukkit.manager.Team;

public class TagAPI implements Listener {

	public static int count;
	/* 21 */ public static HashSet<Team> teams = new HashSet<>();

	public static boolean visible = true;

	private static void checkTeam(Team teamInfo) {
		/* 26 */ if (teamInfo.getPlayers().isEmpty()) {
			/* 27 */ TagPacket packetInfo = new TagPacket(teamInfo.getName(), null, null, null, 1);
			/* 28 */ for (Player player : Bukkit.getOnlinePlayers()) {
				/* 29 */ packetInfo.sendPacket(player);
			}
			/* 31 */ teams.remove(teamInfo);
		}
	}

	private static Team getPlayerTeam(String player) {
		/* 36 */ for (Team team : teams) {
			/* 37 */ if (team.getPlayers().contains(player)) {
				/* 38 */ return team;
			}
		}
		/* 41 */ return null;
	}

	private static Team getTeam(String teamName, String prefix, String suffix) {
		/* 45 */ for (Team team : teams) {
			/* 46 */ if (team.getPrefix().equals(prefix) && team.getSuffix().equals(suffix)) {
				/* 47 */ return team;
			}
		}
		/* 50 */ Team teamInfo = new Team(String.valueOf(String.valueOf(teamName)) + count++);
		/* 51 */ teamInfo.setPrefix(prefix);
		/* 52 */ teamInfo.setSuffix(suffix);
		/* 53 */ teams.add(teamInfo);
		/* 54 */ TagPacket packetInfo = new TagPacket(teamInfo.getName(), prefix, suffix, teamInfo.getPlayers(), 0);
		/* 55 */ for (Player player : Bukkit.getOnlinePlayers()) {
			/* 56 */ packetInfo.sendPacket(player);
		}
		/* 58 */ return teamInfo;
	}

	public static void removeTag(String playerName) {
		/* 62 */ Team oldTeam = getPlayerTeam(playerName);
		/* 63 */ if (oldTeam != null) {
			/* 64 */ oldTeam.getPlayers().remove(playerName);
			/* 65 */ TagPacket packetInfo = new TagPacket(oldTeam.getName(), Collections.singleton(playerName), 4);
			/* 66 */ for (Player player : Bukkit.getOnlinePlayers()) {
				/* 67 */ packetInfo.sendPacket(player);
			}
			/* 69 */ checkTeam(oldTeam);
		}
	}

	public static void setNameTag(final String playerName, final String teamName, final String prefix,
								  final String suffix) {
		/* 74 */ (new BukkitRunnable() {
			public void run() {
				/* 76 */ TagAPI.removeTag(playerName);
				/* 77 */ Team teamInfo = TagAPI.getTeam(teamName, prefix, suffix);
				/* 78 */ if (teamInfo.getPlayers().contains(playerName)) {
					return;
				}
				/* 81 */ teamInfo.getPlayers().add(playerName);
				/* 82 */ TagPacket packetInfo = new TagPacket(teamInfo.getName(), Collections.singleton(playerName), 3);
				/* 83 */ for (Player player : Bukkit.getOnlinePlayers()) {
					/* 84 */ packetInfo.sendPacket(player);
				}
			}
			/* 87 */ }).runTaskAsynchronously((Plugin) BukkitMain.getInstance());
	}

	public static void setNameTagVisible(boolean visible) {
		/* 91 */ TagAPI.visible = visible;
		/* 92 */ for (Team team : teams) {
			/* 93 */ TagPacket tagPacket = new TagPacket(team.getName(), team.getPrefix(), team.getSuffix(),
					team.getPlayers(), 2);
			/* 94 */ for (Player player : Bukkit.getOnlinePlayers()) {
				/* 95 */ tagPacket.sendPacket(player);
			}
		}
	}

	public static void setPrefix(String playerName, String prefix) {
		/* 101 */ setNameTag(playerName, "team", prefix, "");
	}

	public static void setPrefix(String playerName, String teamName, String prefix) {
		/* 105 */ setNameTag(playerName, teamName, prefix, "");
	}

	public static void setSuffix(String playerName, String suffix) {
		/* 109 */ setNameTag(playerName, "team", "", suffix);
	}

	public static void setSuffix(String playerName, String teamName, String suffix) {
		/* 113 */ setNameTag(playerName, teamName, "", suffix);
	}

	public static void setTag(String playerName, String teamName, String prefix, String suffix) {
		/* 117 */ setNameTag(playerName, teamName, prefix, suffix);
	}

	@EventHandler
	public void join(PlayerJoinEvent event) {
		/* 122 */ for (Team teamInfo : teams) {
			/* 123 */ TagPacket packetInfo = new TagPacket(teamInfo.getName(), teamInfo.getPrefix(),
					teamInfo.getSuffix(), teamInfo.getPlayers(), 0);
			/* 124 */ packetInfo.sendPacket(event.getPlayer());
		}
	}

	@EventHandler
	public void quit(PlayerQuitEvent event) {
		/* 130 */ removeTag(event.getPlayer().getName());
	}
}