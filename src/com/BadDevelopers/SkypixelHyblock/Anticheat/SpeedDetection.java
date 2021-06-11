package com.BadDevelopers.SkypixelHyblock.Anticheat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class SpeedDetection implements Listener {
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		double distance = event.getTo().distance(event.getFrom());
		
		if (player.isFlying()) return;
		
		if (!player.isFlying() && distance <= 1.2) return;
		
		Generic.registerDetection(player, "speed "+Generic.decimalFormat.format(distance));
	}

}
