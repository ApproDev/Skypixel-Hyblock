package com.BadDevelopers.SkypixelHyblock.UI;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import com.BadDevelopers.SkypixelHyblock.Main;

public class UIEventManager implements Listener {
	Main main;
	public UIEventManager(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void InventoryEvent(InventoryOpenEvent event) {
		if (!event.getInventory().getType().equals(InventoryType.WORKBENCH)) return;
		//event.setCancelled(true);
		Bukkit.getScheduler().runTaskLater(main, new Runnable() {

			@Override
			public void run() {
				Bukkit.getPluginManager().registerEvents(new OuterCraftingUI(event.getViewers().get(0), main), main);
				}
			}, 1);
		
		
	}
}
