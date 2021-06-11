package com.BadDevelopers.SkypixelHyblock.CustomMobs;

import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import net.minecraft.server.v1_16_R3.NetworkManager;
import net.minecraft.server.v1_16_R3.PlayerConnection;

public class CustomPlayerConnection extends PlayerConnection {

	public CustomPlayerConnection(MinecraftServer minecraftserver, NetworkManager networkmanager,
			EntityPlayer entityplayer) {
		super(minecraftserver, networkmanager, entityplayer);
	}
	/*@Override
	public void syncPosition() {}
	@Override
	public void disconnect(IChatBaseComponent ichatbasecomponent) {}
	@Override
	public void disconnect(String s) {}*/

}
