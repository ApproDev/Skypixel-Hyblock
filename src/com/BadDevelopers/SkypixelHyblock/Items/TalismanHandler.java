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
import com.BadDevelopers.SkypixelHyblock.Reforges.ReforgeHolder.Reforge;

public class TalismanHandler implements Runnable{

	Main main;
	public TalismanHandler(Main main) {
		this.main = main;
	}
	
	@Override
	public void run() {
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			
			ArrayList<Item> talismanList = new ArrayList<Item>();
			HashMap<Item, ItemStack> itemToItemStack = new HashMap<Item, ItemStack>();
			
			for (ItemStack item : player.getInventory().getContents()) {
				
				if (item == null) continue;
				
				Item skyItem = Item.valueOf(item);
				
				if (skyItem.cat.equals(Category.TALISMAN)) {
					
					talismanList.add(skyItem);
					itemToItemStack.put(skyItem, item);
				}
				
			}
			
			//HashMap<talisman family, item>
			HashMap<String, Item> talismanFamilyMap = new HashMap<String, Item>();

			for (Item item : talismanList.toArray(new Item[talismanList.size()])) {
			
				talismanFamilyMap.putIfAbsent(item.talismanFamily, item);

				Item skyItem = talismanFamilyMap.get(item.talismanFamily);
				
				if (item.rarity > skyItem.rarity) {
					
					talismanFamilyMap.put(item.talismanFamily, item);	
				}

			}
			
			for (Item item : talismanFamilyMap.values()) {
				
				Stat[] talismanStatsList = item.stats;
				Integer[] talismanStatsValues = item.statValues;		
				
				for (Integer i = 0 ; i < talismanStatsList.length ; i++) {
					
					Main.stats.addStat(player, talismanStatsValues[i] , talismanStatsList[i], 1100L, item.name + talismanStatsList[i], true);
	
				}
				
				ItemStack itemAsItemStack = itemToItemStack.get(item);
				
				Reforge reforge = main.reforgeholder.getReforge(itemAsItemStack);

				Stat[] talismanReforgeStatsList = reforge.stats;
				
				Integer itemRarity = item.rarity;
				
				ArrayList<Integer> talismanReforgeStatsValues = main.reforgeholder.getReforgeStatValues(reforge, itemRarity);
								
				Integer[] talismanReforgeStatsValuesAsIntegerArray = talismanReforgeStatsValues.toArray(new Integer[talismanReforgeStatsValues.size()]);
								
				for (Integer i = 0 ; i < talismanReforgeStatsList.length ; i++) {
					
					Main.stats.addStat(player, talismanReforgeStatsValuesAsIntegerArray[i] , talismanReforgeStatsList[i], 1100L, item.name + talismanReforgeStatsList[i], true);
					
				}
				
			}
			
		}
		
	}
	
}
