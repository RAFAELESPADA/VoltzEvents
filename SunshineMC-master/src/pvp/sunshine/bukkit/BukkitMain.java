package pvp.sunshine.bukkit;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.reflect.FieldAccessException;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import pvp.sunshine.bukkit.ability.KitVisibility;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.ability.register.KitSelector;
import pvp.sunshine.bukkit.ability.register.kits.*;
import pvp.sunshine.bukkit.api.LagAPI;
import pvp.sunshine.bukkit.api.TagAPI;
import pvp.sunshine.bukkit.commands.members.*;
import pvp.sunshine.bukkit.commands.team.*;
import pvp.sunshine.bukkit.manager.bossbar.BarAPI;
import pvp.sunshine.bukkit.manager.duels.Battle;
import pvp.sunshine.bukkit.manager.event.*;
import pvp.sunshine.bukkit.manager.fake.Fake;
import pvp.sunshine.bukkit.manager.feast.FeastAPI;
import pvp.sunshine.bukkit.manager.feast.SetFeast;
import pvp.sunshine.bukkit.manager.inventory.*;
import pvp.sunshine.bukkit.manager.inventory.action.OpenInventory;
import pvp.sunshine.bukkit.manager.inventory.cosmetic.CosmeticInventory;
import pvp.sunshine.bukkit.manager.inventory.cosmetic.HeadInventory;
import pvp.sunshine.bukkit.manager.inventory.cosmetic.ParticleInventory;
import pvp.sunshine.bukkit.manager.inventory.cosmetic.TagMenuType;
import pvp.sunshine.bukkit.manager.mysql.PlayerLoad;
import pvp.sunshine.bukkit.manager.mysql.Storage;
import pvp.sunshine.bukkit.manager.mysql.connections.holograms.*;
import pvp.sunshine.bukkit.manager.npc.NPCRegister;
import pvp.sunshine.bukkit.manager.particle.MovimentPlayer;
import pvp.sunshine.bukkit.manager.particle.ParticleType;
import pvp.sunshine.bukkit.manager.scoreboard.*;
import pvp.sunshine.bukkit.manager.scoreboard.duels.PlayerInBattle;
import pvp.sunshine.bukkit.manager.scoreboard.duels.PlayerNotBattle;
import pvp.sunshine.bukkit.utils.PvPUtil;

public class BukkitMain extends JavaPlugin {

    private final Map<Player, String> playerFakeTags = new HashMap<>();

    public static double getTps() {
        return MinecraftServer.getServer().recentTps[0];
    }

    private static ParticleType particle;

    public static ParticleType getParticle() {
        return particle;
    }

    private static NPCRegister npcManager;

    public static BukkitMain getInstance() {
        return getPlugin(BukkitMain.class);
    }

    public void onLoad() {
        try {
            Bukkit.getConsoleSender().sendMessage("§e§lPVP §fIniciando...");
            saveDefaultConfig();
        } catch (Exception ex) {
            getLogger().log(Level.SEVERE, "Erro ao carregar configuração.", ex);
        }
    }

