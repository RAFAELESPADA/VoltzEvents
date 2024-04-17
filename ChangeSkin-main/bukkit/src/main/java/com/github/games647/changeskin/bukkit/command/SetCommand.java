package com.github.games647.changeskin.bukkit.command;

import com.github.games647.changeskin.bukkit.ChangeSkinBukkit;
import com.github.games647.changeskin.bukkit.task.NameResolver;
import com.github.games647.changeskin.bukkit.task.SkinDownloader;
import com.github.games647.changeskin.core.CooldownService;

import java.util.*;

import com.mojang.authlib.GameProfile;
import fr.kappacite.skinsapi.SkinsAPI;
import fr.kappacite.skinsapi.exceptions.SkinAlreadyLoadException;
import fr.kappacite.skinsapi.exceptions.SkinNotLoadedException;
import fr.kappacite.skinsapi.skins.Skin;
import fr.kappacite.skinsapi.skins.SkinsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import stackunderflow.skinapi.api.SkinAPI;

public class SetCommand extends AbstractForwardCommand {

    public SetCommand(ChangeSkinBukkit plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (plugin.isBungeeCord()) {
            onBungeeCord(sender, command.getName(), args);
            return true;
        }
if (args.length == 0) {
            Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Menu de skins");
            // inventory.setItem(22, BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>:&aSkin padrão desc>&7Apenas vips podem escolher skins\n&7Você só pode escolher skins caso seja vip" + "&7Você é um jogador VIP?: " + (sender.hasPermission("cmd.skin") ? "&aSim" : "&aNão") +  "skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTYzMTU5N2RjZTRlNDA1MWU4ZDVhNTQzNjQxOTY2YWI1NGZiZjI1YTBlZDYwNDdmMTFlNjE0MGQ4OGJmNDhmIn19fQ=="));
            // inventory.setItem(48, BukkitUtils.deserializeItemStack("BOOK_AND_QUILL : 1 : nome>:§aEscolher desc>§7Você pode escolher uma nova skin\n§7para ser utilizada em sua conta.\n \n§7Comando: /skin [jogador]\n \nClique para escolher uma skin. \nComando apenas para &a&lVIPS"));
            //inventory.setItem(49, BukkitUtils.deserializeItemStack("ITEM_FRAME : 1 : nome>: desc>&7Altere a sua skin para a mais recente\n&7utilizada em sua conta original!\n \n&8Caso você utilize minecraft pirata\n&8sua skin será padronizada!\n&fComando:&7 /skinupdate\n \n&eClique para atualizar sua skin!"));
            // inventory.setItem(50, BukkitUtils.deserializeItemStack("SKULL_ITEM:3 : 1 : nome>:&aAjuda desc>&7As ações disponíveis neste menu também\n&7podem ser realizadas por comando.\n \n&7Comandos: \n&7/skin <Nick>. : skin>eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFkYzA0OGE3Y2U3OGY3ZGFkNzJhMDdkYTI3ZDg1YzA5MTY4ODFlNTUyMmVlZWQxZTNkYWYyMTdhMzhjMWEifX19"));
        }
        if (isCooldown(sender)) {
            plugin.sendMessage(sender, "cooldown");
            return true;
        }

        if (args.length > 0 && "set".equalsIgnoreCase(args[0])) {
            args = Arrays.copyOfRange(args, 1, args.length);
        }

        if (args.length > 1) {
            if (!sender.hasPermission(command.getPermission() + ".other")) {
                plugin.sendMessage(sender, "no-permission-other");
                return true;
            }

            String targetPlayerName = args[0];
            String toSkin = args[1];

            Player targetPlayer = Bukkit.getPlayerExact(targetPlayerName);
            if (targetPlayer == null) {
                plugin.sendMessage(sender, "not-online");
            } else {



                try {
                    SkinsAPI.getInstance().getSkinsManager().loadSkin(args[0]);
                    String[] finalArgs = args;
                    Bukkit.getScheduler().runTaskLater(plugin.instance, new BukkitRunnable() {
                        @Override
                        public void run() {
                            Skin s = null;
                            try {
                                s = SkinsAPI.getInstance().getSkinsManager().getMojangSkin(finalArgs[1]);
                            } catch (SkinNotLoadedException e) {
                                sender.sendMessage(ChatColor.RED + "Um erro ocorreu!");
                            }
                            if (s == null) {
                                sender.sendMessage(ChatColor.RED + "Escolha um nick original");
                                return;
                            }
                            sender.sendMessage(ChatColor.GREEN + "Você alterou a skin skin de " + targetPlayer + " com sucesso");
                            SkinsAPI.getInstance().getSkinsManager().setPlayerSkin(((Player) targetPlayer).getPlayer(), s , true);
                        }
                    }, 40l);


                } catch (SkinAlreadyLoadException e) {
                    Skin s = null;
                    try {
                        s = SkinsAPI.getInstance().getSkinsManager().getMojangSkin(args[0]);
                    } catch (SkinNotLoadedException e3) {
                        return true;
                    }

                    SkinsAPI.getInstance().getSkinsManager().setPlayerSkin(((Player) targetPlayer).getPlayer(), s , true);
                    sender.sendMessage(ChatColor.GREEN + "Você alterou a skin skin de " + targetPlayer + " com sucesso");
                }



                GameProfile playerProfile = ((CraftPlayer) targetPlayer).getHandle().getProfile();

            }
        } else if (sender instanceof Player) {
            if (args.length == 1) {
                if (!sender.hasPermission("cmd.skin")) {
                    sender.sendMessage(ChatColor.RED + "Você precisa ser " + ChatColor.GREEN + "VIP" + ChatColor.RED + " para escolher sua skin");
                    return true;
                }
                if ("reset".equalsIgnoreCase(args[0])) {
                    try {
                        SkinsAPI.getInstance().getSkinsManager().loadSkin(args[0]);
                        SkinsAPI.getInstance().getSkinsManager().loadSkin("Steve");
                        SkinsAPI.getInstance().getSkinsManager().loadSkin(sender.getName());
                        String[] finalArgs = args;
                        Bukkit.getScheduler().runTaskLater(plugin.instance, new BukkitRunnable() {
                            @Override
                            public void run() {
                                Skin s = null;
                                Skin s2 = null;
                                Skin s3 = null;
                                try {
                                    s = SkinsAPI.getInstance().getSkinsManager().getMojangSkin(finalArgs[0]);
                                    s2 = SkinsAPI.getInstance().getSkinsManager().getMojangSkin("Steve");
                                    s3 = SkinsAPI.getInstance().getSkinsManager().getMojangSkin(sender.getName());
                                } catch (SkinNotLoadedException e) {
                                    sender.sendMessage(ChatColor.RED + "Um erro ocorreu!");
                                }
                                if (s == null) {
                                    sender.sendMessage(ChatColor.GREEN + "Você resetou sua skin com sucesso");
                                    SkinsAPI.getInstance().getSkinsManager().setPlayerSkin(((Player) sender).getPlayer(), s2, true);
                                    return;
                                }
                                sender.sendMessage(ChatColor.GREEN + "Você resetou sua skin com sucesso");
                                SkinsAPI.getInstance().getSkinsManager().setPlayerSkin(((Player) sender).getPlayer(), s3, true);
                                return;
                            }
                        }, 40l);


                    } catch (SkinAlreadyLoadException e) {
                        Skin s2 = null;
                        try {
                            s2 = SkinsAPI.getInstance().getSkinsManager().getMojangSkin("Steve");
                        } catch (SkinNotLoadedException e3) {
                            return true;
                        }
                        sender.sendMessage(ChatColor.GREEN + "Você resetou sua skin com sucesso");

                        SkinsAPI.getInstance().getSkinsManager().setPlayerSkin(((Player) sender).getPlayer(), s2, true);
                        return true;
                    }
                }

                try {
                    SkinsAPI.getInstance().getSkinsManager().loadSkin(args[0]);
                    String[] finalArgs = args;
                    Bukkit.getScheduler().runTaskLater(plugin.instance, new BukkitRunnable() {
                        @Override
                        public void run() {
                            Skin s = null;
                            try {
                                s = SkinsAPI.getInstance().getSkinsManager().getMojangSkin(finalArgs[0]);
                            } catch (SkinNotLoadedException e) {
                                sender.sendMessage(ChatColor.RED + "Um erro ocorreu!");
                            }
                            if (s == null) {
                                sender.sendMessage(ChatColor.RED + "Escolha um nick original");
                                return;
                            }
                            sender.sendMessage(ChatColor.GREEN + "Você alterou sua skin com sucesso");
                            SkinsAPI.getInstance().getSkinsManager().setPlayerSkin(((Player) sender).getPlayer(), s , true);
                        }
                    }, 40l);


                } catch (SkinAlreadyLoadException e) {
                    Skin s = null;
                    try {
                        s = SkinsAPI.getInstance().getSkinsManager().getMojangSkin(args[0]);
                    } catch (SkinNotLoadedException e3) {
                        return true;
                    }
                    sender.sendMessage(ChatColor.GREEN + "Você alterou sua skin com sucesso");
                    SkinsAPI.getInstance().getSkinsManager().setPlayerSkin(((Player) sender).getPlayer(), s , true);
                }
            } else {
                ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta skull = (SkullMeta) item.getItemMeta();
                ArrayList<String> lore = new ArrayList<String>();

                skull.setOwner("Rafael_Melo");
                item.setItemMeta(skull);

                Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Menu de skins");
                inventory.setItem(22, getCustomItemStack(Material.COOKIE, "§aSkin Padrão", Arrays.asList("§7Você precisa ser §aVip §7para trocar de skin", "§7Você é um jogador Vip? " + (sender.hasPermission("cmd.skin") ? "§aSim" : "§cNão"))));
                inventory.setItem(40, getCustomItemStack(Material.BARRIER, "§aReseta sua skin", Arrays.asList("§7Reseta sua skin de volta para sua skin original", "§eClique aqui para resetar sua skin")));
                inventory.setItem(48, getCustomItemStack(Material.BOOK_AND_QUILL, "§aAlterar Skin", Arrays.asList("§7Você precisa ser §aVip §7para trocar de skin", "§eClique aqui para alterar sua skin")));
                inventory.setItem(49, getCustomItemStack(Material.DIAMOND, "§aAtualizar Skin", Arrays.asList("§7Atualiza sua skin atual", "§eClique aqui para atualizar sua skin")));
                inventory.setItem(50, getCustomItemStack(Material.IRON_CHESTPLATE, "§aAjuda", Arrays.asList("§7Utilize /skin> <Nick> para alterar sua skin", "§eUtilize /skinupdate para atualizar sua skin")));
                ((Player) sender).openInventory(inventory);
            }
        } else {
            plugin.sendMessage(sender, "no-console");
        }

        return true;
    }

