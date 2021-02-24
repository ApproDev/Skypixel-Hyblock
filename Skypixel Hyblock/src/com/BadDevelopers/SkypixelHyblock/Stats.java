package com.BadDevelopers.SkypixelHyblock;

import java.util.ArrayList;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class Stats {
/*
 * Health
 * Defence
 * Strength 
 * Crit Chance
 * Speed
 * IQ
 * 
 */
	
	static Main main = JavaPlugin.getPlugin(Main.class);
	
	public enum Stat{
		Health(new NamespacedKey(main, "Health"), Attribute.GENERIC_MAX_HEALTH),
		Defence(new NamespacedKey(main, "Defence"), Attribute.GENERIC_ARMOR),
		Strength(new NamespacedKey(main, "Strength"), Attribute.GENERIC_ATTACK_DAMAGE),
		Crit_Chance(new NamespacedKey(main, "Crit_Chance")),
		Speed(new NamespacedKey(main, "Speed"), Attribute.GENERIC_MOVEMENT_SPEED),
		ATTACK_SPEED(new NamespacedKey(main, "Attack_Speed"), Attribute.GENERIC_ATTACK_SPEED),
		Intellegence(new NamespacedKey(main, "Intellegence"));
		
		Attribute at;
		NamespacedKey key;
		Stat(NamespacedKey key, Attribute at) {
			this.key = key;
			this.at = at;
		}
		Stat(NamespacedKey key) {
			this.key = key;
			this.at = null;
		}
		
		public void Reset(Player player, int amount) {
			if (this.at == null) return;
			
			AttributeInstance ai = player.getAttribute(at);
			
			ai.setBaseValue(ai.getDefaultValue());
		}
		public void Update(Player player, int amount) {
			
			if (this.at == null) return;
			
			AttributeInstance ai = player.getAttribute(at);
			
			if (ai.getDefaultValue() != 0) ai.setBaseValue((amount / 100) * ai.getDefaultValue());
			else ai.setBaseValue(amount / 100);
		}
	}
	
	public static Integer getStat(Player player, Stat stat) {
		PersistentDataContainer pdc = player.getPersistentDataContainer();
		
		Integer hp = pdc.get(stat.key, PersistentDataType.INTEGER);
		
		if (hp == null) return setStat(player, 100, stat);
		
		return hp;
	}
	private static Integer setStat(Player player, int amount, Stat stat) {
		PersistentDataContainer pdc = player.getPersistentDataContainer();
		
		pdc.set(stat.key, PersistentDataType.INTEGER, amount);
		
		stat.Update(player, amount);
		return amount;
	}
	ArrayList<statBoost> boosts = new ArrayList<statBoost>();
	Long lastID = -1L;
	public Long addStat(Player player, int amount, Stat stat, long expires) {
		Long id = lastID + 1L;
		boosts.add(new statBoost(amount, expires, id));
		setStat(player, getStat(player, stat) + amount, stat);
		lastID += id;
		return id;
	}
}

class statBoost {
	public final int amount;
	public final long expires;
	public final long id;
	public statBoost(int amount, long expires, long id) {
		this.amount = amount;
		this.expires = expires;
		this.id = id;
	}
}