    public void onEnable() {
        long startTime = System.currentTimeMillis();
        try {
            Storage.start();
            Bukkit.getServer().getMessenger().registerOutgoingPluginChannel((Plugin) this, "BungeeCord");
            commands();
            listeners();
            broadcast();
            registerTabCompleteListener();
            setWorldTimeToMorning();
            particle = new ParticleType();
            loadChunks();
            FeastAPI.start();
            PvP.init();
            Sumo.init();
            Lava.init();
            Evento.init();
            PlayerNotBattle.init();
            PlayerInBattle.init();
            LagAPI.IntelicLag();
            Bukkit.getServer().getPluginManager().registerEvents(new Fake(playerFakeTags), this);
            getCommand("fake").setExecutor(new Fake(playerFakeTags));
            new BukkitRunnable() {
                public void run() {
                    TopKills.updateHologram();
                    TopDeaths.updateHologram();
                    TopWins.updateHologram();
                    TopLoses.updateHologram();
                    TopCoins.updateHologram();
                    TopRanking.updateHologram();
                    TopClans.updateHologram();
                }
            }.runTaskTimerAsynchronously(this, 0, 20 * 20);
            new BukkitRunnable() {
                @Override
                public void run() {
                    resetCounts();
                    updateCounts();
                }
                private void resetCounts() {
                    for (String ability : KitVisibility.abilityCounts.keySet()) {
                        KitVisibility.abilityCounts.put(ability, 0);
                    }
                }
                private void updateCounts() {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        String ability = RegisterAbility.getAbility(player);
                        if (KitVisibility.abilityCounts.containsKey(ability)) {
                            int currentCount = KitVisibility.abilityCounts.get(ability);
                            KitVisibility.abilityCounts.put(ability, currentCount + 1);
                        }
                    }
                }
            }.runTaskTimer(this, 0L, 5L);
            new BukkitRunnable() {
                public void run() {
                    WarpType.Challenge = 0;
                    WarpType.Fps = 0;
                    WarpType.x1 = 0;
                    WarpType.Knockback = 0;
                    WarpType.Gapple = 0;
                    WarpType.ComboFly = 0;
                    WarpType.getComboInfo();
                    WarpType.getGappleInfo();
                    WarpType.getKnockInfo();
                    WarpType.get1v1Info();
                    WarpType.getChallengeInfo();
                    WarpType.getFpsInfo();
                }
            }.runTaskTimer((Plugin) this, 0L, 5L);
            Bukkit.getConsoleSender()
                    .sendMessage("§a§lPVP §fPlugin habilitado! §a[v" + getDescription().getVersion() + "]");
        } catch (Exception ex) {
            getLogger().log(Level.SEVERE, "Erro ao habilitar o plugin.", ex);
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        Bukkit.getConsoleSender().sendMessage("§a§lPVP §fTempo de abertura: (§a" + elapsedTime + " ms§f)");
    }

    public void loadChunks() {
        for (int x = -150; x < 150; x++) {
            for (int z = -150; z < 150; z++) {
                Chunk chunk = Bukkit.getWorld("lobbypvp").getBlockAt(x, 64, z).getChunk();
                if (chunk.isLoaded()) {
                    continue;
                }
                chunk.load(true);
            }
        }
        Bukkit.getConsoleSender().sendMessage("§a§lWORLD §fChunks carregadas com sucesso!");
    }

    private void setWorldTimeToMorning() {
        (new BukkitRunnable() {
            public void run() {
                Bukkit.getWorlds().forEach(w -> w.setTime(1000L));
            }
        }).runTaskTimerAsynchronously((Plugin) this, 0L, 600L);
    }

