package com.BadDevelopers.SkypixelHyblock;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;


//A holder class to assist with Main#initCommand
public abstract class Command implements CommandExecutor {
	public TabCompleter completer;
	public String name;
}
