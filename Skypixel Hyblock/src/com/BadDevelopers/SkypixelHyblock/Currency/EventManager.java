package com.BadDevelopers.SkypixelHyblock.Currency;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.BadDevelopers.SkypixelHyblock.Main;

public class EventManager implements Listener {
	
	Main main = JavaPlugin.getPlugin(Main.class);
	
	@EventHandler
	public void on(BlockBreakEvent e) {
		
		Player player = e.getPlayer();
		Long moneySource = (long) 100;
		Long newValue = main.currency.getPurse(player) + moneySource;
		main.currency.setPurse(player, newValue);		
	}	
}
