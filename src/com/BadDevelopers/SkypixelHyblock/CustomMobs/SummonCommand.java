package com.BadDevelopers.SkypixelHyblock.CustomMobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.BadDevelopers.SkypixelHyblock.Main;

public class SummonCommand extends com.BadDevelopers.SkypixelHyblock.Command {

	final Main main;
	
	public SummonCommand(Main main) {
		this.main = main;
		this.name = "summon";
		this.completer = new SummonCompleter();
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (!(arg0 instanceof Player)) {
			Bukkit.broadcastMessage("This command is only executable by players!");
		}
		Player player = (Player) arg0;
		
		if (arg3.length != 1) return false;
		
		try {
			Entities.valueOf(arg3[0]).spawn(player.getLocation());
		} catch (IllegalArgumentException e) {return false;}
		
		return true;
	}

}

class SummonCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		ArrayList<String> tab = new ArrayList<String>();
		
		String lastEntry = arg3[arg3.length - 1].toLowerCase();
		
		for (Entities entity : Entities.values()) {
			String s = entity.toString();
			if (s.toLowerCase().contains(lastEntry)) tab.add(s);
		}
		
		return tab;
	}
	
}