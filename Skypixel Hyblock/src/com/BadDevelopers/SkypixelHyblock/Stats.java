package com.BadDevelopers.SkypixelHyblock;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
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
	//This class is instanciated outside of a static context, so this doesn't return null
	static Main main = JavaPlugin.getPlugin(Main.class);
	
	
	//If you want to add a new stat, add it here and everything should work just fine
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
		} // not all stats have an attribute, so there needs to be another method
		Stat(double coeff, char sym) {
			this.sym = sym;
			this.coeff = coeff;
		}
		
		//uses magic values as I couldn't find another way
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
			default:
				return 0;
			}
		}
		// Resets a player's stats. This is used everytime stats are refreshed, so there is no need for it to be used outside of this class
		public void Reset(Player player) {
			if (this.at == null) return;
			
			AttributeInstance ai = player.getAttribute(at);
			
			ai.setBaseValue(getDefaultAttributeValue());
		}
		
		/*This updates a player's stats.
		 *NOTE: DO NOT USE THIS. If you wish to add to / take from a player's stats, use
		 *Stats#addStat as if you update it directly, it will be reset very quickly by this class'
		 *update cycle
		*/
		public void Update(Player player, int amount) {
			
			if (this.at == null) return;
			
			AttributeInstance ai = player.getAttribute(at);
			
			double d = (amount / coeff) + getDefaultAttributeValue();
			//if (this.equals(Stat.Health)) player.sendMessage(amount+" "+coeff);
			if (ai.getBaseValue() != d) ai.setBaseValue(d);
		}
	}
	
	// Allows you to check if a stat boost is present on a player
	public boolean isReasonPresent(Player player, String string) {
		for (statBoost boost : boosts) {
			if (boost.booster.equals(string)) return true;
		}
		return false;
	}
	
	// Allows you to get one of a player's stats
	public Double getStat(Player player, Stat stat) {
		if (stat.equals(Stat.NULL)) return 0d;
		
		checkValidBoosts();
		
		return countBoosts(stat, player)+(stat.getDefaultAttributeValue()*stat.coeff);
	}
	
	// Gets the stat as a round number
	public Long getLongStat(Player player, Stat stat) {
		return Math.round(getStat(player, stat));
	}
	
	// Player info is stored in the statBoost class, so we dont need to seperate it player-by-player
	ArrayList<statBoost> boosts = new ArrayList<statBoost>();
	// stores the last used stat id. Starts on -1 so that the first id is 0;
	Long lastID = -1L;
	
	// Adds a stat boost
	public Long addStat(Player player, int amount, Stat stat, Long expires, String booster, boolean isPassive) {
		Long id = lastID + 1L;
		boosts.add(new statBoost(amount, expires, id, stat, player, booster, isPassive));
		//setStat(player, getStat(player, stat) + amount, stat);
		lastID = id;
		//player.sendMessage(stat.toString() + "" + amount);
		return id;
	}
	
	// adds an array of stats under the same ID. This allows for a stat boost to provide multiple bonuses.
	public Long addStatMultiple(Player player, int amount, Stat[] stats, Long expires, String booster, boolean isPassive) {
		Long id = lastID + 1L;
		for (Stat stat : stats) {
			boosts.add(new statBoost(amount, expires, id, stat, player, booster, isPassive));
			//setStat(player, getStat(player, stat) + amount, stat);
		}
		lastID = id;
		//player.sendMessage(stat.toString() + "" + amount);
		return id;
	}
	
	//Disables a stat.
	public void disableStat(Long id) {
		if (id == null) return;
		for (int i = 0; i < boosts.size(); i++) {
			statBoost boost = boosts.get(i);
			if (boost.id.equals(id)) {
				boosts.remove(i);
			}
		}
	}
	// Adds up all of the boosts which a player has
	public void recalculateBoosts() {
		for (Stat stat : Stat.values()) for (Player player : Bukkit.getOnlinePlayers()) {
			stat.Reset(player);
			
			stat.Update(player, countBoosts(stat, player));
		}
	}
	
	// Gets the amount of boost that a player should have
	int countBoosts(Stat stat, Player player) {
		checkValidBoosts();
		
		int i = 0;
		
		for (statBoost boost : boosts) {
			if (boost.stat.equals(stat) && boost.player.equals(player)) i += boost.amount;
		}
		
		return i;
	}
	
	// Checks if there are any expired boosts
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