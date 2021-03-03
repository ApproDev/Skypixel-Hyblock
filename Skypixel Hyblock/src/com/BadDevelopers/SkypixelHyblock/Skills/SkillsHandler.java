package com.BadDevelopers.SkypixelHyblock.Skills;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.material.Crops;
import org.bukkit.plugin.java.JavaPlugin;

import com.BadDevelopers.SkypixelHyblock.Main;
import com.BadDevelopers.SkypixelHyblock.Skills.Skills.Skill;

@SuppressWarnings("deprecation")
public class SkillsHandler implements Listener{

	Main main = JavaPlugin.getPlugin(Main.class);
	
	//foraging + farming
	@EventHandler
	public void on(BlockBreakEvent e) {
		
		Player player = e.getPlayer();
		
		Material block = e.getBlock().getType();
		
		if (block == Material.OAK_LOG
			|| block.equals(Material.SPRUCE_LOG)
			|| block.equals(Material.BIRCH_LOG)
			|| block.equals(Material.JUNGLE_LOG)
			|| block.equals(Material.ACACIA_LOG)
			|| block.equals(Material.DARK_OAK_LOG))
		{
	
			Integer skillEXPAmount = 6;
			
			player.sendMessage("you got " + skillEXPAmount);
			
			if (Skill.Foraging.getSkill(player) == null) {
				
				Skill.Foraging.setSkill(player, 0);
			}	
			Integer currentSkillEXP = Skill.Foraging.getSkill(player);
			
			Integer newSkillEXP = currentSkillEXP + skillEXPAmount;
			
			Skill.Foraging.setSkill(player, newSkillEXP);
			
			player.sendMessage("you now have " + newSkillEXP + " total foraging exp");		
		}
		
		else if (((Crops) e.getBlock().getState().getData()).getState() instanceof CropState) {
			
			if (((Crops) e.getBlock().getState().getData()).getState() == CropState.RIPE) {
				
				Integer skillEXPAmount = 4;
				
				if (Skill.Farming.getSkill(player) == null) {
					
					Skill.Farming.setSkill(player, 0);
				}	
				Integer currentSkillEXP = Skill.Farming.getSkill(player);
				
				Integer newSkillEXP = currentSkillEXP + skillEXPAmount;
				
				Skill.Farming.setSkill(player, newSkillEXP);
				
				player.sendMessage("you now have " + newSkillEXP + " total farming exp");	
				
			}
		}
	}
	
	//fishing
	@EventHandler
	public void on(PlayerFishEvent e) {
		
		if (e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)){
			
			Player player = e.getPlayer();
			
			Integer skillEXPAmount = 20;
			
			player.sendMessage("you've just got " + skillEXPAmount);
			
			if (Skill.Fishing.getSkill(player) == null) {
				
				Skill.Fishing.setSkill(player, 0);
			}	
			Integer currentSkillEXP = Skill.Fishing.getSkill(player);
			
			Integer newSkillEXP = currentSkillEXP + skillEXPAmount;
			
			Skill.Fishing.setSkill(player, newSkillEXP);
			
			player.sendMessage("you now have " + newSkillEXP + " total fishing exp");	
			
		}
		
	}

}
