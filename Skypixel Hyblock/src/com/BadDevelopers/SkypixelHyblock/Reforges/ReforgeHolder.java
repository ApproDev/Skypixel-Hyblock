package com.BadDevelopers.SkypixelHyblock.Reforges;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.BadDevelopers.SkypixelHyblock.Main;
import com.BadDevelopers.SkypixelHyblock.Stats.Stat;

public class ReforgeHolder implements Listener{
	
	public enum Reforge {
		
		//sword reforges
		NULL("", "sword", new Stat[] {Stat.NULL}, new Integer[] {0}, new Integer[] {0}, new Integer[] {0}, new Integer[] {0}, new Integer[] {0}, new Integer[] {0}),
		SHARP("Sharp", "sword", new Stat[] {Stat.Strength, Stat.Crit_Chance}, new Integer[] {10, 2}, new Integer[] {20, 4}, new Integer[] {30, 6}, new Integer[] {40, 8}, new Integer[] {50, 10}, new Integer[] {60, 12}),
		SMART("Smart", "sword", new Stat[] {Stat.Strength, Stat.Crit_Chance, Stat.Intelligence}, new Integer[] {2, 1, 15}, new Integer[] {4, 2, 30}, new Integer[] {6, 3, 45}, new Integer[] {8, 4, 60}, new Integer[] {10, 5, 75}, new Integer[] {12, 6, 90}),
		CHANCING("Chancing", "sword", new Stat[] {Stat.Crit_Chance}, new Integer[] {5}, new Integer[] {10}, new Integer[] {15}, new Integer[] {20}, new Integer[] {25}, new Integer[] {30}),
		BROAD("Broad", "sword", new Stat[] {Stat.Defence}, new Integer[] {15}, new Integer[] {30}, new Integer[] {45}, new Integer[] {60}, new Integer[] {75}, new Integer[] {90});
		
		public String name;
		public String type;
		public Stat[] stats;
		public Integer[] rar1statValues;
		public Integer[] rar2statValues;
		public Integer[] rar3statValues;
		public Integer[] rar4statValues;
	    public Integer[] rar5statValues;
		public Integer[] rar6statValues;
		
		//reforges
		Reforge(String name, String type, Stat[] stats, Integer[] rar1statValues, Integer[] rar2statValues, Integer[] rar3statValues, Integer[] rar4statValues, Integer[] rar5statValues, Integer[] rar6statValues) {
			this.name = name;
			this.type = type;
			this.stats = stats;
			this.rar1statValues = rar1statValues;
			this.rar2statValues = rar2statValues;
			this.rar3statValues = rar3statValues;
			this.rar4statValues = rar4statValues;
			this.rar5statValues = rar5statValues;
			this.rar6statValues = rar6statValues;
		}
				
	}
	
	public NamespacedKey reforgeNSK = null;
	
	Main main;
	public ReforgeHolder(Main main) {
		this.main = main;
	}
	
	public NamespacedKey getNamespacedKey() {
		
		if (reforgeNSK == null) {
			
			reforgeNSK = new NamespacedKey(main, "reforge");
			
		}
		
		return reforgeNSK;
		
	}
	
	public Reforge getReforge(ItemStack item) {
		
		PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
		
		String s = pdc.get(getNamespacedKey(), PersistentDataType.STRING);
		
		if (s == null) {
			setReforge(item, Reforge.NULL);
			return Reforge.NULL;
		}
		
		return Reforge.valueOf(s);
	}
	
	public ItemStack setReforge(ItemStack item, Reforge newValue) {
		
		ItemMeta itemMeta = item.getItemMeta();
		
		PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();

		pdc.set(getNamespacedKey(), PersistentDataType.STRING, newValue.toString());
		
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public Reforge randomiseReforge(ItemStack item) {
		
		Random r = new Random();
		
		Integer lengthOfEnum = Reforge.values().length;
		
		Integer randomNumber = r.nextInt(lengthOfEnum - 1);
		
		Reforge chosenReforge = Reforge.values()[randomNumber + 1];
		
		return chosenReforge;
		
	}
	
	public ArrayList<Integer> getReforgeStatValues(Reforge reforge, Integer rarity) {
		
		ArrayList<Integer> values = new ArrayList<Integer>();
		
		switch (rarity) {
			
		case 1:
			for (Integer i = 0 ; i < reforge.rar1statValues.length ; i++) {	
				values.add(reforge.rar1statValues[i]);
			}
			break;
		case 2:
			for (Integer i = 0 ; i < reforge.rar2statValues.length ; i++) {	
				values.add(reforge.rar2statValues[i]);
			}
			break;
		case 3:
			for (Integer i = 0 ; i < reforge.rar3statValues.length ; i++) {	
				values.add(reforge.rar3statValues[i]);
			}
			break;
		case 4:
			for (Integer i = 0 ; i < reforge.rar4statValues.length ; i++) {	
				values.add(reforge.rar4statValues[i]);
			}
			break;
		case 5:
			for (Integer i = 0 ; i < reforge.rar5statValues.length ; i++) {	
				values.add(reforge.rar5statValues[i]);
			}
			break;
		case 6:
			for (Integer i = 0 ; i < reforge.rar6statValues.length ; i++) {	
				values.add(reforge.rar6statValues[i]);
			}
			break;
			
		default:
			break; 
			
		}
		
		return values;
		
	}
	
	@EventHandler
	public void on(BlockBreakEvent e) {
		
		ItemStack heldItem = e.getPlayer().getInventory().getItemInMainHand();
		
		if (heldItem == null) {return;}
		
		if (heldItem.getItemMeta() == null) {return;}
		
		Reforge newReforge = randomiseReforge(heldItem);
		
		heldItem = (setReforge(heldItem, newReforge));
		
		ItemMeta im = e.getPlayer().getInventory().getItemInMainHand().getItemMeta();
		
		List<String> lore = im.getLore();
		
		if (lore == null) {lore = new ArrayList<String>();}
		
		if (lore.size() == 0) {lore.add("");}
		
		lore.set(0, ChatColor.GRAY + "Reforged to " + newReforge.name);
		
		im.setLore(lore);
		
		e.getPlayer().getInventory().getItemInMainHand().setItemMeta(im);
		
	}

}
