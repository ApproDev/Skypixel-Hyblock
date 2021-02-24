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

	Main main;
	public Scoreboard(Main main) {
		this.main = main;
	}
	
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
		    	moneySlot = board.getObjective(DisplaySlot.SIDEBAR);	
		    }
		    
		    moneySlot.setDisplaySlot(DisplaySlot.SIDEBAR); 
		// MoneySlot updates
		for (Player player : Bukkit.getOnlinePlayers()){
			
			board = manager.getMainScoreboard();
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
