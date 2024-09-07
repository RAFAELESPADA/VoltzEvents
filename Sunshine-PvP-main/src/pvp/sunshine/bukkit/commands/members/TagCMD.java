package pvp.sunshine.bukkit.commands.members;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.api.TagAPI;
import pvp.sunshine.bukkit.manager.InteractiveChat;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLClan;

public class TagCMD implements CommandExecutor {
    private final Map<String, String> playerTags = new HashMap<>();
    private final Map<String, String> tagPermissions = new HashMap<>();

    public TagCMD() {
        tagPermissions.put("chefia", "tag.chefia");
        tagPermissions.put("admin", "tag.admin");
        tagPermissions.put("gerente", "tag.gerente");
        tagPermissions.put("mod", "tag.mod");
        tagPermissions.put("ajudante", "tag.ajudante");
        tagPermissions.put("construtor", "tag.construtor");
        tagPermissions.put("creator", "tag.creator");
        tagPermissions.put("invest", "tag.invest");
        tagPermissions.put("vip+", "tag.vip+");
        tagPermissions.put("vip", "tag.vip");
        tagPermissions.put("bughunter", "tag.bughunter");

        tagPermissions.put("nitro", "tag.nitro");
        tagPermissions.put("apoiador", "tag.apoiador");
    }


    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (!(sender instanceof Player) || !cmd.getName().equalsIgnoreCase("tag")) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sender.sendMessage("§d§lTAGS §fSuas Tags!");
            sender.sendMessage("");
            getTag(player);
        } else {
            String tagName = args[0].toLowerCase();
            Map<String, String[]> tagMap = new LinkedHashMap<>();
            if (tagPermissions.containsKey(tagName)) {
                String permission = tagPermissions.get(tagName);
                if (!player.hasPermission(permission)) {
                    sender.sendMessage("§c§lERRO §fVocê não tem permissão para utilizar essa tag.");
                    return true;
                }
            } else if (tagName.equals("chefia") && !player.hasPermission("tag.chefia")) {
                player.sendMessage("§c§lERRO §fVocê não tem permissão para utilizar essa tag.");
                return true;
            }
            if (args[0].equalsIgnoreCase("membro")) {
                if (player.hasPermission("tag.membro")) {
                    TagAPI.setNameTag(player.getName(), "s", "§7", " " + SQLClan.getTagPlayer(player));
                    player.setDisplayName("§7" + player.getName());
                    player.sendMessage("§3§lTAG §fSua tag foi alterada para §7§lMEMBRO§f com sucesso.");
                } else {
                    player.sendMessage("§c§lERRO §fVocê não tem permissão para utilizar essa tag.");
                }
                return true;
            }
            tagMap.put("chefia", new String[]{"a", "§6§lCHEFIA §6"});
            tagMap.put("admin", new String[]{"b", "§b§lADMIN §b"});
            tagMap.put("gerente", new String[]{"c", "§3§lGERENTE §3"});
            tagMap.put("mod", new String[]{"d", "§2§lMOD §2"});
            tagMap.put("ajudante", new String[]{"e", "§e§lAJUDANTE §e"});
            tagMap.put("creator", new String[]{"g", "§c§lCREATOR §c"});
            tagMap.put("construtor", new String[]{"f", "§e§lCONSTRUTOR §e"});
            tagMap.put("invest", new String[]{"h", "§a§lINVEST §a"});
            tagMap.put("vip+", new String[]{"i", "§b§lVIP+ §b"});
            tagMap.put("vip", new String[]{"j", "§9§lVIP §9"});
            tagMap.put("bughunter", new String[]{"k", "§8§lBUGHUNTER §8"});
            tagMap.put("apoiador", new String[]{"l", "§5§lAPOIADOR §5"});

            tagMap.put("nitro", new String[]{"m", "§d§lNITRO §d"});
            if (!tagMap.containsKey(tagName)) {
                sender.sendMessage("§c§lERRO §fTag não encontrada.");
                return true;
            }
            String[] tagInfo = tagMap.get(tagName);
            if (this.playerTags.containsKey(player.getName()) && ((String) this.playerTags.get(player.getName())).equals(tagInfo[0])) {
                sender.sendMessage("§c§lERRO §fVocê já está usando essa tag.");
                return true;
            }
            sender.sendMessage("§3§lTAG §fSua tag foi alterada para " + tagInfo[1].toUpperCase() + "§fcom sucesso.");
            TagAPI.setNameTag(player.getName(), tagInfo[0], tagInfo[1], " " + SQLClan.getTagPlayer(player));
            player.setDisplayName(String.valueOf(tagInfo[1]) + player.getName());
            this.playerTags.put(player.getName(), tagInfo[0]);
        }
        return false;
    }

    private static final Map<String, String[]> permissionToTags = new LinkedHashMap<>();

    static {
        permissionToTags.put("tag.chefia", new String[]{"§6§lCHEFIA", "/tag chefia"});
        permissionToTags.put("tag.admin", new String[]{"§b§lADMIN", "/tag admin"});
        permissionToTags.put("tag.gerente", new String[]{"§3§lGERENTE", "/tag gerente"});
        permissionToTags.put("tag.mod", new String[]{"§2§lMOD", "/tag mod"});
        permissionToTags.put("tag.ajudante", new String[]{"§e§lAJUDANTE", "/tag ajudante"});
        permissionToTags.put("tag.construtor", new String[]{"§9§lCONSTRUTOR", "/tag construtor"});
        permissionToTags.put("tag.creator", new String[]{"§c§lCREATOR", "/tag creator"});
        permissionToTags.put("tag.invest", new String[]{"§a§lINVEST", "/tag invest"});
        permissionToTags.put("tag.vip+", new String[]{"§b§lVIP+", "/tag vip+"});
        permissionToTags.put("tag.vip", new String[]{"§9§lVIP", "/tag vip"});
        permissionToTags.put("tag.bughunter", new String[]{"§8§lBUGHUNTER", "/tag bughunter"});
        permissionToTags.put("tag.apoiador", new String[]{"§5§lAPOIADOR", "/tag apoiador"});
        permissionToTags.put("tag.nitro", new String[]{"§d§lNITRO", "/tag nitro"});
        
    }

    public static void getTag(Player p) {
        String[] defaultTag = {"§7§lMEMBRO", "/tag membro"};
        if (p.getName().equalsIgnoreCase("yKawZ") || p.getName().equalsIgnoreCase("Rafael_Auler")) {
            for (Map.Entry<String, String[]> entry : permissionToTags.entrySet()) {
                String[] tagInfo = entry.getValue();
                InteractiveChat.getAPI().commandHover(p.getName(), "", "", tagInfo[0], "§fClique para alterar sua tag!", tagInfo[1]);
            }
        } else {
            for (Map.Entry<String, String[]> entry : permissionToTags.entrySet()) {
                String permission = entry.getKey();
                String[] tagInfo = entry.getValue();
                if (p.hasPermission(permission)) {
                    InteractiveChat.getAPI().commandHover(p.getName(), "", "", tagInfo[0], "§fClique para alterar sua tag!", tagInfo[1]);
                    return;
                }
            }
        }
        InteractiveChat.getAPI().commandHover(p.getName(), "", "", defaultTag[0], "§fClique para alterar sua tag!", defaultTag[1]);
    }
}