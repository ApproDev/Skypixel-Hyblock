package com.BadDevelopers.SkypixelHyblock.Items;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.BadDevelopers.SkypixelHyblock.Main;

public class DropsHandler implements Listener {
	
	
	
	@EventHandler 
	public void DropItemEvent(DropItemEvent event) {
		Cancellable ev = event.origin;
		
		ArrayList<org.bukkit.entity.Item> items = new ArrayList<org.bukkit.entity.Item>();
		if (ev instanceof EntityDropItemEvent) {
			EntityDropItemEvent eve = (EntityDropItemEvent) ev;
			
			items.add(eve.getItemDrop());
		} else {
			BlockDropItemEvent bd = (BlockDropItemEvent) ev;
			
			items.addAll(bd.getItems());
		}
		
		for (org.bukkit.entity.Item item : items) {
			ItemStack is = item.getItemStack();

			com.BadDevelopers.SkypixelHyblock.Items.ItemHolder.Item skyItem = com.BadDevelopers.SkypixelHyblock.Items.ItemHolder.Item.getVanilla(is.getType());
			if (skyItem != null) {
				ItemStack give = skyItem.getItem(is.getAmount(), JavaPlugin.getPlugin(Main.class));
				
				Location loc = item.getLocation();
				
				loc.getWorld().dropItemNaturally(loc, give);
			}
			item.remove();
			
		}
	}
	

	
	@EventHandler
	public void EntityDropItemEvent(EntityDropItemEvent event) {
		Bukkit.getPluginManager().callEvent(new DropItemEvent(event));
	}
	
	@EventHandler
	public void BlockDropItemEvent(BlockDropItemEvent event) {
		Bukkit.getPluginManager().callEvent(new DropItemEvent(event));
	}
}