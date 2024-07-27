package me.rafaelauler.events;




import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;



public class EventListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void BlockBreak(BlockBreakEvent event) {
    	if (WaveWarp.SPAWN.hasPlayer(event.getPlayer().getName()) && !event.getPlayer().hasPermission("kombo.cmd.evento")) {
    	event.setCancelled(true);
    	}
        if (!EventoUtils.evento) return;
        
        Player player = event.getPlayer();
       
        if (!EventoUtils.game.contains(player.getName())) return;
        if (!EventoUtils.build) {
            event.setCancelled(true);
        }
        event.setCancelled(!EventoUtils.blocks.contains(event.getBlock().getLocation()));
    }

   
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void Block(PlayerJoinEvent event) {
        if (!EventoUtils.evento) return;
        event.getPlayer().sendMessage("");
        event.getPlayer().sendMessage("");
        event.getPlayer().sendMessage("");
        event.getPlayer().sendMessage("");
        event.getPlayer().sendMessage("");
        event.getPlayer().sendMessage("");
        event.getPlayer().sendMessage("");
        event.getPlayer().sendMessage("");
        event.getPlayer().sendMessage("");
        event.getPlayer().sendMessage("");
        event.getPlayer().sendMessage("");
        event.getPlayer().sendMessage("");
        event.getPlayer().sendMessage("");
        event.getPlayer().sendMessage("");
        event.getPlayer().sendMessage("");
        event.getPlayer().sendMessage("");
        event.getPlayer().sendMessage("§aUm evento está ocorrendo");
        event.getPlayer().sendMessage("§aAgora mesmo e você tem que esperar começar um novo");
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void BlockPlace(BlockPlaceEvent event) {
    	if (WaveWarp.SPAWN.hasPlayer(event.getPlayer().getName()) && !event.getPlayer().hasPermission("kombo.cmd.evento")) {
        	event.setCancelled(true);
        	}
        if (!EventoUtils.evento) return;
        
        Player player = event.getPlayer();

        if (!EventoUtils.game.contains(player.getName())) return;
        if (!EventoUtils.build) {
            event.setCancelled(true);
        }
        EventoUtils.blocks.add(event.getBlock().getLocation());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void BucketFill(PlayerBucketFillEvent event) {
    	if (WaveWarp.SPAWN.hasPlayer(event.getPlayer().getName()) && !event.getPlayer().hasPermission("kombo.cmd.evento")) {
        	event.setCancelled(true);
        	}
        if (!EventoUtils.evento) return;
        
        Player player = event.getPlayer();
        if (!EventoUtils.game.contains(player.getName())) return;
        if (!EventoUtils.build) {
            event.setCancelled(true);
        }
    }

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		Player p = e.getPlayer();
		if (VanishUtil.has(player.getName())) {
			VanishUtil.remove(player.getName());
			player.sendMessage("§c§lVANISH §fVocê saiu do vanish.");
		}
		EventoUtils.setEvento(true, player);
	WaveWarp.SPAWN.send(player);

	p.sendMessage(ChatColor.GOLD + "KOMBO - SISTEMA DE EVENTOS v2.0");
	p.sendMessage(ChatColor.RED + "Bem vindo ao Servidor de Eventos");

	p.sendMessage(ChatColor.RED + "Caso tenha um promotor online o evento");

	p.sendMessage(ChatColor.RED + "Irá começar em breve!");

	p.sendMessage(ChatColor.RED + "Acesse nosso discord: " + ChatColor.BLUE + "https://discord.gg/SQ48vpV8hM");
	}
	
    @EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		Player p = event.getEntity();
		Bukkit.broadcastMessage(ChatColor.RED + p.getName() + " perdeu o evento!");
		player.sendMessage(ChatColor.DARK_RED + "Você perdeu o evento!");
		WaveWarp.SPAWN.send(player);
		Player k = player.getKiller();
		if (WaveWarp.ONE_VS_ONE.hasPlayer(player.getKiller().getName())) {
			WaveWarp.ONE_VS_ONE.send(k);
		}
		if (WaveWarp.Sumo.hasPlayer(player.getKiller().getName())) {
			WaveWarp.Sumo.send(k);
		}
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void BucketFill(PlayerQuitEvent event) {
    	 if (!EventoUtils.game.contains(event.getPlayer().getName())) return;
        
        EventoUtils.game.remove(event.getPlayer().getName());
        EventoUtils.game.remove(event.getPlayer().getName());
        EventoUtils.game.remove(event.getPlayer().getName());
        EventoUtils.game.remove(event.getPlayer().getName());
        EventoUtils.game.remove(event.getPlayer().getName());
        EventoUtils.game.remove(event.getPlayer().getName());
        EventoUtils.game.remove(event.getPlayer().getName());
        EventoUtils.game.remove(event.getPlayer().getName());
        }
    

    @EventHandler(priority = EventPriority.HIGHEST)
    public void BucketEmpty(PlayerBucketEmptyEvent event) {
        if (!EventoUtils.evento) return;
        
        Player player = event.getPlayer();

        if (!EventoUtils.game.contains(player.getName())) return;
        if (!EventoUtils.build) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onEspada(EntityDamageByEntityEvent e) {
    	if (!(e.getDamager() instanceof Player)) {
    		return;
    	}
    	Player p = (Player) e.getDamager();
    	if (EventoUtils.game.contains(p.getName())) {
    		p.getItemInHand().setDurability((short)0);
    		p.getInventory().getBoots().setDurability((short)0);
    		p.getInventory().getHelmet().setDurability((short)0);
    		p.getInventory().getChestplate().setDurability((short)0);
    		p.getInventory().getBoots().setDurability((short)0);
    		p.updateInventory();
    	}
    	}

    @EventHandler(priority = EventPriority.HIGHEST)
    public void EntityDamage(EntityDamageEvent event) {
        if (!EventoUtils.evento) return;

        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        if (!EventoUtils.game.contains(player.getName())) return;
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            if (!EventoUtils.pvp) {
                event.setCancelled(true);
            }
        } else {
            if (!EventoUtils.damage) {
                event.setCancelled(true);
            }
        }
    }

}