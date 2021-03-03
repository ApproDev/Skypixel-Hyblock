package com.BadDevelopers.SkypixelHyblock.Enchantments;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.BadDevelopers.SkypixelHyblock.Command;
import com.BadDevelopers.SkypixelHyblock.Main;

@Deprecated
public class EnchantCommand extends Command {
	
	public Enchantment[] enchants;
	Main main;
	
	public EnchantCommand(Main main) {
		this.main = main;
		//this.enchants = new Enchantment[] {main.telekinesis};
		this.completer = new EnchantCompleter(enchants);
		this.name = "skyenchant";
	}

	@Override
	public boolean onCommand(CommandSender arg0, org.bukkit.command.Command arg1, String arg2, String[] arg3) {
		
		if (arg3.length != 3) return false;
		
		Player player = Bukkit.getPlayer(arg3[0]);
		if (player == null) return false;
		
		Enchantment ench;

		ench = Enchantment.getByKey(new NamespacedKey(main, arg3[1]));
		
		Integer quantity;
		try {
			quantity = Integer.parseInt(arg3[2]);
		} catch (NumberFormatException e) {return false;}
		
		try {
			player.getInventory().setItemInMainHand(addEnchantment(player.getInventory().getItemInMainHand(), ench, quantity));
		} catch (Exception e) {
			player.sendMessage(Main.prefix+ChatColor.DARK_RED+"Error: Cannot apply "+ench.getKey().getKey()+" level "+quantity+"!");
		}
		
		return true;
	}
	
	static ItemStack addEnchantment(ItemStack is, Enchantment ench, Integer quantity) throws Exception {
		for (Enchantment e : is.getEnchantments().keySet()) if (ench.conflictsWith(e)) throw new Exception();
		
		is.addEnchantment(ench, quantity);
		ItemMeta im = is.getItemMeta();
		List<String> lore = im.getLore();
		
		if (lore == null) lore = new ArrayList<String>();
		
		lore.add(ChatColor.GRAY+ench.getKey().getKey()+" "+IntegerToRomanNumeral(quantity)+ChatColor.RESET);
		
		im.setLore(lore);
		
		is.setItemMeta(im);
		
		return is;
	}
	
	public static String IntegerToRomanNumeral(int input) {
	    if (input < 1 || input > 3999)
	        return "Invalid Roman Number Value";
	    String s = "";
	    while (input >= 1000) {
	        s += "M";
	        input -= 1000;        }
	    while (input >= 900) {
	        s += "CM";
	        input -= 900;
	    }
	    while (input >= 500) {
	        s += "D";
	        input -= 500;
	    }
	    while (input >= 400) {
	        s += "CD";
	        input -= 400;
	    }
	    while (input >= 100) {
	        s += "C";
	        input -= 100;
	    }
	    while (input >= 90) {
	        s += "XC";
	        input -= 90;
	    }
	    while (input >= 50) {
	        s += "L";
	        input -= 50;
	    }
	    while (input >= 40) {
	        s += "XL";
	        input -= 40;
	    }
	    while (input >= 10) {
	        s += "X";
	        input -= 10;
	    }
	    while (input >= 9) {
	        s += "IX";
	        input -= 9;
	    }
	    while (input >= 5) {
	        s += "V";
	        input -= 5;
	    }
	    while (input >= 4) {
	        s += "IV";
	        input -= 4;
	    }
	    while (input >= 1) {
	        s += "I";
	        input -= 1;
	    }    
	    return s;
	}

}

class EnchantCompleter implements TabCompleter {
	
	final Enchantment[] enchants;
	
	public EnchantCompleter(Enchantment[] enchants) {
		this.enchants = enchants;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, org.bukkit.command.Command arg1, String arg2, String[] arg3) {
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
			for (Enchantment ench : enchants) {
				if (!ench
						.getKey()
						.getKey()
						.toLowerCase()
						.contains(lastEntry
								.toLowerCase())) continue;
				tab.add(ench.getKey().getKey());
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
