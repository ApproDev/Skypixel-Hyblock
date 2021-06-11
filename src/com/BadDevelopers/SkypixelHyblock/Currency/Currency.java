package com.BadDevelopers.SkypixelHyblock.Currency;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.BadDevelopers.SkypixelHyblock.Main;

public class Currency {
	
	Main main;
	public Currency(Main main) {
		this.main = main;
	}
	
	//lets plugin get purse value
	public Long getPurse(Player player) {
		
		PersistentDataContainer pdc = player.getPersistentDataContainer();
		
		Long purse = pdc.get(new NamespacedKey(main, "purse"), PersistentDataType.LONG);
		
		if (purse == null) return setPurse(player, 0L);
		
		return purse;
	}

	//lets plugin set purse value
	public Long setPurse(Player player, Long newValue) {
		
		PersistentDataContainer pdc = player.getPersistentDataContainer();
		
		pdc.set(new NamespacedKey(main, "purse"), PersistentDataType.LONG, newValue);
		
		return newValue;
	}
	
	//lets plugin get bank value
	public Long getBank(Player player) {
		
		PersistentDataContainer pdc = player.getPersistentDataContainer();
		
		Long purse = pdc.get(new NamespacedKey(main, "bank"), PersistentDataType.LONG);
		
		if (purse == null) return setPurse(player, 0L);
		
		return purse;
	}
	
	
	//lets plugin set bank value
	public Long setBank(Player player, Long newValue) {
		
		PersistentDataContainer pdc = player.getPersistentDataContainer();
		
		pdc.set(new NamespacedKey(main, "bank"), PersistentDataType.LONG, newValue);
		
		return newValue;
	}
	
}
