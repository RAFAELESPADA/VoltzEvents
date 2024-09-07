package pvp.sunshine.bukkit.commands.team;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffListCMD implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("pvp.stafflist")) {
			sender.sendMessage("§c§lERRO §fVocê não tem permissão para executar este comando.");
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("stafflist")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				player.sendMessage("");
				player.sendMessage("   §9§lSTAFFLIST §f- §8[§ePvP§8]");
				player.sendMessage("");

				List<String> staffMembers = getOnlineStaffMembers();
				for (String staffMember : staffMembers) {
					player.sendMessage(staffMember);
				}
				player.sendMessage("");
			} else {
				sender.sendMessage("§cEste comando só pode ser executado por jogadores.");
			}
			return true;
		}
		return false;
	}

	private List<String> getOnlineStaffMembers() {
		List<String> onlineStaff = new ArrayList<>();
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if (hasStaffPermission(onlinePlayer)) {
				onlineStaff.add(" §f* " + onlinePlayer.getDisplayName() + " §f- Status: §aPresente");
			}
		}
		return onlineStaff;
	}

	private boolean hasStaffPermission(Player player) {
		return !(!player.getName().equals("Rafael_Auler") && !player.hasPermission("tag.chefia")
				&& !player.hasPermission("tag.admin") && !player.hasPermission("tag.gerente")
				&& !player.hasPermission("tag.ajudante") && !player.hasPermission("tag.mod")
				&& !player.hasPermission("tag.construtor"));
	}
}