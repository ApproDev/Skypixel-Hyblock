package com.BadDevelopers.SkypixelHyblock;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.BadDevelopers.SkypixelHyblock.Currency.Currency;
import com.BadDevelopers.SkypixelHyblock.Currency.EventManager;
import com.BadDevelopers.SkypixelHyblock.Currency.MoneyCommand;
import com.BadDevelopers.SkypixelHyblock.Enchantments.EnchantCommand;
import com.BadDevelopers.SkypixelHyblock.Enchantments.Glow;
import com.BadDevelopers.SkypixelHyblock.Enchantments.Telekinesis;
import com.BadDevelopers.SkypixelHyblock.Items.ArmourHandler;
import com.BadDevelopers.SkypixelHyblock.Items.CustomWeaponsEventManager;
import com.BadDevelopers.SkypixelHyblock.Items.DropsHandler;
import com.BadDevelopers.SkypixelHyblock.Items.GiveCommand;
import com.BadDevelopers.SkypixelHyblock.UI.UIEventManager;

public class Main extends JavaPlugin {
	
	public Scoreboard scoreboard;
	public Currency currency;
	
	public static Stats stats;
	
	public static String prefix = ChatColor.AQUA+"["+ChatColor.GOLD+"Hyblock"+ChatColor.AQUA+"] ";
	
	public Telekinesis telekinesis;
	
	public EnchantCommand enchCommand;
	
    @Override
    public void onEnable() {
    	
    	telekinesis = new Telekinesis(new NamespacedKey(this, Telekinesis.name));
    	
    	registerEnchant(new Glow(new NamespacedKey(this, Glow.name)));
    	registerEnchant(telekinesis);
    	
    	scoreboard = new Scoreboard(this);
    	currency = new Currency(this);
    	stats = new Stats();
    	
    	enchCommand = new EnchantCommand(this);
    	
    	initCommand(new GiveCommand(this));
    	initCommand(new MoneyCommand());
    	initCommand(enchCommand);
    	
    	
    	PluginManager pm = Bukkit.getPluginManager();
    	pm.registerEvents(new EventManager(), this);
    	pm.registerEvents(scoreboard, this);
    	pm.registerEvents(new DropsHandler(), this);
    	pm.registerEvents(new CustomWeaponsEventManager(this), this);
    	pm.registerEvents(new UIEventManager(this), this);
    	pm.registerEvents(gen, this);
    	
    	
    	Bukkit.getServer().getScheduler().runTaskTimer(this, stats, 1, 1);
    	
    	Bukkit.getServer().getScheduler().runTaskTimer(this, new ArmourHandler(this), 1, 1);
    	
    	Bukkit.getServer().getScheduler().runTaskTimer(this, scoreboard ,0,1*20);
    	
    	Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() { // clear recipes on server start, as on load isnt possible
			@Override
			public void run() {
				
				Bukkit.clearRecipes();
				//for (World world : Bukkit.getWorlds()) gen.onWorldLoad(new WorldLoadEvent(world));
			}
    	});
    }
    
    private void initCommand(com.BadDevelopers.SkypixelHyblock.Command command) {
    	PluginCommand pc = this.getCommand(command.name);
    	
    	pc.setExecutor(command);
    	pc.setTabCompleter(command.completer);
    }
    
    void registerEnchant(Enchantment ench) {
            try {
                Field f = Enchantment.class.getDeclaredField("acceptingNew");
                f.setAccessible(true);
                f.set(null, true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Enchantment.registerEnchantment(ench);
            }
            catch (IllegalArgumentException e){
            }
            catch(Exception e){
                e.printStackTrace();
            }
        
    }
    
    public TerrainGeneration gen = new TerrainGeneration(this);
    
    @Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
    	return gen;
    }
}
