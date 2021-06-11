package com.BadDevelopers.SkypixelHyblock.Anticheat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ReachDetection implements Listener {
	@EventHandler
	public void onPlayerHit(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return;
		
		Player player = (Player) event.getDamager();
		
		double distance = event.getEntity().getLocation().distance(player.getLocation());
		
		if (distance <= (Generic.getPing(player) / 200) + 4) return;
		
		Generic.registerDetection(player, "reach "+Generic.decimalFormat.format(distance));
	}
}