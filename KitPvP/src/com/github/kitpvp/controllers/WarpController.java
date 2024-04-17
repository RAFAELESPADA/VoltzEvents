package com.github.kitpvp.controllers;

import java.util.HashSet;
import java.util.Set;

import com.github.kitpvp.warpstype.Warp;

public abstract class WarpController {
	
	
	public static Set<Warp> set = new HashSet<>();



	public static void save(Warp warp){
		set.add(warp);
	}

	public static Warp get(String name){
		return set.stream().filter(warp -> warp.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}




}
