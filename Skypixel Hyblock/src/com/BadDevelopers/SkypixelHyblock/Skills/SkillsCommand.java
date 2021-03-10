package com.BadDevelopers.SkypixelHyblock.Skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.BadDevelopers.SkypixelHyblock.Command;
import com.BadDevelopers.SkypixelHyblock.Main;
import com.BadDevelopers.SkypixelHyblock.Skills.Skills.Skill;

public class SkillsCommand extends Command{

	public SkillsCommand() {
		completer = new SkillTabCompleter();
		name = "skyskill";	
	}
	
	Main main = JavaPlugin.getPlugin(Main.class);
	
	public boolean onCommand(CommandSender arg0, org.bukkit.command.Command arg1, String arg2, String[] arg3) {		
		
		if (arg3.length < 3) return false;
		
		if (!(arg0 instanceof Player)) return false;
			
		Player player = (Player) arg0;
		
		Player targetPlayer = Bukkit.getPlayer(arg3[0]);
		
		if (arg3[1].toLowerCase().equals("get")) {
			
			player.sendMessage(targetPlayer.getName() + " has " + Skill.valueOf(arg3[2]).getSkill(targetPlayer) + " " + arg3[2] + " skill exp");
			return true;
		}
		
		if (arg3.length != 4) return false;
		
		Integer quantity;
		try {
			quantity = Integer.parseInt(arg3[3]);
		} catch (NumberFormatException UselessVariable) {return false;}
		
		if (Skill.valueOf(arg3[2]).getSkill(targetPlayer) == null) {
			Skill.valueOf(arg3[2]).setSkill(targetPlayer, 0); 
		}
		
		switch (arg3[1].toLowerCase()) {
				
			case "set":				
				Skill.valueOf(arg3[2]).setSkill(targetPlayer, quantity);
				break;
				
			case "add":
				Skill.valueOf(arg3[2]).setSkill(targetPlayer, Skill.valueOf(arg3[2]).getSkill(targetPlayer) + quantity);
				main.scoreboard.playerSkillGain.put(player, quantity);
				break;
				
			case "remove":
				Skill.valueOf(arg3[2]).setSkill(targetPlayer, Skill.valueOf(arg3[2]).getSkill(targetPlayer) - quantity);
				main.scoreboard.playerSkillGain.put(player, - quantity);
				break;	
		}
		
		main.scoreboard.playerSkillGainType.put(player, Skill.valueOf(arg3[2]));
		main.scoreboard.playerActionBarCooldown.put(player, 0);

		return true;
	}

}

class SkillTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender arg0, org.bukkit.command.Command arg1, String arg2, String[] arg3) {
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
				for (Skill s : Skill.values()) {
					
					if (s.toString().toLowerCase().contains(lastEntry))
					
					tab.add(s.toString());
				}
				break;
			case 4:
				if (!arg3[1].equals("get")) tab.add("1");
				break;
			default:
				return null;	
		}
		
		return tab;
	}
	
}
