package com.BadDevelopers.SkypixelHyblock.CustomMobs;

import java.util.HashMap;

import org.bukkit.Location;

import net.minecraft.server.v1_16_R3.Entity;

public class CustomEntitiesHelper {
	private HashMap<String, Class<? extends Entity>> entities = null;
	
	public HashMap<String, Class<? extends Entity>> getEntities() {
		if (entities == null) {
			entities = new HashMap<String, Class<? extends Entity>>();
			entities.put("Rock_Pet", Rock_Pet.class);
			entities.put("Seraphine", CustomPlayer.class);
		} return entities;
	}
	
	public Class<? extends Entity> getEntity(String name) {
		return getEntities().get(name);
	}
	
	public void spawnEntity(Location loc, Class<? extends Entity> entity) {
		try {
			entity.getDeclaredConstructor(Location.class).newInstance(loc);
		} catch (Exception e) { e.printStackTrace(); } 
	}
}
