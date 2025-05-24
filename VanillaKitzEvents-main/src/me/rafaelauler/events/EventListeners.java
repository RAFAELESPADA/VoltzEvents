package me.rafaelauler.events;




import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.wavemc.core.bukkit.WaveBukkit;




public class EventListeners implements Listener {

	  @EventHandler(priority = EventPriority.HIGHEST)
	    public void BlockBreak(BlockBreakEvent event) {
	    	if (event.getPlayer().getWorld().equals(Bukkit.getWorld("hg"))) {
	    		return;
	    	}
	    	if (!EventoUtils.blocks.contains(event.getBlock().getLocation()) && !WaveWarp.CUSTOM.hasPlayer(event.getPlayer().getName())) {
	    		event.setCancelled(true);   	       
	    		event.getPlayer().sendMessage(ChatColor.RED + "Você não pode quebrar blocos da arena");
	    	}
	    	if (!EventoUtils.build) {
	            event.setCancelled(true);

	    		event.getPlayer().sendMessage(ChatColor.RED + "O Build está desativado");
	        }
	    	       
	    	
	    	
	        
	    	
	    	       }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent event) {
    	if (WaveWarp.CUSTOM.hasPlayer(event.getPlayer().getName())) {
    		if (event.getPlayer().getLocation().getY() < 82 && !EventoUtils.build) {
    			event.getPlayer().sendMessage(ChatColor.RED + "Você só pode sair do spawn do evento Custom assim que o build for ativo");
    			event.getPlayer().teleport(new Location (Bukkit.getWorld("hg"), 572.688, 97.00000, 585.974, (float)89.99, (float)-11.1));
    		}
    	}
    	}
        
    
    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
    public void aAntiSpam(FoodLevelChangeEvent e) {
    	if (!(e.getEntity() instanceof Player)) {
    		return;
    	}
    	Player p = (Player)e.getEntity();
    	
    if (!EventoUtils.started || WaveWarp.SPAWN.hasPlayer(p.getName())) {
  	  e.setCancelled(true);
    }
    }
    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
    public void aAntiSpam(AsyncPlayerChatEvent e) {
    if (!EventoUtils.started && !e.getPlayer().hasPermission("kombo.cmd.evento")) {
  	  e.getPlayer().sendMessage("§cVocê só pode falar no chat quando o evento começar.");
  	  e.setCancelled(true);
    }
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
               double i = p.getHealth();
               if (i >= 20) {
            	   return;
               }
               if (e.getItem() == null) {
            	   return;
               }
               if (!(e.getItem().getType() == Material.MUSHROOM_STEW)) {
            	   return;
               }
                p.setHealth(Math.min(p.getMaxHealth(), p.getHealth() + 7));
                p.setFoodLevel(Math.min(20, p.getFoodLevel() + 7));              
                p.getInventory().setItemInHand(new ItemStack(Material.BOWL));            
                p.updateInventory();
                e.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void Block(PlayerJoinEvent event) {
        if (!EventoUtils.started) return;
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
    		event.getPlayer().sendMessage(ChatColor.RED + "Você não pode colocar blocos da arena");
    	    
        	return;
        	}
    	

        
        if (!EventoUtils.build) {
            event.setCancelled(true);
    		event.getPlayer().sendMessage(ChatColor.RED + "Você não pode colocar blocos agora!");
    	    
            return;
        }
        
        EventoUtils.blocks.add(event.getBlock().getLocation());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void BucketFill(PlayerBucketFillEvent event) {
    	if (event.getPlayer().getWorld().equals(Bukkit.getWorld("hg"))) {
    		return;
    	}
    	if (WaveWarp.SPAWN.hasPlayer(event.getPlayer().getName()) && !event.getPlayer().hasPermission("kombo.cmd.evento")) {
        	event.setCancelled(true);

    		event.getPlayer().sendMessage(ChatColor.RED + "Você não pode colocar agua/lava agora!");
        	} 
        if (!EventoUtils.build) {
            event.setCancelled(true);

    		event.getPlayer().sendMessage(ChatColor.RED + "Você não pode colocar agua/lava agora!");
        }

        
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void BucketFill(PlayerBucketEmptyEvent event) {
    	if (event.getPlayer().getWorld().equals(Bukkit.getWorld("hg"))) {
    		return;
    	}
    	if (WaveWarp.SPAWN.hasPlayer(event.getPlayer().getName()) && !event.getPlayer().hasPermission("kombo.cmd.evento")) {
        	event.setCancelled(true);

    		event.getPlayer().sendMessage(ChatColor.DARK_RED + "Você não pode colocar agua/lava agora!");
        	}
        if (!EventoUtils.build) {
            event.setCancelled(true);

    		event.getPlayer().sendMessage(ChatColor.DARK_RED + "Você não pode colocar agua/lava agora!");
        }

        EventoUtils.blocks.add(event.getBlock().getLocation());
        WaveBukkit.getExecutorService().submit(() -> {
			new BukkitRunnable() {
				@Override
				public void run() {
				event.getBlock().setType(Material.AIR);
				}}.runTaskLater(Main.instance, 20 * 20L);
		
		});
    }
        

        
   

	@EventHandler
	public void onJoin(PlayerQuitEvent e) {

		e.setQuitMessage(null);
	}

	 @EventHandler
	 /*     */   public void onDrop(PlayerDropItemEvent event)
	 /*     */   {
	 /* 116 */     if (event.getItemDrop().getItemStack().getType() == Material.DIAMOND_SWORD) {
	 /* 117 */       event.setCancelled(true);
	 /*     */     }
	 }
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		Player p = e.getPlayer();
		e.setJoinMessage(null);
		if (VanishUtil.has(player.getName())) {
			VanishUtil.remove(player.getName());
			player.sendMessage("§c§lVANISH §fVocê saiu do vanish.");
		}
		EventoUtils.setEvento(true, player);
	WaveWarp.SPAWN.send(player);

	p.sendMessage(ChatColor.GOLD + "VOLTZ - SISTEMA DE EVENTOS v2.0");
	p.sendMessage(ChatColor.RED + "Bem vindo ao Servidor de Eventos");

	p.sendMessage(ChatColor.RED + "Caso tenha um promotor online o evento");

	p.sendMessage(ChatColor.RED + "Irá começar em breve!");

	p.sendMessage(ChatColor.RED + "Acesse nosso discord: " + ChatColor.BLUE + "https://discord.gg/voltz");
	}
	
    @EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		Player p = event.getEntity();
		event.setDeathMessage(null);
		Bukkit.broadcastMessage(ChatColor.RED + p.getName() + " perdeu o evento!");
		player.sendMessage(ChatColor.DARK_RED + "Você perdeu o evento!");
		WaveWarp.SPAWN.send(player);
		Player k = player.getKiller();
		if (k != null) {
		if (WaveWarp.ONEVSONE.hasPlayer(player.getKiller().getName())) {
			WaveWarp.ONEVSONE.send(k);
		}
		if (WaveWarp.SUMO.hasPlayer(player.getKiller().getName())) {
			WaveWarp.SUMO.send(k);
		}
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

        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

      
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