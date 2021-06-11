package com.BadDevelopers.SkypixelHyblock.Anticheat;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class AuraDetection implements Listener {
	@EventHandler
	public void onPlayerHit(EntityDamageByEntityEvent event) {
		if (!event.getDamager().getType().equals(EntityType.PLAYER)) return;
		
		Player player = (Player) event.getDamager();

		Entity ent = event.getEntity();
		
		Location loc = player.getLocation();
		Location against = ent.getLocation();
		
		Vector dir = loc.getDirection();
		dir.normalize();
		
		against.subtract(loc);
		double distance = against.distance(dir.toLocation(against.getWorld()));
		
		System.out.println(distance+" "+ent.getBoundingBox().getMax().length());
		
		if (distance > ent.getBoundingBox().getMax().length())
			Generic.registerDetection(player, "aura");
		
		
		/*
		RayTraceResult result = player.getWorld().rayTraceEntities(loc, loc.getDirection(),
				event.getEntity().getLocation().distance(player.getLocation())+1,
				new Predicate<Entity>() {
			
					@Override
					public boolean test(Entity e) {
						System.out.println(" "+e.getUniqueId()+" "+event.getEntity().getUniqueId()+" ");
						if (e.getUniqueId().equals(event.getEntity().getUniqueId())) {
							System.out.println("true");
							return true; }
						else return false;
					}
				}
		);
		 
		if (result == null) Generic.registerDetection(player, "aura");*/
	}
}
