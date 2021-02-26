package com.BadDevelopers.SkypixelHyblock.UI;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;

import com.BadDevelopers.SkypixelHyblock.Main;

public class CraftingUI extends InvHandler {

	public CraftingUI(HumanEntity player, Main main) {
		super(player, main);
	}

	
	@Override
	public void initGUI() {
		inv = Bukkit.createInventory(null, 45, "Crafting");
		allowableSlots.add(10);
		allowableSlots.add(11);
		allowableSlots.add(12);
		allowableSlots.add(19);
		allowableSlots.add(20);
		allowableSlots.add(21);
		allowableSlots.add(28);
		allowableSlots.add(29);
		allowableSlots.add(30);
		allowableSlots.add(24);
		}
}