    private boolean isCooldown(CommandSender sender) {
        CooldownService cooldownService = plugin.getCore().getCooldownService();
        return sender instanceof Player && cooldownService.isTracked(((Player) sender).getUniqueId());
    }

    private void setSkin(CommandSender sender, Player targetPlayer, String toSkin, boolean keepSkin) {
        //Minecraft player names has the max length of 16 characters so it could be the uuid
        if (toSkin.length() > 16) {
            setSkinUUID(sender, targetPlayer, toSkin, keepSkin);
        } else {
            Runnable nameResolver = new NameResolver(plugin, sender, toSkin, targetPlayer, keepSkin);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, nameResolver);
        }
    }
    public static ItemStack getCustomItemStack(Material material, String name, String lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        if (lore != null) {
            List<String> l = Collections.singletonList(lore);
            itemMeta.setLore(l);
        }
        if (itemStack.getType() == Material.SKULL)
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getCustomItemStack(Material material, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack editItemStack(ItemStack itemStack, String name, List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getPlayerSkull(String name) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        meta.setOwner(name);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    private void setSkinUUID(CommandSender sender, Player receiverPayer, String targetUUID, boolean keepSkin) {
        try {
            UUID uuid = UUID.fromString(targetUUID);
            if (plugin.getConfig().getBoolean("skinPermission") && !plugin.hasSkinPermission(sender, uuid, true)) {
                return;
            }

            plugin.sendMessage(sender, "skin-change-queue");
            Runnable skinDownloader = new SkinDownloader(plugin, sender, receiverPayer, uuid, keepSkin);
            Bukkit.getScheduler().runTaskAsynchronously(plugin, skinDownloader);
        } catch (IllegalArgumentException illegalArgumentException) {
            plugin.sendMessage(sender, "invalid-uuid");
        }
    }

    private boolean isKeepSkin(String... args) {
        if (args.length > 0) {
            String lastArg = args[args.length - 1];
            return "keep".equalsIgnoreCase(lastArg);
        }

        return false;
    }
}
