package com.github.kitpvp.warpstype.warps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.kitpvp.api.API;



import com.github.kitpvp.warpstype.Warp;
import com.github.kitpvp.warpstype.WarpEnums; 
public class Fps extends Warp{

	public Fps() {
		super("fps", new Location(Bukkit.getWorld("world"), 10, 20, 20));
		// TODO Auto-generated constructor stub
	}

	@Override
	public void join(Player p) {
		API.setWarp(p, WarpEnums.FPS);
	}

	@Override
	public void loadHotBar(Player p) {
		p.sendMessage("§aVoce entrou na warp " + API.getWarp(p));
		// TODO Auto-generated method stub
		
	}



}
