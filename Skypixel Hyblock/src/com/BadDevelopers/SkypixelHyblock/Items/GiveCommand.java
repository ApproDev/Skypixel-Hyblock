package com.BadDevelopers.SkypixelHyblock.Items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.BadDevelopers.SkypixelHyblock.Items.ItemHolder.Item;

public class GiveCommand extends com.BadDevelopers.SkypixelHyblock.Command {
	
	public GiveCommand() {
		completer = new GiveCompleter();
		name = "skygive";
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		if (arg3.length != 3) return false;
		
		Player player = Bukkit.getPlayer(arg3[0]);
		if (player == null) return false;
		
		Item item;
		try {
			item = Item.valueOf(arg3[1]);
		} catch (IllegalArgumentException e) {return false;}
		Integer quantity;
		try {
			quantity = Integer.parseInt(arg3[2]);
		} catch (NumberFormatException e) {return false;}
		
		item.giveItem(player, quantity);
		
		return true;
	}

}

class GiveCompleter implements TabCompleter {
	
	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		ArrayList<String> tab = new ArrayList<String>();
		
		String lastEntry = arg3[arg3.length - 1];
		switch (arg3.length) {
		
		case 1:
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (!p.getName().toLowerCase().contains(lastEntry.toLowerCase())) continue;
				tab.add(p.getName());
			}
			break;
		case 2:
			for (Item item : Item.values()) {
				if (!item.toString().toLowerCase().contains(lastEntry.toLowerCase())) continue;
				tab.add(item.toString());
			}
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
