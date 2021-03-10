package com.BadDevelopers.SkypixelHyblock;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class RideCommand extends Command {

	public RideCommand() {
		this.name = "skyride";
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, org.bukkit.command.Command arg1, String arg2, String[] arg3) {
		Bukkit.getEntity(UUID.fromString(arg3[0]))
		.addPassenger(Bukkit.getEntity(UUID.fromString(arg3[1])));
		return true;
	}

}
