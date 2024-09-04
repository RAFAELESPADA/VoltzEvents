package pvp.sunshine.bukkit.manager.fake;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pvp.sunshine.bukkit.api.TagAPI;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLClan;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Fake implements CommandExecutor, Listener {

    private static final Map<String, String> availableTags = new HashMap<>();
    private final Map<Player, String> playerFakeTags;
    private final Map<Player, String> playerOriginalDisplayNames = new HashMap<>();

    public Fake(Map<Player, String> playerFakeTags) {
        this.playerFakeTags = playerFakeTags;
    }

    static {
        availableTags.put("Vip+", "§b§lVIP+ §b");
        availableTags.put("Vip", "§9§lVIP §9");
        availableTags.put("Membro", "§7");
        availableTags.put("Apoiador", "§5§lAPOIADOR §5");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando só pode ser usado por jogadores.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("pvp.fake")) {
            player.sendMessage("§c§lERRO §fVocê não tem permissão para executar este comando.");
            return true;
        }

        if (args.length == 0 || args.length > 2) {
            player.sendMessage("§c§lERRO §fUso correto: /fake <nome> <tag>, /fake list, /fake reset ou /fake random");
            return true;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                showFakeList(player);
                return true;
            } else if (args[0].equalsIgnoreCase("reset")) {
                if (FakeNameManager.getFakeName(player.getUniqueId()) == null) {
                    player.sendMessage("§c§lFAKE §fVocê não está com nenhum fake agora.");
                    return true;
                }
                resetFakeName(player);
                return true;
            } else if (args[0].equalsIgnoreCase("random")) {
                setRandomFake(player);
                return true;
            }
        }

        if (FakeNameManager.getFakeName(player.getUniqueId()) != null) {
            player.sendMessage("§c§lFAKE §fVocê já está usando um fake. Para remover, utilize /fake reset");
            return true;
        }

        String fakeName = args[0];
       
if (args.length < 2 && !args[0].equals("reset") && !args[0].equals("random") && !args[0].equals("list")) {
	   player.sendMessage("§c§lERRO §fTag inválida. Tags disponíveis: " + String.join(", ", availableTags.keySet()));
       return true;
   
}
     String tag = args[1];   if (fakeName.length() < 4 || fakeName.length() > 16) {
            player.sendMessage("§c§lFAKE §fO nome falso deve ter entre 4 e 16 caracteres.");
            return true;
        }

        if (!fakeName.matches("^[a-zA-Z0-9_]+$")) {
            player.sendMessage("§c§lFAKE §fO nome falso só pode conter letras, números e sublinhados.");
            return true;
        }

        if (!availableTags.containsKey(tag)) {
            player.sendMessage("§c§lERRO §fTag inválida. Tags disponíveis: " + String.join(", ", availableTags.keySet()));
            return true;
        }

        String formattedName = availableTags.get(tag) + fakeName;
        String originalDisplayName = player.getDisplayName();
        player.setDisplayName(formattedName);
        player.setPlayerListName(formattedName);
        FakeNameManager.setFakeName(player.getUniqueId(), fakeName);
        playerFakeTags.put(player, availableTags.get(tag));
        playerOriginalDisplayNames.put(player, originalDisplayName);
        TagAPI.setNameTag(player.getName(), "team", availableTags.get(tag), " " + SQLClan.getTagPlayer(player));
        player.sendMessage("§a§lFAKE §fSeu nick foi atualizado para §e" + formattedName);
        return true;
    }

    private void showFakeList(Player player) {
        StringBuilder fakeList = new StringBuilder("§a§lFAKE §fLista de jogadores com nomes falsos:\n");

        boolean hasFake = false;
        for (UUID uuid : FakeNameManager.getFakeNames().keySet()) {
            Player fakePlayer = Bukkit.getPlayer(uuid);
            if (fakePlayer != null && fakePlayer.isOnline()) {
                fakeList.append(" §7* §e").append(fakePlayer.getName()).append(" §f- §7Fake: §e").append(FakeNameManager.getFakeName(uuid)).append("\n");
                hasFake = true;
            }
        }

        if (!hasFake) {
            fakeList.append("§cNão há ninguém de fake agora.");
        }

        player.sendMessage(fakeList.toString());
    }

    private void resetFakeName(Player player) {
        FakeNameManager.removeFakeName(player.getUniqueId());
        playerFakeTags.remove(player);
        String originalDisplayName = playerOriginalDisplayNames.remove(player);
        player.setPlayerListName(player.getName());
        player.setDisplayName(originalDisplayName);
        player.sendMessage("§a§lFAKE §fSeu nick falso foi removido.");
    }

    private void setRandomFake(Player player) {
        if (FakeNameManager.getFakeName(player.getUniqueId()) != null) {
            player.sendMessage("§c§lFAKE §fVocê já está usando um fake. Para remover, utilize /fake reset");
            return;
        }

        String fakeName = FakeNameGenerator.generateFakeName();
        String tag = getRandomTag();

        String formattedName = availableTags.get(tag) + fakeName;
        String originalDisplayName = player.getDisplayName();
        player.setDisplayName(formattedName);
        player.setPlayerListName(formattedName);
        FakeNameManager.setFakeName(player.getUniqueId(), fakeName);
        playerFakeTags.put(player, availableTags.get(tag));
        playerOriginalDisplayNames.put(player, originalDisplayName);
        player.sendMessage("§a§lFAKE §fSeu nick foi atualizado para §e" + formattedName);
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
    }

    private String getRandomTag() {
        String[] tags = availableTags.keySet().toArray(new String[0]);
        int index = (int) (Math.random() * tags.length);
        return tags[index];
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        removeFake(player);
    }

    @EventHandler
    public void onPlayerQuit2(PlayerKickEvent event) {
        Player player = event.getPlayer();
        removeFake(player);
    }

    private void removeFake(Player player) {
        if (FakeNameManager.getFakeName(player.getUniqueId()) != null) {
            FakeNameManager.removeFakeName(player.getUniqueId());
            playerFakeTags.remove(player);
            String originalDisplayName = playerOriginalDisplayNames.remove(player);
            player.setPlayerListName(player.getName());
            player.setDisplayName(originalDisplayName);
        }
    }
}
