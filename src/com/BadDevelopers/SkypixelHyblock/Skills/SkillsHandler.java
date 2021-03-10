package com.BadDevelopers.SkypixelHyblock.Skills;

import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.BadDevelopers.SkypixelHyblock.Main;
import com.BadDevelopers.SkypixelHyblock.Skills.Skills.Skill;

public class SkillsHandler implements Listener{

	Main main = JavaPlugin.getPlugin(Main.class);
	
	//sets skill exp stuff into pdc
	private void applySkillEXP(Player player, Integer skillEXPAmount, Skill skillType) {	
		
		main.scoreboard.playerSkillGain.put(player, skillEXPAmount);
		main.scoreboard.playerSkillGainType.put(player, skillType);
		main.scoreboard.playerActionBarCooldown.put(player, 0);
		
		if (skillType.getSkill(player) == null) {
			
			skillType.setSkill(player, 0);
		}
		
		Integer currentSkillEXP = skillType.getSkill(player);
		
		Integer newSkillEXP = currentSkillEXP + skillEXPAmount;
		
		skillType.setSkill(player, newSkillEXP);
		
	}
	
	//foraging + farming + mining
	@EventHandler
	public void on(BlockBreakEvent e) {
		
		Integer skillEXPAmount = null;
		
		Player player = e.getPlayer();
		
		Material block = e.getBlock().getType();
		
		if (block == Material.OAK_LOG
			|| block.equals(Material.SPRUCE_LOG)
			|| block.equals(Material.BIRCH_LOG)
			|| block.equals(Material.JUNGLE_LOG)
			|| block.equals(Material.ACACIA_LOG)
			|| block.equals(Material.DARK_OAK_LOG))
		{
			
			applySkillEXP(player, 6, Skill.Foraging);
			
		}
		
		else if (e.getBlock().getBlockData() instanceof Ageable) 
		{
			
			if (((Ageable) e.getBlock().getBlockData()).getAge() == ((Ageable) e.getBlock().getBlockData()).getMaximumAge()) {
				
				applySkillEXP(player, 4, Skill.Farming);
				
			}
		}
		
		else if (e.getBlock().getType().equals(Material.COAL_ORE) 
				|| e.getBlock().getType().equals(Material.IRON_ORE)
				|| e.getBlock().getType().equals(Material.GOLD_ORE)
				|| e.getBlock().getType().equals(Material.NETHER_GOLD_ORE)
				|| e.getBlock().getType().equals(Material.NETHER_QUARTZ_ORE)
				|| e.getBlock().getType().equals(Material.REDSTONE_ORE)
				|| e.getBlock().getType().equals(Material.EMERALD_ORE)
				|| e.getBlock().getType().equals(Material.LAPIS_ORE)
				|| e.getBlock().getType().equals(Material.DIAMOND_ORE)
				|| e.getBlock().getType().equals(Material.ANCIENT_DEBRIS)
				|| e.getBlock().getType().equals(Material.STONE)
				|| e.getBlock().getType().equals(Material.COBBLESTONE)) 
		{
			
			switch (e.getBlock().getType()) {
			
			case COAL_ORE:
				skillEXPAmount = 4;
				break;
			case IRON_ORE:
				skillEXPAmount = 4;
				break;
			case GOLD_ORE:
				skillEXPAmount = 4;
				break;
			case NETHER_GOLD_ORE:
				skillEXPAmount = 4;
				break;
			case NETHER_QUARTZ_ORE:
				skillEXPAmount = 4;
				break;
			case REDSTONE_ORE:
				skillEXPAmount = 6;
				break;
			case EMERALD_ORE:
				skillEXPAmount = 6;
				break;
			case LAPIS_ORE:
				skillEXPAmount = 6;
				break;
			case DIAMOND_ORE:
				skillEXPAmount = 8;
				break;
			case ANCIENT_DEBRIS:
				skillEXPAmount = 10;
				break;
			case STONE:
				skillEXPAmount = 1;
				break;	
			case COBBLESTONE:
				skillEXPAmount = 1;
				break;
			default:
				return;
			}
			
			applySkillEXP(player, skillEXPAmount, Skill.Excavating);
	
		}
		
	}
	
	//fishing
	@EventHandler
	public void on(PlayerFishEvent e) {
		
		if (e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)){
			
			Player player = e.getPlayer();
			
			applySkillEXP(player, 20, Skill.Fishing);
			
		}
		
	}
	
	//archery
	@EventHandler
	public void on(ProjectileLaunchEvent e) {
		
		if (!(e.getEntity().getShooter() instanceof Player)) {return;}
		Player player = (Player) e.getEntity().getShooter();
		applySkillEXP(player, 10, Skill.Archery);
		
	}

}
