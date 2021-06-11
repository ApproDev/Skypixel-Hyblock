package com.BadDevelopers.SkypixelHyblock.CustomMobs;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;

import net.minecraft.server.v1_16_R3.Entity;

public enum Entities {
	Rock_Pet(Rock_Pet.class),
	Player(CustomPlayer.class);
	
	final Class<? extends Entity> entity;
	
	Entities(Class<? extends Entity> entity) {this.entity = entity;}
	
	public Entity spawn(Location loc) {
		try {
			return entity.getDeclaredConstructor(Location.class).newInstance(loc);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
}