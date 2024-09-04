package pvp.sunshine.bukkit.manager;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.ScoreboardTeamBase;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.api.TagAPI;

import java.util.Collection;
import java.util.HashSet;

@SuppressWarnings("all")
public class TagPacket {
	private PacketPlayOutScoreboardTeam packet;

	public TagPacket(String name, Collection<String> players, int updateType) {
		this.reflectionManager = new ReflectionWrapper();
		this.packet = new PacketPlayOutScoreboardTeam();
		if (players == null || players.isEmpty()) {
			players = new HashSet<>();
		}
		this.reflectionManager.begin(this.packet);
		this.reflectionManager.setString("a", name);
		this.reflectionManager.setInteger("h", updateType);
		this.reflectionManager.<String>getCollection("g").addAll(players);
	}

	private ReflectionWrapper reflectionManager;

	public TagPacket(String name, String prefix, String suffix, Collection<String> players, int updateType) {
		this.reflectionManager = new ReflectionWrapper();
		this.packet = new PacketPlayOutScoreboardTeam();
		this.reflectionManager.begin(this.packet);
		this.reflectionManager.setString("a", name);
		this.reflectionManager.setInteger("h", updateType);
		if (updateType == 0 || updateType == 2) {
			this.reflectionManager.setString("b", name);
			this.reflectionManager.setString("c", prefix);
			this.reflectionManager.setString("d", suffix);
			this.reflectionManager.setInteger("i", 1);
			if (!TagAPI.visible) {
				this.reflectionManager.setString("e", ScoreboardTeamBase.EnumNameTagVisibility.NEVER.e);
			}
		}
		if (updateType == 0) {
			this.reflectionManager.<String>getCollection("g").addAll(players);
		}
	}

	public void sendPacket(Player player) {
		(((CraftPlayer) player).getHandle()).playerConnection.sendPacket((Packet) this.packet);
	}
}