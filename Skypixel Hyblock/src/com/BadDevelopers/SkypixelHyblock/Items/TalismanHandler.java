package com.BadDevelopers.SkypixelHyblock.Items;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.BadDevelopers.SkypixelHyblock.Main;
import com.BadDevelopers.SkypixelHyblock.Stats.Stat;
import com.BadDevelopers.SkypixelHyblock.Items.ItemHolder.Category;
import com.BadDevelopers.SkypixelHyblock.Items.ItemHolder.Item;

public class TalismanHandler implements Runnable{

	@Override
	public void run() {
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			
			ArrayList<Item> talismanList = new ArrayList<Item>();
			
			for (ItemStack item : player.getInventory().getContents()) {
				
				if (item == null) continue;
				
				Item skyItem = Item.valueOf(item);
				
				if (skyItem.cat.equals(Category.TALISMAN)) {
					
					talismanList.add(skyItem);
				}
				
			}
			
			HashMap<String, Item> talismanFamilyMap = new HashMap<String, Item>();
			
			for (Item item : talismanList.toArray(new Item[talismanList.size()])) {
				
				talismanFamilyMap.putIfAbsent(item.talismanFamily, item);

				Item skyItem = talismanFamilyMap.get(item.talismanFamily);
				
				if (item.rarity > skyItem.rarity) {
					
					talismanFamilyMap.put(item.talismanFamily, item);	
				}

			}
			
			for (Item item : talismanFamilyMap.values()) {
				
				Stat[] statsList = item.stats;
				Integer[] statsValues = item.statValues;		
				
				for (Integer i = 0 ; i < statsList.length ; i++) {
					
					Main.stats.addStat(player, statsValues[i] , statsList[i], 1010L, item.name + statsList[i], true);
					
				}
				
			}
			
		}
		
	}
	
}
