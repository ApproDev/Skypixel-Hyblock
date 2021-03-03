package com.BadDevelopers.SkypixelHyblock;

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

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Scoreboard implements Runnable, Listener {

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
		    String allStats = ChatColor.RED + "" + Math.round(player.getHealth())*5  + "/" + Main.stats.getLongStat(player, Stat.Health) + " "+Stat.Health.sym+"      " + ChatColor.GREEN+ "" + Main.stats.getLongStat(player, Stat.Defence) + " "+Stat.Defence.sym+"      " + ChatColor.AQUA + "" + Main.stats.getLongStat(player, Stat.Intellegence) + " "+Stat.Intellegence.sym+"      ";
		    
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
