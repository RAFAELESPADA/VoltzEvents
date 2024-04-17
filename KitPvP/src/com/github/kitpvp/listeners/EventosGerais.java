package com.github.kitpvp.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.util.Vector;

import com.github.kitpvp.api.API;
import com.github.kitpvp.controllers.Control;
import com.github.kitpvp.controllers.PlayerControl;

public class EventosGerais implements Listener {

	

	@EventHandler
	public void on(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Control player = new PlayerControl();
		player.joinSpawn();
	}

	@EventHandler
	public void on(LeavesDecayEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void on(WeatherChangeEvent e) {
		if (e.toWeatherState()) {
			e.setCancelled(true);
		}

	}


	@EventHandler
	private void Jumps(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		API.Atirar(p);
		API.Atirar2(p);
	}

	@EventHandler
	public void RemoverDano(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getEntity();
		if (e.getCause() == EntityDamageEvent.DamageCause.FALL && API.fall.contains(p.getName())) {
			API.fall.remove(p.getName());
			e.setCancelled(true);
			EntityDamageEvent event = new EntityDamageEvent(p, EntityDamageEvent.DamageCause.FALL, 0.0F);
			p.setLastDamageCause(event);
			Bukkit.getServer().getPluginManager().callEvent(event);
		} else if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
			if (e.isCancelled()) {
				return;

			}
			API.fall.remove(p.getName());
		}

	}
}