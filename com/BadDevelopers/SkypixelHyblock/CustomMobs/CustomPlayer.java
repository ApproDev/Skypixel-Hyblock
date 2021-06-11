package com.BadDevelopers.SkypixelHyblock.CustomMobs;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.BadDevelopers.SkypixelHyblock.Main;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EnumProtocolDirection;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import net.minecraft.server.v1_16_R3.PlayerInteractManager;
import net.minecraft.server.v1_16_R3.WorldServer;


public class CustomPlayer extends EntityPlayer implements Listener {

	public CustomPlayer(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile,
			PlayerInteractManager playerinteractmanager) {
		super(minecraftserver, worldserver, gameprofile, playerinteractmanager);
	}
	
	public CustomPlayer(Location loc, String name, String url, String signature) {
		super(((CraftWorld) loc.getWorld()).getHandle().getMinecraftServer(),
				((CraftWorld) loc.getWorld()).getHandle().getMinecraftWorld(),
				getProfile(name, url, signature),
				
				new CustomInteractManager(
						((CraftWorld) loc.getWorld()).getHandle().getMinecraftWorld()));
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		this.setYawPitch(0, 0);
		this.playerConnection = new CustomPlayerConnection(server, new CustomNetworkManager(EnumProtocolDirection.CLIENTBOUND), this);
		
		Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(Main.class));
		//((CraftWorld)loc.getWorld()).getHandle().addEntity(this);
		
		
		this.getWorldServer().addPlayerJoin(this);
		
		for (Player player : Bukkit.getOnlinePlayers()) {
	        sendFakePlayerInfo(player);
		}	
		
	}
	public void remove() {
		if (removed) return;
		this.getWorldServer().removePlayer(this);
		removed = true;
	}
	
	boolean removed = false;
	boolean unregister = false;
	
	@Override
	public void die() {
		unregister = true;
		remove();
		
		Bukkit.getOnlinePlayers().forEach(player -> { sendPlayerRemoveInfo(player); });
	}
	
	public void sendPlayerRemoveInfo(Player player) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutEntityDestroy(this.getId()));
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this));
	}
	
	public void sendFakePlayerInfo(Player player) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this));
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (unregister) {
			event.getHandlers().unregister(this);
			return;
		}
		sendFakePlayerInfo(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerDie(PlayerDeathEvent event) {
		if (unregister) {
			event.getHandlers().unregister(this);
			return;
		}
		if (event.getEntity().getEntityId() != this.getId()) return;
		
		die();
	}
	
	public CustomPlayer(Location loc) {
		this(loc, "Seraphine", 
				"ewogICJ0aW1lc3RhbXAiIDogMTYxNDk0MDE4OTUzNSwKICAicHJvZmlsZUlkIiA6ICJiYTgyYzY3MGNmMWE0ZDlmYWUxZjk0ZGRkMDdhYWZkMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJTZXJhcGhpbmUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTJlMTVjMmQ3Y2ZkZDMwMDhkZDc5MGIyZjg5MTc1YzczZDVlNzc2MjdhZjY1M2ZjMThlOWZjYzhiOTZhZGY4NCIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9",
				"Wqucq3+I8mPLACYJln3N+fmDjycOpgzOPMOuSciICrlybeoOjdy+auEo7gOIqVFSL0K5GJfKB7YnJbJAytQEFXkCnKvNHasa239ynMDvYd0X0v+6Jm1HiZ142HZ6Flrro9PGOZ0P4mlUAqNMeBvI/HWQ28A3k08K0rD9e8tOZ0uF7TZhi0STprlr+9ukiX0m/e2Cl9F4kOxIjGIyGJnRKuK8OJojfMHx50m9IKuMu2XVrCYZYJ2cHQjXBYZS7FiIoFJDyY0PGqB+m+mj76PbI4QZTl4wHxUWLX9uI6oYcmnIYbcRVKmyab7kv9hcIAP5l8cUFRUkncgD04Pi20QAFpkn5XRV4zVc8C4246kSHVqTwZhMlVaQ/+65ead7vRyjaLupkhv4Mznege0PuaVsiPsltkpGiLdgq75+wFMl6AXyT4OE6AQk/JKIJcaRJCyBZ5mLJDrsdT2IMC5JGCk9KH+zMTekvSlDJpTVeeVDxnvO4Ttrixzv5iUW1yH2IFsst9+1lXK7cwyiqKz7ETW7sT23KjyVBgMPFA17hsgyYXg4nIk90zWbBUQUe8GMiSN7WROOWVNH+XHh9qz84AxhNeibwlgxJJlITMR5bXVxcqggL5yeiZ6zTP0NmpEF6s2fygfKJuGI10mdMgXDiO4fap/v7b1/SIkTNvll+ZY36k0=");
	}
	
	static GameProfile getProfile(String name, String url, String signature) {
		UUID id = UUID.randomUUID();
		GameProfile profile = new GameProfile(id, name);
		profile.getProperties().put("textures", new Property("textures", url, signature));
		
		return profile;
	}

}

class CustomInteractManager extends PlayerInteractManager {

	public CustomInteractManager(WorldServer worldserver) {
		super(worldserver);
	}
	
}