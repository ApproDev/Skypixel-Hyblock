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
import org.bukkit.scheduler.BukkitScheduler;

import com.BadDevelopers.SkypixelHyblock.CustomRaces.Race;
import com.BadDevelopers.SkypixelHyblock.Currency.Currency;
import com.BadDevelopers.SkypixelHyblock.Currency.CurrencyCommand;
import com.BadDevelopers.SkypixelHyblock.Currency.CurrencyEventManager;
import com.BadDevelopers.SkypixelHyblock.CustomMobs.CustomEntitiesHelper;
import com.BadDevelopers.SkypixelHyblock.CustomMobs.SummonCommand;
import com.BadDevelopers.SkypixelHyblock.Enchantments.Glow;
import com.BadDevelopers.SkypixelHyblock.Items.ArmourHandler;
import com.BadDevelopers.SkypixelHyblock.Items.CustomWeaponsEventManager;
import com.BadDevelopers.SkypixelHyblock.Items.DropsHandler;
import com.BadDevelopers.SkypixelHyblock.Items.GiveCommand;
import com.BadDevelopers.SkypixelHyblock.Items.TalismanHandler;
import com.BadDevelopers.SkypixelHyblock.Skills.SkillsCommand;
import com.BadDevelopers.SkypixelHyblock.Skills.SkillsHandler;
import com.BadDevelopers.SkypixelHyblock.UI.RaceUI;
import com.BadDevelopers.SkypixelHyblock.UI.UIEventManager;

public class Main extends JavaPlugin {
	
	public Scoreboard scoreboard;
	public Currency currency;
	
	public static CustomEntitiesHelper customEntitiesHelper = new CustomEntitiesHelper();
	
	public static Stats stats;
	
	public static String prefix = ChatColor.AQUA+"["+ChatColor.GOLD+"Hyblock"+ChatColor.AQUA+"] ";
	
	public CustomRaces race;
	
    @Override
    public void onEnable() {
    	
    	registerEnchant(new Glow(new NamespacedKey(this, Glow.name)));
    	
    	scoreboard = new Scoreboard(this);
    	currency = new Currency(this);
    	stats = new Stats();
    	
    	initCommand(new GiveCommand(this));
    	initCommand(new CurrencyCommand());
    	initCommand(new SkillsCommand());
    	initCommand(new SummonCommand(this));
    	initCommand(new RideCommand());
    	
    	race = new CustomRaces(this);
    	
    	PluginManager pm = Bukkit.getPluginManager();
    	pm.registerEvents(new CurrencyEventManager(), this);
    	pm.registerEvents(scoreboard, this);
    	pm.registerEvents(new SkillsHandler(), this);
    	pm.registerEvents(new DropsHandler(), this);
    	pm.registerEvents(new CustomWeaponsEventManager(this), this);
    	pm.registerEvents(new UIEventManager(this), this);
    	pm.registerEvents(gen, this);
    	pm.registerEvents(race, this);
    	
    	BukkitScheduler sch = Bukkit.getServer().getScheduler();
    	
    	sch.runTaskTimer(this, stats, 0, 1);
    	
    	sch.runTaskTimer(this, new ArmourHandler(this), 1, 1);
    	
    	sch.runTaskTimer(this, scoreboard, 0, 5);
    	
    	sch.runTaskTimer(this, new TalismanHandler(), 0, 1*20);
    	
    	sch.runTaskTimer(this, race, 0, 5);
    	
    	Main main = this;
    	Bukkit.getServer().getScheduler().runTaskTimer(main, new Runnable() {
			@Override
			public void run() {
				Bukkit.getOnlinePlayers().forEach(
						player -> {
							Race race = Race.getRace(player, main);
							if (race == null) {
								Bukkit.getPluginManager().registerEvents(new RaceUI(player, main), main);
								}
							else race.applyStats(player, main);
							
						});
			}}, 0, 200);
    	
    	
    	Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() { // clear recipes on server start, as on load isnt possible
			@Override
			public void run() {
				Bukkit.clearRecipes();
				//for (World world : Bukkit.getWorlds()) gen.onWorldLoad(new WorldLoadEvent(world));
			}
    	});
    }
    
    
    //Sets the executor and TabCompletor for commands
    private void initCommand(com.BadDevelopers.SkypixelHyblock.Command command) {
    	PluginCommand pc = this.getCommand(command.name);
    	
    	pc.setExecutor(command);
    	pc.setTabCompleter(command.completer);
    }
    
    //Should register a custom enchant, but I removed functionality for that as I couldn't get it to work
    @Deprecated
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
    //helps the server use a custom terrain gen
    @Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
    	return gen;
    }
    //A simple way to do reflection
    public static Object getPrivateField(String fieldName, Class<?> clazz, Object object)
    {
        Field field;
        Object o = null;
        try
        {
            field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);
            
            o = field.get(object);
        }
        catch(NoSuchFieldException | IllegalAccessException e) { e.printStackTrace();}
        return o;
    }
}
