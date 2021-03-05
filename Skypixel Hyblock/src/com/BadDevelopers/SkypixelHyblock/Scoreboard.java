package com.BadDevelopers.SkypixelHyblock;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

import com.BadDevelopers.SkypixelHyblock.Stats.Stat;
import com.BadDevelopers.SkypixelHyblock.Skills.Skills.Skill;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Scoreboard implements Runnable, Listener {

	public HashMap<Player, Integer> playerSkillGain = new HashMap<Player, Integer>();
	public HashMap<Player, Skill> playerSkillGainType = new HashMap<Player, Skill>();
	public HashMap<Player, Integer> playerActionBarCooldown = new HashMap<Player, Integer>();
	
	final Main main;
	ScoreboardManager manager;
	public Scoreboard(Main main) {
		this.main = main;
		
		Bukkit.getScheduler().runTask(main, new Runnable() {

			@Override
			public void run() {
				manager = Bukkit.getScoreboardManager();
				
			}});
	}
	
	@Override
	public void run() {
		   
		ScoreboardReload();
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.getPlayer().setScoreboard(manager.getNewScoreboard());
	}
	
	public void ScoreboardReload() {		
	
		// MoneySlot updates
		for (Player player : Bukkit.getOnlinePlayers()){
			
			org.bukkit.scoreboard.Scoreboard board = player.getScoreboard();
			
			Objective moneySlot = board.getObjective("moneySlot");
			
			// MoneySlot objective Init
		    if (moneySlot == null) {
		    	moneySlot = board.registerNewObjective("moneySlot", "dummy", ChatColor.YELLOW + "" + ChatColor.BOLD + " HYBLOCK ");	
		    }
		    
		    //if no cooldown, sets the cooldown to 10
		    if (playerActionBarCooldown.get(player) == null) {
		    	
		    	playerActionBarCooldown.put(player, 10);	    	
		    }
		    
		    playerActionBarCooldown.put(player, playerActionBarCooldown.get(player) + 1);
		    
		    if (playerActionBarCooldown.get(player) >= 10) {
		    	
		    	playerSkillGain.put(player, null);
		    	playerActionBarCooldown.put(player, 0);
		    	
		    }
		    
		    String skillStat;
		    
		    //checks if player has gained skill xp
		    if (playerSkillGain.get(player) != null) {
		    	
		    	skillStat = "+" + playerSkillGain.get(player) + " " + playerSkillGainType.get(player) + " " + "(" + playerSkillGainType.get(player).getSkill(player)+ ")    ";	
		    
		    }
		    else {skillStat = "";}
		    
		    String allStats = ChatColor.GREEN + "" + skillStat + ChatColor.AQUA + "" + Main.stats.getLongStat(player, Stat.Intellegence) + " "+Stat.Intellegence.sym;
		    
		    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(allStats));
		    
		    moneySlot.setDisplaySlot(DisplaySlot.SIDEBAR); 
			
			moneySlot = board.getObjective(DisplaySlot.SIDEBAR);
			
			Long currentMoney = main.currency.getPurse(player);
			
			if (currentMoney == null) continue;
			
			Integer currentMoneyAsInt = currentMoney.intValue();
			
			Score moneyNumber = moneySlot.getScore("purse: ");
			
	    	moneyNumber.setScore(currentMoneyAsInt);
		    player.setScoreboard(board);
		}
	}

}
