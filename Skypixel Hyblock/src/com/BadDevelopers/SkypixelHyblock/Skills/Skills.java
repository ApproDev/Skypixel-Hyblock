package com.BadDevelopers.SkypixelHyblock.Skills;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import com.BadDevelopers.SkypixelHyblock.Main;

public class Skills {
	
	static Main main = JavaPlugin.getPlugin(Main.class);
	
	public enum Skill {
		
		Woodworking(new NamespacedKey(main, "Woodworking")),
		Stoneworking(new NamespacedKey(main, "Stoneworking")),
		Metalworking(new NamespacedKey(main, "Metalworking")),
		Blacksmithing(new NamespacedKey(main, "Blacksmithing")),
		Unarmed_Fighting(new NamespacedKey(main, "Unarmed_Fighting")),
		Swordmastery(new NamespacedKey(main, "Swordmastery")),
		Archery(new NamespacedKey(main, "Archery")),
		Foraging(new NamespacedKey(main, "Foraging")),
		Fishing(new NamespacedKey(main, "Fishing")),
		Farming(new NamespacedKey(main, "Farming")),
		Animal_Handling(new NamespacedKey(main, "Animal_Handling"));
		
		NamespacedKey key;
		
		Skill(NamespacedKey key){
			this.key = key;
		}
				
		//lets plugin get skill exp
		public Integer getSkill(Player player) {
			
			PersistentDataContainer pdc = player.getPersistentDataContainer();
			
			Integer skillEXP = pdc.get(key, PersistentDataType.INTEGER);
			
			return skillEXP;
		
		}
		
		//lets plugin set skill exp
		public Integer setSkill(Player player, Integer newSkillEXP) {
			
			PersistentDataContainer pdc = player.getPersistentDataContainer();
			
			pdc.set(key, PersistentDataType.INTEGER, newSkillEXP);
			
			return newSkillEXP;
			
		}
	}
	
	
	
	
	
}
