package com.BadDevelopers.SkypixelHyblock;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

public abstract class Command implements CommandExecutor {
	public TabCompleter completer;
	public String name;
}
