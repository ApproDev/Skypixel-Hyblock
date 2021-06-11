package com.BadDevelopers.SkypixelHyblock.CustomMobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.BadDevelopers.SkypixelHyblock.Main;

import net.minecraft.server.v1_16_R3.Entity;

public class SummonCommand extends com.BadDevelopers.SkypixelHyblock.Command {

	final Main main;
	
	public SummonCommand(Main main) {
		this.main = main;
		this.name = "skysummon";
		this.completer = new SummonCompleter();
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (!(arg0 instanceof Player)) {
			Bukkit.broadcastMessage("This command is only executable by players!");
		}
		Player player = (Player) arg0;
		
		if (arg3.length != 1) return false;
		
		Class<? extends Entity> entity = Main.customEntitiesHelper.getEntity(arg3[0]);
		
		if (entity == null) return false;
		
		Main.customEntitiesHelper.spawnEntity(player.getLocation(), entity);
		
		return true;
	}

}

class SummonCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		ArrayList<String> tab = new ArrayList<String>();
		
		String lastEntry = arg3[arg3.length - 1].toLowerCase();
		
		for (String name : Main.customEntitiesHelper.getEntities().keySet()) {
			if (name.toLowerCase().contains(lastEntry)) tab.add(name);
		}
		
		return tab;
	}
	
}