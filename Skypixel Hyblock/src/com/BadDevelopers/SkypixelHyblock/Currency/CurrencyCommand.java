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

public class CurrencyCommand extends com.BadDevelopers.SkypixelHyblock.Command{
	
	public CurrencyCommand() {
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
				main.currency.setPurse(targetPlayer, quantity);
				break;
				
			case "add":
				main.currency.setPurse(targetPlayer, main.currency.getPurse(targetPlayer) + quantity);
				break;
				
			case "remove":
				main.currency.setPurse(targetPlayer, main.currency.getPurse(targetPlayer) - quantity);
				break;	
		}
		
		return true;
	}
	
}

class MoneyTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		ArrayList<String> tab = new ArrayList<String>();
		
		String lastEntry = arg3[arg3.length - 1].toLowerCase();
		
		switch (arg3.length) {
		
			case 1:
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (!p.getName().toLowerCase().contains(lastEntry.toLowerCase())) continue;
					tab.add(p.getName());
				}
				break;
			case 2:
				String[] options = new String[] {"get", "set", "add", "remove"};
				
				for (String choice : options) {
					if (choice.toLowerCase().contains(lastEntry)) tab.add(choice);
				}
				break;
			case 3:
				if (!arg3[1].equals("get")) tab.add("1");
				break;
			default:
				return null;
			
		}
		
		return tab;
	}
	
}
