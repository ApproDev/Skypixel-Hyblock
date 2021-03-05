package com.BadDevelopers.SkypixelHyblock.CustomMobs;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_16_R3.PlayerConnection;

@Deprecated
public class CustomHumanEntity extends EntityHuman {
	
	public CustomHumanEntity(Location loc, String name, String url) {
		super(((CraftWorld) loc.getWorld()).getHandle(), new BlockPosition(loc.getX(), loc.getY(), loc.getZ()), 0, getProfile(name, url));
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		this.setYawPitch(0, 0);
	
		((CraftWorld)loc.getWorld()).getHandle().addEntity(this);
	}
	
	public CustomHumanEntity(Location loc) {
		this(loc, "Seraphine", "http://textures.minecraft.net/texture/52e15c2d7cfdd3008dd790b2f89175c73d5e77627af653fc18e9fcc8b96adf84");
	}
	
	public void sendFakePlayerInfo(Player player) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        //connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this));
	}
	
	static GameProfile getProfile(String name, String url) {
		UUID id = UUID.randomUUID();
		GameProfile profile = new GameProfile(id, name);
		profile.getProperties().put("textures", new Property("textures", url));
	
		return profile;
	}

	@Override
	public boolean isCreative() {
		return false;
	}

	@Override
	public boolean isSpectator() {
		return false;
	}

}
