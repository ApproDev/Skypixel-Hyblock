package com.BadDevelopers.SkypixelHyblock.Anticheat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import com.BadDevelopers.SkypixelHyblock.Main;

public interface Generic {
	public static DecimalFormat decimalFormat = new DecimalFormat("###.#");
	
	public static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	static String prefix = ChatColor.GOLD+"["+
			ChatColor.RED+"KoE-AC"+ChatColor.GOLD+"] "+ChatColor.RESET;
	
	public static int getPing(Player player) {
		return ((CraftPlayer) player).getHandle().ping;
	}
	
	HashMap<UUID,Long> lastDetection = new HashMap<UUID,Long>();
	
	public static void registerDetection(Player player, String info) {
		Long currentTime = System.currentTimeMillis();
		
		boolean containsKey = lastDetection.containsKey(player.getUniqueId());
		long lastDetectionTime;
		
		try {lastDetectionTime = lastDetection.get(player.getUniqueId());} 
		catch (NullPointerException e) {lastDetectionTime = 0L;}
		
		if (!containsKey || 
				lastDetectionTime + 1000 <= currentTime) {
			
			lastDetection.put(player.getUniqueId(), currentTime);
		
			postInfo(prefix+player.getName()+" "+info);
			saveInfo(player, info);
		}
		//else if (lastDetectionTime + 200 <= currentTime) player.kickPlayer(prefix+info);
	}
	
	private static void saveInfo(Player player, String info) {
		File file = new File(Main.main.getDataFolder().getPath()
				+"/AnticheatLogs/"+player.getUniqueId().toString()+".dat");
		
			try {
				if (!file.exists()) file.createNewFile();
				
				FileWriter writer = new FileWriter(file, true);
				
				writer.append(getTime()+info+"\n");
				
				writer.close();
			} catch (IOException e) {e.printStackTrace();}
	}
	
	private static void postInfo(String info) {
		System.out.println(info);
		
		Bukkit.getOperators().forEach((OfflinePlayer player) -> {
			if (player.isOnline()) player.getPlayer().sendMessage(info);
		});
	}
	
	public static String getTime() {
		return "["+dateFormat.format(Date.from(Instant.now()))+"] ";
	}
	
	public static void registerHandlers() {
		Main main = Main.main;
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(new ReachDetection(), main);
		pm.registerEvents(new SpeedDetection(), main);
		pm.registerEvents(new AuraDetection(), main);
	}
}
