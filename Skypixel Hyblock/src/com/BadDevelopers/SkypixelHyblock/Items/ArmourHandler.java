package com.BadDevelopers.SkypixelHyblock.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.BadDevelopers.SkypixelHyblock.Main;
import com.BadDevelopers.SkypixelHyblock.Stats.Stat;
import com.BadDevelopers.SkypixelHyblock.Items.ItemHolder.Item;

public class ArmourHandler implements Runnable {

	Main main;
	public ArmourHandler(Main main) {
		this.main = main;
	}
	
	
	public enum ArmourSet {
		LAPIS_ARMOUR(Item.LAPIS_HELM, Item.LAPIS_CHEST, Item.LAPIS_LEGS, Item.LAPIS_BOOTS, Stat.Health, 60),
		
		IRON_ARMOUR(Item.IRON_HELM, Item.LAPIS_CHEST, Item.LAPIS_LEGS, Item.LAPIS_BOOTS),
		
		NULL(Item.NULL, Item.NULL, Item.NULL, Item.NULL);
		
		Item helmet, chestplate, legs, boots;
		Stat stat = Stat.NULL;
		Integer amount = 0;
		ArmourSet(Item helmet, Item chestplate, Item legs, Item boots) {
			this.helmet = helmet;
			this.chestplate = chestplate;
			this.legs = legs;
			this.boots = boots;
		}
		
		ArmourSet(Item helmet, Item chestplate, Item legs, Item boots, Stat stat, int amount) {
			this(helmet, chestplate, legs, boots);
			this.stat = stat;
			this.amount = amount;
		}
		
		public static boolean isMember(Item item) {
			for (ArmourSet set : values()) {
				if (set.helmet.equals(item) || set.chestplate.equals(item) || set.legs.equals(item) || set.boots.equals(item)) return true;
			}
			return false;
		}
		public static ArmourSet getArmourSet(Item item) {
			for (ArmourSet set : values()) {
				if (set.helmet.equals(item) || set.chestplate.equals(item) || set.legs.equals(item) || set.boots.equals(item)) return set;
			}
			return ArmourSet.NULL;
		}
	}
	private HashMap<UUID, ArmourSet> lastWearing = new HashMap<UUID, ArmourSet>();
	private HashMap<UUID, Long> boostID = new HashMap<UUID, Long>();
	
	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			PlayerInventory pi = player.getInventory();
			ItemStack[] armour = pi.getArmorContents();
			ArrayList<ArmourSet> itemlist = new ArrayList<ArmourSet>();
			for (ItemStack piece : armour) if (Item.isSkyItem(piece)) {
				Item item = Item.valueOf(piece);
				itemlist.add(ArmourSet.getArmourSet(item));
				Main.stats.addStat(player, item.armour, Stat.Defence, 60L, item.name, true);
			}
			
			
			
			if (itemlist.size() != 4) checkStillWearing(player, ArmourSet.NULL);
			else if (itemlist.get(0).equals(itemlist.get(1))
			&& itemlist.get(1).equals(itemlist.get(2))
			&& itemlist.get(2).equals(itemlist.get(3)) ) {
				ArmourSet set = itemlist.get(0);
				checkStillWearing(player, set);
			}
			else checkStillWearing(player, ArmourSet.NULL);
		}
		
	}
	
	void checkStillWearing (Player player, ArmourSet ass) {
		//lastWearing.putIfAbsent(player.getUniqueId(), ArmourSet.NULL);
		ArmourSet set = lastWearing.get(player.getUniqueId());
		
		if (ass.equals(set)) return;
		else {
			Long id = boostID.get(player.getUniqueId());
			Main.stats.disableStat(id);
			
			boostID.put(player.getUniqueId(), Main.stats.addStat(player, ass.amount, ass.stat, Long.valueOf(Integer.MAX_VALUE), ass.name(), true));
		}
		
		lastWearing.put(player.getUniqueId(), ass);
	}
}
