package com.BadDevelopers.SkypixelHyblock;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import com.BadDevelopers.SkypixelHyblock.Currency.Currency;
import com.BadDevelopers.SkypixelHyblock.Currency.EventManager;
import com.BadDevelopers.SkypixelHyblock.Currency.MoneyCommand;
import com.BadDevelopers.SkypixelHyblock.Items.DropsHandler;
import com.BadDevelopers.SkypixelHyblock.Items.GiveCommand;

public class Main extends JavaPlugin {
	
	public Scoreboard scoreboard;
	public Currency currency;
	
    @Override
    public void onEnable() {
    	
    	scoreboard = new Scoreboard(this);
    	currency = new Currency(this);
    	
    	initCommand(new GiveCommand());
    	initCommand(new MoneyCommand());
    	
    	Bukkit.getPluginManager().registerEvents(new EventManager(), this);
    	Bukkit.getPluginManager().registerEvents(new DropsHandler(), this);
    	
    	Bukkit.getServer().getScheduler().runTaskTimer(this, scoreboard ,0,1*20);
    	
    	Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() { // clear recipes on server start, as on load isnt possible
			@Override
			public void run() {
				
				Bukkit.clearRecipes();

			}
    	});
    }
    
    private void initCommand(com.BadDevelopers.SkypixelHyblock.Command command) {
    	PluginCommand pc = this.getCommand(command.name);
    	
    	pc.setExecutor(command);
    	pc.setTabCompleter(command.completer);
    }
}