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

import com.BadDevelopers.SkypixelHyblock.CustomRaces.Race;
import com.BadDevelopers.SkypixelHyblock.Stats.Stat;

public class Stats implements Runnable {
	
	static Main main = JavaPlugin.getPlugin(Main.class);
	
	//lets plugin get mana value
	public Double getMana(Player player) {
		
		PersistentDataContainer pdc = player.getPersistentDataContainer();
		
		Double mana = pdc.get(new NamespacedKey(main, "mana"), PersistentDataType.DOUBLE);
		
		if (mana == null) return setMana(player, 0D);
		
		return mana;
	}

	//lets plugin set mana value
	public Double setMana(Player player, Double newValue) {
		
		PersistentDataContainer pdc = player.getPersistentDataContainer();
		
		pdc.set(new NamespacedKey(main, "mana"), PersistentDataType.DOUBLE, newValue);
		
		return newValue;
	}
	
	//manages mana regen
	public void updateMana() {
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			
			Double maxMana = getStat(player, Stat.Intelligence);
			
			Double manaNeededToAdd = maxMana / 2000;
			
			Double newManaValue = getMana(player) + manaNeededToAdd;
			
			if (newManaValue > maxMana) {newManaValue = maxMana;}
			
			setMana(player, newManaValue);
			
		}
		
	}
	
	
	
	public enum Stat {
		Health(5, Attribute.GENERIC_MAX_HEALTH, 'h'),
		Defence(100, Attribute.GENERIC_ARMOR, 'd'),
		Strength(50, Attribute.GENERIC_ATTACK_DAMAGE, 's'),
		Crit_Chance(1, 'c'),
		Speed(1000, Attribute.GENERIC_MOVEMENT_SPEED, 'd'),
		Attack_Speed(100, Attribute.GENERIC_ATTACK_SPEED, 'a'),
		Intelligence(1, 'i'),
		
		NULL(Integer.MAX_VALUE, ' ');
		
		Attribute at;
		public double coeff;
		public char sym;
		Stat(double coeff, Attribute at, char sym) {
			this.at = at;
			this.sym = sym;
			this.coeff = coeff;
		}
		Stat(double coeff, char sym) {
			this.sym = sym;
			this.coeff = coeff;
		}
		
		double getDefaultAttributeValue() {
			switch(this) {
			case Health:
				return 20;
			case Defence:
				return 0;
			case Attack_Speed:
				return 4;
			case Speed:
				return 0.1; //0.11500000208
			case Strength:
				return 1;
			case Intelligence:
				return 100;
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
			
			if (amount > 1000) amount = 1000;
			
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
	
	public Double getStat(Player player, Stat stat) {
		if (stat.equals(Stat.NULL)) return 0d;
		
		checkValidBoosts();
		
		return countBoosts(stat, player)+(stat.getDefaultAttributeValue()*stat.coeff);
	}
	
	public Long getLongStat(Player player, Stat stat) {
		return Math.round(getStat(player, stat));
	}
	
	/*
	public static Integer getStat(Player player, Stat stat) {
		//PersistentDataContainer pdc = player.getPersistentDataContainer();
		
		if (stat.equals(Stat.NULL)) return 0;
		
		//Integer hp = pdc.get(stat.key, PersistentDataType.INTEGER);
		
		if (hp == null) return setStat(player, 100, stat);
		
		return hp;
	}*/
	/*
	private static Integer setStat(Player player, int amount, Stat stat) {
		//PersistentDataContainer pdc = player.getPersistentDataContainer();
		
		if (stat.equals(Stat.NULL)) return 0;
		
		//pdc.set(stat.key, PersistentDataType.INTEGER, amount);
		
		return amount;
	}*/
	
	ArrayList<statBoost> boosts = new ArrayList<statBoost>();
	Long lastID = -1L;
	public Long addStat(Player player, int amount, Stat stat, Long expires, String booster, boolean isPassive) {
		disableStat(booster);
		
		Long id = lastID + 1L;
		
		boosts.add(new statBoost(amount, expires, id, stat, player, booster, isPassive));
		//setStat(player, getStat(player, stat) + amount, stat);
		lastID = id;
		//player.sendMessage(stat.toString() + "" + amount);
		return id;
	}
	
	public Long addStatMultiple(Player player, int amount, Stat[] stats, Long expires, String booster, boolean isPassive) {
		disableStat(booster);
		
		Long id = lastID + 1L;
		for (Stat stat : stats) {
			if (stat.equals(Stat.Intelligence)) amount *= Race.getRace(player, main).IQStatModifier + 1;
			boosts.add(new statBoost(amount, expires, id, stat, player, booster, isPassive));
			//setStat(player, getStat(player, stat) + amount, stat);
		}
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
	
	public void disableStat(String name) {
		if (name == null) return;
		for (int i = 0; i < boosts.size(); i++) {
			statBoost boost = boosts.get(i);
			if (boost.booster.equals(name)) {
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
		
		updateMana();
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