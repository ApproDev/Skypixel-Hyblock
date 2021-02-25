package com.BadDevelopers.SkypixelHyblock.Currency;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.BadDevelopers.SkypixelHyblock.Main;

public class MoneyCommand extends com.BadDevelopers.SkypixelHyblock.Command{
	
	public MoneyCommand() {
		completer = new MoneyTabCompleter();
		name = "skycoin";
	}

	Main main = JavaPlugin.getPlugin(Main.class);
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {		
		
		if (arg3.length < 2) return false;
		
		if (!(arg0 instanceof Player)) return false;
			
		Player player = (Player) arg0;
		
		Player targetPlayer = Bukkit.getPlayer(arg3[0]);
		
		if (arg3[1].toLowerCase().equals("get")) {
			
			player.sendMessage(targetPlayer.getName() + " has " + main.currency.getPurse(targetPlayer) + " coins ");
			return true;
		}
		
		if (arg3.length != 3) return false;
		
		Long quantity;
		try {
			quantity = Long.parseLong(arg3[2]);
		} catch (NumberFormatException UselessVariable) {return false;}
		
		switch (arg3[1].toLowerCase()) {
				
			case "set":				
				main.currency.setPurse(player, quantity);
				break;
				
			case "add":
				main.currency.setPurse(player, main.currency.getPurse(player) + quantity);
				break;
				
			case "remove":
				main.currency.setPurse(player, main.currency.getPurse(player) - quantity);
				break;	
		}
		
		return true;
	}
	
}

class MoneyTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		ArrayList<String> tab = new ArrayList<String>();
		
		switch (arg3.length) {
		
			case 1:
				for (Player p : Bukkit.getOnlinePlayers()) tab.add(p.getName());
				break;
			case 2:
				tab.add("get");
				tab.add("set");
				tab.add("add");
				tab.add("remove");
				break;
			case 3:
				tab.add("1");
				break;
			default:
				return null;
			
		}
		
		return tab;
	}
	
}
