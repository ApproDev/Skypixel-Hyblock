package com.BadDevelopers.SkypixelHyblock;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import com.BadDevelopers.SkypixelHyblock.Stats.Stat;

public class Stats implements Runnable {
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
	
	public enum Stat {
		Health(new NamespacedKey(main, "Health"), 5, Attribute.GENERIC_MAX_HEALTH),
		Defence(new NamespacedKey(main, "Defence"), 100, Attribute.GENERIC_ARMOR),
		Strength(new NamespacedKey(main, "Strength"), 50, Attribute.GENERIC_ATTACK_DAMAGE),
		Crit_Chance(new NamespacedKey(main, "Crit_Chance"), 1),
		Speed(new NamespacedKey(main, "Speed"), 1000, Attribute.GENERIC_MOVEMENT_SPEED),
		ATTACK_SPEED(new NamespacedKey(main, "Attack_Speed"), 100, Attribute.GENERIC_ATTACK_SPEED),
		Intellegence(new NamespacedKey(main, "Intellegence"), 1),
		
		NULL(null, Integer.MAX_VALUE);
		
		Attribute at;
		NamespacedKey key;
		double coeff;
		Stat(NamespacedKey key, double coeff, Attribute at) {
			this.key = key;
			this.at = at;
			this.coeff = coeff;
		}
		Stat(NamespacedKey key, double coeff) {
			this.key = key;
			this.at = null;
			this.coeff = coeff;
		}
		
		double getDefaultAttributeValue() {
			switch(this) {
			case Health:
				return 20;
			case Defence:
				return 0;
			case ATTACK_SPEED:
				return 4;
			case Speed:
				return 0.1; //0.11500000208
			case Strength:
				return 1;
			default:
				return 0;
			}
		}
		
		public void Reset(Player player) {
			if (this.at == null) return;
			
			AttributeInstance ai = player.getAttribute(at);
			
			ai.setBaseValue(getDefaultAttributeValue());
		}
		
		public void Update(Player player, int amount) {
			
			if (this.at == null) return;
			
			AttributeInstance ai = player.getAttribute(at);
			
			double d = (amount / coeff) + getDefaultAttributeValue();
			//if (this.equals(Stat.Health)) player.sendMessage(amount+" "+coeff);
			if (ai.getBaseValue() != d) ai.setBaseValue(d);
		}
	}
	
	public boolean isReasonPresent(Player player, String string) {
		for (statBoost boost : boosts) {
			if (boost.booster.equals(string)) return true;
		}
		return false;
	}
	
	public static Integer getStat(Player player, Stat stat) {
		PersistentDataContainer pdc = player.getPersistentDataContainer();
		
		if (stat.equals(Stat.NULL)) return 0;
		
		Integer hp = pdc.get(stat.key, PersistentDataType.INTEGER);
		
		if (hp == null) return setStat(player, 100, stat);
		
		return hp;
	}
	
	private static Integer setStat(Player player, int amount, Stat stat) {
		PersistentDataContainer pdc = player.getPersistentDataContainer();
		
		if (stat.equals(Stat.NULL)) return 0;
		
		pdc.set(stat.key, PersistentDataType.INTEGER, amount);
		
		return amount;
	}
	
	ArrayList<statBoost> boosts = new ArrayList<statBoost>();
	Long lastID = -1L;
	public Long addStat(Player player, int amount, Stat stat, Long expires, String booster, boolean isPassive) {
		Long id = lastID + 1L;
		boosts.add(new statBoost(amount, expires, id, stat, player, booster, isPassive));
		setStat(player, getStat(player, stat) + amount, stat);
		lastID = id;
		//player.sendMessage(stat.toString() + "" + amount);
		return id;
	}
	
	public void disableStat(Long id) {
		if (id == null) return;
		for (int i = 0; i < boosts.size(); i++) {
			statBoost boost = boosts.get(i);
			if (boost.id.equals(id)) {
				boosts.remove(i);
			}
		}
	}
	
	public void recalculateBoosts() {
		for (Stat stat : Stat.values()) for (Player player : Bukkit.getOnlinePlayers()) {
			stat.Reset(player);
			
			stat.Update(player, countBoosts(stat, player));
		}
	}
	
	int countBoosts(Stat stat, Player player) {
		checkValidBoosts();
		
		int i = 0;
		
		for (statBoost boost : boosts) {
			if (boost.stat.equals(stat) && boost.player.equals(player)) i += boost.amount;
		}
		
		return i;
	}
	
	public void checkValidBoosts() {
		for (int i = 0; i < boosts.size(); i++) {
			statBoost boost = boosts.get(i);
			if (!boost.stillApplies()) {
				if (!boost.isPassive) boost.player.sendMessage(Main.prefix+"Your "+boost.booster+" boost has run out!");
				boosts.remove(i);
			}
		}
	}

	@Override
	public void run() {
		recalculateBoosts();
		
	}
	
	
}

final class statBoost {
	public final int amount;
	private final Long expires;
	public final Long id;
	final Stat stat;
	public final Player player;
	public final String booster;
	public final long startTime = System.currentTimeMillis();
	public final boolean isPassive;
	public statBoost(int amount, Long expires, long id, Stat stat, Player player, String booster, boolean isPassive) {
		this.amount = amount;
		this.expires = expires;
		this.id = id;
		this.stat = stat;
		this.player = player;
		this.booster = booster;
		this.isPassive = isPassive;
	}
	public boolean stillApplies() {
		return System.currentTimeMillis() < startTime + expires;
	}
}