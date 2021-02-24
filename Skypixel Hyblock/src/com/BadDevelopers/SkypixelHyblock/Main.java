package com.BadDevelopers.SkypixelHyblock;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import com.BadDevelopers.SkypixelHyblock.Currency.Currency;
import com.BadDevelopers.SkypixelHyblock.Currency.EventManager;
import com.BadDevelopers.SkypixelHyblock.Items.DropsHandler;
import com.BadDevelopers.SkypixelHyblock.Items.GiveCommand;

public class Main extends JavaPlugin {
	
	static Scoreboard scoreboard = new Scoreboard();
	public static Currency currency = new Currency();
	
    @Override
    public void onEnable() {
    	initCommand(new GiveCommand());
	Bukkit.getPluginManager().registerEvents(new EventManager(), this);
    	Bukkit.getPluginManager().registerEvents(new DropsHandler(), this);
    	
	Bukkit.getServer().getScheduler().runTaskTimer(this, scoreboard ,0,1*20);
    }
    
    private void initCommand(com.BadDevelopers.SkypixelHyblock.Command command) {
    	PluginCommand pc = this.getCommand(command.name);
    	
    	pc.setExecutor(command);
    	pc.setTabCompleter(command.completer);
    }
}
