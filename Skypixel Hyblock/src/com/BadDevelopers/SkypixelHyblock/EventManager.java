package com.BadDevelopers.SkypixelHyblock;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class EventManager implements Listener {
	
	
	
	@EventHandler
	public void on(BlockBreakEvent e) {
		
		Player player = e.getPlayer();
		Integer moneySource = 100;
		if (Main.currency.moneyGain.get(player.getUniqueId()) == null) {
			Main.currency.moneyGain.put(player.getUniqueId(), moneySource);
		}
		else {
			
			Main.currency.moneyGain.put(player.getUniqueId(), Main.currency.moneyGain.get(player.getUniqueId()) + moneySource);
		}
		
	}
	
}
