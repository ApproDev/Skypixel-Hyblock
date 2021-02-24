package com.BadDevelopers.SkypixelHyblock;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

public class Scoreboard implements Runnable, Listener {

	@Override
	public void run() {
		
	    
		ScoreboardReload();
		
	}
	
	public void ScoreboardReload() {
		
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		org.bukkit.scoreboard.Scoreboard board = manager.getMainScoreboard();	
		Objective moneySlot = board.getObjective("moneySlot");
			// MoneySlot objective Init
		    if (moneySlot == null) {
		    	Bukkit.broadcastMessage("moneySlot is null");
		    	moneySlot = board.registerNewObjective("moneySlot", "dummy", ChatColor.YELLOW + "" + ChatColor.BOLD + " HYBLOCK ");
		    }
		    
		    moneySlot.setDisplaySlot(DisplaySlot.SIDEBAR); 
		// MoneySlot updates
		for (Player player : Bukkit.getOnlinePlayers()){
			
			Integer newMoney = Main.currency.moneyGain.get(player.getUniqueId());
			
			board = manager.getMainScoreboard();
			moneySlot = board.getObjective(DisplaySlot.SIDEBAR);
		    Score moneyNumber = moneySlot.getScore("Purse:");
		    
		    Integer currentMoney = moneyNumber.getScore();
		    currentMoney = newMoney;
		    	
		    // MoneySlot changes applied here
		    if (currentMoney != null) {
		    	moneyNumber.setScore(currentMoney);
		    }
		    player.setScoreboard(board);
		}
	}

}