    private void registerTabCompleteListener() {
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener) new PacketAdapter((Plugin) this,
                ListenerPriority.NORMAL, new PacketType[] { PacketType.Play.Client.TAB_COMPLETE }) {
            public void onPacketReceiving(PacketEvent e) {
                if (e.getPacketType() == PacketType.Play.Client.TAB_COMPLETE)
                    try {
                        PacketContainer packet = e.getPacket();
                        String message = ((String) packet.getSpecificModifier(String.class).read(0)).toLowerCase();
                        if ((message.startsWith("/") && !message.contains(" "))
                                || (message.startsWith("/ver") && !message.contains("  "))
                                || (message.startsWith("/version") && !message.contains("  "))
                                || (message.startsWith("/about") && !message.contains("  "))
                                || (message.startsWith("/help") && !message.contains("  "))
                                || (message.startsWith("/?") && !message.contains("  "))
                                || (message.startsWith("/bukkit:?") && !message.contains("  "))
                                || (message.startsWith("/bukkit:ver") && !message.contains("  "))
                                || (message.startsWith("/bukkit:version") && !message.contains("  "))
                                || (message.startsWith("/bukkit:help") && !message.contains("  "))
                                || (message.startsWith("/minecraft:me") && !message.contains("  "))
                                || (message.startsWith("/bukkit:about") && !message.contains("  ")))
                            e.setCancelled(true);
                    } catch (FieldAccessException field) {
                        BukkitMain.this.getLogger().log(Level.SEVERE, "Couldn't acess field.", (Throwable) field);
                    }
            }
        });
    }

    private void listeners() {
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        registerListener(new TagAPI(), pluginManager);
        registerListener(new BarAPI(), pluginManager);
        registerListener(new BlockJump(), pluginManager);
        registerListener(new Flag(), pluginManager);
        registerListener(new PlayerLoad(), pluginManager);
        Bukkit.getServer().getPluginManager().registerEvents(new Essentials(playerFakeTags), this);
        registerListener(new PlayerJoinListener(), pluginManager);
        registerListener(new PlayerDeathListener(), pluginManager);
        registerListener(new PlayerRespawnListener(), pluginManager);
        registerListener(new AdminCMD(), pluginManager);
        registerListener(new KitType(), pluginManager);
        registerListener(new OpenInventory(), pluginManager);
        registerListener(new Kangaroo(), pluginManager);
        registerListener(new Anchor(), pluginManager);
        registerListener(new AntiStomper(), pluginManager);
        registerListener(new Boxer(), pluginManager);
        registerListener(new Confuser(), pluginManager);
        registerListener(new Fisherman(), pluginManager);
        registerListener(new Gladiator(), pluginManager);
        registerListener(new Magma(), pluginManager);
        registerListener(new Monk(), pluginManager);
        registerListener(new Phantom(), pluginManager);
        registerListener(new Snail(), pluginManager);
        registerListener(new Stomper(), pluginManager);
        registerListener(new Switcher(), pluginManager);
        registerListener(new Thor(), pluginManager);
        registerListener(new TimeLord(), pluginManager);
        registerListener(new Viking(), pluginManager);
        registerListener(new Viper(), pluginManager);
        registerListener(new CombatLog(), pluginManager);
        registerListener(new WarpType(), pluginManager);
        registerListener(new Battle(), pluginManager);
        registerListener(new ProfileType(), pluginManager);
        registerListener(new StatisticsType(), pluginManager);
        registerListener(new EventoCMD(), pluginManager);
        registerListener(new Ninja(), pluginManager);
        registerListener(new Urgal(), pluginManager);
        registerListener(new HotPotato(), pluginManager);
        registerListener(new Fireman(), pluginManager);
        registerListener(new Barbarian(), pluginManager);
        registerListener(new PurchaseType(), pluginManager);
        registerListener(new ShopAbilityType(), pluginManager);
        registerListener(new ExtraType(), pluginManager);
        registerListener(new SumoListener(), pluginManager);
        registerListener(new BoxSystemType(), pluginManager);
        registerListener(new BoxShopType(), pluginManager);
        registerListener(new MidiaType(), pluginManager);
        registerListener(new FlyCMD(), pluginManager);
        registerListener(new WarpCMD(), pluginManager);
        registerListener(new WarpFpsProtection(), pluginManager);
        registerListener(new AutoSoupCMD(), pluginManager);
        registerListener(new ConfigType(), pluginManager);
        registerListener(new MovimentPlayer(), pluginManager);
        registerListener(new CosmeticInventory(), pluginManager);
        registerListener(new HeadInventory(), pluginManager);
        registerListener(new ParticleInventory(), pluginManager);
        registerListener(new TagMenuType(), pluginManager);
    }

    private void commands() {
        registerCommand("gamemode", new GamemodeCMD());
        registerCommand("build", new BuildCMD());
        registerCommand("chat", new ChatCMD());
        registerCommand("tag", new TagCMD());
        registerCommand("stafflist", new StaffListCMD());
        registerCommand("tell", new TellCMD());
        registerCommand("admin", new AdminCMD());
        registerCommand("ranks", new RanksCMD());
        registerCommand("warp", new WarpCMD());
        registerCommand("spawn", new SpawnCMD());
        registerCommand("1v1", new DuelCMD());
        registerCommand("kit", new KitSelector());
        registerCommand("evento", new EventoCMD());
        registerCommand("ping", new PingCMD());
        registerCommand("clear", new ClearCMD());
        registerCommand("tp", new TpCMD());
        registerCommand("bc", new BroadCastCMD());
        registerCommand("fly", new FlyCMD());
        registerCommand("lobby", new LobbyCMD());
        registerCommand("stop", new ReloadCMD());
        registerCommand("stats", new StatsCMD());
        registerCommand("caixas", new CaixasCMD());
        registerCommand("sc", new StaffChat(this));

        registerCommand("staffchat", new StaffChat(this));
        registerCommand("donate", new DonateCMD());
        registerCommand("addperm", new AddPermCMD());
        registerCommand("c", new ChatClanCMD());
        registerCommand("clan", new ClanCMD());
        registerCommand("youtuber", new MidiaCMD());
        registerCommand("tpcoord", new TpCoordCMD());
        registerCommand("account", new AccountCMD());
        registerCommand("setfeast", new SetFeast());
        registerCommand("cvip", new ChatVipCMD());
        registerCommand("autosoup", new AutoSoupCMD());
        registerCommand("checkaccount", new CheckAccountCMD());
    }

    private void registerListener(Listener listener, PluginManager pluginManager) {
        pluginManager.registerEvents(listener, this);
    }

    private void registerCommand(String name, CommandExecutor executor) {
        getCommand(name).setExecutor(executor);
    }

    private void broadcast() {
        try {
            String prefix = " \n §4§lAVISO";
            final String[] messages = {
                    String.valueOf(prefix) + " §8» §fSiga-nos no Twitter: §e@RedeSlower \n ",
                    String.valueOf(prefix) + " §8» §fJunte-se ao nosso §3Discord Oficial§f agora! Acesse: §bhttps://discord.gg/cMjU5x9PrT \n ",
                    String.valueOf(prefix) + " §8» §fAdquira seu §aGalatic§f agora! Visite: §dhttps://discord.gg/cMjU5x9PrT \n ",
                    String.valueOf(prefix) + " §8» §fQuer fazer parte da nossa equipe? Envie sua inscrição em: §9https://discord.gg/cMjU5x9PrT \n ",
                    String.valueOf(prefix) + " §8» §fEstamos aceitando §6Mercado Pago, Pix e PayPal§f como formas de pagamento! \n ",
                    String.valueOf(prefix) + " §8» §fDesconfiado de algum jogador? Utilize §c/report (jogador) (motivo)§f e colabore com nossa moderação! \n ",
                    String.valueOf(prefix) + " §8» §fConfira todos os Rankings com §a/ranks§f! \n ",
                    String.valueOf(prefix) + " §8» §fQuer ser um §bYouTuber§f do servidor? Veja os requisitos em §7/midia§f! \n ",
                    String.valueOf(prefix) + " §8» §fCrie seu próprio §eClan§f! Use §e/clan§f e participe do Top Clans! \n ",
                    String.valueOf(prefix) + " §8» §fNovas §dPartículas Personalizadas§f disponíveis nos cosméticos! \n ",
                    String.valueOf(prefix) + " §8» §fExplore §aTag's Especiais§f nos cosméticos! \n ",
                    String.valueOf(prefix) + " §8» §fProblemas com a senha? Redefina com §e/trocarsenha§f! Sua privacidade é nossa prioridade. \n ",
                    String.valueOf(prefix) + " §8» §fUse §b/account (jogador)§f para ver informações de outras contas! \n ",
                    String.valueOf(prefix) + " §8» §fO §dTop Ranking's§f é baseado no XP total! \n ",
                    String.valueOf(prefix) + " §8» §fEnvie seu vídeo para nós através do nosso sistema automático de solicitação de tags! Use o comando §b/solicitar§f e envie para a nossa equipe seu vídeo ou sua transmissão ao vivo em tempo real! \n ",
                    String.valueOf(prefix) + " §8» §fO modo §eSumô§f é §eRanqueado§f - Ganhe XP e destaque-se! \n ",
                    String.valueOf(prefix) + " §8» §fOs §bTop Clans§f são classificados pelo XP total! \n "
            };

            new BukkitRunnable() {
                public void run() {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.isOnline()) {
                            p.sendMessage(messages[(new Random()).nextInt(messages.length)]);
                        }
                    }
                }
            }.runTaskTimerAsynchronously(this, 0L, 20L * 120L);
        } catch (Exception ex) {
            getLogger().log(Level.SEVERE, "Erro ao enviar mensagens de broadcast.", ex);
        }
    }


    public void onDisable() {
        try {
            Storage.disconnect();
            Bukkit.getConsoleSender()
                    .sendMessage("§c§lPVP §fPlugin desabilitado! §e[v" + getDescription().getVersion() + "]");
        } catch (Exception ex) {
            getLogger().log(Level.SEVERE, "Erro ao desabilitar o plugin.", ex);
        }
    }

    public static void sendServer(Player p, String server) {
        try {
            ByteArrayDataOutput data = ByteStreams.newDataOutput();
            data.writeUTF("Connect");
            data.writeUTF(server);
            p.sendPluginMessage((Plugin) getInstance(), "BungeeCord", data.toByteArray());
        } catch (Exception ex) {
            getInstance().getLogger().log(Level.SEVERE, "Erro ao enviar dados para o servidor BungeeCord.", ex);
        }
    }
}
